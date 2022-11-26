package xml;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("xml/Uri.py")
public class Uri$py extends PyFunctionTable implements PyRunnable {
   static Uri$py self;
   static final PyCode f$0;
   static final PyCode UnsplitUriRef$1;
   static final PyCode SplitUriRef$2;
   static final PyCode Absolutize$3;
   static final PyCode MakeUrllibSafe$4;
   static final PyCode BaseJoin$5;
   static final PyCode RemoveDotSegments$6;
   static final PyCode GetScheme$7;
   static final PyCode IsAbsolute$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = imp.importOne("os.path", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var3);
      var3 = null;
      var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var3);
      var3 = null;
      var3 = imp.importOne("urllib2", var1, -1);
      var1.setlocal("urllib2", var3);
      var3 = null;
      var1.setline(18);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, UnsplitUriRef$1, PyString.fromInterned("should replace urlparse.urlunsplit\n\n    Given a sequence as would be produced by SplitUriRef(), assembles and\n    returns a URI reference as a string.\n    "));
      var1.setlocal("UnsplitUriRef", var5);
      var3 = null;
      var1.setline(39);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?:(?P<scheme>[^:/?#]+):)?(?://(?P<authority>[^/?#]*))?(?P<path>[^?#]*)(?:\\?(?P<query>[^#]*))?(?:#(?P<fragment>.*))?$"));
      var1.setlocal("SPLIT_URI_REF_PATTERN", var3);
      var3 = null;
      var1.setline(41);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, SplitUriRef$2, PyString.fromInterned("should replace urlparse.urlsplit\n\n    Given a valid URI reference as a string, returns a tuple representing the\n    generic URI components, as per RFC 2396 appendix B. The tuple's structure\n    is (scheme, authority, path, query, fragment).\n\n    All values will be strings (possibly empty) or None if undefined.\n\n    Note that per rfc3986, there is no distinction between a path and\n    an \"opaque part\", as there was in RFC 2396.\n    "));
      var1.setlocal("SplitUriRef", var5);
      var3 = null;
      var1.setline(64);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, Absolutize$3, PyString.fromInterned("\n    Resolves a URI reference to absolute form, effecting the result of RFC\n    3986 section 5. The URI reference is considered to be relative to the\n    given base URI.\n\n    It is the caller's responsibility to ensure that the base URI matches\n    the absolute-URI syntax rule of RFC 3986, and that its path component\n    does not contain '.' or '..' segments if the scheme is hierarchical.\n    Unexpected results may occur otherwise.\n\n    This function only conducts a minimal sanity check in order to determine\n    if relative resolution is possible: it raises a UriException if the base\n    URI does not have a scheme component. While it is true that the base URI\n    is irrelevant if the URI reference has a scheme, an exception is raised\n    in order to signal that the given string does not even come close to\n    meeting the criteria to be usable as a base URI.\n\n    It is the caller's responsibility to make a determination of whether the\n    URI reference constitutes a \"same-document reference\", as defined in RFC\n    2396 or RFC 3986. As per the spec, dereferencing a same-document\n    reference \"should not\" involve retrieval of a new representation of the\n    referenced resource. Note that the two specs have different definitions\n    of same-document reference: RFC 2396 says it is *only* the cases where the\n    reference is the empty string, or \"#\" followed by a fragment; RFC 3986\n    requires making a comparison of the base URI to the absolute form of the\n    reference (as is returned by the spec), minus its fragment component,\n    if any.\n\n    This function is similar to urlparse.urljoin() and urllib.basejoin().\n    Those functions, however, are (as of Python 2.3) outdated, buggy, and/or\n    designed to produce results acceptable for use with other core Python\n    libraries, rather than being earnest implementations of the relevant\n    specs. Their problems are most noticeable in their handling of\n    same-document references and 'file:' URIs, both being situations that\n    come up far too often to consider the functions reliable enough for\n    general use.\n    "));
      var1.setlocal("Absolutize", var5);
      var3 = null;
      var1.setline(180);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?:(?:[0-9A-Za-z\\-_\\.!~*'();&=+$,]|(?:%[0-9A-Fa-f]{2}))*)$"));
      var1.setlocal("REG_NAME_HOST_PATTERN", var3);
      var3 = null;
      var1.setline(182);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, MakeUrllibSafe$4, PyString.fromInterned("\n    Makes the given RFC 3986-conformant URI reference safe for passing\n    to legacy urllib functions. The result may not be a valid URI.\n\n    As of Python 2.3.3, urllib.urlopen() does not fully support\n    internationalized domain names, it does not strip fragment components,\n    and on Windows, it expects file URIs to use '|' instead of ':' in the\n    path component corresponding to the drivespec. It also relies on\n    urllib.unquote(), which mishandles unicode arguments. This function\n    produces a URI reference that will work around these issues, although\n    the IDN workaround is limited to Python 2.3 only. May raise a\n    UnicodeEncodeError if the URI reference is Unicode and erroneously\n    contains non-ASCII characters.\n    "));
      var1.setlocal("MakeUrllibSafe", var5);
      var3 = null;
      var1.setline(255);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, BaseJoin$5, PyString.fromInterned("\n    Merges a base URI reference with another URI reference, returning a\n    new URI reference.\n\n    It behaves exactly the same as Absolutize(), except the arguments\n    are reversed, and it accepts any URI reference (even a relative URI)\n    as the base URI. If the base has no scheme component, it is\n    evaluated as if it did, and then the scheme component of the result\n    is removed from the result, unless the uriRef had a scheme. Thus, if\n    neither argument has a scheme component, the result won't have one.\n\n    This function is named BaseJoin because it is very much like\n    urllib.basejoin(), but it follows the current rfc3986 algorithms\n    for path merging, dot segment elimination, and inheritance of query\n    and fragment components.\n\n    WARNING: This function exists for 2 reasons: (1) because of a need\n    within the 4Suite repository to perform URI reference absolutization\n    using base URIs that are stored (inappropriately) as absolute paths\n    in the subjects of statements in the RDF model, and (2) because of\n    a similar need to interpret relative repo paths in a 4Suite product\n    setup.xml file as being relative to a path that can be set outside\n    the document. When these needs go away, this function probably will,\n    too, so it is not advisable to use it.\n    "));
      var1.setlocal("BaseJoin", var5);
      var3 = null;
      var1.setline(294);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, RemoveDotSegments$6, PyString.fromInterned("\n    Supports Absolutize() by implementing the remove_dot_segments function\n    described in RFC 3986 sec. 5.2.  It collapses most of the '.' and '..'\n    segments out of a path without eliminating empty segments. It is intended\n    to be used during the path merging process and may not give expected\n    results when used independently. Use NormalizePathSegments() or\n    NormalizePathSegmentsInUri() if more general normalization is desired.\n\n    semi-private because it is not for general use. I've implemented it\n    using two segment stacks, as alluded to in the spec, rather than the\n    explicit string-walking algorithm that would be too inefficient. (mbrown)\n    "));
      var1.setlocal("RemoveDotSegments", var5);
      var3 = null;
      var1.setline(354);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([a-zA-Z][a-zA-Z0-9+\\-.]*):"));
      var1.setlocal("SCHEME_PATTERN", var3);
      var3 = null;
      var1.setline(355);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, GetScheme$7, PyString.fromInterned("\n    Obtains, with optimum efficiency, just the scheme from a URI reference.\n    Returns a string, or if no scheme could be found, returns None.\n    "));
      var1.setlocal("GetScheme", var5);
      var3 = null;
      var1.setline(374);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, IsAbsolute$8, PyString.fromInterned("\n    Given a string believed to be a URI or URI reference, tests that it is\n    absolute (as per RFC 2396), not relative -- i.e., that it has a scheme.\n    "));
      var1.setlocal("IsAbsolute", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UnsplitUriRef$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyString.fromInterned("should replace urlparse.urlunsplit\n\n    Given a sequence as would be produced by SplitUriRef(), assembles and\n    returns a URI reference as a string.\n    ");
      var1.setline(24);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("tuple"), var1.getglobal("list")}))).__not__().__nonzero__()) {
         var1.setline(25);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("sequence expected, got %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.setline(26);
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
         var1.setline(27);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(28);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(29);
            var3 = var1.getlocal(6);
            var3 = var3._iadd(var1.getlocal(1)._add(PyString.fromInterned(":")));
            var1.setlocal(6, var3);
         }

         var1.setline(30);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(31);
            var3 = var1.getlocal(6);
            var3 = var3._iadd(PyString.fromInterned("//")._add(var1.getlocal(2)));
            var1.setlocal(6, var3);
         }

         var1.setline(32);
         var3 = var1.getlocal(6);
         var3 = var3._iadd(var1.getlocal(3));
         var1.setlocal(6, var3);
         var1.setline(33);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(34);
            var3 = var1.getlocal(6);
            var3 = var3._iadd(PyString.fromInterned("?")._add(var1.getlocal(4)));
            var1.setlocal(6, var3);
         }

         var1.setline(35);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(36);
            var3 = var1.getlocal(6);
            var3 = var3._iadd(PyString.fromInterned("#")._add(var1.getlocal(5)));
            var1.setlocal(6, var3);
         }

         var1.setline(37);
         var3 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject SplitUriRef$2(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("should replace urlparse.urlsplit\n\n    Given a valid URI reference as a string, returns a tuple representing the\n    generic URI components, as per RFC 2396 appendix B. The tuple's structure\n    is (scheme, authority, path, query, fragment).\n\n    All values will be strings (possibly empty) or None if undefined.\n\n    Note that per rfc3986, there is no distinction between a path and\n    an \"opaque part\", as there was in RFC 2396.\n    ");
      var1.setline(55);
      PyObject var3 = var1.getglobal("SPLIT_URI_REF_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(0)).__getattr__("groupdict").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("scheme"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("authority"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("path"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("query"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("fragment"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(61);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Absolutize$3(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("\n    Resolves a URI reference to absolute form, effecting the result of RFC\n    3986 section 5. The URI reference is considered to be relative to the\n    given base URI.\n\n    It is the caller's responsibility to ensure that the base URI matches\n    the absolute-URI syntax rule of RFC 3986, and that its path component\n    does not contain '.' or '..' segments if the scheme is hierarchical.\n    Unexpected results may occur otherwise.\n\n    This function only conducts a minimal sanity check in order to determine\n    if relative resolution is possible: it raises a UriException if the base\n    URI does not have a scheme component. While it is true that the base URI\n    is irrelevant if the URI reference has a scheme, an exception is raised\n    in order to signal that the given string does not even come close to\n    meeting the criteria to be usable as a base URI.\n\n    It is the caller's responsibility to make a determination of whether the\n    URI reference constitutes a \"same-document reference\", as defined in RFC\n    2396 or RFC 3986. As per the spec, dereferencing a same-document\n    reference \"should not\" involve retrieval of a new representation of the\n    referenced resource. Note that the two specs have different definitions\n    of same-document reference: RFC 2396 says it is *only* the cases where the\n    reference is the empty string, or \"#\" followed by a fragment; RFC 3986\n    requires making a comparison of the base URI to the absolute form of the\n    reference (as is returned by the spec), minus its fragment component,\n    if any.\n\n    This function is similar to urlparse.urljoin() and urllib.basejoin().\n    Those functions, however, are (as of Python 2.3) outdated, buggy, and/or\n    designed to produce results acceptable for use with other core Python\n    libraries, rather than being earnest implementations of the relevant\n    specs. Their problems are most noticeable in their handling of\n    same-document references and 'file:' URIs, both being situations that\n    come up far too often to consider the functions reliable enough for\n    general use.\n    ");
      var1.setline(120);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(121);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("baseUri is required and must be a non empty string")));
      } else {
         var1.setline(122);
         if (var1.getglobal("IsAbsolute").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(123);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%r is not an absolute URI")._mod(var1.getlocal(1))));
         } else {
            var1.setline(125);
            PyObject var3 = var1.getlocal(0);
            PyObject var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var10000 = var3._eq(PyString.fromInterned("#"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(126);
               var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#")).__getitem__(Py.newInteger(0))._add(var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(128);
               PyObject var4 = var1.getglobal("None");
               var1.setlocal(2, var4);
               var1.setlocal(3, var4);
               var1.setlocal(4, var4);
               var1.setlocal(5, var4);
               var1.setline(130);
               var4 = var1.getglobal("SplitUriRef").__call__(var2, var1.getlocal(0));
               PyObject[] var5 = Py.unpackSequence(var4, 5);
               PyObject var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(7, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var5[3];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[4];
               var1.setlocal(10, var6);
               var6 = null;
               var4 = null;
               var1.setline(133);
               var4 = var1.getlocal(6);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(134);
                  var4 = var1.getlocal(6);
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(135);
                  var4 = var1.getlocal(7);
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(136);
                  var4 = var1.getglobal("RemoveDotSegments").__call__(var2, var1.getlocal(8));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(137);
                  var4 = var1.getlocal(9);
                  var1.setlocal(5, var4);
                  var4 = null;
               } else {
                  var1.setline(140);
                  var4 = var1.getglobal("SplitUriRef").__call__(var2, var1.getlocal(1));
                  var5 = Py.unpackSequence(var4, 5);
                  var6 = var5[0];
                  var1.setlocal(11, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(12, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(13, var6);
                  var6 = null;
                  var6 = var5[3];
                  var1.setlocal(14, var6);
                  var6 = null;
                  var6 = var5[4];
                  var1.setlocal(15, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(143);
                  var4 = var1.getlocal(7);
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(144);
                     var4 = var1.getlocal(7);
                     var1.setlocal(3, var4);
                     var4 = null;
                     var1.setline(145);
                     var4 = var1.getglobal("RemoveDotSegments").__call__(var2, var1.getlocal(8));
                     var1.setlocal(4, var4);
                     var4 = null;
                     var1.setline(146);
                     var4 = var1.getlocal(9);
                     var1.setlocal(5, var4);
                     var4 = null;
                  } else {
                     var1.setline(150);
                     if (var1.getlocal(8).__not__().__nonzero__()) {
                        var1.setline(151);
                        var4 = var1.getlocal(13);
                        var1.setlocal(4, var4);
                        var4 = null;
                        var1.setline(153);
                        var4 = var1.getlocal(9);
                        var10000 = var4._isnot(var1.getglobal("None"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(9);
                        }

                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getlocal(14);
                        }

                        var4 = var10000;
                        var1.setlocal(5, var4);
                        var4 = null;
                     } else {
                        var1.setline(157);
                        var4 = var1.getlocal(8).__getitem__(Py.newInteger(0));
                        var10000 = var4._eq(PyString.fromInterned("/"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(158);
                           var4 = var1.getglobal("RemoveDotSegments").__call__(var2, var1.getlocal(8));
                           var1.setlocal(4, var4);
                           var4 = null;
                        } else {
                           var1.setline(161);
                           var4 = var1.getlocal(12);
                           var10000 = var4._isnot(var1.getglobal("None"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getlocal(13).__not__();
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(162);
                              var4 = PyString.fromInterned("/")._add(var1.getlocal(8));
                              var1.setlocal(4, var4);
                              var4 = null;
                           } else {
                              var1.setline(164);
                              var4 = var1.getlocal(13).__getslice__((PyObject)null, var1.getlocal(13).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"))._add(Py.newInteger(1)), (PyObject)null)._add(var1.getlocal(8));
                              var1.setlocal(4, var4);
                              var4 = null;
                           }

                           var1.setline(165);
                           var4 = var1.getglobal("RemoveDotSegments").__call__(var2, var1.getlocal(4));
                           var1.setlocal(4, var4);
                           var4 = null;
                        }

                        var1.setline(167);
                        var4 = var1.getlocal(9);
                        var1.setlocal(5, var4);
                        var4 = null;
                     }

                     var1.setline(170);
                     var4 = var1.getlocal(12);
                     var1.setlocal(3, var4);
                     var4 = null;
                  }

                  var1.setline(172);
                  var4 = var1.getlocal(11);
                  var1.setlocal(2, var4);
                  var4 = null;
               }

               var1.setline(177);
               var3 = var1.getglobal("UnsplitUriRef").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(10)})));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject MakeUrllibSafe$4(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyString.fromInterned("\n    Makes the given RFC 3986-conformant URI reference safe for passing\n    to legacy urllib functions. The result may not be a valid URI.\n\n    As of Python 2.3.3, urllib.urlopen() does not fully support\n    internationalized domain names, it does not strip fragment components,\n    and on Windows, it expects file URIs to use '|' instead of ':' in the\n    path component corresponding to the drivespec. It also relies on\n    urllib.unquote(), which mishandles unicode arguments. This function\n    produces a URI reference that will work around these issues, although\n    the IDN workaround is limited to Python 2.3 only. May raise a\n    UnicodeEncodeError if the URI reference is Unicode and erroneously\n    contains non-ASCII characters.\n    ");
      var1.setline(209);
      PyException var3;
      PyObject var7;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         try {
            var1.setline(211);
            var7 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
            var1.setlocal(0, var7);
            var3 = null;
         } catch (Throwable var6) {
            var3 = Py.setException(var6, var1);
            if (var3.match(var1.getglobal("UnicodeError"))) {
               var1.setline(213);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("uri %r must consist of ASCII characters.")._mod(var1.getlocal(0))));
            }

            throw var3;
         }
      }

      var1.setline(214);
      var7 = var1.getglobal("urlparse").__getattr__("urlsplit").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var7, 5);
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
      var1.setline(215);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"));
         var10000 = var7._gt(Py.newInteger(-1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(216);
         var7 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"));
         var4 = Py.unpackSequence(var7, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(218);
         var7 = var1.getglobal("None");
         var1.setlocal(6, var7);
         var3 = null;
         var1.setline(219);
         var7 = var1.getlocal(2);
         var1.setlocal(7, var7);
         var3 = null;
      }

      var1.setline(220);
      var10000 = var1.getlocal(7);
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(7).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var10000 = var7._gt(Py.newInteger(-1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(221);
         var7 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var4 = Py.unpackSequence(var7, 2);
         var5 = var4[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(9, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(223);
         var7 = var1.getlocal(7);
         var1.setlocal(8, var7);
         var3 = null;
         var1.setline(224);
         var7 = var1.getglobal("None");
         var1.setlocal(9, var7);
         var3 = null;
      }

      var1.setline(225);
      var10000 = var1.getlocal(8);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("REG_NAME_HOST_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(8));
      }

      if (var10000.__nonzero__()) {
         var1.setline(227);
         var7 = var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var7);
         var3 = null;
         var1.setline(231);
         var7 = var1.getglobal("sys").__getattr__("version_info").__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
         var10000 = var7._ge(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(3)}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(232);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("str")).__nonzero__()) {
               var1.setline(233);
               var7 = var1.getlocal(8).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
               var1.setlocal(8, var7);
               var3 = null;
            }

            var1.setline(234);
            var7 = var1.getlocal(8).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("idna"));
            var1.setlocal(8, var7);
            var3 = null;
         }

         var1.setline(237);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(238);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(239);
            var7 = var1.getlocal(2);
            var7 = var7._iadd(var1.getlocal(6)._add(PyString.fromInterned("@")));
            var1.setlocal(2, var7);
         }

         var1.setline(240);
         var7 = var1.getlocal(2);
         var7 = var7._iadd(var1.getlocal(8));
         var1.setlocal(2, var7);
         var1.setline(241);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(242);
            var7 = var1.getlocal(2);
            var7 = var7._iadd(PyString.fromInterned(":")._add(var1.getlocal(9)));
            var1.setlocal(2, var7);
         }
      }

      var1.setline(245);
      var7 = var1.getglobal("os").__getattr__("name");
      var10000 = var7._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(1);
         var10000 = var7._eq(PyString.fromInterned("file"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(246);
         var7 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, PyString.fromInterned(":"), (PyObject)PyString.fromInterned("|"), (PyObject)Py.newInteger(1));
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(249);
      var7 = var1.getglobal("urlparse").__getattr__("urlunsplit").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getglobal("None")})));
      var1.setlocal(10, var7);
      var3 = null;
      var1.setline(251);
      var7 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject BaseJoin$5(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString.fromInterned("\n    Merges a base URI reference with another URI reference, returning a\n    new URI reference.\n\n    It behaves exactly the same as Absolutize(), except the arguments\n    are reversed, and it accepts any URI reference (even a relative URI)\n    as the base URI. If the base has no scheme component, it is\n    evaluated as if it did, and then the scheme component of the result\n    is removed from the result, unless the uriRef had a scheme. Thus, if\n    neither argument has a scheme component, the result won't have one.\n\n    This function is named BaseJoin because it is very much like\n    urllib.basejoin(), but it follows the current rfc3986 algorithms\n    for path merging, dot segment elimination, and inheritance of query\n    and fragment components.\n\n    WARNING: This function exists for 2 reasons: (1) because of a need\n    within the 4Suite repository to perform URI reference absolutization\n    using base URIs that are stored (inappropriately) as absolute paths\n    in the subjects of statements in the RDF model, and (2) because of\n    a similar need to interpret relative repo paths in a 4Suite product\n    setup.xml file as being relative to a path that can be set outside\n    the document. When these needs go away, this function probably will,\n    too, so it is not advisable to use it.\n    ");
      var1.setline(281);
      PyObject var3;
      if (var1.getglobal("IsAbsolute").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(282);
         var3 = var1.getglobal("Absolutize").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(284);
         PyString var4 = PyString.fromInterned("basejoin");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(285);
         PyObject var5 = var1.getglobal("Absolutize").__call__(var2, var1.getlocal(1), PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0)})));
         var1.setlocal(3, var5);
         var4 = null;
         var1.setline(286);
         if (var1.getglobal("IsAbsolute").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(288);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(291);
            var3 = var1.getlocal(3).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject RemoveDotSegments$6(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("\n    Supports Absolutize() by implementing the remove_dot_segments function\n    described in RFC 3986 sec. 5.2.  It collapses most of the '.' and '..'\n    segments out of a path without eliminating empty segments. It is intended\n    to be used during the path merging process and may not give expected\n    results when used independently. Use NormalizePathSegments() or\n    NormalizePathSegmentsInUri() if more general normalization is desired.\n\n    semi-private because it is not for general use. I've implemented it\n    using two segment stacks, as alluded to in the spec, rather than the\n    explicit string-walking algorithm that would be too inefficient. (mbrown)\n    ");
      var1.setline(308);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("."));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(PyString.fromInterned(".."));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(309);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(0), Py.newInteger(0), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      } else {
         PyObject var4;
         while(true) {
            var1.setline(311);
            if (!var1.getlocal(0).__nonzero__()) {
               break;
            }

            var1.setline(312);
            var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("./"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(313);
               var4 = var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var4 = null;
            } else {
               var1.setline(314);
               var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("../"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(315);
               var4 = var1.getlocal(0).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var4 = null;
            }
         }

         var1.setline(322);
         PyInteger var5 = Py.newInteger(0);
         var1.setlocal(1, var5);
         var4 = null;
         var1.setline(323);
         var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("/"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(324);
            var4 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(325);
            var5 = Py.newInteger(1);
            var1.setlocal(1, var5);
            var4 = null;
         }

         var1.setline(327);
         var4 = var1.getlocal(0).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("/."));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(328);
            var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(331);
         var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(332);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(333);
         var1.getlocal(2).__getattr__("reverse").__call__(var2);

         while(true) {
            var1.setline(334);
            if (!var1.getlocal(2).__nonzero__()) {
               var1.setline(351);
               var3 = var1.getlocal(1)._mul(PyString.fromInterned("/"))._add(PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(3)));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(335);
            var4 = var1.getlocal(2).__getattr__("pop").__call__(var2);
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(340);
            var4 = var1.getlocal(4);
            var10000 = var4._eq(PyString.fromInterned(".."));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(341);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(342);
                  var1.getlocal(3).__getattr__("pop").__call__(var2);
               } else {
                  var1.setline(343);
                  if (var1.getlocal(1).__not__().__nonzero__()) {
                     var1.setline(344);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
                  }
               }

               var1.setline(345);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(346);
                  var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
               }
            } else {
               var1.setline(348);
               var4 = var1.getlocal(4);
               var10000 = var4._ne(PyString.fromInterned("."));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(349);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            }
         }
      }
   }

   public PyObject GetScheme$7(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyString.fromInterned("\n    Obtains, with optimum efficiency, just the scheme from a URI reference.\n    Returns a string, or if no scheme could be found, returns None.\n    ");
      var1.setline(367);
      PyObject var3 = var1.getglobal("SCHEME_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(368);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(369);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(371);
         var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject IsAbsolute$8(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyString.fromInterned("\n    Given a string believed to be a URI or URI reference, tests that it is\n    absolute (as per RFC 2396), not relative -- i.e., that it has a scheme.\n    ");
      var1.setline(380);
      PyObject var3 = var1.getglobal("GetScheme").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public Uri$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"uriRefSeq", "scheme", "authority", "path", "query", "fragment", "uri"};
      UnsplitUriRef$1 = Py.newCode(1, var2, var1, "UnsplitUriRef", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"uriref", "g", "scheme", "authority", "path", "query", "fragment"};
      SplitUriRef$2 = Py.newCode(1, var2, var1, "SplitUriRef", 41, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"uriRef", "baseUri", "tScheme", "tAuth", "tPath", "tQuery", "rScheme", "rAuth", "rPath", "rQuery", "rFrag", "bScheme", "bAuth", "bPath", "bQuery", "bFrag"};
      Absolutize$3 = Py.newCode(2, var2, var1, "Absolutize", 64, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"uriRef", "scheme", "auth", "path", "query", "frag", "userinfo", "hostport", "host", "port", "uri"};
      MakeUrllibSafe$4 = Py.newCode(1, var2, var1, "MakeUrllibSafe", 182, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base", "uriRef", "dummyscheme", "res"};
      BaseJoin$5 = Py.newCode(2, var2, var1, "BaseJoin", 255, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "leading_slash", "segments", "keepers", "seg"};
      RemoveDotSegments$6 = Py.newCode(1, var2, var1, "RemoveDotSegments", 294, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"uriRef", "m"};
      GetScheme$7 = Py.newCode(1, var2, var1, "GetScheme", 355, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"identifier"};
      IsAbsolute$8 = Py.newCode(1, var2, var1, "IsAbsolute", 374, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new Uri$py("xml/Uri$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(Uri$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UnsplitUriRef$1(var2, var3);
         case 2:
            return this.SplitUriRef$2(var2, var3);
         case 3:
            return this.Absolutize$3(var2, var3);
         case 4:
            return this.MakeUrllibSafe$4(var2, var3);
         case 5:
            return this.BaseJoin$5(var2, var3);
         case 6:
            return this.RemoveDotSegments$6(var2, var3);
         case 7:
            return this.GetScheme$7(var2, var3);
         case 8:
            return this.IsAbsolute$8(var2, var3);
         default:
            return null;
      }
   }
}
