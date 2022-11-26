package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;

class DiagnosticsBeanResolver extends ELResolver {
   private static final DiagnosticsFrameworkTextTextFormatter txtFmt = DiagnosticsFrameworkTextTextFormatter.getInstance();
   private Map beanMap = new HashMap();

   public Object getValue(ELContext context, Object base, Object property) {
      if (context == null) {
         throw new NullPointerException();
      } else if (property == null) {
         return null;
      } else {
         if (base == null && property instanceof String) {
            String name = (String)property;
            Object value = this.beanMap.get(name);
            if (value != null) {
               context.setPropertyResolved(true);
               return value;
            }
         }

         return null;
      }
   }

   public Class getType(ELContext context, Object base, Object property) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (base == null && property instanceof String) {
            Object value = this.beanMap.get((String)property);
            if (value != null) {
               context.setPropertyResolved(true);
               return value.getClass();
            }
         }

         return null;
      }
   }

   public Object invoke(ELContext context, Object base, Object method, Class[] paramTypes, Object[] params) {
      return null;
   }

   public void bind(String name, Object value) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         Object currentBoundObject = this.beanMap.get(name);
         if (currentBoundObject != null) {
            throw new IllegalArgumentException(txtFmt.getELBeanNameAlreadyBoundText(name, currentBoundObject.toString()));
         } else {
            this.beanMap.put(name, value);
         }
      }
   }

   public Object unbind(String name) {
      Object removed = null;
      if (name != null) {
         removed = this.beanMap.remove(name);
      }

      return removed;
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return false;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return null;
   }
}
