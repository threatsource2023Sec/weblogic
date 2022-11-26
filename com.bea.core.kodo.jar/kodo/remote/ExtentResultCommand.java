package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.Extent;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UnsupportedException;

class ExtentResultCommand extends ResultCommand {
   private Class _cls = null;
   private boolean _subs = false;
   private FetchConfiguration _fetch = null;

   ExtentResultCommand() {
      super((byte)15);
   }

   public ExtentResultCommand(long brokerId, Class cls, boolean subs, FetchConfiguration fetch) {
      super((byte)15, brokerId);
      this._cls = cls;
      this._subs = subs;
      this._fetch = fetch;
   }

   protected Result initialize(Broker broker) {
      ServerExtentInfo info = new ServerExtentInfo();
      info.extent = broker.newExtent(this._cls, this._subs);
      info.extent.getFetchConfiguration().copy(this._fetch);
      info.iterator = info.extent.iterator();
      return info;
   }

   protected Iterator iterator(Result rsrc, int startIndex) {
      ServerExtentInfo info = (ServerExtentInfo)rsrc;
      if (info.index > startIndex) {
         throw new InternalException();
      } else {
         while(info.index < startIndex && info.hasNext()) {
            info.next();
         }

         return info;
      }
   }

   protected FetchConfiguration getFetchConfiguration(Result rsrc) {
      return ((ServerExtentInfo)rsrc).extent.getFetchConfiguration();
   }

   protected void read(ObjectInput in) throws Exception {
      super.read(in);
      if (this.getInitialize()) {
         this._cls = (Class)in.readObject();
         this._subs = in.readBoolean();
         this._fetch = (FetchConfiguration)in.readObject();
      }

   }

   protected void write(ObjectOutput out) throws Exception {
      super.write(out);
      if (this.getInitialize()) {
         out.writeObject(this._cls);
         out.writeBoolean(this._subs);
         out.writeObject(this._fetch);
      }

   }

   private static class ServerExtentInfo implements Iterator, Result {
      public int index;
      public Extent extent;
      public Iterator iterator;

      private ServerExtentInfo() {
         this.index = 0;
         this.extent = null;
         this.iterator = null;
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public Object next() {
         Object next = this.iterator.next();
         ++this.index;
         return next;
      }

      public void remove() {
         throw new UnsupportedException();
      }

      public int size() {
         throw new InternalException();
      }

      public void close() {
         ImplHelper.close(this.iterator);
      }

      // $FF: synthetic method
      ServerExtentInfo(Object x0) {
         this();
      }
   }
}
