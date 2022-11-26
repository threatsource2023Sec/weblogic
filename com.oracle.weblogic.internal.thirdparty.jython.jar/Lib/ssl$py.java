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
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("ssl.py")
public class ssl$py extends PyFunctionTable implements PyRunnable {
   static ssl$py self;
   static final PyCode f$0;
   static final PyCode _str_or_unicode$1;
   static final PyCode CertificateError$2;
   static final PyCode SSLZeroReturnError$3;
   static final PyCode SSLWantReadError$4;
   static final PyCode SSLWantWriteError$5;
   static final PyCode SSLSyscallError$6;
   static final PyCode SSLEOFError$7;
   static final PyCode _dnsname_match$8;
   static final PyCode match_hostname$9;
   static final PyCode get_default_verify_paths$10;
   static final PyCode _ASN1Object$11;
   static final PyCode __new__$12;
   static final PyCode Purpose$13;
   static final PyCode create_default_context$14;
   static final PyCode _create_unverified_context$15;
   static final PyCode SSLInitializer$16;
   static final PyCode __init__$17;
   static final PyCode initChannel$18;
   static final PyCode SSLSocket$19;
   static final PyCode __init__$20;
   static final PyCode setup_handshake$21;
   static final PyCode wrap_child$22;
   static final PyCode context$23;
   static final PyCode context$24;
   static final PyCode setup_engine$25;
   static final PyCode connect$26;
   static final PyCode connect_ex$27;
   static final PyCode accept$28;
   static final PyCode unwrap$29;
   static final PyCode do_handshake$30;
   static final PyCode handshake_step$31;
   static final PyCode dup$32;
   static final PyCode _ensure_handshake$33;
   static final PyCode send$34;
   static final PyCode sendall$35;
   static final PyCode recv$36;
   static final PyCode read$37;
   static final PyCode recvfrom$38;
   static final PyCode recvfrom_into$39;
   static final PyCode recv_into$40;
   static final PyCode sendto$41;
   static final PyCode close$42;
   static final PyCode setblocking$43;
   static final PyCode settimeout$44;
   static final PyCode gettimeout$45;
   static final PyCode makefile$46;
   static final PyCode shutdown$47;
   static final PyCode pending$48;
   static final PyCode _readable$49;
   static final PyCode _writable$50;
   static final PyCode _register_selector$51;
   static final PyCode _unregister_selector$52;
   static final PyCode _notify_selectors$53;
   static final PyCode _checkClosed$54;
   static final PyCode _check_connected$55;
   static final PyCode getpeername$56;
   static final PyCode selected_npn_protocol$57;
   static final PyCode selected_alpn_protocol$58;
   static final PyCode fileno$59;
   static final PyCode getpeercert$60;
   static final PyCode f$61;
   static final PyCode issuer$62;
   static final PyCode cipher$63;
   static final PyCode get_channel_binding$64;
   static final PyCode version$65;
   static final PyCode wrap_socket$66;
   static final PyCode cert_time_to_seconds$67;
   static final PyCode DER_cert_to_PEM_cert$68;
   static final PyCode PEM_cert_to_DER_cert$69;
   static final PyCode get_server_certificate$70;
   static final PyCode get_protocol_name$71;
   static final PyCode sslwrap_simple$72;
   static final PyCode RAND_status$73;
   static final PyCode RAND_egd$74;
   static final PyCode RAND_add$75;
   static final PyCode SSLContext$76;
   static final PyCode __init__$77;
   static final PyCode wrap_socket$78;
   static final PyCode _createSSLEngine$79;
   static final PyCode cert_store_stats$80;
   static final PyCode load_cert_chain$81;
   static final PyCode set_ciphers$82;
   static final PyCode load_verify_locations$83;
   static final PyCode load_default_certs$84;
   static final PyCode set_default_verify_paths$85;
   static final PyCode set_alpn_protocols$86;
   static final PyCode set_npn_protocols$87;
   static final PyCode set_servername_callback$88;
   static final PyCode load_dh_params$89;
   static final PyCode set_ecdh_curve$90;
   static final PyCode session_stats$91;
   static final PyCode get_ca_certs$92;
   static final PyCode check_hostname$93;
   static final PyCode check_hostname$94;
   static final PyCode verify_mode$95;
   static final PyCode verify_mode$96;
   static final PyCode verify_flags$97;
   static final PyCode verify_flags$98;
   static final PyCode _parse_dn$99;
   static final PyCode f$100;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;
      var1.setline(2);
      String[] var8 = new String[]{"namedtuple"};
      PyObject[] var9 = imp.importFrom("collections", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var1.setline(3);
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(4);
      var8 = new String[]{"CertificateFactory"};
      var9 = imp.importFrom("java.security.cert", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("CertificateFactory", var4);
      var4 = null;
      var1.setline(5);
      var3 = imp.importOne("uuid", var1, -1);
      var1.setlocal("uuid", var3);
      var3 = null;
      var1.setline(6);
      var8 = new String[]{"BufferedInputStream"};
      var9 = imp.importFrom("java.io", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("BufferedInputStream", var4);
      var4 = null;
      var1.setline(7);
      var8 = new String[]{"KeyStore", "KeyStoreException"};
      var9 = imp.importFrom("java.security", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("KeyStore", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("KeyStoreException", var4);
      var4 = null;
      var1.setline(8);
      var8 = new String[]{"CertificateParsingException"};
      var9 = imp.importFrom("java.security.cert", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("CertificateParsingException", var4);
      var4 = null;
      var1.setline(9);
      var8 = new String[]{"LdapName"};
      var9 = imp.importFrom("javax.naming.ldap", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("LdapName", var4);
      var4 = null;
      var1.setline(10);
      var8 = new String[]{"IllegalArgumentException", "System"};
      var9 = imp.importFrom("java.lang", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("IllegalArgumentException", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("System", var4);
      var4 = null;
      var1.setline(11);
      var3 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var1.setline(12);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(13);
      var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("threading", var1, -1);
      var1.setlocal("threading", var3);
      var3 = null;

      PyObject var5;
      PyObject[] var11;
      PyException var12;
      try {
         var1.setline(20);
         var8 = new String[]{"ChannelInitializer"};
         var9 = imp.importFrom("org.python.netty.channel", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("ChannelInitializer", var4);
         var4 = null;
         var1.setline(21);
         var8 = new String[]{"SslHandler", "SslProvider", "SslContextBuilder", "ClientAuth"};
         var9 = imp.importFrom("org.python.netty.handler.ssl", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("SslHandler", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("SslProvider", var4);
         var4 = null;
         var4 = var9[2];
         var1.setlocal("SslContextBuilder", var4);
         var4 = null;
         var4 = var9[3];
         var1.setlocal("ClientAuth", var4);
         var4 = null;
         var1.setline(22);
         var8 = new String[]{"SimpleTrustManagerFactory", "InsecureTrustManagerFactory"};
         var9 = imp.importFrom("org.python.netty.handler.ssl.util", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("SimpleTrustManagerFactory", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("InsecureTrustManagerFactory", var4);
         var4 = null;
         var1.setline(23);
         var8 = new String[]{"ByteBufAllocator"};
         var9 = imp.importFrom("org.python.netty.buffer", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("ByteBufAllocator", var4);
         var4 = null;
      } catch (Throwable var7) {
         var12 = Py.setException(var7, var1);
         if (!var12.match(var1.getname("ImportError"))) {
            throw var12;
         }

         var1.setline(27);
         String[] var10 = new String[]{"ChannelInitializer"};
         var11 = imp.importFrom("io.netty.channel", var10, var1, -1);
         var5 = var11[0];
         var1.setlocal("ChannelInitializer", var5);
         var5 = null;
         var1.setline(28);
         var10 = new String[]{"SslHandler", "SslProvider", "SslContextBuilder", "ClientAuth"};
         var11 = imp.importFrom("io.netty.handler.ssl", var10, var1, -1);
         var5 = var11[0];
         var1.setlocal("SslHandler", var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal("SslProvider", var5);
         var5 = null;
         var5 = var11[2];
         var1.setlocal("SslContextBuilder", var5);
         var5 = null;
         var5 = var11[3];
         var1.setlocal("ClientAuth", var5);
         var5 = null;
         var1.setline(29);
         var10 = new String[]{"SimpleTrustManagerFactory", "InsecureTrustManagerFactory"};
         var11 = imp.importFrom("io.netty.handler.ssl.util", var10, var1, -1);
         var5 = var11[0];
         var1.setlocal("SimpleTrustManagerFactory", var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal("InsecureTrustManagerFactory", var5);
         var5 = null;
         var1.setline(30);
         var10 = new String[]{"ByteBufAllocator"};
         var11 = imp.importFrom("io.netty.buffer", var10, var1, -1);
         var5 = var11[0];
         var1.setlocal("ByteBufAllocator", var5);
         var5 = null;
      }

      var1.setline(32);
      var8 = new String[]{"SSLError", "raises_java_exception", "SSL_ERROR_SSL", "SSL_ERROR_WANT_READ", "SSL_ERROR_WANT_WRITE", "SSL_ERROR_WANT_X509_LOOKUP", "SSL_ERROR_SYSCALL", "SSL_ERROR_ZERO_RETURN", "SSL_ERROR_WANT_CONNECT", "SSL_ERROR_EOF", "SSL_ERROR_INVALID_ERROR_CODE", "SOL_SOCKET", "SO_TYPE", "SOCK_STREAM", "socket", "_socketobject", "ChildSocket", "error"};
      var9 = imp.importFrom("_socket", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("SSLError", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("raises_java_exception", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("SSL_ERROR_SSL", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("SSL_ERROR_WANT_READ", var4);
      var4 = null;
      var4 = var9[4];
      var1.setlocal("SSL_ERROR_WANT_WRITE", var4);
      var4 = null;
      var4 = var9[5];
      var1.setlocal("SSL_ERROR_WANT_X509_LOOKUP", var4);
      var4 = null;
      var4 = var9[6];
      var1.setlocal("SSL_ERROR_SYSCALL", var4);
      var4 = null;
      var4 = var9[7];
      var1.setlocal("SSL_ERROR_ZERO_RETURN", var4);
      var4 = null;
      var4 = var9[8];
      var1.setlocal("SSL_ERROR_WANT_CONNECT", var4);
      var4 = null;
      var4 = var9[9];
      var1.setlocal("SSL_ERROR_EOF", var4);
      var4 = null;
      var4 = var9[10];
      var1.setlocal("SSL_ERROR_INVALID_ERROR_CODE", var4);
      var4 = null;
      var4 = var9[11];
      var1.setlocal("SOL_SOCKET", var4);
      var4 = null;
      var4 = var9[12];
      var1.setlocal("SO_TYPE", var4);
      var4 = null;
      var4 = var9[13];
      var1.setlocal("SOCK_STREAM", var4);
      var4 = null;
      var4 = var9[14];
      var1.setlocal("socket", var4);
      var4 = null;
      var4 = var9[15];
      var1.setlocal("_socketobject", var4);
      var4 = null;
      var4 = var9[16];
      var1.setlocal("ChildSocket", var4);
      var4 = null;
      var4 = var9[17];
      var1.setlocal("socket_error", var4);
      var4 = null;
      var1.setline(51);
      var8 = new String[]{"_get_openssl_key_manager", "_extract_cert_from_data", "_extract_certs_for_paths", "_str_hash_key_entry", "_get_ecdh_parameter_spec", "CompositeX509TrustManagerFactory"};
      var9 = imp.importFrom("_sslcerts", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_get_openssl_key_manager", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("_extract_cert_from_data", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("_extract_certs_for_paths", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("_str_hash_key_entry", var4);
      var4 = null;
      var4 = var9[4];
      var1.setlocal("_get_ecdh_parameter_spec", var4);
      var4 = null;
      var4 = var9[5];
      var1.setlocal("CompositeX509TrustManagerFactory", var4);
      var4 = null;
      var1.setline(53);
      var8 = new String[]{"SSLContext"};
      var9 = imp.importFrom("_sslcerts", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_JavaSSLContext", var4);
      var4 = null;
      var1.setline(55);
      var8 = new String[]{"SimpleDateFormat"};
      var9 = imp.importFrom("java.text", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("SimpleDateFormat", var4);
      var4 = null;
      var1.setline(56);
      var8 = new String[]{"ArrayList", "Locale", "TimeZone", "NoSuchElementException"};
      var9 = imp.importFrom("java.util", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("ArrayList", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("Locale", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("TimeZone", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("NoSuchElementException", var4);
      var4 = null;
      var1.setline(57);
      var8 = new String[]{"CountDownLatch"};
      var9 = imp.importFrom("java.util.concurrent", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("CountDownLatch", var4);
      var4 = null;
      var1.setline(58);
      var8 = new String[]{"LdapName"};
      var9 = imp.importFrom("javax.naming.ldap", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("LdapName", var4);
      var4 = null;
      var1.setline(59);
      var8 = new String[]{"SSLException", "SSLHandshakeException"};
      var9 = imp.importFrom("javax.net.ssl", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("SSLException", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("SSLHandshakeException", var4);
      var4 = null;
      var1.setline(60);
      var8 = new String[]{"X500Principal"};
      var9 = imp.importFrom("javax.security.auth.x500", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("X500Principal", var4);
      var4 = null;
      var1.setline(61);
      var8 = new String[]{"Oid"};
      var9 = imp.importFrom("org.ietf.jgss", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("Oid", var4);
      var4 = null;

      try {
         var1.setline(65);
         var8 = new String[]{"SNIHostName", "SNIMatcher"};
         var9 = imp.importFrom("javax.net.ssl", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("SNIHostName", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("SNIMatcher", var4);
         var4 = null;
         var1.setline(66);
         var3 = var1.getname("True");
         var1.setlocal("HAS_SNI", var3);
         var3 = null;
      } catch (Throwable var6) {
         var12 = Py.setException(var6, var1);
         if (!var12.match(var1.getname("ImportError"))) {
            throw var12;
         }

         var1.setline(68);
         var4 = var1.getname("False");
         var1.setlocal("HAS_SNI", var4);
         var4 = null;
      }

      var1.setline(70);
      var3 = var1.getname("logging").__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_socket"));
      var1.setlocal("log", var3);
      var3 = null;
      var1.setline(74);
      PyString var13 = PyString.fromInterned("OpenSSL 1.0.0 (as emulated by Java SSL)");
      var1.setlocal("OPENSSL_VERSION", var13);
      var3 = null;
      var1.setline(75);
      PyLong var14 = Py.newLong("16777216");
      var1.setlocal("OPENSSL_VERSION_NUMBER", var14);
      var3 = null;
      var1.setline(76);
      PyTuple var15 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)});
      var1.setlocal("OPENSSL_VERSION_INFO", var15);
      var3 = null;
      var1.setline(77);
      var3 = var1.getname("OPENSSL_VERSION_INFO");
      var1.setlocal("_OPENSSL_API_VERSION", var3);
      var3 = null;
      var1.setline(79);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
      var11 = Py.unpackSequence(var3, 3);
      var5 = var11[0];
      var1.setlocal("CERT_NONE", var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal("CERT_OPTIONAL", var5);
      var5 = null;
      var5 = var11[2];
      var1.setlocal("CERT_REQUIRED", var5);
      var5 = null;
      var3 = null;
      var1.setline(81);
      PyDictionary var16 = new PyDictionary(new PyObject[]{var1.getname("CERT_NONE"), var1.getname("ClientAuth").__getattr__("NONE"), var1.getname("CERT_OPTIONAL"), var1.getname("ClientAuth").__getattr__("OPTIONAL"), var1.getname("CERT_REQUIRED"), var1.getname("ClientAuth").__getattr__("REQUIRE")});
      var1.setlocal("_CERT_TO_CLIENT_AUTH", var16);
      var3 = null;
      var1.setline(86);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(6));
      var11 = Py.unpackSequence(var3, 6);
      var5 = var11[0];
      var1.setlocal("_", var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal("PROTOCOL_SSLv3", var5);
      var5 = null;
      var5 = var11[2];
      var1.setlocal("PROTOCOL_SSLv23", var5);
      var5 = null;
      var5 = var11[3];
      var1.setlocal("PROTOCOL_TLSv1", var5);
      var5 = null;
      var5 = var11[4];
      var1.setlocal("PROTOCOL_TLSv1_1", var5);
      var5 = null;
      var5 = var11[5];
      var1.setlocal("PROTOCOL_TLSv1_2", var5);
      var5 = null;
      var3 = null;
      var1.setline(87);
      var16 = new PyDictionary(new PyObject[]{var1.getname("PROTOCOL_SSLv3"), PyString.fromInterned("SSLv3"), var1.getname("PROTOCOL_SSLv23"), PyString.fromInterned("SSLv23"), var1.getname("PROTOCOL_TLSv1"), PyString.fromInterned("TLSv1"), var1.getname("PROTOCOL_TLSv1_1"), PyString.fromInterned("TLSv1.1"), var1.getname("PROTOCOL_TLSv1_2"), PyString.fromInterned("TLSv1.2")});
      var1.setlocal("_PROTOCOL_NAMES", var16);
      var3 = null;
      var1.setline(95);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
      var11 = Py.unpackSequence(var3, 4);
      var5 = var11[0];
      var1.setlocal("OP_ALL", var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal("OP_NO_SSLv2", var5);
      var5 = null;
      var5 = var11[2];
      var1.setlocal("OP_NO_SSLv3", var5);
      var5 = null;
      var5 = var11[3];
      var1.setlocal("OP_NO_TLSv1", var5);
      var5 = null;
      var3 = null;
      var1.setline(96);
      var15 = new PyTuple(new PyObject[]{Py.newInteger(1048576), Py.newInteger(131072), Py.newInteger(4194304), Py.newInteger(524288)});
      var11 = Py.unpackSequence(var15, 4);
      var5 = var11[0];
      var1.setlocal("OP_SINGLE_DH_USE", var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal("OP_NO_COMPRESSION", var5);
      var5 = null;
      var5 = var11[2];
      var1.setlocal("OP_CIPHER_SERVER_PREFERENCE", var5);
      var5 = null;
      var5 = var11[3];
      var1.setlocal("OP_SINGLE_ECDH_USE", var5);
      var5 = null;
      var3 = null;
      var1.setline(98);
      var15 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(4), Py.newInteger(12), Py.newInteger(32)});
      var11 = Py.unpackSequence(var15, 4);
      var5 = var11[0];
      var1.setlocal("VERIFY_DEFAULT", var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal("VERIFY_CRL_CHECK_LEAF", var5);
      var5 = null;
      var5 = var11[2];
      var1.setlocal("VERIFY_CRL_CHECK_CHAIN", var5);
      var5 = null;
      var5 = var11[3];
      var1.setlocal("VERIFY_X509_STRICT", var5);
      var5 = null;
      var3 = null;
      var1.setline(100);
      PyList var17 = new PyList(Py.EmptyObjects);
      var1.setlocal("CHANNEL_BINDING_TYPES", var17);
      var3 = null;
      var1.setline(103);
      var15 = new PyTuple(new PyObject[]{var1.getname("False"), var1.getname("False"), var1.getname("True")});
      var11 = Py.unpackSequence(var15, 3);
      var5 = var11[0];
      var1.setlocal("HAS_ALPN", var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal("HAS_NPN", var5);
      var5 = null;
      var5 = var11[2];
      var1.setlocal("HAS_ECDH", var5);
      var5 = null;
      var3 = null;
      var1.setline(118);
      var13 = PyString.fromInterned("ECDH+AESGCM:DH+AESGCM:ECDH+AES256:DH+AES256:ECDH+AES128:DH+AES:ECDH+HIGH:DH+HIGH:ECDH+3DES:DH+3DES:RSA+AESGCM:RSA+AES:RSA+HIGH:RSA+3DES:!aNULL:!eNULL:!MD5");
      var1.setlocal("_DEFAULT_CIPHERS", var13);
      var3 = null;
      var1.setline(134);
      var13 = PyString.fromInterned("ECDH+AESGCM:DH+AESGCM:ECDH+AES256:DH+AES256:ECDH+AES128:DH+AES:ECDH+HIGH:DH+HIGH:ECDH+3DES:DH+3DES:RSA+AESGCM:RSA+AES:RSA+HIGH:RSA+3DES:!aNULL:!eNULL:!MD5:!DSS:!RC4");
      var1.setlocal("_RESTRICTED_SERVER_CIPHERS", var13);
      var3 = null;
      var1.setline(140);
      var3 = var1.getname("SimpleDateFormat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MMM dd HH:mm:ss yyyy z"), (PyObject)var1.getname("Locale").__getattr__("US"));
      var1.setlocal("_rfc2822_date_format", var3);
      var3 = null;
      var1.setline(141);
      var1.getname("_rfc2822_date_format").__getattr__("setTimeZone").__call__(var2, var1.getname("TimeZone").__getattr__("getTimeZone").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GMT")));
      var1.setline(143);
      var16 = new PyDictionary(new PyObject[]{PyString.fromInterned("CN"), PyString.fromInterned("commonName"), PyString.fromInterned("E"), PyString.fromInterned("emailAddress"), PyString.fromInterned("L"), PyString.fromInterned("localityName"), PyString.fromInterned("ST"), PyString.fromInterned("stateOrProvinceName"), PyString.fromInterned("O"), PyString.fromInterned("organizationName"), PyString.fromInterned("OU"), PyString.fromInterned("organizationalUnitName"), PyString.fromInterned("C"), PyString.fromInterned("countryName"), PyString.fromInterned("STREET"), PyString.fromInterned("streetAddress"), PyString.fromInterned("DC"), PyString.fromInterned("domainComponent"), PyString.fromInterned("UID"), PyString.fromInterned("userid")});
      var1.setlocal("_ldap_rdn_display_names", var16);
      var3 = null;
      var1.setline(157);
      var17 = new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("rfc822"), PyString.fromInterned("DNS"), PyString.fromInterned("x400Address"), PyString.fromInterned("directory"), PyString.fromInterned("ediParty"), PyString.fromInterned("uniformResourceIdentifier"), PyString.fromInterned("ipAddress"), PyString.fromInterned("registeredID")});
      var1.setlocal("_cert_name_types", var17);
      var3 = null;
      var1.setline(170);
      var9 = Py.EmptyObjects;
      PyFunction var18 = new PyFunction(var1.f_globals, var9, _str_or_unicode$1, (PyObject)null);
      var1.setlocal("_str_or_unicode", var18);
      var3 = null;
      var1.setline(178);
      var9 = new PyObject[]{var1.getname("ValueError")};
      var4 = Py.makeClass("CertificateError", var9, CertificateError$2);
      var1.setlocal("CertificateError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(183);
      var9 = new PyObject[]{var1.getname("SSLError")};
      var4 = Py.makeClass("SSLZeroReturnError", var9, SSLZeroReturnError$3);
      var1.setlocal("SSLZeroReturnError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(186);
      var9 = new PyObject[]{var1.getname("SSLError")};
      var4 = Py.makeClass("SSLWantReadError", var9, SSLWantReadError$4);
      var1.setlocal("SSLWantReadError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(189);
      var9 = new PyObject[]{var1.getname("SSLError")};
      var4 = Py.makeClass("SSLWantWriteError", var9, SSLWantWriteError$5);
      var1.setlocal("SSLWantWriteError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(192);
      var9 = new PyObject[]{var1.getname("SSLError")};
      var4 = Py.makeClass("SSLSyscallError", var9, SSLSyscallError$6);
      var1.setlocal("SSLSyscallError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(195);
      var9 = new PyObject[]{var1.getname("SSLError")};
      var4 = Py.makeClass("SSLEOFError", var9, SSLEOFError$7);
      var1.setlocal("SSLEOFError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(199);
      var9 = new PyObject[]{Py.newInteger(1)};
      var18 = new PyFunction(var1.f_globals, var9, _dnsname_match$8, PyString.fromInterned("Matching according to RFC 6125, section 6.4.3\n\n    http://tools.ietf.org/html/rfc6125#section-6.4.3\n    "));
      var1.setlocal("_dnsname_match", var18);
      var3 = null;
      var1.setline(250);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, match_hostname$9, PyString.fromInterned("Verify that *cert* (in decoded format as returned by\n    SSLSocket.getpeercert()) matches the *hostname*.  RFC 2818 and RFC 6125\n    rules are followed, but IP addresses are not accepted for *hostname*.\n\n    CertificateError is raised on failure. On success, the function\n    returns nothing.\n    "));
      var1.setlocal("match_hostname", var18);
      var3 = null;
      var1.setline(293);
      var3 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DefaultVerifyPaths"), (PyObject)PyString.fromInterned("cafile capath openssl_cafile_env openssl_cafile openssl_capath_env openssl_capath"));
      var1.setlocal("DefaultVerifyPaths", var3);
      var3 = null;
      var1.setline(298);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, get_default_verify_paths$10, PyString.fromInterned("Return paths to default cafile and capath.\n    "));
      var1.setlocal("get_default_verify_paths", var18);
      var3 = null;
      var1.setline(330);
      var9 = new PyObject[]{var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_ASN1Object"), (PyObject)PyString.fromInterned("nid shortname longname oid"))};
      var4 = Py.makeClass("_ASN1Object", var9, _ASN1Object$11);
      var1.setlocal("_ASN1Object", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(344);
      var9 = new PyObject[]{var1.getname("_ASN1Object")};
      var4 = Py.makeClass("Purpose", var9, Purpose$13);
      var1.setlocal("Purpose", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(348);
      var3 = var1.getname("Purpose").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1.3.6.1.5.5.7.3.1"));
      var1.getname("Purpose").__setattr__("SERVER_AUTH", var3);
      var3 = null;
      var1.setline(349);
      var3 = var1.getname("Purpose").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1.3.6.1.5.5.7.3.2"));
      var1.getname("Purpose").__setattr__("CLIENT_AUTH", var3);
      var3 = null;
      var1.setline(352);
      var9 = new PyObject[]{var1.getname("Purpose").__getattr__("SERVER_AUTH"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var9, create_default_context$14, PyString.fromInterned("Create a SSLContext object with default settings.\n\n    NOTE: The protocol and settings may change anytime without prior\n          deprecation. The values represent a fair balance between maximum\n          compatibility and security.\n    "));
      var1.setlocal("create_default_context", var18);
      var3 = null;
      var1.setline(404);
      var9 = new PyObject[]{var1.getname("PROTOCOL_SSLv23"), var1.getname("None"), var1.getname("False"), var1.getname("Purpose").__getattr__("SERVER_AUTH"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var9, _create_unverified_context$15, PyString.fromInterned("Create a SSLContext object for Python stdlib modules\n\n    All Python stdlib modules shall use this function to create SSLContext\n    objects in order to keep common settings in one place. The configuration\n    is less restricted than create_default_context()'s to increase backward\n    compatibility.\n    "));
      var1.setlocal("_create_unverified_context", var18);
      var3 = null;
      var1.setline(447);
      var3 = var1.getname("create_default_context");
      var1.setlocal("_create_default_https_context", var3);
      var3 = null;
      var1.setline(451);
      var3 = var1.getname("_create_unverified_context");
      var1.setlocal("_create_stdlib_context", var3);
      var3 = null;
      var1.setline(454);
      var9 = new PyObject[]{var1.getname("ChannelInitializer")};
      var4 = Py.makeClass("SSLInitializer", var9, SSLInitializer$16);
      var1.setlocal("SSLInitializer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(463);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("SSLSocket", var9, SSLSocket$19);
      var1.setlocal("SSLSocket", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(899);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("CERT_NONE"), var1.getname("PROTOCOL_SSLv23"), var1.getname("None"), var1.getname("True"), var1.getname("True"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var9, wrap_socket$66, (PyObject)null);
      var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var18);
      var1.setlocal("wrap_socket", var3);
      var3 = null;
      var1.setline(913);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, cert_time_to_seconds$67, PyString.fromInterned("Return the time in seconds since the Epoch, given the timestring\n    representing the \"notBefore\" or \"notAfter\" date from a certificate\n    in ``\"%b %d %H:%M:%S %Y %Z\"`` strptime format (C locale).\n\n    \"notBefore\" or \"notAfter\" dates must use UTC (RFC 5280).\n\n    Month is one of: Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec\n    UTC should be specified as GMT (see ASN1_TIME_print())\n    "));
      var1.setlocal("cert_time_to_seconds", var18);
      var3 = null;
      var1.setline(944);
      var13 = PyString.fromInterned("-----BEGIN CERTIFICATE-----");
      var1.setlocal("PEM_HEADER", var13);
      var3 = null;
      var1.setline(945);
      var13 = PyString.fromInterned("-----END CERTIFICATE-----");
      var1.setlocal("PEM_FOOTER", var13);
      var3 = null;
      var1.setline(948);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, DER_cert_to_PEM_cert$68, PyString.fromInterned("Takes a certificate in binary DER format and returns the\n    PEM version of it as a string."));
      var1.setlocal("DER_cert_to_PEM_cert", var18);
      var3 = null;
      var1.setline(964);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, PEM_cert_to_DER_cert$69, PyString.fromInterned("Takes a certificate in ASCII PEM format and returns the\n    DER-encoded version of it as a byte sequence"));
      var1.setlocal("PEM_cert_to_DER_cert", var18);
      var3 = null;
      var1.setline(978);
      var9 = new PyObject[]{var1.getname("PROTOCOL_SSLv3"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var9, get_server_certificate$70, PyString.fromInterned("Retrieve the certificate from the server at the specified address,\n    and return it as a PEM-encoded string.\n    If 'ca_certs' is specified, validate the server cert against it.\n    If 'ssl_version' is specified, use it in the connection attempt."));
      var1.setlocal("get_server_certificate", var18);
      var3 = null;
      var1.setline(998);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, get_protocol_name$71, (PyObject)null);
      var1.setlocal("get_protocol_name", var18);
      var3 = null;
      var1.setline(1004);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var9, sslwrap_simple$72, PyString.fromInterned("A replacement for the old socket.ssl function.  Designed\n    for compability with Python 2.5 and earlier.  Will disappear in\n    Python 3.0."));
      var1.setlocal("sslwrap_simple", var18);
      var3 = null;
      var1.setline(1024);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, RAND_status$73, (PyObject)null);
      var1.setlocal("RAND_status", var18);
      var3 = null;
      var1.setline(1028);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, RAND_egd$74, (PyObject)null);
      var1.setlocal("RAND_egd", var18);
      var3 = null;
      var1.setline(1033);
      var9 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var9, RAND_add$75, (PyObject)null);
      var1.setlocal("RAND_add", var18);
      var3 = null;
      var1.setline(1037);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("SSLContext", var9, SSLContext$76);
      var1.setlocal("SSLContext", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _str_or_unicode$1(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(172);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("UnicodeEncodeError"))) {
            var1.setline(174);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(176);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject CertificateError$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(179);
      return var1.getf_locals();
   }

   public PyObject SSLZeroReturnError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(184);
      return var1.getf_locals();
   }

   public PyObject SSLWantReadError$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(187);
      return var1.getf_locals();
   }

   public PyObject SSLWantWriteError$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(190);
      return var1.getf_locals();
   }

   public PyObject SSLSyscallError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(193);
      return var1.getf_locals();
   }

   public PyObject SSLEOFError$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(196);
      return var1.getf_locals();
   }

   public PyObject _dnsname_match$8(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyString.fromInterned("Matching according to RFC 6125, section 6.4.3\n\n    http://tools.ietf.org/html/rfc6125#section-6.4.3\n    ");
      var1.setline(204);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(205);
      PyObject var6;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(206);
         var6 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(208);
         PyObject var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(209);
         var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(210);
         var4 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(212);
         var4 = var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*"));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(213);
         var4 = var1.getlocal(7);
         PyObject var10000 = var4._gt(var1.getlocal(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(218);
            throw Py.makeException(var1.getglobal("CertificateError").__call__(var2, PyString.fromInterned("too many wildcards in certificate DNS name: ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
         } else {
            var1.setline(222);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(223);
               var4 = var1.getlocal(0).__getattr__("lower").__call__(var2);
               var10000 = var4._eq(var1.getlocal(1).__getattr__("lower").__call__(var2));
               var4 = null;
               var6 = var10000;
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(228);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(PyString.fromInterned("*"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(231);
                  var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^.]+"));
               } else {
                  var1.setline(232);
                  var10000 = var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xn--"));
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xn--"));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(237);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(5)));
                  } else {
                     var1.setline(240);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(5)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\*"), (PyObject)PyString.fromInterned("[^.]*")));
                  }
               }

               var1.setline(243);
               var4 = var1.getlocal(6).__iter__();

               while(true) {
                  var1.setline(243);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(246);
                     var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("\\A")._add(PyString.fromInterned("\\.").__getattr__("join").__call__(var2, var1.getlocal(3)))._add(PyString.fromInterned("\\Z")), var1.getglobal("re").__getattr__("IGNORECASE"));
                     var1.setlocal(9, var4);
                     var4 = null;
                     var1.setline(247);
                     var6 = var1.getlocal(9).__getattr__("match").__call__(var2, var1.getlocal(1));
                     var1.f_lasti = -1;
                     return var6;
                  }

                  var1.setlocal(8, var5);
                  var1.setline(244);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(8)));
               }
            }
         }
      }
   }

   public PyObject match_hostname$9(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyString.fromInterned("Verify that *cert* (in decoded format as returned by\n    SSLSocket.getpeercert()) matches the *hostname*.  RFC 2818 and RFC 6125\n    rules are followed, but IP addresses are not accepted for *hostname*.\n\n    CertificateError is raised on failure. On success, the function\n    returns nothing.\n    ");
      var1.setline(258);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(259);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("empty or no certificate, match_hostname needs a SSL socket or SSL context with either CERT_OPTIONAL or CERT_REQUIRED")));
      } else {
         var1.setline(262);
         PyList var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(263);
         PyObject var9 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("subjectAltName"), (PyObject)(new PyTuple(Py.EmptyObjects)));
         var1.setlocal(3, var9);
         var3 = null;
         var1.setline(264);
         var9 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(264);
            PyObject var4 = var9.__iternext__();
            PyObject var10000;
            PyObject var6;
            PyObject var10;
            if (var4 == null) {
               var1.setline(269);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(272);
                  var9 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("subject"), (PyObject)(new PyTuple(Py.EmptyObjects))).__iter__();

                  while(true) {
                     var1.setline(272);
                     var4 = var9.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(6, var4);
                     var1.setline(273);
                     var10 = var1.getlocal(6).__iter__();

                     while(true) {
                        var1.setline(273);
                        var6 = var10.__iternext__();
                        if (var6 == null) {
                           break;
                        }

                        PyObject[] var7 = Py.unpackSequence(var6, 2);
                        PyObject var8 = var7[0];
                        var1.setlocal(4, var8);
                        var8 = null;
                        var8 = var7[1];
                        var1.setlocal(5, var8);
                        var8 = null;
                        var1.setline(276);
                        PyObject var11 = var1.getlocal(4);
                        var10000 = var11._eq(PyString.fromInterned("commonName"));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(277);
                           if (var1.getglobal("_dnsname_match").__call__(var2, var1.getlocal(5), var1.getlocal(1)).__nonzero__()) {
                              var1.setline(278);
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setline(279);
                           var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5));
                        }
                     }
                  }
               }

               var1.setline(280);
               var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var9._gt(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(281);
                  throw Py.makeException(var1.getglobal("CertificateError").__call__(var2, PyString.fromInterned("hostname %r doesn't match either of %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(2)))}))));
               } else {
                  var1.setline(284);
                  var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                  var10000 = var9._eq(Py.newInteger(1));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(285);
                     throw Py.makeException(var1.getglobal("CertificateError").__call__(var2, PyString.fromInterned("hostname %r doesn't match %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(0))}))));
                  } else {
                     var1.setline(289);
                     throw Py.makeException(var1.getglobal("CertificateError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no appropriate commonName or subjectAltName fields were found")));
                  }
               }
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(265);
            var10 = var1.getlocal(4);
            var10000 = var10._eq(PyString.fromInterned("DNS"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(266);
               if (var1.getglobal("_dnsname_match").__call__(var2, var1.getlocal(5), var1.getlocal(1)).__nonzero__()) {
                  var1.setline(267);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(268);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject get_default_verify_paths$10(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyString.fromInterned("Return paths to default cafile and capath.\n    ");
      var1.setline(301);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(0, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(302);
      PyObject var6 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SSL_CERT_DIR"), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(303);
      var6 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SSL_CERT_FILE"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(305);
      var6 = var1.getglobal("System").__getattr__("getProperty").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("javax.net.ssl.trustStore"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(307);
      var6 = var1.getlocal(4);
      PyObject var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(4));
      }

      if (var10000.__nonzero__()) {
         var1.setline(308);
         var6 = var1.getlocal(4);
         var1.setlocal(0, var6);
         var3 = null;
      } else {
         var1.setline(310);
         var6 = var1.getlocal(2);
         var10000 = var6._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(311);
            var1.setline(311);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2)).__nonzero__() ? var1.getlocal(2) : var1.getglobal("None");
            var1.setlocal(1, var6);
            var3 = null;
         }

         var1.setline(312);
         var6 = var1.getlocal(3);
         var10000 = var6._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(313);
            var1.setline(313);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__() ? var1.getlocal(3) : var1.getglobal("None");
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(315);
         var6 = var1.getlocal(0);
         var10000 = var6._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(317);
            var6 = var1.getglobal("System").__getattr__("getProperty").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java.home"));
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(318);
            var6 = (new PyTuple(new PyObject[]{PyString.fromInterned("lib/security/jssecacerts"), PyString.fromInterned("lib/security/cacerts")})).__iter__();

            while(true) {
               var1.setline(318);
               PyObject var7 = var6.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(6, var7);
               var1.setline(319);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(6));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(320);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(4)).__nonzero__()) {
                  var1.setline(321);
                  var5 = var1.getlocal(4);
                  var1.setlocal(0, var5);
                  var5 = null;
                  var1.setline(322);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(0));
                  var1.setlocal(1, var5);
                  var5 = null;
               }
            }
         }
      }

      var1.setline(324);
      var10000 = var1.getglobal("DefaultVerifyPaths");
      PyObject[] var8 = new PyObject[6];
      var1.setline(324);
      var8[0] = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(0)).__nonzero__() ? var1.getlocal(0) : var1.getglobal("None");
      var1.setline(325);
      PyObject var10002 = var1.getlocal(1);
      if (var10002.__nonzero__()) {
         var10002 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1));
      }

      var8[1] = var10002.__nonzero__() ? var1.getlocal(1) : var1.getglobal("None");
      var8[2] = PyString.fromInterned("SSL_CERT_FILE");
      var8[3] = var1.getlocal(3);
      var8[4] = PyString.fromInterned("SSL_CERT_DIR");
      var8[5] = var1.getlocal(2);
      var6 = var10000.__call__(var2, var8);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _ASN1Object$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("ASN.1 object identifier lookup\n    "));
      var1.setline(332);
      PyString.fromInterned("ASN.1 object identifier lookup\n    ");
      var1.setline(333);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(335);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$12, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$12(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("1.3.6.1.5.5.7.3.1"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(338);
         var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_ASN1Object"), var1.getlocal(0)).__getattr__("__new__");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0), Py.newInteger(129), PyString.fromInterned("serverAuth"), PyString.fromInterned("TLS Web Server Authentication"), var1.getlocal(1)};
         var3 = var10000.__call__(var2, var5);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(339);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("1.3.6.1.5.5.7.3.2"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(340);
            var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_ASN1Object"), var1.getlocal(0)).__getattr__("__new__");
            PyObject[] var6 = new PyObject[]{var1.getlocal(0), Py.newInteger(130), PyString.fromInterned("clientAuth"), PyString.fromInterned("clientAuth"), var1.getlocal(1)};
            var3 = var10000.__call__(var2, var6);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(341);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2));
         }
      }
   }

   public PyObject Purpose$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("SSLContext purpose flags with X509v3 Extended Key Usage objects\n    "));
      var1.setline(346);
      PyString.fromInterned("SSLContext purpose flags with X509v3 Extended Key Usage objects\n    ");
      return var1.getf_locals();
   }

   public PyObject create_default_context$14(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyString.fromInterned("Create a SSLContext object with default settings.\n\n    NOTE: The protocol and settings may change anytime without prior\n          deprecation. The values represent a fair balance between maximum\n          compatibility and security.\n    ");
      var1.setline(360);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_ASN1Object")).__not__().__nonzero__()) {
         var1.setline(361);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(363);
         PyObject var3 = var1.getglobal("SSLContext").__call__(var2, var1.getglobal("PROTOCOL_SSLv23"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(366);
         PyObject var10000 = var1.getlocal(4);
         String var6 = "options";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._ior(var1.getglobal("OP_NO_SSLv2"));
         var4.__setattr__(var6, var5);
         var1.setline(370);
         var10000 = var1.getlocal(4);
         var6 = "options";
         var4 = var10000;
         var5 = var4.__getattr__(var6);
         var5 = var5._ior(var1.getglobal("OP_NO_SSLv3"));
         var4.__setattr__(var6, var5);
         var1.setline(376);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getglobal("Purpose").__getattr__("SERVER_AUTH"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(378);
            var3 = var1.getglobal("CERT_REQUIRED");
            var1.getlocal(4).__setattr__("verify_mode", var3);
            var3 = null;
            var1.setline(379);
            var3 = var1.getglobal("True");
            var1.getlocal(4).__setattr__("check_hostname", var3);
            var3 = null;
         } else {
            var1.setline(380);
            var3 = var1.getlocal(0);
            var10000 = var3._eq(var1.getglobal("Purpose").__getattr__("CLIENT_AUTH"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(381);
            }
         }

         var1.setline(394);
         var10000 = var1.getlocal(1);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(3);
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(395);
            var1.getlocal(4).__getattr__("load_verify_locations").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         } else {
            var1.setline(396);
            var3 = var1.getlocal(4).__getattr__("verify_mode");
            var10000 = var3._ne(var1.getglobal("CERT_NONE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(400);
               var1.getlocal(4).__getattr__("load_default_certs").__call__(var2, var1.getlocal(0));
            }
         }

         var1.setline(401);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _create_unverified_context$15(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyString.fromInterned("Create a SSLContext object for Python stdlib modules\n\n    All Python stdlib modules shall use this function to create SSLContext\n    objects in order to keep common settings in one place. The configuration\n    is less restricted than create_default_context()'s to increase backward\n    compatibility.\n    ");
      var1.setline(415);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("_ASN1Object")).__not__().__nonzero__()) {
         var1.setline(416);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(3)));
      } else {
         var1.setline(418);
         PyObject var3 = var1.getglobal("SSLContext").__call__(var2, var1.getlocal(0));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(420);
         PyObject var10000 = var1.getlocal(9);
         String var6 = "options";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._ior(var1.getglobal("OP_NO_SSLv2"));
         var4.__setattr__(var6, var5);
         var1.setline(423);
         var10000 = var1.getlocal(9);
         var6 = "options";
         var4 = var10000;
         var5 = var4.__getattr__(var6);
         var5 = var5._ior(var1.getglobal("OP_NO_SSLv3"));
         var4.__setattr__(var6, var5);
         var1.setline(425);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(426);
            var3 = var1.getlocal(1);
            var1.getlocal(9).__setattr__("verify_mode", var3);
            var3 = null;
         }

         var1.setline(427);
         var3 = var1.getlocal(2);
         var1.getlocal(9).__setattr__("check_hostname", var3);
         var3 = null;
         var1.setline(429);
         var10000 = var1.getlocal(5);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(430);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("certfile must be specified")));
         } else {
            var1.setline(431);
            var10000 = var1.getlocal(4);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(5);
            }

            if (var10000.__nonzero__()) {
               var1.setline(432);
               var1.getlocal(9).__getattr__("load_cert_chain").__call__(var2, var1.getlocal(4), var1.getlocal(5));
            }

            var1.setline(435);
            var10000 = var1.getlocal(6);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(7);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(8);
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(436);
               var1.getlocal(9).__getattr__("load_verify_locations").__call__(var2, var1.getlocal(6), var1.getlocal(7), var1.getlocal(8));
            } else {
               var1.setline(437);
               var3 = var1.getlocal(9).__getattr__("verify_mode");
               var10000 = var3._ne(var1.getglobal("CERT_NONE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(441);
                  var1.getlocal(9).__getattr__("load_default_certs").__call__(var2, var1.getlocal(3));
               }
            }

            var1.setline(443);
            var3 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject SSLInitializer$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(455);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(458);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, initChannel$18, (PyObject)null);
      var1.setlocal("initChannel", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ssl_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initChannel$18(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      PyObject var3 = var1.getlocal(1).__getattr__("pipeline").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(460);
      var1.getlocal(2).__getattr__("addFirst").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ssl"), (PyObject)var1.getlocal(0).__getattr__("ssl_handler"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SSLSocket$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(465);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("CERT_NONE"), var1.getname("PROTOCOL_SSLv23"), var1.getname("None"), var1.getname("True"), var1.getname("True"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(592);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, context$23, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("context", var5);
      var3 = null;
      var1.setline(596);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, context$24, (PyObject)null);
      var5 = var1.getname("context").__getattr__("setter").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("context", var5);
      var3 = null;
      var1.setline(600);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setup_engine$25, (PyObject)null);
      var1.setlocal("setup_engine", var4);
      var3 = null;
      var1.setline(609);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, connect$26, PyString.fromInterned("Connects to remote ADDR, and then wraps the connection in\n        an SSL channel."));
      var1.setlocal("connect", var4);
      var3 = null;
      var1.setline(623);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, connect_ex$27, PyString.fromInterned("Connects to remote ADDR, and then wraps the connection in\n        an SSL channel."));
      var1.setlocal("connect_ex", var4);
      var3 = null;
      var1.setline(640);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, accept$28, PyString.fromInterned("Accepts a new connection from a remote client, and returns\n        a tuple containing that new connection wrapped with a server-side\n        SSL channel, and the address of the remote client."));
      var1.setlocal("accept", var4);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unwrap$29, (PyObject)null);
      var1.setlocal("unwrap", var4);
      var3 = null;
      var1.setline(664);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_handshake$30, (PyObject)null);
      var1.setlocal("do_handshake", var4);
      var3 = null;
      var1.setline(693);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dup$32, (PyObject)null);
      var1.setlocal("dup", var4);
      var3 = null;
      var1.setline(697);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ensure_handshake$33, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_ensure_handshake", var5);
      var3 = null;
      var1.setline(711);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$34, (PyObject)null);
      var1.setlocal("send", var4);
      var3 = null;
      var1.setline(715);
      var5 = var1.getname("send");
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(717);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sendall$35, (PyObject)null);
      var1.setlocal("sendall", var4);
      var3 = null;
      var1.setline(721);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recv$36, (PyObject)null);
      var1.setlocal("recv", var4);
      var3 = null;
      var1.setline(725);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$37, PyString.fromInterned("Read up to LEN bytes and return them.\n        Return zero-length string on EOF."));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(748);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recvfrom$38, (PyObject)null);
      var1.setlocal("recvfrom", var4);
      var3 = null;
      var1.setline(752);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recvfrom_into$39, (PyObject)null);
      var1.setlocal("recvfrom_into", var4);
      var3 = null;
      var1.setline(756);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recv_into$40, (PyObject)null);
      var1.setlocal("recv_into", var4);
      var3 = null;
      var1.setline(760);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, sendto$41, (PyObject)null);
      var1.setlocal("sendto", var4);
      var3 = null;
      var1.setline(766);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$42, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(769);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setblocking$43, (PyObject)null);
      var1.setlocal("setblocking", var4);
      var3 = null;
      var1.setline(772);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, settimeout$44, (PyObject)null);
      var1.setlocal("settimeout", var4);
      var3 = null;
      var1.setline(775);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gettimeout$45, (PyObject)null);
      var1.setlocal("gettimeout", var4);
      var3 = null;
      var1.setline(778);
      var3 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, makefile$46, (PyObject)null);
      var1.setlocal("makefile", var4);
      var3 = null;
      var1.setline(781);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shutdown$47, (PyObject)null);
      var1.setlocal("shutdown", var4);
      var3 = null;
      var1.setline(785);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pending$48, (PyObject)null);
      var1.setlocal("pending", var4);
      var3 = null;
      var1.setline(790);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _readable$49, (PyObject)null);
      var1.setlocal("_readable", var4);
      var3 = null;
      var1.setline(793);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _writable$50, (PyObject)null);
      var1.setlocal("_writable", var4);
      var3 = null;
      var1.setline(796);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _register_selector$51, (PyObject)null);
      var1.setlocal("_register_selector", var4);
      var3 = null;
      var1.setline(799);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _unregister_selector$52, (PyObject)null);
      var1.setlocal("_unregister_selector", var4);
      var3 = null;
      var1.setline(802);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _notify_selectors$53, (PyObject)null);
      var1.setlocal("_notify_selectors", var4);
      var3 = null;
      var1.setline(805);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _checkClosed$54, (PyObject)null);
      var1.setlocal("_checkClosed", var4);
      var3 = null;
      var1.setline(809);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _check_connected$55, (PyObject)null);
      var1.setlocal("_check_connected", var4);
      var3 = null;
      var1.setline(817);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getpeername$56, (PyObject)null);
      var1.setlocal("getpeername", var4);
      var3 = null;
      var1.setline(820);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, selected_npn_protocol$57, (PyObject)null);
      var1.setlocal("selected_npn_protocol", var4);
      var3 = null;
      var1.setline(825);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, selected_alpn_protocol$58, (PyObject)null);
      var1.setlocal("selected_alpn_protocol", var4);
      var3 = null;
      var1.setline(829);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$59, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(832);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, getpeercert$60, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("getpeercert", var5);
      var3 = null;
      var1.setline(854);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, issuer$62, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("issuer", var5);
      var3 = null;
      var1.setline(858);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, cipher$63, (PyObject)null);
      var1.setlocal("cipher", var4);
      var3 = null;
      var1.setline(869);
      var3 = new PyObject[]{PyString.fromInterned("tls-unique")};
      var4 = new PyFunction(var1.f_globals, var3, get_channel_binding$64, PyString.fromInterned("Get channel binding data for current connection.  Raise ValueError\n        if the requested `cb_type` is not supported.  Return bytes of the data\n        or None if the data is not available (e.g. before the handshake).\n        "));
      var1.setlocal("get_channel_binding", var4);
      var3 = null;
      var1.setline(887);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, version$65, (PyObject)null);
      var1.setlocal("version", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(470);
      PyObject var3 = var1.getlocal(1);
      var1.getderef(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(471);
      var3 = var1.getlocal(8);
      var1.getderef(0).__setattr__("do_handshake_on_connect", var3);
      var3 = null;
      var1.setline(472);
      var3 = var1.getlocal(1).__getattr__("_sock");
      var1.getderef(0).__setattr__("_sock", var3);
      var3 = null;
      var1.setline(481);
      var3 = var1.getglobal("False");
      var1.getderef(0).__setattr__("_connected", var3);
      var3 = null;
      var1.setline(482);
      PyObject var10000;
      if (var1.getlocal(13).__nonzero__()) {
         var1.setline(483);
         var3 = var1.getlocal(13);
         var1.getderef(0).__setattr__("_context", var3);
         var3 = null;
      } else {
         var1.setline(485);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(486);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("certfile must be specified for server-side operations")));
         }

         var1.setline(488);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(489);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("certfile must be specified")));
         }

         var1.setline(490);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(491);
            var3 = var1.getlocal(3);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(492);
         var3 = var1.getglobal("SSLContext").__call__(var2, var1.getlocal(6));
         var1.getderef(0).__setattr__("_context", var3);
         var3 = null;
         var1.setline(493);
         var3 = var1.getlocal(5);
         var1.getderef(0).__getattr__("_context").__setattr__("verify_mode", var3);
         var3 = null;
         var1.setline(494);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(495);
            var1.getderef(0).__getattr__("_context").__getattr__("load_verify_locations").__call__(var2, var1.getlocal(7));
         }

         var1.setline(496);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(497);
            var1.getderef(0).__getattr__("_context").__getattr__("load_cert_chain").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         }

         var1.setline(498);
         if (var1.getlocal(10).__nonzero__()) {
            var1.setline(499);
            var1.getderef(0).__getattr__("_context").__getattr__("set_npn_protocols").__call__(var2, var1.getlocal(10));
         }

         var1.setline(500);
         if (var1.getlocal(11).__nonzero__()) {
            var1.setline(501);
            var1.getderef(0).__getattr__("_context").__getattr__("set_ciphers").__call__(var2, var1.getlocal(11));
         }

         var1.setline(502);
         var3 = var1.getlocal(2);
         var1.getderef(0).__setattr__("keyfile", var3);
         var3 = null;
         var1.setline(503);
         var3 = var1.getlocal(3);
         var1.getderef(0).__setattr__("certfile", var3);
         var3 = null;
         var1.setline(504);
         var3 = var1.getlocal(5);
         var1.getderef(0).__setattr__("cert_reqs", var3);
         var3 = null;
         var1.setline(505);
         var3 = var1.getlocal(6);
         var1.getderef(0).__setattr__("ssl_version", var3);
         var3 = null;
         var1.setline(506);
         var3 = var1.getlocal(7);
         var1.getderef(0).__setattr__("ca_certs", var3);
         var3 = null;
         var1.setline(507);
         var3 = var1.getlocal(11);
         var1.getderef(0).__setattr__("ciphers", var3);
         var3 = null;
      }

      var1.setline(509);
      var3 = var1.getlocal(1).__getattr__("getsockopt").__call__(var2, var1.getglobal("SOL_SOCKET"), var1.getglobal("SO_TYPE"));
      var10000 = var3._ne(var1.getglobal("SOCK_STREAM"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(510);
         throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("only stream sockets are supported")));
      } else {
         var1.setline(512);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(12);
         }

         if (var10000.__nonzero__()) {
            var1.setline(513);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("server_hostname can only be specified in client mode")));
         } else {
            var1.setline(515);
            var10000 = var1.getderef(0).__getattr__("_context").__getattr__("check_hostname");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(12).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(516);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("check_hostname requires server_hostname")));
            } else {
               var1.setline(517);
               var3 = var1.getlocal(4);
               var1.getderef(0).__setattr__("server_side", var3);
               var3 = null;
               var1.setline(518);
               var3 = var1.getlocal(12);
               var1.getderef(0).__setattr__("server_hostname", var3);
               var3 = null;
               var1.setline(519);
               var3 = var1.getlocal(9);
               var1.getderef(0).__setattr__("suppress_ragged_eofs", var3);
               var3 = null;
               var1.setline(521);
               var3 = var1.getglobal("None");
               var1.getderef(0).__setattr__("ssl_handler", var3);
               var3 = null;
               var1.setline(533);
               var3 = var1.getderef(0);
               var1.getderef(0).__setattr__("_sslobj", var3);
               var3 = null;
               var1.setline(535);
               var3 = var1.getglobal("None");
               var1.getderef(0).__setattr__("engine", var3);
               var3 = null;
               var1.setline(537);
               var10000 = var1.getderef(0).__getattr__("do_handshake_on_connect");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getderef(0).__getattr__("_sock").__getattr__("connected");
               }

               PyObject var10002;
               PyObject[] var10003;
               PyCode var10004;
               PyObject[] var5;
               PyFunction var6;
               if (var10000.__nonzero__()) {
                  var1.setline(538);
                  var10000 = var1.getglobal("log").__getattr__("debug");
                  var5 = new PyObject[]{PyString.fromInterned("Handshaking socket on connect"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
                  String[] var4 = new String[]{"extra"};
                  var10000.__call__(var2, var5, var4);
                  var3 = null;
                  var1.setline(539);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0).__getattr__("_sock"), var1.getglobal("ChildSocket")).__nonzero__()) {
                     var1.setline(549);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0).__getattr__("_sock").__getattr__("parent_socket"), var1.getglobal("SSLSocket")).__nonzero__()) {
                        var1.setline(551);
                        var10000 = var1.getglobal("log").__getattr__("debug");
                        var5 = new PyObject[]{PyString.fromInterned("Child socket - will handshake in child loop type=%s parent=%s"), var1.getglobal("type").__call__(var2, var1.getderef(0).__getattr__("_sock")), var1.getderef(0).__getattr__("_sock").__getattr__("parent_socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
                        var4 = new String[]{"extra"};
                        var10000.__call__(var2, var5, var4);
                        var3 = null;
                        var1.setline(555);
                        var1.getderef(0).__getattr__("_sock").__getattr__("_make_active").__call__(var2);
                     } else {
                        var1.setline(561);
                        var10000 = var1.getglobal("log").__getattr__("debug");
                        var5 = new PyObject[]{PyString.fromInterned("Child socket will wrap self with handshake"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
                        var4 = new String[]{"extra"};
                        var10000.__call__(var2, var5, var4);
                        var3 = null;
                        var1.setline(562);
                        var3 = var1.getglobal("CountDownLatch").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                        var1.setderef(1, var3);
                        var3 = null;
                        var1.setline(564);
                        var5 = Py.EmptyObjects;
                        var10002 = var1.f_globals;
                        var10003 = var5;
                        var10004 = setup_handshake$21;
                        var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
                        var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
                        var1.setlocal(14, var6);
                        var3 = null;
                        var1.setline(569);
                        var3 = var1.getlocal(14);
                        var1.getderef(0).__getattr__("_sock").__setattr__("ssl_wrap_self", var3);
                        var3 = null;
                        var1.setline(570);
                        var1.getderef(0).__getattr__("_sock").__getattr__("_make_active").__call__(var2);
                        var1.setline(571);
                        var1.getderef(1).__getattr__("await").__call__(var2);
                        var1.setline(572);
                        var10000 = var1.getglobal("log").__getattr__("debug");
                        var5 = new PyObject[]{PyString.fromInterned("Child socket waiting on handshake=%s"), var1.getderef(0).__getattr__("_handshake_future"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
                        var4 = new String[]{"extra"};
                        var10000.__call__(var2, var5, var4);
                        var3 = null;
                        var1.setline(573);
                        var1.getderef(0).__getattr__("_sock").__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("_handshake_future"), (PyObject)PyString.fromInterned("SSL handshake"));
                     }
                  } else {
                     var1.setline(575);
                     var1.getderef(0).__getattr__("do_handshake").__call__(var2);
                  }
               }

               var1.setline(577);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("_sock"), (PyObject)PyString.fromInterned("accepted_children")).__nonzero__()) {
                  var1.setline(578);
                  var5 = Py.EmptyObjects;
                  var10002 = var1.f_globals;
                  var10003 = var5;
                  var10004 = wrap_child$22;
                  var5 = new PyObject[]{var1.getclosure(0)};
                  var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
                  var1.setlocal(15, var6);
                  var3 = null;
                  var1.setline(590);
                  var3 = var1.getlocal(15);
                  var1.getderef(0).__getattr__("_sock").__setattr__("ssl_wrap_child_socket", var3);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject setup_handshake$21(PyFrame var1, ThreadState var2) {
      var1.setline(565);
      PyObject var3 = var1.getderef(0).__getattr__("do_handshake").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(566);
      var1.getderef(1).__getattr__("countDown").__call__(var2);
      var1.setline(567);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wrap_child$22(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Wrapping child socket - about to handshake! parent=%s"), var1.getderef(0).__getattr__("_sock"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(582);
      var10000 = var1.getderef(0).__getattr__("context").__getattr__("wrap_socket");
      var3 = new PyObject[4];
      PyObject var10002 = var1.getglobal("_socketobject");
      PyObject[] var6 = new PyObject[]{var1.getlocal(0)};
      String[] var5 = new String[]{"_sock"};
      var10002 = var10002.__call__(var2, var6, var5);
      var4 = null;
      var3[0] = var10002;
      var3[1] = var1.getderef(0).__getattr__("do_handshake_on_connect");
      var3[2] = var1.getderef(0).__getattr__("suppress_ragged_eofs");
      var3[3] = var1.getglobal("True");
      var4 = new String[]{"do_handshake_on_connect", "suppress_ragged_eofs", "server_side"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var7 = var10000;
      var1.getlocal(0).__setattr__("_wrapper_socket", var7);
      var3 = null;
      var1.setline(587);
      if (var1.getderef(0).__getattr__("do_handshake_on_connect").__nonzero__()) {
         var1.setline(589);
         var1.getlocal(0).__getattr__("_wrapper_socket").__getattr__("do_handshake").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject context$23(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getattr__("_context");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject context$24(PyFrame var1, ThreadState var2) {
      var1.setline(598);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_context", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setup_engine$25(PyFrame var1, ThreadState var2) {
      var1.setline(601);
      PyObject var3 = var1.getlocal(0).__getattr__("engine");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(603);
         var10000 = var1.getlocal(0).__getattr__("_context").__getattr__("_createSSLEngine");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("server_hostname"), var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("certfile"), (PyObject)var1.getglobal("None")), var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("keyfile"), (PyObject)var1.getglobal("None")), var1.getlocal(0).__getattr__("server_side")};
         String[] var4 = new String[]{"cert_file", "key_file", "server_side"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.getlocal(0).__setattr__("engine", var3);
         var3 = null;
         var1.setline(607);
         var1.getlocal(0).__getattr__("engine").__getattr__("setUseClientMode").__call__(var2, var1.getlocal(0).__getattr__("server_side").__not__());
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$26(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyString.fromInterned("Connects to remote ADDR, and then wraps the connection in\n        an SSL channel.");
      var1.setline(612);
      if (var1.getlocal(0).__getattr__("server_side").__nonzero__()) {
         var1.setline(613);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't connect in server-side mode")));
      } else {
         var1.setline(614);
         if (var1.getlocal(0).__getattr__("_connected").__nonzero__()) {
            var1.setline(615);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attempt to connect already-connected SSLSocket!")));
         } else {
            var1.setline(617);
            PyObject var10000 = var1.getglobal("log").__getattr__("debug");
            PyObject[] var3 = new PyObject[]{PyString.fromInterned("Connect SSL with handshaking %s"), var1.getlocal(0).__getattr__("do_handshake_on_connect"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("_sock")})};
            String[] var4 = new String[]{"extra"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.setline(619);
            var1.getlocal(0).__getattr__("_sock").__getattr__("connect").__call__(var2, var1.getlocal(1));
            var1.setline(620);
            if (var1.getlocal(0).__getattr__("do_handshake_on_connect").__nonzero__()) {
               var1.setline(621);
               var1.getlocal(0).__getattr__("do_handshake").__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject connect_ex$27(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyString.fromInterned("Connects to remote ADDR, and then wraps the connection in\n        an SSL channel.");
      var1.setline(626);
      if (var1.getlocal(0).__getattr__("server_side").__nonzero__()) {
         var1.setline(627);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't connect in server-side mode")));
      } else {
         var1.setline(628);
         if (var1.getlocal(0).__getattr__("_connected").__nonzero__()) {
            var1.setline(629);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attempt to connect already-connected SSLSocket!")));
         } else {
            var1.setline(631);
            PyObject var10000 = var1.getglobal("log").__getattr__("debug");
            PyObject[] var3 = new PyObject[]{PyString.fromInterned("Connect SSL with handshaking %s"), var1.getlocal(0).__getattr__("do_handshake_on_connect"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("_sock")})};
            String[] var4 = new String[]{"extra"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.setline(633);
            PyObject var5 = var1.getlocal(0).__getattr__("_sock").__getattr__("connect_ex").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(634);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(var1.getglobal("errno").__getattr__("EISCONN"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(635);
               var5 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_connected", var5);
               var3 = null;
               var1.setline(636);
               if (var1.getlocal(0).__getattr__("do_handshake_on_connect").__nonzero__()) {
                  var1.setline(637);
                  var1.getlocal(0).__getattr__("do_handshake").__call__(var2);
               }
            }

            var1.setline(638);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject accept$28(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      PyString.fromInterned("Accepts a new connection from a remote client, and returns\n        a tuple containing that new connection wrapped with a server-side\n        SSL channel, and the address of the remote client.");
      var1.setline(644);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("accept").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(645);
      if (var1.getlocal(0).__getattr__("do_handshake_on_connect").__nonzero__()) {
         var1.setline(646);
         var3 = var1.getlocal(1).__getattr__("_wrapper_socket");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(647);
         var1.getlocal(1).__delattr__("_wrapper_socket");
         var1.setline(648);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(650);
         PyObject var10000 = var1.getlocal(0).__getattr__("context").__getattr__("wrap_socket");
         var4 = new PyObject[4];
         PyObject var10002 = var1.getglobal("_socketobject");
         PyObject[] var8 = new PyObject[]{var1.getlocal(1)};
         String[] var6 = new String[]{"_sock"};
         var10002 = var10002.__call__(var2, var8, var6);
         var5 = null;
         var4[0] = var10002;
         var4[1] = var1.getlocal(0).__getattr__("do_handshake_on_connect");
         var4[2] = var1.getlocal(0).__getattr__("suppress_ragged_eofs");
         var4[3] = var1.getglobal("True");
         String[] var9 = new String[]{"do_handshake_on_connect", "suppress_ragged_eofs", "server_side"};
         var10000 = var10000.__call__(var2, var4, var9);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unwrap$29(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(658);
         var1.getlocal(0).__getattr__("_sock").__getattr__("channel").__getattr__("pipeline").__call__(var2).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ssl"));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("NoSuchElementException"))) {
            throw var3;
         }

         var1.setline(660);
      }

      var1.setline(661);
      var1.getlocal(0).__getattr__("ssl_handler").__getattr__("close").__call__(var2);
      var1.setline(662);
      PyObject var5 = var1.getlocal(0).__getattr__("_sock");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject do_handshake$30(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(665);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("SSL handshaking"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(666);
      var1.getderef(0).__getattr__("setup_engine").__call__(var2, var1.getderef(0).__getattr__("sock").__getattr__("getpeername").__call__(var2));
      var1.setline(668);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = handshake_step$31;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(672);
      PyObject var6 = var1.getderef(0).__getattr__("ssl_handler");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(673);
         var6 = var1.getglobal("SslHandler").__call__(var2, var1.getderef(0).__getattr__("engine"));
         var1.getderef(0).__setattr__("ssl_handler", var6);
         var3 = null;
         var1.setline(674);
         var1.getderef(0).__getattr__("ssl_handler").__getattr__("handshakeFuture").__call__(var2).__getattr__("addListener").__call__(var2, var1.getlocal(1));
         var1.setline(676);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("_sock"), (PyObject)PyString.fromInterned("connected"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getderef(0).__getattr__("_sock").__getattr__("connected");
         }

         if (var10000.__nonzero__()) {
            var1.setline(678);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var3 = new PyObject[]{PyString.fromInterned("Adding SSL handler to pipeline after connection"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
            var4 = new String[]{"extra"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.setline(679);
            var1.getderef(0).__getattr__("_sock").__getattr__("channel").__getattr__("pipeline").__call__(var2).__getattr__("addFirst").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ssl"), (PyObject)var1.getderef(0).__getattr__("ssl_handler"));
         } else {
            var1.setline(681);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var3 = new PyObject[]{PyString.fromInterned("Not connected, adding SSL initializer..."), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
            var4 = new String[]{"extra"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.setline(682);
            var1.getderef(0).__getattr__("_sock").__getattr__("connect_handlers").__getattr__("append").__call__(var2, var1.getglobal("SSLInitializer").__call__(var2, var1.getderef(0).__getattr__("ssl_handler")));
         }
      }

      var1.setline(684);
      var6 = var1.getderef(0).__getattr__("ssl_handler").__getattr__("handshakeFuture").__call__(var2);
      var1.getderef(0).__setattr__("_handshake_future", var6);
      var3 = null;
      var1.setline(685);
      if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0).__getattr__("_sock"), var1.getglobal("ChildSocket")).__nonzero__()) {
         var1.setline(686);
      } else {
         var1.setline(691);
         var1.getderef(0).__getattr__("_sock").__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("_handshake_future"), (PyObject)PyString.fromInterned("SSL handshake"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handshake_step$31(PyFrame var1, ThreadState var2) {
      var1.setline(669);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("SSL handshaking completed %s"), var1.getlocal(0), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("_sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(670);
      var1.getderef(0).__getattr__("_notify_selectors").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dup$32(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      throw Py.makeException(var1.getglobal("NotImplemented").__call__(var2, PyString.fromInterned("Can't dup() %s instances")._mod(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"))));
   }

   public PyObject _ensure_handshake$33(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Ensure handshake"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(700);
      var1.getlocal(0).__getattr__("_sock").__getattr__("_make_active").__call__(var2);
      var1.setline(703);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_handshake_future")).__not__().__nonzero__()) {
         var1.setline(704);
         var1.getlocal(0).__getattr__("do_handshake").__call__(var2);
      }

      var1.setline(706);
      var1.getlocal(0).__getattr__("_handshake_future").__getattr__("sync").__call__(var2);
      var1.setline(707);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var3 = new PyObject[]{PyString.fromInterned("Completed post connect"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send$34(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(713);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("send").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sendall$35(PyFrame var1, ThreadState var2) {
      var1.setline(718);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(719);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recv$36(PyFrame var1, ThreadState var2) {
      var1.setline(722);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(723);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("recv").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$37(PyFrame var1, ThreadState var2) {
      var1.setline(727);
      PyString.fromInterned("Read up to LEN bytes and return them.\n        Return zero-length string on EOF.");
      var1.setline(728);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(729);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);

      PyObject var10000;
      PyString var3;
      try {
         var1.setline(734);
         PyObject var8 = var1.getlocal(2);
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(735);
            var10000 = var1.getlocal(0).__getattr__("recvfrom_into");
            PyObject var10002 = var1.getlocal(2);
            Object var10003 = var1.getlocal(1);
            if (!((PyObject)var10003).__nonzero__()) {
               var10003 = Py.newInteger(1024);
            }

            var8 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
            var1.setlocal(3, var8);
            var3 = null;
         } else {
            var1.setline(737);
            var10000 = var1.getlocal(0).__getattr__("recv");
            Object var9 = var1.getlocal(1);
            if (!((PyObject)var9).__nonzero__()) {
               var9 = Py.newInteger(1024);
            }

            var8 = var10000.__call__((ThreadState)var2, (PyObject)var9);
            var1.setlocal(3, var8);
            var3 = null;
         }

         var1.setline(738);
         var8 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var8;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("SSLError"))) {
            PyObject var5 = var4.value;
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(740);
            var5 = var1.getlocal(4).__getattr__("args").__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("SSL_ERROR_EOF"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("suppress_ragged_eofs");
            }

            if (var10000.__nonzero__()) {
               var1.setline(741);
               var5 = var1.getlocal(2);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(742);
                  PyInteger var7 = Py.newInteger(0);
                  var1.f_lasti = -1;
                  return var7;
               } else {
                  var1.setline(744);
                  var3 = PyString.fromInterned("");
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(746);
               throw Py.makeException();
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject recvfrom$38(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(750);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("recvfrom").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recvfrom_into$39(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(754);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("recvfrom_into").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recv_into$40(PyFrame var1, ThreadState var2) {
      var1.setline(757);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(758);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("recv_into").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sendto$41(PyFrame var1, ThreadState var2) {
      var1.setline(763);
      var1.getlocal(0).__getattr__("_ensure_handshake").__call__(var2);
      var1.setline(764);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("send").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$42(PyFrame var1, ThreadState var2) {
      var1.setline(767);
      var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setblocking$43(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      var1.getlocal(0).__getattr__("sock").__getattr__("setblocking").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject settimeout$44(PyFrame var1, ThreadState var2) {
      var1.setline(773);
      var1.getlocal(0).__getattr__("sock").__getattr__("settimeout").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gettimeout$45(PyFrame var1, ThreadState var2) {
      var1.setline(776);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("gettimeout").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makefile$46(PyFrame var1, ThreadState var2) {
      var1.setline(779);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shutdown$47(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      var1.getlocal(0).__getattr__("sock").__getattr__("shutdown").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pending$48(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("_pending").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _readable$49(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("_readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _writable$50(PyFrame var1, ThreadState var2) {
      var1.setline(794);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("_writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _register_selector$51(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      var1.getlocal(0).__getattr__("_sock").__getattr__("_register_selector").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unregister_selector$52(PyFrame var1, ThreadState var2) {
      var1.setline(800);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("_unregister_selector").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _notify_selectors$53(PyFrame var1, ThreadState var2) {
      var1.setline(803);
      var1.getlocal(0).__getattr__("_sock").__getattr__("_notify_selectors").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _checkClosed$54(PyFrame var1, ThreadState var2) {
      var1.setline(807);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_connected$55(PyFrame var1, ThreadState var2) {
      var1.setline(810);
      if (var1.getlocal(0).__getattr__("_connected").__not__().__nonzero__()) {
         var1.setline(815);
         var1.getlocal(0).__getattr__("getpeername").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getpeername$56(PyFrame var1, ThreadState var2) {
      var1.setline(818);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("getpeername").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject selected_npn_protocol$57(PyFrame var1, ThreadState var2) {
      var1.setline(821);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(823);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject selected_alpn_protocol$58(PyFrame var1, ThreadState var2) {
      var1.setline(826);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileno$59(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getpeercert$60(PyFrame var1, ThreadState var2) {
      var1.setline(834);
      PyObject var3 = var1.getlocal(0).__getattr__("engine").__getattr__("getSession").__call__(var2).__getattr__("getPeerCertificates").__call__(var2).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(835);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(836);
         var3 = var1.getlocal(2).__getattr__("getEncoded").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(838);
         PyObject var4 = var1.getlocal(0).__getattr__("_context").__getattr__("verify_mode");
         PyObject var10000 = var4._eq(var1.getglobal("CERT_NONE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(839);
            PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(841);
            var4 = var1.getlocal(2).__getattr__("getSubjectX500Principal").__call__(var2).__getattr__("getName").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(842);
            var4 = var1.getglobal("SSLContext").__getattr__("_parse_dn").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(843);
            var4 = var1.getglobal("tuple").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(844);
            if (var1.getlocal(2).__getattr__("getSubjectAlternativeNames").__call__(var2).__nonzero__()) {
               var1.setline(845);
               var10000 = var1.getglobal("tuple");
               var1.setline(845);
               PyObject[] var7 = Py.EmptyObjects;
               PyFunction var5 = new PyFunction(var1.f_globals, var7, f$61, (PyObject)null);
               PyObject var10002 = var5.__call__(var2, var1.getlocal(2).__getattr__("getSubjectAlternativeNames").__call__(var2).__iter__());
               Arrays.fill(var7, (Object)null);
               var4 = var10000.__call__(var2, var10002);
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(847);
            PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("notAfter"), var1.getglobal("str").__call__(var2, var1.getglobal("_rfc2822_date_format").__getattr__("format").__call__(var2, var1.getlocal(2).__getattr__("getNotAfter").__call__(var2))), PyString.fromInterned("subject"), var1.getlocal(4), PyString.fromInterned("subjectAltName"), var1.getlocal(5)});
            var1.setlocal(7, var8);
            var4 = null;
            var1.setline(852);
            var3 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject f$61(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(845);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var8 = (PyObject)var10000;
      }

      var1.setline(845);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(845);
         var1.setline(845);
         var7 = new PyObject[]{var1.getglobal("_cert_name_types").__getitem__(var1.getlocal(1)), var1.getglobal("str").__call__(var2, var1.getlocal(2))};
         PyTuple var9 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public PyObject issuer$62(PyFrame var1, ThreadState var2) {
      var1.setline(856);
      PyObject var3 = var1.getlocal(0).__getattr__("getpeercert").__call__(var2).__getattr__("getIssuerDN").__call__(var2).__getattr__("toString").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cipher$63(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyObject var3 = var1.getlocal(0).__getattr__("engine").__getattr__("getSession").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(860);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("cipherSuite"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(861);
      PyString var4 = PyString.fromInterned("256");
      PyObject var10000 = var4._in(var1.getlocal(2));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(862);
         var5 = Py.newInteger(256);
         var1.setlocal(3, var5);
         var3 = null;
      } else {
         var1.setline(863);
         var4 = PyString.fromInterned("128");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(864);
            var5 = Py.newInteger(128);
            var1.setlocal(3, var5);
            var3 = null;
         } else {
            var1.setline(866);
            var3 = var1.getglobal("None");
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(867);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("protocol")), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject get_channel_binding$64(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      PyString.fromInterned("Get channel binding data for current connection.  Raise ValueError\n        if the requested `cb_type` is not supported.  Return bytes of the data\n        or None if the data is not available (e.g. before the handshake).\n        ");
      var1.setline(874);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getglobal("CHANNEL_BINDING_TYPES"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(875);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unsupported channel binding type")));
      } else {
         var1.setline(876);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(PyString.fromInterned("tls-unique"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(877);
            throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, PyString.fromInterned("{0} channel binding type not implemented").__getattr__("format").__call__(var2, var1.getlocal(1))));
         } else {
            var1.setline(882);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject version$65(PyFrame var1, ThreadState var2) {
      var1.setline(888);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("ssl_handler").__nonzero__()) {
         var1.setline(889);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("engine").__getattr__("getSession").__call__(var2).__getattr__("getProtocol").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(890);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject wrap_socket$66(PyFrame var1, ThreadState var2) {
      var1.setline(904);
      PyObject var10000 = var1.getglobal("SSLSocket");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(4), var1.getlocal(6), var1.getlocal(3), var1.getlocal(5), var1.getlocal(9), var1.getlocal(7)};
      String[] var4 = new String[]{"keyfile", "certfile", "cert_reqs", "ca_certs", "server_side", "ssl_version", "ciphers", "do_handshake_on_connect"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject cert_time_to_seconds$67(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyString.fromInterned("Return the time in seconds since the Epoch, given the timestring\n    representing the \"notBefore\" or \"notAfter\" date from a certificate\n    in ``\"%b %d %H:%M:%S %Y %Z\"`` strptime format (C locale).\n\n    \"notBefore\" or \"notAfter\" dates must use UTC (RFC 5280).\n\n    Month is one of: Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec\n    UTC should be specified as GMT (see ASN1_TIME_print())\n    ");
      var1.setline(923);
      String[] var3 = new String[]{"strptime"};
      PyObject[] var6 = imp.importFrom("time", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(924);
      var3 = new String[]{"timegm"};
      var6 = imp.importFrom("calendar", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(926);
      PyTuple var7 = new PyTuple(new PyObject[]{PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")});
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(930);
      PyString var8 = PyString.fromInterned(" %d %H:%M:%S %Y GMT");
      var1.setlocal(4, var8);
      var3 = null;

      try {
         var1.setline(932);
         PyObject var10 = var1.getlocal(3).__getattr__("index").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null).__getattr__("title").__call__(var2))._add(Py.newInteger(1));
         var1.setlocal(5, var10);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (var9.match(var1.getglobal("ValueError"))) {
            var1.setline(934);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("time data %r does not match format \"%%b%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(4)}))));
         }

         throw var9;
      }

      var1.setline(938);
      var4 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null), var1.getlocal(4));
      var1.setlocal(6, var4);
      var4 = null;
      var1.setline(941);
      var4 = var1.getlocal(2).__call__(var2, (new PyTuple(new PyObject[]{var1.getlocal(6).__getitem__(Py.newInteger(0)), var1.getlocal(5)}))._add(var1.getlocal(6).__getslice__(Py.newInteger(2), Py.newInteger(6), (PyObject)null)));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject DER_cert_to_PEM_cert$68(PyFrame var1, ThreadState var2) {
      var1.setline(950);
      PyString.fromInterned("Takes a certificate in binary DER format and returns the\n    PEM version of it as a string.");
      var1.setline(952);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("base64"), (PyObject)PyString.fromInterned("standard_b64encode")).__nonzero__()) {
         var1.setline(954);
         var3 = var1.getglobal("base64").__getattr__("standard_b64encode").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(955);
         var3 = var1.getglobal("PEM_HEADER")._add(PyString.fromInterned("\n"))._add(var1.getglobal("textwrap").__getattr__("fill").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(64)))._add(PyString.fromInterned("\n"))._add(var1.getglobal("PEM_FOOTER"))._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(959);
         var3 = var1.getglobal("PEM_HEADER")._add(PyString.fromInterned("\n"))._add(var1.getglobal("base64").__getattr__("encodestring").__call__(var2, var1.getlocal(0)))._add(var1.getglobal("PEM_FOOTER"))._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject PEM_cert_to_DER_cert$69(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      PyString.fromInterned("Takes a certificate in ASCII PEM format and returns the\n    DER-encoded version of it as a byte sequence");
      var1.setline(968);
      if (var1.getlocal(0).__getattr__("startswith").__call__(var2, var1.getglobal("PEM_HEADER")).__not__().__nonzero__()) {
         var1.setline(969);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid PEM encoding; must start with %s")._mod(var1.getglobal("PEM_HEADER"))));
      } else {
         var1.setline(971);
         if (var1.getlocal(0).__getattr__("strip").__call__(var2).__getattr__("endswith").__call__(var2, var1.getglobal("PEM_FOOTER")).__not__().__nonzero__()) {
            var1.setline(972);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid PEM encoding; must end with %s")._mod(var1.getglobal("PEM_FOOTER"))));
         } else {
            var1.setline(974);
            PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2).__getslice__(var1.getglobal("len").__call__(var2, var1.getglobal("PEM_HEADER")), var1.getglobal("len").__call__(var2, var1.getglobal("PEM_FOOTER")).__neg__(), (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(975);
            var3 = var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_server_certificate$70(PyFrame var1, ThreadState var2) {
      var1.setline(982);
      PyString.fromInterned("Retrieve the certificate from the server at the specified address,\n    and return it as a PEM-encoded string.\n    If 'ca_certs' is specified, validate the server cert against it.\n    If 'ssl_version' is specified, use it in the connection attempt.");
      var1.setline(984);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(985);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(986);
         var3 = var1.getglobal("CERT_REQUIRED");
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(988);
         var3 = var1.getglobal("CERT_NONE");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(990);
      var10000 = var1.getglobal("wrap_socket");
      PyObject[] var7 = new PyObject[]{var1.getglobal("socket").__call__(var2), var1.getlocal(1), var1.getlocal(5), var1.getlocal(2)};
      String[] var6 = new String[]{"ssl_version", "cert_reqs", "ca_certs"};
      var10000 = var10000.__call__(var2, var7, var6);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(992);
      var1.getlocal(6).__getattr__("connect").__call__(var2, var1.getlocal(0));
      var1.setline(993);
      var3 = var1.getlocal(6).__getattr__("getpeercert").__call__(var2, var1.getglobal("True"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(994);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.setline(995);
      var3 = var1.getglobal("DER_cert_to_PEM_cert").__call__(var2, var1.getlocal(7));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_protocol_name$71(PyFrame var1, ThreadState var2) {
      var1.setline(999);
      PyObject var3 = var1.getglobal("_PROTOCOL_NAMES").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("<unknown>"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sslwrap_simple$72(PyFrame var1, ThreadState var2) {
      var1.setline(1007);
      PyString.fromInterned("A replacement for the old socket.ssl function.  Designed\n    for compability with Python 2.5 and earlier.  Will disappear in\n    Python 3.0.");
      var1.setline(1009);
      PyObject var10000 = var1.getglobal("wrap_socket");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("PROTOCOL_SSLv23")};
      String[] var4 = new String[]{"keyfile", "certfile", "ssl_version"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(3, var6);
      var3 = null;

      label19: {
         try {
            var1.setline(1011);
            var1.getlocal(0).__getattr__("getpeername").__call__(var2);
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("socket_error"))) {
               var1.setline(1014);
               break label19;
            }

            throw var7;
         }

         var1.setline(1017);
         var1.getlocal(3).__getattr__("do_handshake").__call__(var2);
      }

      var1.setline(1019);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject RAND_status$73(PyFrame var1, ThreadState var2) {
      var1.setline(1025);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject RAND_egd$74(PyFrame var1, ThreadState var2) {
      var1.setline(1029);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      PyObject var10000 = var3._ne(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1030);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Must be an absolute path, but ignoring it regardless")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject RAND_add$75(PyFrame var1, ThreadState var2) {
      var1.setline(1034);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SSLContext$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1039);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("RSA"), PyString.fromInterned("DSA"), PyString.fromInterned("DH_RSA"), PyString.fromInterned("DH_DSA"), PyString.fromInterned("EC"), PyString.fromInterned("EC_EC"), PyString.fromInterned("EC_RSA")});
      var1.setlocal("_jsse_keyType_names", var3);
      var3 = null;
      var1.setline(1041);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$77, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1069);
      var4 = new PyObject[]{var1.getname("False"), var1.getname("True"), var1.getname("True"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, wrap_socket$78, (PyObject)null);
      var1.setlocal("wrap_socket", var5);
      var3 = null;
      var1.setline(1079);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, _createSSLEngine$79, (PyObject)null);
      var1.setlocal("_createSSLEngine", var5);
      var3 = null;
      var1.setline(1123);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, cert_store_stats$80, (PyObject)null);
      var1.setlocal("cert_store_stats", var5);
      var3 = null;
      var1.setline(1126);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, load_cert_chain$81, (PyObject)null);
      var1.setlocal("load_cert_chain", var5);
      var3 = null;
      var1.setline(1132);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_ciphers$82, (PyObject)null);
      var1.setlocal("set_ciphers", var5);
      var3 = null;
      var1.setline(1138);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, load_verify_locations$83, (PyObject)null);
      var1.setlocal("load_verify_locations", var5);
      var3 = null;
      var1.setline(1178);
      var4 = new PyObject[]{var1.getname("Purpose").__getattr__("SERVER_AUTH")};
      var5 = new PyFunction(var1.f_globals, var4, load_default_certs$84, (PyObject)null);
      var1.setlocal("load_default_certs", var5);
      var3 = null;
      var1.setline(1185);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_default_verify_paths$85, PyString.fromInterned("\n        Load a set of default \"certification authority\" (CA) certificates from a filesystem path defined when building\n        the OpenSSL library. Unfortunately, there's no easy way to know whether this method succeeds: no error is\n        returned if no certificates are to be found. When the OpenSSL library is provided as part of the operating\n        system, though, it is likely to be configured properly.\n        "));
      var1.setlocal("set_default_verify_paths", var5);
      var3 = null;
      var1.setline(1196);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_alpn_protocols$86, (PyObject)null);
      var1.setlocal("set_alpn_protocols", var5);
      var3 = null;
      var1.setline(1199);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_npn_protocols$87, (PyObject)null);
      var1.setlocal("set_npn_protocols", var5);
      var3 = null;
      var1.setline(1202);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_servername_callback$88, (PyObject)null);
      var1.setlocal("set_servername_callback", var5);
      var3 = null;
      var1.setline(1208);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, load_dh_params$89, (PyObject)null);
      var1.setlocal("load_dh_params", var5);
      var3 = null;
      var1.setline(1212);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_ecdh_curve$90, (PyObject)null);
      var1.setlocal("set_ecdh_curve", var5);
      var3 = null;
      var1.setline(1215);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, session_stats$91, (PyObject)null);
      var1.setlocal("session_stats", var5);
      var3 = null;
      var1.setline(1231);
      var4 = new PyObject[]{var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, get_ca_certs$92, PyString.fromInterned("get_ca_certs(binary_form=False) -> list of loaded certificate\n\n        Returns a list of dicts with information of loaded CA certs. If the optional argument is True,\n        returns a DER-encoded copy of the CA certificate.\n        NOTE: Certificates in a capath directory aren't loaded unless they have been used at least once.\n        "));
      var1.setlocal("get_ca_certs", var5);
      var3 = null;
      var1.setline(1259);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, check_hostname$93, (PyObject)null);
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("check_hostname", var6);
      var3 = null;
      var1.setline(1263);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, check_hostname$94, (PyObject)null);
      var6 = var1.getname("check_hostname").__getattr__("setter").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("check_hostname", var6);
      var3 = null;
      var1.setline(1270);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, verify_mode$95, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("verify_mode", var6);
      var3 = null;
      var1.setline(1274);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, verify_mode$96, (PyObject)null);
      var6 = var1.getname("verify_mode").__getattr__("setter").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("verify_mode", var6);
      var3 = null;
      var1.setline(1287);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, verify_flags$97, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("verify_flags", var6);
      var3 = null;
      var1.setline(1291);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, verify_flags$98, (PyObject)null);
      var6 = var1.getname("verify_flags").__getattr__("setter").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("verify_flags", var6);
      var3 = null;
      var1.setline(1297);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _parse_dn$99, (PyObject)null);
      var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("_parse_dn", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$77(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var5;
      try {
         var1.setline(1043);
         var5 = var1.getglobal("_PROTOCOL_NAMES").__getitem__(var1.getlocal(1));
         var1.getlocal(0).__setattr__("_protocol_name", var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(1045);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid protocol version")));
         }

         throw var3;
      }

      var1.setline(1047);
      var5 = var1.getlocal(1);
      PyObject var10000 = var5._eq(var1.getglobal("PROTOCOL_SSLv23"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1048);
         PyString var6 = PyString.fromInterned("SSL");
         var1.getlocal(0).__setattr__((String)"_protocol_name", var6);
         var3 = null;
      }

      var1.setline(1050);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("protocol", var5);
      var3 = null;
      var1.setline(1051);
      var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_check_hostname", var5);
      var3 = null;
      var1.setline(1054);
      var5 = var1.getglobal("OP_ALL")._or(var1.getglobal("OP_NO_SSLv2"))._or(var1.getglobal("OP_NO_SSLv3"));
      var1.getlocal(0).__setattr__("options", var5);
      var3 = null;
      var1.setline(1055);
      var5 = var1.getglobal("VERIFY_DEFAULT");
      var1.getlocal(0).__setattr__("_verify_flags", var5);
      var3 = null;
      var1.setline(1056);
      var5 = var1.getglobal("CERT_NONE");
      var1.getlocal(0).__setattr__("_verify_mode", var5);
      var3 = null;
      var1.setline(1057);
      var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_ciphers", var5);
      var3 = null;
      var1.setline(1059);
      var5 = var1.getglobal("KeyStore").__getattr__("getInstance").__call__(var2, var1.getglobal("KeyStore").__getattr__("getDefaultType").__call__(var2));
      var1.getlocal(0).__setattr__("_trust_store", var5);
      var3 = null;
      var1.setline(1060);
      var1.getlocal(0).__getattr__("_trust_store").__getattr__("load").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.setline(1062);
      var5 = var1.getglobal("KeyStore").__getattr__("getInstance").__call__(var2, var1.getglobal("KeyStore").__getattr__("getDefaultType").__call__(var2));
      var1.getlocal(0).__setattr__("_key_store", var5);
      var3 = null;
      var1.setline(1063);
      var1.getlocal(0).__getattr__("_key_store").__getattr__("load").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.setline(1065);
      var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_key_managers", var5);
      var3 = null;
      var1.setline(1067);
      var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_server_name_callback", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject wrap_socket$78(PyFrame var1, ThreadState var2) {
      var1.setline(1073);
      PyObject var10000 = var1.getglobal("SSLSocket");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(0)};
      String[] var4 = new String[]{"sock", "server_side", "do_handshake_on_connect", "suppress_ragged_eofs", "server_hostname", "_context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _createSSLEngine$79(PyFrame var1, ThreadState var2) {
      var1.setline(1080);
      PyObject var3 = var1.getglobal("InsecureTrustManagerFactory").__getattr__("INSTANCE");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1081);
      var3 = var1.getlocal(0).__getattr__("verify_mode");
      PyObject var10000 = var3._ne(var1.getglobal("CERT_NONE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1083);
         var3 = var1.getglobal("SimpleTrustManagerFactory").__getattr__("getInstance").__call__(var2, var1.getglobal("SimpleTrustManagerFactory").__getattr__("getDefaultAlgorithm").__call__(var2));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1084);
         var1.getlocal(7).__getattr__("init").__call__(var2, var1.getlocal(0).__getattr__("_trust_store"));
         var1.setline(1086);
         var3 = var1.getglobal("CompositeX509TrustManagerFactory").__call__(var2, var1.getlocal(7).__getattr__("getTrustManagers").__call__(var2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1087);
         var1.getlocal(6).__getattr__("init").__call__(var2, var1.getlocal(0).__getattr__("_trust_store"));
      }

      var1.setline(1089);
      var3 = var1.getlocal(0).__getattr__("_key_managers");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1090);
      var3 = var1.getlocal(0).__getattr__("_key_managers");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1091);
         var10000 = var1.getglobal("_get_openssl_key_manager");
         PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(4)};
         String[] var4 = new String[]{"cert_file", "key_file"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1093);
      var3 = var1.getglobal("None");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1095);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(1096);
         var3 = var1.getglobal("SslContextBuilder").__getattr__("forClient").__call__(var2);
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(1098);
      if (var1.getlocal(8).__nonzero__()) {
         var1.setline(1099);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(1100);
            var3 = var1.getglobal("SslContextBuilder").__getattr__("forServer").__call__(var2, var1.getlocal(8));
            var1.setlocal(9, var3);
            var3 = null;
         } else {
            var1.setline(1102);
            var3 = var1.getlocal(9).__getattr__("keyManager").__call__(var2, var1.getlocal(8));
            var1.setlocal(9, var3);
            var3 = null;
         }
      }

      var1.setline(1104);
      var3 = var1.getlocal(9).__getattr__("trustManager").__call__(var2, var1.getlocal(6));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1105);
      var3 = var1.getlocal(9).__getattr__("sslProvider").__call__(var2, var1.getglobal("SslProvider").__getattr__("JDK"));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1106);
      var3 = var1.getlocal(9).__getattr__("clientAuth").__call__(var2, var1.getglobal("_CERT_TO_CLIENT_AUTH").__getitem__(var1.getlocal(0).__getattr__("verify_mode")));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1108);
      var3 = var1.getlocal(0).__getattr__("_ciphers");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1109);
         var3 = var1.getlocal(9).__getattr__("ciphers").__call__(var2, var1.getlocal(0).__getattr__("_ciphers"));
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(1111);
      if (var1.getlocal(0).__getattr__("_check_hostname").__nonzero__()) {
         var1.setline(1112);
         var3 = var1.getlocal(9).__getattr__("build").__call__(var2).__getattr__("newEngine").__call__(var2, var1.getglobal("ByteBufAllocator").__getattr__("DEFAULT"), var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(1113);
         if (var1.getglobal("HAS_SNI").__nonzero__()) {
            var1.setline(1114);
            var3 = var1.getlocal(10).__getattr__("getSSLParameters").__call__(var2);
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(1115);
            var1.getlocal(11).__getattr__("setEndpointIdentificationAlgorithm").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTPS"));
            var1.setline(1116);
            var1.getlocal(11).__getattr__("setServerNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("SNIHostName").__call__(var2, var1.getlocal(2))})));
            var1.setline(1117);
            var1.getlocal(10).__getattr__("setSSLParameters").__call__(var2, var1.getlocal(11));
         }
      } else {
         var1.setline(1119);
         var3 = var1.getlocal(9).__getattr__("build").__call__(var2).__getattr__("newEngine").__call__(var2, var1.getglobal("ByteBufAllocator").__getattr__("DEFAULT"), var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setlocal(10, var3);
         var3 = null;
      }

      var1.setline(1121);
      var3 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cert_store_stats$80(PyFrame var1, ThreadState var2) {
      var1.setline(1124);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("crl"), Py.newInteger(0), PyString.fromInterned("x509"), var1.getlocal(0).__getattr__("_key_store").__getattr__("size").__call__(var2), PyString.fromInterned("x509_ca"), var1.getlocal(0).__getattr__("_trust_store").__getattr__("size").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_cert_chain$81(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(1128);
         PyObject var10000 = var1.getglobal("_get_openssl_key_manager");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("_key_store")};
         String[] var8 = new String[]{"_key_store"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         PyObject var7 = var10000;
         var1.getlocal(0).__setattr__("_key_managers", var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IllegalArgumentException"))) {
            PyObject var4 = var3.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1130);
            throw Py.makeException(var1.getglobal("SSLError").__call__(var2, var1.getglobal("SSL_ERROR_SSL"), PyString.fromInterned("PEM lib ({})").__getattr__("format").__call__(var2, var1.getlocal(4))));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_ciphers$82(PyFrame var1, ThreadState var2) {
      var1.setline(1136);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_verify_locations$83(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1139);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1140);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cafile, capath and cadata cannot be all omitted")));
      } else {
         var1.setline(1142);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var11);
         var3 = null;
         var1.setline(1143);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1144);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(1));
         }

         var1.setline(1146);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         PyObject var4;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(1147);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2)).__iter__();

            label81:
            while(true) {
               while(true) {
                  var1.setline(1147);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label81;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(1148);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(5));
                  PyObject[] var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(1149);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(5));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(1150);
                  var5 = var1.getlocal(7).__getattr__("lower").__call__(var2);
                  var10000 = var5._eq(PyString.fromInterned("pem"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1151);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
                  } else {
                     var1.setline(1152);
                     var5 = var1.getlocal(5);
                     var10000 = var5._eq(PyString.fromInterned("cacerts"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1153);
                        if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                           var1.setline(1154);
                           var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
                        }
                     } else {
                        var1.setline(1155);
                        if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                           try {
                              ContextManager var14;
                              PyObject var12 = (var14 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(8)))).__enter__(var2);

                              try {
                                 var1.setlocal(9, var12);
                                 var1.setline(1158);
                                 var12 = var1.getglobal("PEM_HEADER");
                                 var10000 = var12._in(var1.getlocal(9).__getattr__("read").__call__(var2));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1159);
                                    var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
                                 }
                              } catch (Throwable var8) {
                                 if (var14.__exit__(var2, Py.setException(var8, var1))) {
                                    continue;
                                 }

                                 throw (Throwable)Py.makeException();
                              }

                              var14.__exit__(var2, (PyException)null);
                           } catch (Throwable var9) {
                              PyException var13 = Py.setException(var9, var1);
                              if (!var13.match(var1.getglobal("IOError"))) {
                                 throw var13;
                              }

                              var1.setline(1161);
                              var1.getglobal("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Not including %s file as a possible cafile due to permissions error")._mod(var1.getlocal(8)));
                              var1.setline(1162);
                           }
                        }
                     }
                  }
               }
            }
         }

         var1.setline(1164);
         var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(10, var11);
         var3 = null;
         var1.setline(1165);
         var3 = var1.getglobal("None");
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(1166);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         PyObject[] var10;
         if (var10000.__nonzero__()) {
            var1.setline(1167);
            var3 = var1.getglobal("_extract_cert_from_data").__call__(var2, var1.getlocal(3));
            var10 = Py.unpackSequence(var3, 2);
            var5 = var10[0];
            var1.setlocal(10, var5);
            var5 = null;
            var5 = var10[1];
            var1.setlocal(11, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(1169);
         var3 = var1.getglobal("_extract_certs_for_paths").__call__(var2, var1.getlocal(4));
         var10 = Py.unpackSequence(var3, 2);
         var5 = var10[0];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(1170);
         var1.getlocal(10).__getattr__("extend").__call__(var2, var1.getlocal(12));
         var1.setline(1171);
         var3 = var1.getlocal(10).__iter__();

         while(true) {
            var1.setline(1171);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(13, var4);
            var1.setline(1173);
            var5 = var1.getlocal(11);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1174);
               var1.getlocal(0).__getattr__("_trust_store").__getattr__("setCertificateEntry").__call__(var2, var1.getglobal("_str_hash_key_entry").__call__(var2, var1.getlocal(13)), var1.getlocal(13));
            } else {
               var1.setline(1176);
               var1.getlocal(0).__getattr__("_key_store").__getattr__("setCertificateEntry").__call__(var2, var1.getglobal("_str_hash_key_entry").__call__(var2, var1.getlocal(13)), var1.getlocal(13));
            }
         }
      }
   }

   public PyObject load_default_certs$84(PyFrame var1, ThreadState var2) {
      var1.setline(1180);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_ASN1Object")).__not__().__nonzero__()) {
         var1.setline(1181);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(1183);
         var1.getlocal(0).__getattr__("set_default_verify_paths").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject set_default_verify_paths$85(PyFrame var1, ThreadState var2) {
      var1.setline(1191);
      PyString.fromInterned("\n        Load a set of default \"certification authority\" (CA) certificates from a filesystem path defined when building\n        the OpenSSL library. Unfortunately, there's no easy way to know whether this method succeeds: no error is\n        returned if no certificates are to be found. When the OpenSSL library is provided as part of the operating\n        system, though, it is likely to be configured properly.\n        ");
      var1.setline(1192);
      PyObject var3 = var1.getglobal("get_default_verify_paths").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1194);
      PyObject var10000 = var1.getlocal(0).__getattr__("load_verify_locations");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1).__getattr__("cafile"), var1.getlocal(1).__getattr__("capath")};
      String[] var4 = new String[]{"cafile", "capath"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_alpn_protocols$86(PyFrame var1, ThreadState var2) {
      var1.setline(1197);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2));
   }

   public PyObject set_npn_protocols$87(PyFrame var1, ThreadState var2) {
      var1.setline(1200);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2));
   }

   public PyObject set_servername_callback$88(PyFrame var1, ThreadState var2) {
      var1.setline(1203);
      PyObject var10000 = var1.getglobal("callable").__call__(var2, var1.getlocal(1)).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1204);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is not callable").__getattr__("format").__call__(var2, var1.getlocal(1))));
      } else {
         var1.setline(1205);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_server_name_callback", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject load_dh_params$89(PyFrame var1, ThreadState var2) {
      var1.setline(1210);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_ecdh_curve$90(PyFrame var1, ThreadState var2) {
      var1.setline(1213);
      PyObject var3 = var1.getglobal("_get_ecdh_parameter_spec").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject session_stats$91(PyFrame var1, ThreadState var2) {
      var1.setline(1217);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("number"), Py.newInteger(0), PyString.fromInterned("connect"), Py.newInteger(0), PyString.fromInterned("connect_good"), Py.newInteger(0), PyString.fromInterned("connect_renegotiate"), Py.newInteger(0), PyString.fromInterned("accept"), Py.newInteger(0), PyString.fromInterned("accept_good"), Py.newInteger(0), PyString.fromInterned("accept_renegotiate"), Py.newInteger(0), PyString.fromInterned("hits"), Py.newInteger(0), PyString.fromInterned("misses"), Py.newInteger(0), PyString.fromInterned("timeouts"), Py.newInteger(0), PyString.fromInterned("cache_full"), Py.newInteger(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_ca_certs$92(PyFrame var1, ThreadState var2) {
      var1.setline(1237);
      PyString.fromInterned("get_ca_certs(binary_form=False) -> list of loaded certificate\n\n        Returns a list of dicts with information of loaded CA certs. If the optional argument is True,\n        returns a DER-encoded copy of the CA certificate.\n        NOTE: Certificates in a capath directory aren't loaded unless they have been used at least once.\n        ");
      var1.setline(1238);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1239);
      PyObject var8 = var1.getlocal(0).__getattr__("_trust_store").__getattr__("aliases").__call__(var2).__iter__();

      while(true) {
         label39:
         while(true) {
            do {
               var1.setline(1239);
               PyObject var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.setline(1257);
                  var8 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setlocal(3, var4);
               var1.setline(1240);
            } while(!var1.getlocal(0).__getattr__("_trust_store").__getattr__("isCertificateEntry").__call__(var2, var1.getlocal(3)).__nonzero__());

            var1.setline(1241);
            PyObject var5 = var1.getlocal(0).__getattr__("_trust_store").__getattr__("getCertificate").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(1242);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(1243);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4).__getattr__("getEncoded").__call__(var2).__getattr__("tostring").__call__(var2));
            } else {
               var1.setline(1245);
               var5 = var1.getlocal(0).__getattr__("_parse_dn").__call__(var2, var1.getlocal(4).__getattr__("issuerDN"));
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(1246);
               var5 = var1.getlocal(0).__getattr__("_parse_dn").__call__(var2, var1.getlocal(4).__getattr__("subjectDN"));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(1248);
               PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("issuer"), var1.getlocal(5), PyString.fromInterned("subject"), var1.getlocal(6)});
               var1.setlocal(7, var9);
               var5 = null;
               var1.setline(1249);
               var5 = (new PyTuple(new PyObject[]{PyString.fromInterned("serialNumber"), PyString.fromInterned("version")})).__iter__();

               while(true) {
                  var1.setline(1249);
                  PyObject var6 = var5.__iternext__();
                  PyObject var7;
                  if (var6 == null) {
                     var1.setline(1252);
                     var5 = (new PyTuple(new PyObject[]{PyString.fromInterned("notBefore"), PyString.fromInterned("notAfter")})).__iter__();

                     while(true) {
                        var1.setline(1252);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           var1.setline(1255);
                           var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(7));
                           continue label39;
                        }

                        var1.setlocal(8, var6);
                        var1.setline(1253);
                        var7 = var1.getglobal("str").__call__(var2, var1.getglobal("_rfc2822_date_format").__getattr__("format").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(8))));
                        var1.getlocal(7).__setitem__(var1.getlocal(8), var7);
                        var7 = null;
                     }
                  }

                  var1.setlocal(8, var6);
                  var1.setline(1250);
                  var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(8));
                  var1.getlocal(7).__setitem__(var1.getlocal(8), var7);
                  var7 = null;
               }
            }
         }
      }
   }

   public PyObject check_hostname$93(PyFrame var1, ThreadState var2) {
      var1.setline(1261);
      PyObject var3 = var1.getlocal(0).__getattr__("_check_hostname");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject check_hostname$94(PyFrame var1, ThreadState var2) {
      var1.setline(1265);
      PyObject var10000 = var1.getlocal(1);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("verify_mode");
         var10000 = var3._eq(var1.getglobal("CERT_NONE"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1266);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("check_hostname needs a SSL context with either CERT_OPTIONAL or CERT_REQUIRED")));
      } else {
         var1.setline(1268);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_check_hostname", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject verify_mode$95(PyFrame var1, ThreadState var2) {
      var1.setline(1272);
      PyObject var3 = var1.getlocal(0).__getattr__("_verify_mode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject verify_mode$96(PyFrame var1, ThreadState var2) {
      var1.setline(1276);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__not__().__nonzero__()) {
         var1.setline(1277);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("verfy_mode must be one of the ssl.CERT_* modes")));
      } else {
         var1.setline(1279);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("CERT_NONE"), var1.getglobal("CERT_OPTIONAL"), var1.getglobal("CERT_REQUIRED")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1280);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("verfy_mode must be one of the ssl.CERT_* modes")));
         } else {
            var1.setline(1282);
            var10000 = var1.getlocal(0).__getattr__("check_hostname");
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1);
               var10000 = var3._eq(var1.getglobal("CERT_NONE"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1283);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot set verify_mode to CERT_NONE when check_hostname is enabled.")));
            } else {
               var1.setline(1285);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("_verify_mode", var3);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject verify_flags$97(PyFrame var1, ThreadState var2) {
      var1.setline(1289);
      PyObject var3 = var1.getlocal(0).__getattr__("_verify_flags");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject verify_flags$98(PyFrame var1, ThreadState var2) {
      var1.setline(1293);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__not__().__nonzero__()) {
         var1.setline(1294);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("verfy_flags must be one of the ssl.VERIFY_* flags")));
      } else {
         var1.setline(1295);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_verify_flags", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _parse_dn$99(PyFrame var1, ThreadState var2) {
      var1.setline(1299);
      PyObject var3 = var1.getglobal("LdapName").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1302);
      PyObject var10000 = var1.getglobal("tuple");
      var1.setline(1302);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var5, f$100, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(2).__getattr__("getRdns").__call__(var2).__iter__());
      Arrays.fill(var5, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$100(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1302);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var8 = (PyObject)var10000;
      }

      var1.setline(1302);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(1302);
         var1.setline(1302);
         PyObject[] var7 = new PyObject[1];
         PyObject[] var6 = new PyObject[]{var1.getglobal("_ldap_rdn_display_names").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("type")), var1.getglobal("_str_or_unicode").__call__(var2, var1.getlocal(1).__getattr__("value"))};
         PyTuple var9 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var7[0] = var9;
         var9 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public ssl$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      _str_or_unicode$1 = Py.newCode(1, var2, var1, "_str_or_unicode", 170, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CertificateError$2 = Py.newCode(0, var2, var1, "CertificateError", 178, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SSLZeroReturnError$3 = Py.newCode(0, var2, var1, "SSLZeroReturnError", 183, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SSLWantReadError$4 = Py.newCode(0, var2, var1, "SSLWantReadError", 186, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SSLWantWriteError$5 = Py.newCode(0, var2, var1, "SSLWantWriteError", 189, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SSLSyscallError$6 = Py.newCode(0, var2, var1, "SSLSyscallError", 192, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SSLEOFError$7 = Py.newCode(0, var2, var1, "SSLEOFError", 195, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"dn", "hostname", "max_wildcards", "pats", "pieces", "leftmost", "remainder", "wildcards", "frag", "pat"};
      _dnsname_match$8 = Py.newCode(3, var2, var1, "_dnsname_match", 199, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cert", "hostname", "dnsnames", "san", "key", "value", "sub"};
      match_hostname$9 = Py.newCode(2, var2, var1, "match_hostname", 250, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cafile", "capath", "default_cert_dir_env", "default_cert_file_env", "java_cert_file", "java_home", "_path"};
      get_default_verify_paths$10 = Py.newCode(0, var2, var1, "get_default_verify_paths", 298, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ASN1Object$11 = Py.newCode(0, var2, var1, "_ASN1Object", 330, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "oid"};
      __new__$12 = Py.newCode(2, var2, var1, "__new__", 335, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Purpose$13 = Py.newCode(0, var2, var1, "Purpose", 344, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"purpose", "cafile", "capath", "cadata", "context"};
      create_default_context$14 = Py.newCode(4, var2, var1, "create_default_context", 352, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"protocol", "cert_reqs", "check_hostname", "purpose", "certfile", "keyfile", "cafile", "capath", "cadata", "context"};
      _create_unverified_context$15 = Py.newCode(9, var2, var1, "_create_unverified_context", 404, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SSLInitializer$16 = Py.newCode(0, var2, var1, "SSLInitializer", 454, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ssl_handler"};
      __init__$17 = Py.newCode(2, var2, var1, "__init__", 455, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ch", "pipeline"};
      initChannel$18 = Py.newCode(2, var2, var1, "initChannel", 458, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SSLSocket$19 = Py.newCode(0, var2, var1, "SSLSocket", 463, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock", "keyfile", "certfile", "server_side", "cert_reqs", "ssl_version", "ca_certs", "do_handshake_on_connect", "suppress_ragged_eofs", "npn_protocols", "ciphers", "server_hostname", "_context", "setup_handshake", "wrap_child", "setup_handshake_latch"};
      String[] var10001 = var2;
      ssl$py var10007 = self;
      var2 = new String[]{"self", "setup_handshake_latch"};
      __init__$20 = Py.newCode(14, var10001, var1, "__init__", 465, false, false, var10007, 20, var2, (String[])null, 1, 4097);
      var2 = new String[]{"handshake_future"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "setup_handshake_latch"};
      setup_handshake$21 = Py.newCode(0, var10001, var1, "setup_handshake", 564, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[]{"child"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      wrap_child$22 = Py.newCode(1, var10001, var1, "wrap_child", 578, false, false, var10007, 22, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      context$23 = Py.newCode(1, var2, var1, "context", 592, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      context$24 = Py.newCode(2, var2, var1, "context", 596, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr"};
      setup_engine$25 = Py.newCode(2, var2, var1, "setup_engine", 600, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr"};
      connect$26 = Py.newCode(2, var2, var1, "connect", 609, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr", "rc"};
      connect_ex$27 = Py.newCode(2, var2, var1, "connect_ex", 623, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child", "addr", "wrapped_child_socket"};
      accept$28 = Py.newCode(1, var2, var1, "accept", 640, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unwrap$29 = Py.newCode(1, var2, var1, "unwrap", 656, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handshake_step"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      do_handshake$30 = Py.newCode(1, var10001, var1, "do_handshake", 664, false, false, var10007, 30, var2, (String[])null, 0, 4097);
      var2 = new String[]{"result"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      handshake_step$31 = Py.newCode(1, var10001, var1, "handshake_step", 668, false, false, var10007, 31, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      dup$32 = Py.newCode(1, var2, var1, "dup", 693, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _ensure_handshake$33 = Py.newCode(1, var2, var1, "_ensure_handshake", 697, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send$34 = Py.newCode(2, var2, var1, "send", 711, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      sendall$35 = Py.newCode(2, var2, var1, "sendall", 717, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "flags"};
      recv$36 = Py.newCode(3, var2, var1, "recv", 721, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len", "buffer", "v", "x"};
      read$37 = Py.newCode(3, var2, var1, "read", 725, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "flags"};
      recvfrom$38 = Py.newCode(3, var2, var1, "recvfrom", 748, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer", "nbytes", "flags"};
      recvfrom_into$39 = Py.newCode(4, var2, var1, "recvfrom_into", 752, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer", "nbytes", "flags"};
      recv_into$40 = Py.newCode(4, var2, var1, "recv_into", 756, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "arg1", "arg2"};
      sendto$41 = Py.newCode(4, var2, var1, "sendto", 760, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$42 = Py.newCode(1, var2, var1, "close", 766, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mode"};
      setblocking$43 = Py.newCode(2, var2, var1, "setblocking", 769, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout"};
      settimeout$44 = Py.newCode(2, var2, var1, "settimeout", 772, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      gettimeout$45 = Py.newCode(1, var2, var1, "gettimeout", 775, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mode", "bufsize"};
      makefile$46 = Py.newCode(3, var2, var1, "makefile", 778, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "how"};
      shutdown$47 = Py.newCode(2, var2, var1, "shutdown", 781, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pending$48 = Py.newCode(1, var2, var1, "pending", 785, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _readable$49 = Py.newCode(1, var2, var1, "_readable", 790, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _writable$50 = Py.newCode(1, var2, var1, "_writable", 793, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "selector"};
      _register_selector$51 = Py.newCode(2, var2, var1, "_register_selector", 796, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "selector"};
      _unregister_selector$52 = Py.newCode(2, var2, var1, "_unregister_selector", 799, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _notify_selectors$53 = Py.newCode(1, var2, var1, "_notify_selectors", 802, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      _checkClosed$54 = Py.newCode(2, var2, var1, "_checkClosed", 805, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_connected$55 = Py.newCode(1, var2, var1, "_check_connected", 809, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getpeername$56 = Py.newCode(1, var2, var1, "getpeername", 817, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      selected_npn_protocol$57 = Py.newCode(1, var2, var1, "selected_npn_protocol", 820, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      selected_alpn_protocol$58 = Py.newCode(1, var2, var1, "selected_alpn_protocol", 825, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$59 = Py.newCode(1, var2, var1, "fileno", 829, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "binary_form", "cert", "dn", "rdns", "alt_names", "_(845_31)", "pycert"};
      getpeercert$60 = Py.newCode(2, var2, var1, "getpeercert", 832, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "type", "name"};
      f$61 = Py.newCode(1, var2, var1, "<genexpr>", 845, false, false, self, 61, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      issuer$62 = Py.newCode(1, var2, var1, "issuer", 854, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "session", "suite", "strength"};
      cipher$63 = Py.newCode(1, var2, var1, "cipher", 858, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cb_type"};
      get_channel_binding$64 = Py.newCode(2, var2, var1, "get_channel_binding", 869, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      version$65 = Py.newCode(1, var2, var1, "version", 887, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sock", "keyfile", "certfile", "server_side", "cert_reqs", "ssl_version", "ca_certs", "do_handshake_on_connect", "suppress_ragged_eofs", "ciphers"};
      wrap_socket$66 = Py.newCode(10, var2, var1, "wrap_socket", 899, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cert_time", "strptime", "timegm", "months", "time_format", "month_number", "tt"};
      cert_time_to_seconds$67 = Py.newCode(1, var2, var1, "cert_time_to_seconds", 913, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"der_cert_bytes", "f"};
      DER_cert_to_PEM_cert$68 = Py.newCode(1, var2, var1, "DER_cert_to_PEM_cert", 948, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pem_cert_string", "d"};
      PEM_cert_to_DER_cert$69 = Py.newCode(1, var2, var1, "PEM_cert_to_DER_cert", 964, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr", "ssl_version", "ca_certs", "host", "port", "cert_reqs", "s", "dercert"};
      get_server_certificate$70 = Py.newCode(3, var2, var1, "get_server_certificate", 978, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"protocol_code"};
      get_protocol_name$71 = Py.newCode(1, var2, var1, "get_protocol_name", 998, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sock", "keyfile", "certfile", "ssl_sock"};
      sslwrap_simple$72 = Py.newCode(3, var2, var1, "sslwrap_simple", 1004, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RAND_status$73 = Py.newCode(0, var2, var1, "RAND_status", 1024, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      RAND_egd$74 = Py.newCode(1, var2, var1, "RAND_egd", 1028, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"bytes", "entropy"};
      RAND_add$75 = Py.newCode(2, var2, var1, "RAND_add", 1033, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SSLContext$76 = Py.newCode(0, var2, var1, "SSLContext", 1037, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "protocol"};
      __init__$77 = Py.newCode(2, var2, var1, "__init__", 1041, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sock", "server_side", "do_handshake_on_connect", "suppress_ragged_eofs", "server_hostname"};
      wrap_socket$78 = Py.newCode(6, var2, var1, "wrap_socket", 1069, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr", "hostname", "cert_file", "key_file", "server_side", "tmf", "stmf", "kmf", "context_builder", "engine", "params"};
      _createSSLEngine$79 = Py.newCode(6, var2, var1, "_createSSLEngine", 1079, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      cert_store_stats$80 = Py.newCode(1, var2, var1, "cert_store_stats", 1123, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "certfile", "keyfile", "password", "err"};
      load_cert_chain$81 = Py.newCode(4, var2, var1, "load_cert_chain", 1126, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ciphers"};
      set_ciphers$82 = Py.newCode(2, var2, var1, "set_ciphers", 1132, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cafile", "capath", "cadata", "cafiles", "fname", "_", "ext", "possible_cafile", "f", "certs", "private_key", "_certs", "cert"};
      load_verify_locations$83 = Py.newCode(4, var2, var1, "load_verify_locations", 1138, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "purpose"};
      load_default_certs$84 = Py.newCode(2, var2, var1, "load_default_certs", 1178, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "default_verify_paths"};
      set_default_verify_paths$85 = Py.newCode(1, var2, var1, "set_default_verify_paths", 1185, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "protocols"};
      set_alpn_protocols$86 = Py.newCode(2, var2, var1, "set_alpn_protocols", 1196, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "protocols"};
      set_npn_protocols$87 = Py.newCode(2, var2, var1, "set_npn_protocols", 1199, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server_name_callback"};
      set_servername_callback$88 = Py.newCode(2, var2, var1, "set_servername_callback", 1202, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dhfile"};
      load_dh_params$89 = Py.newCode(2, var2, var1, "load_dh_params", 1208, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "curve_name", "params"};
      set_ecdh_curve$90 = Py.newCode(2, var2, var1, "set_ecdh_curve", 1212, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      session_stats$91 = Py.newCode(1, var2, var1, "session_stats", 1215, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "binary_form", "certs", "alias", "cert", "issuer_info", "subject_info", "cert_info", "k"};
      get_ca_certs$92 = Py.newCode(2, var2, var1, "get_ca_certs", 1231, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      check_hostname$93 = Py.newCode(1, var2, var1, "check_hostname", 1259, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      check_hostname$94 = Py.newCode(2, var2, var1, "check_hostname", 1263, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      verify_mode$95 = Py.newCode(1, var2, var1, "verify_mode", 1270, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      verify_mode$96 = Py.newCode(2, var2, var1, "verify_mode", 1274, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      verify_flags$97 = Py.newCode(1, var2, var1, "verify_flags", 1287, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      verify_flags$98 = Py.newCode(2, var2, var1, "verify_flags", 1291, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "dn", "ln", "_(1302_22)"};
      _parse_dn$99 = Py.newCode(2, var2, var1, "_parse_dn", 1297, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "rdn"};
      f$100 = Py.newCode(1, var2, var1, "<genexpr>", 1302, false, false, self, 100, (String[])null, (String[])null, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ssl$py("ssl$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ssl$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._str_or_unicode$1(var2, var3);
         case 2:
            return this.CertificateError$2(var2, var3);
         case 3:
            return this.SSLZeroReturnError$3(var2, var3);
         case 4:
            return this.SSLWantReadError$4(var2, var3);
         case 5:
            return this.SSLWantWriteError$5(var2, var3);
         case 6:
            return this.SSLSyscallError$6(var2, var3);
         case 7:
            return this.SSLEOFError$7(var2, var3);
         case 8:
            return this._dnsname_match$8(var2, var3);
         case 9:
            return this.match_hostname$9(var2, var3);
         case 10:
            return this.get_default_verify_paths$10(var2, var3);
         case 11:
            return this._ASN1Object$11(var2, var3);
         case 12:
            return this.__new__$12(var2, var3);
         case 13:
            return this.Purpose$13(var2, var3);
         case 14:
            return this.create_default_context$14(var2, var3);
         case 15:
            return this._create_unverified_context$15(var2, var3);
         case 16:
            return this.SSLInitializer$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.initChannel$18(var2, var3);
         case 19:
            return this.SSLSocket$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.setup_handshake$21(var2, var3);
         case 22:
            return this.wrap_child$22(var2, var3);
         case 23:
            return this.context$23(var2, var3);
         case 24:
            return this.context$24(var2, var3);
         case 25:
            return this.setup_engine$25(var2, var3);
         case 26:
            return this.connect$26(var2, var3);
         case 27:
            return this.connect_ex$27(var2, var3);
         case 28:
            return this.accept$28(var2, var3);
         case 29:
            return this.unwrap$29(var2, var3);
         case 30:
            return this.do_handshake$30(var2, var3);
         case 31:
            return this.handshake_step$31(var2, var3);
         case 32:
            return this.dup$32(var2, var3);
         case 33:
            return this._ensure_handshake$33(var2, var3);
         case 34:
            return this.send$34(var2, var3);
         case 35:
            return this.sendall$35(var2, var3);
         case 36:
            return this.recv$36(var2, var3);
         case 37:
            return this.read$37(var2, var3);
         case 38:
            return this.recvfrom$38(var2, var3);
         case 39:
            return this.recvfrom_into$39(var2, var3);
         case 40:
            return this.recv_into$40(var2, var3);
         case 41:
            return this.sendto$41(var2, var3);
         case 42:
            return this.close$42(var2, var3);
         case 43:
            return this.setblocking$43(var2, var3);
         case 44:
            return this.settimeout$44(var2, var3);
         case 45:
            return this.gettimeout$45(var2, var3);
         case 46:
            return this.makefile$46(var2, var3);
         case 47:
            return this.shutdown$47(var2, var3);
         case 48:
            return this.pending$48(var2, var3);
         case 49:
            return this._readable$49(var2, var3);
         case 50:
            return this._writable$50(var2, var3);
         case 51:
            return this._register_selector$51(var2, var3);
         case 52:
            return this._unregister_selector$52(var2, var3);
         case 53:
            return this._notify_selectors$53(var2, var3);
         case 54:
            return this._checkClosed$54(var2, var3);
         case 55:
            return this._check_connected$55(var2, var3);
         case 56:
            return this.getpeername$56(var2, var3);
         case 57:
            return this.selected_npn_protocol$57(var2, var3);
         case 58:
            return this.selected_alpn_protocol$58(var2, var3);
         case 59:
            return this.fileno$59(var2, var3);
         case 60:
            return this.getpeercert$60(var2, var3);
         case 61:
            return this.f$61(var2, var3);
         case 62:
            return this.issuer$62(var2, var3);
         case 63:
            return this.cipher$63(var2, var3);
         case 64:
            return this.get_channel_binding$64(var2, var3);
         case 65:
            return this.version$65(var2, var3);
         case 66:
            return this.wrap_socket$66(var2, var3);
         case 67:
            return this.cert_time_to_seconds$67(var2, var3);
         case 68:
            return this.DER_cert_to_PEM_cert$68(var2, var3);
         case 69:
            return this.PEM_cert_to_DER_cert$69(var2, var3);
         case 70:
            return this.get_server_certificate$70(var2, var3);
         case 71:
            return this.get_protocol_name$71(var2, var3);
         case 72:
            return this.sslwrap_simple$72(var2, var3);
         case 73:
            return this.RAND_status$73(var2, var3);
         case 74:
            return this.RAND_egd$74(var2, var3);
         case 75:
            return this.RAND_add$75(var2, var3);
         case 76:
            return this.SSLContext$76(var2, var3);
         case 77:
            return this.__init__$77(var2, var3);
         case 78:
            return this.wrap_socket$78(var2, var3);
         case 79:
            return this._createSSLEngine$79(var2, var3);
         case 80:
            return this.cert_store_stats$80(var2, var3);
         case 81:
            return this.load_cert_chain$81(var2, var3);
         case 82:
            return this.set_ciphers$82(var2, var3);
         case 83:
            return this.load_verify_locations$83(var2, var3);
         case 84:
            return this.load_default_certs$84(var2, var3);
         case 85:
            return this.set_default_verify_paths$85(var2, var3);
         case 86:
            return this.set_alpn_protocols$86(var2, var3);
         case 87:
            return this.set_npn_protocols$87(var2, var3);
         case 88:
            return this.set_servername_callback$88(var2, var3);
         case 89:
            return this.load_dh_params$89(var2, var3);
         case 90:
            return this.set_ecdh_curve$90(var2, var3);
         case 91:
            return this.session_stats$91(var2, var3);
         case 92:
            return this.get_ca_certs$92(var2, var3);
         case 93:
            return this.check_hostname$93(var2, var3);
         case 94:
            return this.check_hostname$94(var2, var3);
         case 95:
            return this.verify_mode$95(var2, var3);
         case 96:
            return this.verify_mode$96(var2, var3);
         case 97:
            return this.verify_flags$97(var2, var3);
         case 98:
            return this.verify_flags$98(var2, var3);
         case 99:
            return this._parse_dn$99(var2, var3);
         case 100:
            return this.f$100(var2, var3);
         default:
            return null;
      }
   }
}
