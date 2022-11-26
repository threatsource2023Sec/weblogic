package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

public final class ControlResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"application", "control", "method", "signature"};
   private static final Resource TOP = new ControlResource((String[])null, 0);
   private String[] methodParams = null;

   private static String flatten(String[] a) {
      return a == null ? null : appendArrayValue(new StringBuffer(256), a, a.length).toString();
   }

   public ControlResource(String application, String control, String method, String[] methodParams) {
      this.methodParams = methodParams;
      String[] values = new String[]{application, control, method, flatten(methodParams)};

      for(int i = 1; i >= 0; --i) {
         if (values[i] != null && values[i].length() == 0) {
            throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("CONTROL", KEYS[i]));
         }
      }

      this.init(values, 0L);
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("signature") ? 3 : 1;
   }

   protected void writeResourceString(StringBuffer buf) {
      buf.append("type=").append(this.getType());

      for(int i = 0; i < this.length; ++i) {
         buf.append(", ").append(KEYS[i]).append("=");
         if (i != 3) {
            appendValue(buf, this.values[i]);
         } else {
            buf.append(this.values[i]);
         }
      }

   }

   private ControlResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   public String getType() {
      return "<control>";
   }

   protected Resource makeParent() {
      switch (this.length) {
         case 0:
            return null;
         case 1:
            return new ApplicationResource(this.values[0], TOP);
         default:
            return new ControlResource(this.values, this.length - 1);
      }
   }

   public String[] getKeys() {
      return KEYS;
   }

   public String getApplicationName() {
      return this.length > 0 ? this.values[0] : null;
   }

   public String getControlName() {
      return this.length > 1 ? this.values[1] : null;
   }

   public String getMethodName() {
      return this.length > 2 ? this.values[2] : null;
   }

   public String[] getMethodParams() {
      return this.methodParams;
   }
}
