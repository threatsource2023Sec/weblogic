package weblogic.servlet.internal.session;

import java.io.Serializable;

public final class SessionInfo implements Serializable {
   private String monitoringId;
   private long creationTime;
   private long modifiedTime;
   private int inactiveInterval;
   private boolean newSession;

   public SessionInfo(SessionData session) {
      this.monitoringId = session.getMonitoringId();
      this.creationTime = session.getCreationTime();
      this.modifiedTime = session.getLastAccessedTime();
      this.inactiveInterval = session.getMaxInactiveInterval();
      this.newSession = session.isNew();
   }

   public String getMonitoringId() {
      return this.monitoringId;
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public long getLastAccessedTime() {
      return this.modifiedTime;
   }

   public int getMaxInactiveInterval() {
      return this.inactiveInterval;
   }

   public boolean isNew() {
      return this.newSession;
   }

   public int hashCode() {
      return this.monitoringId.hashCode();
   }
}
