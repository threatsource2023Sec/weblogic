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
@Filename("urlparse.py")
public class urlparse$py extends PyFunctionTable implements PyRunnable {
   static urlparse$py self;
   static final PyCode f$0;
   static final PyCode clear_cache$1;
   static final PyCode ResultMixin$2;
   static final PyCode username$3;
   static final PyCode password$4;
   static final PyCode hostname$5;
   static final PyCode port$6;
   static final PyCode SplitResult$7;
   static final PyCode geturl$8;
   static final PyCode ParseResult$9;
   static final PyCode geturl$10;
   static final PyCode urlparse$11;
   static final PyCode _splitparams$12;
   static final PyCode _splitnetloc$13;
   static final PyCode urlsplit$14;
   static final PyCode f$15;
   static final PyCode urlunparse$16;
   static final PyCode urlunsplit$17;
   static final PyCode urljoin$18;
   static final PyCode urldefrag$19;
   static final PyCode f$20;
   static final PyCode unquote$21;
   static final PyCode parse_qs$22;
   static final PyCode parse_qsl$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Parse (absolute and relative) URLs.\n\nurlparse module is based upon the following RFC specifications.\n\nRFC 3986 (STD66): \"Uniform Resource Identifiers\" by T. Berners-Lee, R. Fielding\nand L.  Masinter, January 2005.\n\nRFC 2732 : \"Format for Literal IPv6 Addresses in URL's by R.Hinden, B.Carpenter\nand L.Masinter, December 1999.\n\nRFC 2396:  \"Uniform Resource Identifiers (URI)\": Generic Syntax by T.\nBerners-Lee, R. Fielding, and L. Masinter, August 1998.\n\nRFC 2368: \"The mailto URL scheme\", by P.Hoffman , L Masinter, J. Zwinski, July 1998.\n\nRFC 1808: \"Relative Uniform Resource Locators\", by R. Fielding, UC Irvine, June\n1995.\n\nRFC 1738: \"Uniform Resource Locators (URL)\" by T. Berners-Lee, L. Masinter, M.\nMcCahill, December 1994\n\nRFC 3986 is considered the current standard and any future changes to\nurlparse module should conform with it.  The urlparse module is\ncurrently not entirely compliant with this RFC due to defacto\nscenarios for parsing, and for backward compatibility purposes, some\nparsing quirks from older RFCs are retained. The testcases in\ntest_urlparse.py provides a good indicator of parsing behavior.\n\n"));
      var1.setline(29);
      PyString.fromInterned("Parse (absolute and relative) URLs.\n\nurlparse module is based upon the following RFC specifications.\n\nRFC 3986 (STD66): \"Uniform Resource Identifiers\" by T. Berners-Lee, R. Fielding\nand L.  Masinter, January 2005.\n\nRFC 2732 : \"Format for Literal IPv6 Addresses in URL's by R.Hinden, B.Carpenter\nand L.Masinter, December 1999.\n\nRFC 2396:  \"Uniform Resource Identifiers (URI)\": Generic Syntax by T.\nBerners-Lee, R. Fielding, and L. Masinter, August 1998.\n\nRFC 2368: \"The mailto URL scheme\", by P.Hoffman , L Masinter, J. Zwinski, July 1998.\n\nRFC 1808: \"Relative Uniform Resource Locators\", by R. Fielding, UC Irvine, June\n1995.\n\nRFC 1738: \"Uniform Resource Locators (URL)\" by T. Berners-Lee, L. Masinter, M.\nMcCahill, December 1994\n\nRFC 3986 is considered the current standard and any future changes to\nurlparse module should conform with it.  The urlparse module is\ncurrently not entirely compliant with this RFC due to defacto\nscenarios for parsing, and for backward compatibility purposes, some\nparsing quirks from older RFCs are retained. The testcases in\ntest_urlparse.py provides a good indicator of parsing behavior.\n\n");
      var1.setline(31);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("urlparse"), PyString.fromInterned("urlunparse"), PyString.fromInterned("urljoin"), PyString.fromInterned("urldefrag"), PyString.fromInterned("urlsplit"), PyString.fromInterned("urlunsplit"), PyString.fromInterned("parse_qs"), PyString.fromInterned("parse_qsl")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(35);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("ftp"), PyString.fromInterned("http"), PyString.fromInterned("gopher"), PyString.fromInterned("nntp"), PyString.fromInterned("imap"), PyString.fromInterned("wais"), PyString.fromInterned("file"), PyString.fromInterned("https"), PyString.fromInterned("shttp"), PyString.fromInterned("mms"), PyString.fromInterned("prospero"), PyString.fromInterned("rtsp"), PyString.fromInterned("rtspu"), PyString.fromInterned(""), PyString.fromInterned("sftp"), PyString.fromInterned("svn"), PyString.fromInterned("svn+ssh")});
      var1.setlocal("uses_relative", var3);
      var3 = null;
      var1.setline(39);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("ftp"), PyString.fromInterned("http"), PyString.fromInterned("gopher"), PyString.fromInterned("nntp"), PyString.fromInterned("telnet"), PyString.fromInterned("imap"), PyString.fromInterned("wais"), PyString.fromInterned("file"), PyString.fromInterned("mms"), PyString.fromInterned("https"), PyString.fromInterned("shttp"), PyString.fromInterned("snews"), PyString.fromInterned("prospero"), PyString.fromInterned("rtsp"), PyString.fromInterned("rtspu"), PyString.fromInterned("rsync"), PyString.fromInterned(""), PyString.fromInterned("svn"), PyString.fromInterned("svn+ssh"), PyString.fromInterned("sftp"), PyString.fromInterned("nfs"), PyString.fromInterned("git"), PyString.fromInterned("git+ssh")});
      var1.setlocal("uses_netloc", var3);
      var3 = null;
      var1.setline(43);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("ftp"), PyString.fromInterned("hdl"), PyString.fromInterned("prospero"), PyString.fromInterned("http"), PyString.fromInterned("imap"), PyString.fromInterned("https"), PyString.fromInterned("shttp"), PyString.fromInterned("rtsp"), PyString.fromInterned("rtspu"), PyString.fromInterned("sip"), PyString.fromInterned("sips"), PyString.fromInterned("mms"), PyString.fromInterned(""), PyString.fromInterned("sftp"), PyString.fromInterned("tel")});
      var1.setlocal("uses_params", var3);
      var3 = null;
      var1.setline(49);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("gopher"), PyString.fromInterned("hdl"), PyString.fromInterned("mailto"), PyString.fromInterned("news"), PyString.fromInterned("telnet"), PyString.fromInterned("wais"), PyString.fromInterned("imap"), PyString.fromInterned("snews"), PyString.fromInterned("sip"), PyString.fromInterned("sips")});
      var1.setlocal("non_hierarchical", var3);
      var3 = null;
      var1.setline(51);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("http"), PyString.fromInterned("wais"), PyString.fromInterned("imap"), PyString.fromInterned("https"), PyString.fromInterned("shttp"), PyString.fromInterned("mms"), PyString.fromInterned("gopher"), PyString.fromInterned("rtsp"), PyString.fromInterned("rtspu"), PyString.fromInterned("sip"), PyString.fromInterned("sips"), PyString.fromInterned("")});
      var1.setlocal("uses_query", var3);
      var3 = null;
      var1.setline(53);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("ftp"), PyString.fromInterned("hdl"), PyString.fromInterned("http"), PyString.fromInterned("gopher"), PyString.fromInterned("news"), PyString.fromInterned("nntp"), PyString.fromInterned("wais"), PyString.fromInterned("https"), PyString.fromInterned("shttp"), PyString.fromInterned("snews"), PyString.fromInterned("file"), PyString.fromInterned("prospero"), PyString.fromInterned("")});
      var1.setlocal("uses_fragment", var3);
      var3 = null;
      var1.setline(58);
      PyString var6 = PyString.fromInterned("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-.");
      var1.setlocal("scheme_chars", var6);
      var3 = null;
      var1.setline(63);
      PyInteger var7 = Py.newInteger(20);
      var1.setlocal("MAX_CACHE_SIZE", var7);
      var3 = null;
      var1.setline(64);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_parse_cache", var8);
      var3 = null;
      var1.setline(66);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var9, clear_cache$1, PyString.fromInterned("Clear the parse cache."));
      var1.setlocal("clear_cache", var10);
      var3 = null;
      var1.setline(71);
      var9 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("ResultMixin", var9, ResultMixin$2);
      var1.setlocal("ResultMixin", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(116);
      String[] var11 = new String[]{"namedtuple"};
      var9 = imp.importFrom("collections", var11, var1, -1);
      var4 = var9[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var1.setline(118);
      var9 = new PyObject[]{var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SplitResult"), (PyObject)PyString.fromInterned("scheme netloc path query fragment")), var1.getname("ResultMixin")};
      var4 = Py.makeClass("SplitResult", var9, SplitResult$7);
      var1.setlocal("SplitResult", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(126);
      var9 = new PyObject[]{var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ParseResult"), (PyObject)PyString.fromInterned("scheme netloc path params query fragment")), var1.getname("ResultMixin")};
      var4 = Py.makeClass("ParseResult", var9, ParseResult$9);
      var1.setlocal("ParseResult", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(134);
      var9 = new PyObject[]{PyString.fromInterned(""), var1.getname("True")};
      var10 = new PyFunction(var1.f_globals, var9, urlparse$11, PyString.fromInterned("Parse a URL into 6 components:\n    <scheme>://<netloc>/<path>;<params>?<query>#<fragment>\n    Return a 6-tuple: (scheme, netloc, path, params, query, fragment).\n    Note that we don't break the components up in smaller bits\n    (e.g. netloc is a single string) and we don't expand % escapes."));
      var1.setlocal("urlparse", var10);
      var3 = null;
      var1.setline(148);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _splitparams$12, (PyObject)null);
      var1.setlocal("_splitparams", var10);
      var3 = null;
      var1.setline(157);
      var9 = new PyObject[]{Py.newInteger(0)};
      var10 = new PyFunction(var1.f_globals, var9, _splitnetloc$13, (PyObject)null);
      var1.setlocal("_splitnetloc", var10);
      var3 = null;
      var1.setline(165);
      var9 = new PyObject[]{PyString.fromInterned(""), var1.getname("True")};
      var10 = new PyFunction(var1.f_globals, var9, urlsplit$14, PyString.fromInterned("Parse a URL into 5 components:\n    <scheme>://<netloc>/<path>?<query>#<fragment>\n    Return a 5-tuple: (scheme, netloc, path, query, fragment).\n    Note that we don't break the components up in smaller bits\n    (e.g. netloc is a single string) and we don't expand % escapes."));
      var1.setlocal("urlsplit", var10);
      var3 = null;
      var1.setline(220);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, urlunparse$16, PyString.fromInterned("Put a parsed URL back together again.  This may result in a\n    slightly different, but equivalent URL, if the URL that was parsed\n    originally had redundant delimiters, e.g. a ? with an empty query\n    (the draft states that these are equivalent)."));
      var1.setlocal("urlunparse", var10);
      var3 = null;
      var1.setline(230);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, urlunsplit$17, PyString.fromInterned("Combine the elements of a tuple as returned by urlsplit() into a\n    complete URL as a string. The data argument can be any five-item iterable.\n    This may result in a slightly different, but equivalent URL, if the URL that\n    was parsed originally had unnecessary delimiters (for example, a ? with an\n    empty query; the RFC states that these are equivalent)."));
      var1.setlocal("urlunsplit", var10);
      var3 = null;
      var1.setline(248);
      var9 = new PyObject[]{var1.getname("True")};
      var10 = new PyFunction(var1.f_globals, var9, urljoin$18, PyString.fromInterned("Join a base URL and a possibly relative URL to form an absolute\n    interpretation of the latter."));
      var1.setlocal("urljoin", var10);
      var3 = null;
      var1.setline(300);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, urldefrag$19, PyString.fromInterned("Removes any existing fragment from URL.\n\n    Returns a tuple of the defragmented URL and the fragment.  If\n    the URL contained no fragments, the second element is the\n    empty string.\n    "));
      var1.setlocal("urldefrag", var10);
      var3 = null;
      var1.setline(319);
      var6 = PyString.fromInterned("0123456789ABCDEFabcdef");
      var1.setlocal("_hexdig", var6);
      var3 = null;
      var1.setline(320);
      PyObject var10000 = var1.getname("dict");
      var1.setline(320);
      var9 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var9, f$20, (PyObject)null);
      PyObject var10002 = var5.__call__(var2, var1.getname("_hexdig").__iter__());
      Arrays.fill(var9, (Object)null);
      PyObject var12 = var10000.__call__(var2, var10002);
      var1.setlocal("_hextochr", var12);
      var3 = null;
      var1.setline(323);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, unquote$21, PyString.fromInterned("unquote('abc%20def') -> 'abc def'."));
      var1.setlocal("unquote", var10);
      var3 = null;
      var1.setline(339);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var10 = new PyFunction(var1.f_globals, var9, parse_qs$22, PyString.fromInterned("Parse a query given as a string argument.\n\n        Arguments:\n\n        qs: percent-encoded query string to be parsed\n\n        keep_blank_values: flag indicating whether blank values in\n            percent-encoded queries should be treated as blank strings.\n            A true value indicates that blanks should be retained as\n            blank strings.  The default false value indicates that\n            blank values are to be ignored and treated as if they were\n            not included.\n\n        strict_parsing: flag indicating what to do with parsing errors.\n            If false (the default), errors are silently ignored.\n            If true, errors raise a ValueError exception.\n    "));
      var1.setlocal("parse_qs", var10);
      var3 = null;
      var1.setline(365);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var10 = new PyFunction(var1.f_globals, var9, parse_qsl$23, PyString.fromInterned("Parse a query given as a string argument.\n\n    Arguments:\n\n    qs: percent-encoded query string to be parsed\n\n    keep_blank_values: flag indicating whether blank values in\n        percent-encoded queries should be treated as blank strings.  A\n        true value indicates that blanks should be retained as blank\n        strings.  The default false value indicates that blank values\n        are to be ignored and treated as if they were  not included.\n\n    strict_parsing: flag indicating what to do with parsing errors. If\n        false (the default), errors are silently ignored. If true,\n        errors raise a ValueError exception.\n\n    Returns a list, as G-d intended.\n    "));
      var1.setlocal("parse_qsl", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_cache$1(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Clear the parse cache.");
      var1.setline(68);
      var1.getglobal("_parse_cache").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ResultMixin$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Shared methods for the parsed result objects."));
      var1.setline(72);
      PyString.fromInterned("Shared methods for the parsed result objects.");
      var1.setline(74);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, username$3, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("username", var5);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, password$4, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("password", var5);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, hostname$5, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("hostname", var5);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, port$6, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("port", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject username$3(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("netloc");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(77);
      PyString var4 = PyString.fromInterned("@");
      PyObject var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var3 = var1.getlocal(1).__getattr__("rsplit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(79);
         var4 = PyString.fromInterned(":");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(80);
            var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(81);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(82);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject password$4(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("netloc");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(87);
      PyString var4 = PyString.fromInterned("@");
      PyObject var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(88);
         var3 = var1.getlocal(1).__getattr__("rsplit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(89);
         var4 = PyString.fromInterned(":");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(90);
            var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(91);
      var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hostname$5(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getlocal(0).__getattr__("netloc").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@")).__getitem__(Py.newInteger(-1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(96);
      PyString var5 = PyString.fromInterned("[");
      PyObject var10000 = var5._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var5 = PyString.fromInterned("]");
         var10000 = var5._in(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(97);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]")).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(98);
         PyString var4 = PyString.fromInterned(":");
         var10000 = var4._in(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(99);
            var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":")).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(100);
            PyObject var6 = var1.getlocal(1);
            var10000 = var6._eq(PyString.fromInterned(""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(101);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(103);
               var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject port$6(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(0).__getattr__("netloc").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@")).__getitem__(Py.newInteger(-1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]")).__getitem__(Py.newInteger(-1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(108);
      PyString var5 = PyString.fromInterned(":");
      PyObject var10000 = var5._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(109);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":")).__getitem__(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(110);
         var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(10));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(112);
         PyInteger var6 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(2);
         PyInteger var7 = var6;
         var3 = var10001;
         PyObject var4;
         if ((var4 = var7._le(var10001)).__nonzero__()) {
            var4 = var3._le(Py.newInteger(65535));
         }

         var3 = null;
         if (var4.__nonzero__()) {
            var1.setline(113);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(114);
      var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SplitResult$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(120);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(122);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, geturl$8, (PyObject)null);
      var1.setlocal("geturl", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject geturl$8(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getglobal("urlunsplit").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ParseResult$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(128);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(130);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, geturl$10, (PyObject)null);
      var1.setlocal("geturl", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject geturl$10(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getglobal("urlunparse").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urlparse$11(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyString.fromInterned("Parse a URL into 6 components:\n    <scheme>://<netloc>/<path>;<params>?<query>#<fragment>\n    Return a 6-tuple: (scheme, netloc, path, params, query, fragment).\n    Note that we don't break the components up in smaller bits\n    (e.g. netloc is a single string) and we don't expand % escapes.");
      var1.setline(140);
      PyObject var3 = var1.getglobal("urlsplit").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(141);
      var3 = var1.getlocal(3);
      PyObject[] var4 = Py.unpackSequence(var3, 5);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(0, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(142);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("uses_params"));
      var3 = null;
      PyString var6;
      if (var10000.__nonzero__()) {
         var6 = PyString.fromInterned(";");
         var10000 = var6._in(var1.getlocal(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(143);
         var3 = var1.getglobal("_splitparams").__call__(var2, var1.getlocal(0));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(145);
         var6 = PyString.fromInterned("");
         var1.setlocal(7, var6);
         var3 = null;
      }

      var1.setline(146);
      var10000 = var1.getglobal("ParseResult");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(0), var1.getlocal(7), var1.getlocal(5), var1.getlocal(6)};
      var3 = var10000.__call__(var2, var7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _splitparams$12(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyString var3 = PyString.fromInterned("/");
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(150);
         PyObject var5 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"), (PyObject)var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")));
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(151);
         var5 = var1.getlocal(1);
         var10000 = var5._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(152);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(0), PyString.fromInterned("")});
            var1.f_lasti = -1;
            return var6;
         }
      } else {
         var1.setline(154);
         PyObject var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(155);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _splitnetloc$13(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var3 = PyString.fromInterned("/?#").__iter__();

      while(true) {
         var1.setline(159);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(163);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(160);
         PyObject var5 = var1.getlocal(0).__getattr__("find").__call__(var2, var1.getlocal(3), var1.getlocal(1));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(161);
         var5 = var1.getlocal(4);
         PyObject var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(162);
            var5 = var1.getglobal("min").__call__(var2, var1.getlocal(2), var1.getlocal(4));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject urlsplit$14(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Parse a URL into 5 components:\n    <scheme>://<netloc>/<path>?<query>#<fragment>\n    Return a 5-tuple: (scheme, netloc, path, query, fragment).\n    Note that we don't break the components up in smaller bits\n    (e.g. netloc is a single string) and we don't expand % escapes.");
      var1.setline(171);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(172);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("type").__call__(var2, var1.getlocal(0)), var1.getglobal("type").__call__(var2, var1.getlocal(1))});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("_parse_cache").__getattr__("get").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(174);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(175);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(176);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getglobal("_parse_cache"));
         PyObject var10000 = var4._ge(var1.getglobal("MAX_CACHE_SIZE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(177);
            var1.getglobal("clear_cache").__call__(var2);
         }

         var1.setline(178);
         PyString var10 = PyString.fromInterned("");
         var1.setlocal(5, var10);
         var1.setlocal(6, var10);
         var1.setlocal(7, var10);
         var1.setline(179);
         var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(180);
         var4 = var1.getlocal(8);
         var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         PyObject var6;
         PyObject[] var11;
         PyObject[] var15;
         if (var10000.__nonzero__()) {
            var1.setline(181);
            var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("http"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(182);
               var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null).__getattr__("lower").__call__(var2);
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(183);
               var4 = var1.getlocal(0).__getslice__(var1.getlocal(8)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(184);
               var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("//"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(185);
                  var4 = var1.getglobal("_splitnetloc").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(2));
                  var11 = Py.unpackSequence(var4, 2);
                  var6 = var11[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var11[1];
                  var1.setlocal(0, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(186);
                  var10 = PyString.fromInterned("[");
                  var10000 = var10._in(var1.getlocal(5));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var10 = PyString.fromInterned("]");
                     var10000 = var10._notin(var1.getlocal(5));
                     var4 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     var10 = PyString.fromInterned("]");
                     var10000 = var10._in(var1.getlocal(5));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var10 = PyString.fromInterned("[");
                        var10000 = var10._notin(var1.getlocal(5));
                        var4 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(188);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid IPv6 URL")));
                  }
               }

               var1.setline(189);
               var10000 = var1.getlocal(2);
               if (var10000.__nonzero__()) {
                  var10 = PyString.fromInterned("#");
                  var10000 = var10._in(var1.getlocal(0));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(190);
                  var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"), (PyObject)Py.newInteger(1));
                  var11 = Py.unpackSequence(var4, 2);
                  var6 = var11[0];
                  var1.setlocal(0, var6);
                  var6 = null;
                  var6 = var11[1];
                  var1.setlocal(7, var6);
                  var6 = null;
                  var4 = null;
               }

               var1.setline(191);
               var10 = PyString.fromInterned("?");
               var10000 = var10._in(var1.getlocal(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(192);
                  var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(1));
                  var11 = Py.unpackSequence(var4, 2);
                  var6 = var11[0];
                  var1.setlocal(0, var6);
                  var6 = null;
                  var6 = var11[1];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var4 = null;
               }

               var1.setline(193);
               var10000 = var1.getglobal("SplitResult");
               var15 = new PyObject[]{var1.getlocal(1), var1.getlocal(5), var1.getlocal(0), var1.getlocal(6), var1.getlocal(7)};
               var4 = var10000.__call__(var2, var15);
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(194);
               var4 = var1.getlocal(9);
               var1.getglobal("_parse_cache").__setitem__(var1.getlocal(3), var4);
               var4 = null;
               var1.setline(195);
               var3 = var1.getlocal(9);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(196);
            var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null).__iter__();

            do {
               var1.setline(196);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(202);
                  var6 = var1.getlocal(0).__getslice__(var1.getlocal(8)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
                  var1.setlocal(11, var6);
                  var6 = null;
                  var1.setline(203);
                  var10000 = var1.getlocal(11).__not__();
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("any");
                     var1.setline(203);
                     PyObject[] var12 = Py.EmptyObjects;
                     PyFunction var7 = new PyFunction(var1.f_globals, var12, f$15, (PyObject)null);
                     PyObject var10002 = var7.__call__(var2, var1.getlocal(11).__iter__());
                     Arrays.fill(var12, (Object)null);
                     var10000 = var10000.__call__(var2, var10002);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(205);
                     PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null).__getattr__("lower").__call__(var2), var1.getlocal(11)});
                     PyObject[] var13 = Py.unpackSequence(var14, 2);
                     PyObject var8 = var13[0];
                     var1.setlocal(1, var8);
                     var8 = null;
                     var8 = var13[1];
                     var1.setlocal(0, var8);
                     var8 = null;
                     var6 = null;
                  }
                  break;
               }

               var1.setlocal(10, var5);
               var1.setline(197);
               var6 = var1.getlocal(10);
               var10000 = var6._notin(var1.getglobal("scheme_chars"));
               var6 = null;
            } while(!var10000.__nonzero__());
         }

         var1.setline(207);
         var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("//"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(208);
            var4 = var1.getglobal("_splitnetloc").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(2));
            var11 = Py.unpackSequence(var4, 2);
            var6 = var11[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(0, var6);
            var6 = null;
            var4 = null;
            var1.setline(209);
            var10 = PyString.fromInterned("[");
            var10000 = var10._in(var1.getlocal(5));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10 = PyString.fromInterned("]");
               var10000 = var10._notin(var1.getlocal(5));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var10 = PyString.fromInterned("]");
               var10000 = var10._in(var1.getlocal(5));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10 = PyString.fromInterned("[");
                  var10000 = var10._notin(var1.getlocal(5));
                  var4 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(211);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid IPv6 URL")));
            }
         }

         var1.setline(212);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10 = PyString.fromInterned("#");
            var10000 = var10._in(var1.getlocal(0));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(213);
            var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"), (PyObject)Py.newInteger(1));
            var11 = Py.unpackSequence(var4, 2);
            var6 = var11[0];
            var1.setlocal(0, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
         }

         var1.setline(214);
         var10 = PyString.fromInterned("?");
         var10000 = var10._in(var1.getlocal(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(215);
            var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(1));
            var11 = Py.unpackSequence(var4, 2);
            var6 = var11[0];
            var1.setlocal(0, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
         }

         var1.setline(216);
         var10000 = var1.getglobal("SplitResult");
         var15 = new PyObject[]{var1.getlocal(1), var1.getlocal(5), var1.getlocal(0), var1.getlocal(6), var1.getlocal(7)};
         var4 = var10000.__call__(var2, var15);
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(217);
         var4 = var1.getlocal(9);
         var1.getglobal("_parse_cache").__setitem__(var1.getlocal(3), var4);
         var4 = null;
         var1.setline(218);
         var3 = var1.getlocal(9);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$15(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(203);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(203);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(203);
         var1.setline(203);
         PyObject var6 = var1.getlocal(1);
         var7 = var6._notin(PyString.fromInterned("0123456789"));
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject urlunparse$16(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyString.fromInterned("Put a parsed URL back together again.  This may result in a\n    slightly different, but equivalent URL, if the URL that was parsed\n    originally had redundant delimiters, e.g. a ? with an empty query\n    (the draft states that these are equivalent).");
      var1.setline(225);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(226);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(227);
         var3 = PyString.fromInterned("%s;%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)}));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(228);
      var3 = var1.getglobal("urlunsplit").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urlunsplit$17(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyString.fromInterned("Combine the elements of a tuple as returned by urlsplit() into a\n    complete URL as a string. The data argument can be any five-item iterable.\n    This may result in a slightly different, but equivalent URL, if the URL that\n    was parsed originally had unnecessary delimiters (for example, a ? with an\n    empty query; the RFC states that these are equivalent).");
      var1.setline(236);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 5);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(237);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._in(var1.getglobal("uses_netloc"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
               var10000 = var3._ne(PyString.fromInterned("//"));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(238);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var3._ne(PyString.fromInterned("/"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(238);
            var3 = PyString.fromInterned("/")._add(var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(239);
         PyString var6 = PyString.fromInterned("//");
         Object var10001 = var1.getlocal(2);
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("");
         }

         var3 = var6._add((PyObject)var10001)._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(240);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(241);
         var3 = var1.getlocal(1)._add(PyString.fromInterned(":"))._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(242);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(243);
         var3 = var1.getlocal(3)._add(PyString.fromInterned("?"))._add(var1.getlocal(4));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(244);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(245);
         var3 = var1.getlocal(3)._add(PyString.fromInterned("#"))._add(var1.getlocal(5));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(246);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urljoin$18(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString.fromInterned("Join a base URL and a possibly relative URL to form an absolute\n    interpretation of the latter.");
      var1.setline(251);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(252);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(253);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(254);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(255);
            PyObject var4 = var1.getglobal("urlparse").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(2));
            PyObject[] var5 = Py.unpackSequence(var4, 6);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[4];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[5];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(257);
            var4 = var1.getglobal("urlparse").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(2));
            var5 = Py.unpackSequence(var4, 6);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(11, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(12, var6);
            var6 = null;
            var6 = var5[4];
            var1.setlocal(13, var6);
            var6 = null;
            var6 = var5[5];
            var1.setlocal(14, var6);
            var6 = null;
            var4 = null;
            var1.setline(259);
            var4 = var1.getlocal(9);
            PyObject var10000 = var4._ne(var1.getlocal(3));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(9);
               var10000 = var4._notin(var1.getglobal("uses_relative"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(260);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(261);
               var4 = var1.getlocal(9);
               var10000 = var4._in(var1.getglobal("uses_netloc"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(262);
                  if (var1.getlocal(10).__nonzero__()) {
                     var1.setline(263);
                     var3 = var1.getglobal("urlunparse").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14)})));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(265);
                  var4 = var1.getlocal(4);
                  var1.setlocal(10, var4);
                  var4 = null;
               }

               var1.setline(266);
               var4 = var1.getlocal(11).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("/"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(267);
                  var3 = var1.getglobal("urlunparse").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14)})));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(269);
                  var10000 = var1.getlocal(11).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(12).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(270);
                     var4 = var1.getlocal(5);
                     var1.setlocal(11, var4);
                     var4 = null;
                     var1.setline(271);
                     var4 = var1.getlocal(6);
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(272);
                     if (var1.getlocal(13).__not__().__nonzero__()) {
                        var1.setline(273);
                        var4 = var1.getlocal(7);
                        var1.setlocal(13, var4);
                        var4 = null;
                     }

                     var1.setline(274);
                     var3 = var1.getglobal("urlunparse").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14)})));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(276);
                     var4 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(var1.getlocal(11).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")));
                     var1.setlocal(15, var4);
                     var4 = null;
                     var1.setline(278);
                     var4 = var1.getlocal(15).__getitem__(Py.newInteger(-1));
                     var10000 = var4._eq(PyString.fromInterned("."));
                     var4 = null;
                     PyString var7;
                     if (var10000.__nonzero__()) {
                        var1.setline(279);
                        var7 = PyString.fromInterned("");
                        var1.getlocal(15).__setitem__((PyObject)Py.newInteger(-1), var7);
                        var4 = null;
                     }

                     while(true) {
                        var1.setline(280);
                        var7 = PyString.fromInterned(".");
                        var10000 = var7._in(var1.getlocal(15));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           label84:
                           while(true) {
                              var1.setline(282);
                              if (!Py.newInteger(1).__nonzero__()) {
                                 break;
                              }

                              var1.setline(283);
                              PyInteger var8 = Py.newInteger(1);
                              var1.setlocal(16, var8);
                              var4 = null;
                              var1.setline(284);
                              var4 = var1.getglobal("len").__call__(var2, var1.getlocal(15))._sub(Py.newInteger(1));
                              var1.setlocal(17, var4);
                              var4 = null;

                              while(true) {
                                 var1.setline(285);
                                 var4 = var1.getlocal(16);
                                 var10000 = var4._lt(var1.getlocal(17));
                                 var4 = null;
                                 if (!var10000.__nonzero__()) {
                                    break label84;
                                 }

                                 var1.setline(286);
                                 var4 = var1.getlocal(15).__getitem__(var1.getlocal(16));
                                 var10000 = var4._eq(PyString.fromInterned(".."));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var4 = var1.getlocal(15).__getitem__(var1.getlocal(16)._sub(Py.newInteger(1)));
                                    var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("..")}));
                                    var4 = null;
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(288);
                                    var1.getlocal(15).__delslice__(var1.getlocal(16)._sub(Py.newInteger(1)), var1.getlocal(16)._add(Py.newInteger(1)), (PyObject)null);
                                    break;
                                 }

                                 var1.setline(290);
                                 var4 = var1.getlocal(16)._add(Py.newInteger(1));
                                 var1.setlocal(16, var4);
                                 var4 = null;
                              }
                           }

                           var1.setline(293);
                           var4 = var1.getlocal(15);
                           var10000 = var4._eq(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("..")}));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(294);
                              var7 = PyString.fromInterned("");
                              var1.getlocal(15).__setitem__((PyObject)Py.newInteger(-1), var7);
                              var4 = null;
                           } else {
                              var1.setline(295);
                              var4 = var1.getglobal("len").__call__(var2, var1.getlocal(15));
                              var10000 = var4._ge(Py.newInteger(2));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var4 = var1.getlocal(15).__getitem__(Py.newInteger(-1));
                                 var10000 = var4._eq(PyString.fromInterned(".."));
                                 var4 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(296);
                                 PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("")});
                                 var1.getlocal(15).__setslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null, var9);
                                 var4 = null;
                              }
                           }

                           var1.setline(297);
                           var3 = var1.getglobal("urlunparse").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(15)), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14)})));
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setline(281);
                        var1.getlocal(15).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject urldefrag$19(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Removes any existing fragment from URL.\n\n    Returns a tuple of the defragmented URL and the fragment.  If\n    the URL contained no fragments, the second element is the\n    empty string.\n    ");
      var1.setline(307);
      PyString var3 = PyString.fromInterned("#");
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(308);
         PyObject var7 = var1.getglobal("urlparse").__call__(var2, var1.getlocal(0));
         PyObject[] var4 = Py.unpackSequence(var7, 6);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(309);
         var7 = var1.getglobal("urlunparse").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), PyString.fromInterned("")})));
         var1.setlocal(7, var7);
         var3 = null;
         var1.setline(310);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(6)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(312);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(0), PyString.fromInterned("")});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject f$20(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(321);
            var3 = var1.getlocal(0).__iter__();
            var1.setline(321);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var4);
            var1.setline(321);
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
         var1.setline(321);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(2, var6);
            var1.setline(320);
            var1.setline(320);
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

         var1.setline(321);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(321);
         var5 = var1.getglobal("_hexdig").__iter__();
      }
   }

   public PyObject unquote$21(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyString.fromInterned("unquote('abc%20def') -> 'abc def'.");
      var1.setline(325);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(327);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(328);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(329);
         PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(330);
         var4 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(330);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(337);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var5);

            try {
               var1.setline(332);
               PyObject var9 = var1.getlocal(0);
               var9 = var9._iadd(var1.getglobal("_hextochr").__getitem__(var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null))._add(var1.getlocal(2).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)));
               var1.setlocal(0, var9);
            } catch (Throwable var8) {
               PyException var6 = Py.setException(var8, var1);
               PyObject var7;
               if (var6.match(var1.getglobal("KeyError"))) {
                  var1.setline(334);
                  var7 = var1.getlocal(0);
                  var7 = var7._iadd(PyString.fromInterned("%")._add(var1.getlocal(2)));
                  var1.setlocal(0, var7);
               } else {
                  if (!var6.match(var1.getglobal("UnicodeDecodeError"))) {
                     throw var6;
                  }

                  var1.setline(336);
                  var7 = var1.getlocal(0);
                  var7 = var7._iadd(var1.getglobal("unichr").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null), (PyObject)Py.newInteger(16)))._add(var1.getlocal(2).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)));
                  var1.setlocal(0, var7);
               }
            }
         }
      }
   }

   public PyObject parse_qs$22(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyString.fromInterned("Parse a query given as a string argument.\n\n        Arguments:\n\n        qs: percent-encoded query string to be parsed\n\n        keep_blank_values: flag indicating whether blank values in\n            percent-encoded queries should be treated as blank strings.\n            A true value indicates that blanks should be retained as\n            blank strings.  The default false value indicates that\n            blank values are to be ignored and treated as if they were\n            not included.\n\n        strict_parsing: flag indicating what to do with parsing errors.\n            If false (the default), errors are silently ignored.\n            If true, errors raise a ValueError exception.\n    ");
      var1.setline(357);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(358);
      PyObject var7 = var1.getglobal("parse_qsl").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(358);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(363);
            var7 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(359);
         PyObject var8 = var1.getlocal(4);
         PyObject var10000 = var8._in(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(360);
            var1.getlocal(3).__getitem__(var1.getlocal(4)).__getattr__("append").__call__(var2, var1.getlocal(5));
         } else {
            var1.setline(362);
            PyList var9 = new PyList(new PyObject[]{var1.getlocal(5)});
            var1.getlocal(3).__setitem__((PyObject)var1.getlocal(4), var9);
            var5 = null;
         }
      }
   }

   public PyObject parse_qsl$23(PyFrame var1, ThreadState var2) {
      var1.setline(383);
      PyString.fromInterned("Parse a query given as a string argument.\n\n    Arguments:\n\n    qs: percent-encoded query string to be parsed\n\n    keep_blank_values: flag indicating whether blank values in\n        percent-encoded queries should be treated as blank strings.  A\n        true value indicates that blanks should be retained as blank\n        strings.  The default false value indicates that blank values\n        are to be ignored and treated as if they were  not included.\n\n    strict_parsing: flag indicating what to do with parsing errors. If\n        false (the default), errors are silently ignored. If true,\n        errors raise a ValueError exception.\n\n    Returns a list, as G-d intended.\n    ");
      var1.setline(384);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(384);
      var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&")).__iter__();

      while(true) {
         var1.setline(384);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(384);
            var1.dellocal(4);
            PyList var7 = var10000;
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(385);
            var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(386);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               PyObject var8;
               while(true) {
                  do {
                     var1.setline(386);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(403);
                        var3 = var1.getlocal(7);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(8, var4);
                     var1.setline(387);
                     var8 = var1.getlocal(8).__not__();
                     if (var8.__nonzero__()) {
                        var8 = var1.getlocal(2).__not__();
                     }
                  } while(var8.__nonzero__());

                  var1.setline(389);
                  var5 = var1.getlocal(8).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
                  var1.setlocal(9, var5);
                  var5 = null;
                  var1.setline(390);
                  var5 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
                  var8 = var5._ne(Py.newInteger(2));
                  var5 = null;
                  if (!var8.__nonzero__()) {
                     break;
                  }

                  var1.setline(391);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(392);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("bad query field: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(8)})));
                  }

                  var1.setline(394);
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(395);
                     var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
                     break;
                  }
               }

               var1.setline(398);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(1)));
               if (!var8.__nonzero__()) {
                  var8 = var1.getlocal(1);
               }

               if (var8.__nonzero__()) {
                  var1.setline(399);
                  var5 = var1.getglobal("unquote").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(0)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+"), (PyObject)PyString.fromInterned(" ")));
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(400);
                  var5 = var1.getglobal("unquote").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(1)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+"), (PyObject)PyString.fromInterned(" ")));
                  var1.setlocal(11, var5);
                  var5 = null;
                  var1.setline(401);
                  var1.getlocal(7).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(11)})));
               }
            }
         }

         var1.setlocal(6, var4);
         var1.setline(384);
         var5 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

         while(true) {
            var1.setline(384);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(384);
            var1.getlocal(4).__call__(var2, var1.getlocal(5));
         }
      }
   }

   public urlparse$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      clear_cache$1 = Py.newCode(0, var2, var1, "clear_cache", 66, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ResultMixin$2 = Py.newCode(0, var2, var1, "ResultMixin", 71, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "netloc", "userinfo"};
      username$3 = Py.newCode(1, var2, var1, "username", 74, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "netloc", "userinfo"};
      password$4 = Py.newCode(1, var2, var1, "password", 84, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "netloc"};
      hostname$5 = Py.newCode(1, var2, var1, "hostname", 93, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "netloc", "port"};
      port$6 = Py.newCode(1, var2, var1, "port", 105, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SplitResult$7 = Py.newCode(0, var2, var1, "SplitResult", 118, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      geturl$8 = Py.newCode(1, var2, var1, "geturl", 122, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ParseResult$9 = Py.newCode(0, var2, var1, "ParseResult", 126, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      geturl$10 = Py.newCode(1, var2, var1, "geturl", 130, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "scheme", "allow_fragments", "tuple", "netloc", "query", "fragment", "params"};
      urlparse$11 = Py.newCode(3, var2, var1, "urlparse", 134, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "i"};
      _splitparams$12 = Py.newCode(1, var2, var1, "_splitparams", 148, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "start", "delim", "c", "wdelim"};
      _splitnetloc$13 = Py.newCode(2, var2, var1, "_splitnetloc", 157, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "scheme", "allow_fragments", "key", "cached", "netloc", "query", "fragment", "i", "v", "c", "rest", "_(203_31)"};
      urlsplit$14 = Py.newCode(3, var2, var1, "urlsplit", 165, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "c"};
      f$15 = Py.newCode(1, var2, var1, "<genexpr>", 203, false, false, self, 15, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"data", "scheme", "netloc", "url", "params", "query", "fragment"};
      urlunparse$16 = Py.newCode(1, var2, var1, "urlunparse", 220, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "scheme", "netloc", "url", "query", "fragment"};
      urlunsplit$17 = Py.newCode(1, var2, var1, "urlunsplit", 230, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base", "url", "allow_fragments", "bscheme", "bnetloc", "bpath", "bparams", "bquery", "bfragment", "scheme", "netloc", "path", "params", "query", "fragment", "segments", "i", "n"};
      urljoin$18 = Py.newCode(3, var2, var1, "urljoin", 248, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "s", "n", "p", "a", "q", "frag", "defrag"};
      urldefrag$19 = Py.newCode(1, var2, var1, "urldefrag", 300, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "a", "b"};
      f$20 = Py.newCode(1, var2, var1, "<genexpr>", 320, false, false, self, 20, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"s", "res", "item"};
      unquote$21 = Py.newCode(1, var2, var1, "unquote", 323, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"qs", "keep_blank_values", "strict_parsing", "dict", "name", "value"};
      parse_qs$22 = Py.newCode(3, var2, var1, "parse_qs", 339, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"qs", "keep_blank_values", "strict_parsing", "pairs", "_[384_13]", "s2", "s1", "r", "name_value", "nv", "name", "value"};
      parse_qsl$23 = Py.newCode(3, var2, var1, "parse_qsl", 365, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new urlparse$py("urlparse$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(urlparse$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.clear_cache$1(var2, var3);
         case 2:
            return this.ResultMixin$2(var2, var3);
         case 3:
            return this.username$3(var2, var3);
         case 4:
            return this.password$4(var2, var3);
         case 5:
            return this.hostname$5(var2, var3);
         case 6:
            return this.port$6(var2, var3);
         case 7:
            return this.SplitResult$7(var2, var3);
         case 8:
            return this.geturl$8(var2, var3);
         case 9:
            return this.ParseResult$9(var2, var3);
         case 10:
            return this.geturl$10(var2, var3);
         case 11:
            return this.urlparse$11(var2, var3);
         case 12:
            return this._splitparams$12(var2, var3);
         case 13:
            return this._splitnetloc$13(var2, var3);
         case 14:
            return this.urlsplit$14(var2, var3);
         case 15:
            return this.f$15(var2, var3);
         case 16:
            return this.urlunparse$16(var2, var3);
         case 17:
            return this.urlunsplit$17(var2, var3);
         case 18:
            return this.urljoin$18(var2, var3);
         case 19:
            return this.urldefrag$19(var2, var3);
         case 20:
            return this.f$20(var2, var3);
         case 21:
            return this.unquote$21(var2, var3);
         case 22:
            return this.parse_qs$22(var2, var3);
         case 23:
            return this.parse_qsl$23(var2, var3);
         default:
            return null;
      }
   }
}
