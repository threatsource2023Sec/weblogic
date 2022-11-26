package wsgiref;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@MTime(1498849383000L)
@Filename("wsgiref/headers.py")
public class headers$py extends PyFunctionTable implements PyRunnable {
   static headers$py self;
   static final PyCode f$0;
   static final PyCode _formatparam$1;
   static final PyCode Headers$2;
   static final PyCode __init__$3;
   static final PyCode __len__$4;
   static final PyCode __setitem__$5;
   static final PyCode __delitem__$6;
   static final PyCode __getitem__$7;
   static final PyCode has_key$8;
   static final PyCode get_all$9;
   static final PyCode get$10;
   static final PyCode keys$11;
   static final PyCode values$12;
   static final PyCode items$13;
   static final PyCode __repr__$14;
   static final PyCode __str__$15;
   static final PyCode setdefault$16;
   static final PyCode add_header$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Manage HTTP Response Headers\n\nMuch of this module is red-handedly pilfered from email.message in the stdlib,\nso portions are Copyright (C) 2001,2002 Python Software Foundation, and were\nwritten by Barry Warsaw.\n"));
      var1.setline(6);
      PyString.fromInterned("Manage HTTP Response Headers\n\nMuch of this module is red-handedly pilfered from email.message in the stdlib,\nso portions are Copyright (C) 2001,2002 Python Software Foundation, and were\nwritten by Barry Warsaw.\n");
      var1.setline(8);
      String[] var3 = new String[]{"ListType", "TupleType"};
      PyObject[] var5 = imp.importFrom("types", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("ListType", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("TupleType", var4);
      var4 = null;
      var1.setline(12);
      PyObject var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(13);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ \\(\\)<>@,;:\\\\\"/\\[\\]\\?=]"));
      var1.setlocal("tspecials", var6);
      var3 = null;
      var1.setline(15);
      var5 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      PyFunction var7 = new PyFunction(var1.f_globals, var5, _formatparam$1, PyString.fromInterned("Convenience function to format and return a key=value pair.\n\n    This will quote the value if needed or if quote is true.\n    "));
      var1.setlocal("_formatparam", var7);
      var3 = null;
      var1.setline(30);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Headers", var5, Headers$2);
      var1.setlocal("Headers", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _formatparam$1(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyString.fromInterned("Convenience function to format and return a key=value pair.\n\n    This will quote the value if needed or if quote is true.\n    ");
      var1.setline(20);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(21);
         var10000 = var1.getlocal(2);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("tspecials").__getattr__("search").__call__(var2, var1.getlocal(1));
         }

         if (var10000.__nonzero__()) {
            var1.setline(22);
            var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\\\")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\\\""));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(23);
            var3 = PyString.fromInterned("%s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(25);
            var3 = PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(27);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Headers$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Manage a collection of HTTP response headers"));
      var1.setline(32);
      PyString.fromInterned("Manage a collection of HTTP response headers");
      var1.setline(34);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$4, PyString.fromInterned("Return the total number of headers, including duplicates."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(43);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$5, PyString.fromInterned("Set the value of a header."));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$6, PyString.fromInterned("Delete all occurrences of a header, if present.\n\n        Does *not* raise an exception if the header is missing.\n        "));
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$7, PyString.fromInterned("Get the first header value for 'name'\n\n        Return None if the header is missing instead of raising an exception.\n\n        Note that if the header appeared multiple times, the first exactly which\n        occurrance gets returned is undefined.  Use getall() to get all\n        the values matching a header field name.\n        "));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$8, PyString.fromInterned("Return true if the message contains the header."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(71);
      PyObject var5 = var1.getname("has_key");
      var1.setlocal("__contains__", var5);
      var3 = null;
      var1.setline(74);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_all$9, PyString.fromInterned("Return a list of all the values for the named field.\n\n        These will be sorted in the order they appeared in the original header\n        list or were added to this instance, and may contain duplicates.  Any\n        fields deleted and re-inserted are always appended to the header list.\n        If no fields exist with the given name, returns an empty list.\n        "));
      var1.setlocal("get_all", var4);
      var3 = null;
      var1.setline(86);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$10, PyString.fromInterned("Get the first header value for 'name', or return 'default'"));
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$11, PyString.fromInterned("Return a list of all the header field names.\n\n        These will be sorted in the order they appeared in the original header\n        list, or were added to this instance, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        "));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$12, PyString.fromInterned("Return a list of all header values.\n\n        These will be sorted in the order they appeared in the original header\n        list, or were added to this instance, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        "));
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$13, PyString.fromInterned("Get all the header fields and values.\n\n        These will be sorted in the order they were in the original header\n        list, or were added to this instance, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        "));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$14, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$15, PyString.fromInterned("str() returns the formatted headers, complete with end line,\n        suitable for direct HTTP transmission."));
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(133);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setdefault$16, PyString.fromInterned("Return first matching header value for 'name', or 'value'\n\n        If there is no header named 'name', add a new header with name 'name'\n        and value 'value'."));
      var1.setlocal("setdefault", var4);
      var3 = null;
      var1.setline(145);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_header$17, PyString.fromInterned("Extended header setting.\n\n        _name is the header field to add.  keyword arguments can be used to set\n        additional parameters for the header field, with underscores converted\n        to dashes.  Normally the parameter will be added as key=\"value\" unless\n        value is None, in which case only the key will be added.\n\n        Example:\n\n        h.add_header('content-disposition', 'attachment', filename='bud.gif')\n\n        Note that unlike the corresponding 'email.message' method, this does\n        *not* handle '(charset, language, value)' tuples: all values must be\n        strings or None.\n        "));
      var1.setlocal("add_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._isnot(var1.getglobal("ListType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(36);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Headers must be a list of name/value tuples")));
      } else {
         var1.setline(37);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_headers", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __len__$4(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyString.fromInterned("Return the total number of headers, including duplicates.");
      var1.setline(41);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_headers"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setitem__$5(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyString.fromInterned("Set the value of a header.");
      var1.setline(45);
      var1.getlocal(0).__delitem__(var1.getlocal(1));
      var1.setline(46);
      var1.getlocal(0).__getattr__("_headers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$6(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("Delete all occurrences of a header, if present.\n\n        Does *not* raise an exception if the header is missing.\n        ");
      var1.setline(53);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(54);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(54);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(54);
            var1.dellocal(2);
            PyList var6 = var10000;
            var1.getlocal(0).__getattr__("_headers").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var6);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(54);
         PyObject var5 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
         PyObject var10001 = var5._ne(var1.getlocal(1));
         var5 = null;
         if (var10001.__nonzero__()) {
            var1.setline(54);
            var1.getlocal(2).__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject __getitem__$7(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString.fromInterned("Get the first header value for 'name'\n\n        Return None if the header is missing instead of raising an exception.\n\n        Note that if the header appeared multiple times, the first exactly which\n        occurrance gets returned is undefined.  Use getall() to get all\n        the values matching a header field name.\n        ");
      var1.setline(65);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$8(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Return true if the message contains the header.");
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_all$9(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("Return a list of all the values for the named field.\n\n        These will be sorted in the order they appeared in the original header\n        list or were added to this instance, and may contain duplicates.  Any\n        fields deleted and re-inserted are always appended to the header list.\n        If no fields exist with the given name, returns an empty list.\n        ");
      var1.setline(82);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(83);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(83);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(83);
            var1.dellocal(2);
            PyList var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(83);
         PyObject var5 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
         PyObject var10001 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10001.__nonzero__()) {
            var1.setline(83);
            var1.getlocal(2).__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1)));
         }
      }
   }

   public PyObject get$10(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("Get the first header value for 'name', or return 'default'");
      var1.setline(88);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      PyObject var10000;
      PyObject var7;
      do {
         var1.setline(89);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(92);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(90);
         var7 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         var10000 = var7._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(91);
      var7 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject keys$11(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyString.fromInterned("Return a list of all the header field names.\n\n        These will be sorted in the order they appeared in the original header\n        list, or were added to this instance, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        ");
      var1.setline(103);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(103);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(103);
            var1.dellocal(1);
            PyList var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(103);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject values$12(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Return a list of all header values.\n\n        These will be sorted in the order they appeared in the original header\n        list, or were added to this instance, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        ");
      var1.setline(113);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(113);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(113);
            var1.dellocal(1);
            PyList var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(113);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject items$13(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyString.fromInterned("Get all the header fields and values.\n\n        These will be sorted in the order they were in the original header\n        list, or were added to this instance, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        ");
      var1.setline(123);
      PyObject var3 = var1.getlocal(0).__getattr__("_headers").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$14(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = PyString.fromInterned("Headers(%r)")._mod(var1.getlocal(0).__getattr__("_headers"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$15(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyString.fromInterned("str() returns the formatted headers, complete with end line,\n        suitable for direct HTTP transmission.");
      var1.setline(131);
      PyObject var10000 = PyString.fromInterned("\r\n").__getattr__("join");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(131);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(131);
            var1.dellocal(1);
            var3 = var10000.__call__(var2, var10002._add(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(131);
         var1.getlocal(1).__call__(var2, PyString.fromInterned("%s: %s")._mod(var1.getlocal(2)));
      }
   }

   public PyObject setdefault$16(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("Return first matching header value for 'name', or 'value'\n\n        If there is no header named 'name', add a new header with name 'name'\n        and value 'value'.");
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(139);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(140);
         var1.getlocal(0).__getattr__("_headers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
         var1.setline(141);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(143);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject add_header$17(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyString.fromInterned("Extended header setting.\n\n        _name is the header field to add.  keyword arguments can be used to set\n        additional parameters for the header field, with underscores converted\n        to dashes.  Normally the parameter will be added as key=\"value\" unless\n        value is None, in which case only the key will be added.\n\n        Example:\n\n        h.add_header('content-disposition', 'attachment', filename='bud.gif')\n\n        Note that unlike the corresponding 'email.message' method, this does\n        *not* handle '(charset, language, value)' tuples: all values must be\n        strings or None.\n        ");
      var1.setline(161);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(162);
      PyObject var7 = var1.getlocal(2);
      PyObject var10000 = var7._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(163);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
      }

      var1.setline(164);
      var7 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(164);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(169);
            var1.getlocal(0).__getattr__("_headers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("; ").__getattr__("join").__call__(var2, var1.getlocal(4))})));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(165);
         PyObject var8 = var1.getlocal(6);
         var10000 = var8._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(166);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"), (PyObject)PyString.fromInterned("-")));
         } else {
            var1.setline(168);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("_formatparam").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"), (PyObject)PyString.fromInterned("-")), var1.getlocal(6)));
         }
      }
   }

   public headers$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"param", "value", "quote"};
      _formatparam$1 = Py.newCode(3, var2, var1, "_formatparam", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Headers$2 = Py.newCode(0, var2, var1, "Headers", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "headers"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 34, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$4 = Py.newCode(1, var2, var1, "__len__", 39, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "val"};
      __setitem__$5 = Py.newCode(3, var2, var1, "__setitem__", 43, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "_[54_28]", "kv"};
      __delitem__$6 = Py.newCode(2, var2, var1, "__delitem__", 48, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$7 = Py.newCode(2, var2, var1, "__getitem__", 56, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      has_key$8 = Py.newCode(2, var2, var1, "has_key", 67, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "_[83_16]", "kv"};
      get_all$9 = Py.newCode(2, var2, var1, "get_all", 74, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "default", "k", "v"};
      get$10 = Py.newCode(3, var2, var1, "get", 86, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[103_16]", "k", "v"};
      keys$11 = Py.newCode(1, var2, var1, "keys", 95, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[113_16]", "v", "k"};
      values$12 = Py.newCode(1, var2, var1, "values", 105, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$13 = Py.newCode(1, var2, var1, "items", 115, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$14 = Py.newCode(1, var2, var1, "__repr__", 125, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[131_28]", "kv"};
      __str__$15 = Py.newCode(1, var2, var1, "__str__", 128, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "result"};
      setdefault$16 = Py.newCode(3, var2, var1, "setdefault", 133, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_name", "_value", "_params", "parts", "k", "v"};
      add_header$17 = Py.newCode(4, var2, var1, "add_header", 145, false, true, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new headers$py("wsgiref/headers$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(headers$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._formatparam$1(var2, var3);
         case 2:
            return this.Headers$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__len__$4(var2, var3);
         case 5:
            return this.__setitem__$5(var2, var3);
         case 6:
            return this.__delitem__$6(var2, var3);
         case 7:
            return this.__getitem__$7(var2, var3);
         case 8:
            return this.has_key$8(var2, var3);
         case 9:
            return this.get_all$9(var2, var3);
         case 10:
            return this.get$10(var2, var3);
         case 11:
            return this.keys$11(var2, var3);
         case 12:
            return this.values$12(var2, var3);
         case 13:
            return this.items$13(var2, var3);
         case 14:
            return this.__repr__$14(var2, var3);
         case 15:
            return this.__str__$15(var2, var3);
         case 16:
            return this.setdefault$16(var2, var3);
         case 17:
            return this.add_header$17(var2, var3);
         default:
            return null;
      }
   }
}
