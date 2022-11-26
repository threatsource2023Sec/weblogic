package com.octetstring.vde.util;

import com.octetstring.nls.Messages;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig extends Properties {
   private static final String VDE_PROP = "conf/vde.prop";
   private static final String VDE_PROP_DESC = "Octet String VDE Properties";
   public static final String VDE_SERVER_NAME = "vde.server.name";
   public static final String VDE_SERVER_LISTENADDR = "vde.server.listenaddr";
   public static final String VDE_SERVER_PORT = "vde.server.port";
   public static final String VDE_STDSCHEMA = "vde.schema.std";
   public static final String VDE_USERSCHEMA = "vde.schema.user";
   public static final String VDE_SERVER_THREADS = "vde.server.threads";
   public static final String VDE_SERVER_BACKENDS = "vde.server.backends";
   public static final String VDE_SCHEMACHECK = "vde.schemacheck";
   public static final String VDE_ACLFILE = "vde.aclfile";
   public static final String VDE_ACLCHECK = "vde.aclcheck";
   public static final String VDE_ROOTUSER = "vde.rootuser";
   public static final String VDE_ROOTPW = "vde.rootpw";
   public static final String VDE_DEBUG = "vde.debug";
   public static final String VDE_LOGCONSOLE = "vde.logconsole";
   public static final String VDE_ERRORLOG = "vde.logfile";
   public static final String VDE_ACCESSLOG = "vde.accesslogfile";
   public static final String VDE_TLS = "vde.tls";
   public static final String VDE_TLS_KEYSTORE = "vde.tls.keystore";
   public static final String VDE_TLS_PASS = "vde.tls.pass";
   public static final String VDE_CHANGELOG = "vde.changelog";
   public static final String VDE_CHANGELOG_SUFFIX = "vde.changelog.suffix";
   public static final String VDE_CHANGELOG_FILE = "vde.changelog.file";
   public static final String VDE_BACKENDTYPES = "vde.backendtypes";
   public static final String VDE_SERVER_REPLICATION = "vde.replicas";
   public static final String VDE_SEARCHLIMIT_ANON = "vde.searchlimit.anon";
   public static final String VDE_SEARCHLIMIT_AUTH = "vde.searchlimit.auth";
   public static final String VDE_LOGROTATE_HOUR = "vde.logrotate.hour";
   public static final String VDE_LOGROTATE_MIN = "vde.logrotate.minute";
   public static final String VDE_LOGROTATE_MAXLOGS = "vde.logrotate.maxlogs";
   public static final String VDE_LICENSE = "vde.licensefile";
   public static final String VDE_DOS_OPSPERCON = "vde.quota.max.opspercon";
   public static final String VDE_DOS_MAXCONNECTIONS = "vde.quota.max.connections";
   public static final String VDE_DOS_CONPERSUBJECT = "vde.quota.max.conpersubject";
   public static final String VDE_DOS_CONPERIP = "vde.quota.max.conperip";
   public static final String VDE_DOS_RATEPERIOD = "vde.quota.period";
   public static final String VDE_DOS_EXEMPTIPS = "vde.quota.exemptips";
   public static final String VDE_DOS_EXEMPTUSERS = "vde.quota.exemptusers";
   public static final String VDE_DOS_CHECK = "vde.quota.check";
   public static final String VDE_ROUTER_CONFLICTPOL = "vde.router.conflictpolicy";
   public static final String VDE_ROOT_SUFFIX = "vde.root.suffix";
   public static final String VDE_HTML_XSLTFILE = "vde.html.xsltfile";
   public static final String VDE_LISTENERS_FILE = "vde.listeners.file";
   public static final String VDE_ALLOW_ANONYMOUS_BIND = "vde.allow.anonymous";
   public static final String VDE_LDAP_TIMEOUT = "vde.ldap.timeout";
   private static String[] config_opts = new String[]{"vde.server.name", "vde.server.listenaddr", "vde.server.port", "vde.schema.std", "vde.schema.user", "vde.server.threads", "vde.server.backends", "vde.schemacheck", "vde.aclfile", "vde.rootuser", "vde.rootpw", "vde.debug", "vde.logconsole", "vde.logfile", "vde.accesslogfile", "vde.tls", "vde.tls.keystore", "vde.tls.pass", "vde.changelog", "vde.changelog.suffix", "vde.changelog.file", "vde.backendtypes", "vde.replicas", "vde.searchlimit.anon", "vde.searchlimit.auth", "vde.logrotate.hour", "vde.logrotate.minute", "vde.logrotate.maxlogs", "vde.licensefile", "vde.ldap.timeout"};
   private static ServerConfig instance;

   private ServerConfig() {
   }

   private ServerConfig(Properties defaults) {
      super(defaults);
   }

   public static ServerConfig getInstance() {
      if (instance == null) {
         instance = new ServerConfig();
      }

      return instance;
   }

   public Object get(String key) {
      String val = System.getProperty(key);
      if (val == null) {
         val = (String)super.get(key);
      }

      return val;
   }

   public void init() throws IOException {
      String ihome = System.getProperty("vde.home");
      String fullpath = null;
      if (ihome == null) {
         fullpath = "conf/vde.prop";
      } else {
         fullpath = ihome + "/" + "conf/vde.prop";
      }

      try {
         FileInputStream is = new FileInputStream(fullpath);
         this.load(is);
         is.close();
      } catch (FileNotFoundException var4) {
         throw new FileNotFoundException(Messages.getString("Configuration_File_Not_Found___37") + fullpath);
      } catch (IOException var5) {
         throw new IOException(Messages.getString("IO_Error_reading_Configuration_File___38") + fullpath);
      }
   }

   public String[] getOptionNames() {
      return config_opts;
   }

   public void write() {
   }
}
