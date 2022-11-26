package weblogic.i18n.tools;

import java.util.Enumeration;
import java.util.HashSet;
import weblogic.utils.PlatformConstants;

public final class MessageCatalog extends BasicMessageCatalog {
   private static final String i18n_package_DEFAULT = "weblogic.i18n";
   private static final String l10n_package_DEFAULT = "weblogic.i18n";
   public static final String BEA_PREFIX = "BEA";
   public static final String WEBLOGIC_PREFIX = "WL";
   public static final String attr_i18n_package = "i18n_package";
   public static final String attr_l10n_package = "l10n_package";
   public static final String attr_subsystem = "subsystem";
   public static final String attr_version = "version";
   public static final String attr_baseid = "baseid";
   public static final String attr_endid = "endid";
   public static final String attr_filename = "filename";
   public static final String attr_loggables = "loggables";
   public static final String attr_prefix = "prefix";
   public static final String attr_description = "description";
   static final String[] attrNames = new String[]{"i18n_package", "l10n_package", "subsystem", "version", "baseid", "endid", "filename", "loggables", "prefix", "description"};
   private String i18n_package = "weblogic.i18n";
   private String l10n_package = "weblogic.i18n";
   private String subsystem = null;
   private String baseid = null;
   private String endid = null;
   private String prefix = null;
   private int intBaseid;
   private int intEndid;
   private boolean loggables = false;
   private String description = null;
   private int numMethods = 0;
   private String fullName = null;
   private HashSet logSetIds = new HashSet();
   private HashSet logSetMethodnames = new HashSet();
   HashSet msgIds = new HashSet();

   public String getPrefix() {
      return this.prefix;
   }

   public void setPrefix(String p) {
      this.prefix = p;
   }

   public int getBaseid() {
      return this.intBaseid;
   }

   public int getEndid() {
      return this.intEndid;
   }

   public boolean getLoggables() {
      return this.loggables;
   }

   private void setLoggables(String val) {
      this.loggables = val.equals("true");
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String desc) {
      this.description = desc;
   }

   public int getNumMethods() {
      return this.numMethods;
   }

   public MessageCatalog(Config config) {
      this.cfg = config;
   }

   public String[] getAttributeNames() {
      return attrNames;
   }

   public String[] getAttributes() {
      String[] attributes = new String[attrNames.length];

      for(int i = 0; i < attrNames.length; ++i) {
         attributes[i] = this.get(attrNames[i]);
      }

      return attributes;
   }

   public String get(String att) {
      if (att.equals("i18n_package")) {
         return this.i18n_package;
      } else if (att.equals("l10n_package")) {
         return this.l10n_package;
      } else if (att.equals("subsystem")) {
         return this.subsystem;
      } else if (att.equals("version")) {
         return this.version;
      } else if (att.equals("baseid")) {
         return this.baseid;
      } else if (att.equals("endid")) {
         return this.endid;
      } else if (att.equals("filename")) {
         return this.filename;
      } else if (att.equals("loggables")) {
         return String.valueOf(this.getLoggables());
      } else if (att.equals("prefix")) {
         return this.getPrefix();
      } else {
         return att.equals("description") ? this.getDescription() : null;
      }
   }

   public String getFullName() {
      return this.fullName;
   }

   public void setFullName(String name) {
      this.fullName = name;
   }

   public String getPackage() {
      return this.i18n_package;
   }

   public String getI18nPackage() {
      return this.getPackage();
   }

   public String getL10nPackage() {
      return this.l10n_package;
   }

   public String getSubsystem() {
      return this.subsystem;
   }

   public void set(String att, String val) {
      if (att.equals("i18n_package")) {
         this.i18n_package = val;
      } else if (att.equals("l10n_package")) {
         this.l10n_package = val;
      } else if (att.equals("subsystem")) {
         this.subsystem = val;
      } else if (att.equals("version")) {
         this.version = val;
      } else if (att.equals("baseid")) {
         this.baseid = val;
         this.intBaseid = Integer.parseInt(this.baseid);
      } else if (att.equals("endid")) {
         this.endid = val;
         this.intEndid = Integer.parseInt(this.endid);
      } else if (att.equals("filename")) {
         this.filename = val;
      } else if (att.equals("loggables")) {
         this.setLoggables(val);
      } else if (att.equals("prefix")) {
         this.setPrefix(val);
      } else if (att.equals("description")) {
         this.setDescription(val);
      }
   }

   public void changeLogMessage(LogMessage logMsg, String oldId) throws MessageNotFoundException, WrongTypeException {
      if (!this.verifyMessageCatalog(2)) {
         throw new WrongTypeException("changeLogMessage was called on a non-LOG MessageCatalog");
      } else {
         int el = 0;

         int len;
         for(len = this.logMessages.size(); el < len; ++el) {
            LogMessage msg = (LogMessage)this.logMessages.elementAt(el);
            if (msg.get("messageid").equals(oldId)) {
               break;
            }
         }

         if (el >= len) {
            throw new MessageNotFoundException("Message to update not in log messages");
         } else {
            this.logMessages.setElementAt(logMsg, el);
         }
      }
   }

   public void addLogMessage(LogMessage logMsg) throws WrongTypeException, DuplicateElementException {
      if (!this.verifyMessageCatalog(2)) {
         throw new WrongTypeException("addLogMessage was called on a non-LOG MessageCatalog");
      } else {
         String mid = logMsg.getMessageId();
         if (!this.logSetIds.add(mid)) {
            throw new DuplicateElementException("Duplicate message id: " + mid + " in catalog of subsystem " + this.subsystem);
         } else {
            String alias = logMsg.getMethodName();
            if (alias != null && alias.length() > 0 && !this.logSetMethodnames.add(alias)) {
               throw new DuplicateElementException("Duplicate method name: " + alias + " in catalog of subsystem " + this.subsystem);
            } else {
               this.logMessages.add(logMsg);
               if (this.cfg.isDebug()) {
                  Enumeration e = this.logMessages.elements();
                  this.cfg.debug("LogMessages vector:");

                  while(e.hasMoreElements()) {
                     LogMessage l = (LogMessage)e.nextElement();
                     this.cfg.debug("- message " + l.get("messageid"));
                  }
               }

            }
         }
      }
   }

   public void addException(ExceptionMessage ex) {
   }

   public ExceptionMessage findExceptionMessage(String id) {
      return null;
   }

   public void changeMessage(Message simpleMsg, String oldId) throws MessageNotFoundException, WrongTypeException {
      if (!this.verifyMessageCatalog(1)) {
         throw new WrongTypeException("changeMessage was called on a non-SIMPLE MessageCatalog");
      } else {
         int el = 0;

         int len;
         for(len = this.messages.size(); el < len; ++el) {
            Message msg = (Message)this.messages.elementAt(el);
            if (msg.get("messageid").equals(oldId)) {
               break;
            }
         }

         if (el >= len) {
            throw new MessageNotFoundException("Message to update not in messages");
         } else {
            this.messages.setElementAt(simpleMsg, el);
         }
      }
   }

   public void addMessage(Message msg) throws WrongTypeException, DuplicateElementException {
      if (!this.verifyMessageCatalog(1)) {
         throw new WrongTypeException("addMessage was called on a non-SIMPLE MessageCatalog");
      } else {
         String mid = msg.getMessageId();
         if (!this.msgIds.add(mid)) {
            throw new DuplicateElementException("Duplicate message id: " + mid + " in catalog of subsystem " + this.subsystem);
         } else {
            this.messages.add(msg);
            if (this.cfg.verbose) {
               Enumeration e = this.messages.elements();
               this.cfg.debug("Messages vector:");

               while(e.hasMoreElements()) {
                  Message m = (Message)e.nextElement();
                  this.cfg.debug("- message " + m.getMessageId());
               }
            }

         }
      }
   }

   public String validate() {
      boolean reported = false;
      String errMess = null;
      Enumeration e;
      Object o;
      String logErrMess;
      if (this.catType == 2) {
         this.cfg.debug("Validating log message catalog...");
         if (this.i18n_package == null) {
            errMess = "Error(" + this.filename + "): i18n_package must be non null";
            this.cfg.warn(errMess);
         } else {
            errMess = this.checkI18nPackageName();
         }

         if (this.l10n_package == null) {
            errMess = "Error(" + this.filename + "): l10n_package must be non null";
            this.cfg.warn(errMess);
         } else {
            errMess = this.checkL10nPackageName();
         }

         if (this.subsystem == null) {
            errMess = "Error(" + this.filename + "): subsystem must be non null";
            this.cfg.warn(errMess);
         }

         if (this.version == null) {
            errMess = "Error(" + this.filename + "): version must be non null";
            this.cfg.warn(errMess);
         }

         if (this.baseid == null) {
            this.cfg.warn("Warning(" + this.filename + "): baseid not specified");
            this.baseid = "000000";
            this.intBaseid = 0;
         }

         if (this.endid == null) {
            this.cfg.warn("Warning(" + this.filename + "): endid not specified");
            this.endid = "999999";
            this.intEndid = 999999;
         }

         if (this.cfg.isServer()) {
            if (this.prefix != null) {
               if (this.prefix.equals("BEA")) {
                  this.cfg.warn("Warning(" + this.filename + "): BEA prefix is being deprecated in catalogs for Server. Specify WL as the prefix value in the catalog.");
               }
            } else {
               this.setPrefix("WL");
            }
         }

         if (this.logMessages != null && this.logMessages.size() != 0) {
            e = this.logMessages.elements();

            while(e.hasMoreElements()) {
               o = e.nextElement();
               if (o instanceof LogMessage) {
                  LogMessage lm = (LogMessage)o;
                  logErrMess = lm.validate();
                  if (logErrMess != null) {
                     errMess = logErrMess;
                     reported = true;
                     break;
                  }
               }
            }
         } else {
            this.cfg.warn("Warning(" + this.filename + "): no messages defined");
         }

         if (errMess != null && !reported) {
            this.cfg.warn("Errors detected at line " + this.getLine() + ", column " + this.getColumn());
         }

         return errMess;
      } else if (this.catType == 1) {
         this.cfg.debug("Validating simple message catalog...");
         if (this.i18n_package == null) {
            errMess = "Error(" + this.filename + "): i18n_package must be non null";
            this.cfg.warn(errMess);
         } else {
            errMess = this.checkI18nPackageName();
         }

         if (this.l10n_package == null) {
            errMess = "Error(" + this.filename + "): l10n_package must be non null";
            this.cfg.warn(errMess);
         } else if (errMess == null) {
            errMess = this.checkL10nPackageName();
         }

         if (this.subsystem == null) {
            errMess = "Error(" + this.filename + "): subsystem must be non null";
            this.cfg.warn(errMess);
         }

         if (this.version == null) {
            errMess = "Error(" + this.filename + "): version must be non null";
            this.cfg.warn(errMess);
         }

         if (this.messages != null && this.messages.size() != 0) {
            e = this.messages.elements();

            while(e.hasMoreElements()) {
               o = e.nextElement();
               if (o instanceof Message) {
                  Message m = (Message)o;
                  if (m.getMethodName() != null && m.getMethodName().length() > 0) {
                     ++this.numMethods;
                  }

                  logErrMess = m.validate();
                  if (logErrMess != null) {
                     errMess = logErrMess;
                     reported = true;
                     break;
                  }
               }
            }
         } else {
            this.cfg.warn("Warning(" + this.filename + "): no messages defined");
         }

         if (errMess != null && !reported) {
            this.cfg.warn("Errors detected at line " + this.getLine() + ", column " + this.getColumn());
         }

         return errMess;
      } else {
         this.cfg.inform("Warning(" + this.filename + "): Empty catalog");
         return errMess;
      }
   }

   private String checkL10nPackageName() {
      if (this.l10n_package.equals("weblogic.i18n")) {
         StringBuilder sb = new StringBuilder();
         sb.append("The l10n_package in the catalog ");
         sb.append(this.filename);
         sb.append(" is set to the weblogic.i18n default value.");
         sb.append(" Please specify another l10n_package for the catalog so that user files are isolated from the i18n module.");
         sb.append(PlatformConstants.EOL);
         String text = sb.toString();
         if (this.cfg.isStrictPackageCheckingEnabled()) {
            text = "Error :" + text;
            return text;
         }

         text = "Warning :" + text;
         this.cfg.warn(text);
      }

      return null;
   }

   private String checkI18nPackageName() {
      if (this.i18n_package.equals("weblogic.i18n")) {
         StringBuilder sb = new StringBuilder();
         sb.append("The i18n_package value in the catalog ");
         sb.append(this.filename);
         sb.append(" is set to the weblogic.i18n default value.");
         sb.append(" Please specify another i18n_package for the catalog so that user classes are isolated from the i18n module.");
         sb.append(PlatformConstants.EOL);
         String text = sb.toString();
         if (this.cfg.isStrictPackageCheckingEnabled()) {
            text = "Error :" + text;
            return text;
         }

         text = "Warning :" + text;
         this.cfg.warn(text);
      }

      return null;
   }

   public void changeBasicMessage(BasicMessage msg, String oldId) throws WrongTypeException, MessageNotFoundException {
      if (msg instanceof LogMessage && this.verifyMessageCatalog(2)) {
         this.changeLogMessage((LogMessage)msg, oldId);
      } else {
         if (!(msg instanceof Message) || !this.verifyMessageCatalog(1)) {
            throw new IllegalArgumentException();
         }

         this.changeMessage((Message)msg, oldId);
      }

   }
}
