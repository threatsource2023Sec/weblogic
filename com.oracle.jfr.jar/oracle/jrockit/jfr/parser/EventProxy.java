package oracle.jrockit.jfr.parser;

import java.net.URI;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

class EventProxy extends AbstractStructProxy implements FLREvent {
   final int id;
   final long timestamp;

   public EventProxy(ChunkParser chunkParser, int id, long timestamp, Object[] values) {
      super(chunkParser, values);
      this.id = id;
      this.timestamp = timestamp;
   }

   public FLRStruct getStackTrace() {
      EventData d = (EventData)this.chunkParser.eventTypes.get(this.id);
      if (!d.hasStackTrace()) {
         return null;
      } else {
         int index = 0;
         if (d.hasStartTime()) {
            ++index;
         }

         if (d.hasThread()) {
            ++index;
         }

         return (FLRStruct)this.getResolvedValue(index);
      }
   }

   public long getStartTime() {
      EventData d = (EventData)this.chunkParser.eventTypes.get(this.id);
      return !d.hasStartTime() ? 0L : (Long)this.getResolvedValue(0);
   }

   public int getThreadId() {
      EventData d = (EventData)this.chunkParser.eventTypes.get(this.id);
      if (!d.hasThread()) {
         return 0;
      } else {
         int index = 0;
         if (d.hasStartTime()) {
            ++index;
         }

         return (Integer)this.getValue(index);
      }
   }

   public FLRStruct getThread() {
      EventData d = (EventData)this.chunkParser.eventTypes.get(this.id);
      if (!d.hasThread()) {
         return null;
      } else {
         int index = 0;
         if (d.hasStartTime()) {
            ++index;
         }

         return (FLRStruct)this.getResolvedValue(index);
      }
   }

   protected long timestamp() {
      return this.timestamp;
   }

   protected ValueData[] valueData() {
      return (ValueData[])((ValueData[])((EventData)this.chunkParser.eventTypes.get(this.id)).getValues());
   }

   protected ProducerData producer() {
      return (ProducerData)this.chunkParser.producers.get(((EventData)this.chunkParser.eventTypes.get(this.id)).producer);
   }

   public int getId() {
      return this.id;
   }

   public long getProducerId() {
      return (long)((EventData)this.chunkParser.eventTypes.get(this.id)).producer;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public String getDescription() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).getDescription();
   }

   public String getName() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).getName();
   }

   public String getPath() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).getPath();
   }

   public URI getURI() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).getURI();
   }

   protected EventData eventData() {
      return (EventData)this.chunkParser.eventTypes.get(this.id);
   }

   void xmlSnippet(ContentHandler h) throws SAXException {
      EventData d = this.eventData();
      AttributesImpl a = new AttributesImpl();
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "timestamp", "jfr:timestamp", "", String.valueOf(this.timestamp));
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "id", "jfr:id", "", String.valueOf(this.getId()));
      String uri = this.producerURI();
      h.startElement(uri, d.xmlname, d.qname, a);
      super.xmlSnippet(h);
      h.endElement(uri, d.xmlname, d.qname);
   }

   void print(StringBuilder buf, int indent) {
      buf.append(this.getName() + "@" + this.timestamp + " = ");
      super.print(buf, indent);
   }

   public boolean hasStackTrace() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).hasStackTrace();
   }

   public boolean hasStartTime() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).hasStartTime();
   }

   public boolean hasThread() {
      return ((EventData)this.chunkParser.eventTypes.get(this.id)).hasThread();
   }
}
