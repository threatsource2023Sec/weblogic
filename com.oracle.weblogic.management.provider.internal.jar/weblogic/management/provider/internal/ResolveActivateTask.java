package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import weblogic.descriptor.internal.DiffConflicts;
import weblogic.management.provider.ResolveTask;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;

class ResolveActivateTask implements ResolveTask {
   private final AuthenticatedSubject user;
   private final EditAccessImpl editAccess;
   private final long id;
   private final boolean stopOnConflict;
   private final long timeout;
   private final long beginTime = System.currentTimeMillis();
   private final CountDownLatch completionLatch = new CountDownLatch(1);
   private volatile int state = 0;
   private volatile long endTime;
   private volatile Exception error = null;
   private String patchDescription = "";
   private Collection conflicts;

   ResolveActivateTask(EditAccessImpl editAccess, AuthenticatedSubject user, long id, boolean stopOnConflict, long timeout) {
      this.editAccess = editAccess;
      this.user = user;
      this.id = id;
      this.stopOnConflict = stopOnConflict;
      this.timeout = timeout;
   }

   public long getTaskId() {
      return this.id;
   }

   public int getState() {
      return this.state;
   }

   public Map getStateOnServers() {
      return Collections.emptyMap();
   }

   public Map getSystemComponentRestartRequired() {
      return Collections.emptyMap();
   }

   public boolean updateServerState(String server, int state) {
      return false;
   }

   public Iterator getChanges() {
      return Collections.emptyIterator();
   }

   public String getUser() {
      return SubjectUtils.getUsername(this.user);
   }

   public Map getFailedServers() {
      return Collections.emptyMap();
   }

   public void addFailedServer(String server, Exception reason) {
   }

   public void waitForTaskCompletion() {
      try {
         this.completionLatch.await(this.timeout, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var2) {
      }

   }

   public void waitForTaskCompletion(long timeout) {
      try {
         this.completionLatch.await(timeout, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var4) {
      }

   }

   public DeploymentRequestTaskRuntimeMBean getDeploymentRequestTaskRuntimeMBean() {
      return null;
   }

   public long getBeginTime() {
      return this.beginTime;
   }

   public long getEndTime() {
      return this.endTime;
   }

   public Exception getError() {
      return this.error;
   }

   public void addDeferredServer(String server, Exception reason) {
   }

   public String getPartitionName() {
      return this.editAccess.getPartitionName();
   }

   public String getEditSessionName() {
      return this.editAccess.getEditSessionName();
   }

   void execute() {
      try {
         this.editAccess.doResolve(this.stopOnConflict, this);
         this.completed();
      } catch (Exception var2) {
         this.failed(var2);
      }

   }

   EditAccessImpl getEditAccess() {
      return this.editAccess;
   }

   long getTimeout() {
      return this.timeout;
   }

   private synchronized void completed() {
      if (this.completionLatch.getCount() != 0L) {
         this.state = 4;
         this.completionLatch.countDown();
      }

      this.endTime = System.currentTimeMillis();
   }

   synchronized void failed(Exception e) {
      if (this.completionLatch.getCount() != 0L) {
         this.error = e;
         this.state = 5;
         this.completionLatch.countDown();
      }

      this.endTime = System.currentTimeMillis();
   }

   public Collection getConflicts() {
      if (this.conflicts == null) {
         return null;
      } else {
         List result = new ArrayList();
         Iterator var2 = this.conflicts.iterator();

         while(var2.hasNext()) {
            DiffConflicts conflict = (DiffConflicts)var2.next();
            result.addAll(conflict.getConflicts());
         }

         return result;
      }
   }

   public String getPatchDescription() {
      return this.patchDescription;
   }

   public void setPatchDescription(String patchDescription) {
      this.patchDescription = patchDescription;
   }

   public void setConflicts(Collection conflicts) {
      this.conflicts = conflicts;
   }
}
