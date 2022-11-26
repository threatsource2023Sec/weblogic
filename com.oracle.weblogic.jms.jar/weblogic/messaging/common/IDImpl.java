package weblogic.messaging.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.ID;

public class IDImpl implements ID {
   static final long serialVersionUID = 1531949956170006912L;
   private static final byte WL6_VERSION = 1;
   private static final byte EXTVERSION = 2;
   private static final int SEED_SHIFT = 45;
   private static final long SEED_MASK = 262143L;
   private static final long TIMESTAMP_MASK = 35184372088831L;
   protected long unique;
   protected int counter;

   public IDImpl(IDFactory subSystemIDFactory) {
      subSystemIDFactory.initId(this);
   }

   public IDImpl(long timestamp, int seed, int counter) {
      this.init(timestamp, seed, counter);
   }

   void init(long timestamp, int seed, int counter) {
      this.unique = ((long)seed & 262143L) << 45 | timestamp;
      this.counter = counter;
   }

   public long getTimestamp() {
      return this.unique & 35184372088831L;
   }

   public int getSeed() {
      return (int)(this.unique >> 45);
   }

   public int getCounter() {
      return this.counter;
   }

   public IDImpl() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(2);
      out.writeLong(this.unique);
      out.writeInt(this.counter);
   }

   public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
      byte version = in.readByte();
      if (version == 1) {
         this.unique = in.readLong();
         int seed = in.readInt();
         this.unique |= ((long)seed & 262143L) << 45;
         in.readInt();
         in.readInt();
         this.counter = in.readInt();
      } else {
         if (version != 2) {
            throw MessagingUtilities.versionIOException(version, 1, 2);
         }

         this.unique = in.readLong();
         this.counter = in.readInt();
      }

   }

   public String toString() {
      return "<" + this.unique + "." + this.counter + ">";
   }

   public boolean equals(Object o) {
      IDImpl id = (IDImpl)o;
      return this.counter == id.counter && this.unique == id.unique;
   }

   public int compareTo(Object obj) {
      IDImpl id = (IDImpl)obj;
      if (this.unique < id.unique) {
         return -1;
      } else if (this.unique > id.unique) {
         return 1;
      } else if (this.counter < id.counter) {
         return -1;
      } else {
         return this.counter > id.counter ? 1 : 0;
      }
   }

   public int hashCode() {
      return (int)(this.unique ^ (long)this.counter);
   }
}
