package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.ObjectName;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

final class JMOValidator {
   public static final String NAME = "name";
   public static final String TYPE = "j2eeType";
   public static final String WLSNAME = "Name";
   public static final String WLSTYPE = "Type";
   public static final String WLSLOCATION = "Location";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String server;
   private final ObjectName objectName;
   private final String name;
   private final String type;
   private final String domain;
   private final StringBuffer objectNameString;

   JMOValidator(ObjectName oName) {
      this.objectName = oName;
      this.name = this.objectName.getKeyProperty("name");
      this.type = this.objectName.getKeyProperty("j2eeType");
      this.domain = this.objectName.getDomain();
      this.objectNameString = new StringBuffer();
      this.objectNameString.append(this.domain + ":" + "Name" + "=" + this.name + ",");
   }

   String getName(ObjectName objectName) {
      return objectName.getKeyProperty("name");
   }

   String getType(ObjectName objectName) {
      return objectName.getKeyProperty("j2eeType");
   }

   void validate() throws InvalidObjectNameException {
      if (this.name != null && this.type != null) {
         if (this.domain != null && this.domain.indexOf(63) <= 1 && this.domain.indexOf(42) <= 1 && this.domain.indexOf(59) <= 1 && this.domain.indexOf(44) <= 1 && this.domain.indexOf(61) <= 1) {
            JMOTypesHelper.validateParents(this.objectName);
         } else {
            throw new InvalidObjectNameException("Not a valid J2EE Managed Object");
         }
      } else {
         throw new InvalidObjectNameException("Not a valid J2EE Managed Object");
      }
   }

   ObjectName getJ2EEObjectName() {
      return this.objectName;
   }

   static {
      server = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }
}
