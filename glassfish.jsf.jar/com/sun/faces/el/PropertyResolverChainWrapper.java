package com.sun.faces.el;

import java.util.Iterator;
import java.util.List;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyResolver;

public class PropertyResolverChainWrapper extends ELResolver {
   private PropertyResolver legacyPR = null;

   public PropertyResolverChainWrapper(PropertyResolver propertyResolver) {
      this.legacyPR = propertyResolver;
   }

   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      if (base != null && property != null) {
         context.setPropertyResolved(true);
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         ELContext jsfEL = facesContext.getELContext();
         jsfEL.setPropertyResolved(true);
         Object result;
         if (!(base instanceof List) && !base.getClass().isArray()) {
            try {
               result = this.legacyPR.getValue(base, property);
            } catch (EvaluationException var10) {
               context.setPropertyResolved(false);
               throw new ELException(var10);
            }
         } else {
            Object indexObj = facesContext.getApplication().getExpressionFactory().coerceToType(property, Integer.class);
            int index = (Integer)indexObj;

            try {
               result = this.legacyPR.getValue(base, index);
            } catch (EvaluationException var11) {
               context.setPropertyResolved(false);
               throw new ELException(var11);
            }
         }

         context.setPropertyResolved(jsfEL.isPropertyResolved());
         return result;
      } else {
         return null;
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      if (base != null && property != null) {
         context.setPropertyResolved(true);
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         ELContext jsfEL = facesContext.getELContext();
         jsfEL.setPropertyResolved(true);
         Class result;
         if (!(base instanceof List) && !base.getClass().isArray()) {
            try {
               result = this.legacyPR.getType(base, property);
            } catch (EvaluationException var10) {
               context.setPropertyResolved(false);
               throw new ELException(var10);
            }
         } else {
            Object indexObj = facesContext.getApplication().getExpressionFactory().coerceToType(property, Integer.class);
            int index = (Integer)indexObj;

            try {
               result = this.legacyPR.getType(base, index);
            } catch (EvaluationException var11) {
               context.setPropertyResolved(false);
               throw new ELException(var11);
            }
         }

         context.setPropertyResolved(jsfEL.isPropertyResolved());
         return result;
      } else {
         return null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      if (base != null && property != null) {
         context.setPropertyResolved(true);
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         ELContext jsfEL = facesContext.getELContext();
         jsfEL.setPropertyResolved(true);
         if (!(base instanceof List) && !base.getClass().isArray()) {
            try {
               this.legacyPR.setValue(base, property, val);
            } catch (EvaluationException var10) {
               context.setPropertyResolved(false);
               throw new ELException(var10);
            }
         } else {
            Object indexObj = facesContext.getApplication().getExpressionFactory().coerceToType(property, Integer.class);
            int index = (Integer)indexObj;

            try {
               this.legacyPR.setValue(base, index, val);
            } catch (EvaluationException var11) {
               context.setPropertyResolved(false);
               throw new ELException(var11);
            }
         }

         context.setPropertyResolved(jsfEL.isPropertyResolved());
      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) throws ELException {
      if (base != null && property != null) {
         context.setPropertyResolved(true);
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         ELContext jsfEL = facesContext.getELContext();
         jsfEL.setPropertyResolved(true);
         boolean result;
         if (!(base instanceof List) && !base.getClass().isArray()) {
            try {
               result = this.legacyPR.isReadOnly(base, property);
            } catch (EvaluationException var10) {
               context.setPropertyResolved(false);
               throw new ELException(var10);
            }
         } else {
            Object indexObj = facesContext.getApplication().getExpressionFactory().coerceToType(property, Integer.class);
            int index = (Integer)indexObj;

            try {
               result = this.legacyPR.isReadOnly(base, index);
            } catch (EvaluationException var11) {
               context.setPropertyResolved(false);
               throw new ELException(var11);
            }
         }

         context.setPropertyResolved(jsfEL.isPropertyResolved());
         return result;
      } else {
         return false;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base == null ? Object.class : null;
   }
}
