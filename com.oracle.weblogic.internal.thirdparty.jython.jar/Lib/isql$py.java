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
@Filename("isql.py")
public class isql$py extends PyFunctionTable implements PyRunnable {
   static isql$py self;
   static final PyCode f$0;
   static final PyCode IsqlExit$1;
   static final PyCode Prompt$2;
   static final PyCode __init__$3;
   static final PyCode __str__$4;
   static final PyCode __tojava__$5;
   static final PyCode IsqlCmd$6;
   static final PyCode __init__$7;
   static final PyCode parseline$8;
   static final PyCode do_which$9;
   static final PyCode do_EOF$10;
   static final PyCode do_p$11;
   static final PyCode do_column$12;
   static final PyCode do_use$13;
   static final PyCode do_table$14;
   static final PyCode do_proc$15;
   static final PyCode do_schema$16;
   static final PyCode do_delimiter$17;
   static final PyCode do_o$18;
   static final PyCode do_q$19;
   static final PyCode do_set$20;
   static final PyCode f$21;
   static final PyCode f$22;
   static final PyCode do_i$23;
   static final PyCode default$24;
   static final PyCode f$25;
   static final PyCode emptyline$26;
   static final PyCode postloop$27;
   static final PyCode cmdloop$28;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("dbexts", var1, -1);
      var1.setlocal("dbexts", var3);
      var3 = null;
      var3 = imp.importOne("cmd", var1, -1);
      var1.setlocal("cmd", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(4);
         var3 = imp.importOne("java.lang.String", var1, -1);
         var1.setlocal("java", var3);
         var3 = null;
      }

      var1.setline(9);
      PyString.fromInterned("\nIsql works in conjunction with dbexts to provide an interactive environment\nfor database work.\n");
      var1.setline(11);
      PyObject[] var8 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("IsqlExit", var8, IsqlExit$1);
      var1.setlocal("IsqlExit", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(13);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Prompt", var8, Prompt$2);
      var1.setlocal("Prompt", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(34);
      var8 = new PyObject[]{var1.getname("cmd").__getattr__("Cmd")};
      var4 = Py.makeClass("IsqlCmd", var8, IsqlCmd$6);
      var1.setlocal("IsqlCmd", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(216);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(217);
         var3 = imp.importOne("getopt", var1, -1);
         var1.setlocal("getopt", var3);
         var3 = null;

         PyObject var5;
         try {
            var1.setline(220);
            var3 = var1.getname("getopt").__getattr__("getopt").__call__((ThreadState)var2, var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("b:"), (PyObject)(new PyList(Py.EmptyObjects)));
            PyObject[] var10 = Py.unpackSequence(var3, 2);
            var5 = var10[0];
            var1.setlocal("opts", var5);
            var5 = null;
            var5 = var10[1];
            var1.setlocal("args", var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (!var11.match(var1.getname("getopt").__getattr__("error"))) {
               throw var11;
            }

            var4 = var11.value;
            var1.setlocal("msg", var4);
            var4 = null;
            var1.setline(222);
            Py.println();
            var1.setline(223);
            Py.println(var1.getname("msg"));
            var1.setline(224);
            Py.println(PyString.fromInterned("Try `%s --help` for more information.")._mod(var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0))));
            var1.setline(225);
            var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         }

         var1.setline(227);
         var3 = var1.getname("None");
         var1.setlocal("dbname", var3);
         var3 = null;
         var1.setline(228);
         var3 = var1.getname("opts").__iter__();

         while(true) {
            var1.setline(228);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(232);
               var3 = PyString.fromInterned("\nisql - interactive sql (%s)\n")._mod(var1.getname("__version__"));
               var1.setlocal("intro", var3);
               var3 = null;
               var1.setline(234);
               var3 = var1.getname("IsqlCmd").__call__(var2, var1.getname("dbname"));
               var1.setlocal("isql", var3);
               var3 = null;
               var1.setline(235);
               var1.getname("isql").__getattr__("cmdloop").__call__(var2);
               break;
            }

            PyObject[] var9 = Py.unpackSequence(var4, 2);
            PyObject var6 = var9[0];
            var1.setlocal("opt", var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal("arg", var6);
            var6 = null;
            var1.setline(229);
            var5 = var1.getname("opt");
            var10000 = var5._eq(PyString.fromInterned("-b"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(230);
               var5 = var1.getname("arg");
               var1.setlocal("dbname", var5);
               var5 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IsqlExit$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      return var1.getf_locals();
   }

   public PyObject Prompt$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This class fixes a problem with the cmd.Cmd class since it uses an ivar 'prompt'\n    as opposed to a method 'prompt()'.  To get around this, this class is plugged in\n    as a 'prompt' attribute and when invoked the '__str__' method is called which\n    figures out the appropriate prompt to display.  I still think, even though this\n    is clever, the attribute version of 'prompt' is poor design.\n    "));
      var1.setline(20);
      PyString.fromInterned("\n    This class fixes a problem with the cmd.Cmd class since it uses an ivar 'prompt'\n    as opposed to a method 'prompt()'.  To get around this, this class is plugged in\n    as a 'prompt' attribute and when invoked the '__str__' method is called which\n    figures out the appropriate prompt to display.  I still think, even though this\n    is clever, the attribute version of 'prompt' is poor design.\n    ");
      var1.setline(21);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$4, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(28);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(29);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, __tojava__$5, (PyObject)null);
         var1.setlocal("__tojava__", var4);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("isql", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$4(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = PyString.fromInterned("%s> ")._mod(var1.getlocal(0).__getattr__("isql").__getattr__("db").__getattr__("dbname"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("isql").__getattr__("sqlbuffer"));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(26);
         PyString var4 = PyString.fromInterned("... ");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(27);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __tojava__$5(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("java").__getattr__("lang").__getattr__("String"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(31);
         var3 = var1.getlocal(0).__getattr__("__str__").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(32);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject IsqlCmd$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(36);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned(";"), new PyTuple(new PyObject[]{PyString.fromInterned("#"), PyString.fromInterned("--")})};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseline$8, (PyObject)null);
      var1.setlocal("parseline", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_which$9, PyString.fromInterned("\nPrints the current db connection parameters.\n"));
      var1.setlocal("do_which", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_EOF$10, (PyObject)null);
      var1.setlocal("do_EOF", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_p$11, PyString.fromInterned("\nExecute a python expression.\n"));
      var1.setlocal("do_p", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_column$12, PyString.fromInterned("\nInstructions for column display.\n"));
      var1.setlocal("do_column", var4);
      var3 = null;
      var1.setline(74);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_use$13, PyString.fromInterned("\nUse a new database connection.\n"));
      var1.setlocal("do_use", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_table$14, PyString.fromInterned("\nPrints table meta-data.  If no table name, prints all tables.\n"));
      var1.setlocal("do_table", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_proc$15, PyString.fromInterned("\nPrints store procedure meta-data.\n"));
      var1.setlocal("do_proc", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_schema$16, PyString.fromInterned("\nPrints schema information.\n"));
      var1.setlocal("do_schema", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_delimiter$17, PyString.fromInterned("\nChange the delimiter.\n"));
      var1.setlocal("do_delimiter", var4);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_o$18, PyString.fromInterned("\nSet the output.\n"));
      var1.setlocal("do_o", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_q$19, PyString.fromInterned("\nQuit.\n"));
      var1.setlocal("do_q", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_set$20, PyString.fromInterned("\nSet a parameter. Some examples:\n set owner = 'informix'\n set types = ['VIEW', 'TABLE']\nThe right hand side is evaluated using `eval()`\n"));
      var1.setlocal("do_set", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_i$23, (PyObject)null);
      var1.setlocal("do_i", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, default$24, (PyObject)null);
      var1.setlocal("default", var4);
      var3 = null;
      var1.setline(198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emptyline$26, (PyObject)null);
      var1.setlocal("emptyline", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, postloop$27, (PyObject)null);
      var1.setlocal("postloop", var4);
      var3 = null;
      var1.setline(204);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, cmdloop$28, (PyObject)null);
      var1.setlocal("cmdloop", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyObject var10000 = var1.getglobal("cmd").__getattr__("Cmd").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("None")};
      String[] var4 = new String[]{"completekey"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(38);
      PyObject var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var5 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var5._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(39);
         var5 = var1.getglobal("dbexts").__getattr__("dbexts").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("db", var5);
         var3 = null;
      } else {
         var1.setline(41);
         var5 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("db", var5);
         var3 = null;
      }

      var1.setline(42);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"kw", var6);
      var3 = null;
      var1.setline(43);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"sqlbuffer", var7);
      var3 = null;
      var1.setline(44);
      var5 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("comment", var5);
      var3 = null;
      var1.setline(45);
      var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("delimiter", var5);
      var3 = null;
      var1.setline(46);
      var5 = var1.getglobal("Prompt").__call__(var2, var1.getlocal(0));
      var1.getlocal(0).__setattr__("prompt", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseline$8(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getglobal("cmd").__getattr__("Cmd").__getattr__("parseline").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(50);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._ne(PyString.fromInterned("EOF"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(51);
         var3 = var1.getlocal(2).__getattr__("lower").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(52);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject do_which$9(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("\nPrints the current db connection parameters.\n");
      var1.setline(56);
      Py.println(var1.getlocal(0).__getattr__("db"));
      var1.setline(57);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_EOF$10(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_p$11(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyString.fromInterned("\nExecute a python expression.\n");

      try {
         var1.setline(65);
         Py.exec(var1.getlocal(1).__getattr__("strip").__call__(var2), var1.getname("globals").__call__(var2), (PyObject)null);
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(67);
         Py.println(var1.getname("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1)));
      }

      var1.setline(68);
      PyObject var3 = var1.getname("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_column$12(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyString.fromInterned("\nInstructions for column display.\n");
      var1.setline(72);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_use$13(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyString.fromInterned("\nUse a new database connection.\n");
      var1.setline(77);
      PyObject var3 = var1.getlocal(0).__getattr__("db").__getattr__("__class__").__call__(var2, var1.getlocal(1).__getattr__("strip").__call__(var2));
      var1.getlocal(0).__setattr__("db", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_table$14(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("\nPrints table meta-data.  If no table name, prints all tables.\n");
      var1.setline(82);
      PyObject var10000;
      PyObject[] var3;
      String[] var4;
      if (var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("strip").__call__(var2)).__nonzero__()) {
         var1.setline(83);
         var10000 = var1.getlocal(0).__getattr__("db").__getattr__("table");
         var3 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(0).__getattr__("kw"));
         var3 = null;
      } else {
         var1.setline(85);
         var10000 = var1.getlocal(0).__getattr__("db").__getattr__("table");
         var3 = new PyObject[]{var1.getglobal("None")};
         var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(0).__getattr__("kw"));
         var3 = null;
      }

      var1.setline(86);
      PyObject var5 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject do_proc$15(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("\nPrints store procedure meta-data.\n");
      var1.setline(90);
      PyObject var10000;
      PyObject[] var3;
      String[] var4;
      if (var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("strip").__call__(var2)).__nonzero__()) {
         var1.setline(91);
         var10000 = var1.getlocal(0).__getattr__("db").__getattr__("proc");
         var3 = new PyObject[]{var1.getlocal(1)};
         var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(0).__getattr__("kw"));
         var3 = null;
      } else {
         var1.setline(93);
         var10000 = var1.getlocal(0).__getattr__("db").__getattr__("proc");
         var3 = new PyObject[]{var1.getglobal("None")};
         var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(0).__getattr__("kw"));
         var3 = null;
      }

      var1.setline(94);
      PyObject var5 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject do_schema$16(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("\nPrints schema information.\n");
      var1.setline(98);
      Py.println();
      var1.setline(99);
      var1.getlocal(0).__getattr__("db").__getattr__("schema").__call__(var2, var1.getlocal(1));
      var1.setline(100);
      Py.println();
      var1.setline(101);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_delimiter$17(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("\nChange the delimiter.\n");
      var1.setline(105);
      PyObject var3 = var1.getlocal(1).__getattr__("strip").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("delimiter", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_o$18(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("\nSet the output.\n");
      var1.setline(111);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(112);
         var3 = var1.getlocal(0).__getattr__("db").__getattr__("out");
         var1.setlocal(2, var3);
         var3 = null;
         var3 = null;

         PyObject var4;
         try {
            var1.setline(114);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(115);
               var1.getlocal(2).__getattr__("close").__call__(var2);
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(117);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__getattr__("db").__setattr__("out", var4);
            var4 = null;
            throw (Throwable)var5;
         }

         var1.setline(117);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("db").__setattr__("out", var4);
         var4 = null;
      } else {
         var1.setline(119);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(120);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("db").__setattr__("out", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_q$19(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("\nQuit.\n");
      Object var3 = null;

      PyObject var4;
      try {
         var1.setline(125);
         if (var1.getlocal(0).__getattr__("db").__getattr__("out").__nonzero__()) {
            var1.setline(126);
            var1.getlocal(0).__getattr__("db").__getattr__("out").__getattr__("close").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(128);
         var4 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var4;
      }

      var1.setline(128);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject do_set$20(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("\nSet a parameter. Some examples:\n set owner = 'informix'\n set types = ['VIEW', 'TABLE']\nThe right hand side is evaluated using `eval()`\n");
      var1.setline(132);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("strip").__call__(var2));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyObject var4;
      if (!var10000.__nonzero__()) {
         var1.setline(141);
         var10000 = var1.getglobal("filter");
         var1.setline(141);
         PyObject[] var5 = Py.EmptyObjects;
         PyFunction var10002 = new PyFunction(var1.f_globals, var5, f$21);
         PyObject var10003 = var1.getglobal("map");
         var1.setline(141);
         var5 = Py.EmptyObjects;
         var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$22)), (PyObject)var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="))));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(142);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(143);
            if (var1.getlocal(0).__getattr__("kw").__getattr__("has_key").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0))).__nonzero__()) {
               var1.setline(144);
               var1.getlocal(0).__getattr__("kw").__delitem__(var1.getlocal(4).__getitem__(Py.newInteger(0)));
            }
         } else {
            var1.setline(146);
            var4 = var1.getglobal("eval").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1)));
            var1.getlocal(0).__getattr__("kw").__setitem__(var1.getlocal(4).__getitem__(Py.newInteger(0)), var4);
            var4 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(133);
         var3 = var1.getlocal(0).__getattr__("kw").__getattr__("items").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(134);
         if (var1.getglobal("len").__call__(var2, var1.getlocal(2)).__nonzero__()) {
            var1.setline(135);
            Py.println();
            var1.setline(137);
            var3 = var1.getglobal("dbexts").__getattr__("console").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("key"), PyString.fromInterned("value")}))).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

            while(true) {
               var1.setline(137);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(139);
                  Py.println();
                  break;
               }

               var1.setlocal(3, var4);
               var1.setline(138);
               Py.println(var1.getlocal(3));
            }
         }

         var1.setline(140);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$21(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$22(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_i$23(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(151);
         Py.println();
         var1.setline(152);
         PyObject var4 = var1.getlocal(2).__getattr__("readlines").__call__(var2).__iter__();

         while(true) {
            var1.setline(152);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(3, var5);
            var1.setline(153);
            PyObject var6 = var1.getlocal(0).__getattr__("precmd").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(154);
            var6 = var1.getlocal(0).__getattr__("onecmd").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(155);
            var6 = var1.getlocal(0).__getattr__("postcmd").__call__(var2, var1.getlocal(4), var1.getlocal(3));
            var1.setlocal(4, var6);
            var6 = null;
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(157);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(157);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(158);
      var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject default$24(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(162);
         var3 = var1.getlocal(1).__getattr__("strip").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(163);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(164);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(165);
         PyList var10000 = new PyList();
         PyObject var4 = var10000.__getattr__("append");
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(165);
         var4 = var1.getlocal(0).__getattr__("comment").__iter__();

         while(true) {
            var1.setline(165);
            PyObject var8 = var4.__iternext__();
            if (var8 == null) {
               var1.setline(165);
               var1.dellocal(4);
               PyList var7 = var10000;
               var1.setlocal(3, var7);
               var4 = null;
               var1.setline(166);
               PyObject var11 = var1.getglobal("reduce");
               var1.setline(166);
               PyObject[] var9 = Py.EmptyObjects;
               if (var11.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var9, f$25)), (PyObject)var1.getlocal(3)).__nonzero__()) {
                  var1.setline(167);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(168);
               var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
               var11 = var4._eq(PyString.fromInterned("\\"));
               var4 = null;
               if (var11.__nonzero__()) {
                  var1.setline(169);
                  var4 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                  var1.setlocal(2, var4);
                  var4 = null;
               }

               var1.setline(171);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var11 = var4._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("delimiter")));
               var4 = null;
               if (var11.__nonzero__()) {
                  var1.setline(173);
                  var4 = var1.getlocal(2).__getslice__(Py.newInteger(-1)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("delimiter"))), (PyObject)null, (PyObject)null);
                  var11 = var4._eq(var1.getlocal(0).__getattr__("delimiter"));
                  var4 = null;
                  if (var11.__nonzero__()) {
                     var1.setline(175);
                     var1.getlocal(0).__getattr__("sqlbuffer").__getattr__("append").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("delimiter"))), (PyObject)null));
                     var1.setline(176);
                     if (var1.getlocal(0).__getattr__("sqlbuffer").__nonzero__()) {
                        var1.setline(177);
                        var4 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("sqlbuffer"));
                        var1.setlocal(6, var4);
                        var4 = null;
                        var1.setline(178);
                        Py.println(var1.getlocal(6));
                        var1.setline(179);
                        var11 = var1.getlocal(0).__getattr__("db").__getattr__("isql");
                        var9 = new PyObject[]{var1.getlocal(6)};
                        String[] var10 = new String[0];
                        var11._callextra(var9, var10, (PyObject)null, var1.getlocal(0).__getattr__("kw"));
                        var4 = null;
                        var1.setline(180);
                        var7 = new PyList(Py.EmptyObjects);
                        var1.getlocal(0).__setattr__((String)"sqlbuffer", var7);
                        var4 = null;
                        var1.setline(181);
                        if (var1.getlocal(0).__getattr__("db").__getattr__("updatecount").__nonzero__()) {
                           var1.setline(182);
                           Py.println();
                           var1.setline(183);
                           var4 = var1.getlocal(0).__getattr__("db").__getattr__("updatecount");
                           var11 = var4._eq(Py.newInteger(1));
                           var4 = null;
                           if (var11.__nonzero__()) {
                              var1.setline(184);
                              Py.println(PyString.fromInterned("1 row affected"));
                           } else {
                              var1.setline(186);
                              Py.println(PyString.fromInterned("%d rows affected")._mod(var1.getlocal(0).__getattr__("db").__getattr__("updatecount")));
                           }

                           var1.setline(187);
                           Py.println();
                        }

                        var1.setline(188);
                        var3 = var1.getglobal("False");
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }
               }

               var1.setline(189);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(190);
                  var1.getlocal(0).__getattr__("sqlbuffer").__getattr__("append").__call__(var2, var1.getlocal(2));
               }
               break;
            }

            var1.setlocal(5, var8);
            var1.setline(165);
            var1.getlocal(4).__call__(var2, var1.getlocal(2).__getattr__("startswith").__call__(var2, var1.getlocal(5)));
         }
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(192);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"sqlbuffer", var5);
         var5 = null;
         var1.setline(193);
         Py.println();
         var1.setline(194);
         Py.println(var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1)));
         var1.setline(195);
         Py.println();
      }

      var1.setline(196);
      var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$25(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyObject var10000 = var1.getlocal(0);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emptyline$26(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject postloop$27(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      throw Py.makeException(var1.getglobal("IsqlExit").__call__(var2));
   }

   public PyObject cmdloop$28(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(205);
         if (Py.newInteger(1).__nonzero__()) {
            label31: {
               PyException var3;
               try {
                  var1.setline(207);
                  var1.getglobal("cmd").__getattr__("Cmd").__getattr__("cmdloop").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               } catch (Throwable var5) {
                  var3 = Py.setException(var5, var1);
                  PyObject var4;
                  if (var3.match(var1.getglobal("IsqlExit"))) {
                     var4 = var3.value;
                     var1.setlocal(2, var4);
                     var4 = null;
                     break label31;
                  }

                  if (!var3.match(var1.getglobal("Exception"))) {
                     throw var3;
                  }

                  var4 = var3.value;
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(211);
                  Py.println();
                  var1.setline(212);
                  Py.println(var1.getlocal(2));
                  var1.setline(213);
                  Py.println();
               }

               var1.setline(214);
               PyObject var6 = var1.getglobal("None");
               var1.setlocal(1, var6);
               var3 = null;
               continue;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public isql$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IsqlExit$1 = Py.newCode(0, var2, var1, "IsqlExit", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Prompt$2 = Py.newCode(0, var2, var1, "Prompt", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "isql"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prompt"};
      __str__$4 = Py.newCode(1, var2, var1, "__str__", 23, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cls"};
      __tojava__$5 = Py.newCode(2, var2, var1, "__tojava__", 29, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IsqlCmd$6 = Py.newCode(0, var2, var1, "IsqlCmd", 34, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "db", "delimiter", "comment"};
      __init__$7 = Py.newCode(4, var2, var1, "__init__", 36, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "command", "arg"};
      parseline$8 = Py.newCode(2, var2, var1, "parseline", 48, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_which$9 = Py.newCode(2, var2, var1, "do_which", 54, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_EOF$10 = Py.newCode(2, var2, var1, "do_EOF", 59, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_p$11 = Py.newCode(2, var2, var1, "do_p", 62, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "arg"};
      do_column$12 = Py.newCode(2, var2, var1, "do_column", 70, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_use$13 = Py.newCode(2, var2, var1, "do_use", 74, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_table$14 = Py.newCode(2, var2, var1, "do_table", 80, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_proc$15 = Py.newCode(2, var2, var1, "do_proc", 88, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_schema$16 = Py.newCode(2, var2, var1, "do_schema", 96, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "delimiter"};
      do_delimiter$17 = Py.newCode(2, var2, var1, "do_delimiter", 103, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "fp"};
      do_o$18 = Py.newCode(2, var2, var1, "do_o", 109, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_q$19 = Py.newCode(2, var2, var1, "do_q", 122, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "items", "a", "d"};
      do_set$20 = Py.newCode(2, var2, var1, "do_set", 130, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$21 = Py.newCode(1, var2, var1, "<lambda>", 141, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$22 = Py.newCode(1, var2, var1, "<lambda>", 141, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "fp", "line", "stop"};
      do_i$23 = Py.newCode(2, var2, var1, "do_i", 148, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "token", "comment", "_[165_23]", "x", "q"};
      default$24 = Py.newCode(2, var2, var1, "default", 160, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$25 = Py.newCode(2, var2, var1, "<lambda>", 166, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      emptyline$26 = Py.newCode(1, var2, var1, "emptyline", 198, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      postloop$27 = Py.newCode(1, var2, var1, "postloop", 201, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "intro", "e"};
      cmdloop$28 = Py.newCode(2, var2, var1, "cmdloop", 204, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new isql$py("isql$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(isql$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.IsqlExit$1(var2, var3);
         case 2:
            return this.Prompt$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__str__$4(var2, var3);
         case 5:
            return this.__tojava__$5(var2, var3);
         case 6:
            return this.IsqlCmd$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.parseline$8(var2, var3);
         case 9:
            return this.do_which$9(var2, var3);
         case 10:
            return this.do_EOF$10(var2, var3);
         case 11:
            return this.do_p$11(var2, var3);
         case 12:
            return this.do_column$12(var2, var3);
         case 13:
            return this.do_use$13(var2, var3);
         case 14:
            return this.do_table$14(var2, var3);
         case 15:
            return this.do_proc$15(var2, var3);
         case 16:
            return this.do_schema$16(var2, var3);
         case 17:
            return this.do_delimiter$17(var2, var3);
         case 18:
            return this.do_o$18(var2, var3);
         case 19:
            return this.do_q$19(var2, var3);
         case 20:
            return this.do_set$20(var2, var3);
         case 21:
            return this.f$21(var2, var3);
         case 22:
            return this.f$22(var2, var3);
         case 23:
            return this.do_i$23(var2, var3);
         case 24:
            return this.default$24(var2, var3);
         case 25:
            return this.f$25(var2, var3);
         case 26:
            return this.emptyline$26(var2, var3);
         case 27:
            return this.postloop$27(var2, var3);
         case 28:
            return this.cmdloop$28(var2, var3);
         default:
            return null;
      }
   }
}
