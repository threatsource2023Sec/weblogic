package com.sun.faces.cdi;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CdiConverter implements Converter, StateHolder {
   private String converterId;
   private transient Converter delegate;
   private Class forClass;

   public CdiConverter() {
   }

   public CdiConverter(String converterId, Class forClass, Converter delegate) {
      this.converterId = converterId;
      this.forClass = forClass;
      this.delegate = delegate;
   }

   public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      return this.getDelegate(facesContext).getAsObject(facesContext, component, value);
   }

   public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
      return this.getDelegate(facesContext).getAsString(facesContext, component, value);
   }

   public Object saveState(FacesContext facesContext) {
      return new Object[]{this.converterId, this.forClass};
   }

   public void restoreState(FacesContext facesContext, Object state) {
      Object[] stateArray = (Object[])((Object[])state);
      this.converterId = (String)stateArray[0];
      this.forClass = (Class)stateArray[1];
   }

   public boolean isTransient() {
      return false;
   }

   public void setTransient(boolean transientValue) {
   }

   private Converter getDelegate(FacesContext facesContext) {
      if (this.delegate == null) {
         if (!this.converterId.equals("")) {
            this.delegate = facesContext.getApplication().createConverter(this.converterId);
         } else {
            this.delegate = facesContext.getApplication().createConverter(this.forClass);
         }
      }

      return this.delegate;
   }
}
