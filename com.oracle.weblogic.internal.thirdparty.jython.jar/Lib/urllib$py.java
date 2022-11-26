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
@MTime(1498849384000L)
@Filename("urllib.py")
public class urllib$py extends PyFunctionTable implements PyRunnable {
   static urllib$py self;
   static final PyCode f$0;
   static final PyCode url2pathname$1;
   static final PyCode pathname2url$2;
   static final PyCode urlopen$3;
   static final PyCode urlretrieve$4;
   static final PyCode urlcleanup$5;
   static final PyCode ContentTooShortError$6;
   static final PyCode __init__$7;
   static final PyCode URLopener$8;
   static final PyCode __init__$9;
   static final PyCode __del__$10;
   static final PyCode close$11;
   static final PyCode cleanup$12;
   static final PyCode addheader$13;
   static final PyCode open$14;
   static final PyCode open_unknown$15;
   static final PyCode open_unknown_proxy$16;
   static final PyCode retrieve$17;
   static final PyCode open_http$18;
   static final PyCode http_error$19;
   static final PyCode http_error_default$20;
   static final PyCode open_https$21;
   static final PyCode open_file$22;
   static final PyCode open_local_file$23;
   static final PyCode open_ftp$24;
   static final PyCode open_data$25;
   static final PyCode FancyURLopener$26;
   static final PyCode __init__$27;
   static final PyCode http_error_default$28;
   static final PyCode http_error_302$29;
   static final PyCode redirect_internal$30;
   static final PyCode http_error_301$31;
   static final PyCode http_error_303$32;
   static final PyCode http_error_307$33;
   static final PyCode http_error_401$34;
   static final PyCode http_error_407$35;
   static final PyCode retry_proxy_http_basic_auth$36;
   static final PyCode retry_proxy_https_basic_auth$37;
   static final PyCode retry_http_basic_auth$38;
   static final PyCode retry_https_basic_auth$39;
   static final PyCode get_user_passwd$40;
   static final PyCode prompt_user_passwd$41;
   static final PyCode localhost$42;
   static final PyCode thishost$43;
   static final PyCode ftperrors$44;
   static final PyCode noheaders$45;
   static final PyCode ftpwrapper$46;
   static final PyCode __init__$47;
   static final PyCode init$48;
   static final PyCode retrfile$49;
   static final PyCode endtransfer$50;
   static final PyCode close$51;
   static final PyCode file_close$52;
   static final PyCode real_close$53;
   static final PyCode addbase$54;
   static final PyCode __init__$55;
   static final PyCode f$56;
   static final PyCode __repr__$57;
   static final PyCode close$58;
   static final PyCode addclosehook$59;
   static final PyCode __init__$60;
   static final PyCode close$61;
   static final PyCode addinfo$62;
   static final PyCode __init__$63;
   static final PyCode info$64;
   static final PyCode addinfourl$65;
   static final PyCode __init__$66;
   static final PyCode info$67;
   static final PyCode getcode$68;
   static final PyCode geturl$69;
   static final PyCode _is_unicode$70;
   static final PyCode _is_unicode$71;
   static final PyCode toBytes$72;
   static final PyCode unwrap$73;
   static final PyCode splittype$74;
   static final PyCode splithost$75;
   static final PyCode splituser$76;
   static final PyCode splitpasswd$77;
   static final PyCode splitport$78;
   static final PyCode splitnport$79;
   static final PyCode splitquery$80;
   static final PyCode splittag$81;
   static final PyCode splitattr$82;
   static final PyCode splitvalue$83;
   static final PyCode f$84;
   static final PyCode unquote$85;
   static final PyCode unquote_plus$86;
   static final PyCode quote$87;
   static final PyCode quote_plus$88;
   static final PyCode urlencode$89;
   static final PyCode getproxies_environment$90;
   static final PyCode proxy_bypass_environment$91;
   static final PyCode proxy_bypass_macosx_sysconf$92;
   static final PyCode ip2num$93;
   static final PyCode getproxies_macosx_sysconf$94;
   static final PyCode proxy_bypass$95;
   static final PyCode getproxies$96;
   static final PyCode getproxies_registry$97;
   static final PyCode getproxies$98;
   static final PyCode proxy_bypass_registry$99;
   static final PyCode proxy_bypass$100;
   static final PyCode test1$101;
   static final PyCode reporthook$102;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Open an arbitrary URL.\n\nSee the following document for more info on URLs:\n\"Names and Addresses, URIs, URLs, URNs, URCs\", at\nhttp://www.w3.org/pub/WWW/Addressing/Overview.html\n\nSee also the HTTP spec (from which the error codes are derived):\n\"HTTP - Hypertext Transfer Protocol\", at\nhttp://www.w3.org/pub/WWW/Protocols/\n\nRelated standards and specs:\n- RFC1808: the \"relative URL\" spec. (authoritative status)\n- RFC1738 - the \"URL standard\". (authoritative status)\n- RFC1630 - the \"URI spec\". (informational status)\n\nThe object returned by URLopener().open(file) will differ per\nprotocol.  All you know is that is has methods read(), readline(),\nreadlines(), fileno(), close() and info().  The read*(), fileno()\nand close() methods work like those of open files.\nThe info() method returns a mimetools.Message object which can be\nused to query various info about the object, if available.\n(mimetools.Message objects are queried with the getheader() method.)\n"));
      var1.setline(23);
      PyString.fromInterned("Open an arbitrary URL.\n\nSee the following document for more info on URLs:\n\"Names and Addresses, URIs, URLs, URNs, URCs\", at\nhttp://www.w3.org/pub/WWW/Addressing/Overview.html\n\nSee also the HTTP spec (from which the error codes are derived):\n\"HTTP - Hypertext Transfer Protocol\", at\nhttp://www.w3.org/pub/WWW/Protocols/\n\nRelated standards and specs:\n- RFC1808: the \"relative URL\" spec. (authoritative status)\n- RFC1738 - the \"URL standard\". (authoritative status)\n- RFC1630 - the \"URI spec\". (informational status)\n\nThe object returned by URLopener().open(file) will differ per\nprotocol.  All you know is that is has methods read(), readline(),\nreadlines(), fileno(), close() and info().  The read*(), fileno()\nand close() methods work like those of open files.\nThe info() method returns a mimetools.Message object which can be\nused to query various info about the object, if available.\n(mimetools.Message objects are queried with the getheader() method.)\n");
      var1.setline(25);
      PyObject var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var1.setline(26);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(27);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(28);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(29);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(30);
      var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;
      var1.setline(31);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(33);
      String[] var10 = new String[]{"urljoin"};
      PyObject[] var11 = imp.importFrom("urlparse", var10, var1, -1);
      PyObject var4 = var11[0];
      var1.setlocal("basejoin", var4);
      var4 = null;
      var1.setline(35);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("urlopen"), PyString.fromInterned("URLopener"), PyString.fromInterned("FancyURLopener"), PyString.fromInterned("urlretrieve"), PyString.fromInterned("urlcleanup"), PyString.fromInterned("quote"), PyString.fromInterned("quote_plus"), PyString.fromInterned("unquote"), PyString.fromInterned("unquote_plus"), PyString.fromInterned("urlencode"), PyString.fromInterned("url2pathname"), PyString.fromInterned("pathname2url"), PyString.fromInterned("splittag"), PyString.fromInterned("localhost"), PyString.fromInterned("thishost"), PyString.fromInterned("ftperrors"), PyString.fromInterned("basejoin"), PyString.fromInterned("unwrap"), PyString.fromInterned("splittype"), PyString.fromInterned("splithost"), PyString.fromInterned("splituser"), PyString.fromInterned("splitpasswd"), PyString.fromInterned("splitport"), PyString.fromInterned("splitnport"), PyString.fromInterned("splitquery"), PyString.fromInterned("splitattr"), PyString.fromInterned("splitvalue"), PyString.fromInterned("getproxies")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(43);
      PyString var13 = PyString.fromInterned("1.17");
      var1.setlocal("__version__", var13);
      var3 = null;
      var1.setline(45);
      PyInteger var14 = Py.newInteger(10);
      var1.setlocal("MAXFTPCACHE", var14);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getname("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("java"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getname("os").__getattr__("_name");
            var10000 = var3._eq(PyString.fromInterned("nt"));
            var3 = null;
         }
      }

      var3 = var10000;
      var1.setlocal("WINDOWS", var3);
      var3 = null;
      var1.setline(52);
      PyFunction var17;
      if (var1.getname("WINDOWS").__nonzero__()) {
         var1.setline(53);
         var10 = new String[]{"url2pathname", "pathname2url"};
         var11 = imp.importFrom("nturl2path", var10, var1, -1);
         var4 = var11[0];
         var1.setlocal("url2pathname", var4);
         var4 = null;
         var4 = var11[1];
         var1.setlocal("pathname2url", var4);
         var4 = null;
      } else {
         var1.setline(54);
         var3 = var1.getname("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("riscos"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(55);
            var10 = new String[]{"url2pathname", "pathname2url"};
            var11 = imp.importFrom("rourl2path", var10, var1, -1);
            var4 = var11[0];
            var1.setlocal("url2pathname", var4);
            var4 = null;
            var4 = var11[1];
            var1.setlocal("pathname2url", var4);
            var4 = null;
         } else {
            var1.setline(57);
            var11 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var11, url2pathname$1, PyString.fromInterned("OS-specific conversion from a relative URL of the 'file' scheme\n        to a file system path; not recommended for general use."));
            var1.setlocal("url2pathname", var17);
            var3 = null;
            var1.setline(62);
            var11 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var11, pathname2url$2, PyString.fromInterned("OS-specific conversion from a file system path to a relative URL\n        of the 'file' scheme; not recommended for general use."));
            var1.setlocal("pathname2url", var17);
            var3 = null;
         }
      }

      var1.setline(75);
      var3 = var1.getname("None");
      var1.setlocal("_urlopener", var3);
      var3 = null;
      var1.setline(76);
      var11 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var17 = new PyFunction(var1.f_globals, var11, urlopen$3, PyString.fromInterned("Create a file-like object for the specified URL to read from."));
      var1.setlocal("urlopen", var17);
      var3 = null;
      var1.setline(94);
      var11 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var17 = new PyFunction(var1.f_globals, var11, urlretrieve$4, (PyObject)null);
      var1.setlocal("urlretrieve", var17);
      var3 = null;
      var1.setline(103);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, urlcleanup$5, (PyObject)null);
      var1.setlocal("urlcleanup", var17);
      var3 = null;

      label67: {
         try {
            var1.setline(111);
            var3 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var3);
            var3 = null;
         } catch (Throwable var8) {
            Py.setException(var8, var1);
            var1.setline(113);
            var4 = var1.getname("False");
            var1.setlocal("_have_ssl", var4);
            var4 = null;
            break label67;
         }

         var1.setline(115);
         var4 = var1.getname("True");
         var1.setlocal("_have_ssl", var4);
         var4 = null;
      }

      var1.setline(118);
      var11 = new PyObject[]{var1.getname("IOError")};
      var4 = Py.makeClass("ContentTooShortError", var11, ContentTooShortError$6);
      var1.setlocal("ContentTooShortError", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(123);
      PyDictionary var18 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("ftpcache", var18);
      var3 = null;
      var1.setline(124);
      var11 = Py.EmptyObjects;
      var4 = Py.makeClass("URLopener", var11, URLopener$8);
      var1.setlocal("URLopener", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(620);
      var11 = new PyObject[]{var1.getname("URLopener")};
      var4 = Py.makeClass("FancyURLopener", var11, FancyURLopener$26);
      var1.setlocal("FancyURLopener", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(821);
      var3 = var1.getname("None");
      var1.setlocal("_localhost", var3);
      var3 = null;
      var1.setline(822);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, localhost$42, PyString.fromInterned("Return the IP address of the magic hostname 'localhost'."));
      var1.setlocal("localhost", var17);
      var3 = null;
      var1.setline(829);
      var3 = var1.getname("None");
      var1.setlocal("_thishost", var3);
      var3 = null;
      var1.setline(830);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, thishost$43, PyString.fromInterned("Return the IP address of the current host."));
      var1.setlocal("thishost", var17);
      var3 = null;
      var1.setline(840);
      var3 = var1.getname("None");
      var1.setlocal("_ftperrors", var3);
      var3 = null;
      var1.setline(841);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, ftperrors$44, PyString.fromInterned("Return the set of errors raised by the FTP class."));
      var1.setlocal("ftperrors", var17);
      var3 = null;
      var1.setline(849);
      var3 = var1.getname("None");
      var1.setlocal("_noheaders", var3);
      var3 = null;
      var1.setline(850);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, noheaders$45, PyString.fromInterned("Return an empty mimetools.Message object."));
      var1.setlocal("noheaders", var17);
      var3 = null;
      var1.setline(866);
      var11 = Py.EmptyObjects;
      var4 = Py.makeClass("ftpwrapper", var11, ftpwrapper$46);
      var1.setlocal("ftpwrapper", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(959);
      var11 = Py.EmptyObjects;
      var4 = Py.makeClass("addbase", var11, addbase$54);
      var1.setlocal("addbase", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(988);
      var11 = new PyObject[]{var1.getname("addbase")};
      var4 = Py.makeClass("addclosehook", var11, addclosehook$59);
      var1.setlocal("addclosehook", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1008);
      var11 = new PyObject[]{var1.getname("addbase")};
      var4 = Py.makeClass("addinfo", var11, addinfo$62);
      var1.setlocal("addinfo", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1018);
      var11 = new PyObject[]{var1.getname("addbase")};
      var4 = Py.makeClass("addinfourl", var11, addinfourl$65);
      var1.setlocal("addinfourl", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);

      PyFunction var16;
      label62: {
         PyObject[] var15;
         try {
            var1.setline(1053);
            var1.getname("unicode");
         } catch (Throwable var7) {
            PyException var19 = Py.setException(var7, var1);
            if (var19.match(var1.getname("NameError"))) {
               var1.setline(1055);
               var15 = Py.EmptyObjects;
               var16 = new PyFunction(var1.f_globals, var15, _is_unicode$70, (PyObject)null);
               var1.setlocal("_is_unicode", var16);
               var4 = null;
               break label62;
            }

            throw var19;
         }

         var1.setline(1058);
         var15 = Py.EmptyObjects;
         var16 = new PyFunction(var1.f_globals, var15, _is_unicode$71, (PyObject)null);
         var1.setlocal("_is_unicode", var16);
         var4 = null;
      }

      var1.setline(1061);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, toBytes$72, PyString.fromInterned("toBytes(u\"URL\") --> 'URL'."));
      var1.setlocal("toBytes", var17);
      var3 = null;
      var1.setline(1073);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, unwrap$73, PyString.fromInterned("unwrap('<URL:type://host/path>') --> 'type://host/path'."));
      var1.setlocal("unwrap", var17);
      var3 = null;
      var1.setline(1081);
      var3 = var1.getname("None");
      var1.setlocal("_typeprog", var3);
      var3 = null;
      var1.setline(1082);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splittype$74, PyString.fromInterned("splittype('type:opaquestring') --> 'type', 'opaquestring'."));
      var1.setlocal("splittype", var17);
      var3 = null;
      var1.setline(1095);
      var3 = var1.getname("None");
      var1.setlocal("_hostprog", var3);
      var3 = null;
      var1.setline(1096);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splithost$75, PyString.fromInterned("splithost('//host[:port]/path') --> 'host[:port]', '/path'."));
      var1.setlocal("splithost", var17);
      var3 = null;
      var1.setline(1112);
      var3 = var1.getname("None");
      var1.setlocal("_userprog", var3);
      var3 = null;
      var1.setline(1113);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splituser$76, PyString.fromInterned("splituser('user[:passwd]@host[:port]') --> 'user[:passwd]', 'host[:port]'."));
      var1.setlocal("splituser", var17);
      var3 = null;
      var1.setline(1124);
      var3 = var1.getname("None");
      var1.setlocal("_passwdprog", var3);
      var3 = null;
      var1.setline(1125);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splitpasswd$77, PyString.fromInterned("splitpasswd('user:passwd') -> 'user', 'passwd'."));
      var1.setlocal("splitpasswd", var17);
      var3 = null;
      var1.setline(1137);
      var3 = var1.getname("None");
      var1.setlocal("_portprog", var3);
      var3 = null;
      var1.setline(1138);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splitport$78, PyString.fromInterned("splitport('host:port') --> 'host', 'port'."));
      var1.setlocal("splitport", var17);
      var3 = null;
      var1.setline(1152);
      var3 = var1.getname("None");
      var1.setlocal("_nportprog", var3);
      var3 = null;
      var1.setline(1153);
      var11 = new PyObject[]{Py.newInteger(-1)};
      var17 = new PyFunction(var1.f_globals, var11, splitnport$79, PyString.fromInterned("Split host and port, returning numeric port.\n    Return given default port if no ':' found; defaults to -1.\n    Return numerical port if a valid number are found after ':'.\n    Return None if ':' but not a valid number."));
      var1.setlocal("splitnport", var17);
      var3 = null;
      var1.setline(1174);
      var3 = var1.getname("None");
      var1.setlocal("_queryprog", var3);
      var3 = null;
      var1.setline(1175);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splitquery$80, PyString.fromInterned("splitquery('/path?query') --> '/path', 'query'."));
      var1.setlocal("splitquery", var17);
      var3 = null;
      var1.setline(1186);
      var3 = var1.getname("None");
      var1.setlocal("_tagprog", var3);
      var3 = null;
      var1.setline(1187);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splittag$81, PyString.fromInterned("splittag('/path#tag') --> '/path', 'tag'."));
      var1.setlocal("splittag", var17);
      var3 = null;
      var1.setline(1198);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splitattr$82, PyString.fromInterned("splitattr('/path;attr1=value1;attr2=value2;...') ->\n        '/path', ['attr1=value1', 'attr2=value2', ...]."));
      var1.setlocal("splitattr", var17);
      var3 = null;
      var1.setline(1204);
      var3 = var1.getname("None");
      var1.setlocal("_valueprog", var3);
      var3 = null;
      var1.setline(1205);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, splitvalue$83, PyString.fromInterned("splitvalue('attr=value') --> 'attr', 'value'."));
      var1.setlocal("splitvalue", var17);
      var3 = null;
      var1.setline(1220);
      var13 = PyString.fromInterned("0123456789ABCDEFabcdef");
      var1.setlocal("_hexdig", var13);
      var3 = null;
      var1.setline(1221);
      var10000 = var1.getname("dict");
      var1.setline(1221);
      var11 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var11, f$84, (PyObject)null);
      PyObject var10002 = var16.__call__(var2, var1.getname("_hexdig").__iter__());
      Arrays.fill(var11, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal("_hextochr", var3);
      var3 = null;
      var1.setline(1223);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\u0000-\u007f]+)"));
      var1.setlocal("_asciire", var3);
      var3 = null;
      var1.setline(1225);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, unquote$85, PyString.fromInterned("unquote('abc%20def') -> 'abc def'."));
      var1.setlocal("unquote", var17);
      var3 = null;
      var1.setline(1253);
      var11 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var11, unquote_plus$86, PyString.fromInterned("unquote('%7e/abc+def') -> '~/abc def'"));
      var1.setlocal("unquote_plus", var17);
      var3 = null;
      var1.setline(1258);
      var13 = PyString.fromInterned("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.-");
      var1.setlocal("always_safe", var13);
      var3 = null;
      var1.setline(1261);
      var18 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_safe_map", var18);
      var3 = null;
      var1.setline(1262);
      var3 = var1.getname("zip").__call__(var2, var1.getname("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)), var1.getname("str").__call__(var2, var1.getname("bytearray").__call__(var2, var1.getname("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256))))).__iter__();

      while(true) {
         var1.setline(1262);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1264);
            var18 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal("_safe_quoters", var18);
            var3 = null;
            var1.setline(1266);
            var11 = new PyObject[]{PyString.fromInterned("/")};
            var17 = new PyFunction(var1.f_globals, var11, quote$87, PyString.fromInterned("quote('abc def') -> 'abc%20def'\n\n    Each part of a URL, e.g. the path info, the query, etc., has a\n    different set of reserved characters that must be quoted.\n\n    RFC 2396 Uniform Resource Identifiers (URI): Generic Syntax lists\n    the following reserved characters.\n\n    reserved    = \";\" | \"/\" | \"?\" | \":\" | \"@\" | \"&\" | \"=\" | \"+\" |\n                  \"$\" | \",\"\n\n    Each of these characters is reserved in some component of a URL,\n    but not necessarily in all of them.\n\n    By default, the quote function is intended for quoting the path\n    section of a URL.  Thus, it will not encode '/'.  This character\n    is reserved, but in typical usage the quote function is being\n    called on a path where the existing slash characters are used as\n    reserved characters.\n    "));
            var1.setlocal("quote", var17);
            var3 = null;
            var1.setline(1305);
            var11 = new PyObject[]{PyString.fromInterned("")};
            var17 = new PyFunction(var1.f_globals, var11, quote_plus$88, PyString.fromInterned("Quote the query fragment of a URL; replacing ' ' with '+'"));
            var1.setlocal("quote_plus", var17);
            var3 = null;
            var1.setline(1312);
            var11 = new PyObject[]{Py.newInteger(0)};
            var17 = new PyFunction(var1.f_globals, var11, urlencode$89, PyString.fromInterned("Encode a sequence of two-element tuples or dictionary into a URL query string.\n\n    If any values in the query arg are sequences and doseq is true, each\n    sequence element is converted to a separate parameter.\n\n    If the query arg is a sequence of two-element tuples, the order of the\n    parameters in the output will match the order of parameters in the\n    input.\n    "));
            var1.setlocal("urlencode", var17);
            var3 = null;
            var1.setline(1376);
            var11 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var11, getproxies_environment$90, PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n    Scan the environment for variables named <scheme>_proxy;\n    this seems to be the standard convention.  In order to prefer lowercase\n    variables, we process the environment in two passes, first matches any\n    and second matches only lower case proxies.\n\n    If you need a different way, you can pass a proxies dictionary to the\n    [Fancy]URLopener constructor.\n    "));
            var1.setlocal("getproxies_environment", var17);
            var3 = null;
            var1.setline(1412);
            var11 = new PyObject[]{var1.getname("None")};
            var17 = new PyFunction(var1.f_globals, var11, proxy_bypass_environment$91, PyString.fromInterned("Test if proxies should not be used for a particular host.\n\n    Checks the proxies dict for the value of no_proxy, which should be a\n    list of comma separated DNS suffixes, or '*' for all hosts.\n    "));
            var1.setlocal("proxy_bypass_environment", var17);
            var3 = null;
            var1.setline(1443);
            var3 = var1.getname("sys").__getattr__("platform");
            var10000 = var3._eq(PyString.fromInterned("darwin"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1444);
               var10 = new String[]{"_get_proxy_settings", "_get_proxies"};
               var11 = imp.importFrom("_scproxy", var10, var1, -1);
               var4 = var11[0];
               var1.setlocal("_get_proxy_settings", var4);
               var4 = null;
               var4 = var11[1];
               var1.setlocal("_get_proxies", var4);
               var4 = null;
               var1.setline(1446);
               var11 = Py.EmptyObjects;
               var17 = new PyFunction(var1.f_globals, var11, proxy_bypass_macosx_sysconf$92, PyString.fromInterned("\n        Return True iff this host shouldn't be accessed using a proxy\n\n        This function uses the MacOSX framework SystemConfiguration\n        to fetch the proxy information.\n        "));
               var1.setlocal("proxy_bypass_macosx_sysconf", var17);
               var3 = null;
               var1.setline(1505);
               var11 = Py.EmptyObjects;
               var17 = new PyFunction(var1.f_globals, var11, getproxies_macosx_sysconf$94, PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n        This function uses the MacOSX framework SystemConfiguration\n        to fetch the proxy information.\n        "));
               var1.setlocal("getproxies_macosx_sysconf", var17);
               var3 = null;
               var1.setline(1513);
               var11 = Py.EmptyObjects;
               var17 = new PyFunction(var1.f_globals, var11, proxy_bypass$95, PyString.fromInterned("Return True, if a host should be bypassed.\n\n        Checks proxy settings gathered from the environment, if specified, or\n        from the MacOSX framework SystemConfiguration.\n        "));
               var1.setlocal("proxy_bypass", var17);
               var3 = null;
               var1.setline(1525);
               var11 = Py.EmptyObjects;
               var17 = new PyFunction(var1.f_globals, var11, getproxies$96, (PyObject)null);
               var1.setlocal("getproxies", var17);
               var3 = null;
            } else {
               var1.setline(1528);
               var3 = var1.getname("os").__getattr__("name");
               var10000 = var3._eq(PyString.fromInterned("nt"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1529);
                  var11 = Py.EmptyObjects;
                  var17 = new PyFunction(var1.f_globals, var11, getproxies_registry$97, PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n        Win32 uses the registry to store proxies.\n\n        "));
                  var1.setlocal("getproxies_registry", var17);
                  var3 = null;
                  var1.setline(1575);
                  var11 = Py.EmptyObjects;
                  var17 = new PyFunction(var1.f_globals, var11, getproxies$98, PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n        Returns settings gathered from the environment, if specified,\n        or the registry.\n\n        "));
                  var1.setlocal("getproxies", var17);
                  var3 = null;
                  var1.setline(1584);
                  var11 = Py.EmptyObjects;
                  var17 = new PyFunction(var1.f_globals, var11, proxy_bypass_registry$99, (PyObject)null);
                  var1.setlocal("proxy_bypass_registry", var17);
                  var3 = null;
                  var1.setline(1636);
                  var11 = Py.EmptyObjects;
                  var17 = new PyFunction(var1.f_globals, var11, proxy_bypass$100, PyString.fromInterned("Return True, if the host should be bypassed.\n\n        Checks proxy settings gathered from the environment, if specified,\n        or the registry.\n        "));
                  var1.setlocal("proxy_bypass", var17);
                  var3 = null;
               } else {
                  var1.setline(1650);
                  var3 = var1.getname("getproxies_environment");
                  var1.setlocal("getproxies", var3);
                  var3 = null;
                  var1.setline(1651);
                  var3 = var1.getname("proxy_bypass_environment");
                  var1.setlocal("proxy_bypass", var3);
                  var3 = null;
               }
            }

            var1.setline(1654);
            var11 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var11, test1$101, (PyObject)null);
            var1.setlocal("test1", var17);
            var3 = null;
            var1.setline(1670);
            var11 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var11, reporthook$102, (PyObject)null);
            var1.setlocal("reporthook", var17);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal("i", var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal("c", var6);
         var6 = null;
         var1.setline(1263);
         var1.setline(1263);
         PyObject var9 = var1.getname("i");
         var10000 = var9._lt(Py.newInteger(128));
         var5 = null;
         if (var10000.__nonzero__()) {
            var9 = var1.getname("c");
            var10000 = var9._in(var1.getname("always_safe"));
            var5 = null;
         }

         var9 = var10000.__nonzero__() ? var1.getname("c") : PyString.fromInterned("%{:02X}").__getattr__("format").__call__(var2, var1.getname("i"));
         var1.getname("_safe_map").__setitem__(var1.getname("c"), var9);
         var5 = null;
      }
   }

   public PyObject url2pathname$1(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("OS-specific conversion from a relative URL of the 'file' scheme\n        to a file system path; not recommended for general use.");
      var1.setline(60);
      PyObject var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pathname2url$2(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString.fromInterned("OS-specific conversion from a file system path to a relative URL\n        of the 'file' scheme; not recommended for general use.");
      var1.setline(65);
      PyObject var3 = var1.getglobal("quote").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urlopen$3(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Create a file-like object for the specified URL to read from.");
      var1.setline(78);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(79);
      PyObject var10000 = var1.getlocal(4);
      var5 = new PyObject[]{PyString.fromInterned("urllib.urlopen() has been removed in Python 3.0 in favor of urllib2.urlopen()"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(83);
      PyObject var6 = var1.getlocal(2);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var6 = var1.getlocal(3);
         var10000 = var6._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(84);
         var10000 = var1.getglobal("FancyURLopener");
         var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
         var7 = new String[]{"proxies", "context"};
         var10000 = var10000.__call__(var2, var5, var7);
         var3 = null;
         var6 = var10000;
         var1.setlocal(5, var6);
         var3 = null;
      } else {
         var1.setline(85);
         if (var1.getglobal("_urlopener").__not__().__nonzero__()) {
            var1.setline(86);
            var6 = var1.getglobal("FancyURLopener").__call__(var2);
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(87);
            var6 = var1.getlocal(5);
            var1.setglobal("_urlopener", var6);
            var3 = null;
         } else {
            var1.setline(89);
            var6 = var1.getglobal("_urlopener");
            var1.setlocal(5, var6);
            var3 = null;
         }
      }

      var1.setline(90);
      var6 = var1.getlocal(1);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         var6 = var1.getlocal(5).__getattr__("open").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(93);
         var6 = var1.getlocal(5).__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject urlretrieve$4(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(97);
         var10000 = var1.getglobal("FancyURLopener");
         PyObject[] var5 = new PyObject[]{var1.getlocal(4)};
         String[] var4 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(98);
         if (var1.getglobal("_urlopener").__not__().__nonzero__()) {
            var1.setline(99);
            var3 = var1.getglobal("FancyURLopener").__call__(var2);
            var1.setglobal("_urlopener", var3);
            var1.setlocal(5, var3);
         } else {
            var1.setline(101);
            var3 = var1.getglobal("_urlopener");
            var1.setlocal(5, var3);
            var3 = null;
         }
      }

      var1.setline(102);
      var3 = var1.getlocal(5).__getattr__("retrieve").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urlcleanup$5(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      if (var1.getglobal("_urlopener").__nonzero__()) {
         var1.setline(105);
         var1.getglobal("_urlopener").__getattr__("cleanup").__call__(var2);
      }

      var1.setline(106);
      var1.getglobal("_safe_quoters").__getattr__("clear").__call__(var2);
      var1.setline(107);
      var1.getglobal("ftpcache").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ContentTooShortError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(119);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      var1.getglobal("IOError").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(121);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("content", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject URLopener$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to open URLs.\n    This is a class rather than just a subroutine because we may need\n    more than one set of global protocol-specific options.\n    Note -- this is a base class for those who don't want the\n    automatic handling of errors type 302 (relocated) and 401\n    (authorization needed)."));
      var1.setline(130);
      PyString.fromInterned("Class to open URLs.\n    This is a class rather than just a subroutine because we may need\n    more than one set of global protocol-specific options.\n    Note -- this is a base class for those who don't want the\n    automatic handling of errors type 302 (relocated) and 401\n    (authorization needed).");
      var1.setline(132);
      PyObject var3 = var1.getname("None");
      var1.setlocal("_URLopener__tempfiles", var3);
      var3 = null;
      var1.setline(134);
      var3 = PyString.fromInterned("Python-urllib/%s")._mod(var1.getname("__version__"));
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(137);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(161);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __del__$10, (PyObject)null);
      var1.setlocal("__del__", var5);
      var3 = null;
      var1.setline(164);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$11, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(167);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, cleanup$12, (PyObject)null);
      var1.setlocal("cleanup", var5);
      var3 = null;
      var1.setline(181);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addheader$13, PyString.fromInterned("Add a header to be used by the HTTP interface only\n        e.g. u.addheader('Accept', 'sound/basic')"));
      var1.setlocal("addheader", var5);
      var3 = null;
      var1.setline(187);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, open$14, PyString.fromInterned("Use URLopener().open(file) instead of open(file, 'r')."));
      var1.setlocal("open", var5);
      var3 = null;
      var1.setline(223);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, open_unknown$15, PyString.fromInterned("Overridable interface to open unknown URL type."));
      var1.setlocal("open_unknown", var5);
      var3 = null;
      var1.setline(228);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, open_unknown_proxy$16, PyString.fromInterned("Overridable interface to open unknown URL type."));
      var1.setlocal("open_unknown_proxy", var5);
      var3 = null;
      var1.setline(234);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, retrieve$17, PyString.fromInterned("retrieve(url) returns (filename, headers) for a local object\n        or (tempfilename, headers) for a remote object."));
      var1.setlocal("retrieve", var5);
      var3 = null;
      var1.setline(299);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, open_http$18, PyString.fromInterned("Use HTTP protocol."));
      var1.setlocal("open_http", var5);
      var3 = null;
      var1.setline(372);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, http_error$19, PyString.fromInterned("Handle http errors.\n        Derived class can override this, or provide specific handlers\n        named http_error_DDD where DDD is the 3-digit error code."));
      var1.setlocal("http_error", var5);
      var3 = null;
      var1.setline(387);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, http_error_default$20, PyString.fromInterned("Default error handler: close the connection and raise IOError."));
      var1.setlocal("http_error_default", var5);
      var3 = null;
      var1.setline(392);
      if (var1.getname("_have_ssl").__nonzero__()) {
         var1.setline(393);
         var4 = new PyObject[]{var1.getname("None")};
         var5 = new PyFunction(var1.f_globals, var4, open_https$21, PyString.fromInterned("Use HTTPS protocol."));
         var1.setlocal("open_https", var5);
         var3 = null;
      }

      var1.setline(466);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, open_file$22, PyString.fromInterned("Use local file or FTP depending on form of URL."));
      var1.setlocal("open_file", var5);
      var3 = null;
      var1.setline(475);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, open_local_file$23, PyString.fromInterned("Use local file."));
      var1.setlocal("open_local_file", var5);
      var3 = null;
      var1.setline(512);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, open_ftp$24, PyString.fromInterned("Use FTP protocol."));
      var1.setlocal("open_ftp", var5);
      var3 = null;
      var1.setline(574);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, open_data$25, PyString.fromInterned("Use \"data\" URL."));
      var1.setlocal("open_data", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(139);
         var3 = var1.getglobal("getproxies").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(140);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("has_key")).__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("proxies must be a mapping"));
      } else {
         var1.setline(141);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("proxies", var3);
         var3 = null;
         var1.setline(142);
         var3 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("key_file"));
         var1.getlocal(0).__setattr__("key_file", var3);
         var3 = null;
         var1.setline(143);
         var3 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cert_file"));
         var1.getlocal(0).__setattr__("cert_file", var3);
         var3 = null;
         var1.setline(144);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("context", var3);
         var3 = null;
         var1.setline(145);
         PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("User-Agent"), var1.getlocal(0).__getattr__("version")}), new PyTuple(new PyObject[]{PyString.fromInterned("Accept"), PyString.fromInterned("*/*")})});
         var1.getlocal(0).__setattr__((String)"addheaders", var4);
         var3 = null;
         var1.setline(146);
         var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_URLopener__tempfiles", var4);
         var3 = null;
         var1.setline(147);
         var3 = var1.getglobal("os").__getattr__("unlink");
         var1.getlocal(0).__setattr__("_URLopener__unlink", var3);
         var3 = null;
         var1.setline(148);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("tempcache", var3);
         var3 = null;
         var1.setline(155);
         var3 = var1.getglobal("ftpcache");
         var1.getlocal(0).__setattr__("ftpcache", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __del__$10(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$11(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      var1.getlocal(0).__getattr__("cleanup").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup$12(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      if (var1.getlocal(0).__getattr__("_URLopener__tempfiles").__nonzero__()) {
         var1.setline(172);
         PyObject var3 = var1.getlocal(0).__getattr__("_URLopener__tempfiles").__iter__();

         while(true) {
            var1.setline(172);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(177);
               var1.getlocal(0).__getattr__("_URLopener__tempfiles").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
               break;
            }

            var1.setlocal(1, var4);

            try {
               var1.setline(174);
               var1.getlocal(0).__getattr__("_URLopener__unlink").__call__(var2, var1.getlocal(1));
            } catch (Throwable var6) {
               PyException var5 = Py.setException(var6, var1);
               if (!var5.match(var1.getglobal("OSError"))) {
                  throw var5;
               }

               var1.setline(176);
            }
         }
      }

      var1.setline(178);
      if (var1.getlocal(0).__getattr__("tempcache").__nonzero__()) {
         var1.setline(179);
         var1.getlocal(0).__getattr__("tempcache").__getattr__("clear").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addheader$13(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyString.fromInterned("Add a header to be used by the HTTP interface only\n        e.g. u.addheader('Accept', 'sound/basic')");
      var1.setline(184);
      var1.getlocal(0).__getattr__("addheaders").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$14(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString.fromInterned("Use URLopener().open(file) instead of open(file, 'r').");
      var1.setline(189);
      PyObject var3 = var1.getglobal("unwrap").__call__(var2, var1.getglobal("toBytes").__call__(var2, var1.getlocal(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(192);
      PyObject var10000 = var1.getglobal("quote");
      PyObject[] var8 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("%/:=&?~#+!$,;'@()*[]|")};
      String[] var4 = new String[]{"safe"};
      var10000 = var10000.__call__(var2, var8, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(193);
      var10000 = var1.getlocal(0).__getattr__("tempcache");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("tempcache"));
         var3 = null;
      }

      PyObject[] var5;
      PyObject var11;
      if (var10000.__nonzero__()) {
         var1.setline(194);
         var3 = var1.getlocal(0).__getattr__("tempcache").__getitem__(var1.getlocal(1));
         PyObject[] var14 = Py.unpackSequence(var3, 2);
         var11 = var14[0];
         var1.setlocal(3, var11);
         var5 = null;
         var11 = var14[1];
         var1.setlocal(4, var11);
         var5 = null;
         var3 = null;
         var1.setline(195);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(196);
         var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(5), var1.getlocal(4), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(197);
         PyObject var9 = var1.getglobal("splittype").__call__(var2, var1.getlocal(1));
         var5 = Py.unpackSequence(var9, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var4 = null;
         var1.setline(198);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            var1.setline(199);
            PyString var10 = PyString.fromInterned("file");
            var1.setlocal(6, var10);
            var4 = null;
         }

         var1.setline(200);
         var9 = var1.getlocal(6);
         var10000 = var9._in(var1.getlocal(0).__getattr__("proxies"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(201);
            var9 = var1.getlocal(0).__getattr__("proxies").__getitem__(var1.getlocal(6));
            var1.setlocal(8, var9);
            var4 = null;
            var1.setline(202);
            var9 = var1.getglobal("splittype").__call__(var2, var1.getlocal(8));
            var5 = Py.unpackSequence(var9, 2);
            var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(9, var6);
            var6 = null;
            var4 = null;
            var1.setline(203);
            var9 = var1.getglobal("splithost").__call__(var2, var1.getlocal(9));
            var5 = Py.unpackSequence(var9, 2);
            var6 = var5[0];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(11, var6);
            var6 = null;
            var4 = null;
            var1.setline(204);
            PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(1)});
            var1.setlocal(7, var12);
            var4 = null;
         } else {
            var1.setline(206);
            var9 = var1.getglobal("None");
            var1.setlocal(8, var9);
            var4 = null;
         }

         var1.setline(207);
         var9 = PyString.fromInterned("open_")._add(var1.getlocal(6));
         var1.setlocal(12, var9);
         var4 = null;
         var1.setline(208);
         var9 = var1.getlocal(6);
         var1.getlocal(0).__setattr__("type", var9);
         var4 = null;
         var1.setline(209);
         var9 = var1.getlocal(12).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
         var1.setlocal(12, var9);
         var4 = null;
         var1.setline(210);
         if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__not__().__nonzero__()) {
            var1.setline(211);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(212);
               var3 = var1.getlocal(0).__getattr__("open_unknown_proxy").__call__(var2, var1.getlocal(8), var1.getlocal(1), var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(214);
               var3 = var1.getlocal(0).__getattr__("open_unknown").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            try {
               var1.setline(216);
               var9 = var1.getlocal(2);
               var10000 = var9._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(217);
                  var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__call__(var2, var1.getlocal(7));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(219);
                  var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__call__(var2, var1.getlocal(7), var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               }
            } catch (Throwable var7) {
               PyException var13 = Py.setException(var7, var1);
               if (var13.match(var1.getglobal("socket").__getattr__("error"))) {
                  var11 = var13.value;
                  var1.setlocal(13, var11);
                  var5 = null;
                  var1.setline(221);
                  throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("socket error"), var1.getlocal(13)}), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
               } else {
                  throw var13;
               }
            }
         }
      }
   }

   public PyObject open_unknown$15(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyString.fromInterned("Overridable interface to open unknown URL type.");
      var1.setline(225);
      PyObject var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(226);
      throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("url error"), PyString.fromInterned("unknown url type"), var1.getlocal(3)}));
   }

   public PyObject open_unknown_proxy$16(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyString.fromInterned("Overridable interface to open unknown URL type.");
      var1.setline(230);
      PyObject var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(231);
      throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("url error"), PyString.fromInterned("invalid proxy for %s")._mod(var1.getlocal(4)), var1.getlocal(1)}));
   }

   public PyObject retrieve$17(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      PyString.fromInterned("retrieve(url) returns (filename, headers) for a local object\n        or (tempfilename, headers) for a remote object.");
      var1.setline(237);
      PyObject var3 = var1.getglobal("unwrap").__call__(var2, var1.getglobal("toBytes").__call__(var2, var1.getlocal(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(238);
      PyObject var10000 = var1.getlocal(0).__getattr__("tempcache");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("tempcache"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(239);
         var3 = var1.getlocal(0).__getattr__("tempcache").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(240);
         PyObject var4 = var1.getglobal("splittype").__call__(var2, var1.getlocal(1));
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var4 = null;
         var1.setline(241);
         var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5).__not__();
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(5);
               var10000 = var4._eq(PyString.fromInterned("file"));
               var4 = null;
            }
         }

         if (var10000.__nonzero__()) {
            try {
               var1.setline(243);
               var4 = var1.getlocal(0).__getattr__("open_local_file").__call__(var2, var1.getlocal(6));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(244);
               var4 = var1.getlocal(7).__getattr__("info").__call__(var2);
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(245);
               var1.getlocal(7).__getattr__("close").__call__(var2);
               var1.setline(246);
               PyTuple var11 = new PyTuple(new PyObject[]{var1.getglobal("url2pathname").__call__(var2, var1.getglobal("splithost").__call__(var2, var1.getlocal(6)).__getitem__(Py.newInteger(1))), var1.getlocal(8)});
               var1.f_lasti = -1;
               return var11;
            } catch (Throwable var10) {
               PyException var13 = Py.setException(var10, var1);
               if (!var13.match(var1.getglobal("IOError"))) {
                  throw var13;
               }
            }

            var1.setline(248);
         }

         var1.setline(249);
         var4 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(1), var1.getlocal(4));
         var1.setlocal(7, var4);
         var4 = null;
         var4 = null;

         try {
            var1.setline(251);
            PyObject var12 = var1.getlocal(7).__getattr__("info").__call__(var2);
            var1.setlocal(9, var12);
            var5 = null;
            var1.setline(252);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(253);
               var12 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("wb"));
               var1.setlocal(10, var12);
               var5 = null;
            } else {
               var1.setline(255);
               var12 = imp.importOne("tempfile", var1, -1);
               var1.setlocal(11, var12);
               var5 = null;
               var1.setline(256);
               var12 = var1.getglobal("splittype").__call__(var2, var1.getlocal(1));
               PyObject[] var14 = Py.unpackSequence(var12, 2);
               PyObject var7 = var14[0];
               var1.setlocal(12, var7);
               var7 = null;
               var7 = var14[1];
               var1.setlocal(13, var7);
               var7 = null;
               var5 = null;
               var1.setline(257);
               var10000 = var1.getglobal("splithost");
               Object var10002 = var1.getlocal(13);
               if (!((PyObject)var10002).__nonzero__()) {
                  var10002 = PyString.fromInterned("");
               }

               var12 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var14 = Py.unpackSequence(var12, 2);
               var7 = var14[0];
               var1.setlocal(12, var7);
               var7 = null;
               var7 = var14[1];
               var1.setlocal(13, var7);
               var7 = null;
               var5 = null;
               var1.setline(258);
               var10000 = var1.getglobal("splitquery");
               var10002 = var1.getlocal(13);
               if (!((PyObject)var10002).__nonzero__()) {
                  var10002 = PyString.fromInterned("");
               }

               var12 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var14 = Py.unpackSequence(var12, 2);
               var7 = var14[0];
               var1.setlocal(13, var7);
               var7 = null;
               var7 = var14[1];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(259);
               var10000 = var1.getglobal("splitattr");
               var10002 = var1.getlocal(13);
               if (!((PyObject)var10002).__nonzero__()) {
                  var10002 = PyString.fromInterned("");
               }

               var12 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var14 = Py.unpackSequence(var12, 2);
               var7 = var14[0];
               var1.setlocal(13, var7);
               var7 = null;
               var7 = var14[1];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(260);
               var12 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(13)).__getitem__(Py.newInteger(1));
               var1.setlocal(14, var12);
               var5 = null;
               var1.setline(261);
               var12 = var1.getlocal(11).__getattr__("mkstemp").__call__(var2, var1.getlocal(14));
               var14 = Py.unpackSequence(var12, 2);
               var7 = var14[0];
               var1.setlocal(15, var7);
               var7 = null;
               var7 = var14[1];
               var1.setlocal(2, var7);
               var7 = null;
               var5 = null;
               var1.setline(262);
               var1.getlocal(0).__getattr__("_URLopener__tempfiles").__getattr__("append").__call__(var2, var1.getlocal(2));
               var1.setline(263);
               var12 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)PyString.fromInterned("wb"));
               var1.setlocal(10, var12);
               var5 = null;
            }

            var5 = null;

            try {
               var1.setline(265);
               PyTuple var15 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(9)});
               var1.setlocal(16, var15);
               var6 = null;
               var1.setline(266);
               var6 = var1.getlocal(0).__getattr__("tempcache");
               var10000 = var6._isnot(var1.getglobal("None"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(267);
                  var6 = var1.getlocal(16);
                  var1.getlocal(0).__getattr__("tempcache").__setitem__(var1.getlocal(1), var6);
                  var6 = null;
               }

               var1.setline(268);
               var6 = Py.newInteger(1024)._mul(Py.newInteger(8));
               var1.setlocal(17, var6);
               var6 = null;
               var1.setline(269);
               PyInteger var16 = Py.newInteger(-1);
               var1.setlocal(18, var16);
               var6 = null;
               var1.setline(270);
               var16 = Py.newInteger(0);
               var1.setlocal(19, var16);
               var6 = null;
               var1.setline(271);
               var16 = Py.newInteger(0);
               var1.setlocal(20, var16);
               var6 = null;
               var1.setline(272);
               PyString var17 = PyString.fromInterned("content-length");
               var10000 = var17._in(var1.getlocal(9));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(273);
                  var6 = var1.getglobal("int").__call__(var2, var1.getlocal(9).__getitem__(PyString.fromInterned("Content-Length")));
                  var1.setlocal(18, var6);
                  var6 = null;
               }

               var1.setline(274);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(275);
                  var1.getlocal(3).__call__(var2, var1.getlocal(20), var1.getlocal(17), var1.getlocal(18));
               }

               while(true) {
                  var1.setline(276);
                  if (!Py.newInteger(1).__nonzero__()) {
                     break;
                  }

                  var1.setline(277);
                  var6 = var1.getlocal(7).__getattr__("read").__call__(var2, var1.getlocal(17));
                  var1.setlocal(21, var6);
                  var6 = null;
                  var1.setline(278);
                  var6 = var1.getlocal(21);
                  var10000 = var6._eq(PyString.fromInterned(""));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(280);
                  var6 = var1.getlocal(19);
                  var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(21)));
                  var1.setlocal(19, var6);
                  var1.setline(281);
                  var1.getlocal(10).__getattr__("write").__call__(var2, var1.getlocal(21));
                  var1.setline(282);
                  var6 = var1.getlocal(20);
                  var6 = var6._iadd(Py.newInteger(1));
                  var1.setlocal(20, var6);
                  var1.setline(283);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(284);
                     var1.getlocal(3).__call__(var2, var1.getlocal(20), var1.getlocal(17), var1.getlocal(18));
                  }
               }
            } catch (Throwable var8) {
               Py.addTraceback(var8, var1);
               var1.setline(286);
               var1.getlocal(10).__getattr__("close").__call__(var2);
               throw (Throwable)var8;
            }

            var1.setline(286);
            var1.getlocal(10).__getattr__("close").__call__(var2);
         } catch (Throwable var9) {
            Py.addTraceback(var9, var1);
            var1.setline(288);
            var1.getlocal(7).__getattr__("close").__call__(var2);
            throw (Throwable)var9;
         }

         var1.setline(288);
         var1.getlocal(7).__getattr__("close").__call__(var2);
         var1.setline(291);
         var4 = var1.getlocal(18);
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(19);
            var10000 = var4._lt(var1.getlocal(18));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(292);
            throw Py.makeException(var1.getglobal("ContentTooShortError").__call__(var2, PyString.fromInterned("retrieval incomplete: got only %i out of %i bytes")._mod(new PyTuple(new PyObject[]{var1.getlocal(19), var1.getlocal(18)})), var1.getlocal(16)));
         } else {
            var1.setline(295);
            var3 = var1.getlocal(16);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject open_http$18(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyString.fromInterned("Use HTTP protocol.");
      var1.setline(301);
      PyObject var3 = imp.importOne("httplib", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(303);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(304);
      PyObject var10000;
      PyObject[] var4;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(305);
         var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(306);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(307);
            var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(6));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(308);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(309);
         var3 = var1.getlocal(6);
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(311);
         var3 = var1.getlocal(1);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(313);
         var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(6));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(315);
         var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(7));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
         var1.setline(316);
         var3 = var1.getlocal(10);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(317);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(318);
         var3 = var1.getlocal(9).__getattr__("lower").__call__(var2);
         var10000 = var3._ne(PyString.fromInterned("http"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(319);
            var3 = var1.getglobal("None");
            var1.setlocal(8, var3);
            var3 = null;
         } else {
            var1.setline(321);
            var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(10));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var3 = null;
            var1.setline(322);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(323);
               var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(8));
               var4 = Py.unpackSequence(var3, 2);
               var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(8, var5);
               var5 = null;
               var3 = null;
            }

            var1.setline(324);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(325);
               var3 = PyString.fromInterned("%s://%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(8), var1.getlocal(10)}));
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(326);
            if (var1.getglobal("proxy_bypass").__call__(var2, var1.getlocal(8)).__nonzero__()) {
               var1.setline(327);
               var3 = var1.getlocal(8);
               var1.setlocal(6, var3);
               var3 = null;
            }
         }
      }

      var1.setline(330);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(330);
         throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("http error"), PyString.fromInterned("no host given")}));
      } else {
         var1.setline(332);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(333);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(334);
            var3 = var1.getglobal("base64").__getattr__("b64encode").__call__(var2, var1.getlocal(5)).__getattr__("strip").__call__(var2);
            var1.setlocal(11, var3);
            var3 = null;
         } else {
            var1.setline(336);
            var3 = var1.getglobal("None");
            var1.setlocal(11, var3);
            var3 = null;
         }

         var1.setline(338);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(339);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(340);
            var3 = var1.getglobal("base64").__getattr__("b64encode").__call__(var2, var1.getlocal(4)).__getattr__("strip").__call__(var2);
            var1.setlocal(12, var3);
            var3 = null;
         } else {
            var1.setline(342);
            var3 = var1.getglobal("None");
            var1.setlocal(12, var3);
            var3 = null;
         }

         var1.setline(343);
         var3 = var1.getlocal(3).__getattr__("HTTP").__call__(var2, var1.getlocal(6));
         var1.setlocal(13, var3);
         var3 = null;
         var1.setline(344);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(345);
            var1.getlocal(13).__getattr__("putrequest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POST"), (PyObject)var1.getlocal(7));
            var1.setline(346);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type"), (PyObject)PyString.fromInterned("application/x-www-form-urlencoded"));
            var1.setline(347);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)PyString.fromInterned("%d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
         } else {
            var1.setline(349);
            var1.getlocal(13).__getattr__("putrequest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET"), (PyObject)var1.getlocal(7));
         }

         var1.setline(350);
         if (var1.getlocal(11).__nonzero__()) {
            var1.setline(350);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Proxy-Authorization"), (PyObject)PyString.fromInterned("Basic %s")._mod(var1.getlocal(11)));
         }

         var1.setline(351);
         if (var1.getlocal(12).__nonzero__()) {
            var1.setline(351);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Authorization"), (PyObject)PyString.fromInterned("Basic %s")._mod(var1.getlocal(12)));
         }

         var1.setline(352);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(352);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)var1.getlocal(8));
         }

         var1.setline(353);
         var3 = var1.getlocal(0).__getattr__("addheaders").__iter__();

         while(true) {
            var1.setline(353);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               var1.setline(354);
               var1.getlocal(13).__getattr__("endheaders").__call__(var2, var1.getlocal(2));
               var1.setline(355);
               var3 = var1.getlocal(13).__getattr__("getreply").__call__(var2);
               var4 = Py.unpackSequence(var3, 3);
               var5 = var4[0];
               var1.setlocal(15, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(16, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(17, var5);
               var5 = null;
               var3 = null;
               var1.setline(356);
               var3 = var1.getlocal(13).__getattr__("getfile").__call__(var2);
               var1.setlocal(18, var3);
               var3 = null;
               var1.setline(357);
               var3 = var1.getlocal(15);
               var10000 = var3._eq(Py.newInteger(-1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(358);
                  if (var1.getlocal(18).__nonzero__()) {
                     var1.setline(358);
                     var1.getlocal(18).__getattr__("close").__call__(var2);
                  }

                  var1.setline(360);
                  throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("http protocol error"), Py.newInteger(0), PyString.fromInterned("got a bad status line"), var1.getglobal("None")}));
               } else {
                  var1.setline(364);
                  PyInteger var9 = Py.newInteger(200);
                  PyObject var10001 = var1.getlocal(15);
                  PyInteger var10 = var9;
                  var3 = var10001;
                  if ((var7 = var10._le(var10001)).__nonzero__()) {
                     var7 = var3._lt(Py.newInteger(300));
                  }

                  var3 = null;
                  if (var7.__nonzero__()) {
                     var1.setline(365);
                     var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(18), var1.getlocal(17), PyString.fromInterned("http:")._add(var1.getlocal(1)), var1.getlocal(15));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(367);
                     var7 = var1.getlocal(2);
                     var10000 = var7._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(368);
                        var10000 = var1.getlocal(0).__getattr__("http_error");
                        var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(18), var1.getlocal(15), var1.getlocal(16), var1.getlocal(17)};
                        var3 = var10000.__call__(var2, var4);
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(370);
                        var10000 = var1.getlocal(0).__getattr__("http_error");
                        var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(18), var1.getlocal(15), var1.getlocal(16), var1.getlocal(17), var1.getlocal(2)};
                        var3 = var10000.__call__(var2, var4);
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }
               }
            }

            var1.setlocal(14, var7);
            var1.setline(353);
            var10000 = var1.getlocal(13).__getattr__("putheader");
            PyObject[] var8 = Py.EmptyObjects;
            String[] var6 = new String[0];
            var10000._callextra(var8, var6, var1.getlocal(14), (PyObject)null);
            var5 = null;
         }
      }
   }

   public PyObject http_error$19(PyFrame var1, ThreadState var2) {
      var1.setline(375);
      PyString.fromInterned("Handle http errors.\n        Derived class can override this, or provide specific handlers\n        named http_error_DDD where DDD is the 3-digit error code.");
      var1.setline(377);
      PyObject var3 = PyString.fromInterned("http_error_%d")._mod(var1.getlocal(3));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(378);
      PyObject var10000;
      if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(7)).__nonzero__()) {
         var1.setline(379);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(7));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(380);
         var3 = var1.getlocal(6);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyObject[] var5;
         if (var10000.__nonzero__()) {
            var1.setline(381);
            var10000 = var1.getlocal(8);
            var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
            var3 = var10000.__call__(var2, var5);
            var1.setlocal(9, var3);
            var3 = null;
         } else {
            var1.setline(383);
            var10000 = var1.getlocal(8);
            var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
            var3 = var10000.__call__(var2, var5);
            var1.setlocal(9, var3);
            var3 = null;
         }

         var1.setline(384);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(384);
            var3 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(385);
      var10000 = var1.getlocal(0).__getattr__("http_error_default");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject http_error_default$20(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      PyString.fromInterned("Default error handler: close the connection and raise IOError.");
      var1.setline(389);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(390);
      throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("http error"), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)}));
   }

   public PyObject open_https$21(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      PyString.fromInterned("Use HTTPS protocol.");
      var1.setline(396);
      PyObject var3 = imp.importOne("httplib", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(397);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(398);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(399);
      PyObject[] var4;
      PyObject var5;
      PyObject var10000;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(400);
         var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(401);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(402);
            var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(6));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(403);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(404);
         var3 = var1.getlocal(6);
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(406);
         var3 = var1.getlocal(1);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(408);
         var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(6));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(409);
         var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(7));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
         var1.setline(410);
         var3 = var1.getlocal(10);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(411);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(412);
         var3 = var1.getlocal(9).__getattr__("lower").__call__(var2);
         var10000 = var3._ne(PyString.fromInterned("https"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(413);
            var3 = var1.getglobal("None");
            var1.setlocal(8, var3);
            var3 = null;
         } else {
            var1.setline(415);
            var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(10));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var3 = null;
            var1.setline(416);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(417);
               var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(8));
               var4 = Py.unpackSequence(var3, 2);
               var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(8, var5);
               var5 = null;
               var3 = null;
            }

            var1.setline(418);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(419);
               var3 = PyString.fromInterned("%s://%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(8), var1.getlocal(10)}));
               var1.setlocal(7, var3);
               var3 = null;
            }
         }
      }

      var1.setline(421);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(421);
         throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("https error"), PyString.fromInterned("no host given")}));
      } else {
         var1.setline(422);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(423);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(424);
            var3 = var1.getglobal("base64").__getattr__("b64encode").__call__(var2, var1.getlocal(5)).__getattr__("strip").__call__(var2);
            var1.setlocal(11, var3);
            var3 = null;
         } else {
            var1.setline(426);
            var3 = var1.getglobal("None");
            var1.setlocal(11, var3);
            var3 = null;
         }

         var1.setline(427);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(428);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(429);
            var3 = var1.getglobal("base64").__getattr__("b64encode").__call__(var2, var1.getlocal(4)).__getattr__("strip").__call__(var2);
            var1.setlocal(12, var3);
            var3 = null;
         } else {
            var1.setline(431);
            var3 = var1.getglobal("None");
            var1.setlocal(12, var3);
            var3 = null;
         }

         var1.setline(432);
         var10000 = var1.getlocal(3).__getattr__("HTTPS");
         PyObject[] var10 = new PyObject[]{var1.getlocal(6), Py.newInteger(0), var1.getlocal(0).__getattr__("key_file"), var1.getlocal(0).__getattr__("cert_file"), var1.getlocal(0).__getattr__("context")};
         String[] var7 = new String[]{"key_file", "cert_file", "context"};
         var10000 = var10000.__call__(var2, var10, var7);
         var3 = null;
         var3 = var10000;
         var1.setlocal(13, var3);
         var3 = null;
         var1.setline(436);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(437);
            var1.getlocal(13).__getattr__("putrequest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POST"), (PyObject)var1.getlocal(7));
            var1.setline(438);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type"), (PyObject)PyString.fromInterned("application/x-www-form-urlencoded"));
            var1.setline(440);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)PyString.fromInterned("%d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
         } else {
            var1.setline(442);
            var1.getlocal(13).__getattr__("putrequest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET"), (PyObject)var1.getlocal(7));
         }

         var1.setline(443);
         if (var1.getlocal(11).__nonzero__()) {
            var1.setline(443);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Proxy-Authorization"), (PyObject)PyString.fromInterned("Basic %s")._mod(var1.getlocal(11)));
         }

         var1.setline(444);
         if (var1.getlocal(12).__nonzero__()) {
            var1.setline(444);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Authorization"), (PyObject)PyString.fromInterned("Basic %s")._mod(var1.getlocal(12)));
         }

         var1.setline(445);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(445);
            var1.getlocal(13).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)var1.getlocal(8));
         }

         var1.setline(446);
         var3 = var1.getlocal(0).__getattr__("addheaders").__iter__();

         while(true) {
            var1.setline(446);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               var1.setline(447);
               var1.getlocal(13).__getattr__("endheaders").__call__(var2, var1.getlocal(2));
               var1.setline(448);
               var3 = var1.getlocal(13).__getattr__("getreply").__call__(var2);
               var4 = Py.unpackSequence(var3, 3);
               var5 = var4[0];
               var1.setlocal(15, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(16, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(17, var5);
               var5 = null;
               var3 = null;
               var1.setline(449);
               var3 = var1.getlocal(13).__getattr__("getfile").__call__(var2);
               var1.setlocal(18, var3);
               var3 = null;
               var1.setline(450);
               var3 = var1.getlocal(15);
               var10000 = var3._eq(Py.newInteger(-1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(451);
                  if (var1.getlocal(18).__nonzero__()) {
                     var1.setline(451);
                     var1.getlocal(18).__getattr__("close").__call__(var2);
                  }

                  var1.setline(453);
                  throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("http protocol error"), Py.newInteger(0), PyString.fromInterned("got a bad status line"), var1.getglobal("None")}));
               } else {
                  var1.setline(457);
                  PyInteger var11 = Py.newInteger(200);
                  PyObject var10001 = var1.getlocal(15);
                  PyInteger var12 = var11;
                  var3 = var10001;
                  if ((var8 = var12._le(var10001)).__nonzero__()) {
                     var8 = var3._lt(Py.newInteger(300));
                  }

                  var3 = null;
                  if (var8.__nonzero__()) {
                     var1.setline(458);
                     var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(18), var1.getlocal(17), PyString.fromInterned("https:")._add(var1.getlocal(1)), var1.getlocal(15));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(460);
                     var8 = var1.getlocal(2);
                     var10000 = var8._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(461);
                        var10000 = var1.getlocal(0).__getattr__("http_error");
                        var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(18), var1.getlocal(15), var1.getlocal(16), var1.getlocal(17)};
                        var3 = var10000.__call__(var2, var4);
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(463);
                        var10000 = var1.getlocal(0).__getattr__("http_error");
                        var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(18), var1.getlocal(15), var1.getlocal(16), var1.getlocal(17), var1.getlocal(2)};
                        var3 = var10000.__call__(var2, var4);
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }
               }
            }

            var1.setlocal(14, var8);
            var1.setline(446);
            var10000 = var1.getlocal(13).__getattr__("putheader");
            PyObject[] var9 = Py.EmptyObjects;
            String[] var6 = new String[0];
            var10000._callextra(var9, var6, var1.getlocal(14), (PyObject)null);
            var5 = null;
         }
      }
   }

   public PyObject open_file$22(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyString.fromInterned("Use local file or FTP depending on form of URL.");
      var1.setline(468);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
         var1.setline(469);
         throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("file error"), PyString.fromInterned("proxy support for file protocol currently not implemented")}));
      } else {
         var1.setline(470);
         PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject var10000 = var3._eq(PyString.fromInterned("//"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(2), Py.newInteger(3), (PyObject)null);
            var10000 = var3._ne(PyString.fromInterned("/"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getslice__(Py.newInteger(2), Py.newInteger(12), (PyObject)null).__getattr__("lower").__call__(var2);
               var10000 = var3._ne(PyString.fromInterned("localhost/"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(471);
            var3 = var1.getlocal(0).__getattr__("open_ftp").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(473);
            var3 = var1.getlocal(0).__getattr__("open_local_file").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject open_local_file$23(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyString.fromInterned("Use local file.");
      var1.setline(477);
      PyObject var3 = imp.importOne("mimetypes", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var3 = imp.importOne("mimetools", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var3 = imp.importOne("email.utils", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;

      String[] var4;
      PyObject var5;
      PyObject[] var9;
      PyObject var10;
      PyException var11;
      PyObject[] var13;
      try {
         var1.setline(479);
         String[] var12 = new String[]{"StringIO"};
         var13 = imp.importFrom("cStringIO", var12, var1, -1);
         var10 = var13[0];
         var1.setlocal(5, var10);
         var4 = null;
      } catch (Throwable var8) {
         var11 = Py.setException(var8, var1);
         if (!var11.match(var1.getglobal("ImportError"))) {
            throw var11;
         }

         var1.setline(481);
         var4 = new String[]{"StringIO"};
         var9 = imp.importFrom("StringIO", var4, var1, -1);
         var5 = var9[0];
         var1.setlocal(5, var5);
         var5 = null;
      }

      var1.setline(482);
      var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
      var9 = Py.unpackSequence(var3, 2);
      var5 = var9[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var9[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(483);
      var3 = var1.getglobal("url2pathname").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;

      try {
         var1.setline(485);
         var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(8));
         var1.setlocal(9, var3);
         var3 = null;
      } catch (Throwable var7) {
         var11 = Py.setException(var7, var1);
         if (var11.match(var1.getglobal("OSError"))) {
            var10 = var11.value;
            var1.setlocal(10, var10);
            var4 = null;
            var1.setline(487);
            throw Py.makeException(var1.getglobal("IOError").__call__(var2, var1.getlocal(10).__getattr__("errno"), var1.getlocal(10).__getattr__("strerror"), var1.getlocal(10).__getattr__("filename")));
         }

         throw var11;
      }

      var1.setline(488);
      var3 = var1.getlocal(9).__getattr__("st_size");
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(489);
      PyObject var10000 = var1.getlocal(4).__getattr__("utils").__getattr__("formatdate");
      var13 = new PyObject[]{var1.getlocal(9).__getattr__("st_mtime"), var1.getglobal("True")};
      var4 = new String[]{"usegmt"};
      var10000 = var10000.__call__(var2, var13, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(490);
      var3 = var1.getlocal(2).__getattr__("guess_type").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(13, var3);
      var3 = null;
      var1.setline(491);
      var10000 = var1.getlocal(3).__getattr__("Message");
      PyObject var10002 = var1.getlocal(5);
      PyString var10004 = PyString.fromInterned("Content-Type: %s\nContent-Length: %d\nLast-modified: %s\n");
      PyTuple var10005 = new PyTuple;
      PyObject[] var10007 = new PyObject[3];
      Object var10010 = var1.getlocal(13);
      if (!((PyObject)var10010).__nonzero__()) {
         var10010 = PyString.fromInterned("text/plain");
      }

      var10007[0] = (PyObject)var10010;
      var10007[1] = var1.getlocal(11);
      var10007[2] = var1.getlocal(12);
      var10005.<init>(var10007);
      var3 = var10000.__call__(var2, var10002.__call__(var2, var10004._mod(var10005)));
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(494);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(495);
         var3 = var1.getlocal(7);
         var1.setlocal(15, var3);
         var3 = null;
         var1.setline(496);
         var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("/"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(497);
            var3 = PyString.fromInterned("file://")._add(var1.getlocal(7));
            var1.setlocal(15, var3);
            var3 = null;
         } else {
            var1.setline(498);
            var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("./"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(499);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("local file url may start with / or file:. Unknown url of type: %s")._mod(var1.getlocal(1))));
            }
         }

         var1.setline(500);
         var3 = var1.getglobal("addinfourl").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("rb")), var1.getlocal(14), var1.getlocal(15));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(502);
         var10 = var1.getglobal("splitport").__call__(var2, var1.getlocal(6));
         PyObject[] var14 = Py.unpackSequence(var10, 2);
         PyObject var6 = var14[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var14[1];
         var1.setlocal(16, var6);
         var6 = null;
         var4 = null;
         var1.setline(503);
         var10000 = var1.getlocal(16).__not__();
         if (var10000.__nonzero__()) {
            var10 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getlocal(6));
            var10000 = var10._in(new PyTuple(new PyObject[]{var1.getglobal("localhost").__call__(var2), var1.getglobal("thishost").__call__(var2)}));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(505);
            var10 = var1.getlocal(7);
            var1.setlocal(15, var10);
            var4 = null;
            var1.setline(506);
            var10 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var10._eq(PyString.fromInterned("/"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(507);
               var10 = PyString.fromInterned("file://")._add(var1.getlocal(7));
               var1.setlocal(15, var10);
               var4 = null;
            }

            var1.setline(508);
            var3 = var1.getglobal("addinfourl").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("rb")), var1.getlocal(14), var1.getlocal(15));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(510);
            throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("local file error"), PyString.fromInterned("not on local host")}));
         }
      }
   }

   public PyObject open_ftp$24(PyFrame var1, ThreadState var2) {
      var1.setline(513);
      PyString.fromInterned("Use FTP protocol.");
      var1.setline(514);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
         var1.setline(515);
         throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("ftp error"), PyString.fromInterned("proxy support for ftp protocol currently not implemented")}));
      } else {
         var1.setline(516);
         PyObject var3 = imp.importOne("mimetypes", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var3 = imp.importOne("mimetools", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;

         String[] var4;
         PyObject var5;
         PyObject[] var11;
         PyObject var13;
         PyObject[] var14;
         try {
            var1.setline(518);
            String[] var12 = new String[]{"StringIO"};
            var14 = imp.importFrom("cStringIO", var12, var1, -1);
            var13 = var14[0];
            var1.setlocal(4, var13);
            var4 = null;
         } catch (Throwable var9) {
            PyException var10 = Py.setException(var9, var1);
            if (!var10.match(var1.getglobal("ImportError"))) {
               throw var10;
            }

            var1.setline(520);
            var4 = new String[]{"StringIO"};
            var11 = imp.importFrom("StringIO", var4, var1, -1);
            var5 = var11[0];
            var1.setlocal(4, var5);
            var5 = null;
         }

         var1.setline(521);
         var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
         var11 = Py.unpackSequence(var3, 2);
         var5 = var11[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(522);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(522);
            throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("ftp error"), PyString.fromInterned("no host given")}));
         } else {
            var1.setline(523);
            var3 = var1.getglobal("splitport").__call__(var2, var1.getlocal(5));
            var11 = Py.unpackSequence(var3, 2);
            var5 = var11[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
            var1.setline(524);
            var3 = var1.getglobal("splituser").__call__(var2, var1.getlocal(5));
            var11 = Py.unpackSequence(var3, 2);
            var5 = var11[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(525);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(525);
               var3 = var1.getglobal("splitpasswd").__call__(var2, var1.getlocal(8));
               var11 = Py.unpackSequence(var3, 2);
               var5 = var11[0];
               var1.setlocal(8, var5);
               var5 = null;
               var5 = var11[1];
               var1.setlocal(9, var5);
               var5 = null;
               var3 = null;
            } else {
               var1.setline(526);
               var3 = var1.getglobal("None");
               var1.setlocal(9, var3);
               var3 = null;
            }

            var1.setline(527);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(528);
            Object var10000 = var1.getlocal(8);
            if (!((PyObject)var10000).__nonzero__()) {
               var10000 = PyString.fromInterned("");
            }

            Object var16 = var10000;
            var1.setlocal(8, (PyObject)var16);
            var3 = null;
            var1.setline(529);
            var10000 = var1.getlocal(9);
            if (!((PyObject)var10000).__nonzero__()) {
               var10000 = PyString.fromInterned("");
            }

            var16 = var10000;
            var1.setlocal(9, (PyObject)var16);
            var3 = null;
            var1.setline(530);
            var3 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(531);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(532);
               var3 = imp.importOne("ftplib", var1, -1);
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(533);
               var3 = var1.getlocal(10).__getattr__("FTP_PORT");
               var1.setlocal(7, var3);
               var3 = null;
            } else {
               var1.setline(535);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(536);
            var3 = var1.getglobal("splitattr").__call__(var2, var1.getlocal(6));
            var11 = Py.unpackSequence(var3, 2);
            var5 = var11[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal(11, var5);
            var5 = null;
            var3 = null;
            var1.setline(537);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(538);
            var3 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(539);
            PyTuple var17 = new PyTuple(new PyObject[]{var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(12).__getitem__(Py.newInteger(-1))});
            var11 = Py.unpackSequence(var17, 2);
            var5 = var11[0];
            var1.setlocal(12, var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal(13, var5);
            var5 = null;
            var3 = null;
            var1.setline(540);
            PyObject var19 = var1.getlocal(12);
            if (var19.__nonzero__()) {
               var19 = var1.getlocal(12).__getitem__(Py.newInteger(0)).__not__();
            }

            if (var19.__nonzero__()) {
               var1.setline(540);
               var3 = var1.getlocal(12).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(12, var3);
               var3 = null;
            }

            var1.setline(541);
            var19 = var1.getlocal(12);
            if (var19.__nonzero__()) {
               var19 = var1.getlocal(12).__getitem__(Py.newInteger(0)).__not__();
            }

            PyString var18;
            if (var19.__nonzero__()) {
               var1.setline(541);
               var18 = PyString.fromInterned("/");
               var1.getlocal(12).__setitem__((PyObject)Py.newInteger(0), var18);
               var3 = null;
            }

            var1.setline(542);
            var17 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(5), var1.getlocal(7), PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(12))});
            var1.setlocal(14, var17);
            var3 = null;
            var1.setline(544);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("ftpcache"));
            var19 = var3._gt(var1.getglobal("MAXFTPCACHE"));
            var3 = null;
            if (var19.__nonzero__()) {
               var1.setline(546);
               var3 = var1.getlocal(0).__getattr__("ftpcache").__getattr__("keys").__call__(var2).__iter__();

               while(true) {
                  var1.setline(546);
                  var13 = var3.__iternext__();
                  if (var13 == null) {
                     break;
                  }

                  var1.setlocal(15, var13);
                  var1.setline(547);
                  var5 = var1.getlocal(15);
                  var19 = var5._ne(var1.getlocal(14));
                  var5 = null;
                  if (var19.__nonzero__()) {
                     var1.setline(548);
                     var5 = var1.getlocal(0).__getattr__("ftpcache").__getitem__(var1.getlocal(15));
                     var1.setlocal(16, var5);
                     var5 = null;
                     var1.setline(549);
                     var1.getlocal(0).__getattr__("ftpcache").__delitem__(var1.getlocal(15));
                     var1.setline(550);
                     var1.getlocal(16).__getattr__("close").__call__(var2);
                  }
               }
            }

            try {
               var1.setline(552);
               var3 = var1.getlocal(14);
               var19 = var3._in(var1.getlocal(0).__getattr__("ftpcache"));
               var3 = null;
               if (var19.__not__().__nonzero__()) {
                  var1.setline(553);
                  var19 = var1.getglobal("ftpwrapper");
                  var14 = new PyObject[]{var1.getlocal(8), var1.getlocal(9), var1.getlocal(5), var1.getlocal(7), var1.getlocal(12)};
                  var3 = var19.__call__(var2, var14);
                  var1.getlocal(0).__getattr__("ftpcache").__setitem__(var1.getlocal(14), var3);
                  var3 = null;
               }

               var1.setline(555);
               if (var1.getlocal(13).__not__().__nonzero__()) {
                  var1.setline(555);
                  var18 = PyString.fromInterned("D");
                  var1.setlocal(17, var18);
                  var3 = null;
               } else {
                  var1.setline(556);
                  var18 = PyString.fromInterned("I");
                  var1.setlocal(17, var18);
                  var3 = null;
               }

               var1.setline(557);
               var3 = var1.getlocal(11).__iter__();

               while(true) {
                  var1.setline(557);
                  var13 = var3.__iternext__();
                  if (var13 == null) {
                     var1.setline(562);
                     var3 = var1.getlocal(0).__getattr__("ftpcache").__getitem__(var1.getlocal(14)).__getattr__("retrfile").__call__(var2, var1.getlocal(13), var1.getlocal(17));
                     var11 = Py.unpackSequence(var3, 2);
                     var5 = var11[0];
                     var1.setlocal(20, var5);
                     var5 = null;
                     var5 = var11[1];
                     var1.setlocal(21, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(563);
                     var3 = var1.getlocal(2).__getattr__("guess_type").__call__(var2, PyString.fromInterned("ftp:")._add(var1.getlocal(1))).__getitem__(Py.newInteger(0));
                     var1.setlocal(22, var3);
                     var3 = null;
                     var1.setline(564);
                     var18 = PyString.fromInterned("");
                     var1.setlocal(23, var18);
                     var3 = null;
                     var1.setline(565);
                     if (var1.getlocal(22).__nonzero__()) {
                        var1.setline(566);
                        var3 = var1.getlocal(23);
                        var3 = var3._iadd(PyString.fromInterned("Content-Type: %s\n")._mod(var1.getlocal(22)));
                        var1.setlocal(23, var3);
                     }

                     var1.setline(567);
                     var3 = var1.getlocal(21);
                     var19 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (var19.__nonzero__()) {
                        var3 = var1.getlocal(21);
                        var19 = var3._ge(Py.newInteger(0));
                        var3 = null;
                     }

                     if (var19.__nonzero__()) {
                        var1.setline(568);
                        var3 = var1.getlocal(23);
                        var3 = var3._iadd(PyString.fromInterned("Content-Length: %d\n")._mod(var1.getlocal(21)));
                        var1.setlocal(23, var3);
                     }

                     var1.setline(569);
                     var3 = var1.getlocal(3).__getattr__("Message").__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(23)));
                     var1.setlocal(23, var3);
                     var3 = null;
                     var1.setline(570);
                     var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(20), var1.getlocal(23), PyString.fromInterned("ftp:")._add(var1.getlocal(1)));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(18, var13);
                  var1.setline(558);
                  var5 = var1.getglobal("splitvalue").__call__(var2, var1.getlocal(18));
                  PyObject[] var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(18, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(19, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(559);
                  var5 = var1.getlocal(18).__getattr__("lower").__call__(var2);
                  var19 = var5._eq(PyString.fromInterned("type"));
                  var5 = null;
                  if (var19.__nonzero__()) {
                     var5 = var1.getlocal(19);
                     var19 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("A"), PyString.fromInterned("i"), PyString.fromInterned("I"), PyString.fromInterned("d"), PyString.fromInterned("D")}));
                     var5 = null;
                  }

                  if (var19.__nonzero__()) {
                     var1.setline(561);
                     var5 = var1.getlocal(19).__getattr__("upper").__call__(var2);
                     var1.setlocal(17, var5);
                     var5 = null;
                  }
               }
            } catch (Throwable var8) {
               PyException var15 = Py.setException(var8, var1);
               if (var15.match(var1.getglobal("ftperrors").__call__(var2))) {
                  var5 = var15.value;
                  var1.setlocal(24, var5);
                  var5 = null;
                  var1.setline(572);
                  throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("ftp error"), var1.getlocal(24)}), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
               } else {
                  throw var15;
               }
            }
         }
      }
   }

   public PyObject open_data$25(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyString.fromInterned("Use \"data\" URL.");
      var1.setline(576);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
         var1.setline(577);
         throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("data error"), PyString.fromInterned("proxy support for data protocol currently not implemented")}));
      } else {
         var1.setline(585);
         PyObject var3 = imp.importOne("mimetools", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;

         String[] var4;
         PyObject var5;
         PyException var8;
         PyObject[] var11;
         try {
            var1.setline(587);
            String[] var9 = new String[]{"StringIO"};
            PyObject[] var10 = imp.importFrom("cStringIO", var9, var1, -1);
            PyObject var12 = var10[0];
            var1.setlocal(4, var12);
            var4 = null;
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("ImportError"))) {
               throw var8;
            }

            var1.setline(589);
            var4 = new String[]{"StringIO"};
            var11 = imp.importFrom("StringIO", var4, var1, -1);
            var5 = var11[0];
            var1.setlocal(4, var5);
            var5 = null;
         }

         try {
            var1.setline(591);
            var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","), (PyObject)Py.newInteger(1));
            var11 = Py.unpackSequence(var3, 2);
            var5 = var11[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("ValueError"))) {
               var1.setline(593);
               throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("data error"), PyString.fromInterned("bad data URL")}));
            }

            throw var8;
         }

         var1.setline(594);
         PyString var13;
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(595);
            var13 = PyString.fromInterned("text/plain;charset=US-ASCII");
            var1.setlocal(5, var13);
            var3 = null;
         }

         var1.setline(596);
         var3 = var1.getlocal(5).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(597);
         var3 = var1.getlocal(6);
         PyObject var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var13 = PyString.fromInterned("=");
            var10000 = var13._notin(var1.getlocal(5).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(598);
            var3 = var1.getlocal(5).__getslice__(var1.getlocal(6)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(599);
            var3 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(601);
            var13 = PyString.fromInterned("");
            var1.setlocal(7, var13);
            var3 = null;
         }

         var1.setline(602);
         PyList var14 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var14);
         var3 = null;
         var1.setline(603);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("Date: %s")._mod(var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S GMT"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)))));
         var1.setline(605);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("Content-type: %s")._mod(var1.getlocal(5)));
         var1.setline(606);
         var3 = var1.getlocal(7);
         var10000 = var3._eq(PyString.fromInterned("base64"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(607);
            var3 = var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(609);
            var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(610);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("Content-Length: %d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
         var1.setline(611);
         var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
         var1.setline(612);
         var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(2));
         var1.setline(613);
         var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(614);
         var3 = var1.getlocal(4).__call__(var2, var1.getlocal(8));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(615);
         var3 = var1.getlocal(3).__getattr__("Message").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)Py.newInteger(0));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(617);
         var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(9), var1.getlocal(10), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject FancyURLopener$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Derived class with handlers for errors we can handle (perhaps)."));
      var1.setline(621);
      PyString.fromInterned("Derived class with handlers for errors we can handle (perhaps).");
      var1.setline(623);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$27, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(629);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, http_error_default$28, PyString.fromInterned("Default error handling -- don't raise an exception."));
      var1.setlocal("http_error_default", var4);
      var3 = null;
      var1.setline(633);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, http_error_302$29, PyString.fromInterned("Error 302 -- relocated (temporarily)."));
      var1.setlocal("http_error_302", var4);
      var3 = null;
      var1.setline(651);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, redirect_internal$30, (PyObject)null);
      var1.setlocal("redirect_internal", var4);
      var3 = null;
      var1.setline(675);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, http_error_301$31, PyString.fromInterned("Error 301 -- also relocated (permanently)."));
      var1.setlocal("http_error_301", var4);
      var3 = null;
      var1.setline(679);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, http_error_303$32, PyString.fromInterned("Error 303 -- also relocated (essentially identical to 302)."));
      var1.setlocal("http_error_303", var4);
      var3 = null;
      var1.setline(683);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, http_error_307$33, PyString.fromInterned("Error 307 -- relocated, but turn POST into error."));
      var1.setlocal("http_error_307", var4);
      var3 = null;
      var1.setline(690);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, http_error_401$34, PyString.fromInterned("Error 401 -- authentication required.\n        This function supports Basic authentication only."));
      var1.setlocal("http_error_401", var4);
      var3 = null;
      var1.setline(712);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, http_error_407$35, PyString.fromInterned("Error 407 -- proxy authentication required.\n        This function supports Basic authentication only."));
      var1.setlocal("http_error_407", var4);
      var3 = null;
      var1.setline(734);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, retry_proxy_http_basic_auth$36, (PyObject)null);
      var1.setlocal("retry_proxy_http_basic_auth", var4);
      var3 = null;
      var1.setline(751);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, retry_proxy_https_basic_auth$37, (PyObject)null);
      var1.setlocal("retry_proxy_https_basic_auth", var4);
      var3 = null;
      var1.setline(768);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, retry_http_basic_auth$38, (PyObject)null);
      var1.setlocal("retry_http_basic_auth", var4);
      var3 = null;
      var1.setline(781);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, retry_https_basic_auth$39, (PyObject)null);
      var1.setlocal("retry_https_basic_auth", var4);
      var3 = null;
      var1.setline(794);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, get_user_passwd$40, (PyObject)null);
      var1.setlocal("get_user_passwd", var4);
      var3 = null;
      var1.setline(805);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prompt_user_passwd$41, PyString.fromInterned("Override this in a GUI environment!"));
      var1.setlocal("prompt_user_passwd", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$27(PyFrame var1, ThreadState var2) {
      var1.setline(624);
      PyObject var10000 = var1.getglobal("URLopener").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.setline(625);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"auth_cache", var5);
      var3 = null;
      var1.setline(626);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"tries", var6);
      var3 = null;
      var1.setline(627);
      var6 = Py.newInteger(10);
      var1.getlocal(0).__setattr__((String)"maxtries", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject http_error_default$28(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyString.fromInterned("Default error handling -- don't raise an exception.");
      var1.setline(631);
      PyObject var3 = var1.getglobal("addinfourl").__call__(var2, var1.getlocal(2), var1.getlocal(5), PyString.fromInterned("http:")._add(var1.getlocal(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject http_error_302$29(PyFrame var1, ThreadState var2) {
      var1.setline(634);
      PyString.fromInterned("Error 302 -- relocated (temporarily).");
      var1.setline(635);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "tries";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var3 = null;

      PyInteger var12;
      Throwable var14;
      label52: {
         boolean var10001;
         label53: {
            try {
               var1.setline(637);
               var10000 = var1.getlocal(0).__getattr__("maxtries");
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("tries");
                  var10000 = var4._ge(var1.getlocal(0).__getattr__("maxtries"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(638);
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("http_error_500")).__nonzero__()) {
                     var1.setline(639);
                     var4 = var1.getlocal(0).__getattr__("http_error_500");
                     var1.setlocal(7, var4);
                     var4 = null;
                  } else {
                     var1.setline(641);
                     var4 = var1.getlocal(0).__getattr__("http_error_default");
                     var1.setlocal(7, var4);
                     var4 = null;
                  }

                  var1.setline(642);
                  var10000 = var1.getlocal(7);
                  PyObject[] var13 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(500), PyString.fromInterned("Internal Server Error: Redirect Recursion"), var1.getlocal(5)};
                  var4 = var10000.__call__(var2, var13);
                  break label53;
               }
            } catch (Throwable var9) {
               var14 = var9;
               var10001 = false;
               break label52;
            }

            try {
               var1.setline(645);
               var10000 = var1.getlocal(0).__getattr__("redirect_internal");
               PyObject[] var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
               var5 = var10000.__call__(var2, var11);
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(647);
               var4 = var1.getlocal(8);
            } catch (Throwable var8) {
               var14 = var8;
               var10001 = false;
               break label52;
            }

            var1.setline(649);
            var12 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"tries", var12);
            var5 = null;

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var7) {
               var14 = var7;
               var10001 = false;
               break label52;
            }
         }

         var1.setline(649);
         var12 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"tries", var12);
         var5 = null;

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var6) {
            var14 = var6;
            var10001 = false;
         }
      }

      Throwable var10 = var14;
      Py.addTraceback(var10, var1);
      var1.setline(649);
      var12 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"tries", var12);
      var5 = null;
      throw (Throwable)var10;
   }

   public PyObject redirect_internal$30(PyFrame var1, ThreadState var2) {
      var1.setline(652);
      PyString var3 = PyString.fromInterned("location");
      PyObject var10000 = var3._in(var1.getlocal(5));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(653);
         var4 = var1.getlocal(5).__getitem__(PyString.fromInterned("location"));
         var1.setlocal(7, var4);
         var3 = null;
      } else {
         var1.setline(654);
         var3 = PyString.fromInterned("uri");
         var10000 = var3._in(var1.getlocal(5));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(657);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(655);
         var4 = var1.getlocal(5).__getitem__(PyString.fromInterned("uri"));
         var1.setlocal(7, var4);
         var3 = null;
      }

      var1.setline(658);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(660);
      var4 = var1.getglobal("basejoin").__call__(var2, var1.getlocal(0).__getattr__("type")._add(PyString.fromInterned(":"))._add(var1.getlocal(1)), var1.getlocal(7));
      var1.setlocal(7, var4);
      var3 = null;
      var1.setline(664);
      var4 = var1.getlocal(7).__getattr__("lower").__call__(var2);
      var1.setlocal(8, var4);
      var3 = null;
      var1.setline(665);
      var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("https://"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ftp://"));
         }
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(668);
         throw Py.makeException(var1.getglobal("IOError").__call__(var2, PyString.fromInterned("redirect error"), var1.getlocal(3), var1.getlocal(4)._add(PyString.fromInterned(" - Redirection to url '%s' is not allowed")._mod(var1.getlocal(7))), var1.getlocal(5)));
      } else {
         var1.setline(673);
         var4 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(7));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject http_error_301$31(PyFrame var1, ThreadState var2) {
      var1.setline(676);
      PyString.fromInterned("Error 301 -- also relocated (permanently).");
      var1.setline(677);
      PyObject var10000 = var1.getlocal(0).__getattr__("http_error_302");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject http_error_303$32(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyString.fromInterned("Error 303 -- also relocated (essentially identical to 302).");
      var1.setline(681);
      PyObject var10000 = var1.getlocal(0).__getattr__("http_error_302");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject http_error_307$33(PyFrame var1, ThreadState var2) {
      var1.setline(684);
      PyString.fromInterned("Error 307 -- relocated, but turn POST into error.");
      var1.setline(685);
      PyObject var3 = var1.getlocal(6);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(686);
         var10000 = var1.getlocal(0).__getattr__("http_error_302");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         var3 = var10000.__call__(var2, var5);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(688);
         var10000 = var1.getlocal(0).__getattr__("http_error_default");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject http_error_401$34(PyFrame var1, ThreadState var2) {
      var1.setline(692);
      PyString.fromInterned("Error 401 -- authentication required.\n        This function supports Basic authentication only.");
      var1.setline(693);
      PyString var3 = PyString.fromInterned("www-authenticate");
      PyObject var10000 = var3._in(var1.getlocal(5));
      var3 = null;
      PyObject[] var6;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(694);
         var10000 = var1.getglobal("URLopener").__getattr__("http_error_default");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var6);
      }

      var1.setline(696);
      PyObject var7 = var1.getlocal(5).__getitem__(PyString.fromInterned("www-authenticate"));
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(697);
      var7 = imp.importOne("re", var1, -1);
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(698);
      var7 = var1.getlocal(8).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ \t]*([^ \t]+)[ \t]+realm=\"([^\"]*)\""), (PyObject)var1.getlocal(7));
      var1.setlocal(9, var7);
      var3 = null;
      var1.setline(699);
      if (var1.getlocal(9).__not__().__nonzero__()) {
         var1.setline(700);
         var10000 = var1.getglobal("URLopener").__getattr__("http_error_default");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var6);
      }

      var1.setline(702);
      var7 = var1.getlocal(9).__getattr__("groups").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(703);
      var7 = var1.getlocal(10).__getattr__("lower").__call__(var2);
      var10000 = var7._ne(PyString.fromInterned("basic"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(704);
         var10000 = var1.getglobal("URLopener").__getattr__("http_error_default");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var6);
      }

      var1.setline(706);
      var7 = PyString.fromInterned("retry_")._add(var1.getlocal(0).__getattr__("type"))._add(PyString.fromInterned("_basic_auth"));
      var1.setlocal(12, var7);
      var3 = null;
      var1.setline(707);
      var7 = var1.getlocal(6);
      var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(708);
         var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__call__(var2, var1.getlocal(1), var1.getlocal(11));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(710);
         var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__call__(var2, var1.getlocal(1), var1.getlocal(11), var1.getlocal(6));
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject http_error_407$35(PyFrame var1, ThreadState var2) {
      var1.setline(714);
      PyString.fromInterned("Error 407 -- proxy authentication required.\n        This function supports Basic authentication only.");
      var1.setline(715);
      PyString var3 = PyString.fromInterned("proxy-authenticate");
      PyObject var10000 = var3._in(var1.getlocal(5));
      var3 = null;
      PyObject[] var6;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(716);
         var10000 = var1.getglobal("URLopener").__getattr__("http_error_default");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var6);
      }

      var1.setline(718);
      PyObject var7 = var1.getlocal(5).__getitem__(PyString.fromInterned("proxy-authenticate"));
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(719);
      var7 = imp.importOne("re", var1, -1);
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(720);
      var7 = var1.getlocal(8).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ \t]*([^ \t]+)[ \t]+realm=\"([^\"]*)\""), (PyObject)var1.getlocal(7));
      var1.setlocal(9, var7);
      var3 = null;
      var1.setline(721);
      if (var1.getlocal(9).__not__().__nonzero__()) {
         var1.setline(722);
         var10000 = var1.getglobal("URLopener").__getattr__("http_error_default");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var6);
      }

      var1.setline(724);
      var7 = var1.getlocal(9).__getattr__("groups").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(725);
      var7 = var1.getlocal(10).__getattr__("lower").__call__(var2);
      var10000 = var7._ne(PyString.fromInterned("basic"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(726);
         var10000 = var1.getglobal("URLopener").__getattr__("http_error_default");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var6);
      }

      var1.setline(728);
      var7 = PyString.fromInterned("retry_proxy_")._add(var1.getlocal(0).__getattr__("type"))._add(PyString.fromInterned("_basic_auth"));
      var1.setlocal(12, var7);
      var3 = null;
      var1.setline(729);
      var7 = var1.getlocal(6);
      var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(730);
         var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__call__(var2, var1.getlocal(1), var1.getlocal(11));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(732);
         var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__call__(var2, var1.getlocal(1), var1.getlocal(11), var1.getlocal(6));
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject retry_proxy_http_basic_auth$36(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyObject var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(736);
      var3 = PyString.fromInterned("http://")._add(var1.getlocal(4))._add(var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(737);
      var3 = var1.getlocal(0).__getattr__("proxies").__getitem__(PyString.fromInterned("http"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(738);
      var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(7));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(739);
      var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(9));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(740);
      var3 = var1.getlocal(9).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"))._add(Py.newInteger(1));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(741);
      var3 = var1.getlocal(9).__getslice__(var1.getlocal(11), (PyObject)null, (PyObject)null);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(742);
      var3 = var1.getlocal(0).__getattr__("get_user_passwd").__call__(var2, var1.getlocal(9), var1.getlocal(2), var1.getlocal(11));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(12, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(13, var5);
      var5 = null;
      var3 = null;
      var1.setline(743);
      PyObject var10000 = var1.getlocal(12);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(13);
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(743);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(744);
         var10000 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(12), PyString.fromInterned("")};
         String[] var7 = new String[]{"safe"};
         var10000 = var10000.__call__(var2, var4, var7);
         var4 = null;
         var10000 = var10000._add(PyString.fromInterned(":"));
         PyObject var10001 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(13), PyString.fromInterned("")};
         var7 = new String[]{"safe"};
         var10001 = var10001.__call__(var2, var4, var7);
         var4 = null;
         PyObject var6 = var10000._add(var10001)._add(PyString.fromInterned("@"))._add(var1.getlocal(9));
         var1.setlocal(9, var6);
         var4 = null;
         var1.setline(745);
         var6 = PyString.fromInterned("http://")._add(var1.getlocal(9))._add(var1.getlocal(10));
         var1.getlocal(0).__getattr__("proxies").__setitem__((PyObject)PyString.fromInterned("http"), var6);
         var4 = null;
         var1.setline(746);
         var6 = var1.getlocal(3);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(747);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(749);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(6), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject retry_proxy_https_basic_auth$37(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      PyObject var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(753);
      var3 = PyString.fromInterned("https://")._add(var1.getlocal(4))._add(var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(754);
      var3 = var1.getlocal(0).__getattr__("proxies").__getitem__(PyString.fromInterned("https"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(755);
      var3 = var1.getglobal("splittype").__call__(var2, var1.getlocal(7));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(756);
      var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(9));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(757);
      var3 = var1.getlocal(9).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"))._add(Py.newInteger(1));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(758);
      var3 = var1.getlocal(9).__getslice__(var1.getlocal(11), (PyObject)null, (PyObject)null);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(759);
      var3 = var1.getlocal(0).__getattr__("get_user_passwd").__call__(var2, var1.getlocal(9), var1.getlocal(2), var1.getlocal(11));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(12, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(13, var5);
      var5 = null;
      var3 = null;
      var1.setline(760);
      PyObject var10000 = var1.getlocal(12);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(13);
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(760);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(761);
         var10000 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(12), PyString.fromInterned("")};
         String[] var7 = new String[]{"safe"};
         var10000 = var10000.__call__(var2, var4, var7);
         var4 = null;
         var10000 = var10000._add(PyString.fromInterned(":"));
         PyObject var10001 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(13), PyString.fromInterned("")};
         var7 = new String[]{"safe"};
         var10001 = var10001.__call__(var2, var4, var7);
         var4 = null;
         PyObject var6 = var10000._add(var10001)._add(PyString.fromInterned("@"))._add(var1.getlocal(9));
         var1.setlocal(9, var6);
         var4 = null;
         var1.setline(762);
         var6 = PyString.fromInterned("https://")._add(var1.getlocal(9))._add(var1.getlocal(10));
         var1.getlocal(0).__getattr__("proxies").__setitem__((PyObject)PyString.fromInterned("https"), var6);
         var4 = null;
         var1.setline(763);
         var6 = var1.getlocal(3);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(764);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(766);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(6), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject retry_http_basic_auth$38(PyFrame var1, ThreadState var2) {
      var1.setline(769);
      PyObject var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(770);
      var3 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"))._add(Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(771);
      var3 = var1.getlocal(4).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(772);
      var3 = var1.getlocal(0).__getattr__("get_user_passwd").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(6));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(773);
      PyObject var10000 = var1.getlocal(7);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8);
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(773);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(774);
         var10000 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(7), PyString.fromInterned("")};
         String[] var7 = new String[]{"safe"};
         var10000 = var10000.__call__(var2, var4, var7);
         var4 = null;
         var10000 = var10000._add(PyString.fromInterned(":"));
         PyObject var10001 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(8), PyString.fromInterned("")};
         var7 = new String[]{"safe"};
         var10001 = var10001.__call__(var2, var4, var7);
         var4 = null;
         PyObject var6 = var10000._add(var10001)._add(PyString.fromInterned("@"))._add(var1.getlocal(4));
         var1.setlocal(4, var6);
         var4 = null;
         var1.setline(775);
         var6 = PyString.fromInterned("http://")._add(var1.getlocal(4))._add(var1.getlocal(5));
         var1.setlocal(9, var6);
         var4 = null;
         var1.setline(776);
         var6 = var1.getlocal(3);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(777);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(9));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(779);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(9), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject retry_https_basic_auth$39(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      PyObject var3 = var1.getglobal("splithost").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(783);
      var3 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"))._add(Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(784);
      var3 = var1.getlocal(4).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(785);
      var3 = var1.getlocal(0).__getattr__("get_user_passwd").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(6));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(786);
      PyObject var10000 = var1.getlocal(7);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8);
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(786);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(787);
         var10000 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(7), PyString.fromInterned("")};
         String[] var7 = new String[]{"safe"};
         var10000 = var10000.__call__(var2, var4, var7);
         var4 = null;
         var10000 = var10000._add(PyString.fromInterned(":"));
         PyObject var10001 = var1.getglobal("quote");
         var4 = new PyObject[]{var1.getlocal(8), PyString.fromInterned("")};
         var7 = new String[]{"safe"};
         var10001 = var10001.__call__(var2, var4, var7);
         var4 = null;
         PyObject var6 = var10000._add(var10001)._add(PyString.fromInterned("@"))._add(var1.getlocal(4));
         var1.setlocal(4, var6);
         var4 = null;
         var1.setline(788);
         var6 = PyString.fromInterned("https://")._add(var1.getlocal(4))._add(var1.getlocal(5));
         var1.setlocal(9, var6);
         var4 = null;
         var1.setline(789);
         var6 = var1.getlocal(3);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(790);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(9));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(792);
            var3 = var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(9), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_user_passwd$40(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      PyObject var3 = var1.getlocal(2)._add(PyString.fromInterned("@"))._add(var1.getlocal(1).__getattr__("lower").__call__(var2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(796);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("auth_cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(797);
         if (!var1.getlocal(3).__nonzero__()) {
            var1.setline(800);
            var3 = var1.getlocal(0).__getattr__("auth_cache").__getitem__(var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(798);
         var1.getlocal(0).__getattr__("auth_cache").__delitem__(var1.getlocal(4));
      }

      var1.setline(801);
      PyObject var4 = var1.getlocal(0).__getattr__("prompt_user_passwd").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var5 = Py.unpackSequence(var4, 2);
      PyObject var6 = var5[0];
      var1.setlocal(5, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(6, var6);
      var6 = null;
      var4 = null;
      var1.setline(802);
      var10000 = var1.getlocal(5);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(6);
      }

      if (var10000.__nonzero__()) {
         var1.setline(802);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
         var1.getlocal(0).__getattr__("auth_cache").__setitem__((PyObject)var1.getlocal(4), var8);
         var4 = null;
      }

      var1.setline(803);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject prompt_user_passwd$41(PyFrame var1, ThreadState var2) {
      var1.setline(806);
      PyString.fromInterned("Override this in a GUI environment!");
      var1.setline(807);
      PyObject var3 = imp.importOne("getpass", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;

      PyTuple var6;
      try {
         var1.setline(809);
         var3 = var1.getglobal("raw_input").__call__(var2, PyString.fromInterned("Enter username for %s at %s: ")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)})));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(811);
         var3 = var1.getlocal(3).__getattr__("getpass").__call__(var2, PyString.fromInterned("Enter password for %s in %s at %s: ")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(2), var1.getlocal(1)})));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(813);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyboardInterrupt"))) {
            var1.setline(815);
            Py.println();
            var1.setline(816);
            var6 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var6;
         } else {
            throw var4;
         }
      }
   }

   public PyObject localhost$42(PyFrame var1, ThreadState var2) {
      var1.setline(823);
      PyString.fromInterned("Return the IP address of the magic hostname 'localhost'.");
      var1.setline(825);
      PyObject var3 = var1.getglobal("_localhost");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(826);
         var3 = var1.getglobal("socket").__getattr__("gethostbyname").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("localhost"));
         var1.setglobal("_localhost", var3);
         var3 = null;
      }

      var1.setline(827);
      var3 = var1.getglobal("_localhost");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject thishost$43(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      PyString.fromInterned("Return the IP address of the current host.");
      var1.setline(833);
      PyObject var3 = var1.getglobal("_thishost");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(835);
            var3 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getglobal("socket").__getattr__("gethostname").__call__(var2));
            var1.setglobal("_thishost", var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("socket").__getattr__("gaierror"))) {
               throw var6;
            }

            var1.setline(837);
            PyObject var4 = var1.getglobal("socket").__getattr__("gethostbyname").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("localhost"));
            var1.setglobal("_thishost", var4);
            var4 = null;
         }
      }

      var1.setline(838);
      var3 = var1.getglobal("_thishost");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ftperrors$44(PyFrame var1, ThreadState var2) {
      var1.setline(842);
      PyString.fromInterned("Return the set of errors raised by the FTP class.");
      var1.setline(844);
      PyObject var3 = var1.getglobal("_ftperrors");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(845);
         var3 = imp.importOne("ftplib", var1, -1);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(846);
         var3 = var1.getlocal(0).__getattr__("all_errors");
         var1.setglobal("_ftperrors", var3);
         var3 = null;
      }

      var1.setline(847);
      var3 = var1.getglobal("_ftperrors");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject noheaders$45(PyFrame var1, ThreadState var2) {
      var1.setline(851);
      PyString.fromInterned("Return an empty mimetools.Message object.");
      var1.setline(853);
      PyObject var3 = var1.getglobal("_noheaders");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(854);
         var3 = imp.importOne("mimetools", var1, -1);
         var1.setlocal(0, var3);
         var3 = null;

         String[] var4;
         try {
            var1.setline(856);
            String[] var9 = new String[]{"StringIO"};
            PyObject[] var11 = imp.importFrom("cStringIO", var9, var1, -1);
            PyObject var10 = var11[0];
            var1.setlocal(1, var10);
            var4 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("ImportError"))) {
               throw var7;
            }

            var1.setline(858);
            var4 = new String[]{"StringIO"};
            PyObject[] var8 = imp.importFrom("StringIO", var4, var1, -1);
            PyObject var5 = var8[0];
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(859);
         var3 = var1.getlocal(0).__getattr__("Message").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2), (PyObject)Py.newInteger(0));
         var1.setglobal("_noheaders", var3);
         var3 = null;
         var1.setline(860);
         var1.getglobal("_noheaders").__getattr__("fp").__getattr__("close").__call__(var2);
      }

      var1.setline(861);
      var3 = var1.getglobal("_noheaders");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ftpwrapper$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class used by open_ftp() for cache of open FTP connections."));
      var1.setline(867);
      PyString.fromInterned("Class used by open_ftp() for cache of open FTP connections.");
      var1.setline(869);
      PyObject[] var3 = new PyObject[]{var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(886);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, init$48, (PyObject)null);
      var1.setlocal("init", var4);
      var3 = null;
      var1.setline(895);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, retrfile$49, (PyObject)null);
      var1.setlocal("retrfile", var4);
      var3 = null;
      var1.setline(938);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endtransfer$50, (PyObject)null);
      var1.setlocal("endtransfer", var4);
      var3 = null;
      var1.setline(941);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$51, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(946);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, file_close$52, (PyObject)null);
      var1.setlocal("file_close", var4);
      var3 = null;
      var1.setline(952);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, real_close$53, (PyObject)null);
      var1.setlocal("real_close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(872);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("user", var3);
      var3 = null;
      var1.setline(873);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("passwd", var3);
      var3 = null;
      var1.setline(874);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(875);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(876);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("dirs", var3);
      var3 = null;
      var1.setline(877);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(878);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"refcount", var5);
      var3 = null;
      var1.setline(879);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("keepalive", var3);
      var3 = null;

      try {
         var1.setline(881);
         var1.getlocal(0).__getattr__("init").__call__(var2);
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(883);
         var1.getlocal(0).__getattr__("close").__call__(var2);
         var1.setline(884);
         throw Py.makeException();
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject init$48(PyFrame var1, ThreadState var2) {
      var1.setline(887);
      PyObject var3 = imp.importOne("ftplib", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(888);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"busy", var4);
      var3 = null;
      var1.setline(889);
      var3 = var1.getlocal(1).__getattr__("FTP").__call__(var2);
      var1.getlocal(0).__setattr__("ftp", var3);
      var3 = null;
      var1.setline(890);
      var1.getlocal(0).__getattr__("ftp").__getattr__("connect").__call__(var2, var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port"), var1.getlocal(0).__getattr__("timeout"));
      var1.setline(891);
      var1.getlocal(0).__getattr__("ftp").__getattr__("login").__call__(var2, var1.getlocal(0).__getattr__("user"), var1.getlocal(0).__getattr__("passwd"));
      var1.setline(892);
      var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dirs"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(893);
      var1.getlocal(0).__getattr__("ftp").__getattr__("cwd").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject retrfile$49(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      PyObject var3 = imp.importOne("ftplib", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(897);
      var1.getlocal(0).__getattr__("endtransfer").__call__(var2);
      var1.setline(898);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("d"), PyString.fromInterned("D")}));
      var3 = null;
      PyString var10;
      PyInteger var11;
      if (var10000.__nonzero__()) {
         var1.setline(898);
         var10 = PyString.fromInterned("TYPE A");
         var1.setlocal(4, var10);
         var3 = null;
         var1.setline(898);
         var11 = Py.newInteger(1);
         var1.setlocal(5, var11);
         var3 = null;
      } else {
         var1.setline(899);
         var3 = PyString.fromInterned("TYPE ")._add(var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(899);
         var11 = Py.newInteger(0);
         var1.setlocal(5, var11);
         var3 = null;
      }

      PyException var14;
      try {
         var1.setline(901);
         var1.getlocal(0).__getattr__("ftp").__getattr__("voidcmd").__call__(var2, var1.getlocal(4));
      } catch (Throwable var9) {
         var14 = Py.setException(var9, var1);
         if (!var14.match(var1.getlocal(3).__getattr__("all_errors"))) {
            throw var14;
         }

         var1.setline(903);
         var1.getlocal(0).__getattr__("init").__call__(var2);
         var1.setline(904);
         var1.getlocal(0).__getattr__("ftp").__getattr__("voidcmd").__call__(var2, var1.getlocal(4));
      }

      var1.setline(905);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(906);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__not__();
      }

      PyObject var4;
      PyObject var5;
      PyObject[] var12;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(909);
            var3 = PyString.fromInterned("RETR ")._add(var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(910);
            var3 = var1.getlocal(0).__getattr__("ftp").__getattr__("ntransfercmd").__call__(var2, var1.getlocal(4));
            var12 = Py.unpackSequence(var3, 2);
            var5 = var12[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var12[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var8) {
            var14 = Py.setException(var8, var1);
            if (!var14.match(var1.getlocal(3).__getattr__("error_perm"))) {
               throw var14;
            }

            var4 = var14.value;
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(912);
            var4 = var1.getglobal("str").__call__(var2, var1.getlocal(8)).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
            var10000 = var4._ne(PyString.fromInterned("550"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(913);
               throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("ftp error"), var1.getlocal(8)}), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
            }
         }
      }

      var1.setline(914);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(916);
         var1.getlocal(0).__getattr__("ftp").__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE A"));
         var1.setline(918);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(919);
            var3 = var1.getlocal(0).__getattr__("ftp").__getattr__("pwd").__call__(var2);
            var1.setlocal(9, var3);
            var3 = null;
            var3 = null;

            try {
               try {
                  var1.setline(922);
                  var1.getlocal(0).__getattr__("ftp").__getattr__("cwd").__call__(var2, var1.getlocal(1));
               } catch (Throwable var6) {
                  PyException var13 = Py.setException(var6, var1);
                  if (var13.match(var1.getlocal(3).__getattr__("error_perm"))) {
                     var5 = var13.value;
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(924);
                     throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{PyString.fromInterned("ftp error"), var1.getlocal(8)}), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
                  }

                  throw var13;
               }
            } catch (Throwable var7) {
               Py.addTraceback(var7, var1);
               var1.setline(926);
               var1.getlocal(0).__getattr__("ftp").__getattr__("cwd").__call__(var2, var1.getlocal(9));
               throw (Throwable)var7;
            }

            var1.setline(926);
            var1.getlocal(0).__getattr__("ftp").__getattr__("cwd").__call__(var2, var1.getlocal(9));
            var1.setline(927);
            var3 = PyString.fromInterned("LIST ")._add(var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(929);
            var10 = PyString.fromInterned("LIST");
            var1.setlocal(4, var10);
            var3 = null;
         }

         var1.setline(930);
         var3 = var1.getlocal(0).__getattr__("ftp").__getattr__("ntransfercmd").__call__(var2, var1.getlocal(4));
         var12 = Py.unpackSequence(var3, 2);
         var5 = var12[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(931);
      var11 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"busy", var11);
      var3 = null;
      var1.setline(932);
      var3 = var1.getglobal("addclosehook").__call__(var2, var1.getlocal(6).__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb")), var1.getlocal(0).__getattr__("file_close"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(933);
      var10000 = var1.getlocal(0);
      String var15 = "refcount";
      var4 = var10000;
      var5 = var4.__getattr__(var15);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var15, var5);
      var1.setline(934);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.setline(936);
      PyTuple var16 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(7)});
      var1.f_lasti = -1;
      return var16;
   }

   public PyObject endtransfer$50(PyFrame var1, ThreadState var2) {
      var1.setline(939);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"busy", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$51(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("keepalive", var3);
      var3 = null;
      var1.setline(943);
      var3 = var1.getlocal(0).__getattr__("refcount");
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(944);
         var1.getlocal(0).__getattr__("real_close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject file_close$52(PyFrame var1, ThreadState var2) {
      var1.setline(947);
      var1.getlocal(0).__getattr__("endtransfer").__call__(var2);
      var1.setline(948);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "refcount";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._isub(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(949);
      PyObject var6 = var1.getlocal(0).__getattr__("refcount");
      var10000 = var6._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("keepalive").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(950);
         var1.getlocal(0).__getattr__("real_close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject real_close$53(PyFrame var1, ThreadState var2) {
      var1.setline(953);
      var1.getlocal(0).__getattr__("endtransfer").__call__(var2);

      try {
         var1.setline(955);
         var1.getlocal(0).__getattr__("ftp").__getattr__("close").__call__(var2);
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("ftperrors").__call__(var2))) {
            throw var3;
         }

         var1.setline(957);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addbase$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for addinfo and addclosehook."));
      var1.setline(960);
      PyString.fromInterned("Base class for addinfo and addclosehook.");
      var1.setline(962);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$55, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(976);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$57, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(980);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$58, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$55(PyFrame var1, ThreadState var2) {
      var1.setline(963);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fp", var3);
      var3 = null;
      var1.setline(964);
      var3 = var1.getlocal(0).__getattr__("fp").__getattr__("read");
      var1.getlocal(0).__setattr__("read", var3);
      var3 = null;
      var1.setline(965);
      var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline");
      var1.getlocal(0).__setattr__("readline", var3);
      var3 = null;
      var1.setline(966);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)PyString.fromInterned("readlines")).__nonzero__()) {
         var1.setline(966);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readlines");
         var1.getlocal(0).__setattr__("readlines", var3);
         var3 = null;
      }

      var1.setline(967);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)PyString.fromInterned("fileno")).__nonzero__()) {
         var1.setline(968);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("fileno");
         var1.getlocal(0).__setattr__("fileno", var3);
         var3 = null;
      } else {
         var1.setline(970);
         var1.setline(970);
         PyObject[] var4 = Py.EmptyObjects;
         PyFunction var5 = new PyFunction(var1.f_globals, var4, f$56);
         var1.getlocal(0).__setattr__((String)"fileno", var5);
         var3 = null;
      }

      var1.setline(971);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)PyString.fromInterned("__iter__")).__nonzero__()) {
         var1.setline(972);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("__iter__");
         var1.getlocal(0).__setattr__("__iter__", var3);
         var3 = null;
         var1.setline(973);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)PyString.fromInterned("next")).__nonzero__()) {
            var1.setline(974);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("next");
            var1.getlocal(0).__setattr__("next", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$56(PyFrame var1, ThreadState var2) {
      var1.setline(970);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$57(PyFrame var1, ThreadState var2) {
      var1.setline(977);
      PyObject var3 = PyString.fromInterned("<%s at %r whose fp = %r>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getlocal(0).__getattr__("fp")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$58(PyFrame var1, ThreadState var2) {
      var1.setline(981);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("read", var3);
      var3 = null;
      var1.setline(982);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("readline", var3);
      var3 = null;
      var1.setline(983);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("readlines", var3);
      var3 = null;
      var1.setline(984);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("fileno", var3);
      var3 = null;
      var1.setline(985);
      if (var1.getlocal(0).__getattr__("fp").__nonzero__()) {
         var1.setline(985);
         var1.getlocal(0).__getattr__("fp").__getattr__("close").__call__(var2);
      }

      var1.setline(986);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("fp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addclosehook$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to add a close hook to an open file."));
      var1.setline(989);
      PyString.fromInterned("Class to add a close hook to an open file.");
      var1.setline(991);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$60, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(996);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$61, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$60(PyFrame var1, ThreadState var2) {
      var1.setline(992);
      var1.getglobal("addbase").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(993);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("closehook", var3);
      var3 = null;
      var1.setline(994);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("hookargs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$61(PyFrame var1, ThreadState var2) {
      Object var3 = null;

      try {
         var1.setline(998);
         PyObject var4 = var1.getlocal(0).__getattr__("closehook");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(999);
         var4 = var1.getlocal(0).__getattr__("hookargs");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1000);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1001);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("closehook", var4);
            var4 = null;
            var1.setline(1002);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("hookargs", var4);
            var4 = null;
            var1.setline(1003);
            PyObject var10000 = var1.getlocal(1);
            PyObject[] var7 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000._callextra(var7, var5, var1.getlocal(2), (PyObject)null);
            var4 = null;
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(1005);
         var1.getglobal("addbase").__getattr__("close").__call__(var2, var1.getlocal(0));
         throw (Throwable)var6;
      }

      var1.setline(1005);
      var1.getglobal("addbase").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addinfo$62(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("class to add an info() method to an open file."));
      var1.setline(1009);
      PyString.fromInterned("class to add an info() method to an open file.");
      var1.setline(1011);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$63, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1015);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, info$64, (PyObject)null);
      var1.setlocal("info", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$63(PyFrame var1, ThreadState var2) {
      var1.setline(1012);
      var1.getglobal("addbase").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1013);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("headers", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$64(PyFrame var1, ThreadState var2) {
      var1.setline(1016);
      PyObject var3 = var1.getlocal(0).__getattr__("headers");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject addinfourl$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("class to add info() and geturl() methods to an open file."));
      var1.setline(1019);
      PyString.fromInterned("class to add info() and geturl() methods to an open file.");
      var1.setline(1021);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$66, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1027);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, info$67, (PyObject)null);
      var1.setlocal("info", var4);
      var3 = null;
      var1.setline(1030);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcode$68, (PyObject)null);
      var1.setlocal("getcode", var4);
      var3 = null;
      var1.setline(1033);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, geturl$69, (PyObject)null);
      var1.setlocal("geturl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$66(PyFrame var1, ThreadState var2) {
      var1.setline(1022);
      var1.getglobal("addbase").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1023);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("headers", var3);
      var3 = null;
      var1.setline(1024);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("url", var3);
      var3 = null;
      var1.setline(1025);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$67(PyFrame var1, ThreadState var2) {
      var1.setline(1028);
      PyObject var3 = var1.getlocal(0).__getattr__("headers");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcode$68(PyFrame var1, ThreadState var2) {
      var1.setline(1031);
      PyObject var3 = var1.getlocal(0).__getattr__("code");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject geturl$69(PyFrame var1, ThreadState var2) {
      var1.setline(1034);
      PyObject var3 = var1.getlocal(0).__getattr__("url");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _is_unicode$70(PyFrame var1, ThreadState var2) {
      var1.setline(1056);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _is_unicode$71(PyFrame var1, ThreadState var2) {
      var1.setline(1059);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject toBytes$72(PyFrame var1, ThreadState var2) {
      var1.setline(1062);
      PyString.fromInterned("toBytes(u\"URL\") --> 'URL'.");
      var1.setline(1065);
      PyObject var5;
      if (var1.getglobal("_is_unicode").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         PyException var3;
         try {
            var1.setline(1067);
            var5 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ASCII"));
            var1.setlocal(0, var5);
            var3 = null;
         } catch (Throwable var4) {
            var3 = Py.setException(var4, var1);
            if (var3.match(var1.getglobal("UnicodeError"))) {
               var1.setline(1069);
               throw Py.makeException(var1.getglobal("UnicodeError").__call__(var2, PyString.fromInterned("URL ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))._add(PyString.fromInterned(" contains non-ASCII characters"))));
            }

            throw var3;
         }
      }

      var1.setline(1071);
      var5 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject unwrap$73(PyFrame var1, ThreadState var2) {
      var1.setline(1074);
      PyString.fromInterned("unwrap('<URL:type://host/path>') --> 'type://host/path'.");
      var1.setline(1075);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1076);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("<"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned(">"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1077);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__getattr__("strip").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1078);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("URL:"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1078);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(4), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1079);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject splittype$74(PyFrame var1, ThreadState var2) {
      var1.setline(1083);
      PyString.fromInterned("splittype('type:opaquestring') --> 'type', 'opaquestring'.");
      var1.setline(1085);
      PyObject var3 = var1.getglobal("_typeprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1086);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1087);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([^/:]+):"));
         var1.setglobal("_typeprog", var3);
         var3 = null;
      }

      var1.setline(1089);
      var3 = var1.getglobal("_typeprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1090);
      PyTuple var4;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1091);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1092);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("lower").__call__(var2), var1.getlocal(0).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(3))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1093);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splithost$75(PyFrame var1, ThreadState var2) {
      var1.setline(1097);
      PyString.fromInterned("splithost('//host[:port]/path') --> 'host[:port]', '/path'.");
      var1.setline(1099);
      PyObject var3 = var1.getglobal("_hostprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1100);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1101);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^//([^/?]*)(.*)$"));
         var1.setglobal("_hostprog", var3);
         var3 = null;
      }

      var1.setline(1103);
      var3 = var1.getglobal("_hostprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1104);
      PyTuple var4;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1105);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1106);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1107);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1108);
            var3 = PyString.fromInterned("/")._add(var1.getlocal(4));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(1109);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1110);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splituser$76(PyFrame var1, ThreadState var2) {
      var1.setline(1114);
      PyString.fromInterned("splituser('user[:passwd]@host[:port]') --> 'user[:passwd]', 'host[:port]'.");
      var1.setline(1116);
      PyObject var3 = var1.getglobal("_userprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1117);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1118);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(.*)@(.*)$"));
         var1.setglobal("_userprog", var3);
         var3 = null;
      }

      var1.setline(1120);
      var3 = var1.getglobal("_userprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1121);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1121);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1122);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splitpasswd$77(PyFrame var1, ThreadState var2) {
      var1.setline(1126);
      PyString.fromInterned("splitpasswd('user:passwd') -> 'user', 'passwd'.");
      var1.setline(1128);
      PyObject var3 = var1.getglobal("_passwdprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1129);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1130);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([^:]*):(.*)$"), (PyObject)var1.getlocal(1).__getattr__("S"));
         var1.setglobal("_passwdprog", var3);
         var3 = null;
      }

      var1.setline(1132);
      var3 = var1.getglobal("_passwdprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1133);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1133);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1134);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splitport$78(PyFrame var1, ThreadState var2) {
      var1.setline(1139);
      PyString.fromInterned("splitport('host:port') --> 'host', 'port'.");
      var1.setline(1141);
      PyObject var3 = var1.getglobal("_portprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1142);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1143);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(.*):([0-9]*)$"));
         var1.setglobal("_portprog", var3);
         var3 = null;
      }

      var1.setline(1145);
      var3 = var1.getglobal("_portprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1146);
      PyTuple var6;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1147);
         var3 = var1.getlocal(2).__getattr__("groups").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(1148);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1149);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var6;
         }
      }

      var1.setline(1150);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None")});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject splitnport$79(PyFrame var1, ThreadState var2) {
      var1.setline(1157);
      PyString.fromInterned("Split host and port, returning numeric port.\n    Return given default port if no ':' found; defaults to -1.\n    Return numerical port if a valid number are found after ':'.\n    Return None if ':' but not a valid number.");
      var1.setline(1159);
      PyObject var3 = var1.getglobal("_nportprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1160);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1161);
         var3 = var1.getlocal(2).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(.*):(.*)$"));
         var1.setglobal("_nportprog", var3);
         var3 = null;
      }

      var1.setline(1163);
      var3 = var1.getglobal("_nportprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1164);
      PyTuple var8;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1165);
         var3 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(1166);
         if (var1.getlocal(4).__nonzero__()) {
            try {
               var1.setline(1168);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (!var9.match(var1.getglobal("ValueError"))) {
                  throw var9;
               }

               var1.setline(1170);
               PyObject var7 = var1.getglobal("None");
               var1.setlocal(5, var7);
               var4 = null;
            }

            var1.setline(1171);
            var8 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var8;
         }
      }

      var1.setline(1172);
      var8 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject splitquery$80(PyFrame var1, ThreadState var2) {
      var1.setline(1176);
      PyString.fromInterned("splitquery('/path?query') --> '/path', 'query'.");
      var1.setline(1178);
      PyObject var3 = var1.getglobal("_queryprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1179);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1180);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(.*)\\?([^?]*)$"));
         var1.setglobal("_queryprog", var3);
         var3 = null;
      }

      var1.setline(1182);
      var3 = var1.getglobal("_queryprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1183);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1183);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1184);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splittag$81(PyFrame var1, ThreadState var2) {
      var1.setline(1188);
      PyString.fromInterned("splittag('/path#tag') --> '/path', 'tag'.");
      var1.setline(1190);
      PyObject var3 = var1.getglobal("_tagprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1191);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1192);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(.*)#([^#]*)$"));
         var1.setglobal("_tagprog", var3);
         var3 = null;
      }

      var1.setline(1194);
      var3 = var1.getglobal("_tagprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1195);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1195);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1196);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splitattr$82(PyFrame var1, ThreadState var2) {
      var1.setline(1200);
      PyString.fromInterned("splitattr('/path;attr1=value1;attr2=value2;...') ->\n        '/path', ['attr1=value1', 'attr2=value2', ...].");
      var1.setline(1201);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1202);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject splitvalue$83(PyFrame var1, ThreadState var2) {
      var1.setline(1206);
      PyString.fromInterned("splitvalue('attr=value') --> 'attr', 'value'.");
      var1.setline(1208);
      PyObject var3 = var1.getglobal("_valueprog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1209);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1210);
         var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([^=]*)=(.*)$"));
         var1.setglobal("_valueprog", var3);
         var3 = null;
      }

      var1.setline(1212);
      var3 = var1.getglobal("_valueprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1213);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1213);
         var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1214);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject f$84(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1222);
            var3 = var1.getlocal(0).__iter__();
            var1.setline(1222);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var4);
            var1.setline(1222);
            var5 = var1.getglobal("_hexdig").__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var9 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(1222);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(2, var6);
            var1.setline(1221);
            var1.setline(1221);
            PyObject[] var8 = new PyObject[]{var1.getlocal(1)._add(var1.getlocal(2)), var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1)._add(var1.getlocal(2)), (PyObject)Py.newInteger(16)))};
            PyTuple var10 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            var1.f_lasti = 1;
            var7 = new Object[8];
            var7[3] = var3;
            var7[4] = var4;
            var7[5] = var5;
            var7[6] = var6;
            var1.f_savedlocals = var7;
            return var10;
         }

         var1.setline(1222);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1222);
         var5 = var1.getglobal("_hexdig").__iter__();
      }
   }

   public PyObject unquote$85(PyFrame var1, ThreadState var2) {
      var1.setline(1226);
      PyString.fromInterned("unquote('abc%20def') -> 'abc def'.");
      var1.setline(1227);
      PyObject var10000;
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyList var9;
      if (var1.getglobal("_is_unicode").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(1228);
         PyString var8 = PyString.fromInterned("%");
         var10000 = var8._notin(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1229);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1230);
            var4 = var1.getglobal("_asciire").__getattr__("split").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(1231);
            var9 = new PyList(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0))});
            var1.setlocal(2, var9);
            var4 = null;
            var1.setline(1232);
            var4 = var1.getlocal(2).__getattr__("append");
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1233);
            var4 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

            while(true) {
               var1.setline(1233);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1236);
                  var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(1234);
               var1.getlocal(3).__call__(var2, var1.getglobal("unquote").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(4)))).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("latin1")));
               var1.setline(1235);
               var1.getlocal(3).__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1))));
            }
         }
      } else {
         var1.setline(1238);
         var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1240);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1241);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1242);
            var9 = new PyList(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0))});
            var1.setlocal(2, var9);
            var4 = null;
            var1.setline(1243);
            var4 = var1.getlocal(2).__getattr__("append");
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1244);
            var4 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

            while(true) {
               var1.setline(1244);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1251);
                  var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(5, var5);

               try {
                  var1.setline(1246);
                  var1.getlocal(3).__call__(var2, var1.getglobal("_hextochr").__getitem__(var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null)));
                  var1.setline(1247);
                  var1.getlocal(3).__call__(var2, var1.getlocal(5).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
               } catch (Throwable var7) {
                  PyException var6 = Py.setException(var7, var1);
                  if (!var6.match(var1.getglobal("KeyError"))) {
                     throw var6;
                  }

                  var1.setline(1249);
                  var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
                  var1.setline(1250);
                  var1.getlocal(3).__call__(var2, var1.getlocal(5));
               }
            }
         }
      }
   }

   public PyObject unquote_plus$86(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      PyString.fromInterned("unquote('%7e/abc+def') -> '~/abc def'");
      var1.setline(1255);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+"), (PyObject)PyString.fromInterned(" "));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1256);
      var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quote$87(PyFrame var1, ThreadState var2) {
      var1.setline(1286);
      PyString.fromInterned("quote('abc def') -> 'abc%20def'\n\n    Each part of a URL, e.g. the path info, the query, etc., has a\n    different set of reserved characters that must be quoted.\n\n    RFC 2396 Uniform Resource Identifiers (URI): Generic Syntax lists\n    the following reserved characters.\n\n    reserved    = \";\" | \"/\" | \"?\" | \":\" | \"@\" | \"&\" | \"=\" | \"+\" |\n                  \"$\" | \",\"\n\n    Each of these characters is reserved in some component of a URL,\n    but not necessarily in all of them.\n\n    By default, the quote function is intended for quoting the path\n    section of a URL.  Thus, it will not encode '/'.  This character\n    is reserved, but in typical usage the quote function is being\n    called on a path where the existing slash characters are used as\n    reserved characters.\n    ");
      var1.setline(1288);
      PyObject var3;
      PyObject var10000;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(1289);
         var3 = var1.getlocal(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1290);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("None object cannot be quoted")));
         } else {
            var1.setline(1291);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(1292);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("always_safe")});
         var1.setlocal(2, var4);
         var4 = null;

         PyObject var6;
         try {
            var1.setline(1294);
            PyObject var9 = var1.getglobal("_safe_quoters").__getitem__(var1.getlocal(2));
            PyObject[] var11 = Py.unpackSequence(var9, 2);
            var6 = var11[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(1, var6);
            var6 = null;
            var4 = null;
         } catch (Throwable var7) {
            PyException var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("KeyError"))) {
               throw var8;
            }

            var1.setline(1296);
            PyObject var5 = var1.getglobal("_safe_map").__getattr__("copy").__call__(var2);
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(1297);
            var10000 = var1.getlocal(4).__getattr__("update");
            PyList var10002 = new PyList();
            var5 = var10002.__getattr__("append");
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(1297);
            var5 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(1297);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(1297);
                  var1.dellocal(5);
                  var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.setline(1298);
                  var5 = var1.getlocal(4).__getattr__("__getitem__");
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(1299);
                  var5 = var1.getglobal("always_safe")._add(var1.getlocal(1));
                  var1.setlocal(1, var5);
                  var5 = null;
                  var1.setline(1300);
                  PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1)});
                  var1.getglobal("_safe_quoters").__setitem__((PyObject)var1.getlocal(2), var10);
                  var5 = null;
                  break;
               }

               var1.setlocal(6, var6);
               var1.setline(1297);
               var1.getlocal(5).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(6)})));
            }
         }

         var1.setline(1301);
         if (var1.getlocal(0).__getattr__("rstrip").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(1302);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1303);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getlocal(3), var1.getlocal(0)));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject quote_plus$88(PyFrame var1, ThreadState var2) {
      var1.setline(1306);
      PyString.fromInterned("Quote the query fragment of a URL; replacing ' ' with '+'");
      var1.setline(1307);
      PyString var3 = PyString.fromInterned(" ");
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(1308);
         var4 = var1.getglobal("quote").__call__(var2, var1.getlocal(0), var1.getlocal(1)._add(PyString.fromInterned(" ")));
         var1.setlocal(0, var4);
         var3 = null;
         var1.setline(1309);
         var4 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("+"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1310);
         var4 = var1.getglobal("quote").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject urlencode$89(PyFrame var1, ThreadState var2) {
      var1.setline(1321);
      PyString.fromInterned("Encode a sequence of two-element tuples or dictionary into a URL query string.\n\n    If any values in the query arg are sequences and doseq is true, each\n    sequence element is converted to a separate parameter.\n\n    If the query arg is a sequence of two-element tuples, the order of the\n    parameters in the output will match the order of parameters in the\n    input.\n    ");
      var1.setline(1323);
      PyObject var3;
      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("items")).__nonzero__()) {
         var1.setline(1325);
         var3 = var1.getlocal(0).__getattr__("items").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      } else {
         try {
            var1.setline(1332);
            PyObject var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getglobal("tuple")).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1333);
               throw Py.makeException(var1.getglobal("TypeError"));
            }
         } catch (Throwable var8) {
            PyException var10 = Py.setException(var8, var1);
            if (var10.match(var1.getglobal("TypeError"))) {
               var1.setline(1339);
               var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(1340);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not a valid non-string sequence or mapping object"), var1.getlocal(4));
            }

            throw var10;
         }
      }

      var1.setline(1342);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var11);
      var3 = null;
      var1.setline(1343);
      PyObject var12;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1345);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(1345);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(1346);
            var12 = var1.getglobal("quote_plus").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6)));
            var1.setlocal(6, var12);
            var5 = null;
            var1.setline(1347);
            var12 = var1.getglobal("quote_plus").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(7)));
            var1.setlocal(7, var12);
            var5 = null;
            var1.setline(1348);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("="))._add(var1.getlocal(7)));
         }
      } else {
         var1.setline(1350);
         var3 = var1.getlocal(0).__iter__();

         label72:
         while(true) {
            while(true) {
               var1.setline(1350);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break label72;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(1351);
               var12 = var1.getglobal("quote_plus").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6)));
               var1.setlocal(6, var12);
               var5 = null;
               var1.setline(1352);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("str")).__nonzero__()) {
                  var1.setline(1353);
                  var12 = var1.getglobal("quote_plus").__call__(var2, var1.getlocal(7));
                  var1.setlocal(7, var12);
                  var5 = null;
                  var1.setline(1354);
                  var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("="))._add(var1.getlocal(7)));
               } else {
                  var1.setline(1355);
                  if (var1.getglobal("_is_unicode").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                     var1.setline(1359);
                     var12 = var1.getglobal("quote_plus").__call__(var2, var1.getlocal(7).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ASCII"), (PyObject)PyString.fromInterned("replace")));
                     var1.setlocal(7, var12);
                     var5 = null;
                     var1.setline(1360);
                     var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("="))._add(var1.getlocal(7)));
                  } else {
                     try {
                        var1.setline(1364);
                        var1.getglobal("len").__call__(var2, var1.getlocal(7));
                     } catch (Throwable var9) {
                        PyException var13 = Py.setException(var9, var1);
                        if (var13.match(var1.getglobal("TypeError"))) {
                           var1.setline(1367);
                           var6 = var1.getglobal("quote_plus").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(7)));
                           var1.setlocal(7, var6);
                           var6 = null;
                           var1.setline(1368);
                           var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("="))._add(var1.getlocal(7)));
                           continue;
                        }

                        throw var13;
                     }

                     var1.setline(1371);
                     var6 = var1.getlocal(7).__iter__();

                     while(true) {
                        var1.setline(1371);
                        PyObject var7 = var6.__iternext__();
                        if (var7 == null) {
                           break;
                        }

                        var1.setlocal(8, var7);
                        var1.setline(1372);
                        var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("="))._add(var1.getglobal("quote_plus").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(8)))));
                     }
                  }
               }
            }
         }
      }

      var1.setline(1373);
      var3 = PyString.fromInterned("&").__getattr__("join").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getproxies_environment$90(PyFrame var1, ThreadState var2) {
      var1.setline(1386);
      PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n    Scan the environment for variables named <scheme>_proxy;\n    this seems to be the standard convention.  In order to prefer lowercase\n    variables, we process the environment in two passes, first matches any\n    and second matches only lower case proxies.\n\n    If you need a different way, you can pass a proxies dictionary to the\n    [Fancy]URLopener constructor.\n    ");
      var1.setline(1388);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1389);
      PyObject var7 = var1.getglobal("os").__getattr__("environ").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1389);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         if (var4 == null) {
            var1.setline(1398);
            PyString var8 = PyString.fromInterned("REQUEST_METHOD");
            var10000 = var8._in(var1.getglobal("os").__getattr__("environ"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1399);
               var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http"), (PyObject)var1.getglobal("None"));
            }

            var1.setline(1402);
            var7 = var1.getglobal("os").__getattr__("environ").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(1402);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(1410);
                  var7 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var7;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(1, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(2, var6);
               var6 = null;
               var1.setline(1403);
               var9 = var1.getlocal(1).__getslice__(Py.newInteger(-6), (PyObject)null, (PyObject)null);
               var10000 = var9._eq(PyString.fromInterned("_proxy"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1404);
                  var9 = var1.getlocal(1).__getattr__("lower").__call__(var2);
                  var1.setlocal(1, var9);
                  var5 = null;
                  var1.setline(1405);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(1406);
                     var9 = var1.getlocal(2);
                     var1.getlocal(0).__setitem__(var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-6), (PyObject)null), var9);
                     var5 = null;
                  } else {
                     var1.setline(1408);
                     var1.getlocal(0).__getattr__("pop").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-6), (PyObject)null), var1.getglobal("None"));
                  }
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(1390);
         var9 = var1.getlocal(1).__getattr__("lower").__call__(var2);
         var1.setlocal(1, var9);
         var5 = null;
         var1.setline(1391);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var9 = var1.getlocal(1).__getslice__(Py.newInteger(-6), (PyObject)null, (PyObject)null);
            var10000 = var9._eq(PyString.fromInterned("_proxy"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1392);
            var9 = var1.getlocal(2);
            var1.getlocal(0).__setitem__(var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-6), (PyObject)null), var9);
            var5 = null;
         }
      }
   }

   public PyObject proxy_bypass_environment$91(PyFrame var1, ThreadState var2) {
      var1.setline(1417);
      PyString.fromInterned("Test if proxies should not be used for a particular host.\n\n    Checks the proxies dict for the value of no_proxy, which should be a\n    list of comma separated DNS suffixes, or '*' for all hosts.\n    ");
      var1.setline(1418);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1419);
         var3 = var1.getglobal("getproxies_environment").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      PyInteger var4;
      try {
         var1.setline(1422);
         var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("no"));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (var8.match(var1.getglobal("KeyError"))) {
            var1.setline(1424);
            var4 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var8;
      }

      var1.setline(1426);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(PyString.fromInterned("*"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1427);
         var4 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1429);
         var3 = var1.getglobal("splitport").__call__(var2, var1.getlocal(0));
         PyObject[] var5 = Py.unpackSequence(var3, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var3 = null;
         var1.setline(1431);
         PyList var11 = new PyList();
         var3 = var11.__getattr__("append");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1431);
         var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

         while(true) {
            var1.setline(1431);
            PyObject var9 = var3.__iternext__();
            if (var9 == null) {
               var1.setline(1431);
               var1.dellocal(6);
               PyList var10 = var11;
               var1.setlocal(5, var10);
               var3 = null;
               var1.setline(1432);
               var3 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(1432);
                  var9 = var3.__iternext__();
                  if (var9 == null) {
                     var1.setline(1440);
                     var4 = Py.newInteger(0);
                     var1.f_lasti = -1;
                     return var4;
                  }

                  var1.setlocal(8, var9);
                  var1.setline(1433);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1434);
                     var6 = var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(8));
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(1435);
                     var6 = PyString.fromInterned("(.+\\.)?%s$")._mod(var1.getlocal(8));
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(1436);
                     var10000 = var1.getglobal("re").__getattr__("match").__call__(var2, var1.getlocal(9), var1.getlocal(3), var1.getglobal("re").__getattr__("I"));
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("re").__getattr__("match").__call__(var2, var1.getlocal(9), var1.getlocal(0), var1.getglobal("re").__getattr__("I"));
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(1438);
                        var4 = Py.newInteger(1);
                        var1.f_lasti = -1;
                        return var4;
                     }
                  }
               }
            }

            var1.setlocal(7, var9);
            var1.setline(1431);
            var1.getlocal(6).__call__(var2, var1.getlocal(7).__getattr__("strip").__call__(var2));
         }
      }
   }

   public PyObject proxy_bypass_macosx_sysconf$92(PyFrame var1, ThreadState var2) {
      var1.setline(1452);
      PyString.fromInterned("\n        Return True iff this host shouldn't be accessed using a proxy\n\n        This function uses the MacOSX framework SystemConfiguration\n        to fetch the proxy information.\n        ");
      var1.setline(1453);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1454);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1455);
      String[] var8 = new String[]{"fnmatch"};
      PyObject[] var9 = imp.importFrom("fnmatch", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(1457);
      var3 = var1.getglobal("splitport").__call__(var2, var1.getlocal(0));
      PyObject[] var10 = Py.unpackSequence(var3, 2);
      PyObject var5 = var10[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var10[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1459);
      var9 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var9, ip2num$93, (PyObject)null);
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(1466);
      var3 = var1.getglobal("_get_proxy_settings").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1469);
      PyString var12 = PyString.fromInterned(".");
      PyObject var10000 = var12._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1470);
         if (var1.getlocal(7).__getitem__(PyString.fromInterned("exclude_simple")).__nonzero__()) {
            var1.setline(1471);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(1473);
      var4 = var1.getglobal("None");
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(1475);
      var4 = var1.getlocal(7).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exceptions"), (PyObject)(new PyTuple(Py.EmptyObjects))).__iter__();

      do {
         PyObject var6;
         label56:
         while(true) {
            while(true) {
               do {
                  var1.setline(1475);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(1503);
                     var3 = var1.getglobal("False");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(9, var5);
                  var1.setline(1477);
               } while(var1.getlocal(9).__not__().__nonzero__());

               var1.setline(1479);
               var6 = var1.getlocal(1).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+(?:\\.\\d+)*)(/\\d+)?"), (PyObject)var1.getlocal(9));
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(1480);
               var6 = var1.getlocal(10);
               var10000 = var6._isnot(var1.getglobal("None"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1481);
                  var6 = var1.getlocal(8);
                  var10000 = var6._is(var1.getglobal("None"));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     break label56;
                  }

                  try {
                     var1.setline(1483);
                     var6 = var1.getlocal(2).__getattr__("gethostbyname").__call__(var2, var1.getlocal(4));
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(1484);
                     var6 = var1.getlocal(6).__call__(var2, var1.getlocal(8));
                     var1.setlocal(8, var6);
                     var6 = null;
                     break label56;
                  } catch (Throwable var7) {
                     PyException var13 = Py.setException(var7, var1);
                     if (!var13.match(var1.getlocal(2).__getattr__("error"))) {
                        throw var13;
                     }
                  }
               } else {
                  var1.setline(1500);
                  if (var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(9)).__nonzero__()) {
                     var1.setline(1501);
                     var3 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }

         var1.setline(1488);
         var6 = var1.getlocal(6).__call__(var2, var1.getlocal(10).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(1489);
         var6 = var1.getlocal(10).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
         var1.setlocal(12, var6);
         var6 = null;
         var1.setline(1490);
         var6 = var1.getlocal(12);
         var10000 = var6._is(var1.getglobal("None"));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1491);
            var6 = Py.newInteger(8)._mul(var1.getlocal(10).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."))._add(Py.newInteger(1)));
            var1.setlocal(12, var6);
            var6 = null;
         } else {
            var1.setline(1494);
            var6 = var1.getglobal("int").__call__(var2, var1.getlocal(12).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(12, var6);
            var6 = null;
         }

         var1.setline(1495);
         var6 = Py.newInteger(32)._sub(var1.getlocal(12));
         var1.setlocal(12, var6);
         var6 = null;
         var1.setline(1497);
         var6 = var1.getlocal(8)._rshift(var1.getlocal(12));
         var10000 = var6._eq(var1.getlocal(11)._rshift(var1.getlocal(12)));
         var6 = null;
      } while(!var10000.__nonzero__());

      var1.setline(1498);
      var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ip2num$93(PyFrame var1, ThreadState var2) {
      var1.setline(1460);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1461);
      var3 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1462);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ne(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1463);
         var3 = var1.getlocal(1)._add(new PyList(new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)})).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1464);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0))._lshift(Py.newInteger(24))._or(var1.getlocal(1).__getitem__(Py.newInteger(1))._lshift(Py.newInteger(16)))._or(var1.getlocal(1).__getitem__(Py.newInteger(2))._lshift(Py.newInteger(8)))._or(var1.getlocal(1).__getitem__(Py.newInteger(3)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getproxies_macosx_sysconf$94(PyFrame var1, ThreadState var2) {
      var1.setline(1510);
      PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n        This function uses the MacOSX framework SystemConfiguration\n        to fetch the proxy information.\n        ");
      var1.setline(1511);
      PyObject var3 = var1.getglobal("_get_proxies").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject proxy_bypass$95(PyFrame var1, ThreadState var2) {
      var1.setline(1518);
      PyString.fromInterned("Return True, if a host should be bypassed.\n\n        Checks proxy settings gathered from the environment, if specified, or\n        from the MacOSX framework SystemConfiguration.\n        ");
      var1.setline(1519);
      PyObject var3 = var1.getglobal("getproxies_environment").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1520);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1521);
         var3 = var1.getglobal("proxy_bypass_environment").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1523);
         var3 = var1.getglobal("proxy_bypass_macosx_sysconf").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getproxies$96(PyFrame var1, ThreadState var2) {
      var1.setline(1526);
      PyObject var10000 = var1.getglobal("getproxies_environment").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("getproxies_macosx_sysconf").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getproxies_registry$97(PyFrame var1, ThreadState var2) {
      var1.setline(1534);
      PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n        Win32 uses the registry to store proxies.\n\n        ");
      var1.setline(1535);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;

      PyObject var4;
      PyException var11;
      PyObject var12;
      try {
         var1.setline(1537);
         var12 = imp.importOne("_winreg", var1, -1);
         var1.setlocal(1, var12);
         var3 = null;
      } catch (Throwable var9) {
         var11 = Py.setException(var9, var1);
         if (var11.match(var1.getglobal("ImportError"))) {
            var1.setline(1540);
            var4 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var11;
      }

      try {
         var1.setline(1542);
         var12 = var1.getlocal(1).__getattr__("OpenKey").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("HKEY_CURRENT_USER"), (PyObject)PyString.fromInterned("Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings"));
         var1.setlocal(2, var12);
         var3 = null;
         var1.setline(1544);
         var12 = var1.getlocal(1).__getattr__("QueryValueEx").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("ProxyEnable")).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var12);
         var3 = null;
         var1.setline(1546);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1548);
            var12 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("QueryValueEx").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("ProxyServer")).__getitem__(Py.newInteger(0)));
            var1.setlocal(4, var12);
            var3 = null;
            var1.setline(1550);
            PyString var13 = PyString.fromInterned("=");
            PyObject var10000 = var13._in(var1.getlocal(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1552);
               var12 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

               while(true) {
                  var1.setline(1552);
                  PyObject var5 = var12.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var1.setlocal(5, var5);
                  var1.setline(1553);
                  PyObject var6 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
                  PyObject[] var7 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(6, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(7, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(1555);
                  var6 = imp.importOne("re", var1, -1);
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(1556);
                  if (var1.getlocal(8).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([^/:]+)://"), (PyObject)var1.getlocal(7)).__not__().__nonzero__()) {
                     var1.setline(1557);
                     var6 = PyString.fromInterned("%s://%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)}));
                     var1.setlocal(7, var6);
                     var6 = null;
                  }

                  var1.setline(1558);
                  var6 = var1.getlocal(7);
                  var1.getlocal(0).__setitem__(var1.getlocal(6), var6);
                  var6 = null;
               }
            } else {
               var1.setline(1561);
               var12 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
               var10000 = var12._eq(PyString.fromInterned("http:"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1562);
                  var12 = var1.getlocal(4);
                  var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("http"), var12);
                  var3 = null;
               } else {
                  var1.setline(1564);
                  var12 = PyString.fromInterned("http://%s")._mod(var1.getlocal(4));
                  var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("http"), var12);
                  var3 = null;
                  var1.setline(1565);
                  var12 = PyString.fromInterned("https://%s")._mod(var1.getlocal(4));
                  var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("https"), var12);
                  var3 = null;
                  var1.setline(1566);
                  var12 = PyString.fromInterned("ftp://%s")._mod(var1.getlocal(4));
                  var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("ftp"), var12);
                  var3 = null;
               }
            }
         }

         var1.setline(1567);
         var1.getlocal(2).__getattr__("Close").__call__(var2);
      } catch (Throwable var10) {
         var11 = Py.setException(var10, var1);
         if (!var11.match(new PyTuple(new PyObject[]{var1.getglobal("WindowsError"), var1.getglobal("ValueError"), var1.getglobal("TypeError")}))) {
            throw var11;
         }

         var1.setline(1572);
      }

      var1.setline(1573);
      var4 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getproxies$98(PyFrame var1, ThreadState var2) {
      var1.setline(1581);
      PyString.fromInterned("Return a dictionary of scheme -> proxy server URL mappings.\n\n        Returns settings gathered from the environment, if specified,\n        or the registry.\n\n        ");
      var1.setline(1582);
      PyObject var10000 = var1.getglobal("getproxies_environment").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("getproxies_registry").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject proxy_bypass_registry$99(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyInteger var4;
      PyObject var12;
      try {
         var1.setline(1586);
         var12 = imp.importOne("_winreg", var1, -1);
         var1.setlocal(1, var12);
         var3 = null;
         var1.setline(1587);
         var12 = imp.importOne("re", var1, -1);
         var1.setlocal(2, var12);
         var3 = null;
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(1590);
            var4 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      try {
         var1.setline(1592);
         var12 = var1.getlocal(1).__getattr__("OpenKey").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("HKEY_CURRENT_USER"), (PyObject)PyString.fromInterned("Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings"));
         var1.setlocal(3, var12);
         var3 = null;
         var1.setline(1594);
         var12 = var1.getlocal(1).__getattr__("QueryValueEx").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("ProxyEnable")).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var12);
         var3 = null;
         var1.setline(1596);
         var12 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("QueryValueEx").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("ProxyOverride")).__getitem__(Py.newInteger(0)));
         var1.setlocal(5, var12);
         var3 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (var3.match(var1.getglobal("WindowsError"))) {
            var1.setline(1600);
            var4 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(1601);
      PyObject var10000 = var1.getlocal(4).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1602);
         var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1604);
         var12 = var1.getglobal("splitport").__call__(var2, var1.getlocal(0));
         PyObject[] var5 = Py.unpackSequence(var12, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var3 = null;
         var1.setline(1605);
         PyList var14 = new PyList(new PyObject[]{var1.getlocal(6)});
         var1.setlocal(0, var14);
         var3 = null;

         try {
            var1.setline(1607);
            var12 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getlocal(6));
            var1.setlocal(8, var12);
            var3 = null;
            var1.setline(1608);
            var12 = var1.getlocal(8);
            var10000 = var12._ne(var1.getlocal(6));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1609);
               var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(8));
            }
         } catch (Throwable var11) {
            var3 = Py.setException(var11, var1);
            if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var3;
            }

            var1.setline(1611);
         }

         try {
            var1.setline(1613);
            var12 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2, var1.getlocal(6));
            var1.setlocal(9, var12);
            var3 = null;
            var1.setline(1614);
            var12 = var1.getlocal(9);
            var10000 = var12._ne(var1.getlocal(6));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1615);
               var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(9));
            }
         } catch (Throwable var10) {
            var3 = Py.setException(var10, var1);
            if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var3;
            }

            var1.setline(1617);
         }

         var1.setline(1621);
         var12 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
         var1.setlocal(5, var12);
         var3 = null;
         var1.setline(1623);
         var12 = var1.getlocal(5).__iter__();

         label73:
         while(true) {
            var1.setline(1623);
            PyObject var13 = var12.__iternext__();
            if (var13 == null) {
               var1.setline(1634);
               var4 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var4;
            }

            var1.setlocal(10, var13);
            var1.setline(1624);
            var6 = var1.getlocal(10);
            var10000 = var6._eq(PyString.fromInterned("<local>"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1625);
               PyString var15 = PyString.fromInterned(".");
               var10000 = var15._notin(var1.getlocal(6));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1626);
                  var4 = Py.newInteger(1);
                  var1.f_lasti = -1;
                  return var4;
               }
            }

            var1.setline(1627);
            var6 = var1.getlocal(10).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)PyString.fromInterned("\\."));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(1628);
            var6 = var1.getlocal(10).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*"), (PyObject)PyString.fromInterned(".*"));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(1629);
            var6 = var1.getlocal(10).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"), (PyObject)PyString.fromInterned("."));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(1630);
            var6 = var1.getlocal(0).__iter__();

            do {
               var1.setline(1630);
               PyObject var7 = var6.__iternext__();
               if (var7 == null) {
                  continue label73;
               }

               var1.setlocal(11, var7);
               var1.setline(1632);
            } while(!var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(10), var1.getlocal(11), var1.getlocal(2).__getattr__("I")).__nonzero__());

            var1.setline(1633);
            var4 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject proxy_bypass$100(PyFrame var1, ThreadState var2) {
      var1.setline(1641);
      PyString.fromInterned("Return True, if the host should be bypassed.\n\n        Checks proxy settings gathered from the environment, if specified,\n        or the registry.\n        ");
      var1.setline(1642);
      PyObject var3 = var1.getglobal("getproxies_environment").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1643);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1644);
         var3 = var1.getglobal("proxy_bypass_environment").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1646);
         var3 = var1.getglobal("proxy_bypass_registry").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject test1$101(PyFrame var1, ThreadState var2) {
      var1.setline(1655);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1656);
      PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(1656);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1657);
            var6 = var1.getlocal(0)._mul(Py.newInteger(4));
            var1.setlocal(0, var6);
            var3 = null;
            var1.setline(1658);
            var6 = var1.getglobal("time").__getattr__("time").__call__(var2);
            var1.setlocal(2, var6);
            var3 = null;
            var1.setline(1659);
            var6 = var1.getglobal("quote").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(1660);
            var6 = var1.getglobal("unquote").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(1661);
            var6 = var1.getglobal("time").__getattr__("time").__call__(var2);
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(1662);
            var6 = var1.getlocal(4);
            PyObject var10000 = var6._ne(var1.getlocal(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1663);
               Py.println(PyString.fromInterned("Wrong!"));
            }

            var1.setline(1664);
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(0)));
            var1.setline(1665);
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(3)));
            var1.setline(1666);
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(4)));
            var1.setline(1667);
            Py.printComma(var1.getglobal("round").__call__((ThreadState)var2, (PyObject)var1.getlocal(5)._sub(var1.getlocal(2)), (PyObject)Py.newInteger(3)));
            Py.println(PyString.fromInterned("sec"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1656);
         PyObject var5 = var1.getlocal(0)._add(var1.getglobal("chr").__call__(var2, var1.getlocal(1)));
         var1.setlocal(0, var5);
         var5 = null;
      }
   }

   public PyObject reporthook$102(PyFrame var1, ThreadState var2) {
      var1.setline(1672);
      Py.println(PyString.fromInterned("Block number: %d, Block size: %d, Total size: %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public urllib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pathname"};
      url2pathname$1 = Py.newCode(1, var2, var1, "url2pathname", 57, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pathname"};
      pathname2url$2 = Py.newCode(1, var2, var1, "pathname2url", 62, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "data", "proxies", "context", "warnpy3k", "opener"};
      urlopen$3 = Py.newCode(4, var2, var1, "urlopen", 76, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "filename", "reporthook", "data", "context", "opener"};
      urlretrieve$4 = Py.newCode(5, var2, var1, "urlretrieve", 94, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      urlcleanup$5 = Py.newCode(0, var2, var1, "urlcleanup", 103, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ContentTooShortError$6 = Py.newCode(0, var2, var1, "ContentTooShortError", 118, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message", "content"};
      __init__$7 = Py.newCode(3, var2, var1, "__init__", 119, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      URLopener$8 = Py.newCode(0, var2, var1, "URLopener", 124, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "proxies", "context", "x509"};
      __init__$9 = Py.newCode(4, var2, var1, "__init__", 137, false, true, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$10 = Py.newCode(1, var2, var1, "__del__", 161, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$11 = Py.newCode(1, var2, var1, "close", 164, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      cleanup$12 = Py.newCode(1, var2, var1, "cleanup", 167, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addheader$13 = Py.newCode(2, var2, var1, "addheader", 181, true, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullurl", "data", "filename", "headers", "fp", "urltype", "url", "proxy", "proxyhost", "host", "selector", "name", "msg"};
      open$14 = Py.newCode(3, var2, var1, "open", 187, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullurl", "data", "type", "url"};
      open_unknown$15 = Py.newCode(3, var2, var1, "open_unknown", 223, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "proxy", "fullurl", "data", "type", "url"};
      open_unknown_proxy$16 = Py.newCode(4, var2, var1, "open_unknown_proxy", 228, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "filename", "reporthook", "data", "type", "url1", "fp", "hdrs", "headers", "tfp", "tempfile", "garbage", "path", "suffix", "fd", "result", "bs", "size", "read", "blocknum", "block"};
      retrieve$17 = Py.newCode(5, var2, var1, "retrieve", 234, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "data", "httplib", "user_passwd", "proxy_passwd", "host", "selector", "realhost", "urltype", "rest", "proxy_auth", "auth", "h", "args", "errcode", "errmsg", "headers", "fp"};
      open_http$18 = Py.newCode(3, var2, var1, "open_http", 299, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data", "name", "method", "result"};
      http_error$19 = Py.newCode(7, var2, var1, "http_error", 372, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers"};
      http_error_default$20 = Py.newCode(6, var2, var1, "http_error_default", 387, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "data", "httplib", "user_passwd", "proxy_passwd", "host", "selector", "realhost", "urltype", "rest", "proxy_auth", "auth", "h", "args", "errcode", "errmsg", "headers", "fp"};
      open_https$21 = Py.newCode(3, var2, var1, "open_https", 393, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url"};
      open_file$22 = Py.newCode(2, var2, var1, "open_file", 466, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "mimetypes", "mimetools", "email", "StringIO", "host", "file", "localname", "stats", "e", "size", "modified", "mtype", "headers", "urlfile", "port"};
      open_local_file$23 = Py.newCode(2, var2, var1, "open_local_file", 475, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "mimetypes", "mimetools", "StringIO", "host", "path", "port", "user", "passwd", "ftplib", "attrs", "dirs", "file", "key", "k", "v", "type", "attr", "value", "fp", "retrlen", "mtype", "headers", "msg"};
      open_ftp$24 = Py.newCode(2, var2, var1, "open_ftp", 512, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "data", "mimetools", "StringIO", "type", "semi", "encoding", "msg", "f", "headers"};
      open_data$25 = Py.newCode(3, var2, var1, "open_data", 574, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FancyURLopener$26 = Py.newCode(0, var2, var1, "FancyURLopener", 620, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwargs"};
      __init__$27 = Py.newCode(3, var2, var1, "__init__", 623, true, true, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers"};
      http_error_default$28 = Py.newCode(6, var2, var1, "http_error_default", 629, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data", "meth", "result"};
      http_error_302$29 = Py.newCode(7, var2, var1, "http_error_302", 633, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data", "newurl", "newurl_lower"};
      redirect_internal$30 = Py.newCode(7, var2, var1, "redirect_internal", 651, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data"};
      http_error_301$31 = Py.newCode(7, var2, var1, "http_error_301", 675, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data"};
      http_error_303$32 = Py.newCode(7, var2, var1, "http_error_303", 679, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data"};
      http_error_307$33 = Py.newCode(7, var2, var1, "http_error_307", 683, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data", "stuff", "re", "match", "scheme", "realm", "name"};
      http_error_401$34 = Py.newCode(7, var2, var1, "http_error_401", 690, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers", "data", "stuff", "re", "match", "scheme", "realm", "name"};
      http_error_407$35 = Py.newCode(7, var2, var1, "http_error_407", 712, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "realm", "data", "host", "selector", "newurl", "proxy", "urltype", "proxyhost", "proxyselector", "i", "user", "passwd"};
      retry_proxy_http_basic_auth$36 = Py.newCode(4, var2, var1, "retry_proxy_http_basic_auth", 734, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "realm", "data", "host", "selector", "newurl", "proxy", "urltype", "proxyhost", "proxyselector", "i", "user", "passwd"};
      retry_proxy_https_basic_auth$37 = Py.newCode(4, var2, var1, "retry_proxy_https_basic_auth", 751, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "realm", "data", "host", "selector", "i", "user", "passwd", "newurl"};
      retry_http_basic_auth$38 = Py.newCode(4, var2, var1, "retry_http_basic_auth", 768, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "realm", "data", "host", "selector", "i", "user", "passwd", "newurl"};
      retry_https_basic_auth$39 = Py.newCode(4, var2, var1, "retry_https_basic_auth", 781, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "realm", "clear_cache", "key", "user", "passwd"};
      get_user_passwd$40 = Py.newCode(4, var2, var1, "get_user_passwd", 794, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "realm", "getpass", "user", "passwd"};
      prompt_user_passwd$41 = Py.newCode(3, var2, var1, "prompt_user_passwd", 805, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      localhost$42 = Py.newCode(0, var2, var1, "localhost", 822, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      thishost$43 = Py.newCode(0, var2, var1, "thishost", 830, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ftplib"};
      ftperrors$44 = Py.newCode(0, var2, var1, "ftperrors", 841, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mimetools", "StringIO"};
      noheaders$45 = Py.newCode(0, var2, var1, "noheaders", 850, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ftpwrapper$46 = Py.newCode(0, var2, var1, "ftpwrapper", 866, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "user", "passwd", "host", "port", "dirs", "timeout", "persistent"};
      __init__$47 = Py.newCode(8, var2, var1, "__init__", 869, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ftplib", "_target"};
      init$48 = Py.newCode(1, var2, var1, "init", 886, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "type", "ftplib", "cmd", "isdir", "conn", "retrlen", "reason", "pwd", "ftpobj"};
      retrfile$49 = Py.newCode(3, var2, var1, "retrfile", 895, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endtransfer$50 = Py.newCode(1, var2, var1, "endtransfer", 938, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$51 = Py.newCode(1, var2, var1, "close", 941, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      file_close$52 = Py.newCode(1, var2, var1, "file_close", 946, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      real_close$53 = Py.newCode(1, var2, var1, "real_close", 952, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      addbase$54 = Py.newCode(0, var2, var1, "addbase", 959, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      __init__$55 = Py.newCode(2, var2, var1, "__init__", 962, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$56 = Py.newCode(0, var2, var1, "<lambda>", 970, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$57 = Py.newCode(1, var2, var1, "__repr__", 976, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$58 = Py.newCode(1, var2, var1, "close", 980, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      addclosehook$59 = Py.newCode(0, var2, var1, "addclosehook", 988, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "closehook", "hookargs"};
      __init__$60 = Py.newCode(4, var2, var1, "__init__", 991, true, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "closehook", "hookargs"};
      close$61 = Py.newCode(1, var2, var1, "close", 996, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      addinfo$62 = Py.newCode(0, var2, var1, "addinfo", 1008, false, false, self, 62, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "headers"};
      __init__$63 = Py.newCode(3, var2, var1, "__init__", 1011, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      info$64 = Py.newCode(1, var2, var1, "info", 1015, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      addinfourl$65 = Py.newCode(0, var2, var1, "addinfourl", 1018, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "headers", "url", "code"};
      __init__$66 = Py.newCode(5, var2, var1, "__init__", 1021, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      info$67 = Py.newCode(1, var2, var1, "info", 1027, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcode$68 = Py.newCode(1, var2, var1, "getcode", 1030, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      geturl$69 = Py.newCode(1, var2, var1, "geturl", 1033, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      _is_unicode$70 = Py.newCode(1, var2, var1, "_is_unicode", 1055, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      _is_unicode$71 = Py.newCode(1, var2, var1, "_is_unicode", 1058, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url"};
      toBytes$72 = Py.newCode(1, var2, var1, "toBytes", 1061, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url"};
      unwrap$73 = Py.newCode(1, var2, var1, "unwrap", 1073, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "re", "match", "scheme"};
      splittype$74 = Py.newCode(1, var2, var1, "splittype", 1082, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "re", "match", "host_port", "path"};
      splithost$75 = Py.newCode(1, var2, var1, "splithost", 1096, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "re", "match"};
      splituser$76 = Py.newCode(1, var2, var1, "splituser", 1113, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"user", "re", "match"};
      splitpasswd$77 = Py.newCode(1, var2, var1, "splitpasswd", 1125, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "re", "match", "port"};
      splitport$78 = Py.newCode(1, var2, var1, "splitport", 1138, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "defport", "re", "match", "port", "nport"};
      splitnport$79 = Py.newCode(2, var2, var1, "splitnport", 1153, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "re", "match"};
      splitquery$80 = Py.newCode(1, var2, var1, "splitquery", 1175, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "re", "match"};
      splittag$81 = Py.newCode(1, var2, var1, "splittag", 1187, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "words"};
      splitattr$82 = Py.newCode(1, var2, var1, "splitattr", 1198, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"attr", "re", "match"};
      splitvalue$83 = Py.newCode(1, var2, var1, "splitvalue", 1205, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "a", "b"};
      f$84 = Py.newCode(1, var2, var1, "<genexpr>", 1221, false, false, self, 84, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"s", "bits", "res", "append", "i", "item"};
      unquote$85 = Py.newCode(1, var2, var1, "unquote", 1225, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      unquote_plus$86 = Py.newCode(1, var2, var1, "unquote_plus", 1253, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "safe", "cachekey", "quoter", "safe_map", "_[1297_25]", "c"};
      quote$87 = Py.newCode(2, var2, var1, "quote", 1266, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "safe"};
      quote_plus$88 = Py.newCode(2, var2, var1, "quote_plus", 1305, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"query", "doseq", "ty", "va", "tb", "l", "k", "v", "elt"};
      urlencode$89 = Py.newCode(2, var2, var1, "urlencode", 1312, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"proxies", "name", "value"};
      getproxies_environment$90 = Py.newCode(0, var2, var1, "getproxies_environment", 1376, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "proxies", "no_proxy", "hostonly", "port", "no_proxy_list", "_[1431_21]", "proxy", "name", "pattern"};
      proxy_bypass_environment$91 = Py.newCode(2, var2, var1, "proxy_bypass_environment", 1412, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "re", "socket", "fnmatch", "hostonly", "port", "ip2num", "proxy_settings", "hostIP", "value", "m", "base", "mask"};
      proxy_bypass_macosx_sysconf$92 = Py.newCode(1, var2, var1, "proxy_bypass_macosx_sysconf", 1446, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ipAddr", "parts"};
      ip2num$93 = Py.newCode(1, var2, var1, "ip2num", 1459, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getproxies_macosx_sysconf$94 = Py.newCode(0, var2, var1, "getproxies_macosx_sysconf", 1505, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "proxies"};
      proxy_bypass$95 = Py.newCode(1, var2, var1, "proxy_bypass", 1513, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getproxies$96 = Py.newCode(0, var2, var1, "getproxies", 1525, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"proxies", "_winreg", "internetSettings", "proxyEnable", "proxyServer", "p", "protocol", "address", "re"};
      getproxies_registry$97 = Py.newCode(0, var2, var1, "getproxies_registry", 1529, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getproxies$98 = Py.newCode(0, var2, var1, "getproxies", 1575, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "_winreg", "re", "internetSettings", "proxyEnable", "proxyOverride", "rawHost", "port", "addr", "fqdn", "test", "val"};
      proxy_bypass_registry$99 = Py.newCode(1, var2, var1, "proxy_bypass_registry", 1584, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "proxies"};
      proxy_bypass$100 = Py.newCode(1, var2, var1, "proxy_bypass", 1636, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "i", "t0", "qs", "uqs", "t1"};
      test1$101 = Py.newCode(0, var2, var1, "test1", 1654, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"blocknum", "blocksize", "totalsize"};
      reporthook$102 = Py.newCode(3, var2, var1, "reporthook", 1670, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new urllib$py("urllib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(urllib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.url2pathname$1(var2, var3);
         case 2:
            return this.pathname2url$2(var2, var3);
         case 3:
            return this.urlopen$3(var2, var3);
         case 4:
            return this.urlretrieve$4(var2, var3);
         case 5:
            return this.urlcleanup$5(var2, var3);
         case 6:
            return this.ContentTooShortError$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.URLopener$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.__del__$10(var2, var3);
         case 11:
            return this.close$11(var2, var3);
         case 12:
            return this.cleanup$12(var2, var3);
         case 13:
            return this.addheader$13(var2, var3);
         case 14:
            return this.open$14(var2, var3);
         case 15:
            return this.open_unknown$15(var2, var3);
         case 16:
            return this.open_unknown_proxy$16(var2, var3);
         case 17:
            return this.retrieve$17(var2, var3);
         case 18:
            return this.open_http$18(var2, var3);
         case 19:
            return this.http_error$19(var2, var3);
         case 20:
            return this.http_error_default$20(var2, var3);
         case 21:
            return this.open_https$21(var2, var3);
         case 22:
            return this.open_file$22(var2, var3);
         case 23:
            return this.open_local_file$23(var2, var3);
         case 24:
            return this.open_ftp$24(var2, var3);
         case 25:
            return this.open_data$25(var2, var3);
         case 26:
            return this.FancyURLopener$26(var2, var3);
         case 27:
            return this.__init__$27(var2, var3);
         case 28:
            return this.http_error_default$28(var2, var3);
         case 29:
            return this.http_error_302$29(var2, var3);
         case 30:
            return this.redirect_internal$30(var2, var3);
         case 31:
            return this.http_error_301$31(var2, var3);
         case 32:
            return this.http_error_303$32(var2, var3);
         case 33:
            return this.http_error_307$33(var2, var3);
         case 34:
            return this.http_error_401$34(var2, var3);
         case 35:
            return this.http_error_407$35(var2, var3);
         case 36:
            return this.retry_proxy_http_basic_auth$36(var2, var3);
         case 37:
            return this.retry_proxy_https_basic_auth$37(var2, var3);
         case 38:
            return this.retry_http_basic_auth$38(var2, var3);
         case 39:
            return this.retry_https_basic_auth$39(var2, var3);
         case 40:
            return this.get_user_passwd$40(var2, var3);
         case 41:
            return this.prompt_user_passwd$41(var2, var3);
         case 42:
            return this.localhost$42(var2, var3);
         case 43:
            return this.thishost$43(var2, var3);
         case 44:
            return this.ftperrors$44(var2, var3);
         case 45:
            return this.noheaders$45(var2, var3);
         case 46:
            return this.ftpwrapper$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.init$48(var2, var3);
         case 49:
            return this.retrfile$49(var2, var3);
         case 50:
            return this.endtransfer$50(var2, var3);
         case 51:
            return this.close$51(var2, var3);
         case 52:
            return this.file_close$52(var2, var3);
         case 53:
            return this.real_close$53(var2, var3);
         case 54:
            return this.addbase$54(var2, var3);
         case 55:
            return this.__init__$55(var2, var3);
         case 56:
            return this.f$56(var2, var3);
         case 57:
            return this.__repr__$57(var2, var3);
         case 58:
            return this.close$58(var2, var3);
         case 59:
            return this.addclosehook$59(var2, var3);
         case 60:
            return this.__init__$60(var2, var3);
         case 61:
            return this.close$61(var2, var3);
         case 62:
            return this.addinfo$62(var2, var3);
         case 63:
            return this.__init__$63(var2, var3);
         case 64:
            return this.info$64(var2, var3);
         case 65:
            return this.addinfourl$65(var2, var3);
         case 66:
            return this.__init__$66(var2, var3);
         case 67:
            return this.info$67(var2, var3);
         case 68:
            return this.getcode$68(var2, var3);
         case 69:
            return this.geturl$69(var2, var3);
         case 70:
            return this._is_unicode$70(var2, var3);
         case 71:
            return this._is_unicode$71(var2, var3);
         case 72:
            return this.toBytes$72(var2, var3);
         case 73:
            return this.unwrap$73(var2, var3);
         case 74:
            return this.splittype$74(var2, var3);
         case 75:
            return this.splithost$75(var2, var3);
         case 76:
            return this.splituser$76(var2, var3);
         case 77:
            return this.splitpasswd$77(var2, var3);
         case 78:
            return this.splitport$78(var2, var3);
         case 79:
            return this.splitnport$79(var2, var3);
         case 80:
            return this.splitquery$80(var2, var3);
         case 81:
            return this.splittag$81(var2, var3);
         case 82:
            return this.splitattr$82(var2, var3);
         case 83:
            return this.splitvalue$83(var2, var3);
         case 84:
            return this.f$84(var2, var3);
         case 85:
            return this.unquote$85(var2, var3);
         case 86:
            return this.unquote_plus$86(var2, var3);
         case 87:
            return this.quote$87(var2, var3);
         case 88:
            return this.quote_plus$88(var2, var3);
         case 89:
            return this.urlencode$89(var2, var3);
         case 90:
            return this.getproxies_environment$90(var2, var3);
         case 91:
            return this.proxy_bypass_environment$91(var2, var3);
         case 92:
            return this.proxy_bypass_macosx_sysconf$92(var2, var3);
         case 93:
            return this.ip2num$93(var2, var3);
         case 94:
            return this.getproxies_macosx_sysconf$94(var2, var3);
         case 95:
            return this.proxy_bypass$95(var2, var3);
         case 96:
            return this.getproxies$96(var2, var3);
         case 97:
            return this.getproxies_registry$97(var2, var3);
         case 98:
            return this.getproxies$98(var2, var3);
         case 99:
            return this.proxy_bypass_registry$99(var2, var3);
         case 100:
            return this.proxy_bypass$100(var2, var3);
         case 101:
            return this.test1$101(var2, var3);
         case 102:
            return this.reporthook$102(var2, var3);
         default:
            return null;
      }
   }
}
