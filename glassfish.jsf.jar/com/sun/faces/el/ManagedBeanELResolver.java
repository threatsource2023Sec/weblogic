package com.sun.faces.el;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.mgbean.BeanBuilder;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ManagedBeanELResolver extends ELResolver {
   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return null;
      } else if (property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
         throw new PropertyNotFoundException(message);
      } else {
         return this.resolveBean(context, property, true);
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      if (base == null && property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
         throw new PropertyNotFoundException(message);
      } else {
         return null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      if (base == null && property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         if (base == null) {
            this.resolveBean(context, property, false);
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
         return false;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      if (base != null) {
         return null;
      } else {
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         BeanManager beanManager = getBeanManager();
         if (beanManager != null && !beanManager.getRegisteredBeans().isEmpty()) {
            Map beans = beanManager.getRegisteredBeans();
            List list = new ArrayList(beans.size());

            String beanName;
            BeanBuilder builder;
            String description;
            for(Iterator var7 = beans.entrySet().iterator(); var7.hasNext(); list.add(Util.getFeatureDescriptor(beanName, beanName, description == null ? "" : description, false, false, true, builder.getBeanClass(), Boolean.TRUE))) {
               Map.Entry bean = (Map.Entry)var7.next();
               beanName = (String)bean.getKey();
               builder = (BeanBuilder)bean.getValue();
               String loc = Util.getLocaleFromContextOrSystem(facesContext).toString();
               Map descriptions = builder.getDescriptions();
               description = null;
               if (descriptions != null) {
                  description = (String)descriptions.get(loc);
                  if (description == null) {
                     description = (String)descriptions.get("DEFAULT");
                  }
               }
            }

            return list.iterator();
         } else {
            List l = Collections.emptyList();
            return l.iterator();
         }
      }
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base != null ? null : Object.class;
   }

   private static BeanManager getBeanManager() {
      ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
      return associate != null ? associate.getBeanManager() : null;
   }

   private Object resolveBean(ELContext context, Object property, boolean markAsResolvedIfCreated) {
      Object result = null;
      BeanManager manager = getBeanManager();
      if (manager != null) {
         String beanName = property.toString();
         BeanBuilder builder = manager.getBuilder(beanName);
         if (builder != null) {
            FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
            ExternalContext extContext = facesContext.getExternalContext();
            if (extContext.getRequestMap().containsKey(beanName)) {
               return null;
            }

            if (null != extContext.getSession(false) && extContext.getSessionMap().containsKey(beanName)) {
               return null;
            }

            if (extContext.getApplicationMap().containsKey(beanName)) {
               return null;
            }

            result = manager.getBeanFromScope(beanName, builder, facesContext);
            if (result == null) {
               result = manager.create(beanName, builder, facesContext);
            }

            context.setPropertyResolved(markAsResolvedIfCreated && result != null);
         }
      }

      return result;
   }
}
