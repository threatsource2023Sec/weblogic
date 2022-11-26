package weblogic.i18ntools.parser;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import weblogic.i18n.Localizer;
import weblogic.i18n.tools.BasicMessageCatalog;
import weblogic.i18n.tools.DuplicateElementException;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageNotFoundException;
import weblogic.i18n.tools.WrongTypeException;
import weblogic.i18ntools.L10nLookup;
import weblogic.i18ntools.internal.I18nConfig;

public final class LocaleMessageCatalog extends BasicMessageCatalog {
   public static final String attr_version = "version";
   public static final String attr_filename = "filename";
   public static final String attr_l10n_package = "l10n_package";
   static final String[] attrNames = new String[]{"version", "filename", "l10n_package"};
   private L10nParserTextFormatter fmt;
   private I18nConfig cfg;
   private boolean reported = false;
   private String l10nPackage = null;
   private Locale locale;
   private MessageCatalog masterCatalog = null;
   private HashSet logIds = new HashSet();
   HashSet msgIds = new HashSet();
   private String className = null;

   public void setMasterCatalog(MessageCatalog cat) {
      this.masterCatalog = cat;
   }

   public MessageCatalog getMasterCatalog() {
      return this.masterCatalog;
   }

   public String getL10nPackage() {
      return this.l10nPackage;
   }

   public void setLocale(Locale lcl) {
      this.locale = lcl;
   }

   public Locale getLocale() {
      return this.locale;
   }

   public LocaleMessageCatalog(I18nConfig config) {
      this.cfg = config;
      if (this.cfg == null) {
         this.cfg = new I18nConfig();
      }

      this.fmt = new L10nParserTextFormatter();
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
      if (att.equals("version")) {
         return this.version;
      } else if (att.equals("filename")) {
         return this.filename;
      } else {
         return att.equals("l10n_package") ? this.l10nPackage : null;
      }
   }

   public void set(String att, String val) {
      if (att.equals("version")) {
         this.version = val;
      } else if (att.equals("filename")) {
         this.filename = val;
      } else if (att.equals("l10n_package")) {
         this.l10nPackage = val;
      }
   }

   public boolean changeLogMessage(LocaleLogMessage logMsg, String oldId) throws MessageNotFoundException, WrongTypeException {
      if (!this.verifyMessageCatalog(2)) {
         return false;
      } else {
         int el = 0;

         int len;
         for(len = this.logMessages.size(); el < len; ++el) {
            LocaleLogMessage msg = (LocaleLogMessage)this.logMessages.elementAt(el);
            if (msg.get("messageid").equals(oldId)) {
               break;
            }
         }

         if (el >= len) {
            throw new MessageNotFoundException(this.fmt.noSuchMessage(oldId, this.getFileName()));
         } else {
            this.logMessages.setElementAt(logMsg, el);
            return true;
         }
      }
   }

   public boolean addLogMessage(LocaleLogMessage logMsg) throws WrongTypeException, DuplicateElementException {
      if (!this.verifyMessageCatalog(2)) {
         return false;
      } else if (!this.logIds.add(logMsg.getMessageId())) {
         this.cfg.linecol(this.fmt);
         throw new DuplicateElementException(this.fmt.dupMessage(logMsg.getMessageId(), this.getFileName()));
      } else {
         this.logMessages.add(logMsg);
         if (this.cfg.isDebug()) {
            Enumeration e = this.logMessages.elements();
            this.cfg.debug(this.fmt.logVector());

            while(e.hasMoreElements()) {
               LocaleLogMessage l = (LocaleLogMessage)e.nextElement();
               this.cfg.debug(this.fmt.element(l.get("messageid")));
            }
         }

         return true;
      }
   }

   public boolean changeMessage(LocaleMessage simpleMsg, String oldId) throws MessageNotFoundException, WrongTypeException {
      if (!this.verifyMessageCatalog(1)) {
         return false;
      } else {
         int el = 0;

         int len;
         for(len = this.messages.size(); el < len; ++el) {
            LocaleMessage msg = (LocaleMessage)this.messages.elementAt(el);
            if (msg.get("messageid").equals(oldId)) {
               break;
            }
         }

         if (el >= len) {
            throw new MessageNotFoundException(this.fmt.noSuchMessage(oldId, this.getFileName()));
         } else {
            this.messages.setElementAt(simpleMsg, el);
            return true;
         }
      }
   }

   public boolean addMessage(LocaleMessage msg) throws WrongTypeException, DuplicateElementException {
      if (!this.verifyMessageCatalog(1)) {
         return false;
      } else if (!this.msgIds.add(msg.getMessageId())) {
         this.cfg.linecol(this.fmt);
         throw new DuplicateElementException(this.fmt.dupMessage(msg.getMessageId(), this.getFileName()));
      } else {
         this.messages.add(msg);
         if (this.cfg.isVerbose()) {
            Enumeration e = this.messages.elements();
            this.cfg.debug(this.fmt.msgVector());

            while(e.hasMoreElements()) {
               LocaleMessage m = (LocaleMessage)e.nextElement();
               this.cfg.debug(this.fmt.element(m.getMessageId()));
            }
         }

         return true;
      }
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String name) {
      this.className = name;
   }

   public String validate(boolean validateResources) {
      String errMess = null;
      String simpleErrMess;
      Enumeration e;
      Object o;
      if (this.catType == 2) {
         this.cfg.debug("Validating log message catalog...");
         if (this.version == null) {
            errMess = this.fmt.nullVersion(this.filename);
            this.cfg.warn(errMess);
         }

         this.l10nPackage = null;
         if (this.logMessages != null && this.logMessages.size() != 0) {
            e = this.logMessages.elements();

            while(e.hasMoreElements()) {
               o = e.nextElement();
               if (o instanceof LocaleLogMessage) {
                  LocaleLogMessage lm = (LocaleLogMessage)o;
                  simpleErrMess = lm.validate(validateResources);
                  if (this.l10nPackage == null) {
                     this.l10nPackage = lm.getL10nPackage();
                  }

                  if (simpleErrMess != null) {
                     errMess = simpleErrMess;
                     this.reported = true;
                     break;
                  }
               }
            }
         } else {
            this.cfg.warn(this.fmt.noMessages(this.filename));
         }

         return errMess;
      } else if (this.catType != 1) {
         this.cfg.inform(this.fmt.noMessages(this.filename));
         if (!this.reported) {
            this.cfg.warn(this.fmt.locator(this.getLine(), this.getColumn()));
         }

         return errMess;
      } else {
         this.cfg.debug(this.fmt.validateSimple());
         if (this.version == null) {
            errMess = this.fmt.nullVersion(this.filename);
            this.cfg.warn(errMess);
         }

         if (this.l10nPackage == null) {
            errMess = this.fmt.noPackage(this.filename);
            this.cfg.warn(errMess);
         } else {
            String name = this.l10nPackage + "." + this.filename.substring(0, this.filename.length() - ".xml".length()) + "TextLocalizer";

            try {
               Localizer l10n = L10nLookup.getLocalizer(Locale.getDefault(), name);
               String masterPkg = l10n.getL10nPackage();
               if (!masterPkg.equals(this.l10nPackage)) {
                  errMess = this.fmt.incorrectPackage(this.l10nPackage, masterPkg, this.filename);
                  this.cfg.warn(errMess);
               } else {
                  this.setClassName(name);
               }
            } catch (MissingResourceException var7) {
               errMess = this.fmt.invalidPackage(this.l10nPackage, this.filename);
               this.cfg.warn(errMess);
            }
         }

         if (this.messages != null && this.messages.size() != 0) {
            e = this.messages.elements();

            while(e.hasMoreElements()) {
               o = e.nextElement();
               if (o instanceof LocaleMessage) {
                  LocaleMessage m = (LocaleMessage)o;
                  simpleErrMess = m.validate(validateResources);
                  if (simpleErrMess != null) {
                     errMess = simpleErrMess;
                     this.reported = true;
                     break;
                  }
               }
            }
         } else {
            this.cfg.inform(this.fmt.noMessages(this.filename));
         }

         return errMess;
      }
   }
}
