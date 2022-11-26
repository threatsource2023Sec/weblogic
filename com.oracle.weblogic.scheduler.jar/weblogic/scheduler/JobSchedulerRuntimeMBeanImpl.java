package weblogic.scheduler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JobRuntimeMBean;
import weblogic.management.runtime.JobSchedulerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.utils.RuntimeGeneratorService;
import weblogic.timers.RuntimeDomainSelector;

public class JobSchedulerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JobSchedulerRuntimeMBean {
   private static final boolean DEBUG = false;
   private static HashMap runtimeMBeanMap = new HashMap();
   private LinkedHashMap jobMap = new JobHashMap();
   private final TimerBasis timerBasis;
   private final String domainID;

   static void init(RuntimeMBean parent) throws ManagementException {
      synchronized(runtimeMBeanMap) {
         runtimeMBeanMap.put("weblogic.timers.defaultDomain", new JobSchedulerRuntimeMBeanImpl(parent, TimerMaster.getInstance(), "weblogic.timers.defaultDomain"));
      }
   }

   static JobSchedulerRuntimeMBeanImpl init(RuntimeMBean parent, TimerMaster timerMaster) throws ManagementException {
      String domainId = RuntimeDomainSelector.getDomain();
      synchronized(runtimeMBeanMap) {
         JobSchedulerRuntimeMBeanImpl newMBean = new JobSchedulerRuntimeMBeanImpl(parent, timerMaster, domainId);
         runtimeMBeanMap.put(domainId, newMBean);
         return newMBean;
      }
   }

   private JobSchedulerRuntimeMBeanImpl(RuntimeMBean parent, TimerMaster timerMaster, String domainID) throws ManagementException {
      super("JobSchedulerRuntime", parent, true);
      this.timerBasis = timerMaster.getTimerBasis();
      this.domainID = domainID;
   }

   public static JobSchedulerRuntimeMBeanImpl getInstance() {
      return getInstance(true);
   }

   public static JobSchedulerRuntimeMBeanImpl getInstance(boolean defaultDomain) {
      String domainId = defaultDomain ? "weblogic.timers.defaultDomain" : RuntimeDomainSelector.getDomain();
      synchronized(runtimeMBeanMap) {
         return (JobSchedulerRuntimeMBeanImpl)runtimeMBeanMap.get(domainId);
      }
   }

   public void unregister() throws ManagementException {
      synchronized(runtimeMBeanMap) {
         runtimeMBeanMap.remove(this.domainID);
      }

      super.unregister();
   }

   public synchronized JobRuntimeMBean getJob(String id) {
      JobRuntimeMBeanImpl runtime = (JobRuntimeMBeanImpl)this.jobMap.get(id);
      if (runtime != null) {
         return runtime;
      } else {
         try {
            JobRuntimeMBean mbean = new JobRuntimeMBeanImpl(this, id, (String)null, -1L, this.timerBasis);
            this.jobMap.put(id, mbean);
            return mbean;
         } catch (ManagementException var4) {
            return null;
         }
      }
   }

   public synchronized JobRuntimeMBean[] getExecutedJobs() {
      JobRuntimeMBean[] beans = new JobRuntimeMBean[this.jobMap.size()];
      this.jobMap.values().toArray(beans);
      return beans;
   }

   synchronized void timerExecuted(String id, String description, long interval) {
      JobRuntimeMBeanImpl runtime = (JobRuntimeMBeanImpl)this.jobMap.get(id);
      if (runtime != null) {
         runtime.update();
      } else {
         try {
            this.jobMap.put(id, new JobRuntimeMBeanImpl(this, id, description, interval, this.timerBasis));
         } catch (ManagementException var7) {
         }
      }

   }

   @Service
   private static class RuntimeGeneratorServiceImpl implements RuntimeGeneratorService {
      public JobSchedulerRuntimeMBean createJobSchedulerRuntimeMBean() {
         return JobSchedulerRuntimeMBeanImpl.getInstance();
      }
   }

   private static final class JobHashMap extends LinkedHashMap {
      private static final int JOB_MBEAN_SIZE = initProperty("weblogic.scheduler.JobRuntimeMBeanPoolSize", 50);

      JobHashMap() {
         super((JOB_MBEAN_SIZE + 1) / 2, 2.0F, true);
      }

      private static int initProperty(String name, int defaultValue) {
         try {
            return Integer.getInteger(name, defaultValue);
         } catch (SecurityException var3) {
            return defaultValue;
         } catch (NumberFormatException var4) {
            return defaultValue;
         }
      }

      protected boolean removeEldestEntry(Map.Entry eldest) {
         if (JOB_MBEAN_SIZE > 0 && this.size() > JOB_MBEAN_SIZE) {
            JobRuntimeMBeanImpl mbean = (JobRuntimeMBeanImpl)eldest.getValue();

            try {
               mbean.unregister();
            } catch (ManagementException var4) {
            }

            return true;
         } else {
            return false;
         }
      }

      private static void p(String str) {
         System.out.println("[JobRuntimeMBean] " + str);
      }
   }
}
