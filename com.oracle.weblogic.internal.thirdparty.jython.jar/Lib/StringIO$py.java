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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("StringIO.py")
public class StringIO$py extends PyFunctionTable implements PyRunnable {
   static StringIO$py self;
   static final PyCode f$0;
   static final PyCode _complain_ifclosed$1;
   static final PyCode StringIO$2;
   static final PyCode __init__$3;
   static final PyCode __iter__$4;
   static final PyCode next$5;
   static final PyCode close$6;
   static final PyCode isatty$7;
   static final PyCode seek$8;
   static final PyCode tell$9;
   static final PyCode read$10;
   static final PyCode readline$11;
   static final PyCode readlines$12;
   static final PyCode truncate$13;
   static final PyCode write$14;
   static final PyCode writelines$15;
   static final PyCode flush$16;
   static final PyCode getvalue$17;
   static final PyCode test$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("File-like objects that read from or write to a string buffer.\n\nThis implements (nearly) all stdio methods.\n\nf = StringIO()      # ready for writing\nf = StringIO(buf)   # ready for reading\nf.close()           # explicitly release resources held\nflag = f.isatty()   # always false\npos = f.tell()      # get current position\nf.seek(pos)         # set current position\nf.seek(pos, mode)   # mode 0: absolute; 1: relative; 2: relative to EOF\nbuf = f.read()      # read until EOF\nbuf = f.read(n)     # read up to n bytes\nbuf = f.readline()  # read until end of line ('\\n') or EOF\nlist = f.readlines()# list of f.readline() results until EOF\nf.truncate([size])  # truncate file at to at most size (default: current pos)\nf.write(buf)        # write at current position\nf.writelines(list)  # for line in list: f.write(line)\nf.getvalue()        # return whole file's contents as a string\n\nNotes:\n- Using a real file is often faster (but less convenient).\n- There's also a much faster implementation in C, called cStringIO, but\n  it's not subclassable.\n- fileno() is left unimplemented so that code which uses it triggers\n  an exception early.\n- Seeking far beyond EOF and then writing will insert real null\n  bytes that occupy space in the buffer.\n- There's a simple test set (see end of this file).\n"));
      var1.setline(30);
      PyString.fromInterned("File-like objects that read from or write to a string buffer.\n\nThis implements (nearly) all stdio methods.\n\nf = StringIO()      # ready for writing\nf = StringIO(buf)   # ready for reading\nf.close()           # explicitly release resources held\nflag = f.isatty()   # always false\npos = f.tell()      # get current position\nf.seek(pos)         # set current position\nf.seek(pos, mode)   # mode 0: absolute; 1: relative; 2: relative to EOF\nbuf = f.read()      # read until EOF\nbuf = f.read(n)     # read up to n bytes\nbuf = f.readline()  # read until end of line ('\\n') or EOF\nlist = f.readlines()# list of f.readline() results until EOF\nf.truncate([size])  # truncate file at to at most size (default: current pos)\nf.write(buf)        # write at current position\nf.writelines(list)  # for line in list: f.write(line)\nf.getvalue()        # return whole file's contents as a string\n\nNotes:\n- Using a real file is often faster (but less convenient).\n- There's also a much faster implementation in C, called cStringIO, but\n  it's not subclassable.\n- fileno() is left unimplemented so that code which uses it triggers\n  an exception early.\n- Seeking far beyond EOF and then writing will insert real null\n  bytes that occupy space in the buffer.\n- There's a simple test set (see end of this file).\n");

      PyException var3;
      PyInteger var4;
      PyObject[] var7;
      PyObject var9;
      try {
         var1.setline(32);
         String[] var6 = new String[]{"EINVAL"};
         var7 = imp.importFrom("errno", var6, var1, -1);
         var9 = var7[0];
         var1.setlocal("EINVAL", var9);
         var4 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getname("ImportError"))) {
            throw var3;
         }

         var1.setline(34);
         var4 = Py.newInteger(22);
         var1.setlocal("EINVAL", var4);
         var4 = null;
      }

      var1.setline(36);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("StringIO")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(38);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, _complain_ifclosed$1, (PyObject)null);
      var1.setlocal("_complain_ifclosed", var10);
      var3 = null;
      var1.setline(42);
      var7 = Py.EmptyObjects;
      var9 = Py.makeClass("StringIO", var7, StringIO$2);
      var1.setlocal("StringIO", var9);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(278);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, test$18, (PyObject)null);
      var1.setlocal("test", var10);
      var3 = null;
      var1.setline(323);
      PyObject var11 = var1.getname("__name__");
      PyObject var10000 = var11._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(324);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _complain_ifclosed$1(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(40);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("I/O operation on closed file"));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject StringIO$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("class StringIO([buffer])\n\n    When a StringIO object is created, it can be initialized to an existing\n    string by passing the string to the constructor. If no string is given,\n    the StringIO will start empty.\n\n    The StringIO object can accept either Unicode or 8-bit strings, but\n    mixing the two may take some care. If both are used, 8-bit strings that\n    cannot be interpreted as 7-bit ASCII (that use the 8th bit) will cause\n    a UnicodeError to be raised when getvalue() is called.\n    "));
      var1.setline(53);
      PyString.fromInterned("class StringIO([buffer])\n\n    When a StringIO object is created, it can be initialized to an existing\n    string by passing the string to the constructor. If no string is given,\n    the StringIO will start empty.\n\n    The StringIO object can accept either Unicode or 8-bit strings, but\n    mixing the two may take some care. If both are used, 8-bit strings that\n    cannot be interpreted as 7-bit ASCII (that use the 8th bit) will cause\n    a UnicodeError to be raised when getvalue() is called.\n    ");
      var1.setline(54);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$4, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$5, PyString.fromInterned("A file object is its own iterator, for example iter(f) returns f\n        (unless f is closed). When a file is used as an iterator, typically\n        in a for loop (for example, for line in f: print line), the next()\n        method is called repeatedly. This method returns the next input line,\n        or raises StopIteration when EOF is hit.\n        "));
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$6, PyString.fromInterned("Free the memory buffer.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isatty$7, PyString.fromInterned("Returns False because StringIO objects are not connected to a\n        tty-like device.\n        "));
      var1.setlocal("isatty", var4);
      var3 = null;
      var1.setline(95);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$8, PyString.fromInterned("Set the file's current position.\n\n        The mode argument is optional and defaults to 0 (absolute file\n        positioning); other values are 1 (seek relative to the current\n        position) and 2 (seek relative to the file's end).\n\n        There is no return value.\n        "));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$9, PyString.fromInterned("Return the file's current position."));
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(119);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, read$10, PyString.fromInterned("Read at most size bytes from the file\n        (less if the read hits EOF before obtaining size bytes).\n\n        If the size argument is negative or omitted, read all data until EOF\n        is reached. The bytes are returned as a string object. An empty\n        string is returned when EOF is encountered immediately.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(139);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, readline$11, PyString.fromInterned("Read one entire line from the file.\n\n        A trailing newline character is kept in the string (but may be absent\n        when a file ends with an incomplete line). If the size argument is\n        present and non-negative, it is a maximum byte count (including the\n        trailing newline) and an incomplete line may be returned.\n\n        An empty string is returned only when EOF is encountered immediately.\n\n        Note: Unlike stdio's fgets(), the returned string contains null\n        characters ('\\0') if they occurred in the input.\n        "));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(168);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, readlines$12, PyString.fromInterned("Read until EOF using readline() and return a list containing the\n        lines thus read.\n\n        If the optional sizehint argument is present, instead of reading up\n        to EOF, whole lines totalling approximately sizehint bytes (or more\n        to accommodate a final whole line).\n        "));
      var1.setlocal("readlines", var4);
      var3 = null;
      var1.setline(187);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$13, PyString.fromInterned("Truncate the file's size.\n\n        If the optional size argument is present, the file is truncated to\n        (at most) that size. The size defaults to the current position.\n        The current file position is not changed unless the position\n        is beyond the new file size.\n\n        If the specified size exceeds the file's current size, the\n        file remains unchanged.\n        "));
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$14, PyString.fromInterned("Write a string to the file.\n\n        There is no return value.\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(241);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writelines$15, PyString.fromInterned("Write a sequence of strings to the file. The sequence can be any\n        iterable object producing strings, typically a list of strings. There\n        is no return value.\n\n        (The name is intended to match readlines(); writelines() does not add\n        line separators.)\n        "));
      var1.setlocal("writelines", var4);
      var3 = null;
      var1.setline(253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$16, PyString.fromInterned("Flush the internal buffer\n        "));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(258);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getvalue$17, PyString.fromInterned("\n        Retrieve the entire contents of the \"file\" at any time before\n        the StringIO object's close() method is called.\n\n        The StringIO object can accept either Unicode or 8-bit strings,\n        but mixing the two may take some care. If both are used, 8-bit\n        strings that cannot be interpreted as 7-bit ASCII (that use the\n        8th bit) will cause a UnicodeError to be raised when getvalue()\n        is called.\n        "));
      var1.setlocal("getvalue", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__().__nonzero__()) {
         var1.setline(57);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(58);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("buf", var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("len", var3);
      var3 = null;
      var1.setline(60);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"buflist", var4);
      var3 = null;
      var1.setline(61);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"pos", var5);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.setline(63);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"softspace", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$5(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("A file object is its own iterator, for example iter(f) returns f\n        (unless f is closed). When a file is used as an iterator, typically\n        in a for loop (for example, for line in f: print line), the next()\n        method is called repeatedly. This method returns the next input line,\n        or raises StopIteration when EOF is hit.\n        ");
      var1.setline(75);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(77);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(78);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(79);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject close$6(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyString.fromInterned("Free the memory buffer.\n        ");
      var1.setline(84);
      if (var1.getlocal(0).__getattr__("closed").__not__().__nonzero__()) {
         var1.setline(85);
         PyObject var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("closed", var3);
         var3 = null;
         var1.setline(86);
         var1.getlocal(0).__delattr__("buf");
         var1.getlocal(0).__delattr__("pos");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isatty$7(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Returns False because StringIO objects are not connected to a\n        tty-like device.\n        ");
      var1.setline(92);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(93);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$8(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("Set the file's current position.\n\n        The mode argument is optional and defaults to 0 (absolute file\n        positioning); other values are 1 (seek relative to the current\n        position) and 2 (seek relative to the file's end).\n\n        There is no return value.\n        ");
      var1.setline(104);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(105);
      PyObject var10000;
      String var3;
      if (var1.getlocal(0).__getattr__("buflist").__nonzero__()) {
         var1.setline(106);
         var10000 = var1.getlocal(0);
         var3 = "buf";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var3);
         var5 = var5._iadd(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("buflist")));
         var4.__setattr__(var3, var5);
         var1.setline(107);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"buflist", var6);
         var3 = null;
      }

      var1.setline(108);
      PyObject var7 = var1.getlocal(2);
      var10000 = var7._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(109);
         var7 = var1.getlocal(1);
         var7 = var7._iadd(var1.getlocal(0).__getattr__("pos"));
         var1.setlocal(1, var7);
      } else {
         var1.setline(110);
         var7 = var1.getlocal(2);
         var10000 = var7._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(111);
            var7 = var1.getlocal(1);
            var7 = var7._iadd(var1.getlocal(0).__getattr__("len"));
            var1.setlocal(1, var7);
         }
      }

      var1.setline(112);
      var7 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1));
      var1.getlocal(0).__setattr__("pos", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$9(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyString.fromInterned("Return the file's current position.");
      var1.setline(116);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(117);
      PyObject var3 = var1.getlocal(0).__getattr__("pos");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$10(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Read at most size bytes from the file\n        (less if the read hits EOF before obtaining size bytes).\n\n        If the size argument is negative or omitted, read all data until EOF\n        is reached. The bytes are returned as a string object. An empty\n        string is returned when EOF is encountered immediately.\n        ");
      var1.setline(127);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(128);
      PyObject var10000;
      String var3;
      if (var1.getlocal(0).__getattr__("buflist").__nonzero__()) {
         var1.setline(129);
         var10000 = var1.getlocal(0);
         var3 = "buf";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var3);
         var5 = var5._iadd(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("buflist")));
         var4.__setattr__(var3, var5);
         var1.setline(130);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"buflist", var6);
         var3 = null;
      }

      var1.setline(131);
      PyObject var7 = var1.getlocal(1);
      var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var7 = var1.getlocal(1);
         var10000 = var7._lt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(132);
         var7 = var1.getlocal(0).__getattr__("len");
         var1.setlocal(2, var7);
         var3 = null;
      } else {
         var1.setline(134);
         var7 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("pos")._add(var1.getlocal(1)), var1.getlocal(0).__getattr__("len"));
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(135);
      var7 = var1.getlocal(0).__getattr__("buf").__getslice__(var1.getlocal(0).__getattr__("pos"), var1.getlocal(2), (PyObject)null);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(136);
      var7 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("pos", var7);
      var3 = null;
      var1.setline(137);
      var7 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject readline$11(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyString.fromInterned("Read one entire line from the file.\n\n        A trailing newline character is kept in the string (but may be absent\n        when a file ends with an incomplete line). If the size argument is\n        present and non-negative, it is a maximum byte count (including the\n        trailing newline) and an incomplete line may be returned.\n\n        An empty string is returned only when EOF is encountered immediately.\n\n        Note: Unlike stdio's fgets(), the returned string contains null\n        characters ('\\0') if they occurred in the input.\n        ");
      var1.setline(152);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(153);
      PyObject var10000;
      String var3;
      if (var1.getlocal(0).__getattr__("buflist").__nonzero__()) {
         var1.setline(154);
         var10000 = var1.getlocal(0);
         var3 = "buf";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var3);
         var5 = var5._iadd(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("buflist")));
         var4.__setattr__(var3, var5);
         var1.setline(155);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"buflist", var6);
         var3 = null;
      }

      var1.setline(156);
      PyObject var7 = var1.getlocal(0).__getattr__("buf").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getlocal(0).__getattr__("pos"));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(157);
      var7 = var1.getlocal(2);
      var10000 = var7._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(158);
         var7 = var1.getlocal(0).__getattr__("len");
         var1.setlocal(3, var7);
         var3 = null;
      } else {
         var1.setline(160);
         var7 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(161);
      var7 = var1.getlocal(1);
      var10000 = var7._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(1);
         var10000 = var7._ge(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(162);
         var7 = var1.getlocal(0).__getattr__("pos")._add(var1.getlocal(1));
         var10000 = var7._lt(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(163);
            var7 = var1.getlocal(0).__getattr__("pos")._add(var1.getlocal(1));
            var1.setlocal(3, var7);
            var3 = null;
         }
      }

      var1.setline(164);
      var7 = var1.getlocal(0).__getattr__("buf").__getslice__(var1.getlocal(0).__getattr__("pos"), var1.getlocal(3), (PyObject)null);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(165);
      var7 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("pos", var7);
      var3 = null;
      var1.setline(166);
      var7 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject readlines$12(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyString.fromInterned("Read until EOF using readline() and return a list containing the\n        lines thus read.\n\n        If the optional sizehint argument is present, instead of reading up\n        to EOF, whole lines totalling approximately sizehint bytes (or more\n        to accommodate a final whole line).\n        ");
      var1.setline(176);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(177);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(178);
      PyObject var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;

      while(true) {
         var1.setline(179);
         if (!var1.getlocal(4).__nonzero__()) {
            break;
         }

         var1.setline(180);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(181);
         var6 = var1.getlocal(2);
         var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var1.setlocal(2, var6);
         var1.setline(182);
         var3 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(1);
         PyInteger var10000 = var3;
         var6 = var10001;
         PyObject var4;
         if ((var4 = var10000._lt(var10001)).__nonzero__()) {
            var4 = var6._le(var1.getlocal(2));
         }

         var3 = null;
         if (var4.__nonzero__()) {
            break;
         }

         var1.setline(184);
         var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(4, var6);
         var3 = null;
      }

      var1.setline(185);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject truncate$13(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Truncate the file's size.\n\n        If the optional size argument is present, the file is truncated to\n        (at most) that size. The size defaults to the current position.\n        The current file position is not changed unless the position\n        is beyond the new file size.\n\n        If the specified size exceeds the file's current size, the\n        file remains unchanged.\n        ");
      var1.setline(198);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(199);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(200);
         var3 = var1.getlocal(0).__getattr__("pos");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(201);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)var1.getglobal("EINVAL"), (PyObject)PyString.fromInterned("Negative size not allowed")));
         }

         var1.setline(203);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(var1.getlocal(0).__getattr__("pos"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(204);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("pos", var3);
            var3 = null;
         }
      }

      var1.setline(205);
      var3 = var1.getlocal(0).__getattr__("getvalue").__call__(var2).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
      var1.getlocal(0).__setattr__("buf", var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("len", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$14(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString.fromInterned("Write a string to the file.\n\n        There is no return value.\n        ");
      var1.setline(213);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(214);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(214);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(216);
         PyObject var3;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__().__nonzero__()) {
            var1.setline(217);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(218);
         var3 = var1.getlocal(0).__getattr__("pos");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(219);
         var3 = var1.getlocal(0).__getattr__("len");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(220);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._eq(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(221);
            var1.getlocal(0).__getattr__("buflist").__getattr__("append").__call__(var2, var1.getlocal(1));
            var1.setline(222);
            var3 = var1.getlocal(2)._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var1.getlocal(0).__setattr__("len", var3);
            var1.getlocal(0).__setattr__("pos", var3);
            var1.setline(223);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(224);
            var3 = var1.getlocal(2);
            var10000 = var3._gt(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(225);
               var1.getlocal(0).__getattr__("buflist").__getattr__("append").__call__(var2, PyString.fromInterned("\u0000")._mul(var1.getlocal(2)._sub(var1.getlocal(3))));
               var1.setline(226);
               var3 = var1.getlocal(2);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(227);
            var3 = var1.getlocal(2)._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(228);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(229);
               if (var1.getlocal(0).__getattr__("buflist").__nonzero__()) {
                  var1.setline(230);
                  var10000 = var1.getlocal(0);
                  String var6 = "buf";
                  PyObject var4 = var10000;
                  PyObject var5 = var4.__getattr__(var6);
                  var5 = var5._iadd(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("buflist")));
                  var4.__setattr__(var6, var5);
               }

               var1.setline(231);
               PyList var7 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("buf").__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null), var1.getlocal(1), var1.getlocal(0).__getattr__("buf").__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null)});
               var1.getlocal(0).__setattr__((String)"buflist", var7);
               var3 = null;
               var1.setline(232);
               PyString var8 = PyString.fromInterned("");
               var1.getlocal(0).__setattr__((String)"buf", var8);
               var3 = null;
               var1.setline(233);
               var3 = var1.getlocal(4);
               var10000 = var3._gt(var1.getlocal(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(234);
                  var3 = var1.getlocal(4);
                  var1.setlocal(3, var3);
                  var3 = null;
               }
            } else {
               var1.setline(236);
               var1.getlocal(0).__getattr__("buflist").__getattr__("append").__call__(var2, var1.getlocal(1));
               var1.setline(237);
               var3 = var1.getlocal(4);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(238);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("len", var3);
            var3 = null;
            var1.setline(239);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("pos", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject writelines$15(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("Write a sequence of strings to the file. The sequence can be any\n        iterable object producing strings, typically a list of strings. There\n        is no return value.\n\n        (The name is intended to match readlines(); writelines() does not add\n        line separators.)\n        ");
      var1.setline(249);
      PyObject var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(250);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(250);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(251);
         var1.getlocal(2).__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject flush$16(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyString.fromInterned("Flush the internal buffer\n        ");
      var1.setline(256);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getvalue$17(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyString.fromInterned("\n        Retrieve the entire contents of the \"file\" at any time before\n        the StringIO object's close() method is called.\n\n        The StringIO object can accept either Unicode or 8-bit strings,\n        but mixing the two may take some care. If both are used, 8-bit\n        strings that cannot be interpreted as 7-bit ASCII (that use the\n        8th bit) will cause a UnicodeError to be raised when getvalue()\n        is called.\n        ");
      var1.setline(269);
      var1.getglobal("_complain_ifclosed").__call__(var2, var1.getlocal(0).__getattr__("closed"));
      var1.setline(270);
      if (var1.getlocal(0).__getattr__("buflist").__nonzero__()) {
         var1.setline(271);
         PyObject var10000 = var1.getlocal(0);
         String var3 = "buf";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var3);
         var5 = var5._iadd(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("buflist")));
         var4.__setattr__(var3, var5);
         var1.setline(272);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"buflist", var6);
         var3 = null;
      }

      var1.setline(273);
      PyObject var7 = var1.getlocal(0).__getattr__("buf");
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject test$18(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(280);
      if (var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(1));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(283);
         PyString var5 = PyString.fromInterned("/etc/passwd");
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(284);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r")).__getattr__("readlines").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(285);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r")).__getattr__("read").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(286);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(287);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null).__iter__();

      while(true) {
         var1.setline(287);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(289);
            var1.getlocal(4).__getattr__("writelines").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null));
            var1.setline(290);
            var3 = var1.getlocal(4).__getattr__("getvalue").__call__(var2);
            PyObject var10000 = var3._ne(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(291);
               throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("write failed"));
            } else {
               var1.setline(292);
               var3 = var1.getlocal(4).__getattr__("tell").__call__(var2);
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(293);
               Py.printComma(PyString.fromInterned("File length ="));
               Py.println(var1.getlocal(6));
               var1.setline(294);
               var1.getlocal(4).__getattr__("seek").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0))));
               var1.setline(295);
               var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
               var1.setline(296);
               var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setline(297);
               Py.printComma(PyString.fromInterned("First line ="));
               Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(4).__getattr__("readline").__call__(var2)));
               var1.setline(298);
               Py.printComma(PyString.fromInterned("Position ="));
               Py.println(var1.getlocal(4).__getattr__("tell").__call__(var2));
               var1.setline(299);
               var3 = var1.getlocal(4).__getattr__("readline").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(300);
               Py.printComma(PyString.fromInterned("Second line ="));
               Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(5)));
               var1.setline(301);
               var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)).__neg__(), (PyObject)Py.newInteger(1));
               var1.setline(302);
               var3 = var1.getlocal(4).__getattr__("read").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(5)));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(303);
               var3 = var1.getlocal(5);
               var10000 = var3._ne(var1.getlocal(7));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(304);
                  throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("bad result after seek back"));
               } else {
                  var1.setline(305);
                  var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)Py.newInteger(1));
                  var1.setline(306);
                  var3 = var1.getlocal(4).__getattr__("readlines").__call__(var2);
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(307);
                  var3 = var1.getlocal(8).__getitem__(Py.newInteger(-1));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(308);
                  var1.getlocal(4).__getattr__("seek").__call__(var2, var1.getlocal(4).__getattr__("tell").__call__(var2)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5))));
                  var1.setline(309);
                  var3 = var1.getlocal(4).__getattr__("read").__call__(var2);
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(310);
                  var3 = var1.getlocal(5);
                  var10000 = var3._ne(var1.getlocal(7));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(311);
                     throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("bad result after seek back from EOF"));
                  } else {
                     var1.setline(312);
                     Py.printComma(PyString.fromInterned("Read"));
                     Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
                     Py.println(PyString.fromInterned("more lines"));
                     var1.setline(313);
                     Py.printComma(PyString.fromInterned("File length ="));
                     Py.println(var1.getlocal(4).__getattr__("tell").__call__(var2));
                     var1.setline(314);
                     var3 = var1.getlocal(4).__getattr__("tell").__call__(var2);
                     var10000 = var3._ne(var1.getlocal(6));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(315);
                        throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("bad length"));
                     } else {
                        var1.setline(316);
                        var1.getlocal(4).__getattr__("truncate").__call__(var2, var1.getlocal(6)._div(Py.newInteger(2)));
                        var1.setline(317);
                        var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
                        var1.setline(318);
                        Py.printComma(PyString.fromInterned("Truncated length ="));
                        Py.println(var1.getlocal(4).__getattr__("tell").__call__(var2));
                        var1.setline(319);
                        var3 = var1.getlocal(4).__getattr__("tell").__call__(var2);
                        var10000 = var3._ne(var1.getlocal(6)._div(Py.newInteger(2)));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(320);
                           throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("truncate did not adjust length"));
                        } else {
                           var1.setline(321);
                           var1.getlocal(4).__getattr__("close").__call__(var2);
                           var1.f_lasti = -1;
                           return Py.None;
                        }
                     }
                  }
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(288);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5));
      }
   }

   public StringIO$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"closed"};
      _complain_ifclosed$1 = Py.newCode(1, var2, var1, "_complain_ifclosed", 38, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StringIO$2 = Py.newCode(0, var2, var1, "StringIO", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "buf"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 54, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$4 = Py.newCode(1, var2, var1, "__iter__", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "r"};
      next$5 = Py.newCode(1, var2, var1, "next", 68, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$6 = Py.newCode(1, var2, var1, "close", 81, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$7 = Py.newCode(1, var2, var1, "isatty", 88, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "mode"};
      seek$8 = Py.newCode(3, var2, var1, "seek", 95, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$9 = Py.newCode(1, var2, var1, "tell", 114, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "newpos", "r"};
      read$10 = Py.newCode(2, var2, var1, "read", 119, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "length", "i", "newpos", "r"};
      readline$11 = Py.newCode(2, var2, var1, "readline", 139, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint", "total", "lines", "line"};
      readlines$12 = Py.newCode(2, var2, var1, "readlines", 168, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      truncate$13 = Py.newCode(2, var2, var1, "truncate", 187, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "spos", "slen", "newpos"};
      write$14 = Py.newCode(2, var2, var1, "write", 208, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable", "write", "line"};
      writelines$15 = Py.newCode(2, var2, var1, "writelines", 241, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$16 = Py.newCode(1, var2, var1, "flush", 253, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getvalue$17 = Py.newCode(1, var2, var1, "getvalue", 258, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "file", "lines", "text", "f", "line", "length", "line2", "list"};
      test$18 = Py.newCode(0, var2, var1, "test", 278, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new StringIO$py("StringIO$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(StringIO$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._complain_ifclosed$1(var2, var3);
         case 2:
            return this.StringIO$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__iter__$4(var2, var3);
         case 5:
            return this.next$5(var2, var3);
         case 6:
            return this.close$6(var2, var3);
         case 7:
            return this.isatty$7(var2, var3);
         case 8:
            return this.seek$8(var2, var3);
         case 9:
            return this.tell$9(var2, var3);
         case 10:
            return this.read$10(var2, var3);
         case 11:
            return this.readline$11(var2, var3);
         case 12:
            return this.readlines$12(var2, var3);
         case 13:
            return this.truncate$13(var2, var3);
         case 14:
            return this.write$14(var2, var3);
         case 15:
            return this.writelines$15(var2, var3);
         case 16:
            return this.flush$16(var2, var3);
         case 17:
            return this.getvalue$17(var2, var3);
         case 18:
            return this.test$18(var2, var3);
         default:
            return null;
      }
   }
}
