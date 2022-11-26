package weblogic.ejb.container.monitoring;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.MessageDrivenControlEJBRuntimeMBean;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;

public final class MessageDrivenControlEJBRuntimeMBeanImpl extends DomainRuntimeMBeanDelegate implements MessageDrivenControlEJBRuntimeMBean {
   boolean verbose = false;
   long CACHING_STUB_SVUID = 8673161367344012623L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MessageDrivenControlEJBRuntimeMBeanImpl() throws ManagementException {
      super("MessageDrivenControlEJBRuntime");
   }

   public boolean suspendMDBs(String ejbName, String applicationName) {
      boolean success = true;
      ServerRuntimeMBean[] var4 = this.getServerRuntimes();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerRuntimeMBean server = var4[var6];
         Iterator var8 = this.getMessageDrivenRuntimes(server).iterator();

         while(var8.hasNext()) {
            MessageDrivenEJBRuntimeMBean mdbr = (MessageDrivenEJBRuntimeMBean)var8.next();
            if (ejbName.equals(mdbr.getEJBName()) && applicationName.equals(mdbr.getApplicationName())) {
               try {
                  success &= mdbr.suspend();
               } catch (Exception var11) {
                  var11.printStackTrace();
               }
            }
         }
      }

      return success;
   }

   public boolean resumeMDBs(String ejbName, String applicationName) {
      boolean success = true;
      ServerRuntimeMBean[] var4 = this.getServerRuntimes();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerRuntimeMBean server = var4[var6];
         Iterator var8 = this.getMessageDrivenRuntimes(server).iterator();

         while(var8.hasNext()) {
            MessageDrivenEJBRuntimeMBean mdbr = (MessageDrivenEJBRuntimeMBean)var8.next();
            if (ejbName.equals(mdbr.getEJBName()) && applicationName.equals(mdbr.getApplicationName())) {
               try {
                  success &= mdbr.resume();
               } catch (Exception var11) {
                  var11.printStackTrace();
               }
            }
         }
      }

      return success;
   }

   public boolean printMDBStatus(String ejbName, String applicationName) {
      boolean success = true;
      ServerRuntimeMBean[] var4 = this.getServerRuntimes();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerRuntimeMBean server = var4[var6];
         Iterator var8 = this.getMessageDrivenRuntimes(server).iterator();

         while(var8.hasNext()) {
            MessageDrivenEJBRuntimeMBean mdbr = (MessageDrivenEJBRuntimeMBean)var8.next();
            if (ejbName.equals(mdbr.getEJBName()) && applicationName.equals(mdbr.getApplicationName()) && this.verbose) {
               Debug.say("printMDBStatus ejbName=" + mdbr.getEJBName() + "; MDBStatus=" + mdbr.getMDBStatus());
            }
         }
      }

      return success;
   }

   private Set getMessageDrivenRuntimes(ServerRuntimeMBean serverRuntime) {
      Set allBeans = new HashSet(5);
      ApplicationRuntimeMBean[] appRuntimes = serverRuntime.getApplicationRuntimes();
      if (appRuntimes != null) {
         ApplicationRuntimeMBean[] var4 = appRuntimes;
         int var5 = appRuntimes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ApplicationRuntimeMBean appRuntime = var4[var6];
            ComponentRuntimeMBean[] compRuntimes = appRuntime.getComponentRuntimes();
            if (compRuntimes != null) {
               ComponentRuntimeMBean[] var9 = compRuntimes;
               int var10 = compRuntimes.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  ComponentRuntimeMBean compRuntime = var9[var11];
                  if (compRuntime instanceof MessageDrivenEJBRuntimeMBean) {
                     allBeans.add((MessageDrivenEJBRuntimeMBean)compRuntime);
                  }
               }
            }
         }
      }

      return allBeans;
   }

   private ServerRuntimeMBean[] getServerRuntimes() {
      return ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().getServerRuntimes();
   }
}
