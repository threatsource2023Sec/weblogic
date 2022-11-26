import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("fnmatch.py")
public class fnmatch$py extends PyFunctionTable implements PyRunnable {
   static fnmatch$py self;
   static final PyCode f$0;
   static final PyCode _purge$1;
   static final PyCode fnmatch$2;
   static final PyCode filter$3;
   static final PyCode fnmatchcase$4;
   static final PyCode translate$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Filename matching with shell patterns.\n\nfnmatch(FILENAME, PATTERN) matches according to the local convention.\nfnmatchcase(FILENAME, PATTERN) always takes case in account.\n\nThe functions operate by translating the pattern into a regular\nexpression.  They cache the compiled regular expressions for speed.\n\nThe function translate(PATTERN) returns a regular expression\ncorresponding to PATTERN.  (It does not compile it.)\n"));
      var1.setline(11);
      PyString.fromInterned("Filename matching with shell patterns.\n\nfnmatch(FILENAME, PATTERN) matches according to the local convention.\nfnmatchcase(FILENAME, PATTERN) always takes case in account.\n\nThe functions operate by translating the pattern into a regular\nexpression.  They cache the compiled regular expressions for speed.\n\nThe function translate(PATTERN) returns a regular expression\ncorresponding to PATTERN.  (It does not compile it.)\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(15);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("filter"), PyString.fromInterned("fnmatch"), PyString.fromInterned("fnmatchcase"), PyString.fromInterned("translate")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(17);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_cache", var5);
      var3 = null;
      var1.setline(18);
      PyInteger var6 = Py.newInteger(100);
      var1.setlocal("_MAXCACHE", var6);
      var3 = null;
      var1.setline(20);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, _purge$1, PyString.fromInterned("Clear the pattern cache"));
      var1.setlocal("_purge", var8);
      var3 = null;
      var1.setline(24);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, fnmatch$2, PyString.fromInterned("Test whether FILENAME matches PATTERN.\n\n    Patterns are Unix shell style:\n\n    *       matches everything\n    ?       matches any single character\n    [seq]   matches any character in seq\n    [!seq]  matches any char not in seq\n\n    An initial period in FILENAME is not special.\n    Both FILENAME and PATTERN are first case-normalized\n    if the operating system requires it.\n    If you don't want this, use fnmatchcase(FILENAME, PATTERN).\n    "));
      var1.setlocal("fnmatch", var8);
      var3 = null;
      var1.setline(45);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, filter$3, PyString.fromInterned("Return the subset of the list NAMES that match PAT"));
      var1.setlocal("filter", var8);
      var3 = null;
      var1.setline(67);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, fnmatchcase$4, PyString.fromInterned("Test whether FILENAME matches PATTERN, including case.\n\n    This is a version of fnmatch() which doesn't case-normalize\n    its arguments.\n    "));
      var1.setlocal("fnmatchcase", var8);
      var3 = null;
      var1.setline(81);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, translate$5, PyString.fromInterned("Translate a shell PATTERN to a regular expression.\n\n    There is no way to quote meta-characters.\n    "));
      var1.setlocal("translate", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _purge$1(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyString.fromInterned("Clear the pattern cache");
      var1.setline(22);
      var1.getglobal("_cache").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fnmatch$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyString.fromInterned("Test whether FILENAME matches PATTERN.\n\n    Patterns are Unix shell style:\n\n    *       matches everything\n    ?       matches any single character\n    [seq]   matches any character in seq\n    [!seq]  matches any char not in seq\n\n    An initial period in FILENAME is not special.\n    Both FILENAME and PATTERN are first case-normalized\n    if the operating system requires it.\n    If you don't want this, use fnmatchcase(FILENAME, PATTERN).\n    ");
      var1.setline(40);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getlocal(2).__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getlocal(2).__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getglobal("fnmatchcase").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject filter$3(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyString.fromInterned("Return the subset of the list NAMES that match PAT");
      var1.setline(47);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var3 = imp.importOne("posixpath", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(48);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(49);
      var3 = var1.getlocal(2).__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("_cache"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(51);
         var3 = var1.getglobal("translate").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(52);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("_cache"));
         var10000 = var3._ge(var1.getglobal("_MAXCACHE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(53);
            var1.getglobal("_cache").__getattr__("clear").__call__(var2);
         }

         var1.setline(54);
         var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(5));
         var1.getglobal("_cache").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(55);
      var3 = var1.getglobal("_cache").__getitem__(var1.getlocal(1)).__getattr__("match");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getlocal(2).__getattr__("path");
      var10000 = var3._is(var1.getlocal(3));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(58);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(58);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(59);
            if (var1.getlocal(6).__call__(var2, var1.getlocal(7)).__nonzero__()) {
               var1.setline(60);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
            }
         }
      } else {
         var1.setline(62);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(62);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(63);
            if (var1.getlocal(6).__call__(var2, var1.getlocal(2).__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(7))).__nonzero__()) {
               var1.setline(64);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
            }
         }
      }

      var1.setline(65);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fnmatchcase$4(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Test whether FILENAME matches PATTERN, including case.\n\n    This is a version of fnmatch() which doesn't case-normalize\n    its arguments.\n    ");
      var1.setline(74);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("_cache"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(75);
         var3 = var1.getglobal("translate").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(76);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("_cache"));
         var10000 = var3._ge(var1.getglobal("_MAXCACHE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(77);
            var1.getglobal("_cache").__getattr__("clear").__call__(var2);
         }

         var1.setline(78);
         var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(2));
         var1.getglobal("_cache").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(79);
      var3 = var1.getglobal("_cache").__getitem__(var1.getlocal(1)).__getattr__("match").__call__(var2, var1.getlocal(0));
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject translate$5(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Translate a shell PATTERN to a regular expression.\n\n    There is no way to quote meta-characters.\n    ");
      var1.setline(87);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(88);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(3, var6);
      var3 = null;

      while(true) {
         while(true) {
            var1.setline(89);
            PyObject var7 = var1.getlocal(1);
            PyObject var10000 = var7._lt(var1.getlocal(2));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(116);
               var7 = var1.getlocal(3)._add(PyString.fromInterned("\\Z(?ms)"));
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(90);
            var7 = var1.getlocal(0).__getitem__(var1.getlocal(1));
            var1.setlocal(4, var7);
            var3 = null;
            var1.setline(91);
            var7 = var1.getlocal(1)._add(Py.newInteger(1));
            var1.setlocal(1, var7);
            var3 = null;
            var1.setline(92);
            var7 = var1.getlocal(4);
            var10000 = var7._eq(PyString.fromInterned("*"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(93);
               var7 = var1.getlocal(3)._add(PyString.fromInterned(".*"));
               var1.setlocal(3, var7);
               var3 = null;
            } else {
               var1.setline(94);
               var7 = var1.getlocal(4);
               var10000 = var7._eq(PyString.fromInterned("?"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(95);
                  var7 = var1.getlocal(3)._add(PyString.fromInterned("."));
                  var1.setlocal(3, var7);
                  var3 = null;
               } else {
                  var1.setline(96);
                  var7 = var1.getlocal(4);
                  var10000 = var7._eq(PyString.fromInterned("["));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(97);
                     var7 = var1.getlocal(1);
                     var1.setlocal(5, var7);
                     var3 = null;
                     var1.setline(98);
                     var7 = var1.getlocal(5);
                     var10000 = var7._lt(var1.getlocal(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var7 = var1.getlocal(0).__getitem__(var1.getlocal(5));
                        var10000 = var7._eq(PyString.fromInterned("!"));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(99);
                        var7 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(5, var7);
                        var3 = null;
                     }

                     var1.setline(100);
                     var7 = var1.getlocal(5);
                     var10000 = var7._lt(var1.getlocal(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var7 = var1.getlocal(0).__getitem__(var1.getlocal(5));
                        var10000 = var7._eq(PyString.fromInterned("]"));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(101);
                        var7 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(5, var7);
                        var3 = null;
                     }

                     while(true) {
                        var1.setline(102);
                        var7 = var1.getlocal(5);
                        var10000 = var7._lt(var1.getlocal(2));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var7 = var1.getlocal(0).__getitem__(var1.getlocal(5));
                           var10000 = var7._ne(PyString.fromInterned("]"));
                           var3 = null;
                        }

                        if (!var10000.__nonzero__()) {
                           var1.setline(104);
                           var7 = var1.getlocal(5);
                           var10000 = var7._ge(var1.getlocal(2));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(105);
                              var7 = var1.getlocal(3)._add(PyString.fromInterned("\\["));
                              var1.setlocal(3, var7);
                              var3 = null;
                           } else {
                              var1.setline(107);
                              var7 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(5), (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\\\"));
                              var1.setlocal(6, var7);
                              var3 = null;
                              var1.setline(108);
                              var7 = var1.getlocal(5)._add(Py.newInteger(1));
                              var1.setlocal(1, var7);
                              var3 = null;
                              var1.setline(109);
                              var7 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                              var10000 = var7._eq(PyString.fromInterned("!"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(110);
                                 var7 = PyString.fromInterned("^")._add(var1.getlocal(6).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                                 var1.setlocal(6, var7);
                                 var3 = null;
                              } else {
                                 var1.setline(111);
                                 var7 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                                 var10000 = var7._eq(PyString.fromInterned("^"));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(112);
                                    var7 = PyString.fromInterned("\\")._add(var1.getlocal(6));
                                    var1.setlocal(6, var7);
                                    var3 = null;
                                 }
                              }

                              var1.setline(113);
                              var7 = PyString.fromInterned("%s[%s]")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(6)}));
                              var1.setlocal(3, var7);
                              var3 = null;
                           }
                           break;
                        }

                        var1.setline(103);
                        var7 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(5, var7);
                        var3 = null;
                     }
                  } else {
                     var1.setline(115);
                     var7 = var1.getlocal(3)._add(var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(4)));
                     var1.setlocal(3, var7);
                     var3 = null;
                  }
               }
            }
         }
      }
   }

   public fnmatch$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _purge$1 = Py.newCode(0, var2, var1, "_purge", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "pat", "os"};
      fnmatch$2 = Py.newCode(2, var2, var1, "fnmatch", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"names", "pat", "os", "posixpath", "result", "res", "match", "name"};
      filter$3 = Py.newCode(2, var2, var1, "filter", 45, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "pat", "res"};
      fnmatchcase$4 = Py.newCode(2, var2, var1, "fnmatchcase", 67, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pat", "i", "n", "res", "c", "j", "stuff"};
      translate$5 = Py.newCode(1, var2, var1, "translate", 81, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fnmatch$py("fnmatch$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fnmatch$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._purge$1(var2, var3);
         case 2:
            return this.fnmatch$2(var2, var3);
         case 3:
            return this.filter$3(var2, var3);
         case 4:
            return this.fnmatchcase$4(var2, var3);
         case 5:
            return this.translate$5(var2, var3);
         default:
            return null;
      }
   }
}
