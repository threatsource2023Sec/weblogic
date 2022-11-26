package weblogic.health;

public final class MemoryEvent {
   public static final int MEMORY_OK = 0;
   public static final int MEMORY_LOW = 1;
   private int eventType;

   MemoryEvent(int eventType) {
      this.eventType = eventType;
   }

   public int getEventType() {
      return this.eventType;
   }
}
