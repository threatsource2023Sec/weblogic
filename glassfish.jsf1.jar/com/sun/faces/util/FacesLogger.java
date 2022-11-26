package com.sun.faces.util;

import java.util.logging.Logger;

public enum FacesLogger {
   APPLICATION("application"),
   CONFIG("config"),
   CONTEXT("context"),
   LIFECYCLE("lifecycle"),
   MANAGEDBEAN("managedbean"),
   RENDERKIT("renderkit"),
   TAGLIB("taglib"),
   TIMING("timing");

   private static final String LOGGER_RESOURCES = "com.sun.faces.LogStrings";
   public static final String FACES_LOGGER_NAME_PREFIX = "javax.enterprise.resource.webcontainer.jsf.";
   private String loggerName;

   private FacesLogger(String loggerName) {
      this.loggerName = "javax.enterprise.resource.webcontainer.jsf." + loggerName;
   }

   public String getLoggerName() {
      return this.loggerName;
   }

   public String getResourcesName() {
      return "com.sun.faces.LogStrings";
   }

   public Logger getLogger() {
      return Logger.getLogger(this.loggerName, "com.sun.faces.LogStrings");
   }
}
