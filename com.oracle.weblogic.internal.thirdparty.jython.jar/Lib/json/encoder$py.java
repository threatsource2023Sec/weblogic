package json;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/encoder.py")
public class encoder$py extends PyFunctionTable implements PyRunnable {
   static encoder$py self;
   static final PyCode f$0;
   static final PyCode encode_basestring$1;
   static final PyCode replace$2;
   static final PyCode py_encode_basestring_ascii$3;
   static final PyCode replace$4;
   static final PyCode JSONEncoder$5;
   static final PyCode __init__$6;
   static final PyCode default$7;
   static final PyCode encode$8;
   static final PyCode iterencode$9;
   static final PyCode _encoder$10;
   static final PyCode floatstr$11;
   static final PyCode _make_iterencode$12;
   static final PyCode _iterencode_list$13;
   static final PyCode _iterencode_dict$14;
   static final PyCode f$15;
   static final PyCode _iterencode$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Implementation of JSONEncoder\n"));
      var1.setline(2);
      PyString.fromInterned("Implementation of JSONEncoder\n");
      var1.setline(3);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;

      PyObject var4;
      PyException var7;
      String[] var8;
      PyObject[] var9;
      try {
         var1.setline(6);
         var8 = new String[]{"encode_basestring_ascii"};
         var9 = imp.importFrom("_json", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("c_encode_basestring_ascii", var4);
         var4 = null;
      } catch (Throwable var6) {
         var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(8);
         var4 = var1.getname("None");
         var1.setlocal("c_encode_basestring_ascii", var4);
         var4 = null;
      }

      try {
         var1.setline(10);
         var8 = new String[]{"make_encoder"};
         var9 = imp.importFrom("_json", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("c_make_encoder", var4);
         var4 = null;
      } catch (Throwable var5) {
         var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(12);
         var4 = var1.getname("None");
         var1.setlocal("c_make_encoder", var4);
         var4 = null;
      }

      var1.setline(14);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\x00-\\x1f\\\\\"\\b\\f\\n\\r\\t]"));
      var1.setlocal("ESCAPE", var3);
      var3 = null;
      var1.setline(15);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\\\\"]|[^\\ -~])"));
      var1.setlocal("ESCAPE_ASCII", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\x80-\\xff]"));
      var1.setlocal("HAS_UTF8", var3);
      var3 = null;
      var1.setline(17);
      PyDictionary var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("\\"), PyString.fromInterned("\\\\"), PyString.fromInterned("\""), PyString.fromInterned("\\\""), PyString.fromInterned("\b"), PyString.fromInterned("\\b"), PyString.fromInterned("\f"), PyString.fromInterned("\\f"), PyString.fromInterned("\n"), PyString.fromInterned("\\n"), PyString.fromInterned("\r"), PyString.fromInterned("\\r"), PyString.fromInterned("\t"), PyString.fromInterned("\\t")});
      var1.setlocal("ESCAPE_DCT", var10);
      var3 = null;
      var1.setline(26);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(32)).__iter__();

      while(true) {
         var1.setline(26);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(30);
            var3 = var1.getname("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf"));
            var1.setlocal("INFINITY", var3);
            var3 = null;
            var1.setline(31);
            var3 = var1.getname("repr");
            var1.setlocal("FLOAT_REPR", var3);
            var3 = null;
            var1.setline(33);
            var9 = Py.EmptyObjects;
            PyFunction var11 = new PyFunction(var1.f_globals, var9, encode_basestring$1, PyString.fromInterned("Return a JSON representation of a Python string\n\n    "));
            var1.setlocal("encode_basestring", var11);
            var3 = null;
            var1.setline(42);
            var9 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var9, py_encode_basestring_ascii$3, PyString.fromInterned("Return an ASCII-only JSON representation of a Python string\n\n    "));
            var1.setlocal("py_encode_basestring_ascii", var11);
            var3 = null;
            var1.setline(67);
            PyObject var10000 = var1.getname("c_encode_basestring_ascii");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getname("py_encode_basestring_ascii");
            }

            var3 = var10000;
            var1.setlocal("encode_basestring_ascii", var3);
            var3 = null;
            var1.setline(70);
            var9 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("JSONEncoder", var9, JSONEncoder$5);
            var1.setlocal("JSONEncoder", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(271);
            var9 = new PyObject[]{var1.getname("ValueError"), var1.getname("basestring"), var1.getname("dict"), var1.getname("float"), var1.getname("id"), var1.getname("int"), var1.getname("isinstance"), var1.getname("list"), var1.getname("long"), var1.getname("str"), var1.getname("tuple")};
            var11 = new PyFunction(var1.f_globals, var9, _make_iterencode$12, (PyObject)null);
            var1.setlocal("_make_iterencode", var11);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("i", var4);
         var1.setline(27);
         var1.getname("ESCAPE_DCT").__getattr__("setdefault").__call__(var2, var1.getname("chr").__call__(var2, var1.getname("i")), PyString.fromInterned("\\u{0:04x}").__getattr__("format").__call__(var2, var1.getname("i")));
      }
   }

   public PyObject encode_basestring$1(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Return a JSON representation of a Python string\n\n    ");
      var1.setline(37);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, replace$2, (PyObject)null);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(39);
      PyObject var5 = PyString.fromInterned("\"")._add(var1.getglobal("ESCAPE").__getattr__("sub").__call__(var2, var1.getlocal(1), var1.getlocal(0)))._add(PyString.fromInterned("\""));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject replace$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getglobal("ESCAPE_DCT").__getitem__(var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject py_encode_basestring_ascii$3(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Return an ASCII-only JSON representation of a Python string\n\n    ");
      var1.setline(46);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("HAS_UTF8").__getattr__("search").__call__(var2, var1.getlocal(0));
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(47);
         var3 = var1.getlocal(0).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(48);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, replace$4, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(64);
      var3 = PyString.fromInterned("\"")._add(var1.getglobal("str").__call__(var2, var1.getglobal("ESCAPE_ASCII").__getattr__("sub").__call__(var2, var1.getlocal(1), var1.getlocal(0))))._add(PyString.fromInterned("\""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject replace$4(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(51);
         var3 = var1.getglobal("ESCAPE_DCT").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(53);
            PyObject var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(54);
            var5 = var1.getlocal(2);
            PyObject var10000 = var5._lt(Py.newInteger(65536));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(55);
               var3 = PyString.fromInterned("\\u{0:04x}").__getattr__("format").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(59);
               var5 = var1.getlocal(2);
               var5 = var5._isub(Py.newInteger(65536));
               var1.setlocal(2, var5);
               var1.setline(60);
               var5 = Py.newInteger(55296)._or(var1.getlocal(2)._rshift(Py.newInteger(10))._and(Py.newInteger(1023)));
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(61);
               var5 = Py.newInteger(56320)._or(var1.getlocal(2)._and(Py.newInteger(1023)));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(62);
               var3 = PyString.fromInterned("\\u{0:04x}\\u{1:04x}").__getattr__("format").__call__(var2, var1.getlocal(3), var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject JSONEncoder$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Extensible JSON <http://json.org> encoder for Python data structures.\n\n    Supports the following objects and types by default:\n\n    +-------------------+---------------+\n    | Python            | JSON          |\n    +===================+===============+\n    | dict              | object        |\n    +-------------------+---------------+\n    | list, tuple       | array         |\n    +-------------------+---------------+\n    | str, unicode      | string        |\n    +-------------------+---------------+\n    | int, long, float  | number        |\n    +-------------------+---------------+\n    | True              | true          |\n    +-------------------+---------------+\n    | False             | false         |\n    +-------------------+---------------+\n    | None              | null          |\n    +-------------------+---------------+\n\n    To extend this to recognize other objects, subclass and implement a\n    ``.default()`` method with another method that returns a serializable\n    object for ``o`` if possible, otherwise it should call the superclass\n    implementation (to raise ``TypeError``).\n\n    "));
      var1.setline(98);
      PyString.fromInterned("Extensible JSON <http://json.org> encoder for Python data structures.\n\n    Supports the following objects and types by default:\n\n    +-------------------+---------------+\n    | Python            | JSON          |\n    +===================+===============+\n    | dict              | object        |\n    +-------------------+---------------+\n    | list, tuple       | array         |\n    +-------------------+---------------+\n    | str, unicode      | string        |\n    +-------------------+---------------+\n    | int, long, float  | number        |\n    +-------------------+---------------+\n    | True              | true          |\n    +-------------------+---------------+\n    | False             | false         |\n    +-------------------+---------------+\n    | None              | null          |\n    +-------------------+---------------+\n\n    To extend this to recognize other objects, subclass and implement a\n    ``.default()`` method with another method that returns a serializable\n    object for ``o`` if possible, otherwise it should call the superclass\n    implementation (to raise ``TypeError``).\n\n    ");
      var1.setline(99);
      PyString var3 = PyString.fromInterned(", ");
      var1.setlocal("item_separator", var3);
      var3 = null;
      var1.setline(100);
      var3 = PyString.fromInterned(": ");
      var1.setlocal("key_separator", var3);
      var3 = null;
      var1.setline(101);
      PyObject[] var4 = new PyObject[]{var1.getname("False"), var1.getname("True"), var1.getname("True"), var1.getname("True"), var1.getname("False"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("utf-8"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$6, PyString.fromInterned("Constructor for JSONEncoder, with sensible defaults.\n\n        If skipkeys is false, then it is a TypeError to attempt\n        encoding of keys that are not str, int, long, float or None.  If\n        skipkeys is True, such items are simply skipped.\n\n        If *ensure_ascii* is true (the default), all non-ASCII\n        characters in the output are escaped with \\uXXXX sequences,\n        and the results are str instances consisting of ASCII\n        characters only.  If ensure_ascii is False, a result may be a\n        unicode instance.  This usually happens if the input contains\n        unicode strings or the *encoding* parameter is used.\n\n        If check_circular is true, then lists, dicts, and custom encoded\n        objects will be checked for circular references during encoding to\n        prevent an infinite recursion (which would cause an OverflowError).\n        Otherwise, no such check takes place.\n\n        If allow_nan is true, then NaN, Infinity, and -Infinity will be\n        encoded as such.  This behavior is not JSON specification compliant,\n        but is consistent with most JavaScript based encoders and decoders.\n        Otherwise, it will be a ValueError to encode such floats.\n\n        If sort_keys is true, then the output of dictionaries will be\n        sorted by key; this is useful for regression tests to ensure\n        that JSON serializations can be compared on a day-to-day basis.\n\n        If indent is a non-negative integer, then JSON array\n        elements and object members will be pretty-printed with that\n        indent level.  An indent level of 0 will only insert newlines.\n        None is the most compact representation.  Since the default\n        item separator is ', ',  the output might include trailing\n        whitespace when indent is specified.  You can use\n        separators=(',', ': ') to avoid this.\n\n        If specified, separators should be a (item_separator, key_separator)\n        tuple.  The default is (', ', ': ').  To get the most compact JSON\n        representation you should specify (',', ':') to eliminate whitespace.\n\n        If specified, default is a function that gets called for objects\n        that can't otherwise be serialized.  It should return a JSON encodable\n        version of the object or raise a ``TypeError``.\n\n        If encoding is not None, then all input strings will be\n        transformed into unicode using that encoding prior to JSON-encoding.\n        The default is UTF-8.\n\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(165);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, default$7, PyString.fromInterned("Implement this method in a subclass such that it returns\n        a serializable object for ``o``, or calls the base implementation\n        (to raise a ``TypeError``).\n\n        For example, to support arbitrary iterators, you could\n        implement default like this::\n\n            def default(self, o):\n                try:\n                    iterable = iter(o)\n                except TypeError:\n                    pass\n                else:\n                    return list(iterable)\n                return JSONEncoder.default(self, o)\n\n        "));
      var1.setlocal("default", var5);
      var3 = null;
      var1.setline(185);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, encode$8, PyString.fromInterned("Return a JSON string representation of a Python data structure.\n\n        >>> JSONEncoder().encode({\"foo\": [\"bar\", \"baz\"]})\n        '{\"foo\": [\"bar\", \"baz\"]}'\n\n        "));
      var1.setlocal("encode", var5);
      var3 = null;
      var1.setline(211);
      var4 = new PyObject[]{var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, iterencode$9, PyString.fromInterned("Encode the given object and yield each string\n        representation as available.\n\n        For example::\n\n            for chunk in JSONEncoder().iterencode(bigobject):\n                mysocket.write(chunk)\n\n        "));
      var1.setlocal("iterencode", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyString.fromInterned("Constructor for JSONEncoder, with sensible defaults.\n\n        If skipkeys is false, then it is a TypeError to attempt\n        encoding of keys that are not str, int, long, float or None.  If\n        skipkeys is True, such items are simply skipped.\n\n        If *ensure_ascii* is true (the default), all non-ASCII\n        characters in the output are escaped with \\uXXXX sequences,\n        and the results are str instances consisting of ASCII\n        characters only.  If ensure_ascii is False, a result may be a\n        unicode instance.  This usually happens if the input contains\n        unicode strings or the *encoding* parameter is used.\n\n        If check_circular is true, then lists, dicts, and custom encoded\n        objects will be checked for circular references during encoding to\n        prevent an infinite recursion (which would cause an OverflowError).\n        Otherwise, no such check takes place.\n\n        If allow_nan is true, then NaN, Infinity, and -Infinity will be\n        encoded as such.  This behavior is not JSON specification compliant,\n        but is consistent with most JavaScript based encoders and decoders.\n        Otherwise, it will be a ValueError to encode such floats.\n\n        If sort_keys is true, then the output of dictionaries will be\n        sorted by key; this is useful for regression tests to ensure\n        that JSON serializations can be compared on a day-to-day basis.\n\n        If indent is a non-negative integer, then JSON array\n        elements and object members will be pretty-printed with that\n        indent level.  An indent level of 0 will only insert newlines.\n        None is the most compact representation.  Since the default\n        item separator is ', ',  the output might include trailing\n        whitespace when indent is specified.  You can use\n        separators=(',', ': ') to avoid this.\n\n        If specified, separators should be a (item_separator, key_separator)\n        tuple.  The default is (', ', ': ').  To get the most compact JSON\n        representation you should specify (',', ':') to eliminate whitespace.\n\n        If specified, default is a function that gets called for objects\n        that can't otherwise be serialized.  It should return a JSON encodable\n        version of the object or raise a ``TypeError``.\n\n        If encoding is not None, then all input strings will be\n        transformed into unicode using that encoding prior to JSON-encoding.\n        The default is UTF-8.\n\n        ");
      var1.setline(153);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("skipkeys", var3);
      var3 = null;
      var1.setline(154);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("ensure_ascii", var3);
      var3 = null;
      var1.setline(155);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("check_circular", var3);
      var3 = null;
      var1.setline(156);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("allow_nan", var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("sort_keys", var3);
      var3 = null;
      var1.setline(158);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("indent", var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(160);
         var3 = var1.getlocal(7);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.getlocal(0).__setattr__("item_separator", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("key_separator", var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(161);
      var3 = var1.getlocal(9);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         var3 = var1.getlocal(9);
         var1.getlocal(0).__setattr__("default", var3);
         var3 = null;
      }

      var1.setline(163);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject default$7(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyString.fromInterned("Implement this method in a subclass such that it returns\n        a serializable object for ``o``, or calls the base implementation\n        (to raise a ``TypeError``).\n\n        For example, to support arbitrary iterators, you could\n        implement default like this::\n\n            def default(self, o):\n                try:\n                    iterable = iter(o)\n                except TypeError:\n                    pass\n                else:\n                    return list(iterable)\n                return JSONEncoder.default(self, o)\n\n        ");
      var1.setline(183);
      throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned(" is not JSON serializable"))));
   }

   public PyObject encode$8(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Return a JSON string representation of a Python data structure.\n\n        >>> JSONEncoder().encode({\"foo\": [\"bar\", \"baz\"]})\n        '{\"foo\": [\"bar\", \"baz\"]}'\n\n        ");
      var1.setline(193);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(194);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(195);
            var3 = var1.getlocal(0).__getattr__("encoding");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(196);
            var3 = var1.getlocal(2);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._eq(PyString.fromInterned("utf-8"));
               var3 = null;
               var10000 = var10000.__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(198);
               var3 = var1.getlocal(1).__getattr__("decode").__call__(var2, var1.getlocal(2));
               var1.setlocal(1, var3);
               var3 = null;
            }
         }

         var1.setline(199);
         if (var1.getlocal(0).__getattr__("ensure_ascii").__nonzero__()) {
            var1.setline(200);
            var3 = var1.getglobal("encode_basestring_ascii").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(202);
            var3 = var1.getglobal("encode_basestring").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(206);
         var10000 = var1.getlocal(0).__getattr__("iterencode");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
         String[] var5 = new String[]{"_one_shot"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         PyObject var6 = var10000;
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(207);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__not__().__nonzero__()) {
            var1.setline(208);
            var6 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var6);
            var4 = null;
         }

         var1.setline(209);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject iterencode$9(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyString.fromInterned("Encode the given object and yield each string\n        representation as available.\n\n        For example::\n\n            for chunk in JSONEncoder().iterencode(bigobject):\n                mysocket.write(chunk)\n\n        ");
      var1.setline(221);
      PyDictionary var3;
      PyObject var4;
      if (var1.getlocal(0).__getattr__("check_circular").__nonzero__()) {
         var1.setline(222);
         var3 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(224);
         var4 = var1.getglobal("None");
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(225);
      if (var1.getlocal(0).__getattr__("ensure_ascii").__nonzero__()) {
         var1.setline(226);
         var4 = var1.getglobal("encode_basestring_ascii");
         var1.setlocal(4, var4);
         var3 = null;
      } else {
         var1.setline(228);
         var4 = var1.getglobal("encode_basestring");
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(229);
      var4 = var1.getlocal(0).__getattr__("encoding");
      PyObject var10000 = var4._ne(PyString.fromInterned("utf-8"));
      var3 = null;
      PyObject[] var5;
      PyFunction var6;
      if (var10000.__nonzero__()) {
         var1.setline(230);
         var5 = new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("encoding")};
         var6 = new PyFunction(var1.f_globals, var5, _encoder$10, (PyObject)null);
         var1.setlocal(4, var6);
         var3 = null;
      }

      var1.setline(235);
      var5 = new PyObject[]{var1.getlocal(0).__getattr__("allow_nan"), var1.getglobal("FLOAT_REPR"), var1.getglobal("INFINITY"), var1.getglobal("INFINITY").__neg__()};
      var6 = new PyFunction(var1.f_globals, var5, floatstr$11, (PyObject)null);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(258);
      var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var4 = var1.getglobal("c_make_encoder");
         var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("indent");
            var10000 = var4._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("sort_keys").__not__();
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(260);
         var10000 = var1.getglobal("c_make_encoder");
         var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("default"), var1.getlocal(4), var1.getlocal(0).__getattr__("indent"), var1.getlocal(0).__getattr__("key_separator"), var1.getlocal(0).__getattr__("item_separator"), var1.getlocal(0).__getattr__("sort_keys"), var1.getlocal(0).__getattr__("skipkeys"), var1.getlocal(0).__getattr__("allow_nan")};
         var4 = var10000.__call__(var2, var5);
         var1.setlocal(6, var4);
         var3 = null;
      } else {
         var1.setline(265);
         var10000 = var1.getglobal("_make_iterencode");
         var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("default"), var1.getlocal(4), var1.getlocal(0).__getattr__("indent"), var1.getlocal(5), var1.getlocal(0).__getattr__("key_separator"), var1.getlocal(0).__getattr__("item_separator"), var1.getlocal(0).__getattr__("sort_keys"), var1.getlocal(0).__getattr__("skipkeys"), var1.getlocal(2)};
         var4 = var10000.__call__(var2, var5);
         var1.setlocal(6, var4);
         var3 = null;
      }

      var1.setline(269);
      var4 = var1.getlocal(6).__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _encoder$10(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
         var1.setline(232);
         var3 = var1.getlocal(0).__getattr__("decode").__call__(var2, var1.getlocal(2));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(233);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject floatstr$11(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ne(var1.getlocal(0));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(242);
         var4 = PyString.fromInterned("NaN");
         var1.setlocal(5, var4);
         var3 = null;
      } else {
         var1.setline(243);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(244);
            var4 = PyString.fromInterned("Infinity");
            var1.setlocal(5, var4);
            var3 = null;
         } else {
            var1.setline(245);
            var3 = var1.getlocal(0);
            var10000 = var3._eq(var1.getlocal(4));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(248);
               var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(246);
            var4 = PyString.fromInterned("-Infinity");
            var1.setlocal(5, var4);
            var3 = null;
         }
      }

      var1.setline(250);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(251);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Out of range float values are not JSON compliant: ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.setline(255);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _make_iterencode$12(PyFrame var1, ThreadState var2) {
      var1.to_cell(10, 1);
      var1.to_cell(15, 2);
      var1.to_cell(4, 3);
      var1.to_cell(6, 4);
      var1.to_cell(11, 5);
      var1.to_cell(16, 6);
      var1.to_cell(12, 7);
      var1.to_cell(1, 8);
      var1.to_cell(2, 9);
      var1.to_cell(20, 11);
      var1.to_cell(14, 12);
      var1.to_cell(7, 13);
      var1.to_cell(19, 14);
      var1.to_cell(8, 15);
      var1.to_cell(5, 17);
      var1.to_cell(18, 18);
      var1.to_cell(0, 19);
      var1.to_cell(3, 20);
      var1.to_cell(17, 21);
      var1.to_cell(13, 22);
      var1.setline(287);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = _iterencode_list$13;
      var3 = new PyObject[]{var1.getclosure(19), var1.getclosure(12), var1.getclosure(1), var1.getclosure(20), var1.getclosure(4), var1.getclosure(6), var1.getclosure(5), var1.getclosure(9), var1.getclosure(2), var1.getclosure(18), var1.getclosure(14), var1.getclosure(22), var1.getclosure(3), var1.getclosure(21), var1.getclosure(11), var1.getclosure(16), var1.getclosure(7), var1.getclosure(0), var1.getclosure(10)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setderef(16, var4);
      var3 = null;
      var1.setline(340);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = _iterencode_dict$14;
      var3 = new PyObject[]{var1.getclosure(19), var1.getclosure(12), var1.getclosure(1), var1.getclosure(20), var1.getclosure(4), var1.getclosure(13), var1.getclosure(6), var1.getclosure(5), var1.getclosure(22), var1.getclosure(3), var1.getclosure(2), var1.getclosure(18), var1.getclosure(14), var1.getclosure(15), var1.getclosure(9), var1.getclosure(17), var1.getclosure(21), var1.getclosure(11), var1.getclosure(16), var1.getclosure(7), var1.getclosure(0), var1.getclosure(10)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setderef(0, var4);
      var3 = null;
      var1.setline(416);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = _iterencode$16;
      var3 = new PyObject[]{var1.getclosure(6), var1.getclosure(5), var1.getclosure(9), var1.getclosure(2), var1.getclosure(18), var1.getclosure(14), var1.getclosure(22), var1.getclosure(3), var1.getclosure(21), var1.getclosure(11), var1.getclosure(16), var1.getclosure(7), var1.getclosure(0), var1.getclosure(19), var1.getclosure(12), var1.getclosure(1), var1.getclosure(8), var1.getclosure(10)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setderef(10, var4);
      var3 = null;
      var1.setline(447);
      PyObject var5 = var1.getderef(10);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _iterencode_list$13(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyString var13;
      label152: {
         PyObject var4;
         PyObject var5;
         PyObject var8;
         Object[] var9;
         PyObject[] var10;
         PyObject var12;
         PyObject var10002;
         PyTuple var10003;
         label143: {
            PyObject var6;
            Object[] var7;
            Object var10000;
            switch (var1.f_lasti) {
               case 0:
               default:
                  var1.setline(288);
                  if (var1.getlocal(0).__not__().__nonzero__()) {
                     var1.setline(289);
                     var1.setline(289);
                     var13 = PyString.fromInterned("[]");
                     var1.f_lasti = 1;
                     var3 = new Object[3];
                     var1.f_savedlocals = var3;
                     return var13;
                  }

                  var1.setline(291);
                  var8 = var1.getderef(0);
                  var12 = var8._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var12.__nonzero__()) {
                     var1.setline(292);
                     var8 = var1.getderef(1).__call__(var2, var1.getlocal(0));
                     var1.setlocal(2, var8);
                     var3 = null;
                     var1.setline(293);
                     var8 = var1.getlocal(2);
                     var12 = var8._in(var1.getderef(0));
                     var3 = null;
                     if (var12.__nonzero__()) {
                        var1.setline(294);
                        throw Py.makeException(var1.getderef(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Circular reference detected")));
                     }

                     var1.setline(295);
                     var8 = var1.getlocal(0);
                     var1.getderef(0).__setitem__(var1.getlocal(2), var8);
                     var3 = null;
                  }

                  var1.setline(296);
                  PyString var11 = PyString.fromInterned("[");
                  var1.setlocal(3, var11);
                  var3 = null;
                  var1.setline(297);
                  var8 = var1.getderef(3);
                  var12 = var8._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var12.__nonzero__()) {
                     var1.setline(298);
                     var8 = var1.getlocal(1);
                     var8 = var8._iadd(Py.newInteger(1));
                     var1.setlocal(1, var8);
                     var1.setline(299);
                     var8 = PyString.fromInterned("\n")._add(PyString.fromInterned(" ")._mul(var1.getderef(3)._mul(var1.getlocal(1))));
                     var1.setlocal(4, var8);
                     var3 = null;
                     var1.setline(300);
                     var8 = var1.getderef(4)._add(var1.getlocal(4));
                     var1.setlocal(5, var8);
                     var3 = null;
                     var1.setline(301);
                     var8 = var1.getlocal(3);
                     var8 = var8._iadd(var1.getlocal(4));
                     var1.setlocal(3, var8);
                  } else {
                     var1.setline(303);
                     var8 = var1.getglobal("None");
                     var1.setlocal(4, var8);
                     var3 = null;
                     var1.setline(304);
                     var8 = var1.getderef(4);
                     var1.setlocal(5, var8);
                     var3 = null;
                  }

                  var1.setline(305);
                  var8 = var1.getglobal("True");
                  var1.setlocal(6, var8);
                  var3 = null;
                  var1.setline(306);
                  var8 = var1.getlocal(0).__iter__();
                  break label143;
               case 1:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  var1.setline(290);
                  var1.f_lasti = -1;
                  return Py.None;
               case 2:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label143;
               case 3:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label143;
               case 4:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label143;
               case 5:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label143;
               case 6:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label143;
               case 7:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label143;
               case 8:
                  var9 = var1.f_savedlocals;
                  var8 = (PyObject)var9[3];
                  var4 = (PyObject)var9[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  var1.setline(325);
                  var12 = var1.getderef(5);
                  var10002 = var1.getlocal(7);
                  var10 = new PyObject[]{var1.getderef(13), var1.getderef(14)};
                  var10003 = new PyTuple(var10);
                  Arrays.fill(var10, (Object)null);
                  if (var12.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
                     var1.setline(326);
                     var5 = var1.getderef(15).__call__(var2, var1.getlocal(7), var1.getlocal(1));
                     var1.setlocal(8, var5);
                     var5 = null;
                  } else {
                     var1.setline(327);
                     if (var1.getderef(5).__call__(var2, var1.getlocal(7), var1.getderef(16)).__nonzero__()) {
                        var1.setline(328);
                        var5 = var1.getderef(17).__call__(var2, var1.getlocal(7), var1.getlocal(1));
                        var1.setlocal(8, var5);
                        var5 = null;
                     } else {
                        var1.setline(330);
                        var5 = var1.getderef(18).__call__(var2, var1.getlocal(7), var1.getlocal(1));
                        var1.setlocal(8, var5);
                        var5 = null;
                     }
                  }

                  var1.setline(331);
                  var5 = var1.getlocal(8).__iter__();
                  break;
               case 9:
                  var7 = var1.f_savedlocals;
                  var8 = (PyObject)var7[3];
                  var4 = (PyObject)var7[4];
                  var5 = (PyObject)var7[5];
                  var6 = (PyObject)var7[6];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break;
               case 10:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var12 = (PyObject)var10000;
                  break label152;
               case 11:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  } else {
                     var12 = (PyObject)var10000;
                     var1.setline(337);
                     var8 = var1.getderef(0);
                     var12 = var8._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (var12.__nonzero__()) {
                        var1.setline(338);
                        var1.getderef(0).__delitem__(var1.getlocal(2));
                     }

                     var1.f_lasti = -1;
                     return Py.None;
                  }
            }

            var1.setline(331);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var1.setlocal(9, var6);
               var1.setline(332);
               var1.setline(332);
               var12 = var1.getlocal(9);
               var1.f_lasti = 9;
               var7 = new Object[]{null, null, null, var8, var4, var5, var6};
               var1.f_savedlocals = var7;
               return var12;
            }
         }

         var1.setline(306);
         var4 = var8.__iternext__();
         if (var4 != null) {
            var1.setlocal(7, var4);
            var1.setline(307);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(308);
               var5 = var1.getglobal("False");
               var1.setlocal(6, var5);
               var5 = null;
            } else {
               var1.setline(310);
               var5 = var1.getlocal(5);
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(311);
            if (var1.getderef(5).__call__(var2, var1.getlocal(7), var1.getderef(6)).__nonzero__()) {
               var1.setline(312);
               var1.setline(312);
               var12 = var1.getlocal(3)._add(var1.getderef(7).__call__(var2, var1.getlocal(7)));
               var1.f_lasti = 2;
               var9 = new Object[]{null, null, null, var8, var4, null};
               var1.f_savedlocals = var9;
               return var12;
            }

            var1.setline(313);
            var5 = var1.getlocal(7);
            var12 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var12.__nonzero__()) {
               var1.setline(314);
               var1.setline(314);
               var12 = var1.getlocal(3)._add(PyString.fromInterned("null"));
               var1.f_lasti = 3;
               var9 = new Object[7];
               var9[3] = var8;
               var9[4] = var4;
               var1.f_savedlocals = var9;
               return var12;
            }

            var1.setline(315);
            var5 = var1.getlocal(7);
            var12 = var5._is(var1.getglobal("True"));
            var5 = null;
            if (var12.__nonzero__()) {
               var1.setline(316);
               var1.setline(316);
               var12 = var1.getlocal(3)._add(PyString.fromInterned("true"));
               var1.f_lasti = 4;
               var9 = new Object[7];
               var9[3] = var8;
               var9[4] = var4;
               var1.f_savedlocals = var9;
               return var12;
            }

            var1.setline(317);
            var5 = var1.getlocal(7);
            var12 = var5._is(var1.getglobal("False"));
            var5 = null;
            if (var12.__nonzero__()) {
               var1.setline(318);
               var1.setline(318);
               var12 = var1.getlocal(3)._add(PyString.fromInterned("false"));
               var1.f_lasti = 5;
               var9 = new Object[7];
               var9[3] = var8;
               var9[4] = var4;
               var1.f_savedlocals = var9;
               return var12;
            }

            var1.setline(319);
            var12 = var1.getderef(5);
            var10002 = var1.getlocal(7);
            var10 = new PyObject[]{var1.getderef(8), var1.getderef(9)};
            var10003 = new PyTuple(var10);
            Arrays.fill(var10, (Object)null);
            if (var12.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
               var1.setline(320);
               var1.setline(320);
               var12 = var1.getlocal(3)._add(var1.getderef(10).__call__(var2, var1.getlocal(7)));
               var1.f_lasti = 6;
               var9 = new Object[7];
               var9[3] = var8;
               var9[4] = var4;
               var1.f_savedlocals = var9;
               return var12;
            }

            var1.setline(321);
            if (var1.getderef(5).__call__(var2, var1.getlocal(7), var1.getderef(11)).__nonzero__()) {
               var1.setline(322);
               var1.setline(322);
               var12 = var1.getlocal(3)._add(var1.getderef(12).__call__(var2, var1.getlocal(7)));
               var1.f_lasti = 7;
               var9 = new Object[7];
               var9[3] = var8;
               var9[4] = var4;
               var1.f_savedlocals = var9;
               return var12;
            }

            var1.setline(324);
            var1.setline(324);
            var12 = var1.getlocal(3);
            var1.f_lasti = 8;
            var9 = new Object[7];
            var9[3] = var8;
            var9[4] = var4;
            var1.f_savedlocals = var9;
            return var12;
         }

         var1.setline(333);
         var8 = var1.getlocal(4);
         var12 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var12.__nonzero__()) {
            var1.setline(334);
            var8 = var1.getlocal(1);
            var8 = var8._isub(Py.newInteger(1));
            var1.setlocal(1, var8);
            var1.setline(335);
            var1.setline(335);
            var12 = PyString.fromInterned("\n")._add(PyString.fromInterned(" ")._mul(var1.getderef(3)._mul(var1.getlocal(1))));
            var1.f_lasti = 10;
            var3 = new Object[8];
            var1.f_savedlocals = var3;
            return var12;
         }
      }

      var1.setline(336);
      var1.setline(336);
      var13 = PyString.fromInterned("]");
      var1.f_lasti = 11;
      var3 = new Object[8];
      var1.f_savedlocals = var3;
      return var13;
   }

   public PyObject _iterencode_dict$14(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyString var16;
      label263: {
         PyObject var4;
         PyObject var8;
         Object[] var9;
         PyObject var15;
         label264: {
            PyObject var5;
            PyObject var6;
            PyObject[] var12;
            PyObject var10002;
            PyTuple var10003;
            label221: {
               Object[] var7;
               label247: {
                  Object var10000;
                  switch (var1.f_lasti) {
                     case 0:
                     default:
                        var1.setline(341);
                        if (var1.getlocal(0).__not__().__nonzero__()) {
                           var1.setline(342);
                           var1.setline(342);
                           var16 = PyString.fromInterned("{}");
                           var1.f_lasti = 1;
                           var3 = new Object[3];
                           var1.f_savedlocals = var3;
                           return var16;
                        }

                        var1.setline(344);
                        var8 = var1.getderef(0);
                        var15 = var8._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(345);
                           var8 = var1.getderef(1).__call__(var2, var1.getlocal(0));
                           var1.setlocal(2, var8);
                           var3 = null;
                           var1.setline(346);
                           var8 = var1.getlocal(2);
                           var15 = var8._in(var1.getderef(0));
                           var3 = null;
                           if (var15.__nonzero__()) {
                              var1.setline(347);
                              throw Py.makeException(var1.getderef(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Circular reference detected")));
                           }

                           var1.setline(348);
                           var8 = var1.getlocal(0);
                           var1.getderef(0).__setitem__(var1.getlocal(2), var8);
                           var3 = null;
                        }

                        var1.setline(349);
                        var1.setline(349);
                        var16 = PyString.fromInterned("{");
                        var1.f_lasti = 2;
                        var3 = new Object[5];
                        var1.f_savedlocals = var3;
                        return var16;
                     case 1:
                        var3 = var1.f_savedlocals;
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        var1.setline(343);
                        var1.f_lasti = -1;
                        return Py.None;
                     case 2:
                        var3 = var1.f_savedlocals;
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        var1.setline(350);
                        var8 = var1.getderef(3);
                        var15 = var8._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(351);
                           var8 = var1.getlocal(1);
                           var8 = var8._iadd(Py.newInteger(1));
                           var1.setlocal(1, var8);
                           var1.setline(352);
                           var8 = PyString.fromInterned("\n")._add(PyString.fromInterned(" ")._mul(var1.getderef(3)._mul(var1.getlocal(1))));
                           var1.setlocal(3, var8);
                           var3 = null;
                           var1.setline(353);
                           var8 = var1.getderef(4)._add(var1.getlocal(3));
                           var1.setlocal(4, var8);
                           var3 = null;
                           var1.setline(354);
                           var1.setline(354);
                           var15 = var1.getlocal(3);
                           var1.f_lasti = 3;
                           var3 = new Object[5];
                           var1.f_savedlocals = var3;
                           return var15;
                        }

                        var1.setline(356);
                        var8 = var1.getglobal("None");
                        var1.setlocal(3, var8);
                        var3 = null;
                        var1.setline(357);
                        var8 = var1.getderef(4);
                        var1.setlocal(4, var8);
                        var3 = null;
                        break;
                     case 3:
                        var3 = var1.f_savedlocals;
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break;
                     case 4:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label264;
                     case 5:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        } else {
                           var15 = (PyObject)var10000;
                           var1.setline(387);
                           var1.setline(387);
                           var15 = var1.getderef(15);
                           var1.f_lasti = 6;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var15;
                        }
                     case 6:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        var1.setline(388);
                        if (var1.getderef(6).__call__(var2, var1.getlocal(8), var1.getderef(7)).__nonzero__()) {
                           var1.setline(389);
                           var1.setline(389);
                           var15 = var1.getderef(14).__call__(var2, var1.getlocal(8));
                           var1.f_lasti = 7;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var15;
                        }

                        var1.setline(390);
                        var5 = var1.getlocal(8);
                        var15 = var5._is(var1.getglobal("None"));
                        var5 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(391);
                           var1.setline(391);
                           var16 = PyString.fromInterned("null");
                           var1.f_lasti = 8;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var16;
                        }

                        var1.setline(392);
                        var5 = var1.getlocal(8);
                        var15 = var5._is(var1.getglobal("True"));
                        var5 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(393);
                           var1.setline(393);
                           var16 = PyString.fromInterned("true");
                           var1.f_lasti = 9;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var16;
                        }

                        var1.setline(394);
                        var5 = var1.getlocal(8);
                        var15 = var5._is(var1.getglobal("False"));
                        var5 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(395);
                           var1.setline(395);
                           var16 = PyString.fromInterned("false");
                           var1.f_lasti = 10;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var16;
                        }

                        var1.setline(396);
                        var15 = var1.getderef(6);
                        var10002 = var1.getlocal(8);
                        var12 = new PyObject[]{var1.getderef(10), var1.getderef(11)};
                        var10003 = new PyTuple(var12);
                        Arrays.fill(var12, (Object)null);
                        if (var15.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
                           var1.setline(397);
                           var1.setline(397);
                           var15 = var1.getderef(12).__call__(var2, var1.getlocal(8));
                           var1.f_lasti = 11;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var15;
                        }

                        var1.setline(398);
                        if (var1.getderef(6).__call__(var2, var1.getlocal(8), var1.getderef(8)).__nonzero__()) {
                           var1.setline(399);
                           var1.setline(399);
                           var15 = var1.getderef(9).__call__(var2, var1.getlocal(8));
                           var1.f_lasti = 12;
                           var9 = new Object[7];
                           var9[3] = var8;
                           var9[4] = var4;
                           var1.f_savedlocals = var9;
                           return var15;
                        }

                        var1.setline(401);
                        var15 = var1.getderef(6);
                        var10002 = var1.getlocal(8);
                        var12 = new PyObject[]{var1.getderef(16), var1.getderef(17)};
                        var10003 = new PyTuple(var12);
                        Arrays.fill(var12, (Object)null);
                        if (var15.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
                           var1.setline(402);
                           var5 = var1.getderef(18).__call__(var2, var1.getlocal(8), var1.getlocal(1));
                           var1.setlocal(9, var5);
                           var5 = null;
                        } else {
                           var1.setline(403);
                           if (var1.getderef(6).__call__(var2, var1.getlocal(8), var1.getderef(19)).__nonzero__()) {
                              var1.setline(404);
                              var5 = var1.getderef(20).__call__(var2, var1.getlocal(8), var1.getlocal(1));
                              var1.setlocal(9, var5);
                              var5 = null;
                           } else {
                              var1.setline(406);
                              var5 = var1.getderef(21).__call__(var2, var1.getlocal(8), var1.getlocal(1));
                              var1.setlocal(9, var5);
                              var5 = null;
                           }
                        }

                        var1.setline(407);
                        var5 = var1.getlocal(9).__iter__();
                        break label247;
                     case 7:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label221;
                     case 8:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label221;
                     case 9:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label221;
                     case 10:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label221;
                     case 11:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label221;
                     case 12:
                        var9 = var1.f_savedlocals;
                        var8 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label221;
                     case 13:
                        var7 = var1.f_savedlocals;
                        var8 = (PyObject)var7[3];
                        var4 = (PyObject)var7[4];
                        var5 = (PyObject)var7[5];
                        var6 = (PyObject)var7[6];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label247;
                     case 14:
                        var3 = var1.f_savedlocals;
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        break label263;
                     case 15:
                        var3 = var1.f_savedlocals;
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var15 = (PyObject)var10000;
                        var1.setline(413);
                        var8 = var1.getderef(0);
                        var15 = var8._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(414);
                           var1.getderef(0).__delitem__(var1.getlocal(2));
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                  }

                  var1.setline(358);
                  var8 = var1.getglobal("True");
                  var1.setlocal(5, var8);
                  var3 = null;
                  var1.setline(359);
                  if (var1.getderef(5).__nonzero__()) {
                     var1.setline(360);
                     var15 = var1.getglobal("sorted");
                     PyObject[] var13 = new PyObject[]{var1.getlocal(0).__getattr__("items").__call__(var2), null};
                     var1.setline(360);
                     PyObject[] var10 = Py.EmptyObjects;
                     var13[1] = new PyFunction(var1.f_globals, var10, f$15);
                     String[] var11 = new String[]{"key"};
                     var15 = var15.__call__(var2, var13, var11);
                     var3 = null;
                     var8 = var15;
                     var1.setlocal(6, var8);
                     var3 = null;
                  } else {
                     var1.setline(362);
                     var8 = var1.getlocal(0).__getattr__("iteritems").__call__(var2);
                     var1.setlocal(6, var8);
                     var3 = null;
                  }

                  var1.setline(363);
                  var8 = var1.getlocal(6).__iter__();
                  break label221;
               }

               var1.setline(407);
               var6 = var5.__iternext__();
               if (var6 != null) {
                  var1.setlocal(10, var6);
                  var1.setline(408);
                  var1.setline(408);
                  var15 = var1.getlocal(10);
                  var1.f_lasti = 13;
                  var7 = new Object[]{null, null, null, var8, var4, var5, var6};
                  var1.f_savedlocals = var7;
                  return var15;
               }
            }

            while(true) {
               var1.setline(363);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.setline(409);
                  var8 = var1.getlocal(3);
                  var15 = var8._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var15.__nonzero__()) {
                     var1.setline(410);
                     var8 = var1.getlocal(1);
                     var8 = var8._isub(Py.newInteger(1));
                     var1.setlocal(1, var8);
                     var1.setline(411);
                     var1.setline(411);
                     var15 = PyString.fromInterned("\n")._add(PyString.fromInterned(" ")._mul(var1.getderef(3)._mul(var1.getlocal(1))));
                     var1.f_lasti = 14;
                     var3 = new Object[8];
                     var1.f_savedlocals = var3;
                     return var15;
                  }
                  break label263;
               }

               var12 = Py.unpackSequence(var4, 2);
               var6 = var12[0];
               var1.setlocal(7, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(364);
               if (var1.getderef(6).__call__(var2, var1.getlocal(7), var1.getderef(7)).__nonzero__()) {
                  var1.setline(365);
               } else {
                  var1.setline(368);
                  if (var1.getderef(6).__call__(var2, var1.getlocal(7), var1.getderef(8)).__nonzero__()) {
                     var1.setline(369);
                     var5 = var1.getderef(9).__call__(var2, var1.getlocal(7));
                     var1.setlocal(7, var5);
                     var5 = null;
                  } else {
                     var1.setline(370);
                     var5 = var1.getlocal(7);
                     var15 = var5._is(var1.getglobal("True"));
                     var5 = null;
                     PyString var14;
                     if (var15.__nonzero__()) {
                        var1.setline(371);
                        var14 = PyString.fromInterned("true");
                        var1.setlocal(7, var14);
                        var5 = null;
                     } else {
                        var1.setline(372);
                        var5 = var1.getlocal(7);
                        var15 = var5._is(var1.getglobal("False"));
                        var5 = null;
                        if (var15.__nonzero__()) {
                           var1.setline(373);
                           var14 = PyString.fromInterned("false");
                           var1.setlocal(7, var14);
                           var5 = null;
                        } else {
                           var1.setline(374);
                           var5 = var1.getlocal(7);
                           var15 = var5._is(var1.getglobal("None"));
                           var5 = null;
                           if (var15.__nonzero__()) {
                              var1.setline(375);
                              var14 = PyString.fromInterned("null");
                              var1.setlocal(7, var14);
                              var5 = null;
                           } else {
                              var1.setline(376);
                              var15 = var1.getderef(6);
                              var10002 = var1.getlocal(7);
                              var12 = new PyObject[]{var1.getderef(10), var1.getderef(11)};
                              var10003 = new PyTuple(var12);
                              Arrays.fill(var12, (Object)null);
                              if (!var15.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
                                 var1.setline(378);
                                 if (!var1.getderef(13).__nonzero__()) {
                                    var1.setline(381);
                                    throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("key ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(7)))._add(PyString.fromInterned(" is not a string"))));
                                 }
                                 continue;
                              }

                              var1.setline(377);
                              var5 = var1.getderef(12).__call__(var2, var1.getlocal(7));
                              var1.setlocal(7, var5);
                              var5 = null;
                           }
                        }
                     }
                  }
               }

               var1.setline(382);
               if (!var1.getlocal(5).__nonzero__()) {
                  var1.setline(385);
                  var1.setline(385);
                  var15 = var1.getlocal(4);
                  var1.f_lasti = 4;
                  var9 = new Object[7];
                  var9[3] = var8;
                  var9[4] = var4;
                  var1.f_savedlocals = var9;
                  return var15;
               }

               var1.setline(383);
               var5 = var1.getglobal("False");
               var1.setlocal(5, var5);
               var5 = null;
               break;
            }
         }

         var1.setline(386);
         var1.setline(386);
         var15 = var1.getderef(14).__call__(var2, var1.getlocal(7));
         var1.f_lasti = 5;
         var9 = new Object[7];
         var9[3] = var8;
         var9[4] = var4;
         var1.f_savedlocals = var9;
         return var15;
      }

      var1.setline(412);
      var1.setline(412);
      var16 = PyString.fromInterned("}");
      var1.f_lasti = 15;
      var3 = new Object[8];
      var1.f_savedlocals = var3;
      return var16;
   }

   public PyObject f$15(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _iterencode$16(PyFrame var1, ThreadState var2) {
      label140: {
         PyObject var3;
         PyObject var4;
         Object[] var5;
         PyObject var8;
         label122: {
            label132: {
               Object var10000;
               Object[] var6;
               switch (var1.f_lasti) {
                  case 0:
                  default:
                     var1.setline(417);
                     if (var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getderef(1)).__nonzero__()) {
                        var1.setline(418);
                        var1.setline(418);
                        var8 = var1.getderef(2).__call__(var2, var1.getlocal(0));
                        var1.f_lasti = 1;
                        var6 = new Object[3];
                        var1.f_savedlocals = var6;
                        return var8;
                     }

                     var1.setline(419);
                     var3 = var1.getlocal(0);
                     var8 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     PyString var9;
                     if (var8.__nonzero__()) {
                        var1.setline(420);
                        var1.setline(420);
                        var9 = PyString.fromInterned("null");
                        var1.f_lasti = 2;
                        var6 = new Object[5];
                        var1.f_savedlocals = var6;
                        return var9;
                     }

                     var1.setline(421);
                     var3 = var1.getlocal(0);
                     var8 = var3._is(var1.getglobal("True"));
                     var3 = null;
                     if (var8.__nonzero__()) {
                        var1.setline(422);
                        var1.setline(422);
                        var9 = PyString.fromInterned("true");
                        var1.f_lasti = 3;
                        var6 = new Object[5];
                        var1.f_savedlocals = var6;
                        return var9;
                     }

                     var1.setline(423);
                     var3 = var1.getlocal(0);
                     var8 = var3._is(var1.getglobal("False"));
                     var3 = null;
                     if (var8.__nonzero__()) {
                        var1.setline(424);
                        var1.setline(424);
                        var9 = PyString.fromInterned("false");
                        var1.f_lasti = 4;
                        var6 = new Object[5];
                        var1.f_savedlocals = var6;
                        return var9;
                     }

                     var1.setline(425);
                     var8 = var1.getderef(0);
                     PyObject var10002 = var1.getlocal(0);
                     PyObject[] var7 = new PyObject[]{var1.getderef(3), var1.getderef(4)};
                     PyTuple var10003 = new PyTuple(var7);
                     Arrays.fill(var7, (Object)null);
                     if (var8.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
                        var1.setline(426);
                        var1.setline(426);
                        var8 = var1.getderef(5).__call__(var2, var1.getlocal(0));
                        var1.f_lasti = 5;
                        var6 = new Object[5];
                        var1.f_savedlocals = var6;
                        return var8;
                     }

                     var1.setline(427);
                     if (var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getderef(6)).__nonzero__()) {
                        var1.setline(428);
                        var1.setline(428);
                        var8 = var1.getderef(7).__call__(var2, var1.getlocal(0));
                        var1.f_lasti = 6;
                        var6 = new Object[5];
                        var1.f_savedlocals = var6;
                        return var8;
                     }

                     var1.setline(429);
                     var8 = var1.getderef(0);
                     var10002 = var1.getlocal(0);
                     var7 = new PyObject[]{var1.getderef(8), var1.getderef(9)};
                     var10003 = new PyTuple(var7);
                     Arrays.fill(var7, (Object)null);
                     if (var8.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
                        var1.setline(430);
                        var3 = var1.getderef(10).__call__(var2, var1.getlocal(0), var1.getlocal(1)).__iter__();
                        break label132;
                     }

                     var1.setline(432);
                     if (var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getderef(11)).__nonzero__()) {
                        var1.setline(433);
                        var3 = var1.getderef(12).__call__(var2, var1.getlocal(0), var1.getlocal(1)).__iter__();
                        break label122;
                     }

                     var1.setline(436);
                     var3 = var1.getderef(13);
                     var8 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (var8.__nonzero__()) {
                        var1.setline(437);
                        var3 = var1.getderef(14).__call__(var2, var1.getlocal(0));
                        var1.setlocal(3, var3);
                        var3 = null;
                        var1.setline(438);
                        var3 = var1.getlocal(3);
                        var8 = var3._in(var1.getderef(13));
                        var3 = null;
                        if (var8.__nonzero__()) {
                           var1.setline(439);
                           throw Py.makeException(var1.getderef(15).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Circular reference detected")));
                        }

                        var1.setline(440);
                        var3 = var1.getlocal(0);
                        var1.getderef(13).__setitem__(var1.getlocal(3), var3);
                        var3 = null;
                     }

                     var1.setline(441);
                     var3 = var1.getderef(16).__call__(var2, var1.getlocal(0));
                     var1.setlocal(0, var3);
                     var3 = null;
                     var1.setline(442);
                     var3 = var1.getderef(17).__call__(var2, var1.getlocal(0), var1.getlocal(1)).__iter__();
                     break;
                  case 1:
                     var6 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label140;
                  case 2:
                     var6 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label140;
                  case 3:
                     var6 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label140;
                  case 4:
                     var6 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label140;
                  case 5:
                     var6 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label140;
                  case 6:
                     var6 = var1.f_savedlocals;
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label140;
                  case 7:
                     var5 = var1.f_savedlocals;
                     var3 = (PyObject)var5[3];
                     var4 = (PyObject)var5[4];
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label132;
                  case 8:
                     var5 = var1.f_savedlocals;
                     var3 = (PyObject)var5[3];
                     var4 = (PyObject)var5[4];
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
                     break label122;
                  case 9:
                     var5 = var1.f_savedlocals;
                     var3 = (PyObject)var5[3];
                     var4 = (PyObject)var5[4];
                     var10000 = var1.getGeneratorInput();
                     if (var10000 instanceof PyException) {
                        throw (Throwable)var10000;
                     }

                     var8 = (PyObject)var10000;
               }

               var1.setline(442);
               var4 = var3.__iternext__();
               if (var4 != null) {
                  var1.setlocal(2, var4);
                  var1.setline(443);
                  var1.setline(443);
                  var8 = var1.getlocal(2);
                  var1.f_lasti = 9;
                  var5 = new Object[]{null, null, null, var3, var4, null};
                  var1.f_savedlocals = var5;
                  return var8;
               }

               var1.setline(444);
               var3 = var1.getderef(13);
               var8 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(445);
                  var1.getderef(13).__delitem__(var1.getlocal(3));
               }
               break label140;
            }

            var1.setline(430);
            var4 = var3.__iternext__();
            if (var4 != null) {
               var1.setlocal(2, var4);
               var1.setline(431);
               var1.setline(431);
               var8 = var1.getlocal(2);
               var1.f_lasti = 7;
               var5 = new Object[]{null, null, null, var3, var4};
               var1.f_savedlocals = var5;
               return var8;
            }
            break label140;
         }

         var1.setline(433);
         var4 = var3.__iternext__();
         if (var4 != null) {
            var1.setlocal(2, var4);
            var1.setline(434);
            var1.setline(434);
            var8 = var1.getlocal(2);
            var1.f_lasti = 8;
            var5 = new Object[]{null, null, null, var3, var4, null};
            var1.f_savedlocals = var5;
            return var8;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public encoder$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "replace"};
      encode_basestring$1 = Py.newCode(1, var2, var1, "encode_basestring", 33, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match"};
      replace$2 = Py.newCode(1, var2, var1, "replace", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "replace"};
      py_encode_basestring_ascii$3 = Py.newCode(1, var2, var1, "py_encode_basestring_ascii", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match", "s", "n", "s1", "s2"};
      replace$4 = Py.newCode(1, var2, var1, "replace", 48, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JSONEncoder$5 = Py.newCode(0, var2, var1, "JSONEncoder", 70, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "skipkeys", "ensure_ascii", "check_circular", "allow_nan", "sort_keys", "indent", "separators", "encoding", "default"};
      __init__$6 = Py.newCode(10, var2, var1, "__init__", 101, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "o"};
      default$7 = Py.newCode(2, var2, var1, "default", 165, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "o", "_encoding", "chunks"};
      encode$8 = Py.newCode(2, var2, var1, "encode", 185, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "o", "_one_shot", "markers", "_encoder", "floatstr", "_iterencode"};
      iterencode$9 = Py.newCode(3, var2, var1, "iterencode", 211, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"o", "_orig_encoder", "_encoding"};
      _encoder$10 = Py.newCode(3, var2, var1, "_encoder", 230, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"o", "allow_nan", "_repr", "_inf", "_neginf", "text"};
      floatstr$11 = Py.newCode(5, var2, var1, "floatstr", 235, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"markers", "_default", "_encoder", "_indent", "_floatstr", "_key_separator", "_item_separator", "_sort_keys", "_skipkeys", "_one_shot", "ValueError", "basestring", "dict", "float", "id", "int", "isinstance", "list", "long", "str", "tuple", "_iterencode_dict", "_iterencode", "_iterencode_list"};
      String[] var10001 = var2;
      encoder$py var10007 = self;
      var2 = new String[]{"_iterencode_dict", "ValueError", "int", "_floatstr", "_item_separator", "basestring", "isinstance", "dict", "_default", "_encoder", "_iterencode", "tuple", "id", "_sort_keys", "str", "_skipkeys", "_iterencode_list", "_key_separator", "long", "markers", "_indent", "list", "float"};
      _make_iterencode$12 = Py.newCode(21, var10001, var1, "_make_iterencode", 271, false, false, var10007, 12, var2, (String[])null, 3, 4097);
      var2 = new String[]{"lst", "_current_indent_level", "markerid", "buf", "newline_indent", "separator", "first", "value", "chunks", "chunk"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"markers", "id", "ValueError", "_indent", "_item_separator", "isinstance", "basestring", "_encoder", "int", "long", "str", "float", "_floatstr", "list", "tuple", "_iterencode_list", "dict", "_iterencode_dict", "_iterencode"};
      _iterencode_list$13 = Py.newCode(2, var10001, var1, "_iterencode_list", 287, false, false, var10007, 13, (String[])null, var2, 0, 4129);
      var2 = new String[]{"dct", "_current_indent_level", "markerid", "newline_indent", "item_separator", "first", "items", "key", "value", "chunks", "chunk"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"markers", "id", "ValueError", "_indent", "_item_separator", "_sort_keys", "isinstance", "basestring", "float", "_floatstr", "int", "long", "str", "_skipkeys", "_encoder", "_key_separator", "list", "tuple", "_iterencode_list", "dict", "_iterencode_dict", "_iterencode"};
      _iterencode_dict$14 = Py.newCode(2, var10001, var1, "_iterencode_dict", 340, false, false, var10007, 14, (String[])null, var2, 0, 4129);
      var2 = new String[]{"kv"};
      f$15 = Py.newCode(1, var2, var1, "<lambda>", 360, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"o", "_current_indent_level", "chunk", "markerid"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"isinstance", "basestring", "_encoder", "int", "long", "str", "float", "_floatstr", "list", "tuple", "_iterencode_list", "dict", "_iterencode_dict", "markers", "id", "ValueError", "_default", "_iterencode"};
      _iterencode$16 = Py.newCode(2, var10001, var1, "_iterencode", 416, false, false, var10007, 16, (String[])null, var2, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new encoder$py("json/encoder$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(encoder$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.encode_basestring$1(var2, var3);
         case 2:
            return this.replace$2(var2, var3);
         case 3:
            return this.py_encode_basestring_ascii$3(var2, var3);
         case 4:
            return this.replace$4(var2, var3);
         case 5:
            return this.JSONEncoder$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.default$7(var2, var3);
         case 8:
            return this.encode$8(var2, var3);
         case 9:
            return this.iterencode$9(var2, var3);
         case 10:
            return this._encoder$10(var2, var3);
         case 11:
            return this.floatstr$11(var2, var3);
         case 12:
            return this._make_iterencode$12(var2, var3);
         case 13:
            return this._iterencode_list$13(var2, var3);
         case 14:
            return this._iterencode_dict$14(var2, var3);
         case 15:
            return this.f$15(var2, var3);
         case 16:
            return this._iterencode$16(var2, var3);
         default:
            return null;
      }
   }
}
