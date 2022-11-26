package weblogic.servlet.internal;

import java.io.IOException;
import weblogic.common.internal.PassivationUtils;

public class ContextAttributeWrapper extends AttributeWrapper {
   static final long serialVersionUID = 639350188940216525L;
   private transient Object convertedObject = null;

   public ContextAttributeWrapper(Object obj) {
      super(obj);
   }

   ContextAttributeWrapper(Object obj, WebAppServletContext ctx) {
      super(obj, ctx);
   }

   protected Object getObject(boolean checkReload, WebAppServletContext ctx) throws ClassNotFoundException, IOException {
      if (this.serializedObject != null) {
         synchronized(this) {
            return this.serializedObject == null ? this.object : this.convertBytesToObject(this.serializedObject);
         }
      } else if (!checkReload) {
         return this.object;
      } else if (needToWrap(this.object) && !this.isOptimisticSerialization(ctx)) {
         ClassLoader currentCL = Thread.currentThread().getContextClassLoader();
         if (this.needToConvert(currentCL, ctx)) {
            synchronized(this) {
               if (this.convertedObject != null && this.convertedObject.getClass().getClassLoader() == currentCL) {
                  return this.convertedObject;
               } else {
                  this.convertedObject = PassivationUtils.copy(this.object);
                  this.setForceToConvert(false);
                  return this.convertedObject;
               }
            }
         } else {
            return this.object;
         }
      } else {
         return this.object;
      }
   }

   public synchronized void convertToBytes() throws IOException {
      if (this.object != null) {
         super.convertToBytes();
         this.convertedObject = null;
         this.object = null;
      }
   }

   protected void reInitClassLoaderInfo() {
   }

   protected boolean isSameThreadClassLoader(int cclHash) {
      return false;
   }
}
