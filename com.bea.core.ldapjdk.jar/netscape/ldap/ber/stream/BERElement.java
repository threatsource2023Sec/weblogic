package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public abstract class BERElement implements Serializable {
   public static final int BOOLEAN = 1;
   public static final int INTEGER = 2;
   public static final int BITSTRING = 3;
   public static final int OCTETSTRING = 4;
   public static final int NULL = 5;
   public static final int OBJECTID = 6;
   public static final int REAL = 9;
   public static final int ENUMERATED = 10;
   public static final int SET = 49;
   public static final int SEQUENCE = 48;
   public static final int NUMERICSTRING = 18;
   public static final int PRINTABLESTRING = 19;
   public static final int TELETEXSTRING = 20;
   public static final int VIDEOTEXSTRING = 21;
   public static final int IA5STRING = 22;
   public static final int UTCTIME = 23;
   public static final int GRAPHICSTRING = 25;
   public static final int VISIBLESTRING = 26;
   public static final int GENERALSTRING = 27;
   public static final int TAG = -1;
   public static final int CHOICE = -2;
   public static final int ANY = -3;
   public static final int EOC = 0;
   public static final int UNIVERSAL = 0;
   public static final int APPLICATION = 64;
   public static final int CONTEXT = 128;
   public static final int SASLCONTEXT = 160;
   public static final int PRIVATE = 192;
   public static final int PRIMITIVE = 0;
   public static final int CONSTRUCTED = 32;
   public static final int MRA_OID = 1;
   public static final int MRA_TYPE = 2;
   public static final int MRA_VALUE = 3;
   public static final int MRA_DNATTRS = 4;
   public static final int EXOP_REQ_OID = 0;
   public static final int EXOP_REQ_VALUE = 1;
   public static final int EXOP_RES_OID = 10;
   public static final int EXOP_RES_VALUE = 11;
   public static final int SK_MATCHRULE = 0;
   public static final int SK_REVERSE = 1;
   public static final int SR_ATTRTYPE = 0;

   public static BERElement getElement(BERTagDecoder var0, InputStream var1, int[] var2) throws IOException {
      Object var3 = null;
      int var4 = var1.read();
      var2[0] = 1;
      if (var4 == 0) {
         var1.read();
         var2[0] = 1;
         var3 = null;
      } else if (var4 == 1) {
         var3 = new BERBoolean(var1, var2);
      } else if (var4 == 2) {
         var3 = new BERInteger(var1, var2);
      } else if (var4 == 3) {
         var3 = new BERBitString(var1, var2);
      } else if (var4 == 35) {
         var3 = new BERBitString(var0, var1, var2);
      } else if (var4 == 4) {
         var3 = new BEROctetString(var1, var2);
      } else if (var4 == 36) {
         var3 = new BEROctetString(var0, var1, var2);
      } else if (var4 == 5) {
         var3 = new BERNull(var1, var2);
      } else if (var4 == 6) {
         var3 = new BERObjectId(var1, var2);
      } else if (var4 == 9) {
         var3 = new BERReal(var1, var2);
      } else if (var4 == 10) {
         var3 = new BEREnumerated(var1, var2);
      } else if (var4 == 48) {
         var3 = new BERSequence(var0, var1, var2);
      } else if (var4 == 49) {
         var3 = new BERSet(var0, var1, var2);
      } else if (var4 == 18) {
         var3 = new BERNumericString(var1, var2);
      } else if (var4 == 50) {
         var3 = new BERNumericString(var0, var1, var2);
      } else if (var4 == 19) {
         var3 = new BERPrintableString(var1, var2);
      } else if (var4 == 51) {
         var3 = new BERPrintableString(var0, var1, var2);
      } else if (var4 == 23) {
         var3 = new BERUTCTime(var1, var2);
      } else if (var4 == 55) {
         var3 = new BERUTCTime(var0, var1, var2);
      } else if (var4 == 26) {
         var3 = new BERVisibleString(var1, var2);
      } else if (var4 == 58) {
         var3 = new BERVisibleString(var0, var1, var2);
      } else {
         if ((var4 & 192) <= 0) {
            throw new IOException("invalid tag " + var4);
         }

         var3 = new BERTag(var0, var4, var1, var2);
      }

      return (BERElement)var3;
   }

   public static int readLengthOctets(InputStream var0, int[] var1) throws IOException {
      int var2 = 0;
      int var3 = var0.read();
      int var10002 = var1[0]++;
      if (var3 == 128) {
         var2 = -1;
      } else if ((var3 & 128) > 0) {
         int var4 = var3 & 127;

         for(int var5 = 0; var5 < var4; ++var5) {
            var3 = var0.read();
            var10002 = var1[0]++;
            var2 = (var2 << 8) + var3;
         }
      } else {
         var2 = var3;
      }

      return var2;
   }

   public static void sendDefiniteLength(OutputStream var0, int var1) throws IOException {
      boolean var2 = false;
      if (var1 <= 127) {
         var0.write(var1);
      } else {
         int var3 = 0;
         int var4 = var1;

         while(var4 >= 0) {
            ++var3;
            var4 >>= 8;
            if (var4 <= 0) {
               break;
            }
         }

         byte[] var5 = new byte[var3 + 1];
         var5[0] = (byte)(128 | var3);
         var4 = var1;

         for(int var6 = var3; var6 > 0; --var6) {
            var5[var6] = (byte)(var4 & 255);
            var4 >>= 8;
         }

         var0.write(var5);
      }

   }

   protected int readUnsignedBinary(InputStream var1, int[] var2, int var3) throws IOException {
      int var4 = 0;

      for(int var6 = 0; var6 < var3; ++var6) {
         int var5 = var1.read();
         int var10002 = var2[0]++;
         var4 = (var4 << 8) + var5;
      }

      return var4;
   }

   protected int readTwosComplement(InputStream var1, int[] var2, int var3) throws IOException {
      int var4 = 0;
      if (var3 > 0) {
         boolean var5 = false;
         int var6 = var1.read();
         int var10002 = var2[0]++;
         if ((var6 & 128) > 0) {
            var5 = true;
         }

         for(int var7 = 0; var7 < var3; ++var7) {
            if (var7 > 0) {
               var6 = var1.read();
               var10002 = var2[0]++;
            }

            if (var5) {
               var4 = (var4 << 8) + (var6 ^ 255) & 255;
            } else {
               var4 = (var4 << 8) + (var6 & 255);
            }
         }

         if (var5) {
            var4 = (var4 + 1) * -1;
         }
      }

      return var4;
   }

   public String byteToHexString(byte var1) {
      return var1 < 0 ? Integer.toHexString((var1 & 127) + 128) : Integer.toHexString(var1);
   }

   public abstract void write(OutputStream var1) throws IOException;

   public abstract int getType();

   public abstract String toString();
}
