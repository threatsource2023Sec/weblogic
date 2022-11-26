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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("base64.py")
public class base64$py extends PyFunctionTable implements PyRunnable {
   static base64$py self;
   static final PyCode f$0;
   static final PyCode _translate$1;
   static final PyCode b64encode$2;
   static final PyCode b64decode$3;
   static final PyCode standard_b64encode$4;
   static final PyCode standard_b64decode$5;
   static final PyCode urlsafe_b64encode$6;
   static final PyCode urlsafe_b64decode$7;
   static final PyCode b32encode$8;
   static final PyCode b32decode$9;
   static final PyCode b16encode$10;
   static final PyCode b16decode$11;
   static final PyCode encode$12;
   static final PyCode decode$13;
   static final PyCode encodestring$14;
   static final PyCode decodestring$15;
   static final PyCode test$16;
   static final PyCode test1$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("RFC 3548: Base16, Base32, Base64 Data Encodings"));
      var1.setline(3);
      PyString.fromInterned("RFC 3548: Base16, Base32, Base64 Data Encodings");
      var1.setline(8);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var3);
      var3 = null;
      var1.setline(13);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("encode"), PyString.fromInterned("decode"), PyString.fromInterned("encodestring"), PyString.fromInterned("decodestring"), PyString.fromInterned("b64encode"), PyString.fromInterned("b64decode"), PyString.fromInterned("b32encode"), PyString.fromInterned("b32decode"), PyString.fromInterned("b16encode"), PyString.fromInterned("b16decode"), PyString.fromInterned("standard_b64encode"), PyString.fromInterned("standard_b64decode"), PyString.fromInterned("urlsafe_b64encode"), PyString.fromInterned("urlsafe_b64decode")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(28);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal("_[28_16]", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(28);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(28);
            var1.dellocal("_[28_16]");
            var7 = var10000;
            var1.setlocal("_translation", var7);
            var3 = null;
            var1.setline(29);
            PyString var8 = PyString.fromInterned("");
            var1.setlocal("EMPTYSTRING", var8);
            var3 = null;
            var1.setline(32);
            PyObject[] var9 = Py.EmptyObjects;
            PyFunction var10 = new PyFunction(var1.f_globals, var9, _translate$1, (PyObject)null);
            var1.setlocal("_translate", var10);
            var3 = null;
            var1.setline(42);
            var9 = new PyObject[]{var1.getname("None")};
            var10 = new PyFunction(var1.f_globals, var9, b64encode$2, PyString.fromInterned("Encode a string using Base64.\n\n    s is the string to encode.  Optional altchars must be a string of at least\n    length 2 (additional characters are ignored) which specifies an\n    alternative alphabet for the '+' and '/' characters.  This allows an\n    application to e.g. generate url or filesystem safe Base64 strings.\n\n    The encoded string is returned.\n    "));
            var1.setlocal("b64encode", var10);
            var3 = null;
            var1.setline(59);
            var9 = new PyObject[]{var1.getname("None")};
            var10 = new PyFunction(var1.f_globals, var9, b64decode$3, PyString.fromInterned("Decode a Base64 encoded string.\n\n    s is the string to decode.  Optional altchars must be a string of at least\n    length 2 (additional characters are ignored) which specifies the\n    alternative alphabet used instead of the '+' and '/' characters.\n\n    The decoded string is returned.  A TypeError is raised if s were\n    incorrectly padded or if there are non-alphabet characters present in the\n    string.\n    "));
            var1.setlocal("b64decode", var10);
            var3 = null;
            var1.setline(79);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, standard_b64encode$4, PyString.fromInterned("Encode a string using the standard Base64 alphabet.\n\n    s is the string to encode.  The encoded string is returned.\n    "));
            var1.setlocal("standard_b64encode", var10);
            var3 = null;
            var1.setline(86);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, standard_b64decode$5, PyString.fromInterned("Decode a string encoded with the standard Base64 alphabet.\n\n    s is the string to decode.  The decoded string is returned.  A TypeError\n    is raised if the string is incorrectly padded or if there are non-alphabet\n    characters present in the string.\n    "));
            var1.setlocal("standard_b64decode", var10);
            var3 = null;
            var1.setline(95);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, urlsafe_b64encode$6, PyString.fromInterned("Encode a string using a url-safe Base64 alphabet.\n\n    s is the string to encode.  The encoded string is returned.  The alphabet\n    uses '-' instead of '+' and '_' instead of '/'.\n    "));
            var1.setlocal("urlsafe_b64encode", var10);
            var3 = null;
            var1.setline(103);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, urlsafe_b64decode$7, PyString.fromInterned("Decode a string encoded with the standard Base64 alphabet.\n\n    s is the string to decode.  The decoded string is returned.  A TypeError\n    is raised if the string is incorrectly padded or if there are non-alphabet\n    characters present in the string.\n\n    The alphabet uses '-' instead of '+' and '_' instead of '/'.\n    "));
            var1.setlocal("urlsafe_b64decode", var10);
            var3 = null;
            var1.setline(117);
            PyDictionary var11 = new PyDictionary(new PyObject[]{Py.newInteger(0), PyString.fromInterned("A"), Py.newInteger(9), PyString.fromInterned("J"), Py.newInteger(18), PyString.fromInterned("S"), Py.newInteger(27), PyString.fromInterned("3"), Py.newInteger(1), PyString.fromInterned("B"), Py.newInteger(10), PyString.fromInterned("K"), Py.newInteger(19), PyString.fromInterned("T"), Py.newInteger(28), PyString.fromInterned("4"), Py.newInteger(2), PyString.fromInterned("C"), Py.newInteger(11), PyString.fromInterned("L"), Py.newInteger(20), PyString.fromInterned("U"), Py.newInteger(29), PyString.fromInterned("5"), Py.newInteger(3), PyString.fromInterned("D"), Py.newInteger(12), PyString.fromInterned("M"), Py.newInteger(21), PyString.fromInterned("V"), Py.newInteger(30), PyString.fromInterned("6"), Py.newInteger(4), PyString.fromInterned("E"), Py.newInteger(13), PyString.fromInterned("N"), Py.newInteger(22), PyString.fromInterned("W"), Py.newInteger(31), PyString.fromInterned("7"), Py.newInteger(5), PyString.fromInterned("F"), Py.newInteger(14), PyString.fromInterned("O"), Py.newInteger(23), PyString.fromInterned("X"), Py.newInteger(6), PyString.fromInterned("G"), Py.newInteger(15), PyString.fromInterned("P"), Py.newInteger(24), PyString.fromInterned("Y"), Py.newInteger(7), PyString.fromInterned("H"), Py.newInteger(16), PyString.fromInterned("Q"), Py.newInteger(25), PyString.fromInterned("Z"), Py.newInteger(8), PyString.fromInterned("I"), Py.newInteger(17), PyString.fromInterned("R"), Py.newInteger(26), PyString.fromInterned("2")});
            var1.setlocal("_b32alphabet", var11);
            var3 = null;
            var1.setline(129);
            var3 = var1.getname("_b32alphabet").__getattr__("items").__call__(var2);
            var1.setlocal("_b32tab", var3);
            var3 = null;
            var1.setline(130);
            var1.getname("_b32tab").__getattr__("sort").__call__(var2);
            var1.setline(131);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal("_[131_11]", var3);
            var3 = null;
            var1.setline(131);
            var3 = var1.getname("_b32tab").__iter__();

            while(true) {
               var1.setline(131);
               var4 = var3.__iternext__();
               PyObject[] var5;
               PyObject var6;
               if (var4 == null) {
                  var1.setline(131);
                  var1.dellocal("_[131_11]");
                  var7 = var10000;
                  var1.setlocal("_b32tab", var7);
                  var3 = null;
                  var1.setline(132);
                  PyObject var13 = var1.getname("dict");
                  PyList var10002 = new PyList();
                  var3 = var10002.__getattr__("append");
                  var1.setlocal("_[132_16]", var3);
                  var3 = null;
                  var1.setline(132);
                  var3 = var1.getname("_b32alphabet").__getattr__("items").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(132);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(132);
                        var1.dellocal("_[132_16]");
                        var3 = var13.__call__((ThreadState)var2, (PyObject)var10002);
                        var1.setlocal("_b32rev", var3);
                        var3 = null;
                        var1.setline(135);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, b32encode$8, PyString.fromInterned("Encode a string using Base32.\n\n    s is the string to encode.  The encoded string is returned.\n    "));
                        var1.setlocal("b32encode", var10);
                        var3 = null;
                        var1.setline(177);
                        var9 = new PyObject[]{var1.getname("False"), var1.getname("None")};
                        var10 = new PyFunction(var1.f_globals, var9, b32decode$9, PyString.fromInterned("Decode a Base32 encoded string.\n\n    s is the string to decode.  Optional casefold is a flag specifying whether\n    a lowercase alphabet is acceptable as input.  For security purposes, the\n    default is False.\n\n    RFC 3548 allows for optional mapping of the digit 0 (zero) to the letter O\n    (oh), and for optional mapping of the digit 1 (one) to either the letter I\n    (eye) or letter L (el).  The optional argument map01 when not None,\n    specifies which letter the digit 1 should be mapped to (when map01 is not\n    None, the digit 0 is always mapped to the letter O).  For security\n    purposes the default is None, so that 0 and 1 are not allowed in the\n    input.\n\n    The decoded string is returned.  A TypeError is raised if s were\n    incorrectly padded or if there are non-alphabet characters present in the\n    string.\n    "));
                        var1.setlocal("b32decode", var10);
                        var3 = null;
                        var1.setline(251);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, b16encode$10, PyString.fromInterned("Encode a string using Base16.\n\n    s is the string to encode.  The encoded string is returned.\n    "));
                        var1.setlocal("b16encode", var10);
                        var3 = null;
                        var1.setline(259);
                        var9 = new PyObject[]{var1.getname("False")};
                        var10 = new PyFunction(var1.f_globals, var9, b16decode$11, PyString.fromInterned("Decode a Base16 encoded string.\n\n    s is the string to decode.  Optional casefold is a flag specifying whether\n    a lowercase alphabet is acceptable as input.  For security purposes, the\n    default is False.\n\n    The decoded string is returned.  A TypeError is raised if s were\n    incorrectly padded or if there are non-alphabet characters present in the\n    string.\n    "));
                        var1.setlocal("b16decode", var10);
                        var3 = null;
                        var1.setline(282);
                        PyInteger var12 = Py.newInteger(76);
                        var1.setlocal("MAXLINESIZE", var12);
                        var3 = null;
                        var1.setline(283);
                        var3 = var1.getname("MAXLINESIZE")._floordiv(Py.newInteger(4))._mul(Py.newInteger(3));
                        var1.setlocal("MAXBINSIZE", var3);
                        var3 = null;
                        var1.setline(285);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, encode$12, PyString.fromInterned("Encode a file."));
                        var1.setlocal("encode", var10);
                        var3 = null;
                        var1.setline(300);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, decode$13, PyString.fromInterned("Decode a file."));
                        var1.setlocal("decode", var10);
                        var3 = null;
                        var1.setline(310);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, encodestring$14, PyString.fromInterned("Encode a string into multiple lines of base-64 data."));
                        var1.setlocal("encodestring", var10);
                        var3 = null;
                        var1.setline(319);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, decodestring$15, PyString.fromInterned("Decode a string."));
                        var1.setlocal("decodestring", var10);
                        var3 = null;
                        var1.setline(326);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, test$16, PyString.fromInterned("Small test program"));
                        var1.setlocal("test", var10);
                        var3 = null;
                        var1.setline(352);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, test1$17, (PyObject)null);
                        var1.setlocal("test1", var10);
                        var3 = null;
                        var1.setline(359);
                        var3 = var1.getname("__name__");
                        var13 = var3._eq(PyString.fromInterned("__main__"));
                        var3 = null;
                        if (var13.__nonzero__()) {
                           var1.setline(360);
                           var1.getname("test").__call__(var2);
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal("k", var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal("v", var6);
                     var6 = null;
                     var1.setline(132);
                     var1.getname("_[132_16]").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getname("v"), var1.getname("long").__call__(var2, var1.getname("k"))})));
                  }
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal("k", var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal("v", var6);
               var6 = null;
               var1.setline(131);
               var1.getname("_[131_11]").__call__(var2, var1.getname("v"));
            }
         }

         var1.setlocal("_x", var4);
         var1.setline(28);
         var1.getname("_[28_16]").__call__(var2, var1.getname("chr").__call__(var2, var1.getname("_x")));
      }
   }

   public PyObject _translate$1(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getglobal("_translation").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(34);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(36);
            var3 = var1.getlocal(0).__getattr__("translate").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2)));
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(35);
         PyObject var7 = var1.getlocal(4);
         var1.getlocal(2).__setitem__(var1.getglobal("ord").__call__(var2, var1.getlocal(3)), var7);
         var5 = null;
      }
   }

   public PyObject b64encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Encode a string using Base64.\n\n    s is the string to encode.  Optional altchars must be a string of at least\n    length 2 (additional characters are ignored) which specifies an\n    alternative alphabet for the '+' and '/' characters.  This allows an\n    application to e.g. generate url or filesystem safe Base64 strings.\n\n    The encoded string is returned.\n    ");
      var1.setline(53);
      PyObject var3 = var1.getglobal("binascii").__getattr__("b2a_base64").__call__(var2, var1.getlocal(0)).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(55);
         var3 = var1.getglobal("_translate").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("+"), var1.getlocal(1).__getitem__(Py.newInteger(0)), PyString.fromInterned("/"), var1.getlocal(1).__getitem__(Py.newInteger(1))})));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(56);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject b64decode$3(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Decode a Base64 encoded string.\n\n    s is the string to decode.  Optional altchars must be a string of at least\n    length 2 (additional characters are ignored) which specifies the\n    alternative alphabet used instead of the '+' and '/' characters.\n\n    The decoded string is returned.  A TypeError is raised if s were\n    incorrectly padded or if there are non-alphabet characters present in the\n    string.\n    ");
      var1.setline(70);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(71);
         var3 = var1.getglobal("_translate").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyDictionary(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), PyString.fromInterned("+"), var1.getlocal(1).__getitem__(Py.newInteger(1)), PyString.fromInterned("/")})));
         var1.setlocal(0, var3);
         var3 = null;
      }

      try {
         var1.setline(73);
         var3 = var1.getglobal("binascii").__getattr__("a2b_base64").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("binascii").__getattr__("Error"))) {
            PyObject var5 = var4.value;
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(76);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)));
         } else {
            throw var4;
         }
      }
   }

   public PyObject standard_b64encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyString.fromInterned("Encode a string using the standard Base64 alphabet.\n\n    s is the string to encode.  The encoded string is returned.\n    ");
      var1.setline(84);
      PyObject var3 = var1.getglobal("b64encode").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject standard_b64decode$5(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("Decode a string encoded with the standard Base64 alphabet.\n\n    s is the string to decode.  The decoded string is returned.  A TypeError\n    is raised if the string is incorrectly padded or if there are non-alphabet\n    characters present in the string.\n    ");
      var1.setline(93);
      PyObject var3 = var1.getglobal("b64decode").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urlsafe_b64encode$6(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyString.fromInterned("Encode a string using a url-safe Base64 alphabet.\n\n    s is the string to encode.  The encoded string is returned.  The alphabet\n    uses '-' instead of '+' and '_' instead of '/'.\n    ");
      var1.setline(101);
      PyObject var3 = var1.getglobal("b64encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("-_"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject urlsafe_b64decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyString.fromInterned("Decode a string encoded with the standard Base64 alphabet.\n\n    s is the string to decode.  The decoded string is returned.  A TypeError\n    is raised if the string is incorrectly padded or if there are non-alphabet\n    characters present in the string.\n\n    The alphabet uses '-' instead of '+' and '_' instead of '/'.\n    ");
      var1.setline(112);
      PyObject var3 = var1.getglobal("b64decode").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("-_"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject b32encode$8(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyString.fromInterned("Encode a string using Base32.\n\n    s is the string to encode.  The encoded string is returned.\n    ");
      var1.setline(140);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(141);
      PyObject var8 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)Py.newInteger(5));
      PyObject[] var4 = Py.unpackSequence(var8, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(143);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(144);
         var8 = var1.getlocal(0);
         var8 = var8._iadd(PyString.fromInterned("\u0000")._mul(Py.newInteger(5)._sub(var1.getlocal(3))));
         var1.setlocal(0, var8);
         var1.setline(145);
         var8 = var1.getlocal(2);
         var8 = var8._iadd(Py.newInteger(1));
         var1.setlocal(2, var8);
      }

      var1.setline(146);
      var8 = var1.getglobal("range").__call__(var2, var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(146);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            var1.setline(164);
            var8 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.setlocal(8, var8);
            var3 = null;
            var1.setline(166);
            var8 = var1.getlocal(3);
            PyObject var10000 = var8._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(167);
               var8 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-6), (PyObject)null)._add(PyString.fromInterned("======"));
               var1.f_lasti = -1;
               return var8;
            } else {
               var1.setline(168);
               var9 = var1.getlocal(3);
               var10000 = var9._eq(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(169);
                  var8 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-4), (PyObject)null)._add(PyString.fromInterned("===="));
                  var1.f_lasti = -1;
                  return var8;
               } else {
                  var1.setline(170);
                  var9 = var1.getlocal(3);
                  var10000 = var9._eq(Py.newInteger(3));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(171);
                     var8 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null)._add(PyString.fromInterned("==="));
                     var1.f_lasti = -1;
                     return var8;
                  } else {
                     var1.setline(172);
                     var9 = var1.getlocal(3);
                     var10000 = var9._eq(Py.newInteger(4));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(173);
                        var8 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(PyString.fromInterned("="));
                        var1.f_lasti = -1;
                        return var8;
                     } else {
                        var1.setline(174);
                        var8 = var1.getlocal(8);
                        var1.f_lasti = -1;
                        return var8;
                     }
                  }
               }
            }
         }

         var1.setlocal(4, var9);
         var1.setline(152);
         var5 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("!HHB"), (PyObject)var1.getlocal(0).__getslice__(var1.getlocal(4)._mul(Py.newInteger(5)), var1.getlocal(4)._add(Py.newInteger(1))._mul(Py.newInteger(5)), (PyObject)null));
         PyObject[] var6 = Py.unpackSequence(var5, 3);
         PyObject var7 = var6[0];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(153);
         var5 = var1.getlocal(6);
         var5 = var5._iadd(var1.getlocal(5)._and(Py.newInteger(1))._lshift(Py.newInteger(16)));
         var1.setlocal(6, var5);
         var1.setline(154);
         var5 = var1.getlocal(7);
         var5 = var5._iadd(var1.getlocal(6)._and(Py.newInteger(3))._lshift(Py.newInteger(8)));
         var1.setlocal(7, var5);
         var1.setline(155);
         var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("_b32tab").__getitem__(var1.getlocal(5)._rshift(Py.newInteger(11))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(5)._rshift(Py.newInteger(6))._and(Py.newInteger(31))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(5)._rshift(Py.newInteger(1))._and(Py.newInteger(31))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(6)._rshift(Py.newInteger(12))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(6)._rshift(Py.newInteger(7))._and(Py.newInteger(31))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(6)._rshift(Py.newInteger(2))._and(Py.newInteger(31))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(7)._rshift(Py.newInteger(5))), var1.getglobal("_b32tab").__getitem__(var1.getlocal(7)._and(Py.newInteger(31)))})));
      }
   }

   public PyObject b32decode$9(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyString.fromInterned("Decode a Base32 encoded string.\n\n    s is the string to decode.  Optional casefold is a flag specifying whether\n    a lowercase alphabet is acceptable as input.  For security purposes, the\n    default is False.\n\n    RFC 3548 allows for optional mapping of the digit 0 (zero) to the letter O\n    (oh), and for optional mapping of the digit 1 (one) to either the letter I\n    (eye) or letter L (el).  The optional argument map01 when not None,\n    specifies which letter the digit 1 should be mapped to (when map01 is not\n    None, the digit 0 is always mapped to the letter O).  For security\n    purposes the default is None, so that 0 and 1 are not allowed in the\n    input.\n\n    The decoded string is returned.  A TypeError is raised if s were\n    incorrectly padded or if there are non-alphabet characters present in the\n    string.\n    ");
      var1.setline(196);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)Py.newInteger(8));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(197);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(198);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Incorrect padding")));
      } else {
         var1.setline(202);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(203);
            var3 = var1.getglobal("_translate").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("0"), PyString.fromInterned("O"), PyString.fromInterned("1"), var1.getlocal(2)})));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(204);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(205);
            var3 = var1.getlocal(0).__getattr__("upper").__call__(var2);
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(209);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(210);
         var3 = var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?P<pad>[=]*)$"), (PyObject)var1.getlocal(0));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(211);
         PyObject var10000;
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(212);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pad")));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(213);
            var3 = var1.getlocal(5);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(214);
               var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(5).__neg__(), (PyObject)null);
               var1.setlocal(0, var3);
               var3 = null;
            }
         }

         var1.setline(216);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(7, var8);
         var3 = null;
         var1.setline(217);
         var7 = Py.newInteger(0);
         var1.setlocal(8, var7);
         var3 = null;
         var1.setline(218);
         var7 = Py.newInteger(35);
         var1.setlocal(9, var7);
         var3 = null;
         var1.setline(219);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(219);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(230);
               var3 = var1.getglobal("binascii").__getattr__("unhexlify").__call__(var2, PyString.fromInterned("%010x")._mod(var1.getlocal(8)));
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(231);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(232);
                  PyString var10 = PyString.fromInterned("");
                  var1.setlocal(12, var10);
                  var3 = null;
               } else {
                  var1.setline(233);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(Py.newInteger(1));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(234);
                     var3 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(12, var3);
                     var3 = null;
                  } else {
                     var1.setline(235);
                     var3 = var1.getlocal(5);
                     var10000 = var3._eq(Py.newInteger(3));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(236);
                        var3 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
                        var1.setlocal(12, var3);
                        var3 = null;
                     } else {
                        var1.setline(237);
                        var3 = var1.getlocal(5);
                        var10000 = var3._eq(Py.newInteger(4));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(238);
                           var3 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null);
                           var1.setlocal(12, var3);
                           var3 = null;
                        } else {
                           var1.setline(239);
                           var3 = var1.getlocal(5);
                           var10000 = var3._eq(Py.newInteger(6));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(242);
                              throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Incorrect padding")));
                           }

                           var1.setline(240);
                           var3 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-4), (PyObject)null);
                           var1.setlocal(12, var3);
                           var3 = null;
                        }
                     }
                  }
               }

               var1.setline(243);
               var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(12));
               var1.setline(244);
               var3 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(7));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(10, var6);
            var1.setline(220);
            var5 = var1.getglobal("_b32rev").__getattr__("get").__call__(var2, var1.getlocal(10));
            var1.setlocal(11, var5);
            var5 = null;
            var1.setline(221);
            var5 = var1.getlocal(11);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(222);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Non-base32 digit found")));
            }

            var1.setline(223);
            var5 = var1.getlocal(8);
            var5 = var5._iadd(var1.getglobal("_b32rev").__getitem__(var1.getlocal(10))._lshift(var1.getlocal(9)));
            var1.setlocal(8, var5);
            var1.setline(224);
            var5 = var1.getlocal(9);
            var5 = var5._isub(Py.newInteger(5));
            var1.setlocal(9, var5);
            var1.setline(225);
            var5 = var1.getlocal(9);
            var10000 = var5._lt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(226);
               var1.getlocal(7).__getattr__("append").__call__(var2, var1.getglobal("binascii").__getattr__("unhexlify").__call__(var2, PyString.fromInterned("%010x")._mod(var1.getlocal(8))));
               var1.setline(227);
               PyInteger var9 = Py.newInteger(0);
               var1.setlocal(8, var9);
               var5 = null;
               var1.setline(228);
               var9 = Py.newInteger(35);
               var1.setlocal(9, var9);
               var5 = null;
            }
         }
      }
   }

   public PyObject b16encode$10(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyString.fromInterned("Encode a string using Base16.\n\n    s is the string to encode.  The encoded string is returned.\n    ");
      var1.setline(256);
      PyObject var3 = var1.getglobal("binascii").__getattr__("hexlify").__call__(var2, var1.getlocal(0)).__getattr__("upper").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject b16decode$11(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyString.fromInterned("Decode a Base16 encoded string.\n\n    s is the string to decode.  Optional casefold is a flag specifying whether\n    a lowercase alphabet is acceptable as input.  For security purposes, the\n    default is False.\n\n    The decoded string is returned.  A TypeError is raised if s were\n    incorrectly padded or if there are non-alphabet characters present in the\n    string.\n    ");
      var1.setline(270);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(271);
         var3 = var1.getlocal(0).__getattr__("upper").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(272);
      if (var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^0-9A-F]"), (PyObject)var1.getlocal(0)).__nonzero__()) {
         var1.setline(273);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Non-base16 digit found")));
      } else {
         var1.setline(274);
         var3 = var1.getglobal("binascii").__getattr__("unhexlify").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject encode$12(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyString.fromInterned("Encode a file.");

      while(true) {
         var1.setline(287);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(288);
         PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("MAXBINSIZE"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(289);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         while(true) {
            var1.setline(291);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            PyObject var10000 = var3._lt(var1.getglobal("MAXBINSIZE"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(292);
            var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("MAXBINSIZE")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(293);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               break;
            }

            var1.setline(295);
            var3 = var1.getlocal(2);
            var3 = var3._iadd(var1.getlocal(3));
            var1.setlocal(2, var3);
         }

         var1.setline(296);
         var3 = var1.getglobal("binascii").__getattr__("b2a_base64").__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(297);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(4));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$13(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyString.fromInterned("Decode a file.");

      while(true) {
         var1.setline(302);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(303);
         PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(304);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(306);
         var3 = var1.getglobal("binascii").__getattr__("a2b_base64").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(307);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encodestring$14(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyString.fromInterned("Encode a string into multiple lines of base-64 data.");
      var1.setline(312);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(313);
      PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)var1.getglobal("MAXBINSIZE")).__iter__();

      while(true) {
         var1.setline(313);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(316);
            var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(314);
         PyObject var5 = var1.getlocal(0).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getglobal("MAXBINSIZE")), (PyObject)null);
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(315);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("binascii").__getattr__("b2a_base64").__call__(var2, var1.getlocal(3)));
      }
   }

   public PyObject decodestring$15(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyString.fromInterned("Decode a string.");
      var1.setline(321);
      PyObject var3 = var1.getglobal("binascii").__getattr__("a2b_base64").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$16(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(327);
      PyString.fromInterned("Small test program");
      var1.setline(328);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyObject var5;
      try {
         var1.setline(330);
         var3 = var1.getlocal(1).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("deut"));
         PyObject[] var10 = Py.unpackSequence(var3, 2);
         var5 = var10[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var8) {
         PyException var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getlocal(1).__getattr__("error"))) {
            throw var9;
         }

         var4 = var9.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(332);
         var4 = var1.getlocal(0).__getattr__("stderr");
         var1.getlocal(0).__setattr__("stdout", var4);
         var4 = null;
         var1.setline(333);
         Py.println(var1.getlocal(4));
         var1.setline(334);
         Py.println(PyString.fromInterned("usage: %s [-d|-e|-u|-t] [file|-]\n        -d, -u: decode\n        -e: encode (default)\n        -t: encode and decode string 'Aladdin:open sesame'")._mod(var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(0))));
         var1.setline(338);
         var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      }

      var1.setline(339);
      var3 = var1.getglobal("encode");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getlocal(2).__iter__();

      PyObject var10000;
      do {
         var1.setline(340);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(345);
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var10000 = var3._ne(PyString.fromInterned("-"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               label67: {
                  ContextManager var12;
                  var4 = (var12 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

                  try {
                     var1.setlocal(8, var4);
                     var1.setline(347);
                     var1.getlocal(5).__call__(var2, var1.getlocal(8), var1.getlocal(0).__getattr__("stdout"));
                  } catch (Throwable var7) {
                     if (var12.__exit__(var2, Py.setException(var7, var1))) {
                        break label67;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var12.__exit__(var2, (PyException)null);
               }
            } else {
               var1.setline(349);
               var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("stdin"), var1.getlocal(0).__getattr__("stdout"));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var11 = Py.unpackSequence(var4, 2);
         PyObject var6 = var11[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(341);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(PyString.fromInterned("-e"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(341);
            var5 = var1.getglobal("encode");
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(342);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(PyString.fromInterned("-d"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(342);
            var5 = var1.getglobal("decode");
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(343);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(PyString.fromInterned("-u"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(343);
            var5 = var1.getglobal("decode");
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(344);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(PyString.fromInterned("-t"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(344);
      var1.getglobal("test1").__call__(var2);
      var1.setline(344);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test1$17(PyFrame var1, ThreadState var2) {
      var1.setline(353);
      PyString var3 = PyString.fromInterned("Aladdin:open sesame");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(354);
      PyObject var4 = var1.getglobal("encodestring").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(355);
      var4 = var1.getglobal("decodestring").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(356);
      Py.printComma(var1.getlocal(0));
      Py.printComma(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      Py.println(var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public base64$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "altchars", "translation", "k", "v"};
      _translate$1 = Py.newCode(2, var2, var1, "_translate", 32, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "altchars", "encoded"};
      b64encode$2 = Py.newCode(2, var2, var1, "b64encode", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "altchars", "msg"};
      b64decode$3 = Py.newCode(2, var2, var1, "b64decode", 59, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      standard_b64encode$4 = Py.newCode(1, var2, var1, "standard_b64encode", 79, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      standard_b64decode$5 = Py.newCode(1, var2, var1, "standard_b64decode", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      urlsafe_b64encode$6 = Py.newCode(1, var2, var1, "urlsafe_b64encode", 95, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      urlsafe_b64decode$7 = Py.newCode(1, var2, var1, "urlsafe_b64decode", 103, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "parts", "quanta", "leftover", "i", "c1", "c2", "c3", "encoded"};
      b32encode$8 = Py.newCode(1, var2, var1, "b32encode", 135, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "casefold", "map01", "quanta", "leftover", "padchars", "mo", "parts", "acc", "shift", "c", "val", "last"};
      b32decode$9 = Py.newCode(3, var2, var1, "b32decode", 177, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      b16encode$10 = Py.newCode(1, var2, var1, "b16encode", 251, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "casefold"};
      b16decode$11 = Py.newCode(2, var2, var1, "b16decode", 259, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "s", "ns", "line"};
      encode$12 = Py.newCode(2, var2, var1, "encode", 285, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "line", "s"};
      decode$13 = Py.newCode(2, var2, var1, "decode", 300, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "pieces", "i", "chunk"};
      encodestring$14 = Py.newCode(1, var2, var1, "encodestring", 310, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      decodestring$15 = Py.newCode(1, var2, var1, "decodestring", 319, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "getopt", "opts", "args", "msg", "func", "o", "a", "f"};
      test$16 = Py.newCode(0, var2, var1, "test", 326, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s0", "s1", "s2"};
      test1$17 = Py.newCode(0, var2, var1, "test1", 352, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new base64$py("base64$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(base64$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._translate$1(var2, var3);
         case 2:
            return this.b64encode$2(var2, var3);
         case 3:
            return this.b64decode$3(var2, var3);
         case 4:
            return this.standard_b64encode$4(var2, var3);
         case 5:
            return this.standard_b64decode$5(var2, var3);
         case 6:
            return this.urlsafe_b64encode$6(var2, var3);
         case 7:
            return this.urlsafe_b64decode$7(var2, var3);
         case 8:
            return this.b32encode$8(var2, var3);
         case 9:
            return this.b32decode$9(var2, var3);
         case 10:
            return this.b16encode$10(var2, var3);
         case 11:
            return this.b16decode$11(var2, var3);
         case 12:
            return this.encode$12(var2, var3);
         case 13:
            return this.decode$13(var2, var3);
         case 14:
            return this.encodestring$14(var2, var3);
         case 15:
            return this.decodestring$15(var2, var3);
         case 16:
            return this.test$16(var2, var3);
         case 17:
            return this.test1$17(var2, var3);
         default:
            return null;
      }
   }
}
