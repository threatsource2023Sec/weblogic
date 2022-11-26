package weblogic.i18n.tools;

import java.util.Date;
import java.util.NoSuchElementException;

public final class Message extends BasicMessage implements MessageWithMethod {
   private Config cfg;

   public Message(Config config) {
      this.cfg = config;
   }

   public String get(String att) {
      String attr = super.get(att);
      if (attr == null && att.equals("method")) {
         attr = this.method;
      }

      return attr;
   }

   public void set(String att, String val) throws NoSuchElementException {
      super.set(att, val);
   }

   public String validate() {
      String errMess = null;
      this.cfg.debug("Validating message...");
      if (this.messageid == null) {
         errMess = "Error: messageid must be non null";
         this.cfg.warn(errMess);
      }

      if (this.getMethodName() != null) {
         try {
            this.validateMethod();
         } catch (MethodException var4) {
            errMess = "Error(" + this.messageid + "): " + var4.getMessage();
            this.cfg.warn(errMess);
         }
      }

      if (this.messageBody == null) {
         if (!this.cfg.nowarn) {
            this.cfg.inform("Warning(" + this.messageid + "): null messagebody");
         }

         this.messageBody = new MessageBody("");
      }

      try {
         this.messageBody.validateCdata();
      } catch (CdataException var3) {
         errMess = "Error(" + this.messageid + "): " + var3.getMessage();
         this.cfg.warn(errMess);
      }

      if (errMess != null) {
         this.cfg.warn("Errors detected at line " + this.getLine() + ", column " + this.getColumn());
      }

      return errMess;
   }

   public String toXML(boolean fmtCData) {
      StringBuffer xml = new StringBuffer(100);
      this.addCommentToXML(xml);
      this.startXML(xml, "message");
      if (this.messageid != null) {
         this.addAttributeToXML(xml, "messageid", this.messageid);
      }

      if (this.datelastchanged == null) {
         this.datelastchanged = Long.toString((new Date()).getTime());
      }

      this.datehash = this.makeDateHash();
      this.addAttributeToXML(xml, "datehash", this.datehash);
      this.addAttributeToXML(xml, "datelastchanged", this.datelastchanged);
      if (this.method != null) {
         this.addAttributeToXML(xml, "method", this.method);
      }

      this.endAttributeToXML(xml);
      String text = null;
      if (this.messageBody != null) {
         if (fmtCData) {
            text = this.messageBody.getCdata();
         } else {
            text = this.messageBody.getOriginalCData();
         }

         this.addSubElementToXML(xml, "messagebody", text, fmtCData);
      }

      this.endXML(xml, "message");
      return xml.toString();
   }

   protected String makeDateHash() {
      String hashString = this.getDateLastChanged() + this.messageBody.getCdata();
      return String.valueOf(hashString.hashCode());
   }
}
