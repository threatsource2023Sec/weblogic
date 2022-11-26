package com.octetstring.vde;

import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.nls.Messages;
import com.octetstring.vde.operation.AddOperation;
import com.octetstring.vde.operation.BindOperation;
import com.octetstring.vde.operation.CompareOperation;
import com.octetstring.vde.operation.DeleteOperation;
import com.octetstring.vde.operation.ModifyOperation;
import com.octetstring.vde.operation.RenameOperation;
import com.octetstring.vde.operation.SearchOperation;
import com.octetstring.vde.operation.StartTLSOperation;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DirectoryBindException;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ParseFilter;
import java.io.IOException;
import java.net.Socket;

public class MessageHandler {
   public static final DirectoryString ANON = new DirectoryString("cn=Anonymous");
   private Connection connection = null;

   public MessageHandler() {
   }

   public MessageHandler(Connection connection) throws IOException {
      this.setConnection(connection);
   }

   public Connection getConnection() {
      return this.connection;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public boolean answerRequest(LDAPMessage request) {
      this.connection.incrOpCount();
      switch (request.getProtocolOp().getSelector()) {
         case 0:
            this.connection.notifyok();
            return this.doBind(request);
         case 1:
         case 4:
         case 5:
         case 7:
         case 9:
         case 11:
         case 13:
         case 15:
         case 17:
         default:
            this.connection.notifyok();
            return false;
         case 2:
            this.connection.notifyok();
            String tmpstr = "op=" + String.valueOf(request.getMessageID().intValue() - 1) + " UNBIND";
            String tmpstr2 = "op=" + String.valueOf(request.getMessageID().intValue() - 1) + " fd=0 closed - U1";
            Logger.getInstance().alog(this.getConnection().getNumber(), tmpstr);
            Logger.getInstance().alog(this.getConnection().getNumber(), tmpstr2);
            return false;
         case 3:
            this.connection.notifyok();
            return this.doSearch(request);
         case 6:
            this.connection.notifyok();
            return this.doModify(request);
         case 8:
            this.connection.notifyok();
            return this.doAdd(request);
         case 10:
            this.connection.notifyok();
            return this.doDelete(request);
         case 12:
            this.connection.notifyok();
            return this.doRename(request);
         case 14:
            this.connection.notifyok();
            return this.doCompare(request);
         case 16:
            this.connection.notifyok();
            return true;
         case 18:
            return this.doExtended(request);
      }
   }

   public boolean doExtended(LDAPMessage request) {
      String oid = new String(request.getProtocolOp().getExtendedReq().getRequestName().toByteArray());
      if (oid.equals("1.3.6.1.4.1.1466.20037")) {
         StartTLSOperation sto = new StartTLSOperation(this.connection, request);

         try {
            sto.perform();
            this.sendResponse(sto.getResponse());
         } catch (DirectoryException var6) {
            Logger.getInstance().log(0, this, var6.getMessage());
            this.connection.notifyok();
            sto = null;
            request = null;
            return false;
         }

         try {
            Socket newsock = sto.getSocket();
            if (newsock != null) {
               this.connection.setClient(newsock);
               sto = null;
               request = null;
               return true;
            }
         } catch (IOException var5) {
         }

         sto = null;
      }

      request = null;
      this.connection.notifyok();
      return true;
   }

   public boolean doAdd(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " ADD dn=\"" + new String(request.getProtocolOp().getAddRequest().getEntry().toByteArray()) + "\" mem=" + Runtime.getRuntime().freeMemory() + "/" + Runtime.getRuntime().totalMemory());
      AddOperation addop = new AddOperation(this.connection.getAuthCred(), request);
      addop.perform();

      try {
         this.sendResponse(addop.getResponse());
         LDAPMessage lm = addop.getResponse();
         Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " RESULT err=" + lm.getProtocolOp().getAddResponse().getResultCode() + " tag=0 nentries=0 etime=0 mem=" + Runtime.getRuntime().freeMemory() + "/" + Runtime.getRuntime().totalMemory());
         lm = null;
         addop = null;
         request = null;
         return true;
      } catch (DirectoryException var5) {
         return false;
      }
   }

   public boolean doBind(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      DirectoryString user = this.getConnection().getAuthCred().getUser();
      if (user == null || user.toString().trim().length() == 0) {
         user = ANON;
      }

      DoSManager.getInstance().unregisterSubject(this.getConnection(), user);
      Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " BIND dn=\"" + new String(request.getProtocolOp().getBindRequest().getName().toByteArray()) + "\" method=" + request.getProtocolOp().getBindRequest().getAuthentication().getSelector() + " version=" + request.getProtocolOp().getBindRequest().getVersion());
      BindOperation bindop = new BindOperation(request);
      Credentials tmpCreds = this.getConnection().getAuthCred();
      if (tmpCreds != null) {
         bindop.setCreds(tmpCreds);
      }

      try {
         bindop.perform();
      } catch (DirectoryBindException var10) {
         try {
            this.sendResponse(bindop.getResponse());
            LDAPMessage lm = bindop.getResponse();
            Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " RESULT err=" + lm.getProtocolOp().getBindResponse().getResultCode() + " tag=0 nentries=0 etime=0");
            return false;
         } catch (DirectoryException var8) {
            return false;
         }
      }

      try {
         this.sendResponse(bindop.getResponse());
         LDAPMessage lm = bindop.getResponse();
         Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " RESULT err=" + lm.getProtocolOp().getBindResponse().getResultCode() + " tag=0 nentries=0 etime=0");
         lm = null;
         request = null;
      } catch (DirectoryException var9) {
         return false;
      }

      this.getConnection().setAuthCred(bindop.getCreds());
      boolean continueConnection = false;
      if (DoSManager.getInstance().registerSubject(this.getConnection())) {
         continueConnection = true;
      } else {
         continueConnection = false;
      }

      bindop = null;
      return continueConnection;
   }

   public boolean doCompare(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      CompareOperation compop = new CompareOperation(this.connection.getAuthCred(), request);
      if (Logger.getInstance().isLogable(7)) {
         Logger.getInstance().log(7, this, "Conn#" + this.getConnection().getNumber() + ": Performing Compare Op");
      }

      compop.perform();

      try {
         this.sendResponse(compop.getResponse());
         return true;
      } catch (DirectoryException var5) {
         return false;
      }
   }

   public boolean doDelete(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " DEL dn=\"" + new String(request.getProtocolOp().getDelRequest().toByteArray()) + "\"");
      DeleteOperation delop = new DeleteOperation(this.connection.getAuthCred(), request);
      delop.perform();

      try {
         this.sendResponse(delop.getResponse());
         LDAPMessage lm = delop.getResponse();
         Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " RESULT err=" + lm.getProtocolOp().getDelResponse().getResultCode() + " tag=0 nentries=0 etime=0");
         return true;
      } catch (DirectoryException var5) {
         return false;
      }
   }

   public boolean doModify(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " MOD dn=\"" + new String(request.getProtocolOp().getModifyRequest().getObject().toByteArray()) + "\"");
      ModifyOperation modop = new ModifyOperation(this.connection.getAuthCred(), request);
      modop.perform();

      try {
         this.sendResponse(modop.getResponse());
         LDAPMessage lm = modop.getResponse();
         Logger.getInstance().alog(this.getConnection().getNumber(), "op=" + opno + " RESULT err=" + lm.getProtocolOp().getModifyResponse().getResultCode() + " tag=0 nentries=0 etime=0");
         return true;
      } catch (DirectoryException var5) {
         return false;
      }
   }

   public boolean doRename(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      RenameOperation renop = new RenameOperation(this.connection.getAuthCred(), request);
      if (Logger.getInstance().isLogable(7)) {
         Logger.getInstance().log(7, this, "Conn#" + this.getConnection().getNumber() + ": Performing Rename Op");
      }

      renop.perform();

      try {
         this.sendResponse(renop.getResponse());
         return true;
      } catch (DirectoryException var5) {
         return false;
      }
   }

   public boolean doSearch(LDAPMessage request) {
      int opno = request.getMessageID().intValue() - 1;
      StringBuffer tmpstr = new StringBuffer();
      tmpstr.append("op=").append(String.valueOf(opno));
      tmpstr.append(" SRCH base=\"");
      tmpstr.append(new String(request.getProtocolOp().getSearchRequest().getBaseObject().toByteArray()));
      tmpstr.append("\" scope=");
      tmpstr.append(request.getProtocolOp().getSearchRequest().getScope());
      tmpstr.append(" filter=\"");
      tmpstr.append(ParseFilter.filterToString(request.getProtocolOp().getSearchRequest().getFilter()));
      tmpstr.append("\"");
      Logger.getInstance().alog(this.getConnection().getNumber(), tmpstr);
      SearchOperation searchop = new SearchOperation(this.connection.getAuthCred(), request);
      int rescount = 0;

      while(searchop.isMore()) {
         searchop.perform();

         try {
            this.sendResponse(searchop.getResponse());
            ++rescount;
         } catch (DirectoryException var7) {
            return false;
         }
      }

      --rescount;
      LDAPMessage lm = searchop.getResponse();
      if (lm.getProtocolOp().getSearchResDone() != null) {
         tmpstr = new StringBuffer();
         tmpstr.append("op=");
         tmpstr.append(String.valueOf(opno));
         tmpstr.append(" RESULT err=");
         tmpstr.append(lm.getProtocolOp().getSearchResDone().getResultCode());
         tmpstr.append(" tag=0 nentries=");
         tmpstr.append(String.valueOf(rescount));
         tmpstr.append(" etime=0");
         Logger.getInstance().alog(this.getConnection().getNumber(), tmpstr);
         lm = null;
         request = null;
         searchop = null;
      }

      return true;
   }

   public void reset() {
      this.getConnection().setAuthCred(new Credentials());
   }

   public void sendResponse(LDAPMessage response) throws DirectoryException {
      try {
         synchronized(this.connection) {
            this.getConnection().sendResponse(response);
         }
      } catch (Exception var5) {
         Logger.getInstance().log(3, this, Messages.getString("Transmission_to__48") + this.getConnection().getAuthCred().getIPAddress() + Messages.getString("_interrupted._49"));
         throw new DirectoryException(Messages.getString("Error_Communicating_with_Client___50") + var5);
      }
   }
}
