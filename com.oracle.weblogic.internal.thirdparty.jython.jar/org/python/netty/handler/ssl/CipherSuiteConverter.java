package org.python.netty.handler.ssl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

final class CipherSuiteConverter {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CipherSuiteConverter.class);
   private static final Pattern JAVA_CIPHERSUITE_PATTERN = Pattern.compile("^(?:TLS|SSL)_((?:(?!_WITH_).)+)_WITH_(.*)_(.*)$");
   private static final Pattern OPENSSL_CIPHERSUITE_PATTERN = Pattern.compile("^(?:((?:(?:EXP-)?(?:(?:DHE|EDH|ECDH|ECDHE|SRP)-(?:DSS|RSA|ECDSA)|(?:ADH|AECDH|KRB5|PSK|SRP)))|EXP)-)?(.*)-(.*)$");
   private static final Pattern JAVA_AES_CBC_PATTERN = Pattern.compile("^(AES)_([0-9]+)_CBC$");
   private static final Pattern JAVA_AES_PATTERN = Pattern.compile("^(AES)_([0-9]+)_(.*)$");
   private static final Pattern OPENSSL_AES_CBC_PATTERN = Pattern.compile("^(AES)([0-9]+)$");
   private static final Pattern OPENSSL_AES_PATTERN = Pattern.compile("^(AES)([0-9]+)-(.*)$");
   private static final ConcurrentMap j2o = PlatformDependent.newConcurrentHashMap();
   private static final ConcurrentMap o2j = PlatformDependent.newConcurrentHashMap();

   static void clearCache() {
      j2o.clear();
      o2j.clear();
   }

   static boolean isJ2OCached(String key, String value) {
      return value.equals(j2o.get(key));
   }

   static boolean isO2JCached(String key, String protocol, String value) {
      Map p2j = (Map)o2j.get(key);
      return p2j == null ? false : value.equals(p2j.get(protocol));
   }

   static String toOpenSsl(Iterable javaCipherSuites) {
      StringBuilder buf = new StringBuilder();
      Iterator var2 = javaCipherSuites.iterator();

      while(var2.hasNext()) {
         String c = (String)var2.next();
         if (c == null) {
            break;
         }

         String converted = toOpenSsl(c);
         if (converted != null) {
            c = converted;
         }

         buf.append(c);
         buf.append(':');
      }

      if (buf.length() > 0) {
         buf.setLength(buf.length() - 1);
         return buf.toString();
      } else {
         return "";
      }
   }

   static String toOpenSsl(String javaCipherSuite) {
      String converted = (String)j2o.get(javaCipherSuite);
      return converted != null ? converted : cacheFromJava(javaCipherSuite);
   }

   private static String cacheFromJava(String javaCipherSuite) {
      String openSslCipherSuite = toOpenSslUncached(javaCipherSuite);
      if (openSslCipherSuite == null) {
         return null;
      } else {
         j2o.putIfAbsent(javaCipherSuite, openSslCipherSuite);
         String javaCipherSuiteSuffix = javaCipherSuite.substring(4);
         Map p2j = new HashMap(4);
         p2j.put("", javaCipherSuiteSuffix);
         p2j.put("SSL", "SSL_" + javaCipherSuiteSuffix);
         p2j.put("TLS", "TLS_" + javaCipherSuiteSuffix);
         o2j.put(openSslCipherSuite, p2j);
         logger.debug("Cipher suite mapping: {} => {}", javaCipherSuite, openSslCipherSuite);
         return openSslCipherSuite;
      }
   }

   static String toOpenSslUncached(String javaCipherSuite) {
      Matcher m = JAVA_CIPHERSUITE_PATTERN.matcher(javaCipherSuite);
      if (!m.matches()) {
         return null;
      } else {
         String handshakeAlgo = toOpenSslHandshakeAlgo(m.group(1));
         String bulkCipher = toOpenSslBulkCipher(m.group(2));
         String hmacAlgo = toOpenSslHmacAlgo(m.group(3));
         return handshakeAlgo.isEmpty() ? bulkCipher + '-' + hmacAlgo : handshakeAlgo + '-' + bulkCipher + '-' + hmacAlgo;
      }
   }

   private static String toOpenSslHandshakeAlgo(String handshakeAlgo) {
      boolean export = handshakeAlgo.endsWith("_EXPORT");
      if (export) {
         handshakeAlgo = handshakeAlgo.substring(0, handshakeAlgo.length() - 7);
      }

      if ("RSA".equals(handshakeAlgo)) {
         handshakeAlgo = "";
      } else if (handshakeAlgo.endsWith("_anon")) {
         handshakeAlgo = 'A' + handshakeAlgo.substring(0, handshakeAlgo.length() - 5);
      }

      if (export) {
         if (handshakeAlgo.isEmpty()) {
            handshakeAlgo = "EXP";
         } else {
            handshakeAlgo = "EXP-" + handshakeAlgo;
         }
      }

      return handshakeAlgo.replace('_', '-');
   }

   private static String toOpenSslBulkCipher(String bulkCipher) {
      if (bulkCipher.startsWith("AES_")) {
         Matcher m = JAVA_AES_CBC_PATTERN.matcher(bulkCipher);
         if (m.matches()) {
            return m.replaceFirst("$1$2");
         }

         m = JAVA_AES_PATTERN.matcher(bulkCipher);
         if (m.matches()) {
            return m.replaceFirst("$1$2-$3");
         }
      }

      if ("3DES_EDE_CBC".equals(bulkCipher)) {
         return "DES-CBC3";
      } else if (!"RC4_128".equals(bulkCipher) && !"RC4_40".equals(bulkCipher)) {
         if (!"DES40_CBC".equals(bulkCipher) && !"DES_CBC_40".equals(bulkCipher)) {
            return "RC2_CBC_40".equals(bulkCipher) ? "RC2-CBC" : bulkCipher.replace('_', '-');
         } else {
            return "DES-CBC";
         }
      } else {
         return "RC4";
      }
   }

   private static String toOpenSslHmacAlgo(String hmacAlgo) {
      return hmacAlgo;
   }

   static String toJava(String openSslCipherSuite, String protocol) {
      Map p2j = (Map)o2j.get(openSslCipherSuite);
      if (p2j == null) {
         p2j = cacheFromOpenSsl(openSslCipherSuite);
         if (p2j == null) {
            return null;
         }
      }

      String javaCipherSuite = (String)p2j.get(protocol);
      if (javaCipherSuite == null) {
         javaCipherSuite = protocol + '_' + (String)p2j.get("");
      }

      return javaCipherSuite;
   }

   private static Map cacheFromOpenSsl(String openSslCipherSuite) {
      String javaCipherSuiteSuffix = toJavaUncached(openSslCipherSuite);
      if (javaCipherSuiteSuffix == null) {
         return null;
      } else {
         String javaCipherSuiteSsl = "SSL_" + javaCipherSuiteSuffix;
         String javaCipherSuiteTls = "TLS_" + javaCipherSuiteSuffix;
         Map p2j = new HashMap(4);
         p2j.put("", javaCipherSuiteSuffix);
         p2j.put("SSL", javaCipherSuiteSsl);
         p2j.put("TLS", javaCipherSuiteTls);
         o2j.putIfAbsent(openSslCipherSuite, p2j);
         j2o.putIfAbsent(javaCipherSuiteTls, openSslCipherSuite);
         j2o.putIfAbsent(javaCipherSuiteSsl, openSslCipherSuite);
         logger.debug("Cipher suite mapping: {} => {}", javaCipherSuiteTls, openSslCipherSuite);
         logger.debug("Cipher suite mapping: {} => {}", javaCipherSuiteSsl, openSslCipherSuite);
         return p2j;
      }
   }

   static String toJavaUncached(String openSslCipherSuite) {
      Matcher m = OPENSSL_CIPHERSUITE_PATTERN.matcher(openSslCipherSuite);
      if (!m.matches()) {
         return null;
      } else {
         String handshakeAlgo = m.group(1);
         boolean export;
         if (handshakeAlgo == null) {
            handshakeAlgo = "";
            export = false;
         } else if (handshakeAlgo.startsWith("EXP-")) {
            handshakeAlgo = handshakeAlgo.substring(4);
            export = true;
         } else if ("EXP".equals(handshakeAlgo)) {
            handshakeAlgo = "";
            export = true;
         } else {
            export = false;
         }

         handshakeAlgo = toJavaHandshakeAlgo(handshakeAlgo, export);
         String bulkCipher = toJavaBulkCipher(m.group(2), export);
         String hmacAlgo = toJavaHmacAlgo(m.group(3));
         return handshakeAlgo + "_WITH_" + bulkCipher + '_' + hmacAlgo;
      }
   }

   private static String toJavaHandshakeAlgo(String handshakeAlgo, boolean export) {
      if (handshakeAlgo.isEmpty()) {
         handshakeAlgo = "RSA";
      } else if ("ADH".equals(handshakeAlgo)) {
         handshakeAlgo = "DH_anon";
      } else if ("AECDH".equals(handshakeAlgo)) {
         handshakeAlgo = "ECDH_anon";
      }

      handshakeAlgo = handshakeAlgo.replace('-', '_');
      return export ? handshakeAlgo + "_EXPORT" : handshakeAlgo;
   }

   private static String toJavaBulkCipher(String bulkCipher, boolean export) {
      if (bulkCipher.startsWith("AES")) {
         Matcher m = OPENSSL_AES_CBC_PATTERN.matcher(bulkCipher);
         if (m.matches()) {
            return m.replaceFirst("$1_$2_CBC");
         }

         m = OPENSSL_AES_PATTERN.matcher(bulkCipher);
         if (m.matches()) {
            return m.replaceFirst("$1_$2_$3");
         }
      }

      if ("DES-CBC3".equals(bulkCipher)) {
         return "3DES_EDE_CBC";
      } else if ("RC4".equals(bulkCipher)) {
         return export ? "RC4_40" : "RC4_128";
      } else if ("DES-CBC".equals(bulkCipher)) {
         return export ? "DES_CBC_40" : "DES_CBC";
      } else if ("RC2-CBC".equals(bulkCipher)) {
         return export ? "RC2_CBC_40" : "RC2_CBC";
      } else {
         return bulkCipher.replace('-', '_');
      }
   }

   private static String toJavaHmacAlgo(String hmacAlgo) {
      return hmacAlgo;
   }

   private CipherSuiteConverter() {
   }
}
