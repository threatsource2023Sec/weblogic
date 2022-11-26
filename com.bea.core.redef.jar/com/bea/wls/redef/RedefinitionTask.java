package com.bea.wls.redef;

import java.util.HashSet;
import java.util.Set;

public class RedefinitionTask implements Runnable {
   private ClassRedefinitionRuntime _owner;
   private String _moduleName;
   private String[] _classNames;
   private int _candidateCount;
   private int _processedCount = 0;
   private Status _status;
   private long _beginTime;
   private long _endTime;

   public RedefinitionTask(ClassRedefinitionRuntime owner, String moduleName, String[] classNames) {
      this._status = RedefinitionTask.Status.SCHEDULED;
      this._owner = owner;
      this._moduleName = moduleName;
      this._classNames = classNames;
   }

   public int getCandidateClassesCount() {
      return this._candidateCount;
   }

   public int getProcessedClassesCount() {
      return this._processedCount;
   }

   synchronized void updateCandidateClassesCount(int incr) {
      this._candidateCount += incr;
   }

   synchronized void updateProcessedClassesCount(int incr) {
      this._processedCount += incr;
   }

   public Status getStatus() {
      return this._status;
   }

   public void setStatus(Status status) {
      this._status = status;
   }

   public long getBeginTime() {
      return this._beginTime;
   }

   public void setBeginTime(long beginTime) {
      this._beginTime = beginTime;
   }

   public long getEndTime() {
      return this._endTime;
   }

   public void setEndTime(long endTime) {
      this._endTime = endTime;
   }

   public boolean isDeletable() {
      switch (this._status) {
         case FAILED:
         case FINISHED:
         case CANCELLED:
            return true;
         default:
            return false;
      }
   }

   public boolean isRunnable() {
      return this._status == RedefinitionTask.Status.SCHEDULED;
   }

   public boolean isRunning() {
      return this._status == RedefinitionTask.Status.RUNNING;
   }

   private Set getCandidateClasses(String[] classNames) {
      int size = classNames != null ? classNames.length : 0;
      if (size == 0) {
         return null;
      } else {
         Set candidates = new HashSet();
         String[] var4 = classNames;
         int var5 = classNames.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String name = var4[var6];
            candidates.add(name);
         }

         return candidates;
      }
   }

   public void run() {
      try {
         this._owner.redefineClasses(this._moduleName, this.getCandidateClasses(this._classNames), this);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static enum Status {
      SCHEDULED,
      RUNNING,
      CANCELLED,
      FAILED,
      FINISHED;
   }
}
