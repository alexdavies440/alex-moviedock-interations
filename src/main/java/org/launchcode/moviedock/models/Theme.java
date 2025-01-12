package org.launchcode.moviedock.models;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

//@ControllerAdvice
public class Theme extends AbstractEntity {


    private String mode;
    private String cssPath;
    private String navMode;

    public Theme(String aMode) {
        this.mode = aMode;

        if (aMode.equals("dark")) {
            this.cssPath = "/css/styles.css";
            this.navMode = "navbar navbar-inverse navbar-fixed-top bar";
        }
        if (aMode.equals("light")) {
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
