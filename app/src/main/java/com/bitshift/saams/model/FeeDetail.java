package com.bitshift.saams.model;

import java.io.Serializable;
import java.util.List;

public class FeeDetail implements Serializable {

    String receiptNo;
    String dateTime;
    String totalOfReceipt;
    List<FeeReceiptComponent> items;

    public FeeDetail(String receiptNo, String dateTime, String totalOfReceipt, List<FeeReceiptComponent> items) {
        this.receiptNo = receiptNo;
        this.dateTime = dateTime;
        this.totalOfReceipt = totalOfReceipt;
        this.items = items;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTotalOfReceipt() {
        return totalOfReceipt;
    }

    public void setTotalOfReceipt(String totalOfReceipt) {
        this.totalOfReceipt = totalOfReceipt;
    }

    public List<FeeReceiptComponent> getItems() {
        return items;
    }

    public void setItems(List<FeeReceiptComponent> items) {
        this.items = items;
    }
}
