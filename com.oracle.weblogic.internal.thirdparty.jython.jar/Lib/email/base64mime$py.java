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
@Filename("email/base64mime.py")
public class base64mime$py extends PyFunctionTable implements PyRunnable {
   static base64mime$py self;
   static final PyCode f$0;
   static final PyCode base64_len$1;
   static final PyCode header_encode$2;
   static final PyCode encode$3;
   static final PyCode decode$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Base64 content transfer encoding per RFCs 2045-2047.\n\nThis module handles the content transfer encoding method defined in RFC 2045\nto encode arbitrary 8-bit data using the three 8-bit bytes in four 7-bit\ncharacters encoding known as Base64.\n\nIt is used in the MIME standards for email to attach images, audio, and text\nusing some 8-bit character sets to messages.\n\nThis module provides an interface to encode and decode both headers and bodies\nwith Base64 encoding.\n\nRFC 2045 defines a method for including character set information in an\n`encoded-word' in a header.  This method is commonly used for 8-bit real names\nin To:, From:, Cc:, etc. fields, as well as Subject: lines.\n\nThis module does not do the line wrapping or end-of-line character conversion\nnecessary for proper internationalized headers; it only does dumb encoding and\ndecoding.  To deal with the various line wrapping issues, use the email.header\nmodule.\n"));
      var1.setline(25);
      PyString.fromInterned("Base64 content transfer encoding per RFCs 2045-2047.\n\nThis module handles the content transfer encoding method defined in RFC 2045\nto encode arbitrary 8-bit data using the three 8-bit bytes in four 7-bit\ncharacters encoding known as Base64.\n\nIt is used in the MIME standards for email to attach images, audio, and text\nusing some 8-bit character sets to messages.\n\nThis module provides an interface to encode and decode both headers and bodies\nwith Base64 encoding.\n\nRFC 2045 defines a method for including character set information in an\n`encoded-word' in a header.  This method is commonly used for 8-bit real names\nin To:, From:, Cc:, etc. fields, as well as Subject: lines.\n\nThis module does not do the line wrapping or end-of-line character conversion\nnecessary for proper internationalized headers; it only does dumb encoding and\ndecoding.  To deal with the various line wrapping issues, use the email.header\nmodule.\n");
      var1.setline(27);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("base64_len"), PyString.fromInterned("body_decode"), PyString.fromInterned("body_encode"), PyString.fromInterned("decode"), PyString.fromInterned("decodestring"), PyString.fromInterned("encode"), PyString.fromInterned("encodestring"), PyString.fromInterned("header_encode")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(39);
      String[] var5 = new String[]{"b2a_base64", "a2b_base64"};
      PyObject[] var6 = imp.importFrom("binascii", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("b2a_base64", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("a2b_base64", var4);
      var4 = null;
      var1.setline(40);
      var5 = new String[]{"fix_eols"};
      var6 = imp.importFrom("email.utils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("fix_eols", var4);
      var4 = null;
      var1.setline(42);
      PyString var7 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var7);
      var3 = null;
      var1.setline(43);
      var7 = PyString.fromInterned("\n");
      var1.setlocal("NL", var7);
      var3 = null;
      var1.setline(44);
      var7 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var7);
      var3 = null;
      var1.setline(47);
      PyInteger var8 = Py.newInteger(7);
      var1.setlocal("MISC_LEN", var8);
      var3 = null;
      var1.setline(52);
      var6 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var6, base64_len$1, PyString.fromInterned("Return the length of s when it is encoded with base64."));
      var1.setlocal("base64_len", var9);
      var3 = null;
      var1.setline(64);
      var6 = new PyObject[]{PyString.fromInterned("iso-8859-1"), var1.getname("False"), Py.newInteger(76), var1.getname("NL")};
      var9 = new PyFunction(var1.f_globals, var6, header_encode$2, PyString.fromInterned("Encode a single header line with Base64 encoding in a given charset.\n\n    Defined in RFC 2045, this Base64 encoding is identical to normal Base64\n    encoding, except that each line must be intelligently wrapped (respecting\n    the Base64 encoding), and subsequent lines must start with a space.\n\n    charset names the character set to use to encode the header.  It defaults\n    to iso-8859-1.\n\n    End-of-line characters (\\r, \\n, \\r\\n) will be automatically converted\n    to the canonical email line separator \\r\\n unless the keep_eols\n    parameter is True (the default is False).\n\n    Each line of the header will be terminated in the value of eol, which\n    defaults to \"\\n\".  Set this to \"\\r\\n\" if you are using the result of\n    this function directly in email.\n\n    The resulting string will be in the form:\n\n    \"=?charset?b?WW/5ciBtYXp66XLrIHf8eiBhIGhhbXBzdGHuciBBIFlv+XIgbWF6euly?=\\n\n      =?charset?b?6yB3/HogYSBoYW1wc3Rh7nIgQkMgWW/5ciBtYXp66XLrIHf8eiBhIGhh?=\"\n\n    with each line wrapped at, at most, maxlinelen characters (defaults to 76\n    characters).\n    "));
      var1.setlocal("header_encode", var9);
      var3 = null;
      var1.setline(122);
      var6 = new PyObject[]{var1.getname("True"), Py.newInteger(76), var1.getname("NL")};
      var9 = new PyFunction(var1.f_globals, var6, encode$3, PyString.fromInterned("Encode a string with base64.\n\n    Each line will be wrapped at, at most, maxlinelen characters (defaults to\n    76 characters).\n\n    If binary is False, end-of-line characters will be converted to the\n    canonical email end-of-line sequence \\r\\n.  Otherwise they will be left\n    verbatim (this is the default).\n\n    Each line of encoded text will end with eol, which defaults to \"\\n\".  Set\n    this to \"\\r\\n\" if you will be using the result of this function directly\n    in an email.\n    "));
      var1.setlocal("encode", var9);
      var3 = null;
      var1.setline(155);
      PyObject var10 = var1.getname("encode");
      var1.setlocal("body_encode", var10);
      var3 = null;
      var1.setline(156);
      var10 = var1.getname("encode");
      var1.setlocal("encodestring", var10);
      var3 = null;
      var1.setline(160);
      var6 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, decode$4, PyString.fromInterned("Decode a raw base64 string.\n\n    If convert_eols is set to a string value, all canonical email linefeeds,\n    e.g. \"\\r\\n\", in the decoded text will be converted to the value of\n    convert_eols.  os.linesep is a good choice for convert_eols if you are\n    decoding a text attachment.\n\n    This function does not parse a full MIME header value encoded with\n    base64 (like =?iso-8895-1?b?bmloISBuaWgh?=) -- please use the high\n    level email.header class for that functionality.\n    "));
      var1.setlocal("decode", var9);
      var3 = null;
      var1.setline(182);
      var10 = var1.getname("decode");
      var1.setlocal("body_decode", var10);
      var3 = null;
      var1.setline(183);
      var10 = var1.getname("decode");
      var1.setlocal("decodestring", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject base64_len$1(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString.fromInterned("Return the length of s when it is encoded with base64.");
      var1.setline(54);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)Py.newInteger(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(1)._mul(Py.newInteger(4));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(58);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(59);
         var3 = var1.getlocal(3);
         var3 = var3._iadd(Py.newInteger(4));
         var1.setlocal(3, var3);
      }

      var1.setline(60);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject header_encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyString.fromInterned("Encode a single header line with Base64 encoding in a given charset.\n\n    Defined in RFC 2045, this Base64 encoding is identical to normal Base64\n    encoding, except that each line must be intelligently wrapped (respecting\n    the Base64 encoding), and subsequent lines must start with a space.\n\n    charset names the character set to use to encode the header.  It defaults\n    to iso-8859-1.\n\n    End-of-line characters (\\r, \\n, \\r\\n) will be automatically converted\n    to the canonical email line separator \\r\\n unless the keep_eols\n    parameter is True (the default is False).\n\n    Each line of the header will be terminated in the value of eol, which\n    defaults to \"\\n\".  Set this to \"\\r\\n\" if you are using the result of\n    this function directly in email.\n\n    The resulting string will be in the form:\n\n    \"=?charset?b?WW/5ciBtYXp66XLrIHf8eiBhIGhhbXBzdGHuciBBIFlv+XIgbWF6euly?=\\n\n      =?charset?b?6yB3/HogYSBoYW1wc3Rh7nIgQkMgWW/5ciBtYXp66XLrIHf8eiBhIGhh?=\"\n\n    with each line wrapped at, at most, maxlinelen characters (defaults to 76\n    characters).\n    ");
      var1.setline(92);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(93);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(95);
         PyObject var4;
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(96);
            var4 = var1.getglobal("fix_eols").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(100);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var7);
         var4 = null;
         var1.setline(101);
         var4 = var1.getlocal(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1)))._sub(var1.getglobal("MISC_LEN"));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(102);
         var4 = var1.getlocal(6)._mul(Py.newInteger(3))._floordiv(Py.newInteger(4));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(104);
         var4 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)var1.getlocal(7)).__iter__();

         while(true) {
            var1.setline(104);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(108);
               var7 = new PyList(Py.EmptyObjects);
               var1.setlocal(9, var7);
               var4 = null;
               var1.setline(109);
               var4 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(109);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(117);
                     var4 = var1.getlocal(4)._add(PyString.fromInterned(" "));
                     var1.setlocal(11, var4);
                     var4 = null;
                     var1.setline(118);
                     var3 = var1.getlocal(11).__getattr__("join").__call__(var2, var1.getlocal(9));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(10, var5);
                  var1.setline(111);
                  if (var1.getlocal(10).__getattr__("endswith").__call__(var2, var1.getglobal("NL")).__nonzero__()) {
                     var1.setline(112);
                     PyObject var6 = var1.getlocal(10).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(10, var6);
                     var6 = null;
                  }

                  var1.setline(114);
                  var1.getlocal(9).__getattr__("append").__call__(var2, PyString.fromInterned("=?%s?b?%s?=")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(10)})));
               }
            }

            var1.setlocal(8, var5);
            var1.setline(105);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getglobal("b2a_base64").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(8), var1.getlocal(8)._add(var1.getlocal(7)), (PyObject)null)));
         }
      }
   }

   public PyObject encode$3(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyString.fromInterned("Encode a string with base64.\n\n    Each line will be wrapped at, at most, maxlinelen characters (defaults to\n    76 characters).\n\n    If binary is False, end-of-line characters will be converted to the\n    canonical email end-of-line sequence \\r\\n.  Otherwise they will be left\n    verbatim (this is the default).\n\n    Each line of encoded text will end with eol, which defaults to \"\\n\".  Set\n    this to \"\\r\\n\" if you will be using the result of this function directly\n    in an email.\n    ");
      var1.setline(136);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(137);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(139);
         PyObject var4;
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(140);
            var4 = var1.getglobal("fix_eols").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(142);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var7);
         var4 = null;
         var1.setline(143);
         var4 = var1.getlocal(2)._mul(Py.newInteger(3))._floordiv(Py.newInteger(4));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(144);
         var4 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)var1.getlocal(5)).__iter__();

         while(true) {
            var1.setline(144);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(151);
               var3 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(6, var5);
            var1.setline(147);
            PyObject var6 = var1.getglobal("b2a_base64").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(6), var1.getlocal(6)._add(var1.getlocal(5)), (PyObject)null));
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(148);
            PyObject var10000 = var1.getlocal(7).__getattr__("endswith").__call__(var2, var1.getglobal("NL"));
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(3);
               var10000 = var6._ne(var1.getglobal("NL"));
               var6 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(149);
               var6 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(var1.getlocal(3));
               var1.setlocal(7, var6);
               var6 = null;
            }

            var1.setline(150);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
         }
      }
   }

   public PyObject decode$4(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyString.fromInterned("Decode a raw base64 string.\n\n    If convert_eols is set to a string value, all canonical email linefeeds,\n    e.g. \"\\r\\n\", in the decoded text will be converted to the value of\n    convert_eols.  os.linesep is a good choice for convert_eols if you are\n    decoding a text attachment.\n\n    This function does not parse a full MIME header value encoded with\n    base64 (like =?iso-8895-1?b?bmloISBuaWgh?=) -- please use the high\n    level email.header class for that functionality.\n    ");
      var1.setline(172);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(173);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(175);
         PyObject var4 = var1.getglobal("a2b_base64").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(176);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(177);
            var3 = var1.getlocal(2).__getattr__("replace").__call__(var2, var1.getglobal("CRLF"), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(178);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public base64mime$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "groups_of_3", "leftover", "n"};
      base64_len$1 = Py.newCode(1, var2, var1, "base64_len", 52, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"header", "charset", "keep_eols", "maxlinelen", "eol", "base64ed", "max_encoded", "max_unencoded", "i", "lines", "line", "joiner"};
      header_encode$2 = Py.newCode(5, var2, var1, "header_encode", 64, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "binary", "maxlinelen", "eol", "encvec", "max_unencoded", "i", "enc"};
      encode$3 = Py.newCode(4, var2, var1, "encode", 122, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "convert_eols", "dec"};
      decode$4 = Py.newCode(2, var2, var1, "decode", 160, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new base64mime$py("email/base64mime$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(base64mime$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.base64_len$1(var2, var3);
         case 2:
            return this.header_encode$2(var2, var3);
         case 3:
            return this.encode$3(var2, var3);
         case 4:
            return this.decode$4(var2, var3);
         default:
            return null;
      }
   }
}
