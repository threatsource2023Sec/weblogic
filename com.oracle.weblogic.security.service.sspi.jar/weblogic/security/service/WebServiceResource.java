package weblogic.security.service;

import weblogic.security.spi.Resource;

public final class WebServiceResource extends ResourceBase {
   private static String[] KEYS = new String[]{"application", "contextPath", "webService", "method", "signature"};
   private static final Resource TOP = new WebServiceResource((String[])null, 0);

   private static String flatten(String[] a) {
      return a == null ? null : appendArrayValue(new StringBuffer(256), a, a.length).toString();
   }

   public WebServiceResource(String application, String contextPath, String webService, String method, String[] signature) {
      String[] vals = new String[]{application, contextPath, webService, method, flatten(signature)};
      this.init(vals, 0L);
   }

   private WebServiceResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   protected void writeResourceString(StringBuffer buf) {
      buf.append("type=").append(this.getType());

      for(int i = 0; i < this.length; ++i) {
         buf.append(", ").append(KEYS[i]).append('=');
         if (i != 4) {
            appendValue(buf, this.values[i]);
         } else {
            buf.append(this.values[i]);
         }
      }

   }

   public void initialize(String resourceDefinition) {
   }

   public String getType() {
      return "<webservices>";
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("signature") ? 3 : 1;
   }

   protected Resource makeParent() {
      switch (this.length) {
         case 0:
            return null;
         case 1:
            return new ApplicationResource(this.values[0], TOP);
         default:
            return new WebServiceResource(this.values, this.length - 1);
      }
   }

   public String[] getKeys() {
      return KEYS;
   }
}
