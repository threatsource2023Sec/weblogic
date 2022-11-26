package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

public final class EJBResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"application", "module", "ejb", "method", "methodInterface", "signature"};
   private static final Resource TOP = new EJBResource((String[])null, 0);
   private static boolean USE_SP1_IMPL = false;
   private String[] methodParams = null;

   private static String flatten(String[] a) {
      return a == null ? null : appendArrayValue(new StringBuffer(256), a, a.length).toString();
   }

   public EJBResource(String app, String module, String ejb, String method, String methodInterface, String[] methodParams) {
      this.methodParams = methodParams;
      String[] values = new String[]{app, module, ejb, method, methodInterface, flatten(methodParams)};

      for(int i = 4; i >= 0; --i) {
         if (values[i] != null && values[i].length() == 0) {
            throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("EJB", KEYS[i]));
         }
      }

      this.init(values, 0L);
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("signature") ? 3 : 1;
   }

   protected void writeResourceString(StringBuffer buf) {
      if (USE_SP1_IMPL) {
         super.writeResourceString(buf);
      } else {
         buf.append("type=").append(this.getType());

         for(int i = 0; i < this.length; ++i) {
            buf.append(", ").append(KEYS[i]).append("=");
            if (i != 5) {
               appendValue(buf, this.values[i]);
            } else {
               buf.append(this.values[i]);
            }
         }

      }
   }

   private EJBResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   public String getType() {
      return "<ejb>";
   }

   protected Resource makeParent() {
      switch (this.length) {
         case 0:
            return null;
         case 1:
            return new ApplicationResource(this.values[0], TOP);
         default:
            return new EJBResource(this.values, this.length - 1);
      }
   }

   public String[] getKeys() {
      return KEYS;
   }

   public String getApplicationName() {
      return this.length > 0 ? this.values[0] : null;
   }

   public String getModuleName() {
      return this.length > 1 ? this.values[1] : null;
   }

   public String getEJBName() {
      return this.length > 2 ? this.values[2] : null;
   }

   public String getMethodName() {
      return this.length > 3 ? this.values[3] : null;
   }

   public String getMethodInterface() {
      return this.length > 4 ? this.values[4] : null;
   }

   public String[] getMethodParams() {
      return this.methodParams;
   }

   static {
      try {
         USE_SP1_IMPL = Boolean.getBoolean("weblogic.security.useSp1EJBResource");
      } catch (Exception var1) {
      }

   }
}
