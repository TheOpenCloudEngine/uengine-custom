package com.abc.modeler;


import com.abc.activitytype.view.*;
import org.uengine.modeling.Palette;
import org.uengine.modeling.Symbol;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class DataProcessingPalette extends Palette {

    public DataProcessingPalette() {
        this.setName("Data Processing");

        addSymbol((new AnalysisActivityView()).createSymbol());
        addSymbol((new SyncActivityView()).createSymbol());
        addSymbol((new ShellActivityView()).createSymbol());
        addSymbol(new SparkActivityView().createSymbol());
        addSymbol(new MapreduceActivityView().createSymbol());
        addSymbol((new CustomSQLActivityView()).createSymbol());
        addSymbol((new HiveActivityView()).createSymbol());
        addSymbol((new DataInputActivityView()).createSymbol());
    }

    public DataProcessingPalette(String type) {
        this();
        setType(type);
    }

    @Override
    public void addSymbol(Symbol symbol) {
        symbol.setIconResizing(true);

        super.addSymbol(symbol);
    }
}
