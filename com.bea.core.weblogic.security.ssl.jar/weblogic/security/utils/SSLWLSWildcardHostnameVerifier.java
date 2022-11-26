package weblogic.security.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLSession;

public class SSLWLSWildcardHostnameVerifier extends SSLWLSHostnameVerifier.DefaultHostnameVerifier {
   private static final String WILDCARD_DNSNAME_REGEX = "^\\*((\\.[^*.]+){2,})$";
   private static final Pattern WILDCARD_DNSNAME_PATTERN = Pattern.compile("^\\*((\\.[^*.]+){2,})$");
   private static final String URL_HOSTNAME_REGEX = "^[^*.\\s]+((\\.[^*.]+){2,})$";
   private static final Pattern URL_HOSTNAME_PATTERN = Pattern.compile("^[^*.\\s]+((\\.[^*.]+){2,})$");

   public SSLWLSWildcardHostnameVerifier() {
      if (SSLSetup.isDebugEnabled(3)) {
         SSLSetup.info("HostnameVerifier: allowing wildcarded certificates");
      }

   }

   public boolean verify(String urlhostname, SSLSession session) {
      boolean matched = false;
      if (urlhostname != null && session != null) {
         Collection wildcardDNSNames = SSLCertUtility.getDNSSubjAltNames(session, true, false);
         String certhostname = SSLCertUtility.getCommonName(session);
         if (wildcardDNSNames != null && wildcardDNSNames.size() > 0) {
            matched = this.verifyCNAfterSAN() ? verifySANWildcardDNSNames(urlhostname, wildcardDNSNames) || isLegalWildcarded(urlhostname, certhostname) : verifySANWildcardDNSNames(urlhostname, wildcardDNSNames);
         } else {
            matched = isLegalWildcarded(urlhostname, certhostname);
         }

         if (!matched) {
            matched = super.verify(urlhostname, session);
         }
      }

      return matched;
   }

   private static boolean isLegalWildcarded(String url, String cn) {
      if (cn != null) {
         if (cn.indexOf("*") == -1) {
            if (SSLSetup.isDebugEnabled(3)) {
               SSLSetup.info("HostnameVerifier: no wildcard present, wildcard validation not performed.");
            }

            return false;
         }

         if (cn.indexOf(".") != cn.lastIndexOf(".") && cn.startsWith("*.") && cn.indexOf("*") == cn.lastIndexOf("*") && domainMatchesDomain(cn, url)) {
            return true;
         }
      }

      return false;
   }

   private static boolean domainMatchesDomain(String cn, String url) {
      int ind = cn.indexOf("*");
      if (ind == -1) {
         return false;
      } else {
         String lStrippedCN = cn.substring(ind + 1).toLowerCase();
         String lUrl = url.toLowerCase();
         if (!lUrl.endsWith(lStrippedCN)) {
            return false;
         } else if (lUrl.lastIndexOf(lStrippedCN) == -1) {
            return false;
         } else {
            String urlBeginning = lUrl.substring(0, lUrl.length() - lStrippedCN.length());
            if (urlBeginning.length() <= 0) {
               return false;
            } else {
               return urlBeginning.indexOf(".") == -1;
            }
         }
      }
   }

   private static boolean verifySANWildcardDNSNames(String urlHostname, Collection wildcardDNSNames) {
      boolean matched = false;
      if (wildcardDNSNames != null && !wildcardDNSNames.isEmpty()) {
         Matcher urlHostnameMatcher = URL_HOSTNAME_PATTERN.matcher(urlHostname);
         boolean isURLHostnameValid = urlHostnameMatcher.matches();
         Iterator iter = wildcardDNSNames.iterator();

         while(iter.hasNext()) {
            String dnsName = (String)iter.next();
            Matcher wildCardDNSNameMatcher = WILDCARD_DNSNAME_PATTERN.matcher(dnsName);
            if (wildCardDNSNameMatcher.matches()) {
               String domainOfWildcardDNS = wildCardDNSNameMatcher.group(1);
               if (isURLHostnameValid) {
                  String domainOfURL = urlHostnameMatcher.group(1);
                  if (domainOfWildcardDNS != null && domainOfURL != null && domainOfWildcardDNS.equalsIgnoreCase(domainOfURL)) {
                     matched = true;
                     break;
                  }
               }
            }
         }
      }

      return matched;
   }
}
