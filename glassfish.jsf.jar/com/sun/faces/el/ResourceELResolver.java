package com.sun.faces.el;

import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ResourceELResolver extends ELResolver {
   public Object getValue(ELContext context, Object base, Object property) {
      String ret;
      if (base == null && property == null) {
         ret = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(ret);
      } else {
         ret = null;
         if (base instanceof ResourceHandler) {
            ResourceHandler handler = (ResourceHandler)base;
            String prop = property.toString();
            Resource res;
            if (!prop.contains(":")) {
               res = handler.createResource(prop);
            } else {
               if (!this.isPropertyValid(prop)) {
                  throw new ELException("Invalid resource format.  Property " + prop + " contains more than one colon (:)");
               }

               Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
               String[] parts = Util.split(appMap, prop, ":");
               if (null != parts[0] && parts[0].equals("this")) {
                  FacesContext facesContext = FacesContext.getCurrentInstance();
                  UIComponent currentComponent = UIComponent.getCurrentCompositeComponent(facesContext);
                  Resource componentResource = (Resource)currentComponent.getAttributes().get("javax.faces.application.Resource.ComponentResource");
                  if (null != componentResource) {
                     String libName = null;
                     if (null != (libName = componentResource.getLibraryName())) {
                        parts[0] = libName;
                     }
                  }
               }

               res = handler.createResource(parts[1], parts[0]);
            }

            if (res != null) {
               FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
               ExternalContext extContext = facesContext.getExternalContext();
               ret = extContext.encodeResourceURL(res.getRequestPath());
            }

            context.setPropertyResolved(true);
         }

         return ret;
      }
   }

   public Class getType(ELContext context, Object base, Object property) {
      if (base == null && property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         return null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
      if (base == null && property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      if (base == null && property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         return false;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return String.class;
   }

   private boolean isPropertyValid(String property) {
      int idx = property.indexOf(58);
      return property.indexOf(58, idx + 1) == -1;
   }
}
