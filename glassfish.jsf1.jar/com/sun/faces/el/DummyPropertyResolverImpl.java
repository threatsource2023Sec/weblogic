package com.sun.faces.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

public class DummyPropertyResolverImpl extends PropertyResolver {
   public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
      return null;
   }

   public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
      return null;
   }

   public void setValue(Object base, Object property, Object value) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
   }

   public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
   }

   public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
      return false;
   }

   public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
      return false;
   }

   public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
      return null;
   }

   public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
      FacesContext context = FacesContext.getCurrentInstance();
      context.getELContext().setPropertyResolved(false);
      return null;
   }
}
