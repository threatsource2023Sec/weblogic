package weblogic.management.commo;

import java.io.Serializable;
import javax.management.JMException;
import javax.management.JMRuntimeException;
import javax.management.ObjectName;

public final class Commo {
   public static final ObjectName getTypeTypeObjectName() throws JMException {
      try {
         return getTypeObjectName("CustomMBeanType");
      } catch (Exception var1) {
         if (var1 instanceof JMException) {
            throw (JMException)var1;
         } else {
            throw (JMRuntimeException)var1;
         }
      }
   }

   public static final String getTypeShortName(ObjectName typeObjectName) throws JMException {
      try {
         String shortName = typeObjectName.getKeyProperty("Name");
         return shortName;
      } catch (Exception var2) {
         if (var2 instanceof JMException) {
            throw (JMException)var2;
         } else {
            throw (JMRuntimeException)var2;
         }
      }
   }

   public static final ObjectName getTypeObjectName(String typeName) throws JMException {
      try {
         ObjectName mBeanTypeObjectName = null;
         if (!typeName.equals("*")) {
            mBeanTypeObjectName = new ObjectName("WeblogicManagement:Type=CustomMBeanType,Name=" + typeName);
         } else {
            mBeanTypeObjectName = new ObjectName("WeblogicManagement:Type=CustomMBeanType," + typeName);
         }

         return mBeanTypeObjectName;
      } catch (Exception var2) {
         if (var2 instanceof JMException) {
            throw (JMException)var2;
         } else {
            throw (JMRuntimeException)var2;
         }
      }
   }

   public static final class Pair implements Serializable {
      static final long serialVersionUID = 1L;
      Object key;
      Object value;
      private int hashCode;

      public Pair(Object k, Object v) {
         this.key = k;
         this.value = v;
      }

      public final String getName() {
         return this.key.toString();
      }

      public final Object getKey() {
         return this.key;
      }

      public final Object getValue() {
         return this.value;
      }

      public int hashCode() {
         return this.hashCode != 0 ? this.hashCode : (this.hashCode = (this.value != null ? this.value.hashCode() : 0) ^ (this.key != null ? this.key.hashCode() : 0));
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (!(obj instanceof Pair)) {
            return false;
         } else {
            Pair pair = (Pair)obj;
            if (this.value != null && this.key != null) {
               return this.value.equals(pair.value) && this.key.equals(pair.key);
            } else if (this.value == null && this.key != null) {
               return pair.value == null && this.key.equals(pair.key);
            } else if (this.value != null && this.key == null) {
               return pair.key == null && this.value.equals(pair.value);
            } else if (this.value == null && this.key == null) {
               return pair.key == null && pair.value == null;
            } else {
               return false;
            }
         }
      }
   }
}
