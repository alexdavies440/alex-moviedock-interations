package org.launchcode.moviedock.models.themes;

import org.launchcode.moviedock.models.AbstractEntity;

public class Theme extends AbstractEntity {


    private Mode mode;
    private String cssPath;
    private String navMode;

    public Theme(Mode aMode) {
        this.mode = aMode;

        if (aMode.equals(Mode.DARK)) {
            this.cssPath = "/css/styles.css";
            this.navMode = "navbar navbar-inverse navbar-fixed-top bar";
        }
        if (aMode.equals(Mode.LIGHT)) {
            this.cssPath = "/css/styles-light.css";
            this.navMode = "navbar navbar-default navbar-fixed-top bar";
        }
        if (aMode.equals(Mode.GREEN)) {
            this.cssPath = "/css/styles-green.css";
            this.navMode = "navbar navbar-fixed-top bar";
        }
        if (aMode.equals(Mode.SLATE)) {
            this.cssPath = "/css/styles-slate.css";
            this.navMode = "navbar navbar-fixed-top bar";
        }
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getCssPath() {
        return cssPath;
    }

    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }

    public String getNavMode() {
        return navMode;
    }

    public void setNavMode(String navMode) {
        this.navMode = navMode;
    }

}
