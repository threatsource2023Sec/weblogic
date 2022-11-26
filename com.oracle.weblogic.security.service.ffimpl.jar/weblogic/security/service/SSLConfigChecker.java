package weblogic.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.security.SecurityLogger;

public class SSLConfigChecker {
   private SSLMBean sslmBean;
   private NetworkAccessPointMBean[] networkAccessPointMBeans;
   private static final String WEAK_CIPHER_REGEX = "^\\S+(_ANON_|_EXPORT_|_NULL_|_MD5|_DES_|_RC2_|_RC4_|_PSK_)\\S*$";
   private static final Pattern WEAK_CIPHER_PATTERN = Pattern.compile("^\\S+(_ANON_|_EXPORT_|_NULL_|_MD5|_DES_|_RC2_|_RC4_|_PSK_)\\S*$", 2);
   private final String ignoreHostNameVerificationSysProp1 = "weblogic.security.SSL.ignoreHostnameVerification";
   private final String ignoreHostNameVerificationSysProp2 = "weblogic.security.SSL.ignoreHostnameVerify";
   private final boolean ignoreHostNameVerification1 = Boolean.getBoolean("weblogic.security.SSL.ignoreHostnameVerification");
   private final boolean ignoreHostNameVerification2 = Boolean.getBoolean("weblogic.security.SSL.ignoreHostnameVerify");
   private final String sslVersion = System.getProperty("weblogic.security.SSL.protocolVersion");
   private final String minSSLVersion = System.getProperty("weblogic.security.SSL.minimumProtocolVersion");
   private final String enforceConstraint = System.getProperty("weblogic.security.SSL.enforceConstraints");
   private final String disableNullCipherSysProp = "weblogic.security.disableNullCipher";
   private final boolean disableNullCipherSetToFalse = "false".equalsIgnoreCase(System.getProperty("weblogic.security.disableNullCipher"));
   private final String allowNullCipherSysProp = "weblogic.ssl.AllowUnencryptedNullCipher";
   private final boolean allowNullCipherSetToTrue = "true".equalsIgnoreCase(System.getProperty("weblogic.ssl.AllowUnencryptedNullCipher"));
   private final String allowAnonymousCipherSysProp = "weblogic.security.SSL.AllowAnonymousCipher";
   private final boolean allowAnonymousCipher = Boolean.getBoolean("weblogic.security.SSL.AllowAnonymousCipher");

   public SSLConfigChecker(SSLMBean sslmBean, NetworkAccessPointMBean[] networkAccessPointMBeans) {
      this.sslmBean = sslmBean;
      this.networkAccessPointMBeans = networkAccessPointMBeans;
   }

   public boolean checkAndLog() {
      boolean passed = this.checkSystemPropertiesAndLog();
      passed = this.checkSSLMBeanPropertiesAndLog() && passed;
      if (this.networkAccessPointMBeans != null && this.networkAccessPointMBeans.length > 0) {
         NetworkAccessPointMBean[] var2 = this.networkAccessPointMBeans;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            NetworkAccessPointMBean n = var2[var4];
            passed = this.checkNetworkAccessPointMBeanPropertiesAndLog(n) && passed;
         }
      }

      return passed;
   }

   private boolean checkSystemPropertiesAndLog() {
      boolean passed = true;
      if (this.ignoreHostNameVerification1) {
         SecurityLogger.logHostNameVerificationDisabledBySysProp("-Dweblogic.security.SSL.ignoreHostnameVerification=true");
         passed = false;
      }

      if (this.ignoreHostNameVerification2) {
         SecurityLogger.logHostNameVerificationDisabledBySysProp("-Dweblogic.security.SSL.ignoreHostnameVerify=true");
         passed = false;
      }

      if ("SSLv3".equalsIgnoreCase(this.sslVersion)) {
         SecurityLogger.logSSLv3EnabledBySysProp("-Dweblogic.security.SSL.protocolVersion=" + this.sslVersion);
         passed = false;
      }

      if ("SSLv3".equalsIgnoreCase(this.minSSLVersion)) {
         SecurityLogger.logSSLv3MinProtocolEnabledBySysProp("-Dweblogic.security.SSL.minimumProtocolVersion=" + this.minSSLVersion);
         passed = false;
      }

      if ("off".equalsIgnoreCase(this.enforceConstraint) || "false".equalsIgnoreCase(this.enforceConstraint)) {
         SecurityLogger.logBasicConstraintsValidationEnabledBySysProp("-Dweblogic.security.SSL.enforceConstraints");
         passed = false;
      }

      if (this.allowAnonymousCipher) {
         SecurityLogger.logAllowAnonymousCiphersBySysProp("-Dweblogic.security.SSL.AllowAnonymousCipher=" + this.allowAnonymousCipher);
         passed = false;
      }

      if (this.disableNullCipherSetToFalse) {
         SecurityLogger.logNullCipherAllowedBySysProp("-Dweblogic.security.disableNullCipher=false");
         passed = false;
      }

      if (this.allowNullCipherSetToTrue) {
         SecurityLogger.logNullCipherAllowedBySysProp("-Dweblogic.ssl.AllowUnencryptedNullCipher=true");
         passed = false;
      }

      return passed;
   }

   private boolean checkSSLMBeanPropertiesAndLog() {
      boolean passed = true;
      if (this.sslmBean != null && this.sslmBean.isEnabled()) {
         if (this.sslmBean.isAllowUnencryptedNullCipher()) {
            SecurityLogger.logNullCipherAllowedBySSLMBean(this.sslmBean.getName());
            passed = false;
         }

         passed = this.checkCiphersuites(this.sslmBean.getName(), true, this.sslmBean.getCiphersuites()) && passed;
         if (this.sslmBean.isHostnameVerificationIgnored()) {
            SecurityLogger.logHostNameVerificationDisabledBySSLMBean(this.sslmBean.getName());
            passed = false;
         }

         if ("SSLv3".equalsIgnoreCase(this.sslmBean.getMinimumTLSProtocolVersion())) {
            SecurityLogger.logSSLv3MinProtocolEnabledBySSLMBean(this.sslmBean.getName());
            passed = false;
         }

         if (this.sslmBean.isClientInitSecureRenegotiationAccepted()) {
            SecurityLogger.logTLSClientInitSecureRenegotiationBySSLMBean(this.sslmBean.getName());
            passed = false;
         }
      }

      return passed;
   }

   private boolean checkNetworkAccessPointMBeanPropertiesAndLog(NetworkAccessPointMBean accessPointMBean) {
      boolean passed = true;
      if (accessPointMBean != null && accessPointMBean.isEnabled()) {
         if (accessPointMBean.isAllowUnencryptedNullCipher()) {
            SecurityLogger.logNullCipherAllowedByNetworkAccessPointMBean(accessPointMBean.getName());
            passed = false;
         }

         passed = this.checkCiphersuites(accessPointMBean.getName(), false, accessPointMBean.getCiphersuites()) && passed;
         if (accessPointMBean.isHostnameVerificationIgnored()) {
            SecurityLogger.logHostNameVerificationDisabledByNetworkAccessPointMBean(accessPointMBean.getName());
            passed = false;
         }

         if ("SSLv3".equalsIgnoreCase(accessPointMBean.getMinimumTLSProtocolVersion())) {
            SecurityLogger.logSSLv3MinProtocolEnabledByNetworkAccessPointMBean(accessPointMBean.getName());
            passed = false;
         }

         if (accessPointMBean.isClientInitSecureRenegotiationAccepted()) {
            SecurityLogger.logTLSClientInitSecureRenegotiationByNetworkAccessPointMBean(accessPointMBean.getName());
            passed = false;
         }
      }

      return passed;
   }

   private boolean checkCiphersuites(String serverOrNetworkChannel, boolean isServer, String[] ciphers) {
      boolean passed = true;
      List weakCiphers = new ArrayList();
      if (ciphers != null && ciphers.length > 0) {
         String[] var6 = ciphers;
         int var7 = ciphers.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String c = var6[var8];
            Matcher matcher = WEAK_CIPHER_PATTERN.matcher(c);
            if (matcher.matches()) {
               passed = false;
               weakCiphers.add(c);
            }
         }

         if (!passed) {
            if (isServer) {
               SecurityLogger.logWeakCipherSuitesBySSLMBean(serverOrNetworkChannel, joinStrings(weakCiphers));
            } else {
               SecurityLogger.logWeakCipherSuitesByNetworkAccessPointMBean(serverOrNetworkChannel, joinStrings(weakCiphers));
            }
         }
      }

      return passed;
   }

   private static String joinStrings(List strings) {
      String result = "";
      if (strings != null && strings.size() > 0) {
         result = Arrays.toString(strings.toArray());
      }

      return result;
   }
}
