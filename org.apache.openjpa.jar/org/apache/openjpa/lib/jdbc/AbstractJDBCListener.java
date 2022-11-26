package org.apache.openjpa.lib.jdbc;

public class AbstractJDBCListener implements JDBCListener {
   protected void eventOccurred(JDBCEvent event) {
   }

   public void beforePrepareStatement(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterPrepareStatement(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void beforeCreateStatement(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterCreateStatement(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void beforeExecuteStatement(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterExecuteStatement(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void beforeCommit(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterCommit(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void beforeRollback(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterRollback(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void beforeReturn(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterReturn(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void afterConnect(JDBCEvent event) {
      this.eventOccurred(event);
   }

   public void beforeClose(JDBCEvent event) {
      this.eventOccurred(event);
   }
}
