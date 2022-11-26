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
import com.asn1c.core.String32;
import com.asn1c.core.String32Buffer;
import com.asn1c.core.TrimmedBitString;
import com.asn1c.core.UTCTime;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public abstract class DERDecoder extends FilterDecoder {
   private Vector embeddedPdvIdentificationCache;
   private Vector characterStringIdentificationCache;
   private HashMap decoderMap;
   private FactoryMap factoryMap;
   protected BaseTypeFactory baseTypeFactory;
   private boolean isIndefiniteLengthDecoder;
   private boolean tagLengthCached = false;
   private int currentTag;
   private int currentLength;
   private boolean currentIndefiniteLength;
   private byte[] currentTagLengthOctets = new byte[16];
   private int currentTagLengthLength;
   private static int done = -1;
   private static final int[] tab = new int[]{0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};

   public DERDecoder(Decoder var1, String var2, FactoryMap var3, byte[] var4) {
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
      this.isIndefiniteLengthDecoder = false;
   }

   protected DERDecoder(Decoder var1, DERDecoder var2, boolean var3) {
      super(var1);
      if (done == -1) {
         throw new InternalError();
      } else {
         this.embeddedPdvIdentificationCache = new Vector();
         this.characterStringIdentificationCache = new Vector();
         this.decoderMap = var2.decoderMap;
         this.factoryMap = var2.factoryMap;
         this.baseTypeFactory = var2.baseTypeFactory;
         this.isIndefiniteLengthDecoder = var3;
      }
   }

   protected Factory getFactory(String var1) {
      return this.factoryMap.get(var1);
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

   protected FactoryMap getFactoryMap() {
      return this.factoryMap;
   }

   protected DERDecoder getDecoder(String var1) {
      return (DERDecoder)this.decoderMap.get(var1);
   }

   public void startup() {
      Collection var1 = this.decoderMap.values();

      DERDecoder var3;
      for(Iterator var2 = var1.iterator(); var2.hasNext(); var3.tagLengthCached = false) {
         var3 = (DERDecoder)var2.next();
      }

   }

   public void readTagLength() throws BadDataException, IOException {
      if (!this.tagLengthCached) {
         this.currentTagLengthLength = 0;
         int var1 = this.readUInteger(8);
         this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)var1;
         int var2 = var1 & 224;
         int var3 = var1 & 31;
         if (var3 == 31) {
            var3 = 0;

            do {
               var1 = this.readUInteger(8);
               this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)var1;
               if ((var3 & -536870912) != 0) {
                  throw new BadDataException();
               }

               var3 = var3 << 7 | var1 & 127;
            } while((var1 & -128) != 0);
         }

         this.currentTag = var2 << 24 | var3;
         var1 = this.readUInteger(8);
         this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)var1;
         if (var1 < 128) {
            this.currentLength = var1;
            this.currentIndefiniteLength = false;
         } else if (var1 == 128 && (this.currentTag & 536870912) != 0) {
            this.currentLength = 0;
            this.currentIndefiniteLength = true;
         } else {
            if (var1 < 129 || var1 > 132) {
               throw new BadDataException();
            }

            try {
               this.currentLength = this.readUInteger((var1 - 128) * 8);
            } catch (ValueTooLargeException var5) {
               throw new BadDataException();
            }

            this.currentIndefiniteLength = false;
            switch (var1) {
               case -124:
                  this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)(this.currentLength >> 24);
               case -125:
                  this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)(this.currentLength >> 16);
               case -126:
                  this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)(this.currentLength >> 8);
               case -127:
                  this.currentTagLengthOctets[this.currentTagLengthLength++] = (byte)this.currentLength;
            }
         }

         this.tagLengthCached = true;
         if (done != 1) {
            boolean var6 = false;
         }

      }
   }

   protected void checkTag(int var1, boolean var2) throws BadDataException, IOException {
      this.readTagLength();
      if (var2) {
         if (this.currentTag != var1) {
            throw new BadDataException();
         }
      } else if ((this.currentTag & -536870913) != (var1 & -536870913)) {
         throw new BadDataException();
      }

   }

   public int peekTag() throws BadDataException, IOException {
      if (this.notEndOfContents()) {
         this.readTagLength();
         return this.currentTag & -536870913;
      } else {
         return 0;
      }
   }

   public DERDecoder createExplicitTagDecoder(int var1) throws BadDataException, IOException {
      this.checkTag(var1 | 536870912, true);
      if (this.currentIndefiniteLength) {
         return this.createExtensionClone(this, true);
      } else {
         BitString var2 = this.readBits(this.currentLength * 8);
         return this.createExtensionClone(new DataDecoder(var2), false);
      }
   }

   public void readEndOfContents(DERDecoder var1) throws BadDataException, IOException {
      if (var1.isIndefiniteLengthDecoder) {
         var1.checkTag(0, true);
      } else {
         var1.flushIn();
      }

      this.tagLengthCached = false;
   }

   public abstract DERDecoder createExtensionClone(Decoder var1, boolean var2);

   public boolean notEndOfContents() throws BadDataException, IOException {
      if (!this.isIndefiniteLengthDecoder) {
         return !this.checkEndOfData();
      } else {
         this.readTagLength();
         return this.currentTag != 0 || this.currentLength != 0;
      }
   }

   public void skip() throws BadDataException, IOException {
      this.readTagLength();
      super.skip((long)(this.currentLength * 8));
      this.tagLengthCached = false;
   }

   public Null readNull(int var1) throws BadDataException, IOException {
      this.checkTag(var1, true);
      if (this.currentLength != 0) {
         throw new BadDataException();
      } else {
         this.tagLengthCached = false;
         return Null.NULL;
      }
   }

   public boolean readBoolean(int var1) throws BadDataException, IOException {
      this.checkTag(var1, true);
      if (this.currentLength != 1) {
         throw new BadDataException();
      } else {
         byte var2 = (byte)this.readSInteger(8);
         this.tagLengthCached = false;
         return var2 != 0;
      }
   }

   public byte readIntegerS8(int var1) throws BadDataException, IOException, ValueTooLargeException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      return (byte)this.readSInteger(this.currentLength * 8);
   }

   public short readIntegerS16(int var1) throws BadDataException, IOException, ValueTooLargeException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      return (short)this.readSInteger(this.currentLength * 8);
   }

   public int readIntegerS32(int var1) throws BadDataException, IOException, ValueTooLargeException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      return this.readSInteger(this.currentLength * 8);
   }

   public long readIntegerS64(int var1) throws BadDataException, IOException, ValueTooLargeException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      return this.readSLong(this.currentLength * 8);
   }

   public BigInteger readIntegerSX(int var1) throws BadDataException, IOException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      return this.readSBigInteger(this.currentLength * 8);
   }

   public double readRealS64(int var1) throws BadDataException, IOException, ValueTooLargeException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      BitString var2 = this.readBits(this.currentLength * 8);
      if (var2.bitLength() == 0) {
         return 0.0;
      } else if (var2.getBit(0)) {
         boolean var12 = var2.getBit(1);
         byte var4;
         if (var2.getBit(2)) {
            if (var2.getBit(3)) {
               throw new BadDataException();
            }

            var4 = 4;
         } else if (var2.getBit(3)) {
            var4 = 3;
         } else {
            var4 = 1;
         }

         int var5 = var2.getUInteger(4, 2);
         int var6;
         int var7;
         if (var2.getBit(6)) {
            if (var2.getBit(7)) {
               int var10 = var2.getUInteger(8, 8);
               var6 = var2.getSInteger(16, (var10 + 1) * 8);
               var7 = 24 + var10 * 8;
            } else {
               var6 = var2.getSInteger(8, 24);
               var7 = 32;
            }
         } else if (var2.getBit(7)) {
            var6 = var2.getSInteger(8, 16);
            var7 = 24;
         } else {
            var6 = var2.getSInteger(8, 8);
            var7 = 16;
         }

         var6 = var6 * var4 + var5;
         return var12 ? (double)(-var2.getULong(var7, var2.bitLength() - var7)) * Math.pow(2.0, (double)var6) : (double)var2.getULong(var7, var2.bitLength() - var7) * Math.pow(2.0, (double)var6);
      } else if (var2.getBit(1)) {
         if (var2.bitLength() != 8) {
            throw new BadDataException();
         } else {
            switch (var2.getUInteger(2, 6)) {
               case 0:
                  return Double.POSITIVE_INFINITY;
               case 1:
                  return Double.NEGATIVE_INFINITY;
               default:
                  throw new BadDataException();
            }
         }
      } else {
         String var3 = var2.getString(8, var2.bitLength() / 8 - 1, 8);
         var3 = var3.replace(',', '.');

         try {
            return Real64.valueOf(var3).doubleValue();
         } catch (NumberFormatException var11) {
            throw new BadDataException();
         }
      }
   }

   public BitString readBitString(int var1) throws BadDataException, IOException {
      this.readTagLength();
      BitString var3;
      if ((this.currentTag & 536870912) == 0) {
         this.checkTag(var1, true);
         this.tagLengthCached = false;
         byte var4 = (byte)this.readSInteger(8);
         if (var4 >= 0 && var4 <= 7) {
            var3 = this.readBits(this.currentLength * 8 - var4 - 8);
            super.skip((long)var4);
            return var3;
         } else {
            throw new BadDataException();
         }
      } else {
         DERDecoder var2 = this.createExplicitTagDecoder(var1);
         this.tagLengthCached = false;
         var3 = new BitString();

         while(var2.notEndOfContents()) {
            var3.append(var2.readBitString(3));
         }

         this.readEndOfContents(var2);
         return var3;
      }
   }

   public TrimmedBitString readTrimmedBitString(int var1) throws BadDataException, IOException {
      return new TrimmedBitString(this.readBitString(var1));
   }

   public OctetString readOctetString(int var1) throws BadDataException, IOException {
      this.readTagLength();
      if ((this.currentTag & 536870912) == 0) {
         this.checkTag(var1, true);
         this.tagLengthCached = false;
         BitString var4 = this.readBits(this.currentLength * 8);
         return var4.getOctets(0, this.currentLength);
      } else {
         DERDecoder var2 = this.createExplicitTagDecoder(var1);
         this.tagLengthCached = false;
         OctetString var3 = new OctetString();

         while(var2.notEndOfContents()) {
            var3.append(var2.readOctetString(4));
         }

         this.readEndOfContents(var2);
         return var3;
      }
   }

   public String readString8(int var1) throws BadDataException, IOException {
      this.readTagLength();
      if ((this.currentTag & 536870912) == 0) {
         this.checkTag(var1, true);
         this.tagLengthCached = false;
         BitString var4 = this.readBits(this.currentLength * 8);
         return var4.getString(0, var4.bitLength() / 8, 8);
      } else {
         DERDecoder var2 = this.createExplicitTagDecoder(var1);
         this.tagLengthCached = false;
         StringBuffer var3 = new StringBuffer();

         while(var2.notEndOfContents()) {
            var3.append(var2.readString8(4));
         }

         this.readEndOfContents(var2);
         return var3.toString();
      }
   }

   public String readString16(int var1) throws BadDataException, IOException {
      this.readTagLength();
      if ((this.currentTag & 536870912) == 0) {
         this.checkTag(var1, true);
         this.tagLengthCached = false;
         BitString var4 = this.readBits(this.currentLength * 8);
         return var4.getString(0, var4.bitLength() / 16, 16);
      } else {
         DERDecoder var2 = this.createExplicitTagDecoder(var1);
         this.tagLengthCached = false;
         StringBuffer var3 = new StringBuffer();

         while(var2.notEndOfContents()) {
            var3.append(var2.readString16(4));
         }

         this.readEndOfContents(var2);
         return var3.toString();
      }
   }

   public String32 readString32(int var1) throws BadDataException, IOException {
      this.readTagLength();
      if ((this.currentTag & 536870912) == 0) {
         this.checkTag(var1, true);
         this.tagLengthCached = false;
         BitString var4 = this.readBits(this.currentLength * 8);
         return var4.getString32(0, var4.bitLength() / 32, 32);
      } else {
         DERDecoder var2 = this.createExplicitTagDecoder(var1);
         this.tagLengthCached = false;
         String32Buffer var3 = new String32Buffer();

         while(var2.notEndOfContents()) {
            var3.append(var2.readString32(4));
         }

         this.readEndOfContents(var2);
         return new String32(var3);
      }
   }

   public ObjectIdentifier readObjectIdentifier(int var1) throws BadDataException, IOException, ValueTooLargeException {
      this.checkTag(var1, true);
      this.tagLengthCached = false;
      BitString var2 = this.readBits(this.currentLength * 8);
      int var3 = var2.bitLength();
      int var4 = 0;
      ObjectIdentifier var5 = new ObjectIdentifier();
      int var6 = 0;

      while(var4 < var3) {
         long var7 = 0L;

         do {
            if (var4 >= var3) {
               throw new BadDataException();
            }

            if (var7 > 72057594037927935L) {
               throw new ValueTooLargeException();
            }

            var7 = var7 << 7 | (long)var2.getUInteger(var4 + 1, 7);
            var4 += 8;
         } while(var2.getBit(var4 - 8));

         if (var6 == 0) {
            if (var7 < 80L) {
               var5.set(var6++, var7 / 40L);
               var5.set(var6++, var7 % 40L);
            } else {
               var5.set(var6++, 2L);
               var5.set(var6++, var7 - 80L);
            }
         } else {
            var5.set(var6++, var7);
         }
      }

      return var5;
   }

   public External readExternal(int var1) throws BadDataException, IOException, ValueTooLargeException {
      DERDecoder var7 = this.createExplicitTagDecoder(var1);
      var7.readTagLength();
      int var3;
      Identification var4;
      if (var7.currentTag == 6) {
         ObjectIdentifier var2 = var7.readObjectIdentifier(6);
         var7.readTagLength();
         if (var7.currentTag == 2) {
            var3 = var7.readIntegerS32(2);
            var4 = new Identification(3, var3, var2);
         } else {
            var4 = new Identification(1, var2);
         }
      } else {
         if (var7.currentTag != 2) {
            throw new BadDataException();
         }

         var3 = var7.readIntegerS32(2);
         var4 = new Identification(2, var3);
      }

      var7.readTagLength();
      ObjectDescriptor var5;
      if (var7.currentTag == 7) {
         var5 = new ObjectDescriptor(var7.readString8(7));
      } else {
         var5 = null;
      }

      var7.readTagLength();
      DataValue var6;
      switch (var7.currentTag & -536870913) {
         case Integer.MIN_VALUE:
            DERDecoder var8 = var7.createExplicitTagDecoder(Integer.MIN_VALUE);
            Open var9 = new Open(var8.readOpen());
            var7.readEndOfContents(var8);
            var6 = new DataValue(0, var9);
            break;
         case -2147483647:
            OctetString var10 = var7.readOctetString(-2147483647);
            var6 = new DataValue(1, new BitString(var10));
            break;
         case -2147483646:
            BitString var11 = var7.readBitString(-2147483646);
            var6 = new DataValue(1, var11);
            break;
         default:
            throw new BadDataException();
      }

      this.readEndOfContents(var7);
      return new External(var4, var5, var6);
   }

   public EmbeddedPDV readEmbeddedPDV(int var1) throws BadDataException, IOException {
      throw new InternalError("Decoding of EMBEDDED PDV for DER not implemented yet.");
   }

   public CharacterString readCharacterString(int var1) throws BadDataException, IOException {
      throw new InternalError("Decoding of CHARACTER STRING for DER not implemented yet.");
   }

   private static int parseDecimal(String var0) throws BadDataException, IOException {
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

   private static double parseFraction(String var0) throws BadDataException, IOException {
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

   public GeneralizedTime readGeneralizedTime(int var1, int var2) throws BadDataException, IOException {
      String var3 = this.readString8(var1);
      return this.stringToGeneralizedTime(var3, var2);
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

   public UTCTime readUTCTime(int var1, int var2) throws BadDataException, IOException {
      String var3 = this.readString8(var1);
      return this.stringToUTCTime(var3, var2);
   }

   public OctetString readOpen() throws BadDataException, IOException {
      this.readTagLength();
      OctetString var1 = new OctetString(this.currentTagLengthOctets, 0, this.currentTagLengthLength);
      if ((this.currentTag & 536870912) == 0) {
         this.checkTag(this.currentTag, true);
         this.tagLengthCached = false;
         BitString var3 = this.readBits(this.currentLength * 8);
         var1.append(var3.getOctets(0, this.currentLength));
         return var1;
      } else {
         DERDecoder var2 = this.createExplicitTagDecoder(this.currentTag);
         this.tagLengthCached = false;

         while(var2.notEndOfContents()) {
            var1.append(var2.readOpen());
         }

         this.readEndOfContents(var2);
         if (this.isIndefiniteLengthDecoder) {
            var1.appendOctet((byte)0);
            var1.appendOctet((byte)0);
         }

         return var1;
      }
   }

   public void checkMandatoryComponents(TrimmedBitString var1, int var2) throws BadDataException {
      var1.getOnes(0, var2);
   }
}
