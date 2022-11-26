package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

public final class COMResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"application", "class"};
   private static final Resource TOP = new COMResource((String[])null, 0);

   public COMResource(String application, String className) throws InvalidParameterException {
      this.initialize(application, className);
   }

   /** @deprecated */
   @Deprecated
   public COMResource(String application, String className, String action) throws InvalidParameterException {
      this.initialize(application, className);
   }

   private void initialize(String application, String className) {
      if (application != null && application.length() == 0) {
         throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("COM", KEYS[0]));
      } else {
         this.init(new String[]{application, className}, 0L);
      }
   }

   private COMResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   public String getType() {
      return "<com>";
   }

   protected Resource makeParent() {
      switch (this.length) {
         case 0:
            return null;
         case 1:
            return new ApplicationResource(this.values[0], TOP);
         case 2:
            int i = this.values[1].lastIndexOf(46);
            if (i > -1) {
               return new COMResource(new String[]{this.values[0], this.values[1].substring(0, i)}, 2);
            }
         default:
            return new COMResource(this.values, this.length - 1);
      }
   }

   public String[] getKeys() {
      return KEYS;
   }

   public String getClassName() {
      return this.length > 1 ? this.values[1] : null;
   }
}
