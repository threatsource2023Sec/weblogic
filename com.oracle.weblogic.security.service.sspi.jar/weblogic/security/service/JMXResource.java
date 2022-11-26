package weblogic.security.service;

import weblogic.security.spi.Resource;

public final class JMXResource extends ResourceBase {
   public static final String GET = "get";
   public static final String GET_ENCRYPTED = "getEncrypted";
   public static final String SET = "set";
   public static final String SET_ENCRYPTED = "setEncrypted";
   public static final String FIND = "find";
   public static final String INVOKE = "invoke";
   public static final String CREATE = "create";
   public static final String UNREGISTER = "unregister";
   private static final String[] KEYS = new String[]{"operation", "application", "mbeanType", "target"};
   private static final Resource TOP = new JMXResource((String)null, (String)null, (String)null, (String)null);

   public JMXResource(String operation, String application, String beanType, String target) {
      String[] vals = new String[]{operation, application, beanType, target};
      this.init(vals, 0L);
   }

   private JMXResource(String[] values, int length) {
      if (values != null && values[1] != null) {
         this.init(values, values.length, 0L);
         this.length = length;
      } else {
         this.init(values, length, 0L);
      }

   }

   public String getType() {
      return "<jmx>";
   }

   public String[] getKeys() {
      return KEYS;
   }

   protected Resource makeParent() {
      if (this.length == 0) {
         return null;
      } else if (this.length == 1) {
         return TOP;
      } else if (this.length == 2 && this.values[1] != null && this.values.length > 2) {
         String[] newVals = new String[this.values.length];

         for(int i = 0; i < this.values.length; ++i) {
            newVals[i] = this.values[i];
         }

         newVals[1] = null;
         return new JMXResource(newVals, newVals.length);
      } else {
         return new JMXResource(this.values, this.length - 1);
      }
   }
}
