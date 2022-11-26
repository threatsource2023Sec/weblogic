package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class AaaTcb extends tcb {
   byte[] Aaa;
   private int atz_appkey;
   private int aud_appkey;
   private byte[] atz_token;
   private byte[] aud_token;
   private String atz_name;
   private String atz_group;
   private String aud_name;
   private String aud_group;
   private String atz_provider = new String("BEA");
   private String aud_provider = new String("BEA");
   public static final int ATZ = 1;
   public static final int AUD = 2;
   public static final int TOKENVERSION80 = 100;
   public static final int TOKENVERSION71 = 1;
   public static final int AAA_MINOR_VERSION = 1;
   public static final int AAA_MAJOR_VERSION = 1;
   public static final int[] tuxnat_mech_objid = new int[]{6, 10, 43, 6, 1, 4, 1, 163, 122, 2, 1, 1};

   public AaaTcb() {
      super((short)15);
   }

   public boolean prepareForCache() {
      return false;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      int level = ntrace.getTraceLevel();
      if (traceEnabled) {
         ntrace.doTrace("[/AaaTcb/_tmpresend/" + myheader);
      }

      int send_size = false;
      int calculated_size = myheader.getHeaderLen();
      this.atz_token = new byte[140];
      this.aud_token = new byte[140];
      this.encodeATZ(this.atz_token, 0);
      this.encodeAUD(this.aud_token, 0);
      if (level == 1000373) {
         TDumpByte td = new TDumpByte("ATZ_TOKEN", this.atz_token);
         td.printDump("AUD_TOKEN", this.aud_token);
      }

      TypedFML32 mybuf = new TypedFML32();
      Integer majv = new Integer(1);
      Integer minv = new Integer(1);

      try {
         mybuf.Fchg(new FmlKey(33554532, 0), majv);
         mybuf.Fchg(new FmlKey(33554533, 0), minv);
         mybuf.Fchg(new FmlKey(201326694, 0), this.atz_token);
         mybuf.Fchg(new FmlKey(201326695, 0), this.aud_token);
      } catch (Ferror var13) {
         if (traceEnabled) {
            ntrace.doTrace("*]/AaaTcb/_tmpresend/5/" + var13);
         }

         throw new TPException(12, "Unable to add field " + var13);
      }

      int send_size;
      try {
         int initial_size = encoder.size();
         mybuf._tmpresend(encoder);
         int new_size = encoder.size();
         send_size = new_size - initial_size;
      } catch (IOException var14) {
         if (traceEnabled) {
            ntrace.doTrace("*]/AaaTcb/_tmpresend/10/" + var14);
         }

         throw var14;
      } catch (TPException var15) {
         if (traceEnabled) {
            ntrace.doTrace("*]/AaaTcb/_tmpresend/20/" + var15);
         }

         throw var15;
      }

      calculated_size += send_size;
      myheader.setLen(calculated_size);
      if (traceEnabled) {
         ntrace.doTrace("]/AaaTcb/_tmpresend/30/");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/AaaTcb/_tmpostrecv/" + decoder + "/" + recv_size + "/" + hint_index);
      }

      TypedFML32 fml = new TypedFML32();

      try {
         fml._tmpostrecv(decoder, recv_size);

         int vmaj;
         int vmin;
         try {
            FmlKey key = new FmlKey(33554532, 0);
            vmaj = (Integer)((Integer)fml.Fget(key));
            key = new FmlKey(33554533, 0);
            vmin = (Integer)((Integer)fml.Fget(key));
            key = new FmlKey(201326694, 0);
            this.atz_token = (byte[])((byte[])fml.Fget(key));
            key = new FmlKey(201326695, 0);
            this.aud_token = (byte[])((byte[])fml.Fget(key));
         } catch (Ferror var9) {
            if (traceEnabled) {
               ntrace.doTrace("]/AaaTcb/_tmpostrecv/10/-1/" + var9);
            }

            return -1;
         }

         if (vmaj != 1 || vmin != 1) {
            if (traceEnabled) {
               ntrace.doTrace("]/AaaTcb/_tmpostrecv/20/-1");
            }

            return -1;
         }

         this.decodeATZ(this.atz_token, 0);
         this.decodeAUD(this.aud_token, 0);
      } catch (TPException var10) {
         if (traceEnabled) {
            ntrace.doTrace("]/AaaTcb/_tmpostrecv/40/-1/" + var10);
         }

         return -1;
      }

      if (ntrace.getTraceLevel() == 1000373) {
         TDumpByte td = new TDumpByte("ATZ_TOKEN", this.atz_token);
         td.printDump("AUD_TOKEN", this.aud_token);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/AaaTcb/_tmpostrecv/30/0");
      }

      return 0;
   }

   public void setATZUserName(String usr) {
      if (usr != null) {
         this.atz_name = new String(usr);
      }

   }

   public String getATZUserName() {
      return this.atz_name;
   }

   public void setATZGroupName(String grp) {
      if (grp != null) {
         this.atz_group = new String(grp);
      }

   }

   public String getATZGroupName() {
      return this.atz_group;
   }

   public void setATZProvider(String prov) {
      if (prov != null) {
         this.atz_provider = new String(prov);
      }

   }

   public String getATZProvider() {
      return this.atz_provider;
   }

   public void setATZAppKey(int key) {
      this.atz_appkey = key;
   }

   public int getATZAppKey() {
      return this.atz_appkey;
   }

   public void setAUDUserName(String usr) {
      if (usr != null) {
         this.aud_name = new String(usr);
      }

   }

   public String getAUDUserName() {
      return this.aud_name;
   }

   public void setAUDGroupName(String grp) {
      if (grp != null) {
         this.aud_group = new String(grp);
      }

   }

   public String getAUDGroupName() {
      return this.aud_group;
   }

   public void setAUDProvider(String prov) {
      if (prov != null) {
         this.aud_provider = new String(prov);
      }

   }

   public String getAUDProvider() {
      return this.aud_provider;
   }

   public void setAUDAppKey(int key) {
      this.aud_appkey = key;
   }

   public int getAUDAppKey() {
      return this.aud_appkey;
   }

   public int encodeATZ(byte[] buf, int offset) {
      offset += Utilities.baWriteInt(1, buf, offset);
      offset += Utilities.baWriteInt(100, buf, offset);
      offset += Utilities.baWriteInt(this.atz_appkey, buf, offset);
      offset += Utilities.baWriteXdrString(buf, offset, this.atz_name);
      offset += Utilities.baWriteXdrString(buf, offset, this.atz_provider);

      for(int i = 0; i < tuxnat_mech_objid.length; ++i) {
         offset += Utilities.baWriteInt(tuxnat_mech_objid[i], buf, offset);
      }

      offset += Utilities.baWriteXdrString(buf, offset, this.atz_group);
      return offset;
   }

   public int decodeATZ(byte[] buf, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/AaaTcb/decodeATZ(" + Utilities.prettyByteArray(buf) + ", " + offset + ")");
      }

      int type = Utilities.baReadInt(buf, offset);
      offset += 4;
      int ver = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.atz_appkey = Utilities.baReadInt(buf, offset);
      offset += 4;
      if (traceEnabled) {
         ntrace.doTrace("atz_appkey: " + this.atz_appkey);
      }

      this.atz_name = Utilities.baReadXdrString(buf, offset);
      if (this.atz_name != null) {
         offset += Utilities.roundup4(this.atz_name.length()) + 4;
      } else {
         offset += 4;
      }

      if (ver >= 100) {
         this.atz_provider = Utilities.baReadXdrString(buf, offset);
         if (this.atz_provider != null) {
            offset += Utilities.roundup4(this.atz_provider.length()) + 4;
         } else {
            offset += 4;
         }

         int[] remote_mech = new int[tuxnat_mech_objid.length];

         int i;
         for(i = 0; i < tuxnat_mech_objid.length; ++i) {
            remote_mech[i] = Utilities.baReadInt(buf, offset);
            offset += 4;
         }

         this.atz_group = Utilities.baReadXdrString(buf, offset);
         if (this.atz_group != null) {
            offset += Utilities.roundup4(this.atz_group.length()) + 4;
         } else {
            offset += 4;
         }

         for(i = 0; i < tuxnat_mech_objid.length; ++i) {
            if (remote_mech[i] != tuxnat_mech_objid[i]) {
               if (traceEnabled) {
                  ntrace.doTrace("]/AaaTcb/decodeATZ/10/" + -offset);
               }

               return -offset;
            }
         }
      }

      if (ntrace.getTraceLevel() == 1000373) {
         if (ver >= 100) {
            ntrace.doTrace("ATZ(name " + this.atz_name + ", provider " + this.atz_provider + ", group " + this.atz_group + ")");
         } else {
            ntrace.doTrace("ATZ(name " + this.atz_name + ")");
         }
      }

      if (type != 1 || ver < 100 && ver != 1) {
         if (traceEnabled) {
            ntrace.doTrace("]/AaaTcb/decodeATZ/20/" + -offset);
         }

         return -offset;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/AaaTcb/decodeATZ/30/" + offset);
         }

         return offset;
      }
   }

   public int encodeAUD(byte[] buf, int offset) {
      offset += Utilities.baWriteInt(2, buf, offset);
      offset += Utilities.baWriteInt(100, buf, offset);
      offset += Utilities.baWriteInt(this.aud_appkey, buf, offset);
      offset += Utilities.baWriteXdrString(buf, offset, this.aud_name);
      offset += Utilities.baWriteXdrString(buf, offset, this.aud_provider);

      for(int i = 0; i < tuxnat_mech_objid.length; ++i) {
         offset += Utilities.baWriteInt(tuxnat_mech_objid[i], buf, offset);
      }

      offset += Utilities.baWriteXdrString(buf, offset, this.aud_group);
      return offset;
   }

   public int decodeAUD(byte[] buf, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/AaaTcb/decodeAUD(" + Utilities.prettyByteArray(buf) + ", " + offset + ")");
      }

      int type = Utilities.baReadInt(buf, offset);
      offset += 4;
      int ver = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.aud_appkey = Utilities.baReadInt(buf, offset);
      offset += 4;
      if (traceEnabled) {
         ntrace.doTrace("atz_appkey: " + this.atz_appkey);
      }

      this.aud_name = Utilities.baReadXdrString(buf, offset);
      if (this.aud_name != null) {
         offset += Utilities.roundup4(this.aud_name.length()) + 4;
      } else {
         offset += 4;
      }

      if (ver >= 100) {
         this.aud_provider = Utilities.baReadXdrString(buf, offset);
         if (this.aud_provider != null) {
            offset += Utilities.roundup4(this.aud_provider.length()) + 4;
         } else {
            offset += 4;
         }

         int[] remote_mech = new int[tuxnat_mech_objid.length];

         int i;
         for(i = 0; i < tuxnat_mech_objid.length; ++i) {
            remote_mech[i] = Utilities.baReadInt(buf, offset);
            offset += 4;
         }

         this.aud_group = Utilities.baReadXdrString(buf, offset);
         if (this.aud_group != null) {
            offset += Utilities.roundup4(this.aud_group.length()) + 4;
         } else {
            offset += 4;
         }

         for(i = 0; i < tuxnat_mech_objid.length; ++i) {
            if (remote_mech[i] != tuxnat_mech_objid[i]) {
               if (traceEnabled) {
                  ntrace.doTrace("]/AaaTcb/decodeAUD/10/" + -offset);
               }

               return -offset;
            }
         }
      }

      if (ntrace.getTraceLevel() == 1000373) {
         if (ver >= 100) {
            ntrace.doTrace("AUD(name " + this.aud_name + ", provider " + this.aud_provider + ", group " + this.aud_group + ")");
         } else {
            ntrace.doTrace("AUD(name " + this.aud_name + ")");
         }
      }

      if (type != 2 || ver < 100 && ver != 1) {
         if (traceEnabled) {
            ntrace.doTrace("]/AaaTcb/decodeAUD/20/" + -offset);
         }

         return -offset;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/AaaTcb/decodeAUD/30/" + offset);
         }

         return offset;
      }
   }
}
