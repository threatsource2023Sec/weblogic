package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class metahdr implements Serializable {
   private static final long serialVersionUID = 4253064680349914379L;
   protected int mtype = 0;
   protected int mprotocol = 0;
   protected int qaddr = 0;
   protected int mid = 0;
   protected int size = 0;
   protected int flags = 0;
   protected int uncmprs_sz = 0;
   static final int METAHDR_SIZE = 32;
   static final int WSCLASSIC = 42;
   static final int WSVIEWENC = 43;
   static final int WSBLKPROT = 44;
   static final int WSCOMPROT = 45;
   static final int WSTIMPROT = 46;
   static final int WSMILPROT = 47;
   static final int WSEMPPROT = 48;
   static final int WSPK5PROT = 49;
   static final int WSPROTOCOL = 49;
   public static final int DOM50PROTOCOL = 10;
   public static final int DOM61PROTOCOL = 11;
   public static final int DOM63PROTOCOL = 12;
   public static final int DOM65PROTOCOL = 13;
   public static final int DMMILPROTOCOL = 14;
   public static final int DMEMPPROTOCOL = 15;
   public static final int DMRIGPROTOCOL = 16;
   public static final int DM12CR1PROTOCOL = 20;
   public static final int DOMPROTOCOL = 20;
   public static final int TMTITAMPROT = 62;
   public static final int TMR64PROT = 64;
   public static final int TMMILPROT = 65;
   public static final int TMWLE5PROT = 66;
   public static final int TMEMPROT = 70;
   public static final int TMRIGELPROT = 72;
   public static final int TMPEGPROT = 81;
   public static final int TMPROTOCOL = 81;
   public static final int TMENCODE = 1;
   public static final int TMDECODE = 2;
   public static final int TMALARM = 4;
   public static final int TMINFILE = 16;
   public static final int TMLIBNET = 32;
   public static final int TMBRIDGE = 64;
   public static final int TMFREENW = 128;
   public static final int TMCOMPRESSED = 256;
   public static final int TMDBBLFWD = 512;
   public static final int TMDYED = 1024;
   public static final int TMEXTRAHDR = 2048;
   public static final int TMCONVR = 4096;
   public static final int TMTGIOP = 8192;
   public static final int TMOBIT = 16384;
   public static final int TMREPLY = 4194304;
   public static final int TMXDR64COMPAT = 67108864;
   public static final int TMENQUEUE = 1073741824;
   public static final int TMSTORAGE = Integer.MIN_VALUE;
   static final int TM_MAX_ITYPES = 16;
   public static final int GPE0 = 1;
   public static final int GPE40 = 2;
   public static final int GPE128 = 4;
   public static final int GPE56 = 32;
   public static final int GPE_ALLOWANY = 64;
   static final int BADMID = -2;

   public metahdr() {
      this.mprotocol = 49;
      this.flags = -2147483647;
      this.size = 32;
      this.mid = -2;
   }

   public metahdr(boolean initmsg) {
      if (initmsg) {
         this.mprotocol = 81;
         this.flags = 1;
      } else {
         this.mprotocol = 49;
         this.flags = -2147483647;
      }

      this.size = 32;
      this.mid = -2;
   }

   public boolean prepareForCache() {
      this.flags = -2147483647;
      this.size = 32;
      this.mid = -2;
      this.mtype = 0;
      this.qaddr = 0;
      this.uncmprs_sz = 0;
      return true;
   }

   public void set_TMENQUEUE(boolean isenq) {
      if (isenq) {
         this.flags |= 1073741824;
      } else {
         this.flags &= -1073741825;
      }

   }

   public void setTMREPLY(boolean isReply) {
      if (isReply) {
         this.flags |= 4194304;
      } else {
         this.flags &= -4194305;
      }

   }

   public int getProtocol() {
      return this.mprotocol;
   }

   public void setFlags(int newFlags) {
      this.flags = newFlags;
   }

   public int getFlags() {
      return this.flags;
   }

   private void print_metahdr() {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(36);
      if (traceEnabled) {
         ntrace.doTrace("[/print_metahdr/");
      }

      if (traceEnabled) {
         ntrace.doTrace("/print_metahdr/mtype=" + this.mtype + "/");
         ntrace.doTrace("/print_metahdr/mprotocol=" + this.mprotocol + "/");
         ntrace.doTrace("/print_metahdr/qaddr=" + this.qaddr + "/");
         ntrace.doTrace("/print_metahdr/mid=" + this.mid + "/");
         ntrace.doTrace("/print_metahdr/size=" + this.size + "/");
         ntrace.doTrace("/print_metahdr/flags=" + this.flags + "/");
         ntrace.doTrace("/print_metahdr/uncmprs_sz=" + this.uncmprs_sz + "/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/print_metahdr/10/");
      }

   }

   public void write_metahdr(DataOutputStream encoder) throws IOException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(36);
      if (traceEnabled) {
         ntrace.doTrace("[/write_metahdr/");
      }

      if (ntrace.isTraceEnabled(32)) {
         this.print_metahdr();
      }

      try {
         encoder.writeInt(this.mtype);
         encoder.writeInt(this.mprotocol);
         encoder.writeInt(this.qaddr);
         encoder.writeInt(this.mid);
         encoder.writeInt(this.size);
         encoder.writeInt(this.flags);
         encoder.writeInt(this.uncmprs_sz);
         encoder.writeInt(0);
      } catch (NullPointerException var4) {
         throw new IOException("Connection is down - output stream is null");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/write_metahdr/10/");
      }

   }

   public void read_metahdr(DataInputStream decoder) throws IOException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(36);
      if (traceEnabled) {
         ntrace.doTrace("[/read_metahdr/");
      }

      this.mtype = decoder.readInt();
      this.mprotocol = decoder.readInt();
      this.qaddr = decoder.readInt();
      this.mid = decoder.readInt();
      this.size = decoder.readInt();
      this.flags = decoder.readInt();
      this.uncmprs_sz = decoder.readInt();
      int reserved64 = decoder.readInt();
      if (ntrace.isTraceEnabled(32)) {
         this.print_metahdr();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/read_metahdr/10");
      }

   }
}
