package weblogic.i18ntools.parser;

import java.util.Date;
import weblogic.i18n.tools.BasicMessage;
import weblogic.i18ntools.internal.I18nConfig;

public final class LocaleMessage extends BasicMessage {
   private I18nConfig cfg;
   private L10nParserTextFormatter fmt;

   public LocaleMessage(I18nConfig cfg) {
      this.cfg = cfg;
      this.fmt = new L10nParserTextFormatter();
   }

   public void set(String att, String val) {
      if (att.equals("messageid")) {
         this.messageid = val;
      } else if (att.equals("datelastchanged")) {
         this.datelastchanged = val;
      }
   }

   public String validate(boolean validateResources) {
      String errMess = null;
      this.cfg.debug(this.fmt.validatingMsg());
      if (this.messageid == null) {
         errMess = this.fmt.nullId();
         this.cfg.warn(errMess);
      }

      if (this.messageBody == null) {
         errMess = this.fmt.nullBody(this.messageid);
         this.cfg.warn(errMess);
      }

      if (errMess != null) {
         this.cfg.warn(this.fmt.locator(this.getLine(), this.getColumn()));
      }

      return errMess;
   }

   public String toXML(boolean fmtCData) {
      StringBuffer xml = new StringBuffer();
      this.startXML(xml, "message");
      if (this.messageid != null) {
         this.addAttributeToXML(xml, "messageid", this.messageid);
      }

      if (this.datelastchanged == null) {
         this.datelastchanged = Long.toString((new Date()).getTime());
      }

      this.addAttributeToXML(xml, "datelastchanged", this.datelastchanged);
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
