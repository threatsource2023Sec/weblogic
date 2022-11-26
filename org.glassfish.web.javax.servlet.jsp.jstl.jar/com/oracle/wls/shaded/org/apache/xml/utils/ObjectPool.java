package com.oracle.wls.shaded.org.apache.xml.utils;

import com.oracle.wls.shaded.org.apache.xml.res.XMLMessages;
import java.io.Serializable;
import java.util.ArrayList;

public class ObjectPool implements Serializable {
   static final long serialVersionUID = -8519013691660936643L;
   private final Class objectType;
   private final ArrayList freeStack;

   public ObjectPool(Class type) {
      this.objectType = type;
      this.freeStack = new ArrayList();
   }

   public ObjectPool(String className) {
      try {
         this.objectType = ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
      } catch (ClassNotFoundException var3) {
         throw new WrappedRuntimeException(var3);
      }

      this.freeStack = new ArrayList();
   }

   public ObjectPool(Class type, int size) {
      this.objectType = type;
      this.freeStack = new ArrayList(size);
   }

   public ObjectPool() {
      this.objectType = null;
      this.freeStack = new ArrayList();
   }

   public synchronized Object getInstanceIfFree() {
      if (!this.freeStack.isEmpty()) {
         Object result = this.freeStack.remove(this.freeStack.size() - 1);
         return result;
      } else {
         return null;
      }
   }

   public synchronized Object getInstance() {
      if (this.freeStack.isEmpty()) {
         try {
            return this.objectType.newInstance();
         } catch (InstantiationException var2) {
         } catch (IllegalAccessException var3) {
         }

         throw new RuntimeException(XMLMessages.createXMLMessage("ER_EXCEPTION_CREATING_POOL", (Object[])null));
      } else {
         Object result = this.freeStack.remove(this.freeStack.size() - 1);
         return result;
      }
   }

   public synchronized void freeInstance(Object obj) {
      this.freeStack.add(obj);
   }
}
