package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

public class EISResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"application", "module", "eis", "destinationId"};
   private static final Resource TOP = new EISResource((String[])null, 0);

   public EISResource(String application, String module, String eis) throws InvalidParameterException {
      this.initialize(application, module, eis, (String)null);
   }

   public EISResource(String application, String module, String eis, String destinationId) throws InvalidParameterException {
      this.initialize(application, module, eis, destinationId);
   }

   private void initialize(String application, String module, String eis, String destinationId) {
      if (application != null && application.length() == 0) {
         throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("EIS", KEYS[0]));
      } else {
         if (destinationId != null) {
            this.init(new String[]{application, module, eis, destinationId}, 0L);
         } else {
            this.init(new String[]{application, module, eis}, 0L);
         }

      }
   }

   private EISResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   public String getType() {
      return "<eis>";
   }

   protected Resource makeParent() {
      switch (this.length) {
         case 0:
            return null;
         case 1:
            return new ApplicationResource(this.values[0], TOP);
         default:
            return new EISResource(this.values, this.length - 1);
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

   public String getEISName() {
      return this.length > 2 ? this.values[2] : null;
   }

   public String getDestinationId() {
      return this.length > 3 ? this.values[3] : null;
   }
}
