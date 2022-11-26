package com.sun.faces.scripting;

import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.FacesException;

public class ELResolverProxy extends ELResolver {
   private String scriptName;

   public ELResolverProxy(String scriptName) {
      this.scriptName = scriptName;
   }

   public Object getValue(ELContext elContext, Object o, Object o1) {
      return this.getGroovyDelegate().getValue(elContext, o, o1);
   }

   public Class getType(ELContext elContext, Object o, Object o1) {
      return this.getGroovyDelegate().getType(elContext, o, o1);
   }

   public void setValue(ELContext elContext, Object o, Object o1, Object o2) {
      this.getGroovyDelegate().setValue(elContext, o, o1, o2);
   }

   public boolean isReadOnly(ELContext elContext, Object o, Object o1) {
      return this.getGroovyDelegate().isReadOnly(elContext, o, o1);
   }

   public Iterator getFeatureDescriptors(ELContext elContext, Object o) {
      return this.getGroovyDelegate().getFeatureDescriptors(elContext, o);
   }

   public Class getCommonPropertyType(ELContext elContext, Object o) {
      return this.getGroovyDelegate().getCommonPropertyType(elContext, o);
   }

   private ELResolver getGroovyDelegate() {
      try {
         return (ELResolver)GroovyHelper.newInstance(this.scriptName);
      } catch (Exception var2) {
         throw new FacesException(var2);
      }
   }
}
