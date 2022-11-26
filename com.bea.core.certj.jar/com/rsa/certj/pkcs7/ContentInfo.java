package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.spi.path.CertPathCtx;
import java.io.Serializable;

/** @deprecated */
public abstract class ContentInfo implements Serializable, Cloneable {
   /** @deprecated */
   public static final int DATA = 1;
   /** @deprecated */
   public static final int SIGNED_DATA = 2;
   /** @deprecated */
   public static final int ENVELOPED_DATA = 3;
   /** @deprecated */
   public static final int DIGESTED_DATA = 5;
   /** @deprecated */
   public static final int ENCRYPTED_DATA = 6;
   /** @deprecated */
   public static final int NO_CONTENT = 0;
   /** @deprecated */
   public static final int CMS_DATA = 11;
   /** @deprecated */
   public static final int CMS_SIGNED_DATA = 22;
   /** @deprecated */
   public static final int PKCS7_VERSION_NUMBER_0 = 0;
   /** @deprecated */
   public static final int PKCS7_VERSION_NUMBER_1 = 1;
   /** @deprecated */
   public static final int CMS_VERSION_NUMBER_3 = 3;
   static final byte[] P7OID = new byte[]{42, -122, 72, -122, -9, 13, 1, 7, 0};
   static final int P7OID_LEN = 9;
   /** @deprecated */
   protected static final int ENCODE = 0;
   /** @deprecated */
   protected static final int DECODE = 1;
   /** @deprecated */
   protected int contentType;
   /** @deprecated */
   protected int maxBufferSize;
   /** @deprecated */
   protected ContentInfo content;
   /** @deprecated */
   protected CertPathCtx theCertPathCtx;
   /** @deprecated */
   protected CertJ theCertJ;
   /** @deprecated */
   protected ASN1Container[] contentASN1Def;
   /** @deprecated */
   protected ASN1Template contentASN1Template;
   private ASN1Container[] contentInfoASN1Def;
   /** @deprecated */
   protected ASN1Template contentInfoASN1Template;
   /** @deprecated */
   protected int derLen = 0;
   /** @deprecated */
   protected int numberOfBytesRead;
   /** @deprecated */
   protected String theDevice;
   /** @deprecated */
   protected String[] theDeviceList;
   /** @deprecated */
   protected byte[] internalBuffer;
   /** @deprecated */
   protected int internalOffset;
   /** @deprecated */
   protected int internalBufferLen;
   /** @deprecated */
   protected byte[] contentInfoEncoding;
   /** @deprecated */
   protected int incompleteContainer;
   /** @deprecated */
   protected byte[] contentEncoding;
   /** @deprecated */
   protected int flag;
   /** @deprecated */
   protected byte[] oid;
   private static final String UNKNOWN_OID_FOUND = "Cannot build PKCS #7 content object: unknown OID.";

   /** @deprecated */
   protected ContentInfo() {
   }

   /** @deprecated */
   public static ContentInfo getInstance(int var0, CertJ var1, CertPathCtx var2) throws PKCS7Exception {
      switch (var0) {
         case 1:
            return new Data();
         case 2:
            return new SignedData(var1, var2);
         case 3:
            return new EnvelopedData(var1, var2);
         case 4:
         default:
            throw new PKCS7Exception("Cannot build PKCS #7 content object: unknown type.");
         case 5:
            return new DigestedData(var1, var2);
         case 6:
            return new EncryptedData(var1, var2);
      }
   }

   /** @deprecated */
   public static ContentInfo getInstance(int var0, String var1, CertJ var2, CertPathCtx var3) throws PKCS7Exception {
      switch (var0) {
         case 11:
            return new CMSData(var1);
         case 22:
            return new CMSSignedData(var1, var2, var3);
         default:
            throw new PKCS7Exception("Cannot build CMS content object: unknown type.");
      }
   }

   /** @deprecated */
   public static ContentInfo getInstance(byte[] var0, int var1, int var2, CertJ var3, CertPathCtx var4) throws PKCS7Exception {
      if (var0 != null && var2 > 0) {
         if (var1 >= 0 && var1 + var2 <= var0.length) {
            if (var2 != 9) {
               throw new PKCS7Exception("Cannot build PKCS #7 content object: unknown OID.");
            } else {
               int var5;
               for(var5 = 0; var5 < 7; ++var5) {
                  if (var0[var5 + var1] != P7OID[var5]) {
                     throw new PKCS7Exception("Cannot build PKCS #7 content object: unknown OID.");
                  }
               }

               var5 = var0[var2 + var1 - 1] & 255;
               switch (var5) {
                  case 1:
                     return new Data();
                  case 2:
                     return new SignedData(var3, var4);
                  case 3:
                     return new EnvelopedData(var3, var4);
                  case 4:
                  default:
                     throw new PKCS7Exception("Cannot build PKCS #7 content object: unknown OID.");
                  case 5:
                     return new DigestedData(var3, var4);
                  case 6:
                     return new EncryptedData(var3, var4);
               }
            }
         } else {
            throw new PKCS7Exception("Invalid content type.");
         }
      } else {
         throw new PKCS7Exception("Cannot build PKCS #7 content object: OID is null.");
      }
   }

   /** @deprecated */
   public static int getMessageType(byte[] var0, int var1, int var2) throws PKCS7Exception {
      return getMessageType(var0, var1, var2, 0);
   }

   /** @deprecated */
   public static int getMessageType(byte[] var0, int var1, int var2, int var3) throws PKCS7Exception {
      if (var0 != null && var2 > 0) {
         if (var1 >= 0 && var1 + var2 <= var0.length) {
            try {
               SequenceContainer var4 = new SequenceContainer(var3);
               EndContainer var5 = new EndContainer();
               OIDContainer var6 = new OIDContainer(16777216);
               EncodedContainer var7 = new EncodedContainer(10616576);
               ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
               ASN1Template var9 = new ASN1Template(var8);
               var9.berDecodeInit();
               var9.berDecodeUpdate(var0, var1, var2);
               if (!var8[1].isComplete()) {
                  return -1;
               } else if (var8[1].dataLen != 9) {
                  throw new PKCS7Exception("Unknown OID.");
               } else {
                  return var8[1].data[var8[1].dataOffset + var8[1].dataLen - 1];
               }
            } catch (ASN_Exception var10) {
               throw new PKCS7Exception("Cannot read message: ", var10);
            }
         } else {
            throw new PKCS7Exception("Invalid message data");
         }
      } else {
         throw new PKCS7Exception("Cannot read the message: data is null.");
      }
   }

   /** @deprecated */
   public void setDevice(String var1) {
      this.theDevice = var1;
   }

   /** @deprecated */
   public String getDevice() {
      if (this.theDevice != null) {
         return this.theDevice;
      } else {
         return this.theCertJ != null ? this.theCertJ.getDevice() : null;
      }
   }

   /** @deprecated */
   protected String getDeviceOrJava() {
      String var1 = this.getDevice();
      return var1 == null ? "Java" : var1;
   }

   /** @deprecated */
   public String[] getDeviceList() {
      String[] var1 = null;
      if (this.theDeviceList != null) {
         var1 = new String[this.theDeviceList.length];
         System.arraycopy(this.theDeviceList, 0, var1, 0, this.theDeviceList.length);
      } else if (this.theDevice != null) {
         var1 = new String[]{this.theDevice};
      } else if (this.theCertJ != null) {
         var1 = new String[]{this.theCertJ.getDevice()};
      }

      return var1;
   }

   /** @deprecated */
   public int getContentType() {
      return this.contentType;
   }

   /** @deprecated */
   public void setCertJ(CertJ var1) {
      this.theCertJ = var1;
   }

   /** @deprecated */
   public void setCertPath(CertPathCtx var1) {
      this.theCertPathCtx = var1;
   }

   /** @deprecated */
   public ContentInfo getContent() {
      return this.content;
   }

   /** @deprecated */
   public int getContentInfoDERLen() throws PKCS7Exception {
      return this.getContentInfoDERLen(0);
   }

   /** @deprecated */
   public int estimateContentInfoDERLen() throws PKCS7Exception {
      return this.getContentInfoDERLen();
   }

   /** @deprecated */
   protected int estimateContentDERLen() throws PKCS7Exception {
      return this.getContentDERLen();
   }

   /** @deprecated */
   public int getContentInfoDERLen(int var1) throws PKCS7Exception {
      this.contentInfoASN1Def = this.getASN1Containers(true, var1);
      this.contentInfoASN1Template = new ASN1Template(this.contentInfoASN1Def);

      try {
         this.derLen = this.contentInfoASN1Template.derEncodeInit();
      } catch (ASN_Exception var3) {
         throw new PKCS7Exception(var3);
      }

      return this.derLen;
   }

   /** @deprecated */
   protected abstract int getContentDERLen() throws PKCS7Exception;

   /** @deprecated */
   public int writeMessage(byte[] var1, int var2) throws PKCS7Exception {
      return this.writeMessage(var1, var2, 0);
   }

   /** @deprecated */
   public int writeMessage(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var2 >= 0 && var1.length != 0) {
         int var4 = var2;
         if (this.derLen == 0) {
            this.getContentInfoDERLen(var3);
         }

         try {
            var2 += this.contentInfoASN1Template.derEncode(var1, var2);
            if (this.allowChangeContentTypeOIDLastByte()) {
               var1[var2 - 1] = (byte)this.contentType;
            }
         } catch (ASN_Exception var6) {
            throw new PKCS7Exception("Cannot encode contentInfo: ", var6);
         }

         if (this.contentInfoEncoding != null) {
            System.arraycopy(this.contentInfoEncoding, 0, var1, var2, this.contentInfoEncoding.length);
            var2 += this.contentInfoEncoding.length;
         } else {
            var2 += this.writeContent(var1, var2);
         }

         this.derLen = 0;
         return var2 - var4;
      } else {
         throw new PKCS7Exception("Cannot write message: output array is null.");
      }
   }

   /** @deprecated */
   protected abstract int writeContent(byte[] var1, int var2) throws PKCS7Exception;

   /** @deprecated */
   public boolean readInit(byte[] var1, int var2, int var3) throws PKCS7Exception {
      return this.readInit(var1, var2, var3, 0);
   }

   /** @deprecated */
   public boolean readInit(byte[] var1, int var2, int var3, int var4) throws PKCS7Exception {
      return this.readInit(var1, var2, var3, var4, 0);
   }

   /** @deprecated */
   public boolean readInit(byte[] var1, int var2, int var3, int var4, int var5) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            try {
               this.maxBufferSize = var5;
               this.contentInfoASN1Def = this.getASN1Containers(false, var4);
               this.contentInfoASN1Template = new ASN1Template(this.contentInfoASN1Def);
               this.contentInfoASN1Template.berDecodeInit();
               this.numberOfBytesRead = this.contentInfoASN1Template.berDecodeUpdate(var1, var2, var3);
            } catch (ASN_Exception var7) {
               throw new PKCS7Exception("Cannot read message: ", var7);
            }

            if (!this.contentInfoASN1Def[1].isComplete()) {
               return false;
            } else {
               this.contentInfoEncoding = new byte[this.contentInfoASN1Def[2].dataLen];
               if (this.contentInfoEncoding.length > 0) {
                  System.arraycopy(this.contentInfoASN1Def[2].data, this.contentInfoASN1Def[2].dataOffset, this.contentInfoEncoding, 0, this.contentInfoASN1Def[2].dataLen);
                  this.contentReadInit(this.contentInfoASN1Def[2].data, this.contentInfoASN1Def[2].dataOffset, this.contentInfoASN1Def[2].dataLen);
               }

               return true;
            }
         } else {
            throw new PKCS7Exception("Invalid message data");
         }
      } else {
         throw new PKCS7Exception("Cannot read message: data is null.");
      }
   }

   /** @deprecated */
   protected abstract boolean contentReadInit(byte[] var1, int var2, int var3) throws PKCS7Exception;

   /** @deprecated */
   public int readUpdate(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            if (this.contentInfoASN1Template == null) {
               throw new PKCS7Exception("Need to call readInit() before readUpdate().");
            } else {
               try {
                  this.numberOfBytesRead += this.contentInfoASN1Template.berDecodeUpdate(var1, var2, var3);
               } catch (ASN_Exception var5) {
                  throw new PKCS7Exception("Cannot readUpdate message: ", var5);
               }

               if (this.contentInfoEncoding != null) {
                  byte[] var4 = new byte[this.contentInfoEncoding.length];
                  System.arraycopy(this.contentInfoEncoding, 0, var4, 0, this.contentInfoEncoding.length);
                  this.contentInfoEncoding = new byte[var4.length + var3];
                  System.arraycopy(var4, 0, this.contentInfoEncoding, 0, var4.length);
                  System.arraycopy(var1, var2, this.contentInfoEncoding, var4.length, var3);
               }

               return this.contentReadUpdate(var1, var2, var3);
            }
         } else {
            throw new PKCS7Exception("Invalid message data");
         }
      } else {
         throw new PKCS7Exception("Cannot read message: data is null.");
      }
   }

   /** @deprecated */
   protected abstract int contentReadUpdate(byte[] var1, int var2, int var3) throws PKCS7Exception;

   /** @deprecated */
   public boolean readFinal() throws PKCS7Exception {
      if (this.contentInfoASN1Template == null) {
         throw new PKCS7Exception("Need to call readInit() before readFinal().");
      } else {
         try {
            this.contentInfoASN1Template.berDecodeFinal();
         } catch (ASN_Exception var2) {
            throw new PKCS7Exception("Cannot call readFinal: ", var2);
         }

         if (!this.contentInfoASN1Template.isComplete()) {
            throw new PKCS7Exception("Cannot call readFinal, more message expected.");
         } else {
            return true;
         }
      }
   }

   /** @deprecated */
   public int bytesRead() {
      return this.numberOfBytesRead;
   }

   /** @deprecated */
   public boolean endOfMessage() {
      return this.contentASN1Def[0].isComplete();
   }

   /** @deprecated */
   public int getUnprocessedDataLen() {
      return this.internalBuffer == null ? 0 : this.internalBuffer.length;
   }

   /** @deprecated */
   protected int copyNewData(byte[] var1, int var2, int var3, int var4) {
      if (var1 == null) {
         return 0;
      } else {
         if (this.internalBuffer == null) {
            if (var4 != 0) {
               this.internalBuffer = new byte[var4];
            } else {
               this.internalBuffer = new byte[var3];
            }
         }

         if (this.internalBuffer.length - this.internalOffset < var3) {
            byte[] var5 = new byte[this.internalBuffer.length];
            System.arraycopy(this.internalBuffer, 0, var5, 0, this.internalBuffer.length);
            this.internalBuffer = new byte[var3 + this.internalBuffer.length];
            System.arraycopy(var5, 0, this.internalBuffer, 0, var5.length);
            this.internalOffset = var5.length;
         }

         System.arraycopy(var1, var2, this.internalBuffer, this.internalOffset, var3);
         this.internalOffset += var3;
         return var3;
      }
   }

   /** @deprecated */
   protected byte[] copyToOutput(ASN1Container var1) {
      byte[] var2;
      if (this.internalBuffer == null) {
         var2 = new byte[var1.dataLen];
         System.arraycopy(var1.data, var1.dataOffset, var2, 0, var1.dataLen);
      } else if (this.maxBufferSize != 0 && this.internalBuffer.length >= this.maxBufferSize) {
         System.arraycopy(var1.data, var1.dataOffset, this.internalBuffer, this.internalOffset, var1.dataLen);
         var2 = new byte[this.internalBuffer.length];
         System.arraycopy(this.internalBuffer, 0, var2, 0, this.internalBuffer.length);
      } else {
         var2 = new byte[var1.dataLen + this.internalBuffer.length];
         System.arraycopy(this.internalBuffer, 0, var2, 0, this.internalBuffer.length);
         System.arraycopy(var1.data, var1.dataOffset, var2, this.internalBuffer.length, var1.dataLen);
      }

      this.clearInternalBuffer(this.internalBuffer);
      this.internalBuffer = null;
      this.internalOffset = 0;
      this.incompleteContainer = 0;
      return var2;
   }

   /** @deprecated */
   protected void clearInternalBuffer(byte[] var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2] = 0;
         }
      }

   }

   /** @deprecated */
   protected ASN1Container[] getASN1Containers(boolean var1, int var2) throws PKCS7Exception {
      SequenceContainer var3 = new SequenceContainer(var2, true, 0);
      EndContainer var4 = new EndContainer();
      int var5 = 0;
      if (var1) {
         if (this.contentInfoEncoding != null) {
            var5 = this.contentInfoEncoding.length;
         } else {
            var5 = this.getContentDERLen();
         }
      }

      try {
         OIDContainer var6 = this.buildOIDContainer();
         EncodedContainer var7 = new EncodedContainer(10616576, true, 0, this.maxBufferSize, (byte[])null, 0, var5);
         ASN1Container[] var8 = new ASN1Container[]{var3, var6, var7, var4};
         return var8;
      } catch (ASN_Exception var9) {
         throw new PKCS7Exception("ContentInfo.getASN1Containers: ", var9);
      }
   }

   protected OIDContainer buildOIDContainer() throws ASN_Exception {
      return new OIDContainer(16777216, true, 0, P7OID, 0, 9);
   }

   /** @deprecated */
   protected boolean allowChangeContentTypeOIDLastByte() {
      boolean var1 = true;
      if (this.oid != null) {
         var1 = false;
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ContentInfo var1 = (ContentInfo)super.clone();
      var1.theDevice = this.theDevice;
      var1.theDeviceList = this.getDeviceList();
      var1.contentType = this.contentType;
      if (this.content != null) {
         var1.content = (ContentInfo)this.content.clone();
      }

      var1.theCertPathCtx = this.theCertPathCtx;
      var1.theCertJ = this.theCertJ;

      try {
         if (this.contentInfoASN1Template != null) {
            var1.getContentInfoDERLen();
         }

         return var1;
      } catch (PKCS7Exception var3) {
         throw new CloneNotSupportedException("Could not copy ASN1Template.");
      }
   }

   /** @deprecated */
   public void clearInternalContentBuffer() {
      this.contentInfoEncoding = null;
   }

   /** @deprecated */
   public void clearSensitiveData() {
      if (this.content != null) {
         this.content.clearSensitiveData();
      }

      this.contentType = 0;
      this.content = null;
      this.theDevice = null;
      this.theDeviceList = null;
      this.contentInfoASN1Template = null;
      this.contentASN1Template = null;
      this.derLen = 0;
      this.clearInternalBuffer(this.internalBuffer);
      this.internalBuffer = null;
      this.internalOffset = 0;
      this.incompleteContainer = 0;
      this.theCertPathCtx = null;
      this.theCertJ = null;
   }

   /** @deprecated */
   protected void finalize() {
      this.clearSensitiveData();
   }
}
