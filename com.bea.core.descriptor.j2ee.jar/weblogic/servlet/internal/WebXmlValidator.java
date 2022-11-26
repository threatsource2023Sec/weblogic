package weblogic.servlet.internal;

import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.LoginConfigBean;
import weblogic.j2ee.descriptor.SessionConfigBean;
import weblogic.j2ee.descriptor.WebAppBean;

public final class WebXmlValidator {
   public static void validateSingletons(WebAppBean root) throws IllegalArgumentException {
      validateLoginConfig(root);
      validateJspConfig(root);
      validateSessionConfig(root);
   }

   private static void validateLoginConfig(WebAppBean root) {
      LoginConfigBean[] loginConfigs = root.getLoginConfigs();
      if (loginConfigs != null && loginConfigs.length >= 2) {
         throw new IllegalArgumentException("Multiple occurrences of login-config element are not allowed in web.xml");
      }
   }

   private static void validateJspConfig(WebAppBean root) {
      JspConfigBean[] jspConfigs = root.getJspConfigs();
      if (jspConfigs != null && jspConfigs.length >= 2) {
         throw new IllegalArgumentException("Multiple occurrences of jsp-config element are not allowed in web.xml");
      }
   }

   private static void validateSessionConfig(WebAppBean root) {
      SessionConfigBean[] sessionConfigs = root.getSessionConfigs();
      if (sessionConfigs != null && sessionConfigs.length >= 2) {
         throw new IllegalArgumentException("Multiple occurrences of session-config element are not allowed in web.xml");
      }
   }

   public static void validateURLPatterns(String[] patterns) {
      if (patterns != null) {
         for(int i = 0; i < patterns.length; ++i) {
            validateURLPattern(patterns[i]);
         }

      }
   }

   public static void validateURLPattern(String pattern) {
      if (pattern.indexOf(13) > -1 || pattern.indexOf(10) > -1) {
         throw new IllegalArgumentException("The url-pattern element cannot have CR/LF characters");
      }
   }

   public static void validateJspConfigURLPatterns(String[] patterns) {
      if (patterns != null) {
         for(int i = 0; i < patterns.length; ++i) {
            validateJspConfigURLPattern(patterns[i]);
         }

      }
   }

   static void validateJspConfigURLPattern(String pattern) {
      validateServletURLPattern(pattern);
      if (pattern.length() > 1 && pattern.indexOf("*.") > 0 && pattern.startsWith("/")) {
         throw new IllegalArgumentException("The url-pattern, '" + pattern + "' is invalid.");
      }
   }

   public static void validateServletURLPatterns(String[] patterns) {
      if (patterns != null) {
         for(int i = 0; i < patterns.length; ++i) {
            validateServletURLPattern(patterns[i]);
         }

      }
   }

   public static void validateServletURLPattern(String pattern) {
      if (pattern.indexOf(13) > -1 || pattern.indexOf(10) > -1) {
         throw new IllegalArgumentException("The url-pattern element cannot have CR/LF characters");
      }
   }

   public static void validateDispatchers(String[] dispatchers) {
      if (dispatchers != null) {
         for(int i = 0; i < dispatchers.length; ++i) {
            if (!"FORWARD".equals(dispatchers[i]) && !"INCLUDE".equals(dispatchers[i]) && !"REQUEST".equals(dispatchers[i]) && !"ERROR".equals(dispatchers[i]) && !"ASYNC".equals(dispatchers[i])) {
               throw new IllegalArgumentException("Invalid value found in dispatcher element. FORWARD, INCLUDE, REQUEST, ERROR, ASYNC are the only allowed values");
            }
         }

      }
   }

   public static void validateTrackingModes(String[] trackingModes) {
      if (trackingModes != null) {
         for(int i = 0; i < trackingModes.length; ++i) {
            if (!"COOKIE".equals(trackingModes[i]) && !"URL".equals(trackingModes[i]) && !"SSL".equals(trackingModes[i])) {
               throw new IllegalArgumentException("Invalid value found in tracking mode element. COOKIE, URL, SSL are the only allowed values");
            }
         }

      }
   }
}
