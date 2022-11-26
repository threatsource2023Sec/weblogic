package weblogic.i18n.tools;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import weblogic.i18n.Localizer;

public final class LogMessage extends BasicLogMessage implements MessageWithMethod {
   private Config cfg;
   public static final String attr_severity = "severity";
   public static final String attr_methodType = "methodtype";
   public static final String attr_stacktrace = "stacktrace";
   public static final String attr_messageResetPeriod = "messageResetPeriod";
   public static final String attr_diagnosticvolume = "diagnosticvolume";
   public static final String attr_loggable = "loggable";
   public static final String attr_exclude_partitioned = "exclude_partition";
   private String severity = null;
   private boolean stackTrace = true;
   private String methodType = "logger";
   private long messageResetPeriod = 0L;
   private String diagnosticvolume = "Off";
   private boolean loggable = false;
   private boolean excludePartition = false;

   public LogMessage(Config config) {
      this.cfg = config;
   }

   public String get(String att) {
      String attr = super.get(att);
      if (attr == null) {
         if (att.equals("severity")) {
            return this.severity;
         } else if (att.equals("methodtype")) {
            return this.methodType;
         } else if (att.equals("stacktrace")) {
            return String.valueOf(this.getStackTrace());
         } else if (att.equals("messageResetPeriod")) {
            return String.valueOf(this.getMessageResetPeriod());
         } else if (att.equals("diagnosticvolume")) {
            return this.diagnosticvolume;
         } else if (att.equals("loggable")) {
            return String.valueOf(this.getLoggable());
         } else {
            return att.equals("exclude_partition") ? String.valueOf(this.isExcludePartition()) : null;
         }
      } else {
         return attr;
      }
   }

   public String getSeverity() {
      return this.severity;
   }

   public String getAlias() {
      return this.methodName;
   }

   public String getMethodType() {
      return this.methodType;
   }

   public boolean getStackTrace() {
      return this.stackTrace;
   }

   public long getMessageResetPeriod() {
      return this.messageResetPeriod;
   }

   public String getDiagnosticVolume() {
      return this.diagnosticvolume;
   }

   public boolean getLoggable() {
      return this.loggable;
   }

   public String getPrefixedMessageId() {
      String id = this.messageid;
      MessageCatalog cat = (MessageCatalog)this.getParent();
      if (cat != null) {
         String prefix = cat.getPrefix();
         if (prefix != null && prefix.length() > 0) {
            id = prefix + "-" + id;
         }
      }

      return id;
   }

   public void set(String att, String val) throws NoSuchElementException {
      if (att.equals("severity")) {
         this.severity = val;
      } else if (att.equals("methodtype")) {
         this.setMethodType(val);
      } else if (att.equals("stacktrace")) {
         this.setStackTrace(val);
      } else if (att.equals("diagnosticvolume")) {
         this.setDiagnosticVolume(val);
      } else if (att.equals("messageResetPeriod")) {
         this.setMessageResetPeriod(val);
      } else if (att.equals("loggable")) {
         this.setLoggable(val);
      } else if (att.equals("exclude_partition")) {
         this.setExcludePartition(val);
      } else {
         super.set(att, val);
      }
   }

   private void setMethodType(String val) {
      this.methodType = val;
   }

   private void setStackTrace(String val) {
      this.stackTrace = val.equals("true");
   }

   private void setMessageResetPeriod(String val) {
      this.messageResetPeriod = Long.parseLong(val);
   }

   public void setStackTrace(boolean boolVal) {
      this.stackTrace = boolVal;
   }

   private void setDiagnosticVolume(String val) {
      this.diagnosticvolume = val;
   }

   private void setLoggable(String val) {
      this.loggable = val != null && val.equals("true");
   }

   public void setArguments(String[] args) {
      this.arguments = args;
   }

   public void setArguments(String args) {
      StringTokenizer tokenizer = new StringTokenizer(args, ",");
      this.arguments = new String[tokenizer.countTokens()];

      for(int i = 0; tokenizer.hasMoreTokens(); this.arguments[i++] = tokenizer.nextToken()) {
      }

   }

   public String validate() {
      String errMess = null;
      if (this.messageid == null) {
         errMess = "Error: null message id detected";
         this.cfg.warn(errMess);
      }

      if (this.idIsNumeric(this.messageid)) {
         int id = Integer.parseInt(this.messageid);
         boolean extendedServerRange = this.isExtendedServerRange(id);
         if (id > 999999 && !this.cfg.server) {
            errMess = "Error(" + id + "): Id must be <= " + 999999;
            this.cfg.warn(errMess);
         } else {
            int base = ((MessageCatalog)this.parent).getBaseid();
            int end = ((MessageCatalog)this.parent).getEndid();
            if (id >= base && id <= end) {
               if (this.cfg.server) {
                  boolean normalServerRange = this.isNormalServerRange(id);
                  if (!normalServerRange && !extendedServerRange) {
                     errMess = "Error(" + id + "): Id for server message must be less than " + 500000 + " or in the extended range [" + 1000000 + "-" + 9999999 + "]";
                     this.cfg.warn(errMess);
                  }
               } else if (id < 500000) {
                  errMess = "Error(" + id + "): Id for non-server message must be >= than " + 500000;
                  this.cfg.warn(errMess);
               }
            } else {
               errMess = "Error(" + id + "): Id " + id + " is out of range: [" + base + "," + end + "] specified in message catalog";
               if (!this.cfg.nowarn) {
                  this.cfg.warn(errMess);
               }
            }
         }

         if (this.messageid != null && this.messageid.length() < 6) {
            String format = extendedServerRange ? "0000000" : "000000";
            DecimalFormat df = new DecimalFormat(format);
            this.messageid = df.format((long)id);
         }
      } else {
         this.cfg.warn("Warning(" + this.messageid + "): non-numeric log message id");
      }

      if (this.severity == null) {
         errMess = "Error(" + this.messageid + "): severity must be non null";
         this.cfg.warn(errMess);
      } else {
         this.severity = this.severity.toLowerCase(Locale.US);
         boolean found = false;
         int i;
         if (!this.cfg.server) {
            for(i = 0; i < Localizer.NON_SERVER_SEVERITIES.length; ++i) {
               if (this.severity.equals(Localizer.NON_SERVER_SEVERITIES[i])) {
                  found = true;
                  break;
               }
            }
         } else {
            for(i = 0; i < Localizer.SERVER_SEVERITIES.length; ++i) {
               if (this.severity.equals(Localizer.SERVER_SEVERITIES[i])) {
                  found = true;
                  break;
               }
            }
         }

         if (!found) {
            errMess = "Error(" + this.messageid + "): Invalid severity - " + this.severity;
            this.cfg.warn(errMess);
         }
      }

      if (this.getMethodName() != null) {
         if (!this.methodType.equals("logger") && !this.methodType.equals("getter")) {
            errMess = "Error(" + this.messageid + "): methodtype must be logger or getter. <" + this.methodType + "> was found";
            this.cfg.warn(errMess);
         }

         try {
            this.validateMethod();
         } catch (MethodException var8) {
            errMess = "Error(" + this.messageid + "): " + var8.getMessage();
            this.cfg.warn(errMess);
         }
      } else {
         errMess = "Error(" + this.messageid + "): No method defined";
      }

      if (this.messageBody == null) {
         errMess = "Error(" + this.messageid + "): messagebody must be non null";
         this.cfg.warn(errMess);
         this.messageBody = new MessageBody("TBD");
      }

      if (this.messageDetail == null) {
         if (!this.cfg.nowarn) {
            this.cfg.inform("Warning(" + this.messageid + "): no messagedetail");
         }

         this.messageDetail = new MessageDetail("TBD");
      }

      if (this.cause == null) {
         if (!this.cfg.nowarn) {
            this.cfg.inform("Warning(" + this.messageid + "): no cause");
         }

         this.cause = new Cause("TBD");
      }

      if (this.action == null) {
         if (!this.cfg.nowarn) {
            this.cfg.inform("Warning(" + this.messageid + "): no action");
         }

         this.action = new Action("TBD");
      }

      try {
         this.messageBody.validateCdata();
         this.messageDetail.validateCdata();
         this.cause.validateCdata();
         this.action.validateCdata();
      } catch (CdataException var7) {
         errMess = "Error(" + this.messageid + "): " + var7.getMessage();
         this.cfg.warn(errMess);
      }

      if (errMess != null) {
         this.cfg.warn("Errors detected at line " + this.getLine() + ", column " + this.getColumn());
      }

      return errMess;
   }

   private boolean isNormalServerRange(int id) {
      return id >= 0 && id <= 500000;
   }

   private boolean isExtendedServerRange(int id) {
      return id >= 1000000 && id <= 9999999;
   }

   private boolean idIsNumeric(String id) {
      try {
         new Integer(id);
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
      this.datehash = this.makeDateHash();
      this.addAttributeToXML(xml, "datehash", this.datehash);
      if (this.severity != null) {
         this.addAttributeToXML(xml, "severity", this.severity);
      }

      if (this.method != null) {
         this.addAttributeToXML(xml, "method", this.method);
      }

      if (!this.methodType.equals("logger")) {
         this.addAttributeToXML(xml, "methodtype", this.methodType);
      }

      if (!this.stackTrace) {
         this.addAttributeToXML(xml, "stacktrace", String.valueOf(this.stackTrace));
      }

      if (!this.diagnosticvolume.equals("Off")) {
         this.addAttributeToXML(xml, "diagnosticvolume", this.diagnosticvolume);
      }

      if (this.retire) {
         this.addAttributeToXML(xml, "retired", String.valueOf(this.retire));
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

   public boolean isExcludePartition() {
      return true;
   }

   private void setExcludePartition(String value) {
      this.excludePartition = value != null && value.equals("true");
   }
}
