package com.sun.faces.scripting;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

public class NavigationHandlerProxy extends NavigationHandler {
   private String scriptName;
   private NavigationHandler nvDelegate;

   public NavigationHandlerProxy(String scriptName, NavigationHandler nvDelegate) {
      this.scriptName = scriptName;
      this.nvDelegate = nvDelegate;
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome) {
      this.getGroovyDelegate().handleNavigation(context, fromAction, outcome);
   }

   private NavigationHandler getGroovyDelegate() {
      try {
         return (NavigationHandler)GroovyHelper.newInstance(this.scriptName, NavigationHandler.class, this.nvDelegate);
      } catch (Exception var2) {
         throw new FacesException(var2);
      }
   }
}
