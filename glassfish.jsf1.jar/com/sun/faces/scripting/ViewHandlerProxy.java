package com.sun.faces.scripting;

import java.io.IOException;
import java.util.Locale;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ViewHandlerProxy extends ViewHandler {
   private String scriptName;
   private ViewHandler vhDelegate;

   public ViewHandlerProxy(String scriptName, ViewHandler vhDelegate) {
      this.scriptName = scriptName;
      this.vhDelegate = vhDelegate;
   }

   public String calculateCharacterEncoding(FacesContext context) {
      return this.getGroovyDelegate().calculateCharacterEncoding(context);
   }

   public void initView(FacesContext context) throws FacesException {
      this.getGroovyDelegate().initView(context);
   }

   public Locale calculateLocale(FacesContext context) {
      return this.getGroovyDelegate().calculateLocale(context);
   }

   public String calculateRenderKitId(FacesContext context) {
      return this.getGroovyDelegate().calculateRenderKitId(context);
   }

   public UIViewRoot createView(FacesContext context, String viewId) {
      return this.getGroovyDelegate().createView(context, viewId);
   }

   public String getActionURL(FacesContext context, String viewId) {
      return this.getGroovyDelegate().getActionURL(context, viewId);
   }

   public String getResourceURL(FacesContext context, String path) {
      return this.getGroovyDelegate().getResourceURL(context, path);
   }

   public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
      this.getGroovyDelegate().renderView(context, viewToRender);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      return this.getGroovyDelegate().restoreView(context, viewId);
   }

   public void writeState(FacesContext context) throws IOException {
      this.getGroovyDelegate().writeState(context);
   }

   private ViewHandler getGroovyDelegate() {
      try {
         return (ViewHandler)GroovyHelper.newInstance(this.scriptName, ViewHandler.class, this.vhDelegate);
      } catch (Exception var2) {
         throw new FacesException(var2);
      }
   }
}
