package email;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("email/quoprimime.py")
public class quoprimime$py extends PyFunctionTable implements PyRunnable {
   static quoprimime$py self;
   static final PyCode f$0;
   static final PyCode header_quopri_check$1;
   static final PyCode body_quopri_check$2;
   static final PyCode header_quopri_len$3;
   static final PyCode body_quopri_len$4;
   static final PyCode _max_append$5;
   static final PyCode unquote$6;
   static final PyCode quote$7;
   static final PyCode header_encode$8;
   static final PyCode encode$9;
   static final PyCode decode$10;
   static final PyCode _unquote_match$11;
   static final PyCode header_decode$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Quoted-printable content transfer encoding per RFCs 2045-2047.\n\nThis module handles the content transfer encoding method defined in RFC 2045\nto encode US ASCII-like 8-bit data called `quoted-printable'.  It is used to\nsafely encode text that is in a character set similar to the 7-bit US ASCII\ncharacter set, but that includes some 8-bit characters that are normally not\nallowed in email bodies or headers.\n\nQuoted-printable is very space-inefficient for encoding binary files; use the\nemail.base64mime module for that instead.\n\nThis module provides an interface to encode and decode both headers and bodies\nwith quoted-printable encoding.\n\nRFC 2045 defines a method for including character set information in an\n`encoded-word' in a header.  This method is commonly used for 8-bit real names\nin To:/From:/Cc: etc. fields, as well as Subject: lines.\n\nThis module does not do the line wrapping or end-of-line character\nconversion necessary for proper internationalized headers; it only\ndoes dumb encoding and decoding.  To deal with the various line\nwrapping issues, use the email.header module.\n"));
      var1.setline(27);
      PyString.fromInterned("Quoted-printable content transfer encoding per RFCs 2045-2047.\n\nThis module handles the content transfer encoding method defined in RFC 2045\nto encode US ASCII-like 8-bit data called `quoted-printable'.  It is used to\nsafely encode text that is in a character set similar to the 7-bit US ASCII\ncharacter set, but that includes some 8-bit characters that are normally not\nallowed in email bodies or headers.\n\nQuoted-printable is very space-inefficient for encoding binary files; use the\nemail.base64mime module for that instead.\n\nThis module provides an interface to encode and decode both headers and bodies\nwith quoted-printable encoding.\n\nRFC 2045 defines a method for including character set information in an\n`encoded-word' in a header.  This method is commonly used for 8-bit real names\nin To:/From:/Cc: etc. fields, as well as Subject: lines.\n\nThis module does not do the line wrapping or end-of-line character\nconversion necessary for proper internationalized headers; it only\ndoes dumb encoding and decoding.  To deal with the various line\nwrapping issues, use the email.header module.\n");
      var1.setline(29);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("body_decode"), PyString.fromInterned("body_encode"), PyString.fromInterned("body_quopri_check"), PyString.fromInterned("body_quopri_len"), PyString.fromInterned("decode"), PyString.fromInterned("decodestring"), PyString.fromInterned("encode"), PyString.fromInterned("encodestring"), PyString.fromInterned("header_decode"), PyString.fromInterned("header_encode"), PyString.fromInterned("header_quopri_check"), PyString.fromInterned("header_quopri_len"), PyString.fromInterned("quote"), PyString.fromInterned("unquote")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(46);
      PyObject var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(48);
      String[] var6 = new String[]{"hexdigits"};
      PyObject[] var7 = imp.importFrom("string", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("hexdigits", var4);
      var4 = null;
      var1.setline(49);
      var6 = new String[]{"fix_eols"};
      var7 = imp.importFrom("email.utils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("fix_eols", var4);
      var4 = null;
      var1.setline(51);
      PyString var8 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var8);
      var3 = null;
      var1.setline(52);
      var8 = PyString.fromInterned("\n");
      var1.setlocal("NL", var8);
      var3 = null;
      var1.setline(55);
      PyInteger var9 = Py.newInteger(7);
      var1.setlocal("MISC_LEN", var9);
      var3 = null;
      var1.setline(57);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^-a-zA-Z0-9!*+/ ]"));
      var1.setlocal("hqre", var5);
      var3 = null;
      var1.setline(58);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^ !-<>-~\\t]"));
      var1.setlocal("bqre", var5);
      var3 = null;
      var1.setline(63);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, header_quopri_check$1, PyString.fromInterned("Return True if the character should be escaped with header quopri."));
      var1.setlocal("header_quopri_check", var10);
      var3 = null;
      var1.setline(68);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, body_quopri_check$2, PyString.fromInterned("Return True if the character should be escaped with body quopri."));
      var1.setlocal("body_quopri_check", var10);
      var3 = null;
      var1.setline(73);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, header_quopri_len$3, PyString.fromInterned("Return the length of str when it is encoded with header quopri."));
      var1.setlocal("header_quopri_len", var10);
      var3 = null;
      var1.setline(84);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, body_quopri_len$4, PyString.fromInterned("Return the length of str when it is encoded with body quopri."));
      var1.setlocal("body_quopri_len", var10);
      var3 = null;
      var1.setline(95);
      var7 = new PyObject[]{PyString.fromInterned("")};
      var10 = new PyFunction(var1.f_globals, var7, _max_append$5, (PyObject)null);
      var1.setlocal("_max_append", var10);
      var3 = null;
      var1.setline(104);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, unquote$6, PyString.fromInterned("Turn a string in the form =AB to the ASCII character with value 0xab"));
      var1.setlocal("unquote", var10);
      var3 = null;
      var1.setline(109);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, quote$7, (PyObject)null);
      var1.setlocal("quote", var10);
      var3 = null;
      var1.setline(114);
      var7 = new PyObject[]{PyString.fromInterned("iso-8859-1"), var1.getname("False"), Py.newInteger(76), var1.getname("NL")};
      var10 = new PyFunction(var1.f_globals, var7, header_encode$8, PyString.fromInterned("Encode a single header line with quoted-printable (like) encoding.\n\n    Defined in RFC 2045, this `Q' encoding is similar to quoted-printable, but\n    used specifically for email header fields to allow charsets with mostly 7\n    bit characters (and some 8 bit) to remain more or less readable in non-RFC\n    2045 aware mail clients.\n\n    charset names the character set to use to encode the header.  It defaults\n    to iso-8859-1.\n\n    The resulting string will be in the form:\n\n    \"=?charset?q?I_f=E2rt_in_your_g=E8n=E8ral_dire=E7tion?\\n\n      =?charset?q?Silly_=C8nglish_Kn=EEghts?=\"\n\n    with each line wrapped safely at, at most, maxlinelen characters (defaults\n    to 76 characters).  If maxlinelen is None, the entire string is encoded in\n    one chunk with no splitting.\n\n    End-of-line characters (\\r, \\n, \\r\\n) will be automatically converted\n    to the canonical email line separator \\r\\n unless the keep_eols\n    parameter is True (the default is False).\n\n    Each line of the header will be terminated in the value of eol, which\n    defaults to \"\\n\".  Set this to \"\\r\\n\" if you are using the result of\n    this function directly in email.\n    "));
      var1.setlocal("header_encode", var10);
      var3 = null;
      var1.setline(178);
      var7 = new PyObject[]{var1.getname("False"), Py.newInteger(76), var1.getname("NL")};
      var10 = new PyFunction(var1.f_globals, var7, encode$9, PyString.fromInterned("Encode with quoted-printable, wrapping at maxlinelen characters.\n\n    If binary is False (the default), end-of-line characters will be converted\n    to the canonical email end-of-line sequence \\r\\n.  Otherwise they will\n    be left verbatim.\n\n    Each line of encoded text will end with eol, which defaults to \"\\n\".  Set\n    this to \"\\r\\n\" if you will be using the result of this function directly\n    in an email.\n\n    Each line will be wrapped at, at most, maxlinelen characters (defaults to\n    76 characters).  Long lines will have the `soft linefeed' quoted-printable\n    character \"=\" appended to them, so the decoded text will be identical to\n    the original text.\n    "));
      var1.setlocal("encode", var10);
      var3 = null;
      var1.setline(261);
      var5 = var1.getname("encode");
      var1.setlocal("body_encode", var5);
      var3 = null;
      var1.setline(262);
      var5 = var1.getname("encode");
      var1.setlocal("encodestring", var5);
      var3 = null;
      var1.setline(268);
      var7 = new PyObject[]{var1.getname("NL")};
      var10 = new PyFunction(var1.f_globals, var7, decode$10, PyString.fromInterned("Decode a quoted-printable string.\n\n    Lines are separated with eol, which defaults to \\n.\n    "));
      var1.setlocal("decode", var10);
      var3 = null;
      var1.setline(316);
      var5 = var1.getname("decode");
      var1.setlocal("body_decode", var5);
      var3 = null;
      var1.setline(317);
      var5 = var1.getname("decode");
      var1.setlocal("decodestring", var5);
      var3 = null;
      var1.setline(321);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _unquote_match$11, PyString.fromInterned("Turn a match in the form =AB to the ASCII character with value 0xab"));
      var1.setlocal("_unquote_match", var10);
      var3 = null;
      var1.setline(328);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, header_decode$12, PyString.fromInterned("Decode a string encoded with RFC 2045 MIME header `Q' encoding.\n\n    This function does not parse a full MIME header value encoded with\n    quoted-printable (like =?iso-8895-1?q?Hello_World?=) -- please use\n    the high level email.header class for that functionality.\n    "));
      var1.setlocal("header_decode", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject header_quopri_check$1(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString.fromInterned("Return True if the character should be escaped with header quopri.");
      var1.setline(65);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getglobal("hqre").__getattr__("match").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject body_quopri_check$2(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Return True if the character should be escaped with body quopri.");
      var1.setline(70);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getglobal("bqre").__getattr__("match").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject header_quopri_len$3(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("Return the length of str when it is encoded with header quopri.");
      var1.setline(75);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(76);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(76);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(81);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(77);
         PyObject var5;
         if (var1.getglobal("hqre").__getattr__("match").__call__(var2, var1.getlocal(2)).__nonzero__()) {
            var1.setline(78);
            var5 = var1.getlocal(1);
            var5 = var5._iadd(Py.newInteger(3));
            var1.setlocal(1, var5);
         } else {
            var1.setline(80);
            var5 = var1.getlocal(1);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(1, var5);
         }
      }
   }

   public PyObject body_quopri_len$4(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Return the length of str when it is encoded with body quopri.");
      var1.setline(86);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(87);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(87);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(92);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(88);
         PyObject var5;
         if (var1.getglobal("bqre").__getattr__("match").__call__(var2, var1.getlocal(2)).__nonzero__()) {
            var1.setline(89);
            var5 = var1.getlocal(1);
            var5 = var5._iadd(Py.newInteger(3));
            var1.setlocal(1, var5);
         } else {
            var1.setline(91);
            var5 = var1.getlocal(1);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(1, var5);
         }
      }
   }

   public PyObject _max_append$5(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(97);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("lstrip").__call__(var2));
      } else {
         var1.setline(98);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(-1)))._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         PyObject var10000 = var3._le(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(99);
            var10000 = var1.getlocal(0);
            PyInteger var6 = Py.newInteger(-1);
            PyObject var4 = var10000;
            PyObject var5 = var4.__getitem__(var6);
            var5 = var5._iadd(var1.getlocal(3)._add(var1.getlocal(1)));
            var4.__setitem__((PyObject)var6, var5);
         } else {
            var1.setline(101);
            var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("lstrip").__call__(var2));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unquote$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Turn a string in the form =AB to the ASCII character with value 0xab");
      var1.setline(106);
      PyObject var3 = var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(3), (PyObject)null), (PyObject)Py.newInteger(16)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quote$7(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = PyString.fromInterned("=%02X")._mod(var1.getglobal("ord").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject header_encode$8(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyString.fromInterned("Encode a single header line with quoted-printable (like) encoding.\n\n    Defined in RFC 2045, this `Q' encoding is similar to quoted-printable, but\n    used specifically for email header fields to allow charsets with mostly 7\n    bit characters (and some 8 bit) to remain more or less readable in non-RFC\n    2045 aware mail clients.\n\n    charset names the character set to use to encode the header.  It defaults\n    to iso-8859-1.\n\n    The resulting string will be in the form:\n\n    \"=?charset?q?I_f=E2rt_in_your_g=E8n=E8ral_dire=E7tion?\\n\n      =?charset?q?Silly_=C8nglish_Kn=EEghts?=\"\n\n    with each line wrapped safely at, at most, maxlinelen characters (defaults\n    to 76 characters).  If maxlinelen is None, the entire string is encoded in\n    one chunk with no splitting.\n\n    End-of-line characters (\\r, \\n, \\r\\n) will be automatically converted\n    to the canonical email line separator \\r\\n unless the keep_eols\n    parameter is True (the default is False).\n\n    Each line of the header will be terminated in the value of eol, which\n    defaults to \"\\n\".  Set this to \"\\r\\n\" if you are using the result of\n    this function directly in email.\n    ");
      var1.setline(144);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(145);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(147);
         PyObject var4;
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(148);
            var4 = var1.getglobal("fix_eols").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(152);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var7);
         var4 = null;
         var1.setline(153);
         var4 = var1.getlocal(3);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(155);
            PyInteger var8 = Py.newInteger(100000);
            var1.setlocal(6, var8);
            var4 = null;
         } else {
            var1.setline(157);
            var4 = var1.getlocal(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1)))._sub(var1.getglobal("MISC_LEN"))._sub(Py.newInteger(1));
            var1.setlocal(6, var4);
            var4 = null;
         }

         var1.setline(159);
         var4 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(159);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(173);
               var4 = var1.getlocal(4)._add(PyString.fromInterned(" "));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(174);
               var10000 = var1.getlocal(8).__getattr__("join");
               PyList var10002 = new PyList();
               var4 = var10002.__getattr__("append");
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(174);
               var4 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(174);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(174);
                     var1.dellocal(9);
                     var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(10, var5);
                  var1.setline(174);
                  var1.getlocal(9).__call__(var2, PyString.fromInterned("=?%s?q?%s?=")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(10)})));
               }
            }

            var1.setlocal(7, var5);
            var1.setline(161);
            PyObject var6 = var1.getlocal(7);
            var10000 = var6._eq(PyString.fromInterned(" "));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(162);
               var1.getglobal("_max_append").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("_"), (PyObject)var1.getlocal(6));
            } else {
               var1.setline(164);
               if (var1.getglobal("hqre").__getattr__("match").__call__(var2, var1.getlocal(7)).__not__().__nonzero__()) {
                  var1.setline(165);
                  var1.getglobal("_max_append").__call__(var2, var1.getlocal(5), var1.getlocal(7), var1.getlocal(6));
               } else {
                  var1.setline(168);
                  var1.getglobal("_max_append").__call__(var2, var1.getlocal(5), PyString.fromInterned("=%02X")._mod(var1.getglobal("ord").__call__(var2, var1.getlocal(7))), var1.getlocal(6));
               }
            }
         }
      }
   }

   public PyObject encode$9(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyString.fromInterned("Encode with quoted-printable, wrapping at maxlinelen characters.\n\n    If binary is False (the default), end-of-line characters will be converted\n    to the canonical email end-of-line sequence \\r\\n.  Otherwise they will\n    be left verbatim.\n\n    Each line of encoded text will end with eol, which defaults to \"\\n\".  Set\n    this to \"\\r\\n\" if you will be using the result of this function directly\n    in an email.\n\n    Each line will be wrapped at, at most, maxlinelen characters (defaults to\n    76 characters).  Long lines will have the `soft linefeed' quoted-printable\n    character \"=\" appended to them, so the decoded text will be identical to\n    the original text.\n    ");
      var1.setline(194);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(195);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(197);
         PyObject var4;
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(198);
            var4 = var1.getglobal("fix_eols").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(203);
         PyString var9 = PyString.fromInterned("");
         var1.setlocal(4, var9);
         var4 = null;
         var1.setline(204);
         PyInteger var10 = Py.newInteger(-1);
         var1.setlocal(5, var10);
         var4 = null;
         var1.setline(207);
         var4 = var1.getlocal(0).__getattr__("splitlines").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(208);
         var4 = var1.getlocal(6).__iter__();

         label78:
         while(true) {
            var1.setline(208);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(257);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(7, var5);
            var1.setline(210);
            PyObject var6;
            PyObject var10000;
            if (var1.getlocal(7).__getattr__("endswith").__call__(var2, var1.getglobal("CRLF")).__nonzero__()) {
               var1.setline(211);
               var6 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
               var1.setlocal(7, var6);
               var6 = null;
            } else {
               var1.setline(212);
               var6 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
               var10000 = var6._in(var1.getglobal("CRLF"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(213);
                  var6 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(7, var6);
                  var6 = null;
               }
            }

            var1.setline(215);
            var6 = var1.getlocal(5);
            var6 = var6._iadd(Py.newInteger(1));
            var1.setlocal(5, var6);
            var1.setline(216);
            PyString var11 = PyString.fromInterned("");
            var1.setlocal(8, var11);
            var6 = null;
            var1.setline(217);
            var6 = var1.getglobal("None");
            var1.setlocal(9, var6);
            var6 = null;
            var1.setline(218);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(221);
            var6 = var1.getglobal("range").__call__(var2, var1.getlocal(10)).__iter__();

            while(true) {
               while(true) {
                  var1.setline(221);
                  PyObject var7 = var6.__iternext__();
                  if (var7 == null) {
                     var1.setline(238);
                     var10000 = var1.getlocal(9);
                     if (var10000.__nonzero__()) {
                        var6 = var1.getlocal(9);
                        var10000 = var6._in(PyString.fromInterned(" \t"));
                        var6 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(240);
                        var6 = var1.getlocal(5)._add(Py.newInteger(1));
                        var10000 = var6._eq(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(241);
                           var6 = var1.getglobal("quote").__call__(var2, var1.getlocal(9));
                           var1.setlocal(9, var6);
                           var6 = null;
                           var1.setline(242);
                           var6 = var1.getglobal("len").__call__(var2, var1.getlocal(8))._add(var1.getglobal("len").__call__(var2, var1.getlocal(9)));
                           var10000 = var6._gt(var1.getlocal(2));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(243);
                              var6 = var1.getlocal(4);
                              var6 = var6._iadd(var1.getlocal(8)._add(PyString.fromInterned("="))._add(var1.getlocal(3))._add(var1.getlocal(9)));
                              var1.setlocal(4, var6);
                           } else {
                              var1.setline(245);
                              var6 = var1.getlocal(4);
                              var6 = var6._iadd(var1.getlocal(8)._add(var1.getlocal(9)));
                              var1.setlocal(4, var6);
                           }
                        } else {
                           var1.setline(248);
                           var6 = var1.getlocal(4);
                           var6 = var6._iadd(var1.getlocal(8)._add(var1.getlocal(9))._add(PyString.fromInterned("="))._add(var1.getlocal(3)));
                           var1.setlocal(4, var6);
                        }

                        var1.setline(249);
                        var11 = PyString.fromInterned("");
                        var1.setlocal(8, var11);
                        var6 = null;
                     }

                     var1.setline(252);
                     var10000 = var1.getlocal(6).__getitem__(var1.getlocal(5)).__getattr__("endswith").__call__(var2, var1.getglobal("CRLF"));
                     if (!var10000.__nonzero__()) {
                        var6 = var1.getlocal(6).__getitem__(var1.getlocal(5)).__getitem__(Py.newInteger(-1));
                        var10000 = var6._in(var1.getglobal("CRLF"));
                        var6 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(253);
                        var6 = var1.getlocal(4);
                        var6 = var6._iadd(var1.getlocal(8)._add(var1.getlocal(3)));
                        var1.setlocal(4, var6);
                     } else {
                        var1.setline(255);
                        var6 = var1.getlocal(4);
                        var6 = var6._iadd(var1.getlocal(8));
                        var1.setlocal(4, var6);
                     }

                     var1.setline(256);
                     var11 = PyString.fromInterned("");
                     var1.setlocal(8, var11);
                     var6 = null;
                     continue label78;
                  }

                  var1.setlocal(11, var7);
                  var1.setline(222);
                  PyObject var8 = var1.getlocal(7).__getitem__(var1.getlocal(11));
                  var1.setlocal(12, var8);
                  var8 = null;
                  var1.setline(223);
                  var8 = var1.getlocal(12);
                  var1.setlocal(9, var8);
                  var8 = null;
                  var1.setline(224);
                  if (var1.getglobal("bqre").__getattr__("match").__call__(var2, var1.getlocal(12)).__nonzero__()) {
                     var1.setline(225);
                     var8 = var1.getglobal("quote").__call__(var2, var1.getlocal(12));
                     var1.setlocal(12, var8);
                     var8 = null;
                  } else {
                     var1.setline(226);
                     var8 = var1.getlocal(11)._add(Py.newInteger(1));
                     var10000 = var8._eq(var1.getlocal(10));
                     var8 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(228);
                        var8 = var1.getlocal(12);
                        var10000 = var8._notin(PyString.fromInterned(" \t"));
                        var8 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(229);
                           var8 = var1.getlocal(8);
                           var8 = var8._iadd(var1.getlocal(12));
                           var1.setlocal(8, var8);
                        }

                        var1.setline(230);
                        var8 = var1.getlocal(12);
                        var1.setlocal(9, var8);
                        var8 = null;
                        continue;
                     }
                  }

                  var1.setline(233);
                  var8 = var1.getglobal("len").__call__(var2, var1.getlocal(8))._add(var1.getglobal("len").__call__(var2, var1.getlocal(12)));
                  var10000 = var8._ge(var1.getlocal(2));
                  var8 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(234);
                     var8 = var1.getlocal(4);
                     var8 = var8._iadd(var1.getlocal(8)._add(PyString.fromInterned("="))._add(var1.getlocal(3)));
                     var1.setlocal(4, var8);
                     var1.setline(235);
                     PyString var12 = PyString.fromInterned("");
                     var1.setlocal(8, var12);
                     var8 = null;
                  }

                  var1.setline(236);
                  var8 = var1.getlocal(8);
                  var8 = var8._iadd(var1.getlocal(12));
                  var1.setlocal(8, var8);
               }
            }
         }
      }
   }

   public PyObject decode$10(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyString.fromInterned("Decode a quoted-printable string.\n\n    Lines are separated with eol, which defaults to \\n.\n    ");
      var1.setline(273);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(274);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(278);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(280);
         PyObject var7 = var1.getlocal(0).__getattr__("splitlines").__call__(var2).__iter__();

         while(true) {
            label56:
            while(true) {
               var1.setline(280);
               PyObject var5 = var7.__iternext__();
               PyObject var10000;
               if (var5 == null) {
                  var1.setline(310);
                  var10000 = var1.getlocal(0).__getattr__("endswith").__call__(var2, var1.getlocal(1)).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(2).__getattr__("endswith").__call__(var2, var1.getlocal(1));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(311);
                     var7 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(2, var7);
                     var4 = null;
                  }

                  var1.setline(312);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(281);
               PyObject var6 = var1.getlocal(3).__getattr__("rstrip").__call__(var2);
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(282);
               if (var1.getlocal(3).__not__().__nonzero__()) {
                  var1.setline(283);
                  var6 = var1.getlocal(2);
                  var6 = var6._iadd(var1.getlocal(1));
                  var1.setlocal(2, var6);
               } else {
                  var1.setline(286);
                  PyInteger var8 = Py.newInteger(0);
                  var1.setlocal(4, var8);
                  var6 = null;
                  var1.setline(287);
                  var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
                  var1.setlocal(5, var6);
                  var6 = null;

                  while(true) {
                     while(true) {
                        var1.setline(288);
                        var6 = var1.getlocal(4);
                        var10000 = var6._lt(var1.getlocal(5));
                        var6 = null;
                        if (!var10000.__nonzero__()) {
                           continue label56;
                        }

                        var1.setline(289);
                        var6 = var1.getlocal(3).__getitem__(var1.getlocal(4));
                        var1.setlocal(6, var6);
                        var6 = null;
                        var1.setline(290);
                        var6 = var1.getlocal(6);
                        var10000 = var6._ne(PyString.fromInterned("="));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(291);
                           var6 = var1.getlocal(2);
                           var6 = var6._iadd(var1.getlocal(6));
                           var1.setlocal(2, var6);
                           var1.setline(292);
                           var6 = var1.getlocal(4);
                           var6 = var6._iadd(Py.newInteger(1));
                           var1.setlocal(4, var6);
                        } else {
                           var1.setline(295);
                           var6 = var1.getlocal(4)._add(Py.newInteger(1));
                           var10000 = var6._eq(var1.getlocal(5));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(296);
                              var6 = var1.getlocal(4);
                              var6 = var6._iadd(Py.newInteger(1));
                              var1.setlocal(4, var6);
                              continue;
                           }

                           var1.setline(299);
                           var6 = var1.getlocal(4)._add(Py.newInteger(2));
                           var10000 = var6._lt(var1.getlocal(5));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var6 = var1.getlocal(3).__getitem__(var1.getlocal(4)._add(Py.newInteger(1)));
                              var10000 = var6._in(var1.getglobal("hexdigits"));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var6 = var1.getlocal(3).__getitem__(var1.getlocal(4)._add(Py.newInteger(2)));
                                 var10000 = var6._in(var1.getglobal("hexdigits"));
                                 var6 = null;
                              }
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(300);
                              var6 = var1.getlocal(2);
                              var6 = var6._iadd(var1.getglobal("unquote").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(3)), (PyObject)null)));
                              var1.setlocal(2, var6);
                              var1.setline(301);
                              var6 = var1.getlocal(4);
                              var6 = var6._iadd(Py.newInteger(3));
                              var1.setlocal(4, var6);
                           } else {
                              var1.setline(304);
                              var6 = var1.getlocal(2);
                              var6 = var6._iadd(var1.getlocal(6));
                              var1.setlocal(2, var6);
                              var1.setline(305);
                              var6 = var1.getlocal(4);
                              var6 = var6._iadd(Py.newInteger(1));
                              var1.setlocal(4, var6);
                           }
                        }

                        var1.setline(307);
                        var6 = var1.getlocal(4);
                        var10000 = var6._eq(var1.getlocal(5));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(308);
                           var6 = var1.getlocal(2);
                           var6 = var6._iadd(var1.getlocal(1));
                           var1.setlocal(2, var6);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _unquote_match$11(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("Turn a match in the form =AB to the ASCII character with value 0xab");
      var1.setline(323);
      PyObject var3 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(324);
      var3 = var1.getglobal("unquote").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject header_decode$12(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyString.fromInterned("Decode a string encoded with RFC 2045 MIME header `Q' encoding.\n\n    This function does not parse a full MIME header value encoded with\n    quoted-printable (like =?iso-8895-1?q?Hello_World?=) -- please use\n    the high level email.header class for that functionality.\n    ");
      var1.setline(335);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"), (PyObject)PyString.fromInterned(" "));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(336);
      var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("=[a-fA-F0-9]{2}"), (PyObject)var1.getglobal("_unquote_match"), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public quoprimime$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"c"};
      header_quopri_check$1 = Py.newCode(1, var2, var1, "header_quopri_check", 63, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c"};
      body_quopri_check$2 = Py.newCode(1, var2, var1, "body_quopri_check", 68, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "count", "c"};
      header_quopri_len$3 = Py.newCode(1, var2, var1, "header_quopri_len", 73, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "count", "c"};
      body_quopri_len$4 = Py.newCode(1, var2, var1, "body_quopri_len", 84, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"L", "s", "maxlen", "extra"};
      _max_append$5 = Py.newCode(4, var2, var1, "_max_append", 95, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      unquote$6 = Py.newCode(1, var2, var1, "unquote", 104, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c"};
      quote$7 = Py.newCode(1, var2, var1, "quote", 109, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"header", "charset", "keep_eols", "maxlinelen", "eol", "quoted", "max_encoded", "c", "joiner", "_[174_24]", "line"};
      header_encode$8 = Py.newCode(5, var2, var1, "header_encode", 114, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"body", "binary", "maxlinelen", "eol", "encoded_body", "lineno", "lines", "line", "encoded_line", "prev", "linelen", "j", "c"};
      encode$9 = Py.newCode(4, var2, var1, "encode", 178, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoded", "eol", "decoded", "line", "i", "n", "c"};
      decode$10 = Py.newCode(2, var2, var1, "decode", 268, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match", "s"};
      _unquote_match$11 = Py.newCode(1, var2, var1, "_unquote_match", 321, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      header_decode$12 = Py.newCode(1, var2, var1, "header_decode", 328, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new quoprimime$py("email/quoprimime$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(quoprimime$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.header_quopri_check$1(var2, var3);
         case 2:
            return this.body_quopri_check$2(var2, var3);
         case 3:
            return this.header_quopri_len$3(var2, var3);
         case 4:
            return this.body_quopri_len$4(var2, var3);
         case 5:
            return this._max_append$5(var2, var3);
         case 6:
            return this.unquote$6(var2, var3);
         case 7:
            return this.quote$7(var2, var3);
         case 8:
            return this.header_encode$8(var2, var3);
         case 9:
            return this.encode$9(var2, var3);
         case 10:
            return this.decode$10(var2, var3);
         case 11:
            return this._unquote_match$11(var2, var3);
         case 12:
            return this.header_decode$12(var2, var3);
         default:
            return null;
      }
   }
}
