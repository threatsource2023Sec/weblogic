package com.trilead.ssh2.auth;

import com.trilead.ssh2.InteractiveCallback;
import com.trilead.ssh2.crypto.PEMDecoder;
import com.trilead.ssh2.packets.PacketServiceAccept;
import com.trilead.ssh2.packets.PacketServiceRequest;
import com.trilead.ssh2.packets.PacketUserauthBanner;
import com.trilead.ssh2.packets.PacketUserauthFailure;
import com.trilead.ssh2.packets.PacketUserauthInfoRequest;
import com.trilead.ssh2.packets.PacketUserauthInfoResponse;
import com.trilead.ssh2.packets.PacketUserauthRequestInteractive;
import com.trilead.ssh2.packets.PacketUserauthRequestNone;
import com.trilead.ssh2.packets.PacketUserauthRequestPassword;
import com.trilead.ssh2.packets.PacketUserauthRequestPublicKey;
import com.trilead.ssh2.packets.TypesWriter;
import com.trilead.ssh2.signature.DSAPrivateKey;
import com.trilead.ssh2.signature.DSASHA1Verify;
import com.trilead.ssh2.signature.DSASignature;
import com.trilead.ssh2.signature.RSAPrivateKey;
import com.trilead.ssh2.signature.RSASHA1Verify;
import com.trilead.ssh2.signature.RSASignature;
import com.trilead.ssh2.transport.MessageHandler;
import com.trilead.ssh2.transport.TransportManager;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.SecureRandom;
import java.util.Vector;

public class AuthenticationManager implements MessageHandler {
   TransportManager tm;
   Vector packets = new Vector();
   boolean connectionClosed = false;
   String banner;
   String[] remainingMethods = new String[0];
   boolean isPartialSuccess = false;
   boolean authenticated = false;
   boolean initDone = false;

   public AuthenticationManager(TransportManager tm) {
      this.tm = tm;
   }

   boolean methodPossible(String methName) {
      if (this.remainingMethods == null) {
         return false;
      } else {
         for(int i = 0; i < this.remainingMethods.length; ++i) {
            if (this.remainingMethods[i].compareTo(methName) == 0) {
               return true;
            }
         }

         return false;
      }
   }

   byte[] deQueue() throws IOException {
      synchronized(this.packets) {
         while(this.packets.size() == 0) {
            if (this.connectionClosed) {
               throw (IOException)(new IOException("The connection is closed.")).initCause(this.tm.getReasonClosedCause());
            }

            try {
               this.packets.wait();
            } catch (InterruptedException var4) {
               throw new InterruptedIOException();
            }
         }

         byte[] res = (byte[])((byte[])this.packets.firstElement());
         this.packets.removeElementAt(0);
         return res;
      }
   }

   byte[] getNextMessage() throws IOException {
      while(true) {
         byte[] msg = this.deQueue();
         if (msg[0] != 53) {
            return msg;
         }

         PacketUserauthBanner sb = new PacketUserauthBanner(msg, 0, msg.length);
         this.banner = sb.getBanner();
      }
   }

   public String[] getRemainingMethods(String user) throws IOException {
      this.initialize(user);
      return this.remainingMethods;
   }

   public boolean getPartialSuccess() {
      return this.isPartialSuccess;
   }

   private boolean initialize(String user) throws IOException {
      if (!this.initDone) {
         this.tm.registerMessageHandler(this, 0, 255);
         PacketServiceRequest sr = new PacketServiceRequest("ssh-userauth");
         this.tm.sendMessage(sr.getPayload());
         PacketUserauthRequestNone urn = new PacketUserauthRequestNone("ssh-connection", user);
         this.tm.sendMessage(urn.getPayload());
         byte[] msg = this.getNextMessage();
         new PacketServiceAccept(msg, 0, msg.length);
         msg = this.getNextMessage();
         this.initDone = true;
         if (msg[0] == 52) {
            this.authenticated = true;
            this.tm.removeMessageHandler(this, 0, 255);
            return true;
         } else if (msg[0] == 51) {
            PacketUserauthFailure puf = new PacketUserauthFailure(msg, 0, msg.length);
            this.remainingMethods = puf.getAuthThatCanContinue();
            this.isPartialSuccess = puf.isPartialSuccess();
            return false;
         } else {
            throw new IOException("Unexpected SSH message (type " + msg[0] + ")");
         }
      } else {
         return this.authenticated;
      }
   }

   public boolean authenticatePublicKey(String user, char[] PEMPrivateKey, String password, SecureRandom rnd) throws IOException {
      try {
         this.initialize(user);
         if (!this.methodPossible("publickey")) {
            throw new IOException("Authentication method publickey not supported by the server at this stage.");
         } else {
            Object key = PEMDecoder.decode(PEMPrivateKey, password);
            byte[] pk_enc;
            TypesWriter tw;
            byte[] msg;
            if (key instanceof DSAPrivateKey) {
               DSAPrivateKey pk = (DSAPrivateKey)key;
               pk_enc = DSASHA1Verify.encodeSSHDSAPublicKey(pk.getPublicKey());
               tw = new TypesWriter();
               msg = this.tm.getSessionIdentifier();
               tw.writeString(msg, 0, msg.length);
               tw.writeByte(50);
               tw.writeString(user);
               tw.writeString("ssh-connection");
               tw.writeString("publickey");
               tw.writeBoolean(true);
               tw.writeString("ssh-dss");
               tw.writeString(pk_enc, 0, pk_enc.length);
               byte[] msg = tw.getBytes();
               DSASignature ds = DSASHA1Verify.generateSignature(msg, pk, rnd);
               byte[] ds_enc = DSASHA1Verify.encodeSSHDSASignature(ds);
               PacketUserauthRequestPublicKey ua = new PacketUserauthRequestPublicKey("ssh-connection", user, "ssh-dss", pk_enc, ds_enc);
               this.tm.sendMessage(ua.getPayload());
            } else {
               if (!(key instanceof RSAPrivateKey)) {
                  throw new IOException("Unknown private key type returned by the PEM decoder.");
               }

               RSAPrivateKey pk = (RSAPrivateKey)key;
               pk_enc = RSASHA1Verify.encodeSSHRSAPublicKey(pk.getPublicKey());
               tw = new TypesWriter();
               msg = this.tm.getSessionIdentifier();
               tw.writeString(msg, 0, msg.length);
               tw.writeByte(50);
               tw.writeString(user);
               tw.writeString("ssh-connection");
               tw.writeString("publickey");
               tw.writeBoolean(true);
               tw.writeString("ssh-rsa");
               tw.writeString(pk_enc, 0, pk_enc.length);
               msg = tw.getBytes();
               RSASignature ds = RSASHA1Verify.generateSignature(msg, pk);
               byte[] rsa_sig_enc = RSASHA1Verify.encodeSSHRSASignature(ds);
               PacketUserauthRequestPublicKey ua = new PacketUserauthRequestPublicKey("ssh-connection", user, "ssh-rsa", pk_enc, rsa_sig_enc);
               this.tm.sendMessage(ua.getPayload());
            }

            byte[] ar = this.getNextMessage();
            if (ar[0] == 52) {
               this.authenticated = true;
               this.tm.removeMessageHandler(this, 0, 255);
               return true;
            } else if (ar[0] == 51) {
               PacketUserauthFailure puf = new PacketUserauthFailure(ar, 0, ar.length);
               this.remainingMethods = puf.getAuthThatCanContinue();
               this.isPartialSuccess = puf.isPartialSuccess();
               return false;
            } else {
               throw new IOException("Unexpected SSH message (type " + ar[0] + ")");
            }
         }
      } catch (IOException var14) {
         this.tm.close(var14, false);
         throw (IOException)(new IOException("Publickey authentication failed.")).initCause(var14);
      }
   }

   public boolean authenticateNone(String user) throws IOException {
      try {
         this.initialize(user);
         return this.authenticated;
      } catch (IOException var3) {
         this.tm.close(var3, false);
         throw (IOException)(new IOException("None authentication failed.")).initCause(var3);
      }
   }

   public boolean authenticatePassword(String user, String pass) throws IOException {
      try {
         this.initialize(user);
         if (!this.methodPossible("password")) {
            throw new IOException("Authentication method password not supported by the server at this stage.");
         } else {
            PacketUserauthRequestPassword ua = new PacketUserauthRequestPassword("ssh-connection", user, pass);
            this.tm.sendMessage(ua.getPayload());
            byte[] ar = this.getNextMessage();
            if (ar[0] == 52) {
               this.authenticated = true;
               this.tm.removeMessageHandler(this, 0, 255);
               return true;
            } else if (ar[0] == 51) {
               PacketUserauthFailure puf = new PacketUserauthFailure(ar, 0, ar.length);
               this.remainingMethods = puf.getAuthThatCanContinue();
               this.isPartialSuccess = puf.isPartialSuccess();
               return false;
            } else {
               throw new IOException("Unexpected SSH message (type " + ar[0] + ")");
            }
         }
      } catch (IOException var6) {
         this.tm.close(var6, false);
         throw (IOException)(new IOException("Password authentication failed.")).initCause(var6);
      }
   }

   public boolean authenticateInteractive(String user, String[] submethods, InteractiveCallback cb) throws IOException {
      try {
         this.initialize(user);
         if (!this.methodPossible("keyboard-interactive")) {
            throw new IOException("Authentication method keyboard-interactive not supported by the server at this stage.");
         } else {
            if (submethods == null) {
               submethods = new String[0];
            }

            PacketUserauthRequestInteractive ua = new PacketUserauthRequestInteractive("ssh-connection", user, submethods);
            this.tm.sendMessage(ua.getPayload());

            while(true) {
               byte[] ar = this.getNextMessage();
               if (ar[0] == 52) {
                  this.authenticated = true;
                  this.tm.removeMessageHandler(this, 0, 255);
                  return true;
               }

               if (ar[0] == 51) {
                  PacketUserauthFailure puf = new PacketUserauthFailure(ar, 0, ar.length);
                  this.remainingMethods = puf.getAuthThatCanContinue();
                  this.isPartialSuccess = puf.isPartialSuccess();
                  return false;
               }

               if (ar[0] != 60) {
                  throw new IOException("Unexpected SSH message (type " + ar[0] + ")");
               }

               PacketUserauthInfoRequest pui = new PacketUserauthInfoRequest(ar, 0, ar.length);

               String[] responses;
               try {
                  responses = cb.replyToChallenge(pui.getName(), pui.getInstruction(), pui.getNumPrompts(), pui.getPrompt(), pui.getEcho());
               } catch (Exception var9) {
                  throw (IOException)(new IOException("Exception in callback.")).initCause(var9);
               }

               if (responses == null) {
                  throw new IOException("Your callback may not return NULL!");
               }

               PacketUserauthInfoResponse puir = new PacketUserauthInfoResponse(responses);
               this.tm.sendMessage(puir.getPayload());
            }
         }
      } catch (IOException var10) {
         this.tm.close(var10, false);
         throw (IOException)(new IOException("Keyboard-interactive authentication failed.")).initCause(var10);
      }
   }

   public void handleMessage(byte[] msg, int msglen) throws IOException {
      synchronized(this.packets) {
         if (msg == null) {
            this.connectionClosed = true;
         } else {
            byte[] tmp = new byte[msglen];
            System.arraycopy(msg, 0, tmp, 0, msglen);
            this.packets.addElement(tmp);
         }

         this.packets.notifyAll();
         if (this.packets.size() > 5) {
            this.connectionClosed = true;
            throw new IOException("Error, peer is flooding us with authentication packets.");
         }
      }
   }
}
