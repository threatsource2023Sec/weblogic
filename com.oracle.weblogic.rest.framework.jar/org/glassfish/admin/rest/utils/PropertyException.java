package org.glassfish.admin.rest.utils;

public class PropertyException extends Exception {
   private String field = null;

   public PropertyException(String field, Throwable cause) {
      super(cause);
      this.field = field;
   }

   public PropertyException(String field, String message) {
      super(message);
      this.field = field;
   }

   public PropertyException(String field, String message, Throwable cause) {
      super(message, cause);
      this.field = field;
   }

   public String getField() {
      return this.field;
   }
}
