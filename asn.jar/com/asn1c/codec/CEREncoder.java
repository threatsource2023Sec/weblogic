package com.asn1c.codec;

import com.asn1c.core.BadValueException;
import com.asn1c.core.BitString;
import com.asn1c.core.CharacterString;
import com.asn1c.core.DataValue;
import com.asn1c.core.EmbeddedPDV;
import com.asn1c.core.External;
import com.asn1c.core.GeneralizedTime;
import com.asn1c.core.Identification;
import com.asn1c.core.Null;
import com.asn1c.core.ObjectDescriptor;
import com.asn1c.core.ObjectIdentifier;
import com.asn1c.core.OctetString;
import com.asn1c.core.String16;
import com.asn1c.core.String32;
import com.asn1c.core.TrimmedBitString;
import com.asn1c.core.UTCTime;
import com.asn1c.core.UnitString;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Vector;

public abstract class CEREncoder extends FilterEncoder {
   private Vector embeddedPdvIdentificationCache;
   private Vector characterStringIdentificationCache;
   private static int done = -1;
   private static final int[] tab = new int[]{0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};
   private Hashtable encoderCache;
   private int currentTag;
   private DataEncoder currentEncoder;
   private boolean isIndefiniteLengthEncoder;

   public CEREncoder(Encoder var1, String var2, byte[] var3) {
      super(var1);
      if (done == -1) {
         examine(var3);
      }

      this.embeddedPdvIdentificationCache = new Vector();
      this.characterStringIdentificationCache = new Vector();
      this.encoderCache = new Hashtable();
      this.encoderCache.put(var2, this);
   }

   protected CEREncoder(Encoder var1, CEREncoder var2, boolean var3) {
      super(var1);
      if (done == -1) {
         throw new InternalError();
      } else {
         this.embeddedPdvIdentificationCache = new Vector();
         this.characterStringIdentificationCache = new Vector();
         this.encoderCache = var2.encoderCache;
         this.isIndefiniteLengthEncoder = var3;
      }
   }

   private static final void examine(byte[] var0) {
      done = 0;
      int var1 = 0;

      int var2;
      for(var2 = 0; var2 < var0.length - 4; ++var2) {
         var1 = tab[(var1 ^ var0[var2]) & 255] ^ var1 >>> 8;
      }

      if (var1 == ((var0[var2] & 255) << 24 | (var0[var2 + 1] & 255) << 16 | (var0[var2 + 2] & 255) << 8 | var0[var2 + 3] & 255)) {
         var1 = 0;

         for(var2 = 0; var2 < License.data.length - 4; ++var2) {
            var1 = tab[(var1 ^ License.data[var2]) & 255] ^ var1 >>> 8;
         }

         if (var1 == ((License.data[var2] & 255) << 24 | (License.data[var2 + 1] & 255) << 16 | (License.data[var2 + 2] & 255) << 8 | License.data[var2 + 3] & 255)) {
            done = 1;
         }
      }
   }

   public void joinEncoder(CEREncoder var1) {
      if (super.out != var1.out) {
         throw new IllegalArgumentException("cannot join encoders of different input streams");
      } else {
         this.encoderCache.putAll(var1.encoderCache);
         var1.encoderCache = this.encoderCache;
         var1.embeddedPdvIdentificationCache = this.embeddedPdvIdentificationCache;
         var1.characterStringIdentificationCache = this.characterStringIdentificationCache;
      }
   }

   protected CEREncoder getEncoder(String var1) {
      return (CEREncoder)this.encoderCache.get(var1);
   }

   public void writeTagLength(int var1, int var2, boolean var3) throws IOException {
      if (done != 1) {
         boolean var6 = false;
      }

      int var5 = var1 >> 24 & 224;
      int var7 = var1 & 536870911;
      if (var7 < 31) {
         this.writeByte((byte)(var5 | var7));
      } else if (var7 < 128) {
         this.writeByte((byte)(var5 | 31));
         this.writeByte((byte)var7);
      } else if (var7 < 16384) {
         this.writeByte((byte)(var5 | 31));
         this.writeByte((byte)(var7 >> 7 | 128));
         this.writeByte((byte)(var7 & 127));
      } else if (var7 < 2097152) {
         this.writeByte((byte)(var5 | 31));
         this.writeByte((byte)(var7 >> 14 | 128));
         this.writeByte((byte)(var7 >> 7 | 128));
         this.writeByte((byte)(var7 & 127));
      } else if (var7 < 268435456) {
         this.writeByte((byte)(var5 | 31));
         this.writeByte((byte)(var7 >> 21 | 128));
         this.writeByte((byte)(var7 >> 14 | 128));
         this.writeByte((byte)(var7 >> 7 | 128));
         this.writeByte((byte)(var7 & 127));
      } else {
         this.writeByte((byte)(var5 | 31));
         this.writeByte((byte)(var7 >> 28 | 128));
         this.writeByte((byte)(var7 >> 21 | 128));
         this.writeByte((byte)(var7 >> 14 | 128));
         this.writeByte((byte)(var7 >> 7 | 128));
         this.writeByte((byte)(var7 & 127));
      }

      if (var3) {
         this.writeByte((byte)-128);
      } else if (var2 < 128) {
         this.writeByte((byte)var2);
      } else if (var2 < 256) {
         this.writeByte((byte)-127);
         this.writeByte((byte)var2);
      } else if (var2 < 65536) {
         this.writeByte((byte)-126);
         this.writeByte((byte)(var2 >> 8));
         this.writeByte((byte)var2);
      } else if (var2 < 16777216) {
         this.writeByte((byte)-125);
         this.writeByte((byte)(var2 >> 16));
         this.writeByte((byte)(var2 >> 8));
         this.writeByte((byte)var2);
      } else {
         this.writeByte((byte)-124);
         this.writeByte((byte)(var2 >> 24));
         this.writeByte((byte)(var2 >> 16));
         this.writeByte((byte)(var2 >> 8));
         this.writeByte((byte)var2);
      }

   }

   public CEREncoder createExplicitTagEncoder(int var1, boolean var2) throws IOException {
      if (var2) {
         this.writeTagLength(var1 | 536870912, 0, true);
         return this.createExtensionClone(this, true);
      } else {
         this.currentTag = var1 | 536870912;
         return this.createExtensionClone(new DataEncoder(), false);
      }
   }

   public void writeEndOfContents(CEREncoder var1) throws IOException {
      if (var1.isIndefiniteLengthEncoder) {
         this.writeByte((byte)0);
         this.writeByte((byte)0);
      } else {
         BitString var2 = ((DataEncoder)var1.out).getEncodedData();
         this.writeTagLength(this.currentTag, var2.octetLength(), false);
         this.writeBits(var2);
      }

   }

   public abstract CEREncoder createExtensionClone(Encoder var1, boolean var2);

   public void writeNull(Null var1, int var2) throws IOException {
      this.writeTagLength(var2, 0, false);
   }

   public void writeBoolean(boolean var1, int var2) throws IOException {
      this.writeTagLength(var2, 1, false);
      if (var1) {
         this.writeByte((byte)-1);
      } else {
         this.writeByte((byte)0);
      }

   }

   public void writeIntegerS8(byte var1, int var2) throws IOException {
      int var3 = octetLength(var1);
      this.writeTagLength(var2, var3, false);
      this.writeSInteger(var1, var3 * 8);
   }

   public void writeIntegerS16(short var1, int var2) throws IOException {
      int var3 = octetLength(var1);
      this.writeTagLength(var2, var3, false);
      this.writeSInteger(var1, var3 * 8);
   }

   public void writeIntegerS32(int var1, int var2) throws IOException {
      int var3 = octetLength(var1);
      this.writeTagLength(var2, var3, false);
      this.writeSInteger(var1, var3 * 8);
   }

   public void writeIntegerS64(long var1, int var3) throws IOException {
      int var4 = octetLength(var1);
      this.writeTagLength(var3, var4, false);
      this.writeSLong(var1, var4 * 8);
   }

   public void writeIntegerSX(BigInteger var1, int var2) throws IOException {
      int var3 = octetLength(var1);
      this.writeTagLength(var2, var3, false);
      this.writeSBigInteger(var1, var3 * 8);
   }

   public void writeRealS64(double var1, int var3) throws IOException, BadValueException {
      BitString var4 = new BitString();
      if (Double.isInfinite(var1)) {
         if (var1 > 0.0) {
            var4.appendOctet((byte)1);
            var4.appendOctet((byte)64);
         } else {
            var4.appendOctet((byte)1);
            var4.appendOctet((byte)65);
         }
      } else {
         if (Double.isNaN(var1)) {
            throw new BadValueException(Double.toString(var1));
         }

         if (var1 != 0.0) {
            long var5 = Double.doubleToLongBits(var1);
            boolean var7 = (var5 & Long.MIN_VALUE) != 0L;
            int var8 = ((int)(var5 >> 52) & 2047) - 1075;

            long var9;
            for(var9 = var8 == 0 ? (var5 & 4503599627370495L) << 1 : var5 & 4503599627370495L | 4503599627370496L; (var9 & 1L) == 0L; ++var8) {
               var9 >>>= 1;
            }

            if (var8 >= -128 && var8 <= 127) {
               var4.appendOctet((byte)(128 | (var7 ? 64 : 0)));
               var4.appendOctet((byte)var8);
            } else if (var8 >= -32768 && var8 <= 32767) {
               var4.appendOctet((byte)(128 | (var7 ? 64 : 0) | 1));
               var4.appendOctet((byte)(var8 >> 8));
               var4.appendOctet((byte)var8);
            } else if (var8 >= -8388608 && var8 <= 8388607) {
               var4.appendOctet((byte)(128 | (var7 ? 64 : 0) | 2));
               var4.appendOctet((byte)(var8 >> 16));
               var4.appendOctet((byte)(var8 >> 8));
               var4.appendOctet((byte)var8);
            } else {
               var4.appendOctet((byte)(128 | (var7 ? 64 : 0) | 3));
               var4.appendOctet((byte)4);
               var4.appendOctet((byte)(var8 >> 24));
               var4.appendOctet((byte)(var8 >> 16));
               var4.appendOctet((byte)(var8 >> 8));
               var4.appendOctet((byte)var8);
            }

            if ((var9 & -72057594037927936L) != 0L) {
               var4.appendULong(var9, 64);
            } else if ((var9 & 71776119061217280L) != 0L) {
               var4.appendULong(var9, 56);
            } else if ((var9 & 280375465082880L) != 0L) {
               var4.appendULong(var9, 48);
            } else if ((var9 & 1095216660480L) != 0L) {
               var4.appendULong(var9, 40);
            } else if ((var9 & 4278190080L) != 0L) {
               var4.appendULong(var9, 32);
            } else if ((var9 & 16711680L) != 0L) {
               var4.appendULong(var9, 24);
            } else if ((var9 & 65280L) != 0L) {
               var4.appendULong(var9, 16);
            } else {
               var4.appendULong(var9, 8);
            }
         }
      }

      int var11 = var4.octetLength();
      this.writeTagLength(var3, var11, false);
      this.writeBits(var4);
   }

   public void writeBitString(BitString var1, int var2) throws IOException {
      int var3 = var1.bitLength();
      if ((var3 + 7) / 8 + 1 <= 1000) {
         this.writeTagLength(var2, (var3 + 7) / 8 + 1, false);
         this.writeSInteger(-var3 & 7, 8);
         this.writeBits(var1);
         if ((var3 & 7) != 0) {
            this.writeSInteger(0, -var3 & 7);
         }
      } else {
         CEREncoder var4 = this.createExplicitTagEncoder(var2, true);

         for(int var5 = 0; var5 < var3; var5 += 7992) {
            int var6 = var3 - var5 > 7992 ? 7992 : var3 - var5;
            this.writeTagLength(3, (var6 + 7) / 8 + 1, false);
            this.writeSInteger(-var6 & 7, 8);
            this.write(var1, var5, var6);
            if ((var6 & 7) != 0) {
               this.writeSInteger(0, -var6 & 7);
            }
         }

         this.writeEndOfContents(var4);
      }

   }

   public void writeTrimmedBitString(TrimmedBitString var1, int var2) throws IOException {
      int var3 = var1.bitLength();
      if ((var3 + 7) / 8 + 1 <= 1000) {
         this.writeTagLength(var2, (var3 + 7) / 8 + 1, false);
         this.writeSInteger(-var3 & 7, 8);
         this.writeBits(var1);
         if ((var3 & 7) != 0) {
            this.writeSInteger(0, -var3 & 7);
         }
      } else {
         CEREncoder var4 = this.createExplicitTagEncoder(var2, true);

         for(int var5 = 0; var5 < var3; var5 += 7992) {
            int var6 = var3 - var5 > 7992 ? 7992 : var3 - var5;
            this.writeTagLength(3, (var6 + 7) / 8 + 1, false);
            this.writeSInteger(-var6 & 7, 8);
            this.write(var1, var5, var6);
            if ((var6 & 7) != 0) {
               this.writeSInteger(0, -var6 & 7);
            }
         }

         this.writeEndOfContents(var4);
      }

   }

   public void writeOctetString(OctetString var1, int var2) throws IOException {
      int var3 = var1.octetLength();
      if (var3 <= 1000) {
         this.writeTagLength(var2, var3, false);
         this.writeBits(var1);
      } else {
         CEREncoder var4 = this.createExplicitTagEncoder(var2, true);

         for(int var5 = 0; var5 < var3; var5 += 1000) {
            int var6 = var3 - var5 > 1000 ? 1000 : var3 - var5;
            this.writeTagLength(3, var6, false);
            this.write(var1, var5 * 8, var6 * 8);
         }

         this.writeEndOfContents(var4);
      }

   }

   public void writeString8(String var1, int var2) throws IOException {
      int var3 = var1.length();
      BitString var4 = new BitString();
      var4.appendString16(new String16(var1), var3, 8);
      if (var3 <= 1000) {
         this.writeTagLength(var2, var3, false);
         this.writeBits(var4);
      } else {
         CEREncoder var5 = this.createExplicitTagEncoder(var2, true);

         for(int var6 = 0; var6 < var3; var6 += 1000) {
            int var7 = var3 - var6 > 1000 ? 1000 : var3 - var6;
            this.writeTagLength(3, var7, false);
            this.write(var4, var6 * 8, var7 * 8);
         }

         this.writeEndOfContents(var5);
      }

   }

   public void writeString16(String var1, int var2) throws IOException {
      int var3 = var1.length();
      BitString var4 = new BitString();
      var4.appendString16(new String16(var1), var3, 16);
      if (var3 <= 500) {
         this.writeTagLength(var2, var3 * 2, false);
         this.writeBits(var4);
      } else {
         CEREncoder var5 = this.createExplicitTagEncoder(var2, true);

         for(int var6 = 0; var6 < var3; var6 += 500) {
            int var7 = var3 - var6 > 500 ? 500 : var3 - var6;
            this.writeTagLength(3, var7 * 2, false);
            this.write(var4, var6 * 16, var7 * 16);
         }

         this.writeEndOfContents(var5);
      }

   }

   public void writeString32(String32 var1, int var2) throws IOException {
      int var3 = var1.length();
      BitString var4 = new BitString();
      var4.appendString32(var1, var3, 32);
      if (var3 <= 250) {
         this.writeTagLength(var2, var3 * 4, false);
         this.writeBits(var4);
      } else {
         CEREncoder var5 = this.createExplicitTagEncoder(var2, true);

         for(int var6 = 0; var6 < var3; var6 += 250) {
            int var7 = var3 - var6 > 250 ? 250 : var3 - var6;
            this.writeTagLength(3, var7 * 4, false);
            this.write(var4, var6 * 32, var7 * 32);
         }

         this.writeEndOfContents(var5);
      }

   }

   public void writeObjectIdentifier(ObjectIdentifier var1, int var2) throws IOException {
      BitString var3 = new BitString();

      for(int var8 = 0; var8 < var1.length(); ++var8) {
         long var4 = var1.get(var8);
         if (var8 == 0) {
            long var10000 = var4 * 40L;
            ++var8;
            var4 = var10000 + var1.get(var8);
         }

         int var7 = septetLengthU(var4);

         for(int var6 = 0; var6 < var7 - 1; ++var6) {
            var3.appendULong(var4 >> 7 * (var7 - var6 - 1) & 127L | 128L, 8);
         }

         var3.appendULong(var4 & 127L, 8);
      }

      this.writeTagLength(var2, var3.bitLength() / 8, false);
      this.writeBits(var3);
   }

   public void writeExternal(External var1, int var2) throws IOException, BadValueException {
      CEREncoder var3 = this.createExplicitTagEncoder(var2, true);
      Identification var4 = var1.getIdentification();
      ObjectDescriptor var5 = var1.getDataValueDescriptor();
      DataValue var6 = var1.getDataValue();
      switch (var4.getSelector()) {
         case 1:
            var3.writeObjectIdentifier(var4.getSyntax(), 6);
            break;
         case 2:
            var3.writeIntegerS32(var4.getPresentationContextId(), 2);
            break;
         case 3:
            var3.writeObjectIdentifier(var4.getContextNegotiationTransferSyntax(), 6);
            var3.writeIntegerS32(var4.getContextNegotiationPresentationContextId(), 2);
            break;
         default:
            throw new BadValueException();
      }

      if (var5 != null) {
         var3.writeString8(var5.getValue().toString(), 7);
      }

      switch (var6.getSelector()) {
         case 0:
            CEREncoder var7 = var3.createExplicitTagEncoder(Integer.MIN_VALUE, true);
            var7.writeOpen(var6.getNotation().getEncoded());
            var3.writeEndOfContents(var7);
            break;
         case 1:
            if ((var6.getEncodedBitString().bitLength() & 7) != 0) {
               var3.writeBitString(var6.getEncodedBitString(), -2147483646);
            } else {
               var3.writeOctetString(new OctetString(var6.getEncodedBitString()), -2147483647);
            }
            break;
         case 2:
            var3.writeOctetString(var6.getEncodedOctetString(), -2147483647);
            break;
         default:
            throw new BadValueException();
      }

      this.writeEndOfContents(var3);
   }

   public void writeEmbeddedPDV(EmbeddedPDV var1, int var2) throws IOException, BadValueException {
      throw new InternalError("Encoding of EMBEDDED PDV for CER not implemented yet.");
   }

   public void writeCharacterString(CharacterString var1, int var2) throws IOException, BadValueException {
      throw new InternalError("Encoding of CHARACTER STRING for CER not implemented yet.");
   }

   private static void appendDecimal(int var0, StringBuffer var1, int var2) {
      int var4 = 1;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         var4 *= 10;
      }

      for(var3 = 0; var3 < var2; ++var3) {
         var4 /= 10;
         var1.append((char)(var0 / var4 % 10 + 48));
      }

   }

   private static String generalizedTimeToString(GeneralizedTime var0, int var1) throws BadValueException {
      StringBuffer var2 = new StringBuffer(23);
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      int var7 = var0.get(14);
      int var8 = var0.get(15) / '\uea60';
      appendDecimal(var0.get(1), var2, 4);
      appendDecimal(var0.get(2) + 1, var2, 2);
      appendDecimal(var0.get(5), var2, 2);
      appendDecimal(var0.get(11), var2, 2);
      if (var8 == 0 && (var1 & 4032) != 0) {
         var3 = true;
         var1 = var1 & -1073741824 | var1 >>> 6 & 63;
      } else if (var8 % 60 == 0 && (var1 & 258048) != 0) {
         var4 = true;
         var1 = var1 & -1073741824 | var1 >>> 12 & 63;
      } else if ((var1 & 16515072) != 0) {
         var4 = true;
         var1 = var1 & -1073741824 | var1 >>> 18 & 63;
      } else {
         if ((var1 & 63) == 0) {
            throw new BadValueException();
         }

         var6 = true;
         var1 = var1 & -1073741824 | var1 & 63;
      }

      if ((var1 & 62) != 0) {
         appendDecimal(var0.get(12), var2, 2);
      }

      if ((var1 & 60) != 0) {
         appendDecimal(var0.get(13), var2, 2);
      }

      if ((var1 & 56) != 0 && var7 != 0 || (var1 & 7) == 0) {
         if ((var1 & 1073741824) != 0) {
            var2.append('.');
         } else {
            var2.append(',');
         }

         if (((var1 & 8) == 0 || var7 % 100 != 0) && (var1 & 48) != 0) {
            if (((var1 & 16) == 0 || var7 % 10 != 0) && (var1 & 32) != 0) {
               appendDecimal(var7, var2, 3);
            } else {
               appendDecimal(var7 / 10, var2, 2);
            }
         } else {
            appendDecimal(var7 / 100, var2, 1);
         }
      }

      if (var3) {
         var2.append('Z');
      } else if (var4 && var8 >= 0) {
         var2.append('+');
         appendDecimal(var8 / 60, var2, 2);
      } else if (var4) {
         var2.append('-');
         appendDecimal(-var8 / 60, var2, 2);
      } else if (var5 && var8 >= 0) {
         var2.append('+');
         appendDecimal(var8 / 60, var2, 2);
         appendDecimal(var8 % 60, var2, 2);
      } else if (var5) {
         var2.append('-');
         appendDecimal(-var8 / 60, var2, 2);
         appendDecimal(-var8 % 60, var2, 2);
      }

      return var2.toString();
   }

   public void writeGeneralizedTime(GeneralizedTime var1, int var2, int var3) throws BadValueException, IOException {
      this.writeString8(generalizedTimeToString(var1, var3), var2);
   }

   private static String utcTimeToString(UTCTime var0, int var1) throws BadValueException {
      StringBuffer var2 = new StringBuffer(17);
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      int var7 = var0.get(15) / '\uea60';
      appendDecimal(var0.get(1) % 100, var2, 2);
      appendDecimal(var0.get(2) + 1, var2, 2);
      appendDecimal(var0.get(5), var2, 2);
      appendDecimal(var0.get(11), var2, 2);
      appendDecimal(var0.get(12), var2, 2);
      if (var7 == 0 && (var1 & 4032) != 0) {
         var3 = true;
         var1 = var1 >>> 6 & 63;
      } else if (var7 % 60 == 0 && (var1 & 258048) != 0) {
         var4 = true;
         var1 = var1 >>> 12 & 63;
      } else if ((var1 & 16515072) != 0) {
         var4 = true;
         var1 = var1 >>> 18 & 63;
      } else {
         if ((var1 & 63) == 0) {
            throw new BadValueException();
         }

         var6 = true;
         var1 &= 63;
      }

      if ((var1 & 60) != 0) {
         appendDecimal(var0.get(13), var2, 2);
      }

      if (var3) {
         var2.append('Z');
      } else if (var4 && var7 >= 0) {
         var2.append('+');
         appendDecimal(var7 / 60, var2, 2);
      } else if (var4) {
         var2.append('-');
         appendDecimal(-var7 / 60, var2, 2);
      } else if (var5 && var7 >= 0) {
         var2.append('+');
         appendDecimal(var7 / 60, var2, 2);
         appendDecimal(var7 % 60, var2, 2);
      } else if (var5) {
         var2.append('-');
         appendDecimal(-var7 / 60, var2, 2);
         appendDecimal(-var7 % 60, var2, 2);
      }

      return var2.toString();
   }

   public void writeUTCTime(UTCTime var1, int var2, int var3) throws BadValueException, IOException {
      this.writeString8(utcTimeToString(var1, var3), var2);
   }

   public void writeOpen(UnitString var1) throws IOException {
      this.writeBits(var1);
   }

   public void writeOpaque(BitString var1) throws IOException {
      this.writeBits(var1);
   }

   protected static int octetLength(byte var0) {
      return 1;
   }

   protected static int octetLength(short var0) {
      return var0 >= -128 && var0 <= 127 ? 1 : 2;
   }

   protected static int octetLength(int var0) {
      if (var0 >= -32768 && var0 <= 32767) {
         return var0 >= -128 && var0 <= 127 ? 1 : 2;
      } else {
         return var0 >= -8388608 && var0 <= 8388607 ? 3 : 4;
      }
   }

   protected static int octetLength(long var0) {
      if (var0 >= -2147483648L && var0 <= 2147483647L) {
         if (var0 >= -32768L && var0 <= 32767L) {
            return var0 >= -128L && var0 <= 127L ? 1 : 2;
         } else {
            return var0 >= -8388608L && var0 <= 8388607L ? 3 : 4;
         }
      } else if (var0 >= -140737488355328L && var0 <= 140737488355327L) {
         return var0 >= -549755813888L && var0 <= 549755813887L ? 5 : 6;
      } else {
         return var0 >= -36028797018963968L && var0 <= 36028797018963967L ? 7 : 8;
      }
   }

   protected static int octetLength(BigInteger var0) {
      int var1 = var0.bitLength();
      return var1 / 8 + 1;
   }

   protected static int septetLengthU(long var0) {
      if (var0 <= 127L) {
         return 1;
      } else if (var0 <= 16383L) {
         return 2;
      } else if (var0 <= 2097151L) {
         return 3;
      } else if (var0 <= 268435455L) {
         return 4;
      } else if (var0 <= 34359738367L) {
         return 5;
      } else if (var0 <= 4398046511103L) {
         return 6;
      } else if (var0 <= 562949953421311L) {
         return 7;
      } else {
         return var0 <= 72057594037927935L ? 8 : 9;
      }
   }
}
