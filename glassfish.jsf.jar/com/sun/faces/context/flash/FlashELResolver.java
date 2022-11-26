package com.sun.faces.context.flash;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.context.FlashFactory;

public class FlashELResolver extends ELResolver {
   private static final String FLASH_VARIABLE_NAME = "flash";
   private static final String FLASH_NOW_VARIABLE_NAME = "now";
   private static final String FLASH_KEEP_VARIABLE_NAME = "keep";

   public Object getValue(ELContext elContext, Object base, Object property) {
      if (null == property) {
         return null;
      } else {
         Object result = null;
         if (null == base) {
            return null;
         } else {
            if (base instanceof Flash) {
               FacesContext facesContext = (FacesContext)elContext.getContext(FacesContext.class);
               ExternalContext extCtx = facesContext.getExternalContext();
               switch (property.toString()) {
                  case "keep":
                     elContext.setPropertyResolved(true);
                     result = base;
                     FlashFactory ff = (FlashFactory)FactoryFinder.getFactory("javax.faces.context.FlashFactory");
                     ff.getFlash(true);
                     ELFlash.setKeepFlag(facesContext);
                     break;
                  case "now":
                     Map requestMap = extCtx.getRequestMap();
                     requestMap.put("csfcffn", property);
                     elContext.setPropertyResolved(true);
                     result = requestMap;
                     break;
                  default:
                     result = null;
               }
            }

            return result;
         }
      }
   }

   public Class getType(ELContext elContext, Object base, Object property) {
      if (null != base) {
         return null;
      } else if (null == property) {
         String message = " base " + base + " property " + property;
         throw new PropertyNotFoundException(message);
      } else {
         if (property.toString().equals("flash")) {
            elContext.setPropertyResolved(true);
         }

         return null;
      }
   }

   public void setValue(ELContext elContext, Object base, Object property, Object value) {
      if (null == base) {
         if (null == property) {
            String message = " base " + base + " property " + property;
            throw new PropertyNotFoundException(message);
         } else if (property.toString().equals("flash")) {
            elContext.setPropertyResolved(true);
            throw new PropertyNotWritableException(property.toString());
         }
      }
   }

   public boolean isReadOnly(ELContext elContext, Object base, Object property) {
      if (base != null) {
         return false;
      } else if (property == null) {
         String message = " base " + base + " property " + property;
         throw new PropertyNotFoundException(message);
      } else if (property.toString().equals("flash")) {
         elContext.setPropertyResolved(true);
         return true;
      } else {
         return false;
      }
   }

   public Iterator getFeatureDescriptors(ELContext elContext, Object base) {
      if (null != base) {
         return null;
      } else {
         Iterator result = null;
         FacesContext facesContext = (FacesContext)elContext.getContext(FacesContext.class);
         ExternalContext extCtx = facesContext.getExternalContext();
         Flash flash;
         if (null != (flash = extCtx.getFlash())) {
            Iterator iter = flash.entrySet().iterator();
            if (iter.hasNext()) {
               ArrayList fds = new ArrayList(flash.size());

               while(iter.hasNext()) {
                  Map.Entry cur = (Map.Entry)iter.next();
                  FeatureDescriptor fd = new FeatureDescriptor();
                  fd.setName((String)cur.getKey());
                  fds.add(fd);
               }

               result = fds.iterator();
            }
         }

         return result;
      }
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      Class result = null;
      if (null != base && "flash".equals(base.toString())) {
         result = Object.class;
      }

      return result;
   }
}
