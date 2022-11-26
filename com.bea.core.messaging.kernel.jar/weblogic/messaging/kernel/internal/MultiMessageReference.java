package weblogic.messaging.kernel.internal;

public class MultiMessageReference extends MessageReference {
   private MultiPersistenceHandle persistenceHandle;
   private MultiMessageReference mmrNext;
   private MultiMessageReference mmrPrev;
   private boolean mmrInList;

   MultiMessageReference() {
   }

   public MultiMessageReference(QueueImpl queue, MessageHandle handle, MultiPersistenceHandle persHandle) {
      super(queue, handle);
      if (persHandle != null) {
         persHandle.addMessageReference(this);
      }

   }

   private MultiMessageReference(MultiMessageReference ref) {
      super(ref);
   }

   MessageReference duplicate() {
      return new MultiMessageReference(this);
   }

   public MultiPersistenceHandle getPersistenceHandle() {
      return this.persistenceHandle;
   }

   void setPersistenceHandle(MultiPersistenceHandle handle) {
      this.persistenceHandle = handle;
   }

   void setMMRNext(MultiMessageReference mmr) {
      this.mmrNext = mmr;
   }

   void setMMRPrev(MultiMessageReference mmr) {
      this.mmrPrev = mmr;
   }

   MultiMessageReference getMMRNext() {
      return this.mmrNext;
   }

   MultiMessageReference getMMRPrev() {
      return this.mmrPrev;
   }

   boolean getMMRInList() {
      return this.mmrInList;
   }

   void setMMRInList(boolean b) {
      this.mmrInList = b;
   }
}
