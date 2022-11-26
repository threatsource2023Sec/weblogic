package weblogic.security.internal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.util.Properties;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityMessagesTextFormatter;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.io.TerminalIO;

public final class ServerAuthenticate {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static boolean creatingDomain(String[] args) {
      for(int i = 0; args != null && i < args.length; ++i) {
         if ("domainCreation".equals(args[i])) {
            return true;
         }
      }

      return false;
   }

   public static void main(String[] args) {
      String username = getCommandLineProperty("weblogic.management.username");
      String password = getCommandLineProperty("weblogic.management.password");
      boolean boot_user_on_command_line = username != null && password != null;
      boolean trust_keystore_on_command_line = false;
      if (getCommandLineProperty("weblogic.security.TrustKeyStore") != null || getCommandLineProperty("weblogic.security.CustomTrustKeyStoreFileName") != null || getCommandLineProperty("weblogic.security.CustomTrustKeyStoreType") != null || getCommandLineProperty("weblogic.security.CustomTrustKeyStorePassPhrase") != null || getCommandLineProperty("weblogic.security.JavaStandardTrustKeyStorePassPhrase") != null) {
         trust_keystore_on_command_line = true;
      }

      if (!boot_user_on_command_line || !trust_keystore_on_command_line) {
         boolean nodeManagerBoot = "true".equalsIgnoreCase(System.getProperty("weblogic.system.NodeManagerBoot"));
         String bootPropertiesFile = System.getProperty("weblogic.system.BootIdentityFile");
         SerializedSystemIni.upgradeSSI();
         if (BootProperties.exists(bootPropertiesFile) && (nodeManagerBoot || SerializedSystemIni.exists())) {
            BootProperties.load(bootPropertiesFile, nodeManagerBoot);
         }

         BootProperties.upgradeBP(bootPropertiesFile);
         BootProperties bootProps = BootProperties.getBootProperties();
         if (!trust_keystore_on_command_line && bootProps != null) {
            setCommandLineProperty("weblogic.security.TrustKeyStore", bootProps.getTrustKeyStore());
            setCommandLineProperty("weblogic.security.CustomTrustKeyStoreFileName", bootProps.getCustomTrustKeyStoreFileName());
            setCommandLineProperty("weblogic.security.CustomTrustKeyStoreType", bootProps.getCustomTrustKeyStoreType());
            setCommandLineProperty("weblogic.security.CustomTrustKeyStorePassPhrase", bootProps.getCustomTrustKeyStorePassPhrase());
            setCommandLineProperty("weblogic.security.JavaStandardTrustKeyStorePassPhrase", bootProps.getJavaStandardTrustKeyStorePassPhrase());
            setCommandLineProperty("weblogic.security.CustomIdentityKeyStoreFileName", bootProps.getCustomIdentityKeyStoreFileName());
            setCommandLineProperty("weblogic.security.CustomIdentityKeyStoreType", bootProps.getCustomIdentityKeyStoreType());
            setCommandLineProperty("weblogic.security.CustomIdentityKeyStorePassPhrase", bootProps.getCustomIdentityKeyStorePassPhrase());
            setCommandLineProperty("weblogic.security.CustomIdentityKeyStoreAlias", bootProps.getCustomIdentityAlias());
            setCommandLineProperty("weblogic.security.CustomIdentityPrivateKeyPassPhrase", bootProps.getCustomIdentityPrivateKeyPassPhrase());
         }

         if (!boot_user_on_command_line) {
            if (nodeManagerBoot && bootProps != null) {
               String user = bootProps.getOne(kernelId);
               if (user == null) {
                  user = "";
               }

               String pass = bootProps.getTwo(kernelId);
               if (pass == null) {
                  pass = "";
               }

               String idd = bootProps.getIdentityDomain(kernelId);
               BootProperties.unload(true);
               Properties props = System.getProperties();
               props.setProperty("weblogic.management.username", user);
               props.setProperty("weblogic.management.password", pass);
               if (idd != null && idd.length() > 0) {
                  props.setProperty("weblogic.management.IdentityDomain", idd);
               }

            } else {
               initUserNameAndPassword(creatingDomain(args), BootProperties.getBootProperties());
            }
         }
      }
   }

   private static void initUserNameAndPassword(boolean creatingDomain, BootProperties bootProps) {
      SecurityMessagesTextFormatter textFrmtr = SecurityMessagesTextFormatter.getInstance();
      String username = null;
      String password = null;
      String idd = null;
      if (bootProps != null) {
         username = bootProps.getOne(kernelId);
         password = bootProps.getTwo(kernelId);
         idd = bootProps.getIdentityDomain(kernelId);
      } else {
         SecurityLogger.logGettingBootIdentityFromUser();
      }

      if (username == null || "".equals(username.trim())) {
         username = System.getProperty("weblogic.management.username");
         if (username == null || "".equals(username.trim())) {
            username = promptValue(textFrmtr.getUsernamePromptMessage(), true);
         }

         if (username == null) {
            username = "";
         }

         username = username.trim();
      }

      if (password == null) {
         boolean isNativeLibraryAvailable = TerminalIO.isNoEchoAvailable();
         if (!isNativeLibraryAvailable) {
            AdminService adminService = (AdminService)LocatorUtilities.getService(AdminService.class);
            boolean isProductionMode = adminService.isProductionModeEnabled();
            boolean allowEcho = adminService.isPasswordEchoAllowed();
            if (isProductionMode && !allowEcho) {
               SecurityLogger.logErrorProductionModeNoEcho();
               System.exit(-1);
            }

            if (!isProductionMode && !allowEcho) {
               SecurityLogger.logErrorDevModeNoEcho();
               System.exit(-1);
            }
         }

         password = promptForPassword(creatingDomain);
      }

      Properties props = System.getProperties();
      props.put("weblogic.management.username", username);
      props.put("weblogic.management.password", password);
      if (idd != null && idd.length() > 0) {
         props.put("weblogic.management.IdentityDomain", idd);
      }

   }

   private static String promptForPassword(boolean creatingDomain) {
      SecurityMessagesTextFormatter textFrmtr = SecurityMessagesTextFormatter.getInstance();
      String password1 = promptValue(textFrmtr.getPasswordPromptMessage(), false);
      if (password1 == null) {
         password1 = "";
      }

      if (creatingDomain) {
         String password2 = promptValue(textFrmtr.getPasswordPromptMessageRenter(), false);
         if (!password1.equals(password2)) {
            System.out.println(textFrmtr.getPasswordsNoMatch());
            String password3 = promptValue(textFrmtr.getPasswordPromptMessageRenter(), false);
            if (!password1.equals(password3)) {
               System.err.println("***************************************************************************");
               System.err.println(textFrmtr.getPasswordsNoMatchBoom());
               System.err.println("***************************************************************************");
               System.exit(-1);
            }
         }
      }

      return password1;
   }

   public static String promptValue(String text_prompt, boolean echo) {
      String returnValue = null;

      try {
         System.out.print(text_prompt);
         if (!echo && TerminalIO.isNoEchoAvailable()) {
            try {
               returnValue = TerminalIO.readTerminalNoEcho();
            } catch (Error var4) {
               System.err.println("Error: Failed to get value from Standard Input");
            }
         } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            returnValue = reader.readLine();
         }
      } catch (Exception var5) {
         System.err.println("Error: Failed to get value from Standard Input");
      }

      return returnValue;
   }

   private static String getCommandLineProperty(String name) {
      String value = System.getProperty(name);
      return value != null && value.length() > 0 ? value : null;
   }

   private static void setCommandLineProperty(String name, String value) {
      Properties props = System.getProperties();
      if (value != null && value.length() > 0) {
         props.setProperty(name, value);
      } else if (getCommandLineProperty(name) != null) {
         props.remove(name);
      }

   }
}
