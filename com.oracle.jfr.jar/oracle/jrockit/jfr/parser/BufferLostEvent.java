package oracle.jrockit.jfr.parser;

import com.oracle.jrockit.jfr.DataType;
import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Transition;
import java.net.URI;
import java.net.URISyntaxException;
import oracle.jrockit.jfr.events.ContentTypeImpl;

class BufferLostEvent extends EventProxy {
   static final ValueData[] struct = new ValueData[2];
   static final EventData eventData;

   public BufferLostEvent(ChunkParser chunkParser, long timestamp, Object[] values) {
      super(chunkParser, 2, timestamp, values);
   }

   public long getProducerId() {
      return 0L;
   }

   protected ProducerData producer() {
      return null;
   }

   protected EventData eventData() {
      return eventData;
   }

   String producerURI() {
      return "http://www.oracle.com/jrockit/jfr/";
   }

   protected ValueData[] valueData() {
      return struct;
   }

   public String getDescription() {
      return "Could not transfer from thread local buffer to global";
   }

   public String getName() {
      return "Buffer Lost";
   }

   public String getPath() {
      return "threadbuffer_lost";
   }

   public FLRStruct getStackTrace() {
      return null;
   }

   public long getStartTime() {
      return 0L;
   }

   public int getThreadId() {
      return (Integer)this.getValue(0);
   }

   public FLRStruct getThread() {
      return (FLRStruct)this.getResolvedValue(0);
   }

   public URI getURI() {
      try {
         return new URI(this.producerURI() + this.getPath());
      } catch (URISyntaxException var2) {
         throw new InternalError();
      }
   }

   public boolean hasStackTrace() {
      return false;
   }

   public boolean hasStartTime() {
      return false;
   }

   public boolean hasThread() {
      return true;
   }

   static {
      try {
         struct[0] = new ValueData(0, "jfr", "_thread", "Event thread", (String)null, (String)null, Transition.None, ContentTypeImpl.OSTHREAD.getType(), ContentTypeImpl.OSTHREAD, 0);
         struct[1] = new ValueData(0, "jfr", "bytes", "Bytes lost", (String)null, (String)null, Transition.None, DataType.U4, ContentTypeImpl.BYTES, 0);
         eventData = new EventData(0, new URI("http://www.oracle.com/jrockit/jfr/"), "jfr", "BufferLost", "Thread buffer data lost", "bufferlost", false, true, false, false, false, 2, struct);
      } catch (InvalidEventDefinitionException var1) {
         throw new RuntimeException(var1);
      } catch (URISyntaxException var2) {
         throw new RuntimeException(var2);
      } catch (InvalidValueException var3) {
         throw new RuntimeException(var3);
      }
   }
}
