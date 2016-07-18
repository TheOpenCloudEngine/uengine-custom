package com.abc.modeler;

import org.springframework.stereotype.Component;
import org.uengine.modeling.modeler.palette.BPMNPalette;

/**
 * Created by jjy on 2016. 7. 18..
 */

@Component
public class AnalysisBPMNPalette extends BPMNPalette{
    public AnalysisBPMNPalette() {
        super();

        addPalette(new AnalysisPalette());
    }
}
