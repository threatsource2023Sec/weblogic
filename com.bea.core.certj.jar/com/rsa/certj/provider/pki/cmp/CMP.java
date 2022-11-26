package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.crmf.CRMFException;
import com.rsa.certj.crmf.RegInfo;
import com.rsa.certj.provider.pki.PKICommonImplementation;
import com.rsa.certj.provider.pki.PKIDebug;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIInterface;
import com.rsa.certj.spi.pki.PKIMessage;
import com.rsa.certj.spi.pki.PKIRequestMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.PKIResult;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.certj.spi.pki.PKITransportException;
import com.rsa.certj.spi.pki.POPGenerationInfo;
import com.rsa.certj.spi.pki.POPValidationInfo;
import com.rsa.certj.spi.pki.ProtectInfo;
import com.rsa.jsafe.JSAFE_PrivateKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

/** @deprecated */
public class CMP extends Provider implements PKIDebug {
   /** @deprecated */
   public static final int CMP1999 = 1;
   /** @deprecated */
   public static final int CMP2000 = 2;
   /** @deprecated */
   protected static final int MESSAGE_IR = 0;
   /** @deprecated */
   protected static final int MESSAGE_IP = 1;
   /** @deprecated */
   protected static final int MESSAGE_CR = 2;
   /** @deprecated */
   protected static final int MESSAGE_CP = 3;
   /** @deprecated */
   protected static final int MESSAGE_P10CR = 4;
   /** @deprecated */
   protected static final int MESSAGE_POPDECC = 5;
   /** @deprecated */
   protected static final int MESSAGE_POPDECR = 6;
   /** @deprecated */
   protected static final int MESSAGE_KUR = 7;
   /** @deprecated */
   protected static final int MESSAGE_KUP = 8;
   /** @deprecated */
   protected static final int MESSAGE_KRR = 9;
   /** @deprecated */
   protected static final int MESSAGE_KRP = 10;
   /** @deprecated */
   protected static final int MESSAGE_RR = 11;
   /** @deprecated */
   protected static final int MESSAGE_RP = 12;
   /** @deprecated */
   protected static final int MESSAGE_CCR = 13;
   /** @deprecated */
   protected static final int MESSAGE_CCP = 14;
   /** @deprecated */
   protected static final int MESSAGE_CKUANN = 15;
   /** @deprecated */
   protected static final int MESSAGE_CANN = 16;
   /** @deprecated */
   protected static final int MESSAGE_RANN = 17;
   /** @deprecated */
   protected static final int MESSAGE_CRLANN = 18;
   /** @deprecated */
   protected static final int MESSAGE_PKICONF = 19;
   /** @deprecated */
   protected static final int MESSAGE_NESTED = 20;
   /** @deprecated */
   protected static final int MESSAGE_GENM = 21;
   /** @deprecated */
   protected static final int MESSAGE_GENP = 22;
   /** @deprecated */
   protected static final int MESSAGE_ERROR = 23;
   /** @deprecated */
   protected static final int MESSAGE_CERTCONF = 24;
   /** @deprecated */
   public static final int DEFAULT_CMPTCP_PORT = 829;
   /** @deprecated */
   public static final int CMPTCP_ERROR_NONE = 0;
   /** @deprecated */
   public static final int CMPTCP_ERROR_VERSION = 257;
   /** @deprecated */
   public static final int CMPTCP_ERROR_CLIENT = 512;
   /** @deprecated */
   public static final int CMPTCP_ERROR_MSGTYPE = 513;
   /** @deprecated */
   public static final int CMPTCP_ERROR_POLLID = 514;
   /** @deprecated */
   public static final int CMPTCP_ERROR_SERVER = 768;
   /** @deprecated */
   public static final int CMPTCP_FLAGS_CLOSE_CONN = 1;
   /** @deprecated */
   public static final int CMPTCP_FLAGS_NO_AUTOPOLL = 2;
   /** @deprecated */
   protected static final int CONTEXT_SPECIAL = 10551296;
   /** @deprecated */
   protected static final byte[] PASSWORD_BASED_MAC_OID = new byte[]{42, -122, 72, -122, -10, 125, 7, 66, 13};
   private static final int CMPTCP_SPEC_VERSION = 10;
   private static final int HEADER_REMAINDER_SIZE_V10 = 3;
   private static final int HEADER_LEN_SIZE = 4;
   private static final int HEADER_SIZE_V10 = 7;
   private static final int POLLID_SIZE = 4;
   private static final int SLEEP_TIME_SIZE = 4;
   private static final int HEADER_VERSION_INDEX = 4;
   private static final int HEADER_FLAGS_INDEX = 5;
   private static final int HEADER_MSGTYPE_INDEX = 6;
   private static final int MSGTYPE_PKIREQ = 0;
   private static final int MSGTYPE_POLLREP = 1;
   private static final int MSGTYPE_POLLREQ = 2;
   private static final int MSGTYPE_FINREP = 3;
   private static final int MSGTYPE_PKIREP = 5;
   private static final int MSGTYPE_ERRORMSGREP = 6;
   private static final int CMPTCP_MAX_DATAMSG_LEN = 50000;
   private static final int MSG_POLLREP_LEN = 8;
   private static final int MSG_FINREP_LEN = 0;
   private static final int MSG_ERROR_MSGREP_TYPE_LEN = 2;
   private static final int MSG_ERROR_MSGREP_LEN_LEN = 2;
   private Hashtable configProperties;
   private int cmptcpFlags;
   private int pollDuration;

   /** @deprecated */
   public CMP(String var1, InputStream var2) throws InvalidParameterException {
      super(4, var1);
      if (var2 == null) {
         throw new InvalidParameterException("CMP.CMP: configStream should not be null.");
      } else {
         this.configProperties = PKICommonImplementation.loadProperties(var2);
      }
   }

   /** @deprecated */
   public CMP(String var1, File var2) throws InvalidParameterException {
      super(4, var1);
      if (var2 == null) {
         throw new InvalidParameterException("CMP.CMP: configFile should not be null.");
      } else {
         FileInputStream var3 = null;

         try {
            var3 = new FileInputStream(var2);
            this.configProperties = PKICommonImplementation.loadProperties(var3);
         } catch (FileNotFoundException var12) {
            throw new InvalidParameterException("CMP.CMP: " + var2.toString() + " does not exist.");
         } finally {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (IOException var11) {
               }
            }

         }

      }
   }

   /** @deprecated */
   public CMP(String var1, String var2) throws InvalidParameterException {
      super(4, var1);
      if (var2 == null) {
         throw new InvalidParameterException("CMP.CMP: configFileName should not be null.");
      } else {
         FileInputStream var3 = null;

         try {
            var3 = new FileInputStream(new File(var2));
            this.configProperties = PKICommonImplementation.loadProperties(var3);
         } catch (FileNotFoundException var12) {
            throw new InvalidParameterException("CMP.CMP: " + var2 + " does not exist.");
         } finally {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (IOException var11) {
               }
            }

         }

      }
   }

   /** @deprecated */
   public synchronized void setCMPTCPOptions(int var1, int var2) {
      this.cmptcpFlags = var1;
      this.pollDuration = var2;
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (Exception var3) {
         throw new ProviderManagementException("CMP.instantiate: ", var3);
      }
   }

   /** @deprecated */
   public void saveMessage(byte[] var1, PKIMessage var2, ProtectInfo var3) throws CMPException {
   }

   /** @deprecated */
   public void saveCertificate(PKIResponseMessage var1) throws CMPException {
   }

   /** @deprecated */
   public void saveData(byte[] var1, String var2) throws CMPException {
   }

   /** @deprecated */
   protected static byte[] derEncodeProtectedPart(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) throws CMPException {
      try {
         SequenceContainer var6 = new SequenceContainer(0, true, 0);
         EncodedContainer var7 = new EncodedContainer(0, true, 0, var0, var1, var2);
         EncodedContainer var8 = new EncodedContainer(0, true, 0, var3, var4, var5);
         EndContainer var9 = new EndContainer();
         ASN1Container[] var10 = new ASN1Container[]{var6, var7, var8, var9};
         ASN1Template var11 = new ASN1Template(var10);
         byte[] var12 = new byte[var11.derEncodeInit()];
         var11.derEncode(var12, 0);
         return var12;
      } catch (ASN_Exception var13) {
         throw new CMPException("CMP.derEncodeProtectedPart: Encoding ProtectedPart failed.", var13);
      }
   }

   /** @deprecated */
   protected static RegInfo convertRegInfo(Properties var0) throws CMPException {
      if (var0 == null) {
         return null;
      } else {
         RegInfo var1 = new RegInfo();
         Enumeration var2 = var0.propertyNames();

         while(var2.hasMoreElements()) {
            String var3 = (String)var2.nextElement();
            String var4 = var0.getProperty(var3, (String)null);

            try {
               var1.addNameValuePair(var3, var4, true);
            } catch (CRMFException var6) {
               throw new CMPException("CMP$Implementation.convertRegInfo: ", var6);
            }
         }

         return var1;
      }
   }

   private synchronized int getCmpTcpFlags() {
      return this.cmptcpFlags;
   }

   private synchronized int getPollDuration() {
      return this.pollDuration;
   }

   private final class a extends PKICommonImplementation implements PKIInterface {
      private a(CertJ var2, String var3) throws InvalidParameterException, PKIException {
         super(var2, var3);
         this.loadConfig(CMP.this.configProperties);
         if (this.destList.length <= 1 && this.destList.length != 0) {
            this.closeConnection = (CMP.this.getCmpTcpFlags() & 1) != 0;
            String var4 = this.destList[0];
            StringTokenizer var5 = new StringTokenizer(var4, ":");
            if (!var5.hasMoreTokens()) {
               throw new CMPException("CMP$Implementation.Implementation:dest string does not contain protocol part.");
            } else {
               String var6 = var5.nextToken();
               if (!var6.equals("cmptcp")) {
                  throw new CMPException("CMP$Implementation.Implementation: protocol(" + var6 + ") is not supported.");
               } else {
                  String var7 = var4.substring(var6.length() + 3);
                  var5 = new StringTokenizer(var7, "/");
                  if (!var5.hasMoreTokens()) {
                     throw new CMPException("CMP$Implementation.Implementation:urlString does not contain host part.");
                  } else {
                     String var8 = var5.nextToken();
                     StringTokenizer var9 = new StringTokenizer(var8, ":");
                     this.host = null;
                     this.port = 829;
                     if (var9.hasMoreTokens()) {
                        this.host = var9.nextToken();
                        if (var9.hasMoreTokens()) {
                           this.port = Integer.parseInt(var9.nextToken());
                        }
                     }

                  }
               }
            }
         } else {
            throw new CMPException("CMP$Implementation.Implementation: config file should contain exactly one dest information.");
         }
      }

      /** @deprecated */
      public PKIResponseMessage readCertificationResponseMessage(byte[] var1, ProtectInfo var2) throws NotSupportedException {
         throw new NotSupportedException("CMP$Implementation.readCertificationResponseMessage: use sendRequest method.");
      }

      private CMPResponseCommon a(byte[] var1, CMPProtectInfo var2, CMPRequestCommon var3) throws CMPException {
         return CMPResponseCommon.berDecode(var1, var2, var3, this.certJ);
      }

      /** @deprecated */
      public byte[] writeCertificationRequestMessage(PKIRequestMessage var1, ProtectInfo var2) throws NotSupportedException {
         throw new NotSupportedException("CMP$Implementation.writeCertificationRequestMessage: use sendRequest method.");
      }

      private byte[] a(CMPRequestCommon var1, CMPProtectInfo var2) throws CMPException {
         return var1.derEncode(var2, this.certJ);
      }

      public PKIResponseMessage sendRequest(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws PKIException {
         if (!(var1 instanceof CMPRequestCommon)) {
            throw new CMPException("CMP$Implementation.sendRequest: message should be an instance of either CMPInitRequestMessage or CMPCertRequestMessage.");
         } else if (var2 != null && !(var2 instanceof CMPProtectInfo)) {
            throw new CMPException("CMP$Implementation.sendRequest:protectInfo should be either null, or an instance of CMPProtectInfo.");
         } else {
            CMPProtectInfo var4 = null;
            if (var2 != null) {
               var4 = (CMPProtectInfo)var2;
            }

            CMPRequestCommon var5 = (CMPRequestCommon)var1;
            this.b(var5, var4);
            byte[] var6 = this.a(var5, var4);
            CMP.this.saveMessage(var6, var5, var4);
            PKIResult var7 = this.a(var6);
            PKIStatusInfo var8 = var7.getStatusInfo();
            if (var8.getStatus() != 0) {
               throw new PKITransportException("CMP$Implementation.sendRequest: transport error occurred while sending a message of type(" + var5.getMessageType() + ").", var8);
            } else {
               byte[] var9 = var7.getEncodedResponse();
               CMP.this.saveData(var9, "RespTemp.ber");
               CMPResponseCommon var10 = this.a(var9, var4, var5);
               CMP.this.saveMessage(var9, var10, var4);
               int var11 = var10.getMessageType();
               if (var11 == 23) {
                  return var10;
               } else {
                  var8 = var10.getStatusInfo();
                  this.a(var10, var5);
                  if (var3 != null) {
                     this.disperseCertsAndCRLs(var10, var3);
                  }

                  CMP.this.saveCertificate(var10);
                  return var10;
               }
            }
         }
      }

      /** @deprecated */
      public PKIResponseMessage requestCertification(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws PKIException {
         return this.sendRequest(var1, var2, var3);
      }

      /** @deprecated */
      public PKIResult sendMessage(byte[] var1) throws PKIException {
         return this.a(var1);
      }

      public void generateProofOfPossession(PKIRequestMessage var1, JSAFE_PrivateKey var2, POPGenerationInfo var3) throws CMPException {
         if (!(var1 instanceof CMPCertRequestCommon)) {
            throw new CMPException("CMP$Implementation.generateProofOfPossession: message should be an instance of CMPCertRequestCommon.");
         } else {
            CMPCertRequestCommon var4 = (CMPCertRequestCommon)var1;
            if (!(var3 instanceof CMPPOPGenerationInfo)) {
               throw new CMPException("CMP$Implementation.generateProofOfPossession: popGenerationInfo should be an instance of CMPPOPGenerationInfo.");
            } else {
               int var5 = ((CMPPOPGenerationInfo)var3).getPopType();
               switch (var5) {
                  case 0:
                     var4.setPop(var5);
                     break;
                  case 1:
                     if (var2 == null) {
                        throw new CMPException("CMP$Implementation.generateProofOfPossession: privateKey cannot be null.");
                     }

                     var4.setPop((CMPPOPGenerationInfoSignature)var3, var2, this.certJ);
                     break;
                  case 2:
                     var4.setPop(var5, (CMPPOPGenerationInfoEncryption)var3);
                     break;
                  case 3:
                  default:
                     throw new CMPException("CMP$Implementation.generateProofOfPossession: pop type(" + var5 + ") not supported.");
               }

            }
         }
      }

      public boolean validateProofOfPossession(PKIMessage var1, POPValidationInfo var2) throws NotSupportedException {
         throw new NotSupportedException("CMP$Implementation.validateProofOfPossession: not supported.");
      }

      public void provideProofOfPossession(PKIRequestMessage var1, int var2, byte[] var3) throws NotSupportedException {
         throw new NotSupportedException("CMP$Implementation.provideProofOfPossession: not supported.");
      }

      private void a(CMPResponseCommon var1, CMPRequestCommon var2) throws CMPException {
         if (!CertJUtils.byteArraysEqual(var2.getTransactionID(), var1.getTransactionID())) {
            throw new CMPException("Implementation.checkTransactionID: transaction IDs do not match.");
         }
      }

      private void b(CMPRequestCommon var1, CMPProtectInfo var2) throws CMPException {
         int var3 = var1.getMessageType();
         boolean var4 = false;
         if (var2 != null) {
            var4 = var2.pbmProtected();
         }

         if (var3 == 0 && var2 != null && !var4) {
            throw new CMPException("CMP$Implementation.checkProtectInfoType: signature protection cannot be used for CMPInitRequestMessage.");
         }
      }

      private void a() throws PKITransportException {
         try {
            this.socket.close();
            this.socket = null;
         } catch (IOException var2) {
            throw new PKITransportException("CMP$Implementation.closeSocket: unable to close a socket.", var2);
         }
      }

      private synchronized PKIResult a(byte[] var1) throws PKIException {
         boolean var2 = false;

         PKIResult var4;
         try {
            this.openSocketIfNecessary();
            byte[][] var3 = this.a(this.a(var1.length, 0), var1, true);
            var2 = this.c(var3[0]);
            var4 = this.a(var3);
         } finally {
            if (this.socket != null && (this.closeConnection || var2)) {
               this.a();
            }

         }

         return var4;
      }

      private synchronized PKIResult a(byte[] var1, int var2, Date var3) throws PKIException {
         boolean var4 = false;

         try {
            Thread.sleep((long)(var2 * 1000));
         } catch (InterruptedException var10) {
         }

         if (var3 != null && var3.after(new Date())) {
            return new PKIResult(new PKIStatusInfo(3, 0, (String[])null, var2));
         } else {
            PKIResult var6;
            try {
               this.openSocketIfNecessary();
               byte[][] var5 = this.a(this.a(4, 2), var1, 0, 4, true);
               var4 = this.c(var5[0]);
               var6 = this.a(var5);
            } finally {
               if (this.socket != null && (this.closeConnection || var4)) {
                  this.a();
               }

            }

            return var6;
         }
      }

      private byte[][] a(byte[] var1, byte[] var2, boolean var3) throws PKITransportException {
         return this.a(var1, var2, 0, var2.length, var3);
      }

      private byte[][] a(byte[] var1, byte[] var2, int var3, int var4, boolean var5) throws PKITransportException {
         byte[] var6 = new byte[var1.length + var4];
         System.arraycopy(var1, 0, var6, 0, var1.length);
         System.arraycopy(var2, var3, var6, var1.length, var4);
         return this.a(var6, var5);
      }

      private byte[][] a(byte[] var1, boolean var2) throws PKITransportException {
         try {
            this.writeToSocket(var1);
         } catch (IOException var7) {
            return this.a(var1, var2, "unable to write data to server.");
         }

         byte[] var3 = new byte[7];

         int var4;
         try {
            var4 = this.a(var3, 0, 7);
            if (var4 != 7) {
               return this.a(var1, var2, "unable to read CMPTCP header from server.");
            }
         } catch (InterruptedIOException var10) {
            this.a();
            throw new PKITransportException("CMP$Implementation.cmptcpTrasport: socket read operation interrupted, possibly timed out. Check timeoutSecs value used in your configuration file. " + var10.bytesTransferred + " bytes have been transferred(", var10);
         } catch (IOException var11) {
            return this.a(var1, var2, "unable to read CMPTCP header from server.");
         }

         var4 = this.b(var3);
         byte[] var5 = new byte[var4];

         try {
            int var6 = this.a(var5, 0, var4);
            if (var6 != var4) {
               this.a();
               throw new PKITransportException("CMP$Implementation.cmptcpTransport: unable to read CMPTCP body from server. Possibly the server is down.");
            }
         } catch (InterruptedIOException var8) {
            this.a();
            throw new PKITransportException("CMP$Implementation.cmptcpTrasport: socket read operation interrupted, possibly timed out. Check timeoutSecs value used in your configuration file. " + var8.bytesTransferred + " bytes have been transferred(", var8);
         } catch (IOException var9) {
            this.a();
            throw new PKITransportException("CMP$Implementation.cmptcpTrasport: socket read operation failed. Possibly the server is down.", var9);
         }

         byte[][] var12 = new byte[][]{var3, var5};
         return var12;
      }

      private int a(byte[] var1, int var2, int var3) throws IOException {
         InputStream var4 = this.socket.getInputStream();
         int var5 = 0;

         int var6;
         do {
            var6 = var4.read(var1, var2 + var5, var3 - var5);
            if (var6 > 0) {
               var5 += var6;
            }
         } while(var6 >= 0 && var5 < var3);

         return var5;
      }

      private byte[][] a(byte[] var1, boolean var2, String var3) throws PKITransportException {
         if (var2) {
            this.a();
            this.openSocket();
            return this.a(var1, false);
         } else {
            throw new PKITransportException("CMP$Implementation.retryTransportOrThrow: " + var3 + " Possibly the server is down.");
         }
      }

      private byte[] a(int var1, int var2) {
         byte[] var3 = new byte[7];
         this.a(3 + var1, var3, 0, 4);
         var3[4] = 10;
         byte var4 = 0;
         if (this.closeConnection) {
            var4 = 1;
         }

         var3[5] = var4;
         var3[6] = (byte)var2;
         return var3;
      }

      private int b(byte[] var1) throws PKITransportException {
         int var2 = this.b(var1, 0, 4) - 3;
         if (var2 > 50000) {
            throw new PKITransportException("CMP$Implementation.cmptcpFindMessageBodyLen: received data is too long(>50000).");
         } else {
            return var2;
         }
      }

      private PKIResult a(byte[][] var1) throws PKIException {
         byte[] var2 = var1[0];
         byte[] var3 = var1[1];
         byte var4 = var2[6];
         switch (var4) {
            case 0:
            case 2:
            case 4:
            default:
               throw new CMPException("CMP$Implementation.cmptcpProcessResponse: unexpected message of type(" + var4 + ")received.");
            case 1:
               if (var3.length != 8) {
                  throw new CMPException("CMP$Implementation.cmptcpProcessResponse: POLLREP should have a body of exactly 8 bytes.");
               } else {
                  int var5 = this.b(var3, 4, 4);
                  if ((CMP.this.getCmpTcpFlags() & 2) != 0) {
                     return new PKIResult(new PKIStatusInfo(3, 0, (String[])null, var5));
                  }

                  Date var6 = null;
                  int var7 = CMP.this.getPollDuration();
                  if (var7 > 0) {
                     var6 = new Date(System.currentTimeMillis() + (long)(var7 * 1000));
                  }

                  return this.a(var3, var5, var6);
               }
            case 3:
               if (var3.length != 0) {
                  throw new CMPException("CMP$Implementation.cmptcpProcessResponse: FINREP should have a body of exactly 0 bytes.");
               }

               return new PKIResult(new PKIStatusInfo(0, 0, (String[])null, 0));
            case 5:
               return new PKIResult(new PKIStatusInfo(0, 0, (String[])null, 0), var3);
            case 6:
               return new PKIResult(this.d(var3));
         }
      }

      private boolean c(byte[] var1) {
         return var1[5] != 0;
      }

      private PKIStatusInfo d(byte[] var1) throws CMPException {
         byte var2 = 4;
         if (var1.length < var2) {
            throw new CMPException("CMP$Implementation.cmptcpErrorMsgToStatusInfo: POLLREQ should have a body of more than " + var2 + " bytes.");
         } else {
            int var3 = this.b(var1, 0, 2);
            int var4 = this.b(var1, 2, 2);
            if (var1.length < var2 + var4) {
               throw new CMPException("CMP$Implementation.cmptcpErrorMsgToStatusInfo: message body too short.");
            } else {
               int var5 = var1.length - (var2 + var4);
               int var6;
               switch (var3) {
                  case 257:
                     var6 = 128;
                     break;
                  case 512:
                     var6 = 536870912;
                     break;
                  case 513:
                     var6 = 67108864;
                     break;
                  case 514:
                     if ((CMP.this.getCmpTcpFlags() & 2) != 0) {
                        var6 = 67108864;
                     } else {
                        var6 = 536870912;
                     }
                     break;
                  case 768:
                     var6 = 1048576;
                     break;
                  default:
                     throw new CMPException("CMP$Implementation.cmptcpErrorMsgToStatusInfo: unknown error type(" + var3 + ").");
               }

               String var7 = null;
               String var8 = null;
               if (var4 > 0) {
                  var7 = new String(var1, var2, var4);
               }

               if (var5 > 0) {
                  var8 = new String(var1, var2 + var4, var5);
               }

               String[] var9 = null;
               if (var7 != null && var8 != null) {
                  var9 = new String[]{var7, var8};
               } else if (var7 != null) {
                  var9 = new String[]{var7};
               } else if (var8 != null) {
                  var9 = new String[]{var8};
               }

               return new PKIStatusInfo(2, var6, var9, var3);
            }
         }
      }

      private void a(int var1, byte[] var2, int var3, int var4) {
         for(int var5 = var4 - 1; var5 >= 0; --var5) {
            var2[var3 + var5] = (byte)(var1 & 255);
            var1 >>= 8;
         }

      }

      private int b(byte[] var1, int var2, int var3) {
         int var4 = 0;

         for(int var6 = 0; var6 < var3; ++var6) {
            byte var5 = var1[var2 + var6];
            var4 = (var4 << 8) + (var5 & 255);
         }

         return var4;
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException, PKIException {
         this(var2, var3);
      }
   }
}
