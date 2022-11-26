package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class tch implements Serializable {
   private short tmtcm_type;
   private int tmtcm_len;
   private int tmtcm_flags;
   static final int TCH_HEB_MASK = 15;
   static final int TCM_IN_USE = 16;
   static final int TCB_ENCODE = 32;
   static final int TCM_UNKNOWN = 64;
   static final int TCM_FIU = 512;
   static final int TCM_NN = 1024;
   static final int TCM_STK = 2048;
   static final int TCM_TTL = 4096;
   static final int TCMTYPESHIFT = 16;
   private static final int TCH_SIZE = 12;

   public tch() {
      this.tmtcm_flags = 16 | 32 | 512 | 4096;
   }

   public tch(short type) {
      this.tmtcm_type = type;
      this.tmtcm_flags = 16 | 32 | 512 | 4096;
   }

   public tch(tch copyFrom) {
      if (copyFrom == null) {
         this.tmtcm_type = 0;
         this.tmtcm_len = 0;
         this.tmtcm_flags = 16 | 32 | 512 | 4096;
      } else {
         this.tmtcm_type = copyFrom.getType();
         this.tmtcm_len = copyFrom.getLen();
         this.tmtcm_flags = copyFrom.getFlags();
      }

   }

   public void myClone(tch copyFrom) {
      this.tmtcm_type = copyFrom.getType();
      this.tmtcm_len = copyFrom.getLen();
      this.tmtcm_flags = copyFrom.getFlags();
   }

   public void setLen(int thesize) {
      this.tmtcm_len = thesize;
   }

   public int getLen() {
      return this.tmtcm_len;
   }

   public int getHeaderLen() {
      return 12;
   }

   public short getType() {
      return this.tmtcm_type;
   }

   public int getFlags() {
      return this.tmtcm_flags;
   }

   public boolean prepareForCache() {
      this.tmtcm_len = 0;
      this.tmtcm_flags = 16 | 32 | 512 | 4096;
      return true;
   }

   public void write_tch(DataOutputStream dstream) throws IOException {
      int real_type = this.tmtcm_type << 16;
      dstream.writeInt(real_type);
      dstream.writeInt(this.tmtcm_len);
      dstream.writeInt(this.tmtcm_flags);
   }

   public void read_tch(DataInputStream istream) throws IOException {
      int real_tmtcm_type = istream.readInt();
      this.tmtcm_type = (short)(real_tmtcm_type >> 16);
      this.tmtcm_len = istream.readInt();
      this.tmtcm_flags = istream.readInt();
   }
}
