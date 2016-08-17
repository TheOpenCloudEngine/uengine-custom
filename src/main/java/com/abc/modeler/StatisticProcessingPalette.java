package com.abc.modeler;


import com.abc.activitytype.view.*;
import org.uengine.modeling.Palette;
import org.uengine.modeling.Symbol;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class StatisticProcessingPalette extends Palette {

    public StatisticProcessingPalette() {
        this.setName("Statistic Processing");

//        addSymbol((new AnalysisActivityView()).createSymbol());
//        addSymbol((new SyncActivityView()).createSymbol());
//        addSymbol((new ShellActivityView()).createSymbol());
//        addSymbol((new HadoopActivityView()).createSymbol());
//        addSymbol((new CustomSQLActivityView()).createSymbol());
//        addSymbol((new HiveActivityView()).createSymbol());
//        addSymbol((new DataInputActivityView()).createSymbol());
    }

    public StatisticProcessingPalette(String type) {
        this();
        setType(type);
    }

    @Override
    public void addSymbol(Symbol symbol) {
        symbol.setIconResizing(true);

        super.addSymbol(symbol);
    }
}
