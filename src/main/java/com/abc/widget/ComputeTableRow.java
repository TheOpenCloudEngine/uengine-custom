package com.abc.widget;

import org.metaworks.EventContext;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Range;
import org.metaworks.annotation.ServiceMethod;

import java.io.Serializable;

/**
 * Created by jjy on 2016. 9. 1..
 */
@Face(ejsPathForArray = "dwr/metaworks/genericfaces/CleanArrayFace.ejs")
public class ComputeTableRow implements Serializable{

    String column;
        public String getColumn() {
            return column;
        }
        public void setColumn(String column) {
            this.column = column;
        }

    String function;
        @Range(
                options={"sum", "avg", "round"},
                values={"sum", "avg", "round"}


        )
        public String getFunction() {
            return function;
        }
        public void setFunction(String function) {
            this.function = function;
        }

    int n;
        public int getN() {
            return n;
        }
        public void setN(int n) {
            this.n = n;
        }

    String formula;
        public String getFormula() {
            return formula;
        }
        public void setFormula(String formula) {
            this.formula = formula;
        }

    boolean isDeleted;
        public boolean isDeleted() {
            return isDeleted;
        }
        public void setDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
        }



    @ServiceMethod(target=ServiceMethod.TARGET_SELF, inContextMenu = true)
    public void remove(){
        setDeleted(true);
    }

    @ServiceMethod(target=ServiceMethod.TARGET_SELF, eventBinding = EventContext.EVENT_CHANGE, bindingFor = "function", callByContent = true, inContextMenu = true)
    public void refresh(){

        setFormula(getFunction() + "(" + getColumn() + ", " + getN() + ")");

    }

    @ServiceMethod(target=ServiceMethod.TARGET_SELF, eventBinding = EventContext.EVENT_CHANGE, bindingFor = "column", callByContent = true)
    public void refresh_(){

       refresh();

    }

}
