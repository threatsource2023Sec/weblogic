package com.oracle.cmm.lowertier.el;

import java.util.HashMap;
import javax.el.BeanELResolver;
import javax.el.ELContext;

public class ELResolverImpl extends BeanELResolver {
   private HashMap beans = new HashMap();

   public ELResolverImpl() {
      super(true);
   }

   public Object getValue(ELContext context, Object base, Object property) {
      if (context == null) {
         throw new NullPointerException();
      } else if (property == null) {
         return null;
      } else {
         if (base == null && property instanceof String) {
            String name = (String)property;
            Object value = this.beans.get(name);
            if (value != null) {
               context.setPropertyResolved(true);
               return value;
            }
         }

         return super.getValue(context, base, property);
      }
   }

   public Class getType(ELContext context, Object base, Object property) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (base == null && property instanceof String) {
            Object value = this.beans.get((String)property);
            if (value != null) {
               context.setPropertyResolved(true);
               return value.getClass();
            }
         }

         return super.getType(context, base, property);
      }
   }

   public void addBean(String bean, Object value) {
      if (bean != null && value != null) {
         this.beans.put(bean, value);
      } else {
         throw new NullPointerException();
      }
   }

   public Object remove(String name) {
      Object removed = null;
      if (name != null) {
         removed = this.beans.remove(name);
      }

      return removed;
   }
}
