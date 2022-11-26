package weblogic.deploy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.kernel.Kernel;
import weblogic.management.deploy.utils.MBeanHomeToolTextFormatter;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.utils.Getopt2;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.compiler.Tool;
import weblogic.utils.io.TerminalIO;

public abstract class MBeanHomeTool extends Tool {
   public static final String DEFAULT_ADMIN_URL = "t3://localhost:7001";
   public static final String DEFAULT_PROTOCOL = "t3://";
   public static final String DEFAULT_USER = null;
   public static final String DEFAULT_USER_VALUE = "installadministrator";
   public static final String OPTION_ADMIN_URL = "adminurl";
   public static final String OPTION_USER = "user";
   public static final String OPTION_PASSWORD = "password";
   public static final String OPTION_IDD = "idd";
   public static final String HTTP_STRING = "http";
   public static final String HTTPS_STRING = "https";
   public static final String T3_STRING = "t3";
   public static final String T3S_STRING = "t3s";
   public static final String OPTION_USERNAME = "username";
   public static final String OPTION_URL = "url";
   public static final String IIOP_STRING = "iiop";
   public static final String OPTION_USERCONFIG = "userconfigfile";
   public static final String DEFAULT_USERCONFIG = null;
   public static final String OPTION_USERKEY = "userkeyfile";
   public static final String DEFAULT_USERKEY = null;
   private MBeanHomeToolTextFormatter textFormatter = new MBeanHomeToolTextFormatter();
   private String password;
   private String idd;
   private String user;
   private UsernameAndPassword UandP = null;
   private String userConfigFile = null;
   private String userKeyFile = null;
   private Context ctx = null;
   private JMXConnector jmx = null;
   private MBeanServerConnection mbs = null;

   public MBeanHomeTool(String[] args) {
      super(args);
   }

   public Getopt2 getOpts() {
      return this.opts;
   }

   public MBeanServerConnection getMBeanServer() throws MBeanHomeToolException {
      if (this.mbs != null) {
         return this.mbs;
      } else {
         String uRLString = this.opts.getOption("adminurl", "t3://localhost:7001");
         if (uRLString.indexOf("://") == -1) {
            uRLString = "t3://" + uRLString;
         }

         try {
            if (!Kernel.isServer()) {
               this.processUsernameAndPassword();
            } else if (uRLString.startsWith("iiop") && System.getProperty("weblogic.system.iiop.enableClient") == null) {
               System.setProperty("weblogic.system.iiop.enableClient", "false");
            }

            URI url = new URI(uRLString);
            uRLString = uRLString + "/jndi/" + "weblogic.management.mbeanservers.domainruntime";
            Hashtable h = new Hashtable();
            if (!Kernel.isServer()) {
               h.put("java.naming.security.principal", this.getUser());
               h.put("java.naming.security.credentials", this.getPassword());
               if (this.getIDD() != null) {
                  h.put("weblogic.management.remote.identitydomain", this.getIDD());
               }
            }

            Environment tempEnv = new Environment();
            tempEnv.setProviderUrl(uRLString);
            tempEnv.setSecurityPrincipal(this.getUser());
            tempEnv.setSecurityCredentials(this.getPassword());
            if (this.getIDD() != null) {
               tempEnv.setSecurityIdentityDomain(this.getIDD());
            }

            if (uRLString.startsWith("iiop")) {
               tempEnv.setInitialContextFactory("weblogic.jndi.WLInitialContextFactory");
            }

            this.ctx = tempEnv.getInitialContext();
            String path = url.getPath() != null ? url.getPath() : "";
            h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
            JMXServiceURL jmxs = new JMXServiceURL(url.getScheme(), url.getHost(), url.getPort(), path + "/jndi/" + "weblogic.management.mbeanservers.domainruntime");
            JMXConnector jmx = JMXConnectorFactory.connect(jmxs, h);
            this.mbs = jmx.getMBeanServerConnection();
            return this.mbs;
         } catch (Exception var8) {
            String username = this.getUser();
            if (username != null && IdentityDomainNamesEncoder.isEncodedNames(username)) {
               username = IdentityDomainNamesEncoder.decodeNames(username).getName();
            }

            throw new MBeanHomeToolException(this.textFormatter.errorOnConnect(uRLString, username, StackTraceUtils.throwable2StackTrace(var8)));
         }
      }
   }

   public String getUser() {
      return this.user;
   }

   private String getUserFromHuman() {
      if (this.user != null) {
         return this.user;
      } else {
         this.user = this.opts.getOption("username");
         if (this.user == null || this.user.length() == 0) {
            System.out.print(this.textFormatter.promptUsername());
            System.out.flush();

            try {
               this.user = (new BufferedReader(new InputStreamReader(System.in))).readLine();
            } catch (IOException var2) {
               this.user = "installadministrator";
            }
         }

         if (this.user == null || this.user.length() == 0) {
            this.user = "installadministrator";
         }

         return this.user;
      }
   }

   public String getPassword() {
      return this.password;
   }

   private String getPasswordFromHuman() throws MBeanHomeToolException {
      if (this.password != null) {
         return this.password;
      } else {
         this.password = this.opts.getOption("password");
         if (this.password == null) {
            String promptPassword = this.textFormatter.promptPassword(this.getUser());
            int tries = 0;

            while(this.password == null || this.password.length() == 0) {
               try {
                  System.out.print(promptPassword);
                  System.out.flush();
                  if (TerminalIO.isNoEchoAvailable()) {
                     this.password = TerminalIO.readTerminalNoEcho();
                  } else {
                     this.password = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                  }
               } catch (IOException var4) {
                  throw new MBeanHomeToolException(this.textFormatter.exceptionNoPassword());
               }

               if (tries++ > 3) {
                  throw new MBeanHomeToolException(this.textFormatter.exceptionNoPassword());
               }
            }
         }

         return this.password;
      }
   }

   public String getIDD() {
      return this.idd;
   }

   public void prepare() {
      this.opts.addOption("adminurl", this.textFormatter.exampleAdminUrl(), this.textFormatter.usageAdminUrl("t3://localhost:7001"));
      this.opts.addOption("username", this.textFormatter.exampleUser(), this.textFormatter.usageUser());
      this.opts.addOption("password", this.textFormatter.examplePassword(), this.textFormatter.usagePassword());
      this.opts.addOption("idd", this.textFormatter.exampleIDD(), this.textFormatter.usageIDD());
      this.opts.addOption("userconfigfile", this.textFormatter.exampleUserConfig(), this.textFormatter.usageUserConfig());
      this.opts.addOption("userkeyfile", this.textFormatter.exampleUserKey(), this.textFormatter.usageUserkey());
      this.opts.addAlias("username", "user");
      this.opts.addAlias("user", "username");
      this.opts.addAlias("url", "adminurl");
   }

   public void processUsernameAndPassword() throws MBeanHomeToolException {
      this.user = this.opts.getOption("user", DEFAULT_USER);
      this.password = this.opts.getOption("password");
      this.idd = this.opts.getOption("idd");
      this.userConfigFile = this.opts.getOption("userconfigfile", DEFAULT_USERCONFIG);
      this.userKeyFile = this.opts.getOption("userkeyfile", DEFAULT_USERKEY);
      if (this.user == null || this.password == null) {
         if (this.user == null && this.userConfigFile == null && this.userKeyFile == null) {
            this.user = this.getUserFromHuman();
         }

         if (this.password == null && this.userConfigFile == null && this.userKeyFile == null) {
            this.password = this.getPasswordFromHuman();
         }

         if (this.user == null || this.password == null) {
            UsernameAndPassword UAndP = null;

            try {
               if (this.userConfigFile == null && this.userKeyFile == null) {
                  UAndP = UserConfigFileManager.getUsernameAndPassword("weblogic.management");
               } else {
                  UAndP = UserConfigFileManager.getUsernameAndPassword(this.userConfigFile, this.userKeyFile, "weblogic.management");
               }

               if (UAndP != null) {
                  if (this.user == null) {
                     this.user = UAndP.getUsername();
                  }

                  if (this.password == null) {
                     this.password = new String(UAndP.getPassword());
                  }
               }
            } catch (Throwable var3) {
               throw new MBeanHomeToolException(this.textFormatter.exceptionNoPassword());
            }
         }

      }
   }

   protected void reset() {
      try {
         if (this.ctx != null) {
            this.ctx.close();
         }
      } catch (NamingException var14) {
      } finally {
         this.ctx = null;
      }

      try {
         if (this.jmx != null) {
            this.jmx.close();
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      } finally {
         this.jmx = null;
      }

   }
}
