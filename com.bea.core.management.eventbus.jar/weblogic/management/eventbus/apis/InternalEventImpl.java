package weblogic.management.eventbus.apis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InternalEventImpl implements InternalEvent {
   private final InternalEvent.EventType eventType;
   private final Map payload;
   private static AtomicLong nextId = new AtomicLong(0L);
   private final long id;

   public InternalEventImpl(InternalEvent.EventType eventType) {
      this(eventType, (Map)null);
   }

   public InternalEventImpl(InternalEvent.EventType eventType, Map payload) {
      this.eventType = eventType;
      this.payload = payload;
      nextId.compareAndSet(Long.MAX_VALUE, 0L);
      this.id = nextId.incrementAndGet();
   }

   public final InternalEvent.EventType getType() {
      return this.eventType;
   }

   public Map getPayload() {
      return this.payload == null ? null : new HashMap(this.payload);
   }

   public Object getPayloadEntry(String entryName) {
      return this.payload == null ? null : this.payload.get(entryName);
   }

   public long getId() {
      return this.id;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Event Type    : " + this.getType());
      sb.append("\n");
      sb.append("Event ID      : " + this.getId());
      sb.append("\n");
      sb.append("Event instance: " + super.toString());
      sb.append("\n");
      if (this.payload != null) {
         sb.append("Event payload:\n");
         Iterator var2 = this.payload.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            sb.append("" + (String)entry.getKey() + " --> " + entry.getValue());
            sb.append("\n");
         }
      }

      return sb.toString();
   }
}
