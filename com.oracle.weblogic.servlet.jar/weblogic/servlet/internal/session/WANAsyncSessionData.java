package weblogic.servlet.internal.session;

import java.io.Serializable;
import weblogic.cluster.replication.AsyncReplicatable;

public class WANAsyncSessionData extends WANSessionData implements AsyncReplicatable {
   private static final long serialVersionUID = 1539991387938295130L;
   private transient ReplicatedSessionChange asyncChange;
   private boolean blockingFlushCall;

   public WANAsyncSessionData() {
      this.blockingFlushCall = false;
   }

   public WANAsyncSessionData(String sessionid, SessionContext httpCtx) {
      this(sessionid, httpCtx, true);
   }

   protected WANAsyncSessionData(String sessionid, SessionContext httpCtx, boolean isNew) {
      super(sessionid, httpCtx, isNew);
      this.blockingFlushCall = false;
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
