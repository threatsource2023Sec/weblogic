package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import weblogic.wtc.WTCLogger;

public final class atntd implements atn {
   atncredtd mycred;
   private static final int AP1Q_VERSION = 1;
   private static final int AP1P_VERSION = 1;
   private static final int AP2Q_VERSION = 1;
   private static final int AP2P_VERSION = 1;
   private static final int SALT_SIZE = 8;
   private static final int ITERATIONS = 5;
   static final String DEFAULT_PASSWORD = "DeFaUlT";
   static final int RESULT_OK = 0;
   static final int RESULT_FAIL = -1;

   public atntd() {
   }

   public atntd(TPINIT tpinfo, int timestamp) {
      this.mycred = new atncredtd(tpinfo, timestamp);
   }

   public atncred gssAcquireCred(String desired_name) throws EngineSecError {
      return this.mycred;
   }

   public atncred gssAcquireCred(String desired_name, String identity_proof) throws EngineSecError {
      if (!desired_name.equals(this.mycred.cred_usrname)) {
         throw new EngineSecError(-3003, "desired name (" + desired_name + ") does not match cred (" + this.mycred.cred_usrname + ")");
      } else {
         return this.mycred;
      }
   }

   public atncred gssAcquireCred(String desired_name, byte[] identity_proof) throws EngineSecError {
      if (!desired_name.equals(this.mycred.cred_usrname)) {
         throw new EngineSecError(-3003, "desired name (" + desired_name + ") does not match cred (" + this.mycred.cred_usrname + ")");
      } else {
         return this.mycred;
      }
   }

   private byte random_transform(byte b) {
      int i = b << 1;
      if ((i & 256) != 0) {
         i &= 255;
         ++i;
      }

      return (byte)i;
   }

   private int roundup4(int i) {
      return i + 3 & -4;
   }

   public atncontext gssGetContext(atncred claimant_cred_handle, String target_name) throws EngineSecError {
      return new atnctxtd((atncredtd)claimant_cred_handle);
   }

   public int gssInitSecContext(atncontext context, byte[] input_token, int input_token_size, byte[] output_token) throws EngineSecError {
      atnctxtd ctx = (atnctxtd)context;
      String dummy = null;
      char[] password = null;
      byte[] ciphertext = null;
      byte[] interum = null;
      byte[] interum2 = null;
      int lcv;
      byte[] salt;
      PBEKeySpec pbeKeySpec;
      PBEParameterSpec pbeParamSpec;
      SecretKeyFactory keyFac;
      SecretKey pbeKey;
      Cipher pbeCipher;
      String mypasswd;
      ByteArrayInputStream istream;
      DataInputStream decoder;
      int slcv;
      char[] password;
      byte[] ciphertext;
      byte[] interum;
      switch (ctx.context_state) {
         case 1:
            int needed_space = 40;
            if (output_token.length < needed_space) {
               throw new EngineSecError(-3005, needed_space);
            } else {
               try {
                  ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                  DataOutputStream encoder = new DataOutputStream(bstream);
                  encoder.writeInt(1);
                  encoder.writeInt(ctx.context_credential.cred_timestamp);
                  SecureRandom r = new SecureRandom();

                  for(lcv = 0; lcv < 8; ++lcv) {
                     int random_int = r.nextInt(256);
                     byte random_byte = (byte)random_int;
                     random_byte = this.random_transform(random_byte);
                     ctx.context_challenge[lcv] = random_byte;
                     encoder.writeInt(random_int);
                  }

                  interum = bstream.toByteArray();
                  salt = new byte[8];

                  for(slcv = 0; slcv < 8; ++slcv) {
                     salt[slcv] = 0;
                  }

                  pbeParamSpec = new PBEParameterSpec(salt, 5);
                  if (ctx.context_credential.cred_passwd == null) {
                     mypasswd = "DeFaUlT";
                  } else {
                     mypasswd = ctx.context_credential.cred_passwd;
                  }

                  password = mypasswd.toCharArray();
                  pbeKeySpec = new PBEKeySpec(password);
                  keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                  pbeKey = keyFac.generateSecret(pbeKeySpec);
                  pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
                  pbeCipher.init(1, pbeKey, pbeParamSpec);
                  ciphertext = pbeCipher.doFinal(interum);
                  if (ciphertext.length > output_token.length) {
                     throw new EngineSecError(-3005, ciphertext.length);
                  } else {
                     for(lcv = 0; lcv < ciphertext.length; ++lcv) {
                        output_token[lcv] = ciphertext[lcv];
                     }

                     ctx.context_state = 3;
                     return ciphertext.length;
                  }
               } catch (IOException var33) {
                  throw new EngineSecError(-3003, "IO error: " + var33);
               } catch (Exception var34) {
                  WTCLogger.logUEgssCryptoError1(var34.getMessage());
                  throw new EngineSecError(-3003, "Crypto error: " + var34);
               }
            }
         case 3:
            if (input_token != null && input_token_size > 0) {
               int needed_space = 152 + 4 * (ctx.context_credential.cred_proof == null ? 0 : ctx.context_credential.cred_proof.length) + 4 + 4 + 4 + 4 + 4;
               if (output_token.length < needed_space) {
                  throw new EngineSecError(-3005, needed_space);
               }

               try {
                  salt = new byte[8];

                  for(int slcv = 0; slcv < 8; ++slcv) {
                     salt[slcv] = 0;
                  }

                  pbeParamSpec = new PBEParameterSpec(salt, 5);
                  if (ctx.context_credential.cred_passwd == null) {
                     mypasswd = "DeFaUlT";
                  } else {
                     mypasswd = ctx.context_credential.cred_passwd;
                  }

                  password = mypasswd.toCharArray();
                  pbeKeySpec = new PBEKeySpec(password);
                  keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                  pbeKey = keyFac.generateSecret(pbeKeySpec);
                  pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
                  pbeCipher.init(2, pbeKey, pbeParamSpec);
                  byte[] interum2 = pbeCipher.doFinal(input_token, 0, input_token_size);
                  istream = new ByteArrayInputStream(interum2);
                  decoder = new DataInputStream(istream);
                  decoder.readInt();

                  for(lcv = 0; lcv < 8; ++lcv) {
                     if (decoder.readInt() != ctx.context_challenge[lcv]) {
                        WTCLogger.logErrorGssInvRetChallenge();
                        throw new EngineSecError(-3013, "Invalid return challenge");
                     }
                  }

                  byte[] ap1p_new_challenge = new byte[8];

                  for(lcv = 0; lcv < 8; ++lcv) {
                     ap1p_new_challenge[lcv] = (byte)decoder.readInt();
                     ap1p_new_challenge[lcv] = this.random_transform(ap1p_new_challenge[lcv]);
                  }

                  for(slcv = 0; slcv < 8; ++slcv) {
                     salt[slcv] = (byte)decoder.readInt();
                  }

                  ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                  DataOutputStream encoder = new DataOutputStream(bstream);
                  encoder.writeInt(1);

                  for(lcv = 0; lcv < 8; ++lcv) {
                     encoder.writeInt(ap1p_new_challenge[lcv]);
                  }

                  int plen;
                  for(plen = 0; plen < 3; ++plen) {
                     switch (plen) {
                        case 0:
                           dummy = ctx.context_credential.cred_usrname;
                           break;
                        case 1:
                           dummy = ctx.context_credential.cred_cltname;
                           break;
                        case 2:
                           dummy = ctx.context_credential.cred_grpname;
                     }

                     if (dummy == null) {
                        encoder.writeInt(0);
                     } else {
                        byte[] dummyBytes = Utilities.getEncBytes(dummy);
                        encoder.writeInt(dummyBytes.length);
                        encoder.write(dummyBytes);
                        int pad_bytes = this.roundup4(dummyBytes.length) - dummyBytes.length;

                        for(lcv = 0; lcv < pad_bytes; ++lcv) {
                           encoder.writeByte(0);
                        }
                     }
                  }

                  encoder.writeInt(ctx.context_credential.cred_flags);
                  if (ctx.context_credential.cred_proof == null) {
                     encoder.writeInt(0);
                  } else {
                     plen = ctx.context_credential.cred_proof.length;
                     encoder.writeInt(plen);

                     for(lcv = 0; lcv < plen; ++lcv) {
                        encoder.writeInt(ctx.context_credential.cred_proof[lcv]);
                     }
                  }

                  for(lcv = 0; lcv < 5; ++lcv) {
                     encoder.writeInt(0);
                  }

                  interum = bstream.toByteArray();
                  pbeParamSpec = new PBEParameterSpec(salt, 5);
                  if (ctx.context_credential.cred_passwd == null) {
                     mypasswd = "DeFaUlT";
                  } else {
                     mypasswd = ctx.context_credential.cred_passwd;
                  }

                  password = mypasswd.toCharArray();
                  pbeKeySpec = new PBEKeySpec(password);
                  keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                  pbeKey = keyFac.generateSecret(pbeKeySpec);
                  pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
                  pbeCipher.init(1, pbeKey, pbeParamSpec);
                  ciphertext = pbeCipher.doFinal(interum);
                  if (ciphertext.length > output_token.length) {
                     throw new EngineSecError(-3005, ciphertext.length);
                  }

                  for(lcv = 0; lcv < ciphertext.length; ++lcv) {
                     output_token[lcv] = ciphertext[lcv];
                  }

                  ctx.context_state = 6;
                  return ciphertext.length;
               } catch (IOException var31) {
                  WTCLogger.logIOEgssIOerror(var31.getMessage());
                  throw new EngineSecError(-3003, "IO error: " + var31);
               } catch (Exception var32) {
                  WTCLogger.logUEgssCryptoError2(var32.getMessage());
                  throw new EngineSecError(-3003, "Crypto error: " + var32);
               }
            }

            throw new EngineSecError(-3002, "Bad input token for init_sec_context");
         case 6:
            if (input_token != null && input_token_size > 0) {
               try {
                  istream = new ByteArrayInputStream(input_token, 0, input_token_size);
                  decoder = new DataInputStream(istream);
                  decoder.readInt();
                  int ap2p_result = decoder.readInt();
                  switch (ap2p_result) {
                     case -1:
                     default:
                        throw new EngineSecError(-3013, "Invalid return from server");
                     case 0:
                        return 0;
                  }
               } catch (IOException var30) {
                  throw new EngineSecError(-3003, "IO error: " + var30);
               }
            }

            throw new EngineSecError(-3002, "Bad input token for init_sec_context (phase 3)");
         default:
            return -1;
      }
   }

   public int gssAcceptSecContext(atncontext context, byte[] input_token, int input_token_size, byte[] output_token) throws EngineSecError {
      return 0;
   }

   public int getActualPDUSendSize() {
      return 0;
   }

   public int getEstimatedPDUSendSize(atncontext context) {
      return 0;
   }

   public int getEstimatedPDURecvSize(atncontext context) {
      return 0;
   }

   public int setSecurityType(int sec_type) {
      return 0;
   }

   public void setSrcName(String src) {
   }

   public void setDesiredName(String desired) {
   }

   public void setTargetName(String target) {
   }

   public void setApplicationPasswd(String app_pw) {
   }

   public void setLocalPasswd(String passwd) {
   }

   public void setRemotePasswd(String passwd) {
   }

   public int setInitiatorAddr(byte[] initiator) {
      return 0;
   }

   public int setAcceptorAddr(byte[] acceptor) {
      return 0;
   }

   public int setApplicationData(byte[] application) {
      return 0;
   }

   public void setMachineType(String mtype) {
   }
}
