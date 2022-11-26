package weblogic.t3.srvr;

import java.lang.management.ManagementFactory;
import java.security.AccessController;
import javax.annotation.PostConstruct;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.jvnet.hk2.annotations.Service;
import weblogic.health.HealthUtils;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JVMRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.platform.GCListener;
import weblogic.platform.GarbageCollectionEvent;
import weblogic.platform.VM;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.cmm.MemoryNotificationService;

@Service
public class JVMRuntime extends RuntimeMBeanDelegate implements JVMRuntimeMBean, GCListener, MemoryNotificationService {
   private static final long serialVersionUID = -6411120496639444681L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Runtime runtime;
   private static JVMRuntime singleton;
   private boolean jvmThreadDumpvailable = true;

   protected JVMRuntime() throws ManagementException {
      super(ManagementService.getRuntimeAccess(kernelId).getServerName(), ManagementService.getRuntimeAccess(kernelId).getServerRuntime(), true, "JVMuntime");
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setJVMRuntime(this);
      this.runtime = Runtime.getRuntime();
   }

   @PostConstruct
   private void postConstruct() {
      setSingleton(this);
   }

   private static synchronized void setSingleton(JVMRuntime me) {
      if (singleton != null) {
         throw new IllegalStateException("Attempt to double initialize");
      } else {
         singleton = me;
         VM.getVM().addGCListener(singleton);
      }
   }

   /** @deprecated */
   @Deprecated
   public static synchronized JVMRuntime theOne() {
      return singleton;
   }

   public void shutdown() {
      this.runtime.exit(0);
   }

   public void runGC() {
      this.runtime.gc();
   }

   public long getHeapFreeCurrent() {
      return this.runtime.freeMemory();
   }

   public long getHeapSizeCurrent() {
      return this.runtime.totalMemory();
   }

   public long getHeapSizeMax() {
      return this.runtime.maxMemory();
   }

   public String getJavaVersion() {
      return System.getProperty("java.version");
   }

   public String getJavaVendor() {
      return System.getProperty("java.vendor");
   }

   public String getJavaVMVendor() {
      return System.getProperty("java.vm.vendor");
   }

   public String getOSName() {
      return System.getProperty("os.name");
   }

   public String getOSVersion() {
      return System.getProperty("os.version");
   }

   private ThreadDumpGenerator getThreadDumpGenerator() throws MalformedObjectNameException {
      MBeanServer server = ManagementFactory.getPlatformMBeanServer();
      ObjectName name = new ObjectName("com.sun.management", "type", "DiagnosticCommand");
      return (ThreadDumpGenerator)JMX.newMBeanProxy(server, name, ThreadDumpGenerator.class);
   }

   public String getThreadStackDump() {
      try {
         if (this.jvmThreadDumpvailable) {
            return this.getThreadDumpGenerator().threadPrint();
         }
      } catch (Throwable var2) {
         this.jvmThreadDumpvailable = false;
      }

      return VM.getVM().threadDumpAsString(false, false);
   }

   public long getUptime() {
      return System.currentTimeMillis() - ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getActivationTime();
   }

   public int getHeapFreePercent() {
      return this.getHeapSizeMax() == Long.MAX_VALUE ? (int)(this.getHeapFreeCurrent() * 100L / this.getHeapSizeCurrent()) : (int)((this.getHeapSizeMax() - this.getHeapSizeCurrent() + this.getHeapFreeCurrent()) * 100L / this.getHeapSizeMax());
   }

   public synchronized void sendMemoryNotification(int oldPercentFree, int newPercentFree) {
      this._postSet("HeapFreePercent", oldPercentFree, newPercentFree);
   }

   public void onGarbageCollection(GarbageCollectionEvent event) {
      HealthUtils.logAndGetFreeMemoryPercent();
   }

   public interface ThreadDumpGenerator {
      String threadPrint(String... var1);
   }
}
