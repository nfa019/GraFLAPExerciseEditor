package org.exerciseEditor;

import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.model.GrammarModel;
import org.exerciseEditor.model.MachineModel;

public class CreateModels {
    public AutomatonModel automatonModel;
    private GrammarModel grammarModel;
    private MachineModel machineModel;

    public void createModels() {
        automatonModel = new AutomatonModel.Builder().build();
        grammarModel = new GrammarModel.Builder().build();
        machineModel = new MachineModel.Builder().build();
    }

    public AutomatonModel getAutomatonModel() {
        return automatonModel;
    }

    public GrammarModel getGrammarModel() {
        return grammarModel;
    }

    public MachineModel getMachineModel() {
        return machineModel;
    }
}
