package org.exerciseEditor.model;

import org.exerciseEditor.controller.MachineController;
import org.exerciseEditor.enums.Determinism;
import org.exerciseEditor.enums.MachineType;
import org.jetbrains.annotations.NotNull;

public class MachineModel extends BaseModel {
    private final String input;
    private final String output;
    private final MachineType type;
    private final Determinism determinism;
    private final String jffPathName;

    public void setMachineViewController(MachineController machineViewController) {
    }

    public static class Builder {
        private String title = "";
        private String description = "";
        private String input = "";
        private String output = "";
        private MachineType type;
        private Determinism determinism = Determinism.NOT_SPECIFIED;
        private String jffPathName = "";

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder input(String input) {
            this.input = input;
            return this;
        }

        public Builder output(String output) {
            this.output = output;
            return this;
        }

        public Builder type(MachineType type) {
            this.type = type;
            return this;
        }

        public Builder determinism(Determinism determinism) {
            this.determinism = determinism;
            return this;
        }

        public Builder jffPathName(String jffPathName) {
            this.jffPathName = jffPathName;
            return this;
        }

        public MachineModel build() {
            return new MachineModel(this);
        }
    }

    private MachineModel(@NotNull Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.input = builder.input;
        this.output = builder.output;
        this.type = builder.type;
        this.determinism = builder.determinism;
        this.jffPathName = builder.jffPathName;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public MachineType getType() {
        return type;
    }

    public Determinism getDeterminism() {
        return determinism;
    }

    public String getJffPathName() {
        return jffPathName;
    }
}
