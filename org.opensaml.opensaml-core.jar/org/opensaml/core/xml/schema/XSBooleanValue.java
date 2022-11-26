package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class XSBooleanValue {
   private boolean numeric;
   private Boolean value;

   public XSBooleanValue() {
      this.numeric = false;
      this.value = null;
   }

   public XSBooleanValue(@Nullable Boolean newValue, boolean numericRepresentation) {
      this.numeric = numericRepresentation;
      this.value = newValue;
   }

   @Nullable
   public Boolean getValue() {
      return this.value;
   }

   public void setValue(@Nullable Boolean newValue) {
      this.value = newValue;
   }

   public boolean isNumericRepresentation() {
      return this.numeric;
   }

   public void setNumericRepresentation(boolean numericRepresentation) {
      this.numeric = numericRepresentation;
   }

   public int hashCode() {
      byte hash;
      if (this.numeric) {
         if (this.value == null) {
            hash = 0;
         } else if (this.value) {
            hash = 1;
         } else {
            hash = 3;
         }
      } else if (this.value == null) {
         hash = 4;
      } else if (this.value) {
         hash = 5;
      } else {
         hash = 6;
      }

      return hash;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSBooleanValue) {
         return this.hashCode() == obj.hashCode();
      } else {
         return false;
      }
   }

   public String toString() {
      return toString(this.value, this.numeric);
   }

   public static String toString(Boolean value, boolean numericRepresentation) {
      if (value == null) {
         return "false";
      } else if (numericRepresentation) {
         return value ? "1" : "0";
      } else {
         return value.toString();
      }
   }

   public static XSBooleanValue valueOf(@Nullable String booleanString) {
      String trimmedBooleanString = StringSupport.trimOrNull(booleanString);
      if (trimmedBooleanString != null) {
         if ("1".equals(trimmedBooleanString)) {
            return new XSBooleanValue(Boolean.TRUE, true);
         }

         if ("0".equals(trimmedBooleanString)) {
            return new XSBooleanValue(Boolean.FALSE, true);
         }

         if ("true".equals(trimmedBooleanString)) {
            return new XSBooleanValue(Boolean.TRUE, false);
         }
      }

      return new XSBooleanValue(Boolean.FALSE, false);
   }
}
