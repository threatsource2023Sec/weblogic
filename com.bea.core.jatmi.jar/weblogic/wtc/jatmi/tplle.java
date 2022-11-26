package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCLicenseManager;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public final class tplle {
   private static SecureRandom rnd = null;
   private static final short[] DH40params = new short[]{48, 129, 216, 6, 9, 42, 134, 72, 134, 247, 13, 1, 3, 1, 48, 129, 202, 2, 97, 0, 211, 226, 116, 59, 135, 159, 211, 32, 59, 222, 47, 113, 126, 251, 130, 213, 96, 42, 8, 251, 121, 88, 76, 83, 53, 122, 43, 155, 136, 238, 184, 186, 231, 92, 217, 143, 45, 239, 31, 79, 159, 164, 126, 227, 134, 89, 39, 198, 248, 201, 125, 147, 71, 180, 151, 7, 183, 250, 142, 71, 35, 54, 254, 208, 69, 193, 163, 178, 151, 165, 78, 130, 218, 92, 238, 37, 149, 13, 204, 167, 35, 52, 212, 215, 162, 78, 21, 62, 156, 182, 161, 151, 254, 254, 140, 195, 2, 97, 0, 134, 119, 129, 138, 48, 137, 84, 80, 211, 17, 183, 80, 188, 150, 155, 36, 35, 239, 134, 241, 73, 124, 80, 22, 223, 173, 236, 144, 75, 64, 245, 229, 109, 240, 59, 169, 236, 169, 188, 78, 12, 221, 33, 78, 130, 189, 31, 54, 166, 87, 19, 132, 236, 4, 14, 141, 150, 133, 13, 151, 235, 73, 227, 91, 63, 83, 139, 4, 102, 110, 230, 125, 132, 67, 173, 206, 60, 142, 65, 30, 72, 116, 8, 243, 245, 158, 234, 110, 239, 233, 139, 48, 60, 229, 49, 219, 2, 2, 0, 128};
   private static final short[] DH128params = new short[]{48, 130, 1, 154, 6, 9, 42, 134, 72, 134, 247, 13, 1, 3, 1, 48, 130, 1, 139, 2, 129, 193, 0, 211, 226, 116, 59, 135, 159, 211, 32, 59, 222, 47, 113, 126, 251, 130, 213, 96, 42, 8, 251, 121, 88, 76, 83, 53, 122, 43, 155, 136, 238, 184, 186, 231, 92, 217, 143, 45, 239, 31, 79, 159, 164, 126, 227, 134, 89, 39, 198, 248, 201, 125, 147, 71, 180, 151, 7, 183, 250, 142, 71, 35, 54, 254, 208, 69, 193, 163, 178, 151, 165, 78, 130, 218, 92, 238, 37, 149, 13, 204, 32, 12, 184, 195, 245, 49, 99, 7, 212, 32, 51, 122, 27, 22, 210, 63, 182, 44, 189, 36, 246, 145, 130, 137, 137, 143, 121, 195, 208, 123, 177, 157, 57, 84, 205, 225, 49, 19, 110, 235, 86, 195, 104, 27, 157, 150, 216, 22, 246, 128, 223, 67, 113, 101, 54, 210, 87, 122, 255, 174, 244, 39, 20, 93, 88, 85, 226, 57, 204, 242, 17, 248, 239, 78, 10, 84, 140, 171, 33, 251, 118, 254, 168, 253, 85, 165, 222, 37, 168, 48, 26, 111, 152, 161, 81, 180, 18, 108, 194, 51, 149, 221, 79, 229, 212, 220, 160, 8, 196, 231, 177, 227, 181, 2, 129, 192, 12, 208, 21, 45, 161, 78, 177, 138, 175, 87, 131, 147, 38, 183, 235, 128, 221, 67, 21, 24, 113, 125, 24, 117, 90, 75, 124, 96, 200, 28, 107, 73, 210, 235, 55, 154, 180, 54, 197, 11, 69, 215, 68, 220, 185, 117, 219, 173, 198, 50, 67, 146, 48, 135, 49, 95, 249, 36, 33, 69, 198, 80, 253, 163, 112, 244, 183, 154, 199, 34, 28, 204, 252, 8, 27, 139, 32, 96, 18, 170, 254, 183, 201, 24, 63, 168, 19, 65, 108, 118, 47, 47, 52, 180, 124, 208, 138, 14, 115, 201, 223, 251, 187, 255, 5, 18, 175, 28, 198, 177, 234, 41, 238, 98, 163, 217, 45, 148, 98, 182, 150, 247, 9, 53, 111, 8, 205, 171, 15, 30, 16, 208, 126, 239, 130, 124, 116, 124, 150, 238, 187, 173, 40, 147, 191, 57, 38, 137, 114, 63, 24, 52, 109, 33, 19, 76, 14, 48, 13, 58, 12, 172, 224, 97, 16, 150, 190, 81, 114, 101, 103, 50, 117, 25, 232, 169, 179, 207, 113, 12, 6, 239, 22, 114, 162, 151, 196, 129, 20, 139, 237, 66, 2, 2, 0, 128};
   private int lle_state = 1;
   private int lle_pkt_magic;
   private int lle_pkt_type;
   private int lle_pkt_protocol;
   private int lle_reason;
   private int lle_data;
   private int lle_flags;
   private int lle_prime_len;
   private int lle_pub_len;
   private int negotiated_flags;
   private int sendDisclosureLen;
   private int recvDisclosureLen;
   private byte[] sendDisclosure;
   private byte[] recvDisclosure;
   private short[] ber;
   private BigInteger p = null;
   private BigInteger g = null;
   private BigInteger x1 = null;
   private BigInteger y1 = null;
   private BigInteger y2 = null;
   private byte[] sendKey = null;
   private byte[] recvKey = null;
   private byte[] yy1 = null;
   private byte[] yy2 = null;
   private byte[] params = null;
   private boolean[] h_snd_initialized = new boolean[]{false};
   private int[] h_snd_i = new int[]{0};
   private int[] h_snd_j = new int[]{0};
   private byte[] h_snd_table = new byte[256];
   private boolean[] h_rcv_initialized = new boolean[]{false};
   private int[] h_rcv_i = new int[]{0};
   private int[] h_rcv_j = new int[]{0};
   private byte[] h_rcv_table = new byte[256];
   private byte[] fingerprint;
   public static final int LLE_ALLOCATED = 1;
   public static final int LLE_SENDFIRSTDH = 2;
   public static final int LLE_DH2_0 = 3;
   public static final int LLE_DH2_40 = 4;
   public static final int LLE_DH2_56 = 5;
   public static final int LLE_DH2_128 = 6;
   public static final int LLE_VERSION = 7;
   public static final int LLE_NEGOTIATE = 8;
   public static final int LLE_SYSTEM = 9;
   public static final int LLE_RDWR = 10;
   public static final int AE_NOT_INITIALIZED = 11;
   private static final int ENCMAGIC = 2091903911;
   private static final int TMLLEPROT = 0;
   private static final int LLELENGTHINBYTES = 16;
   private static final int ENACKNEGOTIATE = 0;
   private static final int ENACKZERO = 1;
   private static final int ENACKVERSION = 2;
   private static final int ENACKSYSTEM = 3;
   private static final int TMLLENACK = 0;
   private static final int TMLLEDH1 = 1;
   private static final int TMLLEDH2 = 2;
   private static final int LLE_FREE = 0;
   private static final int PUBLICBYTES40 = 11;
   private static final int PRIVATEBYTES40 = 5;
   private static final int PUBLICBYTES56 = 9;
   private static final int PRIVATEBYTES56 = 7;

   public int crypKeyeOne(int var1, byte[] var2, int var3) {
      // $FF: Couldn't be decompiled
   }

   public int crypKeyeTwo(int var1, byte[] var2, byte[] var3, int var4) {
      // $FF: Couldn't be decompiled
   }

   public int crypFinishOne(byte[] dh2_net) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tplle/crypFinishOne/");
      }

      if (dh2_net == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/ERROR: The encryption packet is invalid");
         }

         return 9;
      } else if (this.lle_state != 2) {
         if (traceEnabled) {
            ntrace.doTrace("]/ERROR: Encryption object in an unexpected state");
         }

         return 9;
      } else {
         this.decodePktHdr(dh2_net, 0);
         if (this.lle_pkt_magic == 2091903911 && this.lle_pkt_protocol == 0 && (this.lle_pkt_type == 0 || this.lle_pkt_type == 2)) {
            if (this.lle_pkt_type == 0) {
               this.decodeNACK(dh2_net, 0);
               switch (this.lle_reason) {
                  case 0:
                     if (traceEnabled) {
                        ntrace.doTrace("]/tplle/crypFinishOne/60/LLE_NEGOTIATE");
                     }

                     return 8;
                  case 1:
                     if ((this.negotiated_flags & 1) == 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/ERROR: Invalid negotiation parameter from remote site");
                        }

                        return 9;
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/tplle/crypFinishOne/50/LLE_DH2_0");
                     }

                     return 3;
                  default:
                     if (traceEnabled) {
                        ntrace.doTrace("]/ERROR: Remote system returns an error" + this.lle_reason);
                     }

                     return 9;
               }
            } else {
               this.decodeDH2(dh2_net, 0);
               if (this.sendDisclosureLen != this.recvDisclosureLen) {
                  this.lle_state = 9;
                  if (traceEnabled) {
                     ntrace.doTrace("]/ERROR: Invalid parameter from remote site");
                  }

                  return 9;
               } else if (((this.negotiated_flags & 32) == 0 || this.sendDisclosureLen == 9) && ((this.negotiated_flags & 2) == 0 || this.sendDisclosureLen == 11) && ((this.negotiated_flags & 4) == 0 || this.sendDisclosureLen == 0) && (this.negotiated_flags & this.lle_flags) != 0) {
                  byte[] send_key;
                  byte[] recv_key;
                  try {
                     this.acceptOtherPublicValue(this.yy2, this.sendDisclosure, this.recvDisclosure);
                     send_key = this.getSendKey();
                     recv_key = this.getRecvKey();
                     if (traceEnabled) {
                        TDumpByte td = new TDumpByte();
                        td.printDump("/tplle/crypFinishOne/send_key", send_key);
                        td.printDump("/tplle/crypFinishOne/recv_key", recv_key);
                     }
                  } catch (Exception var6) {
                     this.lle_state = 9;
                     if (traceEnabled) {
                        ntrace.doTrace("]/ERROR: Invalid parameter from remote site. Reason: " + var6);
                     }

                     return 9;
                  }

                  this.generateFingerprint(send_key, recv_key);
                  this.lle_state = 10;
                  this.Init(this.h_snd_initialized, this.h_snd_i, this.h_snd_j, this.h_snd_table, send_key);
                  this.Init(this.h_rcv_initialized, this.h_rcv_i, this.h_rcv_j, this.h_rcv_table, recv_key);
                  switch (this.sendDisclosureLen) {
                     case 9:
                        if (traceEnabled) {
                           ntrace.doTrace("]/tplle/crypFinishOne/120/LLE_DH2_56");
                        }

                        return 5;
                     case 11:
                        if (traceEnabled) {
                           ntrace.doTrace("]/tplle/crypFinishOne/110/LLE_DH2_40");
                        }

                        return 4;
                     default:
                        if (traceEnabled) {
                           ntrace.doTrace("]/tplle/crypFinishOne/130/LLE_DH2_128");
                        }

                        return 6;
                  }
               } else {
                  this.lle_state = 9;
                  if (traceEnabled) {
                     ntrace.doTrace("]/ERROR: Invalid parameter from remote site");
                  }

                  return 9;
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/ERROR: Invalid packet from network");
            }

            return 9;
         }
      }
   }

   public int crypFinishTwo() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tplle/crypFinishTwo/");
      }

      int retval = this.lle_state;
      switch (retval) {
         case 4:
         case 5:
         case 6:
            byte[] send_key;
            byte[] recv_key;
            try {
               send_key = this.getSendKey();
               recv_key = this.getRecvKey();
               if (traceEnabled) {
                  TDumpByte td = new TDumpByte();
                  td.printDump("/tplle/crypFinishTwo/send_key", send_key);
                  td.printDump("/tplle/crypFinishTwo/recv_key", recv_key);
               }
            } catch (Exception var6) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ERROR: Diffie-Hellman exchange failed");
               }

               return 9;
            }

            this.generateFingerprint(send_key, recv_key);
            this.lle_state = 10;
            this.Init(this.h_snd_initialized, this.h_snd_i, this.h_snd_j, this.h_snd_table, send_key);
            this.Init(this.h_rcv_initialized, this.h_rcv_i, this.h_rcv_j, this.h_rcv_table, recv_key);
         default:
            if (traceEnabled) {
               ntrace.doTrace("]/tplle/crypFinishTwo/20/" + retval);
            }

            return retval;
      }
   }

   public int crypGetRBuf(byte[] buf, int offset, int count) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tplle/crypGetRBuf/" + offset + "/" + count);
      }

      if (TCLicenseManager.getInstalledEncryption() == 1) {
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetRBuf/10/1");
         }

         return 1;
      } else if (this.lle_state != 10) {
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetRBuf/20/1");
         }

         return 1;
      } else if (buf == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetRBuf/30/1");
         }

         return 1;
      } else {
         this.Update(this.h_rcv_initialized, this.h_rcv_i, this.h_rcv_j, this.h_rcv_table, buf, offset, count);
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetRBuf/40/0");
         }

         return 0;
      }
   }

   public int crypGetSBuf(byte[] buf, int count) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tplle/crypGetSBuf/");
      }

      if (TCLicenseManager.getInstalledEncryption() == 1) {
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetSBuf/10/1");
         }

         return 1;
      } else if (this.lle_state != 10) {
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetSBuf/20/1");
         }

         return 1;
      } else if (buf == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetSBuf/30/1");
         }

         return 1;
      } else {
         this.Update(this.h_snd_initialized, this.h_snd_i, this.h_snd_j, this.h_snd_table, buf, 0, count);
         if (traceEnabled) {
            ntrace.doTrace("]/tplle/crypGetRBuf/40/0");
         }

         return 0;
      }
   }

   public byte[] getFingerprint() {
      return this.fingerprint;
   }

   private int encodeDH1(byte[] buf, int offset) {
      offset += this.encodePktHdr(buf, offset, 1);
      offset += Utilities.baWriteInt(this.lle_flags, buf, offset);
      if ((this.lle_flags & 1) != 0) {
         offset += Utilities.baWriteInt(0, buf, offset);
         offset += Utilities.baWriteInt(0, buf, offset);
         offset += Utilities.baWriteInt(0, buf, offset);
      } else {
         offset += Utilities.baWriteInt(this.lle_prime_len, buf, offset);
         offset += Utilities.baWriteInt(this.lle_pub_len, buf, offset);
         offset += Utilities.baWriteInt(0, buf, offset);
         System.arraycopy(this.params, 0, buf, offset, this.lle_prime_len);
         offset += Utilities.roundup4(this.lle_prime_len);
         System.arraycopy(this.yy1, 0, buf, offset, this.lle_pub_len);
         offset += this.yy1.length;
      }

      return offset;
   }

   private int decodeDH1(byte[] buf, int offset) {
      offset += this.decodePktHdr(buf, 0);
      this.lle_flags = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.lle_prime_len = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.lle_pub_len = Utilities.baReadInt(buf, offset);
      offset += 4;
      offset += 4;
      if (this.lle_prime_len > 0) {
         this.params = new byte[this.lle_prime_len];
         this.yy2 = new byte[this.lle_pub_len];
         System.arraycopy(buf, offset, this.params, 0, this.lle_prime_len);
         offset += Utilities.roundup4(this.lle_prime_len);
         System.arraycopy(buf, offset, this.yy2, 0, this.lle_pub_len);
         offset += this.lle_pub_len;
      }

      return offset;
   }

   private int encodeDH2(byte[] buf, int offset) {
      if (ntrace.isTraceEnabled(32)) {
         ntrace.doTrace("negotiated_flags " + this.negotiated_flags);
         ntrace.doTrace("yy1_len " + this.yy1.length);
         ntrace.doTrace("sendDisclosureLen " + this.sendDisclosureLen + ", recvDisclosureLen " + this.recvDisclosureLen);
      }

      offset += this.encodePktHdr(buf, offset, 2);
      offset += Utilities.baWriteInt(this.negotiated_flags, buf, offset);
      offset += Utilities.baWriteInt(this.yy1.length, buf, offset);
      offset += Utilities.baWriteInt(this.sendDisclosureLen, buf, offset);
      offset += Utilities.baWriteInt(this.recvDisclosureLen, buf, offset);
      System.arraycopy(this.yy1, 0, buf, offset, this.yy1.length);
      if (this.sendDisclosureLen > 0) {
         offset += Utilities.roundup4(this.yy1.length);
         System.arraycopy(this.sendKey, 0, buf, offset, this.sendDisclosureLen);
         offset += Utilities.roundup4(this.sendDisclosureLen);
         System.arraycopy(this.recvKey, 0, buf, offset, this.recvDisclosureLen);
         offset += this.recvDisclosureLen;
      } else {
         offset += this.yy1.length;
      }

      return offset;
   }

   private int decodeDH2(byte[] buf, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tplle/decodeDH2/" + offset);
      }

      offset += this.decodePktHdr(buf, offset);
      this.negotiated_flags = Utilities.baReadInt(buf, offset);
      offset += 4;
      int yy2_len = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.recvDisclosureLen = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.sendDisclosureLen = Utilities.baReadInt(buf, offset);
      offset += 4;
      if (ntrace.isTraceEnabled(32)) {
         ntrace.doTrace("negotiated_flags " + this.negotiated_flags);
         ntrace.doTrace("yy2_len " + yy2_len);
         ntrace.doTrace("sendDisclosureLen " + this.sendDisclosureLen + ", recvDisclosureLen " + this.recvDisclosureLen);
      }

      this.yy2 = new byte[yy2_len];
      System.arraycopy(buf, offset, this.yy2, 0, yy2_len);
      this.sendDisclosure = new byte[this.sendDisclosureLen];
      this.recvDisclosure = new byte[this.recvDisclosureLen];
      if (this.sendDisclosureLen > 0) {
         offset += Utilities.roundup4(yy2_len);
         System.arraycopy(buf, offset, this.recvDisclosure, 0, this.recvDisclosureLen);
         offset += Utilities.roundup4(this.recvDisclosureLen);
         System.arraycopy(buf, offset, this.sendDisclosure, 0, this.sendDisclosureLen);
         offset += this.sendDisclosureLen;
      } else {
         offset += yy2_len;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tplle/decodeDH2/10/" + offset);
      }

      return offset;
   }

   private int encodeNACK(byte[] buf, int offset, int reason, int data) {
      offset += this.encodePktHdr(buf, offset, 0);
      offset += Utilities.baWriteInt(reason, buf, offset);
      offset += Utilities.baWriteInt(data, buf, offset);
      return offset;
   }

   private int decodeNACK(byte[] buf, int offset) {
      offset += this.decodePktHdr(buf, offset);
      this.lle_reason = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.lle_data = Utilities.baReadInt(buf, offset);
      offset += 4;
      return offset;
   }

   private int encodePktHdr(byte[] buf, int offset, int type) {
      offset += Utilities.baWriteInt(2091903911, buf, offset);
      offset += Utilities.baWriteInt(type, buf, offset);
      offset += Utilities.baWriteInt(0, buf, offset);
      Utilities.baWriteInt(0, buf, offset);
      return 16;
   }

   private int decodePktHdr(byte[] buf, int offset) {
      this.lle_pkt_magic = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.lle_pkt_type = Utilities.baReadInt(buf, offset);
      offset += 4;
      this.lle_pkt_protocol = Utilities.baReadInt(buf, offset);
      offset += 4;
      offset += 4;
      return 16;
   }

   private byte[] getParameters() throws Exception {
      int pos = 0;
      int len = false;
      if (this.ber == null) {
         throw new Exception("BER parameters not set");
      } else {
         byte[] parms = new byte[this.ber.length];

         for(int i = 0; i < this.ber.length; ++i) {
            parms[i] = (byte)this.ber[i];
         }

         if (this.ber[pos++] != 48) {
            throw new Exception("BER parameters have wrong format, " + pos);
         } else {
            pos += this.lenHeader(pos);
            if (this.ber[pos++] != 6) {
               throw new Exception("BER parameters have wrong format, " + pos);
            } else {
               pos = pos + this.lenHeader(pos) + this.lenContent(pos);
               if (this.ber[pos++] != 48) {
                  throw new Exception("BER parameters have wrong format, " + pos);
               } else {
                  pos += this.lenHeader(pos);
                  if (this.ber[pos++] != 2) {
                     throw new Exception("BER parameters have wrong format, " + pos);
                  } else {
                     int len = this.lenContent(pos);
                     pos += this.lenHeader(pos);
                     byte[] tmp = new byte[len];

                     int i;
                     for(i = 0; i < len; ++i) {
                        tmp[i] = (byte)this.ber[pos + i];
                     }

                     this.p = new BigInteger(tmp);
                     pos += len;
                     if (this.ber[pos++] != 2) {
                        throw new Exception("BER parameters have wrong format, " + pos);
                     } else {
                        len = this.lenContent(pos);
                        pos += this.lenHeader(pos);
                        tmp = new byte[len];

                        for(i = 0; i < len; ++i) {
                           tmp[i] = (byte)this.ber[pos + i];
                        }

                        this.g = new BigInteger(tmp);
                        return parms;
                     }
                  }
               }
            }
         }
      }
   }

   private int lenContent(int pos) {
      if (this.ber[pos] < 128) {
         return this.ber[pos];
      } else {
         int lenlen = this.ber[pos] - 128;
         int result = 0;

         for(int i = 0; i < lenlen; ++i) {
            result = result * 256 + this.ber[pos + 1 + i];
         }

         return result;
      }
   }

   private int lenHeader(int pos) {
      return this.ber[pos] < 128 ? 1 : this.ber[pos] - 128 + 1;
   }

   private byte[] getMyPublicValue() throws Exception {
      if (this.g != null && this.p != null) {
         if (rnd == null) {
            rnd = new SecureRandom();
         }

         try {
            rnd.setSeed(System.currentTimeMillis());
            rnd.setSeed(Runtime.getRuntime().freeMemory());
            rnd.setSeed(Runtime.getRuntime().totalMemory());
            rnd.setSeed(System.getProperty("java.version", "default").getBytes());
            rnd.setSeed(System.getProperty("java.vendor", "default").getBytes());
            rnd.setSeed(System.getProperty("os.name", "default").getBytes());
            rnd.setSeed(System.getProperty("os.version", "default").getBytes());
            rnd.setSeed(System.getProperty("user.name", "default").getBytes());
            rnd.setSeed(System.getProperty("user.dir", "default").getBytes());
            rnd.setSeed(System.getProperty("user.home", "default").getBytes());
            rnd.setSeed(System.getProperty("java.home", "default").getBytes());
            rnd.setSeed(System.getProperty("java.class.path", "default").getBytes());
            rnd.setSeed(System.currentTimeMillis());
         } catch (Exception var2) {
         }

         this.x1 = new BigInteger(128, rnd);
         this.y1 = this.g.modPow(this.x1, this.p);
         return unpad(this.y1.toByteArray());
      } else {
         throw new Exception("must get parameters before public value");
      }
   }

   private void acceptOtherPublicValue(byte[] y2bytes, byte[] sendDisclosure, byte[] recvDisclosure) throws Exception {
      if (this.g != null && this.p != null && this.x1 != null) {
         if (sendDisclosure.length != recvDisclosure.length) {
            throw new Exception("disclosure value mismatch");
         } else {
            short strength;
            if (sendDisclosure.length == 0) {
               strength = 128;
            } else if (sendDisclosure.length == 9) {
               strength = 56;
            } else {
               if (sendDisclosure.length != 11) {
                  throw new Exception("bad disclosure value length");
               }

               strength = 40;
            }

            this.y2 = new BigInteger(1, y2bytes);
            BigInteger secret = this.y2.modPow(this.x1, this.p);
            this.x1 = null;
            this.p = null;
            this.g = null;
            this.y1 = null;
            this.y2 = null;
            this.sendKey = new byte[16];
            this.recvKey = new byte[16];
            byte[] secretBytes = removePadding(secret.toByteArray(), y2bytes.length);
            secret = null;

            int i;
            for(i = 0; i < this.recvKey.length; ++i) {
               this.recvKey[i] = secretBytes[i];
            }

            for(i = 0; i < this.sendKey.length; ++i) {
               this.sendKey[i] = secretBytes[secretBytes.length / 2 + i];
            }

            for(i = 0; i < secretBytes.length; ++i) {
               secretBytes[i] = 0;
            }

            byte[] secretBytes = null;
            if (recvDisclosure.length > 0) {
               for(i = 0; i < recvDisclosure.length; ++i) {
                  if (recvDisclosure[i] != this.recvKey[i]) {
                     throw new Exception("R disclosure value error " + i);
                  }

                  this.recvKey[i] = recvDisclosure[i];
               }

               for(i = 0; i < sendDisclosure.length; ++i) {
                  if (sendDisclosure[i] != this.sendKey[i]) {
                     throw new Exception("S disclosure value error " + i);
                  }

                  this.sendKey[i] = sendDisclosure[i];
               }
            }

            if (strength == 56) {
               this.SWAPBYTES(this.sendKey, 0, 13);
               this.SWAPBYTES(this.sendKey, 8, 9);
               this.SWAPBYTES(this.sendKey, 7, 11);
               this.SWAPBYTES(this.sendKey, 3, 4);
               this.SWAPBYTES(this.recvKey, 0, 13);
               this.SWAPBYTES(this.recvKey, 8, 9);
               this.SWAPBYTES(this.recvKey, 7, 11);
               this.SWAPBYTES(this.recvKey, 3, 4);
            } else if (strength == 40) {
               byte[] saveme = new byte[5];

               int pd;
               for(pd = 0; pd < saveme.length; ++pd) {
                  saveme[pd] = this.sendKey[pd + 11];
               }

               for(pd = 10; pd >= 0; --pd) {
                  this.sendKey[pd + 5] = this.sendKey[pd];
               }

               for(pd = 0; pd < saveme.length; ++pd) {
                  this.sendKey[pd] = saveme[pd];
               }

               for(pd = 0; pd < saveme.length; ++pd) {
                  saveme[pd] = this.recvKey[pd + 11];
               }

               for(pd = 10; pd >= 0; --pd) {
                  this.recvKey[pd + 5] = this.recvKey[pd];
               }

               for(pd = 0; pd < saveme.length; ++pd) {
                  this.recvKey[pd] = saveme[pd];
               }
            }

         }
      } else {
         throw new Exception("must get own public value first");
      }
   }

   private void SWAPBYTES(byte[] a, int x, int y) {
      byte tmp = a[x];
      a[x] = a[y];
      a[y] = tmp;
   }

   private byte[] getSendKey() throws Exception {
      if (this.sendKey == null) {
         throw new Exception("no send key");
      } else {
         byte[] result = new byte[this.sendKey.length];

         for(int i = 0; i < this.sendKey.length; ++i) {
            result[i] = this.sendKey[i];
            this.sendKey[i] = 0;
         }

         this.sendKey = null;
         return result;
      }
   }

   private byte[] getRecvKey() throws Exception {
      if (this.recvKey == null) {
         throw new Exception("no recv key");
      } else {
         byte[] result = new byte[this.recvKey.length];

         for(int i = 0; i < this.recvKey.length; ++i) {
            result[i] = this.recvKey[i];
            this.recvKey[i] = 0;
         }

         this.recvKey = null;
         return result;
      }
   }

   private static byte[] unpad(byte[] b) {
      if (b[0] == 0 && b[1] < 0) {
         byte[] fixedBytes = new byte[b.length - 1];

         for(int i = 0; i < fixedBytes.length; ++i) {
            fixedBytes[i] = b[i + 1];
            b[i + 1] = 0;
         }

         return fixedBytes;
      } else {
         return b;
      }
   }

   private void Init(boolean[] c_initialized, int[] c_i, int[] c_j, byte[] c_table, byte[] key) {
      c_i[0] = c_j[0] = 0;
      c_initialized[0] = true;
      byte[] table = c_table;
      int keyLen = key.length;
      byte[] keyPtr = key;

      int i;
      for(i = 0; i < 256; ++i) {
         table[i] = (byte)i;
      }

      int k = 0;
      int j = 0;
      i = 0;

      while(i < 256) {
         int ti = table[i];
         int tk = keyPtr[k];
         j += tk;
         j += ti;
         j &= 255;
         int tj = table[j];
         ++k;
         ++i;
         table[i - 1] = (byte)tj;
         table[j] = (byte)ti;
         if (k == keyLen) {
            k = 0;
         }
      }

   }

   private int Update(boolean[] c_initialized, int[] c_i, int[] c_j, byte[] c_table, byte[] data, int start_pos, int len) {
      if (start_pos < 0) {
         start_pos = 0;
      }

      if (start_pos >= data.length) {
         return 0;
      } else {
         if (start_pos + len > data.length) {
            len = data.length - start_pos;
         }

         if (!c_initialized[0]) {
            return 11;
         } else {
            int i = c_i[0];
            int j = c_j[0];
            byte[] table = c_table;
            int pInput = start_pos;
            int pOutput = start_pos;

            byte ttmp;
            byte tj;
            for(int pLastInput = start_pos + len - 1; pInput <= pLastInput; data[pOutput++] = (byte)(ttmp ^ tj)) {
               i = i + 1 & 255;
               int ti = table[i];
               j = j + ti & 255;
               int tj = table[j];
               table[i] = (byte)tj;
               table[j] = (byte)ti;
               tj = tj + ti & 255;
               tj = table[tj];
               ttmp = data[pInput++];
            }

            c_i[0] = i;
            c_j[0] = j;
            return 0;
         }
      }
   }

   private void generateFingerprint(byte[] sndKey, byte[] rcvKey) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tplle/generateFingerprint/");
      }

      byte[] keycombo = new byte[16];

      for(int i = 0; i < 16; ++i) {
         keycombo[i] = (byte)(sndKey[i] ^ rcvKey[i]);
      }

      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         md.update(keycombo, 0, 16);
         this.fingerprint = md.digest();
      } catch (NoSuchAlgorithmException var7) {
         if (traceEnabled) {
            ntrace.doTrace("/tplle/generateFingerprint/" + var7);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tplle/generateFingerprint/");
      }

   }

   private static byte[] removePadding(byte[] b, int size) {
      if (b.length == size) {
         return b;
      } else {
         byte[] fixedByte = new byte[size];
         int delta = size - b.length;
         int i;
         int j;
         if (delta > 0) {
            i = size - 1;

            for(j = i - delta; i >= delta; --j) {
               fixedByte[i] = b[j];
               --i;
            }

            for(i = 0; i < delta; ++i) {
               fixedByte[i] = 0;
            }
         } else {
            i = 0;

            for(j = 0 - delta; i < size; ++j) {
               fixedByte[i] = b[j];
               ++i;
            }
         }

         Arrays.fill(b, (byte)0);
         return fixedByte;
      }
   }
}
