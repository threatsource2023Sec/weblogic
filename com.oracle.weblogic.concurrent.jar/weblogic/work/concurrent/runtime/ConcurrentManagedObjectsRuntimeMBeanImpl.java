package weblogic.work.concurrent.runtime;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean;
import weblogic.management.runtime.ManagedExecutorServiceRuntimeMBean;
import weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean;
import weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class ConcurrentManagedObjectsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ConcurrentManagedObjectsRuntimeMBean {
   private AtomicInteger runningLongRunningRequests = new AtomicInteger();
   private AtomicInteger runningFactoryThreadCount = new AtomicInteger();
   private AtomicLong rejectedLongRunning = new AtomicLong();
   private AtomicLong rejectedNewThread = new AtomicLong();
   private Set managedExecutorRuntimes = Collections.synchronizedSet(new HashSet());
   private Set managedScheduledExecutorRuntimes = Collections.synchronizedSet(new HashSet());
   private Set mtfRuntimes = Collections.synchronizedSet(new HashSet());

   public ConcurrentManagedObjectsRuntimeMBeanImpl() throws ManagementException {
   }

   public int getRunningLongRunningRequests() {
      return this.runningLongRunningRequests.get();
   }

   public void addLongRunning() {
      this.runningLongRunningRequests.incrementAndGet();
   }

   public void releaseLongRunning() {
      this.runningLongRunningRequests.decrementAndGet();
   }

   public int getRunningThreadsCount() {
      return this.runningFactoryThreadCount.get();
   }

   public void addNewThread() {
      this.runningFactoryThreadCount.incrementAndGet();
   }

   public void releaseNewThread() {
      this.runningFactoryThreadCount.decrementAndGet();
   }

   public long getRejectedLongRunningRequests() {
      return this.rejectedLongRunning.get();
   }

   public void incrementRejectedLongRunning() {
      this.rejectedLongRunning.incrementAndGet();
   }

   public void decrementRejectedLongRunning() {
      this.rejectedLongRunning.decrementAndGet();
   }

   public long getRejectedNewThreadRequests() {
      return this.rejectedNewThread.get();
   }

   public void incrementRejectedNewThread() {
      this.rejectedNewThread.incrementAndGet();
   }

   public void decrementRejectedNewThread() {
      this.rejectedNewThread.decrementAndGet();
   }

   public boolean addManagedExecutorServiceRuntime(ManagedExecutorServiceRuntimeMBean mbean) {
      return this.managedExecutorRuntimes.add(mbean);
   }

   public ManagedExecutorServiceRuntimeMBean[] getManagedExecutorServiceRuntimes() {
      int len = this.managedExecutorRuntimes.size();
      return (ManagedExecutorServiceRuntimeMBean[])((ManagedExecutorServiceRuntimeMBean[])this.managedExecutorRuntimes.toArray(new ManagedExecutorServiceRuntimeMBean[len]));
   }

   public boolean addManagedScheduledExecutorServiceRuntime(ManagedScheduledExecutorServiceRuntimeMBean mbean) {
      return this.managedScheduledExecutorRuntimes.add(mbean);
   }

   public ManagedScheduledExecutorServiceRuntimeMBean[] getManagedScheduledExecutorServiceRuntimes() {
      int len = this.managedScheduledExecutorRuntimes.size();
      return (ManagedScheduledExecutorServiceRuntimeMBean[])((ManagedScheduledExecutorServiceRuntimeMBean[])this.managedScheduledExecutorRuntimes.toArray(new ManagedScheduledExecutorServiceRuntimeMBean[len]));
   }

   public boolean addManagedThreadFactoryRuntime(ManagedThreadFactoryRuntimeMBean mbean) {
      return this.mtfRuntimes.add(mbean);
   }

   public ManagedThreadFactoryRuntimeMBean[] getManagedThreadFactoryRuntimes() {
      int len = this.mtfRuntimes.size();
      return (ManagedThreadFactoryRuntimeMBean[])((ManagedThreadFactoryRuntimeMBean[])this.mtfRuntimes.toArray(new ManagedThreadFactoryRuntimeMBean[len]));
   }
}
