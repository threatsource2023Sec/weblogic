package com.sun.faces.facelets.tag;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.faces.view.facelets.MetadataTarget;

public class MetadataTargetImpl extends MetadataTarget {
   private final Map pd;
   private final Class type;

   public MetadataTargetImpl(Class type) throws IntrospectionException {
      this.type = type;
      this.pd = new HashMap();
      BeanInfo info = Introspector.getBeanInfo(type);
      PropertyDescriptor[] pda = info.getPropertyDescriptors();

      for(int i = 0; i < pda.length; ++i) {
         this.pd.put(pda[i].getName(), pda[i]);
      }

   }

   public PropertyDescriptor getProperty(String name) {
      return (PropertyDescriptor)this.pd.get(name);
   }

   public boolean isTargetInstanceOf(Class type) {
      return type.isAssignableFrom(this.type);
   }

   public Class getTargetClass() {
      return this.type;
   }

   public Class getPropertyType(String name) {
      PropertyDescriptor pd = this.getProperty(name);
      return pd != null ? pd.getPropertyType() : null;
   }

   public Method getWriteMethod(String name) {
      PropertyDescriptor pd = this.getProperty(name);
      return pd != null ? pd.getWriteMethod() : null;
   }

   public Method getReadMethod(String name) {
      PropertyDescriptor pd = this.getProperty(name);
      return pd != null ? pd.getReadMethod() : null;
   }
}
