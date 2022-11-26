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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("dumbdbm.py")
public class dumbdbm$py extends PyFunctionTable implements PyRunnable {
   static dumbdbm$py self;
   static final PyCode f$0;
   static final PyCode _Database$1;
   static final PyCode __init__$2;
   static final PyCode _update$3;
   static final PyCode _commit$4;
   static final PyCode __getitem__$5;
   static final PyCode _addval$6;
   static final PyCode _setval$7;
   static final PyCode _addkey$8;
   static final PyCode __setitem__$9;
   static final PyCode __delitem__$10;
   static final PyCode keys$11;
   static final PyCode has_key$12;
   static final PyCode __contains__$13;
   static final PyCode iterkeys$14;
   static final PyCode __len__$15;
   static final PyCode close$16;
   static final PyCode _chmod$17;
   static final PyCode open$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A dumb and slow but simple dbm clone.\n\nFor database spam, spam.dir contains the index (a text file),\nspam.bak *may* contain a backup of the index (also a text file),\nwhile spam.dat contains the data (a binary file).\n\nXXX TO DO:\n\n- seems to contain a bug when updating...\n\n- reclaim free space (currently, space once occupied by deleted or expanded\nitems is never reused)\n\n- support concurrent access (currently, if two processes take turns making\nupdates, they can mess up the index)\n\n- support efficient access to large databases (currently, the whole index\nis read when the database is opened, and some updates rewrite the whole index)\n\n- support opening for read-only (flag = 'm')\n\n"));
      var1.setline(22);
      PyString.fromInterned("A dumb and slow but simple dbm clone.\n\nFor database spam, spam.dir contains the index (a text file),\nspam.bak *may* contain a backup of the index (also a text file),\nwhile spam.dat contains the data (a binary file).\n\nXXX TO DO:\n\n- seems to contain a bug when updating...\n\n- reclaim free space (currently, space once occupied by deleted or expanded\nitems is never reused)\n\n- support concurrent access (currently, if two processes take turns making\nupdates, they can mess up the index)\n\n- support efficient access to large databases (currently, the whole index\nis read when the database is opened, and some updates rewrite the whole index)\n\n- support opening for read-only (flag = 'm')\n\n");
      var1.setline(24);
      PyObject var3 = imp.importOneAs("os", var1, -1);
      var1.setlocal("_os", var3);
      var3 = null;
      var1.setline(25);
      var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var1.setline(26);
      var3 = imp.importOne("UserDict", var1, -1);
      var1.setlocal("UserDict", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("__builtin__").__getattr__("open");
      var1.setlocal("_open", var3);
      var3 = null;
      var1.setline(30);
      PyInteger var5 = Py.newInteger(512);
      var1.setlocal("_BLOCKSIZE", var5);
      var3 = null;
      var1.setline(32);
      var3 = var1.getname("IOError");
      var1.setlocal("error", var3);
      var3 = null;
      var1.setline(34);
      PyObject[] var6 = new PyObject[]{var1.getname("UserDict").__getattr__("DictMixin")};
      PyObject var4 = Py.makeClass("_Database", var6, _Database$1);
      var1.setlocal("_Database", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(225);
      var6 = new PyObject[]{var1.getname("None"), Py.newInteger(438)};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, open$18, PyString.fromInterned("Open the database file, filename, and return corresponding object.\n\n    The flag argument, used to control how the database is opened in the\n    other DBM implementations, is ignored in the dumbdbm module; the\n    database is always opened for update, and will be created if it does\n    not exist.\n\n    The optional mode argument is the UNIX mode of the file, used only when\n    the database has to be created.  It defaults to octal code 0666 (and\n    will be modified by the prevailing umask).\n\n    "));
      var1.setlocal("open", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Database$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(44);
      PyObject var3 = var1.getname("_os");
      var1.setlocal("_os", var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getname("_open");
      var1.setlocal("_open", var3);
      var3 = null;
      var1.setline(47);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(77);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _update$3, (PyObject)null);
      var1.setlocal("_update", var5);
      var3 = null;
      var1.setline(93);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _commit$4, (PyObject)null);
      var1.setlocal("_commit", var5);
      var3 = null;
      var1.setline(116);
      var3 = var1.getname("_commit");
      var1.setlocal("sync", var3);
      var3 = null;
      var1.setline(118);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getitem__$5, (PyObject)null);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(130);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _addval$6, (PyObject)null);
      var1.setlocal("_addval", var5);
      var3 = null;
      var1.setline(145);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _setval$7, (PyObject)null);
      var1.setlocal("_setval", var5);
      var3 = null;
      var1.setline(155);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _addkey$8, (PyObject)null);
      var1.setlocal("_addkey", var5);
      var3 = null;
      var1.setline(162);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setitem__$9, (PyObject)null);
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(189);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __delitem__$10, (PyObject)null);
      var1.setlocal("__delitem__", var5);
      var3 = null;
      var1.setline(198);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, keys$11, (PyObject)null);
      var1.setlocal("keys", var5);
      var3 = null;
      var1.setline(201);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, has_key$12, (PyObject)null);
      var1.setlocal("has_key", var5);
      var3 = null;
      var1.setline(204);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __contains__$13, (PyObject)null);
      var1.setlocal("__contains__", var5);
      var3 = null;
      var1.setline(207);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, iterkeys$14, (PyObject)null);
      var1.setlocal("iterkeys", var5);
      var3 = null;
      var1.setline(209);
      var3 = var1.getname("iterkeys");
      var1.setlocal("__iter__", var3);
      var3 = null;
      var1.setline(211);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __len__$15, (PyObject)null);
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(214);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$16, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(218);
      var3 = var1.getname("close");
      var1.setlocal("__del__", var3);
      var3 = null;
      var1.setline(220);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _chmod$17, (PyObject)null);
      var1.setlocal("_chmod", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_mode", var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getlocal(1)._add(var1.getglobal("_os").__getattr__("extsep"))._add(PyString.fromInterned("dir"));
      var1.getlocal(0).__setattr__("_dirfile", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(1)._add(var1.getglobal("_os").__getattr__("extsep"))._add(PyString.fromInterned("dat"));
      var1.getlocal(0).__setattr__("_datfile", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(1)._add(var1.getglobal("_os").__getattr__("extsep"))._add(PyString.fromInterned("bak"));
      var1.getlocal(0).__setattr__("_bakfile", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_index", var3);
      var3 = null;

      try {
         var1.setline(69);
         var3 = var1.getglobal("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_datfile"), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("IOError"))) {
            throw var6;
         }

         var1.setline(71);
         PyObject var4 = var1.getglobal("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_datfile"), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(72);
         var1.getlocal(0).__getattr__("_chmod").__call__(var2, var1.getlocal(0).__getattr__("_datfile"));
      }

      var1.setline(73);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(74);
      var1.getlocal(0).__getattr__("_update").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _update$3(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_index", var3);
      var3 = null;

      label32: {
         try {
            var1.setline(80);
            PyObject var11 = var1.getglobal("_open").__call__(var2, var1.getlocal(0).__getattr__("_dirfile"));
            var1.setlocal(1, var11);
            var3 = null;
         } catch (Throwable var9) {
            PyException var10 = Py.setException(var9, var1);
            if (var10.match(var1.getglobal("IOError"))) {
               var1.setline(82);
               break label32;
            }

            throw var10;
         }

         var1.setline(84);
         PyObject var4 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(84);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(88);
               var1.getlocal(1).__getattr__("close").__call__(var2);
               break;
            }

            var1.setlocal(2, var5);
            var1.setline(85);
            PyObject var6 = var1.getlocal(2).__getattr__("rstrip").__call__(var2);
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(86);
            var6 = var1.getglobal("eval").__call__(var2, var1.getlocal(2));
            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(3, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(4, var8);
            var8 = null;
            var6 = null;
            var1.setline(87);
            var6 = var1.getlocal(4);
            var1.getlocal(0).__getattr__("_index").__setitem__(var1.getlocal(3), var6);
            var6 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _commit$4(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getlocal(0).__getattr__("_index");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(98);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyException var9;
         try {
            var1.setline(101);
            var1.getlocal(0).__getattr__("_os").__getattr__("unlink").__call__(var2, var1.getlocal(0).__getattr__("_bakfile"));
         } catch (Throwable var8) {
            var9 = Py.setException(var8, var1);
            if (!var9.match(var1.getlocal(0).__getattr__("_os").__getattr__("error"))) {
               throw var9;
            }

            var1.setline(103);
         }

         try {
            var1.setline(106);
            var1.getlocal(0).__getattr__("_os").__getattr__("rename").__call__(var2, var1.getlocal(0).__getattr__("_dirfile"), var1.getlocal(0).__getattr__("_bakfile"));
         } catch (Throwable var7) {
            var9 = Py.setException(var7, var1);
            if (!var9.match(var1.getlocal(0).__getattr__("_os").__getattr__("error"))) {
               throw var9;
            }

            var1.setline(108);
         }

         var1.setline(110);
         var3 = var1.getlocal(0).__getattr__("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_dirfile"), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(111);
         var1.getlocal(0).__getattr__("_chmod").__call__(var2, var1.getlocal(0).__getattr__("_dirfile"));
         var1.setline(112);
         var3 = var1.getlocal(0).__getattr__("_index").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(112);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(114);
               var1.getlocal(1).__getattr__("close").__call__(var2);
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(113);
            var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%r, %r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }
      }
   }

   public PyObject __getitem__$5(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getlocal(0).__getattr__("_index").__getitem__(var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(120);
      var3 = var1.getglobal("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_datfile"), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(121);
      var1.getlocal(4).__getattr__("seek").__call__(var2, var1.getlocal(2));
      var1.setline(122);
      var3 = var1.getlocal(4).__getattr__("read").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(123);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(124);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _addval$6(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getglobal("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_datfile"), (PyObject)PyString.fromInterned("rb+"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(132);
      var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(133);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getattr__("tell").__call__(var2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getlocal(3)._add(var1.getglobal("_BLOCKSIZE"))._sub(Py.newInteger(1))._floordiv(var1.getglobal("_BLOCKSIZE"))._mul(var1.getglobal("_BLOCKSIZE"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(135);
      var1.getlocal(2).__getattr__("write").__call__(var2, PyString.fromInterned("\u0000")._mul(var1.getlocal(4)._sub(var1.getlocal(3))));
      var1.setline(136);
      var3 = var1.getlocal(4);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(137);
      var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(138);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(139);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _setval$7(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyObject var3 = var1.getglobal("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_datfile"), (PyObject)PyString.fromInterned("rb+"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(147);
      var1.getlocal(3).__getattr__("seek").__call__(var2, var1.getlocal(1));
      var1.setline(148);
      var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(2));
      var1.setline(149);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(150);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(2))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _addkey$8(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("_index").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getglobal("_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_dirfile"), (PyObject)PyString.fromInterned("a"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(158);
      var1.getlocal(0).__getattr__("_chmod").__call__(var2, var1.getlocal(0).__getattr__("_dirfile"));
      var1.setline(159);
      var1.getlocal(3).__getattr__("write").__call__(var2, PyString.fromInterned("%r, %r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(160);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$9(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10001 = var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._eq(var10001)).__nonzero__()) {
         var4 = var3._eq(var1.getglobal("type").__call__(var2, var1.getlocal(2)));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(164);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("keys and values must be strings"));
      } else {
         var1.setline(165);
         var3 = var1.getlocal(1);
         var10000 = var3._notin(var1.getlocal(0).__getattr__("_index"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(166);
            var1.getlocal(0).__getattr__("_addkey").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_addval").__call__(var2, var1.getlocal(2)));
         } else {
            var1.setline(170);
            var3 = var1.getlocal(0).__getattr__("_index").__getitem__(var1.getlocal(1));
            PyObject[] var6 = Py.unpackSequence(var3, 2);
            PyObject var5 = var6[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var6[1];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
            var1.setline(171);
            var3 = var1.getlocal(4)._add(var1.getglobal("_BLOCKSIZE"))._sub(Py.newInteger(1))._floordiv(var1.getglobal("_BLOCKSIZE"));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(172);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(var1.getglobal("_BLOCKSIZE"))._sub(Py.newInteger(1))._floordiv(var1.getglobal("_BLOCKSIZE"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(173);
            var3 = var1.getlocal(6);
            var10000 = var3._le(var1.getlocal(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(174);
               var3 = var1.getlocal(0).__getattr__("_setval").__call__(var2, var1.getlocal(3), var1.getlocal(2));
               var1.getlocal(0).__getattr__("_index").__setitem__(var1.getlocal(1), var3);
               var3 = null;
            } else {
               var1.setline(179);
               var3 = var1.getlocal(0).__getattr__("_addval").__call__(var2, var1.getlocal(2));
               var1.getlocal(0).__getattr__("_index").__setitem__(var1.getlocal(1), var3);
               var3 = null;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __delitem__$10(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      var1.getlocal(0).__getattr__("_index").__delitem__(var1.getlocal(1));
      var1.setline(196);
      var1.getlocal(0).__getattr__("_commit").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject keys$11(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getlocal(0).__getattr__("_index").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$12(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_index"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$13(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_index"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterkeys$14(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyObject var3 = var1.getlocal(0).__getattr__("_index").__getattr__("iterkeys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$15(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_index"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$16(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      var1.getlocal(0).__getattr__("_commit").__call__(var2);
      var1.setline(216);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_index", var3);
      var1.getlocal(0).__setattr__("_datfile", var3);
      var1.getlocal(0).__setattr__("_dirfile", var3);
      var1.getlocal(0).__setattr__("_bakfile", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _chmod$17(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_os"), (PyObject)PyString.fromInterned("chmod")).__nonzero__()) {
         var1.setline(222);
         var1.getlocal(0).__getattr__("_os").__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_mode"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$18(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyString.fromInterned("Open the database file, filename, and return corresponding object.\n\n    The flag argument, used to control how the database is opened in the\n    other DBM implementations, is ignored in the dumbdbm module; the\n    database is always opened for update, and will be created if it does\n    not exist.\n\n    The optional mode argument is the UNIX mode of the file, used only when\n    the database has to be created.  It defaults to octal code 0666 (and\n    will be modified by the prevailing umask).\n\n    ");

      PyObject var6;
      label19: {
         PyException var3;
         try {
            var1.setline(242);
            var6 = var1.getglobal("_os").__getattr__("umask").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(243);
            var1.getglobal("_os").__getattr__("umask").__call__(var2, var1.getlocal(3));
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("AttributeError"))) {
               var1.setline(245);
               break label19;
            }

            throw var3;
         }

         var1.setline(248);
         PyObject var4 = var1.getlocal(2)._and(var1.getlocal(3).__invert__());
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(250);
      var6 = var1.getglobal("_Database").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.f_lasti = -1;
      return var6;
   }

   public dumbdbm$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _Database$1 = Py.newCode(0, var2, var1, "_Database", 34, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filebasename", "mode", "f"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 47, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "line", "key", "pos_and_siz_pair"};
      _update$3 = Py.newCode(1, var2, var1, "_update", 77, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "key", "pos_and_siz_pair"};
      _commit$4 = Py.newCode(1, var2, var1, "_commit", 93, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "pos", "siz", "f", "dat"};
      __getitem__$5 = Py.newCode(2, var2, var1, "__getitem__", 118, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val", "f", "pos", "npos"};
      _addval$6 = Py.newCode(2, var2, var1, "_addval", 130, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "val", "f"};
      _setval$7 = Py.newCode(3, var2, var1, "_setval", 145, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "pos_and_siz_pair", "f"};
      _addkey$8 = Py.newCode(3, var2, var1, "_addkey", 155, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "val", "pos", "siz", "oldblocks", "newblocks"};
      __setitem__$9 = Py.newCode(3, var2, var1, "__setitem__", 162, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __delitem__$10 = Py.newCode(2, var2, var1, "__delitem__", 189, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$11 = Py.newCode(1, var2, var1, "keys", 198, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$12 = Py.newCode(2, var2, var1, "has_key", 201, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$13 = Py.newCode(2, var2, var1, "__contains__", 204, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      iterkeys$14 = Py.newCode(1, var2, var1, "iterkeys", 207, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$15 = Py.newCode(1, var2, var1, "__len__", 211, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$16 = Py.newCode(1, var2, var1, "close", 214, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      _chmod$17 = Py.newCode(2, var2, var1, "_chmod", 220, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "flag", "mode", "um"};
      open$18 = Py.newCode(3, var2, var1, "open", 225, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dumbdbm$py("dumbdbm$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dumbdbm$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._Database$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._update$3(var2, var3);
         case 4:
            return this._commit$4(var2, var3);
         case 5:
            return this.__getitem__$5(var2, var3);
         case 6:
            return this._addval$6(var2, var3);
         case 7:
            return this._setval$7(var2, var3);
         case 8:
            return this._addkey$8(var2, var3);
         case 9:
            return this.__setitem__$9(var2, var3);
         case 10:
            return this.__delitem__$10(var2, var3);
         case 11:
            return this.keys$11(var2, var3);
         case 12:
            return this.has_key$12(var2, var3);
         case 13:
            return this.__contains__$13(var2, var3);
         case 14:
            return this.iterkeys$14(var2, var3);
         case 15:
            return this.__len__$15(var2, var3);
         case 16:
            return this.close$16(var2, var3);
         case 17:
            return this._chmod$17(var2, var3);
         case 18:
            return this.open$18(var2, var3);
         default:
            return null;
      }
   }
}
