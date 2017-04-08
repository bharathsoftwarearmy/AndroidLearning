package com.bijesh.exchange.myapplication.models.parsers;

/**
 * Created by Bijesh on 4/8/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareSymbol {

    @SerializedName("SYMB_COMPANY_NAME")
    @Expose
    private String sYMBCOMPANYNAME;
    @SerializedName(" SM_KEY_SYMBOL")
    @Expose
    private String sMKEYSYMBOL;
    @SerializedName(" SM_NEW_SYMBOL")
    @Expose
    private String sMNEWSYMBOL;
    @SerializedName(" SM_APPLICABLE_FROM")
    @Expose
    private String sMAPPLICABLEFROM;

    public String getSYMBCOMPANYNAME() {
        return sYMBCOMPANYNAME;
    }

    public void setSYMBCOMPANYNAME(String sYMBCOMPANYNAME) {
        this.sYMBCOMPANYNAME = sYMBCOMPANYNAME;
    }

    public String getSMKEYSYMBOL() {
        return sMKEYSYMBOL;
    }

    public void setSMKEYSYMBOL(String sMKEYSYMBOL) {
        this.sMKEYSYMBOL = sMKEYSYMBOL;
    }

    public String getSMNEWSYMBOL() {
        return sMNEWSYMBOL;
    }

    public void setSMNEWSYMBOL(String sMNEWSYMBOL) {
        this.sMNEWSYMBOL = sMNEWSYMBOL;
    }

    public String getSMAPPLICABLEFROM() {
        return sMAPPLICABLEFROM;
    }

    public void setSMAPPLICABLEFROM(String sMAPPLICABLEFROM) {
        this.sMAPPLICABLEFROM = sMAPPLICABLEFROM;
    }

}
