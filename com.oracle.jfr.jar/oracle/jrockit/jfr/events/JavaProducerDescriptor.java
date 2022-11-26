package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.DataType;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import oracle.jrockit.jfr.ProducerDescriptor;
import oracle.jrockit.jfr.StringConstantPool;

public class JavaProducerDescriptor implements ProducerDescriptor {
   private final int id;
   private final List events;
   private final Collection publicEvents;
   private final String name;
   private final String description;
   private final URI uri;
   private final ByteBuffer binaryDescriptor;

   public JavaProducerDescriptor(int id, String name, String descriptor, URI uri, List events, Map pools) {
      this.id = id;
      this.events = events;
      this.name = name;
      this.description = descriptor;
      this.uri = uri;
      this.binaryDescriptor = this.createBinaryDescriptor(pools);
      this.publicEvents = Collections.unmodifiableCollection(events);
   }

   public int getId() {
      return this.id;
   }

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public URI getURI() {
      return this.uri;
   }

   public Collection getEvents() {
      return this.publicEvents;
   }

   public ByteBuffer getBinaryDescriptor() {
      return this.binaryDescriptor;
   }

   public long writeCheckPoint(FileChannel channel, long previous) {
      return previous;
   }

   private ByteBuffer createBinaryDescriptor(Map pools) {
      ByteArrayOutputStream bout = new ByteArrayOutputStream(this.events.size() * 200 + pools.size() * 50);
      DataOutputStream out = new DataOutputStream(bout);

      try {
         out.writeInt(this.id);
         out.writeUTF(this.name);
         out.writeUTF(this.description);
         out.writeUTF(this.uri.toString());
         ArrayList l = new ArrayList();
         HashMap relations = new HashMap();
         int relation = 0;
         Iterator i$ = this.events.iterator();

         int len;
         while(i$.hasNext()) {
            JavaEventDescriptor e = (JavaEventDescriptor)i$.next();
            ValueDescriptor[] arr$ = e.getValues();
            len = arr$.length;

            for(int i$ = 0; i$ < len; ++i$) {
               ValueDescriptor d = arr$[i$];
               String s = d.getRelationKey();
               if (s != null && !relations.containsKey(s)) {
                  ++relation;
                  relations.put(s, relation);
                  l.add(s);
               }
            }
         }

         out.writeInt(relations.size());
         i$ = l.iterator();

         while(i$.hasNext()) {
            String s = (String)i$.next();
            out.writeUTF(s);
         }

         int numDatatypes = 0;
         if (!pools.isEmpty()) {
            ++numDatatypes;
         }

         numDatatypes += this.events.size();
         out.writeInt(numDatatypes);
         Iterator i$ = this.events.iterator();

         while(i$.hasNext()) {
            JavaEventDescriptor e = (JavaEventDescriptor)i$.next();
            len = e.getValues().length;
            out.writeInt(len);
            ValueDescriptor[] arr$ = e.getValues();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               ValueDescriptor d = arr$[i$];
               out.writeUTF(d.getId());
               out.writeUTF(d.getName());
               out.writeUTF(d.getDescription());
               out.write(d.getTransition().value());
               DataType t = d.getDataType();
               int ct = d.getContentTypeOrdinal();
               if (t == DataType.STRING && d.getConstantPool() != null && pools.containsKey(d.getConstantPool())) {
                  t = DataType.INTEGER;
                  ct = ((StringConstantPool)pools.get(d.getConstantPool())).getConstantIndex();
               }

               out.write(t.ordinal());
               out.writeInt(ct);
               String s = d.getRelationKey();
               if (s == null) {
                  out.writeInt(0);
               } else {
                  out.writeInt((Integer)relations.get(s));
               }

               out.writeInt(0);
            }
         }

         if (!pools.isEmpty()) {
            out.writeInt(1);
            out.writeUTF("utf");
            out.writeUTF("String");
            out.writeUTF("");
            out.write(0);
            out.write(DataType.UTF8.ordinal());
            out.writeInt(0);
            out.writeInt(0);
            out.writeInt(0);
         }

         out.writeInt(this.events.size());
         int i = 0;
         Iterator i$ = this.events.iterator();

         while(i$.hasNext()) {
            EventDescriptor d = (JavaEventDescriptor)i$.next();
            out.writeInt(d.getId());
            out.writeUTF(d.getName());
            out.writeUTF(d.getDescription());
            out.writeUTF(d.getPath());
            out.writeBoolean(d.hasStartTime());
            out.writeBoolean(d.hasThread());
            out.writeBoolean(d.hasStackTrace());
            out.writeBoolean(d.isRequestable());
            out.writeInt(i++);
            out.writeInt(0);
         }

         out.writeInt(pools.size());
         i$ = pools.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry e = (Map.Entry)i$.next();
            String name = (String)e.getKey();
            StringConstantPool p = (StringConstantPool)e.getValue();
            out.writeInt(p.getConstantIndex());
            out.writeUTF(name);
            out.writeUTF("");
            out.write(DataType.INTEGER.ordinal());
            out.writeInt(this.events.size());
         }

         out.flush();
         out.close();
         byte[] bytes = bout.toByteArray();
         return ByteBuffer.wrap(bytes);
      } catch (IOException var18) {
         throw (InternalError)(new InternalError("Could not create descriptors")).initCause(var18);
      }
   }
}
