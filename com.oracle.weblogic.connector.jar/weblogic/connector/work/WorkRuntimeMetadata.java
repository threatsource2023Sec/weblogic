package weblogic.connector.work;

import weblogic.connector.common.Debug;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class WorkRuntimeMetadata {
   public static final String LONG_RUNNING_THREAD_NAME_PREFIX = "[LONG RUNNING]";
   private WorkImpl work;
   private boolean isLongRunning;
   private String appId;
   private String moduleName;
   private String workName;
   private long workId = -1L;
   private AuthenticatedSubject subject;
   private String originalThreadName;

   public WorkImpl getWork() {
      return this.work;
   }

   public void setWork(WorkImpl work) {
      this.work = work;
   }

   public void setEstablishedSubject(AuthenticatedSubject subject) {
      this.subject = subject;
   }

   public AuthenticatedSubject getEstablishedSubject() {
      return this.subject;
   }

   public String getAppId() {
      return this.appId;
   }

   public void setAppId(String appId) {
      this.appId = appId;
   }

   public String getWorkName() {
      return this.workName;
   }

   public void setWorkName(String workName) {
      this.workName = workName;
   }

   public boolean isLongRunning() {
      return this.isLongRunning;
   }

   public void setLongRunning(boolean isLongRunning) {
      this.isLongRunning = isLongRunning;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public void setWorkId(long workId) {
      this.workId = workId;
   }

   public long getWorkId() {
      return this.workId;
   }

   public boolean setThreadNameAsNeeded() {
      if (!this.isLongRunning) {
         return false;
      } else {
         this.originalThreadName = Thread.currentThread().getName();
         String threadname = this.getPreferredThreadname();
         if (Debug.isWorkEventsEnabled()) {
            Debug.workEvent("Will change thread name to '" + threadname + "'");
         }

         Thread.currentThread().setName(threadname);
         return true;
      }
   }

   public void restoreThreadNameAsNeeded() {
      if (this.originalThreadName != null) {
         if (Debug.isWorkEventsEnabled()) {
            Debug.workEvent("Will restore thread name to '" + this.originalThreadName + "'");
         }

         Thread.currentThread().setName(this.originalThreadName);
      }

   }

   public String getPreferredThreadname() {
      StringBuilder sb = new StringBuilder();
      if (this.isLongRunning) {
         sb.append("[LONG RUNNING]");
      }

      if (this.workId != -1L) {
         sb.append("work#" + this.workId + " ");
      } else {
         sb.append("work ");
      }

      if (this.appId != null || this.moduleName != null) {
         sb.append("for " + this.appId + "#" + this.moduleName);
      }

      if (this.workName != null) {
         sb.append("#" + this.workName);
      }

      return sb.toString();
   }
}
