package com.abc.widget;

import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.metaworks.annotation.Range;
import org.metaworks.annotation.ServiceMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 9. 1..
 */
public class ComputeTable implements ContextAware{

   List<ComputeTableRow> computeTableRowList;

    public List<ComputeTableRow> getComputeTableRowList() {
        return computeTableRowList;
    }

    public void setComputeTableRowList(List<ComputeTableRow> computeTableRowList) {
        this.computeTableRowList = computeTableRowList;
    }


    public ComputeTable(){

        setComputeTableRowList(new ArrayList<ComputeTableRow>());

        getComputeTableRowList().add(new ComputeTableRow());

    }


    MetaworksContext metaworksContext;
        @Override
        public MetaworksContext getMetaworksContext() {
            return metaworksContext;
        }
        @Override
        public void setMetaworksContext(MetaworksContext metaworksContext) {
            this.metaworksContext = metaworksContext;
        }





    @ServiceMethod(callByContent = true)
    public void addRow(){
        setMetaworksContext(new MetaworksContext());
        getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);

        getComputeTableRowList().add(new ComputeTableRow());
    }
}
