package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class WaitForCoherenceServicesHAStatusBaseCommand extends AbstractCommand {
   private static final long serialVersionUID = -471351750230977311L;
   public static final String ENDANGERED = "endangered";
   public static final String SITE_SAFE = "site-safe";
   public static final String RACK_SAFE = "rack-safe";
   public static final String MACHINE_SAFE = "machine-safe";
   public static final String NODE_SAFE = "node-safe";
   protected static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final List HA_STATUS_LIST = Arrays.asList("endangered", "node-safe", "machine-safe", "rack-safe", "site-safe");
   public static final List HA_STATUS_VALID_LIST = Collections.unmodifiableList(Arrays.asList("node-safe", "machine-safe"));
   @SharedState
   protected String serverName;
   @SharedState
   protected int haStatusWaitTimeout;
   @SharedState
   protected String haStatusTarget;
   @SharedState
   protected MutableString startTime;

   public boolean waitForHAStatus() throws Exception {
      do {
         MBeanServer domainRuntimeServer = ManagementService.getDomainRuntimeMBeanServer(kernelId);
         ObjectName name = new ObjectName("Coherence:type=Service,member=" + this.serverName + ",*");
         Set serviceMBeans = domainRuntimeServer.queryMBeans(name, (QueryExp)null);
         Set serviceMBeans = serviceMBeans == null ? new HashSet() : serviceMBeans;
         this.debug("The number of Coherence Services running/stopped at this point is " + ((Set)serviceMBeans).size());
         List nonRestartableServices = new ArrayList();
         Iterator var5 = ((Set)serviceMBeans).iterator();

         while(var5.hasNext()) {
            ObjectInstance service = (ObjectInstance)var5.next();
            ObjectName objectName = service.getObjectName();
            String haStatus = (String)domainRuntimeServer.getAttribute(objectName, "StatusHA");
            haStatus = haStatus == null ? "n/a" : haStatus.toLowerCase();
            if (!haStatus.equals("n/a")) {
               int currentHaStatusIndex = HA_STATUS_LIST.indexOf(haStatus);
               int targetHaStatusIndex = HA_STATUS_LIST.indexOf(this.haStatusTarget);
               if (currentHaStatusIndex != -1 && currentHaStatusIndex < targetHaStatusIndex) {
                  nonRestartableServices.add(objectName.toString());
                  this.debug("The Service " + objectName + " has an HA status lower than the required HA Status");
               }
            }
         }

         if (nonRestartableServices.size() == 0) {
            this.debug("There are no Coherence Services which are in danger of losing cache due to a server restart at this point");
            return true;
         }

         Thread.sleep(1000L);
      } while(!this.timedOut());

      return false;
   }

   protected void debug(String message) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug(message);
      }

   }

   private boolean timedOut() {
      long curTime = System.currentTimeMillis();
      long timeoutTime = Long.parseLong(this.startTime.getValue());
      return this.haStatusWaitTimeout > 0 && curTime - timeoutTime > (long)(this.haStatusWaitTimeout * 1000);
   }
}
