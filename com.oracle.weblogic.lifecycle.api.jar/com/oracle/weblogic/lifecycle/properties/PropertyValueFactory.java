package com.oracle.weblogic.lifecycle.properties;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

public final class PropertyValueFactory {
   private static ClearOrEncryptedService clearOrEncryptedService;

   private PropertyValueFactory() {
   }

   public static StringPropertyValue getStringPropertyValue(String value) {
      return new StringPropertyValueImpl(value);
   }

   public static ConfidentialPropertyValue getConfidentialPropertyValue(String value) {
      return new ConfidentialPropertyValueImpl(value);
   }

   public static ListPropertyValue getListPropertyValue(List value) {
      return new ListPropertyValueImpl(value);
   }

   public static PropertiesPropertyValue getPropertiesPropertyValue(Map value) {
      return new PropertiesPropertyValueImpl(value);
   }

   public static PropertyValue getStringOrConfidentialPropertyValue(String value) {
      ClearOrEncryptedService ces = getClearOrEncryptedService();
      return (PropertyValue)(ces.isEncrypted(value) ? new ConfidentialPropertyValueImpl(value) : new StringPropertyValueImpl(value));
   }

   private static ClearOrEncryptedService getClearOrEncryptedService() {
      if (clearOrEncryptedService == null) {
         Class var0 = PropertyValueFactory.class;
         synchronized(PropertyValueFactory.class) {
            if (clearOrEncryptedService == null) {
               clearOrEncryptedService = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
            }
         }
      }

      return clearOrEncryptedService;
   }

   private static class PropertiesPropertyValueImpl implements PropertiesPropertyValue, Serializable {
      private static final long serialVersionUID = 1L;
      Map value;

      PropertiesPropertyValueImpl(Map value) {
         this.value = value;
      }

      public Map getValue() {
         return this.value;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         if (this.value != null && !this.value.isEmpty()) {
            int i = 0;
            Iterator var3 = this.value.entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry me = (Map.Entry)var3.next();
               if (me != null) {
                  if (i > 0) {
                     sb.append(", ");
                  }

                  sb.append("{ ").append((String)me.getKey()).append(" : ").append(me.getValue()).append(" }");
                  ++i;
               }
            }
         }

         return sb.toString();
      }
   }

   private static class ListPropertyValueImpl implements ListPropertyValue, Serializable {
      private static final long serialVersionUID = 1L;
      private List values;

      public ListPropertyValueImpl(List values) {
         this.values = values;
      }

      public List getValue() {
         return this.values;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         if (this.values != null && !this.values.isEmpty()) {
            int i = 0;

            for(Iterator var3 = this.values.iterator(); var3.hasNext(); ++i) {
               PropertyValue pv = (PropertyValue)var3.next();
               if (i > 0) {
                  sb.append(", ");
               }

               sb.append(pv);
            }
         }

         return sb.toString();
      }
   }

   private static class ConfidentialPropertyValueImpl implements ConfidentialPropertyValue, Serializable {
      private static final long serialVersionUID = 1L;
      String value;
      String encryptedValue;

      ConfidentialPropertyValueImpl(String value) {
         ClearOrEncryptedService ces = PropertyValueFactory.getClearOrEncryptedService();
         if (ces.isEncrypted(value)) {
            this.encryptedValue = value;
            this.value = ces.decrypt(value);
         } else {
            this.encryptedValue = ces.encrypt(value);
            this.value = value;
         }

      }

      public boolean isEmpty() {
         return this.value == null || this.value.trim().isEmpty();
      }

      public String getValue() {
         return this.value;
      }

      public String getEncryptedValue() {
         return this.encryptedValue;
      }

      public String toString() {
         return this.getValue();
      }
   }

   private static class StringPropertyValueImpl implements StringPropertyValue, Serializable {
      String value;

      StringPropertyValueImpl(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }

      public String toString() {
         return this.getValue();
      }
   }
}
