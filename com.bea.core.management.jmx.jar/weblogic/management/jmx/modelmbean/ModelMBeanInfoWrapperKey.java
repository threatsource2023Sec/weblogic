package weblogic.management.jmx.modelmbean;

import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.provider.BeanInfoKey;

class ModelMBeanInfoWrapperKey implements Comparable {
   protected final ObjectNameManager nameManager;
   protected final BeanInfoKey beanInfoKey;

   public ModelMBeanInfoWrapperKey(BeanInfoKey beanInfoKey, ObjectNameManager nameManager) {
      this.beanInfoKey = beanInfoKey;
      this.nameManager = nameManager;
   }

   public String getBeanInfoClassName() {
      return this.beanInfoKey.getBeanInfoClassName();
   }

   public ClassLoader getBeanInfoClassLoader() {
      return this.beanInfoKey.getBeanInfoClass().getClassLoader();
   }

   public ObjectNameManager getNameManager() {
      return this.nameManager;
   }

   public boolean isReadOnly() {
      return this.beanInfoKey.isReadOnly();
   }

   public String getVersion() {
      return this.beanInfoKey.getVersion();
   }

   private static int compareObjects(Object one, Object two) {
      if (one == two) {
         return 0;
      } else if (one == null) {
         return -1;
      } else if (two == null) {
         return 1;
      } else {
         int thisVal = one.hashCode();
         int anotherVal = two.hashCode();
         return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
      }
   }

   public int compareTo(Object otherObject) {
      ModelMBeanInfoWrapperKey other = (ModelMBeanInfoWrapperKey)otherObject;
      if (other == null) {
         return 1;
      } else {
         int result = this.getBeanInfoClassName().compareTo(other.getBeanInfoClassName());
         if (result != 0) {
            return result;
         } else {
            result = compareObjects(this.getVersion(), other.getVersion());
            if (result != 0) {
               return result;
            } else {
               result = compareObjects(this.nameManager, other.nameManager);
               if (result != 0) {
                  return result;
               } else {
                  result = this.isReadOnly() && !other.isReadOnly() ? -1 : (!this.isReadOnly() && other.isReadOnly() ? 1 : 0);
                  if (result != 0) {
                     return result;
                  } else {
                     result = compareObjects(this.getBeanInfoClassLoader(), other.getBeanInfoClassLoader());
                     return result;
                  }
               }
            }
         }
      }
   }

   public boolean equals(Object otherObject) {
      return this.compareTo(otherObject) == 0;
   }

   public int hashCode() {
      return this.getBeanInfoClassName().hashCode();
   }

   public String toString() {
      return "ModelMBeanInfoWrapperKey: name=" + this.getBeanInfoClassName() + ",version=" + this.getVersion() + ",readonly=" + this.isReadOnly() + ",nameMgr=" + this.nameManager + ",classldr=" + this.getBeanInfoClassLoader();
   }
}
