package email;

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
@MTime(1498849383000L)
@Filename("email/utils.py")
public class utils$py extends PyFunctionTable implements PyRunnable {
   static utils$py self;
   static final PyCode f$0;
   static final PyCode _identity$1;
   static final PyCode _bdecode$2;
   static final PyCode fix_eols$3;
   static final PyCode formataddr$4;
   static final PyCode getaddresses$5;
   static final PyCode formatdate$6;
   static final PyCode make_msgid$7;
   static final PyCode parsedate$8;
   static final PyCode parsedate_tz$9;
   static final PyCode parseaddr$10;
   static final PyCode unquote$11;
   static final PyCode decode_rfc2231$12;
   static final PyCode encode_rfc2231$13;
   static final PyCode decode_params$14;
   static final PyCode collapse_rfc2231_value$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Miscellaneous utilities."));
      var1.setline(5);
      PyString.fromInterned("Miscellaneous utilities.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("collapse_rfc2231_value"), PyString.fromInterned("decode_params"), PyString.fromInterned("decode_rfc2231"), PyString.fromInterned("encode_rfc2231"), PyString.fromInterned("formataddr"), PyString.fromInterned("formatdate"), PyString.fromInterned("getaddresses"), PyString.fromInterned("make_msgid"), PyString.fromInterned("mktime_tz"), PyString.fromInterned("parseaddr"), PyString.fromInterned("parsedate"), PyString.fromInterned("parsedate_tz"), PyString.fromInterned("unquote")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(23);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(24);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(25);
      var5 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var5);
      var3 = null;
      var1.setline(26);
      var5 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var5);
      var3 = null;
      var1.setline(27);
      var5 = imp.importOne("random", var1, -1);
      var1.setlocal("random", var5);
      var3 = null;
      var1.setline(28);
      var5 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var5);
      var3 = null;
      var1.setline(29);
      var5 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var5);
      var3 = null;
      var1.setline(30);
      var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(32);
      String[] var6 = new String[]{"quote"};
      PyObject[] var7 = imp.importFrom("email._parseaddr", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("quote", var4);
      var4 = null;
      var1.setline(33);
      var6 = new String[]{"AddressList"};
      var7 = imp.importFrom("email._parseaddr", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_AddressList", var4);
      var4 = null;
      var1.setline(34);
      var6 = new String[]{"mktime_tz"};
      var7 = imp.importFrom("email._parseaddr", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("mktime_tz", var4);
      var4 = null;
      var1.setline(37);
      var6 = new String[]{"parsedate"};
      var7 = imp.importFrom("email._parseaddr", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_parsedate", var4);
      var4 = null;
      var1.setline(38);
      var6 = new String[]{"parsedate_tz"};
      var7 = imp.importFrom("email._parseaddr", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_parsedate_tz", var4);
      var4 = null;
      var1.setline(40);
      var6 = new String[]{"decodestring"};
      var7 = imp.importFrom("quopri", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_qdecode", var4);
      var4 = null;
      var1.setline(43);
      var6 = new String[]{"_bencode", "_qencode"};
      var7 = imp.importFrom("email.encoders", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_bencode", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("_qencode", var4);
      var4 = null;
      var1.setline(45);
      PyString var8 = PyString.fromInterned(", ");
      var1.setlocal("COMMASPACE", var8);
      var3 = null;
      var1.setline(46);
      var8 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var8);
      var3 = null;
      var1.setline(47);
      PyUnicode var9 = PyUnicode.fromInterned("");
      var1.setlocal("UEMPTYSTRING", var9);
      var3 = null;
      var1.setline(48);
      var8 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var8);
      var3 = null;
      var1.setline(49);
      var8 = PyString.fromInterned("'");
      var1.setlocal("TICK", var8);
      var3 = null;
      var1.setline(51);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[][\\\\()<>@,:;\".]"));
      var1.setlocal("specialsre", var5);
      var3 = null;
      var1.setline(52);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[][\\\\()\"]"));
      var1.setlocal("escapesre", var5);
      var3 = null;
      var1.setline(58);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, _identity$1, (PyObject)null);
      var1.setlocal("_identity", var10);
      var3 = null;
      var1.setline(62);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _bdecode$2, PyString.fromInterned("Decodes a base64 string.\n\n    This function is equivalent to base64.decodestring and it's retained only\n    for backward compatibility. It used to remove the last \\n of the decoded\n    string, if it had any (see issue 7143).\n    "));
      var1.setlocal("_bdecode", var10);
      var3 = null;
      var1.setline(75);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, fix_eols$3, PyString.fromInterned("Replace all line-ending characters with \\r\\n."));
      var1.setlocal("fix_eols", var10);
      var3 = null;
      var1.setline(85);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, formataddr$4, PyString.fromInterned("The inverse of parseaddr(), this takes a 2-tuple of the form\n    (realname, email_address) and returns the string value suitable\n    for an RFC 2822 From, To or Cc header.\n\n    If the first element of pair is false, then the second element is\n    returned unmodified.\n    "));
      var1.setlocal("formataddr", var10);
      var3 = null;
      var1.setline(104);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, getaddresses$5, PyString.fromInterned("Return a list of (REALNAME, EMAIL) for each fieldvalue."));
      var1.setlocal("getaddresses", var10);
      var3 = null;
      var1.setline(112);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n  =\\?                   # literal =?\n  (?P<charset>[^?]*?)   # non-greedy up to the next ? is the charset\n  \\?                    # literal ?\n  (?P<encoding>[qb])    # either a \"q\" or a \"b\", case insensitive\n  \\?                    # literal ?\n  (?P<atom>.*?)         # non-greedy up to the next ?= is the atom\n  \\?=                   # literal ?=\n  "), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("IGNORECASE")));
      var1.setlocal("ecre", var5);
      var3 = null;
      var1.setline(124);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var10 = new PyFunction(var1.f_globals, var7, formatdate$6, PyString.fromInterned("Returns a date string as specified by RFC 2822, e.g.:\n\n    Fri, 09 Nov 2001 01:08:47 -0000\n\n    Optional timeval if given is a floating point time value as accepted by\n    gmtime() and localtime(), otherwise the current time is used.\n\n    Optional localtime is a flag that when True, interprets timeval, and\n    returns a date relative to the local timezone instead of UTC, properly\n    taking daylight savings time into account.\n\n    Optional argument usegmt means that the timezone is written out as\n    an ascii string, not numeric one (so \"GMT\" instead of \"+0000\"). This\n    is needed for HTTP, and is only used when localtime==False.\n    "));
      var1.setlocal("formatdate", var10);
      var3 = null;
      var1.setline(177);
      var7 = new PyObject[]{var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var7, make_msgid$7, PyString.fromInterned("Returns a string suitable for RFC 2822 compliant Message-ID, e.g:\n\n    <20020201195627.33539.96671@nightshade.la.mastaler.com>\n\n    Optional idstring if given is a string used to strengthen the\n    uniqueness of the message id.\n    "));
      var1.setlocal("make_msgid", var10);
      var3 = null;
      var1.setline(202);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, parsedate$8, (PyObject)null);
      var1.setlocal("parsedate", var10);
      var3 = null;
      var1.setline(208);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, parsedate_tz$9, (PyObject)null);
      var1.setlocal("parsedate_tz", var10);
      var3 = null;
      var1.setline(214);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, parseaddr$10, (PyObject)null);
      var1.setlocal("parseaddr", var10);
      var3 = null;
      var1.setline(222);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, unquote$11, PyString.fromInterned("Remove quotes from a string."));
      var1.setlocal("unquote", var10);
      var3 = null;
      var1.setline(234);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, decode_rfc2231$12, PyString.fromInterned("Decode string according to RFC 2231"));
      var1.setlocal("decode_rfc2231", var10);
      var3 = null;
      var1.setline(242);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var7, encode_rfc2231$13, PyString.fromInterned("Encode string according to RFC 2231.\n\n    If neither charset nor language is given, then s is returned as-is.  If\n    charset is given but not language, the string is encoded using the empty\n    string for language.\n    "));
      var1.setlocal("encode_rfc2231", var10);
      var3 = null;
      var1.setline(258);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?P<name>\\w+)\\*((?P<num>[0-9]+)\\*?)?$"));
      var1.setlocal("rfc2231_continuation", var5);
      var3 = null;
      var1.setline(260);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, decode_params$14, PyString.fromInterned("Decode parameters list according to RFC 2231.\n\n    params is a sequence of 2-tuples containing (param name, string value).\n    "));
      var1.setlocal("decode_params", var10);
      var3 = null;
      var1.setline(313);
      var7 = new PyObject[]{PyString.fromInterned("replace"), PyString.fromInterned("us-ascii")};
      var10 = new PyFunction(var1.f_globals, var7, collapse_rfc2231_value$15, (PyObject)null);
      var1.setlocal("collapse_rfc2231_value", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _identity$1(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _bdecode$2(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Decodes a base64 string.\n\n    This function is equivalent to base64.decodestring and it's retained only\n    for backward compatibility. It used to remove the last \\n of the decoded\n    string, if it had any (see issue 7143).\n    ");
      var1.setline(69);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(70);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(71);
         var3 = var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject fix_eols$3(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("Replace all line-ending characters with \\r\\n.");
      var1.setline(78);
      PyObject var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?<!\\r)\\n"), (PyObject)var1.getglobal("CRLF"), (PyObject)var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("\\r(?!\\n)"), (PyObject)var1.getglobal("CRLF"), (PyObject)var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(81);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formataddr$4(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("The inverse of parseaddr(), this takes a 2-tuple of the form\n    (realname, email_address) and returns the string value suitable\n    for an RFC 2822 From, To or Cc header.\n\n    If the first element of pair is false, then the second element is\n    returned unmodified.\n    ");
      var1.setline(93);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(94);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(95);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(96);
         if (var1.getglobal("specialsre").__getattr__("search").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(97);
            var6 = PyString.fromInterned("\"");
            var1.setlocal(3, var6);
            var3 = null;
         }

         var1.setline(98);
         var3 = var1.getglobal("escapesre").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\\\g<0>"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(99);
         var3 = PyString.fromInterned("%s%s%s <%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1), var1.getlocal(3), var1.getlocal(2)}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(100);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getaddresses$5(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Return a list of (REALNAME, EMAIL) for each fieldvalue.");
      var1.setline(106);
      PyObject var3 = var1.getglobal("COMMASPACE").__getattr__("join").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(107);
      var3 = var1.getglobal("_AddressList").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getlocal(2).__getattr__("addresslist");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatdate$6(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyString.fromInterned("Returns a date string as specified by RFC 2822, e.g.:\n\n    Fri, 09 Nov 2001 01:08:47 -0000\n\n    Optional timeval if given is a floating point time value as accepted by\n    gmtime() and localtime(), otherwise the current time is used.\n\n    Optional localtime is a flag that when True, interprets timeval, and\n    returns a date relative to the local timezone instead of UTC, properly\n    taking daylight savings time into account.\n\n    Optional argument usegmt means that the timezone is written out as\n    an ascii string, not numeric one (so \"GMT\" instead of \"+0000\"). This\n    is needed for HTTP, and is only used when localtime==False.\n    ");
      var1.setline(142);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(143);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(144);
      PyString var6;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(145);
         var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(148);
         var10000 = var1.getglobal("time").__getattr__("daylight");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
         }

         if (var10000.__nonzero__()) {
            var1.setline(149);
            var3 = var1.getglobal("time").__getattr__("altzone");
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(151);
            var3 = var1.getglobal("time").__getattr__("timezone");
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(152);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getglobal("abs").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(3600));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(155);
         var3 = var1.getlocal(4);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(156);
            var6 = PyString.fromInterned("-");
            var1.setlocal(7, var6);
            var3 = null;
         } else {
            var1.setline(158);
            var6 = PyString.fromInterned("+");
            var1.setlocal(7, var6);
            var3 = null;
         }

         var1.setline(159);
         var3 = PyString.fromInterned("%s%02d%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5), var1.getlocal(6)._floordiv(Py.newInteger(60))}));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(161);
         var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(163);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(164);
            var6 = PyString.fromInterned("GMT");
            var1.setlocal(8, var6);
            var3 = null;
         } else {
            var1.setline(166);
            var6 = PyString.fromInterned("-0000");
            var1.setlocal(8, var6);
            var3 = null;
         }
      }

      var1.setline(167);
      var3 = PyString.fromInterned("%s, %02d %s %04d %02d:%02d:%02d %s")._mod(new PyTuple(new PyObject[]{(new PyList(new PyObject[]{PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")})).__getitem__(var1.getlocal(3).__getitem__(Py.newInteger(6))), var1.getlocal(3).__getitem__(Py.newInteger(2)), (new PyList(new PyObject[]{PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")})).__getitem__(var1.getlocal(3).__getitem__(Py.newInteger(1))._sub(Py.newInteger(1))), var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getitem__(Py.newInteger(3)), var1.getlocal(3).__getitem__(Py.newInteger(4)), var1.getlocal(3).__getitem__(Py.newInteger(5)), var1.getlocal(8)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject make_msgid$7(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyString.fromInterned("Returns a string suitable for RFC 2822 compliant Message-ID, e.g:\n\n    <20020201195627.33539.96671@nightshade.la.mastaler.com>\n\n    Optional idstring if given is a string used to strengthen the\n    uniqueness of the message id.\n    ");
      var1.setline(185);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(186);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y%m%d%H%M%S"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(188);
      var3 = var1.getglobal("random").__getattr__("randrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(100000));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(190);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(0, var4);
         var3 = null;
      } else {
         var1.setline(192);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(193);
      var3 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(194);
      var3 = PyString.fromInterned("<%s.%s.%s%s@%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(0), var1.getlocal(5)}));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(195);
      var3 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parsedate$8(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(204);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(205);
         var3 = var1.getglobal("_parsedate").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parsedate_tz$9(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(210);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(211);
         var3 = var1.getglobal("_parsedate_tz").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parseaddr$10(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyObject var3 = var1.getglobal("_AddressList").__call__(var2, var1.getlocal(0)).__getattr__("addresslist");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(216);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(217);
         PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(218);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unquote$11(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("Remove quotes from a string.");
      var1.setline(224);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(225);
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
         }

         if (var10000.__nonzero__()) {
            var1.setline(226);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\"), (PyObject)PyString.fromInterned("\\")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\""), (PyObject)PyString.fromInterned("\""));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(227);
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(228);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(229);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode_rfc2231$12(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyString.fromInterned("Decode string according to RFC 2231");
      var1.setline(236);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("TICK"), (PyObject)Py.newInteger(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._le(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(238);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(239);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject encode_rfc2231$13(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("Encode string according to RFC 2231.\n\n    If neither charset nor language is given, then s is returned as-is.  If\n    charset is given but not language, the string is encoded using the empty\n    string for language.\n    ");
      var1.setline(249);
      PyObject var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(250);
      PyObject var10000 = var1.getlocal(3).__getattr__("quote");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), PyString.fromInterned("")};
      String[] var4 = new String[]{"safe"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(252);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(253);
         PyObject var6 = var1.getlocal(2);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(254);
            PyString var7 = PyString.fromInterned("");
            var1.setlocal(2, var7);
            var4 = null;
         }

         var1.setline(255);
         var3 = PyString.fromInterned("%s'%s'%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0)}));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject decode_params$14(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      PyString.fromInterned("Decode parameters list according to RFC 2231.\n\n    params is a sequence of 2-tuples containing (param name, string value).\n    ");
      var1.setline(266);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(267);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var9);
      var3 = null;
      var1.setline(271);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var10);
      var3 = null;
      var1.setline(272);
      var3 = var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(273);
      var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));

      while(true) {
         var1.setline(274);
         if (!var1.getlocal(0).__nonzero__()) {
            var1.setline(289);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(290);
               var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(290);
                  PyObject var11 = var3.__iternext__();
                  if (var11 == null) {
                     break;
                  }

                  PyObject[] var14 = Py.unpackSequence(var11, 2);
                  PyObject var6 = var14[0];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var6 = var14[1];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(291);
                  PyList var15 = new PyList(Py.EmptyObjects);
                  var1.setlocal(4, var15);
                  var5 = null;
                  var1.setline(292);
                  var5 = var1.getglobal("False");
                  var1.setlocal(9, var5);
                  var5 = null;
                  var1.setline(294);
                  var1.getlocal(8).__getattr__("sort").__call__(var2);
                  var1.setline(300);
                  var5 = var1.getlocal(8).__iter__();

                  while(true) {
                     var1.setline(300);
                     var6 = var5.__iternext__();
                     PyObject[] var7;
                     PyObject var12;
                     if (var6 == null) {
                        var1.setline(305);
                        var5 = var1.getglobal("quote").__call__(var2, var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4)));
                        var1.setlocal(4, var5);
                        var5 = null;
                        var1.setline(306);
                        if (var1.getlocal(9).__nonzero__()) {
                           var1.setline(307);
                           var5 = var1.getglobal("decode_rfc2231").__call__(var2, var1.getlocal(4));
                           PyObject[] var13 = Py.unpackSequence(var5, 3);
                           var12 = var13[0];
                           var1.setlocal(11, var12);
                           var7 = null;
                           var12 = var13[1];
                           var1.setlocal(12, var12);
                           var7 = null;
                           var12 = var13[2];
                           var1.setlocal(4, var12);
                           var7 = null;
                           var5 = null;
                           var1.setline(308);
                           var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12), PyString.fromInterned("\"%s\"")._mod(var1.getlocal(4))})})));
                        } else {
                           var1.setline(310);
                           var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("\"%s\"")._mod(var1.getlocal(4))})));
                        }
                        break;
                     }

                     var7 = Py.unpackSequence(var6, 3);
                     PyObject var8 = var7[0];
                     var1.setlocal(7, var8);
                     var8 = null;
                     var8 = var7[1];
                     var1.setlocal(10, var8);
                     var8 = null;
                     var8 = var7[2];
                     var1.setlocal(5, var8);
                     var8 = null;
                     var1.setline(301);
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(302);
                        var12 = var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(10));
                        var1.setlocal(10, var12);
                        var7 = null;
                        var1.setline(303);
                        var12 = var1.getglobal("True");
                        var1.setlocal(9, var12);
                        var7 = null;
                     }

                     var1.setline(304);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(10));
                  }
               }
            }

            var1.setline(311);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(275);
         var3 = var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(276);
         if (var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*")).__nonzero__()) {
            var1.setline(277);
            var3 = var1.getglobal("True");
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(279);
            var3 = var1.getglobal("False");
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(280);
         var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(281);
         var3 = var1.getglobal("rfc2231_continuation").__getattr__("match").__call__(var2, var1.getlocal(3));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(282);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(283);
            var3 = var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"), (PyObject)PyString.fromInterned("num"));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
            var1.setline(284);
            var3 = var1.getlocal(7);
            PyObject var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(285);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(286);
            var1.getlocal(2).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(Py.EmptyObjects))).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(4), var1.getlocal(5)})));
         } else {
            var1.setline(288);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("\"%s\"")._mod(var1.getglobal("quote").__call__(var2, var1.getlocal(4)))})));
         }
      }
   }

   public PyObject collapse_rfc2231_value$15(PyFrame var1, ThreadState var2) {
      var1.setline(315);
      PyObject var3;
      if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(324);
         var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(316);
         var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(2)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(317);
         Object var10000 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("us-ascii");
         }

         Object var6 = var10000;
         var1.setlocal(4, (PyObject)var6);
         var3 = null;

         try {
            var1.setline(319);
            var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("LookupError"))) {
               var1.setline(322);
               var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      }
   }

   public utils$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      _identity$1 = Py.newCode(1, var2, var1, "_identity", 58, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      _bdecode$2 = Py.newCode(1, var2, var1, "_bdecode", 62, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      fix_eols$3 = Py.newCode(1, var2, var1, "fix_eols", 75, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pair", "name", "address", "quotes"};
      formataddr$4 = Py.newCode(1, var2, var1, "formataddr", 85, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fieldvalues", "all", "a"};
      getaddresses$5 = Py.newCode(1, var2, var1, "getaddresses", 104, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timeval", "localtime", "usegmt", "now", "offset", "hours", "minutes", "sign", "zone"};
      formatdate$6 = Py.newCode(3, var2, var1, "formatdate", 124, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"idstring", "timeval", "utcdate", "pid", "randint", "idhost", "msgid"};
      make_msgid$7 = Py.newCode(1, var2, var1, "make_msgid", 177, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data"};
      parsedate$8 = Py.newCode(1, var2, var1, "parsedate", 202, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data"};
      parsedate_tz$9 = Py.newCode(1, var2, var1, "parsedate_tz", 208, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr", "addrs"};
      parseaddr$10 = Py.newCode(1, var2, var1, "parseaddr", 214, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str"};
      unquote$11 = Py.newCode(1, var2, var1, "unquote", 222, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "parts"};
      decode_rfc2231$12 = Py.newCode(1, var2, var1, "decode_rfc2231", 234, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "charset", "language", "urllib"};
      encode_rfc2231$13 = Py.newCode(3, var2, var1, "encode_rfc2231", 242, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"params", "new_params", "rfc2231_params", "name", "value", "encoded", "mo", "num", "continuations", "extended", "s", "charset", "language"};
      decode_params$14 = Py.newCode(1, var2, var1, "decode_params", 260, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value", "errors", "fallback_charset", "rawval", "charset"};
      collapse_rfc2231_value$15 = Py.newCode(3, var2, var1, "collapse_rfc2231_value", 313, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new utils$py("email/utils$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(utils$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._identity$1(var2, var3);
         case 2:
            return this._bdecode$2(var2, var3);
         case 3:
            return this.fix_eols$3(var2, var3);
         case 4:
            return this.formataddr$4(var2, var3);
         case 5:
            return this.getaddresses$5(var2, var3);
         case 6:
            return this.formatdate$6(var2, var3);
         case 7:
            return this.make_msgid$7(var2, var3);
         case 8:
            return this.parsedate$8(var2, var3);
         case 9:
            return this.parsedate_tz$9(var2, var3);
         case 10:
            return this.parseaddr$10(var2, var3);
         case 11:
            return this.unquote$11(var2, var3);
         case 12:
            return this.decode_rfc2231$12(var2, var3);
         case 13:
            return this.encode_rfc2231$13(var2, var3);
         case 14:
            return this.decode_params$14(var2, var3);
         case 15:
            return this.collapse_rfc2231_value$15(var2, var3);
         default:
            return null;
      }
   }
}
