package oracle.jrockit.jfr.parser;

import com.oracle.jrockit.jfr.DataType;
import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Transition;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import oracle.jrockit.jfr.events.ContentTypeImpl;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ChunkParser implements Iterable {
   public static final String EVENT_THREAD_ID = "_thread";
   public static final String START_TIME_ID = "_start";
   public static final String STACKTRACE_ID = "_stacktrace";
   private final FLRInput input;
   private final long chunkStart;
   private final long chunkEnd;
   private long start;
   private long end;
   private long startTicks;
   private long ticksPerSecond;
   private TimeZone timezone;
   private Locale locale;
   private byte[] buffer;
   private char[] cbuffer;
   private int major;
   private int minor;
   private int version;
   final HashMap structs = new HashMap();
   final HashMap eventTypes = new HashMap();
   final HashMap resolvers = new HashMap();
   final HashMap contentDescs = new HashMap();
   final HashMap producers = new HashMap();
   static Attributes empty = new AttributesImpl();

   ChunkParser(FLRInput input) throws IOException, ParseException {
      this.input = input;
      this.chunkStart = input.position();
      this.chunkEnd = this.begin();
   }

   long getChunkStart() {
      return this.chunkStart;
   }

   long getChunkEnd() {
      return this.chunkEnd;
   }

   public List getProducers() {
      ArrayList l = new ArrayList();
      l.addAll(this.producers.values());
      return l;
   }

   public long getStartTimestampMillis() {
      return this.start;
   }

   public long getEndTimestampMillis() {
      return this.end;
   }

   public long getStartTimeStampTicks() {
      return this.startTicks;
   }

   public long ticksToNanos(long ticks) {
      return (long)((double)ticks / ((double)this.ticksPerSecond / 1.0E9));
   }

   public long ticksToMicros(long ticks) {
      return (long)((double)ticks / ((double)this.ticksPerSecond / 1000000.0));
   }

   public long ticksToMillis(long ticks) {
      return (long)((double)ticks / ((double)this.ticksPerSecond / 1000.0));
   }

   public long ticksToSeconds(long ticks) {
      return (long)((double)ticks / (double)this.ticksPerSecond);
   }

   public long getTickFrequency() {
      return this.ticksPerSecond;
   }

   private void move(long chunkpos) throws IOException {
      this.input.position(chunkpos + this.chunkStart);
   }

   private long absolute(long relativepos) {
      return relativepos + this.chunkStart;
   }

   private long begin() throws IOException, ParseException {
      this.move(0L);
      byte[] bytes = new byte[4];
      this.input.get(bytes);
      if (bytes[0] == 70 && bytes[1] == 76 && bytes[2] == 82 && bytes[3] == 0) {
         this.major = this.input.getShort();
         this.minor = this.input.getShort();
         this.version = this.major << 16 | this.minor;
         long desc = this.input.getLong();
         this.move(desc);
         long cend = this.readDescriptors();
         this.start = this.input.getLong();
         this.end = this.input.getLong();
         this.startTicks = this.input.getLong();
         this.ticksPerSecond = this.input.getLong();
         long prevCP = this.input.getLong();
         String loc = this.readUTF();
         String[] lparts = loc.split("_");
         switch (lparts.length) {
            case 1:
               this.locale = new Locale(loc);
               break;
            case 2:
               this.locale = new Locale(lparts[0], lparts[1]);
               break;
            case 3:
               this.locale = new Locale(lparts[0], lparts[1], lparts[2]);
               break;
            default:
               throw new ParseException("Bad locale " + loc);
         }

         int gmtoffset = this.input.getInt();

         try {
            this.timezone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(gmtoffset)[0]);
         } catch (ArrayIndexOutOfBoundsException var12) {
            throw new ParseException("Bad TimeZone " + gmtoffset);
         }

         while(prevCP != 0L) {
            prevCP = this.parseCP(prevCP);
         }

         this.move(16L);
         return cend;
      } else {
         throw new ParseException("Bad file header : " + Arrays.toString(bytes));
      }
   }

   EventProxy nextEvent() throws ParseException, IOException {
      int size = this.input.getInt();
      int id = this.input.getInt();

      long timestamp;
      for(timestamp = this.input.getLong(); id == 1; timestamp = this.input.getLong()) {
         long pos = this.input.position();
         this.input.position(pos + (long)size - 4L - 4L - 8L);
         size = this.input.getInt();
         id = this.input.getInt();
      }

      if (id == 2) {
         Object[] values = this.readStruct(BufferLostEvent.struct);
         return new BufferLostEvent(this, timestamp, values);
      } else if (id == 0) {
         return null;
      } else {
         EventData d = (EventData)this.eventTypes.get(id);
         if (d == null) {
            throw new ParseException("Bad event id " + id);
         } else {
            Object[] values = this.readStruct((ValueData[])((ValueData[])d.getValues()));
            return new EventProxy(this, id, timestamp, values);
         }
      }
   }

   public FLREvent next() throws ParseException, IOException {
      return this.nextEvent();
   }

   final Object[] resolve(int contentTypeID, Number value, long timestamp) {
      ContentTypeResolver r = (ContentTypeResolver)this.resolvers.get(contentTypeID);
      if (r == null) {
         if (this.contentDescs.get(contentTypeID) == null) {
            throw new IllegalArgumentException("bad content type: contentTypeID=" + contentTypeID + " value=" + value + " timestamp=" + timestamp);
         } else {
            return null;
         }
      } else {
         while(r != null && r.timestamp < timestamp) {
            r = r.next;
         }

         while(r != null) {
            Object[] content = (Object[])r.map.get(value);
            if (content != null) {
               return content;
            }

            r = r.next;
         }

         return null;
      }
   }

   public Iterator iterator() {
      return new Iterator() {
         private FLREvent e;

         public boolean hasNext() {
            try {
               this.e = ChunkParser.this.next();
            } catch (Exception var2) {
               throw new RuntimeException(var2);
            }

            return this.e != null;
         }

         public FLREvent next() {
            return this.e;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   void writeXML(ContentHandler h) throws SAXException, IOException, ParseException {
      AttributesImpl a = new AttributesImpl();
      SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd-HHmmssSS");
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "startTime", "jfr:startTime", "", d.format(new Date(this.start)));
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "endTime", "jfr:endTime", "", d.format(new Date(this.end)));
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "startTicks", "jfr:startTicks", "", String.valueOf(this.startTicks));
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "ticksPerMillis", "jfr:ticksPerMillis", "", String.valueOf(this.ticksPerSecond));
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "timezone", "jfr:timezone", "", this.timezone.getID());
      a.addAttribute("http://www.oracle.com/jrockit/jfr/", "locale", "jfr:locale", "", this.locale.toString());
      Iterator i$ = this.producers.values().iterator();

      ProducerData p;
      while(i$.hasNext()) {
         p = (ProducerData)i$.next();
         h.startPrefixMapping(p.namespace, p.uri.toString());
      }

      h.startElement("http://www.oracle.com/jrockit/jfr/", "chunk", "jfr:chunk", a);
      i$ = this.producers.values().iterator();

      while(i$.hasNext()) {
         p = (ProducerData)i$.next();
         AttributesImpl pa = new AttributesImpl();
         pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "id", "jfr:id", "", String.valueOf(p.id));
         pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "name", "jfr:name", "", p.name);
         pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "description", "jfr:description", "", p.desc);
         pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "uri", "jfr:uri", "", p.uri.toString());
         pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "namespace", "jfr:namespace", "", p.namespace);
         h.startElement("http://www.oracle.com/jrockit/jfr/", "producer", "jfr:producer", pa);
         Iterator i$ = p.events.iterator();

         while(i$.hasNext()) {
            EventData e = (EventData)i$.next();
            pa.clear();
            pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "id", "jfr:id", "", String.valueOf(e.getId()));
            pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "name", "jfr:name", "", e.getName());
            pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "description", "jfr:description", "", e.getDescription());
            pa.addAttribute("http://www.oracle.com/jrockit/jfr/", "path", "jfr:path", "", e.getPath());
            h.startElement("http://www.oracle.com/jrockit/jfr/", "event", "jfr:event", pa);
            h.endElement("http://www.oracle.com/jrockit/jfr/", "event", "jfr:event");
         }

         h.endElement("http://www.oracle.com/jrockit/jfr/", "producer", "jfr:producer");
      }

      EventProxy e;
      while((e = this.nextEvent()) != null) {
         e.xmlSnippet(h);
      }

      h.endElement("http://www.oracle.com/jrockit/jfr/", "chunk", "jfr:chunk");
      Iterator i$ = this.producers.values().iterator();

      while(i$.hasNext()) {
         ProducerData p = (ProducerData)i$.next();
         h.endPrefixMapping(p.namespace);
      }

   }

   private long parseCP(long pos) throws IOException, ParseException {
      this.move(pos);
      int size = this.input.getInt();
      long end = this.absolute(pos + (long)size);
      int id = this.input.getInt();
      long time = this.input.getLong();
      if (id != 1) {
         throw new ParseException("Illegal checkpoint event id " + id);
      } else {
         if (this.minor < 7 && this.major == 0) {
            int pid = this.input.getInt();
            ProducerData p = (ProducerData)this.producers.get(pid);
            if (p == null) {
               throw new ParseException("Bad producer id " + pid);
            }
         }

         long pcp = this.input.getLong();

         while(true) {
            ContentTypeDescriptor cd;
            int n;
            do {
               if (this.input.position() >= end) {
                  return pcp;
               }

               int cid = this.input.getInt();
               cd = (ContentTypeDescriptor)this.contentDescs.get(cid);
               if (cd == null) {
                  throw new ParseException("Bad content type in constant pool : " + cid);
               }

               n = this.input.getInt();
            } while(n == 0);

            HashMap map = new HashMap();
            ValueData[] struct = (ValueData[])((ValueData[])cd.getValues());

            int index;
            for(index = 0; index < n; ++index) {
               Number key = (Number)this.readPrimitive(cd.contentType.getType());
               Object[] values = this.readStruct(struct);
               map.put(key, values);
            }

            index = cd.contentType.getOrdinal();
            ContentTypeResolver r = new ContentTypeResolver(cd, map, time, (ContentTypeResolver)this.resolvers.get(index));
            this.resolvers.put(index, r);
         }
      }
   }

   private Object[] readStruct(ValueData[] struct) throws IOException, ParseException {
      int n = struct.length;
      Object[] res = new Object[n];

      for(int i = 0; i < n; ++i) {
         res[i] = this.readValue(struct[i]);
      }

      return res;
   }

   private Object readValue(ValueData d) throws IOException, ParseException {
      DataType t = d.getDataType();
      int len;
      Object[] result;
      switch (t) {
         case ARRAY:
            len = this.input.getInt();
            result = new Object[len];
            DataType at = DataType.values()[d.getInnerType()];

            for(int i = 0; i < len; ++i) {
               result[i] = this.readPrimitive(at);
            }

            return result;
         case STRUCT:
            ProducerData p = (ProducerData)this.producers.get(d.producer);
            ValueData[] struct = (ValueData[])p.structs.get(d.getInnerType());
            return this.readStruct(struct);
         case STRUCTARRAY:
            len = this.input.getInt();
            result = new Object[len];
            ProducerData p = (ProducerData)this.producers.get(d.producer);
            ValueData[] struct = (ValueData[])p.structs.get(d.getInnerType());

            for(int i = 0; i < len; ++i) {
               result[i] = this.readStruct(struct);
            }

            return result;
         default:
            return this.readPrimitive(t);
      }
   }

   private Object readPrimitive(DataType dataType) throws IOException, ParseException {
      switch (dataType) {
         case BOOLEAN:
            return this.input.get() != 0;
         case BYTE:
         case U1:
            return this.input.get();
         case SHORT:
         case U2:
            return this.input.getShort();
         case INTEGER:
         case U4:
            return this.input.getInt();
         case LONG:
         case U8:
            return this.input.getLong();
         case FLOAT:
            return this.input.getFloat();
         case DOUBLE:
            return this.input.getDouble();
         case UTF8:
            return this.readUTF();
         case STRING:
            return this.readString();
         default:
            throw new ParseException("not implemented : " + dataType);
      }
   }

   private long readDescriptors() throws IOException, ParseException {
      long pos = this.input.position();
      int size = this.input.getInt();
      int id = this.input.getInt();
      if (id != 0) {
         throw new ParseException("Bad descriptor section, id=" + id);
      } else {
         int n = this.input.getInt();

         while(n-- > 0) {
            this.readProducer();
         }

         return pos + (long)size;
      }
   }

   private byte[] buffer(int len) {
      if (this.buffer == null || this.buffer.length < len) {
         this.buffer = new byte[len];
      }

      return this.buffer;
   }

   private char[] cbuffer(int len) {
      if (this.cbuffer == null || this.cbuffer.length < len) {
         this.cbuffer = new char[len];
      }

      return this.cbuffer;
   }

   private String readUTF() throws IOException {
      int len = this.input.getShort();
      byte[] buffer = this.buffer(len);
      this.input.get(buffer, 0, len);
      return new String(buffer, 0, len, "UTF-8");
   }

   private String readString() throws IOException {
      int len = this.input.getInt();
      char[] buffer = this.cbuffer(len);

      for(int i = 0; i < len; ++i) {
         buffer[i] = (char)this.input.getShort();
      }

      return new String(buffer, 0, len);
   }

   private DataType getDataType(int index) throws ParseException {
      try {
         return DataType.values()[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ParseException("Illegal data type : " + index);
      }
   }

   private void readProducer() throws IOException, ParseException {
      boolean isOldPID = this.major < 1 && this.minor < 8;
      int pid = this.input.getInt();
      if (!isOldPID && pid == 0) {
         throw new ParseException("Reserved producer id");
      } else {
         String pname = this.readUTF();
         String pdesc = this.readUTF();
         String tmp = this.readUTF();
         URI uri = null;

         try {
            uri = new URI(tmp);
         } catch (URISyntaxException var32) {
            throw new ParseException(var32);
         }

         int jvmpid = isOldPID ? 0 : 1;
         String ns = pid == jvmpid ? "jvm" : "p" + pid;
         int m = this.input.getInt();
         ArrayList relations = null;
         if (this.minor > 5 || this.major > 0) {
            relations = new ArrayList(m);

            while(m-- > 0) {
               relations.add(this.readUTF());
            }

            m = this.input.getInt();
         }

         ArrayList structList = new ArrayList(m);

         String id;
         String name;
         String desc;
         byte datatype;
         int contenttype;
         int arraytype;
         while(m-- > 0) {
            int n = this.input.getInt();
            ValueData[] descs = new ValueData[n];

            for(int i = 0; i < n; ++i) {
               id = this.readUTF();
               name = this.readUTF();
               desc = this.readUTF();
               int ti = this.input.get();
               datatype = this.input.get();
               contenttype = this.input.getInt();
               arraytype = this.input.getInt();
               this.input.getInt();
               Transition tt = Transition.values()[ti];
               DataType dt = this.getDataType(datatype);
               DataType rdt = dt;
               if (dt == DataType.ARRAY) {
                  try {
                     rdt = DataType.values()[arraytype];
                  } catch (ArrayIndexOutOfBoundsException var31) {
                     throw new ParseException("Invalid inner type " + arraytype);
                  }
               }

               ContentTypeImpl ct = ContentTypeImpl.getBuiltIn(contenttype);
               if (ct != null && !ct.isCompatible(rdt)) {
                  throw new ParseException("Incompatible types found: " + rdt + " != " + ct);
               }

               if (ct == null) {
                  ct = new ContentTypeImpl(contenttype, (DataType)null, (String)null) {
                     private ContentTypeImpl ct;

                     private ContentTypeImpl get() {
                        if (this.ct == null) {
                           this.ct = ((ContentTypeDescriptor)ChunkParser.this.contentDescs.get(this.getOrdinal())).contentType;
                        }

                        return this.ct;
                     }

                     public String getName() {
                        return this.get().getName();
                     }

                     public DataType getType() {
                        return this.get().getType();
                     }

                     public String toString() {
                        return this.get().toString();
                     }
                  };
               }

               String relation = null;
               if (dt.isPrimitive() && arraytype > 0) {
                  relation = (String)relations.get(arraytype - 1);
               }

               try {
                  descs[i] = new ValueData(pid, ns, id, name, desc, relation, tt, dt, ct, arraytype);
               } catch (InvalidValueException var30) {
                  throw new ParseException("Could not read value", var30);
               }
            }

            structList.add(descs);
            this.structs.put(this.structs.size() + 1, descs);
         }

         m = this.input.getInt();
         ArrayList events = new ArrayList(m);

         while(m-- > 0) {
            int eid = this.input.getInt();
            String name = this.readUTF();
            id = this.readUTF();
            name = this.readUTF();
            boolean hasStartTime = this.input.get() != 0;
            boolean hasThread = this.input.get() != 0;
            boolean canHaveStacktrace = this.input.get() != 0;
            boolean isRequestable = this.input.get() != 0;
            arraytype = this.input.getInt();
            this.input.getInt();
            ValueData[] struct = (ValueData[])structList.get(arraytype);
            if (hasStartTime || hasThread || canHaveStacktrace) {
               ArrayList l = new ArrayList(struct.length + 3);

               try {
                  if (hasStartTime) {
                     l.add(new ValueData(pid, ns, "_start", "Start time", "", (String)null, Transition.None, ContentTypeImpl.MILLIS.getType(), ContentTypeImpl.MILLIS, 0));
                  }

                  if (hasThread) {
                     l.add(new ValueData(pid, ns, "_thread", "Event thread", (String)null, "", Transition.None, ContentTypeImpl.OSTHREAD.getType(), ContentTypeImpl.OSTHREAD, 0));
                  }

                  if (canHaveStacktrace) {
                     l.add(new ValueData(pid, ns, "_stacktrace", "Stacktrace", "", (String)null, Transition.None, ContentTypeImpl.STACKTRACE.getType(), ContentTypeImpl.STACKTRACE, 0));
                  }
               } catch (InvalidValueException var33) {
                  throw new ParseException("Could not read event " + name, var33);
               }

               l.addAll(Arrays.asList(struct));
               struct = (ValueData[])l.toArray(new ValueData[l.size()]);
            }

            try {
               EventData d = new EventData(pid, uri, ns, name, id, name, hasStartTime, hasThread, canHaveStacktrace, hasStartTime, isRequestable, eid, struct);
               this.eventTypes.put(eid, d);
               events.add(d);
            } catch (InvalidEventDefinitionException var28) {
               throw new ParseException("Could not read event", var28);
            } catch (InvalidValueException var29) {
               throw new ParseException("Could not read event", var29);
            }
         }

         m = this.input.getInt();
         HashMap contentTypes = new HashMap();
         ProducerData p = new ProducerData(pid, pname, pdesc, uri, ns, events, contentTypes, structList);
         int index = 0;

         while(m-- > 0) {
            int cid = this.input.getInt();
            desc = this.readUTF();
            String cdesc = this.readUTF();
            datatype = this.input.get();
            contenttype = this.input.getInt();
            DataType dt = this.getDataType(datatype);
            ContentTypeImpl ct = ContentTypeImpl.getBuiltIn(cid);
            if (ct == null) {
               ct = new ContentTypeImpl(cid, dt, desc);
            }

            ContentTypeDescriptor d = new ContentTypeDescriptor(ct, cdesc, index++, p, contenttype);
            this.contentDescs.put(cid, d);
            contentTypes.put(cid, d);
         }

         this.producers.put(pid, p);
      }
   }

   static String xmlName(String path, String name) {
      StringBuilder buf = new StringBuilder();
      if (path.length() > 0) {
         buf.append(path);
      } else {
         buf.append(name);
      }

      int i = 0;

      for(int n = buf.length(); i < n; ++i) {
         char c = buf.charAt(i);
         char x = c;
         if (i == 0 && !Character.isUnicodeIdentifierStart(c)) {
            x = '_';
         }

         if (i != 0 && !Character.isUnicodeIdentifierPart(c)) {
            x = '_';
         }

         if (c != x) {
            buf.setCharAt(i, x);
         }
      }

      return buf.toString();
   }
}
