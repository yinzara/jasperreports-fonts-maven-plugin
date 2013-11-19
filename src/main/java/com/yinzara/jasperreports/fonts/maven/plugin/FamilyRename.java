/*
 The MIT License (MIT)

 Copyright (c) 2013 yinzara

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.yinzara.jasperreports.fonts.maven.plugin;

/**
 *
 * @author Matthew
 */
public class FamilyRename {

    /**
     * The font family to rename from.
     */
    private String fromFamily;

    /**
     * The font family to rename to.
     */
    private String toFamily;

    /**
     * Gets the family to match font family name against.
     *
     * @return The font family name
     */
    public String getFromFamily() {
        return fromFamily;
    }

    /**
     * Sets the family to match font family name against.
     *
     * @param fromFamily Family to set
     */
    public void setFromFamily(final String fromFamily) {
        this.fromFamily = fromFamily;
    }

    /**
     * Gets the family name to set the font family to.
     *
     * @return The font family name
     */
    public String getToFamily() {
        return toFamily;
    }

    /**
     * Sets the family name to set the font family to.
     *
     * @param toFamily Family to set
     */
    public void setToFamily(final String toFamily) {
        this.toFamily = toFamily;
    }

}
