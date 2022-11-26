package com.bea.security.utils.authentication;

import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.utils.NonEchoCmdlineReader;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import com.bea.security.utils.random.SecureRandomData;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

public final class PasswordHashUtility {
   private static final int DEFAULT_SALT_SIZE = 4;
   private static final String DEFAULT_ALGORITHM = "SHA-1";
   private static final String DEFAULT_ENCODING = "UTF-8";
   private PasswordHashAlgorithm hashAlg = null;
   private String salt = null;
   private String processed = null;
   private boolean allowPlaintext = false;

   private static Hashtable getAlgorithms() {
      return PasswordHashUtility.AlgorithmManagerHolder.manager.algorithms;
   }

   public PasswordHashUtility(String theAlgorithm) {
      if (theAlgorithm != null && theAlgorithm.trim().length() > 0) {
         this.hashAlg = this.getHashAlgorithm(theAlgorithm);
      }

   }

   public PasswordHashUtility(boolean allowPlaintextIn, String hashedPass) {
      this.allowPlaintext = allowPlaintextIn;
      this.processed = this.parsePassword(hashedPass);
   }

   public void clearData() {
      this.salt = null;
      this.processed = null;
      this.hashAlg = null;
   }

   public String getAlgorithm() {
      return this.hashAlg == null ? null : this.hashAlg.getName();
   }

   public boolean getIsSalted() {
      return this.salt != null;
   }

   public String hashUserPassword(String passIn, int saltSize) {
      if (this.hashAlg == null) {
         return passIn;
      } else {
         String saltStr = null;
         if (saltSize > 0) {
            byte[] someBytes = new byte[saltSize];
            SecureRandomData.getInstance().getRandomBytes(someBytes);
            saltStr = (new BASE64Encoder()).encodeBuffer(someBytes);
            if (saltStr.length() > saltSize) {
               saltStr = saltStr.substring(0, saltSize);
            }
         }

         String password = "{" + this.hashAlg.getName() + "}";
         if (saltStr != null) {
            password = password + saltStr;
         }

         password = password + this.convertPassword(passIn, saltStr);
         return password;
      }
   }

   public String hashPassword(String passIn, boolean allowPlaintext, boolean isSalted) {
      if (this.hashAlg == null) {
         if (allowPlaintext) {
            return passIn;
         } else {
            throw new PasswordHashException(ProvidersLogger.getPlaintextPasswordUsageRejected());
         }
      } else {
         return isSalted ? this.hashUserPassword(passIn, 4) : this.hashUserPassword(passIn, 0);
      }
   }

   public boolean comparePassword(String passToCompare) {
      if (passToCompare != null && this.processed != null) {
         String convertedPass = this.convertPassword(passToCompare, this.salt);
         return convertedPass != null && this.processed.equals(convertedPass);
      } else {
         return passToCompare == this.processed && this.allowPlaintext;
      }
   }

   private String parsePassword(String passInStr) {
      if (passInStr != null && passInStr.length() >= 1) {
         char[] passIn = passInStr.toCharArray();
         if (passIn[0] != '{') {
            if (this.allowPlaintext) {
               return passInStr;
            } else {
               throw new PasswordHashException(ProvidersLogger.getUnableParseHashedPassword());
            }
         } else {
            int i;
            for(i = 1; i < passIn.length && passIn[i] != '}'; ++i) {
            }

            if (i >= passIn.length) {
               if (this.allowPlaintext) {
                  return passInStr;
               } else {
                  throw new PasswordHashException(ProvidersLogger.getUnableParseHashedPassword());
               }
            } else {
               String algorithm = new String(passIn, 1, i - 1);
               int offset = i + 1;

               try {
                  this.hashAlg = this.getHashAlgorithm(algorithm);
               } catch (PasswordHashException var9) {
                  if (this.allowPlaintext) {
                     return passInStr;
                  }

                  throw var9;
               }

               if (this.hashAlg != null && this.hashAlg.getB64Size() != -1) {
                  int totalRemaining = passIn.length - offset;
                  int saltSize = totalRemaining - this.hashAlg.getB64Size();
                  if (saltSize < 0) {
                     this.hashAlg = null;
                     if (this.allowPlaintext) {
                        return passInStr;
                     } else {
                        throw new PasswordHashException(ProvidersLogger.getUnableParseHashedPassword());
                     }
                  } else {
                     if (saltSize > 0) {
                        this.salt = new String(passIn, offset, saltSize);
                        offset += saltSize;
                     }

                     char[] encodedPwdHashFromDB = new char[passIn.length - offset];
                     System.arraycopy(passIn, offset, encodedPwdHashFromDB, 0, passIn.length - offset);
                     return new String(encodedPwdHashFromDB);
                  }
               } else {
                  this.hashAlg = null;
                  if (this.allowPlaintext) {
                     return passInStr;
                  } else {
                     throw new PasswordHashException(ProvidersLogger.getUnableParseHashedPassword());
                  }
               }
            }
         }
      } else if (this.allowPlaintext) {
         return passInStr;
      } else {
         throw new PasswordHashException(ProvidersLogger.getPlaintextPasswordUsageRejected());
      }
   }

   private String convertPassword(String passToConvert, String salt) {
      if (this.hashAlg == null) {
         return passToConvert;
      } else {
         try {
            MessageDigest digest = this.hashAlg.getMessageDigestInstance();
            if (salt != null) {
               digest.update(salt.getBytes("UTF-8"));
            }

            digest.update(passToConvert.getBytes("UTF-8"));
            byte[] pwdHashFromUser = digest.digest();
            String encodedPwdHashFromUser = (new BASE64Encoder()).encodeBuffer(pwdHashFromUser);
            return encodedPwdHashFromUser;
         } catch (NoSuchAlgorithmException var6) {
            throw new PasswordHashException(ProvidersLogger.getHashAlgorithmNotFound(this.hashAlg.getName()), var6);
         } catch (UnsupportedEncodingException var7) {
            throw new PasswordHashException(ProvidersLogger.getUnablePasswordDigestUtf8Required(var7.getMessage()), var7);
         }
      }
   }

   private PasswordHashAlgorithm getHashAlgorithm(String algorithm) {
      if (algorithm == null) {
         return null;
      } else {
         algorithm = algorithm.toUpperCase();
         Hashtable algorithms = getAlgorithms();
         PasswordHashAlgorithm algInfo = (PasswordHashAlgorithm)algorithms.get(algorithm);
         if (algInfo == null) {
            try {
               algInfo = new PasswordHashAlgorithm(algorithm);
            } catch (NoSuchAlgorithmException var5) {
               throw new PasswordHashException(ProvidersLogger.getHashAlgorithmNotFound(algorithm), var5);
            }

            if (algInfo == null || algInfo.getHashSize() == -1) {
               throw new PasswordHashException(ProvidersLogger.getHashAlgorithmNotUsable(algorithm));
            }

            algorithms.put(algorithm, algInfo);
         }

         return algInfo;
      }
   }

   public static void main(String[] args) {
      String alg = "SHA-1";
      int saltSize = 4;
      String[] passwords = null;

      for(int i = 0; i < args.length; ++i) {
         if (args[i].equalsIgnoreCase("-a")) {
            ++i;
            if (i >= args.length) {
               usageAndExit(ProvidersLogger.getIllegalValue("algorithm", "null"));
            }

            alg = args[i];
         } else {
            if (!args[i].equalsIgnoreCase("-s")) {
               passwords = new String[args.length - i];
               System.arraycopy(args, i, passwords, 0, args.length - i);
               break;
            }

            ++i;
            if (i >= args.length) {
               usageAndExit(ProvidersLogger.getIllegalValue("salt size", "null"));
            }

            try {
               saltSize = Integer.parseInt(args[i]);
            } catch (Exception var9) {
               usageAndExit(ProvidersLogger.getIllegalValue("salt size", args[i]));
            }
         }
      }

      try {
         PasswordHashUtility utility = new PasswordHashUtility(alg);
         if (passwords == null || passwords.length <= 0) {
            NonEchoCmdlineReader nonEcho = new NonEchoCmdlineReader();
            String prompt = ProvidersLogger.getPasswordHashUtilityPrompt();
            String hint = prompt.substring(prompt.indexOf(93) + 1);

            String pw;
            do {
               pw = nonEcho.readAndConfirmPassword(hint);
               System.out.println(utility.hashUserPassword(pw, saltSize));
            } while(pw != null && !pw.equals("quit") && !pw.equals(""));

            return;
         }

         for(int i = 0; i < passwords.length; ++i) {
            System.out.println(utility.hashUserPassword(passwords[i], saltSize));
         }
      } catch (Exception var10) {
         usageAndExit(var10.getMessage());
      }

   }

   private static void usageAndExit(String msg) {
      if (msg != null) {
         System.err.println(msg);
      }

      String usage = ProvidersLogger.getPasswordHashUtilityUsage();
      int idx = usage.indexOf(93);
      System.err.println(usage.substring(idx + 1));
      System.exit(1);
   }

   private static class AlgorithmManager {
      public Hashtable algorithms = new Hashtable();

      public AlgorithmManager() {
         this.algorithms = new Hashtable();
         this.algorithms.put("SHA", new PasswordHashAlgorithm("SHA", "SHA-1", 20));
         this.algorithms.put("SSHA", new PasswordHashAlgorithm("SSHA", "SHA-1", 20));
         this.algorithms.put("SHA1", new PasswordHashAlgorithm("SHA1", "SHA-1", 20));
         this.algorithms.put("SHA-1", new PasswordHashAlgorithm("SHA-1", "SHA-1", 20));
         this.algorithms.put("MD5", new PasswordHashAlgorithm("MD5", "MD5", 16));
      }
   }

   private static class AlgorithmManagerHolder {
      public static final AlgorithmManager manager = new AlgorithmManager();
   }
}
