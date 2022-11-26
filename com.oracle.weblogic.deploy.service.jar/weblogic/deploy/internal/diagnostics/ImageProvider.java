package weblogic.deploy.internal.diagnostics;

import java.security.AccessController;
import java.util.Iterator;
import weblogic.application.utils.XMLWriter;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class ImageProvider {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final boolean isAdminServer;
   public boolean timedOut = false;

   public abstract void writeDiagnosticImage(XMLWriter var1);

   public void timeoutImageCreation() {
      this.timedOut = true;
   }

   public void writeCollection(XMLWriter writer, Iterator iterator, String element) {
      StringBuffer sb = new StringBuffer();

      while(iterator.hasNext()) {
         sb.append((String)iterator.next());
         if (iterator.hasNext()) {
            sb.append(", ");
         }
      }

      writer.addElement(element, sb.toString());
   }

   static {
      isAdminServer = ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer();
   }
}
