package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BooleanContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.CertificateException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public abstract class X509V3Extension implements Serializable, Cloneable {
   /** @deprecated */
   protected String extensionTypeString;
   /** @deprecated */
   protected static final int OID_BYTE_ONE = 85;
   /** @deprecated */
   protected static final int OID_BYTE_TWO = 29;
   /** @deprecated */
   public static final int SUBJECT_KEY_ID = 14;
   /** @deprecated */
   public static final int AUTHORITY_KEY_ID = 35;
   /** @deprecated */
   public static final int KEY_USAGE = 15;
   /** @deprecated */
   public static final int PRIVATE_KEY_USAGE_PERIOD = 16;
   /** @deprecated */
   public static final int SUBJECT_ALT_NAME = 17;
   /** @deprecated */
   public static final int ISSUER_ALT_NAME = 18;
   /** @deprecated */
   public static final int BASIC_CONSTRAINTS = 19;
   /** @deprecated */
   public static final int SUBJECT_DIRECTORY_ATTRIBUTES = 9;
   /** @deprecated */
   public static final int NAME_CONSTRAINTS = 30;
   /** @deprecated */
   public static final int CERT_POLICIES = 32;
   /** @deprecated */
   public static final int POLICY_MAPPINGS = 33;
   /** @deprecated */
   public static final int POLICY_CONSTRAINTS = 36;
   /** @deprecated */
   public static final int EXTENDED_KEY_USAGE = 37;
   /** @deprecated */
   public static final int CRL_NUMBER = 20;
   /** @deprecated */
   public static final int REASON_CODE = 21;
   /** @deprecated */
   public static final int HOLD_INSTRUCTION_CODE = 23;
   /** @deprecated */
   public static final int INVALIDITY_DATE = 24;
   /** @deprecated */
   public static final int DELTA_CRL_INDICATOR = 27;
   /** @deprecated */
   public static final int ISSUING_DISTRIBUTION_POINT = 28;
   /** @deprecated */
   public static final int CERTIFICATE_ISSUER = 29;
   /** @deprecated */
   public static final int CRL_DISTRIBUTION_POINTS = 31;
   /** @deprecated */
   public static final int FRESHEST_CRL = 46;
   /** @deprecated */
   public static final int INHIBIT_ANY_POLICY = 54;
   private static final int SPECIAL_EXT = 100;
   /** @deprecated */
   public static final int AUTHORITY_INFO_ACCESS = 100;
   /** @deprecated */
   public static final byte[] AUTHORITY_INFO_OID = new byte[]{43, 6, 1, 5, 5, 7, 1, 1};
   /** @deprecated */
   public static final byte[] ANY_POLICY_OID = new byte[]{85, 29, 32, 0};
   /** @deprecated */
   public static final int NETSCAPE_CERT_TYPE = 101;
   /** @deprecated */
   public static final byte[] NETSCAPE_CERT_TYPE_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 1};
   /** @deprecated */
   public static final int NETSCAPE_BASE_URL = 102;
   /** @deprecated */
   public static final byte[] NETSCAPE_BASE_URL_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 2};
   /** @deprecated */
   public static final int NETSCAPE_REVOCATION_URL = 103;
   /** @deprecated */
   public static final byte[] NETSCAPE_REVOCATION_URL_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 3};
   /** @deprecated */
   public static final int NETSCAPE_CA_REVOCATION_URL = 104;
   /** @deprecated */
   public static final byte[] NETSCAPE_CA_REVOCATION_URL_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 4};
   /** @deprecated */
   public static final int NETSCAPE_CERT_RENEWAL_URL = 105;
   /** @deprecated */
   public static final byte[] NETSCAPE_CERT_RENEWAL_URL_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 7};
   /** @deprecated */
   public static final int NETSCAPE_CA_POLICY_URL = 106;
   /** @deprecated */
   public static final byte[] NETSCAPE_CA_POLICY_URL_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 8};
   /** @deprecated */
   public static final int NETSCAPE_SSL_SERVER_NAME = 107;
   /** @deprecated */
   public static final byte[] NETSCAPE_SSL_SERVER_NAME_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 12};
   /** @deprecated */
   public static final int NETSCAPE_COMMENT = 108;
   /** @deprecated */
   public static final byte[] NETSCAPE_COMMENT_OID = new byte[]{96, -122, 72, 1, -122, -8, 66, 1, 13};
   /** @deprecated */
   public static final int VERISIGN_CZAG = 109;
   /** @deprecated */
   public static final byte[] VERISIGN_CZAG_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 3};
   /** @deprecated */
   public static final int VERISIGN_FIDELITY_ID = 110;
   /** @deprecated */
   public static final byte[] VERISIGN_FIDELITY_ID_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 5};
   /** @deprecated */
   public static final int VERISIGN_NETSCAPE_INBOX_V1 = 111;
   /** @deprecated */
   public static final byte[] VERISIGN_NETSCAPE_INBOX_V1_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 6};
   /** @deprecated */
   public static final int VERISIGN_NETSCAPE_INBOX_V2 = 112;
   /** @deprecated */
   public static final byte[] VERISIGN_NETSCAPE_INBOX_V2_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 10};
   /** @deprecated */
   public static final int VERISIGN_JURISDICTION_HASH = 113;
   /** @deprecated */
   public static final byte[] VERISIGN_JURISDICTION_HASH_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 11};
   /** @deprecated */
   public static final int VERISIGN_TOKEN_TYPE = 114;
   /** @deprecated */
   public static final byte[] VERISIGN_TOKEN_TYPE_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 8};
   /** @deprecated */
   public static final int VERISIGN_SERIAL_NUMBER = 115;
   /** @deprecated */
   public static final byte[] VERISIGN_SERIAL_NUMBER_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 7};
   /** @deprecated */
   public static final int VERISIGN_NON_VERIFIED = 116;
   /** @deprecated */
   public static final byte[] VERISIGN_NON_VERIFIED_OID = new byte[]{96, -122, 72, 1, -122, -8, 69, 1, 6, 4};
   /** @deprecated */
   public static final int OCSP_NOCHECK = 117;
   /** @deprecated */
   public static final byte[] OCSP_NOCHECK_OID = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 5};
   /** @deprecated */
   public static final int ARCHIVE_CUTOFF = 118;
   /** @deprecated */
   public static final byte[] ARCHIVE_CUTOFF_OID = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 6};
   /** @deprecated */
   public static final int CRL_REFERENCE = 119;
   /** @deprecated */
   public static final byte[] CRL_REFERENCE_OID = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 3};
   /** @deprecated */
   public static final int OCSP_NONCE = 120;
   /** @deprecated */
   public static final byte[] OCSP_NONCE_OID = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 2};
   /** @deprecated */
   public static final int OCSP_ACCEPTABLE_RESPONSES = 121;
   /** @deprecated */
   public static final byte[] OCSP_ACCEPTABLE_RESPONSES_OID = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 4};
   /** @deprecated */
   public static final int OCSP_SERVICE_LOCATOR = 122;
   /** @deprecated */
   public static final byte[] OCSP_SERVICE_LOCATOR_OID = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 7};
   /** @deprecated */
   public static final int QC_STATEMENTS = 123;
   /** @deprecated */
   public static final byte[] QC_STATEMENTS_OID = new byte[]{43, 6, 1, 5, 5, 7, 1, 3};
   /** @deprecated */
   public static final int BIO_INFO = 124;
   /** @deprecated */
   public static final byte[] BIO_INFO_OID = new byte[]{43, 6, 1, 5, 5, 7, 1, 2};
   /** @deprecated */
   public static final int SUBJECT_INFO_ACCESS = 125;
   /** @deprecated */
   public static final byte[] SUBJECT_INFO_OID = new byte[]{43, 6, 1, 5, 5, 7, 1, 11};
   /** @deprecated */
   public static final int NON_STANDARD_EXTENSION = -1;
   private static final int[] STANDARD_EXTENSION_LIST = new int[]{14, 35, 15, 16, 17, 18, 19, 9, 30, 32, 33, 36, 37, 20, 21, 23, 24, 27, 28, 29, 31, 46, 54, -1};
   private static final byte[][] SPECIAL_EXTENSION_LIST;
   private static Vector extendedExtensionOIDs;
   private static Vector extendedExtensionObjects;
   /** @deprecated */
   protected boolean criticality;
   /** @deprecated */
   protected int extensionTypeFlag;
   byte[] theOID;
   int theOIDLen;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   /** @deprecated */
   protected byte[] extEncoding;

   /** @deprecated */
   public static void extend(byte[] var0, X509V3Extension var1) throws CertificateException {
      if (var0 != null && var1 != null) {
         if (oidAlreadyInUse(var0)) {
            throw new CertificateException("X509V3Extension.extend: oid is already in use.");
         } else {
            X509V3Extension var2 = findExtendedExtension(var0, 0, var0.length);
            if (var2 == null) {
               extendedExtensionOIDs.addElement(var0);
               extendedExtensionObjects.addElement(var1);
            } else if (!var1.getClass().isInstance(var2)) {
               throw new CertificateException("X509V3Extension.extend: there exists an extended extension using the same OID.");
            }

         }
      } else {
         throw new CertificateException("X509V3Extension.extend: neither oid nor extension should be null.");
      }
   }

   private static boolean oidAlreadyInUse(byte[] var0) {
      return isStandardOID(var0) || isSpecialOID(var0);
   }

   private static boolean isStandardOID(byte[] var0) {
      if (var0.length != 3) {
         return false;
      } else if (var0[0] != 85) {
         return false;
      } else if (var0[1] != 29) {
         return false;
      } else {
         for(int var1 = 0; var1 < STANDARD_EXTENSION_LIST.length; ++var1) {
            if (var0[2] == STANDARD_EXTENSION_LIST[var1]) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isSpecialOID(byte[] var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < SPECIAL_EXTENSION_LIST.length; ++var2) {
         byte[] var3 = SPECIAL_EXTENSION_LIST[var2];
         if (oidsMatch(var3, var0, 0, var1)) {
            return true;
         }
      }

      return false;
   }

   private static X509V3Extension findExtendedExtension(byte[] var0, int var1, int var2) throws CertificateException {
      int var3 = extendedExtensionOIDs.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         byte[] var5 = (byte[])((byte[])extendedExtensionOIDs.elementAt(var4));
         if (oidsMatch(var5, var0, var1, var2)) {
            try {
               X509V3Extension var6 = (X509V3Extension)extendedExtensionObjects.elementAt(var4);
               return (X509V3Extension)var6.clone();
            } catch (CloneNotSupportedException var7) {
               throw new CertificateException("X509V3Extension.findExtendedExtension: extended extension should support clone method.", var7);
            }
         }
      }

      return null;
   }

   private static boolean oidsMatch(byte[] var0, byte[] var1, int var2, int var3) {
      if (var0.length != var3) {
         return false;
      } else {
         for(int var4 = 0; var4 < var3; ++var4) {
            if (var0[var4] != var1[var2 + var4]) {
               return false;
            }
         }

         return true;
      }
   }

   /** @deprecated */
   public static X509V3Extension getInstance(byte[] var0, int var1) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         ASN1Container[] var2 = decodeExtension(var0, var1);
         int var3 = findOID(var2[1].data, var2[1].dataOffset, var2[1].dataLen);
         Object var4;
         switch (var3) {
            case -1:
               var4 = findExtendedExtension(var2[1].data, var2[1].dataOffset, var2[1].dataLen);
               if (var4 == null) {
                  var4 = new NonStandardExtension();
               }
               break;
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 22:
            case 25:
            case 26:
            case 34:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            default:
               throw new CertificateException("Unknown extension.");
            case 9:
               var4 = new SubjectDirectoryAttributes();
               break;
            case 14:
               var4 = new SubjectKeyID();
               break;
            case 15:
               var4 = new KeyUsage();
               break;
            case 16:
               var4 = new PrivateKeyUsagePeriod();
               break;
            case 17:
               var4 = new SubjectAltName();
               break;
            case 18:
               var4 = new IssuerAltName();
               break;
            case 19:
               var4 = new BasicConstraints();
               break;
            case 20:
               var4 = new CRLNumber();
               break;
            case 21:
               var4 = new ReasonCode();
               break;
            case 23:
               var4 = new HoldInstructionCode();
               break;
            case 24:
               var4 = new InvalidityDate();
               break;
            case 27:
               var4 = new DeltaCRLIndicator();
               break;
            case 28:
               var4 = new IssuingDistributionPoint();
               break;
            case 29:
               var4 = new CertificateIssuer();
               break;
            case 30:
               var4 = new NameConstraints();
               break;
            case 31:
               var4 = new CRLDistributionPoints();
               break;
            case 32:
               var4 = new CertPolicies();
               break;
            case 33:
               var4 = new PolicyMappings();
               break;
            case 35:
               var4 = new AuthorityKeyID();
               break;
            case 36:
               var4 = new PolicyConstraints();
               break;
            case 37:
               var4 = new ExtendedKeyUsage();
               break;
            case 46:
               var4 = new FreshestCRL();
               break;
            case 54:
               var4 = new InhibitAnyPolicy();
               break;
            case 100:
               var4 = new AuthorityInfoAccess();
               break;
            case 101:
               var4 = new NetscapeCertType();
               break;
            case 102:
               var4 = new NetscapeBaseURL();
               break;
            case 103:
               var4 = new NetscapeRevocationURL();
               break;
            case 104:
               var4 = new NetscapeCARevocationURL();
               break;
            case 105:
               var4 = new NetscapeCertRenewalURL();
               break;
            case 106:
               var4 = new NetscapeCAPolicyURL();
               break;
            case 107:
               var4 = new NetscapeSSLServerName();
               break;
            case 108:
               var4 = new NetscapeComment();
               break;
            case 109:
               var4 = new VeriSignCZAG();
               break;
            case 110:
               var4 = new VeriSignFidelityUniqueID();
               break;
            case 111:
               var4 = new VeriSignNetscapeInBoxV1();
               break;
            case 112:
               var4 = new VeriSignNetscapeInBoxV2();
               break;
            case 113:
               var4 = new VeriSignJurisdictionHash();
               break;
            case 114:
               var4 = new VeriSignTokenType();
               break;
            case 115:
               var4 = new VeriSignSerialNumberRollover();
               break;
            case 116:
               var4 = new VeriSignNonVerifiedElements();
               break;
            case 117:
               var4 = new OCSPNoCheck();
               break;
            case 118:
               var4 = new ArchiveCutoff();
               break;
            case 119:
               var4 = new CRLReference();
               break;
            case 120:
               var4 = new OCSPNonce();
               break;
            case 121:
               var4 = new OCSPAcceptableResponses();
               break;
            case 122:
               var4 = new OCSPServiceLocator();
               break;
            case 123:
               var4 = new QCStatements();
               break;
            case 124:
               var4 = new BiometricInfo();
               break;
            case 125:
               var4 = new SubjectInfoAccess();
         }

         ((X509V3Extension)var4).setCriticality(((BooleanContainer)var2[2]).value);

         try {
            ((X509V3Extension)var4).setEncoding(var2[3].data, var2[3].dataOffset, var2[3].dataLen);
            ((X509V3Extension)var4).decodeValue(var2[3].data, var2[3].dataOffset);
         } catch (CertificateException var6) {
            if (((X509V3Extension)var4).getCriticality()) {
               throw new CertificateException(var6);
            }
         }

         ((X509V3Extension)var4).setOID(var2[1].data, var2[1].dataOffset, var2[1].dataLen);
         return (X509V3Extension)var4;
      }
   }

   /** @deprecated */
   public void setEncoding(byte[] var1, int var2, int var3) {
      this.extEncoding = new byte[var3];
      System.arraycopy(var1, var2, this.extEncoding, 0, var3);
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            return var1 + ASN1Lengths.determineLength(var0, var1);
         } catch (ASN_Exception var3) {
            throw new CertificateException("Could not read the BER encoding.");
         }
      }
   }

   /** @deprecated */
   protected static ASN1Container[] decodeExtension(byte[] var0, int var1) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var2 = new SequenceContainer(0);
         EndContainer var3 = new EndContainer();
         OIDContainer var4 = new OIDContainer(16777216);
         BooleanContainer var5 = new BooleanContainer(131072);
         OctetStringContainer var6 = new OctetStringContainer(0);
         ASN1Container[] var7 = new ASN1Container[]{var2, var4, var5, var6, var3};

         try {
            ASN1.berDecode(var0, var1, var7);
            return var7;
         } catch (ASN_Exception var9) {
            throw new CertificateException("Cannot read the BER of the extension.");
         }
      }
   }

   /** @deprecated */
   protected void setOID(byte[] var1, int var2, int var3) {
   }

   /** @deprecated */
   public byte[] getOID() {
      if (this.theOID != null && this.theOIDLen != 0) {
         byte[] var1 = new byte[this.theOIDLen];
         System.arraycopy(this.theOID, 0, var1, 0, this.theOIDLen);
         return var1;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public void setStandardOID(int var1) {
      this.theOID = new byte[3];
      this.theOID[0] = 85;
      this.theOID[1] = 29;
      this.theOID[2] = (byte)var1;
      this.theOIDLen = 3;
   }

   /** @deprecated */
   public void setSpecialOID(byte[] var1) {
      if (var1 != null) {
         this.theOIDLen = var1.length;
         this.theOID = new byte[this.theOIDLen];
         System.arraycopy(var1, 0, this.theOID, 0, this.theOIDLen);
      }

   }

   private static int findOID(byte[] var0, int var1, int var2) {
      int var3;
      if (var2 == 3 && (var0[var1] & 255) == 85 && (var0[var1 + 1] & 255) == 29) {
         var3 = var0[var1 + 2] & 255;
         int var4 = 0;

         while(var3 != STANDARD_EXTENSION_LIST[var4]) {
            ++var4;
            if (STANDARD_EXTENSION_LIST[var4] == -1) {
               return -1;
            }
         }

         return var3;
      } else {
         for(var3 = 0; var3 < SPECIAL_EXTENSION_LIST.length; ++var3) {
            if (arrayCompare(SPECIAL_EXTENSION_LIST[var3], 0, SPECIAL_EXTENSION_LIST[var3].length, var0, var1, var2)) {
               return 100 + var3;
            }
         }

         return -1;
      }
   }

   private static boolean arrayCompare(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) {
      if (var0 != null && var3 != null) {
         if (var2 != var5) {
            return false;
         } else {
            for(int var6 = 0; var6 < var2; ++var6) {
               if (var0[var6 + var1] != var3[var6 + var4]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return var0 == null && var3 == null;
      }
   }

   /** @deprecated */
   public void setCriticality(boolean var1) {
      if (var1 != this.criticality) {
         this.criticality = var1;
      }
   }

   /** @deprecated */
   public boolean getCriticality() {
      return this.criticality;
   }

   /** @deprecated */
   public abstract void decodeValue(byte[] var1, int var2) throws CertificateException;

   /** @deprecated */
   public int getExtensionType() {
      return this.extensionTypeFlag;
   }

   /** @deprecated */
   public String getExtensionTypeString() {
      return this.extensionTypeString;
   }

   /** @deprecated */
   public boolean isExtensionType(int var1) {
      return var1 == this.extensionTypeFlag;
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      return this.extEncoding != null ? this.derEncodeExtensionLen(var1, this.extEncoding.length) : this.derEncodeExtensionLen(var1, this.derEncodeValueInit());
   }

   /** @deprecated */
   public abstract int derEncodeValueInit();

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) {
      return var1 != null && var2 >= 0 ? this.derEncodeExtension(var1, var2, var3) : 0;
   }

   /** @deprecated */
   protected int derEncodeExtensionLen(int var1, int var2) {
      this.special = var1;

      try {
         SequenceContainer var4 = new SequenceContainer(0, true, 0);
         EndContainer var5 = new EndContainer();
         OIDContainer var6 = new OIDContainer(16777216, true, 0, this.theOID, 0, this.theOIDLen);
         BooleanContainer var3;
         if (!this.criticality) {
            var3 = new BooleanContainer(131072, false, 0, this.criticality);
         } else {
            var3 = new BooleanContainer(131072, true, 0, this.criticality);
         }

         OctetStringContainer var7 = new OctetStringContainer(0, true, 0, (byte[])null, 0, var2);
         ASN1Container[] var8 = new ASN1Container[]{var4, var6, var3, var7, var5};
         this.asn1Template = new ASN1Template(var8);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var9) {
         return 0;
      }
   }

   /** @deprecated */
   protected int derEncodeExtension(byte[] var1, int var2, int var3) {
      if (var1 == null) {
         return 0;
      } else if ((this.asn1Template == null || this.special != var3) && this.getDERLen(var3) == 0) {
         return 0;
      } else {
         int var4;
         try {
            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            return 0;
         }

         if (this.extEncoding != null) {
            if (var1.length - (var2 + var4) < this.extEncoding.length) {
               return 0;
            } else {
               System.arraycopy(this.extEncoding, 0, var1, var2 + var4, this.extEncoding.length);
               return this.extEncoding.length + var4;
            }
         } else {
            var4 += this.derEncodeValue(var1, var2 + var4);
            return var4;
         }
      }
   }

   /** @deprecated */
   public abstract int derEncodeValue(byte[] var1, int var2);

   /** @deprecated */
   public abstract Object clone() throws CloneNotSupportedException;

   /** @deprecated */
   protected void copyValues(X509V3Extension var1) {
      if (var1 != null) {
         var1.criticality = this.criticality;
         if (this.theOID != null) {
            var1.theOID = (byte[])((byte[])this.theOID.clone());
            var1.theOIDLen = this.theOIDLen;
         }

         var1.special = this.special;
         if (this.asn1Template != null) {
            var1.getDERLen(this.special);
         }

      }
   }

   /** @deprecated */
   protected void reset() {
      this.asn1Template = null;
   }

   /** @deprecated */
   protected byte[] intToByteArray(int var1) {
      int var3 = var1 >>> 24;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      byte[] var2;
      if (var3 != 0) {
         var2 = new byte[]{(byte)var3, (byte)var4, (byte)var5, (byte)var6};
      } else if (var4 != 0) {
         var2 = new byte[]{(byte)var4, (byte)var5, (byte)var6};
      } else if (var5 != 0) {
         var2 = new byte[]{(byte)var5, (byte)var6};
      } else {
         var2 = new byte[]{(byte)var6};
      }

      return var2;
   }

   /** @deprecated */
   protected int byteArrayToInt(byte[] var1) {
      int var2 = 0;

      for(int var4 = 0; var4 < var1.length; ++var4) {
         byte var3 = var1[var4];
         var2 = (var2 << 8) + (var3 & 255);
      }

      return var2;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof X509V3Extension)) {
         return false;
      } else {
         int var2 = this.getDERLen(0);
         if (var2 == 0) {
            return false;
         } else {
            byte[] var3 = new byte[var2];
            this.getDEREncoding(var3, 0, 0);
            X509V3Extension var4 = (X509V3Extension)var1;
            int var5 = var4.getDERLen(0);
            if (var5 == 0) {
               return false;
            } else {
               byte[] var6 = new byte[var5];
               var4.getDEREncoding(var6, 0, 0);
               return CertJUtils.byteArraysEqual(var3, var6);
            }
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];
         this.getDEREncoding(var2, 0, 0);
         return Arrays.hashCode(var2);
      }
   }

   static {
      SPECIAL_EXTENSION_LIST = new byte[][]{AUTHORITY_INFO_OID, NETSCAPE_CERT_TYPE_OID, NETSCAPE_BASE_URL_OID, NETSCAPE_REVOCATION_URL_OID, NETSCAPE_CA_REVOCATION_URL_OID, NETSCAPE_CERT_RENEWAL_URL_OID, NETSCAPE_CA_POLICY_URL_OID, NETSCAPE_SSL_SERVER_NAME_OID, NETSCAPE_COMMENT_OID, VERISIGN_CZAG_OID, VERISIGN_FIDELITY_ID_OID, VERISIGN_NETSCAPE_INBOX_V1_OID, VERISIGN_NETSCAPE_INBOX_V2_OID, VERISIGN_JURISDICTION_HASH_OID, VERISIGN_TOKEN_TYPE_OID, VERISIGN_SERIAL_NUMBER_OID, VERISIGN_NON_VERIFIED_OID, OCSP_NOCHECK_OID, ARCHIVE_CUTOFF_OID, CRL_REFERENCE_OID, OCSP_NONCE_OID, OCSP_ACCEPTABLE_RESPONSES_OID, OCSP_SERVICE_LOCATOR_OID, QC_STATEMENTS_OID, BIO_INFO_OID, SUBJECT_INFO_OID};
      extendedExtensionOIDs = new Vector();
      extendedExtensionObjects = new Vector();
   }
}
