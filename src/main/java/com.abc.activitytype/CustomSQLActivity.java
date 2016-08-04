package com.abc.activitytype;

import com.abc.face.ParameterValueListFace;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.Order;
import org.metaworks.annotation.Range;
import org.metaworks.dao.ConnectionFactory;
import org.uengine.kernel.*;
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
        @Hidden
        public ConnectionFactory getConnectionFactory() { return this.connectionFactory; }
        public void setConnectionFactory(ConnectionFactory factory) { this.connectionFactory = factory; }




    List<String> parameterValueList = new ArrayList();
        @Face(
                faceClass = ParameterValueListFace.class,
                displayName = "파라미터 값 리스트"
        )
        @Order(2)
        public List<String> getParameterValueList() { return parameterValueList; }
        public void setParameterValueList(List<String> parameterValueList) { this.parameterValueList = parameterValueList; }

    /*
    ParameterValuePanel parameterValuePanel;
        @Order(2)
        public ParameterValuePanel getParameterValuePanel() { return parameterValuePanel; }
        public void setParameterValuePanel(ParameterValuePanel parameterValuePanel) { this.parameterValuePanel = parameterValuePanel; }
    */

    String sqlStmt;
        @Order(3)
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
        @Order(4)
        public String getOptionDataSource() {
            return optionDataSource;
        }
        public void setOptionDataSource(String optionDataSource) {
        this.optionDataSource = optionDataSource;
    }

    public CustomSQLActivity() {

        super("Custom SQL");
        //this.setParameterValuePanel(new ParameterValuePanel());
    }

    public void executeActivity(ProcessInstance instance) throws Exception {


        /**
         * this is sample code, simply trace list value;
         */
        List<String> getParameterValueList = getParameterValueList();
        for(String value : getParameterValueList) {
            System.out.println("============== setting value =======================");
            System.out.println(value);
        }

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            boolean uEngineShouldManageTheTransaction = !instance.getProcessTransactionContext().getProcessManager().isManagedTransaction() && this.getConnectionFactory() instanceof DataSourceConnectionFactory;

            if("DEFAULT".equals(getOptionDataSource())) {
                if (this.getConnectionFactory() == null) {
                    con = instance.getProcessTransactionContext().getConnection();
                } else if (uEngineShouldManageTheTransaction) {
                    con = instance.getProcessTransactionContext().createManagedExternalConnection((DataSourceConnectionFactory) this.getConnectionFactory());
                } else {
                    try {
                        con = this.getConnectionFactory().getConnection();
                    } catch (Exception exception) {
                        throw new UEngineException("[SQLActivity]Error when to get connection from connection factory: " + exception.getMessage(), exception);
                    }
                }
            } else if("HIVE".equals(getOptionDataSource())) {
                try {
                    con = this.getCreateConnection(getOptionDataSource());
                } catch (Exception exception) {
                    throw new UEngineException("[SQLActivity]Error when to get connection from connection factory: " + exception.getMessage(), exception);
                }
            } else if("SPARK".equals(getOptionDataSource())) {
                try {
                    con = this.getCreateConnection(getOptionDataSource());
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

    // it's Sample, so this get connection from Oracle Driver
    // after Hive, spark Dicide, change business Logic For Hive, Spark
    protected Connection getCreateConnection(String optionDataSource) {

        String jdbcDriver = "";
        String conUrl = "";
        String conId = "";
        String conPwd = "";
        if("HIVE".equals(optionDataSource)) {
            jdbcDriver = GlobalContext.getPropertyString("oracle.jdbc.driverClassName");
            conUrl = GlobalContext.getPropertyString("oracle.jdbc.url");
            conId = GlobalContext.getPropertyString("oracle.jdbc.username");
            conPwd = GlobalContext.getPropertyString("oracle.jdbc.password");
        } else if("SPARK".equals(optionDataSource)) {
            jdbcDriver = GlobalContext.getPropertyString("oracle.jdbc.driverClassName");
            conUrl = GlobalContext.getPropertyString("oracle.jdbc.url");
            conId = GlobalContext.getPropertyString("oracle.jdbc.username");
            conPwd = GlobalContext.getPropertyString("oracle.jdbc.password");
        }

        Connection con = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(conUrl, conId, conPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
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