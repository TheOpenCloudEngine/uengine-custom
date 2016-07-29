package com.abc.activitytype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Order;
import org.metaworks.annotation.Range;
import org.metaworks.dao.ConnectionFactory;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.UEngineException;
import org.uengine.kernel.ValidationContext;
import org.uengine.util.dao.DataSourceConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by uEngineYBS on 2016-07-28.
 *
 * 초간단 SQL 액티비티.
 * 현재는 어떤 옵션없이 select 쿼리에 한해서만 작동한다.
 * 차후 이 부분을 확장할 때는 기존의 SQLActivity의 옵션들로 delete, update, insert를 가능하게 처리예정.
 *
 */
public class CustomSQLActivity extends DefaultActivity {

    ConnectionFactory connectionFactory;
    public ConnectionFactory getConnectionFactory() { return this.connectionFactory; }
    public void setConnectionFactory(ConnectionFactory factory) { this.connectionFactory = factory; }

    @Order(2)
    String sqlStmt;
        public String getSqlStmt() { return this.sqlStmt; }
        public void setSqlStmt(String value) { this.sqlStmt = value; }

    /**
     * TODO Option
     */
    String optionDataSource;
    @Face(
            displayName = "접속 정보"
    )
    @Range(options={"Default", "Hive", "Spark"}, values={"DEFAULT", "HIVE", "SPARK"})
    @Order(3)
    public String getOptionDataSource() {
        return optionDataSource;
    }
    public void setOptionDataSource(String optionDataSource) {
        this.optionDataSource = optionDataSource;
    }

    public CustomSQLActivity() {
        super("Custom SQL");
    }

    public void executeActivity(ProcessInstance instance) throws Exception {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {

            // MayBe From optionDataSource(), change Connection Default, Hive, Spark
            boolean uEngineShouldManageTheTransaction = !instance.getProcessTransactionContext().getProcessManager().isManagedTransaction() && this.getConnectionFactory() instanceof DataSourceConnectionFactory;

            if(this.getConnectionFactory() == null) {
                con = instance.getProcessTransactionContext().getConnection();
            } else if(uEngineShouldManageTheTransaction) {
                con = instance.getProcessTransactionContext().createManagedExternalConnection((DataSourceConnectionFactory)this.getConnectionFactory());
            } else {
                try {
                    con = this.getConnectionFactory().getConnection();
                } catch (Exception exception) {
                    throw new UEngineException("[SQLActivity]Error when to get connection from connection factory: " + exception.getMessage(), exception);
                }
            }

            String actualSql = this.evaluateContent(instance, this.getSqlStmt()).toString().trim();

            pstmt = con.prepareStatement(actualSql);
            resultSet = pstmt.executeQuery();

            this.getResult(resultSet, instance);

            con.close();

            if(this.getConnectionFactory() != null && !uEngineShouldManageTheTransaction) {
                con.close();
                pstmt.close();
                resultSet.close();
            }

            this.fireComplete(instance);

        } catch (Exception e) {
            throw e;
        } finally {
            if(con != null) con.close();
            if(pstmt != null) pstmt.close();
            if(resultSet != null) resultSet.close();
        }
    }

    protected void getResult(ResultSet resultSet, ProcessInstance instance) throws Exception {

        List<JSONObject> resultSetList = new ArrayList<JSONObject>();

        try {
            ResultSetMetaData rsMeta = resultSet.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for(int i=1;i<=columnCnt;i++) {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
            }

            while(resultSet.next()) { // convert each object to an human readable JSON object
                JSONObject obj = new JSONObject();
                for(int i=1;i<=columnCnt;i++) {
                    String key = columnNames.get(i - 1);
                    String value = resultSet.getString(i);
                    obj.put(key, value);
                }
                resultSetList.add(obj);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String listString = objectMapper.writeValueAsString(resultSetList);


            instance.setProperty(getTracingTag(), "resultSql", listString);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ValidationContext validate(Map options) {
        ValidationContext vc = super.validate(options);
        String sql = this.getSqlStmt();

        if(sql == null || "".equals(sql)) {
            vc.add("Sql Stmt is Mandatory");
        }

        return vc;
    }

}