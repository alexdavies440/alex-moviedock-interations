package org.launchcode.moviedock.models.themes;

public enum Theme {
    DARK("/css/styles.css", "navbar navbar-inverse navbar-fixed-top bar"),
    LIGHT("/css/styles-light.css", "navbar navbar-default navbar-fixed-top bar"),
    ICE("/css/styles-ice.css", "navbar navbar-fixed-top bar"),
    SLATE("/css/styles-slate.css", "navbar navbar-fixed-top bar");

    private final String cssPath;
    private final String navMode;

    Theme(String cssPath, String navMode) {
        this.cssPath = cssPath;
        this.navMode = navMode;
    }

    public String getCssPath() {
        return cssPath;
    }

    public String getNavMode() {
        return navMode;
    }
}
