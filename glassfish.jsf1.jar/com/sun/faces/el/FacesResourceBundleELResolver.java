package com.sun.faces.el;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationResourceBundle;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class FacesResourceBundleELResolver extends ELResolver {
   public Object getValue(ELContext context, Object base, Object property) {
      if (null != base) {
         return null;
      } else if (null == property) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         Application app = facesContext.getApplication();
         ResourceBundle result = app.getResourceBundle(facesContext, property.toString());
         if (null != result) {
            context.setPropertyResolved(true);
         }

         return result;
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      if (null != base) {
         return null;
      } else if (null == property) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         Application app = facesContext.getApplication();
         ResourceBundle result = app.getResourceBundle(facesContext, property.toString());
         if (null != result) {
            context.setPropertyResolved(true);
            return ResourceBundle.class;
         } else {
            return null;
         }
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      String message;
      if (base == null && property == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         Application app = facesContext.getApplication();
         ResourceBundle result = app.getResourceBundle(facesContext, property.toString());
         if (null != result) {
            context.setPropertyResolved(true);
            message = MessageUtils.getExceptionMessageString("com.sun.faces.OBJECT_IS_READONLY");
            message = message + " base " + base + " property " + property;
            throw new PropertyNotWritableException(message);
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
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         Application app = facesContext.getApplication();
         ResourceBundle result = app.getResourceBundle(facesContext, property.toString());
         if (null != result) {
            context.setPropertyResolved(true);
            return true;
         } else {
            return false;
         }
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      if (base != null) {
         return null;
      } else {
         ArrayList list = new ArrayList();
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
         Map rbMap = associate.getResourceBundles();
         if (rbMap == null) {
            return list.iterator();
         } else {
            Iterator i = rbMap.entrySet().iterator();

            while(i.hasNext()) {
               Map.Entry entry = (Map.Entry)i.next();
               String var = (String)entry.getKey();
               ApplicationResourceBundle bundle = (ApplicationResourceBundle)entry.getValue();
               if (bundle != null) {
                  Locale curLocale = Util.getLocaleFromContextOrSystem(facesContext);
                  String description = bundle.getDescription(curLocale);
                  String displayName = bundle.getDisplayName(curLocale);
                  list.add(Util.getFeatureDescriptor(var, displayName, description, false, false, true, ResourceBundle.class, Boolean.TRUE));
               }
            }

            return list.iterator();
         }
      }
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base != null ? null : String.class;
   }
}
