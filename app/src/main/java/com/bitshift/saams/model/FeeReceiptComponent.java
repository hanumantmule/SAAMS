package com.bitshift.saams.model;

import java.io.Serializable;

public class FeeReceiptComponent implements Serializable {
    String feeName;
    String feeAmount;

    public FeeReceiptComponent(String feeName, String feeAmount) {
        this.feeName = feeName;
        this.feeAmount = feeAmount;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }
}
