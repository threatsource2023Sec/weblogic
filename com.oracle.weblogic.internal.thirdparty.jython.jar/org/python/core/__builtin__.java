package org.python.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import org.python.core.util.RelativeFile;

public class __builtin__ {
   private static final PyStringMap internedStrings = new PyStringMap();
   public static PyString __doc__zip = new PyString("zip(seq1 [, seq2 [...]]) -> [(seq1[0], seq2[0] ...), (...)]\n\nReturn a list of tuples, where each tuple contains the i-th element\nfrom each of the argument sequences.  The returned list is\ntruncated in length to the length of the shortest argument sequence.");

   public static void fillWithBuiltins(PyObject dict) {
      dict.__setitem__((String)"object", PyObject.TYPE);
      dict.__setitem__((String)"type", PyType.TYPE);
      dict.__setitem__((String)"bool", PyBoolean.TYPE);
      dict.__setitem__((String)"int", PyInteger.TYPE);
      dict.__setitem__((String)"enumerate", PyEnumerate.TYPE);
      dict.__setitem__((String)"float", PyFloat.TYPE);
      dict.__setitem__((String)"long", PyLong.TYPE);
      dict.__setitem__((String)"complex", PyComplex.TYPE);
      dict.__setitem__((String)"dict", PyDictionary.TYPE);
      dict.__setitem__((String)"list", PyList.TYPE);
      dict.__setitem__((String)"tuple", PyTuple.TYPE);
      dict.__setitem__((String)"set", PySet.TYPE);
      dict.__setitem__((String)"frozenset", PyFrozenSet.TYPE);
      dict.__setitem__((String)"property", PyProperty.TYPE);
      dict.__setitem__((String)"staticmethod", PyStaticMethod.TYPE);
      dict.__setitem__((String)"classmethod", PyClassMethod.TYPE);
      dict.__setitem__((String)"super", PySuper.TYPE);
      dict.__setitem__((String)"str", PyString.TYPE);
      dict.__setitem__((String)"unicode", PyUnicode.TYPE);
      dict.__setitem__((String)"basestring", PyBaseString.TYPE);
      dict.__setitem__((String)"file", PyFile.TYPE);
      dict.__setitem__((String)"slice", PySlice.TYPE);
      dict.__setitem__((String)"xrange", PyXRange.TYPE);
      dict.__setitem__("None", Py.None);
      dict.__setitem__("NotImplemented", Py.NotImplemented);
      dict.__setitem__("Ellipsis", Py.Ellipsis);
      dict.__setitem__((String)"True", Py.True);
      dict.__setitem__((String)"False", Py.False);
      dict.__setitem__((String)"bytes", PyString.TYPE);
      dict.__setitem__((String)"bytearray", PyByteArray.TYPE);
      dict.__setitem__((String)"buffer", Py2kBuffer.TYPE);
      dict.__setitem__((String)"memoryview", PyMemoryView.TYPE);
      dict.__setitem__((String)"__debug__", Py.One);
      dict.__setitem__((String)"abs", new BuiltinFunctions("abs", 7, 1));
      dict.__setitem__((String)"apply", new BuiltinFunctions("apply", 9, 1, 3));
      dict.__setitem__((String)"callable", new BuiltinFunctions("callable", 14, 1));
      dict.__setitem__((String)"coerce", new BuiltinFunctions("coerce", 13, 2));
      dict.__setitem__((String)"chr", new BuiltinFunctions("chr", 0, 1));
      dict.__setitem__((String)"cmp", new BuiltinFunctions("cmp", 6, 2));
      dict.__setitem__((String)"globals", new BuiltinFunctions("globals", 4, 0));
      dict.__setitem__((String)"hash", new BuiltinFunctions("hash", 5, 1));
      dict.__setitem__((String)"id", new BuiltinFunctions("id", 11, 1));
      dict.__setitem__((String)"isinstance", new BuiltinFunctions("isinstance", 10, 2));
      dict.__setitem__((String)"len", new BuiltinFunctions("len", 1, 1));
      dict.__setitem__((String)"ord", new BuiltinFunctions("ord", 3, 1));
      dict.__setitem__((String)"range", new BuiltinFunctions("range", 2, 1, 3));
      dict.__setitem__((String)"sum", new BuiltinFunctions("sum", 12, 1, 2));
      dict.__setitem__((String)"unichr", new BuiltinFunctions("unichr", 6, 1));
      dict.__setitem__((String)"delattr", new BuiltinFunctions("delattr", 15, 2));
      dict.__setitem__((String)"dir", new BuiltinFunctions("dir", 16, 0, 1));
      dict.__setitem__((String)"divmod", new BuiltinFunctions("divmod", 17, 2));
      dict.__setitem__((String)"eval", new BuiltinFunctions("eval", 18, 1, 3));
      dict.__setitem__((String)"execfile", new BuiltinFunctions("execfile", 19, 1, 3));
      dict.__setitem__((String)"filter", new BuiltinFunctions("filter", 20, 2));
      dict.__setitem__((String)"getattr", new BuiltinFunctions("getattr", 21, 2, 3));
      dict.__setitem__((String)"hasattr", new BuiltinFunctions("hasattr", 22, 2));
      dict.__setitem__((String)"hex", new BuiltinFunctions("hex", 23, 1));
      dict.__setitem__((String)"input", new BuiltinFunctions("input", 24, 0, 1));
      dict.__setitem__((String)"intern", new BuiltinFunctions("intern", 25, 1));
      dict.__setitem__((String)"issubclass", new BuiltinFunctions("issubclass", 26, 2));
      dict.__setitem__((String)"iter", new BuiltinFunctions("iter", 27, 1, 2));
      dict.__setitem__((String)"locals", new BuiltinFunctions("locals", 28, 0));
      dict.__setitem__((String)"map", new BuiltinFunctions("map", 29, 2, -1));
      dict.__setitem__((String)"max", new MaxFunction());
      dict.__setitem__((String)"min", new MinFunction());
      dict.__setitem__((String)"oct", new BuiltinFunctions("oct", 32, 1));
      dict.__setitem__((String)"pow", new BuiltinFunctions("pow", 33, 2, 3));
      dict.__setitem__((String)"raw_input", new BuiltinFunctions("raw_input", 34, 0, 1));
      dict.__setitem__((String)"reduce", new BuiltinFunctions("reduce", 35, 2, 3));
      dict.__setitem__((String)"reload", new BuiltinFunctions("reload", 36, 1));
      dict.__setitem__((String)"repr", new BuiltinFunctions("repr", 37, 1));
      dict.__setitem__((String)"round", new RoundFunction());
      dict.__setitem__((String)"setattr", new BuiltinFunctions("setattr", 39, 3));
      dict.__setitem__((String)"vars", new BuiltinFunctions("vars", 41, 0, 1));
      dict.__setitem__((String)"zip", new BuiltinFunctions("zip", 43, 0, -1));
      dict.__setitem__((String)"compile", new CompileFunction());
      dict.__setitem__((String)"open", new OpenFunction());
      dict.__setitem__((String)"reversed", new BuiltinFunctions("reversed", 45, 1));
      dict.__setitem__((String)"__import__", new ImportFunction());
      dict.__setitem__((String)"sorted", new SortedFunction());
      dict.__setitem__((String)"all", new AllFunction());
      dict.__setitem__((String)"any", new AnyFunction());
      dict.__setitem__((String)"format", new FormatFunction());
      dict.__setitem__((String)"print", new PrintFunction());
      dict.__setitem__((String)"next", new NextFunction());
      dict.__setitem__((String)"bin", new BinFunction());
   }

   public static PyObject abs(PyObject o) {
      return o.__abs__();
   }

   public static PyObject apply(PyObject o) {
      return o.__call__();
   }

   public static PyObject apply(PyObject o, PyObject args) {
      return o.__call__(Py.make_array(args));
   }

   public static PyObject apply(PyObject o, PyObject args, PyDictionary kws) {
      Map table = kws.getMap();
      if (table.size() > 0) {
         Iterator ik = table.keySet().iterator();
         Iterator iv = table.values().iterator();
         int n = table.size();
         String[] kw = new String[n];
         PyObject[] aargs = Py.make_array(args);
         PyObject[] a = new PyObject[n + aargs.length];
         System.arraycopy(aargs, 0, a, 0, aargs.length);
         int offset = aargs.length;

         for(int i = 0; i < n; ++i) {
            PyObject name = (PyObject)ik.next();
            if (name.getClass() != PyString.class) {
               throw Py.TypeError(String.format("keywords must be strings"));
            }

            kw[i] = ((PyString)name).internedString();
            a[i + offset] = (PyObject)iv.next();
         }

         return o.__call__(a, kw);
      } else {
         return apply(o, args);
      }
   }

   public static boolean callable(PyObject obj) {
      return obj.isCallable();
   }

   public static int unichr(PyObject obj) {
      long l = obj.asLong();
      if (l < -2147483648L) {
         throw Py.OverflowError("signed integer is less than minimum");
      } else if (l > 2147483647L) {
         throw Py.OverflowError("signed integer is greater than maximum");
      } else {
         return unichr((int)l);
      }
   }

   public static int unichr(int i) {
      if (i >= 0 && i <= 1114111) {
         if (i >= 55296 && i <= 57343) {
            throw Py.ValueError("unichr() arg is a lone surrogate in range (0xD800, 0xDFFF) (Jython UTF-16 encoding)");
         } else {
            return i;
         }
      } else {
         throw Py.ValueError("unichr() arg not in range(0x110000)");
      }
   }

   public static char chr(int i) {
      if (i >= 0 && i <= 255) {
         return (char)i;
      } else {
         throw Py.ValueError("chr() arg not in range(256)");
      }
   }

   public static int cmp(PyObject x, PyObject y) {
      return x._cmp(y);
   }

   public static PyTuple coerce(PyObject o1, PyObject o2) {
      PyObject[] result = o1._coerce(o2);
      if (result != null) {
         return new PyTuple(result);
      } else {
         throw Py.TypeError("number coercion failed");
      }
   }

   public static void delattr(PyObject obj, PyObject name) {
      obj.__delattr__(asName(name, "delattr"));
   }

   public static PyObject dir(PyObject o) {
      PyObject ret = o.__dir__();
      if (!Py.isInstance(ret, PyList.TYPE)) {
         throw Py.TypeError("__dir__() must return a list, not " + ret.getType().fastGetName());
      } else {
         ((PyList)ret).sort();
         return ret;
      }
   }

   public static PyObject dir() {
      PyObject l = locals();
      PyObject retObj = l.invoke("keys");

      PyList ret;
      try {
         ret = (PyList)retObj;
      } catch (ClassCastException var4) {
         throw Py.TypeError(String.format("Expected keys() to be a list, not '%s'", retObj.getType().fastGetName()));
      }

      ret.sort();
      return ret;
   }

   public static PyObject divmod(PyObject x, PyObject y) {
      return x._divmod(y);
   }

   private static boolean isMappingType(PyObject o) {
      return o == null || o == Py.None || o.isMappingType();
   }

   private static void verify_mappings(PyObject globals, PyObject locals) {
      if (!isMappingType(globals)) {
         throw Py.TypeError("globals must be a mapping");
      } else if (!isMappingType(locals)) {
         throw Py.TypeError("locals must be a mapping");
      }
   }

   public static PyObject eval(PyObject o, PyObject globals, PyObject locals) {
      verify_mappings(globals, locals);
      PyCode code;
      if (o instanceof PyCode) {
         code = (PyCode)o;
      } else {
         if (!(o instanceof PyString)) {
            throw Py.TypeError("eval: argument 1 must be string or code object");
         }

         code = (PyCode)CompileFunction.compile(o, "<string>", "eval");
      }

      return Py.runCode(code, locals, globals);
   }

   public static PyObject eval(PyObject o, PyObject globals) {
      return eval(o, globals, globals);
   }

   public static PyObject eval(PyObject o) {
      if (o instanceof PyBaseCode && ((PyBaseCode)o).hasFreevars()) {
         throw Py.TypeError("code object passed to eval() may not contain free variables");
      } else {
         return eval(o, (PyObject)null, (PyObject)null);
      }
   }

   public static void execfile(String name, PyObject globals, PyObject locals) {
      execfile_flags(name, globals, locals, Py.getCompilerFlags());
   }

   public static void execfile_flags(String name, PyObject globals, PyObject locals, CompilerFlags cflags) {
      verify_mappings(globals, locals);

      FileInputStream file;
      try {
         file = new FileInputStream(new RelativeFile(name));
      } catch (FileNotFoundException var15) {
         throw Py.IOError((IOException)var15);
      }

      PyCode code;
      try {
         code = Py.compile_flags((InputStream)file, name, CompileMode.exec, cflags);
      } finally {
         try {
            file.close();
         } catch (IOException var13) {
            throw Py.IOError(var13);
         }
      }

      Py.runCode(code, locals, globals);
   }

   public static void execfile(String name, PyObject globals) {
      execfile(name, globals, globals);
   }

   public static void execfile(String name) {
      execfile(name, (PyObject)null, (PyObject)null);
   }

   public static PyObject filter(PyObject func, PyObject seq) {
      if (seq instanceof PyString) {
         return filterBaseString(func, (PyString)seq, seq instanceof PyUnicode ? PyUnicode.TYPE : PyString.TYPE);
      } else if (seq instanceof PyTuple) {
         return filterTuple(func, (PyTuple)seq);
      } else {
         PyList list = new PyList();
         Iterator var3 = seq.asIterable().iterator();

         while(true) {
            PyObject item;
            while(true) {
               if (!var3.hasNext()) {
                  return list;
               }

               item = (PyObject)var3.next();
               if (func != PyBoolean.TYPE && func != Py.None) {
                  if (!func.__call__(item).__nonzero__()) {
                     continue;
                  }
                  break;
               } else if (item.__nonzero__()) {
                  break;
               }
            }

            list.append(item);
         }
      }
   }

   public static PyObject filterBaseString(PyObject func, PyBaseString seq, PyType stringType) {
      if (func == Py.None && seq.getType() == stringType) {
         return seq;
      } else {
         StringBuilder builder = new StringBuilder();
         Iterator var4 = seq.asIterable().iterator();

         while(true) {
            PyObject item;
            while(true) {
               if (!var4.hasNext()) {
                  String result = builder.toString();
                  return (PyObject)(stringType == PyString.TYPE ? new PyString(result) : new PyUnicode(result));
               }

               item = (PyObject)var4.next();
               if (func == Py.None) {
                  if (!item.__nonzero__()) {
                     continue;
                  }
               } else if (!func.__call__(item).__nonzero__()) {
                  continue;
               }
               break;
            }

            if (!Py.isInstance(item, stringType)) {
               String name = stringType.fastGetName();
               throw Py.TypeError(String.format("can't filter %s to %s: __getitem__ returned different type", name, name));
            }

            builder.append(item.toString());
         }
      }
   }

   public static PyObject filterTuple(PyObject func, PyTuple seq) {
      int len = seq.size();
      if (len == 0) {
         return seq.getType() != PyTuple.TYPE ? Py.EmptyTuple : seq;
      } else {
         PyList list = new PyList();

         for(int i = 0; i < len; ++i) {
            PyObject item = seq.__finditem__(i);
            if (func == Py.None) {
               if (!item.__nonzero__()) {
                  continue;
               }
            } else if (!func.__call__(item).__nonzero__()) {
               continue;
            }

            list.append(item);
         }

         return PyTuple.fromIterable(list);
      }
   }

   public static PyObject getattr(PyObject obj, PyObject name) {
      return getattr(obj, name, (PyObject)null);
   }

   public static PyObject getattr(PyObject obj, PyObject nameObj, PyObject def) {
      String name = asName(nameObj, "getattr");
      PyObject result = null;
      PyException attributeError = null;

      try {
         result = obj.__findattr_ex__(name);
      } catch (PyException var7) {
         if (!var7.match(Py.AttributeError)) {
            throw var7;
         }

         attributeError = var7;
      }

      if (result != null) {
         return result;
      } else if (def != null) {
         return def;
      } else {
         if (attributeError == null) {
            obj.noAttributeError(name);
         }

         throw attributeError;
      }
   }

   public static PyObject globals() {
      return Py.getFrame().f_globals;
   }

   public static boolean hasattr(PyObject obj, PyObject nameObj) {
      String name = asName(nameObj, "hasattr");

      try {
         return obj.__findattr__(name) != null;
      } catch (PyException var4) {
         if (!var4.match(Py.KeyboardInterrupt) && !var4.match(Py.SystemExit)) {
            return false;
         } else {
            throw var4;
         }
      }
   }

   public static PyInteger hash(PyObject o) {
      return o.__hash__();
   }

   public static PyString hex(PyObject o) {
      return o.__hex__();
   }

   public static long id(PyObject o) {
      return Py.id(o);
   }

   public static PyObject input(PyObject prompt) {
      String line = raw_input(prompt);
      return eval(new PyString(line));
   }

   public static PyObject input() {
      return input(new PyString(""));
   }

   public static PyString intern(PyObject obj) {
      if (obj instanceof PyString && !(obj instanceof PyUnicode)) {
         if (obj.getType() != PyString.TYPE) {
            throw Py.TypeError("can't intern subclass of string");
         } else {
            PyString s = (PyString)obj;
            String istring = s.internedString();
            PyObject ret = internedStrings.__finditem__(istring);
            if (ret != null) {
               return (PyString)ret;
            } else {
               internedStrings.__setitem__((String)istring, s);
               return s;
            }
         }
      } else {
         throw Py.TypeError("intern() argument 1 must be string, not " + obj.getType().fastGetName());
      }
   }

   public static boolean isinstance(PyObject obj, PyObject cls) {
      return Py.isInstance(obj, cls);
   }

   public static boolean issubclass(PyObject derived, PyObject cls) {
      return Py.isSubClass(derived, cls);
   }

   public static PyObject iter(PyObject obj) {
      return obj.__iter__();
   }

   public static PyObject iter(PyObject callable, PyObject sentinel) {
      return new PyCallIter(callable, sentinel);
   }

   public static int len(PyObject obj) {
      return obj.__len__();
   }

   public static PyObject locals() {
      return Py.getFrame().getLocals();
   }

   public static PyObject map(PyObject[] argstar) {
      int n = argstar.length - 1;
      if (n < 1) {
         throw Py.TypeError("map requires at least two arguments");
      } else {
         PyObject f = argstar[0];
         PyList list = new PyList();
         PyObject[] args = new PyObject[n];
         PyObject[] iters = new PyObject[n];

         for(int j = 0; j < n; ++j) {
            iters[j] = Py.iter(argstar[j + 1], "argument " + (j + 1) + " to map() must support iteration");
         }

         while(true) {
            boolean any_items = false;

            for(int j = 0; j < n; ++j) {
               PyObject element;
               if ((element = iters[j].__iternext__()) != null) {
                  args[j] = element;
                  any_items = true;
               } else {
                  args[j] = Py.None;
               }
            }

            if (!any_items) {
               return list;
            }

            if (f == Py.None) {
               if (n == 1) {
                  list.append(args[0]);
               } else {
                  list.append(new PyTuple((PyObject[])args.clone()));
               }
            } else {
               list.append(f.__call__(args));
            }
         }
      }
   }

   public static PyString oct(PyObject o) {
      return o.__oct__();
   }

   public static final int ord(PyObject c) throws PyException {
      String cs;
      int length;
      if (c instanceof PyUnicode) {
         cs = ((PyUnicode)c).getString();
         length = cs.codePointCount(0, cs.length());
         if (length == 1) {
            return cs.codePointAt(0);
         }
      } else if (c instanceof PyString) {
         cs = ((PyString)c).getString();
         length = cs.length();
         if (length == 1) {
            return cs.charAt(0);
         }
      } else {
         if (!(c instanceof BaseBytes)) {
            throw Py.TypeError("ord() expected string of length 1, but " + c.getType().fastGetName() + " found");
         }

         BaseBytes cb = (BaseBytes)c;
         length = cb.__len__();
         if (length == 1) {
            return cb.intAt(0);
         }
      }

      throw Py.TypeError("ord() expected a character, but string of length " + length + " found");
   }

   public static PyObject pow(PyObject x, PyObject y) {
      return x._pow(y);
   }

   private static boolean coerce(PyObject[] objs) {
      PyObject x = objs[0];
      PyObject y = objs[1];
      PyObject[] result = x._coerce(y);
      if (result != null) {
         objs[0] = result[0];
         objs[1] = result[1];
         return true;
      } else {
         result = y._coerce(x);
         if (result != null) {
            objs[0] = result[1];
            objs[1] = result[0];
            return true;
         } else {
            return false;
         }
      }
   }

   public static PyObject pow(PyObject x, PyObject y, PyObject z) {
      if (z == Py.None) {
         return pow(x, y);
      } else {
         PyObject[] tmp = new PyObject[]{x, y};
         if (coerce(tmp)) {
            x = tmp[0];
            y = tmp[1];
            tmp[1] = z;
            if (coerce(tmp)) {
               x = tmp[0];
               z = tmp[1];
               tmp[0] = y;
               if (coerce(tmp)) {
                  z = tmp[1];
                  y = tmp[0];
               }
            }
         } else {
            tmp[1] = z;
            if (coerce(tmp)) {
               x = tmp[0];
               z = tmp[1];
               tmp[0] = y;
               if (coerce(tmp)) {
                  y = tmp[0];
                  z = tmp[1];
                  tmp[1] = x;
                  if (coerce(tmp)) {
                     x = tmp[1];
                     y = tmp[0];
                  }
               }
            }
         }

         PyObject result = x.__pow__(y, z);
         if (result != null) {
            return result;
         } else {
            throw Py.TypeError(String.format("unsupported operand type(s) for pow(): '%.100s', '%.100s', '%.100s'", x.getType().fastGetName(), y.getType().fastGetName(), z.getType().fastGetName()));
         }
      }
   }

   public static PyObject range(PyObject n) {
      return range(Py.Zero, n, Py.One);
   }

   public static PyObject range(PyObject start, PyObject stop) {
      return range(start, stop, Py.One);
   }

   public static PyObject range(PyObject ilow, PyObject ihigh, PyObject istep) {
      ilow = getRangeLongArgument(ilow, "start");
      ihigh = getRangeLongArgument(ihigh, "end");
      istep = getRangeLongArgument(istep, "step");
      int cmpResult = istep._cmp(Py.Zero);
      if (cmpResult == 0) {
         throw Py.ValueError("range() step argument must not be zero");
      } else {
         int n;
         if (cmpResult > 0) {
            n = getLenOfRangeLongs(ilow, ihigh, istep);
         } else {
            n = getLenOfRangeLongs(ihigh, ilow, istep.__neg__());
         }

         if (n < 0) {
            throw Py.OverflowError("range() result has too many items");
         } else {
            PyObject[] range = new PyObject[n];

            for(int i = 0; i < n; ++i) {
               range[i] = ilow;
               ilow = ilow._add(istep);
            }

            return new PyList(range);
         }
      }
   }

   private static int getLenOfRangeLongs(PyObject lo, PyObject hi, PyObject step) {
      if (lo._cmp(hi) >= 0) {
         return 0;
      } else {
         try {
            PyObject diff = hi._sub(lo)._sub(Py.One);
            PyObject n = diff.__floordiv__(step).__add__(Py.One);
            return n.asInt();
         } catch (PyException var5) {
            return -1;
         }
      }
   }

   private static PyObject getRangeLongArgument(PyObject arg, String name) {
      if (!(arg instanceof PyInteger) && !(arg instanceof PyLong)) {
         if (!(arg instanceof PyFloat) && arg.isNumberType()) {
            PyObject intObj = arg.__int__();
            if (!(intObj instanceof PyInteger) && !(intObj instanceof PyLong)) {
               throw Py.TypeError("__int__ should return int object");
            } else {
               return intObj;
            }
         } else {
            throw Py.TypeError(String.format("range() integer %s argument expected, got %s.", name, arg.getType().fastGetName()));
         }
      } else {
         return arg;
      }
   }

   private static PyString readline(PyObject file) {
      if (file instanceof PyFile) {
         return ((PyFile)file).readline();
      } else {
         PyObject ret = file.invoke("readline");
         if (!(ret instanceof PyString)) {
            throw Py.TypeError("object.readline() returned non-string");
         } else {
            return (PyString)ret;
         }
      }
   }

   public static String raw_input(PyObject prompt, PyObject file) {
      PyObject stdout = Py.getSystemState().stdout;
      if (stdout instanceof PyAttributeDeleted) {
         throw Py.RuntimeError("[raw_]input: lost sys.stdout");
      } else {
         Py.print(stdout, prompt);
         String data = readline(file).toString();
         if (data.endsWith("\n")) {
            return data.substring(0, data.length() - 1);
         } else if (data.length() == 0) {
            throw Py.EOFError("raw_input()");
         } else {
            return data;
         }
      }
   }

   public static String raw_input(PyObject prompt) {
      PyObject stdin = Py.getSystemState().stdin;
      if (stdin instanceof PyAttributeDeleted) {
         throw Py.RuntimeError("[raw_]input: lost sys.stdin");
      } else {
         return raw_input(prompt, stdin);
      }
   }

   public static String raw_input() {
      return raw_input(Py.EmptyString);
   }

   public static PyObject reduce(PyObject f, PyObject l, PyObject z) {
      PyObject result = z;
      PyObject iter = Py.iter(l, "reduce() arg 2 must support iteration");

      PyObject item;
      while((item = iter.__iternext__()) != null) {
         if (result == null) {
            result = item;
         } else {
            result = f.__call__(result, item);
         }
      }

      if (result == null) {
         throw Py.TypeError("reduce of empty sequence with no initial value");
      } else {
         return result;
      }
   }

   public static PyObject reduce(PyObject f, PyObject l) {
      return reduce(f, l, (PyObject)null);
   }

   public static PyObject reload(PyObject o) {
      Object module = o.__tojava__(PyModule.class);
      if (module == Py.NoConversion) {
         if (o instanceof PySystemState) {
            return reload((PySystemState)o);
         } else if (o instanceof PyJavaType) {
            return o;
         } else {
            throw Py.TypeError("reload() argument must be a module");
         }
      } else {
         return reload((PyModule)module);
      }
   }

   public static PyObject reload(PyModule o) {
      return imp.reload(o);
   }

   public static PyObject reload(PySystemState o) {
      o.reload();
      return o;
   }

   public static PyString repr(PyObject o) {
      return o.__repr__();
   }

   public static void setattr(PyObject obj, PyObject name, PyObject value) {
      obj.__setattr__(asName(name, "setattr"), value);
   }

   public static PyObject sum(PyObject seq, PyObject result) {
      if (result instanceof PyString) {
         throw Py.TypeError("sum() can't sum strings [use ''.join(seq) instead]");
      } else {
         PyObject item;
         for(Iterator var2 = seq.asIterable().iterator(); var2.hasNext(); result = result._add(item)) {
            item = (PyObject)var2.next();
         }

         return result;
      }
   }

   public static PyObject reversed(PyObject seq) {
      PyObject reversed = seq.__findattr__("__reversed__");
      if (reversed != null) {
         return reversed.__call__();
      } else if (seq.__findattr__("__getitem__") != null && seq.__findattr__("__len__") != null && seq.__findattr__("keys") == null) {
         PyObject reversed = new PyReversedIterator(seq);
         return reversed;
      } else {
         throw Py.TypeError("argument to reversed() must be a sequence");
      }
   }

   public static PyObject sum(PyObject seq) {
      return sum(seq, Py.Zero);
   }

   public static PyType type(PyObject o) {
      return o.getType();
   }

   public static PyObject vars() {
      return locals();
   }

   public static PyObject vars(PyObject o) {
      try {
         return o.__getattr__("__dict__");
      } catch (PyException var2) {
         if (var2.match(Py.AttributeError)) {
            throw Py.TypeError("vars() argument must have __dict__ attribute");
         } else {
            throw var2;
         }
      }
   }

   public static PyObject zip() {
      return new PyList();
   }

   public static PyObject zip(PyObject[] argstar) {
      int itemsize = argstar.length;
      PyObject[] iters = new PyObject[itemsize];

      for(int j = 0; j < itemsize; ++j) {
         PyObject iter = argstar[j].__iter__();
         if (iter == null) {
            throw Py.TypeError("zip argument #" + (j + 1) + " must support iteration");
         }

         iters[j] = iter;
      }

      PyList ret = new PyList();
      int i = 0;

      while(true) {
         PyObject[] next = new PyObject[itemsize];

         for(int j = 0; j < itemsize; ++j) {
            PyObject item;
            try {
               item = iters[j].__iternext__();
            } catch (PyException var9) {
               if (var9.match(Py.StopIteration)) {
                  return ret;
               }

               throw var9;
            }

            if (item == null) {
               return ret;
            }

            next[j] = item;
         }

         ret.append(new PyTuple(next));
         ++i;
      }
   }

   public static PyObject __import__(String name) {
      return __import__(name, (PyObject)null, (PyObject)null, (PyObject)null, -1);
   }

   public static PyObject __import__(String name, PyObject globals) {
      return __import__(name, globals, (PyObject)null, (PyObject)null, -1);
   }

   public static PyObject __import__(String name, PyObject globals, PyObject locals) {
      return __import__(name, globals, locals, (PyObject)null, -1);
   }

   public static PyObject __import__(String name, PyObject globals, PyObject locals, PyObject fromlist) {
      return __import__(name, globals, locals, fromlist, -1);
   }

   public static PyObject __import__(String name, PyObject globals, PyObject locals, PyObject fromlist, int level) {
      PyFrame frame = Py.getFrame();
      PyObject builtins;
      if (frame != null && frame.f_builtins != null) {
         builtins = frame.f_builtins;
      } else {
         builtins = Py.getSystemState().builtins;
      }

      PyObject __import__ = builtins.__finditem__("__import__");
      if (__import__ == null) {
         return null;
      } else {
         PyObject[] args;
         if (level < 0) {
            args = new PyObject[]{Py.newString(name), globals, locals, fromlist};
         } else {
            args = new PyObject[]{Py.newString(name), globals, locals, fromlist, Py.newInteger(level)};
         }

         PyObject module = __import__.__call__(args);
         return module;
      }
   }

   private static String asName(PyObject name, String function) {
      if (name instanceof PyUnicode) {
         return ((PyUnicode)name).encode().intern();
      } else if (name instanceof PyString) {
         return ((PyString)name).internedString();
      } else {
         throw Py.TypeError(function + "(): attribute name must be string");
      }
   }
}
