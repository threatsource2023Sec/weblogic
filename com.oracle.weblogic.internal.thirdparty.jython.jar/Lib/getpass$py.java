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
@Filename("getpass.py")
public class getpass$py extends PyFunctionTable implements PyRunnable {
   static getpass$py self;
   static final PyCode f$0;
   static final PyCode jython_getpass$1;
   static final PyCode unix_getpass$2;
   static final PyCode win_getpass$3;
   static final PyCode default_getpass$4;
   static final PyCode _raw_input$5;
   static final PyCode getuser$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utilities to get a password and/or the current user name.\n\ngetpass(prompt) - prompt for a password, with echo turned off\ngetuser() - get the user name from the environment or password database\n\nOn Windows, the msvcrt module will be used.\nOn the Mac EasyDialogs.AskPassword is used, if available.\n\n"));
      var1.setline(9);
      PyString.fromInterned("Utilities to get a password and/or the current user name.\n\ngetpass(prompt) - prompt for a password, with echo turned off\ngetuser() - get the user name from the environment or password database\n\nOn Windows, the msvcrt module will be used.\nOn the Mac EasyDialogs.AskPassword is used, if available.\n\n");
      var1.setline(17);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(18);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(20);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("getpass"), PyString.fromInterned("getuser")});
      var1.setlocal("__all__", var10);
      var3 = null;
      var1.setline(22);
      PyObject[] var12 = new PyObject[]{PyString.fromInterned("Password: "), var1.getname("None")};
      PyFunction var13 = new PyFunction(var1.f_globals, var12, jython_getpass$1, PyString.fromInterned("Prompt for a password, with echo turned off.\n    The prompt is written on stream, by default stdout.\n\n    Restore terminal settings at end.\n    "));
      var1.setlocal("jython_getpass", var13);
      var3 = null;
      var1.setline(38);
      var12 = new PyObject[]{PyString.fromInterned("Password: "), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var12, unix_getpass$2, PyString.fromInterned("Prompt for a password, with echo turned off.\n    The prompt is written on stream, by default stdout.\n\n    Restore terminal settings at end.\n    "));
      var1.setlocal("unix_getpass", var13);
      var3 = null;
      var1.setline(66);
      var12 = new PyObject[]{PyString.fromInterned("Password: "), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var12, win_getpass$3, PyString.fromInterned("Prompt for password with echo off, using Windows getch()."));
      var1.setlocal("win_getpass", var13);
      var3 = null;
      var1.setline(89);
      var12 = new PyObject[]{PyString.fromInterned("Password: "), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var12, default_getpass$4, (PyObject)null);
      var1.setlocal("default_getpass", var13);
      var3 = null;
      var1.setline(94);
      var12 = new PyObject[]{PyString.fromInterned(""), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var12, _raw_input$5, (PyObject)null);
      var1.setlocal("_raw_input", var13);
      var3 = null;
      var1.setline(111);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, getuser$6, PyString.fromInterned("Get the username from the environment or password database.\n\n    First try various environment variables, then the password\n    database.  This works on Windows as long as USERNAME is set.\n\n    "));
      var1.setlocal("getuser", var13);
      var3 = null;

      label46: {
         PyException var4;
         PyObject var11;
         try {
            var1.setline(130);
            var3 = imp.importOne("termios", var1, -1);
            var1.setlocal("termios", var3);
            var3 = null;
            var1.setline(133);
            new PyTuple(new PyObject[]{var1.getname("termios").__getattr__("tcgetattr"), var1.getname("termios").__getattr__("tcsetattr")});
         } catch (Throwable var9) {
            PyException var17 = Py.setException(var9, var1);
            if (var17.match(new PyTuple(new PyObject[]{var1.getname("ImportError"), var1.getname("AttributeError")}))) {
               PyException var5;
               try {
                  var1.setline(136);
                  var11 = imp.importOne("msvcrt", var1, -1);
                  var1.setlocal("msvcrt", var11);
                  var4 = null;
               } catch (Throwable var8) {
                  var4 = Py.setException(var8, var1);
                  if (var4.match(var1.getname("ImportError"))) {
                     PyObject var6;
                     try {
                        var1.setline(139);
                        String[] var14 = new String[]{"AskPassword"};
                        PyObject[] var15 = imp.importFrom("EasyDialogs", var14, var1, -1);
                        var6 = var15[0];
                        var1.setlocal("AskPassword", var6);
                        var6 = null;
                     } catch (Throwable var7) {
                        var5 = Py.setException(var7, var1);
                        if (var5.match(var1.getname("ImportError"))) {
                           var1.setline(141);
                           var6 = var1.getname("os").__getattr__("name");
                           PyObject var10000 = var6._eq(PyString.fromInterned("java"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(142);
                              var6 = var1.getname("jython_getpass");
                              var1.setlocal("getpass", var6);
                              var6 = null;
                           }
                           break label46;
                        }

                        throw var5;
                     }

                     var1.setline(144);
                     var6 = var1.getname("AskPassword");
                     var1.setlocal("getpass", var6);
                     var6 = null;
                     break label46;
                  }

                  throw var4;
               }

               var1.setline(146);
               PyObject var16 = var1.getname("win_getpass");
               var1.setlocal("getpass", var16);
               var5 = null;
               break label46;
            }

            throw var17;
         }

         var1.setline(148);
         var11 = var1.getname("unix_getpass");
         var1.setlocal("getpass", var11);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject jython_getpass$1(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Prompt for a password, with echo turned off.\n    The prompt is written on stream, by default stdout.\n\n    Restore terminal settings at end.\n    ");

      PyObject var3;
      PyObject var4;
      try {
         var1.setline(29);
         var3 = var1.getglobal("sys").__getattr__("_jy_console").__getattr__("reader");
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(31);
         var4 = var1.getglobal("default_getpass").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var4;
      }

      var1.setline(32);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(33);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0));
         var1.setline(34);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(0, var6);
         var3 = null;
      }

      var1.setline(35);
      var4 = var1.getlocal(2).__getattr__("readLine").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("\u0000")).__getattr__("encode").__call__(var2, var1.getglobal("sys").__getattr__("_jy_console").__getattr__("encoding"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject unix_getpass$2(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Prompt for a password, with echo turned off.\n    The prompt is written on stream, by default stdout.\n\n    Restore terminal settings at end.\n    ");
      var1.setline(44);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(45);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      PyObject var4;
      try {
         var1.setline(48);
         var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("fileno").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var7) {
         Py.setException(var7, var1);
         var1.setline(50);
         var4 = var1.getglobal("default_getpass").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var4;
      }

      var1.setline(52);
      var3 = var1.getglobal("termios").__getattr__("tcgetattr").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getlocal(3).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getlocal(4).__getitem__(Py.newInteger(3))._and(var1.getglobal("termios").__getattr__("ECHO").__invert__());
      var1.getlocal(4).__setitem__((PyObject)Py.newInteger(3), var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(57);
         var1.getglobal("termios").__getattr__("tcsetattr").__call__(var2, var1.getlocal(2), var1.getglobal("termios").__getattr__("TCSADRAIN"), var1.getlocal(4));
         var1.setline(58);
         PyObject var5 = var1.getglobal("_raw_input").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(5, var5);
         var5 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(60);
         var1.getglobal("termios").__getattr__("tcsetattr").__call__(var2, var1.getlocal(2), var1.getglobal("termios").__getattr__("TCSADRAIN"), var1.getlocal(3));
         throw (Throwable)var6;
      }

      var1.setline(60);
      var1.getglobal("termios").__getattr__("tcsetattr").__call__(var2, var1.getlocal(2), var1.getglobal("termios").__getattr__("TCSADRAIN"), var1.getlocal(3));
      var1.setline(62);
      var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(63);
      var4 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject win_getpass$3(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Prompt for password with echo off, using Windows getch().");
      var1.setline(68);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdin");
      PyObject var10000 = var3._isnot(var1.getglobal("sys").__getattr__("__stdin__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(69);
         var3 = var1.getglobal("default_getpass").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(70);
         PyObject var4 = imp.importOne("msvcrt", var1, -1);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(71);
         var4 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(71);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(73);
               PyString var6 = PyString.fromInterned("");
               var1.setlocal(4, var6);
               var4 = null;

               while(true) {
                  var1.setline(74);
                  if (!Py.newInteger(1).__nonzero__()) {
                     break;
                  }

                  var1.setline(75);
                  var4 = var1.getlocal(2).__getattr__("getch").__call__(var2);
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(76);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(PyString.fromInterned("\r"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(3);
                     var10000 = var4._eq(PyString.fromInterned("\n"));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(78);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(PyString.fromInterned("\u0003"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(79);
                     throw Py.makeException(var1.getglobal("KeyboardInterrupt"));
                  }

                  var1.setline(80);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(PyString.fromInterned("\b"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(81);
                     var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(4, var4);
                     var4 = null;
                  } else {
                     var1.setline(83);
                     var4 = var1.getlocal(4)._add(var1.getlocal(3));
                     var1.setlocal(4, var4);
                     var4 = null;
                  }
               }

               var1.setline(84);
               var1.getlocal(2).__getattr__("putch").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r"));
               var1.setline(85);
               var1.getlocal(2).__getattr__("putch").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               var1.setline(86);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(72);
            var1.getlocal(2).__getattr__("putch").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject default_getpass$4(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.println(var3, PyString.fromInterned("Warning: Problem with getpass. Passwords may be echoed."));
      var1.setline(91);
      var3 = var1.getglobal("_raw_input").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _raw_input$5(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(98);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(99);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(100);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(101);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0));
         var1.setline(102);
         var1.getlocal(1).__getattr__("flush").__call__(var2);
      }

      var1.setline(103);
      var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("readline").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(104);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(105);
         throw Py.makeException(var1.getglobal("EOFError"));
      } else {
         var1.setline(106);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var10000 = var3._eq(PyString.fromInterned("\n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(107);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(108);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getuser$6(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Get the username from the environment or password database.\n\n    First try various environment variables, then the password\n    database.  This works on Windows as long as USERNAME is set.\n\n    ");
      var1.setline(119);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("LOGNAME"), PyString.fromInterned("USER"), PyString.fromInterned("LNAME"), PyString.fromInterned("USERNAME")})).__iter__();

      PyObject var5;
      do {
         var1.setline(119);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(125);
            var3 = imp.importOne("pwd", var1, -1);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(126);
            var5 = var1.getlocal(2).__getattr__("getpwuid").__call__(var2, var1.getglobal("os").__getattr__("getuid").__call__(var2)).__getitem__(Py.newInteger(0));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(0, var4);
         var1.setline(120);
         var5 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(121);
      } while(!var1.getlocal(1).__nonzero__());

      var1.setline(122);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public getpass$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"prompt", "stream", "reader"};
      jython_getpass$1 = Py.newCode(2, var2, var1, "jython_getpass", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt", "stream", "fd", "old", "new", "passwd"};
      unix_getpass$2 = Py.newCode(2, var2, var1, "unix_getpass", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt", "stream", "msvcrt", "c", "pw"};
      win_getpass$3 = Py.newCode(2, var2, var1, "win_getpass", 66, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt", "stream"};
      default_getpass$4 = Py.newCode(2, var2, var1, "default_getpass", 89, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt", "stream", "line"};
      _raw_input$5 = Py.newCode(2, var2, var1, "_raw_input", 94, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "user", "pwd"};
      getuser$6 = Py.newCode(0, var2, var1, "getuser", 111, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new getpass$py("getpass$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(getpass$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.jython_getpass$1(var2, var3);
         case 2:
            return this.unix_getpass$2(var2, var3);
         case 3:
            return this.win_getpass$3(var2, var3);
         case 4:
            return this.default_getpass$4(var2, var3);
         case 5:
            return this._raw_input$5(var2, var3);
         case 6:
            return this.getuser$6(var2, var3);
         default:
            return null;
      }
   }
}
