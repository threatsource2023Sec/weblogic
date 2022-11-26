package com.bea.httppubsub.util;

import java.io.Serializable;

public class NameValuePair implements Serializable {
   private static final long serialVersionUID = 7651619503761047947L;
   private final String name;
   private final Object value;

   public NameValuePair(String name, Object value) {
      if (name != null && name.length() != 0) {
         this.name = name;
         this.value = value;
      } else {
         throw new IllegalArgumentException("Name cannot be empty.");
      }
   }

   public String getName() {
      return this.name;
   }

   public Object getValue() {
      return this.value;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof NameValuePair)) {
         return false;
      } else {
         NameValuePair that = (NameValuePair)obj;
         return this.name.equals(that.name) && this.value == null ? that.value == null : this.value.equals(that.value);
      }
   }

   public int hashCode() {
      int result = 17;
      result = result * 37 + this.name.hashCode();
      result = result * 37 + (this.value == null ? 0 : this.value.hashCode());
      return result;
   }

   public String toString() {
      return "[" + this.name + "] = [" + this.value + "]";
   }
}
