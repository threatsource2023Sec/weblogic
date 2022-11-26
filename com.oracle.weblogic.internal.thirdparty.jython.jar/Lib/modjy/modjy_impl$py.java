package modjy;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_impl.py")
public class modjy_impl$py extends PyFunctionTable implements PyRunnable {
   static modjy_impl$py self;
   static final PyCode f$0;
   static final PyCode modjy_impl$1;
   static final PyCode deal_with_app_return$2;
   static final PyCode init_impl$3;
   static final PyCode add_packages$4;
   static final PyCode add_classdirs$5;
   static final PyCode add_extdirs$6;
   static final PyCode do_j_env_params$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(22);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(24);
      imp.importAll("modjy_exceptions", var1, -1);
      var1.setline(26);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("modjy_impl", var5, modjy_impl$1);
      var1.setlocal("modjy_impl", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject modjy_impl$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(28);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, deal_with_app_return$2, (PyObject)null);
      var1.setlocal("deal_with_app_return", var4);
      var3 = null;
      var1.setline(74);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, init_impl$3, (PyObject)null);
      var1.setlocal("init_impl", var4);
      var3 = null;
      var1.setline(77);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_packages$4, (PyObject)null);
      var1.setlocal("add_packages", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_classdirs$5, (PyObject)null);
      var1.setlocal("add_classdirs", var4);
      var3 = null;
      var1.setline(89);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_extdirs$6, (PyObject)null);
      var1.setlocal("add_extdirs", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_j_env_params$7, (PyObject)null);
      var1.setlocal("do_j_env_params", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject deal_with_app_return$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Processing app return type: %s")._mod(var1.getglobal("str").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3)))));
      var1.setline(30);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("types").__getattr__("StringTypes")).__nonzero__()) {
         var1.setline(31);
         throw Py.makeException(var1.getglobal("ReturnNotIterable").__call__(var2, PyString.fromInterned("Application returned object that was not an iterable: %s")._mod(var1.getglobal("str").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3))))));
      } else {
         var1.setline(32);
         PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var3._is(var1.getglobal("types").__getattr__("FileType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(33);
         }

         var1.setline(34);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("__len__"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("callable").__call__(var2, var1.getlocal(3).__getattr__("__len__"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(35);
            var3 = var1.getlocal(3).__getattr__("__len__").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(37);
            PyInteger var9 = Py.newInteger(-1);
            var1.setlocal(4, var9);
            var3 = null;
         }

         var3 = null;

         try {
            PyException var4;
            PyObject var5;
            try {
               var1.setline(40);
               PyInteger var10 = Py.newInteger(0);
               var1.setlocal(5, var10);
               var4 = null;
               var1.setline(41);
               PyObject var11 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(41);
                  var5 = var11.__iternext__();
                  if (var5 != null) {
                     var1.setlocal(6, var5);
                     var1.setline(42);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("types").__getattr__("StringTypes")).__not__().__nonzero__()) {
                        var1.setline(43);
                        throw Py.makeException(var1.getglobal("NonStringOutput").__call__(var2, PyString.fromInterned("Application returned iterable containing non-strings: %s")._mod(var1.getglobal("str").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(6))))));
                     }

                     var1.setline(44);
                     PyObject var6 = var1.getlocal(5);
                     var10000 = var6._eq(Py.newInteger(0));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(46);
                        if (var1.getlocal(2).__getattr__("called").__not__().__nonzero__()) {
                           var1.setline(47);
                           throw Py.makeException(var1.getglobal("StartResponseNotCalled").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Start_response callable was never called.")));
                        }

                        var1.setline(48);
                        var10000 = var1.getlocal(2).__getattr__("content_length").__not__();
                        if (var10000.__nonzero__()) {
                           var6 = var1.getlocal(4);
                           var10000 = var6._eq(Py.newInteger(1));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var6 = var1.getlocal(2).__getattr__("write_callable").__getattr__("num_writes");
                              var10000 = var6._eq(Py.newInteger(0));
                              var6 = null;
                           }
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(52);
                           var1.getlocal(2).__getattr__("set_content_length").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(6)));
                        }
                     }

                     var1.setline(53);
                     var1.getlocal(2).__getattr__("write_callable").__call__(var2, var1.getlocal(6));
                     var1.setline(54);
                     var6 = var1.getlocal(5);
                     var6 = var6._iadd(Py.newInteger(1));
                     var1.setlocal(5, var6);
                     var1.setline(55);
                     var6 = var1.getlocal(5);
                     var10000 = var6._eq(var1.getlocal(4));
                     var6 = null;
                     if (!var10000.__nonzero__()) {
                        continue;
                     }
                  }

                  var1.setline(57);
                  var11 = var1.getlocal(4);
                  var10000 = var11._ne(Py.newInteger(-1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var11 = var1.getlocal(5);
                     var10000 = var11._ne(var1.getlocal(4));
                     var4 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(58);
                  throw Py.makeException(var1.getglobal("WrongLength").__call__(var2, PyString.fromInterned("Iterator len() was wrong. Expected %d pieces: got %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}))));
               }
            } catch (Throwable var7) {
               var4 = Py.setException(var7, var1);
               if (var4.match(var1.getglobal("AttributeError"))) {
                  var5 = var4.value;
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(60);
                  var5 = var1.getglobal("str").__call__(var2, var1.getlocal(7));
                  var10000 = var5._eq(PyString.fromInterned("__getitem__"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(61);
                     throw Py.makeException(var1.getglobal("ReturnNotIterable").__call__(var2, PyString.fromInterned("Application returned object that was not an iterable: %s")._mod(var1.getglobal("str").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3))))));
                  }

                  var1.setline(63);
                  throw Py.makeException(var1.getlocal(7));
               }

               if (var4.match(var1.getglobal("TypeError"))) {
                  var5 = var4.value;
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(65);
                  throw Py.makeException(var1.getglobal("ReturnNotIterable").__call__(var2, PyString.fromInterned("Application returned object that was not an iterable: %s")._mod(var1.getglobal("str").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3))))));
               }

               if (var4.match(var1.getglobal("ModjyException"))) {
                  var5 = var4.value;
                  var1.setlocal(9, var5);
                  var5 = null;
                  var1.setline(67);
                  throw Py.makeException(var1.getlocal(9));
               }

               if (var4.match(var1.getglobal("Exception"))) {
                  var5 = var4.value;
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(69);
                  throw Py.makeException(var1.getglobal("ApplicationException").__call__(var2, var1.getlocal(10)));
               }

               throw var4;
            }
         } catch (Throwable var8) {
            Py.addTraceback(var8, var1);
            var1.setline(71);
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("close"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("callable").__call__(var2, var1.getlocal(3).__getattr__("close"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(72);
               var1.getlocal(3).__getattr__("close").__call__(var2);
            }

            throw (Throwable)var8;
         }

         var1.setline(71);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("close"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("callable").__call__(var2, var1.getlocal(3).__getattr__("close"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(72);
            var1.getlocal(3).__getattr__("close").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject init_impl$3(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      var1.getlocal(0).__getattr__("do_j_env_params").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_packages$4(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

      while(true) {
         var1.setline(78);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(78);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(79);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(79);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(80);
               var1.getlocal(0).__getattr__("log").__getattr__("info").__call__(var2, PyString.fromInterned("Adding java package %s to jython")._mod(var1.getlocal(4)));
               var1.setline(81);
               var1.getglobal("sys").__getattr__("add_package").__call__(var2, var1.getlocal(4));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(78);
         var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("strip").__call__(var2));
      }
   }

   public PyObject add_classdirs$5(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(84);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

      while(true) {
         var1.setline(84);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(84);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(85);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(85);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(86);
               var1.getlocal(0).__getattr__("log").__getattr__("info").__call__(var2, PyString.fromInterned("Adding directory %s to jython class file search path")._mod(var1.getlocal(4)));
               var1.setline(87);
               var1.getglobal("sys").__getattr__("add_classdir").__call__(var2, var1.getlocal(4));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(84);
         var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("strip").__call__(var2));
      }
   }

   public PyObject add_extdirs$6(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

      while(true) {
         var1.setline(90);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(90);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(91);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(91);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(92);
               var1.getlocal(0).__getattr__("log").__getattr__("info").__call__(var2, PyString.fromInterned("Adding directory %s for .jars and .zips search path")._mod(var1.getlocal(4)));
               var1.setline(93);
               var1.getglobal("sys").__getattr__("add_extdir").__call__(var2, var1.getlocal(0).__getattr__("expand_relative_path").__call__(var2, var1.getlocal(4)));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(90);
         var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("strip").__call__(var2));
      }
   }

   public PyObject do_j_env_params$7(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("packages")).__nonzero__()) {
         var1.setline(97);
         var1.getlocal(0).__getattr__("add_packages").__call__(var2, var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("packages")));
      }

      var1.setline(98);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("classdirs")).__nonzero__()) {
         var1.setline(99);
         var1.getlocal(0).__getattr__("add_classdirs").__call__(var2, var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("classdirs")));
      }

      var1.setline(100);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("extdirs")).__nonzero__()) {
         var1.setline(101);
         var1.getlocal(0).__getattr__("add_extdirs").__call__(var2, var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("extdirs")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public modjy_impl$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_impl$1 = Py.newCode(0, var2, var1, "modjy_impl", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "environ", "start_response_callable", "app_return", "expected_pieces", "ix", "next_piece", "ax", "tx", "mx", "x"};
      deal_with_app_return$2 = Py.newCode(4, var2, var1, "deal_with_app_return", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      init_impl$3 = Py.newCode(1, var2, var1, "init_impl", 74, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package_list", "packages", "_[78_20]", "p"};
      add_packages$4 = Py.newCode(2, var2, var1, "add_packages", 77, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "classdir_list", "classdirs", "_[84_21]", "cd"};
      add_classdirs$5 = Py.newCode(2, var2, var1, "add_classdirs", 83, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "extdir_list", "extdirs", "_[90_19]", "ed"};
      add_extdirs$6 = Py.newCode(2, var2, var1, "add_extdirs", 89, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      do_j_env_params$7 = Py.newCode(1, var2, var1, "do_j_env_params", 95, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_impl$py("modjy/modjy_impl$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_impl$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_impl$1(var2, var3);
         case 2:
            return this.deal_with_app_return$2(var2, var3);
         case 3:
            return this.init_impl$3(var2, var3);
         case 4:
            return this.add_packages$4(var2, var3);
         case 5:
            return this.add_classdirs$5(var2, var3);
         case 6:
            return this.add_extdirs$6(var2, var3);
         case 7:
            return this.do_j_env_params$7(var2, var3);
         default:
            return null;
      }
   }
}
