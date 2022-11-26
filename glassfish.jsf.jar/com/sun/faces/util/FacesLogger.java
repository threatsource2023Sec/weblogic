package com.sun.faces.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public enum FacesLogger {
   APPLICATION("application"),
   APPLICATION_VIEW("application.view"),
   RESOURCE("resource"),
   CONFIG("config"),
   CONTEXT("context"),
   FACELETS_COMPILER("facelets.compiler"),
   FACELETS_COMPONENT("facelets.tag.component"),
   FACELETS_EL("facelets.el"),
   FACELETS_META("facelets.tag.meta"),
   FACELETS_COMPOSITION("facelets.tag.ui.composition"),
   FACELETS_DECORATE("facelets.tag.ui.decorate"),
   FACELETS_INCLUDE("facelets.tag.ui.include"),
   FACELETS_FACELET("faclets.facelet"),
   FACELETS_FACTORY("facelets.factory"),
   FLOW("flow"),
   LIFECYCLE("lifecycle"),
   MANAGEDBEAN("managedbean"),
   RENDERKIT("renderkit"),
   TAGLIB("taglib"),
   TIMING("timing"),
   UTIL("util"),
   FLASH("flash");

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

   public String interpolateMessage(FacesContext context, String messageId, Object[] params) {
      String result = null;
      ResourceBundle rb = null;
      UIViewRoot root = context.getViewRoot();
      ClassLoader loader = Util.getCurrentLoader(this);
      Locale curLocale;
      if (null == root) {
         curLocale = Locale.getDefault();
      } else {
         curLocale = root.getLocale();
      }

      try {
         rb = ResourceBundle.getBundle(this.getResourcesName(), curLocale, loader);
         String message = rb.getString(messageId);
         if (params != null) {
            result = MessageFormat.format(message, params);
         } else {
            result = message;
         }
      } catch (MissingResourceException var10) {
         result = messageId;
      }

      return result;
   }
}
