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
import com.asn1c.core.Open;
import com.asn1c.core.String16;
import com.asn1c.core.String16Table;
import com.asn1c.core.String32;
import com.asn1c.core.String32Buffer;
import com.asn1c.core.String32Table;
import com.asn1c.core.TrimmedBitString;
import com.asn1c.core.UTCTime;
import com.asn1c.core.UnitString;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public abstract class UPEREncoder extends FilterEncoder {
   private Vector embeddedPdvIdentificationCache;
   private Vector characterStringIdentificationCache;
   private static int done = -1;
   private static final int[] tab = new int[]{0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};
   private Hashtable encoderCache;

   public UPEREncoder(Encoder var1, String var2, byte[] var3) {
      super(var1);
      if (done == -1) {
         examine(var3);
      }

      this.embeddedPdvIdentificationCache = new Vector();
      this.characterStringIdentificationCache = new Vector();
      this.encoderCache = new Hashtable();
      this.encoderCache.put(var2, this);
   }

   protected UPEREncoder(Encoder var1, UPEREncoder var2) {
      super(var1);
      if (done == -1) {
         throw new InternalError();
      } else {
         this.embeddedPdvIdentificationCache = new Vector();
         this.characterStringIdentificationCache = new Vector();
         this.encoderCache = var2.encoderCache;
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

   public void joinEncoder(UPEREncoder var1) {
      if (super.out != var1.out) {
         throw new IllegalArgumentException("cannot join encoders of different input streams");
      } else {
         this.encoderCache.putAll(var1.encoderCache);
         var1.encoderCache = this.encoderCache;
         var1.embeddedPdvIdentificationCache = this.embeddedPdvIdentificationCache;
         var1.characterStringIdentificationCache = this.characterStringIdentificationCache;
      }
   }

   protected UPEREncoder getEncoder(String var1) {
      return (UPEREncoder)this.encoderCache.get(var1);
   }

   public int writeFragmentLength(int var1) throws IOException, EOFException {
      if (done != 1) {
         var1 = 0;
      }

      if (var1 < 128) {
         this.writeUInteger(var1, 8);
         return var1;
      } else if (var1 < 16384) {
         this.writeUInteger(var1 | '耀', 16);
         return var1;
      } else if (var1 < 32768) {
         this.writeUInteger(193, 8);
         return 16384;
      } else if (var1 < 49152) {
         this.writeUInteger(194, 8);
         return 32768;
      } else if (var1 < 65536) {
         this.writeUInteger(195, 8);
         return 49152;
      } else {
         this.writeUInteger(196, 8);
         return 65536;
      }
   }

   protected void writeFragmented(UnitString var1, int var2) throws IOException, EOFException {
      int var3 = var1.bitLength() / var2;
      int var4 = 0;

      int var5;
      do {
         var5 = this.writeFragmentLength(var3);
         this.write(var1, var4, var5 * var2);
         var4 += var5 * var2;
         var3 -= var5;
      } while(var5 >= 16384);

   }

   public void skipToAlignment() throws IOException, EOFException {
      int var1 = this.getBitsToAlignment();
      if (var1 > 0) {
         this.writeUInteger(0, var1);
      }

   }

   public void flushOut() throws IOException, EOFException {
      this.skipToAlignment();
      if (this.getBitsWritten() == 0L) {
         this.writeUInteger(0, 8);
      }

      super.flushOut();
   }

   public void writeNull(Null var1) {
   }

   public void writeBoolean(boolean var1) throws IOException, EOFException {
      this.writeBit(var1);
   }

   public void writeConstrainedByteB(byte var1, int var2, byte var3) throws IOException, EOFException, ValueTooLargeException {
      this.writeUInteger(var1 - var3, var2);
   }

   public void writeConstrainedShortB(short var1, int var2, short var3) throws IOException, EOFException, ValueTooLargeException {
      this.writeUInteger(var1 - var3, var2);
   }

   public void writeConstrainedIntegerB(int var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException {
      this.writeUInteger(var1 - var3, var2);
   }

   public void writeConstrainedLongB(long var1, int var3, long var4) throws IOException, EOFException, ValueTooLargeException {
      this.writeULong(var1 - var4, var3);
   }

   public void writeConstrainedBigIntegerB(BigInteger var1, int var2, BigInteger var3) throws IOException, EOFException {
      BigInteger var4 = var1.subtract(var3);
      this.writeUBigInteger(var4, var2);
   }

   public void writeConstrainedIntegerBB(int var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException {
      int var4 = octetLengthU(var1 - var3);
      this.writeUInteger(var4 - 1, var2);
      this.writeUInteger(var1 - var3, var4 * 8);
   }

   public void writeConstrainedLongBB(long var1, int var3, long var4) throws IOException, EOFException, ValueTooLargeException {
      int var6 = octetLengthU(var1 - var4);
      this.writeUInteger(var6 - 1, var3);
      this.writeULong(var1 - var4, var6 * 8);
   }

   public void writeConstrainedBigIntegerBB(BigInteger var1, int var2, BigInteger var3) throws IOException, EOFException, ValueTooLargeException {
      BigInteger var4 = var1.subtract(var3);
      int var5 = octetLengthU(var4);
      this.writeUInteger(var5 - 1, var2);
      this.writeUBigInteger(var4, var5 * 8);
   }

   public void writeConstrainedBigIntegerFBB(BigInteger var1, BigInteger var2) throws IOException, EOFException {
      BigInteger var3 = var1.subtract(var2);
      int var4 = octetLengthU(var3);
      BitString var5 = new BitString();
      var5.appendUBigInteger(var3, var4 * 8);
      this.writeFragmented(var5, 8);
   }

   public void writeSemiconstrainedByteFBB(byte var1, byte var2) throws IOException, EOFException, ValueTooLargeException {
      int var3 = octetLengthU(var1 - var2);
      BitString var4 = new BitString();
      var4.appendUInteger(var1 - var2, var3 * 8);
      this.writeFragmented(var4, 8);
   }

   public void writeSemiconstrainedShortFBB(short var1, short var2) throws IOException, EOFException, ValueTooLargeException {
      int var3 = octetLengthU(var1 - var2);
      BitString var4 = new BitString();
      var4.appendUInteger(var1 - var2, var3 * 8);
      this.writeFragmented(var4, 8);
   }

   public void writeSemiconstrainedIntegerFBB(int var1, int var2) throws IOException, EOFException, ValueTooLargeException {
      int var3 = octetLengthU(var1 - var2);
      BitString var4 = new BitString();
      var4.appendUInteger(var1 - var2, var3 * 8);
      this.writeFragmented(var4, 8);
   }

   public void writeSemiconstrainedLongFBB(long var1, long var3) throws IOException, EOFException, ValueTooLargeException {
      int var5 = octetLengthU(var1 - var3);
      BitString var6 = new BitString();
      var6.appendULong(var1 - var3, var5 * 8);
      this.writeFragmented(var6, 8);
   }

   public void writeSemiconstrainedBigIntegerFBB(BigInteger var1, BigInteger var2) throws IOException, EOFException {
      BigInteger var3 = var1.subtract(var2);
      int var4 = octetLengthU(var3);
      BitString var5 = new BitString();
      var5.appendUBigInteger(var3, var4 * 8);
      this.writeFragmented(var5, 8);
   }

   public void writeSemiconstrainedByteSB(byte var1, byte var2) throws IOException, EOFException, ValueTooLargeException {
      if (var1 - var2 < 64) {
         this.writeBit(false);
         this.writeUInteger(var1 - var2, 6);
      } else {
         this.writeBit(true);
         this.writeSemiconstrainedByteFBB(var1, var2);
      }

   }

   public void writeSemiconstrainedShortSB(short var1, short var2) throws IOException, EOFException, ValueTooLargeException {
      if (var1 - var2 < 64) {
         this.writeBit(false);
         this.writeUInteger(var1 - var2, 6);
      } else {
         this.writeBit(true);
         this.writeSemiconstrainedShortFBB(var1, var2);
      }

   }

   public void writeSemiconstrainedIntegerSB(int var1, int var2) throws IOException, EOFException, ValueTooLargeException {
      if (var1 - var2 < 64) {
         this.writeBit(false);
         this.writeUInteger(var1 - var2, 6);
      } else {
         this.writeBit(true);
         this.writeSemiconstrainedIntegerFBB(var1, var2);
      }

   }

   public void writeSemiconstrainedLongSB(long var1, long var3) throws IOException, EOFException, ValueTooLargeException {
      if (var1 - var3 < 64L) {
         this.writeBit(false);
         this.writeULong(var1 - var3, 6);
      } else {
         this.writeBit(true);
         this.writeSemiconstrainedLongFBB(var1, var3);
      }

   }

   public void writeSemiconstrainedBigIntegerSB(BigInteger var1, BigInteger var2) throws IOException, EOFException {
      BigInteger var3 = var1.subtract(var2);
      BigInteger var4 = new BigInteger(new byte[]{64});
      if (var3.compareTo(var4) < 0) {
         this.writeBit(false);
         this.writeUBigInteger(var3, 6);
      } else {
         this.writeBit(true);
         this.writeSemiconstrainedBigIntegerFBB(var1, var2);
      }

   }

   public void writeUnconstrainedByteFBB(byte var1) throws IOException, EOFException, ValueTooLargeException {
      int var2 = octetLength(var1);
      BitString var3 = new BitString();
      var3.appendSInteger(var1, var2 * 8);
      this.writeFragmented(var3, 8);
   }

   public void writeUnconstrainedShortFBB(short var1) throws IOException, EOFException, ValueTooLargeException {
      int var2 = octetLength(var1);
      BitString var3 = new BitString();
      var3.appendSInteger(var1, var2 * 8);
      this.writeFragmented(var3, 8);
   }

   public void writeUnconstrainedIntegerFBB(int var1) throws IOException, EOFException, ValueTooLargeException {
      int var2 = octetLength(var1);
      BitString var3 = new BitString();
      var3.appendSInteger(var1, var2 * 8);
      this.writeFragmented(var3, 8);
   }

   public void writeUnconstrainedLongFBB(long var1) throws IOException, EOFException, ValueTooLargeException {
      int var3 = octetLength(var1);
      BitString var4 = new BitString();
      var4.appendSLong(var1, var3 * 8);
      this.writeFragmented(var4, 8);
   }

   public void writeUnconstrainedBigIntegerFBB(BigInteger var1) throws IOException, EOFException {
      int var2 = octetLength(var1);
      BitString var3 = new BitString();
      var3.appendSBigInteger(var1, var2 * 8);
      this.writeFragmented(var3, 8);
   }

   public void writeRealDouble(double var1) throws IOException, EOFException, BadValueException {
      BitString var3 = new BitString();
      if (Double.isInfinite(var1)) {
         if (var1 > 0.0) {
            var3.appendOctet((byte)1);
            var3.appendOctet((byte)64);
         } else {
            var3.appendOctet((byte)1);
            var3.appendOctet((byte)65);
         }
      } else {
         if (Double.isNaN(var1)) {
            throw new BadValueException(Double.toString(var1));
         }

         if (var1 != 0.0) {
            long var4 = Double.doubleToLongBits(var1);
            boolean var6 = (var4 & Long.MIN_VALUE) != 0L;
            int var7 = ((int)(var4 >> 52) & 2047) - 1075;

            long var8;
            for(var8 = var7 == 0 ? (var4 & 4503599627370495L) << 1 : var4 & 4503599627370495L | 4503599627370496L; (var8 & 1L) == 0L; ++var7) {
               var8 >>>= 1;
            }

            if (var7 >= -128 && var7 <= 127) {
               var3.appendOctet((byte)(128 | (var6 ? 64 : 0)));
               var3.appendOctet((byte)var7);
            } else if (var7 >= -32768 && var7 <= 32767) {
               var3.appendOctet((byte)(128 | (var6 ? 64 : 0) | 1));
               var3.appendOctet((byte)(var7 >> 8));
               var3.appendOctet((byte)var7);
            } else if (var7 >= -8388608 && var7 <= 8388607) {
               var3.appendOctet((byte)(128 | (var6 ? 64 : 0) | 2));
               var3.appendOctet((byte)(var7 >> 16));
               var3.appendOctet((byte)(var7 >> 8));
               var3.appendOctet((byte)var7);
            } else {
               var3.appendOctet((byte)(128 | (var6 ? 64 : 0) | 3));
               var3.appendOctet((byte)4);
               var3.appendOctet((byte)(var7 >> 24));
               var3.appendOctet((byte)(var7 >> 16));
               var3.appendOctet((byte)(var7 >> 8));
               var3.appendOctet((byte)var7);
            }

            if ((var8 & -72057594037927936L) != 0L) {
               var3.appendULong(var8, 64);
            } else if ((var8 & 71776119061217280L) != 0L) {
               var3.appendULong(var8, 56);
            } else if ((var8 & 280375465082880L) != 0L) {
               var3.appendULong(var8, 48);
            } else if ((var8 & 1095216660480L) != 0L) {
               var3.appendULong(var8, 40);
            } else if ((var8 & 4278190080L) != 0L) {
               var3.appendULong(var8, 32);
            } else if ((var8 & 16711680L) != 0L) {
               var3.appendULong(var8, 24);
            } else if ((var8 & 65280L) != 0L) {
               var3.appendULong(var8, 16);
            } else {
               var3.appendULong(var8, 8);
            }
         }
      }

      this.writeFragmented(var3, 8);
   }

   public void writeConstrainedBitStringB(BitString var1, int var2) throws IOException, EOFException {
      this.write(var1, 0, var2);
   }

   public void writeConstrainedTrimmedBitStringB(TrimmedBitString var1, int var2) throws IOException, EOFException {
      this.write(var1, 0, var2);
   }

   public void writeConstrainedBitStringBB(BitString var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException {
      int var4 = var1.bitLength();
      this.writeUInteger(var4 - var3, var2);
      this.write(var1, 0, var4);
   }

   public void writeConstrainedTrimmedBitStringBB(TrimmedBitString var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException {
      int var4 = var1.bitLength();
      if (var4 < var3) {
         var4 = var3;
      }

      this.writeUInteger(var4 - var3, var2);
      this.write(var1, 0, var4);
   }

   public void writeConstrainedBitStringFBB(BitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeConstrainedTrimmedBitStringFBB(TrimmedBitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeSemiconstrainedBitStringFBB(BitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeSemiconstrainedTrimmedBitStringFBB(TrimmedBitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeUnconstrainedBitStringFBB(BitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeUnconstrainedTrimmedBitStringFBB(TrimmedBitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeConstrainedOctetStringB(OctetString var1, int var2) throws IOException, EOFException {
      this.write(new BitString(var1), 0, var2 * 8);
   }

   public void writeConstrainedOctetStringBB(OctetString var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException {
      int var4 = var1.octetLength();
      this.writeUInteger(var4 - var3, var2);
      this.write(new BitString(var1), 0, var4 * 8);
   }

   public void writeConstrainedOctetStringFBB(OctetString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(new BitString(var1), 8);
   }

   public void writeSemiconstrainedOctetStringFBB(OctetString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(new BitString(var1), 8);
   }

   public void writeUnconstrainedOctetStringFBB(OctetString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(new BitString(var1), 8);
   }

   public void writeObjectIdentifier(ObjectIdentifier var1) throws IOException, EOFException, ValueTooLargeException {
      BitString var2 = new BitString();

      for(int var7 = 0; var7 < var1.length(); ++var7) {
         long var3 = var1.get(var7);
         if (var7 == 0) {
            long var10000 = var3 * 40L;
            ++var7;
            var3 = var10000 + var1.get(var7);
         }

         int var6 = septetLengthU(var3);

         for(int var5 = 0; var5 < var6 - 1; ++var5) {
            var2.appendULong(var3 >> 7 * (var6 - var5 - 1) & 127L | 128L, 8);
         }

         var2.appendULong(var3 & 127L, 8);
      }

      this.writeFragmented(var2, 8);
   }

   protected String applyTable(String var1, String16Table var2) throws BadValueException {
      if (var2 == null) {
         return var1;
      } else {
         StringBuffer var3 = new StringBuffer(var1.toString());
         int var4 = var3.length();

         while(var4-- > 0) {
            var3.setCharAt(var4, var2.mapToValue(var3.charAt(var4)));
         }

         return var3.toString();
      }
   }

   protected String32 applyTable(String32 var1, String32Table var2) throws BadValueException {
      if (var2 == null) {
         return var1;
      } else {
         String32Buffer var3 = new String32Buffer(var1);
         int var4 = var3.length();

         while(var4-- > 0) {
            var3.setCharAt(var4, var2.mapToValue(var3.charAt(var4)));
         }

         return var3.toString32();
      }
   }

   public void writeConstrainedStringB(String var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      BitString var5 = new BitString();
      var5.appendString16(new String16(this.applyTable(var1, var4)), var2, var3);
      this.write(var5, 0, var2 * var3);
   }

   public void writeConstrainedString32B(String32 var1, int var2, int var3, String32Table var4) throws IOException, EOFException, BadValueException {
      BitString var5 = new BitString();
      var5.appendString32(this.applyTable(var1, var4), var2, var3);
      this.write(var5, 0, var2 * var3);
   }

   public void writeConstrainedStringBB(String var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, ValueTooLargeException, BadValueException {
      int var6 = var1.length();
      this.writeUInteger(var6 - var3, var2);
      BitString var7 = new BitString();
      var7.appendString16(new String16(this.applyTable(var1, var5)), var1.length(), var4);
      this.write(var7, 0, var1.length() * var4);
   }

   public void writeConstrainedString32BB(String32 var1, int var2, int var3, int var4, String32Table var5) throws IOException, EOFException, ValueTooLargeException, BadValueException {
      int var6 = var1.length();
      this.writeUInteger(var6 - var3, var2);
      BitString var7 = new BitString();
      var7.appendString32(this.applyTable(var1, var5), var1.length(), var4);
      this.write(var7, 0, var1.length() * var4);
   }

   public void writeConstrainedStringFBB(String var1, int var2, String16Table var3) throws IOException, EOFException, BadValueException {
      BitString var4 = new BitString();
      var4.appendString16(new String16(this.applyTable(var1, var3)), var1.length(), var2);
      this.writeFragmented(var4, var2);
   }

   public void writeConstrainedString32FBB(String32 var1, int var2, String32Table var3) throws IOException, EOFException, BadValueException {
      BitString var4 = new BitString();
      var4.appendString32(this.applyTable(var1, var3), var1.length(), var2);
      this.writeFragmented(var4, var2);
   }

   public void writeSemiconstrainedStringFBB(String var1, int var2, String16Table var3) throws IOException, EOFException, BadValueException {
      BitString var4 = new BitString();
      var4.appendString16(new String16(this.applyTable(var1, var3)), var1.length(), var2);
      this.writeFragmented(var4, var2);
   }

   public void writeSemiconstrainedString32FBB(String32 var1, int var2, String32Table var3) throws IOException, EOFException, BadValueException {
      BitString var4 = new BitString();
      var4.appendString32(this.applyTable(var1, var3), var1.length(), var2);
      this.writeFragmented(var4, var2);
   }

   public void writeUnconstrainedStringFBB(String var1, int var2, String16Table var3) throws IOException, EOFException, BadValueException {
      BitString var4 = new BitString();
      var4.appendString16(new String16(this.applyTable(var1, var3)), var1.length(), var2);
      this.writeFragmented(var4, var2);
   }

   public void writeUnconstrainedString32FBB(String32 var1, int var2, String32Table var3) throws IOException, EOFException, BadValueException {
      BitString var4 = new BitString();
      var4.appendString32(this.applyTable(var1, var3), var1.length(), var2);
      this.writeFragmented(var4, var2);
   }

   public void writeMultibyteString(String var1) throws IOException, EOFException {
      BitString var2 = new BitString();
      var2.appendString16(new String16(var1), var1.length(), 8);
      this.writeFragmented(var2, 8);
   }

   public void writeObjectDescriptor(ObjectDescriptor var1) throws IOException, EOFException {
      BitString var2 = new BitString();
      String16 var3 = var1.toString16();
      var2.appendString16(var3, var3.length(), 8);
      this.writeFragmented(var2, 8);
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

   public void writeConstrainedGeneralizedTimeB(GeneralizedTime var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, BadValueException {
      this.writeConstrainedStringB(generalizedTimeToString(var1, var4), var2, var3, var5);
   }

   public void writeConstrainedGeneralizedTimeBB(GeneralizedTime var1, int var2, int var3, int var4, int var5, String16Table var6) throws IOException, EOFException, ValueTooLargeException, BadValueException {
      this.writeConstrainedStringBB(generalizedTimeToString(var1, var5), var2, var3, var4, var6);
   }

   public void writeConstrainedGeneralizedTimeFBB(GeneralizedTime var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      this.writeConstrainedStringFBB(generalizedTimeToString(var1, var3), var2, var4);
   }

   public void writeSemiconstrainedGeneralizedTimeFBB(GeneralizedTime var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      this.writeSemiconstrainedStringFBB(generalizedTimeToString(var1, var3), var2, var4);
   }

   public void writeUnconstrainedGeneralizedTimeFBB(GeneralizedTime var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      this.writeUnconstrainedStringFBB(generalizedTimeToString(var1, var3), var2, var4);
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

   public void writeConstrainedUTCTimeB(UTCTime var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, BadValueException {
      this.writeConstrainedStringB(utcTimeToString(var1, var4), var2, var3, var5);
   }

   public void writeConstrainedUTCTimeBB(UTCTime var1, int var2, int var3, int var4, int var5, String16Table var6) throws IOException, EOFException, ValueTooLargeException, BadValueException {
      this.writeConstrainedStringBB(utcTimeToString(var1, var5), var2, var3, var4, var6);
   }

   public void writeConstrainedUTCTimeFBB(UTCTime var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      this.writeConstrainedStringFBB(utcTimeToString(var1, var3), var2, var4);
   }

   public void writeSemiconstrainedUTCTimeFBB(UTCTime var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      this.writeSemiconstrainedStringFBB(utcTimeToString(var1, var3), var2, var4);
   }

   public void writeUnconstrainedUTCTimeFBB(UTCTime var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadValueException {
      this.writeUnconstrainedStringFBB(utcTimeToString(var1, var3), var2, var4);
   }

   public void writeExternal(External var1) throws IOException, EOFException, BadValueException, ValueTooLargeException {
      Identification var2 = var1.getIdentification();
      ObjectDescriptor var3 = var1.getDataValueDescriptor();
      DataValue var4 = var1.getDataValue();
      switch (var2.getSelector()) {
         case 1:
            this.writeBit(true);
            this.writeBit(false);
            this.writeBit(var3 != null);
            this.writeObjectIdentifier(var2.getSyntax());
            break;
         case 2:
            this.writeBit(false);
            this.writeBit(true);
            this.writeBit(var3 != null);
            this.writeUnconstrainedIntegerFBB(var2.getPresentationContextId());
            break;
         case 3:
            this.writeBit(true);
            this.writeBit(true);
            this.writeBit(var3 != null);
            this.writeObjectIdentifier(var2.getContextNegotiationTransferSyntax());
            this.writeUnconstrainedIntegerFBB(var2.getContextNegotiationPresentationContextId());
            break;
         default:
            throw new BadValueException();
      }

      if (var3 != null) {
         this.writeObjectDescriptor(var3);
      }

      switch (var4.getSelector()) {
         case 0:
            this.writeBit(false);
            this.writeBit(false);
            this.writeOpen(var4.getNotation().getEncoded());
            break;
         case 1:
            if ((var4.getEncodedBitString().bitLength() & 7) != 0) {
               this.writeBit(true);
               this.writeBit(false);
               this.writeUnconstrainedBitStringFBB(var4.getEncodedBitString());
            } else {
               this.writeBit(false);
               this.writeBit(true);
               this.writeUnconstrainedOctetStringFBB(new OctetString(var4.getEncodedBitString()));
            }
            break;
         case 2:
            this.writeBit(false);
            this.writeBit(true);
            this.writeUnconstrainedOctetStringFBB(var4.getEncodedOctetString());
            break;
         default:
            throw new BadValueException();
      }

   }

   public void writeEmbeddedPDV(EmbeddedPDV var1) throws IOException, EOFException, BadValueException, ValueTooLargeException {
      Identification var2 = var1.getIdentification();
      DataValue var3 = var1.getDataValue();
      boolean var4 = true;
      int var5 = this.embeddedPdvIdentificationCache.size();

      while(var5-- > 0) {
         if (((Identification)this.embeddedPdvIdentificationCache.elementAt(var5)).compareTo(var2) == 0) {
            var4 = false;
            break;
         }
      }

      this.writeBit(var4);
      this.writeSemiconstrainedIntegerSB(var5, 0);
      if (var4) {
         this.writeUInteger(var2.getSelector(), 3);
         switch (var2.getSelector()) {
            case 0:
               this.writeObjectIdentifier(var2.getSyntaxesAbstract());
               this.writeObjectIdentifier(var2.getSyntaxesTransfer());
               break;
            case 1:
               this.writeObjectIdentifier(var2.getSyntax());
               break;
            case 2:
               this.writeUnconstrainedIntegerFBB(var2.getPresentationContextId());
               break;
            case 3:
               this.writeUnconstrainedIntegerFBB(var2.getContextNegotiationPresentationContextId());
               this.writeObjectIdentifier(var2.getContextNegotiationTransferSyntax());
               break;
            case 4:
               this.writeObjectIdentifier(var2.getTransferSyntax());
            case 5:
               break;
            default:
               throw new BadValueException();
         }

         this.embeddedPdvIdentificationCache.addElement(dupIdentification(var2));
      }

      BitString var6;
      switch (var3.getSelector()) {
         case 0:
            Open var7 = var3.getNotation();
            var6 = new BitString();
            var6.append(var7.getEncoded());
            break;
         case 1:
            var6 = var3.getEncodedBitString();
            break;
         case 2:
            var6 = new BitString();
            var6.append(var3.getEncodedOctetString());
            break;
         default:
            throw new BadValueException();
      }

      this.writeFragmented(var6, 1);
   }

   public void writeEmbeddedPDVOpt(EmbeddedPDV var1) throws IOException, EOFException, BadValueException, ValueTooLargeException {
      DataValue var3 = var1.getDataValue();
      BitString var2;
      switch (var3.getSelector()) {
         case 0:
            Open var4 = var3.getNotation();
            var2 = new BitString();
            var2.append(var4.getEncoded());
            break;
         case 1:
            var2 = var3.getEncodedBitString();
            break;
         case 2:
            var2 = new BitString();
            var2.append(var3.getEncodedOctetString());
            break;
         default:
            throw new BadValueException();
      }

      this.writeFragmented(var2, 1);
   }

   public void writeCharacterString(CharacterString var1) throws IOException, EOFException, BadValueException, ValueTooLargeException {
      Identification var2 = var1.getIdentification();
      DataValue var3 = var1.getDataValue();
      boolean var4 = true;
      int var5 = this.characterStringIdentificationCache.size();

      while(var5-- > 0) {
         if (((Identification)this.characterStringIdentificationCache.elementAt(var5)).compareTo(var2) == 0) {
            var4 = false;
            break;
         }
      }

      this.writeBit(var4);
      this.writeSemiconstrainedIntegerSB(var5, 0);
      if (var4) {
         this.writeUInteger(var2.getSelector(), 3);
         switch (var2.getSelector()) {
            case 0:
               this.writeObjectIdentifier(var2.getSyntaxesAbstract());
               this.writeObjectIdentifier(var2.getSyntaxesTransfer());
               break;
            case 1:
               this.writeObjectIdentifier(var2.getSyntax());
               break;
            case 2:
               this.writeUnconstrainedIntegerFBB(var2.getPresentationContextId());
               break;
            case 3:
               this.writeUnconstrainedIntegerFBB(var2.getContextNegotiationPresentationContextId());
               this.writeObjectIdentifier(var2.getContextNegotiationTransferSyntax());
               break;
            case 4:
               this.writeObjectIdentifier(var2.getTransferSyntax());
            case 5:
               break;
            default:
               throw new BadValueException();
         }

         this.characterStringIdentificationCache.addElement(dupIdentification(var2));
      }

      OctetString var6;
      switch (var3.getSelector()) {
         case 0:
            Open var7 = var3.getNotation();
            var6 = new OctetString(var7.getEncoded());
            break;
         case 1:
            BitString var8 = var3.getEncodedBitString();
            if (var8.bitLength() % 8 != 0) {
               throw new BadValueException();
            }

            var6 = new OctetString(var8);
            break;
         case 2:
            var6 = var3.getEncodedOctetString();
            break;
         default:
            throw new BadValueException();
      }

      this.writeUnconstrainedOctetStringFBB(var6);
   }

   public void writeCharacterStringOpt(CharacterString var1, Identification var2) throws IOException, EOFException, BadValueException, ValueTooLargeException {
      DataValue var4 = var1.getDataValue();
      OctetString var3;
      switch (var4.getSelector()) {
         case 0:
            Open var5 = var4.getNotation();
            var3 = new OctetString(var5.getEncoded());
            break;
         case 1:
            BitString var6 = var4.getEncodedBitString();
            if (var6.bitLength() % 8 != 0) {
               throw new BadValueException();
            }

            var3 = new OctetString(var6);
            break;
         case 2:
            var3 = var4.getEncodedOctetString();
            break;
         default:
            throw new BadValueException();
      }

      this.writeUnconstrainedOctetStringFBB(var3);
   }

   public void writeOpen(UnitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeUnconstrainedOctetStringFBB(new OctetString(var1));
   }

   public void writeBitFieldB(TrimmedBitString var1, int var2) throws IOException, EOFException {
      this.write(var1, 0, var2);
   }

   public void writeBitFieldFBB(TrimmedBitString var1) throws IOException, EOFException, ValueTooLargeException {
      this.writeFragmented(var1, 1);
   }

   public void writeBitFieldSB(TrimmedBitString var1) throws IOException, EOFException, ValueTooLargeException, BadValueException {
      int var2 = var1.bitLength();
      if (var2 == 0) {
         throw new BadValueException();
      } else {
         if (var2 <= 64) {
            this.writeUInteger(var2 - 1, 7);
            this.write(var1, 0, var2);
         } else {
            this.writeBit(true);
            this.writeFragmented(var1, 1);
         }

      }
   }

   public void writeExtension(BitString var1) throws IOException, EOFException {
      this.writeFragmented(var1, 8);
   }

   public UPEREncoder createExtensionEncoder() throws IOException, EOFException {
      DataEncoder var4 = new DataEncoder();
      UPEREncoder var3 = this.createExtensionClone(var4);
      Iterator var5 = this.encoderCache.values().iterator();

      while(var5.hasNext()) {
         UPEREncoder var2 = (UPEREncoder)var5.next();
         if (var2 != this) {
            var3.joinEncoder(var2.createExtensionClone(var4));
         }
      }

      return var3;
   }

   public void closeExtensionEncoder(UPEREncoder var1) throws IOException, EOFException {
      var1.flushOut();
      DataEncoder var2 = (DataEncoder)var1.getOutputEncoder();
      this.writeFragmented(var2.getEncodedData(), 8);
   }

   public abstract UPEREncoder createExtensionClone(Encoder var1);

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

   protected static int octetLengthU(byte var0) {
      return 1;
   }

   protected static int octetLengthU(short var0) {
      return var0 >= 0 && var0 < 256 ? 1 : 2;
   }

   protected static int octetLengthU(int var0) {
      if (var0 >= 0 && var0 < 65536) {
         return var0 < 256 ? 1 : 2;
      } else {
         return var0 >= 0 && var0 < 16777216 ? 3 : 4;
      }
   }

   protected static int octetLengthU(long var0) {
      if (var0 >= 0L && var0 < 4294967296L) {
         if (var0 < 65536L) {
            return var0 < 256L ? 1 : 2;
         } else {
            return var0 < 16777216L ? 3 : 4;
         }
      } else if (var0 >= 0L && var0 < 281474976710656L) {
         return var0 < 1099511627776L ? 5 : 6;
      } else {
         return var0 >= 0L && var0 < 72057594037927936L ? 7 : 8;
      }
   }

   protected static int octetLengthU(BigInteger var0) {
      int var1 = var0.bitLength();
      return var1 == 0 ? 1 : (var1 + 7) / 8;
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

   protected static Identification dupIdentification(Identification var0) {
      switch (var0.getSelector()) {
         case 0:
            return new Identification(0, (ObjectIdentifier)var0.getSyntaxesAbstract().clone(), (ObjectIdentifier)var0.getSyntaxesTransfer().clone());
         case 1:
            return new Identification(1, (ObjectIdentifier)var0.getSyntax().clone());
         case 2:
            return new Identification(2, var0.getPresentationContextId());
         case 3:
            return new Identification(3, var0.getContextNegotiationPresentationContextId(), (ObjectIdentifier)var0.getContextNegotiationTransferSyntax().clone());
         case 4:
            return new Identification(4, (ObjectIdentifier)var0.getTransferSyntax().clone());
         case 5:
            return new Identification(5);
         default:
            throw new IllegalStateException("Bad selector in identification");
      }
   }
}
