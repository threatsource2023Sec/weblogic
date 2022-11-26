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
@Filename("pwd.py")
public class pwd$py extends PyFunctionTable implements PyRunnable {
   static pwd$py self;
   static final PyCode f$0;
   static final PyCode struct_passwd$1;
   static final PyCode __new__$2;
   static final PyCode __getattr__$3;
   static final PyCode getpwuid$4;
   static final PyCode getpwnam$5;
   static final PyCode getpwall$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nThis module provides access to the Unix password database.\n\nPassword database entries are reported as 7-tuples containing the\nfollowing items from the password database (see `<pwd.h>'), in order:\npw_name, pw_passwd, pw_uid, pw_gid, pw_gecos, pw_dir, pw_shell.  The\nuid and gid items are integers, all others are strings. An exception\nis raised if the entry asked for cannot be found.\n"));
      var1.setline(9);
      PyString.fromInterned("\nThis module provides access to the Unix password database.\n\nPassword database entries are reported as 7-tuples containing the\nfollowing items from the password database (see `<pwd.h>'), in order:\npw_name, pw_passwd, pw_uid, pw_gid, pw_gecos, pw_dir, pw_shell.  The\nuid and gid items are integers, all others are strings. An exception\nis raised if the entry asked for cannot be found.\n");
      var1.setline(11);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("getpwuid"), PyString.fromInterned("getpwnam"), PyString.fromInterned("getpwall")});
      var1.setlocal("__all__", var3);
      var3 = null;

      PyObject var4;
      PyObject[] var7;
      try {
         var1.setline(14);
         String[] var6 = new String[]{"_name", "_posix_impl"};
         var7 = imp.importFrom("os", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("_name", var4);
         var4 = null;
         var4 = var7[1];
         var1.setlocal("_posix_impl", var4);
         var4 = null;
         var1.setline(15);
         var6 = new String[]{"newStringOrUnicode"};
         var7 = imp.importFrom("org.python.core.Py", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("newStringOrUnicode", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(17);
         throw Py.makeException(var1.getname("ImportError"));
      }

      var1.setline(18);
      PyObject var8 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var8);
      var3 = null;
      var1.setline(20);
      var8 = var1.getname("_name");
      PyObject var10000 = var8._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(21);
         throw Py.makeException(var1.getname("ImportError"), PyString.fromInterned("pwd module not supported on Windows"));
      } else {
         var1.setline(23);
         var7 = new PyObject[]{var1.getname("tuple")};
         var4 = Py.makeClass("struct_passwd", var7, struct_passwd$1);
         var1.setlocal("struct_passwd", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(48);
         var7 = Py.EmptyObjects;
         PyFunction var9 = new PyFunction(var1.f_globals, var7, getpwuid$4, PyString.fromInterned("\n    getpwuid(uid) -> (pw_name,pw_passwd,pw_uid,\n                      pw_gid,pw_gecos,pw_dir,pw_shell)\n    Return the password database entry for the given numeric user ID.\n    See pwd.__doc__ for more on password database entries.\n    "));
         var1.setlocal("getpwuid", var9);
         var3 = null;
         var1.setline(63);
         var7 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var7, getpwnam$5, PyString.fromInterned("\n    getpwnam(name) -> (pw_name,pw_passwd,pw_uid,\n                        pw_gid,pw_gecos,pw_dir,pw_shell)\n    Return the password database entry for the given user name.\n    See pwd.__doc__ for more on password database entries.\n    "));
         var1.setlocal("getpwnam", var9);
         var3 = null;
         var1.setline(76);
         var7 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var7, getpwall$6, PyString.fromInterned("\n    getpwall() -> list_of_entries\n    Return a list of all available password database entries,\n    in arbitrary order.\n    See pwd.__doc__ for more on password database entries.\n    "));
         var1.setlocal("getpwall", var9);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject struct_passwd$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    pwd.struct_passwd: Results from getpw*() routines.\n\n    This object may be accessed either as a tuple of\n      (pw_name,pw_passwd,pw_uid,pw_gid,pw_gecos,pw_dir,pw_shell)\n    or via the object attributes as named in the above tuple.\n    "));
      var1.setline(30);
      PyString.fromInterned("\n    pwd.struct_passwd: Results from getpw*() routines.\n\n    This object may be accessed either as a tuple of\n      (pw_name,pw_passwd,pw_uid,pw_gid,pw_gecos,pw_dir,pw_shell)\n    or via the object attributes as named in the above tuple.\n    ");
      var1.setline(32);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("pw_name"), PyString.fromInterned("pw_passwd"), PyString.fromInterned("pw_uid"), PyString.fromInterned("pw_gid"), PyString.fromInterned("pw_gecos"), PyString.fromInterned("pw_dir"), PyString.fromInterned("pw_shell")});
      var1.setlocal("attrs", var3);
      var3 = null;
      var1.setline(35);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(41);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$3, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("newStringOrUnicode").__call__(var2, var1.getlocal(1).__getattr__("loginName")), var1.getglobal("newStringOrUnicode").__call__(var2, var1.getlocal(1).__getattr__("password")), var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("UID")), var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("GID")), var1.getglobal("newStringOrUnicode").__call__(var2, var1.getlocal(1).__getattr__("GECOS")), var1.getglobal("newStringOrUnicode").__call__(var2, var1.getlocal(1).__getattr__("home")), var1.getglobal("newStringOrUnicode").__call__(var2, var1.getlocal(1).__getattr__("shell"))});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(39);
      PyObject var4 = var1.getglobal("tuple").__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __getattr__$3(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(43);
         PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(0).__getattr__("attrs").__getattr__("index").__call__(var2, var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("ValueError"))) {
            var1.setline(45);
            throw Py.makeException(var1.getglobal("AttributeError"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject getpwuid$4(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("\n    getpwuid(uid) -> (pw_name,pw_passwd,pw_uid,\n                      pw_gid,pw_gecos,pw_dir,pw_shell)\n    Return the password database entry for the given numeric user ID.\n    See pwd.__doc__ for more on password database entries.\n    ");
      var1.setline(55);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._gt(var1.getglobal("sys").__getattr__("maxint"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(56);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(57);
         var3 = var1.getglobal("_posix_impl").__getattr__("getpwuid").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(58);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(59);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(0)));
         } else {
            var1.setline(60);
            var3 = var1.getglobal("struct_passwd").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getpwnam$5(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("\n    getpwnam(name) -> (pw_name,pw_passwd,pw_uid,\n                        pw_gid,pw_gecos,pw_dir,pw_shell)\n    Return the password database entry for the given user name.\n    See pwd.__doc__ for more on password database entries.\n    ");
      var1.setline(70);
      PyObject var3 = var1.getglobal("_posix_impl").__getattr__("getpwnam").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(71);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(72);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(73);
         var3 = var1.getglobal("struct_passwd").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getpwall$6(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("\n    getpwall() -> list_of_entries\n    Return a list of all available password database entries,\n    in arbitrary order.\n    See pwd.__doc__ for more on password database entries.\n    ");
      var1.setline(83);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(84);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(85);
         var4 = var1.getglobal("_posix_impl").__getattr__("getpwent").__call__(var2);
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(86);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            break;
         }

         var1.setline(88);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("struct_passwd").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(89);
      var4 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var4;
   }

   public pwd$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      struct_passwd$1 = Py.newCode(0, var2, var1, "struct_passwd", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "pwd"};
      __new__$2 = Py.newCode(2, var2, var1, "__new__", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$3 = Py.newCode(2, var2, var1, "__getattr__", 41, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"uid", "entry"};
      getpwuid$4 = Py.newCode(1, var2, var1, "getpwuid", 48, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "entry"};
      getpwnam$5 = Py.newCode(1, var2, var1, "getpwnam", 63, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"entries", "entry"};
      getpwall$6 = Py.newCode(0, var2, var1, "getpwall", 76, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pwd$py("pwd$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pwd$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.struct_passwd$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this.__getattr__$3(var2, var3);
         case 4:
            return this.getpwuid$4(var2, var3);
         case 5:
            return this.getpwnam$5(var2, var3);
         case 6:
            return this.getpwall$6(var2, var3);
         default:
            return null;
      }
   }
}
