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
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PySet;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("netrc.py")
public class netrc$py extends PyFunctionTable implements PyRunnable {
   static netrc$py self;
   static final PyCode f$0;
   static final PyCode NetrcParseError$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode netrc$4;
   static final PyCode __init__$5;
   static final PyCode _parse$6;
   static final PyCode authenticators$7;
   static final PyCode __repr__$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("An object-oriented interface to .netrc files."));
      var1.setline(1);
      PyString.fromInterned("An object-oriented interface to .netrc files.");
      var1.setline(5);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("shlex", var1, -1);
      var1.setlocal("shlex", var3);
      var3 = null;
      var1.setline(7);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("netrc"), PyString.fromInterned("NetrcParseError")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(10);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("NetrcParseError", var6, NetrcParseError$1);
      var1.setlocal("NetrcParseError", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(22);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("netrc", var6, netrc$4);
      var1.setlocal("netrc", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(121);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(122);
         Py.println(var1.getname("netrc").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NetrcParseError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised on syntax errors in the .netrc file."));
      var1.setline(11);
      PyString.fromInterned("Exception raised on syntax errors in the .netrc file.");
      var1.setline(12);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(14);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(15);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(16);
      var1.getglobal("Exception").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = PyString.fromInterned("%s (%s, line %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("msg"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("lineno")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject netrc$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse$6, (PyObject)null);
      var1.setlocal("_parse", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, authenticators$7, PyString.fromInterned("Return a (user, account, password) tuple for given host."));
      var1.setlocal("authenticators", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$8, PyString.fromInterned("Dump the class data in the format of a .netrc file."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(24);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(26);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME")), (PyObject)PyString.fromInterned(".netrc"));
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               var1.setline(28);
               throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Could not find .netrc: $HOME is not set")));
            }

            throw var7;
         }
      }

      var1.setline(29);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"hosts", var8);
      var3 = null;
      var1.setline(30);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"macros", var8);
      var3 = null;
      ContextManager var9;
      PyObject var4 = (var9 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(1)))).__enter__(var2);

      label29: {
         try {
            var1.setlocal(2, var4);
            var1.setline(32);
            var1.getlocal(0).__getattr__("_parse").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } catch (Throwable var6) {
            if (var9.__exit__(var2, Py.setException(var6, var1))) {
               break label29;
            }

            throw (Throwable)Py.makeException();
         }

         var9.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _parse$6(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getglobal("shlex").__getattr__("shlex").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(36);
      PyObject var10000 = var1.getlocal(3);
      String var6 = "wordchars";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(PyString.fromInterned("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"));
      var4.__setattr__(var6, var5);
      var1.setline(37);
      var3 = var1.getlocal(3).__getattr__("commenters").__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"), (PyObject)PyString.fromInterned(""));
      var1.getlocal(3).__setattr__("commenters", var3);
      var3 = null;

      label83:
      while(true) {
         var1.setline(38);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(40);
         var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
         var1.setlocal(4, var3);
         var1.setlocal(5, var3);
         var1.setline(41);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(43);
         var3 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("#"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(46);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._add(Py.newInteger(1));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(47);
            var1.getlocal(3).__getattr__("instream").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__neg__(), (PyObject)Py.newInteger(1));
            var1.setline(48);
            var1.getlocal(3).__getattr__("instream").__getattr__("readline").__call__(var2);
         } else {
            var1.setline(50);
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned("machine"));
            var3 = null;
            PyString var7;
            if (var10000.__nonzero__()) {
               var1.setline(51);
               var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
               var1.setlocal(7, var3);
               var3 = null;
            } else {
               var1.setline(52);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(PyString.fromInterned("default"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(54);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(PyString.fromInterned("macdef"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(66);
                     throw Py.makeException(var1.getglobal("NetrcParseError").__call__(var2, PyString.fromInterned("bad toplevel token %r")._mod(var1.getlocal(5)), var1.getlocal(1), var1.getlocal(3).__getattr__("lineno")));
                  }

                  var1.setline(55);
                  var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(56);
                  PyList var10 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__getattr__("macros").__setitem__((PyObject)var1.getlocal(7), var10);
                  var3 = null;
                  var1.setline(57);
                  var7 = PyString.fromInterned(" \t");
                  var1.getlocal(3).__setattr__((String)"whitespace", var7);
                  var3 = null;

                  while(true) {
                     var1.setline(58);
                     if (!Py.newInteger(1).__nonzero__()) {
                        continue label83;
                     }

                     var1.setline(59);
                     var3 = var1.getlocal(3).__getattr__("instream").__getattr__("readline").__call__(var2);
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(60);
                     var10000 = var1.getlocal(8).__not__();
                     if (!var10000.__nonzero__()) {
                        var3 = var1.getlocal(8);
                        var10000 = var3._eq(PyString.fromInterned("\n"));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(61);
                        var7 = PyString.fromInterned(" \t\r\n");
                        var1.getlocal(3).__setattr__((String)"whitespace", var7);
                        var3 = null;
                        continue label83;
                     }

                     var1.setline(63);
                     var1.getlocal(0).__getattr__("macros").__getitem__(var1.getlocal(7)).__getattr__("append").__call__(var2, var1.getlocal(8));
                  }
               }

               var1.setline(53);
               var7 = PyString.fromInterned("default");
               var1.setlocal(7, var7);
               var3 = null;
            }

            var1.setline(70);
            var7 = PyString.fromInterned("");
            var1.setlocal(9, var7);
            var3 = null;
            var1.setline(71);
            var3 = var1.getglobal("None");
            var1.setlocal(10, var3);
            var1.setlocal(11, var3);
            var1.setline(72);
            PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__getattr__("hosts").__setitem__((PyObject)var1.getlocal(7), var8);
            var3 = null;

            while(true) {
               var1.setline(73);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(74);
               var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(75);
               var10000 = var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"));
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(5);
                  var10000 = var3._in(new PySet(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("machine"), PyString.fromInterned("default"), PyString.fromInterned("macdef")}));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(77);
                  if (!var1.getlocal(11).__nonzero__()) {
                     var1.setline(82);
                     throw Py.makeException(var1.getglobal("NetrcParseError").__call__(var2, PyString.fromInterned("malformed %s entry %s terminated by %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(7), var1.getglobal("repr").__call__(var2, var1.getlocal(5))})), var1.getlocal(1), var1.getlocal(3).__getattr__("lineno")));
                  }

                  var1.setline(78);
                  PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)});
                  var1.getlocal(0).__getattr__("hosts").__setitem__((PyObject)var1.getlocal(7), var9);
                  var3 = null;
                  var1.setline(79);
                  var1.getlocal(3).__getattr__("push_token").__call__(var2, var1.getlocal(5));
                  break;
               }

               var1.setline(86);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(PyString.fromInterned("login"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(PyString.fromInterned("user"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(87);
                  var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
                  var1.setlocal(9, var3);
                  var3 = null;
               } else {
                  var1.setline(88);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(PyString.fromInterned("account"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(89);
                     var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
                     var1.setlocal(10, var3);
                     var3 = null;
                  } else {
                     var1.setline(90);
                     var3 = var1.getlocal(5);
                     var10000 = var3._eq(PyString.fromInterned("password"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(93);
                        throw Py.makeException(var1.getglobal("NetrcParseError").__call__(var2, PyString.fromInterned("bad follower token %r")._mod(var1.getlocal(5)), var1.getlocal(1), var1.getlocal(3).__getattr__("lineno")));
                     }

                     var1.setline(91);
                     var3 = var1.getlocal(3).__getattr__("get_token").__call__(var2);
                     var1.setlocal(11, var3);
                     var3 = null;
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject authenticators$7(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Return a (user, account, password) tuple for given host.");
      var1.setline(98);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("hosts"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(99);
         var3 = var1.getlocal(0).__getattr__("hosts").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(100);
         PyString var4 = PyString.fromInterned("default");
         var10000 = var4._in(var1.getlocal(0).__getattr__("hosts"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(101);
            var3 = var1.getlocal(0).__getattr__("hosts").__getitem__(PyString.fromInterned("default"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(103);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __repr__$8(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyString.fromInterned("Dump the class data in the format of a .netrc file.");
      var1.setline(107);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(108);
      PyObject var8 = var1.getlocal(0).__getattr__("hosts").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(108);
         PyObject var4 = var8.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(114);
            var8 = var1.getlocal(0).__getattr__("macros").__getattr__("keys").__call__(var2).__iter__();

            while(true) {
               var1.setline(114);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.setline(119);
                  var8 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setlocal(4, var4);
               var1.setline(115);
               var5 = var1.getlocal(1)._add(PyString.fromInterned("macdef "))._add(var1.getlocal(4))._add(PyString.fromInterned("\n"));
               var1.setlocal(1, var5);
               var5 = null;
               var1.setline(116);
               var5 = var1.getlocal(0).__getattr__("macros").__getitem__(var1.getlocal(4)).__iter__();

               while(true) {
                  var1.setline(116);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(118);
                     var5 = var1.getlocal(1)._add(PyString.fromInterned("\n"));
                     var1.setlocal(1, var5);
                     var5 = null;
                     break;
                  }

                  var1.setlocal(5, var6);
                  var1.setline(117);
                  PyObject var7 = var1.getlocal(1)._add(var1.getlocal(5));
                  var1.setlocal(1, var7);
                  var7 = null;
               }
            }
         }

         var1.setlocal(2, var4);
         var1.setline(109);
         var5 = var1.getlocal(0).__getattr__("hosts").__getitem__(var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(110);
         var5 = var1.getlocal(1)._add(PyString.fromInterned("machine "))._add(var1.getlocal(2))._add(PyString.fromInterned("\n\tlogin "))._add(var1.getglobal("repr").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0))))._add(PyString.fromInterned("\n"));
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(111);
         if (var1.getlocal(3).__getitem__(Py.newInteger(1)).__nonzero__()) {
            var1.setline(112);
            var5 = var1.getlocal(1)._add(PyString.fromInterned("account "))._add(var1.getglobal("repr").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))));
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(113);
         var5 = var1.getlocal(1)._add(PyString.fromInterned("\tpassword "))._add(var1.getglobal("repr").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(2))))._add(PyString.fromInterned("\n"));
         var1.setlocal(1, var5);
         var5 = null;
      }
   }

   public netrc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NetrcParseError$1 = Py.newCode(0, var2, var1, "NetrcParseError", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "filename", "lineno"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 18, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      netrc$4 = Py.newCode(0, var2, var1, "netrc", 22, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "fp"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 23, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "fp", "lexer", "toplevel", "tt", "pos", "entryname", "line", "login", "account", "password"};
      _parse$6 = Py.newCode(3, var2, var1, "_parse", 34, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host"};
      authenticators$7 = Py.newCode(2, var2, var1, "authenticators", 96, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rep", "host", "attrs", "macro", "line"};
      __repr__$8 = Py.newCode(1, var2, var1, "__repr__", 105, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new netrc$py("netrc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(netrc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NetrcParseError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.netrc$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this._parse$6(var2, var3);
         case 7:
            return this.authenticators$7(var2, var3);
         case 8:
            return this.__repr__$8(var2, var3);
         default:
            return null;
      }
   }
}
