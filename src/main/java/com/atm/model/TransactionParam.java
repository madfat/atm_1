package com.atm.model;

public class TransactionParam {
    private String type;
    private String srcAccount;
    private String dstAccount;
    private Double trxAmount;
    private String refNo;

    public TransactionParam() {
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(String srcAccount) {
        this.srcAccount = srcAccount;
    }

    public String getDstAccount() {
        return dstAccount;
    }

    public void setDstAccount(String dstAccount) {
        this.dstAccount = dstAccount;
    }

    public Double getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(Double trxAmount) {
        this.trxAmount = trxAmount;
    }
}
