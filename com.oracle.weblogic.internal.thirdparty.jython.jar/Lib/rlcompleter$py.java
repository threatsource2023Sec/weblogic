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
@MTime(1498849383000L)
@Filename("rlcompleter.py")
public class rlcompleter$py extends PyFunctionTable implements PyRunnable {
   static rlcompleter$py self;
   static final PyCode f$0;
   static final PyCode Completer$1;
   static final PyCode __init__$2;
   static final PyCode complete$3;
   static final PyCode _callable_postfix$4;
   static final PyCode global_matches$5;
   static final PyCode attr_matches$6;
   static final PyCode get_class_members$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Word completion for GNU readline.\n\nThe completer completes keywords, built-ins and globals in a selectable\nnamespace (which defaults to __main__); when completing NAME.NAME..., it\nevaluates (!) the expression up to the last dot and completes its attributes.\n\nIt's very cool to do \"import sys\" type \"sys.\", hit the completion key (twice),\nand see the list of names defined by the sys module!\n\nTip: to use the tab key as the completion key, call\n\n    readline.parse_and_bind(\"tab: complete\")\n\nNotes:\n\n- Exceptions raised by the completer function are *ignored* (and generally cause\n  the completion to fail).  This is a feature -- since readline sets the tty\n  device in raw (or cbreak) mode, printing a traceback wouldn't work well\n  without some complicated hoopla to save, reset and restore the tty state.\n\n- The evaluation of the NAME.NAME... form may cause arbitrary application\n  defined code to be executed if an object with a __getattr__ hook is found.\n  Since it is the responsibility of the application (or the user) to enable this\n  feature, I consider this an acceptable risk.  More complicated expressions\n  (e.g. function calls or indexing operations) are *not* evaluated.\n\n- GNU readline is also used by the built-in functions input() and\nraw_input(), and thus these also benefit/suffer from the completer\nfeatures.  Clearly an interactive application can benefit by\nspecifying its own completer function and using raw_input() for all\nits input.\n\n- When the original stdin is not a tty device, GNU readline is never\n  used, and this module (and the readline module) are silently inactive.\n\n"));
      var1.setline(36);
      PyString.fromInterned("Word completion for GNU readline.\n\nThe completer completes keywords, built-ins and globals in a selectable\nnamespace (which defaults to __main__); when completing NAME.NAME..., it\nevaluates (!) the expression up to the last dot and completes its attributes.\n\nIt's very cool to do \"import sys\" type \"sys.\", hit the completion key (twice),\nand see the list of names defined by the sys module!\n\nTip: to use the tab key as the completion key, call\n\n    readline.parse_and_bind(\"tab: complete\")\n\nNotes:\n\n- Exceptions raised by the completer function are *ignored* (and generally cause\n  the completion to fail).  This is a feature -- since readline sets the tty\n  device in raw (or cbreak) mode, printing a traceback wouldn't work well\n  without some complicated hoopla to save, reset and restore the tty state.\n\n- The evaluation of the NAME.NAME... form may cause arbitrary application\n  defined code to be executed if an object with a __getattr__ hook is found.\n  Since it is the responsibility of the application (or the user) to enable this\n  feature, I consider this an acceptable risk.  More complicated expressions\n  (e.g. function calls or indexing operations) are *not* evaluated.\n\n- GNU readline is also used by the built-in functions input() and\nraw_input(), and thus these also benefit/suffer from the completer\nfeatures.  Clearly an interactive application can benefit by\nspecifying its own completer function and using raw_input() for all\nits input.\n\n- When the original stdin is not a tty device, GNU readline is never\n  used, and this module (and the readline module) are silently inactive.\n\n");
      var1.setline(38);
      PyObject var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var1.setline(39);
      var3 = imp.importOne("__main__", var1, -1);
      var1.setlocal("__main__", var3);
      var3 = null;
      var1.setline(41);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("Completer")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(43);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Completer", var7, Completer$1);
      var1.setlocal("Completer", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(154);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, get_class_members$7, (PyObject)null);
      var1.setlocal("get_class_members", var8);
      var3 = null;

      label19: {
         try {
            var1.setline(162);
            var3 = imp.importOne("readline", var1, -1);
            var1.setlocal("readline", var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var9 = Py.setException(var5, var1);
            if (var9.match(var1.getname("ImportError"))) {
               var1.setline(164);
               break label19;
            }

            throw var9;
         }

         var1.setline(166);
         var1.getname("readline").__getattr__("set_completer").__call__(var2, var1.getname("Completer").__call__(var2).__getattr__("complete"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Completer$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(44);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Create a new completer for the command line.\n\n        Completer([namespace]) -> completer instance.\n\n        If unspecified, the default namespace where completions are performed\n        is __main__ (technically, __main__.__dict__). Namespaces should be\n        given as dictionaries.\n\n        Completer instances should be used as the completion mechanism of\n        readline via the set_completer() call:\n\n        readline.set_completer(Completer(my_namespace).complete)\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, complete$3, PyString.fromInterned("Return the next possible completion for 'text'.\n\n        This is called successively with state == 0, 1, 2, ... until it\n        returns None.  The completion should begin with 'text'.\n\n        "));
      var1.setlocal("complete", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _callable_postfix$4, (PyObject)null);
      var1.setlocal("_callable_postfix", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, global_matches$5, PyString.fromInterned("Compute matches when text is a simple name.\n\n        Return a list of all keywords, built-in functions and names currently\n        defined in self.namespace that match.\n\n        "));
      var1.setlocal("global_matches", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, attr_matches$6, PyString.fromInterned("Compute matches when text contains a dot.\n\n        Assuming the text is of the form NAME.NAME....[NAME], and is\n        evaluatable in self.namespace, it will be evaluated and its attributes\n        (as revealed by dir()) are used as possible completions.  (For class\n        instances, class members are also considered.)\n\n        WARNING: this can still invoke arbitrary C code, if an object\n        with a __getattr__ hook is evaluated.\n\n        "));
      var1.setlocal("attr_matches", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyString.fromInterned("Create a new completer for the command line.\n\n        Completer([namespace]) -> completer instance.\n\n        If unspecified, the default namespace where completions are performed\n        is __main__ (technically, __main__.__dict__). Namespaces should be\n        given as dictionaries.\n\n        Completer instances should be used as the completion mechanism of\n        readline via the set_completer() call:\n\n        readline.set_completer(Completer(my_namespace).complete)\n        ");
      var1.setline(59);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("dict")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(60);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("namespace must be a dictionary"));
      } else {
         var1.setline(65);
         PyObject var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyInteger var4;
         if (var10000.__nonzero__()) {
            var1.setline(66);
            var4 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"use_main_ns", var4);
            var3 = null;
         } else {
            var1.setline(68);
            var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"use_main_ns", var4);
            var3 = null;
            var1.setline(69);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("namespace", var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject complete$3(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Return the next possible completion for 'text'.\n\n        This is called successively with state == 0, 1, 2, ... until it\n        returns None.  The completion should begin with 'text'.\n\n        ");
      var1.setline(78);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("use_main_ns").__nonzero__()) {
         var1.setline(79);
         var3 = var1.getglobal("__main__").__getattr__("__dict__");
         var1.getlocal(0).__setattr__("namespace", var3);
         var3 = null;
      }

      var1.setline(81);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(82);
         PyString var6 = PyString.fromInterned(".");
         var10000 = var6._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(83);
            var3 = var1.getlocal(0).__getattr__("attr_matches").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__setattr__("matches", var3);
            var3 = null;
         } else {
            var1.setline(85);
            var3 = var1.getlocal(0).__getattr__("global_matches").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__setattr__("matches", var3);
            var3 = null;
         }
      }

      try {
         var1.setline(87);
         var3 = var1.getlocal(0).__getattr__("matches").__getitem__(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("IndexError"))) {
            var1.setline(89);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _callable_postfix$4(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
         var1.setline(93);
         var3 = var1.getlocal(2)._add(PyString.fromInterned("("));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(94);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject global_matches$5(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyString.fromInterned("Compute matches when text is a simple name.\n\n        Return a list of all keywords, built-in functions and names currently\n        defined in self.namespace that match.\n\n        ");
      var1.setline(103);
      PyObject var3 = imp.importOne("keyword", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(104);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(105);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(2).__getattr__("kwlist").__iter__();

      while(true) {
         var1.setline(106);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(109);
            var3 = (new PyList(new PyObject[]{var1.getglobal("__builtin__").__getattr__("__dict__"), var1.getlocal(0).__getattr__("namespace")})).__iter__();

            while(true) {
               var1.setline(109);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(113);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(6, var4);
               var1.setline(110);
               var5 = var1.getlocal(6).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(110);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  PyObject[] var7 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(5, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(7, var8);
                  var8 = null;
                  var1.setline(111);
                  PyObject var10 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
                  var10000 = var10._eq(var1.getlocal(1));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var10 = var1.getlocal(5);
                     var10000 = var10._ne(PyString.fromInterned("__builtins__"));
                     var7 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(112);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_callable_postfix").__call__(var2, var1.getlocal(7), var1.getlocal(5)));
                  }
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(107);
         var5 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(108);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject attr_matches$6(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Compute matches when text contains a dot.\n\n        Assuming the text is of the form NAME.NAME....[NAME], and is\n        evaluatable in self.namespace, it will be evaluated and its attributes\n        (as revealed by dir()) are used as possible completions.  (For class\n        instances, class members are also considered.)\n\n        WARNING: this can still invoke arbitrary C code, if an object\n        with a __getattr__ hook is evaluated.\n\n        ");
      var1.setline(127);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(2).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\w+(\\.\\w+)*)\\.(\\w*)"), (PyObject)var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(129);
      PyList var8;
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(130);
         var8 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(131);
         PyObject var4 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(3));
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var4 = null;

         try {
            var1.setline(133);
            var4 = var1.getglobal("eval").__call__(var2, var1.getlocal(4), var1.getlocal(0).__getattr__("namespace"));
            var1.setlocal(6, var4);
            var4 = null;
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (var9.match(var1.getglobal("Exception"))) {
               var1.setline(135);
               var8 = new PyList(Py.EmptyObjects);
               var1.f_lasti = -1;
               return var8;
            }

            throw var9;
         }

         var1.setline(138);
         var4 = var1.getglobal("dir").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(139);
         PyString var11 = PyString.fromInterned("__builtins__");
         PyObject var10000 = var11._in(var1.getlocal(7));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(140);
            var1.getlocal(7).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__builtins__"));
         }

         var1.setline(142);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("__class__")).__nonzero__()) {
            var1.setline(143);
            var1.getlocal(7).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__class__"));
            var1.setline(144);
            var1.getlocal(7).__getattr__("extend").__call__(var2, var1.getglobal("get_class_members").__call__(var2, var1.getlocal(6).__getattr__("__class__")));
         }

         var1.setline(145);
         PyList var12 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var12);
         var4 = null;
         var1.setline(146);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(147);
         var4 = var1.getlocal(7).__iter__();

         while(true) {
            var1.setline(147);
            PyObject var10 = var4.__iternext__();
            if (var10 == null) {
               var1.setline(152);
               var3 = var1.getlocal(8);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(10, var10);
            var1.setline(148);
            var6 = var1.getlocal(10).__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null);
            var10000 = var6._eq(var1.getlocal(5));
            var6 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(6), var1.getlocal(10));
            }

            if (var10000.__nonzero__()) {
               var1.setline(149);
               var6 = var1.getglobal("getattr").__call__(var2, var1.getlocal(6), var1.getlocal(10));
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(150);
               var6 = var1.getlocal(0).__getattr__("_callable_postfix").__call__(var2, var1.getlocal(11), PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(10)})));
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(151);
               var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(10));
            }
         }
      }
   }

   public PyObject get_class_members$7(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(156);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__bases__")).__nonzero__()) {
         var1.setline(157);
         var3 = var1.getlocal(0).__getattr__("__bases__").__iter__();

         while(true) {
            var1.setline(157);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(158);
            PyObject var5 = var1.getlocal(1)._add(var1.getglobal("get_class_members").__call__(var2, var1.getlocal(2)));
            var1.setlocal(1, var5);
            var5 = null;
         }
      }

      var1.setline(159);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public rlcompleter$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Completer$1 = Py.newCode(0, var2, var1, "Completer", 43, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "namespace"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 44, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "state"};
      complete$3 = Py.newCode(3, var2, var1, "complete", 71, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val", "word"};
      _callable_postfix$4 = Py.newCode(3, var2, var1, "_callable_postfix", 91, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "keyword", "matches", "n", "word", "nspace", "val"};
      global_matches$5 = Py.newCode(2, var2, var1, "global_matches", 96, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "re", "m", "expr", "attr", "thisobject", "words", "matches", "n", "word", "val"};
      attr_matches$6 = Py.newCode(2, var2, var1, "attr_matches", 115, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"klass", "ret", "base"};
      get_class_members$7 = Py.newCode(1, var2, var1, "get_class_members", 154, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new rlcompleter$py("rlcompleter$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(rlcompleter$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Completer$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.complete$3(var2, var3);
         case 4:
            return this._callable_postfix$4(var2, var3);
         case 5:
            return this.global_matches$5(var2, var3);
         case 6:
            return this.attr_matches$6(var2, var3);
         case 7:
            return this.get_class_members$7(var2, var3);
         default:
            return null;
      }
   }
}
