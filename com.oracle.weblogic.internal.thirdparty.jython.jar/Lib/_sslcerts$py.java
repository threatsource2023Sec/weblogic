import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_sslcerts.py")
public class _sslcerts$py extends PyFunctionTable implements PyRunnable {
   static _sslcerts$py self;
   static final PyCode f$0;
   static final PyCode _get_ca_certs_trust_manager$1;
   static final PyCode _stringio_as_reader$2;
   static final PyCode _extract_readers$3;
   static final PyCode _get_openssl_key_manager$4;
   static final PyCode _str_hash_key_entry$5;
   static final PyCode _parse_password$6;
   static final PyCode _extract_certs_from_keystore_file$7;
   static final PyCode _extract_certs_for_paths$8;
   static final PyCode _extract_cert_from_data$9;
   static final PyCode _read_pem_cert_from_data$10;
   static final PyCode _is_cert_pem$11;
   static final PyCode _get_ecdh_parameter_spec$12;
   static final PyCode CompositeX509KeyManager$13;
   static final PyCode __init__$14;
   static final PyCode chooseClientAlias$15;
   static final PyCode chooseServerAlias$16;
   static final PyCode getPrivateKey$17;
   static final PyCode getCertificateChain$18;
   static final PyCode getClientAliases$19;
   static final PyCode getServerAliases$20;
   static final PyCode CompositeX509TrustManager$21;
   static final PyCode __init__$22;
   static final PyCode checkClientTrusted$23;
   static final PyCode checkServerTrusted$24;
   static final PyCode getAcceptedIssuers$25;
   static final PyCode CompositeX509TrustManagerFactory$26;
   static final PyCode __init__$27;
   static final PyCode engineInit$28;
   static final PyCode engineGetTrustManagers$29;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("uuid", var1, -1);
      var1.setlocal("uuid", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(4);
      String[] var8 = new String[]{"StringIO"};
      PyObject[] var9 = imp.importFrom("StringIO", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(5);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(7);
      var8 = new String[]{"RuntimeException", "System"};
      var9 = imp.importFrom("java.lang", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("RuntimeException", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("System", var4);
      var4 = null;
      var1.setline(8);
      var8 = new String[]{"BufferedInputStream", "BufferedReader", "FileReader", "InputStreamReader", "ByteArrayInputStream", "IOException"};
      var9 = imp.importFrom("java.io", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("BufferedInputStream", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("BufferedReader", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("FileReader", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("InputStreamReader", var4);
      var4 = null;
      var4 = var9[4];
      var1.setlocal("ByteArrayInputStream", var4);
      var4 = null;
      var4 = var9[5];
      var1.setlocal("IOException", var4);
      var4 = null;
      var1.setline(9);
      var8 = new String[]{"KeyStore", "Security", "InvalidAlgorithmParameterException"};
      var9 = imp.importFrom("java.security", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("KeyStore", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("Security", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("InvalidAlgorithmParameterException", var4);
      var4 = null;
      var1.setline(10);
      var8 = new String[]{"CertificateException", "CertificateFactory"};
      var9 = imp.importFrom("java.security.cert", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("CertificateException", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("CertificateFactory", var4);
      var4 = null;
      var1.setline(11);
      var8 = new String[]{"RSAPrivateCrtKey"};
      var9 = imp.importFrom("java.security.interfaces", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("RSAPrivateCrtKey", var4);
      var4 = null;
      var1.setline(12);
      var8 = new String[]{"RSAPublicKey"};
      var9 = imp.importFrom("java.security.interfaces", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("RSAPublicKey", var4);
      var4 = null;
      var1.setline(13);
      var8 = new String[]{"X509KeyManager", "X509TrustManager", "KeyManagerFactory", "SSLContext"};
      var9 = imp.importFrom("javax.net.ssl", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("X509KeyManager", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("X509TrustManager", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("KeyManagerFactory", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("SSLContext", var4);
      var4 = null;

      PyObject var5;
      PyException var10;
      String[] var11;
      PyObject[] var12;
      try {
         var1.setline(17);
         var8 = new String[]{"SimpleTrustManagerFactory"};
         var9 = imp.importFrom("org.python.netty.handler.ssl.util", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("SimpleTrustManagerFactory", var4);
         var4 = null;
      } catch (Throwable var7) {
         var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(21);
         var11 = new String[]{"SimpleTrustManagerFactory"};
         var12 = imp.importFrom("io.netty.handler.ssl.util", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("SimpleTrustManagerFactory", var5);
         var5 = null;
      }

      try {
         var1.setline(37);
         var8 = new String[]{"PrivateKeyInfo"};
         var9 = imp.importFrom("org.bouncycastle.asn1.pkcs", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("PrivateKeyInfo", var4);
         var4 = null;
         var1.setline(38);
         var8 = new String[]{"X509CertificateHolder"};
         var9 = imp.importFrom("org.bouncycastle.cert", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("X509CertificateHolder", var4);
         var4 = null;
         var1.setline(39);
         var8 = new String[]{"JcaX509CertificateConverter"};
         var9 = imp.importFrom("org.bouncycastle.cert.jcajce", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("JcaX509CertificateConverter", var4);
         var4 = null;
         var1.setline(40);
         var8 = new String[]{"BouncyCastleProvider"};
         var9 = imp.importFrom("org.bouncycastle.jce.provider", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("BouncyCastleProvider", var4);
         var4 = null;
         var1.setline(41);
         var8 = new String[]{"ECNamedCurveTable"};
         var9 = imp.importFrom("org.bouncycastle.jce", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("ECNamedCurveTable", var4);
         var4 = null;
         var1.setline(42);
         var8 = new String[]{"ECNamedCurveSpec"};
         var9 = imp.importFrom("org.bouncycastle.jce.spec", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("ECNamedCurveSpec", var4);
         var4 = null;
         var1.setline(43);
         var8 = new String[]{"PEMKeyPair", "PEMParser", "PEMEncryptedKeyPair", "PEMException", "EncryptionException"};
         var9 = imp.importFrom("org.bouncycastle.openssl", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("PEMKeyPair", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("PEMParser", var4);
         var4 = null;
         var4 = var9[2];
         var1.setlocal("PEMEncryptedKeyPair", var4);
         var4 = null;
         var4 = var9[3];
         var1.setlocal("PEMException", var4);
         var4 = null;
         var4 = var9[4];
         var1.setlocal("EncryptionException", var4);
         var4 = null;
         var1.setline(45);
         var8 = new String[]{"JcaPEMKeyConverter", "JcePEMDecryptorProviderBuilder"};
         var9 = imp.importFrom("org.bouncycastle.openssl.jcajce", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("JcaPEMKeyConverter", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("JcePEMDecryptorProviderBuilder", var4);
         var4 = null;
      } catch (Throwable var6) {
         var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(48);
         var11 = new String[]{"PrivateKeyInfo"};
         var12 = imp.importFrom("org.python.bouncycastle.asn1.pkcs", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("PrivateKeyInfo", var5);
         var5 = null;
         var1.setline(49);
         var11 = new String[]{"X509CertificateHolder"};
         var12 = imp.importFrom("org.python.bouncycastle.cert", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("X509CertificateHolder", var5);
         var5 = null;
         var1.setline(50);
         var11 = new String[]{"JcaX509CertificateConverter"};
         var12 = imp.importFrom("org.python.bouncycastle.cert.jcajce", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("JcaX509CertificateConverter", var5);
         var5 = null;
         var1.setline(51);
         var11 = new String[]{"BouncyCastleProvider"};
         var12 = imp.importFrom("org.python.bouncycastle.jce.provider", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("BouncyCastleProvider", var5);
         var5 = null;
         var1.setline(52);
         var11 = new String[]{"ECNamedCurveTable"};
         var12 = imp.importFrom("org.python.bouncycastle.jce", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("ECNamedCurveTable", var5);
         var5 = null;
         var1.setline(53);
         var11 = new String[]{"ECNamedCurveSpec"};
         var12 = imp.importFrom("org.python.bouncycastle.jce.spec", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("ECNamedCurveSpec", var5);
         var5 = null;
         var1.setline(54);
         var11 = new String[]{"PEMKeyPair", "PEMParser", "PEMEncryptedKeyPair", "PEMException", "EncryptionException"};
         var12 = imp.importFrom("org.python.bouncycastle.openssl", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("PEMKeyPair", var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal("PEMParser", var5);
         var5 = null;
         var5 = var12[2];
         var1.setlocal("PEMEncryptedKeyPair", var5);
         var5 = null;
         var5 = var12[3];
         var1.setlocal("PEMException", var5);
         var5 = null;
         var5 = var12[4];
         var1.setlocal("EncryptionException", var5);
         var5 = null;
         var1.setline(56);
         var11 = new String[]{"JcaPEMKeyConverter", "JcePEMDecryptorProviderBuilder"};
         var12 = imp.importFrom("org.python.bouncycastle.openssl.jcajce", var11, var1, -1);
         var5 = var12[0];
         var1.setlocal("JcaPEMKeyConverter", var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal("JcePEMDecryptorProviderBuilder", var5);
         var5 = null;
      }

      var1.setline(58);
      var3 = var1.getname("logging").__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_socket"));
      var1.setlocal("log", var3);
      var3 = null;
      var1.setline(59);
      var1.getname("Security").__getattr__("addProvider").__call__(var2, var1.getname("BouncyCastleProvider").__call__(var2));
      var1.setline(61);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^-----BEGIN.*(PRIVATE KEY|CERTIFICATE)-----$"));
      var1.setlocal("RE_BEGIN_KEY_CERT", var3);
      var3 = null;
      var1.setline(64);
      var9 = new PyObject[]{var1.getname("None")};
      PyFunction var13 = new PyFunction(var1.f_globals, var9, _get_ca_certs_trust_manager$1, (PyObject)null);
      var1.setlocal("_get_ca_certs_trust_manager", var13);
      var3 = null;
      var1.setline(80);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _stringio_as_reader$2, (PyObject)null);
      var1.setlocal("_stringio_as_reader", var13);
      var3 = null;
      var1.setline(84);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _extract_readers$3, (PyObject)null);
      var1.setlocal("_extract_readers", var13);
      var3 = null;
      var1.setline(107);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var9, _get_openssl_key_manager$4, (PyObject)null);
      var1.setlocal("_get_openssl_key_manager", var13);
      var3 = null;
      var1.setline(152);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _str_hash_key_entry$5, PyString.fromInterned("Very naiive"));
      var1.setlocal("_str_hash_key_entry", var13);
      var3 = null;
      var1.setline(162);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _parse_password$6, (PyObject)null);
      var1.setlocal("_parse_password", var13);
      var3 = null;
      var1.setline(181);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _extract_certs_from_keystore_file$7, (PyObject)null);
      var1.setlocal("_extract_certs_from_keystore_file", var13);
      var3 = null;
      var1.setline(201);
      var9 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var9, _extract_certs_for_paths$8, (PyObject)null);
      var1.setlocal("_extract_certs_for_paths", var13);
      var3 = null;
      var1.setline(224);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var9, _extract_cert_from_data$9, (PyObject)null);
      var1.setlocal("_extract_cert_from_data", var13);
      var3 = null;
      var1.setline(245);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _read_pem_cert_from_data$10, (PyObject)null);
      var1.setlocal("_read_pem_cert_from_data", var13);
      var3 = null;
      var1.setline(284);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _is_cert_pem$11, (PyObject)null);
      var1.setlocal("_is_cert_pem", var13);
      var3 = null;
      var1.setline(297);
      var9 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var9, _get_ecdh_parameter_spec$12, (PyObject)null);
      var1.setlocal("_get_ecdh_parameter_spec", var13);
      var3 = null;
      var1.setline(318);
      var9 = new PyObject[]{var1.getname("X509KeyManager")};
      var4 = Py.makeClass("CompositeX509KeyManager", var9, CompositeX509KeyManager$13);
      var1.setlocal("CompositeX509KeyManager", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(370);
      var9 = new PyObject[]{var1.getname("X509TrustManager")};
      var4 = Py.makeClass("CompositeX509TrustManager", var9, CompositeX509TrustManager$21);
      var1.setlocal("CompositeX509TrustManager", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(414);
      var9 = new PyObject[]{var1.getname("SimpleTrustManagerFactory")};
      var4 = Py.makeClass("CompositeX509TrustManagerFactory", var9, CompositeX509TrustManagerFactory$26);
      var1.setlocal("CompositeX509TrustManagerFactory", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_ca_certs_trust_manager$1(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(65);
      PyObject var3 = var1.getglobal("KeyStore").__getattr__("getInstance").__call__(var2, var1.getglobal("KeyStore").__getattr__("getDefaultType").__call__(var2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(66);
      var1.getlocal(1).__getattr__("load").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.setline(67);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(68);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         label31: {
            ContextManager var9;
            PyObject var4 = (var9 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(0)))).__enter__(var2);

            try {
               var1.setlocal(3, var4);
               var1.setline(70);
               var4 = var1.getglobal("CertificateFactory").__getattr__("getInstance").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X.509"));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(71);
               var4 = var1.getlocal(4).__getattr__("generateCertificates").__call__(var2, var1.getglobal("BufferedInputStream").__call__(var2, var1.getlocal(3))).__iter__();

               while(true) {
                  var1.setline(71);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var1.setlocal(5, var5);
                  var1.setline(72);
                  var1.getlocal(1).__getattr__("setCertificateEntry").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("uuid").__getattr__("uuid4").__call__(var2)), var1.getlocal(5));
                  var1.setline(73);
                  PyObject var6 = var1.getlocal(2);
                  var6 = var6._iadd(Py.newInteger(1));
                  var1.setlocal(2, var6);
               }
            } catch (Throwable var7) {
               if (var9.__exit__(var2, Py.setException(var7, var1))) {
                  break label31;
               }

               throw (Throwable)Py.makeException();
            }

            var9.__exit__(var2, (PyException)null);
         }
      }

      var1.setline(74);
      var3 = var1.getglobal("SimpleTrustManagerFactory").__getattr__("getInstance").__call__(var2, var1.getglobal("SimpleTrustManagerFactory").__getattr__("getDefaultAlgorithm").__call__(var2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(75);
      var1.getlocal(6).__getattr__("init").__call__(var2, var1.getlocal(1));
      var1.setline(76);
      var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var11 = new PyObject[]{PyString.fromInterned("Installed %s certificates"), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
      String[] var10 = new String[]{"extra"};
      var10000.__call__(var2, var11, var10);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _stringio_as_reader$2(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getglobal("BufferedReader").__call__(var2, var1.getglobal("InputStreamReader").__call__(var2, var1.getglobal("ByteArrayInputStream").__call__(var2, var1.getglobal("bytearray").__call__(var2, var1.getlocal(0).__getattr__("getvalue").__call__(var2)))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _extract_readers$3(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(86);
      PyObject var6 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(87);
      var6 = var1.getglobal("False");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(88);
      var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(88);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(97);
            if (var1.getlocal(2).__getattr__("getvalue").__call__(var2).__nonzero__()) {
               var1.setline(98);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
            }

            var1.setline(100);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(101);
               String[] var7 = new String[]{"SSLError", "SSL_ERROR_SSL"};
               PyObject[] var8 = imp.importFrom("_socket", var7, var1, -1);
               var4 = var8[0];
               var1.setlocal(5, var4);
               var4 = null;
               var4 = var8[1];
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(102);
               throw Py.makeException(var1.getlocal(5).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("PEM lib (no start line or not enough data)")));
            } else {
               var1.setline(104);
               PyList var10000 = new PyList();
               var6 = var10000.__getattr__("append");
               var1.setlocal(7, var6);
               var3 = null;
               var1.setline(104);
               var6 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(104);
                  var4 = var6.__iternext__();
                  if (var4 == null) {
                     var1.setline(104);
                     var1.dellocal(7);
                     var3 = var10000;
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(8, var4);
                  var1.setline(104);
                  var1.getlocal(7).__call__(var2, var1.getglobal("_stringio_as_reader").__call__(var2, var1.getlocal(8)));
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(89);
         if (var1.getglobal("RE_BEGIN_KEY_CERT").__getattr__("match").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(90);
            PyObject var5 = var1.getglobal("True");
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(91);
            if (var1.getlocal(2).__getattr__("getvalue").__call__(var2).__nonzero__()) {
               var1.setline(92);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
            }

            var1.setline(94);
            var5 = var1.getglobal("StringIO").__call__(var2);
            var1.setlocal(2, var5);
            var5 = null;
         }

         var1.setline(95);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(4));
      }
   }

   public PyObject _get_openssl_key_manager$4(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), var1.getglobal("None")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(110);
      PyObject var6 = var1.getlocal(3);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(111);
         var6 = var1.getglobal("KeyStore").__getattr__("getInstance").__call__(var2, var1.getglobal("KeyStore").__getattr__("getDefaultType").__call__(var2));
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(112);
         var1.getlocal(3).__getattr__("load").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      }

      var1.setline(114);
      var6 = var1.getlocal(1);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject var7;
      PyObject[] var9;
      String[] var10;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var6 = var1.getglobal("_extract_certs_for_paths").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})), (PyObject)var1.getlocal(2));
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(116);
         var6 = var1.getlocal(5);
         var10000 = var6._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(117);
            var10 = new String[]{"SSLError", "SSL_ERROR_SSL"};
            var9 = imp.importFrom("_socket", var10, var1, -1);
            var7 = var9[0];
            var1.setlocal(6, var7);
            var4 = null;
            var7 = var9[1];
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(118);
            throw Py.makeException(var1.getlocal(6).__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("PEM lib (No private key loaded)")));
         }
      }

      var1.setline(120);
      var6 = var1.getlocal(0);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(121);
         var6 = var1.getglobal("_extract_certs_for_paths").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(0)})), (PyObject)var1.getlocal(2));
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(9, var5);
         var5 = null;
         var3 = null;
         var1.setline(122);
         var1.setline(122);
         var6 = var1.getlocal(9).__nonzero__() ? var1.getlocal(9) : var1.getlocal(5);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(123);
         var1.getlocal(4).__getattr__("extend").__call__(var2, var1.getlocal(8));
         var1.setline(125);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(126);
            var10 = new String[]{"SSLError", "SSL_ERROR_SSL"};
            var9 = imp.importFrom("_socket", var10, var1, -1);
            var7 = var9[0];
            var1.setlocal(6, var7);
            var4 = null;
            var7 = var9[1];
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(127);
            throw Py.makeException(var1.getlocal(6).__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("PEM lib (No private key loaded)")));
         }

         var1.setline(129);
         var3 = new PyTuple(new PyObject[]{var1.getglobal("False"), var1.getglobal("False")});
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(130);
         var6 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(130);
            var7 = var6.__iternext__();
            if (var7 == null) {
               break;
            }

            var1.setlocal(12, var7);
            var1.setline(132);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(12).__getattr__("publicKey"), var1.getglobal("RSAPublicKey"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("RSAPrivateCrtKey"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(133);
               var5 = var1.getglobal("True");
               var1.setlocal(11, var5);
               var5 = null;
            }

            var1.setline(135);
            if (var1.getlocal(11).__nonzero__()) {
               var1.setline(136);
               var5 = var1.getlocal(12).__getattr__("publicKey").__getattr__("getModulus").__call__(var2);
               var10000 = var5._eq(var1.getlocal(5).__getattr__("getModulus").__call__(var2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(12).__getattr__("publicKey").__getattr__("getPublicExponent").__call__(var2);
                  var10000 = var5._eq(var1.getlocal(5).__getattr__("getPublicExponent").__call__(var2));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(138);
                  var5 = var1.getglobal("True");
                  var1.setlocal(10, var5);
                  var5 = null;
                  break;
               }
            }
         }

         var1.setline(141);
         var6 = var1.getlocal(1);
         var10000 = var6._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(11);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(10).__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(142);
            var10 = new String[]{"SSLError", "SSL_ERROR_SSL"};
            var9 = imp.importFrom("_socket", var10, var1, -1);
            var7 = var9[0];
            var1.setlocal(6, var7);
            var4 = null;
            var7 = var9[1];
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(143);
            throw Py.makeException(var1.getlocal(6).__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("key values mismatch")));
         }

         var1.setline(145);
         var10000 = var1.getlocal(3).__getattr__("setKeyEntry");
         PyObject var10002 = var1.getglobal("_str_hash_key_entry");
         var9 = new PyObject[]{var1.getlocal(5)};
         String[] var8 = new String[0];
         var10002 = var10002._callextra(var9, var8, var1.getlocal(4), (PyObject)null);
         var3 = null;
         var10000.__call__(var2, var10002, var1.getlocal(5), new PyList(Py.EmptyObjects), var1.getlocal(4));
      }

      var1.setline(147);
      var6 = var1.getglobal("KeyManagerFactory").__getattr__("getInstance").__call__(var2, var1.getglobal("KeyManagerFactory").__getattr__("getDefaultAlgorithm").__call__(var2));
      var1.setlocal(13, var6);
      var3 = null;
      var1.setline(148);
      var1.getlocal(13).__getattr__("init").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(149);
      var6 = var1.getlocal(13);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _str_hash_key_entry$5(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Very naiive");
      var1.setline(154);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(155);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(159);
            var6 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(156);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(157);
            PyObject var5 = var1.getlocal(1);
            var5 = var5._iadd(var1.getglobal("hash").__call__(var2, var1.getlocal(2).__getattr__("toString").__call__(var2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"))));
            var1.setlocal(1, var5);
         }
      }
   }

   public PyObject _parse_password$6(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(164);
      PyObject var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("types").__getattr__("FunctionType"), var1.getglobal("types").__getattr__("MethodType")})));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__call__"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(165);
         var3 = var1.getlocal(0).__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(166);
         var3 = var1.getglobal("True");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(167);
      var3 = var1.getlocal(0);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(168);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(0, var4);
         var3 = null;
      } else {
         var1.setline(169);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("bytearray")}))).__nonzero__()) {
            var1.setline(174);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(175);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PEM lib (must return a string")));
            }

            var1.setline(177);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PEM lib (password should be a string)")));
         }

         var1.setline(170);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(171);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._ge(Py.newInteger(102400));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PEM lib (password cannot be longer than 102400 -1)")));
         }
      }

      var1.setline(178);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _extract_certs_from_keystore_file$7(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getglobal("KeyStore").__getattr__("getInstance").__call__(var2, var1.getglobal("KeyStore").__getattr__("getDefaultType").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyList var5;
      if (var10000.__nonzero__()) {
         var1.setline(184);
         var3 = var1.getglobal("System").__getattr__("getProperty").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("javax.net.ssl.trustStorePassword"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(185);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(186);
            PyString var4 = PyString.fromInterned("changeit");
            var1.setlocal(1, var4);
            var3 = null;
         }
      } else {
         var1.setline(187);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
            var1.setline(188);
            var5 = new PyList(Py.EmptyObjects);
            var1.setlocal(1, var5);
            var3 = null;
         }
      }

      var1.setline(190);
      var1.getlocal(2).__getattr__("load").__call__(var2, var1.getglobal("BufferedInputStream").__call__(var2, var1.getlocal(0)), var1.getlocal(1));
      var1.setline(191);
      var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(193);
      var3 = var1.getlocal(2).__getattr__("aliases").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(194);
         if (!var1.getlocal(4).__getattr__("hasMoreElements").__call__(var2).__nonzero__()) {
            var1.setline(198);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(195);
         var3 = var1.getlocal(4).__getattr__("nextElement").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(196);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("getCertificate").__call__(var2, var1.getlocal(5)));
      }
   }

   public PyObject _extract_certs_for_paths$8(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(203);
      PyObject var3 = var1.getglobal("JcaPEMKeyConverter").__call__(var2).__getattr__("setProvider").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BC"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(204);
      var3 = var1.getglobal("JcaX509CertificateConverter").__call__(var2).__getattr__("setProvider").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BC"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(205);
      PyList var12 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var12);
      var3 = null;
      var1.setline(206);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(207);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(221);
            PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var14;
         }

         var1.setlocal(6, var4);
         var1.setline(208);
         PyObject var5 = var1.getglobal("None");
         var1.setlocal(7, var5);
         var5 = null;
         ContextManager var13;
         PyObject var6 = (var13 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(6)))).__enter__(var2);

         label53: {
            try {
               var1.setlocal(8, var6);

               try {
                  var1.setline(212);
                  var6 = var1.getglobal("_extract_certs_from_keystore_file").__call__(var2, var1.getlocal(8), var1.getlocal(1));
                  var1.setlocal(9, var6);
                  var6 = null;
                  var1.setline(213);
                  var1.getlocal(4).__getattr__("extend").__call__(var2, var1.getlocal(9));
               } catch (Throwable var10) {
                  PyException var15 = Py.setException(var10, var1);
                  if (!var15.match(var1.getglobal("IOException"))) {
                     throw var15;
                  }

                  PyObject var7 = var15.value;
                  var1.setlocal(7, var7);
                  var7 = null;
                  var1.setline(215);
               }
            } catch (Throwable var11) {
               if (var13.__exit__(var2, Py.setException(var11, var1))) {
                  break label53;
               }

               throw (Throwable)Py.makeException();
            }

            var13.__exit__(var2, (PyException)null);
         }

         var1.setline(216);
         var5 = var1.getlocal(7);
         PyObject var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var6 = (var13 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(6)))).__enter__(var2);

            try {
               var1.setlocal(8, var6);
               var1.setline(218);
               var6 = var1.getglobal("_extract_cert_from_data").__call__(var2, var1.getlocal(8), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
               PyObject[] var16 = Py.unpackSequence(var6, 2);
               PyObject var8 = var16[0];
               var1.setlocal(9, var8);
               var8 = null;
               var8 = var16[1];
               var1.setlocal(10, var8);
               var8 = null;
               var6 = null;
               var1.setline(219);
               var1.setline(219);
               var6 = var1.getlocal(10).__nonzero__() ? var1.getlocal(10) : var1.getlocal(5);
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(220);
               var1.getlocal(4).__getattr__("extend").__call__(var2, var1.getlocal(9));
            } catch (Throwable var9) {
               if (var13.__exit__(var2, Py.setException(var9, var1))) {
                  continue;
               }

               throw (Throwable)Py.makeException();
            }

            var13.__exit__(var2, (PyException)null);
         }
      }
   }

   public PyObject _extract_cert_from_data$9(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(226);
      PyObject var6 = var1.getglobal("None");
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(228);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(229);
         var6 = var1.getglobal("StringIO").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
         var1.setlocal(0, var6);
         var3 = null;
      } else {
         var1.setline(230);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
            var1.setline(231);
            var6 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var6);
            var3 = null;
         }
      }

      var1.setline(233);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("seek")).__not__().__nonzero__()) {
         var1.setline(234);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PEM lib (data must be a file like object, string or bytes")));
      } else {
         var1.setline(236);
         if (var1.getglobal("_is_cert_pem").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(237);
            var6 = var1.getglobal("_read_pem_cert_from_data").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            PyObject[] var4 = Py.unpackSequence(var6, 2);
            PyObject var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(239);
            var6 = var1.getglobal("CertificateFactory").__getattr__("getInstance").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X.509"));
            var1.setlocal(6, var6);
            var3 = null;
            var1.setline(240);
            var6 = var1.getglobal("list").__call__(var2, var1.getlocal(6).__getattr__("generateCertificates").__call__(var2, var1.getglobal("ByteArrayInputStream").__call__(var2, var1.getlocal(0).__getattr__("read").__call__(var2))));
            var1.setlocal(4, var6);
            var3 = null;
         }

         var1.setline(242);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _read_pem_cert_from_data$10(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(247);
      PyObject var10 = var1.getglobal("None");
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(249);
      var10 = var1.getlocal(2);
      PyObject var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(250);
         var10 = var1.getglobal("JcaPEMKeyConverter").__call__(var2).__getattr__("setProvider").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BC"));
         var1.setlocal(2, var10);
         var3 = null;
      }

      var1.setline(251);
      var10 = var1.getlocal(3);
      var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(252);
         var10 = var1.getglobal("JcaX509CertificateConverter").__call__(var2).__getattr__("setProvider").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BC"));
         var1.setlocal(3, var10);
         var3 = null;
      }

      var1.setline(253);
      var10 = var1.getglobal("_extract_readers").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(253);
         PyObject var4 = var10.__iternext__();
         if (var4 == null) {
            var1.setline(281);
            PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var14;
         }

         var1.setlocal(6, var4);

         while(true) {
            var1.setline(254);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            PyException var5;
            PyObject var6;
            PyObject var7;
            PyObject var11;
            String[] var12;
            PyObject[] var13;
            try {
               var1.setline(256);
               var11 = var1.getglobal("PEMParser").__call__(var2, var1.getlocal(6)).__getattr__("readObject").__call__(var2);
               var1.setlocal(7, var11);
               var5 = null;
            } catch (Throwable var8) {
               var5 = Py.setException(var8, var1);
               if (var5.match(var1.getglobal("PEMException"))) {
                  var6 = var5.value;
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(258);
                  var12 = new String[]{"SSLError", "SSL_ERROR_SSL"};
                  var13 = imp.importFrom("_socket", var12, var1, -1);
                  var7 = var13[0];
                  var1.setlocal(9, var7);
                  var7 = null;
                  var7 = var13[1];
                  var1.setlocal(10, var7);
                  var7 = null;
                  var1.setline(259);
                  throw Py.makeException(var1.getlocal(9).__call__(var2, var1.getlocal(10), PyString.fromInterned("PEM lib ({})").__getattr__("format").__call__(var2, var1.getlocal(8))));
               }

               throw var5;
            }

            var1.setline(261);
            var11 = var1.getlocal(7);
            var10000 = var11._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(264);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("PEMKeyPair")).__nonzero__()) {
               var1.setline(265);
               var11 = var1.getlocal(2).__getattr__("getKeyPair").__call__(var2, var1.getlocal(7)).__getattr__("getPrivate").__call__(var2);
               var1.setlocal(5, var11);
               var5 = null;
            } else {
               var1.setline(266);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("PrivateKeyInfo")).__nonzero__()) {
                  var1.setline(267);
                  var11 = var1.getlocal(2).__getattr__("getPrivateKey").__call__(var2, var1.getlocal(7));
                  var1.setlocal(5, var11);
                  var5 = null;
               } else {
                  var1.setline(268);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("X509CertificateHolder")).__nonzero__()) {
                     var1.setline(269);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("getCertificate").__call__(var2, var1.getlocal(7)));
                  } else {
                     var1.setline(270);
                     if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("PEMEncryptedKeyPair")).__nonzero__()) {
                        var1.setline(280);
                        throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, PyString.fromInterned("Jython does not implement PEM object {!r}").__getattr__("format").__call__(var2, var1.getlocal(7))));
                     }

                     var1.setline(271);
                     var11 = var1.getglobal("JcePEMDecryptorProviderBuilder").__call__(var2).__getattr__("build").__call__(var2, var1.getglobal("_parse_password").__call__(var2, var1.getlocal(1)));
                     var1.setlocal(11, var11);
                     var5 = null;

                     try {
                        var1.setline(273);
                        var11 = var1.getlocal(2).__getattr__("getKeyPair").__call__(var2, var1.getlocal(7).__getattr__("decryptKeyPair").__call__(var2, var1.getlocal(11)));
                        var1.setlocal(12, var11);
                        var5 = null;
                     } catch (Throwable var9) {
                        var5 = Py.setException(var9, var1);
                        if (var5.match(var1.getglobal("EncryptionException"))) {
                           var6 = var5.value;
                           var1.setlocal(8, var6);
                           var6 = null;
                           var1.setline(275);
                           var12 = new String[]{"SSLError", "SSL_ERROR_SSL"};
                           var13 = imp.importFrom("_socket", var12, var1, -1);
                           var7 = var13[0];
                           var1.setlocal(9, var7);
                           var7 = null;
                           var7 = var13[1];
                           var1.setlocal(10, var7);
                           var7 = null;
                           var1.setline(276);
                           throw Py.makeException(var1.getlocal(9).__call__(var2, var1.getlocal(10), PyString.fromInterned("PEM lib ({})").__getattr__("format").__call__(var2, var1.getlocal(8))));
                        }

                        throw var5;
                     }

                     var1.setline(278);
                     var11 = var1.getlocal(12).__getattr__("getPrivate").__call__(var2);
                     var1.setlocal(5, var11);
                     var5 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject _is_cert_pem$11(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject _get_ecdh_parameter_spec$12(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__not__().__nonzero__()) {
         var1.setline(299);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("curve_name must be string/bytes")));
      } else {
         var1.setline(301);
         PyObject var3 = var1.getglobal("ECNamedCurveTable").__getattr__("getParameterSpec").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(302);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(303);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unknown elliptic curve name {}").__getattr__("format").__call__(var2, var1.getlocal(0))));
         } else {
            var1.setline(305);
            var10000 = var1.getglobal("ECNamedCurveSpec");
            PyObject[] var4 = new PyObject[]{var1.getlocal(1).__getattr__("getName").__call__(var2), var1.getlocal(1).__getattr__("getCurve").__call__(var2), var1.getlocal(1).__getattr__("getG").__call__(var2), var1.getlocal(1).__getattr__("getN").__call__(var2), var1.getlocal(1).__getattr__("getH").__call__(var2), var1.getlocal(1).__getattr__("getSeed").__call__(var2)};
            var3 = var10000.__call__(var2, var4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject CompositeX509KeyManager$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(320);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, chooseClientAlias$15, (PyObject)null);
      var1.setlocal("chooseClientAlias", var4);
      var3 = null;
      var1.setline(330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, chooseServerAlias$16, (PyObject)null);
      var1.setlocal("chooseServerAlias", var4);
      var3 = null;
      var1.setline(337);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getPrivateKey$17, (PyObject)null);
      var1.setlocal("getPrivateKey", var4);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getCertificateChain$18, (PyObject)null);
      var1.setlocal("getCertificateChain", var4);
      var3 = null;
      var1.setline(351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getClientAliases$19, (PyObject)null);
      var1.setlocal("getClientAliases", var4);
      var3 = null;
      var1.setline(360);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getServerAliases$20, (PyObject)null);
      var1.setlocal("getServerAliases", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("key_managers", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject chooseClientAlias$15(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyObject var3 = var1.getlocal(0).__getattr__("key_managers").__iter__();

      PyObject var5;
      do {
         var1.setline(324);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(328);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(325);
         var5 = var1.getlocal(4).__getattr__("chooseClientAlias").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(326);
      } while(!var1.getlocal(5).__nonzero__());

      var1.setline(327);
      var5 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject chooseServerAlias$16(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getlocal(0).__getattr__("key_managers").__iter__();

      PyObject var5;
      do {
         var1.setline(331);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(335);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(332);
         var5 = var1.getlocal(4).__getattr__("chooseServerAlias").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(333);
      } while(!var1.getlocal(5).__nonzero__());

      var1.setline(334);
      var5 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getPrivateKey$17(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = var1.getlocal(0).__getattr__("key_managers").__iter__();

      PyObject var5;
      do {
         var1.setline(338);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(342);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(339);
         var5 = var1.getlocal(2).__getattr__("getPrivateKey").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(340);
      } while(!var1.getlocal(3).__nonzero__());

      var1.setline(341);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getCertificateChain$18(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyObject var3 = var1.getlocal(0).__getattr__("key_managers").__iter__();

      PyObject var5;
      do {
         var1.setline(345);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(349);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(346);
         var5 = var1.getlocal(2).__getattr__("getCertificateChain").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(347);
      } while(!var1.getlocal(3).__nonzero__());

      var1.setline(348);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getClientAliases$19(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(353);
      PyObject var5 = var1.getlocal(0).__getattr__("key_managers").__iter__();

      while(true) {
         var1.setline(353);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(355);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(356);
               var5 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(358);
               var5 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var5;
            }
         }

         var1.setlocal(4, var4);
         var1.setline(354);
         var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(4).__getattr__("getClientAliases").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      }
   }

   public PyObject getServerAliases$20(PyFrame var1, ThreadState var2) {
      var1.setline(361);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(362);
      PyObject var5 = var1.getlocal(0).__getattr__("key_managers").__iter__();

      while(true) {
         var1.setline(362);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(364);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(365);
               var5 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(367);
               var5 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var5;
            }
         }

         var1.setlocal(4, var4);
         var1.setline(363);
         var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(4).__getattr__("getServerAliases").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      }
   }

   public PyObject CompositeX509TrustManager$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(372);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$22, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(375);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, checkClientTrusted$23, (PyObject)null);
      var1.setlocal("checkClientTrusted", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, checkServerTrusted$24, (PyObject)null);
      var1.setlocal("checkServerTrusted", var4);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getAcceptedIssuers$25, (PyObject)null);
      var1.setlocal("getAcceptedIssuers", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$22(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("trust_managers", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject checkClientTrusted$23(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      PyObject var3 = var1.getlocal(0).__getattr__("trust_managers").__iter__();

      while(true) {
         var1.setline(376);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(389);
            throw Py.makeException(var1.getglobal("CertificateException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("certificate verify failed")));
         }

         var1.setlocal(3, var4);

         try {
            var1.setline(378);
            var1.getlocal(3).__getattr__("checkClientTrusted").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setline(379);
            var1.f_lasti = -1;
            return Py.None;
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getglobal("CertificateException"))) {
               if (!var5.match(var1.getglobal("RuntimeException"))) {
                  throw var5;
               }

               PyObject var6 = var5.value;
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(384);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4).__getattr__("getCause").__call__(var2), var1.getglobal("InvalidAlgorithmParameterException")).__nonzero__()) {
                  var1.setline(385);
                  var6 = var1.getlocal(4).__getattr__("getCause").__call__(var2).__getattr__("getMessage").__call__(var2);
                  PyObject var10000 = var6._eq(PyUnicode.fromInterned("the trustAnchors parameter must be non-empty"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     continue;
                  }
               }

               var1.setline(387);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject checkServerTrusted$24(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var3 = var1.getlocal(0).__getattr__("trust_managers").__iter__();

      while(true) {
         var1.setline(392);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(405);
            throw Py.makeException(var1.getglobal("CertificateException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("certificate verify failed")));
         }

         var1.setlocal(3, var4);

         try {
            var1.setline(394);
            var1.getlocal(3).__getattr__("checkServerTrusted").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setline(395);
            var1.f_lasti = -1;
            return Py.None;
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getglobal("CertificateException"))) {
               if (!var5.match(var1.getglobal("RuntimeException"))) {
                  throw var5;
               }

               PyObject var6 = var5.value;
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(400);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4).__getattr__("getCause").__call__(var2), var1.getglobal("InvalidAlgorithmParameterException")).__nonzero__()) {
                  var1.setline(401);
                  var6 = var1.getlocal(4).__getattr__("getCause").__call__(var2).__getattr__("getMessage").__call__(var2);
                  PyObject var10000 = var6._eq(PyUnicode.fromInterned("the trustAnchors parameter must be non-empty"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     continue;
                  }
               }

               var1.setline(403);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject getAcceptedIssuers$25(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(409);
      PyObject var5 = var1.getlocal(0).__getattr__("trust_managers").__iter__();

      while(true) {
         var1.setline(409);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(411);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(410);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("getAcceptedIssuers").__call__(var2));
      }
   }

   public PyObject CompositeX509TrustManagerFactory$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(416);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$27, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(419);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, engineInit$28, (PyObject)null);
      var1.setlocal("engineInit", var4);
      var3 = null;
      var1.setline(422);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, engineGetTrustManagers$29, (PyObject)null);
      var1.setlocal("engineGetTrustManagers", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$27(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      PyObject var3 = var1.getglobal("CompositeX509TrustManager").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_trust_manager", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject engineInit$28(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject engineGetTrustManagers$29(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_trust_manager")});
      var1.f_lasti = -1;
      return var3;
   }

   public _sslcerts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"ca_certs", "trust_store", "num_certs_installed", "f", "cf", "cert", "tmf"};
      _get_ca_certs_trust_manager$1 = Py.newCode(1, var2, var1, "_get_ca_certs_trust_manager", 64, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      _stringio_as_reader$2 = Py.newCode(1, var2, var1, "_stringio_as_reader", 80, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "string_ios", "output", "key_cert_start_line_found", "line", "SSLError", "SSL_ERROR_SSL", "_[104_12]", "sio"};
      _extract_readers$3 = Py.newCode(1, var2, var1, "_extract_readers", 84, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cert_file", "key_file", "password", "_key_store", "certs", "private_key", "SSLError", "SSL_ERROR_SSL", "_certs", "_private_key", "keys_match", "validateable_keys_found", "cert", "kmf"};
      _get_openssl_key_manager$4 = Py.newCode(4, var2, var1, "_get_openssl_key_manager", 107, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "_hash", "arg"};
      _str_hash_key_entry$5 = Py.newCode(1, var2, var1, "_str_hash_key_entry", 152, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"password", "is_password_func"};
      _parse_password$6 = Py.newCode(1, var2, var1, "_parse_password", 162, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "password", "keystore", "certs", "alias_iter", "alias"};
      _extract_certs_from_keystore_file$7 = Py.newCode(2, var2, var1, "_extract_certs_from_keystore_file", 181, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"paths", "password", "key_converter", "cert_converter", "certs", "private_key", "path", "err", "f", "_certs", "_private_key"};
      _extract_certs_for_paths$8 = Py.newCode(2, var2, var1, "_extract_certs_for_paths", 201, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "password", "key_converter", "cert_converter", "certs", "private_key", "cf"};
      _extract_cert_from_data$9 = Py.newCode(4, var2, var1, "_extract_cert_from_data", 224, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "password", "key_converter", "cert_converter", "certs", "private_key", "br", "obj", "err", "SSLError", "SSL_ERROR_SSL", "provider", "key_pair"};
      _read_pem_cert_from_data$10 = Py.newCode(4, var2, var1, "_read_pem_cert_from_data", 245, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      _is_cert_pem$11 = Py.newCode(1, var2, var1, "_is_cert_pem", 284, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"curve_name", "spec_param"};
      _get_ecdh_parameter_spec$12 = Py.newCode(1, var2, var1, "_get_ecdh_parameter_spec", 297, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CompositeX509KeyManager$13 = Py.newCode(0, var2, var1, "CompositeX509KeyManager", 318, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key_managers"};
      __init__$14 = Py.newCode(2, var2, var1, "__init__", 320, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key_type", "issuers", "socket", "key_manager", "alias"};
      chooseClientAlias$15 = Py.newCode(4, var2, var1, "chooseClientAlias", 323, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key_type", "issuers", "socket", "key_manager", "alias"};
      chooseServerAlias$16 = Py.newCode(4, var2, var1, "chooseServerAlias", 330, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "alias", "key_manager", "private_key"};
      getPrivateKey$17 = Py.newCode(2, var2, var1, "getPrivateKey", 337, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "alias", "key_manager", "chain"};
      getCertificateChain$18 = Py.newCode(2, var2, var1, "getCertificateChain", 344, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key_type", "issuers", "aliases", "key_manager"};
      getClientAliases$19 = Py.newCode(3, var2, var1, "getClientAliases", 351, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key_type", "issuers", "aliases", "key_manager"};
      getServerAliases$20 = Py.newCode(3, var2, var1, "getServerAliases", 360, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CompositeX509TrustManager$21 = Py.newCode(0, var2, var1, "CompositeX509TrustManager", 370, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "trust_managers"};
      __init__$22 = Py.newCode(2, var2, var1, "__init__", 372, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chain", "auth_type", "trust_manager", "err"};
      checkClientTrusted$23 = Py.newCode(3, var2, var1, "checkClientTrusted", 375, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chain", "auth_type", "trust_manager", "err"};
      checkServerTrusted$24 = Py.newCode(3, var2, var1, "checkServerTrusted", 391, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "certs", "trust_manager"};
      getAcceptedIssuers$25 = Py.newCode(1, var2, var1, "getAcceptedIssuers", 407, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CompositeX509TrustManagerFactory$26 = Py.newCode(0, var2, var1, "CompositeX509TrustManagerFactory", 414, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "trust_managers"};
      __init__$27 = Py.newCode(2, var2, var1, "__init__", 416, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      engineInit$28 = Py.newCode(2, var2, var1, "engineInit", 419, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      engineGetTrustManagers$29 = Py.newCode(1, var2, var1, "engineGetTrustManagers", 422, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _sslcerts$py("_sslcerts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_sslcerts$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._get_ca_certs_trust_manager$1(var2, var3);
         case 2:
            return this._stringio_as_reader$2(var2, var3);
         case 3:
            return this._extract_readers$3(var2, var3);
         case 4:
            return this._get_openssl_key_manager$4(var2, var3);
         case 5:
            return this._str_hash_key_entry$5(var2, var3);
         case 6:
            return this._parse_password$6(var2, var3);
         case 7:
            return this._extract_certs_from_keystore_file$7(var2, var3);
         case 8:
            return this._extract_certs_for_paths$8(var2, var3);
         case 9:
            return this._extract_cert_from_data$9(var2, var3);
         case 10:
            return this._read_pem_cert_from_data$10(var2, var3);
         case 11:
            return this._is_cert_pem$11(var2, var3);
         case 12:
            return this._get_ecdh_parameter_spec$12(var2, var3);
         case 13:
            return this.CompositeX509KeyManager$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.chooseClientAlias$15(var2, var3);
         case 16:
            return this.chooseServerAlias$16(var2, var3);
         case 17:
            return this.getPrivateKey$17(var2, var3);
         case 18:
            return this.getCertificateChain$18(var2, var3);
         case 19:
            return this.getClientAliases$19(var2, var3);
         case 20:
            return this.getServerAliases$20(var2, var3);
         case 21:
            return this.CompositeX509TrustManager$21(var2, var3);
         case 22:
            return this.__init__$22(var2, var3);
         case 23:
            return this.checkClientTrusted$23(var2, var3);
         case 24:
            return this.checkServerTrusted$24(var2, var3);
         case 25:
            return this.getAcceptedIssuers$25(var2, var3);
         case 26:
            return this.CompositeX509TrustManagerFactory$26(var2, var3);
         case 27:
            return this.__init__$27(var2, var3);
         case 28:
            return this.engineInit$28(var2, var3);
         case 29:
            return this.engineGetTrustManagers$29(var2, var3);
         default:
            return null;
      }
   }
}
