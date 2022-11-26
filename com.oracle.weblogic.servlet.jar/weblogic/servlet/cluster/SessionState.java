package weblogic.servlet.cluster;

import java.io.Serializable;

public final class SessionState {
   private final Serializable state;
   private final int maxInactiveInterval;
   private final long sessionCreationTime;
   private final long accessTime;

   public SessionState(Serializable state, int maxInInactiveInterval, long sessionCreationTime, long accessTime) {
      this.state = state;
      this.maxInactiveInterval = maxInInactiveInterval;
      this.sessionCreationTime = sessionCreationTime;
      this.accessTime = accessTime;
   }

   public Serializable getSessionState() {
      return this.state;
   }

   public int getMaxAnInactiveInterval() {
      return this.maxInactiveInterval;
   }

   public long getSessionCreationTime() {
      return this.sessionCreationTime;
   }

   public long getAccessTime() {
      return this.accessTime;
   }
}
