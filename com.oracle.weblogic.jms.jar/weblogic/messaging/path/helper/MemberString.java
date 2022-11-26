package weblogic.messaging.path.helper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.messaging.path.Member;
import weblogic.messaging.saf.utils.SAFClientUtil;

public class MemberString implements Member, Externalizable {
   static final long serialVersionUID = -5685689480293580262L;
   protected String id;
   protected String serverName;
   protected long timestamp;
   protected int generation;
   private static int lastGeneration;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int FLAG_ID_SERVER = 256;

   public MemberString(String memberId, String server) {
      assert memberId != null && server != null;

      Class var3 = MemberString.class;
      synchronized(MemberString.class) {
         this.timestamp = System.currentTimeMillis();
         this.generation = lastGeneration++;
      }

      this.id = memberId.intern();
      if (server != this.id && !server.equals(this.id)) {
         this.serverName = server.intern();
      } else {
         this.serverName = this.id;
      }

   }

   public MemberString() {
   }

   public Serializable getMemberId() {
      return this.id;
   }

   public String getStringId() {
      return this.id;
   }

   public String getWLServerName() {
      return this.serverName;
   }

   public long getTimeStamp() {
      return this.timestamp;
   }

   public int getGeneration() {
      return this.generation;
   }

   public int hashCode() {
      return this.id.hashCode() ^ this.serverName.hashCode();
   }

   public String toString() {
      return this.id + "^" + this.serverName + "^" + this.generation + "^" + this.timestamp;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Member)) {
         return false;
      } else {
         Member other = (Member)o;
         return other == this || this.timestamp == other.getTimeStamp() && this.generation == other.getGeneration() && this.id.equals(other.getMemberId()) && this.serverName.equals(other.getWLServerName());
      }
   }

   public int compareTo(Object arg) {
      Member other = (Member)arg;
      int c = this.id.compareTo((String)other.getMemberId());
      if (c != 0) {
         return c;
      } else {
         c = this.serverName.compareTo(other.getWLServerName());
         if (c != 0) {
            return c;
         } else {
            long diff = this.timestamp - other.getTimeStamp();
            if (diff < 0L) {
               return -1;
            } else {
               return diff > 0L ? 1 : this.generation - other.getGeneration();
            }
         }
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.id == this.serverName || this.id.equals(this.serverName)) {
         flags |= 256;
      }

      out.writeInt(flags);
      out.writeInt(this.generation);
      out.writeLong(this.timestamp);
      out.writeUTF(this.id);
      if ((flags & 256) == 0) {
         out.writeUTF(this.serverName);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw SAFClientUtil.versionIOException(version, 1, 1);
      } else {
         this.generation = in.readInt();
         this.timestamp = in.readLong();
         this.id = in.readUTF().intern();
         if ((flags & 256) == 0) {
            this.serverName = in.readUTF().intern();
         } else {
            this.serverName = this.id;
         }

      }
   }
}
