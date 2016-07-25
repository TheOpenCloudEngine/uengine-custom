package com.abc.modeler;

import com.abc.activitytype.AnalysisActivity;
import com.abc.activitytype.view.AnalysisActivityView;
import com.abc.activitytype.view.ShellActivityView;
import com.abc.activitytype.view.SyncActivityView;
import org.uengine.kernel.bpmn.view.PoolView;
import org.uengine.kernel.bpmn.view.SubProcessView;
import org.uengine.kernel.view.HumanActivityView;
import org.uengine.kernel.view.RestWebServiceActivityView;
import org.uengine.kernel.view.RoleView;
import org.uengine.modeling.Palette;
import org.uengine.modeling.PaletteWindow;
import org.uengine.modeling.Symbol;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class AnalysisPalette extends PaletteWindow {

    public AnalysisPalette() {
        this.setName("Analysis");

        addSymbol((new AnalysisActivityView()).createSymbol());
        addSymbol((new SyncActivityView()).createSymbol());
        addSymbol((new ShellActivityView()).createSymbol());
    }

    public AnalysisPalette(String type) {
        this();
        setType(type);
    }

    @Override
    public void addSymbol(Symbol symbol) {
        symbol.setIconResizing(true);

        super.addSymbol(symbol);
    }
}
