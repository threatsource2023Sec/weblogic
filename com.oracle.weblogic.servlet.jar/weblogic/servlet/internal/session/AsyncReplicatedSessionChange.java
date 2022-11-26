package weblogic.servlet.internal.session;

public class AsyncReplicatedSessionChange extends ReplicatedSessionChange {
   private static final long serialVersionUID = -7566844526102086387L;
   private volatile boolean isQueued = false;

   void clear() {
   }

   public void setQueued() {
      this.isQueued = true;
   }

   public boolean isQueued() {
      return this.isQueued;
   }

   synchronized void commit() {
      this.attributeChanges.clear();
      this.internalChanges.clear();
      this.modified = false;
      this.isQueued = false;
   }
}
