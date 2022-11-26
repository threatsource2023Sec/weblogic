package weblogic.utils.cmm.serverservice;

import com.oracle.cmm.agent.CMMAgent;
import com.oracle.cmm.agent.CMMAgentFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.PlatformManagedObject;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServiceFailureException;
import weblogic.utils.cmm.MemoryPressureListener;
import weblogic.utils.cmm.internal.MemoryPressureServiceImpl;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
public class CMMJMXVerificationService {
   private static String HOT_SPOT_MXBEAN_CLASS_NAME = "jdk.management.cmm.SystemResourcePressureMXBean";
   private static String CMM_DOMAIN;
   private static final String CMM_DOMAIN_VARIABLE = "weblogic.cmm.mbean.domain";
   private static String CMM_TYPE;
   private static final String CMM_TYPE_VARIABLE = "weblogic.cmm.mbean.type";
   private static String CMM_TYPE_VALUE;
   private static final String CMM_TYPE_VALUE_VARIABLE = "weblogic.cmm.mbean.type.value";
   private static String CMM_ATTRIBUTE_NAME;
   private static final String CMM_ATTRIBUTE_NAME_VARIABLE = "weblogic.cmm.mbean.attribute";
   private static final Class HOT_SPOT_CLASS = getHotSpotClass();
   private static final ObjectName OBJECT_NAME;
   @Inject
   private MemoryPressureServiceImpl memoryPressureService;

   private static Class getHotSpotClass() {
      return (Class)AccessController.doPrivileged(new PrivilegedAction() {
         public Class run() {
            ClassLoader loader = ClassLoader.getSystemClassLoader();

            try {
               return loader.loadClass(CMMJMXVerificationService.HOT_SPOT_MXBEAN_CLASS_NAME);
            } catch (Exception var3) {
               return null;
            }
         }
      });
   }

   private CMMJMXVerificationService() {
   }

   private static ObjectName getMemoryPressureObjectName() {
      Hashtable kvPair = new Hashtable();
      kvPair.put(CMM_TYPE, CMM_TYPE_VALUE);

      try {
         return new ObjectName(CMM_DOMAIN, kvPair);
      } catch (MalformedObjectNameException var2) {
         throw new RuntimeException(var2);
      }
   }

   private static int getPressure(MBeanServer jmxServer) {
      try {
         return (Integer)jmxServer.getAttribute(OBJECT_NAME, CMM_ATTRIBUTE_NAME);
      } catch (AttributeNotFoundException var2) {
         throw new RuntimeException(var2);
      } catch (InstanceNotFoundException var3) {
         throw new RuntimeException(var3);
      } catch (MBeanException var4) {
         throw new RuntimeException(var4);
      } catch (ReflectionException var5) {
         throw new RuntimeException(var5);
      }
   }

   @PostConstruct
   private void postConstruct() throws ServiceFailureException {
      MBeanServer jmxServer = ManagementFactory.getPlatformMBeanServer();
      if (!jmxServer.isRegistered(OBJECT_NAME)) {
         if (HOT_SPOT_CLASS != null) {
            PlatformManagedObject pmo = null;

            try {
               pmo = ManagementFactory.getPlatformMXBean(HOT_SPOT_CLASS);
            } catch (IllegalArgumentException var9) {
               CMMAgent agent = CMMAgentFactory.getInstance().findOrCreateAgent();
               agent.addCMMMBeanToServer(jmxServer);
            }

            if (pmo != null) {
               try {
                  jmxServer.registerMBean(pmo, OBJECT_NAME);
               } catch (Exception var8) {
                  throw new ServiceFailureException(var8);
               }
            }
         } else {
            CMMAgent agent = CMMAgentFactory.getInstance().findOrCreateAgent();
            agent.addCMMMBeanToServer(jmxServer);
         }
      }

      CMMChangeListener listener = new CMMChangeListener(this.memoryPressureService);
      synchronized(listener) {
         try {
            jmxServer.addNotificationListener(OBJECT_NAME, listener, (NotificationFilter)null, (Object)null);
         } catch (InstanceNotFoundException var6) {
            throw new ServiceFailureException(var6);
         }

         listener.initialize(getPressure(jmxServer));
      }
   }

   static {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            CMMJMXVerificationService.CMM_DOMAIN = System.getProperty("weblogic.cmm.mbean.domain", "com.oracle.management");
            CMMJMXVerificationService.CMM_TYPE = System.getProperty("weblogic.cmm.mbean.type", "type");
            CMMJMXVerificationService.CMM_TYPE_VALUE = System.getProperty("weblogic.cmm.mbean.type.value", "ResourcePressureMBean");
            CMMJMXVerificationService.CMM_ATTRIBUTE_NAME = System.getProperty("weblogic.cmm.mbean.attribute", "MemoryPressure");
            return null;
         }
      });
      OBJECT_NAME = getMemoryPressureObjectName();
   }

   private static class GCWork implements Runnable {
      private GCWork() {
      }

      public void run() {
         System.gc();
      }

      // $FF: synthetic method
      GCWork(Object x0) {
         this();
      }
   }

   private static class CMMWork implements Runnable {
      private final CMMChangeListener parent;
      private final MemoryPressureServiceImpl memoryPressureService;
      private final List queue;

      private CMMWork(CMMChangeListener parent, MemoryPressureServiceImpl memoryPressureService, List queue) {
         this.parent = parent;
         this.memoryPressureService = memoryPressureService;
         this.queue = queue;
      }

      private void internalRun(int job) {
         List listeners = this.memoryPressureService.getCurrentListeners(job);
         if (listeners != null) {
            Iterator var3 = listeners.iterator();

            while(var3.hasNext()) {
               MemoryPressureListener listener = (MemoryPressureListener)var3.next();

               try {
                  listener.handleCMMLevel(job);
               } catch (Throwable var6) {
               }
            }

         }
      }

      public void run() {
         while(true) {
            int job;
            synchronized(this.parent) {
               if (this.queue.isEmpty()) {
                  this.parent.workDone();
                  return;
               }

               job = (Integer)this.queue.remove(0);
            }

            this.internalRun(job);
         }
      }

      // $FF: synthetic method
      CMMWork(CMMChangeListener x0, MemoryPressureServiceImpl x1, List x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class CMMChangeListener implements NotificationListener {
      private static final GCWork GCWORK = new GCWork();
      private final WorkManager defaultWorkManager = WorkManagerFactory.getInstance().getDefault();
      private final MemoryPressureServiceImpl memoryPressureService;
      private boolean running = false;
      private final List queue = new LinkedList();

      public CMMChangeListener(MemoryPressureServiceImpl memoryPressureService) {
         this.memoryPressureService = memoryPressureService;
      }

      private void initialize(int initialValue) {
         synchronized(this) {
            this.doWork(initialValue);
         }
      }

      public synchronized void handleNotification(Notification notification, Object handback) {
         if (notification != null) {
            if (notification instanceof AttributeChangeNotification) {
               AttributeChangeNotification acn = (AttributeChangeNotification)notification;
               int oldLevel = (Integer)acn.getOldValue();
               int nextLevel = (Integer)acn.getNewValue();
               this.doWork(nextLevel);
            }
         }
      }

      private void doWork(int nextLevel) {
         this.queue.add(nextLevel);
         if (!this.running) {
            CMMWork worker = new CMMWork(this, this.memoryPressureService, this.queue);
            this.running = true;
            this.defaultWorkManager.schedule(worker);
         }
      }

      private void workDone() {
         this.running = false;
         this.defaultWorkManager.schedule(GCWORK);
      }
   }
}
