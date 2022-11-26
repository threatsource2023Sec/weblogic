package oracle.jrockit.jfr.parser;

import com.oracle.jrockit.jfr.DataType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

abstract class AbstractStructProxy implements FLRStruct {
   protected final ChunkParser chunkParser;
   protected final Object[] values;

   public AbstractStructProxy(ChunkParser chunkParser, Object[] values) {
      this.chunkParser = chunkParser;
      this.values = values;
   }

   public Object getValue(int index) {
      return this.values[index];
   }

   private Object resolveValue(Object o, ValueData d) {
      int ct = d.getContentTypeOrdinal();
      if (o != null && ct > 6 && ct != 11) {
         long timestamp = this.timestamp();
         Object[] content = this.chunkParser.resolve(ct, (Number)o, timestamp);
         if (content == null) {
            return null;
         } else {
            ContentTypeDescriptor desc = (ContentTypeDescriptor)this.chunkParser.contentDescs.get(ct);
            return new SubStruct(this.chunkParser, content, desc.producer.id, timestamp, desc.structIndex);
         }
      } else {
         return o;
      }
   }

   public Object getResolvedValue(int index) {
      Object o = this.values[index];
      ValueData d = this.valueData()[index];
      DataType dt = d.getDataType();
      Object[] v;
      int j;
      switch (dt) {
         case ARRAY:
            v = (Object[])((Object[])o);
            Object[] res = new Object[v.length];

            for(j = 0; j < v.length; ++j) {
               res[j] = this.resolveValue(v[j], d);
            }

            return res;
         case STRUCT:
            return new SubStruct(this.chunkParser, (Object[])((Object[])o), this.producer().id, this.timestamp(), d.getInnerType());
         case STRUCTARRAY:
            v = (Object[])((Object[])o);
            FLRStruct[] res = new FLRStruct[v.length];

            for(j = 0; j < v.length; ++j) {
               res[j] = new SubStruct(this.chunkParser, (Object[])((Object[])v[j]), this.producer().id, this.timestamp(), d.getInnerType());
            }

            return res;
         default:
            return this.resolveValue(o, d);
      }
   }

   public List getResolvedValues() {
      int n = this.values.length;
      ArrayList l = new ArrayList(n);

      for(int i = 0; i < n; ++i) {
         l.add(this.getResolvedValue(i));
      }

      return l;
   }

   protected abstract long timestamp();

   protected abstract ProducerData producer();

   protected abstract ValueData[] valueData();

   private Object getValue(String id, boolean resolve) {
      ValueData[] struct = this.valueData();
      int i = 0;
      ValueData[] arr$ = struct;
      int len$ = struct.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueData d = arr$[i$];
         if (d.getId().equals(id)) {
            return resolve ? this.getResolvedValue(i) : this.getValue(i);
         }

         ++i;
      }

      throw new NoSuchElementException(id);
   }

   public FLRValueInfo getValueInfo(int index) {
      return this.valueData()[index];
   }

   public FLRValueInfo getValueInfo(String id) {
      ValueData[] arr$ = this.valueData();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueData d = arr$[i$];
         if (d.getId().equals(id)) {
            return d;
         }
      }

      throw new NoSuchElementException(id);
   }

   public final Object getValue(String id) {
      return this.getValue(id, false);
   }

   public final Object getResolvedValue(String id) {
      return this.getValue(id, true);
   }

   public List getValues() {
      return Arrays.asList(this.values);
   }

   public List getValueInfos() {
      return Arrays.asList(this.valueData());
   }

   String producerURI() {
      return this.producer().uri.toString();
   }

   void xmlSnippet(ContentHandler h) throws SAXException {
      ValueData[] data = this.valueData();
      String uri = this.producerURI();
      int i = 0;
      int n = data.length;
      ValueData[] arr$ = data;
      int len$ = data.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueData d = arr$[i$];
         Object o = this.getResolvedValue(i);
         if (n > 1) {
            h.startElement(uri, d.xmlname, d.qname, ChunkParser.empty);
         }

         Object[] v2;
         Object[] arr$;
         int len$;
         int i$;
         Object e;
         label39:
         switch (d.getDataType()) {
            case ARRAY:
               v2 = (Object[])((Object[])o);
               arr$ = v2;
               len$ = v2.length;
               i$ = 0;

               while(true) {
                  if (i$ >= len$) {
                     break label39;
                  }

                  e = arr$[i$];
                  h.startElement("http://www.oracle.com/jrockit/jfr/", "elem", "jfr:elem", ChunkParser.empty);
                  String s = String.valueOf(e);
                  h.characters(s.toCharArray(), 0, s.length());
                  h.endElement("http://www.oracle.com/jrockit/jfr/", "elem", "jfr:elem");
                  ++i$;
               }
            case STRUCTARRAY:
               v2 = (Object[])((Object[])o);
               arr$ = v2;
               len$ = v2.length;
               i$ = 0;

               while(true) {
                  if (i$ >= len$) {
                     break label39;
                  }

                  e = arr$[i$];
                  AbstractStructProxy s = (AbstractStructProxy)e;
                  h.startElement("http://www.oracle.com/jrockit/jfr/", "elem", "jfr:elem", ChunkParser.empty);
                  s.xmlSnippet(h);
                  h.endElement("http://www.oracle.com/jrockit/jfr/", "elem", "jfr:elem");
                  ++i$;
               }
            default:
               if (o instanceof FLRStruct) {
                  AbstractStructProxy s = (AbstractStructProxy)o;
                  s.xmlSnippet(h);
               } else {
                  String s = String.valueOf(o);
                  h.characters(s.toCharArray(), 0, s.length());
               }
         }

         if (n > 1) {
            h.endElement(uri, d.xmlname, d.qname);
         }

         ++i;
      }

   }

   private void indent(StringBuilder buf, int indent) {
      for(int j = 0; j < indent; ++j) {
         buf.append("   ");
      }

   }

   void print(StringBuilder buf, int indent) {
      ValueData[] data = this.valueData();
      int i = 0;
      int n = data.length;
      if (n > 1) {
         buf.append("{\n");
      }

      ValueData[] arr$ = data;
      int len$ = data.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueData d = arr$[i$];
         Object o = this.getResolvedValue(i);
         if (n > 1) {
            this.indent(buf, indent + 1);
            buf.append(d.getName()).append(" = ");
         }

         Object[] v2;
         label44:
         switch (d.getDataType()) {
            case ARRAY:
               v2 = (Object[])((Object[])o);
               buf.append(Arrays.toString(v2));
               break;
            case STRUCTARRAY:
               v2 = (Object[])((Object[])o);
               int k = 0;
               Object[] arr$ = v2;
               int len$ = v2.length;
               int i$ = 0;

               while(true) {
                  if (i$ >= len$) {
                     break label44;
                  }

                  Object e = arr$[i$];
                  AbstractStructProxy s = (AbstractStructProxy)e;
                  s.print(buf, indent + 1);
                  ++k;
                  if (k < v2.length) {
                     buf.append(",\n");
                     this.indent(buf, indent + 1);
                  }

                  ++i$;
               }
            default:
               if (o instanceof FLRStruct) {
                  AbstractStructProxy s = (AbstractStructProxy)o;
                  s.print(buf, indent + 1);
               } else {
                  buf.append(o);
                  this.describeValue(buf, d, o);
               }
         }

         ++i;
         if (n > 1) {
            buf.append('\n');
         }
      }

      if (n > 1) {
         this.indent(buf, indent);
         buf.append("}");
      }

   }

   private void describeValue(StringBuilder buf, ValueData d, Object o) {
      if (d.getContentTypeImpl().getOrdinal() == 5) {
         buf.append(" (");
         buf.append(this.chunkParser.ticksToMillis(Long.valueOf((Long)o)));
         buf.append(" ms)");
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      this.print(buf, 0);
      return buf.toString();
   }
}
