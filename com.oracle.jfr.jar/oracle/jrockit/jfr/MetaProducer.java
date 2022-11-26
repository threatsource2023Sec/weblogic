package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.DataType;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import oracle.jrockit.jfr.events.Bits;
import oracle.jrockit.jfr.events.EventControl;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.settings.EventSetting;

final class MetaProducer implements ProducerDescriptor {
   private static final int EVENTSETTING_SIZE;
   private static final int OVERHEAD_SIZE = 16;
   private final URI uri;
   private final int id;
   private final ArrayList controls = new ArrayList();
   private final Control recordingsEvent;
   private final Control settingsEvent;
   private final ByteBuffer descriptor;
   private final JFRImpl jfr;
   private boolean hasChunk;

   public MetaProducer(JFRImpl jfr) {
      this.jfr = jfr;
      this.id = jfr.nextID();

      try {
         this.uri = new URI("http://www.oracle.com/jrockit/jfr-info/");
      } catch (URISyntaxException var3) {
         throw new InternalError();
      }

      this.recordingsEvent = new Control(jfr.nextID(), "recordings/active", "List of Active Recordings", "");
      this.settingsEvent = new Control(jfr.nextID(), "recordings/settingsChanged", "Event Settings Changed", "");
      this.controls.add(this.recordingsEvent);
      this.controls.add(this.settingsEvent);
      this.descriptor = this.createBinaryDescriptor();
   }

   public void onNewChunk() {
      this.hasChunk = true;
      if (this.recordingsEvent.isEnabled()) {
         int n = 0;
         int size = 24;
         Collection recordings = this.jfr.getRecordings();
         Collection settings = this.jfr.getEventSettings().getSettings();
         Iterator i$ = recordings.iterator();

         while(i$.hasNext()) {
            Recording r = (Recording)i$.next();
            if (!r.isRunning()) {
               i$.remove();
            } else {
               size += DataType.LONG.getSize() + DataType.INTEGER.getSize() + Bits.length(r.getName());
            }
         }

         i$ = settings.iterator();

         while(i$.hasNext()) {
            EventSetting s = (EventSetting)i$.next();
            if (s.isEnabled()) {
               size += EVENTSETTING_SIZE;
               ++n;
            }
         }

         boolean ok = false;
         ByteBuffer buf = this.jfr.getThreadBuffer(size);

         try {
            buf.putInt(size);
            buf.putInt(this.recordingsEvent.getId());
            buf.putLong(this.jfr.counterTime());
            buf.putInt(recordings.size());
            Iterator i$ = recordings.iterator();

            while(i$.hasNext()) {
               Recording r = (Recording)i$.next();
               buf.putLong(r.getId());
               Bits.write(buf, r.getName());
            }

            buf.putInt(n);
            i$ = settings.iterator();

            while(i$.hasNext()) {
               EventSetting s = (EventSetting)i$.next();
               if (s.isEnabled()) {
                  buf.putInt(s.getId());
                  buf.put((byte)(s.isEnabled() ? 1 : 0));
                  buf.put((byte)(s.isStacktraceEnabled() ? 1 : 0));
                  buf.putLong(s.getThreshold());
                  buf.putLong(s.getPeriod());
               }
            }

            ok = true;
         } finally {
            this.jfr.releaseThreadBuffer(buf, ok);
         }
      }

   }

   public void chunkDone() {
      this.hasChunk = false;
   }

   public void settingsChanged(Collection settings, Map old) {
      if (this.settingsEvent.isEnabled() && this.hasChunk) {
         ArrayList changed = new ArrayList(settings.size());
         Iterator i$ = settings.iterator();

         while(true) {
            EventSetting s;
            EventSetting o;
            do {
               if (!i$.hasNext()) {
                  if (!changed.isEmpty()) {
                     int size = 20;
                     size += changed.size() * EVENTSETTING_SIZE;
                     boolean ok = false;
                     ByteBuffer buf = this.jfr.getThreadBuffer(size);

                     try {
                        buf.putInt(size);
                        buf.putInt(this.settingsEvent.getId());
                        buf.putLong(this.jfr.counterTime());
                        buf.putInt(changed.size());
                        Iterator i$ = changed.iterator();

                        while(i$.hasNext()) {
                           EventSetting s = (EventSetting)i$.next();
                           buf.putInt(s.getId());
                           buf.put((byte)(s.isEnabled() ? 1 : 0));
                           buf.put((byte)(s.isStacktraceEnabled() ? 1 : 0));
                           buf.putLong(s.getThreshold());
                           buf.putLong(s.getPeriod());
                        }

                        ok = true;
                     } finally {
                        this.jfr.releaseThreadBuffer(buf, ok);
                     }
                  }

                  return;
               }

               s = (EventSetting)i$.next();
               o = (EventSetting)old.get(s.getId());
            } while(o != null && o.equals(s));

            changed.add(s);
         }
      }
   }

   private void writeField(DataOutputStream out, String field, String name, DataType type, int arrayType) throws IOException {
      out.writeUTF(field);
      out.writeUTF(name);
      out.writeUTF("");
      out.write(0);
      out.write(type.ordinal());
      out.writeInt(0);
      out.writeInt(arrayType);
      out.writeInt(0);
   }

   private void writeField(DataOutputStream out, String field, String name, DataType type) throws IOException {
      this.writeField(out, field, name, type, 0);
   }

   private void writeEvent(DataOutputStream out, Control c, int index) throws IOException {
      out.writeInt(c.getId());
      out.writeUTF(c.getName());
      out.writeUTF(c.getDescription());
      out.writeUTF(c.getPath());
      out.writeBoolean(c.hasStartTime());
      out.writeBoolean(c.hasThread());
      out.writeBoolean(c.hasStackTrace());
      out.writeBoolean(c.isRequestable());
      out.writeInt(index);
      out.writeInt(0);
   }

   private ByteBuffer createBinaryDescriptor() {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(bout);

      try {
         out.writeInt(this.getId());
         out.writeUTF(this.getName());
         out.writeUTF(this.getDescription());
         out.writeUTF(this.getURI().toString());
         out.writeInt(0);
         out.writeInt(4);
         out.writeInt(2);
         this.writeField(out, "id", "Id", DataType.LONG);
         this.writeField(out, "name", "Name", DataType.STRING);
         out.writeInt(5);
         this.writeField(out, "id", "Id", DataType.INTEGER);
         this.writeField(out, "enabled", "Enabled", DataType.BOOLEAN);
         this.writeField(out, "stacktrace", "Stacktrace", DataType.BOOLEAN);
         this.writeField(out, "threshold", "Threshold", DataType.LONG);
         this.writeField(out, "period", "Period", DataType.LONG);
         out.writeInt(2);
         this.writeField(out, "recordings", "Recordings", DataType.STRUCTARRAY, 0);
         this.writeField(out, "settings", "Active Settings", DataType.STRUCTARRAY, 1);
         out.writeInt(1);
         this.writeField(out, "settings", "Changed Settings", DataType.STRUCTARRAY, 1);
         out.writeInt(2);
         this.writeEvent(out, this.recordingsEvent, 2);
         this.writeEvent(out, this.settingsEvent, 3);
         out.writeInt(0);
         out.flush();
         out.close();
         return ByteBuffer.wrap(bout.toByteArray());
      } catch (IOException var4) {
         throw (InternalError)(new InternalError("Could not create descriptors")).initCause(var4);
      }
   }

   public ByteBuffer getBinaryDescriptor() {
      return this.descriptor;
   }

   public String getDescription() {
      return "Information about Recordings and Settings";
   }

   public Collection getControls() {
      return this.controls;
   }

   public Collection getEvents() {
      return this.controls;
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return "JFR Metadata";
   }

   public URI getURI() {
      return this.uri;
   }

   public long writeCheckPoint(FileChannel channel, long previous) throws IOException {
      return previous;
   }

   static {
      EVENTSETTING_SIZE = DataType.INTEGER.getSize() + 2 * DataType.LONG.getSize() + 2 * DataType.BOOLEAN.getSize();
   }

   class Control implements EventControl, EventDescriptor {
      private boolean enabled;
      private final int id;
      private final String path;
      private final String name;
      private final String description;
      private final URI uri;

      public Control(int id, String path, String name, String description) {
         this.id = id;
         this.path = path;
         this.name = name;
         this.description = description;
         this.uri = MetaProducer.this.uri.resolve(path);
      }

      public void apply(EventSetting s) {
         this.setEnabled(s.isEnabled());
      }

      public EventDescriptor getDescriptor() {
         return this;
      }

      public long getPeriod() {
         return 0L;
      }

      public long getThresholdTicks() {
         return 0L;
      }

      public void setEnabled(boolean enabled) {
         this.enabled = enabled;
      }

      public void setPeriod(long period) {
      }

      public void setStackTraceEnabled(boolean stacktraceOn) {
      }

      public void setThreshold(long nanoThreshold) {
      }

      public String getDescription() {
         return this.description;
      }

      public int getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public String getPath() {
         return this.path;
      }

      public long getThreshold() {
         return 0L;
      }

      public URI getURI() {
         return this.uri;
      }

      public boolean hasStackTrace() {
         return false;
      }

      public boolean hasStartTime() {
         return false;
      }

      public boolean hasThread() {
         return false;
      }

      public boolean isEnabled() {
         return this.enabled;
      }

      public boolean isRequestable() {
         return false;
      }

      public boolean isStackTraceEnabled() {
         return false;
      }

      public boolean isTimed() {
         return false;
      }
   }
}
