import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("urllib2.py")
public class urllib2$py extends PyFunctionTable implements PyRunnable {
   static urllib2$py self;
   static final PyCode f$0;
   static final PyCode urlopen$1;
   static final PyCode install_opener$2;
   static final PyCode URLError$3;
   static final PyCode __init__$4;
   static final PyCode __str__$5;
   static final PyCode HTTPError$6;
   static final PyCode __init__$7;
   static final PyCode __str__$8;
   static final PyCode reason$9;
   static final PyCode info$10;
   static final PyCode request_host$11;
   static final PyCode Request$12;
   static final PyCode __init__$13;
   static final PyCode __getattr__$14;
   static final PyCode get_method$15;
   static final PyCode add_data$16;
   static final PyCode has_data$17;
   static final PyCode get_data$18;
   static final PyCode get_full_url$19;
   static final PyCode get_type$20;
   static final PyCode get_host$21;
   static final PyCode get_selector$22;
   static final PyCode set_proxy$23;
   static final PyCode has_proxy$24;
   static final PyCode get_origin_req_host$25;
   static final PyCode is_unverifiable$26;
   static final PyCode add_header$27;
   static final PyCode add_unredirected_header$28;
   static final PyCode has_header$29;
   static final PyCode get_header$30;
   static final PyCode header_items$31;
   static final PyCode OpenerDirector$32;
   static final PyCode __init__$33;
   static final PyCode add_handler$34;
   static final PyCode close$35;
   static final PyCode _call_chain$36;
   static final PyCode open$37;
   static final PyCode _open$38;
   static final PyCode error$39;
   static final PyCode build_opener$40;
   static final PyCode isclass$41;
   static final PyCode BaseHandler$42;
   static final PyCode add_parent$43;
   static final PyCode close$44;
   static final PyCode __lt__$45;
   static final PyCode HTTPErrorProcessor$46;
   static final PyCode http_response$47;
   static final PyCode HTTPDefaultErrorHandler$48;
   static final PyCode http_error_default$49;
   static final PyCode HTTPRedirectHandler$50;
   static final PyCode redirect_request$51;
   static final PyCode f$52;
   static final PyCode http_error_302$53;
   static final PyCode _parse_proxy$54;
   static final PyCode ProxyHandler$55;
   static final PyCode __init__$56;
   static final PyCode f$57;
   static final PyCode proxy_open$58;
   static final PyCode HTTPPasswordMgr$59;
   static final PyCode __init__$60;
   static final PyCode add_password$61;
   static final PyCode find_user_password$62;
   static final PyCode reduce_uri$63;
   static final PyCode is_suburi$64;
   static final PyCode HTTPPasswordMgrWithDefaultRealm$65;
   static final PyCode find_user_password$66;
   static final PyCode AbstractBasicAuthHandler$67;
   static final PyCode __init__$68;
   static final PyCode http_error_auth_reqed$69;
   static final PyCode retry_http_basic_auth$70;
   static final PyCode HTTPBasicAuthHandler$71;
   static final PyCode http_error_401$72;
   static final PyCode ProxyBasicAuthHandler$73;
   static final PyCode http_error_407$74;
   static final PyCode randombytes$75;
   static final PyCode AbstractDigestAuthHandler$76;
   static final PyCode __init__$77;
   static final PyCode reset_retry_count$78;
   static final PyCode http_error_auth_reqed$79;
   static final PyCode retry_http_digest_auth$80;
   static final PyCode get_cnonce$81;
   static final PyCode get_authorization$82;
   static final PyCode get_algorithm_impls$83;
   static final PyCode f$84;
   static final PyCode f$85;
   static final PyCode f$86;
   static final PyCode get_entity_digest$87;
   static final PyCode HTTPDigestAuthHandler$88;
   static final PyCode http_error_401$89;
   static final PyCode ProxyDigestAuthHandler$90;
   static final PyCode http_error_407$91;
   static final PyCode AbstractHTTPHandler$92;
   static final PyCode __init__$93;
   static final PyCode set_http_debuglevel$94;
   static final PyCode do_request_$95;
   static final PyCode do_open$96;
   static final PyCode f$97;
   static final PyCode f$98;
   static final PyCode HTTPHandler$99;
   static final PyCode http_open$100;
   static final PyCode HTTPSHandler$101;
   static final PyCode __init__$102;
   static final PyCode https_open$103;
   static final PyCode HTTPCookieProcessor$104;
   static final PyCode __init__$105;
   static final PyCode http_request$106;
   static final PyCode http_response$107;
   static final PyCode UnknownHandler$108;
   static final PyCode unknown_open$109;
   static final PyCode parse_keqv_list$110;
   static final PyCode parse_http_list$111;
   static final PyCode _safe_gethostbyname$112;
   static final PyCode FileHandler$113;
   static final PyCode file_open$114;
   static final PyCode get_names$115;
   static final PyCode open_local_file$116;
   static final PyCode FTPHandler$117;
   static final PyCode ftp_open$118;
   static final PyCode connect_ftp$119;
   static final PyCode CacheFTPHandler$120;
   static final PyCode __init__$121;
   static final PyCode setTimeout$122;
   static final PyCode setMaxConns$123;
   static final PyCode connect_ftp$124;
   static final PyCode check_cache$125;
   static final PyCode clear_cache$126;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("An extensible library for opening URLs using a variety of protocols\n\nThe simplest way to use this module is to call the urlopen function,\nwhich accepts a string containing a URL or a Request object (described\nbelow).  It opens the URL and returns the results as file-like\nobject; the returned object has some extra methods described below.\n\nThe OpenerDirector manages a collection of Handler objects that do\nall the actual work.  Each Handler implements a particular protocol or\noption.  The OpenerDirector is a composite object that invokes the\nHandlers needed to open the requested URL.  For example, the\nHTTPHandler performs HTTP GET and POST requests and deals with\nnon-error returns.  The HTTPRedirectHandler automatically deals with\nHTTP 301, 302, 303 and 307 redirect errors, and the HTTPDigestAuthHandler\ndeals with digest authentication.\n\nurlopen(url, data=None) -- Basic usage is the same as original\nurllib.  pass the url and optionally data to post to an HTTP URL, and\nget a file-like object back.  One difference is that you can also pass\na Request instance instead of URL.  Raises a URLError (subclass of\nIOError); for HTTP errors, raises an HTTPError, which can also be\ntreated as a valid response.\n\nbuild_opener -- Function that creates a new OpenerDirector instance.\nWill install the default handlers.  Accepts one or more Handlers as\narguments, either instances or Handler classes that it will\ninstantiate.  If one of the argument is a subclass of the default\nhandler, the argument will be installed instead of the default.\n\ninstall_opener -- Installs a new opener as the default opener.\n\nobjects of interest:\n\nOpenerDirector -- Sets up the User Agent as the Python-urllib client and manages\nthe Handler classes, while dealing with requests and responses.\n\nRequest -- An object that encapsulates the state of a request.  The\nstate can be as simple as the URL.  It can also include extra HTTP\nheaders, e.g. a User-Agent.\n\nBaseHandler --\n\nexceptions:\nURLError -- A subclass of IOError, individual protocols have their own\nspecific subclass.\n\nHTTPError -- Also a valid HTTP response, so you can treat an HTTP error\nas an exceptional event or valid response.\n\ninternals:\nBaseHandler and parent\n_call_chain conventions\n\nExample usage:\n\nimport urllib2\n\n# set up authentication info\nauthinfo = urllib2.HTTPBasicAuthHandler()\nauthinfo.add_password(realm='PDQ Application',\n                      uri='https://mahler:8092/site-updates.py',\n                      user='klem',\n                      passwd='geheim$parole')\n\nproxy_support = urllib2.ProxyHandler({\"http\" : \"http://ahad-haam:3128\"})\n\n# build a new opener that adds authentication and caching FTP handlers\nopener = urllib2.build_opener(proxy_support, authinfo, urllib2.CacheFTPHandler)\n\n# install it\nurllib2.install_opener(opener)\n\nf = urllib2.urlopen('http://www.python.org/')\n\n\n"));
      var1.setline(76);
      PyString.fromInterned("An extensible library for opening URLs using a variety of protocols\n\nThe simplest way to use this module is to call the urlopen function,\nwhich accepts a string containing a URL or a Request object (described\nbelow).  It opens the URL and returns the results as file-like\nobject; the returned object has some extra methods described below.\n\nThe OpenerDirector manages a collection of Handler objects that do\nall the actual work.  Each Handler implements a particular protocol or\noption.  The OpenerDirector is a composite object that invokes the\nHandlers needed to open the requested URL.  For example, the\nHTTPHandler performs HTTP GET and POST requests and deals with\nnon-error returns.  The HTTPRedirectHandler automatically deals with\nHTTP 301, 302, 303 and 307 redirect errors, and the HTTPDigestAuthHandler\ndeals with digest authentication.\n\nurlopen(url, data=None) -- Basic usage is the same as original\nurllib.  pass the url and optionally data to post to an HTTP URL, and\nget a file-like object back.  One difference is that you can also pass\na Request instance instead of URL.  Raises a URLError (subclass of\nIOError); for HTTP errors, raises an HTTPError, which can also be\ntreated as a valid response.\n\nbuild_opener -- Function that creates a new OpenerDirector instance.\nWill install the default handlers.  Accepts one or more Handlers as\narguments, either instances or Handler classes that it will\ninstantiate.  If one of the argument is a subclass of the default\nhandler, the argument will be installed instead of the default.\n\ninstall_opener -- Installs a new opener as the default opener.\n\nobjects of interest:\n\nOpenerDirector -- Sets up the User Agent as the Python-urllib client and manages\nthe Handler classes, while dealing with requests and responses.\n\nRequest -- An object that encapsulates the state of a request.  The\nstate can be as simple as the URL.  It can also include extra HTTP\nheaders, e.g. a User-Agent.\n\nBaseHandler --\n\nexceptions:\nURLError -- A subclass of IOError, individual protocols have their own\nspecific subclass.\n\nHTTPError -- Also a valid HTTP response, so you can treat an HTTP error\nas an exceptional event or valid response.\n\ninternals:\nBaseHandler and parent\n_call_chain conventions\n\nExample usage:\n\nimport urllib2\n\n# set up authentication info\nauthinfo = urllib2.HTTPBasicAuthHandler()\nauthinfo.add_password(realm='PDQ Application',\n                      uri='https://mahler:8092/site-updates.py',\n                      user='klem',\n                      passwd='geheim$parole')\n\nproxy_support = urllib2.ProxyHandler({\"http\" : \"http://ahad-haam:3128\"})\n\n# build a new opener that adds authentication and caching FTP handlers\nopener = urllib2.build_opener(proxy_support, authinfo, urllib2.CacheFTPHandler)\n\n# install it\nurllib2.install_opener(opener)\n\nf = urllib2.urlopen('http://www.python.org/')\n\n\n");
      var1.setline(92);
      PyObject var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;
      var1.setline(93);
      var3 = imp.importOne("hashlib", var1, -1);
      var1.setlocal("hashlib", var3);
      var3 = null;
      var1.setline(94);
      var3 = imp.importOne("httplib", var1, -1);
      var1.setlocal("httplib", var3);
      var3 = null;
      var1.setline(95);
      var3 = imp.importOne("mimetools", var1, -1);
      var1.setlocal("mimetools", var3);
      var3 = null;
      var1.setline(96);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(97);
      var3 = imp.importOne("posixpath", var1, -1);
      var1.setlocal("posixpath", var3);
      var3 = null;
      var1.setline(98);
      var3 = imp.importOne("random", var1, -1);
      var1.setlocal("random", var3);
      var3 = null;
      var1.setline(99);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(100);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(101);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(102);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(103);
      var3 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var3);
      var3 = null;
      var1.setline(104);
      var3 = imp.importOne("bisect", var1, -1);
      var1.setlocal("bisect", var3);
      var3 = null;
      var1.setline(105);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;

      String[] var4;
      PyObject var9;
      PyException var10;
      String[] var11;
      PyObject[] var12;
      try {
         var1.setline(108);
         var11 = new String[]{"StringIO"};
         var12 = imp.importFrom("cStringIO", var11, var1, -1);
         var9 = var12[0];
         var1.setlocal("StringIO", var9);
         var4 = null;
      } catch (Throwable var7) {
         var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(110);
         var4 = new String[]{"StringIO"};
         PyObject[] var8 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var8[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      label29: {
         try {
            var1.setline(114);
            var3 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var3);
            var3 = null;
         } catch (Throwable var6) {
            var10 = Py.setException(var6, var1);
            if (var10.match(var1.getname("ImportError"))) {
               var1.setline(116);
               var9 = var1.getname("False");
               var1.setlocal("_have_ssl", var9);
               var4 = null;
               break label29;
            }

            throw var10;
         }

         var1.setline(118);
         var9 = var1.getname("True");
         var1.setlocal("_have_ssl", var9);
         var4 = null;
      }

      var1.setline(120);
      var11 = new String[]{"unwrap", "unquote", "splittype", "splithost", "quote", "addinfourl", "splitport", "splittag", "toBytes", "splitattr", "ftpwrapper", "splituser", "splitpasswd", "splitvalue"};
      var12 = imp.importFrom("urllib", var11, var1, -1);
      var9 = var12[0];
      var1.setlocal("unwrap", var9);
      var4 = null;
      var9 = var12[1];
      var1.setlocal("unquote", var9);
      var4 = null;
      var9 = var12[2];
      var1.setlocal("splittype", var9);
      var4 = null;
      var9 = var12[3];
      var1.setlocal("splithost", var9);
      var4 = null;
      var9 = var12[4];
      var1.setlocal("quote", var9);
      var4 = null;
      var9 = var12[5];
      var1.setlocal("addinfourl", var9);
      var4 = null;
      var9 = var12[6];
      var1.setlocal("splitport", var9);
      var4 = null;
      var9 = var12[7];
      var1.setlocal("splittag", var9);
      var4 = null;
      var9 = var12[8];
      var1.setlocal("toBytes", var9);
      var4 = null;
      var9 = var12[9];
      var1.setlocal("splitattr", var9);
      var4 = null;
      var9 = var12[10];
      var1.setlocal("ftpwrapper", var9);
      var4 = null;
      var9 = var12[11];
      var1.setlocal("splituser", var9);
      var4 = null;
      var9 = var12[12];
      var1.setlocal("splitpasswd", var9);
      var4 = null;
      var9 = var12[13];
      var1.setlocal("splitvalue", var9);
      var4 = null;
      var1.setline(125);
      var11 = new String[]{"localhost", "url2pathname", "getproxies", "proxy_bypass"};
      var12 = imp.importFrom("urllib", var11, var1, -1);
      var9 = var12[0];
      var1.setlocal("localhost", var9);
      var4 = null;
      var9 = var12[1];
      var1.setlocal("url2pathname", var9);
      var4 = null;
      var9 = var12[2];
      var1.setlocal("getproxies", var9);
      var4 = null;
      var9 = var12[3];
      var1.setlocal("proxy_bypass", var9);
      var4 = null;
      var1.setline(128);
      var3 = var1.getname("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getname("None");
      var1.setlocal("_opener", var3);
      var3 = null;
      var1.setline(131);
      var12 = new PyObject[]{var1.getname("None"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("None")};
      PyFunction var13 = new PyFunction(var1.f_globals, var12, urlopen$1, (PyObject)null);
      var1.setlocal("urlopen", var13);
      var3 = null;
      var1.setline(156);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, install_opener$2, (PyObject)null);
      var1.setlocal("install_opener", var13);
      var3 = null;
      var1.setline(164);
      var12 = new PyObject[]{var1.getname("IOError")};
      var9 = Py.makeClass("URLError", var12, URLError$3);
      var1.setlocal("URLError", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(177);
      var12 = new PyObject[]{var1.getname("URLError"), var1.getname("addinfourl")};
      var9 = Py.makeClass("HTTPError", var12, HTTPError$6);
      var1.setlocal("HTTPError", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(207);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":\\d+$"));
      var1.setlocal("_cut_port_re", var3);
      var3 = null;
      var1.setline(208);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, request_host$11, PyString.fromInterned("Return request-host, as defined by RFC 2965.\n\n    Variation from RFC: returned value is lowercased, for convenient\n    comparison.\n\n    "));
      var1.setlocal("request_host", var13);
      var3 = null;
      var1.setline(224);
      var12 = Py.EmptyObjects;
      var9 = Py.makeClass("Request", var12, Request$12);
      var1.setlocal("Request", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(336);
      var12 = Py.EmptyObjects;
      var9 = Py.makeClass("OpenerDirector", var12, OpenerDirector$32);
      var1.setlocal("OpenerDirector", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(479);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, build_opener$40, PyString.fromInterned("Create an opener object from a list of handlers.\n\n    The opener will use several default handlers, including support\n    for HTTP, FTP and when applicable, HTTPS.\n\n    If any of the handlers passed as arguments are subclasses of the\n    default handlers, the default handlers will not be used.\n    "));
      var1.setlocal("build_opener", var13);
      var3 = null;
      var1.setline(518);
      var12 = Py.EmptyObjects;
      var9 = Py.makeClass("BaseHandler", var12, BaseHandler$42);
      var1.setlocal("BaseHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(537);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("HTTPErrorProcessor", var12, HTTPErrorProcessor$46);
      var1.setlocal("HTTPErrorProcessor", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(554);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("HTTPDefaultErrorHandler", var12, HTTPDefaultErrorHandler$48);
      var1.setlocal("HTTPDefaultErrorHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(558);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("HTTPRedirectHandler", var12, HTTPRedirectHandler$50);
      var1.setlocal("HTTPRedirectHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(663);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, _parse_proxy$54, PyString.fromInterned("Return (scheme, user, password, host/port) given a URL or an authority.\n\n    If a URL is supplied, it must have an authority (host:port) component.\n    According to RFC 3986, having an authority component means the URL must\n    have two slashes after the scheme:\n\n    >>> _parse_proxy('file:/ftp.example.com/')\n    Traceback (most recent call last):\n    ValueError: proxy URL with no authority: 'file:/ftp.example.com/'\n\n    The first three items of the returned tuple may be None.\n\n    Examples of authority parsing:\n\n    >>> _parse_proxy('proxy.example.com')\n    (None, None, None, 'proxy.example.com')\n    >>> _parse_proxy('proxy.example.com:3128')\n    (None, None, None, 'proxy.example.com:3128')\n\n    The authority component may optionally include userinfo (assumed to be\n    username:password):\n\n    >>> _parse_proxy('joe:password@proxy.example.com')\n    (None, 'joe', 'password', 'proxy.example.com')\n    >>> _parse_proxy('joe:password@proxy.example.com:3128')\n    (None, 'joe', 'password', 'proxy.example.com:3128')\n\n    Same examples, but with URLs instead:\n\n    >>> _parse_proxy('http://proxy.example.com/')\n    ('http', None, None, 'proxy.example.com')\n    >>> _parse_proxy('http://proxy.example.com:3128/')\n    ('http', None, None, 'proxy.example.com:3128')\n    >>> _parse_proxy('http://joe:password@proxy.example.com/')\n    ('http', 'joe', 'password', 'proxy.example.com')\n    >>> _parse_proxy('http://joe:password@proxy.example.com:3128')\n    ('http', 'joe', 'password', 'proxy.example.com:3128')\n\n    Everything after the authority is ignored:\n\n    >>> _parse_proxy('ftp://joe:password@proxy.example.com/rubbish:3128')\n    ('ftp', 'joe', 'password', 'proxy.example.com')\n\n    Test for no trailing '/' case:\n\n    >>> _parse_proxy('http://joe:password@proxy.example.com')\n    ('http', 'joe', 'password', 'proxy.example.com')\n\n    "));
      var1.setlocal("_parse_proxy", var13);
      var3 = null;
      var1.setline(735);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("ProxyHandler", var12, ProxyHandler$55);
      var1.setlocal("ProxyHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(778);
      var12 = Py.EmptyObjects;
      var9 = Py.makeClass("HTTPPasswordMgr", var12, HTTPPasswordMgr$59);
      var1.setlocal("HTTPPasswordMgr", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(842);
      var12 = new PyObject[]{var1.getname("HTTPPasswordMgr")};
      var9 = Py.makeClass("HTTPPasswordMgrWithDefaultRealm", var12, HTTPPasswordMgrWithDefaultRealm$65);
      var1.setlocal("HTTPPasswordMgrWithDefaultRealm", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(852);
      var12 = Py.EmptyObjects;
      var9 = Py.makeClass("AbstractBasicAuthHandler", var12, AbstractBasicAuthHandler$67);
      var1.setlocal("AbstractBasicAuthHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(902);
      var12 = new PyObject[]{var1.getname("AbstractBasicAuthHandler"), var1.getname("BaseHandler")};
      var9 = Py.makeClass("HTTPBasicAuthHandler", var12, HTTPBasicAuthHandler$71);
      var1.setlocal("HTTPBasicAuthHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(913);
      var12 = new PyObject[]{var1.getname("AbstractBasicAuthHandler"), var1.getname("BaseHandler")};
      var9 = Py.makeClass("ProxyBasicAuthHandler", var12, ProxyBasicAuthHandler$73);
      var1.setlocal("ProxyBasicAuthHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(928);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, randombytes$75, PyString.fromInterned("Return n random bytes."));
      var1.setlocal("randombytes", var13);
      var3 = null;
      var1.setline(942);
      var12 = Py.EmptyObjects;
      var9 = Py.makeClass("AbstractDigestAuthHandler", var12, AbstractDigestAuthHandler$76);
      var1.setlocal("AbstractDigestAuthHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1085);
      var12 = new PyObject[]{var1.getname("BaseHandler"), var1.getname("AbstractDigestAuthHandler")};
      var9 = Py.makeClass("HTTPDigestAuthHandler", var12, HTTPDigestAuthHandler$88);
      var1.setlocal("HTTPDigestAuthHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1103);
      var12 = new PyObject[]{var1.getname("BaseHandler"), var1.getname("AbstractDigestAuthHandler")};
      var9 = Py.makeClass("ProxyDigestAuthHandler", var12, ProxyDigestAuthHandler$90);
      var1.setlocal("ProxyDigestAuthHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1115);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("AbstractHTTPHandler", var12, AbstractHTTPHandler$92);
      var1.setlocal("AbstractHTTPHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1225);
      var12 = new PyObject[]{var1.getname("AbstractHTTPHandler")};
      var9 = Py.makeClass("HTTPHandler", var12, HTTPHandler$99);
      var1.setlocal("HTTPHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1232);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("httplib"), (PyObject)PyString.fromInterned("HTTPS")).__nonzero__()) {
         var1.setline(1233);
         var12 = new PyObject[]{var1.getname("AbstractHTTPHandler")};
         var9 = Py.makeClass("HTTPSHandler", var12, HTTPSHandler$101);
         var1.setlocal("HTTPSHandler", var9);
         var4 = null;
         Arrays.fill(var12, (Object)null);
      }

      var1.setline(1245);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("HTTPCookieProcessor", var12, HTTPCookieProcessor$104);
      var1.setlocal("HTTPCookieProcessor", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1263);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("UnknownHandler", var12, UnknownHandler$108);
      var1.setlocal("UnknownHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1268);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, parse_keqv_list$110, PyString.fromInterned("Parse list of key=value strings where keys are not duplicated."));
      var1.setlocal("parse_keqv_list", var13);
      var3 = null;
      var1.setline(1278);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, parse_http_list$111, PyString.fromInterned("Parse lists as described by RFC 2068 Section 2.\n\n    In particular, parse comma-separated lists where the elements of\n    the list may include quoted-strings.  A quoted-string could\n    contain a comma.  A non-quoted string could have quotes in the\n    middle.  Neither commas nor quotes count if they are escaped.\n    Only double-quotes count, not single-quotes.\n    "));
      var1.setlocal("parse_http_list", var13);
      var3 = null;
      var1.setline(1321);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, _safe_gethostbyname$112, (PyObject)null);
      var1.setlocal("_safe_gethostbyname", var13);
      var3 = null;
      var1.setline(1327);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("FileHandler", var12, FileHandler$113);
      var1.setlocal("FileHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1379);
      var12 = new PyObject[]{var1.getname("BaseHandler")};
      var9 = Py.makeClass("FTPHandler", var12, FTPHandler$117);
      var1.setlocal("FTPHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1439);
      var12 = new PyObject[]{var1.getname("FTPHandler")};
      var9 = Py.makeClass("CacheFTPHandler", var12, CacheFTPHandler$120);
      var1.setlocal("CacheFTPHandler", var9);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject urlopen$1(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(4);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(5);
         }
      }

      PyObject var3;
      String[] var4;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(135);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(136);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("You can't pass both context and any of cafile, capath, and cadefault")));
         }

         var1.setline(140);
         if (var1.getglobal("_have_ssl").__not__().__nonzero__()) {
            var1.setline(141);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SSL support not available")));
         }

         var1.setline(142);
         var10000 = var1.getglobal("ssl").__getattr__("create_default_context");
         var5 = new PyObject[]{var1.getglobal("ssl").__getattr__("Purpose").__getattr__("SERVER_AUTH"), var1.getlocal(3), var1.getlocal(4)};
         var4 = new String[]{"purpose", "cafile", "capath"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(145);
         var10000 = var1.getglobal("HTTPSHandler");
         var5 = new PyObject[]{var1.getlocal(6)};
         var4 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(146);
         var3 = var1.getglobal("build_opener").__call__(var2, var1.getlocal(7));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(147);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(148);
            var10000 = var1.getglobal("HTTPSHandler");
            var5 = new PyObject[]{var1.getlocal(6)};
            var4 = new String[]{"context"};
            var10000 = var10000.__call__(var2, var5, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(149);
            var3 = var1.getglobal("build_opener").__call__(var2, var1.getlocal(7));
            var1.setlocal(8, var3);
            var3 = null;
         } else {
            var1.setline(150);
            var3 = var1.getglobal("_opener");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(151);
               var3 = var1.getglobal("build_opener").__call__(var2);
               var1.setglobal("_opener", var3);
               var1.setlocal(8, var3);
            } else {
               var1.setline(153);
               var3 = var1.getglobal("_opener");
               var1.setlocal(8, var3);
               var3 = null;
            }
         }
      }

      var1.setline(154);
      var3 = var1.getlocal(8).__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject install_opener$2(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getlocal(0);
      var1.setglobal("_opener", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject URLError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(170);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(174);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$5, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var3);
      var3 = null;
      var1.setline(172);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("reason", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$5(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3 = PyString.fromInterned("<urlopen error %s>")._mod(var1.getlocal(0).__getattr__("reason"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HTTPError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when HTTP error occurs, but also acts like non-error return"));
      var1.setline(178);
      PyString.fromInterned("Raised when HTTP error occurs, but also acts like non-error return");
      var1.setline(179);
      PyObject var3 = var1.getname("addinfourl").__getattr__("__init__");
      var1.setlocal("_HTTPError__super_init", var3);
      var3 = null;
      var1.setline(181);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(194);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$8, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(199);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, reason$9, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("reason", var3);
      var3 = null;
      var1.setline(203);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, info$10, (PyObject)null);
      var1.setlocal("info", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(184);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("hdrs", var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("fp", var3);
      var3 = null;
      var1.setline(186);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(191);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(192);
         var1.getlocal(0).__getattr__("_HTTPError__super_init").__call__(var2, var1.getlocal(5), var1.getlocal(4), var1.getlocal(1), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$8(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyObject var3 = PyString.fromInterned("HTTP Error %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("code"), var1.getlocal(0).__getattr__("msg")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reason$9(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject info$10(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getlocal(0).__getattr__("hdrs");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject request_host$11(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyString.fromInterned("Return request-host, as defined by RFC 2965.\n\n    Variation from RFC: returned value is lowercased, for convenient\n    comparison.\n\n    ");
      var1.setline(215);
      PyObject var3 = var1.getlocal(0).__getattr__("get_full_url").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(218);
         var3 = var1.getlocal(0).__getattr__("get_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(221);
      var3 = var1.getglobal("_cut_port_re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(222);
      var3 = var1.getlocal(2).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Request$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(226);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), new PyDictionary(Py.EmptyObjects), var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$14, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_method$15, (PyObject)null);
      var1.setlocal("get_method", var4);
      var3 = null;
      var1.setline(264);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_data$16, (PyObject)null);
      var1.setlocal("add_data", var4);
      var3 = null;
      var1.setline(267);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_data$17, (PyObject)null);
      var1.setlocal("has_data", var4);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_data$18, (PyObject)null);
      var1.setlocal("get_data", var4);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_full_url$19, (PyObject)null);
      var1.setlocal("get_full_url", var4);
      var3 = null;
      var1.setline(279);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_type$20, (PyObject)null);
      var1.setlocal("get_type", var4);
      var3 = null;
      var1.setline(286);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_host$21, (PyObject)null);
      var1.setlocal("get_host", var4);
      var3 = null;
      var1.setline(293);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_selector$22, (PyObject)null);
      var1.setlocal("get_selector", var4);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_proxy$23, (PyObject)null);
      var1.setlocal("set_proxy", var4);
      var3 = null;
      var1.setline(305);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_proxy$24, (PyObject)null);
      var1.setlocal("has_proxy", var4);
      var3 = null;
      var1.setline(308);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_origin_req_host$25, (PyObject)null);
      var1.setlocal("get_origin_req_host", var4);
      var3 = null;
      var1.setline(311);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_unverifiable$26, (PyObject)null);
      var1.setlocal("is_unverifiable", var4);
      var3 = null;
      var1.setline(314);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_header$27, (PyObject)null);
      var1.setlocal("add_header", var4);
      var3 = null;
      var1.setline(318);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_unredirected_header$28, (PyObject)null);
      var1.setlocal("add_unredirected_header", var4);
      var3 = null;
      var1.setline(322);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_header$29, (PyObject)null);
      var1.setlocal("has_header", var4);
      var3 = null;
      var1.setline(326);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get_header$30, (PyObject)null);
      var1.setlocal("get_header", var4);
      var3 = null;
      var1.setline(331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, header_items$31, (PyObject)null);
      var1.setlocal("header_items", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyObject var3 = var1.getglobal("unwrap").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_Request__original", var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getglobal("splittag").__call__(var2, var1.getlocal(0).__getattr__("_Request__original"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("_Request__original", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_Request__fragment", var5);
      var5 = null;
      var3 = null;
      var1.setline(231);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(233);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(234);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_tunnel_host", var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(237);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"headers", var9);
      var3 = null;
      var1.setline(238);
      var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(238);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(240);
            var9 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"unredirected_hdrs", var9);
            var3 = null;
            var1.setline(241);
            var3 = var1.getlocal(4);
            PyObject var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(242);
               var3 = var1.getglobal("request_host").__call__(var2, var1.getlocal(0));
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(243);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("origin_req_host", var3);
            var3 = null;
            var1.setline(244);
            var3 = var1.getlocal(5);
            var1.getlocal(0).__setattr__("unverifiable", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var6 = var8[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var8[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(239);
         var1.getlocal(0).__getattr__("add_header").__call__(var2, var1.getlocal(6), var1.getlocal(7));
      }
   }

   public PyObject __getattr__$14(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("_Request__r_type"), PyString.fromInterned("_Request__r_host")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(252);
         var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("get_")._add(var1.getlocal(1).__getslice__(Py.newInteger(12), (PyObject)null, (PyObject)null))).__call__(var2);
         var1.setline(253);
         var3 = var1.getlocal(0).__getattr__("__dict__").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(254);
         throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
      }
   }

   public PyObject get_method$15(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyString var3;
      if (var1.getlocal(0).__getattr__("has_data").__call__(var2).__nonzero__()) {
         var1.setline(258);
         var3 = PyString.fromInterned("POST");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(260);
         var3 = PyString.fromInterned("GET");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject add_data$16(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_data$17(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_data$18(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_full_url$19(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_Request__fragment").__nonzero__()) {
         var1.setline(275);
         var3 = PyString.fromInterned("%s#%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_Request__original"), var1.getlocal(0).__getattr__("_Request__fragment")}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(277);
         var3 = var1.getlocal(0).__getattr__("_Request__original");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_type$20(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(281);
         var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(0).__getattr__("_Request__original"));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.getlocal(0).__setattr__("type", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("_Request__r_type", var5);
         var5 = null;
         var3 = null;
         var1.setline(282);
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(283);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown url type: %s")._mod(var1.getlocal(0).__getattr__("_Request__original")));
         }
      }

      var1.setline(284);
      var3 = var1.getlocal(0).__getattr__("type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_host$21(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getlocal(0).__getattr__("host");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(288);
         var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(0).__getattr__("_Request__r_type"));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.getlocal(0).__setattr__("host", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("_Request__r_host", var5);
         var5 = null;
         var3 = null;
         var1.setline(289);
         if (var1.getlocal(0).__getattr__("host").__nonzero__()) {
            var1.setline(290);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(0).__getattr__("host"));
            var1.getlocal(0).__setattr__("host", var3);
            var3 = null;
         }
      }

      var1.setline(291);
      var3 = var1.getlocal(0).__getattr__("host");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_selector$22(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      PyObject var3 = var1.getlocal(0).__getattr__("_Request__r_host");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_proxy$23(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(PyString.fromInterned("https"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_tunnel_host").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(298);
         var3 = var1.getlocal(0).__getattr__("host");
         var1.getlocal(0).__setattr__("_tunnel_host", var3);
         var3 = null;
      } else {
         var1.setline(300);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("type", var3);
         var3 = null;
         var1.setline(301);
         var3 = var1.getlocal(0).__getattr__("_Request__original");
         var1.getlocal(0).__setattr__("_Request__r_host", var3);
         var3 = null;
      }

      var1.setline(303);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_proxy$24(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyObject var3 = var1.getlocal(0).__getattr__("_Request__r_host");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("_Request__original"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_origin_req_host$25(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyObject var3 = var1.getlocal(0).__getattr__("origin_req_host");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_unverifiable$26(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyObject var3 = var1.getlocal(0).__getattr__("unverifiable");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_header$27(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("headers").__setitem__(var1.getlocal(1).__getattr__("capitalize").__call__(var2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_unredirected_header$28(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("unredirected_hdrs").__setitem__(var1.getlocal(1).__getattr__("capitalize").__call__(var2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_header$29(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("headers"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("unredirected_hdrs"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_header$30(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getlocal(0).__getattr__("headers").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("unredirected_hdrs").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject header_items$31(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject var3 = var1.getlocal(0).__getattr__("unredirected_hdrs").__getattr__("copy").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(333);
      var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("headers"));
      var1.setline(334);
      var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OpenerDirector$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(337);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$33, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(348);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_handler$34, (PyObject)null);
      var1.setlocal("add_handler", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$35, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(399);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _call_chain$36, (PyObject)null);
      var1.setlocal("_call_chain", var4);
      var3 = null;
      var1.setline(411);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT")};
      var4 = new PyFunction(var1.f_globals, var3, open$37, (PyObject)null);
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(439);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _open$38, (PyObject)null);
      var1.setlocal("_open", var4);
      var3 = null;
      var1.setline(454);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$39, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = PyString.fromInterned("Python-urllib/%s")._mod(var1.getglobal("__version__"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(339);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("User-agent"), var1.getlocal(1)})});
      var1.getlocal(0).__setattr__((String)"addheaders", var4);
      var3 = null;
      var1.setline(341);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"handlers", var4);
      var3 = null;
      var1.setline(343);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"handle_open", var5);
      var3 = null;
      var1.setline(344);
      var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"handle_error", var5);
      var3 = null;
      var1.setline(345);
      var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"process_response", var5);
      var3 = null;
      var1.setline(346);
      var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"process_request", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_handler$34(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("add_parent")).__not__().__nonzero__()) {
         var1.setline(350);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("expected BaseHandler instance, got %r")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(353);
         PyObject var3 = var1.getglobal("False");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(354);
         var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            PyObject var5;
            while(true) {
               PyObject var10000;
               do {
                  var1.setline(354);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(391);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(392);
                        var1.getglobal("bisect").__getattr__("insort").__call__(var2, var1.getlocal(0).__getattr__("handlers"), var1.getlocal(1));
                        var1.setline(393);
                        var1.getlocal(1).__getattr__("add_parent").__call__(var2, var1.getlocal(0));
                     }

                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(3, var4);
                  var1.setline(355);
                  var5 = var1.getlocal(3);
                  var10000 = var5._in(new PyList(new PyObject[]{PyString.fromInterned("redirect_request"), PyString.fromInterned("do_open"), PyString.fromInterned("proxy_open")}));
                  var5 = null;
               } while(var10000.__nonzero__());

               var1.setline(359);
               var5 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(360);
               var5 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(361);
               var5 = var1.getlocal(3).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(363);
               if (var1.getlocal(6).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("error")).__nonzero__()) {
                  var1.setline(364);
                  var5 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"))._add(var1.getlocal(4))._add(Py.newInteger(1));
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(365);
                  var5 = var1.getlocal(3).__getslice__(var1.getlocal(7)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
                  var1.setlocal(8, var5);
                  var5 = null;

                  try {
                     var1.setline(367);
                     var5 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
                     var1.setlocal(8, var5);
                     var5 = null;
                  } catch (Throwable var6) {
                     PyException var7 = Py.setException(var6, var1);
                     if (!var7.match(var1.getglobal("ValueError"))) {
                        throw var7;
                     }

                     var1.setline(369);
                  }

                  var1.setline(370);
                  var5 = var1.getlocal(0).__getattr__("handle_error").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyDictionary(Py.EmptyObjects)));
                  var1.setlocal(9, var5);
                  var5 = null;
                  var1.setline(371);
                  var5 = var1.getlocal(9);
                  var1.getlocal(0).__getattr__("handle_error").__setitem__(var1.getlocal(5), var5);
                  var5 = null;
                  break;
               }

               var1.setline(372);
               var5 = var1.getlocal(6);
               var10000 = var5._eq(PyString.fromInterned("open"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(373);
                  var5 = var1.getlocal(5);
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(374);
                  var5 = var1.getlocal(0).__getattr__("handle_open");
                  var1.setlocal(9, var5);
                  var5 = null;
                  break;
               }

               var1.setline(375);
               var5 = var1.getlocal(6);
               var10000 = var5._eq(PyString.fromInterned("response"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(376);
                  var5 = var1.getlocal(5);
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(377);
                  var5 = var1.getlocal(0).__getattr__("process_response");
                  var1.setlocal(9, var5);
                  var5 = null;
                  break;
               }

               var1.setline(378);
               var5 = var1.getlocal(6);
               var10000 = var5._eq(PyString.fromInterned("request"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(379);
                  var5 = var1.getlocal(5);
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(380);
                  var5 = var1.getlocal(0).__getattr__("process_request");
                  var1.setlocal(9, var5);
                  var5 = null;
                  break;
               }
            }

            var1.setline(384);
            var5 = var1.getlocal(9).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)(new PyList(Py.EmptyObjects)));
            var1.setlocal(10, var5);
            var5 = null;
            var1.setline(385);
            if (var1.getlocal(10).__nonzero__()) {
               var1.setline(386);
               var1.getglobal("bisect").__getattr__("insort").__call__(var2, var1.getlocal(10), var1.getlocal(1));
            } else {
               var1.setline(388);
               var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(1));
            }

            var1.setline(389);
            var5 = var1.getglobal("True");
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject close$35(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _call_chain$36(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyObject var3 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(Py.EmptyObjects)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(404);
      var3 = var1.getlocal(5).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(404);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(405);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(6), var1.getlocal(3));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(407);
         var10000 = var1.getlocal(7);
         PyObject[] var7 = Py.EmptyObjects;
         String[] var6 = new String[0];
         var10000 = var10000._callextra(var7, var6, var1.getlocal(4), (PyObject)null);
         var5 = null;
         var5 = var10000;
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(408);
         var5 = var1.getlocal(8);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(409);
      var5 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject open$37(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(414);
         var3 = var1.getglobal("Request").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(416);
         var3 = var1.getlocal(1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(417);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(418);
            var1.getlocal(4).__getattr__("add_data").__call__(var2, var1.getlocal(2));
         }
      }

      var1.setline(420);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(421);
      var3 = var1.getlocal(4).__getattr__("get_type").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(424);
      var3 = var1.getlocal(5)._add(PyString.fromInterned("_request"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(425);
      var3 = var1.getlocal(0).__getattr__("process_request").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyList(Py.EmptyObjects))).__iter__();

      while(true) {
         var1.setline(425);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(429);
            var3 = var1.getlocal(0).__getattr__("_open").__call__(var2, var1.getlocal(4), var1.getlocal(2));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(432);
            var3 = var1.getlocal(5)._add(PyString.fromInterned("_response"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(433);
            var3 = var1.getlocal(0).__getattr__("process_response").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyList(Py.EmptyObjects))).__iter__();

            while(true) {
               var1.setline(433);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(437);
                  var3 = var1.getlocal(9);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(7, var4);
               var1.setline(434);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(7), var1.getlocal(6));
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(435);
               var5 = var1.getlocal(8).__call__(var2, var1.getlocal(4), var1.getlocal(9));
               var1.setlocal(9, var5);
               var5 = null;
            }
         }

         var1.setlocal(7, var4);
         var1.setline(426);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(7), var1.getlocal(6));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(427);
         var5 = var1.getlocal(8).__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var5);
         var5 = null;
      }
   }

   public PyObject _open$38(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyObject var3 = var1.getlocal(0).__getattr__("_call_chain").__call__(var2, var1.getlocal(0).__getattr__("handle_open"), PyString.fromInterned("default"), PyString.fromInterned("default_open"), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(442);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(443);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(445);
         PyObject var4 = var1.getlocal(1).__getattr__("get_type").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(446);
         var4 = var1.getlocal(0).__getattr__("_call_chain").__call__(var2, var1.getlocal(0).__getattr__("handle_open"), var1.getlocal(4), var1.getlocal(4)._add(PyString.fromInterned("_open")), var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(448);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(449);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(451);
            var3 = var1.getlocal(0).__getattr__("_call_chain").__call__(var2, var1.getlocal(0).__getattr__("handle_open"), PyString.fromInterned("unknown"), PyString.fromInterned("unknown_open"), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject error$39(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("http"), PyString.fromInterned("https")}));
      var3 = null;
      PyInteger var8;
      if (var10000.__nonzero__()) {
         var1.setline(457);
         var3 = var1.getlocal(0).__getattr__("handle_error").__getitem__(PyString.fromInterned("http"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(458);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(459);
         var3 = PyString.fromInterned("http_error_%s")._mod(var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(460);
         var8 = Py.newInteger(1);
         var1.setlocal(5, var8);
         var3 = null;
         var1.setline(461);
         var3 = var1.getlocal(2);
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(463);
         var3 = var1.getlocal(0).__getattr__("handle_error");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(464);
         var3 = var1.getlocal(1)._add(PyString.fromInterned("_error"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(465);
         var8 = Py.newInteger(0);
         var1.setlocal(5, var8);
         var3 = null;
      }

      var1.setline(466);
      var3 = (new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1), var1.getlocal(4)}))._add(var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(467);
      var10000 = var1.getlocal(0).__getattr__("_call_chain");
      PyObject[] var9 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var9, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(468);
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(469);
         var3 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(471);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(472);
            PyObject var6 = (new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("default"), PyString.fromInterned("http_error_default")}))._add(var1.getlocal(6));
            var1.setlocal(2, var6);
            var4 = null;
            var1.setline(473);
            var10000 = var1.getlocal(0).__getattr__("_call_chain");
            PyObject[] var7 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000 = var10000._callextra(var7, var5, var1.getlocal(2), (PyObject)null);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject build_opener$40(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyString.fromInterned("Create an opener object from a list of handlers.\n\n    The opener will use several default handlers, including support\n    for HTTP, FTP and when applicable, HTTPS.\n\n    If any of the handlers passed as arguments are subclasses of the\n    default handlers, the default handlers will not be used.\n    ");
      var1.setline(488);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(489);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var7;
      PyCode var10004 = isclass$41;
      var7 = new PyObject[]{var1.getclosure(0)};
      PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(492);
      var3 = var1.getglobal("OpenerDirector").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(493);
      PyList var9 = new PyList(new PyObject[]{var1.getglobal("ProxyHandler"), var1.getglobal("UnknownHandler"), var1.getglobal("HTTPHandler"), var1.getglobal("HTTPDefaultErrorHandler"), var1.getglobal("HTTPRedirectHandler"), var1.getglobal("FTPHandler"), var1.getglobal("FileHandler"), var1.getglobal("HTTPErrorProcessor")});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(496);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("httplib"), (PyObject)PyString.fromInterned("HTTPS")).__nonzero__()) {
         var1.setline(497);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("HTTPSHandler"));
      }

      var1.setline(498);
      var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(499);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(499);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(506);
            var3 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(506);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(509);
                  var3 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(509);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(512);
                        var3 = var1.getlocal(0).__iter__();

                        while(true) {
                           var1.setline(512);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(516);
                              var3 = var1.getlocal(2);
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setlocal(7, var4);
                           var1.setline(513);
                           if (var1.getlocal(1).__call__(var2, var1.getlocal(7)).__nonzero__()) {
                              var1.setline(514);
                              var5 = var1.getlocal(7).__call__(var2);
                              var1.setlocal(7, var5);
                              var5 = null;
                           }

                           var1.setline(515);
                           var1.getlocal(2).__getattr__("add_handler").__call__(var2, var1.getlocal(7));
                        }
                     }

                     var1.setlocal(5, var4);
                     var1.setline(510);
                     var1.getlocal(2).__getattr__("add_handler").__call__(var2, var1.getlocal(5).__call__(var2));
                  }
               }

               var1.setlocal(5, var4);
               var1.setline(507);
               var1.getlocal(3).__getattr__("remove").__call__(var2, var1.getlocal(5));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(500);
         var5 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(500);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(6, var6);
            var1.setline(501);
            if (var1.getlocal(1).__call__(var2, var1.getlocal(6)).__nonzero__()) {
               var1.setline(502);
               if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(6), var1.getlocal(5)).__nonzero__()) {
                  var1.setline(503);
                  var1.getlocal(4).__getattr__("add").__call__(var2, var1.getlocal(5));
               }
            } else {
               var1.setline(504);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getlocal(5)).__nonzero__()) {
                  var1.setline(505);
                  var1.getlocal(4).__getattr__("add").__call__(var2, var1.getlocal(5));
               }
            }
         }
      }
   }

   public PyObject isclass$41(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      PyObject var3 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getderef(0).__getattr__("ClassType"), var1.getglobal("type")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BaseHandler$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(519);
      PyInteger var3 = Py.newInteger(500);
      var1.setlocal("handler_order", var3);
      var3 = null;
      var1.setline(521);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, add_parent$43, (PyObject)null);
      var1.setlocal("add_parent", var5);
      var3 = null;
      var1.setline(524);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$44, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(528);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$45, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject add_parent$43(PyFrame var1, ThreadState var2) {
      var1.setline(522);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("parent", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$44(PyFrame var1, ThreadState var2) {
      var1.setline(526);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __lt__$45(PyFrame var1, ThreadState var2) {
      var1.setline(529);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("handler_order")).__not__().__nonzero__()) {
         var1.setline(533);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(534);
         PyObject var4 = var1.getlocal(0).__getattr__("handler_order");
         PyObject var10000 = var4._lt(var1.getlocal(1).__getattr__("handler_order"));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject HTTPErrorProcessor$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Process HTTP error responses."));
      var1.setline(538);
      PyString.fromInterned("Process HTTP error responses.");
      var1.setline(539);
      PyInteger var3 = Py.newInteger(1000);
      var1.setlocal("handler_order", var3);
      var3 = null;
      var1.setline(541);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, http_response$47, (PyObject)null);
      var1.setlocal("http_response", var5);
      var3 = null;
      var1.setline(552);
      PyObject var6 = var1.getname("http_response");
      var1.setlocal("https_response", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_response$47(PyFrame var1, ThreadState var2) {
      var1.setline(542);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("code"), var1.getlocal(2).__getattr__("msg"), var1.getlocal(2).__getattr__("info").__call__(var2)});
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(546);
      PyInteger var6 = Py.newInteger(200);
      PyObject var10001 = var1.getlocal(3);
      PyInteger var10000 = var6;
      PyObject var7 = var10001;
      PyObject var8;
      if ((var8 = var10000._le(var10001)).__nonzero__()) {
         var8 = var7._lt(Py.newInteger(300));
      }

      var3 = null;
      if (var8.__not__().__nonzero__()) {
         var1.setline(547);
         PyObject var10 = var1.getlocal(0).__getattr__("parent").__getattr__("error");
         PyObject[] var9 = new PyObject[]{PyString.fromInterned("http"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var7 = var10.__call__(var2, var9);
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(550);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject HTTPDefaultErrorHandler$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(555);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, http_error_default$49, (PyObject)null);
      var1.setlocal("http_error_default", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_error_default$49(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      PyObject var10000 = var1.getglobal("HTTPError");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getattr__("get_full_url").__call__(var2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(2)};
      throw Py.makeException(var10000.__call__(var2, var3));
   }

   public PyObject HTTPRedirectHandler$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(561);
      PyInteger var3 = Py.newInteger(4);
      var1.setlocal("max_repeats", var3);
      var3 = null;
      var1.setline(564);
      var3 = Py.newInteger(10);
      var1.setlocal("max_redirections", var3);
      var3 = null;
      var1.setline(566);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, redirect_request$51, PyString.fromInterned("Return a Request or None in response to a redirect.\n\n        This is called by the http_error_30x methods when a\n        redirection response is received.  If a redirection should\n        take place, return a new Request to allow http_error_30x to\n        perform the redirect.  Otherwise, raise HTTPError if no-one\n        else should try to handle this url.  Return None if you can't\n        but another Handler might.\n        "));
      var1.setlocal("redirect_request", var5);
      var3 = null;
      var1.setline(600);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, http_error_302$53, (PyObject)null);
      var1.setlocal("http_error_302", var5);
      var3 = null;
      var1.setline(656);
      PyObject var6 = var1.getname("http_error_302");
      var1.setlocal("http_error_301", var6);
      var1.setlocal("http_error_303", var6);
      var1.setlocal("http_error_307", var6);
      var1.setline(658);
      PyString var7 = PyString.fromInterned("The HTTP server returned a redirect error that would lead to an infinite loop.\nThe last 30x error message was:\n");
      var1.setlocal("inf_msg", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject redirect_request$51(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyString.fromInterned("Return a Request or None in response to a redirect.\n\n        This is called by the http_error_30x methods when a\n        redirection response is received.  If a redirection should\n        take place, return a new Request to allow http_error_30x to\n        perform the redirect.  Otherwise, raise HTTPError if no-one\n        else should try to handle this url.  Return None if you can't\n        but another Handler might.\n        ");
      var1.setline(576);
      PyObject var3 = var1.getlocal(1).__getattr__("get_method").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(577);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{Py.newInteger(301), Py.newInteger(302), Py.newInteger(303), Py.newInteger(307)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(7);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("GET"), PyString.fromInterned("HEAD")}));
         var3 = null;
      }

      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._in(new PyTuple(new PyObject[]{Py.newInteger(301), Py.newInteger(302), Py.newInteger(303)}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(7);
            var10000 = var3._eq(PyString.fromInterned("POST"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(585);
         var3 = var1.getlocal(6).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("%20"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(586);
         var10000 = var1.getglobal("dict");
         var1.setline(586);
         PyObject[] var7 = Py.EmptyObjects;
         PyFunction var5 = new PyFunction(var1.f_globals, var7, f$52, (PyObject)null);
         PyObject var10002 = var5.__call__(var2, var1.getlocal(1).__getattr__("headers").__getattr__("items").__call__(var2).__iter__());
         Arrays.fill(var7, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(589);
         var10000 = var1.getglobal("Request");
         var7 = new PyObject[]{var1.getlocal(6), var1.getlocal(8), var1.getlocal(1).__getattr__("get_origin_req_host").__call__(var2), var1.getglobal("True")};
         String[] var6 = new String[]{"headers", "origin_req_host", "unverifiable"};
         var10000 = var10000.__call__(var2, var7, var6);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(594);
         var10000 = var1.getglobal("HTTPError");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1).__getattr__("get_full_url").__call__(var2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(2)};
         throw Py.makeException(var10000.__call__(var2, var4));
      }
   }

   public PyObject f$52(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var10;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(586);
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

            var10 = (PyObject)var10000;
      }

      PyObject[] var8;
      PyTuple var11;
      do {
         var1.setline(586);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var8 = Py.unpackSequence(var4, 2);
         PyObject var6 = var8[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var8[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(587);
         PyObject var9 = var1.getlocal(1).__getattr__("lower").__call__(var2);
         PyObject[] var7 = new PyObject[]{PyString.fromInterned("content-length"), PyString.fromInterned("content-type")};
         var11 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var10 = var9._notin(var11);
         var5 = null;
      } while(!var10.__nonzero__());

      var1.setline(586);
      var1.setline(586);
      var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
      var11 = new PyTuple(var8);
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = 1;
      var5 = new Object[8];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var11;
   }

   public PyObject http_error_302$53(PyFrame var1, ThreadState var2) {
      var1.setline(603);
      PyString var3 = PyString.fromInterned("location");
      PyObject var10000 = var3._in(var1.getlocal(5));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(604);
         var5 = var1.getlocal(5).__getattr__("getheaders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("location")).__getitem__(Py.newInteger(0));
         var1.setlocal(6, var5);
         var3 = null;
      } else {
         var1.setline(605);
         var3 = PyString.fromInterned("uri");
         var10000 = var3._in(var1.getlocal(5));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(608);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(606);
         var5 = var1.getlocal(5).__getattr__("getheaders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("uri")).__getitem__(Py.newInteger(0));
         var1.setlocal(6, var5);
         var3 = null;
      }

      var1.setline(611);
      var5 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(612);
      var10000 = var1.getlocal(7).__getattr__("path").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(7).__getattr__("netloc");
      }

      if (var10000.__nonzero__()) {
         var1.setline(613);
         var5 = var1.getglobal("list").__call__(var2, var1.getlocal(7));
         var1.setlocal(7, var5);
         var3 = null;
         var1.setline(614);
         var3 = PyString.fromInterned("/");
         var1.getlocal(7).__setitem__((PyObject)Py.newInteger(2), var3);
         var3 = null;
      }

      var1.setline(615);
      var5 = var1.getglobal("urlparse").__getattr__("urlunparse").__call__(var2, var1.getlocal(7));
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(617);
      var5 = var1.getglobal("urlparse").__getattr__("urljoin").__call__(var2, var1.getlocal(1).__getattr__("get_full_url").__call__(var2), var1.getlocal(6));
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(621);
      var5 = var1.getlocal(6).__getattr__("lower").__call__(var2);
      var1.setlocal(8, var5);
      var3 = null;
      var1.setline(622);
      var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("https://"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ftp://"));
         }
      }

      PyObject[] var6;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(625);
         var10000 = var1.getglobal("HTTPError");
         var6 = new PyObject[]{var1.getlocal(6), var1.getlocal(3), var1.getlocal(4)._add(PyString.fromInterned(" - Redirection to url '%s' is not allowed")._mod(var1.getlocal(6))), var1.getlocal(5), var1.getlocal(2)};
         throw Py.makeException(var10000.__call__(var2, var6));
      } else {
         var1.setline(633);
         var10000 = var1.getlocal(0).__getattr__("redirect_request");
         var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         var5 = var10000.__call__(var2, var6);
         var1.setlocal(9, var5);
         var3 = null;
         var1.setline(634);
         var5 = var1.getlocal(9);
         var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(635);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(639);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("redirect_dict")).__nonzero__()) {
               var1.setline(640);
               var5 = var1.getlocal(1).__getattr__("redirect_dict");
               var1.setlocal(10, var5);
               var1.getlocal(9).__setattr__("redirect_dict", var5);
               var1.setline(641);
               var5 = var1.getlocal(10).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(0));
               var10000 = var5._ge(var1.getlocal(0).__getattr__("max_repeats"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var5 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
                  var10000 = var5._ge(var1.getlocal(0).__getattr__("max_redirections"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(643);
                  var10000 = var1.getglobal("HTTPError");
                  var6 = new PyObject[]{var1.getlocal(1).__getattr__("get_full_url").__call__(var2), var1.getlocal(3), var1.getlocal(0).__getattr__("inf_msg")._add(var1.getlocal(4)), var1.getlocal(5), var1.getlocal(2)};
                  throw Py.makeException(var10000.__call__(var2, var6));
               }
            } else {
               var1.setline(646);
               PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(10, var7);
               var1.getlocal(9).__setattr__((String)"redirect_dict", var7);
               var1.getlocal(1).__setattr__((String)"redirect_dict", var7);
            }

            var1.setline(647);
            var5 = var1.getlocal(10).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
            var1.getlocal(10).__setitem__(var1.getlocal(6), var5);
            var3 = null;
            var1.setline(651);
            var1.getlocal(2).__getattr__("read").__call__(var2);
            var1.setline(652);
            var1.getlocal(2).__getattr__("close").__call__(var2);
            var1.setline(654);
            var10000 = var1.getlocal(0).__getattr__("parent").__getattr__("open");
            var6 = new PyObject[]{var1.getlocal(9), var1.getlocal(1).__getattr__("timeout")};
            String[] var4 = new String[]{"timeout"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject _parse_proxy$54(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyString.fromInterned("Return (scheme, user, password, host/port) given a URL or an authority.\n\n    If a URL is supplied, it must have an authority (host:port) component.\n    According to RFC 3986, having an authority component means the URL must\n    have two slashes after the scheme:\n\n    >>> _parse_proxy('file:/ftp.example.com/')\n    Traceback (most recent call last):\n    ValueError: proxy URL with no authority: 'file:/ftp.example.com/'\n\n    The first three items of the returned tuple may be None.\n\n    Examples of authority parsing:\n\n    >>> _parse_proxy('proxy.example.com')\n    (None, None, None, 'proxy.example.com')\n    >>> _parse_proxy('proxy.example.com:3128')\n    (None, None, None, 'proxy.example.com:3128')\n\n    The authority component may optionally include userinfo (assumed to be\n    username:password):\n\n    >>> _parse_proxy('joe:password@proxy.example.com')\n    (None, 'joe', 'password', 'proxy.example.com')\n    >>> _parse_proxy('joe:password@proxy.example.com:3128')\n    (None, 'joe', 'password', 'proxy.example.com:3128')\n\n    Same examples, but with URLs instead:\n\n    >>> _parse_proxy('http://proxy.example.com/')\n    ('http', None, None, 'proxy.example.com')\n    >>> _parse_proxy('http://proxy.example.com:3128/')\n    ('http', None, None, 'proxy.example.com:3128')\n    >>> _parse_proxy('http://joe:password@proxy.example.com/')\n    ('http', 'joe', 'password', 'proxy.example.com')\n    >>> _parse_proxy('http://joe:password@proxy.example.com:3128')\n    ('http', 'joe', 'password', 'proxy.example.com:3128')\n\n    Everything after the authority is ignored:\n\n    >>> _parse_proxy('ftp://joe:password@proxy.example.com/rubbish:3128')\n    ('ftp', 'joe', 'password', 'proxy.example.com')\n\n    Test for no trailing '/' case:\n\n    >>> _parse_proxy('http://joe:password@proxy.example.com')\n    ('http', 'joe', 'password', 'proxy.example.com')\n\n    ");
      var1.setline(713);
      PyObject var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(714);
      PyObject var10000;
      if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__().__nonzero__()) {
         var1.setline(716);
         var3 = var1.getglobal("None");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(717);
         var3 = var1.getlocal(0);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(720);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("//")).__not__().__nonzero__()) {
            var1.setline(721);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("proxy URL with no authority: %r")._mod(var1.getlocal(0))));
         }

         var1.setline(724);
         var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)Py.newInteger(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(725);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(726);
            var3 = var1.getglobal("None");
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(727);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(2), var1.getlocal(4), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(728);
      var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(3));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(729);
      var3 = var1.getlocal(5);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(730);
         var3 = var1.getglobal("splitpasswd").__call__(var2, var1.getlocal(5));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(732);
         var3 = var1.getglobal("None");
         var1.setlocal(7, var3);
         var1.setlocal(8, var3);
      }

      var1.setline(733);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(7), var1.getlocal(8), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject ProxyHandler$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(737);
      PyInteger var3 = Py.newInteger(100);
      var1.setlocal("handler_order", var3);
      var3 = null;
      var1.setline(739);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$56, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(749);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, proxy_open$58, (PyObject)null);
      var1.setlocal("proxy_open", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$56(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(741);
         var3 = var1.getglobal("getproxies").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(742);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("has_key")).__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("proxies must be a mapping"));
      } else {
         var1.setline(743);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("proxies", var3);
         var3 = null;
         var1.setline(744);
         var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(744);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(745);
            var10000 = var1.getglobal("setattr");
            PyObject var10002 = var1.getlocal(0);
            PyObject var10003 = PyString.fromInterned("%s_open")._mod(var1.getlocal(2));
            var1.setline(746);
            var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(0).__getattr__("proxy_open")};
            var10000.__call__((ThreadState)var2, var10002, (PyObject)var10003, (PyObject)(new PyFunction(var1.f_globals, var5, f$57)));
         }
      }
   }

   public PyObject f$57(PyFrame var1, ThreadState var2) {
      var1.setline(746);
      PyObject var3 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject proxy_open$58(PyFrame var1, ThreadState var2) {
      var1.setline(750);
      PyObject var3 = var1.getlocal(1).__getattr__("get_type").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(751);
      var3 = var1.getglobal("_parse_proxy").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(753);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(754);
         var3 = var1.getlocal(4);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(756);
      var10000 = var1.getlocal(1).__getattr__("host");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("proxy_bypass").__call__(var2, var1.getlocal(1).__getattr__("host"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(757);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(759);
         var10000 = var1.getlocal(6);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(7);
         }

         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(760);
            var6 = PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("unquote").__call__(var2, var1.getlocal(6)), var1.getglobal("unquote").__call__(var2, var1.getlocal(7))}));
            var1.setlocal(9, var6);
            var4 = null;
            var1.setline(761);
            var6 = var1.getglobal("base64").__getattr__("b64encode").__call__(var2, var1.getlocal(9)).__getattr__("strip").__call__(var2);
            var1.setlocal(10, var6);
            var4 = null;
            var1.setline(762);
            var1.getlocal(1).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Proxy-authorization"), (PyObject)PyString.fromInterned("Basic ")._add(var1.getlocal(10)));
         }

         var1.setline(763);
         var6 = var1.getglobal("unquote").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var6);
         var4 = null;
         var1.setline(764);
         var1.getlocal(1).__getattr__("set_proxy").__call__(var2, var1.getlocal(8), var1.getlocal(5));
         var1.setline(766);
         var6 = var1.getlocal(4);
         var10000 = var6._eq(var1.getlocal(5));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var6 = var1.getlocal(4);
            var10000 = var6._eq(PyString.fromInterned("https"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(768);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(776);
            var10000 = var1.getlocal(0).__getattr__("parent").__getattr__("open");
            var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(1).__getattr__("timeout")};
            String[] var7 = new String[]{"timeout"};
            var10000 = var10000.__call__(var2, var4, var7);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject HTTPPasswordMgr$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(780);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$60, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(783);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_password$61, (PyObject)null);
      var1.setlocal("add_password", var4);
      var3 = null;
      var1.setline(794);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_user_password$62, (PyObject)null);
      var1.setlocal("find_user_password", var4);
      var3 = null;
      var1.setline(804);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, reduce_uri$63, PyString.fromInterned("Accept authority or URI and extract only the authority and path."));
      var1.setlocal("reduce_uri", var4);
      var3 = null;
      var1.setline(827);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_suburi$64, PyString.fromInterned("Check if test is below base in a URI tree\n\n        Both args must be URIs in reduced form.\n        "));
      var1.setlocal("is_suburi", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$60(PyFrame var1, ThreadState var2) {
      var1.setline(781);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"passwd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_password$61(PyFrame var1, ThreadState var2) {
      var1.setline(785);
      PyList var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(786);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(787);
      PyObject var7 = var1.getlocal(1);
      PyObject var10000 = var7._in(var1.getlocal(0).__getattr__("passwd"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(788);
         PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__getattr__("passwd").__setitem__((PyObject)var1.getlocal(1), var8);
         var3 = null;
      }

      var1.setline(789);
      var7 = (new PyTuple(new PyObject[]{var1.getglobal("True"), var1.getglobal("False")})).__iter__();

      while(true) {
         var1.setline(789);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(790);
         var10000 = var1.getglobal("tuple");
         PyList var10002 = new PyList();
         PyObject var5 = var10002.__getattr__("append");
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(791);
         var5 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(791);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(791);
               var1.dellocal(7);
               var5 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(792);
               PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
               var1.getlocal(0).__getattr__("passwd").__getitem__(var1.getlocal(1)).__setitem__((PyObject)var1.getlocal(6), var9);
               var5 = null;
               break;
            }

            var1.setlocal(8, var6);
            var1.setline(791);
            var1.getlocal(7).__call__(var2, var1.getlocal(0).__getattr__("reduce_uri").__call__(var2, var1.getlocal(8), var1.getlocal(5)));
         }
      }
   }

   public PyObject find_user_password$62(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      PyObject var3 = var1.getlocal(0).__getattr__("passwd").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(796);
      var3 = (new PyTuple(new PyObject[]{var1.getglobal("True"), var1.getglobal("False")})).__iter__();

      while(true) {
         var1.setline(796);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(802);
            PyTuple var11 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(4, var4);
         var1.setline(797);
         PyObject var5 = var1.getlocal(0).__getattr__("reduce_uri").__call__(var2, var1.getlocal(2), var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(798);
         var5 = var1.getlocal(3).__getattr__("iteritems").__call__(var2).__iter__();

         label24:
         while(true) {
            var1.setline(798);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(6, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(7, var8);
            var8 = null;
            var1.setline(799);
            PyObject var10 = var1.getlocal(6).__iter__();

            do {
               var1.setline(799);
               var8 = var10.__iternext__();
               if (var8 == null) {
                  continue label24;
               }

               var1.setlocal(8, var8);
               var1.setline(800);
            } while(!var1.getlocal(0).__getattr__("is_suburi").__call__(var2, var1.getlocal(8), var1.getlocal(5)).__nonzero__());

            var1.setline(801);
            PyObject var9 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var9;
         }
      }
   }

   public PyObject reduce_uri$63(PyFrame var1, ThreadState var2) {
      var1.setline(805);
      PyString.fromInterned("Accept authority or URI and extract only the authority and path.");
      var1.setline(807);
      PyObject var3 = var1.getglobal("urlparse").__getattr__("urlsplit").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(808);
      if (var1.getlocal(3).__getitem__(Py.newInteger(1)).__nonzero__()) {
         var1.setline(810);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(811);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(812);
         Object var10000 = var1.getlocal(3).__getitem__(Py.newInteger(2));
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("/");
         }

         Object var6 = var10000;
         var1.setlocal(6, (PyObject)var6);
         var3 = null;
      } else {
         var1.setline(815);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(816);
         var3 = var1.getlocal(1);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(817);
         PyString var7 = PyString.fromInterned("/");
         var1.setlocal(6, var7);
         var3 = null;
      }

      var1.setline(818);
      var3 = var1.getglobal("splitport").__call__(var2, var1.getlocal(5));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(819);
      PyObject var9 = var1.getlocal(2);
      if (var9.__nonzero__()) {
         var3 = var1.getlocal(8);
         var9 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var9.__nonzero__()) {
            var3 = var1.getlocal(4);
            var9 = var3._isnot(var1.getglobal("None"));
            var3 = null;
         }
      }

      if (var9.__nonzero__()) {
         var1.setline(820);
         var3 = (new PyDictionary(new PyObject[]{PyString.fromInterned("http"), Py.newInteger(80), PyString.fromInterned("https"), Py.newInteger(443)})).__getattr__("get").__call__(var2, var1.getlocal(4));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(823);
         var3 = var1.getlocal(9);
         var9 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(824);
            var3 = PyString.fromInterned("%s:%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(9)}));
            var1.setlocal(5, var3);
            var3 = null;
         }
      }

      var1.setline(825);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject is_suburi$64(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      PyString.fromInterned("Check if test is below base in a URI tree\n\n        Both args must be URIs in reduced form.\n        ");
      var1.setline(832);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(833);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(834);
         PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var4._ne(var1.getlocal(2).__getitem__(Py.newInteger(0)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(835);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(836);
            var4 = var1.getglobal("posixpath").__getattr__("commonprefix").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(2).__getitem__(Py.newInteger(1))})));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(837);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var4._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(838);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(839);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject HTTPPasswordMgrWithDefaultRealm$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(844);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, find_user_password$66, (PyObject)null);
      var1.setlocal("find_user_password", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject find_user_password$66(PyFrame var1, ThreadState var2) {
      var1.setline(845);
      PyObject var3 = var1.getglobal("HTTPPasswordMgr").__getattr__("find_user_password").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(847);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(848);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(849);
         var3 = var1.getglobal("HTTPPasswordMgr").__getattr__("find_user_password").__call__(var2, var1.getlocal(0), var1.getglobal("None"), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject AbstractBasicAuthHandler$67(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(859);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?:.*,)*[ \t]*([^ \t]+)[ \t]+realm=([\"']?)([^\"']*)\\2"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("rx", var3);
      var3 = null;
      var1.setline(866);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$68, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(873);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, http_error_auth_reqed$69, (PyObject)null);
      var1.setlocal("http_error_auth_reqed", var5);
      var3 = null;
      var1.setline(889);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, retry_http_basic_auth$70, (PyObject)null);
      var1.setlocal("retry_http_basic_auth", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$68(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(868);
         var3 = var1.getglobal("HTTPPasswordMgr").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(869);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("passwd", var3);
      var3 = null;
      var1.setline(870);
      var3 = var1.getlocal(0).__getattr__("passwd").__getattr__("add_password");
      var1.getlocal(0).__setattr__("add_password", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject http_error_auth_reqed$69(PyFrame var1, ThreadState var2) {
      var1.setline(877);
      PyObject var3 = var1.getlocal(4).__getattr__("get").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(879);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(880);
         var3 = var1.getglobal("AbstractBasicAuthHandler").__getattr__("rx").__getattr__("search").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(881);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(882);
            var3 = var1.getlocal(5).__getattr__("groups").__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 3);
            PyObject var5 = var4[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;
            var1.setline(883);
            var3 = var1.getlocal(7);
            PyObject var10000 = var3._notin(new PyList(new PyObject[]{PyString.fromInterned("\""), PyString.fromInterned("'")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(884);
               var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("Basic Auth Realm was unquoted"), (PyObject)var1.getglobal("UserWarning"), (PyObject)Py.newInteger(2));
            }

            var1.setline(886);
            var3 = var1.getlocal(6).__getattr__("lower").__call__(var2);
            var10000 = var3._eq(PyString.fromInterned("basic"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(887);
               var3 = var1.getlocal(0).__getattr__("retry_http_basic_auth").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(8));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject retry_http_basic_auth$70(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      PyObject var3 = var1.getlocal(0).__getattr__("passwd").__getattr__("find_user_password").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(891);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(892);
         var3 = PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(893);
         var3 = PyString.fromInterned("Basic %s")._mod(var1.getglobal("base64").__getattr__("b64encode").__call__(var2, var1.getlocal(6)).__getattr__("strip").__call__(var2));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(894);
         var3 = var1.getlocal(2).__getattr__("get_header").__call__(var2, var1.getlocal(0).__getattr__("auth_header"), var1.getglobal("None"));
         var10000 = var3._eq(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(895);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(896);
            var1.getlocal(2).__getattr__("add_unredirected_header").__call__(var2, var1.getlocal(0).__getattr__("auth_header"), var1.getlocal(7));
            var1.setline(897);
            var10000 = var1.getlocal(0).__getattr__("parent").__getattr__("open");
            var4 = new PyObject[]{var1.getlocal(2), var1.getlocal(2).__getattr__("timeout")};
            String[] var6 = new String[]{"timeout"};
            var10000 = var10000.__call__(var2, var4, var6);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(899);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject HTTPBasicAuthHandler$71(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(904);
      PyString var3 = PyString.fromInterned("Authorization");
      var1.setlocal("auth_header", var3);
      var3 = null;
      var1.setline(906);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, http_error_401$72, (PyObject)null);
      var1.setlocal("http_error_401", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_error_401$72(PyFrame var1, ThreadState var2) {
      var1.setline(907);
      PyObject var3 = var1.getlocal(1).__getattr__("get_full_url").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(908);
      var3 = var1.getlocal(0).__getattr__("http_error_auth_reqed").__call__(var2, PyString.fromInterned("www-authenticate"), var1.getlocal(6), var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(910);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ProxyBasicAuthHandler$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(915);
      PyString var3 = PyString.fromInterned("Proxy-authorization");
      var1.setlocal("auth_header", var3);
      var3 = null;
      var1.setline(917);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, http_error_407$74, (PyObject)null);
      var1.setlocal("http_error_407", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_error_407$74(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyObject var3 = var1.getlocal(1).__getattr__("get_host").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(923);
      var3 = var1.getlocal(0).__getattr__("http_error_auth_reqed").__call__(var2, PyString.fromInterned("proxy-authenticate"), var1.getlocal(6), var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(925);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject randombytes$75(PyFrame var1, ThreadState var2) {
      var1.setline(929);
      PyString.fromInterned("Return n random bytes.");
      var1.setline(933);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/dev/urandom")).__nonzero__()) {
         var1.setline(934);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/dev/urandom"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(935);
         var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(936);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.setline(937);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(939);
         PyList var10000 = new PyList();
         PyObject var4 = var10000.__getattr__("append");
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(939);
         var4 = var1.getglobal("range").__call__(var2, var1.getlocal(0)).__iter__();

         while(true) {
            var1.setline(939);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(939);
               var1.dellocal(4);
               PyList var6 = var10000;
               var1.setlocal(3, var6);
               var4 = null;
               var1.setline(940);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(5, var5);
            var1.setline(939);
            var1.getlocal(4).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getglobal("random").__getattr__("randrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(256))));
         }
      }
   }

   public PyObject AbstractDigestAuthHandler$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(953);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$77, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(962);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset_retry_count$78, (PyObject)null);
      var1.setlocal("reset_retry_count", var4);
      var3 = null;
      var1.setline(965);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, http_error_auth_reqed$79, (PyObject)null);
      var1.setlocal("http_error_auth_reqed", var4);
      var3 = null;
      var1.setline(982);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, retry_http_digest_auth$80, (PyObject)null);
      var1.setlocal("retry_http_digest_auth", var4);
      var3 = null;
      var1.setline(994);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_cnonce$81, (PyObject)null);
      var1.setlocal("get_cnonce", var4);
      var3 = null;
      var1.setline(1004);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_authorization$82, (PyObject)null);
      var1.setlocal("get_authorization", var4);
      var3 = null;
      var1.setline(1065);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_algorithm_impls$83, (PyObject)null);
      var1.setlocal("get_algorithm_impls", var4);
      var3 = null;
      var1.setline(1080);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_entity_digest$87, (PyObject)null);
      var1.setlocal("get_entity_digest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$77(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(955);
         var3 = var1.getglobal("HTTPPasswordMgr").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(956);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("passwd", var3);
      var3 = null;
      var1.setline(957);
      var3 = var1.getlocal(0).__getattr__("passwd").__getattr__("add_password");
      var1.getlocal(0).__setattr__("add_password", var3);
      var3 = null;
      var1.setline(958);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"retried", var4);
      var3 = null;
      var1.setline(959);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"nonce_count", var4);
      var3 = null;
      var1.setline(960);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("last_nonce", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset_retry_count$78(PyFrame var1, ThreadState var2) {
      var1.setline(963);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"retried", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject http_error_auth_reqed$79(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      PyObject var3 = var1.getlocal(4).__getattr__("get").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(967);
      var3 = var1.getlocal(0).__getattr__("retried");
      PyObject var10000 = var3._gt(Py.newInteger(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(973);
         var10000 = var1.getglobal("HTTPError");
         PyObject[] var7 = new PyObject[]{var1.getlocal(3).__getattr__("get_full_url").__call__(var2), Py.newInteger(401), PyString.fromInterned("digest auth failed"), var1.getlocal(4), var1.getglobal("None")};
         throw Py.makeException(var10000.__call__(var2, var7));
      } else {
         var1.setline(976);
         var10000 = var1.getlocal(0);
         String var6 = "retried";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var6, var5);
         var1.setline(977);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(978);
            var3 = var1.getlocal(5).__getattr__("split").__call__(var2).__getitem__(Py.newInteger(0));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(979);
            var3 = var1.getlocal(6).__getattr__("lower").__call__(var2);
            var10000 = var3._eq(PyString.fromInterned("digest"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(980);
               var3 = var1.getlocal(0).__getattr__("retry_http_digest_auth").__call__(var2, var1.getlocal(3), var1.getlocal(5));
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject retry_http_digest_auth$80(PyFrame var1, ThreadState var2) {
      var1.setline(983);
      PyObject var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(984);
      var3 = var1.getglobal("parse_keqv_list").__call__(var2, var1.getglobal("parse_http_list").__call__(var2, var1.getlocal(4)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(985);
      var3 = var1.getlocal(0).__getattr__("get_authorization").__call__(var2, var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(986);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(987);
         var3 = PyString.fromInterned("Digest %s")._mod(var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(988);
         var3 = var1.getlocal(1).__getattr__("headers").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("auth_header"), var1.getglobal("None"));
         PyObject var10000 = var3._eq(var1.getlocal(6));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(989);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(990);
            var1.getlocal(1).__getattr__("add_unredirected_header").__call__(var2, var1.getlocal(0).__getattr__("auth_header"), var1.getlocal(6));
            var1.setline(991);
            var10000 = var1.getlocal(0).__getattr__("parent").__getattr__("open");
            var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(1).__getattr__("timeout")};
            String[] var7 = new String[]{"timeout"};
            var10000 = var10000.__call__(var2, var4, var7);
            var4 = null;
            PyObject var6 = var10000;
            var1.setlocal(7, var6);
            var4 = null;
            var1.setline(992);
            var3 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject get_cnonce$81(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      PyObject var3 = var1.getglobal("hashlib").__getattr__("sha1").__call__(var2, PyString.fromInterned("%s:%s:%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("nonce_count"), var1.getlocal(1), var1.getglobal("time").__getattr__("ctime").__call__(var2), var1.getglobal("randombytes").__call__((ThreadState)var2, (PyObject)Py.newInteger(8))}))).__getattr__("hexdigest").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1002);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(16), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_authorization$82(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var8;
      try {
         var1.setline(1006);
         var8 = var1.getlocal(2).__getitem__(PyString.fromInterned("realm"));
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(1007);
         var8 = var1.getlocal(2).__getitem__(PyString.fromInterned("nonce"));
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(1008);
         var8 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("qop"));
         var1.setlocal(5, var8);
         var3 = null;
         var1.setline(1009);
         var8 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("algorithm"), (PyObject)PyString.fromInterned("MD5"));
         var1.setlocal(6, var8);
         var3 = null;
         var1.setline(1012);
         var8 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("opaque"), (PyObject)var1.getglobal("None"));
         var1.setlocal(7, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(1014);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(1016);
      var8 = var1.getlocal(0).__getattr__("get_algorithm_impls").__call__(var2, var1.getlocal(6));
      PyObject[] var5 = Py.unpackSequence(var8, 2);
      PyObject var6 = var5[0];
      var1.setlocal(8, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(9, var6);
      var6 = null;
      var3 = null;
      var1.setline(1017);
      var8 = var1.getlocal(8);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1018);
         var4 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1020);
         var8 = var1.getlocal(0).__getattr__("passwd").__getattr__("find_user_password").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getattr__("get_full_url").__call__(var2));
         var5 = Py.unpackSequence(var8, 2);
         var6 = var5[0];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(11, var6);
         var6 = null;
         var3 = null;
         var1.setline(1021);
         var8 = var1.getlocal(10);
         var10000 = var8._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1022);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(1025);
            if (var1.getlocal(1).__getattr__("has_data").__call__(var2).__nonzero__()) {
               var1.setline(1026);
               var8 = var1.getlocal(0).__getattr__("get_entity_digest").__call__(var2, var1.getlocal(1).__getattr__("get_data").__call__(var2), var1.getlocal(2));
               var1.setlocal(12, var8);
               var3 = null;
            } else {
               var1.setline(1028);
               var8 = var1.getglobal("None");
               var1.setlocal(12, var8);
               var3 = null;
            }

            var1.setline(1030);
            var8 = PyString.fromInterned("%s:%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(3), var1.getlocal(11)}));
            var1.setlocal(13, var8);
            var3 = null;
            var1.setline(1031);
            var8 = PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("get_method").__call__(var2), var1.getlocal(1).__getattr__("get_selector").__call__(var2)}));
            var1.setlocal(14, var8);
            var3 = null;
            var1.setline(1034);
            var8 = var1.getlocal(5);
            var10000 = var8._eq(PyString.fromInterned("auth"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1035);
               var8 = var1.getlocal(4);
               var10000 = var8._eq(var1.getlocal(0).__getattr__("last_nonce"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1036);
                  var10000 = var1.getlocal(0);
                  String var10 = "nonce_count";
                  PyObject var9 = var10000;
                  var6 = var9.__getattr__(var10);
                  var6 = var6._iadd(Py.newInteger(1));
                  var9.__setattr__(var10, var6);
               } else {
                  var1.setline(1038);
                  PyInteger var11 = Py.newInteger(1);
                  var1.getlocal(0).__setattr__((String)"nonce_count", var11);
                  var3 = null;
                  var1.setline(1039);
                  var8 = var1.getlocal(4);
                  var1.getlocal(0).__setattr__("last_nonce", var8);
                  var3 = null;
               }

               var1.setline(1041);
               var8 = PyString.fromInterned("%08x")._mod(var1.getlocal(0).__getattr__("nonce_count"));
               var1.setlocal(15, var8);
               var3 = null;
               var1.setline(1042);
               var8 = var1.getlocal(0).__getattr__("get_cnonce").__call__(var2, var1.getlocal(4));
               var1.setlocal(16, var8);
               var3 = null;
               var1.setline(1043);
               var8 = PyString.fromInterned("%s:%s:%s:%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(15), var1.getlocal(16), var1.getlocal(5), var1.getlocal(8).__call__(var2, var1.getlocal(14))}));
               var1.setlocal(17, var8);
               var3 = null;
               var1.setline(1044);
               var8 = var1.getlocal(9).__call__(var2, var1.getlocal(8).__call__(var2, var1.getlocal(13)), var1.getlocal(17));
               var1.setlocal(18, var8);
               var3 = null;
            } else {
               var1.setline(1045);
               var8 = var1.getlocal(5);
               var10000 = var8._is(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1049);
                  throw Py.makeException(var1.getglobal("URLError").__call__(var2, PyString.fromInterned("qop '%s' is not supported.")._mod(var1.getlocal(5))));
               }

               var1.setline(1046);
               var8 = var1.getlocal(9).__call__(var2, var1.getlocal(8).__call__(var2, var1.getlocal(13)), PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(8).__call__(var2, var1.getlocal(14))})));
               var1.setlocal(18, var8);
               var3 = null;
            }

            var1.setline(1053);
            var8 = PyString.fromInterned("username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1).__getattr__("get_selector").__call__(var2), var1.getlocal(18)}));
            var1.setlocal(19, var8);
            var3 = null;
            var1.setline(1056);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(1057);
               var8 = var1.getlocal(19);
               var8 = var8._iadd(PyString.fromInterned(", opaque=\"%s\"")._mod(var1.getlocal(7)));
               var1.setlocal(19, var8);
            }

            var1.setline(1058);
            if (var1.getlocal(12).__nonzero__()) {
               var1.setline(1059);
               var8 = var1.getlocal(19);
               var8 = var8._iadd(PyString.fromInterned(", digest=\"%s\"")._mod(var1.getlocal(12)));
               var1.setlocal(19, var8);
            }

            var1.setline(1060);
            var8 = var1.getlocal(19);
            var8 = var8._iadd(PyString.fromInterned(", algorithm=\"%s\"")._mod(var1.getlocal(6)));
            var1.setlocal(19, var8);
            var1.setline(1061);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(1062);
               var8 = var1.getlocal(19);
               var8 = var8._iadd(PyString.fromInterned(", qop=auth, nc=%s, cnonce=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(15), var1.getlocal(16)})));
               var1.setlocal(19, var8);
            }

            var1.setline(1063);
            var4 = var1.getlocal(19);
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject get_algorithm_impls$83(PyFrame var1, ThreadState var2) {
      var1.setline(1067);
      PyObject var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1069);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("MD5"));
      var3 = null;
      PyObject[] var4;
      PyFunction var5;
      if (var10000.__nonzero__()) {
         var1.setline(1070);
         var1.setline(1070);
         var4 = Py.EmptyObjects;
         var5 = new PyFunction(var1.f_globals, var4, f$84);
         var1.setderef(0, var5);
         var3 = null;
      } else {
         var1.setline(1071);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("SHA"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1075);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unsupported digest authentication algorithm %r")._mod(var1.getlocal(1).__getattr__("lower").__call__(var2))));
         }

         var1.setline(1072);
         var1.setline(1072);
         var4 = Py.EmptyObjects;
         var5 = new PyFunction(var1.f_globals, var4, f$85);
         var1.setderef(0, var5);
         var3 = null;
      }

      var1.setline(1077);
      var1.setline(1077);
      var4 = Py.EmptyObjects;
      PyObject[] var10003 = var4;
      PyObject var10002 = var1.f_globals;
      PyCode var10004 = f$86;
      var4 = new PyObject[]{var1.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1078);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getderef(0), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject f$84(PyFrame var1, ThreadState var2) {
      var1.setline(1070);
      PyObject var3 = var1.getglobal("hashlib").__getattr__("md5").__call__(var2, var1.getlocal(0)).__getattr__("hexdigest").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$85(PyFrame var1, ThreadState var2) {
      var1.setline(1072);
      PyObject var3 = var1.getglobal("hashlib").__getattr__("sha1").__call__(var2, var1.getlocal(0)).__getattr__("hexdigest").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$86(PyFrame var1, ThreadState var2) {
      var1.setline(1077);
      PyObject var3 = var1.getderef(0).__call__(var2, PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_entity_digest$87(PyFrame var1, ThreadState var2) {
      var1.setline(1082);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HTTPDigestAuthHandler$88(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An authentication protocol defined by RFC 2069\n\n    Digest authentication improves on basic authentication because it\n    does not transmit passwords in the clear.\n    "));
      var1.setline(1090);
      PyString.fromInterned("An authentication protocol defined by RFC 2069\n\n    Digest authentication improves on basic authentication because it\n    does not transmit passwords in the clear.\n    ");
      var1.setline(1092);
      PyString var3 = PyString.fromInterned("Authorization");
      var1.setlocal("auth_header", var3);
      var3 = null;
      var1.setline(1093);
      PyInteger var4 = Py.newInteger(490);
      var1.setlocal("handler_order", var4);
      var3 = null;
      var1.setline(1095);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, http_error_401$89, (PyObject)null);
      var1.setlocal("http_error_401", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_error_401$89(PyFrame var1, ThreadState var2) {
      var1.setline(1096);
      PyObject var3 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(1).__getattr__("get_full_url").__call__(var2)).__getitem__(Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1097);
      var3 = var1.getlocal(0).__getattr__("http_error_auth_reqed").__call__(var2, PyString.fromInterned("www-authenticate"), var1.getlocal(6), var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1099);
      var1.getlocal(0).__getattr__("reset_retry_count").__call__(var2);
      var1.setline(1100);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ProxyDigestAuthHandler$90(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1105);
      PyString var3 = PyString.fromInterned("Proxy-Authorization");
      var1.setlocal("auth_header", var3);
      var3 = null;
      var1.setline(1106);
      PyInteger var4 = Py.newInteger(490);
      var1.setlocal("handler_order", var4);
      var3 = null;
      var1.setline(1108);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, http_error_407$91, (PyObject)null);
      var1.setlocal("http_error_407", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_error_407$91(PyFrame var1, ThreadState var2) {
      var1.setline(1109);
      PyObject var3 = var1.getlocal(1).__getattr__("get_host").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1110);
      var3 = var1.getlocal(0).__getattr__("http_error_auth_reqed").__call__(var2, PyString.fromInterned("proxy-authenticate"), var1.getlocal(6), var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1112);
      var1.getlocal(0).__getattr__("reset_retry_count").__call__(var2);
      var1.setline(1113);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AbstractHTTPHandler$92(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1117);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$93, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_http_debuglevel$94, (PyObject)null);
      var1.setlocal("set_http_debuglevel", var4);
      var3 = null;
      var1.setline(1123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_request_$95, (PyObject)null);
      var1.setlocal("do_request_", var4);
      var3 = null;
      var1.setline(1152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_open$96, PyString.fromInterned("Return an addinfourl object for the request, using http_class.\n\n        http_class must implement the HTTPConnection API from httplib.\n        The addinfourl return value is a file-like object.  It also\n        has methods and attributes including:\n            - info(): return a mimetools.Message object for the headers\n            - geturl(): return the original request URL\n            - code: HTTP status code\n        "));
      var1.setlocal("do_open", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$93(PyFrame var1, ThreadState var2) {
      var1.setline(1118);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_debuglevel", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_http_debuglevel$94(PyFrame var1, ThreadState var2) {
      var1.setline(1121);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_debuglevel", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_request_$95(PyFrame var1, ThreadState var2) {
      var1.setline(1124);
      PyObject var3 = var1.getlocal(1).__getattr__("get_host").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1125);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(1126);
         throw Py.makeException(var1.getglobal("URLError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no host given")));
      } else {
         var1.setline(1128);
         if (var1.getlocal(1).__getattr__("has_data").__call__(var2).__nonzero__()) {
            var1.setline(1129);
            var3 = var1.getlocal(1).__getattr__("get_data").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1130);
            if (var1.getlocal(1).__getattr__("has_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type")).__not__().__nonzero__()) {
               var1.setline(1131);
               var1.getlocal(1).__getattr__("add_unredirected_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)PyString.fromInterned("application/x-www-form-urlencoded"));
            }

            var1.setline(1134);
            if (var1.getlocal(1).__getattr__("has_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length")).__not__().__nonzero__()) {
               var1.setline(1135);
               var1.getlocal(1).__getattr__("add_unredirected_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)PyString.fromInterned("%d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(3))));
            }
         }

         var1.setline(1138);
         var3 = var1.getlocal(2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1139);
         PyObject var5;
         if (var1.getlocal(1).__getattr__("has_proxy").__call__(var2).__nonzero__()) {
            var1.setline(1140);
            var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(1).__getattr__("get_selector").__call__(var2));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(1141);
            var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(6));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(1143);
         if (var1.getlocal(1).__getattr__("has_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host")).__not__().__nonzero__()) {
            var1.setline(1144);
            var1.getlocal(1).__getattr__("add_unredirected_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)var1.getlocal(4));
         }

         var1.setline(1145);
         var3 = var1.getlocal(0).__getattr__("parent").__getattr__("addheaders").__iter__();

         while(true) {
            var1.setline(1145);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               var1.setline(1150);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var8 = Py.unpackSequence(var7, 2);
            PyObject var6 = var8[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var8[1];
            var1.setlocal(9, var6);
            var6 = null;
            var1.setline(1146);
            var5 = var1.getlocal(8).__getattr__("capitalize").__call__(var2);
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(1147);
            if (var1.getlocal(1).__getattr__("has_header").__call__(var2, var1.getlocal(8)).__not__().__nonzero__()) {
               var1.setline(1148);
               var1.getlocal(1).__getattr__("add_unredirected_header").__call__(var2, var1.getlocal(8), var1.getlocal(9));
            }
         }
      }
   }

   public PyObject do_open$96(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      PyString.fromInterned("Return an addinfourl object for the request, using http_class.\n\n        http_class must implement the HTTPConnection API from httplib.\n        The addinfourl return value is a file-like object.  It also\n        has methods and attributes including:\n            - info(): return a mimetools.Message object for the headers\n            - geturl(): return the original request URL\n            - code: HTTP status code\n        ");
      var1.setline(1162);
      PyObject var3 = var1.getlocal(2).__getattr__("get_host").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1163);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(1164);
         throw Py.makeException(var1.getglobal("URLError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no host given")));
      } else {
         var1.setline(1167);
         PyObject var10000 = var1.getlocal(1);
         PyObject[] var8 = new PyObject[]{var1.getlocal(4), var1.getlocal(2).__getattr__("timeout")};
         String[] var4 = new String[]{"timeout"};
         var10000 = var10000._callextra(var8, var4, (PyObject)null, var1.getlocal(3));
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1168);
         var1.getlocal(5).__getattr__("set_debuglevel").__call__(var2, var1.getlocal(0).__getattr__("_debuglevel"));
         var1.setline(1170);
         var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(2).__getattr__("unredirected_hdrs"));
         var1.setderef(0, var3);
         var3 = null;
         var1.setline(1171);
         var10000 = var1.getderef(0).__getattr__("update");
         PyObject var10002 = var1.getglobal("dict");
         var1.setline(1171);
         PyObject var10006 = var1.f_globals;
         var8 = Py.EmptyObjects;
         PyCode var10008 = f$97;
         PyObject[] var9 = new PyObject[]{var1.getclosure(0)};
         PyFunction var10 = new PyFunction(var10006, var8, var10008, (PyObject)null, var9);
         PyObject var10004 = var10.__call__(var2, var1.getlocal(2).__getattr__("headers").__getattr__("items").__call__(var2).__iter__());
         Arrays.fill(var8, (Object)null);
         var10000.__call__(var2, var10002.__call__(var2, var10004));
         var1.setline(1180);
         PyString var13 = PyString.fromInterned("close");
         var1.getderef(0).__setitem__((PyObject)PyString.fromInterned("Connection"), var13);
         var3 = null;
         var1.setline(1181);
         var10000 = var1.getglobal("dict");
         var1.setline(1182);
         var8 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var8, f$98, (PyObject)null);
         var10002 = var10.__call__(var2, var1.getderef(0).__getattr__("items").__call__(var2).__iter__());
         Arrays.fill(var8, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.setderef(0, var3);
         var3 = null;
         var1.setline(1184);
         if (var1.getlocal(2).__getattr__("_tunnel_host").__nonzero__()) {
            var1.setline(1185);
            PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(8, var15);
            var3 = null;
            var1.setline(1186);
            var13 = PyString.fromInterned("Proxy-Authorization");
            var1.setlocal(9, var13);
            var3 = null;
            var1.setline(1187);
            var3 = var1.getlocal(9);
            var10000 = var3._in(var1.getderef(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1188);
               var3 = var1.getderef(0).__getitem__(var1.getlocal(9));
               var1.getlocal(8).__setitem__(var1.getlocal(9), var3);
               var3 = null;
               var1.setline(1191);
               var1.getderef(0).__delitem__(var1.getlocal(9));
            }

            var1.setline(1192);
            var10000 = var1.getlocal(5).__getattr__("set_tunnel");
            var8 = new PyObject[]{var1.getlocal(2).__getattr__("_tunnel_host"), var1.getlocal(8)};
            var4 = new String[]{"headers"};
            var10000.__call__(var2, var8, var4);
            var3 = null;
         }

         PyObject var12;
         try {
            var1.setline(1195);
            var1.getlocal(5).__getattr__("request").__call__(var2, var1.getlocal(2).__getattr__("get_method").__call__(var2), var1.getlocal(2).__getattr__("get_selector").__call__(var2), var1.getlocal(2).__getattr__("data"), var1.getderef(0));
         } catch (Throwable var6) {
            PyException var16 = Py.setException(var6, var1);
            if (var16.match(var1.getglobal("socket").__getattr__("error"))) {
               var12 = var16.value;
               var1.setlocal(10, var12);
               var4 = null;
               var1.setline(1197);
               var1.getlocal(5).__getattr__("close").__call__(var2);
               var1.setline(1198);
               throw Py.makeException(var1.getglobal("URLError").__call__(var2, var1.getlocal(10)));
            }

            throw var16;
         }

         try {
            var1.setline(1201);
            var10000 = var1.getlocal(5).__getattr__("getresponse");
            var9 = new PyObject[]{var1.getglobal("True")};
            String[] var11 = new String[]{"buffering"};
            var10000 = var10000.__call__(var2, var9, var11);
            var4 = null;
            var12 = var10000;
            var1.setlocal(11, var12);
            var4 = null;
         } catch (Throwable var7) {
            PyException var14 = Py.setException(var7, var1);
            if (!var14.match(var1.getglobal("TypeError"))) {
               throw var14;
            }

            var1.setline(1203);
            PyObject var5 = var1.getlocal(5).__getattr__("getresponse").__call__(var2);
            var1.setlocal(11, var5);
            var5 = null;
         }

         var1.setline(1216);
         var3 = var1.getlocal(11).__getattr__("read");
         var1.getlocal(11).__setattr__("recv", var3);
         var3 = null;
         var1.setline(1217);
         var10000 = var1.getglobal("socket").__getattr__("_fileobject");
         var8 = new PyObject[]{var1.getlocal(11), var1.getglobal("True")};
         var4 = new String[]{"close"};
         var10000 = var10000.__call__(var2, var8, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(1219);
         var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(12), var1.getlocal(11).__getattr__("msg"), var1.getlocal(2).__getattr__("get_full_url").__call__(var2));
         var1.setlocal(13, var3);
         var3 = null;
         var1.setline(1220);
         var3 = var1.getlocal(11).__getattr__("status");
         var1.getlocal(13).__setattr__("code", var3);
         var3 = null;
         var1.setline(1221);
         var3 = var1.getlocal(11).__getattr__("reason");
         var1.getlocal(13).__setattr__("msg", var3);
         var3 = null;
         var1.setline(1222);
         var3 = var1.getlocal(13);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$97(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1171);
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

            var9 = (PyObject)var10000;
      }

      PyObject[] var7;
      do {
         var1.setline(1171);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(1172);
         PyObject var8 = var1.getlocal(1);
         var9 = var8._notin(var1.getderef(0));
         var5 = null;
      } while(!var9.__nonzero__());

      var1.setline(1171);
      var1.setline(1171);
      var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
      PyTuple var10 = new PyTuple(var7);
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var10;
   }

   public PyObject f$98(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1182);
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

      var1.setline(1182);
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
         var1.setline(1182);
         var1.setline(1182);
         var7 = new PyObject[]{var1.getlocal(1).__getattr__("title").__call__(var2), var1.getlocal(2)};
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

   public PyObject HTTPHandler$99(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1227);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, http_open$100, (PyObject)null);
      var1.setlocal("http_open", var4);
      var3 = null;
      var1.setline(1230);
      PyObject var5 = var1.getname("AbstractHTTPHandler").__getattr__("do_request_");
      var1.setlocal("http_request", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject http_open$100(PyFrame var1, ThreadState var2) {
      var1.setline(1228);
      PyObject var3 = var1.getlocal(0).__getattr__("do_open").__call__(var2, var1.getglobal("httplib").__getattr__("HTTPConnection"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HTTPSHandler$101(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1235);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$102, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, https_open$103, (PyObject)null);
      var1.setlocal("https_open", var4);
      var3 = null;
      var1.setline(1243);
      PyObject var5 = var1.getname("AbstractHTTPHandler").__getattr__("do_request_");
      var1.setlocal("https_request", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$102(PyFrame var1, ThreadState var2) {
      var1.setline(1236);
      var1.getglobal("AbstractHTTPHandler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1237);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_context", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject https_open$103(PyFrame var1, ThreadState var2) {
      var1.setline(1240);
      PyObject var10000 = var1.getlocal(0).__getattr__("do_open");
      PyObject[] var3 = new PyObject[]{var1.getglobal("httplib").__getattr__("HTTPSConnection"), var1.getlocal(1), var1.getlocal(0).__getattr__("_context")};
      String[] var4 = new String[]{"context"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject HTTPCookieProcessor$104(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1246);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$105, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1252);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, http_request$106, (PyObject)null);
      var1.setlocal("http_request", var4);
      var3 = null;
      var1.setline(1256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, http_response$107, (PyObject)null);
      var1.setlocal("http_response", var4);
      var3 = null;
      var1.setline(1260);
      PyObject var5 = var1.getname("http_request");
      var1.setlocal("https_request", var5);
      var3 = null;
      var1.setline(1261);
      var5 = var1.getname("http_response");
      var1.setlocal("https_response", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$105(PyFrame var1, ThreadState var2) {
      var1.setline(1247);
      PyObject var3 = imp.importOne("cookielib", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1248);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1249);
         var3 = var1.getlocal(2).__getattr__("CookieJar").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1250);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("cookiejar", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject http_request$106(PyFrame var1, ThreadState var2) {
      var1.setline(1253);
      var1.getlocal(0).__getattr__("cookiejar").__getattr__("add_cookie_header").__call__(var2, var1.getlocal(1));
      var1.setline(1254);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject http_response$107(PyFrame var1, ThreadState var2) {
      var1.setline(1257);
      var1.getlocal(0).__getattr__("cookiejar").__getattr__("extract_cookies").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setline(1258);
      PyObject var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject UnknownHandler$108(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1264);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, unknown_open$109, (PyObject)null);
      var1.setlocal("unknown_open", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject unknown_open$109(PyFrame var1, ThreadState var2) {
      var1.setline(1265);
      PyObject var3 = var1.getlocal(1).__getattr__("get_type").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1266);
      throw Py.makeException(var1.getglobal("URLError").__call__(var2, PyString.fromInterned("unknown url type: %s")._mod(var1.getlocal(2))));
   }

   public PyObject parse_keqv_list$110(PyFrame var1, ThreadState var2) {
      var1.setline(1269);
      PyString.fromInterned("Parse list of key=value strings where keys are not duplicated.");
      var1.setline(1270);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1271);
      PyObject var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1271);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(1276);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(2, var4);
         var1.setline(1272);
         PyObject var5 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(4, var7);
         var7 = null;
         var5 = null;
         var1.setline(1273);
         var5 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._eq(PyString.fromInterned("\""));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
            var10000 = var5._eq(PyString.fromInterned("\""));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1274);
            var5 = var1.getlocal(4).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(4, var5);
            var5 = null;
         }

         var1.setline(1275);
         var5 = var1.getlocal(4);
         var1.getlocal(1).__setitem__(var1.getlocal(3), var5);
         var5 = null;
      }
   }

   public PyObject parse_http_list$111(PyFrame var1, ThreadState var2) {
      var1.setline(1286);
      PyString.fromInterned("Parse lists as described by RFC 2068 Section 2.\n\n    In particular, parse comma-separated lists where the elements of\n    the list may include quoted-strings.  A quoted-string could\n    contain a comma.  A non-quoted string could have quotes in the\n    middle.  Neither commas nor quotes count if they are escaped.\n    Only double-quotes count, not single-quotes.\n    ");
      var1.setline(1287);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1288);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1290);
      PyObject var7 = var1.getglobal("False");
      var1.setlocal(3, var7);
      var1.setlocal(4, var7);
      var1.setline(1291);
      var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1291);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(1316);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1317);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
            }

            var1.setline(1319);
            PyList var9 = new PyList();
            var7 = var9.__getattr__("append");
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(1319);
            var7 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(1319);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(1319);
                  var1.dellocal(6);
                  var3 = var9;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(2, var4);
               var1.setline(1319);
               var1.getlocal(6).__call__(var2, var1.getlocal(2).__getattr__("strip").__call__(var2));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(1292);
         PyObject var5;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1293);
            var5 = var1.getlocal(2);
            var5 = var5._iadd(var1.getlocal(5));
            var1.setlocal(2, var5);
            var1.setline(1294);
            var5 = var1.getglobal("False");
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(1296);
            PyObject var10000;
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1297);
               var5 = var1.getlocal(5);
               var10000 = var5._eq(PyString.fromInterned("\\"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1298);
                  var5 = var1.getglobal("True");
                  var1.setlocal(3, var5);
                  var5 = null;
               } else {
                  var1.setline(1300);
                  var5 = var1.getlocal(5);
                  var10000 = var5._eq(PyString.fromInterned("\""));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1301);
                     var5 = var1.getglobal("False");
                     var1.setlocal(4, var5);
                     var5 = null;
                  }

                  var1.setline(1302);
                  var5 = var1.getlocal(2);
                  var5 = var5._iadd(var1.getlocal(5));
                  var1.setlocal(2, var5);
               }
            } else {
               var1.setline(1305);
               var5 = var1.getlocal(5);
               var10000 = var5._eq(PyString.fromInterned(","));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1306);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
                  var1.setline(1307);
                  PyString var8 = PyString.fromInterned("");
                  var1.setlocal(2, var8);
                  var5 = null;
               } else {
                  var1.setline(1310);
                  var5 = var1.getlocal(5);
                  var10000 = var5._eq(PyString.fromInterned("\""));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1311);
                     var5 = var1.getglobal("True");
                     var1.setlocal(4, var5);
                     var5 = null;
                  }

                  var1.setline(1313);
                  var5 = var1.getlocal(2);
                  var5 = var5._iadd(var1.getlocal(5));
                  var1.setlocal(2, var5);
               }
            }
         }
      }
   }

   public PyObject _safe_gethostbyname$112(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(1323);
         var3 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("socket").__getattr__("gaierror"))) {
            var1.setline(1325);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject FileHandler$113(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1329);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, file_open$114, (PyObject)null);
      var1.setlocal("file_open", var4);
      var3 = null;
      var1.setline(1339);
      PyObject var5 = var1.getname("None");
      var1.setlocal("names", var5);
      var3 = null;
      var1.setline(1340);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_names$115, (PyObject)null);
      var1.setlocal("get_names", var4);
      var3 = null;
      var1.setline(1351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open_local_file$116, (PyObject)null);
      var1.setlocal("open_local_file", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject file_open$114(PyFrame var1, ThreadState var2) {
      var1.setline(1330);
      PyObject var3 = var1.getlocal(1).__getattr__("get_selector").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1331);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("//"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(2), Py.newInteger(3), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned("/"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("host");
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getattr__("host");
               var10000 = var3._ne(PyString.fromInterned("localhost"));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1333);
         PyString var4 = PyString.fromInterned("ftp");
         var1.getlocal(1).__setattr__((String)"type", var4);
         var3 = null;
         var1.setline(1334);
         var3 = var1.getlocal(0).__getattr__("parent").__getattr__("open").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1336);
         var3 = var1.getlocal(0).__getattr__("open_local_file").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_names$115(PyFrame var1, ThreadState var2) {
      var1.setline(1341);
      PyObject var3 = var1.getglobal("FileHandler").__getattr__("names");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1343);
            var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("socket").__getattr__("gethostbyname_ex").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("localhost")).__getitem__(Py.newInteger(2))._add(var1.getglobal("socket").__getattr__("gethostbyname_ex").__call__(var2, var1.getglobal("socket").__getattr__("gethostname").__call__(var2)).__getitem__(Py.newInteger(2))));
            var1.getglobal("FileHandler").__setattr__("names", var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("socket").__getattr__("gaierror"))) {
               throw var6;
            }

            var1.setline(1347);
            PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("socket").__getattr__("gethostbyname").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("localhost"))});
            var1.getglobal("FileHandler").__setattr__((String)"names", var4);
            var4 = null;
         }
      }

      var1.setline(1348);
      var3 = var1.getglobal("FileHandler").__getattr__("names");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject open_local_file$116(PyFrame var1, ThreadState var2) {
      var1.setline(1352);
      PyObject var3 = imp.importOne("email.utils", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1353);
      var3 = imp.importOne("mimetypes", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1354);
      var3 = var1.getlocal(1).__getattr__("get_host").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1355);
      var3 = var1.getlocal(1).__getattr__("get_selector").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1356);
      var3 = var1.getglobal("url2pathname").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;

      PyObject var5;
      try {
         var1.setline(1358);
         var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1359);
         var3 = var1.getlocal(7).__getattr__("st_size");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(1360);
         PyObject var10000 = var1.getlocal(2).__getattr__("utils").__getattr__("formatdate");
         PyObject[] var9 = new PyObject[]{var1.getlocal(7).__getattr__("st_mtime"), var1.getglobal("True")};
         String[] var7 = new String[]{"usegmt"};
         var10000 = var10000.__call__(var2, var9, var7);
         var3 = null;
         var3 = var10000;
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1361);
         var3 = var1.getlocal(3).__getattr__("guess_type").__call__(var2, var1.getlocal(5)).__getitem__(Py.newInteger(0));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(1362);
         var10000 = var1.getglobal("mimetools").__getattr__("Message");
         PyObject var10002 = var1.getglobal("StringIO");
         PyString var10004 = PyString.fromInterned("Content-type: %s\nContent-length: %d\nLast-modified: %s\n");
         PyTuple var10005 = new PyTuple;
         PyObject[] var10007 = new PyObject[3];
         Object var10010 = var1.getlocal(10);
         if (!((PyObject)var10010).__nonzero__()) {
            var10010 = PyString.fromInterned("text/plain");
         }

         var10007[0] = (PyObject)var10010;
         var10007[1] = var1.getlocal(8);
         var10007[2] = var1.getlocal(9);
         var10005.<init>(var10007);
         var3 = var10000.__call__(var2, var10002.__call__(var2, var10004._mod(var10005)));
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(1365);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(1366);
            var3 = var1.getglobal("splitport").__call__(var2, var1.getlocal(4));
            PyObject[] var8 = Py.unpackSequence(var3, 2);
            var5 = var8[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(12, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(1367);
         var10000 = var1.getlocal(4).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(12).__not__();
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("_safe_gethostbyname").__call__(var2, var1.getlocal(4));
               var10000 = var3._in(var1.getlocal(0).__getattr__("get_names").__call__(var2));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1369);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1370);
               var3 = PyString.fromInterned("file://")._add(var1.getlocal(4))._add(var1.getlocal(5));
               var1.setlocal(13, var3);
               var3 = null;
            } else {
               var1.setline(1372);
               var3 = PyString.fromInterned("file://")._add(var1.getlocal(5));
               var1.setlocal(13, var3);
               var3 = null;
            }

            var1.setline(1373);
            var3 = var1.getglobal("addinfourl").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("rb")), var1.getlocal(11), var1.getlocal(13));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("OSError"))) {
            var5 = var4.value;
            var1.setlocal(14, var5);
            var5 = null;
            var1.setline(1376);
            throw Py.makeException(var1.getglobal("URLError").__call__(var2, var1.getlocal(14)));
         }

         throw var4;
      }

      var1.setline(1377);
      throw Py.makeException(var1.getglobal("URLError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("file not on local host")));
   }

   public PyObject FTPHandler$117(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1380);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, ftp_open$118, (PyObject)null);
      var1.setlocal("ftp_open", var4);
      var3 = null;
      var1.setline(1433);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, connect_ftp$119, (PyObject)null);
      var1.setlocal("connect_ftp", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject ftp_open$118(PyFrame var1, ThreadState var2) {
      var1.setline(1381);
      PyObject var3 = imp.importOne("ftplib", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1382);
      var3 = imp.importOne("mimetypes", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1383);
      var3 = var1.getlocal(1).__getattr__("get_host").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1384);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(1385);
         throw Py.makeException(var1.getglobal("URLError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ftp error: no host given")));
      } else {
         var1.setline(1386);
         var3 = var1.getglobal("splitport").__call__(var2, var1.getlocal(4));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(1387);
         var3 = var1.getlocal(5);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1388);
            var3 = var1.getlocal(2).__getattr__("FTP_PORT");
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(1390);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(1393);
         var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(4));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(1394);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(1395);
            var3 = var1.getglobal("splitpasswd").__call__(var2, var1.getlocal(6));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(1397);
            var3 = var1.getglobal("None");
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(1398);
         var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1399);
         Object var17 = var1.getlocal(6);
         if (!((PyObject)var17).__nonzero__()) {
            var17 = PyString.fromInterned("");
         }

         Object var12 = var17;
         var1.setlocal(6, (PyObject)var12);
         var3 = null;
         var1.setline(1400);
         var17 = var1.getlocal(7);
         if (!((PyObject)var17).__nonzero__()) {
            var17 = PyString.fromInterned("");
         }

         var12 = var17;
         var1.setlocal(7, (PyObject)var12);
         var3 = null;

         PyObject var10;
         try {
            var1.setline(1403);
            var3 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var8) {
            PyException var13 = Py.setException(var8, var1);
            if (var13.match(var1.getglobal("socket").__getattr__("error"))) {
               var10 = var13.value;
               var1.setlocal(8, var10);
               var4 = null;
               var1.setline(1405);
               throw Py.makeException(var1.getglobal("URLError").__call__(var2, var1.getlocal(8)));
            }

            throw var13;
         }

         var1.setline(1406);
         var3 = var1.getglobal("splitattr").__call__(var2, var1.getlocal(1).__getattr__("get_selector").__call__(var2));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
         var1.setline(1407);
         var3 = var1.getlocal(9).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(1408);
         var3 = var1.getglobal("map").__call__(var2, var1.getglobal("unquote"), var1.getlocal(11));
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(1409);
         PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(11).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(11).__getitem__(Py.newInteger(-1))});
         var4 = Py.unpackSequence(var14, 2);
         var5 = var4[0];
         var1.setlocal(11, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(12, var5);
         var5 = null;
         var3 = null;
         var1.setline(1410);
         var10000 = var1.getlocal(11);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(11).__getitem__(Py.newInteger(0)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1411);
            var3 = var1.getlocal(11).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(11, var3);
            var3 = null;
         }

         try {
            var1.setline(1413);
            var10000 = var1.getlocal(0).__getattr__("connect_ftp");
            PyObject[] var15 = new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getlocal(4), var1.getlocal(5), var1.getlocal(11), var1.getlocal(1).__getattr__("timeout")};
            var3 = var10000.__call__(var2, var15);
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(1414);
            var17 = var1.getlocal(12);
            if (((PyObject)var17).__nonzero__()) {
               var17 = PyString.fromInterned("I");
            }

            if (!((PyObject)var17).__nonzero__()) {
               var17 = PyString.fromInterned("D");
            }

            var12 = var17;
            var1.setlocal(14, (PyObject)var12);
            var3 = null;
            var1.setline(1415);
            var3 = var1.getlocal(10).__iter__();

            while(true) {
               var1.setline(1415);
               var10 = var3.__iternext__();
               if (var10 == null) {
                  var1.setline(1420);
                  var3 = var1.getlocal(13).__getattr__("retrfile").__call__(var2, var1.getlocal(12), var1.getlocal(14));
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(17, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(18, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1421);
                  PyString var16 = PyString.fromInterned("");
                  var1.setlocal(19, var16);
                  var3 = null;
                  var1.setline(1422);
                  var3 = var1.getlocal(3).__getattr__("guess_type").__call__(var2, var1.getlocal(1).__getattr__("get_full_url").__call__(var2)).__getitem__(Py.newInteger(0));
                  var1.setlocal(20, var3);
                  var3 = null;
                  var1.setline(1423);
                  if (var1.getlocal(20).__nonzero__()) {
                     var1.setline(1424);
                     var3 = var1.getlocal(19);
                     var3 = var3._iadd(PyString.fromInterned("Content-type: %s\n")._mod(var1.getlocal(20)));
                     var1.setlocal(19, var3);
                  }

                  var1.setline(1425);
                  var3 = var1.getlocal(18);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(18);
                     var10000 = var3._ge(Py.newInteger(0));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1426);
                     var3 = var1.getlocal(19);
                     var3 = var3._iadd(PyString.fromInterned("Content-length: %d\n")._mod(var1.getlocal(18)));
                     var1.setlocal(19, var3);
                  }

                  var1.setline(1427);
                  var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(19));
                  var1.setlocal(21, var3);
                  var3 = null;
                  var1.setline(1428);
                  var3 = var1.getglobal("mimetools").__getattr__("Message").__call__(var2, var1.getlocal(21));
                  var1.setlocal(19, var3);
                  var3 = null;
                  var1.setline(1429);
                  var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(17), var1.getlocal(19), var1.getlocal(1).__getattr__("get_full_url").__call__(var2));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(15, var10);
               var1.setline(1416);
               var5 = var1.getglobal("splitvalue").__call__(var2, var1.getlocal(15));
               PyObject[] var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(15, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(16, var7);
               var7 = null;
               var5 = null;
               var1.setline(1417);
               var5 = var1.getlocal(15).__getattr__("lower").__call__(var2);
               var10000 = var5._eq(PyString.fromInterned("type"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(16);
                  var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("A"), PyString.fromInterned("i"), PyString.fromInterned("I"), PyString.fromInterned("d"), PyString.fromInterned("D")}));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1419);
                  var5 = var1.getlocal(16).__getattr__("upper").__call__(var2);
                  var1.setlocal(14, var5);
                  var5 = null;
               }
            }
         } catch (Throwable var9) {
            PyException var11 = Py.setException(var9, var1);
            if (var11.match(var1.getlocal(2).__getattr__("all_errors"))) {
               var5 = var11.value;
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(1431);
               throw Py.makeException(var1.getglobal("URLError"), PyString.fromInterned("ftp error: %s")._mod(var1.getlocal(8)), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
            } else {
               throw var11;
            }
         }
      }
   }

   public PyObject connect_ftp$119(PyFrame var1, ThreadState var2) {
      var1.setline(1434);
      PyObject var10000 = var1.getglobal("ftpwrapper");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getglobal("False")};
      String[] var4 = new String[]{"persistent"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(1437);
      var5 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject CacheFTPHandler$120(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1442);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$121, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1449);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setTimeout$122, (PyObject)null);
      var1.setlocal("setTimeout", var4);
      var3 = null;
      var1.setline(1452);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setMaxConns$123, (PyObject)null);
      var1.setlocal("setMaxConns", var4);
      var3 = null;
      var1.setline(1455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, connect_ftp$124, (PyObject)null);
      var1.setlocal("connect_ftp", var4);
      var3 = null;
      var1.setline(1465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, check_cache$125, (PyObject)null);
      var1.setlocal("check_cache", var4);
      var3 = null;
      var1.setline(1485);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_cache$126, (PyObject)null);
      var1.setlocal("clear_cache", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$121(PyFrame var1, ThreadState var2) {
      var1.setline(1443);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"cache", var3);
      var3 = null;
      var1.setline(1444);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"timeout", var3);
      var3 = null;
      var1.setline(1445);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"soonest", var4);
      var3 = null;
      var1.setline(1446);
      var4 = Py.newInteger(60);
      var1.getlocal(0).__setattr__((String)"delay", var4);
      var3 = null;
      var1.setline(1447);
      var4 = Py.newInteger(16);
      var1.getlocal(0).__setattr__((String)"max_conns", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setTimeout$122(PyFrame var1, ThreadState var2) {
      var1.setline(1450);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("delay", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setMaxConns$123(PyFrame var1, ThreadState var2) {
      var1.setline(1453);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("max_conns", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect_ftp$124(PyFrame var1, ThreadState var2) {
      var1.setline(1456);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4), PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(5)), var1.getlocal(6)});
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1457);
      PyObject var4 = var1.getlocal(7);
      PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1458);
         var4 = var1.getglobal("time").__getattr__("time").__call__(var2)._add(var1.getlocal(0).__getattr__("delay"));
         var1.getlocal(0).__getattr__("timeout").__setitem__(var1.getlocal(7), var4);
         var3 = null;
      } else {
         var1.setline(1460);
         var10000 = var1.getglobal("ftpwrapper");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         var4 = var10000.__call__(var2, var5);
         var1.getlocal(0).__getattr__("cache").__setitem__(var1.getlocal(7), var4);
         var3 = null;
         var1.setline(1461);
         var4 = var1.getglobal("time").__getattr__("time").__call__(var2)._add(var1.getlocal(0).__getattr__("delay"));
         var1.getlocal(0).__getattr__("timeout").__setitem__(var1.getlocal(7), var4);
         var3 = null;
      }

      var1.setline(1462);
      var1.getlocal(0).__getattr__("check_cache").__call__(var2);
      var1.setline(1463);
      var4 = var1.getlocal(0).__getattr__("cache").__getitem__(var1.getlocal(7));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject check_cache$125(PyFrame var1, ThreadState var2) {
      var1.setline(1467);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1468);
      var3 = var1.getlocal(0).__getattr__("soonest");
      PyObject var10000 = var3._le(var1.getlocal(1));
      var3 = null;
      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(1469);
         var3 = var1.getlocal(0).__getattr__("timeout").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(1469);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(1470);
            var7 = var1.getlocal(3);
            var10000 = var7._lt(var1.getlocal(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1471);
               var1.getlocal(0).__getattr__("cache").__getitem__(var1.getlocal(2)).__getattr__("close").__call__(var2);
               var1.setline(1472);
               var1.getlocal(0).__getattr__("cache").__delitem__(var1.getlocal(2));
               var1.setline(1473);
               var1.getlocal(0).__getattr__("timeout").__delitem__(var1.getlocal(2));
            }
         }
      }

      var1.setline(1474);
      var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("timeout").__getattr__("values").__call__(var2));
      var1.getlocal(0).__setattr__("soonest", var3);
      var3 = null;
      var1.setline(1477);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("cache"));
      var10000 = var3._eq(var1.getlocal(0).__getattr__("max_conns"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1478);
         var3 = var1.getlocal(0).__getattr__("timeout").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(1478);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(1479);
            var7 = var1.getlocal(3);
            var10000 = var7._eq(var1.getlocal(0).__getattr__("soonest"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1480);
               var1.getlocal(0).__getattr__("cache").__delitem__(var1.getlocal(2));
               var1.setline(1481);
               var1.getlocal(0).__getattr__("timeout").__delitem__(var1.getlocal(2));
               break;
            }
         }

         var1.setline(1483);
         var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("timeout").__getattr__("values").__call__(var2));
         var1.getlocal(0).__setattr__("soonest", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_cache$126(PyFrame var1, ThreadState var2) {
      var1.setline(1486);
      PyObject var3 = var1.getlocal(0).__getattr__("cache").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(1486);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1488);
            var1.getlocal(0).__getattr__("cache").__getattr__("clear").__call__(var2);
            var1.setline(1489);
            var1.getlocal(0).__getattr__("timeout").__getattr__("clear").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1487);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }
   }

   public urllib2$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"url", "data", "timeout", "cafile", "capath", "cadefault", "context", "https_handler", "opener"};
      urlopen$1 = Py.newCode(7, var2, var1, "urlopen", 131, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opener"};
      install_opener$2 = Py.newCode(1, var2, var1, "install_opener", 156, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      URLError$3 = Py.newCode(0, var2, var1, "URLError", 164, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "reason"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 170, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$5 = Py.newCode(1, var2, var1, "__str__", 174, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPError$6 = Py.newCode(0, var2, var1, "HTTPError", 177, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "url", "code", "msg", "hdrs", "fp"};
      __init__$7 = Py.newCode(6, var2, var1, "__init__", 181, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$8 = Py.newCode(1, var2, var1, "__str__", 194, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reason$9 = Py.newCode(1, var2, var1, "reason", 199, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      info$10 = Py.newCode(1, var2, var1, "info", 203, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"request", "url", "host"};
      request_host$11 = Py.newCode(1, var2, var1, "request_host", 208, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Request$12 = Py.newCode(0, var2, var1, "Request", 224, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "url", "data", "headers", "origin_req_host", "unverifiable", "key", "value"};
      __init__$13 = Py.newCode(6, var2, var1, "__init__", 226, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$14 = Py.newCode(2, var2, var1, "__getattr__", 246, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_method$15 = Py.newCode(1, var2, var1, "get_method", 256, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      add_data$16 = Py.newCode(2, var2, var1, "add_data", 264, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_data$17 = Py.newCode(1, var2, var1, "has_data", 267, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_data$18 = Py.newCode(1, var2, var1, "get_data", 270, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_full_url$19 = Py.newCode(1, var2, var1, "get_full_url", 273, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_type$20 = Py.newCode(1, var2, var1, "get_type", 279, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_host$21 = Py.newCode(1, var2, var1, "get_host", 286, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_selector$22 = Py.newCode(1, var2, var1, "get_selector", 293, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "type"};
      set_proxy$23 = Py.newCode(3, var2, var1, "set_proxy", 296, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_proxy$24 = Py.newCode(1, var2, var1, "has_proxy", 305, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_origin_req_host$25 = Py.newCode(1, var2, var1, "get_origin_req_host", 308, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_unverifiable$26 = Py.newCode(1, var2, var1, "is_unverifiable", 311, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "val"};
      add_header$27 = Py.newCode(3, var2, var1, "add_header", 314, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "val"};
      add_unredirected_header$28 = Py.newCode(3, var2, var1, "add_unredirected_header", 318, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header_name"};
      has_header$29 = Py.newCode(2, var2, var1, "has_header", 322, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header_name", "default"};
      get_header$30 = Py.newCode(3, var2, var1, "get_header", 326, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hdrs"};
      header_items$31 = Py.newCode(1, var2, var1, "header_items", 331, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OpenerDirector$32 = Py.newCode(0, var2, var1, "OpenerDirector", 336, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "client_version"};
      __init__$33 = Py.newCode(1, var2, var1, "__init__", 337, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler", "added", "meth", "i", "protocol", "condition", "j", "kind", "lookup", "handlers"};
      add_handler$34 = Py.newCode(2, var2, var1, "add_handler", 348, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$35 = Py.newCode(1, var2, var1, "close", 395, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chain", "kind", "meth_name", "args", "handlers", "handler", "func", "result"};
      _call_chain$36 = Py.newCode(5, var2, var1, "_call_chain", 399, true, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullurl", "data", "timeout", "req", "protocol", "meth_name", "processor", "meth", "response"};
      open$37 = Py.newCode(4, var2, var1, "open", 411, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "data", "result", "protocol"};
      _open$38 = Py.newCode(3, var2, var1, "_open", 439, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "proto", "args", "dict", "meth_name", "http_err", "orig_args", "result"};
      error$39 = Py.newCode(3, var2, var1, "error", 454, true, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"handlers", "isclass", "opener", "default_classes", "skip", "klass", "check", "h", "types"};
      String[] var10001 = var2;
      urllib2$py var10007 = self;
      var2 = new String[]{"types"};
      build_opener$40 = Py.newCode(1, var10001, var1, "build_opener", 479, true, false, var10007, 40, var2, (String[])null, 1, 4097);
      var2 = new String[]{"obj"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"types"};
      isclass$41 = Py.newCode(1, var10001, var1, "isclass", 489, false, false, var10007, 41, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      BaseHandler$42 = Py.newCode(0, var2, var1, "BaseHandler", 518, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "parent"};
      add_parent$43 = Py.newCode(2, var2, var1, "add_parent", 521, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$44 = Py.newCode(1, var2, var1, "close", 524, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __lt__$45 = Py.newCode(2, var2, var1, "__lt__", 528, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPErrorProcessor$46 = Py.newCode(0, var2, var1, "HTTPErrorProcessor", 537, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "request", "response", "code", "msg", "hdrs"};
      http_response$47 = Py.newCode(3, var2, var1, "http_response", 541, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPDefaultErrorHandler$48 = Py.newCode(0, var2, var1, "HTTPDefaultErrorHandler", 554, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "hdrs"};
      http_error_default$49 = Py.newCode(6, var2, var1, "http_error_default", 555, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPRedirectHandler$50 = Py.newCode(0, var2, var1, "HTTPRedirectHandler", 558, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "headers", "newurl", "m", "newheaders", "_(586_30)"};
      redirect_request$51 = Py.newCode(7, var2, var1, "redirect_request", 566, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "k", "v"};
      f$52 = Py.newCode(1, var2, var1, "<genexpr>", 586, false, false, self, 52, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "headers", "newurl", "urlparts", "newurl_lower", "new", "visited"};
      http_error_302$53 = Py.newCode(6, var2, var1, "http_error_302", 600, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"proxy", "scheme", "r_scheme", "authority", "end", "userinfo", "hostport", "user", "password"};
      _parse_proxy$54 = Py.newCode(1, var2, var1, "_parse_proxy", 663, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ProxyHandler$55 = Py.newCode(0, var2, var1, "ProxyHandler", 735, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "proxies", "type", "url"};
      __init__$56 = Py.newCode(2, var2, var1, "__init__", 739, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"r", "proxy", "type", "meth"};
      f$57 = Py.newCode(4, var2, var1, "<lambda>", 746, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "proxy", "type", "orig_type", "proxy_type", "user", "password", "hostport", "user_pass", "creds"};
      proxy_open$58 = Py.newCode(4, var2, var1, "proxy_open", 749, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPPasswordMgr$59 = Py.newCode(0, var2, var1, "HTTPPasswordMgr", 778, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$60 = Py.newCode(1, var2, var1, "__init__", 780, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "realm", "uri", "user", "passwd", "default_port", "reduced_uri", "_[791_17]", "u"};
      add_password$61 = Py.newCode(5, var2, var1, "add_password", 783, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "realm", "authuri", "domains", "default_port", "reduced_authuri", "uris", "authinfo", "uri"};
      find_user_password$62 = Py.newCode(3, var2, var1, "find_user_password", 794, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri", "default_port", "parts", "scheme", "authority", "path", "host", "port", "dport"};
      reduce_uri$63 = Py.newCode(3, var2, var1, "reduce_uri", 804, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "base", "test", "common"};
      is_suburi$64 = Py.newCode(3, var2, var1, "is_suburi", 827, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPPasswordMgrWithDefaultRealm$65 = Py.newCode(0, var2, var1, "HTTPPasswordMgrWithDefaultRealm", 842, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "realm", "authuri", "user", "password"};
      find_user_password$66 = Py.newCode(3, var2, var1, "find_user_password", 844, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractBasicAuthHandler$67 = Py.newCode(0, var2, var1, "AbstractBasicAuthHandler", 852, false, false, self, 67, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "password_mgr"};
      __init__$68 = Py.newCode(2, var2, var1, "__init__", 866, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "authreq", "host", "req", "headers", "mo", "scheme", "quote", "realm"};
      http_error_auth_reqed$69 = Py.newCode(5, var2, var1, "http_error_auth_reqed", 873, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "req", "realm", "user", "pw", "raw", "auth"};
      retry_http_basic_auth$70 = Py.newCode(4, var2, var1, "retry_http_basic_auth", 889, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPBasicAuthHandler$71 = Py.newCode(0, var2, var1, "HTTPBasicAuthHandler", 902, false, false, self, 71, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "headers", "url", "response"};
      http_error_401$72 = Py.newCode(6, var2, var1, "http_error_401", 906, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ProxyBasicAuthHandler$73 = Py.newCode(0, var2, var1, "ProxyBasicAuthHandler", 913, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "headers", "authority", "response"};
      http_error_407$74 = Py.newCode(6, var2, var1, "http_error_407", 917, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "f", "s", "L", "_[939_13]", "i"};
      randombytes$75 = Py.newCode(1, var2, var1, "randombytes", 928, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractDigestAuthHandler$76 = Py.newCode(0, var2, var1, "AbstractDigestAuthHandler", 942, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "passwd"};
      __init__$77 = Py.newCode(2, var2, var1, "__init__", 953, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset_retry_count$78 = Py.newCode(1, var2, var1, "reset_retry_count", 962, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "auth_header", "host", "req", "headers", "authreq", "scheme"};
      http_error_auth_reqed$79 = Py.newCode(5, var2, var1, "http_error_auth_reqed", 965, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "auth", "token", "challenge", "chal", "auth_val", "resp"};
      retry_http_digest_auth$80 = Py.newCode(3, var2, var1, "retry_http_digest_auth", 982, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nonce", "dig"};
      get_cnonce$81 = Py.newCode(2, var2, var1, "get_cnonce", 994, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "chal", "realm", "nonce", "qop", "algorithm", "opaque", "H", "KD", "user", "pw", "entdig", "A1", "A2", "ncvalue", "cnonce", "noncebit", "respdig", "base"};
      get_authorization$82 = Py.newCode(3, var2, var1, "get_authorization", 1004, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "algorithm", "KD", "H"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"H"};
      get_algorithm_impls$83 = Py.newCode(2, var10001, var1, "get_algorithm_impls", 1065, false, false, var10007, 83, var2, (String[])null, 1, 4097);
      var2 = new String[]{"x"};
      f$84 = Py.newCode(1, var2, var1, "<lambda>", 1070, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$85 = Py.newCode(1, var2, var1, "<lambda>", 1072, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "d"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"H"};
      f$86 = Py.newCode(2, var10001, var1, "<lambda>", 1077, false, false, var10007, 86, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "data", "chal"};
      get_entity_digest$87 = Py.newCode(3, var2, var1, "get_entity_digest", 1080, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPDigestAuthHandler$88 = Py.newCode(0, var2, var1, "HTTPDigestAuthHandler", 1085, false, false, self, 88, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "headers", "host", "retry"};
      http_error_401$89 = Py.newCode(6, var2, var1, "http_error_401", 1095, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ProxyDigestAuthHandler$90 = Py.newCode(0, var2, var1, "ProxyDigestAuthHandler", 1103, false, false, self, 90, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "fp", "code", "msg", "headers", "host", "retry"};
      http_error_407$91 = Py.newCode(6, var2, var1, "http_error_407", 1108, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractHTTPHandler$92 = Py.newCode(0, var2, var1, "AbstractHTTPHandler", 1115, false, false, self, 92, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "debuglevel"};
      __init__$93 = Py.newCode(2, var2, var1, "__init__", 1117, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      set_http_debuglevel$94 = Py.newCode(2, var2, var1, "set_http_debuglevel", 1120, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "host", "data", "sel_host", "scheme", "sel", "sel_path", "name", "value"};
      do_request_$95 = Py.newCode(2, var2, var1, "do_request_", 1123, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "http_class", "req", "http_conn_args", "host", "h", "_(1171_28)", "_(1182_12)", "tunnel_headers", "proxy_auth_hdr", "err", "r", "fp", "resp", "headers"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"headers"};
      do_open$96 = Py.newCode(4, var10001, var1, "do_open", 1152, false, true, var10007, 96, var2, (String[])null, 1, 4097);
      var2 = new String[]{"_(x)", "k", "v"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"headers"};
      f$97 = Py.newCode(1, var10001, var1, "<genexpr>", 1171, false, false, var10007, 97, (String[])null, var2, 0, 4129);
      var2 = new String[]{"_(x)", "name", "val"};
      f$98 = Py.newCode(1, var2, var1, "<genexpr>", 1182, false, false, self, 98, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      HTTPHandler$99 = Py.newCode(0, var2, var1, "HTTPHandler", 1225, false, false, self, 99, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req"};
      http_open$100 = Py.newCode(2, var2, var1, "http_open", 1227, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPSHandler$101 = Py.newCode(0, var2, var1, "HTTPSHandler", 1233, false, false, self, 101, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "debuglevel", "context"};
      __init__$102 = Py.newCode(3, var2, var1, "__init__", 1235, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req"};
      https_open$103 = Py.newCode(2, var2, var1, "https_open", 1239, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPCookieProcessor$104 = Py.newCode(0, var2, var1, "HTTPCookieProcessor", 1245, false, false, self, 104, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cookiejar", "cookielib"};
      __init__$105 = Py.newCode(2, var2, var1, "__init__", 1246, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      http_request$106 = Py.newCode(2, var2, var1, "http_request", 1252, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "response"};
      http_response$107 = Py.newCode(3, var2, var1, "http_response", 1256, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnknownHandler$108 = Py.newCode(0, var2, var1, "UnknownHandler", 1263, false, false, self, 108, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "type"};
      unknown_open$109 = Py.newCode(2, var2, var1, "unknown_open", 1264, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"l", "parsed", "elt", "k", "v"};
      parse_keqv_list$110 = Py.newCode(1, var2, var1, "parse_keqv_list", 1268, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "res", "part", "escape", "quote", "cur", "_[1319_12]"};
      parse_http_list$111 = Py.newCode(1, var2, var1, "parse_http_list", 1278, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host"};
      _safe_gethostbyname$112 = Py.newCode(1, var2, var1, "_safe_gethostbyname", 1321, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FileHandler$113 = Py.newCode(0, var2, var1, "FileHandler", 1327, false, false, self, 113, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "url"};
      file_open$114 = Py.newCode(2, var2, var1, "file_open", 1329, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_names$115 = Py.newCode(1, var2, var1, "get_names", 1340, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "email", "mimetypes", "host", "filename", "localfile", "stats", "size", "modified", "mtype", "headers", "port", "origurl", "msg"};
      open_local_file$116 = Py.newCode(2, var2, var1, "open_local_file", 1351, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FTPHandler$117 = Py.newCode(0, var2, var1, "FTPHandler", 1379, false, false, self, 117, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "req", "ftplib", "mimetypes", "host", "port", "user", "passwd", "msg", "path", "attrs", "dirs", "file", "fw", "type", "attr", "value", "fp", "retrlen", "headers", "mtype", "sf"};
      ftp_open$118 = Py.newCode(2, var2, var1, "ftp_open", 1380, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "passwd", "host", "port", "dirs", "timeout", "fw"};
      connect_ftp$119 = Py.newCode(7, var2, var1, "connect_ftp", 1433, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CacheFTPHandler$120 = Py.newCode(0, var2, var1, "CacheFTPHandler", 1439, false, false, self, 120, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$121 = Py.newCode(1, var2, var1, "__init__", 1442, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t"};
      setTimeout$122 = Py.newCode(2, var2, var1, "setTimeout", 1449, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m"};
      setMaxConns$123 = Py.newCode(2, var2, var1, "setMaxConns", 1452, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "passwd", "host", "port", "dirs", "timeout", "key"};
      connect_ftp$124 = Py.newCode(7, var2, var1, "connect_ftp", 1455, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t", "k", "v"};
      check_cache$125 = Py.newCode(1, var2, var1, "check_cache", 1465, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "conn"};
      clear_cache$126 = Py.newCode(1, var2, var1, "clear_cache", 1485, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new urllib2$py("urllib2$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(urllib2$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.urlopen$1(var2, var3);
         case 2:
            return this.install_opener$2(var2, var3);
         case 3:
            return this.URLError$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.__str__$5(var2, var3);
         case 6:
            return this.HTTPError$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.__str__$8(var2, var3);
         case 9:
            return this.reason$9(var2, var3);
         case 10:
            return this.info$10(var2, var3);
         case 11:
            return this.request_host$11(var2, var3);
         case 12:
            return this.Request$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.__getattr__$14(var2, var3);
         case 15:
            return this.get_method$15(var2, var3);
         case 16:
            return this.add_data$16(var2, var3);
         case 17:
            return this.has_data$17(var2, var3);
         case 18:
            return this.get_data$18(var2, var3);
         case 19:
            return this.get_full_url$19(var2, var3);
         case 20:
            return this.get_type$20(var2, var3);
         case 21:
            return this.get_host$21(var2, var3);
         case 22:
            return this.get_selector$22(var2, var3);
         case 23:
            return this.set_proxy$23(var2, var3);
         case 24:
            return this.has_proxy$24(var2, var3);
         case 25:
            return this.get_origin_req_host$25(var2, var3);
         case 26:
            return this.is_unverifiable$26(var2, var3);
         case 27:
            return this.add_header$27(var2, var3);
         case 28:
            return this.add_unredirected_header$28(var2, var3);
         case 29:
            return this.has_header$29(var2, var3);
         case 30:
            return this.get_header$30(var2, var3);
         case 31:
            return this.header_items$31(var2, var3);
         case 32:
            return this.OpenerDirector$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this.add_handler$34(var2, var3);
         case 35:
            return this.close$35(var2, var3);
         case 36:
            return this._call_chain$36(var2, var3);
         case 37:
            return this.open$37(var2, var3);
         case 38:
            return this._open$38(var2, var3);
         case 39:
            return this.error$39(var2, var3);
         case 40:
            return this.build_opener$40(var2, var3);
         case 41:
            return this.isclass$41(var2, var3);
         case 42:
            return this.BaseHandler$42(var2, var3);
         case 43:
            return this.add_parent$43(var2, var3);
         case 44:
            return this.close$44(var2, var3);
         case 45:
            return this.__lt__$45(var2, var3);
         case 46:
            return this.HTTPErrorProcessor$46(var2, var3);
         case 47:
            return this.http_response$47(var2, var3);
         case 48:
            return this.HTTPDefaultErrorHandler$48(var2, var3);
         case 49:
            return this.http_error_default$49(var2, var3);
         case 50:
            return this.HTTPRedirectHandler$50(var2, var3);
         case 51:
            return this.redirect_request$51(var2, var3);
         case 52:
            return this.f$52(var2, var3);
         case 53:
            return this.http_error_302$53(var2, var3);
         case 54:
            return this._parse_proxy$54(var2, var3);
         case 55:
            return this.ProxyHandler$55(var2, var3);
         case 56:
            return this.__init__$56(var2, var3);
         case 57:
            return this.f$57(var2, var3);
         case 58:
            return this.proxy_open$58(var2, var3);
         case 59:
            return this.HTTPPasswordMgr$59(var2, var3);
         case 60:
            return this.__init__$60(var2, var3);
         case 61:
            return this.add_password$61(var2, var3);
         case 62:
            return this.find_user_password$62(var2, var3);
         case 63:
            return this.reduce_uri$63(var2, var3);
         case 64:
            return this.is_suburi$64(var2, var3);
         case 65:
            return this.HTTPPasswordMgrWithDefaultRealm$65(var2, var3);
         case 66:
            return this.find_user_password$66(var2, var3);
         case 67:
            return this.AbstractBasicAuthHandler$67(var2, var3);
         case 68:
            return this.__init__$68(var2, var3);
         case 69:
            return this.http_error_auth_reqed$69(var2, var3);
         case 70:
            return this.retry_http_basic_auth$70(var2, var3);
         case 71:
            return this.HTTPBasicAuthHandler$71(var2, var3);
         case 72:
            return this.http_error_401$72(var2, var3);
         case 73:
            return this.ProxyBasicAuthHandler$73(var2, var3);
         case 74:
            return this.http_error_407$74(var2, var3);
         case 75:
            return this.randombytes$75(var2, var3);
         case 76:
            return this.AbstractDigestAuthHandler$76(var2, var3);
         case 77:
            return this.__init__$77(var2, var3);
         case 78:
            return this.reset_retry_count$78(var2, var3);
         case 79:
            return this.http_error_auth_reqed$79(var2, var3);
         case 80:
            return this.retry_http_digest_auth$80(var2, var3);
         case 81:
            return this.get_cnonce$81(var2, var3);
         case 82:
            return this.get_authorization$82(var2, var3);
         case 83:
            return this.get_algorithm_impls$83(var2, var3);
         case 84:
            return this.f$84(var2, var3);
         case 85:
            return this.f$85(var2, var3);
         case 86:
            return this.f$86(var2, var3);
         case 87:
            return this.get_entity_digest$87(var2, var3);
         case 88:
            return this.HTTPDigestAuthHandler$88(var2, var3);
         case 89:
            return this.http_error_401$89(var2, var3);
         case 90:
            return this.ProxyDigestAuthHandler$90(var2, var3);
         case 91:
            return this.http_error_407$91(var2, var3);
         case 92:
            return this.AbstractHTTPHandler$92(var2, var3);
         case 93:
            return this.__init__$93(var2, var3);
         case 94:
            return this.set_http_debuglevel$94(var2, var3);
         case 95:
            return this.do_request_$95(var2, var3);
         case 96:
            return this.do_open$96(var2, var3);
         case 97:
            return this.f$97(var2, var3);
         case 98:
            return this.f$98(var2, var3);
         case 99:
            return this.HTTPHandler$99(var2, var3);
         case 100:
            return this.http_open$100(var2, var3);
         case 101:
            return this.HTTPSHandler$101(var2, var3);
         case 102:
            return this.__init__$102(var2, var3);
         case 103:
            return this.https_open$103(var2, var3);
         case 104:
            return this.HTTPCookieProcessor$104(var2, var3);
         case 105:
            return this.__init__$105(var2, var3);
         case 106:
            return this.http_request$106(var2, var3);
         case 107:
            return this.http_response$107(var2, var3);
         case 108:
            return this.UnknownHandler$108(var2, var3);
         case 109:
            return this.unknown_open$109(var2, var3);
         case 110:
            return this.parse_keqv_list$110(var2, var3);
         case 111:
            return this.parse_http_list$111(var2, var3);
         case 112:
            return this._safe_gethostbyname$112(var2, var3);
         case 113:
            return this.FileHandler$113(var2, var3);
         case 114:
            return this.file_open$114(var2, var3);
         case 115:
            return this.get_names$115(var2, var3);
         case 116:
            return this.open_local_file$116(var2, var3);
         case 117:
            return this.FTPHandler$117(var2, var3);
         case 118:
            return this.ftp_open$118(var2, var3);
         case 119:
            return this.connect_ftp$119(var2, var3);
         case 120:
            return this.CacheFTPHandler$120(var2, var3);
         case 121:
            return this.__init__$121(var2, var3);
         case 122:
            return this.setTimeout$122(var2, var3);
         case 123:
            return this.setMaxConns$123(var2, var3);
         case 124:
            return this.connect_ftp$124(var2, var3);
         case 125:
            return this.check_cache$125(var2, var3);
         case 126:
            return this.clear_cache$126(var2, var3);
         default:
            return null;
      }
   }
}
