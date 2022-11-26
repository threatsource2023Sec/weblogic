package weblogic.i18n.tools;

import org.xml.sax.Locator;

public class Config {
   boolean verbose = false;
   boolean debug = false;
   boolean server = false;
   boolean nowarn = false;
   boolean strictPackageCheckingEnabled = false;
   public static final boolean validating = true;
   public static final String I18N_PROPERTIES = "i18n.properties";
   public static final String I18N_PROPERTIES_RESOURCE = "weblogic/i18n/i18n.properties";
   public static final String I18N_USER_PROPERTIES = "i18n_user.properties";
   public static final String I18N_USER_PROPERTIES_RESOURCE = "i18n_user.properties";
   public static final String I18N_EXT_PROPERTIES = "i18n_ext.properties";
   public static final String I18N_EXT_PROPERTIES_RESOURCE = "i18n_ext.properties";
   public static final String I18N_BACKUP = "i18n.oldprops";
   public static final int SERVER_MAX = 500000;
   public static final int MAX_ID = 999999;
   public static final int MAX_ID_LENGTH = 6;
   public static final int EXTENDED_I18N_SERVER_MIN_MSG_ID = 1000000;
   public static final int EXTENDED_I18N_SERVER_MAX_MSG_ID = 9999999;
   public static final String I18N_MSGCAT_PUBLIC_ID = "weblogic-message-catalog-dtd";
   public static final String I18N_LOCALE_MSGCAT_PUBLIC_ID = "weblogic-locale-message-catalog-dtd";
   public static final String I18N_MSGCAT_SYSTEM_ID = "http://www.bea.com/servers/wls810/dtd/msgcat.dtd";
   public static final String I18N_LOCALE_MSGCAT_SYSTEM_ID = "http://www.bea.com/servers/wls810/dtd/l10n_msgcat.dtd";
   public static final String I18N_MSGCAT_LOCAL_NAME = "weblogic/msgcat/msgcat.dtd";
   public static final String LOGGER = "logger";
   public static final String GETTER = "getter";
   public static final String DEFAULT_METHODTYPE = "logger";
   public static final String[] METHOD_TYPES = new String[]{"logger", "getter"};
   public static final String DEFAULT_RETIREMENT = "false";
   public static final boolean DEFAULT_RETIREMENT_VAL = false;
   public static final boolean DEFAULT_STACKTRACE_VAL = true;
   public static final String RETIRED_CLASS = "retired";
   public static final String DEFAULT_DIAGNOSTICVOLUME_VAL = "Off";
   public static final boolean DEFAULT_LOGGABLE_VAL = false;
   public static final boolean DEFAULT_EXCLUDE_PARTIIONED_VAL = false;
   public static final String VERSION = "1.0";
   private Locator locator = null;

   public void debug(String txt) {
      if (this.debug) {
         System.err.println(txt);
      }

   }

   public void inform(String txt) {
      if (this.verbose) {
         System.err.println(txt);
      }

   }

   public void warn(String txt) {
      if (!this.nowarn) {
         System.err.println(txt);
      }

   }

   public void setDebug(boolean b) {
      this.debug = b;
   }

   public void setVerbose(boolean b) {
      this.verbose = b;
   }

   public void setNoWarn(boolean b) {
      this.nowarn = b;
   }

   public void setServer(boolean b) {
      this.server = b;
   }

   public boolean isServer() {
      return this.server;
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public boolean isDebug() {
      return this.debug;
   }

   public Locator getLocator() {
      return this.locator;
   }

   public void setLocator(Locator l) {
      this.locator = l;
   }

   public void linecol() {
      Locator locator = this.getLocator();
      if (locator != null) {
         this.warn("Error detected at line " + locator.getLineNumber() + ", column " + locator.getColumnNumber());
      } else {
         this.warn("Error detected at line 1, column 1");
      }

   }

   public boolean isStrictPackageCheckingEnabled() {
      return this.strictPackageCheckingEnabled;
   }

   public void setStrictPackageCheckingEnabled(boolean strictPackageCheckingEnabled) {
      this.strictPackageCheckingEnabled = strictPackageCheckingEnabled;
   }
}
