package weblogic.management.configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import weblogic.common.internal.VersionInfo;
import weblogic.logging.Loggable;
import weblogic.management.WebLogicMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.security.SecurityLogger;

public class LegalHelper {
   public static boolean serverMBeanSetNameLegalCheck(String aValue, Object o) throws InvalidAttributeValueException {
      ServerTemplateMBean server = (ServerTemplateMBean)o;
      DomainMBean domain = (DomainMBean)server.getParent();
      return domain == null || domain.getName() == null || !domain.getName().equals(aValue);
   }

   static boolean exists(Set as, BooleanFun1Arg fun) {
      Iterator i = as.iterator();

      do {
         if (!i.hasNext()) {
            return false;
         }
      } while(!fun.eval((WebLogicMBean)i.next()));

      return true;
   }

   static boolean forall(Set as, BooleanFun1Arg fun) {
      Iterator i = as.iterator();

      do {
         if (!i.hasNext()) {
            return true;
         }
      } while(fun.eval((WebLogicMBean)i.next()));

      return false;
   }

   static boolean in(final WebLogicMBean probe, WebLogicMBean[] list) {
      return exists(new HashSet(Arrays.asList((Object[])list)), new BooleanFun1Arg() {
         public boolean eval(WebLogicMBean a) {
            return a.getName().equals(probe.getName());
         }
      });
   }

   static boolean subset(WebLogicMBean[] x, final WebLogicMBean[] y) {
      return forall(new HashSet(Arrays.asList((Object[])x)), new BooleanFun1Arg() {
         public boolean eval(WebLogicMBean a) {
            return LegalHelper.in(a, y);
         }
      });
   }

   static final void validateListenPorts(ServerTemplateMBean server) {
      SSLMBean ssl = server.getSSL();
      Loggable l;
      if (ssl.isEnabled() && ssl.getListenPort() == server.getListenPort()) {
         l = SecurityLogger.logSSLListenPortSameAsServerListenPortLoggable(server.getListenPort() + "");
         l.log();
         throw new IllegalArgumentException(l.getMessage());
      } else if (server.isAdministrationPortEnabled() && ssl.isEnabled() && ssl.getListenPort() == server.getAdministrationPort()) {
         l = SecurityLogger.logSSLListenPortSameAsAdministrationPortLoggable(ssl.getListenPort() + "");
         l.log();
         throw new IllegalArgumentException(l.getMessage());
      }
   }

   public static void validateJavaHome(String home) throws IllegalArgumentException {
      if (home != null) {
         if (home.indexOf(34) != -1) {
            throw new IllegalArgumentException("JavaHome may not contain '\"'");
         }
      }
   }

   public static void validateClasspath(String home) throws IllegalArgumentException {
      if (home != null) {
         if (home.indexOf(34) != -1) {
            throw new IllegalArgumentException("Classpath may not contain '\"'");
         }
      }
   }

   public static void validateBeaHome(String home) throws IllegalArgumentException {
      if (home != null) {
         if (home.indexOf(34) != -1) {
            throw new IllegalArgumentException("BeaHome may not contain '\"'");
         }
      }
   }

   public static void validateRootDirectory(String home) throws IllegalArgumentException {
      if (home != null) {
         if (home.indexOf(34) != -1) {
            throw new IllegalArgumentException("RootDirectory may not contain '\"'");
         }
      }
   }

   public static void validateSecurityPolicyFile(String home) throws IllegalArgumentException {
      if (home != null) {
         if (home.indexOf(34) != -1) {
            throw new IllegalArgumentException("SecurityPolicyFile may not contain '\"'");
         }
      }
   }

   public static void validateIsSetIfTargetted(DeploymentMBean bean, String attrName) throws IllegalArgumentException {
      TargetMBean[] targets = bean.getTargets();
      if (targets != null && targets.length > 0) {
         Object attr;
         try {
            attr = bean.getAttribute(attrName);
         } catch (AttributeNotFoundException var5) {
            attr = null;
         } catch (ReflectionException var6) {
            attr = null;
         } catch (MBeanException var7) {
            attr = null;
         }

         if (attr == null || attr instanceof String && ((String)attr).length() == 0) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getUnsetOnTargetted(bean.getName(), attrName));
         }
      }

   }

   public static void validateNotNullable(String oldVal, String newVal) throws IllegalArgumentException {
      if (newVal != null && "".equals(newVal.trim())) {
         throw new IllegalArgumentException("value may not be unset.");
      } else if (newVal == null && oldVal != null) {
         throw new IllegalArgumentException("value may not be set to empty string.");
      }
   }

   public static String checkClassName(String className) {
      String result = className;
      if (className != null && className.endsWith(".class")) {
         int len = className.length();
         result = className.substring(0, len - 6);
      }

      return result;
   }

   public static void validateArguments(String args) throws IllegalArgumentException {
      if (args != null) {
         if (args.indexOf(34) != -1 && !Boolean.getBoolean("weblogic.serverStart.allowQuotes")) {
            throw new IllegalArgumentException("Arguments may not contain '\"'");
         }
      }
   }

   public static boolean versionEarlierThan(String currVersion, String compareVersion) throws IllegalArgumentException {
      if (currVersion != null && compareVersion != null) {
         VersionInfo currVersionInfo = new VersionInfo(currVersion);
         VersionInfo compareVersionInfo = new VersionInfo(compareVersion);
         return currVersionInfo.earlierThan(compareVersionInfo);
      } else {
         return false;
      }
   }

   interface BooleanFun1Arg {
      boolean eval(WebLogicMBean var1);
   }
}
