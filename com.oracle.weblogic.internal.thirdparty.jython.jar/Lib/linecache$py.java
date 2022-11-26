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
@Filename("linecache.py")
public class linecache$py extends PyFunctionTable implements PyRunnable {
   static linecache$py self;
   static final PyCode f$0;
   static final PyCode getline$1;
   static final PyCode clearcache$2;
   static final PyCode getlines$3;
   static final PyCode checkcache$4;
   static final PyCode updatecache$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Cache lines from files.\n\nThis is intended to read lines from modules imported -- hence if a filename\nis not found, it will look down the module search path for a file by\nthat name.\n"));
      var1.setline(6);
      PyString.fromInterned("Cache lines from files.\n\nThis is intended to read lines from modules imported -- hence if a filename\nis not found, it will look down the module search path for a file by\nthat name.\n");
      var1.setline(8);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(11);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("getline"), PyString.fromInterned("clearcache"), PyString.fromInterned("checkcache")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(13);
      PyObject[] var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getline$1, (PyObject)null);
      var1.setlocal("getline", var6);
      var3 = null;
      var1.setline(23);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("cache", var7);
      var3 = null;
      var1.setline(26);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, clearcache$2, PyString.fromInterned("Clear the cache entirely."));
      var1.setlocal("clearcache", var6);
      var3 = null;
      var1.setline(33);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, getlines$3, PyString.fromInterned("Get the lines for a file from the cache.\n    Update the cache if it doesn't contain an entry for this file already."));
      var1.setlocal("getlines", var6);
      var3 = null;
      var1.setline(43);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, checkcache$4, PyString.fromInterned("Discard cache entries that are out of date.\n    (This is not checked upon each call!)"));
      var1.setlocal("checkcache", var6);
      var3 = null;
      var1.setline(68);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, updatecache$5, PyString.fromInterned("Update a cache entry and return its list of lines.\n    If something's wrong, print a message, discard the cache entry,\n    and return an empty list."));
      var1.setlocal("updatecache", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getline$1(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyObject var3 = var1.getglobal("getlines").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(15);
      PyInteger var5 = Py.newInteger(1);
      PyObject var10001 = var1.getlocal(1);
      PyInteger var10000 = var5;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      }

      var3 = null;
      if (var4.__nonzero__()) {
         var1.setline(16);
         var3 = var1.getlocal(3).__getitem__(var1.getlocal(1)._sub(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(18);
         PyString var6 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject clearcache$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Clear the cache entirely.");
      var1.setline(30);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setglobal("cache", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getlines$3(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyString.fromInterned("Get the lines for a file from the cache.\n    Update the cache if it doesn't contain an entry for this file already.");
      var1.setline(37);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(38);
         var3 = var1.getglobal("cache").__getitem__(var1.getlocal(0)).__getitem__(Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(40);
         var3 = var1.getglobal("updatecache").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject checkcache$4(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Discard cache entries that are out of date.\n    (This is not checked upon each call!)");
      var1.setline(47);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(48);
         var3 = var1.getglobal("cache").__getattr__("keys").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(50);
         var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getglobal("cache"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(53);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(51);
         PyList var9 = new PyList(new PyObject[]{var1.getlocal(0)});
         var1.setlocal(1, var9);
         var3 = null;
      }

      var1.setline(55);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         PyObject var5;
         while(true) {
            do {
               var1.setline(55);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(0, var4);
               var1.setline(56);
               var5 = var1.getglobal("cache").__getitem__(var1.getlocal(0));
               PyObject[] var6 = Py.unpackSequence(var5, 4);
               PyObject var7 = var6[0];
               var1.setlocal(2, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(3, var7);
               var7 = null;
               var7 = var6[2];
               var1.setlocal(4, var7);
               var7 = null;
               var7 = var6[3];
               var1.setlocal(5, var7);
               var7 = null;
               var5 = null;
               var1.setline(57);
               var5 = var1.getlocal(3);
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
            } while(var10000.__nonzero__());

            try {
               var1.setline(60);
               var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var5);
               var5 = null;
               break;
            } catch (Throwable var8) {
               PyException var10 = Py.setException(var8, var1);
               if (!var10.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var10;
               }

               var1.setline(62);
               var1.getglobal("cache").__delitem__(var1.getlocal(0));
            }
         }

         var1.setline(64);
         var5 = var1.getlocal(2);
         var10000 = var5._ne(var1.getlocal(6).__getattr__("st_size"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(3);
            var10000 = var5._ne(var1.getlocal(6).__getattr__("st_mtime"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(65);
            var1.getglobal("cache").__delitem__(var1.getlocal(0));
         }
      }
   }

   public PyObject updatecache$5(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(71);
      PyString.fromInterned("Update a cache entry and return its list of lines.\n    If something's wrong, print a message, discard the cache entry,\n    and return an empty list.");
      var1.setline(73);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(74);
         var1.getglobal("cache").__delitem__(var1.getlocal(0));
      }

      var1.setline(75);
      var10000 = var1.getlocal(0).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
         }
      }

      PyList var14;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         var14 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var14;
      } else {
         var1.setline(78);
         PyObject var4 = var1.getlocal(0);
         var1.setlocal(2, var4);
         var4 = null;

         PyObject var5;
         PyObject var6;
         PyException var15;
         try {
            var1.setline(80);
            var4 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
         } catch (Throwable var13) {
            label134: {
               var15 = Py.setException(var13, var1);
               if (var15.match(var1.getglobal("OSError"))) {
                  var1.setline(82);
                  var5 = var1.getlocal(0);
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(85);
                  var10000 = var1.getlocal(1);
                  if (var10000.__nonzero__()) {
                     PyString var16 = PyString.fromInterned("__loader__");
                     var10000 = var16._in(var1.getlocal(1));
                     var5 = null;
                  }

                  PyObject var20;
                  if (var10000.__nonzero__()) {
                     var1.setline(86);
                     var5 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__name__"));
                     var1.setlocal(5, var5);
                     var5 = null;
                     var1.setline(87);
                     var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("__loader__"));
                     var1.setlocal(6, var5);
                     var5 = null;
                     var1.setline(88);
                     var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)PyString.fromInterned("get_source"), (PyObject)var1.getglobal("None"));
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(90);
                     var10000 = var1.getlocal(5);
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(7);
                     }

                     if (var10000.__nonzero__()) {
                        label151: {
                           try {
                              var1.setline(92);
                              var5 = var1.getlocal(7).__call__(var2, var1.getlocal(5));
                              var1.setlocal(8, var5);
                              var5 = null;
                           } catch (Throwable var12) {
                              PyException var22 = Py.setException(var12, var1);
                              if (var22.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("IOError")}))) {
                                 var1.setline(94);
                                 break label151;
                              }

                              throw var22;
                           }

                           var1.setline(96);
                           var6 = var1.getlocal(8);
                           var10000 = var6._is(var1.getglobal("None"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(99);
                              var14 = new PyList(Py.EmptyObjects);
                              var1.f_lasti = -1;
                              return var14;
                           }

                           var1.setline(100);
                           PyTuple var24 = new PyTuple;
                           PyObject[] var10002 = new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(8)), var1.getglobal("None"), null, null};
                           PyList var10005 = new PyList();
                           var6 = var10005.__getattr__("append");
                           var1.setlocal(9, var6);
                           var6 = null;
                           var1.setline(102);
                           var6 = var1.getlocal(8).__getattr__("splitlines").__call__(var2).__iter__();

                           while(true) {
                              var1.setline(102);
                              var20 = var6.__iternext__();
                              if (var20 == null) {
                                 var1.setline(102);
                                 var1.dellocal(9);
                                 var10002[2] = var10005;
                                 var10002[3] = var1.getlocal(2);
                                 var24.<init>(var10002);
                                 PyTuple var21 = var24;
                                 var1.getglobal("cache").__setitem__((PyObject)var1.getlocal(0), var21);
                                 var6 = null;
                                 var1.setline(104);
                                 var3 = var1.getglobal("cache").__getitem__(var1.getlocal(0)).__getitem__(Py.newInteger(2));
                                 var1.f_lasti = -1;
                                 return var3;
                              }

                              var1.setlocal(10, var20);
                              var1.setline(102);
                              var1.getlocal(9).__call__(var2, var1.getlocal(10)._add(PyString.fromInterned("\n")));
                           }
                        }
                     }
                  }

                  var1.setline(108);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(109);
                     var14 = new PyList(Py.EmptyObjects);
                     var1.f_lasti = -1;
                     return var14;
                  }

                  var1.setline(111);
                  var5 = var1.getglobal("sys").__getattr__("path").__iter__();

                  while(true) {
                     var1.setline(111);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        var1.setline(125);
                        var14 = new PyList(Py.EmptyObjects);
                        var1.f_lasti = -1;
                        return var14;
                     }

                     var1.setlocal(11, var6);

                     PyException var7;
                     try {
                        var1.setline(115);
                        var20 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(11), var1.getlocal(4));
                        var1.setlocal(2, var20);
                        var7 = null;
                     } catch (Throwable var10) {
                        var7 = Py.setException(var10, var1);
                        if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
                           continue;
                        }

                        throw var7;
                     }

                     try {
                        var1.setline(120);
                        var20 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(2));
                        var1.setlocal(3, var20);
                        var7 = null;
                        break label134;
                     } catch (Throwable var11) {
                        var7 = Py.setException(var11, var1);
                        if (!var7.match(var1.getglobal("os").__getattr__("error"))) {
                           throw var7;
                        }

                        var1.setline(123);
                     }
                  }
               }

               throw var15;
            }
         }

         try {
            label147: {
               ContextManager var17;
               var5 = (var17 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("rU")))).__enter__(var2);

               try {
                  var1.setlocal(12, var5);
                  var1.setline(128);
                  var5 = var1.getlocal(12).__getattr__("readlines").__call__(var2);
                  var1.setlocal(13, var5);
                  var5 = null;
               } catch (Throwable var8) {
                  if (var17.__exit__(var2, Py.setException(var8, var1))) {
                     break label147;
                  }

                  throw (Throwable)Py.makeException();
               }

               var17.__exit__(var2, (PyException)null);
            }
         } catch (Throwable var9) {
            var15 = Py.setException(var9, var1);
            if (var15.match(var1.getglobal("IOError"))) {
               var1.setline(130);
               var14 = new PyList(Py.EmptyObjects);
               var1.f_lasti = -1;
               return var14;
            }

            throw var15;
         }

         var1.setline(131);
         var10000 = var1.getlocal(13);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(13).__getitem__(Py.newInteger(-1)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(132);
            var10000 = var1.getlocal(13);
            PyInteger var18 = Py.newInteger(-1);
            var5 = var10000;
            var6 = var5.__getitem__(var18);
            var6 = var6._iadd(PyString.fromInterned("\n"));
            var5.__setitem__((PyObject)var18, var6);
         }

         var1.setline(133);
         PyTuple var19 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("st_size"), var1.getlocal(3).__getattr__("st_mtime")});
         PyObject[] var23 = Py.unpackSequence(var19, 2);
         var6 = var23[0];
         var1.setlocal(14, var6);
         var6 = null;
         var6 = var23[1];
         var1.setlocal(15, var6);
         var6 = null;
         var4 = null;
         var1.setline(134);
         var19 = new PyTuple(new PyObject[]{var1.getlocal(14), var1.getlocal(15), var1.getlocal(13), var1.getlocal(2)});
         var1.getglobal("cache").__setitem__((PyObject)var1.getlocal(0), var19);
         var4 = null;
         var1.setline(135);
         var3 = var1.getlocal(13);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public linecache$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "lineno", "module_globals", "lines"};
      getline$1 = Py.newCode(3, var2, var1, "getline", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      clearcache$2 = Py.newCode(0, var2, var1, "clearcache", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "module_globals"};
      getlines$3 = Py.newCode(2, var2, var1, "getlines", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "filenames", "size", "mtime", "lines", "fullname", "stat"};
      checkcache$4 = Py.newCode(1, var2, var1, "checkcache", 43, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "module_globals", "fullname", "stat", "basename", "name", "loader", "get_source", "data", "_[102_25]", "line", "dirname", "fp", "lines", "size", "mtime"};
      updatecache$5 = Py.newCode(2, var2, var1, "updatecache", 68, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new linecache$py("linecache$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(linecache$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.getline$1(var2, var3);
         case 2:
            return this.clearcache$2(var2, var3);
         case 3:
            return this.getlines$3(var2, var3);
         case 4:
            return this.checkcache$4(var2, var3);
         case 5:
            return this.updatecache$5(var2, var3);
         default:
            return null;
      }
   }
}
