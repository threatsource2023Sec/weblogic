package com.sun.faces.el;

import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ImplicitObjectELResolverForJsp extends ImplicitObjectELResolver {
   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return null;
      } else if (property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
         throw new PropertyNotFoundException(message);
      } else {
         Integer index = (Integer)IMPLICIT_OBJECTS.get(property.toString());
         if (index == null) {
            return null;
         } else {
            FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
            switch (index) {
               case 5:
                  context.setPropertyResolved(true);
                  return facesContext;
               case 6:
                  context.setPropertyResolved(true);
                  return facesContext.getExternalContext().getFlash();
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 16:
               case 17:
               default:
                  return null;
               case 15:
                  context.setPropertyResolved(true);
                  return facesContext.getApplication().getResourceHandler();
               case 18:
                  context.setPropertyResolved(true);
                  return facesContext.getViewRoot();
               case 19:
                  context.setPropertyResolved(true);
                  return facesContext.getViewRoot().getViewMap();
            }
         }
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return null;
      } else if (property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
         throw new PropertyNotFoundException(message);
      } else {
         Integer index = (Integer)IMPLICIT_OBJECTS.get(property.toString());
         if (index == null) {
            return null;
         } else {
            switch (index) {
               case 5:
               case 18:
                  context.setPropertyResolved(true);
                  return null;
               default:
                  return null;
            }
         }
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      if (base == null) {
         if (property == null) {
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
            throw new PropertyNotFoundException(message);
         } else {
            Integer index = (Integer)IMPLICIT_OBJECTS.get(property.toString());
            if (index != null) {
               switch (index) {
                  case 5:
                     throw new PropertyNotWritableException(MessageUtils.getExceptionMessageString("com.sun.faces.OBJECT_IS_READONLY", "facesContext"));
                  case 18:
                     throw new PropertyNotWritableException(MessageUtils.getExceptionMessageString("com.sun.faces.OBJECT_IS_READONLY", "view"));
                  default:
               }
            }
         }
      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return false;
      } else if (property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
         throw new PropertyNotFoundException(message);
      } else {
         Integer index = (Integer)IMPLICIT_OBJECTS.get(property.toString());
         if (index == null) {
            return false;
         } else {
            switch (index) {
               case 5:
                  context.setPropertyResolved(true);
                  return true;
               case 18:
                  context.setPropertyResolved(true);
                  return true;
               default:
                  return false;
            }
         }
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      if (base != null) {
         return null;
      } else {
         ArrayList list = new ArrayList(2);
         list.add(Util.getFeatureDescriptor("facesContext", "facesContext", "facesContext", false, false, true, FacesContext.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("view", "view", "root", false, false, true, UIViewRoot.class, Boolean.TRUE));
         return list.iterator();
      }
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base != null ? null : String.class;
   }
}
