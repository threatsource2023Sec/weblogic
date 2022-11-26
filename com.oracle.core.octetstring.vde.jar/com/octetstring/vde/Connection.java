package com.octetstring.vde;

import com.asn1c.core.ASN1Exception;
import com.octetstring.ldapv3.ASN1Decoder;
import com.octetstring.ldapv3.ASN1Encoder;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.vde.util.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
   private Socket client = null;
   private boolean debug;
   private Credentials authCred = null;
   private int lastOp = 0;
   private boolean unbound = false;
   private ConnectionHandler ch = null;
   private BufferedInputStream bufIn = null;
   private BufferedOutputStream bufOut = null;
   private ASN1Encoder berEncoder = null;
   ASN1Decoder berDecoder = null;
   private int number = 0;

   public Credentials getAuthCred() {
      return this.authCred;
   }

   public void setAuthCred(Credentials newAuthCred) {
      this.authCred = newAuthCred;
   }

   public BufferedInputStream getBufIn() {
      return this.bufIn;
   }

   public BufferedOutputStream getBufOut() {
      return this.bufOut;
   }

   public void setBufOut(BufferedOutputStream newBufOut) {
      this.bufOut = newBufOut;
   }

   public void setBufIn(BufferedInputStream newBufIn) {
      this.bufIn = newBufIn;
   }

   public boolean isUnbound() {
      return this.unbound;
   }

   public void setUnbound(boolean unbound) {
      this.unbound = unbound;
      DoSManager.getInstance().unregisterConnection(this);
   }

   public Socket getClient() {
      return this.client;
   }

   public void setClient(Socket client) throws IOException {
      boolean reset = false;
      if (this.client != null) {
         reset = true;
      }

      this.client = client;
      this.bufOut = new BufferedOutputStream(client.getOutputStream());
      this.bufIn = new BufferedInputStream(client.getInputStream());
      if (reset && this.ch != null) {
         this.ch.notifyreset();
      }

   }

   public boolean getDebug() {
      return this.debug;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public int getLastOp() {
      return this.lastOp;
   }

   public void setLastOp(int lastOp) {
      this.lastOp = lastOp;
   }

   public synchronized void incrOpCount() {
      ++this.lastOp;
      if (DoSManager.getInstance().isOpExceeded(this)) {
         this.close();
      }

   }

   public int getNumber() {
      return this.number;
   }

   public void setNumber(int number) {
      this.number = number;
   }

   public int available() {
      if (this.bufIn != null) {
         try {
            return this.bufIn.available();
         } catch (IOException var2) {
         }
      }

      return 0;
   }

   public void close() {
      try {
         this.getClient().close();
      } catch (IOException var2) {
         Logger.getInstance().log(7, this, var2.toString());
      }

   }

   public ASN1Decoder getBERDecoder() {
      return this.berDecoder;
   }

   public ASN1Encoder getBEREncoder() {
      return this.berEncoder;
   }

   public LDAPMessage getNextRequest() throws ASN1Exception, IOException {
      return this.getBERDecoder().readLDAPMessage();
   }

   public void sendResponse(LDAPMessage response) throws Exception {
      this.getBEREncoder().writeLDAPMessage(response);
      this.bufOut.flush();
   }

   public void setBERDecoder(ASN1Decoder berDecoder) {
      this.berDecoder = berDecoder;
   }

   public void setBEREncoder(ASN1Encoder berEncoder) {
      this.berEncoder = berEncoder;
   }

   public void setConnectionHandler(ConnectionHandler ch) {
      this.ch = ch;
   }

   public ConnectionHandler getConnectionHandler() {
      return this.ch;
   }

   public void notifyok() {
      if (this.ch != null) {
         this.ch.notifyok();
      }

   }
}
