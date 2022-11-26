package weblogic.transaction.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.zip.CRC32;
import javax.transaction.xa.Xid;
import weblogic.transaction.WLXid;
import weblogic.transaction.XIDFactory;

public class XidImpl implements WLXid, Externalizable {
   private static final long serialVersionUID = -6830543815667704574L;
   private static final Object counterLock = new Object() {
   };
   private static short counter = 32767;
   private static final int NUM_RANDOM_BYTES = 4;
   private static final int NUM_COUNTER_BYTES = 2;
   private static SecureRandom rnd = null;
   private static byte[] rndBytes = null;
   private int formatId = XIDFactory.getFormatId();
   private byte[] gtrid = null;
   private byte[] bqual = null;
   private int hashcode = 0;
   private boolean isSiteNameSet;
   private boolean isClusterCall;
   private int isClusterwideRecoveryEnabled = -1;
   public static final int isClusterwideRecoveryEnabledNotSet = -1;
   public static final int isClusterwideRecoveryEnabledExplicitlySetToFalse = 0;
   public static final int isClusterwideRecoveryEnabledExplicitlySetToTrue = 1;
   public static final char DETERMINER_BYTE = '~';
   public static final char NON_DETERMINER_BYTE = '!';

   public XidImpl() {
   }

   public XidImpl(byte[] agtrid) {
      this.gtrid = agtrid;
   }

   private XidImpl(byte[] agtrid, short acounter) {
      this.gtrid = agtrid;
      this.hashcode = acounter;
   }

   public XidImpl(byte[] agtrid, byte[] abqual) {
      this.gtrid = agtrid;
      this.bqual = abqual;
   }

   public XidImpl(int aformatId, byte[] agtrid, byte[] abqual) {
      this.gtrid = agtrid;
      this.bqual = abqual;
      this.formatId = aformatId;
   }

   public XidImpl(Xid xid) {
      this.gtrid = xid.getGlobalTransactionId();
      this.bqual = xid.getBranchQualifier();
      this.formatId = xid.getFormatId();
   }

   public static XidImpl create(String xid) {
      StringTokenizer tok = new StringTokenizer(xid, "-");
      if (tok.countTokens() < 2) {
         return null;
      } else {
         String formatIdString = tok.nextToken();
         String gtridString = tok.nextToken();
         String bqualString = null;
         if (tok.hasMoreElements()) {
            bqualString = tok.nextToken();
         }

         return new XidImpl(Integer.parseInt(formatIdString, 16), stringToByteArray(gtridString), bqualString != null ? stringToByteArray(bqualString) : null);
      }
   }

   public byte[] getGlobalTransactionId() {
      return this.gtrid;
   }

   public byte[] getBranchQualifier() {
      return this.bqual;
   }

   public byte[] getTruncatedBranchQualifier(String resName) {
      return getBranchQualifier(resName);
   }

   public int getFormatId() {
      return this.formatId;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XidImpl && ((XidImpl)obj).formatId == XIDFactory.getFormatId()) {
         if (this.formatId != XIDFactory.getFormatId()) {
            return false;
         } else {
            XidImpl xid = (XidImpl)obj;
            byte[] a = this.gtrid;
            byte[] b = xid.gtrid;
            if (a == b) {
               return true;
            } else {
               for(int i = getUIDLength() - 1; i >= 0; --i) {
                  if (a[i] != b[i]) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else if (obj != null && obj instanceof Xid) {
         Xid thatXid = (Xid)obj;
         return this.getFormatId() == thatXid.getFormatId() && Arrays.equals(this.getGlobalTransactionId(), thatXid.getGlobalTransactionId()) && Arrays.equals(this.getBranchQualifier(), thatXid.getBranchQualifier());
      } else {
         return false;
      }
   }

   public String toString() {
      return XAResourceHelper.xidToString(this);
   }

   public String toString(boolean includeBranchQualifier) {
      return XAResourceHelper.xidToString(this, includeBranchQualifier);
   }

   public int hashCode() {
      if (this.hashcode == 0) {
         if (this.formatId == XIDFactory.getFormatId()) {
            this.hashcode = readShort(this.gtrid, 0);
         } else {
            int tmpHash = 0;
            int i;
            if (this.gtrid != null) {
               for(i = 0; i < this.gtrid.length; ++i) {
                  tmpHash += this.gtrid[i];
               }
            }

            if (this.bqual != null) {
               for(i = 0; i < this.bqual.length; ++i) {
                  tmpHash += this.bqual[i];
               }
            }

            this.hashcode = tmpHash;
         }
      }

      return this.hashcode;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeInt(this.getFormatId());
      if (this.gtrid == null) {
         oo.write(0);
      } else {
         IOHelper.writeCompressedInt(oo, this.gtrid.length);
         oo.write(this.gtrid);
      }

      if (this.isClusterwideRecoveryEnabled() == 1) {
         byte[] bqualWithClusterByte = new byte[65];
         System.arraycopy(this.bqual, 0, bqualWithClusterByte, 0, this.bqual.length);
         bqualWithClusterByte[64] = 1;
         this.bqual = bqualWithClusterByte;
      }

      if (this.bqual == null) {
         oo.write(0);
      } else {
         IOHelper.writeCompressedInt(oo, this.bqual.length);
         oo.write(this.bqual);
      }

   }

   public void readExternal(ObjectInput oi) throws IOException {
      this.formatId = oi.readInt();
      int len = IOHelper.readCompressedInt(oi);
      if (len > 0) {
         this.gtrid = new byte[len];
         oi.readFully(this.gtrid);
      } else {
         this.gtrid = null;
      }

      len = IOHelper.readCompressedInt(oi);
      if (len > 0) {
         this.bqual = new byte[len];
         oi.readFully(this.bqual);
         if (this.bqual.length > 64) {
            this.isClusterwideRecoveryEnabled = this.bqual[64];
            byte[] bqualWithoutClusterByte = new byte[64];
            System.arraycopy(this.bqual, 0, bqualWithoutClusterByte, 0, bqualWithoutClusterByte.length);
            this.bqual = bqualWithoutClusterByte;
         }
      } else {
         this.bqual = null;
      }

   }

   void setBranchQualifier(String bq) {
      this.setBranchQualifier(bq.getBytes());
   }

   void setBranchQualifier(byte[] bqual, boolean setDirectly) {
      if (setDirectly) {
         this.bqual = bqual;
      } else {
         this.setBranchQualifier(bqual);
      }

   }

   void setCoordinatorURL(byte[] aCoURLHash) {
      if (this.gtrid.length <= getUIDLength()) {
         this.setCoordinatorURLHash(aCoURLHash);
      }
   }

   private void setCoordinatorURLHash(byte[] coUrlHash) {
      byte[] newgtrid = new byte[getUIDLength() + coUrlHash.length];
      System.arraycopy(this.gtrid, 0, newgtrid, 0, getUIDLength());
      System.arraycopy(coUrlHash, 0, newgtrid, getUIDLength(), coUrlHash.length);
      this.gtrid = newgtrid;
   }

   void setCoordinatorURL(byte[] aCoURLHash, byte[] siteName) {
      if (this.gtrid.length > getUIDLength()) {
         if (siteName == null || siteName.length == 0 || this.isSiteNameSet) {
            return;
         }

         this.isSiteNameSet = true;
      }

      this.setCoordinatorURLHash(aCoURLHash, siteName);
   }

   public void setClusterCall(boolean isClusterCall) {
      this.isClusterCall = isClusterCall;
   }

   public boolean isClusterCall() {
      return this.isClusterCall;
   }

   public static XidImpl create(Xid foreignXid) {
      return foreignXid instanceof XidImpl ? (XidImpl)foreignXid : new XidImpl(foreignXid.getFormatId(), foreignXid.getGlobalTransactionId(), foreignXid.getBranchQualifier());
   }

   static XidImpl create(int formatId, byte[] gtrid, byte[] bqual) {
      return new XidImpl(formatId, gtrid, bqual);
   }

   static XidImpl create(byte[] agtrid) {
      return new XidImpl(agtrid);
   }

   public static XidImpl create() {
      byte[] bytes;
      short c;
      synchronized(counterLock) {
         if (counter == 32767) {
            seedRandomGenerator();
            counter = 0;
         } else {
            ++counter;
         }

         c = counter;
         bytes = rndBytes;
      }

      byte[] gtrid = new byte[getUIDLength()];
      writeShort(gtrid, 0, c);
      System.arraycopy(bytes, 0, gtrid, 2, 4);
      return new XidImpl(gtrid, c);
   }

   XidImpl newBranch(String name) {
      XidImpl x = new XidImpl();
      x.gtrid = this.gtrid;
      x.setBranchQualifier(name);
      return x;
   }

   XidImpl newBranch(byte[] aBqual) {
      XidImpl x = new XidImpl();
      x.gtrid = this.gtrid;
      x.bqual = aBqual;
      return x;
   }

   static byte[] getBranchQualifier(String resourceName) {
      return getBranchQualifier(resourceName, false);
   }

   static byte[] getBranchQualifier(String resourceName, boolean isDeterminer) {
      if (resourceName == null) {
         return null;
      } else {
         byte[] bq = resourceName.getBytes();
         if (bq.length > 60) {
            CRC32 crc = new CRC32();
            crc.update(bq);
            byte[] newbq = bq;
            if (bq.length != 64) {
               newbq = new byte[64];
               System.arraycopy(bq, 0, newbq, 0, 60);
            }

            writeInt(newbq, 60, (int)crc.getValue());
            return getBranchQualifierWithDeterminerDesignation(newbq, isDeterminer);
         } else {
            return getBranchQualifierWithDeterminerDesignation(bq, isDeterminer);
         }
      }
   }

   private static byte[] getBranchQualifierWithDeterminerDesignation(byte[] bq, boolean isDeterminer) {
      boolean isBranchQualMaxSize = bq.length >= 64;
      if (!isDeterminer && !isBranchQualMaxSize) {
         return bq;
      } else if (!isBranchQualMaxSize) {
         byte[] branchqualForDeterminer = new byte[64];
         System.arraycopy(bq, 0, branchqualForDeterminer, 0, bq.length);
         branchqualForDeterminer[63] = 126;
         return branchqualForDeterminer;
      } else {
         if (isDeterminer) {
            bq[63] = 126;
         } else if (bq[63] == 126) {
            bq[63] = 33;
         }

         return bq;
      }
   }

   private void setBranchQualifier(byte[] bq) {
      if (bq != null && bq.length >= 64) {
         this.bqual = new byte[64];
         System.arraycopy(bq, 0, this.bqual, 0, 64);
      } else {
         this.bqual = bq;
      }

   }

   static int getUIDLength() {
      return 6;
   }

   private void setCoordinatorURLHash(byte[] coUrlHash, byte[] siteName) {
      byte[] newgtrid = new byte[getUIDLength() + coUrlHash.length + siteName.length];
      System.arraycopy(this.gtrid, 0, newgtrid, 0, getUIDLength());
      System.arraycopy(coUrlHash, 0, newgtrid, getUIDLength(), coUrlHash.length);
      System.arraycopy(siteName, 0, newgtrid, getUIDLength() + coUrlHash.length, siteName.length);
      this.gtrid = newgtrid;
   }

   private static void seedRandomGenerator() {
      rnd.setSeed(System.currentTimeMillis());
      rnd.setSeed(Runtime.getRuntime().freeMemory());
      rnd.setSeed(Runtime.getRuntime().totalMemory());
      rnd.setSeed(System.currentTimeMillis());

      try {
         rnd.setSeed(System.getProperty("java.version", "default").getBytes());
         rnd.setSeed(System.getProperty("java.vendor", "default").getBytes());
         rnd.setSeed(System.getProperty("os.name", "default").getBytes());
         rnd.setSeed(System.getProperty("os.version", "default").getBytes());
      } catch (Exception var1) {
      }

      rndBytes = new byte[4];
      rnd.nextBytes(rndBytes);
   }

   private static void writeShort(byte[] array, int pos, short value) {
      array[pos++] = (byte)(value >>> 8);
      array[pos] = (byte)(value >>> 0);
   }

   private static short readShort(byte[] array, int pos) {
      return (short)((array[pos++] & 255) << 8 | array[pos] & 255);
   }

   static void writeInt(byte[] array, int pos, int value) {
      array[pos++] = (byte)(value >>> 24);
      array[pos++] = (byte)(value >>> 16);
      array[pos++] = (byte)(value >>> 8);
      array[pos] = (byte)(value >>> 0);
   }

   private static byte[] stringToByteArray(String str) {
      if (str == null) {
         return new byte[0];
      } else {
         byte[] bytes = new byte[str.length() / 2];
         int i = 0;

         for(int j = 0; i < str.length(); ++j) {
            bytes[j] = (byte)(Byte.parseByte(str.substring(i++, i), 16) << 4 | Byte.parseByte(str.substring(i, i + 1), 16));
            ++i;
         }

         return bytes;
      }
   }

   public void setClusterwideRecoveryEnabled(boolean isClusterwideRecoveryEnabled) {
      this.isClusterwideRecoveryEnabled = isClusterwideRecoveryEnabled ? 1 : 0;
   }

   public int isClusterwideRecoveryEnabled() {
      return this.isClusterwideRecoveryEnabled;
   }

   static {
      rnd = new SecureRandom();
   }
}
