package weblogic.messaging.dispatcher;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.InetAddress;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.messaging.common.MessagingUtilities;

public class DispatcherId implements Externalizable, Comparable {
   static final long serialVersionUID = 2503587581403689795L;
   private String name;
   private String id;
   private int hashCode;
   private transient int counter;
   private static final byte EXTVERSION0 = 0;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION = 2;
   private static final int VERSION_MASK = 15;
   private static final int ID_FLAG = 16;

   public DispatcherId(String name, String id) {
      this.name = name;
      this.id = id;
      String tmp = name + id;
      this.hashCode = tmp.hashCode();
   }

   public DispatcherId(DispatcherId other, int counter) {
      this.name = other.name;
      this.id = other.id;
      this.hashCode = other.hashCode();
      this.counter = counter;
   }

   public final int compareTo(Object c) {
      DispatcherId b = (DispatcherId)c;
      int bHashCode = b.hashCode;
      if (this.hashCode < bHashCode) {
         return -1;
      } else if (this.hashCode > bHashCode) {
         return 1;
      } else if (this.name.length() < b.name.length()) {
         return -1;
      } else if (this.name.length() > b.name.length()) {
         return 1;
      } else {
         int result = this.name.compareTo(b.name);
         if (result != 0) {
            return result;
         } else {
            if (this.id != null && b.id != null) {
               if (this.id.length() < b.id.length()) {
                  return -1;
               }

               if (this.id.length() > b.id.length()) {
                  return 1;
               }

               result = this.id.compareTo(b.id);
               if (result != 0) {
                  return result;
               }
            }

            if (this.counter == b.counter) {
               return 0;
            } else {
               return this.counter < b.counter ? -1 : 1;
            }
         }
      }
   }

   public final String getName() {
      return this.name;
   }

   public final String getId() {
      return this.id;
   }

   public final int hashCode() {
      return this.hashCode;
   }

   public final boolean equals(Object o) {
      return !(o instanceof DispatcherId) ? false : this.internalEquals((DispatcherId)o, true);
   }

   public final boolean isSameServer(DispatcherId did) {
      return this.internalEquals(did, false);
   }

   private boolean internalEquals(DispatcherId did, boolean useCounter) {
      if (this == did) {
         return true;
      } else if (this.hashCode != did.hashCode) {
         return false;
      } else if (useCounter && this.counter != did.counter) {
         return false;
      } else {
         return this.name.equals(did.name) && (this.id == null || did.id == null || this.id != null && did.id != null && this.id.equals(did.id));
      }
   }

   public final String toString() {
      return this.name;
   }

   public DispatcherId() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int vrsn = this.getVersion(out);
      if (vrsn == 2) {
         if (this.id != null) {
            out.writeByte(18);
         } else {
            out.writeByte(2);
         }

         out.writeUTF(this.name);
         out.writeInt(this.hashCode);
         if (this.id != null) {
            out.writeUTF(this.id);
         }
      } else {
         out.writeByte(1);
         out.writeUTF(this.name);
         out.writeInt(this.hashCode);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte flags = in.readByte();
      byte vrsn = (byte)(flags & 15);
      if (vrsn != 1 && vrsn != 2) {
         throw MessagingUtilities.versionIOException(vrsn, 1, 2);
      } else {
         this.name = in.readUTF();
         this.hashCode = in.readInt();
         if ((flags & 16) != 0) {
            this.id = in.readUTF();
         }

      }
   }

   protected int getVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         int majorVer = pi.getMajor();
         return majorVer <= 8 ? 1 : 2;
      } else {
         return 1;
      }
   }

   public final String getHostAddress() {
      try {
         int index = this.name.indexOf(":");
         if (index != -1) {
            String hostAddr = this.name.substring(index + 1, this.name.length());
            hostAddr = hostAddr.substring(0, hostAddr.indexOf(":"));
            return InetAddress.getByName(hostAddr).getHostAddress();
         }
      } catch (Exception var3) {
      }

      return "0.0.0.0";
   }

   public String getDetail() {
      return "id = " + this.id + ", name = " + this.name + ", counter=" + this.counter + ", hashcode = " + this.hashCode;
   }
}
