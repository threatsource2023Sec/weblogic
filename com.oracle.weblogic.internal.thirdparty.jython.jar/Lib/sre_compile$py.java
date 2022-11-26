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
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("sre_compile.py")
public class sre_compile$py extends PyFunctionTable implements PyRunnable {
   static sre_compile$py self;
   static final PyCode f$0;
   static final PyCode _identityfunction$1;
   static final PyCode set$2;
   static final PyCode _compile$3;
   static final PyCode fixup$4;
   static final PyCode _compile_charset$5;
   static final PyCode _optimize_charset$6;
   static final PyCode _mk_bitmap$7;
   static final PyCode _optimize_unicode$8;
   static final PyCode _simple$9;
   static final PyCode _compile_info$10;
   static final PyCode isstring$11;
   static final PyCode _code$12;
   static final PyCode compile$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Internal support module for sre"));
      var1.setline(11);
      PyString.fromInterned("Internal support module for sre");
      var1.setline(13);
      PyObject var3 = imp.importOne("_sre", var1, -1);
      var1.setlocal("_sre", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(15);
      imp.importAll("sre_constants", var1, -1);
      var1.setline(17);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getname("_sre").__getattr__("MAGIC");
         var10000 = var3._eq(var1.getname("MAGIC"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("SRE module mismatch"));
         }
      }

      var1.setline(19);
      var3 = var1.getname("_sre").__getattr__("CODESIZE");
      var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         PyInteger var6 = Py.newInteger(65535);
         var1.setlocal("MAXCODE", var6);
         var3 = null;
      } else {
         var1.setline(22);
         PyLong var7 = Py.newLong("4294967295");
         var1.setlocal("MAXCODE", var7);
         var3 = null;
      }

      var1.setline(24);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, _identityfunction$1, (PyObject)null);
      var1.setlocal("_identityfunction", var9);
      var3 = null;
      var1.setline(27);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, set$2, (PyObject)null);
      var1.setlocal("set", var9);
      var3 = null;
      var1.setline(33);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("LITERAL"), var1.getname("NOT_LITERAL")})));
      var1.setlocal("_LITERAL_CODES", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("REPEAT"), var1.getname("MIN_REPEAT"), var1.getname("MAX_REPEAT")})));
      var1.setlocal("_REPEATING_CODES", var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("SUCCESS"), var1.getname("FAILURE")})));
      var1.setlocal("_SUCCESS_CODES", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("ASSERT"), var1.getname("ASSERT_NOT")})));
      var1.setlocal("_ASSERT_CODES", var3);
      var3 = null;
      var1.setline(38);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _compile$3, (PyObject)null);
      var1.setlocal("_compile", var9);
      var3 = null;
      var1.setline(184);
      var8 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var8, _compile_charset$5, (PyObject)null);
      var1.setlocal("_compile_charset", var9);
      var3 = null;
      var1.setline(213);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _optimize_charset$6, (PyObject)null);
      var1.setlocal("_optimize_charset", var9);
      var3 = null;
      var1.setline(264);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _mk_bitmap$7, (PyObject)null);
      var1.setlocal("_mk_bitmap", var9);
      var3 = null;
      var1.setline(307);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _optimize_unicode$8, (PyObject)null);
      var1.setlocal("_optimize_unicode", var9);
      var3 = null;
      var1.setline(365);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _simple$9, (PyObject)null);
      var1.setlocal("_simple", var9);
      var3 = null;
      var1.setline(372);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _compile_info$10, (PyObject)null);
      var1.setlocal("_compile_info", var9);
      var3 = null;

      label28: {
         PyTuple var4;
         try {
            var1.setline(479);
            var1.getname("unicode");
         } catch (Throwable var5) {
            PyException var10 = Py.setException(var5, var1);
            if (var10.match(var1.getname("NameError"))) {
               var1.setline(481);
               var4 = new PyTuple(new PyObject[]{var1.getname("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))});
               var1.setlocal("STRING_TYPES", var4);
               var4 = null;
               break label28;
            }

            throw var10;
         }

         var1.setline(483);
         var4 = new PyTuple(new PyObject[]{var1.getname("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), var1.getname("type").__call__(var2, var1.getname("unicode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")))});
         var1.setlocal("STRING_TYPES", var4);
         var4 = null;
      }

      var1.setline(485);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, isstring$11, (PyObject)null);
      var1.setlocal("isstring", var9);
      var3 = null;
      var1.setline(491);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _code$12, (PyObject)null);
      var1.setlocal("_code", var9);
      var3 = null;
      var1.setline(506);
      var8 = new PyObject[]{Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var8, compile$13, (PyObject)null);
      var1.setlocal("compile", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _identityfunction$1(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(29);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(29);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(31);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(30);
         PyInteger var5 = Py.newInteger(1);
         var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject _compile$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getglobal("len");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getglobal("_LITERAL_CODES");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getglobal("_REPEATING_CODES");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getglobal("_SUCCESS_CODES");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getglobal("_ASSERT_CODES");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         label153:
         while(true) {
            var1.setline(46);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(47);
            PyObject var8 = var1.getlocal(9);
            PyObject var10000 = var8._in(var1.getlocal(5));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(48);
               if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_IGNORECASE")).__nonzero__()) {
                  var1.setline(49);
                  var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("OP_IGNORE").__getitem__(var1.getlocal(9))));
                  var1.setline(50);
                  var1.getlocal(3).__call__(var2, var1.getglobal("_sre").__getattr__("getlower").__call__(var2, var1.getlocal(10), var1.getlocal(2)));
               } else {
                  var1.setline(52);
                  var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                  var1.setline(53);
                  var1.getlocal(3).__call__(var2, var1.getlocal(10));
               }
            } else {
               var1.setline(54);
               var8 = var1.getlocal(9);
               var10000 = var8._eq(var1.getglobal("IN"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(55);
                  if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_IGNORECASE")).__nonzero__()) {
                     var1.setline(56);
                     var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("OP_IGNORE").__getitem__(var1.getlocal(9))));
                     var1.setline(57);
                     var5 = new PyObject[]{var1.getlocal(2)};
                     PyFunction var11 = new PyFunction(var1.f_globals, var5, fixup$4, (PyObject)null);
                     var1.setlocal(11, var11);
                     var5 = null;
                  } else {
                     var1.setline(60);
                     var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                     var1.setline(61);
                     var8 = var1.getglobal("_identityfunction");
                     var1.setlocal(11, var8);
                     var5 = null;
                  }

                  var1.setline(62);
                  var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                  var1.setlocal(12, var8);
                  var5 = null;
                  var1.setline(62);
                  var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setline(63);
                  var1.getglobal("_compile_charset").__call__(var2, var1.getlocal(10), var1.getlocal(2), var1.getlocal(0), var1.getlocal(11));
                  var1.setline(64);
                  var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(12));
                  var1.getlocal(0).__setitem__(var1.getlocal(12), var8);
                  var5 = null;
               } else {
                  var1.setline(65);
                  var8 = var1.getlocal(9);
                  var10000 = var8._eq(var1.getglobal("ANY"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(66);
                     if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_DOTALL")).__nonzero__()) {
                        var1.setline(67);
                        var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("ANY_ALL")));
                     } else {
                        var1.setline(69);
                        var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("ANY")));
                     }
                  } else {
                     var1.setline(70);
                     var8 = var1.getlocal(9);
                     var10000 = var8._in(var1.getlocal(6));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(71);
                        if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_TEMPLATE")).__nonzero__()) {
                           var1.setline(72);
                           throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("internal: unsupported template operator"));
                        }

                        var1.setline(80);
                        var10000 = var1.getglobal("_simple").__call__(var2, var1.getlocal(10));
                        if (var10000.__nonzero__()) {
                           var8 = var1.getlocal(9);
                           var10000 = var8._ne(var1.getglobal("REPEAT"));
                           var5 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(81);
                           var8 = var1.getlocal(9);
                           var10000 = var8._eq(var1.getglobal("MAX_REPEAT"));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(82);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("REPEAT_ONE")));
                           } else {
                              var1.setline(84);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("MIN_REPEAT_ONE")));
                           }

                           var1.setline(85);
                           var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                           var1.setlocal(12, var8);
                           var5 = null;
                           var1.setline(85);
                           var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                           var1.setline(86);
                           var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(0)));
                           var1.setline(87);
                           var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(1)));
                           var1.setline(88);
                           var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10).__getitem__(Py.newInteger(2)), var1.getlocal(2));
                           var1.setline(89);
                           var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("SUCCESS")));
                           var1.setline(90);
                           var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(12));
                           var1.getlocal(0).__setitem__(var1.getlocal(12), var8);
                           var5 = null;
                        } else {
                           var1.setline(92);
                           var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("REPEAT")));
                           var1.setline(93);
                           var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                           var1.setlocal(12, var8);
                           var5 = null;
                           var1.setline(93);
                           var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                           var1.setline(94);
                           var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(0)));
                           var1.setline(95);
                           var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(1)));
                           var1.setline(96);
                           var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10).__getitem__(Py.newInteger(2)), var1.getlocal(2));
                           var1.setline(97);
                           var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(12));
                           var1.getlocal(0).__setitem__(var1.getlocal(12), var8);
                           var5 = null;
                           var1.setline(98);
                           var8 = var1.getlocal(9);
                           var10000 = var8._eq(var1.getglobal("MAX_REPEAT"));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(99);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("MAX_UNTIL")));
                           } else {
                              var1.setline(101);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("MIN_UNTIL")));
                           }
                        }
                     } else {
                        var1.setline(102);
                        var8 = var1.getlocal(9);
                        var10000 = var8._eq(var1.getglobal("SUBPATTERN"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(103);
                           if (var1.getlocal(10).__getitem__(Py.newInteger(0)).__nonzero__()) {
                              var1.setline(104);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("MARK")));
                              var1.setline(105);
                              var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(0))._sub(Py.newInteger(1))._mul(Py.newInteger(2)));
                           }

                           var1.setline(107);
                           var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10).__getitem__(Py.newInteger(1)), var1.getlocal(2));
                           var1.setline(108);
                           if (var1.getlocal(10).__getitem__(Py.newInteger(0)).__nonzero__()) {
                              var1.setline(109);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("MARK")));
                              var1.setline(110);
                              var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(0))._sub(Py.newInteger(1))._mul(Py.newInteger(2))._add(Py.newInteger(1)));
                           }
                        } else {
                           var1.setline(111);
                           var8 = var1.getlocal(9);
                           var10000 = var8._in(var1.getlocal(7));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(112);
                              var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                           } else {
                              var1.setline(113);
                              var8 = var1.getlocal(9);
                              var10000 = var8._in(var1.getlocal(8));
                              var5 = null;
                              PyObject var7;
                              if (var10000.__nonzero__()) {
                                 var1.setline(114);
                                 var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                 var1.setline(115);
                                 var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                                 var1.setlocal(12, var8);
                                 var5 = null;
                                 var1.setline(115);
                                 var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                 var1.setline(116);
                                 var8 = var1.getlocal(10).__getitem__(Py.newInteger(0));
                                 var10000 = var8._ge(Py.newInteger(0));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(117);
                                    var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                 } else {
                                    var1.setline(119);
                                    var8 = var1.getlocal(10).__getitem__(Py.newInteger(1)).__getattr__("getwidth").__call__(var2);
                                    PyObject[] var9 = Py.unpackSequence(var8, 2);
                                    var7 = var9[0];
                                    var1.setlocal(13, var7);
                                    var7 = null;
                                    var7 = var9[1];
                                    var1.setlocal(14, var7);
                                    var7 = null;
                                    var5 = null;
                                    var1.setline(120);
                                    var8 = var1.getlocal(13);
                                    var10000 = var8._ne(var1.getlocal(14));
                                    var5 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(121);
                                       throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("look-behind requires fixed-width pattern"));
                                    }

                                    var1.setline(122);
                                    var1.getlocal(3).__call__(var2, var1.getlocal(13));
                                 }

                                 var1.setline(123);
                                 var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10).__getitem__(Py.newInteger(1)), var1.getlocal(2));
                                 var1.setline(124);
                                 var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("SUCCESS")));
                                 var1.setline(125);
                                 var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(12));
                                 var1.getlocal(0).__setitem__(var1.getlocal(12), var8);
                                 var5 = null;
                              } else {
                                 var1.setline(126);
                                 var8 = var1.getlocal(9);
                                 var10000 = var8._eq(var1.getglobal("CALL"));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(127);
                                    var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                    var1.setline(128);
                                    var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                                    var1.setlocal(12, var8);
                                    var5 = null;
                                    var1.setline(128);
                                    var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                    var1.setline(129);
                                    var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10), var1.getlocal(2));
                                    var1.setline(130);
                                    var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("SUCCESS")));
                                    var1.setline(131);
                                    var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(12));
                                    var1.getlocal(0).__setitem__(var1.getlocal(12), var8);
                                    var5 = null;
                                 } else {
                                    var1.setline(132);
                                    var8 = var1.getlocal(9);
                                    var10000 = var8._eq(var1.getglobal("AT"));
                                    var5 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(133);
                                       var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                       var1.setline(134);
                                       if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_MULTILINE")).__nonzero__()) {
                                          var1.setline(135);
                                          var8 = var1.getglobal("AT_MULTILINE").__getattr__("get").__call__(var2, var1.getlocal(10), var1.getlocal(10));
                                          var1.setlocal(10, var8);
                                          var5 = null;
                                       }

                                       var1.setline(136);
                                       if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_LOCALE")).__nonzero__()) {
                                          var1.setline(137);
                                          var8 = var1.getglobal("AT_LOCALE").__getattr__("get").__call__(var2, var1.getlocal(10), var1.getlocal(10));
                                          var1.setlocal(10, var8);
                                          var5 = null;
                                       } else {
                                          var1.setline(138);
                                          if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_UNICODE")).__nonzero__()) {
                                             var1.setline(139);
                                             var8 = var1.getglobal("AT_UNICODE").__getattr__("get").__call__(var2, var1.getlocal(10), var1.getlocal(10));
                                             var1.setlocal(10, var8);
                                             var5 = null;
                                          }
                                       }

                                       var1.setline(140);
                                       var1.getlocal(3).__call__(var2, var1.getglobal("ATCODES").__getitem__(var1.getlocal(10)));
                                    } else {
                                       var1.setline(141);
                                       var8 = var1.getlocal(9);
                                       var10000 = var8._eq(var1.getglobal("BRANCH"));
                                       var5 = null;
                                       if (!var10000.__nonzero__()) {
                                          var1.setline(155);
                                          var8 = var1.getlocal(9);
                                          var10000 = var8._eq(var1.getglobal("CATEGORY"));
                                          var5 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(156);
                                             var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                             var1.setline(157);
                                             if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_LOCALE")).__nonzero__()) {
                                                var1.setline(158);
                                                var8 = var1.getglobal("CH_LOCALE").__getitem__(var1.getlocal(10));
                                                var1.setlocal(10, var8);
                                                var5 = null;
                                             } else {
                                                var1.setline(159);
                                                if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_UNICODE")).__nonzero__()) {
                                                   var1.setline(160);
                                                   var8 = var1.getglobal("CH_UNICODE").__getitem__(var1.getlocal(10));
                                                   var1.setlocal(10, var8);
                                                   var5 = null;
                                                }
                                             }

                                             var1.setline(161);
                                             var1.getlocal(3).__call__(var2, var1.getglobal("CHCODES").__getitem__(var1.getlocal(10)));
                                          } else {
                                             var1.setline(162);
                                             var8 = var1.getlocal(9);
                                             var10000 = var8._eq(var1.getglobal("GROUPREF"));
                                             var5 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(163);
                                                if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_IGNORECASE")).__nonzero__()) {
                                                   var1.setline(164);
                                                   var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("OP_IGNORE").__getitem__(var1.getlocal(9))));
                                                } else {
                                                   var1.setline(166);
                                                   var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                                }

                                                var1.setline(167);
                                                var1.getlocal(3).__call__(var2, var1.getlocal(10)._sub(Py.newInteger(1)));
                                             } else {
                                                var1.setline(168);
                                                var8 = var1.getlocal(9);
                                                var10000 = var8._eq(var1.getglobal("GROUPREF_EXISTS"));
                                                var5 = null;
                                                if (!var10000.__nonzero__()) {
                                                   var1.setline(182);
                                                   throw Py.makeException(var1.getglobal("ValueError"), new PyTuple(new PyObject[]{PyString.fromInterned("unsupported operand type"), var1.getlocal(9)}));
                                                }

                                                var1.setline(169);
                                                var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                                var1.setline(170);
                                                var1.getlocal(3).__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(0))._sub(Py.newInteger(1)));
                                                var1.setline(171);
                                                var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                                                var1.setlocal(17, var8);
                                                var5 = null;
                                                var1.setline(171);
                                                var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                                var1.setline(172);
                                                var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10).__getitem__(Py.newInteger(1)), var1.getlocal(2));
                                                var1.setline(173);
                                                if (var1.getlocal(10).__getitem__(Py.newInteger(2)).__nonzero__()) {
                                                   var1.setline(174);
                                                   var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("JUMP")));
                                                   var1.setline(175);
                                                   var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                                                   var1.setlocal(18, var8);
                                                   var5 = null;
                                                   var1.setline(175);
                                                   var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                                   var1.setline(176);
                                                   var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(17))._add(Py.newInteger(1));
                                                   var1.getlocal(0).__setitem__(var1.getlocal(17), var8);
                                                   var5 = null;
                                                   var1.setline(177);
                                                   var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10).__getitem__(Py.newInteger(2)), var1.getlocal(2));
                                                   var1.setline(178);
                                                   var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(18));
                                                   var1.getlocal(0).__setitem__(var1.getlocal(18), var8);
                                                   var5 = null;
                                                } else {
                                                   var1.setline(180);
                                                   var8 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(17))._add(Py.newInteger(1));
                                                   var1.getlocal(0).__setitem__(var1.getlocal(17), var8);
                                                   var5 = null;
                                                }
                                             }
                                          }
                                       } else {
                                          var1.setline(142);
                                          var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(9)));
                                          var1.setline(143);
                                          PyList var10 = new PyList(Py.EmptyObjects);
                                          var1.setlocal(15, var10);
                                          var5 = null;
                                          var1.setline(144);
                                          var8 = var1.getlocal(15).__getattr__("append");
                                          var1.setlocal(16, var8);
                                          var5 = null;
                                          var1.setline(145);
                                          var8 = var1.getlocal(10).__getitem__(Py.newInteger(1)).__iter__();

                                          while(true) {
                                             var1.setline(145);
                                             var6 = var8.__iternext__();
                                             if (var6 == null) {
                                                var1.setline(152);
                                                var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                                var1.setline(153);
                                                var8 = var1.getlocal(15).__iter__();

                                                while(true) {
                                                   var1.setline(153);
                                                   var6 = var8.__iternext__();
                                                   if (var6 == null) {
                                                      continue label153;
                                                   }

                                                   var1.setlocal(15, var6);
                                                   var1.setline(154);
                                                   var7 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(15));
                                                   var1.getlocal(0).__setitem__(var1.getlocal(15), var7);
                                                   var7 = null;
                                                }
                                             }

                                             var1.setlocal(10, var6);
                                             var1.setline(146);
                                             var7 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
                                             var1.setlocal(12, var7);
                                             var7 = null;
                                             var1.setline(146);
                                             var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                             var1.setline(148);
                                             var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(10), var1.getlocal(2));
                                             var1.setline(149);
                                             var1.getlocal(3).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("JUMP")));
                                             var1.setline(150);
                                             var1.getlocal(16).__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(0)));
                                             var1.setline(150);
                                             var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                                             var1.setline(151);
                                             var7 = var1.getlocal(4).__call__(var2, var1.getlocal(0))._sub(var1.getlocal(12));
                                             var1.getlocal(0).__setitem__(var1.getlocal(12), var7);
                                             var7 = null;
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject fixup$4(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getglobal("_sre").__getattr__("getlower").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _compile_charset$5(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(2).__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(188);
         var3 = var1.getglobal("_identityfunction");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(189);
      var3 = var1.getglobal("_optimize_charset").__call__(var2, var1.getlocal(0), var1.getlocal(3)).__iter__();

      while(true) {
         var1.setline(189);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(211);
            var1.getlocal(4).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("FAILURE")));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(190);
         var1.getlocal(4).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getlocal(5)));
         var1.setline(191);
         PyObject var7 = var1.getlocal(5);
         var10000 = var7._eq(var1.getglobal("NEGATE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(192);
         } else {
            var1.setline(193);
            var7 = var1.getlocal(5);
            var10000 = var7._eq(var1.getglobal("LITERAL"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(194);
               var1.getlocal(4).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(6)));
            } else {
               var1.setline(195);
               var7 = var1.getlocal(5);
               var10000 = var7._eq(var1.getglobal("RANGE"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(196);
                  var1.getlocal(4).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0))));
                  var1.setline(197);
                  var1.getlocal(4).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(1))));
               } else {
                  var1.setline(198);
                  var7 = var1.getlocal(5);
                  var10000 = var7._eq(var1.getglobal("CHARSET"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(199);
                     var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(6));
                  } else {
                     var1.setline(200);
                     var7 = var1.getlocal(5);
                     var10000 = var7._eq(var1.getglobal("BIGCHARSET"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(201);
                        var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(6));
                     } else {
                        var1.setline(202);
                        var7 = var1.getlocal(5);
                        var10000 = var7._eq(var1.getglobal("CATEGORY"));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(210);
                           throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("internal: unsupported set operator"));
                        }

                        var1.setline(203);
                        if (var1.getlocal(1)._and(var1.getglobal("SRE_FLAG_LOCALE")).__nonzero__()) {
                           var1.setline(204);
                           var1.getlocal(4).__call__(var2, var1.getglobal("CHCODES").__getitem__(var1.getglobal("CH_LOCALE").__getitem__(var1.getlocal(6))));
                        } else {
                           var1.setline(205);
                           if (var1.getlocal(1)._and(var1.getglobal("SRE_FLAG_UNICODE")).__nonzero__()) {
                              var1.setline(206);
                              var1.getlocal(4).__call__(var2, var1.getglobal("CHCODES").__getitem__(var1.getglobal("CH_UNICODE").__getitem__(var1.getlocal(6))));
                           } else {
                              var1.setline(208);
                              var1.getlocal(4).__call__(var2, var1.getglobal("CHCODES").__getitem__(var1.getlocal(6)));
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _optimize_charset$6(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(216);
      PyObject var9 = var1.getlocal(2).__getattr__("append");
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(217);
      var9 = (new PyList(new PyObject[]{Py.newInteger(0)}))._mul(Py.newInteger(256));
      var1.setlocal(4, var9);
      var3 = null;

      PyObject var4;
      PyObject var5;
      PyObject var6;
      PyInteger var7;
      PyObject var10000;
      try {
         var1.setline(219);
         var9 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(219);
            var4 = var9.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var11 = Py.unpackSequence(var4, 2);
            var6 = var11[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(220);
            var5 = var1.getlocal(5);
            var10000 = var5._eq(var1.getglobal("NEGATE"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(221);
               var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
            } else {
               var1.setline(222);
               var5 = var1.getlocal(5);
               var10000 = var5._eq(var1.getglobal("LITERAL"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(223);
                  PyInteger var14 = Py.newInteger(1);
                  var1.getlocal(4).__setitem__((PyObject)var1.getlocal(1).__call__(var2, var1.getlocal(6)), var14);
                  var5 = null;
               } else {
                  var1.setline(224);
                  var5 = var1.getlocal(5);
                  var10000 = var5._eq(var1.getglobal("RANGE"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(225);
                     var5 = var1.getglobal("range").__call__(var2, var1.getlocal(1).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0))), var1.getlocal(1).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(1)))._add(Py.newInteger(1))).__iter__();

                     while(true) {
                        var1.setline(225);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           break;
                        }

                        var1.setlocal(7, var6);
                        var1.setline(226);
                        var7 = Py.newInteger(1);
                        var1.getlocal(4).__setitem__((PyObject)var1.getlocal(7), var7);
                        var7 = null;
                     }
                  } else {
                     var1.setline(227);
                     var5 = var1.getlocal(5);
                     var10000 = var5._eq(var1.getglobal("CATEGORY"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(229);
                        var5 = var1.getlocal(0);
                        var1.f_lasti = -1;
                        return var5;
                     }
                  }
               }
            }
         }
      } catch (Throwable var8) {
         PyException var10 = Py.setException(var8, var1);
         if (var10.match(var1.getglobal("IndexError"))) {
            var1.setline(232);
            var5 = var1.getglobal("_optimize_unicode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var5;
         }

         throw var10;
      }

      var1.setline(234);
      PyInteger var12 = Py.newInteger(0);
      var1.setlocal(7, var12);
      var1.setlocal(8, var12);
      var1.setlocal(9, var12);
      var1.setline(235);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(236);
      var9 = var1.getlocal(10).__getattr__("append");
      var1.setlocal(11, var9);
      var3 = null;
      var1.setline(237);
      var9 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(237);
         var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(246);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(247);
               var1.getlocal(11).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)})));
            }

            var1.setline(248);
            var9 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
            var10000 = var9._le(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(250);
               var9 = var1.getlocal(10).__iter__();

               while(true) {
                  var1.setline(250);
                  var4 = var9.__iternext__();
                  if (var4 == null) {
                     var1.setline(255);
                     var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                     var10000 = var9._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(256);
                        var5 = var1.getlocal(2);
                        var1.f_lasti = -1;
                        return var5;
                     }

                     var1.setline(262);
                     var5 = var1.getlocal(0);
                     var1.f_lasti = -1;
                     return var5;
                  }

                  PyObject[] var16 = Py.unpackSequence(var4, 2);
                  PyObject var13 = var16[0];
                  var1.setlocal(8, var13);
                  var7 = null;
                  var13 = var16[1];
                  var1.setlocal(9, var13);
                  var7 = null;
                  var1.setline(251);
                  var6 = var1.getlocal(9);
                  var10000 = var6._eq(Py.newInteger(1));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(252);
                     var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getlocal(8)})));
                  } else {
                     var1.setline(254);
                     var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("RANGE"), new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(8)._add(var1.getlocal(9))._sub(Py.newInteger(1))})})));
                  }
               }
            }

            var1.setline(259);
            var9 = var1.getglobal("_mk_bitmap").__call__(var2, var1.getlocal(4));
            var1.setlocal(13, var9);
            var3 = null;
            var1.setline(260);
            var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("CHARSET"), var1.getlocal(13)})));
            var1.setline(261);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(12, var4);
         var1.setline(238);
         if (var1.getlocal(12).__nonzero__()) {
            var1.setline(239);
            var6 = var1.getlocal(9);
            var10000 = var6._eq(Py.newInteger(0));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(240);
               var6 = var1.getlocal(7);
               var1.setlocal(8, var6);
               var6 = null;
            }

            var1.setline(241);
            var6 = var1.getlocal(9)._add(Py.newInteger(1));
            var1.setlocal(9, var6);
            var6 = null;
         } else {
            var1.setline(242);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(243);
               var1.getlocal(11).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)})));
               var1.setline(244);
               PyInteger var15 = Py.newInteger(0);
               var1.setlocal(9, var15);
               var6 = null;
            }
         }

         var1.setline(245);
         var6 = var1.getlocal(7)._add(Py.newInteger(1));
         var1.setlocal(7, var6);
         var6 = null;
      }
   }

   public PyObject _mk_bitmap$7(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(266);
      PyObject var8 = var1.getlocal(1).__getattr__("append");
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(267);
      var8 = var1.getglobal("_sre").__getattr__("CODESIZE");
      PyObject var10000 = var8._eq(Py.newInteger(2));
      var3 = null;
      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(268);
         var10 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0)});
         var1.setlocal(3, var10);
         var3 = null;
      } else {
         var1.setline(270);
         var10 = new PyTuple(new PyObject[]{Py.newLong("1"), Py.newLong("0")});
         var1.setlocal(3, var10);
         var3 = null;
      }

      var1.setline(271);
      var8 = var1.getlocal(3);
      PyObject[] var4 = Py.unpackSequence(var8, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(272);
      var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(272);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            var1.setline(279);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(6, var9);
         var1.setline(273);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(274);
            var5 = var1.getlocal(5)._add(var1.getlocal(4));
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(275);
         var5 = var1.getlocal(4)._add(var1.getlocal(4));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(276);
         var5 = var1.getlocal(4);
         var10000 = var5._gt(var1.getglobal("MAXCODE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(277);
            var1.getlocal(2).__call__(var2, var1.getlocal(5));
            var1.setline(278);
            var5 = var1.getlocal(3);
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
         }
      }
   }

   public PyObject _optimize_unicode$8(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _simple$9(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(2)).__getattr__("getwidth").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(368);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("MAXREPEAT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(369);
         throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("nothing to repeat"));
      } else {
         var1.setline(370);
         var3 = var1.getlocal(1);
         PyObject var10001 = var1.getlocal(2);
         var10000 = var3;
         var3 = var10001;
         PyObject var6;
         if ((var6 = var10000._eq(var10001)).__nonzero__()) {
            var6 = var3._eq(Py.newInteger(1));
         }

         var10000 = var6;
         var3 = null;
         if (var6.__nonzero__()) {
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
            var10000 = var3._ne(var1.getglobal("SUBPATTERN"));
            var3 = null;
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _compile_info$10(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      PyObject var3 = var1.getlocal(1).__getattr__("getwidth").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(377);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(378);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(380);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var8);
         var3 = null;
         var1.setline(381);
         var3 = var1.getlocal(5).__getattr__("append");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(382);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(7, var10);
         var3 = null;
         var1.setline(383);
         var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var8);
         var3 = null;
         var1.setline(384);
         var3 = var1.getlocal(8).__getattr__("append");
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(385);
         PyObject var9;
         if (var1.getlocal(2)._and(var1.getglobal("SRE_FLAG_IGNORECASE")).__not__().__nonzero__()) {
            var1.setline(387);
            var3 = var1.getlocal(1).__getattr__("data").__iter__();

            PyObject var7;
            PyObject[] var12;
            while(true) {
               var1.setline(387);
               var9 = var3.__iternext__();
               if (var9 == null) {
                  break;
               }

               PyObject[] var11 = Py.unpackSequence(var9, 2);
               PyObject var6 = var11[0];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var11[1];
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(388);
               var5 = var1.getlocal(10);
               var10000 = var5._eq(var1.getglobal("LITERAL"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(389);
                  var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
                  var10000 = var5._eq(var1.getlocal(7));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(390);
                     var5 = var1.getlocal(7)._add(Py.newInteger(1));
                     var1.setlocal(7, var5);
                     var5 = null;
                  }

                  var1.setline(391);
                  var1.getlocal(6).__call__(var2, var1.getlocal(11));
               } else {
                  var1.setline(392);
                  var5 = var1.getlocal(10);
                  var10000 = var5._eq(var1.getglobal("SUBPATTERN"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var5 = var1.getglobal("len").__call__(var2, var1.getlocal(11).__getitem__(Py.newInteger(1)));
                     var10000 = var5._eq(Py.newInteger(1));
                     var5 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(393);
                  var5 = var1.getlocal(11).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                  var12 = Py.unpackSequence(var5, 2);
                  var7 = var12[0];
                  var1.setlocal(10, var7);
                  var7 = null;
                  var7 = var12[1];
                  var1.setlocal(11, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(394);
                  var5 = var1.getlocal(10);
                  var10000 = var5._eq(var1.getglobal("LITERAL"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(395);
                  var1.getlocal(6).__call__(var2, var1.getlocal(11));
               }
            }

            var1.setline(401);
            var10000 = var1.getlocal(5).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("data");
            }

            if (var10000.__nonzero__()) {
               var1.setline(402);
               var3 = var1.getlocal(1).__getattr__("data").__getitem__(Py.newInteger(0));
               var4 = Py.unpackSequence(var3, 2);
               var5 = var4[0];
               var1.setlocal(10, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(11, var5);
               var5 = null;
               var3 = null;
               var1.setline(403);
               var3 = var1.getlocal(10);
               var10000 = var3._eq(var1.getglobal("SUBPATTERN"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(11).__getitem__(Py.newInteger(1));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(404);
                  var3 = var1.getlocal(11).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(10, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(11, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(405);
                  var3 = var1.getlocal(10);
                  var10000 = var3._eq(var1.getglobal("LITERAL"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(406);
                     var1.getlocal(9).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(11)})));
                  } else {
                     var1.setline(407);
                     var3 = var1.getlocal(10);
                     var10000 = var3._eq(var1.getglobal("BRANCH"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(408);
                        var8 = new PyList(Py.EmptyObjects);
                        var1.setlocal(12, var8);
                        var3 = null;
                        var1.setline(409);
                        var3 = var1.getlocal(12).__getattr__("append");
                        var1.setlocal(13, var3);
                        var3 = null;
                        var1.setline(410);
                        var3 = var1.getlocal(11).__getitem__(Py.newInteger(1)).__iter__();

                        while(true) {
                           var1.setline(410);
                           var9 = var3.__iternext__();
                           if (var9 == null) {
                              var1.setline(419);
                              var5 = var1.getlocal(12);
                              var1.setlocal(8, var5);
                              var5 = null;
                              break;
                           }

                           var1.setlocal(14, var9);
                           var1.setline(411);
                           if (var1.getlocal(14).__not__().__nonzero__()) {
                              break;
                           }

                           var1.setline(413);
                           var5 = var1.getlocal(14).__getitem__(Py.newInteger(0));
                           var12 = Py.unpackSequence(var5, 2);
                           var7 = var12[0];
                           var1.setlocal(10, var7);
                           var7 = null;
                           var7 = var12[1];
                           var1.setlocal(11, var7);
                           var7 = null;
                           var5 = null;
                           var1.setline(414);
                           var5 = var1.getlocal(10);
                           var10000 = var5._eq(var1.getglobal("LITERAL"));
                           var5 = null;
                           if (!var10000.__nonzero__()) {
                              break;
                           }

                           var1.setline(415);
                           var1.getlocal(13).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(11)})));
                        }
                     }
                  }
               } else {
                  var1.setline(420);
                  var3 = var1.getlocal(10);
                  var10000 = var3._eq(var1.getglobal("BRANCH"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(421);
                     var8 = new PyList(Py.EmptyObjects);
                     var1.setlocal(12, var8);
                     var3 = null;
                     var1.setline(422);
                     var3 = var1.getlocal(12).__getattr__("append");
                     var1.setlocal(13, var3);
                     var3 = null;
                     var1.setline(423);
                     var3 = var1.getlocal(11).__getitem__(Py.newInteger(1)).__iter__();

                     while(true) {
                        var1.setline(423);
                        var9 = var3.__iternext__();
                        if (var9 == null) {
                           var1.setline(432);
                           var5 = var1.getlocal(12);
                           var1.setlocal(8, var5);
                           var5 = null;
                           break;
                        }

                        var1.setlocal(14, var9);
                        var1.setline(424);
                        if (var1.getlocal(14).__not__().__nonzero__()) {
                           break;
                        }

                        var1.setline(426);
                        var5 = var1.getlocal(14).__getitem__(Py.newInteger(0));
                        var12 = Py.unpackSequence(var5, 2);
                        var7 = var12[0];
                        var1.setlocal(10, var7);
                        var7 = null;
                        var7 = var12[1];
                        var1.setlocal(11, var7);
                        var7 = null;
                        var5 = null;
                        var1.setline(427);
                        var5 = var1.getlocal(10);
                        var10000 = var5._eq(var1.getglobal("LITERAL"));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(428);
                        var1.getlocal(13).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(11)})));
                     }
                  } else {
                     var1.setline(433);
                     var3 = var1.getlocal(10);
                     var10000 = var3._eq(var1.getglobal("IN"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(434);
                        var3 = var1.getlocal(11);
                        var1.setlocal(8, var3);
                        var3 = null;
                     }
                  }
               }
            }
         }

         var1.setline(440);
         var3 = var1.getlocal(0).__getattr__("append");
         var1.setlocal(15, var3);
         var3 = null;
         var1.setline(441);
         var1.getlocal(15).__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("INFO")));
         var1.setline(442);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var1.setlocal(16, var3);
         var3 = null;
         var1.setline(442);
         var1.getlocal(15).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(444);
         var10 = Py.newInteger(0);
         var1.setlocal(17, var10);
         var3 = null;
         var1.setline(445);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(446);
            var3 = var1.getglobal("SRE_INFO_PREFIX");
            var1.setlocal(17, var3);
            var3 = null;
            var1.setline(447);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            PyObject var10001 = var1.getlocal(7);
            var10000 = var3;
            var3 = var10001;
            if ((var9 = var10000._eq(var10001)).__nonzero__()) {
               var9 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("data")));
            }

            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(448);
               var3 = var1.getlocal(17)._add(var1.getglobal("SRE_INFO_LITERAL"));
               var1.setlocal(17, var3);
               var3 = null;
            }
         } else {
            var1.setline(449);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(450);
               var3 = var1.getlocal(17)._add(var1.getglobal("SRE_INFO_CHARSET"));
               var1.setlocal(17, var3);
               var3 = null;
            }
         }

         var1.setline(451);
         var1.getlocal(15).__call__(var2, var1.getlocal(17));
         var1.setline(453);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getglobal("MAXCODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(454);
            var1.getlocal(15).__call__(var2, var1.getlocal(3));
         } else {
            var1.setline(456);
            var1.getlocal(15).__call__(var2, var1.getglobal("MAXCODE"));
            var1.setline(457);
            var3 = var1.getlocal(5).__getslice__((PyObject)null, var1.getglobal("MAXCODE"), (PyObject)null);
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(458);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(var1.getglobal("MAXCODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(459);
            var1.getlocal(15).__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(461);
            var1.getlocal(15).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         }

         var1.setline(463);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(464);
            var1.getlocal(15).__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(5)));
            var1.setline(465);
            var1.getlocal(15).__call__(var2, var1.getlocal(7));
            var1.setline(466);
            var1.getlocal(0).__getattr__("extend").__call__(var2, var1.getlocal(5));
            var1.setline(468);
            var3 = (new PyList(new PyObject[]{Py.newInteger(-1)}))._add((new PyList(new PyObject[]{Py.newInteger(0)}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(5))));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(469);
            var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(5))).__iter__();

            while(true) {
               var1.setline(469);
               var9 = var3.__iternext__();
               if (var9 == null) {
                  var1.setline(473);
                  var1.getlocal(0).__getattr__("extend").__call__(var2, var1.getlocal(18).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                  break;
               }

               var1.setlocal(19, var9);
               var1.setline(470);
               var5 = var1.getlocal(18).__getitem__(var1.getlocal(19))._add(Py.newInteger(1));
               var1.getlocal(18).__setitem__(var1.getlocal(19)._add(Py.newInteger(1)), var5);
               var5 = null;

               while(true) {
                  var1.setline(471);
                  var5 = var1.getlocal(18).__getitem__(var1.getlocal(19)._add(Py.newInteger(1)));
                  var10000 = var5._gt(Py.newInteger(0));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(5).__getitem__(var1.getlocal(19));
                     var10000 = var5._ne(var1.getlocal(5).__getitem__(var1.getlocal(18).__getitem__(var1.getlocal(19)._add(Py.newInteger(1)))._sub(Py.newInteger(1))));
                     var5 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(472);
                  var5 = var1.getlocal(18).__getitem__(var1.getlocal(18).__getitem__(var1.getlocal(19)._add(Py.newInteger(1)))._sub(Py.newInteger(1)))._add(Py.newInteger(1));
                  var1.getlocal(18).__setitem__(var1.getlocal(19)._add(Py.newInteger(1)), var5);
                  var5 = null;
               }
            }
         } else {
            var1.setline(474);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(475);
               var1.getglobal("_compile_charset").__call__(var2, var1.getlocal(8), var1.getlocal(2), var1.getlocal(0));
            }
         }

         var1.setline(476);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0))._sub(var1.getlocal(16));
         var1.getlocal(0).__setitem__(var1.getlocal(16), var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject isstring$11(PyFrame var1, ThreadState var2) {
      var1.setline(486);
      PyObject var3 = var1.getglobal("STRING_TYPES").__iter__();

      PyInteger var5;
      do {
         var1.setline(486);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(489);
            var5 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(1, var4);
         var1.setline(487);
      } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__nonzero__());

      var1.setline(488);
      var5 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _code$12(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var3 = var1.getlocal(0).__getattr__("pattern").__getattr__("flags")._or(var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(494);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(497);
      var1.getglobal("_compile_info").__call__(var2, var1.getlocal(2), var1.getlocal(0), var1.getlocal(1));
      var1.setline(500);
      var1.getglobal("_compile").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("data"), var1.getlocal(1));
      var1.setline(502);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("OPCODES").__getitem__(var1.getglobal("SUCCESS")));
      var1.setline(504);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compile$13(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyObject var3;
      if (var1.getglobal("isstring").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(510);
         var3 = imp.importOne("sre_parse", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(511);
         var3 = var1.getlocal(0);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(512);
         var3 = var1.getlocal(2).__getattr__("parse").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(0, var3);
         var3 = null;
      } else {
         var1.setline(514);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(516);
      var3 = var1.getglobal("_code").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(521);
      var3 = var1.getlocal(0).__getattr__("pattern").__getattr__("groups");
      PyObject var10000 = var3._gt(Py.newInteger(100));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(522);
         throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sorry, but this version only supports 100 named groups")));
      } else {
         var1.setline(527);
         var3 = var1.getlocal(0).__getattr__("pattern").__getattr__("groupdict");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(528);
         var3 = (new PyList(new PyObject[]{var1.getglobal("None")}))._mul(var1.getlocal(0).__getattr__("pattern").__getattr__("groups"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(529);
         var3 = var1.getlocal(5).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(529);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(532);
               var10000 = var1.getglobal("_sre").__getattr__("compile");
               PyObject[] var8 = new PyObject[]{var1.getlocal(3), var1.getlocal(1)._or(var1.getlocal(0).__getattr__("pattern").__getattr__("flags")), var1.getlocal(4), var1.getlocal(0).__getattr__("pattern").__getattr__("groups")._sub(Py.newInteger(1)), var1.getlocal(5), var1.getlocal(6)};
               var3 = var10000.__call__(var2, var8);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(530);
            PyObject var7 = var1.getlocal(7);
            var1.getlocal(6).__setitem__(var1.getlocal(8), var7);
            var5 = null;
         }
      }
   }

   public sre_compile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x"};
      _identityfunction$1 = Py.newCode(1, var2, var1, "_identityfunction", 24, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"seq", "s", "elem"};
      set$2 = Py.newCode(1, var2, var1, "set", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "pattern", "flags", "emit", "_len", "LITERAL_CODES", "REPEATING_CODES", "SUCCESS_CODES", "ASSERT_CODES", "op", "av", "fixup", "skip", "lo", "hi", "tail", "tailappend", "skipyes", "skipno"};
      _compile$3 = Py.newCode(3, var2, var1, "_compile", 38, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"literal", "flags"};
      fixup$4 = Py.newCode(2, var2, var1, "fixup", 57, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"charset", "flags", "code", "fixup", "emit", "op", "av"};
      _compile_charset$5 = Py.newCode(4, var2, var1, "_compile_charset", 184, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"charset", "fixup", "out", "outappend", "charmap", "op", "av", "i", "p", "n", "runs", "runsappend", "c", "data"};
      _optimize_charset$6 = Py.newCode(2, var2, var1, "_optimize_charset", 213, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"bits", "data", "dataappend", "start", "m", "v", "c"};
      _mk_bitmap$7 = Py.newCode(1, var2, var1, "_mk_bitmap", 264, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"charset", "fixup", "array", "charmap", "negate", "op", "av", "i", "comps", "mapping", "block", "data", "chunk", "new", "header", "code"};
      _optimize_unicode$8 = Py.newCode(2, var2, var1, "_optimize_unicode", 307, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"av", "lo", "hi"};
      _simple$9 = Py.newCode(1, var2, var1, "_simple", 365, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "pattern", "flags", "lo", "hi", "prefix", "prefixappend", "prefix_skip", "charset", "charsetappend", "op", "av", "c", "cappend", "p", "emit", "skip", "mask", "table", "i"};
      _compile_info$10 = Py.newCode(3, var2, var1, "_compile_info", 372, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "tp"};
      isstring$11 = Py.newCode(1, var2, var1, "isstring", 485, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "flags", "code"};
      _code$12 = Py.newCode(2, var2, var1, "_code", 491, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "flags", "sre_parse", "pattern", "code", "groupindex", "indexgroup", "k", "i"};
      compile$13 = Py.newCode(2, var2, var1, "compile", 506, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sre_compile$py("sre_compile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sre_compile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._identityfunction$1(var2, var3);
         case 2:
            return this.set$2(var2, var3);
         case 3:
            return this._compile$3(var2, var3);
         case 4:
            return this.fixup$4(var2, var3);
         case 5:
            return this._compile_charset$5(var2, var3);
         case 6:
            return this._optimize_charset$6(var2, var3);
         case 7:
            return this._mk_bitmap$7(var2, var3);
         case 8:
            return this._optimize_unicode$8(var2, var3);
         case 9:
            return this._simple$9(var2, var3);
         case 10:
            return this._compile_info$10(var2, var3);
         case 11:
            return this.isstring$11(var2, var3);
         case 12:
            return this._code$12(var2, var3);
         case 13:
            return this.compile$13(var2, var3);
         default:
            return null;
      }
   }
}
