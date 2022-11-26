package weblogic.platform;

public final class GarbageCollectionEvent {
   public static final int GC_COLLECTION_MAJOR = 0;
   public static final int GC_COLLECTION_MINOR = 1;
   private int eventType;

   GarbageCollectionEvent(int eventType) {
      this.eventType = eventType;
   }

   public int getEventType() {
      return this.eventType;
   }
}
