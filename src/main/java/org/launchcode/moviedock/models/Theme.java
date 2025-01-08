package org.launchcode.moviedock.models;

public class Theme extends AbstractEntity {

    private String mode;
    private String cssPath;
    private String navMode;

    public Theme(String mode) {
        this.mode = mode;

        if (mode.equals("dark")) {
            this.cssPath = "/css/styles.css";
            this.navMode = "navbar navbar-inverse navbar-fixed-top bar";
        }
        if (mode.equals("light")) {
            this.cssPath = "/css/styles-light.css";
            this.navMode = "navbar navbar-default navbar-fixed-top bar";
        }
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
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
