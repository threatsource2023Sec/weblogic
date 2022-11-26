package org.apache.openjpa.event;

public abstract class AbstractLifecycleListener implements LifecycleListener {
   protected void eventOccurred(LifecycleEvent event) {
   }

   public void beforePersist(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterPersist(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeClear(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterClear(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterLoad(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeDelete(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterDelete(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeStore(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterStore(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeDirty(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterDirty(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeDirtyFlushed(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterDirtyFlushed(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterRefresh(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeDetach(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterDetach(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void beforeAttach(LifecycleEvent event) {
      this.eventOccurred(event);
   }

   public void afterAttach(LifecycleEvent event) {
      this.eventOccurred(event);
   }
}
