package encodings;

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
@Filename("encodings/punycode.py")
public class punycode$py extends PyFunctionTable implements PyRunnable {
   static punycode$py self;
   static final PyCode f$0;
   static final PyCode segregate$1;
   static final PyCode selective_len$2;
   static final PyCode selective_find$3;
   static final PyCode insertion_unsort$4;
   static final PyCode T$5;
   static final PyCode generate_generalized_integer$6;
   static final PyCode adapt$7;
   static final PyCode generate_integers$8;
   static final PyCode punycode_encode$9;
   static final PyCode decode_generalized_number$10;
   static final PyCode insertion_sort$11;
   static final PyCode punycode_decode$12;
   static final PyCode Codec$13;
   static final PyCode encode$14;
   static final PyCode decode$15;
   static final PyCode IncrementalEncoder$16;
   static final PyCode encode$17;
   static final PyCode IncrementalDecoder$18;
   static final PyCode decode$19;
   static final PyCode StreamWriter$20;
   static final PyCode StreamReader$21;
   static final PyCode getregentry$22;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Codec for the Punicode encoding, as specified in RFC 3492\n\nWritten by Martin v. Löwis.\n"));
      var1.setline(5);
      PyString.fromInterned(" Codec for the Punicode encoding, as specified in RFC 3492\n\nWritten by Martin v. Löwis.\n");
      var1.setline(7);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(11);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, segregate$1, PyString.fromInterned("3.1 Basic code point segregation"));
      var1.setlocal("segregate", var6);
      var3 = null;
      var1.setline(24);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, selective_len$2, PyString.fromInterned("Return the length of str, considering only characters below max."));
      var1.setlocal("selective_len", var6);
      var3 = null;
      var1.setline(32);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, selective_find$3, PyString.fromInterned("Return a pair (index, pos), indicating the next occurrence of\n    char in str. index is the position of the character considering\n    only ordinals up to and including char, and pos is the position in\n    the full string. index/pos is the starting position in the full\n    string."));
      var1.setlocal("selective_find", var6);
      var3 = null;
      var1.setline(50);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, insertion_unsort$4, PyString.fromInterned("3.2 Insertion unsort coding"));
      var1.setlocal("insertion_unsort", var6);
      var3 = null;
      var1.setline(72);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, T$5, (PyObject)null);
      var1.setlocal("T", var6);
      var3 = null;
      var1.setline(79);
      PyString var7 = PyString.fromInterned("abcdefghijklmnopqrstuvwxyz0123456789");
      var1.setlocal("digits", var7);
      var3 = null;
      var1.setline(80);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, generate_generalized_integer$6, PyString.fromInterned("3.3 Generalized variable-length integers"));
      var1.setlocal("generate_generalized_integer", var6);
      var3 = null;
      var1.setline(93);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, adapt$7, (PyObject)null);
      var1.setlocal("adapt", var6);
      var3 = null;
      var1.setline(108);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, generate_integers$8, PyString.fromInterned("3.4 Bias adaptation"));
      var1.setlocal("generate_integers", var6);
      var3 = null;
      var1.setline(119);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, punycode_encode$9, (PyObject)null);
      var1.setlocal("punycode_encode", var6);
      var3 = null;
      var1.setline(130);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, decode_generalized_number$10, PyString.fromInterned("3.3 Generalized variable-length integers"));
      var1.setlocal("decode_generalized_number", var6);
      var3 = null;
      var1.setline(160);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, insertion_sort$11, PyString.fromInterned("3.2 Insertion unsort coding"));
      var1.setlocal("insertion_sort", var6);
      var3 = null;
      var1.setline(185);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, punycode_decode$12, (PyObject)null);
      var1.setlocal("punycode_decode", var6);
      var3 = null;
      var1.setline(199);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$13);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(211);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$16);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(215);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$18);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(221);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$20);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(224);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$21);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(229);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$22, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject segregate$1(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyString.fromInterned("3.1 Basic code point segregation");
      var1.setline(13);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(14);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(15);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(15);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(20);
            var7 = var1.getlocal(2).__getattr__("keys").__call__(var2);
            var1.setlocal(2, var7);
            var3 = null;
            var1.setline(21);
            var1.getlocal(2).__getattr__("sort").__call__(var2);
            var1.setline(22);
            PyTuple var8 = new PyTuple(new PyObject[]{PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1)).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii")), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(3, var4);
         var1.setline(16);
         PyObject var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var5._lt(Py.newInteger(128));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(17);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         } else {
            var1.setline(19);
            PyInteger var9 = Py.newInteger(1);
            var1.getlocal(2).__setitem__((PyObject)var1.getlocal(3), var9);
            var5 = null;
         }
      }
   }

   public PyObject selective_len$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyString.fromInterned("Return the length of str, considering only characters below max.");
      var1.setline(26);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(27);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(27);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(30);
            var6 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(28);
         PyObject var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var5._lt(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(29);
            var5 = var1.getlocal(2);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(2, var5);
         }
      }
   }

   public PyObject selective_find$3(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyString.fromInterned("Return a pair (index, pos), indicating the next occurrence of\n    char in str. index is the position of the character considering\n    only ordinals up to and including char, and pos is the position in\n    the full string. index/pos is the starting position in the full\n    string.");
      var1.setline(39);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(40);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(41);
         var3 = var1.getlocal(3);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(3, var3);
         var1.setline(42);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._eq(var1.getlocal(4));
         var3 = null;
         PyTuple var5;
         if (var10000.__nonzero__()) {
            var1.setline(43);
            var5 = new PyTuple(new PyObject[]{Py.newInteger(-1), Py.newInteger(-1)});
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(44);
         PyObject var4 = var1.getlocal(0).__getitem__(var1.getlocal(3));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(45);
         var4 = var1.getlocal(5);
         var10000 = var4._eq(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(46);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(2)._add(Py.newInteger(1)), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(47);
         var4 = var1.getlocal(5);
         var10000 = var4._lt(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(48);
            var4 = var1.getlocal(2);
            var4 = var4._iadd(Py.newInteger(1));
            var1.setlocal(2, var4);
         }
      }
   }

   public PyObject insertion_unsort$4(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("3.2 Insertion unsort coding");
      var1.setline(52);
      PyInteger var3 = Py.newInteger(128);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(53);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(54);
      var3 = Py.newInteger(-1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(55);
      PyObject var9 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(55);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(70);
            var9 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(5, var4);
         var1.setline(56);
         PyInteger var5 = Py.newInteger(-1);
         var1.setlocal(6, var5);
         var1.setlocal(7, var5);
         var1.setline(57);
         PyObject var10 = var1.getglobal("ord").__call__(var2, var1.getlocal(5));
         var1.setlocal(8, var10);
         var5 = null;
         var1.setline(58);
         var10 = var1.getglobal("selective_len").__call__(var2, var1.getlocal(0), var1.getlocal(8));
         var1.setlocal(9, var10);
         var5 = null;
         var1.setline(59);
         var10 = var1.getlocal(9)._add(Py.newInteger(1))._mul(var1.getlocal(8)._sub(var1.getlocal(2)));
         var1.setlocal(10, var10);
         var5 = null;

         while(true) {
            var1.setline(60);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(61);
            var10 = var1.getglobal("selective_find").__call__(var2, var1.getlocal(0), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7));
            PyObject[] var6 = Py.unpackSequence(var10, 2);
            PyObject var7 = var6[0];
            var1.setlocal(6, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(7, var7);
            var7 = null;
            var5 = null;
            var1.setline(62);
            var10 = var1.getlocal(6);
            PyObject var10000 = var10._eq(Py.newInteger(-1));
            var5 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(64);
            var10 = var1.getlocal(10);
            var10 = var10._iadd(var1.getlocal(6)._sub(var1.getlocal(4)));
            var1.setlocal(10, var10);
            var1.setline(65);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(10)._sub(Py.newInteger(1)));
            var1.setline(66);
            var10 = var1.getlocal(6);
            var1.setlocal(4, var10);
            var5 = null;
            var1.setline(67);
            var5 = Py.newInteger(0);
            var1.setlocal(10, var5);
            var5 = null;
         }

         var1.setline(68);
         var10 = var1.getlocal(8);
         var1.setlocal(2, var10);
         var5 = null;
      }
   }

   public PyObject T$5(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = Py.newInteger(36)._mul(var1.getlocal(0)._add(Py.newInteger(1)))._sub(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(1));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(75);
         var5 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(76);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._gt(Py.newInteger(26));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(76);
            var5 = Py.newInteger(26);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(77);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject generate_generalized_integer$6(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("3.3 Generalized variable-length integers");
      var1.setline(82);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(83);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(3, var5);
      var3 = null;

      while(true) {
         var1.setline(84);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(85);
         PyObject var6 = var1.getglobal("T").__call__(var2, var1.getlocal(3), var1.getlocal(1));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(86);
         var6 = var1.getlocal(0);
         PyObject var10000 = var6._lt(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(87);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("digits").__getitem__(var1.getlocal(0)));
            var1.setline(88);
            var6 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(89);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("digits").__getitem__(var1.getlocal(4)._add(var1.getlocal(0)._sub(var1.getlocal(4))._mod(Py.newInteger(36)._sub(var1.getlocal(4))))));
         var1.setline(90);
         PyObject var4 = var1.getlocal(0)._sub(var1.getlocal(4))._floordiv(Py.newInteger(36)._sub(var1.getlocal(4)));
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(91);
         var4 = var1.getlocal(3);
         var4 = var4._iadd(Py.newInteger(1));
         var1.setlocal(3, var4);
      }
   }

   public PyObject adapt$7(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(95);
         var3 = var1.getlocal(0);
         var3 = var3._ifloordiv(Py.newInteger(700));
         var1.setlocal(0, var3);
      } else {
         var1.setline(97);
         var3 = var1.getlocal(0);
         var3 = var3._ifloordiv(Py.newInteger(2));
         var1.setlocal(0, var3);
      }

      var1.setline(98);
      var3 = var1.getlocal(0);
      var3 = var3._iadd(var1.getlocal(0)._floordiv(var1.getlocal(2)));
      var1.setlocal(0, var3);
      var1.setline(100);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(3, var4);
      var3 = null;

      while(true) {
         var1.setline(101);
         var3 = var1.getlocal(0);
         PyObject var10000 = var3._gt(Py.newInteger(455));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(104);
            var3 = var1.getlocal(3)._add(Py.newInteger(36)._mul(var1.getlocal(0))._floordiv(var1.getlocal(0)._add(Py.newInteger(38))));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(105);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(102);
         var3 = var1.getlocal(0)._floordiv(Py.newInteger(35));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(103);
         var3 = var1.getlocal(3);
         var3 = var3._iadd(Py.newInteger(36));
         var1.setlocal(3, var3);
      }
   }

   public PyObject generate_integers$8(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("3.4 Bias adaptation");
      var1.setline(111);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(112);
      PyInteger var7 = Py.newInteger(72);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(113);
      PyObject var8 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(113);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(117);
            var8 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(114);
         PyObject var9 = var1.getglobal("generate_generalized_integer").__call__(var2, var1.getlocal(5), var1.getlocal(3));
         var1.setlocal(6, var9);
         var5 = null;
         var1.setline(115);
         var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(6));
         var1.setline(116);
         PyObject var10000 = var1.getglobal("adapt");
         PyObject var10002 = var1.getlocal(5);
         var9 = var1.getlocal(4);
         PyObject var10003 = var9._eq(Py.newInteger(0));
         var5 = null;
         var9 = var10000.__call__(var2, var10002, var10003, var1.getlocal(0)._add(var1.getlocal(4))._add(Py.newInteger(1)));
         var1.setlocal(3, var9);
         var5 = null;
      }
   }

   public PyObject punycode_encode$9(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyObject var3 = var1.getglobal("segregate").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(121);
      var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getglobal("insertion_unsort").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getglobal("generate_integers").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)), var1.getlocal(3));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(124);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(125);
         var3 = var1.getlocal(1)._add(PyString.fromInterned("-"))._add(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(126);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject decode_generalized_number$10(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("3.3 Generalized variable-length integers");
      var1.setline(132);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(133);
      var3 = Py.newInteger(1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(134);
      var3 = Py.newInteger(0);
      var1.setlocal(6, var3);
      var3 = null;

      while(true) {
         var1.setline(135);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject var10000;
         PyTuple var7;
         PyObject var9;
         try {
            var1.setline(137);
            var9 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(1)));
            var1.setlocal(7, var9);
            var3 = null;
         } catch (Throwable var6) {
            PyException var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("IndexError"))) {
               var1.setline(139);
               PyObject var4 = var1.getlocal(3);
               var10000 = var4._eq(PyString.fromInterned("strict"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(140);
                  throw Py.makeException(var1.getglobal("UnicodeError"), PyString.fromInterned("incomplete punicode string"));
               }

               var1.setline(141);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(1)._add(Py.newInteger(1)), var1.getglobal("None")});
               var1.f_lasti = -1;
               return var7;
            }

            throw var8;
         }

         var1.setline(142);
         var9 = var1.getlocal(1);
         var9 = var9._iadd(Py.newInteger(1));
         var1.setlocal(1, var9);
         var1.setline(143);
         var3 = Py.newInteger(65);
         PyObject var10001 = var1.getlocal(7);
         PyInteger var10 = var3;
         var9 = var10001;
         PyObject var5;
         if ((var5 = var10._le(var10001)).__nonzero__()) {
            var5 = var9._le(Py.newInteger(90));
         }

         var3 = null;
         if (var5.__nonzero__()) {
            var1.setline(144);
            var9 = var1.getlocal(7)._sub(Py.newInteger(65));
            var1.setlocal(8, var9);
            var3 = null;
         } else {
            var1.setline(145);
            var3 = Py.newInteger(48);
            var10001 = var1.getlocal(7);
            var10 = var3;
            var9 = var10001;
            if ((var5 = var10._le(var10001)).__nonzero__()) {
               var5 = var9._le(Py.newInteger(57));
            }

            var3 = null;
            if (!var5.__nonzero__()) {
               var1.setline(147);
               var9 = var1.getlocal(3);
               var10000 = var9._eq(PyString.fromInterned("strict"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(148);
                  throw Py.makeException(var1.getglobal("UnicodeError").__call__(var2, PyString.fromInterned("Invalid extended code point '%s'")._mod(var1.getlocal(0).__getitem__(var1.getlocal(1)))));
               }

               var1.setline(151);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")});
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(146);
            var9 = var1.getlocal(7)._sub(Py.newInteger(22));
            var1.setlocal(8, var9);
            var3 = null;
         }

         var1.setline(152);
         var9 = var1.getglobal("T").__call__(var2, var1.getlocal(6), var1.getlocal(2));
         var1.setlocal(9, var9);
         var3 = null;
         var1.setline(153);
         var9 = var1.getlocal(4);
         var9 = var9._iadd(var1.getlocal(8)._mul(var1.getlocal(5)));
         var1.setlocal(4, var9);
         var1.setline(154);
         var9 = var1.getlocal(8);
         var10000 = var9._lt(var1.getlocal(9));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(155);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4)});
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(156);
         var9 = var1.getlocal(5)._mul(Py.newInteger(36)._sub(var1.getlocal(9)));
         var1.setlocal(5, var9);
         var3 = null;
         var1.setline(157);
         var9 = var1.getlocal(6);
         var9 = var9._iadd(Py.newInteger(1));
         var1.setlocal(6, var9);
      }
   }

   public PyObject insertion_sort$11(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("3.2 Insertion unsort coding");
      var1.setline(162);
      PyInteger var3 = Py.newInteger(128);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(163);
      var3 = Py.newInteger(-1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(164);
      var3 = Py.newInteger(72);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(165);
      var3 = Py.newInteger(0);
      var1.setlocal(6, var3);
      var3 = null;

      while(true) {
         var1.setline(166);
         PyObject var4 = var1.getlocal(6);
         PyObject var10000 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var4 = null;
         PyObject var7;
         if (!var10000.__nonzero__()) {
            var1.setline(183);
            var7 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(167);
         var7 = var1.getglobal("decode_generalized_number").__call__(var2, var1.getlocal(1), var1.getlocal(6), var1.getlocal(5), var1.getlocal(2));
         PyObject[] var6 = Py.unpackSequence(var7, 2);
         PyObject var5 = var6[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var6[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(169);
         var7 = var1.getlocal(8);
         var10000 = var7._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            var7 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(173);
         var4 = var1.getlocal(4);
         var4 = var4._iadd(var1.getlocal(8)._add(Py.newInteger(1)));
         var1.setlocal(4, var4);
         var1.setline(174);
         var4 = var1.getlocal(3);
         var4 = var4._iadd(var1.getlocal(4)._floordiv(var1.getglobal("len").__call__(var2, var1.getlocal(0))._add(Py.newInteger(1))));
         var1.setlocal(3, var4);
         var1.setline(175);
         var4 = var1.getlocal(3);
         var10000 = var4._gt(Py.newInteger(1114111));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(176);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("strict"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(177);
               throw Py.makeException(var1.getglobal("UnicodeError"), PyString.fromInterned("Invalid character U+%x")._mod(var1.getlocal(3)));
            }

            var1.setline(178);
            var4 = var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"));
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(179);
         var4 = var1.getlocal(4)._mod(var1.getglobal("len").__call__(var2, var1.getlocal(0))._add(Py.newInteger(1)));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(180);
         var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null)._add(var1.getglobal("unichr").__call__(var2, var1.getlocal(3)))._add(var1.getlocal(0).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(181);
         var10000 = var1.getglobal("adapt");
         PyObject var10002 = var1.getlocal(8);
         var4 = var1.getlocal(6);
         PyObject var10003 = var4._eq(Py.newInteger(0));
         var4 = null;
         var4 = var10000.__call__(var2, var10002, var10003, var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(182);
         var4 = var1.getlocal(7);
         var1.setlocal(6, var4);
         var4 = null;
      }
   }

   public PyObject punycode_decode$12(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(188);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(189);
         var3 = var1.getlocal(0);
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(191);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(192);
         var3 = var1.getlocal(0).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(193);
      var3 = var1.getglobal("unicode").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("ascii"), (PyObject)var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(194);
      var3 = var1.getlocal(4).__getattr__("upper").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(195);
      var3 = var1.getglobal("insertion_sort").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Codec$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(201);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$14, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(205);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$15, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$14(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getglobal("punycode_encode").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(203);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject decode$15(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("strict"), PyString.fromInterned("replace"), PyString.fromInterned("ignore")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(207);
         throw Py.makeException(var1.getglobal("UnicodeError"), PyString.fromInterned("Unsupported error handling ")._add(var1.getlocal(2)));
      } else {
         var1.setline(208);
         var3 = var1.getglobal("punycode_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(209);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject IncrementalEncoder$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(212);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$17, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$17(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getglobal("punycode_encode").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(216);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$19, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$19(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      PyObject var3 = var1.getlocal(0).__getattr__("errors");
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("strict"), PyString.fromInterned("replace"), PyString.fromInterned("ignore")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(218);
         throw Py.makeException(var1.getglobal("UnicodeError"), PyString.fromInterned("Unsupported error handling ")._add(var1.getlocal(0).__getattr__("errors")));
      } else {
         var1.setline(219);
         var3 = var1.getglobal("punycode_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject StreamWriter$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(222);
      return var1.getf_locals();
   }

   public PyObject StreamReader$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(225);
      return var1.getf_locals();
   }

   public PyObject getregentry$22(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("punycode"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamWriter"), var1.getglobal("StreamReader")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamwriter", "streamreader"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public punycode$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"str", "base", "extended", "c"};
      segregate$1 = Py.newCode(1, var2, var1, "segregate", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "max", "res", "c"};
      selective_len$2 = Py.newCode(2, var2, var1, "selective_len", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "char", "index", "pos", "l", "c"};
      selective_find$3 = Py.newCode(4, var2, var1, "selective_find", 32, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "extended", "oldchar", "result", "oldindex", "c", "index", "pos", "char", "curlen", "delta"};
      insertion_unsort$4 = Py.newCode(2, var2, var1, "insertion_unsort", 50, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"j", "bias", "res"};
      T$5 = Py.newCode(2, var2, var1, "T", 72, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"N", "bias", "result", "j", "t"};
      generate_generalized_integer$6 = Py.newCode(2, var2, var1, "generate_generalized_integer", 80, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"delta", "first", "numchars", "divisions", "bias"};
      adapt$7 = Py.newCode(3, var2, var1, "adapt", 93, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"baselen", "deltas", "result", "bias", "points", "delta", "s"};
      generate_integers$8 = Py.newCode(2, var2, var1, "generate_integers", 108, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "base", "extended", "deltas"};
      punycode_encode$9 = Py.newCode(1, var2, var1, "punycode_encode", 119, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"extended", "extpos", "bias", "errors", "result", "w", "j", "char", "digit", "t"};
      decode_generalized_number$10 = Py.newCode(4, var2, var1, "decode_generalized_number", 130, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base", "extended", "errors", "char", "pos", "bias", "extpos", "newpos", "delta"};
      insertion_sort$11 = Py.newCode(3, var2, var1, "insertion_sort", 160, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "errors", "pos", "base", "extended"};
      punycode_decode$12 = Py.newCode(2, var2, var1, "punycode_decode", 185, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$13 = Py.newCode(0, var2, var1, "Codec", 199, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors", "res"};
      encode$14 = Py.newCode(3, var2, var1, "encode", 201, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "res"};
      decode$15 = Py.newCode(3, var2, var1, "decode", 205, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$16 = Py.newCode(0, var2, var1, "IncrementalEncoder", 211, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$17 = Py.newCode(3, var2, var1, "encode", 212, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$18 = Py.newCode(0, var2, var1, "IncrementalDecoder", 215, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$19 = Py.newCode(3, var2, var1, "decode", 216, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$20 = Py.newCode(0, var2, var1, "StreamWriter", 221, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$21 = Py.newCode(0, var2, var1, "StreamReader", 224, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$22 = Py.newCode(0, var2, var1, "getregentry", 229, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new punycode$py("encodings/punycode$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(punycode$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.segregate$1(var2, var3);
         case 2:
            return this.selective_len$2(var2, var3);
         case 3:
            return this.selective_find$3(var2, var3);
         case 4:
            return this.insertion_unsort$4(var2, var3);
         case 5:
            return this.T$5(var2, var3);
         case 6:
            return this.generate_generalized_integer$6(var2, var3);
         case 7:
            return this.adapt$7(var2, var3);
         case 8:
            return this.generate_integers$8(var2, var3);
         case 9:
            return this.punycode_encode$9(var2, var3);
         case 10:
            return this.decode_generalized_number$10(var2, var3);
         case 11:
            return this.insertion_sort$11(var2, var3);
         case 12:
            return this.punycode_decode$12(var2, var3);
         case 13:
            return this.Codec$13(var2, var3);
         case 14:
            return this.encode$14(var2, var3);
         case 15:
            return this.decode$15(var2, var3);
         case 16:
            return this.IncrementalEncoder$16(var2, var3);
         case 17:
            return this.encode$17(var2, var3);
         case 18:
            return this.IncrementalDecoder$18(var2, var3);
         case 19:
            return this.decode$19(var2, var3);
         case 20:
            return this.StreamWriter$20(var2, var3);
         case 21:
            return this.StreamReader$21(var2, var3);
         case 22:
            return this.getregentry$22(var2, var3);
         default:
            return null;
      }
   }
}
