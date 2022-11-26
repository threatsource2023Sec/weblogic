package weblogic.diagnostics.archive.wlstore;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Snapshot implements Externalizable {
   static final long serialVersionUID = 2501916419601393958L;
   private long timestamp;
   private Collection data;

   public Snapshot() {
   }

   public Snapshot(long timestamp, Collection data) {
      this.timestamp = timestamp;
      this.data = data;
   }

   Collection getData() {
      return this.data;
   }

   long getTimestamp() {
      return this.timestamp;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(this.timestamp);
      out.writeInt(this.data.size());
      Iterator it = this.data.iterator();

      while(it.hasNext()) {
         Object obj = it.next();
         out.writeObject(obj);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.timestamp = in.readLong();
      int size = in.readInt();
      this.data = new ArrayList();

      for(int i = 0; i < size; ++i) {
         this.data.add(in.readObject());
      }

   }
}
