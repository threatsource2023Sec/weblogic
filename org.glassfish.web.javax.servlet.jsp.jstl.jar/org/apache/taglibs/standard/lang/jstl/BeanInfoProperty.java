package org.apache.taglibs.standard.lang.jstl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanInfoProperty {
   Method mReadMethod;
   Method mWriteMethod;
   PropertyDescriptor mPropertyDescriptor;

   public Method getReadMethod() {
      return this.mReadMethod;
   }

   public Method getWriteMethod() {
      return this.mWriteMethod;
   }

   public PropertyDescriptor getPropertyDescriptor() {
      return this.mPropertyDescriptor;
   }

   public BeanInfoProperty(Method pReadMethod, Method pWriteMethod, PropertyDescriptor pPropertyDescriptor) {
      this.mReadMethod = pReadMethod;
      this.mWriteMethod = pWriteMethod;
      this.mPropertyDescriptor = pPropertyDescriptor;
   }
}
