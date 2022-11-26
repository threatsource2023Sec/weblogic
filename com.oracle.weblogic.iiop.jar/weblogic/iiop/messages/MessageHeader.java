package weblogic.iiop.messages;

import org.omg.CORBA.MARSHAL;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class MessageHeader implements MessageHeaderConstants {
   private byte[] magic = new byte[4];
   private byte[] GIOP_version = new byte[2];
   private byte flags;
   private byte message_type;
   private long message_size;
   private static final int MSG_POOL_SIZE = 1024;

   public String toString() {
      return "MessageHeader: magic = " + (char)this.magic[0] + (char)this.magic[1] + (char)this.magic[2] + (char)this.magic[3] + " GIOP_version = " + this.GIOP_version[0] + "." + this.GIOP_version[1] + "\n" + ((this.flags & 1) == 0 ? " big-endian byte order" : "little-endian byte order ") + " fragments follow = " + this.isFragmented() + " message_type = " + this.message_type + " message_size = " + this.message_size;
   }

   public MessageHeader(CorbaInputStream is) {
      this.read(is);
   }

   public void read(CorbaInputStream is) {
      is.read_octet_array(this.magic, 0, 4);
      is.read_octet_array(this.GIOP_version, 0, 2);
      if (this.GIOP_version[0] != 1) {
         throw new MARSHAL("Unsupported GIOP version.");
      } else {
         switch (this.getMinorVersion()) {
            case 0:
            case 1:
            case 2:
            default:
               this.flags = is.read_octet();
               is.setLittleEndian(this.isLittleEndian());
               is.setMinorVersion(this.getMinorVersion());
               this.message_type = is.read_octet();
               this.message_size = (long)is.read_long();
               if (this.isFragmented() && (this.message_size + 12L) % 8L != 0L) {
                  throw new MARSHAL("Fragments must be a multiple of 8 bytes ");
               }
         }
      }
   }

   public MessageHeader(int type, int minorVersion) {
      this.magic = MAGIC_VALUE;
      switch (minorVersion) {
         case 0:
            this.GIOP_version = GIOP_VERSION_1_0;
            break;
         case 1:
            this.GIOP_version = GIOP_VERSION_1_1;
            break;
         case 2:
            this.GIOP_version = GIOP_VERSION_1_2;
            break;
         default:
            throw new MARSHAL("Unsupported GIOP version.");
      }

      this.flags = 0;
      this.message_type = (byte)type;
      this.message_size = 0L;
   }

   public static int getMsgType(byte[] buf) {
      switch (buf[7]) {
         case 0:
            return 0;
         case 1:
            return 1;
         case 2:
            return 2;
         case 3:
            return 3;
         case 4:
            return 4;
         case 5:
            return 5;
         case 6:
            return 6;
         case 7:
            return 7;
         default:
            return -1;
      }
   }

   public int getMsgType() {
      return this.message_type;
   }

   public int getMsgSize() {
      return (int)this.message_size;
   }

   public boolean isLittleEndian() {
      return (this.flags & 1) == 1;
   }

   public boolean isFragmented() {
      return (this.flags & 2) == 2;
   }

   public void setFragmented(boolean fragmented) {
      if (fragmented) {
         this.flags = (byte)(this.flags | 2);
      } else {
         this.flags &= -3;
      }

   }

   public int getMinorVersion() {
      return this.GIOP_version[1];
   }

   public String getMsgTypeAsString() {
      switch (this.message_type) {
         case 0:
            return "REQUEST";
         case 1:
            return "REPLY";
         case 2:
            return "CANCEL_REQUEST";
         case 3:
            return "LOCATE_REQUEST";
         case 4:
            return "LOCATE_REPLY";
         case 5:
            return "CLOSE_CONNECTION";
         case 6:
            return "MESSAGE_ERROR";
         case 7:
            return "FRAGMENT";
         default:
            return "UNKNOWN";
      }
   }

   public void write(CorbaOutputStream os) {
      os.setLittleEndian(this.isLittleEndian());
      os.write_octet(MAGIC_VALUE[0]);
      os.write_octet(MAGIC_VALUE[1]);
      os.write_octet(MAGIC_VALUE[2]);
      os.write_octet(MAGIC_VALUE[3]);
      os.write_octet(this.GIOP_version[0]);
      os.write_octet(this.GIOP_version[1]);
      os.write_octet(this.flags);
      os.write_octet(this.message_type);
      os.write_long(51966);
   }

   public static MessageHeader getMessageHeader(CorbaInputStream is) {
      return new MessageHeader(is);
   }

   public final void close() {
      this.flags = 0;
      this.message_type = 0;
      this.message_size = 0L;
   }
}
