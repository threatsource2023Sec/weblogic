package com.octetstring.vde.operation;

import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.AuthenticationChoice;
import com.octetstring.ldapv3.BindResponse;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.ldapv3.SaslCredentials;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DirectoryBindException;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.PasswordEncryptor;
import com.octetstring.vde.util.ServerConfig;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class BindOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   boolean success = false;
   Credentials creds = null;
   int version = 2;
   private static final byte[] hexbytes = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
   private static final DirectoryString USERPASSWORD = new DirectoryString("userpassword");
   private static final DirectoryString EMPTY_DIRSTRING = new DirectoryString("");
   private static final OctetString EMPTY_OSTRING = new OctetString(new byte[0]);
   private static final Int8 VERSION_2 = new Int8("2");
   private static final Int8 VERSION_3 = new Int8("3");

   public BindOperation(LDAPMessage request) {
      this.request = request;
   }

   public Credentials getCreds() {
      return this.creds;
   }

   public void setCreds(Credentials newCreds) {
      this.creds = newCreds;
      if (Logger.getInstance().isLogable(7)) {
         Logger.getInstance().log(7, this, Messages.getString("Set_credentials_to___3") + this.creds.getUser());
      }

   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() throws DirectoryBindException {
      if (this.request.getProtocolOp().getBindRequest().getVersion() == VERSION_2) {
         this.creds.setLdap2(true);
      } else {
         this.creds.setLdap2(false);
      }

      this.response = new LDAPMessage();
      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      BindResponse bindResponse = new BindResponse();
      bindResponse.setResultCode(LDAPResult.SUCCESS);
      bindResponse.setMatchedDN(EMPTY_OSTRING);
      bindResponse.setErrorMessage(EMPTY_OSTRING);
      bindResponse.setServerSaslCreds(EMPTY_OSTRING);
      op.setBindResponse(bindResponse);
      this.response.setMessageID(this.request.getMessageID());
      this.response.setProtocolOp(op);
      if (this.request.getProtocolOp().getBindRequest() != null) {
         this.creds.setRoot(false);
         DirectoryString subject = null;
         if (this.request.getProtocolOp().getBindRequest().getName() != null) {
            subject = new DirectoryString(this.request.getProtocolOp().getBindRequest().getName().toByteArray());
         } else {
            subject = EMPTY_DIRSTRING;
         }

         if (subject.equals(EMPTY_DIRSTRING)) {
            this.creds.setUser(EMPTY_DIRSTRING);
            if (ServerConfig.getInstance().get("vde.allow.anonymous").equals("true")) {
               if (Logger.getInstance().isLogable(7)) {
                  Logger.getInstance().log(7, this, Messages.getString("Bound_as_Anonymous_4"));
               }
            } else {
               bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
            }

            return;
         }

         AuthenticationChoice ac = this.request.getProtocolOp().getBindRequest().getAuthentication();
         String saslMechanism;
         if (ac.getSelector() == 0) {
            byte[] pw = ac.getSimple().toByteArray();
            if (pw == null || pw.length == 0) {
               if (ServerConfig.getInstance().get("vde.allow.anonymous").equals("true")) {
                  this.creds.setUser(EMPTY_DIRSTRING);
                  return;
               }

               bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
               return;
            }

            if ((new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser"))).equals(subject)) {
               if (PasswordEncryptor.compare(pw, (String)ServerConfig.getInstance().get("vde.rootpw"))) {
                  this.creds.setUser(subject);
                  this.creds.setRoot(true);
                  return;
               }

               this.creds.setUser(EMPTY_DIRSTRING);
               bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
               return;
            }

            saslMechanism = BackendHandler.getInstance().getReplicaUser(subject);
            if (saslMechanism != null) {
               if (PasswordEncryptor.compare(pw, saslMechanism)) {
                  this.creds.setUser(subject);
               } else {
                  this.creds.setUser(EMPTY_DIRSTRING);
               }

               return;
            }

            if (BackendHandler.getInstance().doBind(subject)) {
               boolean res = BackendHandler.getInstance().bind(subject, new BinarySyntax(pw));
               if (res) {
                  this.creds.setUser(subject);
               } else {
                  this.creds.setUser(EMPTY_DIRSTRING);
                  bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
               }

               return;
            }

            Entry bindEnt = null;

            try {
               bindEnt = BackendHandler.getInstance().getByDN((DirectoryString)null, subject);
               bindEnt = BackendHandler.getInstance().map(bindEnt);
            } catch (DirectoryException var17) {
            }

            if (bindEnt != null && bindEnt.containsKey(USERPASSWORD)) {
               byte[] entryPaswordBytes = ((Syntax)bindEnt.get(USERPASSWORD).elementAt(0)).getValue();
               String entryPassword = PasswordEncryptor.getUTFString(entryPaswordBytes);
               if (PasswordEncryptor.compare(pw, entryPassword)) {
                  this.creds.setUser(subject);
               } else {
                  this.creds.setUser(EMPTY_DIRSTRING);
                  bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
               }
            } else {
               bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
            }
         } else if (ac.getSelector() == 1) {
            SaslCredentials sasl = (SaslCredentials)ac.getValue();
            saslMechanism = new String(sasl.getMechanism().toByteArray());
            if (saslMechanism.equalsIgnoreCase("CRAM-MD5")) {
               Credentials myCreds = this.getCreds();
               if (myCreds != null && myCreds.getSaslTmp() != null && myCreds.getSaslMech() != null && myCreds.getSaslMech().equalsIgnoreCase("CRAM-MD5")) {
                  Object dngarbage = myCreds.getSaslTmp();
                  if (dngarbage instanceof String) {
                     Entry bindEnt = null;
                     String entryPassword = null;
                     subject = new DirectoryString(myCreds.getSaslTmpDN());
                     if ((new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser"))).equals(subject)) {
                        entryPassword = (String)ServerConfig.getInstance().get("vde.rootpw");
                     } else {
                        entryPassword = BackendHandler.getInstance().getReplicaUser(subject);
                        if (entryPassword == null) {
                           try {
                              bindEnt = BackendHandler.getInstance().getByDN((DirectoryString)null, subject);
                              bindEnt = BackendHandler.getInstance().map(bindEnt);
                           } catch (DirectoryException var16) {
                           }

                           if (bindEnt != null && bindEnt.containsKey(USERPASSWORD)) {
                              entryPassword = new String(((Syntax)bindEnt.get(USERPASSWORD).elementAt(0)).getValue());
                           }
                        }
                     }

                     if (!entryPassword.startsWith("{")) {
                        try {
                           if ((new String(sasl.getCredentials().toByteArray())).equals("dn: " + subject + " " + HMAC_MD5(entryPassword, (String)dngarbage))) {
                              if (subject.equals(new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser")))) {
                                 this.creds.setRoot(true);
                              }

                              this.creds.setUser(subject);
                              this.creds.setSaslMech(myCreds.getSaslMech());
                              this.creds.setSaslTmp((Object)null);
                           } else {
                              this.creds.setUser(EMPTY_DIRSTRING);
                              this.creds.setSaslMech((String)null);
                              this.creds.setSaslTmp((Object)null);
                           }
                        } catch (NoSuchAlgorithmException var15) {
                           Logger.getInstance().log(0, this, Messages.getString("Missing_MD5_Capability_11"));
                        }
                     }
                  }
               } else {
                  this.creds.setUser(EMPTY_DIRSTRING);
                  this.creds.setSaslTmpDN(subject.toString());
                  long timestamp = (new Date()).getTime();
                  byte[] randbytes = new byte[6];
                  Random myRand = new Random();
                  myRand.nextBytes(randbytes);
                  String myHostname = null;

                  try {
                     myHostname = InetAddress.getLocalHost().getHostName();
                  } catch (UnknownHostException var14) {
                     myHostname = "localhost";
                  }

                  String nonce = new String("<" + randbytes + "." + timestamp + "@" + myHostname + ">");
                  bindResponse.setServerSaslCreds(new OctetString(nonce.getBytes()));
                  this.creds.setSaslMech(saslMechanism);
                  this.creds.setSaslTmp(nonce);
                  bindResponse.setResultCode(LDAPResult.SASL_BIND_IN_PROGRESS);
               }

               if (this.getCreds() == null) {
                  this.creds.setSaslMech((String)null);
                  this.creds.setSaslTmp((Object)null);
                  this.creds.setUser(EMPTY_DIRSTRING);
                  bindResponse.setResultCode(LDAPResult.INVALID_CREDENTIALS);
               }
            } else if (!saslMechanism.equals("EXTERNAL")) {
               bindResponse.setResultCode(LDAPResult.AUTH_METHOD_NOT_SUPPORTED);
            }
         } else {
            this.creds.setUser(EMPTY_DIRSTRING);
            bindResponse.setResultCode(LDAPResult.AUTH_METHOD_NOT_SUPPORTED);
         }
      }

   }

   public static String HMAC_MD5(String secret, String text) throws NoSuchAlgorithmException {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] ipad = new byte[64];
      byte[] opad = new byte[64];
      byte[] key = secret.getBytes();
      if (key.length > 64) {
         key = md5.digest(key);
      }

      int i;
      for(i = 0; i < key.length; ++i) {
         ipad[i] = (byte)(54 ^ key[i]);
         opad[i] = (byte)(92 ^ key[i]);
      }

      while(i < 64) {
         ipad[i] = 54;
         opad[i++] = 92;
      }

      md5.update(ipad);
      key = md5.digest(text.getBytes());
      md5.update(opad);
      key = md5.digest(key);
      return hexToString(key, false);
   }

   private static String hexToString(byte[] keybytes, boolean ignored) {
      StringBuffer keystring = new StringBuffer(64);
      keystring.append("0x");

      for(int i = 0; i < keybytes.length; ++i) {
         keystring.append(hexbytes[(keybytes[i] & 239) >> 4]);
         keystring.append(hexbytes[keybytes[i] & 15]);
      }

      return keystring.toString();
   }

   public int getVersion() {
      return this.version;
   }
}
