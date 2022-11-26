package weblogic.servlet.internal.session;

import java.io.Serializable;
import weblogic.cluster.replication.AsyncReplicatable;

public class AsyncReplicatedSessionData extends ReplicatedSessionData implements AsyncReplicatable {
   private static final long serialVersionUID = -7620574765802976000L;
   private transient ReplicatedSessionChange asyncChange;
   private boolean blockingFlushCall = false;

   public AsyncReplicatedSessionData() {
   }

   public AsyncReplicatedSessionData(String sessionid, SessionContext httpSessionContext) {
      super(sessionid, httpSessionContext, true);
   }

   protected AsyncReplicatedSessionData(String sessionID, SessionContext httpSessionContext, boolean isNew) {
      super(sessionID, httpSessionContext, isNew);
   }

   protected void initializeChange() {
      this.asyncChange = new AsyncReplicatedSessionChange();
   }

   protected ReplicatedSessionChange getSessionChange() {
      return this.asyncChange;
   }

   void syncSession() {
      try {
         super.syncSession();
         if (this.blockingFlushCall) {
            this.getReplicationServices().sync();
            this.blockingFlushCall = false;
         }
      } finally {
         this.postInvalidate();
      }

   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      super.removeInternalAttribute(name);
      if (name.equals("weblogic.authuser")) {
         this.blockingFlushCall = true;
      }

   }

   protected Serializable getUpdateObject() {
      return this;
   }

   public void setQueued() {
      ((AsyncReplicatedSessionChange)this.asyncChange).setQueued();
   }

   public boolean isQueued() {
      return ((AsyncReplicatedSessionChange)this.asyncChange).isQueued();
   }

   public Serializable getBatchedChanges() {
      return this.asyncChange;
   }

   public void commit() {
      ((AsyncReplicatedSessionChange)this.asyncChange).commit();
   }
}
