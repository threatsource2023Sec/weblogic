package org.python.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;

final class Java8SslUtils {
   private Java8SslUtils() {
   }

   static List getSniHostNames(SSLParameters sslParameters) {
      List names = sslParameters.getServerNames();
      if (names != null && !names.isEmpty()) {
         List strings = new ArrayList(names.size());
         Iterator var3 = names.iterator();

         while(var3.hasNext()) {
            SNIServerName serverName = (SNIServerName)var3.next();
            if (!(serverName instanceof SNIHostName)) {
               throw new IllegalArgumentException("Only " + SNIHostName.class.getName() + " instances are supported, but found: " + serverName);
            }

            strings.add(((SNIHostName)serverName).getAsciiName());
         }

         return strings;
      } else {
         return Collections.emptyList();
      }
   }

   static void setSniHostNames(SSLParameters sslParameters, List names) {
      List sniServerNames = new ArrayList(names.size());
      Iterator var3 = names.iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         sniServerNames.add(new SNIHostName(name));
      }

      sslParameters.setServerNames(sniServerNames);
   }

   static boolean getUseCipherSuitesOrder(SSLParameters sslParameters) {
      return sslParameters.getUseCipherSuitesOrder();
   }

   static void setUseCipherSuitesOrder(SSLParameters sslParameters, boolean useOrder) {
      sslParameters.setUseCipherSuitesOrder(useOrder);
   }

   static void setSNIMatchers(SSLParameters sslParameters, Collection matchers) {
      sslParameters.setSNIMatchers(matchers);
   }

   static boolean checkSniHostnameMatch(Collection matchers, String hostname) {
      if (matchers != null && !matchers.isEmpty()) {
         SNIHostName name = new SNIHostName(hostname);
         Iterator matcherIt = matchers.iterator();

         SNIMatcher matcher;
         do {
            if (!matcherIt.hasNext()) {
               return false;
            }

            matcher = (SNIMatcher)matcherIt.next();
         } while(matcher.getType() != 0 || !matcher.matches(name));

         return true;
      } else {
         return true;
      }
   }
}
