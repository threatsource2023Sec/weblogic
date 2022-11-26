package weblogic.security.utils;

import com.octetstring.vde.util.PasswordEncryptor;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import weblogic.management.bootstrap.BootStrap;
import weblogic.security.SecurityLogger;
import weblogic.utils.LocatorUtilities;

public final class AdminAccount {
   private static final boolean debug = false;
   private static final String DEFAULT_TEMPLATE = "DefaultAuthenticatorInit.ldift";
   private static final String DEFAULT_BASE_TEMPLATE;

   public static void main(String[] args) {
      if (args.length >= 3 && args.length <= 4) {
         try {
            String optional = null;
            if (args.length == 4) {
               optional = args[3];
            }

            setupAdminAccount(args[0], args[1], args[2], optional);
         } catch (Exception var2) {
            System.out.println("Error: " + var2);
         }

      } else {
         System.out.println("Error: Invalid arguments");
      }
   }

   public static void setupAdminAccount(String user, String pass, String outputDir, String templateName) throws IOException {
      if (user != null) {
         user = user.trim();
      }

      if (pass != null) {
         pass = pass.trim();
      }

      if (outputDir != null) {
         outputDir = outputDir.trim();
      }

      if (templateName != null) {
         templateName = templateName.trim();
      }

      if (user != null && user.length() != 0 && pass != null && pass.length() != 0 && outputDir != null && outputDir.length() != 0) {
         String invalidPwdMsg = "Password must be at least 8 characters in length and must contain one or more non-alphabetic characters.";
         String pwdPattern = "[\\!a-zA-Z]{1,}";
         boolean matches = Pattern.matches(pwdPattern, pass);
         if (pass.length() >= 8 && !matches) {
            String template = templateName;
            if (templateName == null || templateName.length() == 0) {
               template = DEFAULT_BASE_TEMPLATE;
            }

            File outputDirFile = new File(outputDir);
            if (outputDirFile.exists() && outputDirFile.isDirectory()) {
               File templateFile = new File(template);
               if (templateFile.exists() && templateFile.isFile()) {
                  String output = outputDir;
                  if (!outputDir.endsWith(File.separator)) {
                     output = outputDir + File.separator;
                  }

                  output = output + "DefaultAuthenticatorInit.ldift";
                  String encryptedPass = null;

                  try {
                     encryptedPass = PasswordEncryptor.doSSHA256(pass);
                  } catch (Exception var13) {
                     throw new IOException(var13.toString());
                  }

                  if (encryptedPass == null) {
                     throw new IOException(SecurityLogger.getEncryptionError());
                  } else {
                     encryptedPass = "{ssha256}" + encryptedPass;
                     ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
                     providerUtils.convertBaseLDIFTemplate(user, encryptedPass, output, template);
                  }
               } else {
                  throw new IOException(SecurityLogger.getInvalidFileParameterAdminAccount(template));
               }
            } else {
               throw new IOException(SecurityLogger.getInvalidFileParameterAdminAccount(outputDir));
            }
         } else {
            throw new IllegalArgumentException(invalidPwdMsg);
         }
      } else {
         throw new IOException(SecurityLogger.getInvalidParameterAdminAccount());
      }
   }

   static {
      DEFAULT_BASE_TEMPLATE = BootStrap.getPathRelativeWebLogicHome("lib" + File.separator + "Authenticator" + "Base.ldift");
   }
}
