package org.python.netty.handler.ssl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.handler.ssl.util.SelfSignedCertificate;
import org.python.netty.internal.tcnative.Buffer;
import org.python.netty.internal.tcnative.Library;
import org.python.netty.internal.tcnative.SSL;
import org.python.netty.internal.tcnative.SSLContext;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.ReferenceCounted;
import org.python.netty.util.internal.NativeLibraryLoader;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class OpenSsl {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSsl.class);
   private static final String LINUX = "linux";
   private static final String UNKNOWN = "unknown";
   private static final Throwable UNAVAILABILITY_CAUSE;
   static final Set AVAILABLE_CIPHER_SUITES;
   private static final Set AVAILABLE_OPENSSL_CIPHER_SUITES;
   private static final Set AVAILABLE_JAVA_CIPHER_SUITES;
   private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
   private static final boolean SUPPORTS_HOSTNAME_VALIDATION;
   private static final boolean USE_KEYMANAGER_FACTORY;
   private static final boolean SUPPORTS_OCSP;
   static final String PROTOCOL_SSL_V2_HELLO = "SSLv2Hello";
   static final String PROTOCOL_SSL_V2 = "SSLv2";
   static final String PROTOCOL_SSL_V3 = "SSLv3";
   static final String PROTOCOL_TLS_V1 = "TLSv1";
   static final String PROTOCOL_TLS_V1_1 = "TLSv1.1";
   static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
   static final Set SUPPORTED_PROTOCOLS_SET;

   private static boolean doesSupportOcsp() {
      boolean supportsOcsp = false;
      if ((long)version() >= 268443648L) {
         long sslCtx = -1L;

         try {
            sslCtx = SSLContext.make(16, 1);
            SSLContext.enableOcsp(sslCtx, false);
            supportsOcsp = true;
         } catch (Exception var7) {
         } finally {
            if (sslCtx != -1L) {
               SSLContext.free(sslCtx);
            }

         }
      }

      return supportsOcsp;
   }

   private static boolean doesSupportProtocol(int protocol) {
      long sslCtx = -1L;

      boolean var4;
      try {
         sslCtx = SSLContext.make(protocol, 2);
         boolean var3 = true;
         return var3;
      } catch (Exception var8) {
         var4 = false;
      } finally {
         if (sslCtx != -1L) {
            SSLContext.free(sslCtx);
         }

      }

      return var4;
   }

   public static boolean isAvailable() {
      return UNAVAILABILITY_CAUSE == null;
   }

   public static boolean isAlpnSupported() {
      return (long)version() >= 268443648L;
   }

   public static boolean isOcspSupported() {
      return SUPPORTS_OCSP;
   }

   public static int version() {
      return isAvailable() ? SSL.version() : -1;
   }

   public static String versionString() {
      return isAvailable() ? SSL.versionString() : null;
   }

   public static void ensureAvailability() {
      if (UNAVAILABILITY_CAUSE != null) {
         throw (Error)(new UnsatisfiedLinkError("failed to load the required native library")).initCause(UNAVAILABILITY_CAUSE);
      }
   }

   public static Throwable unavailabilityCause() {
      return UNAVAILABILITY_CAUSE;
   }

   /** @deprecated */
   @Deprecated
   public static Set availableCipherSuites() {
      return availableOpenSslCipherSuites();
   }

   public static Set availableOpenSslCipherSuites() {
      return AVAILABLE_OPENSSL_CIPHER_SUITES;
   }

   public static Set availableJavaCipherSuites() {
      return AVAILABLE_JAVA_CIPHER_SUITES;
   }

   public static boolean isCipherSuiteAvailable(String cipherSuite) {
      String converted = CipherSuiteConverter.toOpenSsl(cipherSuite);
      if (converted != null) {
         cipherSuite = converted;
      }

      return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(cipherSuite);
   }

   public static boolean supportsKeyManagerFactory() {
      return SUPPORTS_KEYMANAGER_FACTORY;
   }

   public static boolean supportsHostnameValidation() {
      return SUPPORTS_HOSTNAME_VALIDATION;
   }

   static boolean useKeyManagerFactory() {
      return USE_KEYMANAGER_FACTORY;
   }

   static long memoryAddress(ByteBuf buf) {
      assert buf.isDirect();

      return buf.hasMemoryAddress() ? buf.memoryAddress() : Buffer.address(buf.nioBuffer());
   }

   private OpenSsl() {
   }

   private static void loadTcNative() throws Exception {
      String os = normalizeOs(SystemPropertyUtil.get("os.name", ""));
      String arch = normalizeArch(SystemPropertyUtil.get("os.arch", ""));
      Set libNames = new LinkedHashSet(3);
      libNames.add("netty-tcnative-" + os + '-' + arch);
      if ("linux".equalsIgnoreCase(os)) {
         libNames.add("netty-tcnative-" + os + '-' + arch + "-fedora");
      }

      libNames.add("netty-tcnative");
      NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[])libNames.toArray(new String[libNames.size()]));
   }

   private static boolean initializeTcNative() throws Exception {
      return Library.initialize();
   }

   private static String normalizeOs(String value) {
      value = normalize(value);
      if (value.startsWith("aix")) {
         return "aix";
      } else if (value.startsWith("hpux")) {
         return "hpux";
      } else if (!value.startsWith("os400") || value.length() > 5 && Character.isDigit(value.charAt(5))) {
         if (value.startsWith("linux")) {
            return "linux";
         } else if (!value.startsWith("macosx") && !value.startsWith("osx")) {
            if (value.startsWith("freebsd")) {
               return "freebsd";
            } else if (value.startsWith("openbsd")) {
               return "openbsd";
            } else if (value.startsWith("netbsd")) {
               return "netbsd";
            } else if (!value.startsWith("solaris") && !value.startsWith("sunos")) {
               return value.startsWith("windows") ? "windows" : "unknown";
            } else {
               return "sunos";
            }
         } else {
            return "osx";
         }
      } else {
         return "os400";
      }
   }

   private static String normalizeArch(String value) {
      value = normalize(value);
      if (value.matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
         return "x86_64";
      } else if (value.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
         return "x86_32";
      } else if (value.matches("^(ia64|itanium64)$")) {
         return "itanium_64";
      } else if (value.matches("^(sparc|sparc32)$")) {
         return "sparc_32";
      } else if (value.matches("^(sparcv9|sparc64)$")) {
         return "sparc_64";
      } else if (value.matches("^(arm|arm32)$")) {
         return "arm_32";
      } else if ("aarch64".equals(value)) {
         return "aarch_64";
      } else if (value.matches("^(ppc|ppc32)$")) {
         return "ppc_32";
      } else if ("ppc64".equals(value)) {
         return "ppc_64";
      } else if ("ppc64le".equals(value)) {
         return "ppcle_64";
      } else if ("s390".equals(value)) {
         return "s390_32";
      } else {
         return "s390x".equals(value) ? "s390_64" : "unknown";
      }
   }

   private static String normalize(String value) {
      return value.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
   }

   static void releaseIfNeeded(ReferenceCounted counted) {
      if (counted.refCnt() > 0) {
         ReferenceCountUtil.safeRelease(counted);
      }

   }

   static {
      Throwable cause = null;

      try {
         Class.forName("org.python.netty.internal.tcnative.SSL", false, OpenSsl.class.getClassLoader());
      } catch (ClassNotFoundException var43) {
         cause = var43;
         logger.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
      }

      if (cause == null) {
         try {
            loadTcNative();
         } catch (Throwable var42) {
            cause = var42;
            logger.debug("Failed to load netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information.", var42);
         }

         try {
            initializeTcNative();
            cause = null;
         } catch (Throwable var47) {
            if (cause == null) {
               cause = var47;
            }

            logger.debug("Failed to initialize netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information.", var47);
         }
      }

      UNAVAILABILITY_CAUSE = (Throwable)cause;
      if (cause == null) {
         logger.debug("netty-tcnative using native library: {}", (Object)SSL.versionString());
         Set availableOpenSslCipherSuites = new LinkedHashSet(128);
         boolean supportsKeyManagerFactory = false;
         boolean useKeyManagerFactory = false;
         boolean supportsHostNameValidation = false;

         try {
            long sslCtx = SSLContext.make(31, 1);
            long certBio = 0L;
            SelfSignedCertificate cert = null;

            try {
               SSLContext.setCipherSuite(sslCtx, "ALL");
               long ssl = SSL.newSSL(sslCtx, true);

               try {
                  String[] var12 = SSL.getCiphers(ssl);
                  int var13 = var12.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     String c = var12[var14];
                     if (c != null && !c.isEmpty() && !availableOpenSslCipherSuites.contains(c)) {
                        availableOpenSslCipherSuites.add(c);
                     }
                  }

                  try {
                     SSL.setHostNameValidation(ssl, 0, "netty.io");
                     supportsHostNameValidation = true;
                  } catch (Throwable var41) {
                     logger.debug("Hostname Verification not supported.");
                  }

                  try {
                     cert = new SelfSignedCertificate();
                     certBio = ReferenceCountedOpenSslContext.toBIO(cert.cert());
                     SSL.setCertificateChainBio(ssl, certBio, false);
                     supportsKeyManagerFactory = true;

                     try {
                        useKeyManagerFactory = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
                           public Boolean run() {
                              return SystemPropertyUtil.getBoolean("org.python.netty.handler.ssl.openssl.useKeyManagerFactory", true);
                           }
                        });
                     } catch (Throwable var39) {
                        logger.debug("Failed to get useKeyManagerFactory system property.");
                     }
                  } catch (Throwable var40) {
                     logger.debug("KeyManagerFactory not supported.");
                  }
               } finally {
                  SSL.freeSSL(ssl);
                  if (certBio != 0L) {
                     SSL.freeBIO(certBio);
                  }

                  if (cert != null) {
                     cert.delete();
                  }

               }
            } finally {
               SSLContext.free(sslCtx);
            }
         } catch (Exception var46) {
            logger.warn("Failed to get the list of available OpenSSL cipher suites.", (Throwable)var46);
         }

         AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.unmodifiableSet(availableOpenSslCipherSuites);
         Set availableJavaCipherSuites = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() * 2);
         Iterator var19 = AVAILABLE_OPENSSL_CIPHER_SUITES.iterator();

         while(var19.hasNext()) {
            String cipher = (String)var19.next();
            availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, "TLS"));
            availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, "SSL"));
         }

         AVAILABLE_JAVA_CIPHER_SUITES = Collections.unmodifiableSet(availableJavaCipherSuites);
         Set availableCipherSuites = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() + AVAILABLE_JAVA_CIPHER_SUITES.size());
         availableCipherSuites.addAll(AVAILABLE_OPENSSL_CIPHER_SUITES);
         availableCipherSuites.addAll(AVAILABLE_JAVA_CIPHER_SUITES);
         AVAILABLE_CIPHER_SUITES = availableCipherSuites;
         SUPPORTS_KEYMANAGER_FACTORY = supportsKeyManagerFactory;
         SUPPORTS_HOSTNAME_VALIDATION = supportsHostNameValidation;
         USE_KEYMANAGER_FACTORY = useKeyManagerFactory;
         Set protocols = new LinkedHashSet(6);
         protocols.add("SSLv2Hello");
         if (doesSupportProtocol(1)) {
            protocols.add("SSLv2");
         }

         if (doesSupportProtocol(2)) {
            protocols.add("SSLv3");
         }

         if (doesSupportProtocol(4)) {
            protocols.add("TLSv1");
         }

         if (doesSupportProtocol(8)) {
            protocols.add("TLSv1.1");
         }

         if (doesSupportProtocol(16)) {
            protocols.add("TLSv1.2");
         }

         SUPPORTED_PROTOCOLS_SET = Collections.unmodifiableSet(protocols);
         SUPPORTS_OCSP = doesSupportOcsp();
      } else {
         AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
         AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
         AVAILABLE_CIPHER_SUITES = Collections.emptySet();
         SUPPORTS_KEYMANAGER_FACTORY = false;
         SUPPORTS_HOSTNAME_VALIDATION = false;
         USE_KEYMANAGER_FACTORY = false;
         SUPPORTED_PROTOCOLS_SET = Collections.emptySet();
         SUPPORTS_OCSP = false;
      }

   }
}
