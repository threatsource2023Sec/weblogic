package com.rsa.certj.spi.pki;

import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.pkcs7.RecipientInfo;
import com.rsa.certj.pkcs7.SignerInfo;
import java.util.Date;

/** @deprecated */
public abstract class PKIMessage {
   /** @deprecated */
   public static final int WRAP_NONE = 0;
   /** @deprecated */
   public static final int WRAP_SIGN = 1;
   /** @deprecated */
   public static final int WRAP_ENVELOPE = 2;
   /** @deprecated */
   public static final int WRAP_SIGN_THEN_ENVELOPE = 3;
   /** @deprecated */
   public static final int WRAP_ENVELOPE_THEN_SIGN = 4;
   private int wrapType = 0;
   private SignerInfo sender;
   private RecipientInfo recipient;
   private String encryptionName;
   private int[] encryptionParams;
   private int version = -1;
   private Date messageTime;
   private byte[] transactionID;
   private String[] freeText;
   private Certificate[] extraCerts;
   private CRL[] extraCRLs;
   private Object providerData;

   /** @deprecated */
   public void setWrapInfo(int var1, SignerInfo var2, RecipientInfo var3, String var4, int[] var5) {
      this.wrapType = var1;
      this.sender = var2;
      this.recipient = var3;
      this.encryptionName = var4;
      this.encryptionParams = var5;
   }

   /** @deprecated */
   public int getWrapType() {
      return this.wrapType;
   }

   /** @deprecated */
   public SignerInfo getSender() {
      return this.sender;
   }

   /** @deprecated */
   public RecipientInfo getRecipient() {
      return this.recipient;
   }

   /** @deprecated */
   public String getEncryptionName() {
      return this.encryptionName;
   }

   /** @deprecated */
   public int[] getEncryptionParams() {
      return this.encryptionParams;
   }

   /** @deprecated */
   public int getVersion() {
      return this.version;
   }

   /** @deprecated */
   public void setVersion(int var1) {
      this.version = var1;
   }

   /** @deprecated */
   public Date getMessageTime() {
      return this.messageTime;
   }

   /** @deprecated */
   public void setMessageTime(Date var1) {
      this.messageTime = var1;
   }

   /** @deprecated */
   public Object getProviderData() {
      return this.providerData;
   }

   /** @deprecated */
   public void setProviderData(Object var1) {
      this.providerData = var1;
   }

   /** @deprecated */
   public byte[] getTransactionID() {
      return this.transactionID;
   }

   /** @deprecated */
   public void setTransactionID(byte[] var1) {
      this.transactionID = var1;
   }

   /** @deprecated */
   public String[] getFreeText() {
      return this.freeText;
   }

   /** @deprecated */
   public void setFreeText(String[] var1) {
      this.freeText = var1;
   }

   /** @deprecated */
   public Certificate[] getExtraCerts() {
      return this.extraCerts;
   }

   /** @deprecated */
   public void setExtraCerts(Certificate[] var1) {
      this.extraCerts = var1;
   }

   /** @deprecated */
   public CRL[] getExtraCRLs() {
      return this.extraCRLs;
   }

   /** @deprecated */
   public void setExtraCRLs(CRL[] var1) {
      this.extraCRLs = var1;
   }
}
