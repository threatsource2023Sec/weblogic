package org.python.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.zip.ZipException;

public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
   private static final ZipShort HEADER_ID = new ZipShort(30837);
   private static final ZipShort ZERO = new ZipShort(0);
   private static final BigInteger ONE_THOUSAND = BigInteger.valueOf(1000L);
   private static final long serialVersionUID = 1L;
   private int version = 1;
   private BigInteger uid;
   private BigInteger gid;

   public X7875_NewUnix() {
      this.reset();
   }

   public ZipShort getHeaderId() {
      return HEADER_ID;
   }

   public long getUID() {
      return ZipUtil.bigToLong(this.uid);
   }

   public long getGID() {
      return ZipUtil.bigToLong(this.gid);
   }

   public void setUID(long l) {
      this.uid = ZipUtil.longToBig(l);
   }

   public void setGID(long l) {
      this.gid = ZipUtil.longToBig(l);
   }

   public ZipShort getLocalFileDataLength() {
      int uidSize = trimLeadingZeroesForceMinLength(this.uid.toByteArray()).length;
      int gidSize = trimLeadingZeroesForceMinLength(this.gid.toByteArray()).length;
      return new ZipShort(3 + uidSize + gidSize);
   }

   public ZipShort getCentralDirectoryLength() {
      return ZERO;
   }

   public byte[] getLocalFileDataData() {
      byte[] uidBytes = this.uid.toByteArray();
      byte[] gidBytes = this.gid.toByteArray();
      uidBytes = trimLeadingZeroesForceMinLength(uidBytes);
      gidBytes = trimLeadingZeroesForceMinLength(gidBytes);
      byte[] data = new byte[3 + uidBytes.length + gidBytes.length];
      ZipUtil.reverse(uidBytes);
      ZipUtil.reverse(gidBytes);
      int pos = 0;
      data[pos++] = ZipUtil.unsignedIntToSignedByte(this.version);
      data[pos++] = ZipUtil.unsignedIntToSignedByte(uidBytes.length);
      System.arraycopy(uidBytes, 0, data, pos, uidBytes.length);
      pos += uidBytes.length;
      data[pos++] = ZipUtil.unsignedIntToSignedByte(gidBytes.length);
      System.arraycopy(gidBytes, 0, data, pos, gidBytes.length);
      return data;
   }

   public byte[] getCentralDirectoryData() {
      return new byte[0];
   }

   public void parseFromLocalFileData(byte[] data, int offset, int length) throws ZipException {
      this.reset();
      this.version = ZipUtil.signedByteToUnsignedInt(data[offset++]);
      int uidSize = ZipUtil.signedByteToUnsignedInt(data[offset++]);
      byte[] uidBytes = new byte[uidSize];
      System.arraycopy(data, offset, uidBytes, 0, uidSize);
      offset += uidSize;
      this.uid = new BigInteger(1, ZipUtil.reverse(uidBytes));
      int gidSize = ZipUtil.signedByteToUnsignedInt(data[offset++]);
      byte[] gidBytes = new byte[gidSize];
      System.arraycopy(data, offset, gidBytes, 0, gidSize);
      this.gid = new BigInteger(1, ZipUtil.reverse(gidBytes));
   }

   public void parseFromCentralDirectoryData(byte[] buffer, int offset, int length) throws ZipException {
   }

   private void reset() {
      this.uid = ONE_THOUSAND;
      this.gid = ONE_THOUSAND;
   }

   public String toString() {
      return "0x7875 Zip Extra Field: UID=" + this.uid + " GID=" + this.gid;
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public boolean equals(Object o) {
      if (!(o instanceof X7875_NewUnix)) {
         return false;
      } else {
         X7875_NewUnix xf = (X7875_NewUnix)o;
         return this.version == xf.version && this.uid.equals(xf.uid) && this.gid.equals(xf.gid);
      }
   }

   public int hashCode() {
      int hc = -1234567 * this.version;
      hc ^= Integer.rotateLeft(this.uid.hashCode(), 16);
      hc ^= this.gid.hashCode();
      return hc;
   }

   static byte[] trimLeadingZeroesForceMinLength(byte[] array) {
      if (array == null) {
         return array;
      } else {
         int pos = 0;
         byte[] var2 = array;
         int var3 = array.length;

         int startPos;
         for(startPos = 0; startPos < var3; ++startPos) {
            byte b = var2[startPos];
            if (b != 0) {
               break;
            }

            ++pos;
         }

         int MIN_LENGTH = true;
         byte[] trimmedArray = new byte[Math.max(1, array.length - pos)];
         startPos = trimmedArray.length - (array.length - pos);
         System.arraycopy(array, pos, trimmedArray, startPos, trimmedArray.length - startPos);
         return trimmedArray;
      }
   }
}
