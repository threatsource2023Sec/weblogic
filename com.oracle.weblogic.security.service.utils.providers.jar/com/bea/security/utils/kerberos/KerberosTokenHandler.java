package com.bea.security.utils.kerberos;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.utils.encoders.BASE64Decoder;
import com.bea.security.utils.gss.GSSExceptionInfo;
import com.bea.security.utils.negotiate.CredentialObject;
import com.bea.security.utils.negotiate.NegotiateTokenUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.Subject;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import weblogic.utils.Hex;

public class KerberosTokenHandler {
   private static final String USE_GSS_NAME_PROP = "weblogic.security.krb5.useGSSName";
   private String username = null;
   private boolean moreRequired = true;
   boolean acceptCompleted = false;
   private byte[] outputToken = null;
   private CredentialObject delegatedCredential = null;
   private LoggerSpi logger;
   private boolean isDebugEnabled;
   private GSSManager gssManager = GSSManager.getInstance();
   private static boolean USE_GSS_NAME;

   public KerberosTokenHandler(LoggerSpi logger) {
      this.logger = logger;
      this.isDebugEnabled = logger != null && logger.isDebugEnabled();
   }

   public String getUsername() {
      return this.username;
   }

   public boolean isMoreRequired() {
      return this.moreRequired;
   }

   public boolean isAcceptCompleted() {
      return this.acceptCompleted;
   }

   public byte[] getOutputToken() {
      return this.outputToken;
   }

   public CredentialObject getDelegatedCredential() {
      return this.delegatedCredential;
   }

   public void acceptGssInitContextToken(NegotiateTokenUtils.NegTokenInitInfo tokenInfo) throws KerberosException {
      if (this.username == null) {
         if (tokenInfo != null && tokenInfo.mechToken != null && tokenInfo.mechToken.length >= 1) {
            this.acceptGssInitContextToken((GSSContext)null, tokenInfo.mechToken, tokenInfo.contextFlagMutual);
         } else {
            throw new IllegalArgumentException("Input token cannot be null or empty.");
         }
      }
   }

   public void acceptGssInitContextToken(String gssInitTokenBase64Encoded, boolean mutualAuth) throws KerberosException {
      if (this.username == null) {
         if (gssInitTokenBase64Encoded != null && gssInitTokenBase64Encoded.length() >= 1) {
            try {
               byte[] gssInitToken = (new BASE64Decoder()).decodeBuffer(gssInitTokenBase64Encoded);
               this.acceptGssInitContextToken((GSSContext)null, gssInitToken, mutualAuth);
            } catch (IOException var5) {
               String msg = var5.getMessage();
               if (this.isDebugEnabled) {
                  this.logger.debug(msg, var5);
               }

               throw new KerberosException(msg, var5);
            }
         } else {
            throw new IllegalArgumentException("Input token cannot be null or empty.");
         }
      }
   }

   public void acceptKrbApReqToken(String krbApReqTokenBase64Encoded, boolean mutualAuth) throws KerberosException {
      if (this.username == null) {
         if (krbApReqTokenBase64Encoded != null && krbApReqTokenBase64Encoded.length() >= 1) {
            try {
               byte[] KrbApReqToken = (new BASE64Decoder()).decodeBuffer(krbApReqTokenBase64Encoded);
               byte[] gssInitContextToken = KerberosTokenUtils.getGssInitContextToken(KrbApReqToken, this.logger);
               this.acceptGssInitContextToken((GSSContext)null, gssInitContextToken, mutualAuth);
            } catch (IOException var5) {
               String msg = var5.getMessage();
               if (this.isDebugEnabled) {
                  this.logger.debug("Base64 decoding error: " + msg, var5);
               }

               throw new KerberosException(msg, var5);
            }
         } else {
            throw new IllegalArgumentException("Input token cannot be null or empty.");
         }
      }
   }

   private void acceptGssInitContextToken(final GSSContext gssContext, final byte[] gssInitToken, final boolean mutualAuth) throws KerberosException {
      try {
         Subject.doAsPrivileged((Subject)null, new PrivilegedExceptionAction() {
            public Object run() throws KerberosException {
               KerberosTokenHandler.this.handleInitTokenForMultiKDC(gssContext, gssInitToken, mutualAuth);
               return null;
            }
         }, (AccessControlContext)null);
      } catch (PrivilegedActionException var6) {
         KerberosException kExc = (KerberosException)var6.getException();
         if (this.isDebugEnabled) {
            this.logger.debug("acceptGssInitContextToken failed", kExc);
         }

         throw kExc;
      }
   }

   private void handleInitTokenForMultiKDC(GSSContext gssContext, byte[] gssInitToken, boolean mutualAuth) throws KerberosException {
      String principalName = null;
      if (gssContext == null) {
         principalName = KerberosTokenUtils.extractServicePrincipalFromToken(gssInitToken, this.logger);
         if (principalName != null) {
            try {
               gssContext = KerberosTokenUtils.getAcceptGSSContextForService(this.gssManager, principalName, this.logger);
            } catch (Exception var23) {
               if (this.isDebugEnabled) {
                  this.logger.debug(var23.getMessage(), var23);
               }
            }
         }
      }

      if (gssContext != null) {
         this.acceptGssInitContextTokenInDoAs(gssContext, gssInitToken, mutualAuth);
      } else {
         List principals = KerberosTokenUtils.getConfigedPrincipals(this.logger);
         if (principals.size() == 0) {
            throw new KerberosException("There is no kerberos login module being configured in the system!");
         } else {
            KerberosException ex = new KerberosException("Failed to create GSSContext to accept token!");
            if (principals.contains(principalName)) {
               throw ex;
            } else {
               Iterator var7 = principals.iterator();

               while(true) {
                  if (var7.hasNext()) {
                     String principal = (String)var7.next();
                     if (this.isDebugEnabled) {
                        this.logger.debug("Trying to create GSSContext for principal:" + principal);
                     }

                     gssContext = KerberosTokenUtils.getAcceptGSSContextForService(this.gssManager, principal, this.logger);
                     if (gssContext == null) {
                        continue;
                     }

                     try {
                        this.acceptGssInitContextTokenInDoAs(gssContext, gssInitToken, mutualAuth);
                     } catch (KerberosException var21) {
                        ex = var21;
                        continue;
                     } finally {
                        try {
                           gssContext.dispose();
                        } catch (GSSException var19) {
                        }

                     }

                     return;
                  }

                  if (gssContext == null) {
                     try {
                        gssContext = this.gssManager.createContext((GSSCredential)null);
                        if (gssContext != null) {
                           this.acceptGssInitContextTokenInDoAs(gssContext, gssInitToken, mutualAuth);
                           return;
                        }
                     } catch (GSSException var20) {
                        if (this.isDebugEnabled) {
                           this.logger.debug(var20.getMessage(), var20);
                        }

                        ex = new KerberosException(var20.getMessage(), var20);
                     }
                  }

                  throw ex;
               }
            }
         }
      }
   }

   private void acceptGssInitContextTokenInDoAs(GSSContext gssContext, byte[] gssInitToken, boolean mutualAuth) throws KerberosException {
      try {
         byte[] token;
         try {
            token = gssContext.acceptSecContext(gssInitToken, 0, gssInitToken.length);
         } catch (NullPointerException var23) {
            if (this.isDebugEnabled) {
               this.logger.debug("NPE caught accepting the context, verify the JCE configuration is correct in java.security and the sun.security.jgss.SunProvider is configured");
            }

            throw new KerberosException(SecurityLogger.getUnableToAcceptKrbSecContext(), var23);
         }

         this.acceptCompleted = gssContext.isEstablished();
         if (this.isDebugEnabled) {
            this.logger.debug("gssContext isEstablished " + this.acceptCompleted);
         }

         this.outputToken = null;
         if (token != null) {
            if (this.isDebugEnabled) {
               this.logger.debug("Out token \n" + Hex.dump(token));
            }

            if (mutualAuth) {
               this.outputToken = token;
            }
         } else if (this.isDebugEnabled) {
            this.logger.debug("No Output token present");
         }

         if (this.acceptCompleted) {
            GSSName gssName = gssContext.getSrcName();
            String name = gssName.toString();
            if (this.isDebugEnabled) {
               this.logger.debug("GSS name is " + name);
            }

            int pos = -1;
            if (!USE_GSS_NAME) {
               pos = name.indexOf(64);
            }

            if (pos != -1) {
               this.username = name.substring(0, pos);
            } else {
               this.username = name;
            }

            if (this.isDebugEnabled) {
               this.logger.debug("User name is " + this.username);
            }

            this.moreRequired = false;
            if (gssContext.getCredDelegState()) {
               if (this.isDebugEnabled) {
                  this.logger.debug("delegate state is true, acquire delegated credential...");
               }

               GSSCredential delegateCre = gssContext.getDelegCred();

               try {
                  Class gssUtil = Class.forName("com.sun.security.jgss.GSSUtil");
                  Method createSubject = gssUtil.getMethod("createSubject", GSSName.class, GSSCredential.class);
                  Subject delegatedSubject = (Subject)createSubject.invoke((Object)null, gssName, delegateCre);
                  this.delegatedCredential = new CredentialObject(delegatedSubject);
               } catch (ClassNotFoundException var22) {
                  this.delegatedCredential = new CredentialObject(delegateCre);
               }
            } else if (this.isDebugEnabled) {
               this.logger.debug("delegate state is false, no delegated credential will be obtained.");
            }
         } else {
            this.moreRequired = true;
         }
      } catch (GSSException var24) {
         this.moreRequired = false;
         if (this.isDebugEnabled) {
            GSSExceptionInfo.logInterpretedFailureInfo(this.logger, var24);
         }

         throw new KerberosException(var24.getMessage(), var24);
      } catch (Exception var25) {
         this.moreRequired = false;
         String msg = var25.getMessage();
         if (this.isDebugEnabled) {
            this.logger.debug("Exception: " + msg, var25);
         }

         throw new KerberosException(msg, var25);
      } finally {
         if (gssContext != null) {
            try {
               gssContext.dispose();
            } catch (GSSException var21) {
            }
         }

      }

   }

   static {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            KerberosTokenHandler.USE_GSS_NAME = Boolean.getBoolean("weblogic.security.krb5.useGSSName");
            return null;
         }
      });
   }
}
