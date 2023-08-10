package com.redapps.phonepolice.models;

import java.io.File;
import java.io.Serializable;


public class IntruderModels implements Serializable {
    File file;
    boolean isSelected;

    public IntruderModels(File file, boolean z) {
        this.file = file;
        this.isSelected = z;
    }

    public boolean isIsSelected() {
        return this.isSelected;
    }

    public File getFile() {
        return this.file;
    }
}
