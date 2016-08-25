package com.abc.modeler;

import org.springframework.stereotype.Component;
import org.uengine.modeling.CompositePalette;
import org.uengine.modeling.Palette;
import org.uengine.modeling.PaletteWindow;
import org.uengine.modeling.modeler.palette.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 7. 18..
 */

@Component
public class AnalysisBPMNPalette extends BPMNPalette{
    public AnalysisBPMNPalette() {
        super();

        CompositePalette bpmnPaletteWindow = new PaletteWindow();
        /**
         * BPMN
         */
        bpmnPaletteWindow.addPalette(new EventPalette());
        bpmnPaletteWindow.addPalette(new TaskPalette());
        bpmnPaletteWindow.addPalette(new GatewayPalette());

        /**
         * Dp, Sp
         */
        bpmnPaletteWindow.addPalette(new DataProcessingPalette());
        bpmnPaletteWindow.addPalette(new StatisticProcessingPalette());

        bpmnPaletteWindow.setName("Analysis");

        /**
         * processVariablePalette
         */
        this.processVariablePalette = new ProcessVariablePalette();

        List<Palette> palettes = new ArrayList<>();
        palettes.add(bpmnPaletteWindow);
        palettes.add(this.processVariablePalette);
        setChildPalettes(palettes);
    }

    ProcessVariablePalette processVariablePalette;
        public ProcessVariablePalette getProcessVariablePalette() {
            return this.processVariablePalette;
        }
        public void setProcessVariablePalette(ProcessVariablePalette processVariablePalette) { this.processVariablePalette = processVariablePalette; }
}
