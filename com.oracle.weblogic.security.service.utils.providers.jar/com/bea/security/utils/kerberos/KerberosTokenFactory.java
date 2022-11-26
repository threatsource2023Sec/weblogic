package com.bea.security.utils.kerberos;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import com.bea.security.utils.gss.GSSTokenUtils;
import com.bea.security.utils.negotiate.CredentialObject;
import java.io.IOException;
import java.security.AccessControlContext;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.Set;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class KerberosTokenFactory {
   private String jaasConfigName;
   private LoggerSpi logger;
   private boolean isDebugEnabled;
   private GSSManager gssManager = GSSManager.getInstance();

   private KerberosTokenFactory(String jaasConfigName, LoggerSpi logger) {
      this.jaasConfigName = jaasConfigName;
      this.logger = logger;
      this.isDebugEnabled = logger != null && logger.isDebugEnabled();
   }

   public static KerberosTokenFactory getInstance(String jaasConfigName, LoggerSpi logger) {
      return new KerberosTokenFactory(jaasConfigName, logger);
   }

   public byte[] createGssInitContextToken(Subject initiator, String peerName) throws KerberosException {
      PasswordCredential passwordCred = null;
      CredentialObject delegatedCred = null;
      Set credentials = initiator.getPrivateCredentials();
      Iterator var6 = credentials.iterator();

      while(var6.hasNext()) {
         Object credential = var6.next();
         if (credential instanceof CredentialObject) {
            delegatedCred = (CredentialObject)credential;
            break;
         }

         if (credential instanceof PasswordCredential) {
            passwordCred = (PasswordCredential)credential;
         }
      }

      byte[] token = null;
      if (delegatedCred != null) {
         if (delegatedCred.getDelegatedSub() != null) {
            token = this.createGssInitContextTokenWithTGT(delegatedCred.getDelegatedSub(), peerName);
         } else {
            token = this.createGssInitContextTokenWithGSSCredential(delegatedCred.getCredential(), peerName);
         }
      } else if (passwordCred != null) {
         token = this.createGssInitContextTokenWithPasswordCredential(passwordCred, peerName);
      }

      return token;
   }

   public String createGssInitContextTokenBase64Encoded(Subject initiator, String peerName) throws KerberosException {
      byte[] token = this.createGssInitContextToken(initiator, peerName);
      return token == null ? null : (new BASE64Encoder()).encodeBuffer(token);
   }

   public byte[] createKrbApReqToken(Subject initiator, String peerName) throws KerberosException {
      byte[] token = this.createGssInitContextToken(initiator, peerName);

      try {
         return KerberosTokenUtils.getKrbApReqToken(token, this.logger);
      } catch (IOException var5) {
         throw new KerberosException("Failed to get KrbApReqToken from GSS InitContextToken", var5);
      }
   }

   public String createKrbApReqTokenBase64Encoded(Subject initiator, String peerName) throws KerberosException {
      byte[] token = this.createKrbApReqToken(initiator, peerName);
      return token == null ? null : (new BASE64Encoder()).encodeBuffer(token);
   }

   private byte[] createGssInitContextTokenWithTGT(Subject subject, String peerName) throws KerberosException {
      Set principals = subject.getPrincipals();
      if (principals.isEmpty()) {
         throw new KerberosException("Illegal subject: empty Principals");
      } else {
         final String username = ((Principal)principals.iterator().next()).getName();
         if (this.isDebugEnabled) {
            this.logger.debug("Begin acquire client credential for " + username + " ...");
         }

         GSSCredential delegateCre;
         try {
            delegateCre = (GSSCredential)Subject.doAsPrivileged(subject, new PrivilegedExceptionAction() {
               public GSSCredential run() throws GSSException {
                  GSSName clientGSSName = KerberosTokenFactory.this.gssManager.createName(username, GSSName.NT_USER_NAME);
                  Oid krb5Oid = new Oid(GSSTokenUtils.KERBEROS_V5_OID);
                  return KerberosTokenFactory.this.gssManager.createCredential(clientGSSName, 0, krb5Oid, 1);
               }
            }, (AccessControlContext)null);
         } catch (PrivilegedActionException var7) {
            if (this.isDebugEnabled) {
               this.logger.debug(var7.getMessage(), var7);
            }

            throw new KerberosException(var7);
         }

         if (this.isDebugEnabled) {
            this.logger.debug("Get client credential successfully! ");
         }

         return this.createGssInitContextTokenWithGSSCredential(delegateCre, peerName);
      }
   }

   private byte[] createGssInitContextTokenWithGSSCredential(GSSCredential credential, String peerName) throws KerberosException {
      if (this.isDebugEnabled) {
         this.logger.debug("client credential: " + credential);
      }

      GSSContext context = null;

      byte[] var7;
      try {
         GSSName serverName = this.gssManager.createName(peerName, GSSName.NT_HOSTBASED_SERVICE);
         Oid krb5Oid = new Oid(GSSTokenUtils.KERBEROS_V5_OID);
         context = this.gssManager.createContext(serverName, krb5Oid, credential, 0);
         context.requestMutualAuth(false);
         if (this.isDebugEnabled) {
            this.logger.debug("Initialize securirty context...");
         }

         byte[] token = new byte[0];
         token = context.initSecContext(token, 0, token.length);
         var7 = token;
      } catch (GSSException var16) {
         if (this.isDebugEnabled) {
            this.logger.debug(var16.getMessage(), var16);
         }

         throw new KerberosException(var16);
      } finally {
         if (context != null) {
            try {
               context.dispose();
            } catch (GSSException var15) {
            }
         }

      }

      return var7;
   }

   private byte[] createGssInitContextTokenWithPasswordCredential(PasswordCredential credential, String peerName) throws KerberosException {
      Subject sub;
      try {
         if (this.isDebugEnabled) {
            this.logger.debug("Begin login user: [" + credential.getUserName() + "]");
         }

         LoginContext lc = new LoginContext(this.jaasConfigName, new MyCallbackHandler(credential));
         lc.login();
         sub = lc.getSubject();
      } catch (LoginException var5) {
         if (this.isDebugEnabled) {
            this.logger.debug(var5.getMessage(), var5);
         }

         throw new KerberosException(var5);
      }

      if (this.isDebugEnabled) {
         this.logger.debug("Login successfully! subject: [" + sub + "]");
      }

      return this.createGssInitContextTokenWithTGT(sub, peerName);
   }

   private static class MyCallbackHandler implements CallbackHandler {
      String username;
      char[] password;

      private MyCallbackHandler(PasswordCredential cred) {
         this.username = null;
         this.password = null;
         this.username = cred.getUserName();
         this.password = cred.getPassword();
      }

      public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
         Callback[] var2 = callbacks;
         int var3 = callbacks.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Callback callback = var2[var4];
            if (callback instanceof NameCallback) {
               NameCallback nc = (NameCallback)callback;
               nc.setName(this.username);
            } else if (callback instanceof PasswordCallback) {
               PasswordCallback pc = (PasswordCallback)callback;
               pc.setPassword(this.password);
            }
         }

      }

      // $FF: synthetic method
      MyCallbackHandler(PasswordCredential x0, Object x1) {
         this(x0);
      }
   }
}
