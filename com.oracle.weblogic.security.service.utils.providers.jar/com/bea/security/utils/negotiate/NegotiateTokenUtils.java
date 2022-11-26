package com.bea.security.utils.negotiate;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.utils.encoders.BASE64Decoder;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import com.bea.security.utils.gss.GSSTokenUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;
import weblogic.utils.Hex;

public class NegotiateTokenUtils {
   private static final int SEQUENCE = 48;
   private static final int OCTET_STRING = 4;
   private static final int BIT_STRING = 3;
   private static final int NEG_TOKEN_INIT = 160;
   private static final int NEG_TOKEN_TARG = 161;
   private static final int MECH_TYPE_LIST = 160;
   private static final int CONTEXT_FLAGS = 161;
   private static final int SUPPORTED_MECH = 161;
   private static final int RESPONSE_TOKEN = 162;
   private static final int MECH_TOKEN = 162;
   private static final int ACCEPT_COMPLETED = 0;
   private static final int ACCEPT_INCOMPLETE = 1;
   private static final byte[] NEGOTIATE_RESULT_ELEMENT = new byte[]{-96, 3, 10, 1};
   private static final byte[] SPNEGO_OID = new byte[]{6, 6, 43, 6, 1, 5, 5, 2};
   private static final byte[] MS_KERBEROS_OID = new byte[]{6, 9, 42, -122, 72, -126, -9, 18, 1, 2, 2};
   private static final int CONTEXT_FLAG_DELEG = 1;
   private static final int CONTEXT_FLAG_MUTUAL = 2;
   private static final int CONTEXT_FLAG_REPLAY = 4;
   private static final int CONTEXT_FLAG_SEQUENCE = 8;
   private static final int CONTEXT_FLAG_ANON = 16;
   private static final int CONTEXT_FLAG_CONF = 32;
   private static final int CONTEXT_FLAG_INTEG = 64;

   public static String base64Encode(byte[] theRawBytes) {
      return theRawBytes == null ? null : (new BASE64Encoder()).encodeBuffer(theRawBytes);
   }

   public static byte[] base64Decode(String theEncodedBytes, LoggerSpi log) {
      if (theEncodedBytes == null) {
         return null;
      } else {
         try {
            return (new BASE64Decoder()).decodeBuffer(theEncodedBytes);
         } catch (IOException var3) {
            if (log.isDebugEnabled()) {
               log.debug("base64 decode error.", var3);
            }

            return null;
         }
      }
   }

   public static String encodeNegTokenInit(byte[] krb5TokenData, LoggerSpi log) throws IOException {
      if (log.isDebugEnabled()) {
         log.debug("Encoding mech token element...");
      }

      byte[] derEncodedData = GSSTokenUtils.encodeData(4, krb5TokenData);
      derEncodedData = GSSTokenUtils.encodeData(162, derEncodedData);
      if (log.isDebugEnabled()) {
         log.debug("Context flag is not set, default value will be used.");
      }

      if (log.isDebugEnabled()) {
         log.debug("Encoding mech types element...");
      }

      ByteArrayOutputStream datastream = new ByteArrayOutputStream();
      datastream.write(160);
      GSSTokenUtils.encodeLength(datastream, MS_KERBEROS_OID.length + GSSTokenUtils.KERBEROS_V5_OID.length + 2);
      datastream.write(48);
      GSSTokenUtils.encodeLength(datastream, MS_KERBEROS_OID.length + GSSTokenUtils.KERBEROS_V5_OID.length);
      datastream.write(MS_KERBEROS_OID);
      datastream.write(GSSTokenUtils.KERBEROS_V5_OID);
      datastream.write(derEncodedData);
      derEncodedData = datastream.toByteArray();
      derEncodedData = GSSTokenUtils.encodeData(48, derEncodedData);
      if (log.isDebugEnabled()) {
         log.debug("Encoding NegTokenInit(0xa0)...");
      }

      derEncodedData = GSSTokenUtils.encodeData(160, derEncodedData);
      if (log.isDebugEnabled()) {
         log.debug("Encoding SPNEGO OID(0x06, 0x06, 0x2b, 0x06, 0x01, 0x05, 0x05, 0x02)...");
      }

      datastream = new ByteArrayOutputStream();
      datastream.write(SPNEGO_OID);
      datastream.write(derEncodedData);
      derEncodedData = datastream.toByteArray();
      if (log.isDebugEnabled()) {
         log.debug("Encoding Application Constructed Object(0x60)...");
      }

      derEncodedData = GSSTokenUtils.encodeData(96, derEncodedData);
      if (log.isDebugEnabled()) {
         log.debug("SPNEGO NegTokenInit \n" + Hex.dump(derEncodedData));
      }

      return base64Encode(derEncodedData);
   }

   public static String encodeNegTokenTarg(byte[] krb5TokenData, boolean acceptCompleted, LoggerSpi log) {
      if (krb5TokenData == null) {
         return null;
      } else {
         try {
            ByteArrayOutputStream datastream = new ByteArrayOutputStream();
            datastream.write(4);
            GSSTokenUtils.encodeLength(datastream, krb5TokenData.length);
            datastream.write(krb5TokenData);
            byte[] derEncodedData = datastream.toByteArray();
            if (log.isDebugEnabled()) {
               log.debug("Encoding the responseToken sequence element, encapsulated data length is: " + derEncodedData.length);
            }

            datastream = new ByteArrayOutputStream();
            datastream.write(162);
            GSSTokenUtils.encodeLength(datastream, derEncodedData.length);
            datastream.write(derEncodedData);
            derEncodedData = datastream.toByteArray();
            if (log.isDebugEnabled()) {
               log.debug("Encoding the supportedMech sequence element, encapsulated data length is: " + derEncodedData.length);
            }

            datastream = new ByteArrayOutputStream();
            datastream.write(161);
            GSSTokenUtils.encodeLength(datastream, MS_KERBEROS_OID.length);
            datastream.write(MS_KERBEROS_OID);
            datastream.write(derEncodedData);
            derEncodedData = datastream.toByteArray();
            if (log.isDebugEnabled()) {
               log.debug("Encoding the negResult, encapsulated data length is: " + derEncodedData.length);
            }

            datastream = new ByteArrayOutputStream();
            datastream.write(NEGOTIATE_RESULT_ELEMENT);
            if (acceptCompleted) {
               datastream.write(0);
            } else {
               datastream.write(1);
            }

            datastream.write(derEncodedData);
            derEncodedData = datastream.toByteArray();
            if (log.isDebugEnabled()) {
               log.debug("Encoding the constructed sequence, encapsulated data length is: " + derEncodedData.length);
            }

            datastream = new ByteArrayOutputStream();
            datastream.write(48);
            GSSTokenUtils.encodeLength(datastream, derEncodedData.length);
            datastream.write(derEncodedData);
            derEncodedData = datastream.toByteArray();
            if (log.isDebugEnabled()) {
               log.debug("Encoding the SPNEGO NegTokenTarg, encapsulated data length is: " + derEncodedData.length);
            }

            datastream = new ByteArrayOutputStream();
            datastream.write(161);
            GSSTokenUtils.encodeLength(datastream, derEncodedData.length);
            datastream.write(derEncodedData);
            derEncodedData = datastream.toByteArray();
            if (log.isDebugEnabled()) {
               log.debug("SPNEGO NegTokenTarg \n" + Hex.dump(derEncodedData));
            }

            return base64Encode(derEncodedData);
         } catch (IOException var5) {
            if (log.isDebugEnabled()) {
               log.debug("encode negotiate token target error!", var5);
            }

            if (log.isDebugEnabled()) {
               log.debug("Failed to encode into SPNEGO NegTokenTarg");
            }

            return null;
         }
      }
   }

   public static Object discriminate(byte[] spnegoToken, LoggerSpi log) {
      if (spnegoToken == null) {
         if (log.isDebugEnabled()) {
            log.debug("SPNEGONegotiateToken.discriminate: no bytes to discriminate, not SPNEGO");
         }

         return null;
      } else {
         ByteArrayInputStream is = new ByteArrayInputStream(spnegoToken);

         try {
            if (is.read() != 96) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: not Application Constructed Object, not SPNEGO NegTokenInit token");
               }

               Object var35 = null;
               return var35;
            }

            int len = GSSTokenUtils.decodeLength(is);
            byte[] spnegoOIDArray = new byte[SPNEGO_OID.length];
            Object var38;
            if (is.read(spnegoOIDArray, 0, SPNEGO_OID.length) == -1) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: OID array not found, not SPNEGO");
               }

               var38 = null;
               return var38;
            }

            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: SPNEGO static oid " + Hex.dump(SPNEGO_OID));
               log.debug("SPNEGONegotiateToken.discriminate: SPNEGO in oid " + Hex.dump(spnegoOIDArray));
            }

            if (!Arrays.equals(spnegoOIDArray, SPNEGO_OID)) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: SPNEGO OID not found, not SPNEGO");
               }

               var38 = null;
               return var38;
            }

            if (is.read() != 160) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Not a NegTokenInit");
               }

               var38 = null;
               return var38;
            }

            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: Neg token found");
            }

            len = GSSTokenUtils.decodeLength(is);
            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: len of neg token " + len);
            }

            if (is.read() != 48) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Invalid token format, sequence not found");
               }

               var38 = null;
               return var38;
            }

            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: sequence found");
            }

            len = GSSTokenUtils.decodeLength(is);
            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: len of sequence token " + len);
            }

            int choice = is.read();
            NegTokenInitInfo discrim;
            if (choice == -1) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Invalid token format, choice not found");
               }

               discrim = null;
               return discrim;
            }

            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: choice is " + choice);
            }

            ByteArrayInputStream ois;
            Oid oid;
            if (choice == 160) {
               len = GSSTokenUtils.decodeLength(is);
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: len of mech type " + len);
               }

               if (is.read() != 48) {
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Mech list invaid, not a sequence");
                  }

                  discrim = null;
                  return discrim;
               }

               len = GSSTokenUtils.decodeLength(is);
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: len of mech type seq " + len);
               }

               int offset = spnegoToken.length - is.available();
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: mech type offset " + offset);
               }

               ois = new ByteArrayInputStream(spnegoToken, offset, len);
               if (log.isDebugEnabled()) {
                  log.debug("mech type token \n" + Hex.dump(spnegoToken, offset, len));
               }

               while(true) {
                  if (ois.available() <= 0) {
                     is.skip((long)len);
                     choice = is.read();
                     if (choice == -1) {
                        if (log.isDebugEnabled()) {
                           log.debug("SPNEGONegotiateToken.discriminate: Invalid token format, invalid choice");
                        }

                        oid = null;
                        return oid;
                     }
                     break;
                  }

                  oid = new Oid(ois);
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Mech list oid " + oid);
                  }
               }
            }

            discrim = new NegTokenInitInfo();
            byte[] derValue;
            if (choice == 161) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Context flags");
               }

               len = GSSTokenUtils.decodeLength(is);
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: len of Context flags " + len);
               }

               if (is.read() != 3) {
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Context flags not a bit string");
                  }

                  ois = null;
                  return ois;
               }

               len = GSSTokenUtils.decodeLength(is);
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: len of big string value " + len);
               }

               derValue = new byte[len];
               if (is.read(derValue, 0, len) == -1) {
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Context flags not found");
                  }

                  oid = null;
                  return oid;
               }

               int choice = derValue[len - 1];
               if ((choice & 1) != 0) {
                  discrim.contextFlagDeleg = true;
               }

               if ((choice & 2) != 0) {
                  discrim.contextFlagMutual = true;
               }

               if ((choice & 4) != 0) {
                  discrim.contextFlagReplay = true;
               }

               if ((choice & 8) != 0) {
                  discrim.contextFlagSequence = true;
               }

               if ((choice & 16) != 0) {
                  discrim.contextFlagAnon = true;
               }

               if ((choice & 32) != 0) {
                  discrim.contextFlagConf = true;
               }

               if ((choice & 64) != 0) {
                  discrim.contextFlagInteg = true;
               }

               if (log.isDebugEnabled()) {
                  StringBuilder builder = new StringBuilder("SPNEGONegotiateToken.discriminate: Context flags DER value ");
                  builder.append(Hex.dump(derValue)).append(" Context flags( ").append(Hex.asHex(choice)).append(" -");
                  if (discrim.contextFlagDeleg) {
                     builder.append(" delegFlag ");
                  }

                  if (discrim.contextFlagMutual) {
                     builder.append(" mutualFlag ");
                  }

                  if (discrim.contextFlagReplay) {
                     builder.append(" replayFlag ");
                  }

                  if (discrim.contextFlagSequence) {
                     builder.append(" sequenceFlag ");
                  }

                  if (discrim.contextFlagAnon) {
                     builder.append(" anonFlag ");
                  }

                  if (discrim.contextFlagConf) {
                     builder.append(" confFlag ");
                  }

                  if (discrim.contextFlagInteg) {
                     builder.append(" integFlag ");
                  }

                  builder.append(")");
                  log.debug(builder.toString());
               }

               choice = is.read();
               if (choice == -1) {
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Invalid token format, invalid choice");
                  }

                  oid = null;
                  return oid;
               }
            }

            if (choice == 162) {
               len = GSSTokenUtils.decodeLength(is);
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Mech token len " + len);
               }

               if (is.read() != 4) {
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Mech token invalid, not octet string");
                  }

                  ois = null;
                  return ois;
               }

               len = GSSTokenUtils.decodeLength(is);
               derValue = new byte[len];
               if (is.read(derValue, 0, len) == -1) {
                  if (log.isDebugEnabled()) {
                     log.debug("SPNEGONegotiateToken.discriminate: Mech token invalid EOF");
                  }

                  oid = null;
                  return oid;
               }

               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Mech token \n" + Hex.dump(derValue));
               }

               discrim.mechToken = derValue;
               NegTokenInitInfo var41 = discrim;
               return var41;
            }
         } catch (GSSException var32) {
            if (log.isDebugEnabled()) {
               log.debug("SPNEGONegotiateToken.discriminate: Failure parsing token", var32);
            }

            Object var4 = null;
            return var4;
         } finally {
            try {
               is.close();
            } catch (IOException var31) {
               if (log.isDebugEnabled()) {
                  log.debug("SPNEGONegotiateToken.discriminate: Failure parsing token", var31);
               }
            }

         }

         if (log.isDebugEnabled()) {
            log.debug("SPNEGONegotiateToken.discriminate: No MechToken");
         }

         return null;
      }
   }

   public static class NegTokenInitInfo {
      public byte[] mechToken = null;
      public boolean contextFlagDeleg = false;
      public boolean contextFlagMutual = false;
      public boolean contextFlagReplay = false;
      public boolean contextFlagSequence = false;
      public boolean contextFlagAnon = false;
      public boolean contextFlagConf = false;
      public boolean contextFlagInteg = false;
   }
}
