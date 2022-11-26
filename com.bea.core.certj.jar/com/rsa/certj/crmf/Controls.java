package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;
import java.util.Vector;

/** @deprecated */
public class Controls implements Serializable, Cloneable {
   private Vector theControls = new Vector();
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;
   private CertPathCtx theCertPathCtx;
   private CertJ theCertJ;
   private JSAFE_PublicKey pubKey;
   private JSAFE_PrivateKey privKey;

   /** @deprecated */
   public void decodeControls(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Controls encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               Control var9 = Control.getInstance(var8.data, var8.dataOffset, 0, this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);
               this.theControls.addElement(var9);
            }

         } catch (ASN_Exception var10) {
            throw new CRMFException("Could not read the BER of the Controls.");
         }
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("Controls Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding.");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      return this.encodeInit(var1);
   }

   private int encodeInit(int var1) throws CRMFException {
      this.special = var1;

      try {
         OfContainer var2 = new OfContainer(var1, true, 0, 12288, new EncodedContainer(12288));
         int var3 = this.theControls.size();
         if (var3 == 0) {
            return 2;
         } else {
            for(int var4 = 0; var4 < var3; ++var4) {
               Control var5 = (Control)this.theControls.elementAt(var4);
               int var6 = var5.getDERLen(0);
               byte[] var7 = new byte[var6];
               var6 = var5.getDEREncoding(var7, 0, 0);
               EncodedContainer var8 = new EncodedContainer(12288, true, 0, var7, 0, var6);
               var2.addContainer(var8);
            }

            ASN1Container[] var10 = new ASN1Container[]{var2};
            this.asn1Template = new ASN1Template(var10);
            return this.asn1Template.derEncodeInit();
         }
      } catch (ASN_Exception var9) {
         throw new CRMFException("Cannot encode Controls. ", var9);
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var1.length >= 2) {
         if ((this.asn1Template == null || var3 != this.special) && this.encodeInit(var3) == 0) {
            throw new CRMFException("Cannot compute the DER of the Controls");
         } else if (this.theControls.isEmpty()) {
            var1[var2] = -96;
            var1[var2 + 1] = 0;
            return 2;
         } else {
            try {
               return this.asn1Template.derEncode(var1, var2);
            } catch (ASN_Exception var5) {
               throw new CRMFException("Cannot encode Controls. ", var5);
            }
         }
      } else {
         throw new CRMFException("Specified Controls array is null or too short.");
      }
   }

   /** @deprecated */
   public int addControl(Control var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified Control is null.");
      } else {
         this.theControls.addElement(var1);
         return this.theControls.indexOf(var1);
      }
   }

   /** @deprecated */
   public int getControlCount() {
      return this.theControls.size();
   }

   /** @deprecated */
   public Control getControlByIndex(int var1) throws CRMFException {
      if (var1 >= this.theControls.size()) {
         throw new CRMFException("Specified Controls index is invalid.");
      } else {
         try {
            return (Control)((Control)this.theControls.elementAt(var1)).clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Cannot get specified Control. ", var3);
         }
      }
   }

   /** @deprecated */
   public Control getControlByType(int var1) {
      if (this.theControls.isEmpty()) {
         return null;
      } else {
         try {
            int var2 = this.getControlCount();

            for(int var3 = 0; var3 < var2; ++var3) {
               Control var4 = this.getControlByIndex(var3);
               if (var4.getControlType() == var1) {
                  return var4;
               }
            }

            return null;
         } catch (CRMFException var5) {
            return null;
         }
      }
   }

   /** @deprecated */
   public Control getControlByOID(byte[] var1) {
      if (this.theControls.isEmpty()) {
         return null;
      } else {
         try {
            int var2 = this.getControlCount();

            for(int var3 = 0; var3 < var2; ++var3) {
               Control var4 = this.getControlByIndex(var3);
               if (var4.compareOID(var1)) {
                  return var4;
               }
            }

            return null;
         } catch (CRMFException var5) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void setEnvironment(CertJ var1, CertPathCtx var2, JSAFE_PublicKey var3, JSAFE_PrivateKey var4) {
      this.theCertJ = var1;
      this.theCertPathCtx = var2;
      if (var3 != null) {
         this.pubKey = var3;
      }

      if (var4 != null) {
         this.privKey = var4;
      }

   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      Controls var1 = new Controls();

      try {
         var1.setEnvironment(this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);
         int var2 = this.theControls.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            Control var4 = (Control)this.getControlByIndex(var3).clone();
            var1.addControl(var4);
         }

         if (this.asn1Template != null) {
            var1.getDERLen(this.special);
         }

         return var1;
      } catch (CRMFException var5) {
         throw new CloneNotSupportedException(var5.getMessage());
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Controls) {
         Controls var2 = (Controls)var1;
         int var3 = this.theControls.size();
         int var4 = var2.theControls.size();
         if (var3 != var4) {
            return false;
         } else {
            for(int var5 = 0; var5 < var3; ++var5) {
               if (this.theControls.elementAt(var5) != null) {
                  if (!((Control)this.theControls.elementAt(var5)).equals(var2.theControls.elementAt(var5))) {
                     return false;
                  }
               } else if (var2.theControls.elementAt(var5) != null) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + CertJInternalHelper.hashCodeValue(this.theControls);
   }
}
