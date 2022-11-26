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
@Filename("shlex.py")
public class shlex$py extends PyFunctionTable implements PyRunnable {
   static shlex$py self;
   static final PyCode f$0;
   static final PyCode shlex$1;
   static final PyCode __init__$2;
   static final PyCode push_token$3;
   static final PyCode push_source$4;
   static final PyCode pop_source$5;
   static final PyCode get_token$6;
   static final PyCode read_token$7;
   static final PyCode sourcehook$8;
   static final PyCode error_leader$9;
   static final PyCode __iter__$10;
   static final PyCode next$11;
   static final PyCode split$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A lexical analyzer class for simple shell-like syntaxes."));
      var1.setline(2);
      PyString.fromInterned("A lexical analyzer class for simple shell-like syntaxes.");
      var1.setline(10);
      PyObject var3 = imp.importOne("os.path", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(12);
      String[] var7 = new String[]{"deque"};
      PyObject[] var8 = imp.importFrom("collections", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("deque", var4);
      var4 = null;

      try {
         var1.setline(15);
         var7 = new String[]{"StringIO"};
         var8 = imp.importFrom("cStringIO", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal("StringIO", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(17);
         String[] var9 = new String[]{"StringIO"};
         PyObject[] var11 = imp.importFrom("StringIO", var9, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(19);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("shlex"), PyString.fromInterned("split")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(21);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("shlex", var8, shlex$1);
      var1.setlocal("shlex", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(274);
      var8 = new PyObject[]{var1.getname("False"), var1.getname("True")};
      PyFunction var13 = new PyFunction(var1.f_globals, var8, split$12, (PyObject)null);
      var1.setlocal("split", var13);
      var3 = null;
      var1.setline(281);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(282);
         var3 = var1.getname("len").__call__(var2, var1.getname("sys").__getattr__("argv"));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(283);
            var3 = var1.getname("shlex").__call__(var2);
            var1.setlocal("lexer", var3);
            var3 = null;
         } else {
            var1.setline(285);
            var3 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
            var1.setlocal("file", var3);
            var3 = null;
            var1.setline(286);
            var3 = var1.getname("shlex").__call__(var2, var1.getname("open").__call__(var2, var1.getname("file")), var1.getname("file"));
            var1.setlocal("lexer", var3);
            var3 = null;
         }

         while(true) {
            var1.setline(287);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(288);
            var3 = var1.getname("lexer").__getattr__("get_token").__call__(var2);
            var1.setlocal("tt", var3);
            var3 = null;
            var1.setline(289);
            if (!var1.getname("tt").__nonzero__()) {
               break;
            }

            var1.setline(290);
            Py.println(PyString.fromInterned("Token: ")._add(var1.getname("repr").__call__(var2, var1.getname("tt"))));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shlex$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A lexical analyzer class for simple shell-like syntaxes."));
      var1.setline(22);
      PyString.fromInterned("A lexical analyzer class for simple shell-like syntaxes.");
      var1.setline(23);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_token$3, PyString.fromInterned("Push a token onto the stack popped by the get_token method"));
      var1.setlocal("push_token", var4);
      var3 = null;
      var1.setline(65);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, push_source$4, PyString.fromInterned("Push an input source onto the lexer's input source stack."));
      var1.setlocal("push_source", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_source$5, PyString.fromInterned("Pop the input source stack."));
      var1.setlocal("pop_source", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_token$6, PyString.fromInterned("Get a token from the input stream (or from stack if it's nonempty)"));
      var1.setlocal("get_token", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_token$7, (PyObject)null);
      var1.setlocal("read_token", var4);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sourcehook$8, PyString.fromInterned("Hook called on a filename to be sourced."));
      var1.setlocal("sourcehook", var4);
      var3 = null;
      var1.setline(257);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, error_leader$9, PyString.fromInterned("Emit a C-compiler-like, Emacs-friendly error-message leader."));
      var1.setlocal("error_leader", var4);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$10, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$11, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(25);
         var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(26);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(27);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("instream", var3);
         var3 = null;
         var1.setline(28);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("infile", var3);
         var3 = null;
      } else {
         var1.setline(30);
         var3 = var1.getglobal("sys").__getattr__("stdin");
         var1.getlocal(0).__setattr__("instream", var3);
         var3 = null;
         var1.setline(31);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("infile", var3);
         var3 = null;
      }

      var1.setline(32);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("posix", var3);
      var3 = null;
      var1.setline(33);
      PyString var6;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(34);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("eof", var3);
         var3 = null;
      } else {
         var1.setline(36);
         var6 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"eof", var6);
         var3 = null;
      }

      var1.setline(37);
      var6 = PyString.fromInterned("#");
      var1.getlocal(0).__setattr__((String)"commenters", var6);
      var3 = null;
      var1.setline(38);
      var6 = PyString.fromInterned("abcdfeghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_");
      var1.getlocal(0).__setattr__((String)"wordchars", var6);
      var3 = null;
      var1.setline(40);
      if (var1.getlocal(0).__getattr__("posix").__nonzero__()) {
         var1.setline(41);
         var10000 = var1.getlocal(0);
         String var7 = "wordchars";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var7);
         var5 = var5._iadd(PyString.fromInterned("ßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞ"));
         var4.__setattr__(var7, var5);
      }

      var1.setline(43);
      var6 = PyString.fromInterned(" \t\r\n");
      var1.getlocal(0).__setattr__((String)"whitespace", var6);
      var3 = null;
      var1.setline(44);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("whitespace_split", var3);
      var3 = null;
      var1.setline(45);
      var6 = PyString.fromInterned("'\"");
      var1.getlocal(0).__setattr__((String)"quotes", var6);
      var3 = null;
      var1.setline(46);
      var6 = PyString.fromInterned("\\");
      var1.getlocal(0).__setattr__((String)"escape", var6);
      var3 = null;
      var1.setline(47);
      var6 = PyString.fromInterned("\"");
      var1.getlocal(0).__setattr__((String)"escapedquotes", var6);
      var3 = null;
      var1.setline(48);
      var6 = PyString.fromInterned(" ");
      var1.getlocal(0).__setattr__((String)"state", var6);
      var3 = null;
      var1.setline(49);
      var3 = var1.getglobal("deque").__call__(var2);
      var1.getlocal(0).__setattr__("pushback", var3);
      var3 = null;
      var1.setline(50);
      PyInteger var8 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"lineno", var8);
      var3 = null;
      var1.setline(51);
      var8 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"debug", var8);
      var3 = null;
      var1.setline(52);
      var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"token", var6);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("deque").__call__(var2);
      var1.getlocal(0).__setattr__("filestack", var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("source", var3);
      var3 = null;
      var1.setline(55);
      if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
         var1.setline(56);
         Py.println(PyString.fromInterned("shlex: reading from %s, line %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("instream"), var1.getlocal(0).__getattr__("lineno")})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_token$3(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Push a token onto the stack popped by the get_token method");
      var1.setline(61);
      PyObject var3 = var1.getlocal(0).__getattr__("debug");
      PyObject var10000 = var3._ge(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(62);
         Py.println(PyString.fromInterned("shlex: pushing token ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
      }

      var1.setline(63);
      var1.getlocal(0).__getattr__("pushback").__getattr__("appendleft").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_source$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Push an input source onto the lexer's input source stack.");
      var1.setline(67);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(68);
         var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(69);
      var1.getlocal(0).__getattr__("filestack").__getattr__("appendleft").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("infile"), var1.getlocal(0).__getattr__("instream"), var1.getlocal(0).__getattr__("lineno")})));
      var1.setline(70);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("infile", var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("instream", var3);
      var3 = null;
      var1.setline(72);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"lineno", var4);
      var3 = null;
      var1.setline(73);
      if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
         var1.setline(74);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(75);
            Py.println(PyString.fromInterned("shlex: pushing to file %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("infile")})));
         } else {
            var1.setline(77);
            Py.println(PyString.fromInterned("shlex: pushing to stream %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("instream")})));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_source$5(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Pop the input source stack.");
      var1.setline(81);
      var1.getlocal(0).__getattr__("instream").__getattr__("close").__call__(var2);
      var1.setline(82);
      PyObject var3 = var1.getlocal(0).__getattr__("filestack").__getattr__("popleft").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("infile", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("instream", var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("lineno", var5);
      var5 = null;
      var3 = null;
      var1.setline(83);
      if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
         var1.setline(84);
         Py.println(PyString.fromInterned("shlex: popping to %s, line %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("instream"), var1.getlocal(0).__getattr__("lineno")})));
      }

      var1.setline(86);
      PyString var6 = PyString.fromInterned(" ");
      var1.getlocal(0).__setattr__((String)"state", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_token$6(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Get a token from the input stream (or from stack if it's nonempty)");
      var1.setline(90);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__getattr__("pushback").__nonzero__()) {
         var1.setline(91);
         var3 = var1.getlocal(0).__getattr__("pushback").__getattr__("popleft").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(92);
         var3 = var1.getlocal(0).__getattr__("debug");
         var10000 = var3._ge(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(93);
            Py.println(PyString.fromInterned("shlex: popping token ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
         }

         var1.setline(94);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(96);
         PyObject var4 = var1.getlocal(0).__getattr__("read_token").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(98);
         var4 = var1.getlocal(0).__getattr__("source");
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            while(true) {
               var1.setline(99);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(var1.getlocal(0).__getattr__("source"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(100);
               var4 = var1.getlocal(0).__getattr__("sourcehook").__call__(var2, var1.getlocal(0).__getattr__("read_token").__call__(var2));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(101);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(102);
                  var4 = var1.getlocal(3);
                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(103);
                  var1.getlocal(0).__getattr__("push_source").__call__(var2, var1.getlocal(5), var1.getlocal(4));
               }

               var1.setline(104);
               var4 = var1.getlocal(0).__getattr__("get_token").__call__(var2);
               var1.setlocal(2, var4);
               var4 = null;
            }
         }

         while(true) {
            var1.setline(106);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(var1.getlocal(0).__getattr__("eof"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(113);
               var4 = var1.getlocal(0).__getattr__("debug");
               var10000 = var4._ge(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(114);
                  var4 = var1.getlocal(2);
                  var10000 = var4._ne(var1.getlocal(0).__getattr__("eof"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(115);
                     Py.println(PyString.fromInterned("shlex: token=")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(2))));
                  } else {
                     var1.setline(117);
                     Py.println(PyString.fromInterned("shlex: token=EOF"));
                  }
               }

               var1.setline(118);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(107);
            if (var1.getlocal(0).__getattr__("filestack").__not__().__nonzero__()) {
               var1.setline(108);
               var3 = var1.getlocal(0).__getattr__("eof");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(110);
            var1.getlocal(0).__getattr__("pop_source").__call__(var2);
            var1.setline(111);
            var4 = var1.getlocal(0).__getattr__("get_token").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         }
      }
   }

   public PyObject read_token$7(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(122);
      PyString var4 = PyString.fromInterned(" ");
      var1.setlocal(2, var4);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(123);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(124);
         var3 = var1.getlocal(0).__getattr__("instream").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(125);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(PyString.fromInterned("\n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(126);
            var3 = var1.getlocal(0).__getattr__("lineno")._add(Py.newInteger(1));
            var1.getlocal(0).__setattr__("lineno", var3);
            var3 = null;
         }

         var1.setline(127);
         var3 = var1.getlocal(0).__getattr__("debug");
         var10000 = var3._ge(Py.newInteger(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(128);
            Py.printComma(PyString.fromInterned("shlex: in state"));
            Py.printComma(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("state")));
            Py.printComma(PyString.fromInterned("I see character:"));
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(3)));
         }

         var1.setline(130);
         var3 = var1.getlocal(0).__getattr__("state");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var4 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"token", var4);
            var3 = null;
            break;
         }

         var1.setline(133);
         var3 = var1.getlocal(0).__getattr__("state");
         var10000 = var3._eq(PyString.fromInterned(" "));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(134);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(135);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("state", var3);
               var3 = null;
               break;
            }

            var1.setline(137);
            var3 = var1.getlocal(3);
            var10000 = var3._in(var1.getlocal(0).__getattr__("whitespace"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(138);
               var3 = var1.getlocal(0).__getattr__("debug");
               var10000 = var3._ge(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(139);
                  Py.println(PyString.fromInterned("shlex: I see whitespace in whitespace state"));
               }

               var1.setline(140);
               var10000 = var1.getlocal(0).__getattr__("token");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("posix");
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1);
                  }
               }

               if (var10000.__nonzero__()) {
                  break;
               }
            } else {
               var1.setline(144);
               var3 = var1.getlocal(3);
               var10000 = var3._in(var1.getlocal(0).__getattr__("commenters"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(145);
                  var1.getlocal(0).__getattr__("instream").__getattr__("readline").__call__(var2);
                  var1.setline(146);
                  var3 = var1.getlocal(0).__getattr__("lineno")._add(Py.newInteger(1));
                  var1.getlocal(0).__setattr__("lineno", var3);
                  var3 = null;
               } else {
                  var1.setline(147);
                  var10000 = var1.getlocal(0).__getattr__("posix");
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(3);
                     var10000 = var3._in(var1.getlocal(0).__getattr__("escape"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(148);
                     var4 = PyString.fromInterned("a");
                     var1.setlocal(2, var4);
                     var3 = null;
                     var1.setline(149);
                     var3 = var1.getlocal(3);
                     var1.getlocal(0).__setattr__("state", var3);
                     var3 = null;
                  } else {
                     var1.setline(150);
                     var3 = var1.getlocal(3);
                     var10000 = var3._in(var1.getlocal(0).__getattr__("wordchars"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(151);
                        var3 = var1.getlocal(3);
                        var1.getlocal(0).__setattr__("token", var3);
                        var3 = null;
                        var1.setline(152);
                        var4 = PyString.fromInterned("a");
                        var1.getlocal(0).__setattr__((String)"state", var4);
                        var3 = null;
                     } else {
                        var1.setline(153);
                        var3 = var1.getlocal(3);
                        var10000 = var3._in(var1.getlocal(0).__getattr__("quotes"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(154);
                           if (var1.getlocal(0).__getattr__("posix").__not__().__nonzero__()) {
                              var1.setline(155);
                              var3 = var1.getlocal(3);
                              var1.getlocal(0).__setattr__("token", var3);
                              var3 = null;
                           }

                           var1.setline(156);
                           var3 = var1.getlocal(3);
                           var1.getlocal(0).__setattr__("state", var3);
                           var3 = null;
                        } else {
                           var1.setline(157);
                           if (var1.getlocal(0).__getattr__("whitespace_split").__nonzero__()) {
                              var1.setline(158);
                              var3 = var1.getlocal(3);
                              var1.getlocal(0).__setattr__("token", var3);
                              var3 = null;
                              var1.setline(159);
                              var4 = PyString.fromInterned("a");
                              var1.getlocal(0).__setattr__((String)"state", var4);
                              var3 = null;
                           } else {
                              var1.setline(161);
                              var3 = var1.getlocal(3);
                              var1.getlocal(0).__setattr__("token", var3);
                              var3 = null;
                              var1.setline(162);
                              var10000 = var1.getlocal(0).__getattr__("token");
                              if (!var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(0).__getattr__("posix");
                                 if (var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(1);
                                 }
                              }

                              if (var10000.__nonzero__()) {
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else {
            var1.setline(166);
            var3 = var1.getlocal(0).__getattr__("state");
            var10000 = var3._in(var1.getlocal(0).__getattr__("quotes"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(167);
               var3 = var1.getglobal("True");
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(168);
               if (var1.getlocal(3).__not__().__nonzero__()) {
                  var1.setline(169);
                  var3 = var1.getlocal(0).__getattr__("debug");
                  var10000 = var3._ge(Py.newInteger(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(170);
                     Py.println(PyString.fromInterned("shlex: I see EOF in quotes state"));
                  }

                  var1.setline(172);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("No closing quotation"));
               }

               var1.setline(173);
               var3 = var1.getlocal(3);
               var10000 = var3._eq(var1.getlocal(0).__getattr__("state"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(174);
                  if (var1.getlocal(0).__getattr__("posix").__not__().__nonzero__()) {
                     var1.setline(175);
                     var3 = var1.getlocal(0).__getattr__("token")._add(var1.getlocal(3));
                     var1.getlocal(0).__setattr__("token", var3);
                     var3 = null;
                     var1.setline(176);
                     var4 = PyString.fromInterned(" ");
                     var1.getlocal(0).__setattr__((String)"state", var4);
                     var3 = null;
                     break;
                  }

                  var1.setline(179);
                  var4 = PyString.fromInterned("a");
                  var1.getlocal(0).__setattr__((String)"state", var4);
                  var3 = null;
               } else {
                  var1.setline(180);
                  var10000 = var1.getlocal(0).__getattr__("posix");
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(3);
                     var10000 = var3._in(var1.getlocal(0).__getattr__("escape"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(0).__getattr__("state");
                        var10000 = var3._in(var1.getlocal(0).__getattr__("escapedquotes"));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(182);
                     var3 = var1.getlocal(0).__getattr__("state");
                     var1.setlocal(2, var3);
                     var3 = null;
                     var1.setline(183);
                     var3 = var1.getlocal(3);
                     var1.getlocal(0).__setattr__("state", var3);
                     var3 = null;
                  } else {
                     var1.setline(185);
                     var3 = var1.getlocal(0).__getattr__("token")._add(var1.getlocal(3));
                     var1.getlocal(0).__setattr__("token", var3);
                     var3 = null;
                  }
               }
            } else {
               var1.setline(186);
               var3 = var1.getlocal(0).__getattr__("state");
               var10000 = var3._in(var1.getlocal(0).__getattr__("escape"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(187);
                  if (var1.getlocal(3).__not__().__nonzero__()) {
                     var1.setline(188);
                     var3 = var1.getlocal(0).__getattr__("debug");
                     var10000 = var3._ge(Py.newInteger(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(189);
                        Py.println(PyString.fromInterned("shlex: I see EOF in escape state"));
                     }

                     var1.setline(191);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("No escaped character"));
                  }

                  var1.setline(194);
                  var3 = var1.getlocal(2);
                  var10000 = var3._in(var1.getlocal(0).__getattr__("quotes"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(3);
                     var10000 = var3._ne(var1.getlocal(0).__getattr__("state"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(3);
                        var10000 = var3._ne(var1.getlocal(2));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(196);
                     var3 = var1.getlocal(0).__getattr__("token")._add(var1.getlocal(0).__getattr__("state"));
                     var1.getlocal(0).__setattr__("token", var3);
                     var3 = null;
                  }

                  var1.setline(197);
                  var3 = var1.getlocal(0).__getattr__("token")._add(var1.getlocal(3));
                  var1.getlocal(0).__setattr__("token", var3);
                  var3 = null;
                  var1.setline(198);
                  var3 = var1.getlocal(2);
                  var1.getlocal(0).__setattr__("state", var3);
                  var3 = null;
               } else {
                  var1.setline(199);
                  var3 = var1.getlocal(0).__getattr__("state");
                  var10000 = var3._eq(PyString.fromInterned("a"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(200);
                     if (var1.getlocal(3).__not__().__nonzero__()) {
                        var1.setline(201);
                        var3 = var1.getglobal("None");
                        var1.getlocal(0).__setattr__("state", var3);
                        var3 = null;
                        break;
                     }

                     var1.setline(203);
                     var3 = var1.getlocal(3);
                     var10000 = var3._in(var1.getlocal(0).__getattr__("whitespace"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(204);
                        var3 = var1.getlocal(0).__getattr__("debug");
                        var10000 = var3._ge(Py.newInteger(2));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(205);
                           Py.println(PyString.fromInterned("shlex: I see whitespace in word state"));
                        }

                        var1.setline(206);
                        var4 = PyString.fromInterned(" ");
                        var1.getlocal(0).__setattr__((String)"state", var4);
                        var3 = null;
                        var1.setline(207);
                        var10000 = var1.getlocal(0).__getattr__("token");
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getlocal(0).__getattr__("posix");
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getlocal(1);
                           }
                        }

                        if (var10000.__nonzero__()) {
                           break;
                        }
                     } else {
                        var1.setline(211);
                        var3 = var1.getlocal(3);
                        var10000 = var3._in(var1.getlocal(0).__getattr__("commenters"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(212);
                           var1.getlocal(0).__getattr__("instream").__getattr__("readline").__call__(var2);
                           var1.setline(213);
                           var3 = var1.getlocal(0).__getattr__("lineno")._add(Py.newInteger(1));
                           var1.getlocal(0).__setattr__("lineno", var3);
                           var3 = null;
                           var1.setline(214);
                           if (var1.getlocal(0).__getattr__("posix").__nonzero__()) {
                              var1.setline(215);
                              var4 = PyString.fromInterned(" ");
                              var1.getlocal(0).__setattr__((String)"state", var4);
                              var3 = null;
                              var1.setline(216);
                              var10000 = var1.getlocal(0).__getattr__("token");
                              if (!var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(0).__getattr__("posix");
                                 if (var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(1);
                                 }
                              }

                              if (var10000.__nonzero__()) {
                                 break;
                              }
                           }
                        } else {
                           var1.setline(220);
                           var10000 = var1.getlocal(0).__getattr__("posix");
                           if (var10000.__nonzero__()) {
                              var3 = var1.getlocal(3);
                              var10000 = var3._in(var1.getlocal(0).__getattr__("quotes"));
                              var3 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(221);
                              var3 = var1.getlocal(3);
                              var1.getlocal(0).__setattr__("state", var3);
                              var3 = null;
                           } else {
                              var1.setline(222);
                              var10000 = var1.getlocal(0).__getattr__("posix");
                              if (var10000.__nonzero__()) {
                                 var3 = var1.getlocal(3);
                                 var10000 = var3._in(var1.getlocal(0).__getattr__("escape"));
                                 var3 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(223);
                                 var4 = PyString.fromInterned("a");
                                 var1.setlocal(2, var4);
                                 var3 = null;
                                 var1.setline(224);
                                 var3 = var1.getlocal(3);
                                 var1.getlocal(0).__setattr__("state", var3);
                                 var3 = null;
                              } else {
                                 var1.setline(225);
                                 var3 = var1.getlocal(3);
                                 var10000 = var3._in(var1.getlocal(0).__getattr__("wordchars"));
                                 var3 = null;
                                 if (!var10000.__nonzero__()) {
                                    var3 = var1.getlocal(3);
                                    var10000 = var3._in(var1.getlocal(0).__getattr__("quotes"));
                                    var3 = null;
                                    if (!var10000.__nonzero__()) {
                                       var10000 = var1.getlocal(0).__getattr__("whitespace_split");
                                    }
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(227);
                                    var3 = var1.getlocal(0).__getattr__("token")._add(var1.getlocal(3));
                                    var1.getlocal(0).__setattr__("token", var3);
                                    var3 = null;
                                 } else {
                                    var1.setline(229);
                                    var1.getlocal(0).__getattr__("pushback").__getattr__("appendleft").__call__(var2, var1.getlocal(3));
                                    var1.setline(230);
                                    var3 = var1.getlocal(0).__getattr__("debug");
                                    var10000 = var3._ge(Py.newInteger(2));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(231);
                                       Py.println(PyString.fromInterned("shlex: I see punctuation in word state"));
                                    }

                                    var1.setline(232);
                                    var4 = PyString.fromInterned(" ");
                                    var1.getlocal(0).__setattr__((String)"state", var4);
                                    var3 = null;
                                    var1.setline(233);
                                    if (var1.getlocal(0).__getattr__("token").__nonzero__()) {
                                       break;
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

      var1.setline(237);
      var3 = var1.getlocal(0).__getattr__("token");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(238);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"token", var4);
      var3 = null;
      var1.setline(239);
      var10000 = var1.getlocal(0).__getattr__("posix");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__not__();
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(4);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(240);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(241);
      var3 = var1.getlocal(0).__getattr__("debug");
      var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(242);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(243);
            Py.println(PyString.fromInterned("shlex: raw token=")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(4))));
         } else {
            var1.setline(245);
            Py.println(PyString.fromInterned("shlex: raw token=EOF"));
         }
      }

      var1.setline(246);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sourcehook$8(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyString.fromInterned("Hook called on a filename to be sourced.");
      var1.setline(250);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(PyString.fromInterned("\""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(251);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(253);
      var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("infile"), var1.getglobal("basestring"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(254);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(0).__getattr__("infile")), var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(255);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r"))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject error_leader$9(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyString.fromInterned("Emit a C-compiler-like, Emacs-friendly error-message leader.");
      var1.setline(259);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(260);
         var3 = var1.getlocal(0).__getattr__("infile");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(261);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(262);
         var3 = var1.getlocal(0).__getattr__("lineno");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(263);
      var3 = PyString.fromInterned("\"%s\", line %d: ")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$10(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$11(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyObject var3 = var1.getlocal(0).__getattr__("get_token").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(270);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("eof"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(271);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(272);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject split$12(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyObject var10000 = var1.getglobal("shlex");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(2)};
      String[] var4 = new String[]{"posix"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(276);
      var5 = var1.getglobal("True");
      var1.getlocal(3).__setattr__("whitespace_split", var5);
      var3 = null;
      var1.setline(277);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(278);
         PyString var6 = PyString.fromInterned("");
         var1.getlocal(3).__setattr__((String)"commenters", var6);
         var3 = null;
      }

      var1.setline(279);
      var5 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var5;
   }

   public shlex$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      shlex$1 = Py.newCode(0, var2, var1, "shlex", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "instream", "infile", "posix"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 23, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tok"};
      push_token$3 = Py.newCode(2, var2, var1, "push_token", 59, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newstream", "newfile"};
      push_source$4 = Py.newCode(3, var2, var1, "push_source", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop_source$5 = Py.newCode(1, var2, var1, "pop_source", 79, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tok", "raw", "spec", "newfile", "newstream"};
      get_token$6 = Py.newCode(1, var2, var1, "get_token", 88, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "quoted", "escapedstate", "nextchar", "result"};
      read_token$7 = Py.newCode(1, var2, var1, "read_token", 120, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newfile"};
      sourcehook$8 = Py.newCode(2, var2, var1, "sourcehook", 248, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "lineno"};
      error_leader$9 = Py.newCode(3, var2, var1, "error_leader", 257, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$10 = Py.newCode(1, var2, var1, "__iter__", 265, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "token"};
      next$11 = Py.newCode(1, var2, var1, "next", 268, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "comments", "posix", "lex"};
      split$12 = Py.newCode(3, var2, var1, "split", 274, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new shlex$py("shlex$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(shlex$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.shlex$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.push_token$3(var2, var3);
         case 4:
            return this.push_source$4(var2, var3);
         case 5:
            return this.pop_source$5(var2, var3);
         case 6:
            return this.get_token$6(var2, var3);
         case 7:
            return this.read_token$7(var2, var3);
         case 8:
            return this.sourcehook$8(var2, var3);
         case 9:
            return this.error_leader$9(var2, var3);
         case 10:
            return this.__iter__$10(var2, var3);
         case 11:
            return this.next$11(var2, var3);
         case 12:
            return this.split$12(var2, var3);
         default:
            return null;
      }
   }
}
