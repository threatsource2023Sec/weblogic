package com.octetstring.vde.operation;

import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.ExtendedResponse;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.vde.Connection;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.Logger;
import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class StartTLSOperation implements Operation {
   private Connection connection = null;
   private LDAPMessage request = null;
   private LDAPMessage response = null;
   private SSLSocket newsock = null;
   private static final OctetString EMPTY_OSTRING = new OctetString();

   public StartTLSOperation(Connection connection, LDAPMessage request) {
      this.connection = connection;
      this.request = request;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() throws DirectoryException {
      Socket oldsock = this.connection.getClient();
      ExtendedResponse er = new ExtendedResponse();
      this.response = new LDAPMessage();
      this.response.setMessageID(this.request.getMessageID());
      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      op.setExtendedResp(er);
      this.response.setProtocolOp(op);
      er.setMatchedDN(EMPTY_OSTRING);
      er.setErrorMessage(EMPTY_OSTRING);
      if (oldsock == null) {
         er.setResultCode(LDAPResult.UNAVAILABLE);
      } else {
         er.setResultCode(LDAPResult.SUCCESS);
      }
   }

   public Socket getSocket() {
      Socket oldsock = this.connection.getClient();
      SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();

      try {
         this.newsock = (SSLSocket)sf.createSocket(oldsock, oldsock.getInetAddress().toString(), oldsock.getPort(), true);
         this.newsock.setNeedClientAuth(false);
         this.newsock.setUseClientMode(false);
         this.newsock.startHandshake();
      } catch (IOException var4) {
         Logger.getInstance().log(0, this, "Error Creating TLS Tunnel: " + var4.getMessage());
      }

      return this.newsock;
   }
}
