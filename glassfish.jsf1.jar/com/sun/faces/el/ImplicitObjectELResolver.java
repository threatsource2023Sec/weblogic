package com.sun.faces.el;

import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ImplicitObjectELResolver extends ELResolver implements ELConstants {
   protected static final Map IMPLICIT_OBJECTS;

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
            ExternalContext extCtx = facesContext.getExternalContext();
            switch (index) {
               case 0:
                  context.setPropertyResolved(true);
                  return extCtx.getContext();
               case 1:
                  context.setPropertyResolved(true);
                  return extCtx.getApplicationMap();
               case 2:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestCookieMap();
               case 3:
                  context.setPropertyResolved(true);
                  return facesContext;
               case 4:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestHeaderMap();
               case 5:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestHeaderValuesMap();
               case 6:
                  context.setPropertyResolved(true);
                  return extCtx.getInitParameterMap();
               case 7:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestParameterMap();
               case 8:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestParameterValuesMap();
               case 9:
                  context.setPropertyResolved(true);
                  return extCtx.getRequest();
               case 10:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestMap();
               case 11:
                  context.setPropertyResolved(true);
                  return extCtx.getSession(true);
               case 12:
                  context.setPropertyResolved(true);
                  return extCtx.getSessionMap();
               case 13:
                  context.setPropertyResolved(true);
                  return facesContext.getViewRoot();
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
               throw new PropertyNotWritableException((String)property);
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
         if (index != null) {
            context.setPropertyResolved(true);
            return true;
         } else {
            return false;
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
         if (index != null) {
            context.setPropertyResolved(true);
         }

         return null;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      if (base != null) {
         return null;
      } else {
         ArrayList list = new ArrayList(14);
         list.add(Util.getFeatureDescriptor("application", "application", "application", false, false, true, Object.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("applicationScope", "applicationScope", "applicationScope", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("cookie", "cookie", "cookie", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("facesContext", "facesContext", "facesContext", false, false, true, FacesContext.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("view", "view", "root", false, false, true, UIViewRoot.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("header", "header", "header", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("headerValues", "headerValues", "headerValues", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("initParam", "initParam", "initParam", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("param", "param", "param", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("paramValues", "paramValues", "paramValues", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("request", "request", "request", false, false, true, Object.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("requestScope", "requestScope", "requestScope", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("session", "session", "session", false, false, true, Object.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("sessionScope", "sessionScope", "sessionScope", false, false, true, Map.class, Boolean.TRUE));
         return list.iterator();
      }
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base != null ? null : String.class;
   }

   static {
      String[] implictNames = new String[]{"application", "applicationScope", "cookie", "facesContext", "header", "headerValues", "initParam", "param", "paramValues", "request", "requestScope", "session", "sessionScope", "view"};
      int nameCount = implictNames.length;
      Map implicitObjects = new HashMap((int)((float)nameCount * 1.5F));

      for(int nameIndex = 0; nameIndex < nameCount; ++nameIndex) {
         implicitObjects.put(implictNames[nameIndex], nameIndex);
      }

      IMPLICIT_OBJECTS = implicitObjects;
   }
}
