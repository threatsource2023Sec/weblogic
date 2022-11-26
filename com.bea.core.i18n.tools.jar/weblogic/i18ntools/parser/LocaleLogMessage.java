package weblogic.i18ntools.parser;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import weblogic.i18n.Localizer;
import weblogic.i18n.tools.BasicLogMessage;
import weblogic.i18ntools.L10nLookup;
import weblogic.i18ntools.internal.I18nConfig;

public final class LocaleLogMessage extends BasicLogMessage {
   private I18nConfig cfg;
   private L10nParserTextFormatter fmt;
   private String l10nPackage;

   public LocaleLogMessage(I18nConfig cfg) {
      this.cfg = cfg;
      this.fmt = new L10nParserTextFormatter();
   }

   public void set(String att, String val) throws NoSuchElementException {
      if (att.equals("messageid")) {
         this.messageid = val;
      } else if (att.equals("datelastchanged")) {
         this.datelastchanged = val;
      }
   }

   public String getL10nPackage() {
      return this.l10nPackage;
   }

   public String validate(boolean validateResources) {
      String errMess = null;
      if (this.messageid == null) {
         errMess = this.fmt.nullId();
         this.cfg.warn(errMess);
      }

      if (this.idIsNumeric(this.messageid)) {
         int id = Integer.parseInt(this.messageid);
         int var10000 = this.messageid.length();
         I18nConfig var10001 = this.cfg;
         if (var10000 < 6) {
            DecimalFormat df = new DecimalFormat("000000");
            this.messageid = df.format((long)id);
         }

         if (validateResources) {
            L10nLookup l10n = L10nLookup.getL10n();
            Localizer lcl = null;

            try {
               String propVal = l10n.getProperty(this.messageid);
               if (propVal != null && propVal.endsWith("retired")) {
                  this.setRetired(true);
                  return null;
               }

               lcl = l10n.getLocalizer(id, Locale.getDefault());
               this.l10nPackage = lcl.getL10nPackage();
            } catch (MissingResourceException var7) {
               if (lcl == null) {
                  errMess = this.fmt.noDefaultDef(this.messageid);
                  this.cfg.warn(errMess);
               } else {
                  errMess = this.fmt.noPackageInfo(this.messageid);
                  this.cfg.warn(errMess);
               }
            }
         }
      } else {
         this.cfg.inform(this.fmt.nonNumericId(this.messageid));
      }

      if (this.messageBody == null) {
         errMess = this.fmt.nullBody(this.messageid);
         this.cfg.warn(errMess);
      }

      if (this.messageDetail == null) {
         this.cfg.inform(this.fmt.noDetail(this.messageid));
      } else {
         this.messageDetail.setCdata(this.fmt.TBD());
      }

      if (this.cause == null) {
         this.cfg.inform(this.fmt.noCause(this.messageid));
      } else {
         this.cause.setCdata(this.fmt.TBD());
      }

      if (this.action == null) {
         this.cfg.inform(this.fmt.noAction(this.messageid));
      } else {
         this.action.setCdata(this.fmt.TBD());
      }

      if (errMess != null) {
         this.cfg.warn(this.fmt.locator(this.getLine(), this.getColumn()));
      }

      return errMess;
   }

   private boolean idIsNumeric(String id) {
      try {
         Integer.parseInt(id);
         return true;
      } catch (NumberFormatException var3) {
         return false;
      }
   }

   public String toXML(boolean fmtCData) {
      StringBuffer xml = new StringBuffer(100);
      this.addCommentToXML(xml);
      this.startXML(xml, "logmessage");
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

      if (this.messageDetail != null) {
         if (fmtCData) {
            text = this.messageDetail.getCdata();
         } else {
            text = this.messageDetail.getOriginalCData();
         }

         this.addSubElementToXML(xml, "messagedetail", text, fmtCData);
      }

      if (this.cause != null) {
         if (fmtCData) {
            text = this.cause.getCdata();
         } else {
            text = this.cause.getOriginalCData();
         }

         this.addSubElementToXML(xml, "cause", text, fmtCData);
      }

      if (this.action != null) {
         if (fmtCData) {
            text = this.action.getCdata();
         } else {
            text = this.action.getOriginalCData();
         }

         this.addSubElementToXML(xml, "action", text, fmtCData);
      }

      this.endXML(xml, "logmessage");
      return xml.toString();
   }

   protected String makeDateHash() {
      String hashString = this.getDateLastChanged() + this.messageBody.getCdata() + this.messageDetail.getCdata() + this.cause.getCdata() + this.action.getCdata();
      return String.valueOf(hashString.hashCode());
   }
}
