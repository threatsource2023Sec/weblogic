package org.glassfish.hk2.runlevel;

public class CurrentlyRunningException extends RuntimeException {
   private static final long serialVersionUID = -1712057070339111837L;
   private transient RunLevelFuture currentJob;

   public CurrentlyRunningException() {
   }

   public CurrentlyRunningException(RunLevelFuture runLevelFuture) {
      this.currentJob = runLevelFuture;
   }

   public RunLevelFuture getCurrentJob() {
      return this.currentJob;
   }
}
