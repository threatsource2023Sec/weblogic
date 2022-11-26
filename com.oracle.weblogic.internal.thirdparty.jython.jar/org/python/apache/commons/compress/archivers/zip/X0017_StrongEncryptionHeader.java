package org.python.apache.commons.compress.archivers.zip;

public class X0017_StrongEncryptionHeader extends PKWareExtraHeader {
   private int format;
   private PKWareExtraHeader.EncryptionAlgorithm algId;
   private int bitlen;
   private int flags;
   private long rcount;
   private PKWareExtraHeader.HashAlgorithm hashAlg;
   private int hashSize;
   private byte[] ivData;
   private byte[] erdData;
   private byte[] recipientKeyHash;
   private byte[] keyBlob;
   private byte[] vData;
   private byte[] vCRC32;

   public X0017_StrongEncryptionHeader() {
      super(new ZipShort(23));
   }

   public long getRecordCount() {
      return this.rcount;
   }

   public PKWareExtraHeader.HashAlgorithm getHashAlgorithm() {
      return this.hashAlg;
   }

   public PKWareExtraHeader.EncryptionAlgorithm getEncryptionAlgorithm() {
      return this.algId;
   }

   public void parseCentralDirectoryFormat(byte[] data, int offset, int length) {
      this.format = ZipShort.getValue(data, offset);
      this.algId = PKWareExtraHeader.EncryptionAlgorithm.getAlgorithmByCode(ZipShort.getValue(data, offset + 2));
      this.bitlen = ZipShort.getValue(data, offset + 4);
      this.flags = ZipShort.getValue(data, offset + 6);
      this.rcount = ZipLong.getValue(data, offset + 8);
      if (this.rcount > 0L) {
         this.hashAlg = PKWareExtraHeader.HashAlgorithm.getAlgorithmByCode(ZipShort.getValue(data, offset + 12));
         this.hashSize = ZipShort.getValue(data, offset + 14);

         for(int i = 0; (long)i < this.rcount; ++i) {
            for(int j = 0; j < this.hashSize; ++j) {
            }
         }
      }

   }

   public void parseFileFormat(byte[] data, int offset, int length) {
      int ivSize = ZipShort.getValue(data, offset);
      this.ivData = new byte[ivSize];
      System.arraycopy(data, offset + 4, this.ivData, 0, ivSize);
      this.format = ZipShort.getValue(data, offset + ivSize + 6);
      this.algId = PKWareExtraHeader.EncryptionAlgorithm.getAlgorithmByCode(ZipShort.getValue(data, offset + ivSize + 8));
      this.bitlen = ZipShort.getValue(data, offset + ivSize + 10);
      this.flags = ZipShort.getValue(data, offset + ivSize + 12);
      int erdSize = ZipShort.getValue(data, offset + ivSize + 14);
      this.erdData = new byte[erdSize];
      System.arraycopy(data, offset + ivSize + 16, this.erdData, 0, erdSize);
      this.rcount = ZipLong.getValue(data, offset + ivSize + 16 + erdSize);
      System.out.println("rcount: " + this.rcount);
      int vSize;
      if (this.rcount == 0L) {
         vSize = ZipShort.getValue(data, offset + ivSize + 20 + erdSize);
         this.vData = new byte[vSize - 4];
         this.vCRC32 = new byte[4];
         System.arraycopy(data, offset + ivSize + 22 + erdSize, this.vData, 0, vSize - 4);
         System.arraycopy(data, offset + ivSize + 22 + erdSize + vSize - 4, this.vCRC32, 0, 4);
      } else {
         this.hashAlg = PKWareExtraHeader.HashAlgorithm.getAlgorithmByCode(ZipShort.getValue(data, offset + ivSize + 20 + erdSize));
         this.hashSize = ZipShort.getValue(data, offset + ivSize + 22 + erdSize);
         vSize = ZipShort.getValue(data, offset + ivSize + 24 + erdSize);
         this.recipientKeyHash = new byte[this.hashSize];
         this.keyBlob = new byte[vSize - this.hashSize];
         System.arraycopy(data, offset + ivSize + 24 + erdSize, this.recipientKeyHash, 0, this.hashSize);
         System.arraycopy(data, offset + ivSize + 24 + erdSize + this.hashSize, this.keyBlob, 0, vSize - this.hashSize);
         int vSize = ZipShort.getValue(data, offset + ivSize + 26 + erdSize + vSize);
         this.vData = new byte[vSize - 4];
         this.vCRC32 = new byte[4];
         System.arraycopy(data, offset + ivSize + 22 + erdSize + vSize, this.vData, 0, vSize - 4);
         System.arraycopy(data, offset + ivSize + 22 + erdSize + vSize + vSize - 4, this.vCRC32, 0, 4);
      }

   }

   public void parseFromLocalFileData(byte[] data, int offset, int length) {
      super.parseFromLocalFileData(data, offset, length);
      this.parseFileFormat(data, offset, length);
   }

   public void parseFromCentralDirectoryData(byte[] data, int offset, int length) {
      super.parseFromCentralDirectoryData(data, offset, length);
      this.parseCentralDirectoryFormat(data, offset, length);
   }
}
