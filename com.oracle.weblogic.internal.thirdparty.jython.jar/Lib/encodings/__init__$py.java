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
@MTime(1498849384000L)
@Filename("encodings/__init__.py")
public class encodings$py extends PyFunctionTable implements PyRunnable {
   static encodings$py self;
   static final PyCode f$0;
   static final PyCode CodecRegistryError$1;
   static final PyCode normalize_encoding$2;
   static final PyCode search_function$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Standard \"encodings\" Package\n\n    Standard Python encoding modules are stored in this package\n    directory.\n\n    Codec modules must have names corresponding to normalized encoding\n    names as defined in the normalize_encoding() function below, e.g.\n    'utf-8' must be implemented by the module 'utf_8.py'.\n\n    Each codec module must export the following interface:\n\n    * getregentry() -> codecs.CodecInfo object\n    The getregentry() API must a CodecInfo object with encoder, decoder,\n    incrementalencoder, incrementaldecoder, streamwriter and streamreader\n    atttributes which adhere to the Python Codec Interface Standard.\n\n    In addition, a module may optionally also define the following\n    APIs which are then used by the package's codec search function:\n\n    * getaliases() -> sequence of encoding name strings to use as aliases\n\n    Alias names returned by getaliases() must be normalized encoding\n    names as defined by normalize_encoding().\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n"));
      var1.setline(29);
      PyString.fromInterned(" Standard \"encodings\" Package\n\n    Standard Python encoding modules are stored in this package\n    directory.\n\n    Codec modules must have names corresponding to normalized encoding\n    names as defined in the normalize_encoding() function below, e.g.\n    'utf-8' must be implemented by the module 'utf_8.py'.\n\n    Each codec module must export the following interface:\n\n    * getregentry() -> codecs.CodecInfo object\n    The getregentry() API must a CodecInfo object with encoder, decoder,\n    incrementalencoder, incrementaldecoder, streamwriter and streamreader\n    atttributes which adhere to the Python Codec Interface Standard.\n\n    In addition, a module may optionally also define the following\n    APIs which are then used by the package's codec search function:\n\n    * getaliases() -> sequence of encoding name strings to use as aliases\n\n    Alias names returned by getaliases() must be normalized encoding\n    names as defined by normalize_encoding().\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n");
      var1.setline(31);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(32);
      String[] var5 = new String[]{"aliases", "_java"};
      PyObject[] var6 = imp.importFrom("encodings", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("aliases", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("_java", var4);
      var4 = null;
      var1.setline(33);
      var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var1.setline(35);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_cache", var7);
      var3 = null;
      var1.setline(36);
      PyString var8 = PyString.fromInterned("--unknown--");
      var1.setlocal("_unknown", var8);
      var3 = null;
      var1.setline(37);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("*")});
      var1.setlocal("_import_tail", var9);
      var3 = null;
      var1.setline(38);
      var8 = PyString.fromInterned("                                              . 0123456789       ABCDEFGHIJKLMNOPQRSTUVWXYZ      abcdefghijklmnopqrstuvwxyz                                                                                                                                     ");
      var1.setlocal("_norm_encoding_map", var8);
      var3 = null;
      var1.setline(44);
      var3 = var1.getname("aliases").__getattr__("aliases");
      var1.setlocal("_aliases", var3);
      var3 = null;
      var1.setline(46);
      var6 = new PyObject[]{var1.getname("LookupError"), var1.getname("SystemError")};
      var4 = Py.makeClass("CodecRegistryError", var6, CodecRegistryError$1);
      var1.setlocal("CodecRegistryError", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(49);
      var6 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var6, normalize_encoding$2, PyString.fromInterned(" Normalize an encoding name.\n\n        Normalization works as follows: all non-alphanumeric\n        characters except the dot used for Python package names are\n        collapsed and replaced with a single underscore, e.g. '  -;#'\n        becomes '_'. Leading and trailing underscores are removed.\n\n        Note that encoding names should be ASCII only; if they do use\n        non-ASCII characters, these must be Latin-1 compatible.\n\n    "));
      var1.setlocal("normalize_encoding", var10);
      var3 = null;
      var1.setline(71);
      var6 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var6, search_function$3, (PyObject)null);
      var1.setlocal("search_function", var10);
      var3 = null;
      var1.setline(167);
      var1.getname("codecs").__getattr__("register").__call__(var2, var1.getname("search_function"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CodecRegistryError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(47);
      return var1.getf_locals();
   }

   public PyObject normalize_encoding$2(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned(" Normalize an encoding name.\n\n        Normalization works as follows: all non-alphanumeric\n        characters except the dot used for Python package names are\n        collapsed and replaced with a single underscore, e.g. '  -;#'\n        becomes '_'. Leading and trailing underscores are removed.\n\n        Note that encoding names should be ASCII only; if they do use\n        non-ASCII characters, these must be Latin-1 compatible.\n\n    ");
      var1.setline(64);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("__builtin__"), (PyObject)PyString.fromInterned("unicode"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode"));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("latin-1"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(69);
      var3 = PyString.fromInterned("_").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("translate").__call__(var2, var1.getglobal("_norm_encoding_map")).__getattr__("split").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject search_function$3(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("_cache").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("_unknown"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("_unknown"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(85);
         PyObject var4 = var1.getglobal("normalize_encoding").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(86);
         var10000 = var1.getglobal("_aliases").__getattr__("get").__call__(var2, var1.getlocal(2));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("_aliases").__getattr__("get").__call__(var2, var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)PyString.fromInterned("_")));
         }

         var4 = var10000;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(88);
         var4 = var1.getlocal(3);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         PyList var11;
         if (var10000.__nonzero__()) {
            var1.setline(89);
            var11 = new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(2)});
            var1.setlocal(4, var11);
            var4 = null;
         } else {
            var1.setline(92);
            var11 = new PyList(new PyObject[]{var1.getlocal(2)});
            var1.setlocal(4, var11);
            var4 = null;
         }

         var1.setline(93);
         var4 = var1.getlocal(4).__iter__();

         PyObject var5;
         PyString var6;
         String[] var7;
         PyObject var18;
         while(true) {
            var1.setline(93);
            var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(106);
               var18 = var1.getglobal("None");
               var1.setlocal(6, var18);
               var6 = null;
               break;
            }

            var1.setlocal(5, var5);
            var1.setline(94);
            var10000 = var1.getlocal(5).__not__();
            if (!var10000.__nonzero__()) {
               var6 = PyString.fromInterned(".");
               var10000 = var6._in(var1.getlocal(5));
               var6 = null;
            }

            if (!var10000.__nonzero__()) {
               try {
                  var1.setline(99);
                  var10000 = var1.getglobal("__import__");
                  PyObject[] var13 = new PyObject[]{PyString.fromInterned("encodings.")._add(var1.getlocal(5)), var1.getglobal("_import_tail"), Py.newInteger(0)};
                  var7 = new String[]{"fromlist", "level"};
                  var10000 = var10000.__call__(var2, var13, var7);
                  var6 = null;
                  var18 = var10000;
                  var1.setlocal(6, var18);
                  var6 = null;
                  break;
               } catch (Throwable var10) {
                  PyException var12 = Py.setException(var10, var1);
                  if (!var12.match(var1.getglobal("ImportError"))) {
                     throw var12;
                  }

                  var1.setline(102);
               }
            }
         }

         PyException var16;
         try {
            var1.setline(109);
            var4 = var1.getlocal(6).__getattr__("getregentry");
            var1.setlocal(7, var4);
            var4 = null;
         } catch (Throwable var9) {
            var16 = Py.setException(var9, var1);
            if (!var16.match(var1.getglobal("AttributeError"))) {
               throw var16;
            }

            var1.setline(112);
            var5 = var1.getglobal("None");
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(114);
         var4 = var1.getlocal(6);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(117);
            var4 = var1.getglobal("_java").__getattr__("_java_factory").__call__(var2, var1.getlocal(0));
            PyObject[] var17 = Py.unpackSequence(var4, 2);
            var18 = var17[0];
            var1.setlocal(1, var18);
            var6 = null;
            var18 = var17[1];
            var1.setlocal(8, var18);
            var6 = null;
            var4 = null;
            var1.setline(118);
            var4 = var1.getlocal(1);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(119);
               var4 = var1.getlocal(1);
               var1.getglobal("_cache").__setitem__(var1.getlocal(0), var4);
               var4 = null;
               var1.setline(120);
               var4 = var1.getlocal(8).__iter__();

               while(true) {
                  var1.setline(120);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(123);
                     var3 = var1.getlocal(1);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(9, var5);
                  var1.setline(121);
                  var18 = var1.getlocal(9);
                  var10000 = var18._notin(var1.getglobal("_aliases"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(122);
                     var18 = var1.getlocal(5);
                     var1.getglobal("_aliases").__setitem__(var1.getlocal(9), var18);
                     var6 = null;
                  }
               }
            } else {
               var1.setline(126);
               var4 = var1.getglobal("None");
               var1.getglobal("_cache").__setitem__(var1.getlocal(0), var4);
               var4 = null;
               var1.setline(127);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(130);
            var4 = var1.getlocal(7).__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(131);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("codecs").__getattr__("CodecInfo")).__not__().__nonzero__()) {
               var1.setline(132);
               PyInteger var19 = Py.newInteger(4);
               PyObject var10001 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               PyInteger var21 = var19;
               var4 = var10001;
               if ((var5 = var21._le(var10001)).__nonzero__()) {
                  var5 = var4._le(Py.newInteger(7));
               }

               var4 = null;
               if (var5.__not__().__nonzero__()) {
                  var1.setline(133);
                  throw Py.makeException(var1.getglobal("CodecRegistryError"), PyString.fromInterned("module \"%s\" (%s) failed to register")._mod(new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("__name__"), var1.getlocal(6).__getattr__("__file__")})));
               }

               var1.setline(136);
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("__call__")).__not__();
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("__call__")).__not__();
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                     var10000 = var4._isnot(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(2)), (PyObject)PyString.fromInterned("__call__")).__not__();
                     }

                     if (!var10000.__nonzero__()) {
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(3));
                        var10000 = var4._isnot(var1.getglobal("None"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(3)), (PyObject)PyString.fromInterned("__call__")).__not__();
                        }

                        if (!var10000.__nonzero__()) {
                           var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                           var10000 = var4._gt(Py.newInteger(4));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var4 = var1.getlocal(1).__getitem__(Py.newInteger(4));
                              var10000 = var4._isnot(var1.getglobal("None"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(4)), (PyObject)PyString.fromInterned("__call__")).__not__();
                              }
                           }

                           if (!var10000.__nonzero__()) {
                              var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                              var10000 = var4._gt(Py.newInteger(5));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var4 = var1.getlocal(1).__getitem__(Py.newInteger(5));
                                 var10000 = var4._isnot(var1.getglobal("None"));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(5)), (PyObject)PyString.fromInterned("__call__")).__not__();
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(142);
                  throw Py.makeException(var1.getglobal("CodecRegistryError"), PyString.fromInterned("incompatible codecs in module \"%s\" (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("__name__"), var1.getlocal(6).__getattr__("__file__")})));
               }

               var1.setline(145);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var4._lt(Py.newInteger(7));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(1).__getitem__(Py.newInteger(6));
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(146);
                  var4 = var1.getlocal(1);
                  var4 = var4._iadd((new PyTuple(new PyObject[]{var1.getglobal("None")}))._mul(Py.newInteger(6)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1))))._add(new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("__name__").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1))})));
                  var1.setlocal(1, var4);
               }

               var1.setline(147);
               var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
               PyObject[] var20 = Py.EmptyObjects;
               String[] var14 = new String[0];
               var10000 = var10000._callextra(var20, var14, var1.getlocal(1), (PyObject)null);
               var4 = null;
               var4 = var10000;
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(150);
            var4 = var1.getlocal(1);
            var1.getglobal("_cache").__setitem__(var1.getlocal(0), var4);
            var4 = null;

            label158: {
               try {
                  var1.setline(155);
                  var4 = var1.getlocal(6).__getattr__("getaliases").__call__(var2);
                  var1.setlocal(8, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  var16 = Py.setException(var8, var1);
                  if (var16.match(var1.getglobal("AttributeError"))) {
                     var1.setline(157);
                     break label158;
                  }

                  throw var16;
               }

               var1.setline(159);
               var5 = var1.getlocal(8).__iter__();

               while(true) {
                  var1.setline(159);
                  var18 = var5.__iternext__();
                  if (var18 == null) {
                     break;
                  }

                  var1.setlocal(9, var18);
                  var1.setline(160);
                  PyObject var15 = var1.getlocal(9);
                  var10000 = var15._notin(var1.getglobal("_aliases"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(161);
                     var15 = var1.getlocal(5);
                     var1.getglobal("_aliases").__setitem__(var1.getlocal(9), var15);
                     var7 = null;
                  }
               }
            }

            var1.setline(164);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public encodings$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CodecRegistryError$1 = Py.newCode(0, var2, var1, "CodecRegistryError", 46, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"encoding"};
      normalize_encoding$2 = Py.newCode(1, var2, var1, "normalize_encoding", 49, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "entry", "norm_encoding", "aliased_encoding", "modnames", "modname", "mod", "getregentry", "codecaliases", "alias"};
      search_function$3 = Py.newCode(1, var2, var1, "search_function", 71, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new encodings$py("encodings$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(encodings$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CodecRegistryError$1(var2, var3);
         case 2:
            return this.normalize_encoding$2(var2, var3);
         case 3:
            return this.search_function$3(var2, var3);
         default:
            return null;
      }
   }
}
