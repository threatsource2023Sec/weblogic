package email;

import java.util.Arrays;
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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/header.py")
public class header$py extends PyFunctionTable implements PyRunnable {
   static header$py self;
   static final PyCode f$0;
   static final PyCode decode_header$1;
   static final PyCode make_header$2;
   static final PyCode Header$3;
   static final PyCode __init__$4;
   static final PyCode __str__$5;
   static final PyCode __unicode__$6;
   static final PyCode __eq__$7;
   static final PyCode __ne__$8;
   static final PyCode append$9;
   static final PyCode _split$10;
   static final PyCode _split_ascii$11;
   static final PyCode _encode_chunks$12;
   static final PyCode encode$13;
   static final PyCode _split_ascii$14;
   static final PyCode _binsplit$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Header encoding and decoding functionality."));
      var1.setline(5);
      PyString.fromInterned("Header encoding and decoding functionality.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Header"), PyString.fromInterned("decode_header"), PyString.fromInterned("make_header")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(13);
      PyObject var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(14);
      var5 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var5);
      var3 = null;
      var1.setline(16);
      var5 = imp.importOne("email.quoprimime", var1, -1);
      var1.setlocal("email", var5);
      var3 = null;
      var1.setline(17);
      var5 = imp.importOne("email.base64mime", var1, -1);
      var1.setlocal("email", var5);
      var3 = null;
      var1.setline(19);
      String[] var6 = new String[]{"HeaderParseError"};
      PyObject[] var7 = imp.importFrom("email.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("HeaderParseError", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"Charset"};
      var7 = imp.importFrom("email.charset", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Charset", var4);
      var4 = null;
      var1.setline(22);
      PyString var8 = PyString.fromInterned("\n");
      var1.setlocal("NL", var8);
      var3 = null;
      var1.setline(23);
      var8 = PyString.fromInterned(" ");
      var1.setlocal("SPACE", var8);
      var3 = null;
      var1.setline(24);
      PyUnicode var9 = PyUnicode.fromInterned(" ");
      var1.setlocal("USPACE", var9);
      var3 = null;
      var1.setline(25);
      var5 = PyString.fromInterned(" ")._mul(Py.newInteger(8));
      var1.setlocal("SPACE8", var5);
      var3 = null;
      var1.setline(26);
      var9 = PyUnicode.fromInterned("");
      var1.setlocal("UEMPTYSTRING", var9);
      var3 = null;
      var1.setline(28);
      PyInteger var10 = Py.newInteger(76);
      var1.setlocal("MAXLINELEN", var10);
      var3 = null;
      var1.setline(30);
      var5 = var1.getname("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal("USASCII", var5);
      var3 = null;
      var1.setline(31);
      var5 = var1.getname("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal("UTF8", var5);
      var3 = null;
      var1.setline(34);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n  =\\?                   # literal =?\n  (?P<charset>[^?]*?)   # non-greedy up to the next ? is the charset\n  \\?                    # literal ?\n  (?P<encoding>[qb])    # either a \"q\" or a \"b\", case insensitive\n  \\?                    # literal ?\n  (?P<encoded>.*?)      # non-greedy up to the next ?= is the encoded string\n  \\?=                   # literal ?=\n  (?=[ \\t]|$)           # whitespace or the end of the string\n  "), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("IGNORECASE"))._or(var1.getname("re").__getattr__("MULTILINE")));
      var1.setlocal("ecre", var5);
      var3 = null;
      var1.setline(48);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\041-\\176]+:$"));
      var1.setlocal("fcre", var5);
      var3 = null;
      var1.setline(52);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\n[^ \\t]+:"));
      var1.setlocal("_embeded_header", var5);
      var3 = null;
      var1.setline(57);
      var5 = var1.getname("email").__getattr__("quoprimime").__getattr__("_max_append");
      var1.setlocal("_max_append", var5);
      var3 = null;
      var1.setline(61);
      var7 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var7, decode_header$1, PyString.fromInterned("Decode a message header value without converting charset.\n\n    Returns a list of (decoded_string, charset) pairs containing each of the\n    decoded parts of the header.  Charset is None for non-encoded parts of the\n    header, otherwise a lower-case string containing the name of the character\n    set specified in the encoded string.\n\n    An email.errors.HeaderParseError may be raised when certain decoding error\n    occurs (e.g. a base64 decoding exception).\n    "));
      var1.setlocal("decode_header", var11);
      var3 = null;
      var1.setline(121);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), PyString.fromInterned(" ")};
      var11 = new PyFunction(var1.f_globals, var7, make_header$2, PyString.fromInterned("Create a Header from a sequence of pairs as returned by decode_header()\n\n    decode_header() takes a header value string and returns a sequence of\n    pairs of the format (decoded_string, charset) where charset is the string\n    name of the character set.\n\n    This function takes one of those sequence of pairs and returns a Header\n    instance.  Optional maxlinelen, header_name, and continuation_ws are as in\n    the Header constructor.\n    "));
      var1.setlocal("make_header", var11);
      var3 = null;
      var1.setline(144);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Header", var7, Header$3);
      var1.setlocal("Header", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(418);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, _split_ascii$14, (PyObject)null);
      var1.setlocal("_split_ascii", var11);
      var3 = null;
      var1.setline(488);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, _binsplit$15, (PyObject)null);
      var1.setlocal("_binsplit", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode_header$1(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyString.fromInterned("Decode a message header value without converting charset.\n\n    Returns a list of (decoded_string, charset) pairs containing each of the\n    decoded parts of the header.  Charset is None for non-encoded parts of the\n    header, otherwise a lower-case string containing the name of the character\n    set specified in the encoded string.\n\n    An email.errors.HeaderParseError may be raised when certain decoding error\n    occurs (e.g. a base64 decoding exception).\n    ");
      var1.setline(73);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(74);
      if (var1.getglobal("ecre").__getattr__("search").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(75);
         PyList var10 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None")})});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(76);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(77);
         PyString var11 = PyString.fromInterned("");
         var1.setlocal(2, var11);
         var4 = null;
         var1.setline(78);
         PyObject var12 = var1.getlocal(0).__getattr__("splitlines").__call__(var2).__iter__();

         while(true) {
            while(true) {
               var1.setline(78);
               PyObject var5 = var12.__iternext__();
               if (var5 == null) {
                  var1.setline(117);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(80);
               if (var1.getglobal("ecre").__getattr__("search").__call__(var2, var1.getlocal(3)).__not__().__nonzero__()) {
                  var1.setline(81);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("None")})));
               } else {
                  var1.setline(83);
                  PyObject var6 = var1.getglobal("ecre").__getattr__("split").__call__(var2, var1.getlocal(3));
                  var1.setlocal(4, var6);
                  var6 = null;

                  while(true) {
                     var1.setline(84);
                     if (!var1.getlocal(4).__nonzero__()) {
                        break;
                     }

                     var1.setline(85);
                     var6 = var1.getlocal(4).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("strip").__call__(var2);
                     var1.setlocal(5, var6);
                     var6 = null;
                     var1.setline(86);
                     PyTuple var14;
                     PyObject var10000;
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(88);
                        var10000 = var1.getlocal(1);
                        if (var10000.__nonzero__()) {
                           var6 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                           var10000 = var6._is(var1.getglobal("None"));
                           var6 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(89);
                           var14 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0))._add(var1.getglobal("SPACE"))._add(var1.getlocal(5)), var1.getglobal("None")});
                           var1.getlocal(1).__setitem__((PyObject)Py.newInteger(-1), var14);
                           var6 = null;
                        } else {
                           var1.setline(91);
                           var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getglobal("None")})));
                        }
                     }

                     var1.setline(92);
                     if (var1.getlocal(4).__nonzero__()) {
                        var1.setline(93);
                        PyList var17 = new PyList();
                        var6 = var17.__getattr__("append");
                        var1.setlocal(8, var6);
                        var6 = null;
                        var1.setline(93);
                        var6 = var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null).__iter__();

                        while(true) {
                           var1.setline(93);
                           PyObject var7 = var6.__iternext__();
                           if (var7 == null) {
                              var1.setline(93);
                              var1.dellocal(8);
                              PyList var15 = var17;
                              PyObject[] var13 = Py.unpackSequence(var15, 2);
                              PyObject var8 = var13[0];
                              var1.setlocal(6, var8);
                              var8 = null;
                              var8 = var13[1];
                              var1.setlocal(7, var8);
                              var8 = null;
                              var6 = null;
                              var1.setline(94);
                              var6 = var1.getlocal(4).__getitem__(Py.newInteger(2));
                              var1.setlocal(10, var6);
                              var6 = null;
                              var1.setline(95);
                              var6 = var1.getglobal("None");
                              var1.setlocal(2, var6);
                              var6 = null;
                              var1.setline(96);
                              var6 = var1.getlocal(7);
                              var10000 = var6._eq(PyString.fromInterned("q"));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(97);
                                 var6 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("header_decode").__call__(var2, var1.getlocal(10));
                                 var1.setlocal(2, var6);
                                 var6 = null;
                              } else {
                                 var1.setline(98);
                                 var6 = var1.getlocal(7);
                                 var10000 = var6._eq(PyString.fromInterned("b"));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(99);
                                    var6 = var1.getglobal("len").__call__(var2, var1.getlocal(10))._mod(Py.newInteger(4));
                                    var1.setlocal(11, var6);
                                    var6 = null;
                                    var1.setline(100);
                                    if (var1.getlocal(11).__nonzero__()) {
                                       var1.setline(101);
                                       var6 = var1.getlocal(10);
                                       var6 = var6._iadd(PyString.fromInterned("===").__getslice__((PyObject)null, Py.newInteger(4)._sub(var1.getlocal(11)), (PyObject)null));
                                       var1.setlocal(10, var6);
                                    }

                                    try {
                                       var1.setline(103);
                                       var6 = var1.getglobal("email").__getattr__("base64mime").__getattr__("decode").__call__(var2, var1.getlocal(10));
                                       var1.setlocal(2, var6);
                                       var6 = null;
                                    } catch (Throwable var9) {
                                       PyException var16 = Py.setException(var9, var1);
                                       if (var16.match(var1.getglobal("binascii").__getattr__("Error"))) {
                                          var1.setline(108);
                                          throw Py.makeException(var1.getglobal("HeaderParseError"));
                                       }

                                       throw var16;
                                    }
                                 }
                              }

                              var1.setline(109);
                              var6 = var1.getlocal(2);
                              var10000 = var6._is(var1.getglobal("None"));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(110);
                                 var6 = var1.getlocal(10);
                                 var1.setlocal(2, var6);
                                 var6 = null;
                              }

                              var1.setline(112);
                              var10000 = var1.getlocal(1);
                              if (var10000.__nonzero__()) {
                                 var6 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                                 var10000 = var6._eq(var1.getlocal(6));
                                 var6 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(113);
                                 var14 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0))._add(var1.getlocal(2)), var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1))});
                                 var1.getlocal(1).__setitem__((PyObject)Py.newInteger(-1), var14);
                                 var6 = null;
                              } else {
                                 var1.setline(115);
                                 var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(6)})));
                              }
                              break;
                           }

                           var1.setlocal(9, var7);
                           var1.setline(93);
                           var1.getlocal(8).__call__(var2, var1.getlocal(9).__getattr__("lower").__call__(var2));
                        }
                     }

                     var1.setline(116);
                     var1.getlocal(4).__delslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
                  }
               }
            }
         }
      }
   }

   public PyObject make_header$2(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("Create a Header from a sequence of pairs as returned by decode_header()\n\n    decode_header() takes a header value string and returns a sequence of\n    pairs of the format (decoded_string, charset) where charset is the string\n    name of the character set.\n\n    This function takes one of those sequence of pairs and returns a Header\n    instance.  Optional maxlinelen, header_name, and continuation_ws are as in\n    the Header constructor.\n    ");
      var1.setline(133);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"maxlinelen", "header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var7 = var10000;
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(135);
      var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(135);
         PyObject var8 = var7.__iternext__();
         if (var8 == null) {
            var1.setline(140);
            var7 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var8, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(137);
         PyObject var9 = var1.getlocal(6);
         var10000 = var9._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("Charset")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(138);
            var9 = var1.getglobal("Charset").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var9);
            var5 = null;
         }

         var1.setline(139);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      }
   }

   public PyObject Header$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(145);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned(" "), PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, PyString.fromInterned("Create a MIME-compliant header that can contain many character sets.\n\n        Optional s is the initial header value.  If None, the initial header\n        value is not set.  You can later append to the header with .append()\n        method calls.  s may be a byte string or a Unicode string, but see the\n        .append() documentation for semantics.\n\n        Optional charset serves two purposes: it has the same meaning as the\n        charset argument to the .append() method.  It also sets the default\n        character set for all subsequent .append() calls that omit the charset\n        argument.  If charset is not provided in the constructor, the us-ascii\n        charset is used both as s's initial charset and as the default for\n        subsequent .append() calls.\n\n        The maximum line length can be specified explicit via maxlinelen.  For\n        splitting the first line to a shorter value (to account for the field\n        header which isn't included in s, e.g. `Subject') pass in the name of\n        the field in header_name.  The default maxlinelen is 76.\n\n        continuation_ws must be RFC 2822 compliant folding whitespace (usually\n        either a space or a hard tab) which will be prepended to continuation\n        lines.\n\n        errors is passed through to the .append() call.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$5, PyString.fromInterned("A synonym for self.encode()."));
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __unicode__$6, PyString.fromInterned("Helper for the built-in unicode function."));
      var1.setlocal("__unicode__", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$7, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$8, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(233);
      var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, append$9, PyString.fromInterned("Append a string to the MIME header.\n\n        Optional charset, if given, should be a Charset instance or the name\n        of a character set (which will be converted to a Charset instance).  A\n        value of None (the default) means that the charset given in the\n        constructor is used.\n\n        s may be a byte string or a Unicode string.  If it is a byte string\n        (i.e. isinstance(s, str) is true), then charset is the encoding of\n        that byte string, and a UnicodeError will be raised if the string\n        cannot be decoded with that charset.  If s is a Unicode string, then\n        charset is a hint specifying the character set of the characters in\n        the string.  In this case, when producing an RFC 2822 compliant header\n        using RFC 2047 rules, the Unicode string will be encoded using the\n        following charsets in order: us-ascii, the charset hint, utf-8.  The\n        first character set not to provoke a UnicodeError is used.\n\n        Optional `errors' is passed as the third argument to any unicode() or\n        ustr.encode() call.\n        "));
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(288);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _split$10, (PyObject)null);
      var1.setlocal("_split", var4);
      var3 = null;
      var1.setline(334);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _split_ascii$11, (PyObject)null);
      var1.setlocal("_split_ascii", var4);
      var3 = null;
      var1.setline(339);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _encode_chunks$12, (PyObject)null);
      var1.setlocal("_encode_chunks", var4);
      var3 = null;
      var1.setline(374);
      var3 = new PyObject[]{PyString.fromInterned(";, ")};
      var4 = new PyFunction(var1.f_globals, var3, encode$13, PyString.fromInterned("Encode a message header into an RFC-compliant format.\n\n        There are many issues involved in converting a given string for use in\n        an email header.  Only certain character sets are readable in most\n        email clients, and as header strings can only contain a subset of\n        7-bit ASCII, care must be taken to properly convert and encode (with\n        Base64 or quoted-printable) header strings.  In addition, there is a\n        75-character length limit on any given encoded header field, so\n        line-wrapping must be performed, even with double-byte character sets.\n\n        This method will do its best to convert the string to the correct\n        character set used in email, and encode and line wrap it safely with\n        the appropriate scheme for that character set.\n\n        If the given charset is not known or an error occurs during\n        conversion, this function will return the header untouched.\n\n        Optional splitchars is a string containing characters to split long\n        ASCII lines on, in rough support of RFC 2822's `highest level\n        syntactic breaks'.  This doesn't affect RFC 2047 encoded lines.\n        "));
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyString.fromInterned("Create a MIME-compliant header that can contain many character sets.\n\n        Optional s is the initial header value.  If None, the initial header\n        value is not set.  You can later append to the header with .append()\n        method calls.  s may be a byte string or a Unicode string, but see the\n        .append() documentation for semantics.\n\n        Optional charset serves two purposes: it has the same meaning as the\n        charset argument to the .append() method.  It also sets the default\n        character set for all subsequent .append() calls that omit the charset\n        argument.  If charset is not provided in the constructor, the us-ascii\n        charset is used both as s's initial charset and as the default for\n        subsequent .append() calls.\n\n        The maximum line length can be specified explicit via maxlinelen.  For\n        splitting the first line to a shorter value (to account for the field\n        header which isn't included in s, e.g. `Subject') pass in the name of\n        the field in header_name.  The default maxlinelen is 76.\n\n        continuation_ws must be RFC 2822 compliant folding whitespace (usually\n        either a space or a hard tab) which will be prepended to continuation\n        lines.\n\n        errors is passed through to the .append() call.\n        ");
      var1.setline(173);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(174);
         var3 = var1.getglobal("USASCII");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(175);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Charset")).__not__().__nonzero__()) {
         var1.setline(176);
         var3 = var1.getglobal("Charset").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(177);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_charset", var3);
      var3 = null;
      var1.setline(178);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("_continuation_ws", var3);
      var3 = null;
      var1.setline(179);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"), (PyObject)var1.getglobal("SPACE8")));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(181);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_chunks", var4);
      var3 = null;
      var1.setline(182);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(183);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(6));
      }

      var1.setline(184);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(185);
         var3 = var1.getglobal("MAXLINELEN");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(186);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(189);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("_firstlinelen", var3);
         var3 = null;
      } else {
         var1.setline(193);
         var3 = var1.getlocal(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(4)))._sub(Py.newInteger(2));
         var1.getlocal(0).__setattr__("_firstlinelen", var3);
         var3 = null;
      }

      var1.setline(196);
      var3 = var1.getlocal(3)._sub(var1.getlocal(7));
      var1.getlocal(0).__setattr__("_maxlinelen", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$5(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyString.fromInterned("A synonym for self.encode().");
      var1.setline(200);
      PyObject var3 = var1.getlocal(0).__getattr__("encode").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __unicode__$6(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyString.fromInterned("Helper for the built-in unicode function.");
      var1.setline(204);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(205);
      PyObject var7 = var1.getglobal("None");
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(206);
      var7 = var1.getlocal(0).__getattr__("_chunks").__iter__();

      while(true) {
         var1.setline(206);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(221);
            var7 = var1.getglobal("UEMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(211);
         PyObject var8 = var1.getlocal(4);
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(212);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(213);
            var8 = var1.getlocal(2);
            PyObject var10000 = var8._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii")}));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(214);
               var8 = var1.getlocal(5);
               var10000 = var8._in(new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(215);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("USPACE"));
                  var1.setline(216);
                  var8 = var1.getglobal("None");
                  var1.setlocal(5, var8);
                  var5 = null;
               }
            } else {
               var1.setline(217);
               var8 = var1.getlocal(5);
               var10000 = var8._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(218);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("USPACE"));
               }
            }
         }

         var1.setline(219);
         var8 = var1.getlocal(5);
         var1.setlocal(2, var8);
         var5 = null;
         var1.setline(220);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(3), var1.getglobal("str").__call__(var2, var1.getlocal(4))));
      }
   }

   public PyObject __eq__$7(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("encode").__call__(var2));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ne__$8(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject append$9(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("Append a string to the MIME header.\n\n        Optional charset, if given, should be a Charset instance or the name\n        of a character set (which will be converted to a Charset instance).  A\n        value of None (the default) means that the charset given in the\n        constructor is used.\n\n        s may be a byte string or a Unicode string.  If it is a byte string\n        (i.e. isinstance(s, str) is true), then charset is the encoding of\n        that byte string, and a UnicodeError will be raised if the string\n        cannot be decoded with that charset.  If s is a Unicode string, then\n        charset is a hint specifying the character set of the characters in\n        the string.  In this case, when producing an RFC 2822 compliant header\n        using RFC 2047 rules, the Unicode string will be encoded using the\n        following charsets in order: us-ascii, the charset hint, utf-8.  The\n        first character set not to provoke a UnicodeError is used.\n\n        Optional `errors' is passed as the third argument to any unicode() or\n        ustr.encode() call.\n        ");
      var1.setline(254);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(255);
         var3 = var1.getlocal(0).__getattr__("_charset");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(256);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Charset")).__not__().__nonzero__()) {
            var1.setline(257);
            var3 = var1.getglobal("Charset").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(259);
      var3 = var1.getlocal(2);
      var10000 = var3._ne(PyString.fromInterned("8bit"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(263);
         Object var10;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(266);
            var10 = var1.getlocal(2).__getattr__("input_codec");
            if (!((PyObject)var10).__nonzero__()) {
               var10 = PyString.fromInterned("us-ascii");
            }

            Object var8 = var10;
            var1.setlocal(4, (PyObject)var8);
            var3 = null;
            var1.setline(267);
            var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(271);
            var10 = var1.getlocal(2).__getattr__("output_codec");
            if (!((PyObject)var10).__nonzero__()) {
               var10 = PyString.fromInterned("us-ascii");
            }

            var8 = var10;
            var1.setlocal(6, (PyObject)var8);
            var3 = null;
            var1.setline(272);
            var1.getlocal(5).__getattr__("encode").__call__(var2, var1.getlocal(6), var1.getlocal(3));
         } else {
            var1.setline(273);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
               var1.setline(277);
               var3 = (new PyTuple(new PyObject[]{var1.getglobal("USASCII"), var1.getlocal(2), var1.getglobal("UTF8")})).__iter__();

               while(true) {
                  var1.setline(277);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(285);
                     if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("False").__nonzero__()) {
                        throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("utf-8 conversion failed"));
                     }
                     break;
                  }

                  var1.setlocal(2, var4);

                  PyException var5;
                  try {
                     var1.setline(279);
                     var10 = var1.getlocal(2).__getattr__("output_codec");
                     if (!((PyObject)var10).__nonzero__()) {
                        var10 = PyString.fromInterned("us-ascii");
                     }

                     Object var7 = var10;
                     var1.setlocal(6, (PyObject)var7);
                     var5 = null;
                     var1.setline(280);
                     PyObject var9 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getlocal(6), var1.getlocal(3));
                     var1.setlocal(1, var9);
                     var5 = null;
                     break;
                  } catch (Throwable var6) {
                     var5 = Py.setException(var6, var1);
                     if (!var5.match(var1.getglobal("UnicodeError"))) {
                        throw var5;
                     }

                     var1.setline(283);
                  }
               }
            }
         }
      }

      var1.setline(286);
      var1.getlocal(0).__getattr__("_chunks").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _split$10(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyObject var3 = var1.getlocal(2).__getattr__("to_splittable").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(291);
      var3 = var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(5), var1.getglobal("True"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(292);
      var3 = var1.getlocal(2).__getattr__("encoded_header_len").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(294);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._le(var1.getlocal(3));
      var3 = null;
      PyList var7;
      if (var10000.__nonzero__()) {
         var1.setline(295);
         var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(2)})});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(302);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("8bit"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(303);
            var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})});
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(315);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("us-ascii"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(316);
               var3 = var1.getlocal(0).__getattr__("_split_ascii").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(318);
               var4 = var1.getlocal(7);
               var10000 = var4._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(321);
                  var4 = var1.getlocal(3);
                  var1.setlocal(8, var4);
                  var4 = null;
                  var1.setline(322);
                  var4 = var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null), var1.getglobal("False"));
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(323);
                  var4 = var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(5).__getslice__(var1.getlocal(8), (PyObject)null, (PyObject)null), var1.getglobal("False"));
                  var1.setlocal(10, var4);
                  var4 = null;
               } else {
                  var1.setline(326);
                  var4 = var1.getglobal("_binsplit").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(3));
                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var4 = null;
               }

               var1.setline(329);
               var4 = var1.getlocal(2).__getattr__("to_splittable").__call__(var2, var1.getlocal(9));
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(330);
               var4 = var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(11), var1.getglobal("True"));
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(331);
               PyList var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(2)})});
               var1.setlocal(13, var8);
               var4 = null;
               var1.setline(332);
               var3 = var1.getlocal(13)._add(var1.getlocal(0).__getattr__("_split").__call__(var2, var1.getlocal(10), var1.getlocal(2), var1.getlocal(0).__getattr__("_maxlinelen"), var1.getlocal(4)));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _split_ascii$11(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      PyObject var10000 = var1.getglobal("_split_ascii");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(0).__getattr__("_maxlinelen"), var1.getlocal(0).__getattr__("_continuation_ws"), var1.getlocal(4)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(337);
      var4 = var1.getglobal("zip").__call__(var2, var1.getlocal(5), (new PyList(new PyObject[]{var1.getlocal(2)}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(5))));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _encode_chunks$12(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(358);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(358);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(371);
            var7 = var1.getglobal("NL")._add(var1.getlocal(0).__getattr__("_continuation_ws"));
            var1.setlocal(8, var7);
            var3 = null;
            var1.setline(372);
            var7 = var1.getlocal(8).__getattr__("join").__call__(var2, var1.getlocal(3));
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
         if (!var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(361);
            PyObject var8 = var1.getlocal(5);
            PyObject var10000 = var8._is(var1.getglobal("None"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var8 = var1.getlocal(5).__getattr__("header_encoding");
               var10000 = var8._is(var1.getglobal("None"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(362);
               var8 = var1.getlocal(4);
               var1.setlocal(6, var8);
               var5 = null;
            } else {
               var1.setline(364);
               var8 = var1.getlocal(5).__getattr__("header_encode").__call__(var2, var1.getlocal(4));
               var1.setlocal(6, var8);
               var5 = null;
            }

            var1.setline(366);
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(3).__getitem__(Py.newInteger(-1)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
            }

            PyString var9;
            if (var10000.__nonzero__()) {
               var1.setline(367);
               var9 = PyString.fromInterned("");
               var1.setlocal(7, var9);
               var5 = null;
            } else {
               var1.setline(369);
               var9 = PyString.fromInterned(" ");
               var1.setlocal(7, var9);
               var5 = null;
            }

            var1.setline(370);
            var1.getglobal("_max_append").__call__(var2, var1.getlocal(3), var1.getlocal(6), var1.getlocal(2), var1.getlocal(7));
         }
      }
   }

   public PyObject encode$13(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyString.fromInterned("Encode a message header into an RFC-compliant format.\n\n        There are many issues involved in converting a given string for use in\n        an email header.  Only certain character sets are readable in most\n        email clients, and as header strings can only contain a subset of\n        7-bit ASCII, care must be taken to properly convert and encode (with\n        Base64 or quoted-printable) header strings.  In addition, there is a\n        75-character length limit on any given encoded header field, so\n        line-wrapping must be performed, even with double-byte character sets.\n\n        This method will do its best to convert the string to the correct\n        character set used in email, and encode and line wrap it safely with\n        the appropriate scheme for that character set.\n\n        If the given charset is not known or an error occurs during\n        conversion, this function will return the header untouched.\n\n        Optional splitchars is a string containing characters to split long\n        ASCII lines on, in rough support of RFC 2822's `highest level\n        syntactic breaks'.  This doesn't affect RFC 2047 encoded lines.\n        ");
      var1.setline(396);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(397);
      PyObject var8 = var1.getlocal(0).__getattr__("_firstlinelen");
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(398);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(399);
      var8 = var1.getlocal(0).__getattr__("_chunks").__iter__();

      while(true) {
         var1.setline(399);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(410);
            var8 = var1.getlocal(0).__getattr__("_encode_chunks").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(10, var8);
            var3 = null;
            var1.setline(411);
            if (var1.getglobal("_embeded_header").__getattr__("search").__call__(var2, var1.getlocal(10)).__nonzero__()) {
               var1.setline(412);
               throw Py.makeException(var1.getglobal("HeaderParseError").__call__(var2, PyString.fromInterned("header value appears to contain an embedded header: {!r}").__getattr__("format").__call__(var2, var1.getlocal(10))));
            }

            var1.setline(414);
            var8 = var1.getlocal(10);
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(403);
         PyObject var10 = var1.getlocal(3)._sub(var1.getlocal(4))._sub(Py.newInteger(1));
         var1.setlocal(7, var10);
         var5 = null;
         var1.setline(404);
         var10 = var1.getlocal(7);
         PyObject var10000 = var10._lt(var1.getlocal(6).__getattr__("encoded_header_len").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(406);
            var10 = var1.getlocal(3);
            var1.setlocal(7, var10);
            var5 = null;
         }

         var1.setline(407);
         var10 = var1.getlocal(2);
         var10 = var10._iadd(var1.getlocal(0).__getattr__("_split").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(1)));
         var1.setlocal(2, var10);
         var1.setline(408);
         var10 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         PyObject[] var11 = Py.unpackSequence(var10, 2);
         PyObject var7 = var11[0];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var11[1];
         var1.setlocal(9, var7);
         var7 = null;
         var5 = null;
         var1.setline(409);
         var10 = var1.getlocal(9).__getattr__("encoded_header_len").__call__(var2, var1.getlocal(8));
         var1.setlocal(4, var10);
         var5 = null;
      }
   }

   public PyObject _split_ascii$14(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(420);
      PyObject var8 = var1.getlocal(1);
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(421);
      var8 = var1.getlocal(0).__getattr__("splitlines").__call__(var2).__iter__();

      while(true) {
         label69:
         while(true) {
            var1.setline(421);
            PyObject var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(484);
               var8 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setlocal(7, var4);
            var1.setline(424);
            PyObject var5 = var1.getlocal(7).__getattr__("lstrip").__call__(var2);
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(425);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            PyObject var10000 = var5._lt(var1.getlocal(6));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(426);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(7));
               var1.setline(427);
               var5 = var1.getlocal(2);
               var1.setlocal(6, var5);
               var5 = null;
            } else {
               var1.setline(433);
               var5 = var1.getlocal(4).__iter__();

               PyObject var6;
               PyObject var7;
               do {
                  var1.setline(433);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(439);
                     var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(7));
                     var1.setline(440);
                     var7 = var1.getlocal(2);
                     var1.setlocal(6, var7);
                     var7 = null;
                     continue label69;
                  }

                  var1.setlocal(8, var6);
                  var1.setline(434);
                  var7 = var1.getlocal(8);
                  var10000 = var7._in(var1.getlocal(7));
                  var7 = null;
               } while(!var10000.__nonzero__());

               var1.setline(443);
               var5 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("%s\\s*")._mod(var1.getlocal(8)));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(444);
               var5 = var1.getlocal(8);
               var10000 = var5._in(PyString.fromInterned(";,"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(445);
                  var5 = var1.getlocal(8);
                  var1.setlocal(10, var5);
                  var5 = null;
               } else {
                  var1.setline(447);
                  PyString var9 = PyString.fromInterned("");
                  var1.setlocal(10, var9);
                  var5 = null;
               }

               var1.setline(448);
               var5 = var1.getlocal(10)._add(PyString.fromInterned(" "));
               var1.setlocal(11, var5);
               var5 = null;
               var1.setline(449);
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(11));
               var1.setlocal(12, var5);
               var5 = null;
               var1.setline(450);
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"), (PyObject)var1.getglobal("SPACE8")));
               var1.setlocal(13, var5);
               var5 = null;
               var1.setline(451);
               PyList var10 = new PyList(Py.EmptyObjects);
               var1.setlocal(14, var10);
               var5 = null;
               var1.setline(452);
               PyInteger var11 = Py.newInteger(0);
               var1.setlocal(15, var11);
               var5 = null;
               var1.setline(453);
               var5 = var1.getlocal(9).__getattr__("split").__call__(var2, var1.getlocal(7)).__iter__();

               while(true) {
                  var1.setline(453);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(482);
                     if (var1.getlocal(14).__nonzero__()) {
                        var1.setline(483);
                        var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(11).__getattr__("join").__call__(var2, var1.getlocal(14)));
                     }
                     break;
                  }

                  var1.setlocal(16, var6);
                  var1.setline(454);
                  var7 = var1.getlocal(15)._add(var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(14))._sub(Py.newInteger(1)))._mul(var1.getlocal(12)));
                  var1.setlocal(17, var7);
                  var7 = null;
                  var1.setline(455);
                  var7 = var1.getglobal("len").__call__(var2, var1.getlocal(16));
                  var1.setlocal(18, var7);
                  var7 = null;
                  var1.setline(456);
                  var7 = var1.getlocal(5).__not__();
                  var1.setlocal(19, var7);
                  var7 = null;
                  var1.setline(459);
                  var7 = var1.getlocal(8);
                  var10000 = var7._eq(PyString.fromInterned(" "));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(19);
                     if (var10000.__nonzero__()) {
                        var7 = var1.getglobal("len").__call__(var2, var1.getlocal(14));
                        var10000 = var7._eq(Py.newInteger(1));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getglobal("fcre").__getattr__("match").__call__(var2, var1.getlocal(14).__getitem__(Py.newInteger(0)));
                        }
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(461);
                     var1.getlocal(14).__getattr__("append").__call__(var2, var1.getlocal(16));
                     var1.setline(462);
                     var7 = var1.getlocal(15);
                     var7 = var7._iadd(var1.getlocal(18));
                     var1.setlocal(15, var7);
                  } else {
                     var1.setline(463);
                     var7 = var1.getlocal(17)._add(var1.getlocal(18));
                     var10000 = var7._gt(var1.getlocal(6));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(464);
                        if (var1.getlocal(14).__nonzero__()) {
                           var1.setline(465);
                           var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(11).__getattr__("join").__call__(var2, var1.getlocal(14))._add(var1.getlocal(10)));
                        }

                        var1.setline(469);
                        var7 = var1.getlocal(18);
                        var10000 = var7._gt(var1.getlocal(6));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var7 = var1.getlocal(8);
                           var10000 = var7._ne(PyString.fromInterned(" "));
                           var7 = null;
                        }

                        PyList var13;
                        if (var10000.__nonzero__()) {
                           var1.setline(470);
                           var10000 = var1.getglobal("_split_ascii");
                           PyObject[] var12 = new PyObject[]{var1.getlocal(16), var1.getlocal(6), var1.getlocal(2), var1.getlocal(3), PyString.fromInterned(" ")};
                           var7 = var10000.__call__(var2, var12);
                           var1.setlocal(20, var7);
                           var7 = null;
                           var1.setline(472);
                           var1.getlocal(5).__getattr__("extend").__call__(var2, var1.getlocal(20).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
                           var1.setline(473);
                           var13 = new PyList(new PyObject[]{var1.getlocal(20).__getitem__(Py.newInteger(-1))});
                           var1.setlocal(14, var13);
                           var7 = null;
                        } else {
                           var1.setline(475);
                           var13 = new PyList(new PyObject[]{var1.getlocal(16)});
                           var1.setlocal(14, var13);
                           var7 = null;
                        }

                        var1.setline(476);
                        var7 = var1.getlocal(13)._add(var1.getglobal("len").__call__(var2, var1.getlocal(14).__getitem__(Py.newInteger(-1))));
                        var1.setlocal(15, var7);
                        var7 = null;
                        var1.setline(477);
                        var7 = var1.getlocal(2);
                        var1.setlocal(6, var7);
                        var7 = null;
                     } else {
                        var1.setline(479);
                        var1.getlocal(14).__getattr__("append").__call__(var2, var1.getlocal(16));
                        var1.setline(480);
                        var7 = var1.getlocal(15);
                        var7 = var7._iadd(var1.getlocal(18));
                        var1.setlocal(15, var7);
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _binsplit$15(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(490);
      PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var4);
      var3 = null;

      while(true) {
         var1.setline(491);
         var4 = var1.getlocal(3);
         PyObject var10000 = var4._lt(var1.getlocal(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(512);
            var4 = var1.getlocal(1).__getattr__("from_splittable").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), var1.getglobal("False"));
            var1.setlocal(8, var4);
            var3 = null;
            var1.setline(513);
            var4 = var1.getlocal(1).__getattr__("from_splittable").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null), var1.getglobal("False"));
            var1.setlocal(9, var4);
            var3 = null;
            var1.setline(514);
            PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)});
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(500);
         var4 = var1.getlocal(3)._add(var1.getlocal(4))._add(Py.newInteger(1))._rshift(Py.newInteger(1));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(501);
         var4 = var1.getlocal(1).__getattr__("from_splittable").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null), var1.getglobal("True"));
         var1.setlocal(6, var4);
         var3 = null;
         var1.setline(502);
         var4 = var1.getlocal(1).__getattr__("encoded_header_len").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var4);
         var3 = null;
         var1.setline(503);
         var4 = var1.getlocal(7);
         var10000 = var4._le(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(505);
            var4 = var1.getlocal(5);
            var1.setlocal(3, var4);
            var3 = null;
         } else {
            var1.setline(508);
            var4 = var1.getlocal(5)._sub(Py.newInteger(1));
            var1.setlocal(4, var4);
            var3 = null;
         }
      }
   }

   public header$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"header", "decoded", "dec", "line", "parts", "unenc", "charset", "encoding", "_[93_37]", "s", "encoded", "paderr"};
      decode_header$1 = Py.newCode(1, var2, var1, "decode_header", 61, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"decoded_seq", "maxlinelen", "header_name", "continuation_ws", "h", "s", "charset"};
      make_header$2 = Py.newCode(4, var2, var1, "make_header", 121, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Header$3 = Py.newCode(0, var2, var1, "Header", 144, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "s", "charset", "maxlinelen", "header_name", "continuation_ws", "errors", "cws_expanded_len"};
      __init__$4 = Py.newCode(7, var2, var1, "__init__", 145, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$5 = Py.newCode(1, var2, var1, "__str__", 198, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uchunks", "lastcs", "s", "charset", "nextcs"};
      __unicode__$6 = Py.newCode(1, var2, var1, "__unicode__", 202, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$7 = Py.newCode(2, var2, var1, "__eq__", 225, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$8 = Py.newCode(2, var2, var1, "__ne__", 230, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "charset", "errors", "incodec", "ustr", "outcodec"};
      append$9 = Py.newCode(4, var2, var1, "append", 233, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "charset", "maxlinelen", "splitchars", "splittable", "encoded", "elen", "splitpnt", "first", "last", "fsplittable", "fencoded", "chunk"};
      _split$10 = Py.newCode(5, var2, var1, "_split", 288, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "charset", "firstlen", "splitchars", "chunks"};
      _split_ascii$11 = Py.newCode(5, var2, var1, "_split_ascii", 334, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newchunks", "maxlinelen", "chunks", "header", "charset", "s", "extra", "joiner"};
      _encode_chunks$12 = Py.newCode(3, var2, var1, "_encode_chunks", 339, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "splitchars", "newchunks", "maxlinelen", "lastlen", "s", "charset", "targetlen", "lastchunk", "lastcharset", "value"};
      encode$13 = Py.newCode(2, var2, var1, "encode", 374, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "firstlen", "restlen", "continuation_ws", "splitchars", "lines", "maxlen", "line", "ch", "cre", "eol", "joiner", "joinlen", "wslen", "this", "linelen", "part", "curlen", "partlen", "onfirstline", "subl"};
      _split_ascii$14 = Py.newCode(5, var2, var1, "_split_ascii", 418, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"splittable", "charset", "maxlinelen", "i", "j", "m", "chunk", "chunklen", "first", "last"};
      _binsplit$15 = Py.newCode(3, var2, var1, "_binsplit", 488, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new header$py("email/header$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(header$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.decode_header$1(var2, var3);
         case 2:
            return this.make_header$2(var2, var3);
         case 3:
            return this.Header$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.__str__$5(var2, var3);
         case 6:
            return this.__unicode__$6(var2, var3);
         case 7:
            return this.__eq__$7(var2, var3);
         case 8:
            return this.__ne__$8(var2, var3);
         case 9:
            return this.append$9(var2, var3);
         case 10:
            return this._split$10(var2, var3);
         case 11:
            return this._split_ascii$11(var2, var3);
         case 12:
            return this._encode_chunks$12(var2, var3);
         case 13:
            return this.encode$13(var2, var3);
         case 14:
            return this._split_ascii$14(var2, var3);
         case 15:
            return this._binsplit$15(var2, var3);
         default:
            return null;
      }
   }
}
