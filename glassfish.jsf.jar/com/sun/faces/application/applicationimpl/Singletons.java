package com.sun.faces.application.applicationimpl;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ResourceHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.faces.flow.FlowHandler;

public class Singletons {
   private static final Logger LOGGER;
   private static final String CONTEXT = "context";
   private final ApplicationAssociate associate;
   private volatile ActionListener actionListener;
   private volatile NavigationHandler navigationHandler;
   private volatile ViewHandler viewHandler;
   private volatile ResourceHandler resourceHandler;
   private volatile StateManager stateManager;
   private volatile ArrayList supportedLocales;
   private volatile Locale defaultLocale;
   private volatile String messageBundle;
   private String defaultRenderKitId;

   public Singletons(ApplicationAssociate applicationAssociate) {
      this.associate = applicationAssociate;
   }

   public ViewHandler getViewHandler() {
      return this.viewHandler;
   }

   public synchronized void setViewHandler(ViewHandler viewHandler) {
      Util.notNull("viewHandler", viewHandler);
      this.notRequestServiced("ViewHandler");
      this.viewHandler = viewHandler;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("set ViewHandler Instance to ''{0}''", viewHandler.getClass().getName()));
      }

   }

   public ResourceHandler getResourceHandler() {
      return this.resourceHandler;
   }

   public synchronized void setResourceHandler(ResourceHandler resourceHandler) {
      Util.notNull("resourceHandler", resourceHandler);
      this.notRequestServiced("ResourceHandler");
      this.resourceHandler = resourceHandler;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "set ResourceHandler Instance to ''{0}''", resourceHandler.getClass().getName());
      }

   }

   public StateManager getStateManager() {
      return this.stateManager;
   }

   public synchronized void setStateManager(StateManager stateManager) {
      Util.notNull("stateManager", stateManager);
      this.notRequestServiced("StateManager");
      this.stateManager = stateManager;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("set StateManager Instance to ''{0}''", stateManager.getClass().getName()));
      }

   }

   public ActionListener getActionListener() {
      return this.actionListener;
   }

   public synchronized void setActionListener(ActionListener actionListener) {
      Util.notNull("actionListener", actionListener);
      this.actionListener = actionListener;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("set ActionListener Instance to ''{0}''", actionListener.getClass().getName()));
      }

   }

   public NavigationHandler getNavigationHandler() {
      return this.navigationHandler;
   }

   public synchronized void setNavigationHandler(NavigationHandler navigationHandler) {
      Util.notNull("navigationHandler", navigationHandler);
      this.navigationHandler = navigationHandler;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("set NavigationHandler Instance to ''{0}''", navigationHandler.getClass().getName()));
      }

   }

   public FlowHandler getFlowHandler() {
      return this.associate.getFlowHandler();
   }

   public synchronized void setFlowHandler(FlowHandler flowHandler) {
      Util.notNull("flowHandler", flowHandler);
      this.associate.setFlowHandler(flowHandler);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("set FlowHandler Instance to ''{0}''", flowHandler.getClass().getName()));
      }

   }

   public Iterator getSupportedLocales() {
      return ((List)Util.coalesce(this.supportedLocales, Collections.emptyList())).iterator();
   }

   public synchronized void setSupportedLocales(Collection newLocales) {
      Util.notNull("newLocales", newLocales);
      this.supportedLocales = new ArrayList(newLocales);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("set Supported Locales ''{0}''", this.supportedLocales.toString()));
      }

   }

   public Locale getDefaultLocale() {
      return this.defaultLocale;
   }

   public synchronized void setDefaultLocale(Locale locale) {
      Util.notNull("locale", locale);
      this.defaultLocale = locale;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("set defaultLocale ''{0}''", this.defaultLocale.getClass().getName()));
      }

   }

   public synchronized void setMessageBundle(String messageBundle) {
      Util.notNull("messageBundle", messageBundle);
      this.messageBundle = messageBundle;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("set messageBundle ''{0}''", messageBundle));
      }

   }

   public String getMessageBundle() {
      return this.messageBundle;
   }

   public String getDefaultRenderKitId() {
      return this.defaultRenderKitId;
   }

   public void setDefaultRenderKitId(String renderKitId) {
      this.defaultRenderKitId = renderKitId;
   }

   public ResourceBundle getResourceBundle(FacesContext context, String var) {
      Util.notNull("context", context);
      Util.notNull("var", var);
      return this.associate.getResourceBundle(context, var);
   }

   private void notRequestServiced(String artifactId) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_APPLICATION_ARTIFACT", artifactId));
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
