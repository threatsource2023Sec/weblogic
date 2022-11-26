import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("readline.py")
public class readline$py extends PyFunctionTable implements PyRunnable {
   static readline$py self;
   static final PyCode f$0;
   static final PyCode NotImplementedWarning$1;
   static final PyCode SecurityWarning$2;
   static final PyCode parse_and_bind$3;
   static final PyCode get_line_buffer$4;
   static final PyCode insert_text$5;
   static final PyCode read_init_file$6;
   static final PyCode read_history_file$7;
   static final PyCode write_history_file$8;
   static final PyCode clear_history$9;
   static final PyCode add_history$10;
   static final PyCode get_history_length$11;
   static final PyCode set_history_length$12;
   static final PyCode get_current_history_length$13;
   static final PyCode get_history_item$14;
   static final PyCode remove_history_item$15;
   static final PyCode replace_history_item$16;
   static final PyCode redisplay$17;
   static final PyCode set_startup_hook$18;
   static final PyCode set_pre_input_hook$19;
   static final PyCode set_completer$20;
   static final PyCode complete_handler$21;
   static final PyCode get_completer$22;
   static final PyCode _get_delimited$23;
   static final PyCode get_begidx$24;
   static final PyCode get_endidx$25;
   static final PyCode set_completer_delims$26;
   static final PyCode get_completer_delims$27;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("os.path", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      String[] var8 = new String[]{"warn"};
      PyObject[] var9 = imp.importFrom("warnings", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("warn", var4);
      var4 = null;

      PyException var11;
      try {
         var1.setline(6);
         var3 = var1.getname("sys").__getattr__("_jy_console");
         var1.setlocal("_console", var3);
         var3 = null;
         var1.setline(7);
         var3 = var1.getname("_console").__getattr__("reader");
         var1.setlocal("_reader", var3);
         var3 = null;
      } catch (Throwable var6) {
         var11 = Py.setException(var6, var1);
         if (var11.match(var1.getname("AttributeError"))) {
            var1.setline(9);
            throw Py.makeException(var1.getname("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot access JLine2 setup")));
         }

         throw var11;
      }

      try {
         var1.setline(13);
         var8 = new String[]{"MemoryHistory"};
         var9 = imp.importFrom("org.python.jline.console.history", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("MemoryHistory", var4);
         var4 = null;
      } catch (Throwable var7) {
         var11 = Py.setException(var7, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(16);
         String[] var10 = new String[]{"MemoryHistory"};
         PyObject[] var12 = imp.importFrom("jline.console.history", var10, var1, -1);
         PyObject var5 = var12[0];
         var1.setlocal("MemoryHistory", var5);
         var5 = null;
      }

      var1.setline(19);
      PyList var13 = new PyList(new PyObject[]{PyString.fromInterned("add_history"), PyString.fromInterned("clear_history"), PyString.fromInterned("get_begidx"), PyString.fromInterned("get_completer"), PyString.fromInterned("get_completer_delims"), PyString.fromInterned("get_current_history_length"), PyString.fromInterned("get_endidx"), PyString.fromInterned("get_history_item"), PyString.fromInterned("get_history_length"), PyString.fromInterned("get_line_buffer"), PyString.fromInterned("insert_text"), PyString.fromInterned("parse_and_bind"), PyString.fromInterned("read_history_file"), PyString.fromInterned("read_init_file"), PyString.fromInterned("redisplay"), PyString.fromInterned("remove_history_item"), PyString.fromInterned("set_completer"), PyString.fromInterned("set_completer_delims"), PyString.fromInterned("set_history_length"), PyString.fromInterned("set_pre_input_hook"), PyString.fromInterned("set_startup_hook"), PyString.fromInterned("write_history_file")});
      var1.setlocal("__all__", var13);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("None");
      var1.setlocal("_history_list", var3);
      var3 = null;
      var1.setline(35);
      var9 = new PyObject[]{var1.getname("ImportWarning")};
      var4 = Py.makeClass("NotImplementedWarning", var9, NotImplementedWarning$1);
      var1.setlocal("NotImplementedWarning", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(38);
      var9 = new PyObject[]{var1.getname("ImportWarning")};
      var4 = Py.makeClass("SecurityWarning", var9, SecurityWarning$2);
      var1.setlocal("SecurityWarning", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(42);
      var9 = Py.EmptyObjects;
      PyFunction var14 = new PyFunction(var1.f_globals, var9, parse_and_bind$3, (PyObject)null);
      var1.setlocal("parse_and_bind", var14);
      var3 = null;
      var1.setline(45);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_line_buffer$4, (PyObject)null);
      var1.setlocal("get_line_buffer", var14);
      var3 = null;
      var1.setline(48);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, insert_text$5, (PyObject)null);
      var1.setlocal("insert_text", var14);
      var3 = null;
      var1.setline(51);
      var9 = new PyObject[]{var1.getname("None")};
      var14 = new PyFunction(var1.f_globals, var9, read_init_file$6, (PyObject)null);
      var1.setlocal("read_init_file", var14);
      var3 = null;
      var1.setline(54);
      var9 = new PyObject[]{PyString.fromInterned("~/.history")};
      var14 = new PyFunction(var1.f_globals, var9, read_history_file$7, (PyObject)null);
      var1.setlocal("read_history_file", var14);
      var3 = null;
      var1.setline(59);
      var9 = new PyObject[]{PyString.fromInterned("~/.history")};
      var14 = new PyFunction(var1.f_globals, var9, write_history_file$8, (PyObject)null);
      var1.setlocal("write_history_file", var14);
      var3 = null;
      var1.setline(66);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, clear_history$9, (PyObject)null);
      var1.setlocal("clear_history", var14);
      var3 = null;
      var1.setline(69);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, add_history$10, (PyObject)null);
      var1.setlocal("add_history", var14);
      var3 = null;
      var1.setline(72);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_history_length$11, (PyObject)null);
      var1.setlocal("get_history_length", var14);
      var3 = null;
      var1.setline(75);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, set_history_length$12, (PyObject)null);
      var1.setlocal("set_history_length", var14);
      var3 = null;
      var1.setline(78);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_current_history_length$13, (PyObject)null);
      var1.setlocal("get_current_history_length", var14);
      var3 = null;
      var1.setline(81);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_history_item$14, (PyObject)null);
      var1.setlocal("get_history_item", var14);
      var3 = null;
      var1.setline(88);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, remove_history_item$15, (PyObject)null);
      var1.setlocal("remove_history_item", var14);
      var3 = null;
      var1.setline(91);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, replace_history_item$16, (PyObject)null);
      var1.setlocal("replace_history_item", var14);
      var3 = null;
      var1.setline(94);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, redisplay$17, (PyObject)null);
      var1.setlocal("redisplay", var14);
      var3 = null;
      var1.setline(97);
      var9 = new PyObject[]{var1.getname("None")};
      var14 = new PyFunction(var1.f_globals, var9, set_startup_hook$18, (PyObject)null);
      var1.setlocal("set_startup_hook", var14);
      var3 = null;
      var1.setline(100);
      var9 = new PyObject[]{var1.getname("None")};
      var14 = new PyFunction(var1.f_globals, var9, set_pre_input_hook$19, (PyObject)null);
      var1.setlocal("set_pre_input_hook", var14);
      var3 = null;
      var1.setline(103);
      var3 = var1.getname("None");
      var1.setlocal("_completer_function", var3);
      var3 = null;
      var1.setline(105);
      var9 = new PyObject[]{var1.getname("None")};
      var14 = new PyFunction(var1.f_globals, var9, set_completer$20, PyString.fromInterned("set_completer([function]) -> None\n    Set or remove the completer function.\n    The function is called as function(text, state),\n    for state in 0, 1, 2, ..., until it returns a non-string.\n    It should return the next possible completion starting with 'text'."));
      var1.setlocal("set_completer", var14);
      var3 = null;
      var1.setline(159);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_completer$22, (PyObject)null);
      var1.setlocal("get_completer", var14);
      var3 = null;
      var1.setline(162);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, _get_delimited$23, (PyObject)null);
      var1.setlocal("_get_delimited", var14);
      var3 = null;
      var1.setline(170);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_begidx$24, (PyObject)null);
      var1.setlocal("get_begidx", var14);
      var3 = null;
      var1.setline(173);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_endidx$25, (PyObject)null);
      var1.setlocal("get_endidx", var14);
      var3 = null;
      var1.setline(176);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, set_completer_delims$26, (PyObject)null);
      var1.setlocal("set_completer_delims", var14);
      var3 = null;
      var1.setline(181);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_completer_delims$27, (PyObject)null);
      var1.setlocal("get_completer_delims", var14);
      var3 = null;
      var1.setline(184);
      var1.getname("set_completer_delims").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" \t\n`~!@#$%^&*()-=+[{]}\\|;:'\",<>/?"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NotImplementedWarning$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Not yet implemented by Jython"));
      var1.setline(36);
      PyString.fromInterned("Not yet implemented by Jython");
      return var1.getf_locals();
   }

   public PyObject SecurityWarning$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Security manager prevents access to private field"));
      var1.setline(39);
      PyString.fromInterned("Security manager prevents access to private field");
      return var1.getf_locals();
   }

   public PyObject parse_and_bind$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_line_buffer$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getglobal("_reader").__getattr__("cursorBuffer").__getattr__("buffer"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject insert_text$5(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      var1.getglobal("_reader").__getattr__("putString").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_init_file$6(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      var1.getglobal("warn").__call__(var2, PyString.fromInterned("read_init_file: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})), var1.getglobal("NotImplementedWarning"), PyString.fromInterned("module"), Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_history_file$7(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(55);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var6;
      PyObject var4 = (var6 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(1)))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(2, var4);
            var1.setline(57);
            var1.getglobal("_reader").__getattr__("history").__getattr__("load").__call__(var2, var1.getlocal(2));
         } catch (Throwable var5) {
            if (var6.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var6.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_history_file$8(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(60);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var7;
      PyObject var4 = (var7 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w")))).__enter__(var2);

      label23: {
         try {
            var1.setlocal(2, var4);
            var1.setline(62);
            var4 = var1.getglobal("_reader").__getattr__("history").__getattr__("entries").__call__(var2).__iter__();

            while(true) {
               var1.setline(62);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(3, var5);
               var1.setline(63);
               var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(3).__getattr__("value").__call__(var2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8")));
               var1.setline(64);
               var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            }
         } catch (Throwable var6) {
            if (var7.__exit__(var2, Py.setException(var6, var1))) {
               break label23;
            }

            throw (Throwable)Py.makeException();
         }

         var7.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_history$9(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      var1.getglobal("_reader").__getattr__("history").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_history$10(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      var1.getglobal("_reader").__getattr__("history").__getattr__("add").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_history_length$11(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getglobal("_reader").__getattr__("history").__getattr__("maxSize");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_history_length$12(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0);
      var1.getglobal("_reader").__getattr__("history").__setattr__("maxSize", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_current_history_length$13(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getglobal("_reader").__getattr__("history").__getattr__("size").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_history_item$14(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(84);
         var3 = var1.getglobal("_reader").__getattr__("history").__getattr__("get").__call__(var2, var1.getlocal(0)._sub(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(86);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject remove_history_item$15(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      var1.getglobal("_reader").__getattr__("history").__getattr__("remove").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject replace_history_item$16(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      var1.getglobal("_reader").__getattr__("history").__getattr__("set").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject redisplay$17(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      var1.getglobal("_reader").__getattr__("redrawLine").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_startup_hook$18(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = var1.getlocal(0);
      var1.getglobal("_console").__setattr__("startupHook", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_pre_input_hook$19(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var10000 = var1.getglobal("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("set_pre_input_hook %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})), var1.getglobal("NotImplementedWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_completer$20(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(110);
      PyString.fromInterned("set_completer([function]) -> None\n    Set or remove the completer function.\n    The function is called as function(text, state),\n    for state in 0, 1, 2, ..., until it returns a non-string.\n    It should return the next possible completion starting with 'text'.");
      var1.setline(113);
      PyObject var3 = var1.getderef(0);
      var1.setglobal("_completer_function", var3);
      var3 = null;
      var1.setline(115);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = complete_handler$21;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(156);
      var1.getglobal("_reader").__getattr__("addCompleter").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject complete_handler$21(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = var1.getglobal("_get_delimited").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(117);
      var3 = var1.getlocal(0).__getslice__(var1.getlocal(3), var1.getlocal(1), (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(120);
         var1.getglobal("sys").__getattr__("ps2");
         var1.setline(121);
         var3 = var1.getglobal("True");
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var8) {
         PyException var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getglobal("AttributeError"))) {
            throw var9;
         }

         var1.setline(123);
         var4 = var1.getglobal("False");
         var1.setlocal(5, var4);
         var4 = null;
      }

      var1.setline(125);
      PyObject var10000 = var1.getlocal(5);
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("_reader").__getattr__("prompt");
         var10000 = var3._eq(var1.getglobal("sys").__getattr__("ps2"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(4).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__getattr__("isspace").__call__(var2);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(135);
         var1.getlocal(2).__getattr__("add").__call__(var2, PyString.fromInterned(" ")._mul(Py.newInteger(4)));
         var1.setline(136);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(144);
         var4 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(100)).__iter__();

         while(true) {
            var1.setline(144);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(145);
            PyObject var6 = var1.getglobal("None");
            var1.setlocal(7, var6);
            var6 = null;

            try {
               var1.setline(147);
               var6 = var1.getderef(0).__call__(var2, var1.getlocal(4), var1.getlocal(6));
               var1.setlocal(7, var6);
               var6 = null;
            } catch (Throwable var7) {
               Py.setException(var7, var1);
               var1.setline(149);
            }

            var1.setline(150);
            if (!var1.getlocal(7).__nonzero__()) {
               break;
            }

            var1.setline(151);
            var1.getlocal(2).__getattr__("add").__call__(var2, var1.getlocal(7));
         }

         var1.setline(154);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_completer$22(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyObject var3 = var1.getglobal("_completer_function");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_delimited$23(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(164);
      var3 = var1.getglobal("xrange").__call__((ThreadState)var2, var1.getlocal(1)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(-1), (PyObject)Py.newInteger(-1)).__iter__();

      while(true) {
         var1.setline(164);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(165);
         PyObject var5 = var1.getlocal(0).__getitem__(var1.getlocal(3));
         PyObject var10000 = var5._in(var1.getglobal("_completer_delims"));
         var5 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(167);
         var5 = var1.getlocal(3);
         var1.setlocal(2, var5);
         var5 = null;
      }

      var1.setline(168);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject get_begidx$24(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3 = var1.getglobal("_get_delimited").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("_reader").__getattr__("cursorBuffer").__getattr__("buffer")), var1.getglobal("_reader").__getattr__("cursorBuffer").__getattr__("cursor")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_endidx$25(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getglobal("_get_delimited").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("_reader").__getattr__("cursorBuffer").__getattr__("buffer")), var1.getglobal("_reader").__getattr__("cursorBuffer").__getattr__("cursor")).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_completer_delims$26(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3 = var1.getlocal(0);
      var1.setglobal("_completer_delims", var3);
      var3 = null;
      var1.setline(179);
      var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0));
      var1.setglobal("_completer_delims_set", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_completer_delims$27(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getglobal("_completer_delims");
      var1.f_lasti = -1;
      return var3;
   }

   public readline$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NotImplementedWarning$1 = Py.newCode(0, var2, var1, "NotImplementedWarning", 35, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SecurityWarning$2 = Py.newCode(0, var2, var1, "SecurityWarning", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"string"};
      parse_and_bind$3 = Py.newCode(1, var2, var1, "parse_and_bind", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_line_buffer$4 = Py.newCode(0, var2, var1, "get_line_buffer", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string"};
      insert_text$5 = Py.newCode(1, var2, var1, "insert_text", 48, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename"};
      read_init_file$6 = Py.newCode(1, var2, var1, "read_init_file", 51, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "expanded", "f"};
      read_history_file$7 = Py.newCode(1, var2, var1, "read_history_file", 54, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "expanded", "f", "line"};
      write_history_file$8 = Py.newCode(1, var2, var1, "write_history_file", 59, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      clear_history$9 = Py.newCode(0, var2, var1, "clear_history", 66, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line"};
      add_history$10 = Py.newCode(1, var2, var1, "add_history", 69, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_history_length$11 = Py.newCode(0, var2, var1, "get_history_length", 72, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"length"};
      set_history_length$12 = Py.newCode(1, var2, var1, "set_history_length", 75, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_current_history_length$13 = Py.newCode(0, var2, var1, "get_current_history_length", 78, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"index"};
      get_history_item$14 = Py.newCode(1, var2, var1, "get_history_item", 81, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pos"};
      remove_history_item$15 = Py.newCode(1, var2, var1, "remove_history_item", 88, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pos", "line"};
      replace_history_item$16 = Py.newCode(2, var2, var1, "replace_history_item", 91, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      redisplay$17 = Py.newCode(0, var2, var1, "redisplay", 94, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"function"};
      set_startup_hook$18 = Py.newCode(1, var2, var1, "set_startup_hook", 97, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"function"};
      set_pre_input_hook$19 = Py.newCode(1, var2, var1, "set_pre_input_hook", 100, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"function", "complete_handler"};
      String[] var10001 = var2;
      readline$py var10007 = self;
      var2 = new String[]{"function"};
      set_completer$20 = Py.newCode(1, var10001, var1, "set_completer", 105, false, false, var10007, 20, var2, (String[])null, 0, 4097);
      var2 = new String[]{"buffer", "cursor", "candidates", "start", "delimited", "have_ps2", "state", "completion"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"function"};
      complete_handler$21 = Py.newCode(3, var10001, var1, "complete_handler", 115, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      get_completer$22 = Py.newCode(0, var2, var1, "get_completer", 159, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"buffer", "cursor", "start", "i"};
      _get_delimited$23 = Py.newCode(2, var2, var1, "_get_delimited", 162, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_begidx$24 = Py.newCode(0, var2, var1, "get_begidx", 170, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_endidx$25 = Py.newCode(0, var2, var1, "get_endidx", 173, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string"};
      set_completer_delims$26 = Py.newCode(1, var2, var1, "set_completer_delims", 176, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_completer_delims$27 = Py.newCode(0, var2, var1, "get_completer_delims", 181, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new readline$py("readline$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(readline$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NotImplementedWarning$1(var2, var3);
         case 2:
            return this.SecurityWarning$2(var2, var3);
         case 3:
            return this.parse_and_bind$3(var2, var3);
         case 4:
            return this.get_line_buffer$4(var2, var3);
         case 5:
            return this.insert_text$5(var2, var3);
         case 6:
            return this.read_init_file$6(var2, var3);
         case 7:
            return this.read_history_file$7(var2, var3);
         case 8:
            return this.write_history_file$8(var2, var3);
         case 9:
            return this.clear_history$9(var2, var3);
         case 10:
            return this.add_history$10(var2, var3);
         case 11:
            return this.get_history_length$11(var2, var3);
         case 12:
            return this.set_history_length$12(var2, var3);
         case 13:
            return this.get_current_history_length$13(var2, var3);
         case 14:
            return this.get_history_item$14(var2, var3);
         case 15:
            return this.remove_history_item$15(var2, var3);
         case 16:
            return this.replace_history_item$16(var2, var3);
         case 17:
            return this.redisplay$17(var2, var3);
         case 18:
            return this.set_startup_hook$18(var2, var3);
         case 19:
            return this.set_pre_input_hook$19(var2, var3);
         case 20:
            return this.set_completer$20(var2, var3);
         case 21:
            return this.complete_handler$21(var2, var3);
         case 22:
            return this.get_completer$22(var2, var3);
         case 23:
            return this._get_delimited$23(var2, var3);
         case 24:
            return this.get_begidx$24(var2, var3);
         case 25:
            return this.get_endidx$25(var2, var3);
         case 26:
            return this.set_completer_delims$26(var2, var3);
         case 27:
            return this.get_completer_delims$27(var2, var3);
         default:
            return null;
      }
   }
}
