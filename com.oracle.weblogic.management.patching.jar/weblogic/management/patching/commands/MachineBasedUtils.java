package weblogic.management.patching.commands;

import java.security.AccessController;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class MachineBasedUtils {
   protected static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MachineMBean getMachineMBean(String machineName) throws CommandException {
      if (machineName != null && !machineName.isEmpty()) {
         if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
            MachineMBean mac = domain.lookupMachine(machineName);
            if (mac == null) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getInvalidMachine(machineName));
            } else {
               return mac;
            }
         } else {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().getRequireAdminServerToFindObj(machineName));
         }
      } else {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getInvalidMachine(machineName));
      }
   }
}
