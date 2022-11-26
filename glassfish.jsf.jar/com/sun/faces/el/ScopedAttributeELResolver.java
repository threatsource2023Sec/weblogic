package com.sun.faces.el;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ScopedAttributeELResolver extends ELResolver {
   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return null;
      } else {
         String attribute;
         if (property == null) {
            attribute = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
            throw new PropertyNotFoundException(attribute);
         } else {
            context.setPropertyResolved(true);
            attribute = property.toString();
            FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
            ExternalContext ec = facesContext.getExternalContext();
            Object result = ec.getRequestMap().get(attribute);
            if (result != null) {
               return result;
            } else {
               UIViewRoot root = facesContext.getViewRoot();
               if (root != null) {
                  Map viewMap = root.getViewMap(false);
                  if (viewMap != null) {
                     result = viewMap.get(attribute);
                  }
               }

               if (result != null) {
                  return result;
               } else {
                  result = ec.getSessionMap().get(attribute);
                  if (result != null) {
                     return result;
                  } else {
                     result = ec.getApplicationMap().get(attribute);
                     if (result != null) {
                        return result;
                     } else {
                        ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
                        if (associate != null) {
                           BeanManager manager = associate.getBeanManager();
                           if (manager != null && manager.isManaged(attribute)) {
                              return manager.getBeanFromScope(attribute, facesContext);
                           }
                        }

                        return null;
                     }
                  }
               }
            }
         }
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return null;
      } else if (property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         context.setPropertyResolved(true);
         return Object.class;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      if (base == null) {
         String attribute;
         if (property == null) {
            attribute = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
            throw new PropertyNotFoundException(attribute);
         } else {
            context.setPropertyResolved(true);
            attribute = (String)property;
            FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
            ExternalContext ec = facesContext.getExternalContext();
            if (ec.getRequestMap().get(attribute) != null) {
               ec.getRequestMap().put(attribute, val);
            } else if (facesContext.getViewRoot() != null && facesContext.getViewRoot().getViewMap().get(attribute) != null) {
               facesContext.getViewRoot().getViewMap().put(attribute, val);
            } else if (ec.getSessionMap().get(attribute) != null) {
               ec.getSessionMap().put(attribute, val);
            } else if (ec.getApplicationMap().get(attribute) != null) {
               ec.getApplicationMap().put(attribute, val);
            } else {
               ec.getRequestMap().put(attribute, val);
            }

         }
      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) throws ELException {
      if (base != null) {
         return false;
      } else if (property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         context.setPropertyResolved(true);
         return false;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      ArrayList list = new ArrayList();
      FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
      ExternalContext ec = facesContext.getExternalContext();
      Set attrs = ec.getRequestMap().entrySet();
      Iterator var7 = attrs.iterator();

      while(var7.hasNext()) {
         Map.Entry entry = (Map.Entry)var7.next();
         String attrName = (String)entry.getKey();
         Object attrValue = entry.getValue();
         list.add(Util.getFeatureDescriptor(attrName, attrName, "request scope attribute", false, false, true, attrValue.getClass(), Boolean.TRUE));
      }

      UIViewRoot root = facesContext.getViewRoot();
      if (root != null) {
         Map viewMap = root.getViewMap(false);
         if (viewMap != null && viewMap.size() != 0) {
            attrs = viewMap.entrySet();
            Iterator var16 = attrs.iterator();

            while(var16.hasNext()) {
               Map.Entry entry = (Map.Entry)var16.next();
               String attrName = (String)entry.getKey();
               Object attrValue = entry.getValue();
               list.add(Util.getFeatureDescriptor(attrName, attrName, "view scope attribute", false, false, true, attrValue.getClass(), Boolean.TRUE));
            }
         }
      }

      attrs = ec.getSessionMap().entrySet();
      Iterator var15 = attrs.iterator();

      Map.Entry entry;
      String attrName;
      Object attrValue;
      while(var15.hasNext()) {
         entry = (Map.Entry)var15.next();
         attrName = (String)entry.getKey();
         attrValue = entry.getValue();
         list.add(Util.getFeatureDescriptor(attrName, attrName, "session scope attribute", false, false, true, attrValue.getClass(), Boolean.TRUE));
      }

      attrs = ec.getApplicationMap().entrySet();
      var15 = attrs.iterator();

      while(var15.hasNext()) {
         entry = (Map.Entry)var15.next();
         attrName = (String)entry.getKey();
         attrValue = entry.getValue();
         list.add(Util.getFeatureDescriptor(attrName, attrName, "application scope attribute", false, false, true, attrValue.getClass(), Boolean.TRUE));
      }

      return list.iterator();
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return base != null ? null : Object.class;
   }
}
