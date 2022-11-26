package com.octetstring.vde;

import com.asn1c.codec.InputStreamDecoder;
import com.asn1c.codec.OutputStreamEncoder;
import com.asn1c.core.ASN1Exception;
import com.octetstring.ldapv3.ASN1BERDecoder;
import com.octetstring.ldapv3.ASN1BEREncoder;
import com.octetstring.ldapv3.ASN1Decoder;
import com.octetstring.ldapv3.ASN1Encoder;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.nls.Messages;
import com.octetstring.vde.util.Logger;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionHandler {
   private Connection con = null;
   private WorkQueue wq = null;
   private Socket soc = null;
   private boolean reset = false;
   private boolean ready = false;
   private ExternalExecutor executor = null;

   public ConnectionHandler(Connection con, WorkQueue wq) throws Exception {
      this.wq = wq;
      this.con = con;
      ASN1Decoder bde = new ASN1BERDecoder(new InputStreamDecoder(con.getBufIn()));
      ASN1Encoder ben = new ASN1BEREncoder(new OutputStreamEncoder(con.getBufOut()));
      con.setBERDecoder(bde);
      con.setBEREncoder(ben);
      con.setConnectionHandler(this);
   }

   public ConnectionHandler(Connection con, WorkQueue wq, ThreadGroup wg, String name) throws Exception {
      this.wq = wq;
      this.con = con;
      con.setConnectionHandler(this);
      this.initInputStream(con.getBufIn());
      con.setBEREncoder(new ASN1BEREncoder(new OutputStreamEncoder(con.getBufOut())));
   }

   private void reset() {
      ASN1Decoder bde = new ASN1BERDecoder(new InputStreamDecoder(this.con.getBufIn()));
      ASN1Encoder ben = new ASN1BEREncoder(new OutputStreamEncoder(this.con.getBufOut()));
      this.con.setBERDecoder(bde);
      this.con.setBEREncoder(ben);
      this.reset = false;
   }

   public synchronized void waitok() {
      if (this.ready) {
         this.ready = false;
      }
   }

   public synchronized void notifyok() {
      this.ready = true;
   }

   public synchronized void notifyreset() {
      this.reset = true;
   }

   private void initInputStream(BufferedInputStream bufIn) {
      ASN1Decoder bde = new ASN1BERDecoder(new InputStreamDecoder(bufIn));
      this.con.setBERDecoder(bde);
   }

   public boolean dispatch(InputStream is) throws Exception {
      InputStream is = is instanceof BufferedInputStream ? is : new BufferedInputStream(is);
      this.initInputStream((BufferedInputStream)is);
      this.con.setBufIn((BufferedInputStream)is);
      return this.consumeRequest();
   }

   private boolean consumeRequest() throws Exception {
      LDAPMessage request = null;

      try {
         request = this.con.getNextRequest();
      } catch (ASN1Exception var3) {
         if (var3.getMessage() != null && !var3.getMessage().equals(Messages.getString("Unexpected_end_of_input_data_1"))) {
            if (!this.con.isUnbound()) {
               Logger.getInstance().alog(this.con.getNumber(), "op=-1 fd=0 closed - B1");
            }
         } else {
            Logger.getInstance().log(7, this, "Corrupt BER Message: " + var3);
         }

         Logger.getInstance().printStackTrace(var3);
         throw var3;
      } catch (Exception var4) {
         Logger.getInstance().log(0, this, "Illegal BER Message received: " + var4.getClass().getName() + "-" + var4.getMessage());
         Logger.getInstance().printStackTrace(var4);
         if (!this.con.isUnbound()) {
            Logger.getInstance().alog(this.con.getNumber(), "op=-1 fd=0 closed - B1");
         }

         throw var4;
      }

      if (request != null) {
         if (this.executor != null) {
            this.executor.execute(new WorkQueueItem(this.con, request));
            return true;
         }

         this.wq.addItem(new WorkQueueItem(this.con, request));
      }

      return true;
   }

   public void setExternalExecutor(ExternalExecutor externalExecutor) {
      this.executor = externalExecutor;
   }

   public void run() {
      while(true) {
         try {
            if (this.consumeRequest()) {
               continue;
            }
         } catch (Exception var2) {
         }

         return;
      }
   }
}
