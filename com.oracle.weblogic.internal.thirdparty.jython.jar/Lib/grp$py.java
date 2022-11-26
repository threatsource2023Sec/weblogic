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
@Filename("grp.py")
public class grp$py extends PyFunctionTable implements PyRunnable {
   static grp$py self;
   static final PyCode f$0;
   static final PyCode struct_group$1;
   static final PyCode __new__$2;
   static final PyCode __getattr__$3;
   static final PyCode getgrgid$4;
   static final PyCode getgrnam$5;
   static final PyCode getgrall$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nAccess to the Unix group database.\n\nGroup entries are reported as 4-tuples containing the following fields\nfrom the group database, in order:\n\n  name   - name of the group\n  passwd - group password (encrypted); often empty\n  gid    - numeric ID of the group\n  mem    - list of members\n\nThe gid is an integer, name and password are strings.  (Note that most\nusers are not explicitly listed as members of the groups they are in\naccording to the password database.  Check both databases to get\ncomplete membership information.)\n"));
      var1.setline(16);
      PyString.fromInterned("\nAccess to the Unix group database.\n\nGroup entries are reported as 4-tuples containing the following fields\nfrom the group database, in order:\n\n  name   - name of the group\n  passwd - group password (encrypted); often empty\n  gid    - numeric ID of the group\n  mem    - list of members\n\nThe gid is an integer, name and password are strings.  (Note that most\nusers are not explicitly listed as members of the groups they are in\naccording to the password database.  Check both databases to get\ncomplete membership information.)\n");
      var1.setline(18);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("getgrgid"), PyString.fromInterned("getgrnam"), PyString.fromInterned("getgrall")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(20);
      String[] var5 = new String[]{"_name", "_posix_impl"};
      PyObject[] var6 = imp.importFrom("os", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_name", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("_posix_impl", var4);
      var4 = null;
      var1.setline(21);
      var5 = new String[]{"newString"};
      var6 = imp.importFrom("org.python.core.Py", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("newString", var4);
      var4 = null;
      var1.setline(23);
      PyObject var7 = var1.getname("_name");
      PyObject var10000 = var7._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(24);
         throw Py.makeException(var1.getname("ImportError"), PyString.fromInterned("grp module not supported on Windows"));
      } else {
         var1.setline(26);
         var6 = new PyObject[]{var1.getname("tuple")};
         var4 = Py.makeClass("struct_group", var6, struct_group$1);
         var1.setlocal("struct_group", var4);
         var4 = null;
         Arrays.fill(var6, (Object)null);
         var1.setline(49);
         var6 = Py.EmptyObjects;
         PyFunction var8 = new PyFunction(var1.f_globals, var6, getgrgid$4, PyString.fromInterned("\n    getgrgid(id) -> tuple\n    Return the group database entry for the given numeric group ID.  If\n    id is not valid, raise KeyError.\n    "));
         var1.setlocal("getgrgid", var8);
         var3 = null;
         var1.setline(61);
         var6 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var6, getgrnam$5, PyString.fromInterned("\n    getgrnam(name) -> tuple\n    Return the group database entry for the given group name.  If\n    name is not valid, raise KeyError.\n    "));
         var1.setlocal("getgrnam", var8);
         var3 = null;
         var1.setline(73);
         var6 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var6, getgrall$6, PyString.fromInterned("\n    getgrall() -> list of tuples\n    Return a list of all available group database entries,\n    in arbitrary order.\n    "));
         var1.setlocal("getgrall", var8);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject struct_group$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    grp.struct_group: Results from getgr*() routines.\n\n    This object may be accessed either as a tuple of\n      (gr_name,gr_passwd,gr_gid,gr_mem)\n    or via the object attributes as named in the above tuple.\n    "));
      var1.setline(33);
      PyString.fromInterned("\n    grp.struct_group: Results from getgr*() routines.\n\n    This object may be accessed either as a tuple of\n      (gr_name,gr_passwd,gr_gid,gr_mem)\n    or via the object attributes as named in the above tuple.\n    ");
      var1.setline(35);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("gr_name"), PyString.fromInterned("gr_passwd"), PyString.fromInterned("gr_gid"), PyString.fromInterned("gr_mem")});
      var1.setlocal("attrs", var3);
      var3 = null;
      var1.setline(37);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(42);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$3, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyTuple var10000 = new PyTuple;
      PyObject[] var10002 = new PyObject[]{var1.getglobal("newString").__call__(var2, var1.getlocal(1).__getattr__("name")), var1.getglobal("newString").__call__(var2, var1.getlocal(1).__getattr__("password")), var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("GID")), null};
      PyList var10005 = new PyList();
      PyObject var3 = var10005.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(1).__getattr__("members").__iter__();

      while(true) {
         var1.setline(39);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(39);
            var1.dellocal(2);
            var10002[3] = var10005;
            var10000.<init>(var10002);
            PyTuple var5 = var10000;
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(40);
            var3 = var1.getglobal("tuple").__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(39);
         var1.getlocal(2).__call__(var2, var1.getglobal("newString").__call__(var2, var1.getlocal(3)));
      }
   }

   public PyObject __getattr__$3(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(44);
         PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(0).__getattr__("attrs").__getattr__("index").__call__(var2, var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("ValueError"))) {
            var1.setline(46);
            throw Py.makeException(var1.getglobal("AttributeError"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject getgrgid$4(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("\n    getgrgid(id) -> tuple\n    Return the group database entry for the given numeric group ID.  If\n    id is not valid, raise KeyError.\n    ");
      var1.setline(55);
      PyObject var3 = var1.getglobal("_posix_impl").__getattr__("getgrgid").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(56);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(57);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(58);
         var3 = var1.getglobal("struct_group").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getgrnam$5(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("\n    getgrnam(name) -> tuple\n    Return the group database entry for the given group name.  If\n    name is not valid, raise KeyError.\n    ");
      var1.setline(67);
      PyObject var3 = var1.getglobal("_posix_impl").__getattr__("getgrnam").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(68);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(69);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(70);
         var3 = var1.getglobal("struct_group").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getgrall$6(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyString.fromInterned("\n    getgrall() -> list of tuples\n    Return a list of all available group database entries,\n    in arbitrary order.\n    ");
      var1.setline(79);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(80);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(81);
         var4 = var1.getglobal("_posix_impl").__getattr__("getgrent").__call__(var2);
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(82);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            break;
         }

         var1.setline(84);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("struct_group").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(85);
      var4 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var4;
   }

   public grp$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      struct_group$1 = Py.newCode(0, var2, var1, "struct_group", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "grp", "_[39_16]", "member"};
      __new__$2 = Py.newCode(2, var2, var1, "__new__", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$3 = Py.newCode(2, var2, var1, "__getattr__", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"uid", "entry"};
      getgrgid$4 = Py.newCode(1, var2, var1, "getgrgid", 49, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "entry"};
      getgrnam$5 = Py.newCode(1, var2, var1, "getgrnam", 61, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"groups", "group"};
      getgrall$6 = Py.newCode(0, var2, var1, "getgrall", 73, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new grp$py("grp$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(grp$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.struct_group$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this.__getattr__$3(var2, var3);
         case 4:
            return this.getgrgid$4(var2, var3);
         case 5:
            return this.getgrnam$5(var2, var3);
         case 6:
            return this.getgrall$6(var2, var3);
         default:
            return null;
      }
   }
}
