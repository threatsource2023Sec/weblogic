package com.sun.faces.el;

import com.sun.faces.component.CompositeComponentStackManager;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowHandler;

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
                  CompositeComponentStackManager manager = CompositeComponentStackManager.getManager(facesContext);
                  Object o = manager.peek();
                  if (o == null) {
                     o = UIComponent.getCurrentCompositeComponent(facesContext);
                  }

                  return o;
               case 3:
                  UIComponent c = UIComponent.getCurrentComponent(facesContext);
                  context.setPropertyResolved(c != null);
                  return c;
               case 4:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestCookieMap();
               case 5:
                  context.setPropertyResolved(true);
                  return facesContext;
               case 6:
                  context.setPropertyResolved(true);
                  return facesContext.getExternalContext().getFlash();
               case 7:
                  context.setPropertyResolved(true);
                  FlowHandler flowHandler = facesContext.getApplication().getFlowHandler();
                  Map flowScope = null;
                  if (null != flowHandler) {
                     flowScope = flowHandler.getCurrentFlowScope();
                  }

                  return flowScope;
               case 8:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestHeaderMap();
               case 9:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestHeaderValuesMap();
               case 10:
                  context.setPropertyResolved(true);
                  return extCtx.getInitParameterMap();
               case 11:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestParameterMap();
               case 12:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestParameterValuesMap();
               case 13:
                  context.setPropertyResolved(true);
                  return extCtx.getRequest();
               case 14:
                  context.setPropertyResolved(true);
                  return extCtx.getRequestMap();
               case 15:
                  context.setPropertyResolved(true);
                  return facesContext.getApplication().getResourceHandler();
               case 16:
                  context.setPropertyResolved(true);
                  return extCtx.getSession(true);
               case 17:
                  context.setPropertyResolved(true);
                  return extCtx.getSessionMap();
               case 18:
                  context.setPropertyResolved(true);
                  return facesContext.getViewRoot();
               case 19:
                  UIViewRoot root = facesContext.getViewRoot();
                  if (root != null) {
                     context.setPropertyResolved(true);
                     return facesContext.getViewRoot().getViewMap();
                  }
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
         list.add(Util.getFeatureDescriptor("cc", "cc", "cc", false, false, true, UIComponent.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("component", "component", "component", false, false, true, UIComponent.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("cookie", "cookie", "cookie", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("facesContext", "facesContext", "facesContext", false, false, true, FacesContext.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("flash", "flash", "flash", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("flowScope", "flowScope", "flowScope", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("view", "view", "root", false, false, true, UIViewRoot.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("header", "header", "header", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("headerValues", "headerValues", "headerValues", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("initParam", "initParam", "initParam", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("param", "param", "param", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("paramValues", "paramValues", "paramValues", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("request", "request", "request", false, false, true, Object.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("requestScope", "requestScope", "requestScope", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("resource", "resource", "resource", false, false, true, Object.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("session", "session", "session", false, false, true, Object.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("sessionScope", "sessionScope", "sessionScope", false, false, true, Map.class, Boolean.TRUE));
         list.add(Util.getFeatureDescriptor("viewScope", "viewScope", "viewScope", false, false, true, Map.class, Boolean.TRUE));
         return list.iterator();
      }
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base != null ? null : String.class;
   }

   static {
      String[] implictNames = new String[]{"application", "applicationScope", "cc", "component", "cookie", "facesContext", "flash", "flowScope", "header", "headerValues", "initParam", "param", "paramValues", "request", "requestScope", "resource", "session", "sessionScope", "view", "viewScope"};
      int nameCount = implictNames.length;
      Map implicitObjects = new HashMap((int)((float)nameCount * 1.5F));

      for(int nameIndex = 0; nameIndex < nameCount; ++nameIndex) {
         implicitObjects.put(implictNames[nameIndex], nameIndex);
      }

      IMPLICIT_OBJECTS = implicitObjects;
   }
}
