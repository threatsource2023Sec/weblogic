package org.apache.taglibs.standard.lang.jstl;

import java.beans.IndexedPropertyDescriptor;
import java.lang.reflect.Method;

public class BeanInfoIndexedProperty {
   Method mReadMethod;
   Method mWriteMethod;
   IndexedPropertyDescriptor mIndexedPropertyDescriptor;

   public Method getReadMethod() {
      return this.mReadMethod;
   }

   public Method getWriteMethod() {
      return this.mWriteMethod;
   }

   public IndexedPropertyDescriptor getIndexedPropertyDescriptor() {
      return this.mIndexedPropertyDescriptor;
   }

   public BeanInfoIndexedProperty(Method pReadMethod, Method pWriteMethod, IndexedPropertyDescriptor pIndexedPropertyDescriptor) {
      this.mReadMethod = pReadMethod;
      this.mWriteMethod = pWriteMethod;
      this.mIndexedPropertyDescriptor = pIndexedPropertyDescriptor;
   }
}
