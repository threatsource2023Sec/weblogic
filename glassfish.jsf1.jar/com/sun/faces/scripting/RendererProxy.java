package com.sun.faces.scripting;

import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

public class RendererProxy extends Renderer {
   private String scriptName;

   public RendererProxy(String scriptName) {
      this.scriptName = scriptName;
   }

   public void decode(FacesContext context, UIComponent component) {
      this.getGroovyDelegate().decode(context, component);
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.getGroovyDelegate().encodeBegin(context, component);
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.getGroovyDelegate().encodeChildren(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.getGroovyDelegate().encodeEnd(context, component);
   }

   public String convertClientId(FacesContext context, String clientId) {
      return this.getGroovyDelegate().convertClientId(context, clientId);
   }

   public boolean getRendersChildren() {
      return this.getGroovyDelegate().getRendersChildren();
   }

   public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
      return this.getGroovyDelegate().getConvertedValue(context, component, submittedValue);
   }

   private Renderer getGroovyDelegate() {
      try {
         return (Renderer)GroovyHelper.newInstance(this.scriptName);
      } catch (Exception var2) {
         throw new FacesException(var2);
      }
   }
}
