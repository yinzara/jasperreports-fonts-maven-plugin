/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yinzara.jasperreports.fonts.maven.plugin;

/**
 *
 * @author Matthew
 */
public class FamilyRename {

    /**
     * The font family to rename from
     */
    private String fromFamily;

    /**
     * The font family to rename to
     */
    private String toFamily;

    public String getFromFamily() {
        return fromFamily;
    }

    public void setFromFamily(String fromFamily) {
        this.fromFamily = fromFamily;
    }

    public String getToFamily() {
        return toFamily;
    }

    public void setToFamily(String toFamily) {
        this.toFamily = toFamily;
    }

}
