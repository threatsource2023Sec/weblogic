package weblogic.messaging.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.messaging.MessageID;

public abstract class MessageIDImpl implements Externalizable, Comparable, MessageID {
   static final long serialVersionUID = -1173635685896143247L;
   private static final byte MINVERSION = 1;
   private static final byte WL60_VERSION = 1;
   private static final byte WL61_VERSION = 11;
   private static final byte WL81_VERSION = 12;
   private static final byte MAXVERSION = 12;
   private static final int VERSION_MASK = 63;
   private static final int HAS_DIFFERENTIATOR = 64;
   protected int seed;
   protected long timestamp;
   protected int counter;
   protected int differentiator;

   public MessageIDImpl(MessageIDFactory subSystemMessageIDFactory) {
      subSystemMessageIDFactory.initMessageId(this);
   }

   public MessageIDImpl(int seed, long timestamp, int counter) {
      this.seed = seed;
      this.timestamp = timestamp;
      this.counter = counter;
   }

   public MessageIDImpl(MessageIDImpl messageId, int differentiator) {
      this.seed = messageId.seed;
      this.timestamp = messageId.timestamp;
      this.counter = messageId.counter;
      this.differentiator = differentiator;
   }

   void init(int seed, long timestamp, int counter) {
      this.seed = seed;
      this.timestamp = timestamp;
      this.counter = counter;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setDifferentiator(int differentiator) {
      this.differentiator = differentiator;
   }

   public int getDifferentiator() {
      return this.differentiator;
   }

   public MessageIDImpl() {
   }

   private int getStreamVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)oo).getPeerInfo();
         if (peerInfo.compareTo(PeerInfo.VERSION_81) < 0) {
            return 11;
         }
      }

      return 12;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getStreamVersion(out);
      if (version >= 12 && this.differentiator != 0) {
         version |= 64;
      }

      out.writeByte((byte)version);
      out.writeLong(this.timestamp);
      out.writeInt(this.counter);
      out.writeInt(this.seed);
      if ((version & 64) != 0) {
         out.writeInt(this.differentiator);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readByte();
      int version = flags & 63;
      if (version != 12 && version != 11) {
         if (version != 1) {
            throw MessagingUtilities.versionIOException(version, 1, 12);
         }

         this.timestamp = in.readLong();
         this.counter = in.readInt();
         in.readInt();
         this.seed = in.readInt();
      } else {
         this.timestamp = in.readLong();
         this.counter = in.readInt();
         this.seed = in.readInt();
         if ((flags & 64) != 0) {
            this.differentiator = in.readInt();
         }
      }

   }

   public boolean equals(Object o) {
      MessageIDImpl id = (MessageIDImpl)o;
      return this.timestamp == id.timestamp && this.counter == id.counter && this.seed == id.seed;
   }

   public boolean differentiatedEquals(Object o) {
      return this.equals(o) && this.differentiator == ((MessageIDImpl)o).differentiator;
   }

   public int compare(MessageIDImpl id) {
      if (this.timestamp > id.timestamp) {
         return 1;
      } else if (this.timestamp < id.timestamp) {
         return -1;
      } else if (this.counter > id.counter) {
         return 1;
      } else if (this.counter < id.counter) {
         return -1;
      } else if (this.seed > id.seed) {
         return 1;
      } else if (this.seed < id.seed) {
         return -1;
      } else if (this.differentiator > id.differentiator) {
         return 1;
      } else {
         return this.differentiator < id.differentiator ? -1 : 0;
      }
   }

   public int compareTime(MessageIDImpl id) {
      if (this.timestamp < id.timestamp) {
         return -1;
      } else if (this.timestamp > id.timestamp) {
         return 1;
      } else if (this.counter < id.counter) {
         return -1;
      } else {
         return this.counter > id.counter ? 1 : 0;
      }
   }

   public int compareTo(Object o) {
      try {
         return this.compare((MessageIDImpl)o);
      } catch (ClassCastException var3) {
         return -1;
      }
   }

   public String toString() {
      return "<" + this.seed + "." + this.timestamp + "." + this.counter + ">";
   }

   public int hashCode() {
      return (int)((long)this.seed ^ this.timestamp ^ (long)this.counter);
   }

   public int differentiatedHashCode() {
      return this.hashCode() ^ this.differentiator;
   }
}
