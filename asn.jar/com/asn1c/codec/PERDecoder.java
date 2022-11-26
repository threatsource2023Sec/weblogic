package com.asn1c.codec;

import com.asn1c.core.BadDataException;
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
import com.asn1c.core.Real64;
import com.asn1c.core.String16Table;
import com.asn1c.core.String32;
import com.asn1c.core.String32Buffer;
import com.asn1c.core.String32Table;
import com.asn1c.core.TrimmedBitString;
import com.asn1c.core.UTCTime;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Vector;

public abstract class PERDecoder extends FilterDecoder {
   protected static final int maxSkipBufferSize = 65536;
   protected BitString skipBuffer = null;
   protected Vector embeddedPdvIdentificationCache;
   protected Vector characterStringIdentificationCache;
   protected HashMap decoderMap;
   private FactoryMap factoryMap;
   protected BaseTypeFactory baseTypeFactory;
   private static int done = -1;
   private static final int[] tab = new int[]{0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};

   public PERDecoder(Decoder var1, String var2, FactoryMap var3, byte[] var4) {
      super(var1);
      if (done == -1) {
         examine(var4);
      }

      this.embeddedPdvIdentificationCache = new Vector();
      this.characterStringIdentificationCache = new Vector();
      this.factoryMap = var3 == null ? new FactoryMap() : var3;
      this.baseTypeFactory = (BaseTypeFactory)this.factoryMap.get((String)null);
      if (this.baseTypeFactory == null) {
         this.baseTypeFactory = new BaseTypeFactory();
      }

      this.decoderMap = new HashMap();
      this.decoderMap.put(var2, this);
   }

   protected PERDecoder(Decoder var1, PERDecoder var2) {
      super(var1);
      if (done == -1) {
         throw new InternalError();
      } else {
         this.embeddedPdvIdentificationCache = new Vector();
         this.characterStringIdentificationCache = new Vector();
         this.decoderMap = var2.decoderMap;
         this.factoryMap = var2.factoryMap;
         this.baseTypeFactory = var2.baseTypeFactory;
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

   protected Factory getFactory(String var1) {
      return this.factoryMap.get(var1);
   }

   protected FactoryMap getFactoryMap() {
      return this.factoryMap;
   }

   protected PERDecoder getDecoder(String var1) {
      return (PERDecoder)this.decoderMap.get(var1);
   }

   public int readFragmentLength() throws IOException, EOFException, BadDataException {
      try {
         this.skipToAlignment();
         int var1 = this.readUInteger(8);
         if (done != 1) {
            var1 = 0;
         }

         if (var1 < 128) {
            return var1;
         } else if (var1 < 192) {
            return (var1 << 8 | this.readUInteger(8)) & 16383;
         } else {
            switch (var1) {
               case 193:
                  return 16384;
               case 194:
                  return 32768;
               case 195:
                  return 49152;
               case 196:
                  return 65536;
               default:
                  throw new BadDataException();
            }
         }
      } catch (ValueTooLargeException var2) {
         throw new InternalError();
      }
   }

   protected BitString readFragmented(int var1) throws IOException, EOFException, BadDataException {
      BitString var2 = new BitString();
      int var3 = 0;

      int var4;
      do {
         var4 = this.readFragmentLength();
         var2.setBitLength(var3 + var4 * var1);
         this.read(var2, var3, var4 * var1);
         var3 += var4 * var1;
      } while(var4 >= 16384);

      return var2;
   }

   protected void skipFragmented(int var1) throws IOException, EOFException, BadDataException {
      int var2;
      do {
         var2 = this.readFragmentLength();
         this.skip((long)(var2 * var1));
      } while(var2 >= 16384);

   }

   public void skipToAlignment() throws IOException, EOFException, BadDataException {
      int var1 = this.getBitsToAlignment();

      try {
         if (var1 > 0 && this.readUInteger(var1) != 0) {
            throw new BadDataException();
         }
      } catch (ValueTooLargeException var3) {
         throw new InternalError();
      }
   }

   public void flushIn() throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      if (this.getBitsRead() == 0L) {
         try {
            if (this.readUInteger(8) != 0) {
               throw new BadDataException();
            }
         } catch (ValueTooLargeException var2) {
            throw new InternalError();
         }
      }

      super.flushIn();
   }

   public Null readNull() throws IOException, EOFException {
      return Null.NULL;
   }

   public void skipNull() throws IOException, EOFException {
   }

   public boolean readBoolean() throws IOException, EOFException {
      return this.readBit();
   }

   public void skipBoolean() throws IOException, EOFException {
      this.skip(1L);
   }

   public byte readConstrainedByteB(int var1, byte var2) throws IOException, EOFException, ValueTooLargeException {
      return (byte)(this.readUInteger(var1) + var2);
   }

   public short readConstrainedShortB(int var1, short var2) throws IOException, EOFException, ValueTooLargeException {
      return (short)(this.readUInteger(var1) + var2);
   }

   public int readConstrainedIntegerB(int var1, int var2) throws IOException, EOFException, ValueTooLargeException {
      return this.readUInteger(var1) + var2;
   }

   public long readConstrainedLongB(int var1, long var2) throws IOException, EOFException, ValueTooLargeException {
      return this.readULong(var1) + var2;
   }

   public BigInteger readConstrainedBigIntegerB(int var1, BigInteger var2) throws IOException, EOFException {
      return this.readUBigInteger(var1).add(var2);
   }

   public void skipConstrainedIntegerB(int var1) throws IOException, EOFException, ValueTooLargeException {
      this.skip((long)var1);
   }

   public byte readConstrainedByteO(int var1, byte var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      return (byte)(this.readUInteger(var1) + var2);
   }

   public short readConstrainedShortO(int var1, short var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      return (short)(this.readUInteger(var1) + var2);
   }

   public int readConstrainedIntegerO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      return this.readUInteger(var1) + var2;
   }

   public long readConstrainedLongO(int var1, long var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      return this.readULong(var1) + var2;
   }

   public BigInteger readConstrainedBigIntegerO(int var1, BigInteger var2) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      return this.readUBigInteger(var1).add(var2);
   }

   public void skipConstrainedIntegerO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      this.skip((long)var1);
   }

   public int readConstrainedIntegerBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1);
      if (var3 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         this.skipToAlignment();
         return this.readUInteger((var3 + 1) * 8) + var2;
      }
   }

   public long readConstrainedLongBO(int var1, long var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var4 = this.readUInteger(var1);
      if (var4 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         this.skipToAlignment();
         return this.readULong((var4 + 1) * 8) + var2;
      }
   }

   public BigInteger readConstrainedBigIntegerBO(int var1, BigInteger var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1);
      if (var3 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         this.skipToAlignment();
         return this.readUBigInteger((var3 + 1) * 8).add(var2);
      }
   }

   public void skipConstrainedIntegerBO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var2 = this.readUInteger(var1);
      if (var2 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         this.skipToAlignment();
         this.skip((long)((var2 + 1) * 8));
      }
   }

   public int readConstrainedIntegerOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1);
      if (var3 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         return this.readUInteger((var3 + 1) * 8) + var2;
      }
   }

   public BigInteger readConstrainedBigIntegerOO(int var1, BigInteger var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1);
      if (var3 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         return this.readUBigInteger((var3 + 1) * 8).add(var2);
      }
   }

   public void skipConstrainedIntegerOO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var2 = this.readUInteger(var1);
      if (var2 > 268435454) {
         throw new ValueTooLargeException();
      } else {
         this.skip((long)((var2 + 1) * 8));
         throw new EOFException();
      }
   }

   public BigInteger readConstrainedBigIntegerFOO(BigInteger var1) throws IOException, EOFException, BadDataException {
      BitString var2 = this.readFragmented(8);
      return var2.getUBigInteger(0, var2.bitLength()).add(var1);
   }

   public void skipConstrainedIntegerFOO() throws IOException, EOFException, BadDataException {
      this.skipFragmented(8);
   }

   public byte readSemiconstrainedByteFOO(byte var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var2 = this.readFragmented(8);
      return (byte)(var2.getUInteger(0, var2.bitLength()) + var1);
   }

   public short readSemiconstrainedShortFOO(short var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var2 = this.readFragmented(8);
      return (short)(var2.getUInteger(0, var2.bitLength()) + var1);
   }

   public int readSemiconstrainedIntegerFOO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var2 = this.readFragmented(8);
      return var2.getUInteger(0, var2.bitLength()) + var1;
   }

   public long readSemiconstrainedLongFOO(long var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(8);
      return var3.getULong(0, var3.bitLength()) + var1;
   }

   public BigInteger readSemiconstrainedBigIntegerFOO(BigInteger var1) throws IOException, EOFException, BadDataException {
      BitString var2 = this.readFragmented(8);
      return var2.getUBigInteger(0, var2.bitLength()).add(var1);
   }

   public void skipSemiconstrainedIntegerFOO() throws IOException, EOFException, BadDataException {
      this.skipFragmented(8);
   }

   public byte readSemiconstrainedByteSB(byte var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readBit() ? this.readSemiconstrainedByteFOO(var1) : (byte)(this.readUInteger(6) + var1);
   }

   public short readSemiconstrainedShortSB(short var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readBit() ? this.readSemiconstrainedShortFOO(var1) : (short)(this.readUInteger(6) + var1);
   }

   public int readSemiconstrainedIntegerSB(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readBit() ? this.readSemiconstrainedIntegerFOO(var1) : this.readUInteger(6) + var1;
   }

   public long readSemiconstrainedLongSB(long var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readBit() ? this.readSemiconstrainedLongFOO(var1) : this.readULong(6) + var1;
   }

   public BigInteger readSemiconstrainedBigIntegerSB(BigInteger var1) throws IOException, EOFException, BadDataException {
      return this.readBit() ? this.readSemiconstrainedBigIntegerFOO(var1) : this.readUBigInteger(6).add(var1);
   }

   public void skipSemiconstrainedIntegerSB() throws IOException, EOFException, BadDataException {
      if (this.readBit()) {
         this.skipFragmented(8);
      } else {
         this.skip(6L);
      }

   }

   public byte readUnconstrainedByteFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return (byte)var1.getSInteger(0, var1.bitLength());
   }

   public short readUnconstrainedShortFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return (short)var1.getSInteger(0, var1.bitLength());
   }

   public int readUnconstrainedIntegerFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getSInteger(0, var1.bitLength());
   }

   public long readUnconstrainedLongFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getSLong(0, var1.bitLength());
   }

   public BigInteger readUnconstrainedBigIntegerFOO() throws IOException, EOFException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getSBigInteger(0, var1.bitLength());
   }

   public void skipUnconstrainedIntegerFOO() throws IOException, EOFException, BadDataException {
      this.skipFragmented(8);
   }

   public double readRealDouble() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      if (var1.bitLength() == 0) {
         return 0.0;
      } else if (var1.getBit(0)) {
         boolean var11 = var1.getBit(1);
         byte var3;
         if (var1.getBit(2)) {
            if (var1.getBit(3)) {
               throw new BadDataException();
            }

            var3 = 4;
         } else if (var1.getBit(3)) {
            var3 = 3;
         } else {
            var3 = 1;
         }

         int var4 = var1.getUInteger(4, 2);
         int var5;
         int var6;
         if (var1.getBit(6)) {
            if (var1.getBit(7)) {
               int var9 = var1.getUInteger(8, 8);
               var5 = var1.getSInteger(16, (var9 + 1) * 8);
               var6 = 24 + var9 * 8;
            } else {
               var5 = var1.getSInteger(8, 24);
               var6 = 32;
            }
         } else if (var1.getBit(7)) {
            var5 = var1.getSInteger(8, 16);
            var6 = 24;
         } else {
            var5 = var1.getSInteger(8, 8);
            var6 = 16;
         }

         var5 = var5 * var3 + var4;
         return var11 ? (double)(-var1.getULong(var6, var1.bitLength() - var6)) * Math.pow(2.0, (double)var5) : (double)var1.getULong(var6, var1.bitLength() - var6) * Math.pow(2.0, (double)var5);
      } else if (var1.getBit(1)) {
         if (var1.bitLength() != 8) {
            throw new BadDataException();
         } else {
            switch (var1.getUInteger(2, 6)) {
               case 0:
                  return Double.POSITIVE_INFINITY;
               case 1:
                  return Double.NEGATIVE_INFINITY;
               default:
                  throw new BadDataException();
            }
         }
      } else {
         String var2 = var1.getString(8, var1.bitLength() / 8 - 1, 8);
         var2 = var2.replace(',', '.');

         try {
            return Real64.valueOf(var2).doubleValue();
         } catch (NumberFormatException var10) {
            throw new BadDataException();
         }
      }
   }

   public void skipReal() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   public BitString readConstrainedBitStringB(int var1) throws IOException, EOFException {
      return this.readBits(var1);
   }

   public TrimmedBitString readConstrainedTrimmedBitStringB(int var1) throws IOException, EOFException {
      return new TrimmedBitString(this.readBits(var1));
   }

   public void skipConstrainedBitStringB(int var1) throws IOException, EOFException {
      this.skip((long)var1);
   }

   public void skipConstrainedTrimmedBitStringB(int var1) throws IOException, EOFException {
      this.skip((long)var1);
   }

   public BitString readConstrainedBitStringO(int var1) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      return this.readBits(var1);
   }

   public TrimmedBitString readConstrainedTrimmedBitStringO(int var1) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      return new TrimmedBitString(this.readBits(var1));
   }

   public void skipConstrainedBitStringO(int var1) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      this.skip((long)var1);
   }

   public void skipConstrainedTrimmedBitStringO(int var1) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      this.skip((long)var1);
   }

   public BitString readConstrainedBitStringBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      return this.readBits(var3);
   }

   public TrimmedBitString readConstrainedTrimmedBitStringBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      return new TrimmedBitString(this.readBits(var3));
   }

   public void skipConstrainedBitStringBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      this.skip((long)var3);
   }

   public void skipConstrainedTrimmedBitStringBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      this.skip((long)var3);
   }

   public BitString readConstrainedBitStringOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1) + var2;
      return this.readBits(var3);
   }

   public TrimmedBitString readConstrainedTrimmedBitStringOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1) + var2;
      return new TrimmedBitString(this.readBits(var3));
   }

   public void skipConstrainedBitStringOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1) + var2;
      this.skip((long)var3);
   }

   public void skipConstrainedTrimmedBitStringOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1) + var2;
      this.skip((long)var3);
   }

   public BitString readConstrainedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readFragmented(1);
   }

   public TrimmedBitString readConstrainedTrimmedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return new TrimmedBitString(this.readFragmented(1));
   }

   public void skipConstrainedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public void skipConstrainedTrimmedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public BitString readSemiconstrainedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readFragmented(1);
   }

   public TrimmedBitString readSemiconstrainedTrimmedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return new TrimmedBitString(this.readFragmented(1));
   }

   public void skipSemiconstrainedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public void skipSemiconstrainedTrimmedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public BitString readUnconstrainedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return this.readFragmented(1);
   }

   public TrimmedBitString readUnconstrainedTrimmedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return new TrimmedBitString(this.readFragmented(1));
   }

   public void skipUnconstrainedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public void skipUnconstrainedTrimmedBitStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public OctetString readConstrainedOctetStringB(int var1) throws IOException, EOFException {
      return this.readBits(var1 * 8).getOctets(0, var1);
   }

   public void skipConstrainedOctetStringB(int var1) throws IOException, EOFException {
      this.skip((long)(var1 * 8));
   }

   public OctetString readConstrainedOctetStringO(int var1) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      return this.readBits(var1 * 8).getOctets(0, var1);
   }

   public void skipConstrainedOctetStringO(int var1) throws IOException, EOFException, BadDataException {
      this.skipToAlignment();
      this.skip((long)(var1 * 8));
   }

   public OctetString readConstrainedOctetStringBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      return this.readBits(var3 * 8).getOctets(0, var3);
   }

   public void skipConstrainedOctetStringBO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      this.skip((long)(var3 * 8));
   }

   public OctetString readConstrainedOctetStringOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1) + var2;
      return this.readBits(var3 * 8).getOctets(0, var3);
   }

   public void skipConstrainedOctetStringOO(int var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var3 = this.readUInteger(var1) + var2;
      this.skip((long)(var3 * 8));
   }

   public OctetString readConstrainedOctetStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getOctets(0, var1.bitLength() / 8);
   }

   public void skipConstrainedOctetStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   public OctetString readSemiconstrainedOctetStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getOctets(0, var1.bitLength() / 8);
   }

   public void skipSemiconstrainedOctetStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   public OctetString readUnconstrainedOctetStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getOctets(0, var1.bitLength() / 8);
   }

   public void skipUnconstrainedOctetStringFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   public ObjectIdentifier readObjectIdentifier() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      ObjectIdentifier var1 = new ObjectIdentifier();
      BitString var2 = this.readFragmented(8);
      int var3 = var2.bitLength();
      int var4 = 0;
      int var5 = 0;

      while(var4 < var3) {
         long var6 = 0L;

         do {
            if (var4 >= var3) {
               throw new BadDataException();
            }

            if (var6 > 72057594037927935L) {
               throw new ValueTooLargeException();
            }

            var6 = var6 << 7 | (long)var2.getUInteger(var4 + 1, 7);
            var4 += 8;
         } while(var2.getBit(var4 - 8));

         if (var5 == 0) {
            if (var6 < 80L) {
               var1.set(var5++, var6 / 40L);
               var1.set(var5++, var6 % 40L);
            } else {
               var1.set(var5++, 2L);
               var1.set(var5++, var6 - 80L);
            }
         } else {
            var1.set(var5++, var6);
         }
      }

      return var1;
   }

   public void skipObjectIdentifier() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   protected String applyTable(String var1, String16Table var2) throws BadDataException {
      if (var2 == null) {
         return var1;
      } else {
         StringBuffer var3 = new StringBuffer(var1.length());
         var3.setLength(var1.length());
         int var4 = var3.length();

         while(var4-- > 0) {
            var3.setCharAt(var4, var2.mapToChar(var1.charAt(var4)));
         }

         return var3.toString();
      }
   }

   protected String32 applyTable(String32 var1, String32Table var2) throws BadDataException {
      if (var2 == null) {
         return var1;
      } else {
         String32Buffer var3 = new String32Buffer(var1.length());
         var3.setLength(var1.length());
         int var4 = var3.length();

         while(var4-- > 0) {
            var3.setCharAt(var4, var2.mapToChar(var1.charAt(var4)));
         }

         return var3.toString32();
      }
   }

   public String readConstrainedStringB(int var1, int var2, String16Table var3) throws IOException, EOFException, BadDataException {
      return this.applyTable(this.readBits(var1 * var2).getString(0, var1, var2), var3);
   }

   public String32 readConstrainedString32B(int var1, int var2, String32Table var3) throws IOException, EOFException, BadDataException {
      return this.applyTable(this.readBits(var1 * var2).getString32(0, var1, var2), var3);
   }

   public void skipConstrainedStringB(int var1, int var2) throws IOException, EOFException, BadDataException {
      this.skip((long)(var1 * var2));
   }

   public String readConstrainedStringO(int var1, int var2, String16Table var3) throws IOException, EOFException, BadDataException, BadDataException {
      this.skipToAlignment();
      return this.applyTable(this.readBits(var1 * var2).getString(0, var1, var2), var3);
   }

   public String32 readConstrainedString32O(int var1, int var2, String32Table var3) throws IOException, EOFException, BadDataException, BadDataException {
      this.skipToAlignment();
      return this.applyTable(this.readBits(var1 * var2).getString32(0, var1, var2), var3);
   }

   public void skipConstrainedStringO(int var1, int var2) throws IOException, EOFException, BadDataException, BadDataException {
      this.skipToAlignment();
      this.skip((long)(var1 * var2));
   }

   public String readConstrainedStringBB(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var5 = this.readUInteger(var1) + var2;
      return this.applyTable(this.readBits(var5 * var3).getString(0, var5, var3), var4);
   }

   public String32 readConstrainedString32BB(int var1, int var2, int var3, String32Table var4) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var5 = this.readUInteger(var1) + var2;
      return this.applyTable(this.readBits(var5 * var3).getString32(0, var5, var3), var4);
   }

   public void skipConstrainedStringBB(int var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var4 = this.readUInteger(var1) + var2;
      this.skip((long)(var4 * var3));
   }

   public String readConstrainedStringBO(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var5 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      return this.applyTable(this.readBits(var5 * var3).getString(0, var5, var3), var4);
   }

   public String32 readConstrainedString32BO(int var1, int var2, int var3, String32Table var4) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var5 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      return this.applyTable(this.readBits(var5 * var3).getString32(0, var5, var3), var4);
   }

   public void skipConstrainedStringBO(int var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var4 = this.readUInteger(var1) + var2;
      this.skipToAlignment();
      this.skip((long)(var4 * var3));
   }

   public String readConstrainedStringOO(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var5 = this.readUInteger(var1) + var2;
      return this.applyTable(this.readBits(var5 * var3).getString(0, var5, var3), var4);
   }

   public String32 readConstrainedString32OO(int var1, int var2, int var3, String32Table var4) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var5 = this.readUInteger(var1) + var2;
      return this.applyTable(this.readBits(var5 * var3).getString32(0, var5, var3), var4);
   }

   public void skipConstrainedStringOO(int var1, int var2, int var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipToAlignment();
      int var4 = this.readUInteger(var1) + var2;
      this.skip((long)(var4 * var3));
   }

   public String readConstrainedStringFOO(int var1, String16Table var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(var1);
      return this.applyTable(var3.getString(0, var3.bitLength() / var1, var1), var2);
   }

   public String32 readConstrainedString32FOO(int var1, String32Table var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(var1);
      return this.applyTable(var3.getString32(0, var3.bitLength() / var1, var1), var2);
   }

   public void skipConstrainedStringFOO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(var1);
   }

   public String readSemiconstrainedStringFOO(int var1, String16Table var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(var1);
      return this.applyTable(var3.getString(0, var3.bitLength() / var1, var1), var2);
   }

   public String32 readSemiconstrainedString32FOO(int var1, String32Table var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(var1);
      return this.applyTable(var3.getString32(0, var3.bitLength() / var1, var1), var2);
   }

   public void skipSemiconstrainedStringFOO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(var1);
   }

   public String readUnconstrainedStringFOO(int var1, String16Table var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(var1);
      return this.applyTable(var3.getString(0, var3.bitLength() / var1, var1), var2);
   }

   public String32 readUnconstrainedString32FOO(int var1, String32Table var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var3 = this.readFragmented(var1);
      return this.applyTable(var3.getString32(0, var3.bitLength() / var1, var1), var2);
   }

   public void skipUnconstrainedStringFOO(int var1) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(var1);
   }

   public String readMultibyteString() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getString(0, var1.bitLength() / 8, 8);
   }

   public void skipMultibyteString() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   public String readObjectDescriptor() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      BitString var1 = this.readFragmented(8);
      return var1.getString(0, var1.bitLength() / 8, 8);
   }

   public void skipObjectDescriptor() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(8);
   }

   private static int parseDecimal(String var0) throws BadDataException {
      int var1 = var0.length();
      int var2 = 0;

      for(int var4 = 0; var4 < var1; ++var4) {
         char var3 = var0.charAt(var4);
         if (var3 < '0' || var3 > '9') {
            throw new BadDataException();
         }

         var2 = 10 * var2 + (var3 - 48);
      }

      return var2;
   }

   private static double parseFraction(String var0) throws BadDataException {
      int var1 = var0.length();
      double var2 = 0.0;

      char var4;
      for(int var5 = var1; var5-- > 0; var2 = (var2 + (double)(var4 - 48)) / 10.0) {
         var4 = var0.charAt(var5);
         if (var4 < '0' || var4 > '9') {
            throw new BadDataException();
         }
      }

      return var2;
   }

   public GeneralizedTime stringToGeneralizedTime(String var1, int var2) throws IOException, EOFException, BadDataException {
      int var7 = 0;
      int var8 = 0;
      int var9 = 0;
      int var11 = 0;
      int var13 = var1.length();
      if (var1.length() < 10) {
         throw new BadDataException();
      } else {
         int var3 = parseDecimal(var1.substring(0, 4));
         int var4 = parseDecimal(var1.substring(4, 6)) - 1;
         int var5 = parseDecimal(var1.substring(6, 8));
         int var6 = parseDecimal(var1.substring(8, 10));
         char var14;
         if (var13 > 11) {
            var14 = var1.charAt(10);
            int var12;
            double var15;
            if (var14 != '.' && var14 != ',') {
               if (var14 >= '0' && var14 <= '9') {
                  var7 = parseDecimal(var1.substring(10, 12));
                  if (var13 > 13) {
                     var14 = var1.charAt(12);
                     if (var14 != '.' && var14 != ',') {
                        if (var14 >= '0' && var14 <= '9') {
                           var8 = parseDecimal(var1.substring(12, 14));
                           if (var13 > 15) {
                              var14 = var1.charAt(14);
                              if (var14 != '.' && var14 != ',') {
                                 var1 = var1.substring(14);
                                 var13 -= 14;
                              } else {
                                 for(var12 = 15; var12 < var13 && var1.charAt(var12) >= '0' && var1.charAt(var12) <= '9'; ++var12) {
                                 }

                                 var15 = parseFraction(var1.substring(15, var12));
                                 var15 *= 1000.0;
                                 var9 = (int)var15;
                                 var1 = var1.substring(var12);
                                 var13 -= var12;
                              }
                           } else {
                              var1 = var1.substring(14);
                              var13 -= 14;
                           }
                        } else {
                           var1 = var1.substring(12);
                           var13 -= 12;
                        }
                     } else {
                        for(var12 = 13; var12 < var13 && var1.charAt(var12) >= '0' && var1.charAt(var12) <= '9'; ++var12) {
                        }

                        var15 = parseFraction(var1.substring(13, var12));
                        var15 *= 60.0;
                        var8 = (int)var15;
                        var15 *= 1000.0;
                        var9 = (int)var15;
                        var1 = var1.substring(var12);
                        var13 -= var12;
                     }
                  } else {
                     var1 = var1.substring(12);
                     var13 -= 12;
                  }
               } else {
                  var1 = var1.substring(10);
                  var13 -= 10;
               }
            } else {
               for(var12 = 11; var12 < var13 && var1.charAt(var12) >= '0' && var1.charAt(var12) <= '9'; ++var12) {
               }

               var15 = parseFraction(var1.substring(11, var12));
               var15 *= 60.0;
               var7 = (int)var15;
               var15 *= 60.0;
               var8 = (int)var15;
               var15 *= 1000.0;
               var9 = (int)var15;
               var1 = var1.substring(var12);
               var13 -= var12;
            }
         } else {
            var1 = var1.substring(10);
            var13 -= 10;
         }

         if (var13 > 0) {
            var14 = var1.charAt(0);
            if (var14 == 'Z') {
               if (var13 != 1) {
                  throw new BadDataException();
               } else {
                  return new GeneralizedTime(var3, var4, var5, var6, var7, var8, var9, true);
               }
            } else if (var13 != 3 && var13 != 5 || var14 != '+' && var14 != '-') {
               throw new BadDataException();
            } else {
               int var10 = parseDecimal(var1.substring(1, 3));
               if (var13 == 5) {
                  var11 = parseDecimal(var1.substring(3, 5));
               }

               if (var14 == '-') {
                  var10 = -var10;
                  var11 = -var11;
               }

               return new GeneralizedTime(var3, var4, var5, var6, var7, var8, var9, (var10 * 60 + var11) * '\uea60');
            }
         } else {
            return new GeneralizedTime(var3, var4, var5, var6, var7, var8, var9);
         }
      }
   }

   public GeneralizedTime readConstrainedGeneralizedTimeB(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadDataException {
      String var5 = this.readConstrainedStringB(var1, var2, var4);
      return this.stringToGeneralizedTime(var5, var3);
   }

   public GeneralizedTime readConstrainedGeneralizedTimeO(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadDataException, BadDataException {
      String var5 = this.readConstrainedStringO(var1, var2, var4);
      return this.stringToGeneralizedTime(var5, var3);
   }

   public GeneralizedTime readConstrainedGeneralizedTimeBO(int var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var6 = this.readConstrainedStringBO(var1, var2, var3, var5);
      return this.stringToGeneralizedTime(var6, var4);
   }

   public GeneralizedTime readConstrainedGeneralizedTimeOO(int var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var6 = this.readConstrainedStringOO(var1, var2, var3, var5);
      return this.stringToGeneralizedTime(var6, var4);
   }

   public GeneralizedTime readConstrainedGeneralizedTimeFOO(int var1, int var2, String16Table var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var4 = this.readConstrainedStringFOO(var1, var3);
      return this.stringToGeneralizedTime(var4, var2);
   }

   public GeneralizedTime readSemiconstrainedGeneralizedTimeFOO(int var1, int var2, String16Table var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var4 = this.readConstrainedStringFOO(var1, var3);
      return this.stringToGeneralizedTime(var4, var2);
   }

   public GeneralizedTime readUnconstrainedGeneralizedTimeFOO(int var1, int var2, String16Table var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var4 = this.readConstrainedStringFOO(var1, var3);
      return this.stringToGeneralizedTime(var4, var2);
   }

   public UTCTime stringToUTCTime(String var1, int var2) throws IOException, EOFException, BadDataException {
      int var8 = 0;
      int var10 = 0;
      int var12 = var1.length();
      if (var1.length() < 10) {
         throw new BadDataException();
      } else {
         int var3 = parseDecimal(var1.substring(0, 2));
         int var4 = parseDecimal(var1.substring(2, 4)) - 1;
         int var5 = parseDecimal(var1.substring(4, 6));
         int var6 = parseDecimal(var1.substring(6, 8));
         int var7 = parseDecimal(var1.substring(8, 10));
         char var13;
         if (var12 > 11) {
            var13 = var1.charAt(10);
            if (var13 >= '0' && var13 <= '9') {
               var8 = parseDecimal(var1.substring(10, 12));
               var1 = var1.substring(12);
               var12 -= 12;
            } else {
               var1 = var1.substring(10);
               var12 -= 10;
            }
         } else {
            var1 = var1.substring(10);
            var12 -= 10;
         }

         if (var12 > 0) {
            var13 = var1.charAt(0);
            if (var13 == 'Z') {
               if (var12 != 1) {
                  throw new BadDataException();
               } else {
                  return new UTCTime(var3, var4, var5, var6, var7, var8, true);
               }
            } else if (var12 != 3 && var12 != 5 || var13 != '+' && var13 != '-') {
               throw new BadDataException();
            } else {
               int var9 = parseDecimal(var1.substring(1, 3));
               if (var12 == 5) {
                  var10 = parseDecimal(var1.substring(3, 5));
               }

               if (var13 == '-') {
                  var9 = -var9;
                  var10 = -var10;
               }

               return new UTCTime(var3, var4, var5, var6, var7, var8, (var9 * 60 + var10) * '\uea60');
            }
         } else {
            return new UTCTime(var3, var4, var5, var6, var7, var8);
         }
      }
   }

   public UTCTime readConstrainedUTCTimeB(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadDataException {
      String var5 = this.readConstrainedStringB(var1, var2, var4);
      return this.stringToUTCTime(var5, var3);
   }

   public UTCTime readConstrainedUTCTimeO(int var1, int var2, int var3, String16Table var4) throws IOException, EOFException, BadDataException, BadDataException {
      String var5 = this.readConstrainedStringO(var1, var2, var4);
      return this.stringToUTCTime(var5, var3);
   }

   public UTCTime readConstrainedUTCTimeBO(int var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var6 = this.readConstrainedStringBO(var1, var2, var3, var5);
      return this.stringToUTCTime(var6, var4);
   }

   public UTCTime readConstrainedUTCTimeOO(int var1, int var2, int var3, int var4, String16Table var5) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var6 = this.readConstrainedStringOO(var1, var2, var3, var5);
      return this.stringToUTCTime(var6, var4);
   }

   public UTCTime readConstrainedUTCTimeFOO(int var1, int var2, String16Table var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var4 = this.readConstrainedStringFOO(var1, var3);
      return this.stringToUTCTime(var4, var2);
   }

   public UTCTime readSemiconstrainedUTCTimeFOO(int var1, int var2, String16Table var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var4 = this.readConstrainedStringFOO(var1, var3);
      return this.stringToUTCTime(var4, var2);
   }

   public UTCTime readUnconstrainedUTCTimeFOO(int var1, int var2, String16Table var3) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      String var4 = this.readConstrainedStringFOO(var1, var3);
      return this.stringToUTCTime(var4, var2);
   }

   public External readExternal() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      BitString var4 = this.readBits(3);
      Identification var1;
      if (!var4.getBit(0)) {
         if (!var4.getBit(1)) {
            throw new BadDataException();
         }

         int var5 = this.readUnconstrainedIntegerFOO();
         var1 = new Identification(2, var5);
      } else {
         ObjectIdentifier var8;
         if (!var4.getBit(1)) {
            var8 = this.readObjectIdentifier();
            var1 = new Identification(1, var8);
         } else {
            var8 = this.readObjectIdentifier();
            int var6 = this.readUnconstrainedIntegerFOO();
            var1 = new Identification(3, var6, var8);
         }
      }

      ObjectDescriptor var2;
      if (var4.getBit(2)) {
         var2 = new ObjectDescriptor(this.readObjectDescriptor());
      } else {
         var2 = null;
      }

      DataValue var3;
      switch (this.readUInteger(2)) {
         case 0:
            Open var9 = new Open(this.readOpen());
            var3 = new DataValue(0, var9);
            break;
         case 1:
            BitString var10 = this.readFragmented(8);
            var3 = new DataValue(1, var10);
            break;
         case 2:
            BitString var7 = this.readFragmented(1);
            var3 = new DataValue(1, var7);
            break;
         default:
            throw new BadDataException();
      }

      return new External(var1, var2, var3);
   }

   public void skipExternal() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      BitString var1 = this.readBits(3);
      if (!var1.getBit(2)) {
         if (!var1.getBit(1)) {
            throw new BadDataException();
         }

         this.skipUnconstrainedIntegerFOO();
      } else if (!var1.getBit(1)) {
         this.skipObjectIdentifier();
      } else {
         this.skipObjectIdentifier();
         this.skipUnconstrainedIntegerFOO();
      }

      if (var1.getBit(0)) {
         this.skipObjectDescriptor();
      }

      switch (this.readUInteger(2)) {
         case 0:
            this.skipOpen();
            break;
         case 1:
            this.skipFragmented(8);
            break;
         case 2:
            this.skipFragmented(1);
            break;
         default:
            throw new BadDataException();
      }

   }

   public EmbeddedPDV readEmbeddedPDV() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      boolean var3 = this.readBit();
      int var4 = this.readSemiconstrainedIntegerSB(0);
      Identification var1;
      BitString var5;
      if (var3) {
         var5 = this.readBits(3);
         ObjectIdentifier var6;
         if (!var5.getBit(2)) {
            if (!var5.getBit(1)) {
               if (!var5.getBit(0)) {
                  var6 = this.readObjectIdentifier();
                  ObjectIdentifier var7 = this.readObjectIdentifier();
                  var1 = new Identification(0, var6, var7);
               } else {
                  var6 = this.readObjectIdentifier();
                  var1 = new Identification(1, var6);
               }
            } else if (!var5.getBit(0)) {
               int var8 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(2, var8);
            } else {
               var6 = this.readObjectIdentifier();
               int var9 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(3, var9, var6);
            }
         } else {
            if (var5.getBit(1)) {
               throw new BadDataException();
            }

            if (!var5.getBit(0)) {
               var6 = this.readObjectIdentifier();
               var1 = new Identification(4, var6);
            } else {
               var1 = new Identification(5);
            }
         }

         if (this.embeddedPdvIdentificationCache.size() != var4) {
            throw new BadDataException();
         }

         this.embeddedPdvIdentificationCache.addElement(dupIdentification(var1));
      } else {
         if (var4 >= this.embeddedPdvIdentificationCache.size()) {
            throw new BadDataException();
         }

         var1 = dupIdentification((Identification)this.embeddedPdvIdentificationCache.elementAt(var4));
      }

      var5 = this.readFragmented(1);
      DataValue var2 = new DataValue(1, var5);
      return new EmbeddedPDV(var1, var2);
   }

   public EmbeddedPDV readEmbeddedPDVOpt(Identification var1) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      BitString var3 = this.readFragmented(1);
      DataValue var2 = new DataValue(1, var3);
      return new EmbeddedPDV(var1, var2);
   }

   public void skipEmbeddedPDV() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      boolean var3 = this.readBit();
      int var4 = this.readSemiconstrainedIntegerSB(0);
      if (var3) {
         BitString var5 = this.readBits(3);
         Identification var1;
         ObjectIdentifier var6;
         if (!var5.getBit(2)) {
            if (!var5.getBit(1)) {
               if (!var5.getBit(0)) {
                  var6 = this.readObjectIdentifier();
                  ObjectIdentifier var7 = this.readObjectIdentifier();
                  var1 = new Identification(0, var6, var7);
               } else {
                  var6 = this.readObjectIdentifier();
                  var1 = new Identification(1, var6);
               }
            } else if (!var5.getBit(0)) {
               int var8 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(2, var8);
            } else {
               var6 = this.readObjectIdentifier();
               int var9 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(3, var9, var6);
            }
         } else {
            if (var5.getBit(1)) {
               throw new BadDataException();
            }

            if (!var5.getBit(0)) {
               var6 = this.readObjectIdentifier();
               var1 = new Identification(4, var6);
            } else {
               var1 = new Identification(5);
            }
         }

         if (this.embeddedPdvIdentificationCache.size() != var4) {
            throw new BadDataException();
         }

         this.embeddedPdvIdentificationCache.addElement(var1);
      } else if (var4 >= this.embeddedPdvIdentificationCache.size()) {
         throw new BadDataException();
      }

      this.skipFragmented(8);
   }

   public void skipEmbeddedPDVOpt(Identification var1) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.skipFragmented(8);
   }

   public CharacterString readCharacterString() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      boolean var3 = this.readBit();
      int var4 = this.readSemiconstrainedIntegerSB(0);
      Identification var1;
      BitString var5;
      if (var3) {
         var5 = this.readBits(3);
         ObjectIdentifier var6;
         if (!var5.getBit(2)) {
            if (!var5.getBit(1)) {
               if (!var5.getBit(0)) {
                  var6 = this.readObjectIdentifier();
                  ObjectIdentifier var7 = this.readObjectIdentifier();
                  var1 = new Identification(0, var6, var7);
               } else {
                  var6 = this.readObjectIdentifier();
                  var1 = new Identification(1, var6);
               }
            } else if (!var5.getBit(0)) {
               int var8 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(2, var8);
            } else {
               var6 = this.readObjectIdentifier();
               int var9 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(3, var9, var6);
            }
         } else {
            if (var5.getBit(1)) {
               throw new BadDataException();
            }

            if (!var5.getBit(0)) {
               var6 = this.readObjectIdentifier();
               var1 = new Identification(4, var6);
            } else {
               var1 = new Identification(5);
            }
         }

         if (this.characterStringIdentificationCache.size() != var4) {
            throw new BadDataException();
         }

         this.characterStringIdentificationCache.addElement(dupIdentification(var1));
      } else {
         if (var4 >= this.characterStringIdentificationCache.size()) {
            throw new BadDataException();
         }

         var1 = dupIdentification((Identification)this.characterStringIdentificationCache.elementAt(var4));
      }

      var5 = this.readFragmented(8);
      OctetString var10 = var5.getOctets(0, var5.bitLength() / 8);
      DataValue var2 = new DataValue(2, var10);
      return new CharacterString(var1, var2);
   }

   public CharacterString readCharacterStringOpt(Identification var1) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      BitString var3 = this.readFragmented(8);
      OctetString var4 = var3.getOctets(0, var3.bitLength() / 8);
      DataValue var2 = new DataValue(2, var4);
      return new CharacterString(var1, var2);
   }

   public void skipCharacterString() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      boolean var3 = this.readBit();
      int var4 = this.readSemiconstrainedIntegerSB(0);
      if (var3) {
         BitString var5 = this.readBits(3);
         Identification var1;
         ObjectIdentifier var6;
         if (!var5.getBit(2)) {
            if (!var5.getBit(1)) {
               if (!var5.getBit(0)) {
                  var6 = this.readObjectIdentifier();
                  ObjectIdentifier var7 = this.readObjectIdentifier();
                  var1 = new Identification(0, var6, var7);
               } else {
                  var6 = this.readObjectIdentifier();
                  var1 = new Identification(1, var6);
               }
            } else if (!var5.getBit(0)) {
               int var8 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(2, var8);
            } else {
               var6 = this.readObjectIdentifier();
               int var9 = this.readUnconstrainedIntegerFOO();
               var1 = new Identification(3, var9, var6);
            }
         } else {
            if (var5.getBit(1)) {
               throw new BadDataException();
            }

            if (!var5.getBit(0)) {
               var6 = this.readObjectIdentifier();
               var1 = new Identification(4, var6);
            } else {
               var1 = new Identification(5);
            }
         }

         if (this.characterStringIdentificationCache.size() != var4) {
            throw new BadDataException();
         }

         this.characterStringIdentificationCache.addElement(var1);
      } else if (var4 >= this.characterStringIdentificationCache.size()) {
         throw new BadDataException();
      }

      this.skipFragmented(8);
   }

   public void skipCharacterStringOpt(Identification var1) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.skipFragmented(8);
   }

   public OctetString readOpen() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      BitString var1 = this.readFragmented(8);
      OctetString var2 = var1.getOctets(0, var1.bitLength() / 8);
      return var2;
   }

   public void skipOpen() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.skipFragmented(8);
   }

   public TrimmedBitString readBitFieldB(int var1) throws IOException, EOFException {
      return new TrimmedBitString(this.readBits(var1));
   }

   public void skipBitFieldB(int var1) throws IOException, EOFException {
      this.skip((long)var1);
   }

   public TrimmedBitString readBitFieldFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      return new TrimmedBitString(this.readFragmented(1));
   }

   public void skipBitFieldFOO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      this.skipFragmented(1);
   }

   public TrimmedBitString readBitFieldSO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      if (this.readBit()) {
         return new TrimmedBitString(this.readFragmented(1));
      } else {
         int var1 = this.readUInteger(6) + 1;
         return new TrimmedBitString(this.readBits(var1));
      }
   }

   public void skipBitFieldSO() throws IOException, EOFException, ValueTooLargeException, BadDataException {
      if (this.readBit()) {
         this.skipFragmented(1);
      } else {
         int var1 = this.readUInteger(6) + 1;
         this.skip((long)var1);
      }

   }

   public BitString readExtension() throws IOException, EOFException, BadDataException {
      return this.readFragmented(8);
   }

   public void skipExtension() throws IOException, EOFException, BadDataException {
      this.skipFragmented(8);
   }

   public void skipExtensions(BitString var1, int var2) throws IOException, EOFException, ValueTooLargeException, BadDataException {
      int var3 = var1.bitLength();

      while(var2 < var3) {
         if (var1.getBit(var2++)) {
            this.skipFragmented(8);
         }
      }

   }

   public PERDecoder createExtensionDecoder() throws IOException, EOFException, BadDataException {
      BitString var1 = this.readFragmented(8);
      DataDecoder var3 = new DataDecoder(var1);
      return this.createExtensionClone(var3);
   }

   public void closeExtensionDecoder(PERDecoder var1) throws IOException, EOFException, BadDataException {
      var1.flushIn();
   }

   public abstract PERDecoder createExtensionClone(Decoder var1);

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
