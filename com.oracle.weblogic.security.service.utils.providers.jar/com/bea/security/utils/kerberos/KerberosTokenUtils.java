package com.bea.security.utils.kerberos;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.utils.gss.GSSTokenUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DERGeneralString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;
import weblogic.utils.Hex;

public class KerberosTokenUtils {
   public static final String KRB5_MECH_NAME_OID = "1.2.840.113554.1.2.2.1";
   public static final String KRB5_MECH_OID = "1.2.840.113554.1.2.2";
   private static final String KRB5_ACCEPTOR_IBM = "com.ibm.security.jgss.krb5.accept";
   private static final String KRB5_ACCEPTOR_SUN = "com.sun.security.jgss.krb5.accept";
   private static byte[] KRB_AP_REQ_TID = new byte[]{1, 0};
   static byte[] DER_KRB_AP_REQ = new byte[]{6, 9, 42, -122, 72, -122, -9, 18, 1, 2, 2, 1, 0};

   private KerberosTokenUtils() {
   }

   public static byte[] getGssInitContextToken(byte[] krbApReqToken, LoggerSpi logger) throws IOException {
      boolean isDebugEnabled = logger != null && logger.isDebugEnabled();
      if (isDebugEnabled) {
         logger.debug("Encoding GSS InitContextToken from KrbApReqToken...");
      }

      if (krbApReqToken != null && krbApReqToken.length >= 1) {
         ByteArrayOutputStream datastream = new ByteArrayOutputStream();

         byte[] derEncodedData;
         try {
            if (isDebugEnabled) {
               logger.debug("Encoding Krb5 OID (0x06, 0x09, 0x2a, 0x86, 0x48, 0x86, 0xf7, 0x12, 0x01, 0x02, 0x02)...");
            }

            datastream.write(GSSTokenUtils.KERBEROS_V5_OID);
            if (isDebugEnabled) {
               logger.debug("Encoding Krb AP Req Token ID (0x01, 0x00)...");
            }

            datastream.write(KRB_AP_REQ_TID);
            if (isDebugEnabled) {
               logger.debug("Encoding Krb AP Req Token ( " + Hex.dump(krbApReqToken) + " )...");
            }

            datastream.write(krbApReqToken);
            if (isDebugEnabled) {
               logger.debug("Encoding Application Constructed Object(0x60) and token length...");
            }

            derEncodedData = GSSTokenUtils.encodeData(96, datastream.toByteArray());
            if (isDebugEnabled) {
               logger.debug("Got GSS InitContextToken \n" + Hex.dump(derEncodedData));
            }
         } finally {
            try {
               datastream.close();
            } catch (IOException var11) {
            }

         }

         return derEncodedData;
      } else {
         throw new IllegalArgumentException("Input KrbApReqToken is null or empty.");
      }
   }

   public static byte[] getKrbApReqToken(byte[] gssInitContextToken, LoggerSpi logger) throws IOException {
      boolean isDebugEnabled = logger != null && logger.isDebugEnabled();
      if (isDebugEnabled) {
         logger.debug("Getting KrbApReqToken from GSS InitContextToken...");
      }

      if (gssInitContextToken != null && gssInitContextToken.length >= 1) {
         ByteArrayInputStream is = new ByteArrayInputStream(gssInitContextToken);

         byte[] apReqToken;
         try {
            if (is.read() != 96) {
               throw new IOException("Failed to read Application Constructed type tag.");
            }

            int tokenLen = GSSTokenUtils.decodeLength(is);
            if (tokenLen == -1) {
               throw new IOException("Failed to read token length.");
            }

            int oidLen = GSSTokenUtils.KERBEROS_V5_OID.length;
            byte[] krbOID = new byte[oidLen];
            if (is.read(krbOID) < oidLen) {
               throw new IOException("Failed to read Krb OID.");
            }

            if (!Arrays.equals(krbOID, GSSTokenUtils.KERBEROS_V5_OID)) {
               throw new IOException("Got non-Krb mech OID (" + Hex.dump(krbOID) + ")");
            }

            int krbApReqTokeIdLen = KRB_AP_REQ_TID.length;
            byte[] apReqTID = new byte[krbApReqTokeIdLen];
            if (is.read(apReqTID) < krbApReqTokeIdLen) {
               throw new IOException("Failed to read Kbr AP REQ token Id.");
            }

            if (!Arrays.equals(apReqTID, KRB_AP_REQ_TID)) {
               throw new IOException("Got non-Kbr AP REQ token Id (" + Hex.dump(apReqTID) + ")");
            }

            int apReqTokenLen = tokenLen - oidLen - krbApReqTokeIdLen;
            apReqToken = new byte[apReqTokenLen];
            if (is.read(apReqToken) < apReqTokenLen) {
               throw new IOException("Failed to read Kbr AP REQ token.");
            }

            if (isDebugEnabled) {
               logger.debug("Got KrbApReqToken ( " + Hex.dump(apReqToken) + " )");
            }
         } finally {
            try {
               is.close();
            } catch (IOException var16) {
            }

         }

         return apReqToken;
      } else {
         throw new IllegalArgumentException("Input InitContextToken is null or empty.");
      }
   }

   public static String extractServicePrincipalFromToken(byte[] mechToken, LoggerSpi logger) {
      boolean isDebugEnabled = logger != null && logger.isDebugEnabled();
      int pos = getPositionOfKrbApReq(mechToken);
      if (pos < 0) {
         return null;
      } else {
         try {
            ASN1InputStream ai = new ASN1InputStream(Arrays.copyOfRange(mechToken, pos, mechToken.length));
            ASN1Sequence apReq = (ASN1Sequence)((DERApplicationSpecific)ai.readObject()).getObject();
            ASN1Sequence seqTicket = (ASN1Sequence)((DERApplicationSpecific)untag(apReq.getObjectAt(3))).getObject();
            String realName = ((DERGeneralString)untag(seqTicket.getObjectAt(1))).toString();
            StringBuilder principalName = new StringBuilder();
            DERSequence seqPrincipalName = (DERSequence)untag(seqTicket.getObjectAt(2));

            for(int i = 0; i < seqPrincipalName.size(); ++i) {
               ASN1Primitive obj = untag(seqPrincipalName.getObjectAt(i));
               if (obj instanceof DERSequence) {
                  DERSequence seqObj = (DERSequence)obj;
                  Enumeration en = seqObj.getObjects();

                  while(en.hasMoreElements()) {
                     if (!principalName.toString().isEmpty()) {
                        principalName.append("/");
                     }

                     DERGeneralString name = (DERGeneralString)en.nextElement();
                     principalName.append(name.toString());
                  }
               }
            }

            return principalName.append("@" + realName).toString();
         } catch (Exception var15) {
            if (isDebugEnabled) {
               logger.debug("catch exception while parsing principal name from the SPNEGO token.", var15);
            }

            return null;
         }
      }
   }

   public static GSSContext getAcceptGSSContextForService(GSSManager gssManager, String principalName, LoggerSpi logger) {
      boolean isDebugEnabled = logger != null && logger.isDebugEnabled();
      GSSContext ctx = null;
      if (principalName != null) {
         try {
            GSSName name = gssManager.createName(principalName, new Oid("1.2.840.113554.1.2.2.1"));
            GSSCredential c = gssManager.createCredential(name, 0, new Oid("1.2.840.113554.1.2.2"), 2);
            ctx = gssManager.createContext(c);
         } catch (GSSException var7) {
            if (isDebugEnabled) {
               logger.debug("Failed to create GSSContext for " + principalName, var7);
            }
         }
      }

      return ctx;
   }

   public static boolean isIBMProvider() {
      Provider[] providers = Security.getProviders("GssApiMechanism.1.2.840.113554.1.2.2");
      return providers != null && providers[0].getName().startsWith("IBM");
   }

   public static List getConfigedPrincipals(LoggerSpi logger) {
      boolean isDebugEnabled = logger != null && logger.isDebugEnabled();
      List ret = new ArrayList(0);

      try {
         Configuration cfg = Configuration.getConfiguration();
         AppConfigurationEntry[] ents = cfg.getAppConfigurationEntry(isIBMProvider() ? "com.ibm.security.jgss.krb5.accept" : "com.sun.security.jgss.krb5.accept");
         if (ents != null) {
            AppConfigurationEntry[] var5 = ents;
            int var6 = ents.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               AppConfigurationEntry ent = var5[var7];
               String mName = ent.getLoginModuleName();
               if (mName != null && mName.indexOf("Krb5LoginModule") > 0) {
                  String principal = (String)ent.getOptions().get("principal");
                  if (principal != null) {
                     ret.add(principal);
                  }
               }
            }
         }
      } catch (Exception var11) {
         if (isDebugEnabled) {
            logger.debug("Failed to read system's krb5 login module information.", var11);
         }
      }

      return ret;
   }

   private static int getPositionOfKrbApReq(byte[] ticket) {
      int len = ticket.length;
      int len2 = DER_KRB_AP_REQ.length;

      for(int i = 0; i < len; ++i) {
         if (ticket[i] == DER_KRB_AP_REQ[0]) {
            if (i + len2 >= len) {
               break;
            }

            int j = false;

            int j;
            for(j = 0; j <= len2 && j != len2 && ticket[i + j] == DER_KRB_AP_REQ[j]; ++j) {
            }

            if (j == len2) {
               return i + len2;
            }
         }
      }

      return -1;
   }

   private static ASN1Primitive untag(ASN1Encodable src) {
      return src instanceof DERTaggedObject ? ((DERTaggedObject)src).getObject() : src.toASN1Primitive();
   }
}
