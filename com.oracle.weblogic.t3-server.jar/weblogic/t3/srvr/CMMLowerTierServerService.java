package weblogic.t3.srvr;

import com.oracle.cmm.lowertier.Informer;
import com.oracle.cmm.lowertier.LowerTier;
import java.lang.management.ManagementFactory;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class CMMLowerTierServerService extends AbstractServerService implements Informer {
   private boolean dontRetryMbean = false;
   private static final boolean SERVICE_ENABLED = Boolean.getBoolean("weblogic.utils.cmm.lowertier.ServiceEnabled");
   private static final boolean SERVICE_DISABLED = Boolean.getBoolean("weblogic.utils.cmm.lowertier.ServiceDisabled");
   private static final String RESOURCE_PRESSURE_MBEAN = "com.oracle.management:type=ResourcePressureMBean";
   private static final String RPM_MEMORY_PRESSURE_FIELD = "MemoryPressure";
   private ObjectName resourcePressureName = null;
   private MBeanServer platformMBeanServer = null;
   @Inject
   @Named("CMMServerService")
   private ServerService dependencyOnCMMServerService;
   @Inject
   @Named("BootService")
   private ServerService dependencyOnBootService;

   public void start() throws ServiceFailureException {
      if (!SERVICE_DISABLED) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         RuntimeAccess rtAccess = ManagementService.getRuntimeAccess(kernelId);
         DomainMBean domain = rtAccess.getDomain();
         if (domain.isExalogicOptimizationsEnabled() || SERVICE_ENABLED) {
            try {
               if (!LowerTier.initialize(this)) {
               }
            } catch (IllegalStateException var5) {
            }

         }
      }
   }

   public synchronized void setMemoryPressure(int currentMemoryPressure) {
      if (!this.dontRetryMbean) {
         if (this.platformMBeanServer == null) {
            if (this.resourcePressureName == null) {
               try {
                  this.resourcePressureName = new ObjectName("com.oracle.management:type=ResourcePressureMBean");
               } catch (MalformedObjectNameException var4) {
                  this.dontRetryMbean = true;
                  return;
               } catch (NullPointerException var5) {
                  this.dontRetryMbean = true;
                  return;
               }
            }

            this.platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            if (this.platformMBeanServer == null || !this.platformMBeanServer.isRegistered(this.resourcePressureName)) {
               return;
            }
         }

         try {
            this.platformMBeanServer.setAttribute(this.resourcePressureName, new Attribute("MemoryPressure", currentMemoryPressure));
         } catch (Exception var3) {
         }

      }
   }
}
