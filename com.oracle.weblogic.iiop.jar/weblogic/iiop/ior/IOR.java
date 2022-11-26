package weblogic.iiop.ior;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.utils.io.Chunk;

public final class IOR implements Externalizable {
   private static final long serialVersionUID = 1952182103399381650L;
   public static final IOR NULL;
   private boolean remote;
   private RepositoryId typeId;
   private Profile[] profiles;
   private IOPProfile iopProfile;

   public IOR() {
      this(RepositoryId.EMPTY);
   }

   public static IOR createNullIOR(RepositoryId typeId) {
      return new IOR(typeId);
   }

   private IOR(RepositoryId repositoryId) {
      this.remote = false;
      this.typeId = repositoryId;
      this.profiles = new Profile[0];
   }

   public IOR(CorbaInputStream in) {
      this(in, false);
   }

   public IOR(CorbaInputStream in, boolean force) {
      this.remote = false;
      this.read(in, force);
   }

   public IOR(String typeId, IOPProfile profile) {
      this.remote = false;
      this.typeId = typeId == null ? RepositoryId.OBJECT : new RepositoryId(typeId);
      this.iopProfile = profile;
      this.profiles = new Profile[]{this.iopProfile};
   }

   public Target getTarget() {
      return new Target(this.getProfile().getHost(), this.isSecure() ? this.getProfile().getSecurePort() : this.getProfile().getPort());
   }

   public boolean isRemote() {
      return this.remote;
   }

   public String toString() {
      return this.getClass().getName() + "[" + this.typeId.toString() + "] @" + this.iopProfile.getHost() + ":" + this.iopProfile.getPort();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      byte[] encap = this.getEncapsulation();
      out.writeInt(encap.length);
      out.write(encap);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int size = in.readInt();
      byte[] b = new byte[size];
      in.readFully(b);
      CorbaInputStream s = IiopProtocolFacade.createInputStream(b);
      s.consumeEndian();
      this.read(s, false);
      s.close();
   }

   public final String stringify() {
      byte[] encap = this.getEncapsulation();
      char[] str = new char[4 + encap.length * 2];
      str[0] = 'I';
      str[1] = 'O';
      str[2] = 'R';
      str[3] = ':';

      for(int i = 0; i < encap.length; ++i) {
         str[4 + i * 2] = this.toHexChar(encap[i] >> 4 & 15);
         str[4 + i * 2 + 1] = this.toHexChar(encap[i] & 15);
      }

      return new String(str);
   }

   public static IOR destringify(String str) {
      if (str == null) {
         throw new IllegalArgumentException("Null not a valid argument");
      } else if (str.startsWith("IOR:") && (str.length() & 1) != 1) {
         Chunk head = Chunk.getChunk();
         Chunk tail = head;
         int j = 0;

         for(int i = 4; i < str.length(); ++j) {
            tail.buf[j] = (byte)(fromHexChar(str.charAt(i)) << 4 & 240);
            byte[] var10000 = tail.buf;
            var10000[j] |= (byte)(fromHexChar(str.charAt(i + 1)) & 15);
            if (j == Chunk.CHUNK_SIZE) {
               tail.next = Chunk.getChunk();
               tail.end = Chunk.CHUNK_SIZE;
               tail = tail.next;
               j = 0;
            }

            i += 2;
         }

         tail.end = j;
         CorbaInputStream s = IiopProtocolFacade.createInputStream(head);
         s.consumeEndian();
         IOR ior = new IOR(s);
         s.close();
         return ior;
      } else {
         throw new IllegalArgumentException("String not valid");
      }
   }

   private char toHexChar(int b) {
      switch (b) {
         case 0:
            return '0';
         case 1:
            return '1';
         case 2:
            return '2';
         case 3:
            return '3';
         case 4:
            return '4';
         case 5:
            return '5';
         case 6:
            return '6';
         case 7:
            return '7';
         case 8:
            return '8';
         case 9:
            return '9';
         case 10:
            return 'A';
         case 11:
            return 'B';
         case 12:
            return 'C';
         case 13:
            return 'D';
         case 14:
            return 'E';
         case 15:
            return 'F';
         default:
            throw new AssertionError("Unknown char: " + b);
      }
   }

   private static int fromHexChar(char x) {
      int val = x - 48;
      if (val >= 0 && val <= 9) {
         return val;
      } else {
         val = x - 97 + 10;
         if (val >= 10 && val <= 15) {
            return val;
         } else {
            val = x - 65 + 10;
            if (val >= 10 && val <= 15) {
               return val;
            } else {
               throw new IllegalArgumentException("String not hex");
            }
         }
      }
   }

   private byte[] getEncapsulation() {
      CorbaOutputStream tmp = IiopProtocolFacade.createOutputStream();
      tmp.putEndian();
      this.write(tmp);
      byte[] buf = tmp.getBuffer();
      tmp.close();
      return buf;
   }

   public final RepositoryId getTypeId() {
      return this.typeId;
   }

   public final String getCodebase() {
      CodebaseComponent cb = (CodebaseComponent)this.getProfile().getComponent(25);
      return cb != null ? cb.getCodebase() : null;
   }

   public boolean hasIOPProfile() {
      return this.iopProfile != null;
   }

   public final IOPProfile getProfile() {
      if (this.iopProfile == null) {
         throw new NO_IMPLEMENT(1330446339, CompletionStatus.COMPLETED_NO);
      } else {
         return this.iopProfile;
      }
   }

   public final ListenPoint getListenPoint() {
      return this.getProfile().getListenPoint();
   }

   public final boolean isSecure() {
      return this.getProfile().isSecure();
   }

   public final boolean isNull() {
      return (this.typeId == null || this.typeId.length() == 0) && (this.profiles == null || this.profiles.length == 0);
   }

   private void read(CorbaInputStream in, boolean force) {
      this.typeId = in.read_repository_id();
      if ((this.typeId == null || this.typeId.equals((Object)RepositoryId.EMPTY)) && !force) {
         in.mark(0);
         if (in.read_long() != 0) {
            in.reset();
         }

      } else {
         int numProfiles = in.read_long();
         if (numProfiles > 100) {
            throw new MARSHAL("Impossible number of IOR profiles specified: " + numProfiles);
         } else {
            this.profiles = new Profile[numProfiles];

            for(int i = 0; i < numProfiles; ++i) {
               int tag = in.read_long();
               switch (tag) {
                  case 0:
                     this.iopProfile = new IOPProfile();
                     this.iopProfile.read(in);
                     this.profiles[i] = this.iopProfile;
                     break;
                  default:
                     this.profiles[i] = new Profile(i);
                     this.profiles[i].read(in);
               }
            }

            this.remote = true;
         }
      }
   }

   public void write(CorbaOutputStream out) {
      out.write_repository_id(this.typeId);
      if (this.profiles == null) {
         out.write_long(0);
      } else {
         out.write_long(this.profiles.length);
         Profile[] var2 = this.profiles;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Profile profile = var2[var4];
            profile.write(out);
         }

      }
   }

   public final int hashCode() {
      return this.iopProfile == null ? this.typeId.hashCode() : this.typeId.hashCode() ^ this.iopProfile.hashCode();
   }

   public final boolean equals(Object o) {
      if (!(o instanceof IOR)) {
         return false;
      } else {
         IOR other = (IOR)o;
         return this.typeId.equals((Object)other.typeId) && (this.iopProfile == other.iopProfile || (this.iopProfile == null || this.iopProfile.equals((Object)other.iopProfile)) && this.iopProfile != null);
      }
   }

   static void p(String s) {
      System.err.println("<IOR> " + s);
   }

   static {
      NULL = createNullIOR(RepositoryId.EMPTY);
   }
}
