package org.python.modules;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyClass;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyInstance;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyModule;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PyReflectedFunction;
import org.python.core.PySequence;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.__builtin__;
import org.python.core.codecs;
import org.python.core.exceptions;
import org.python.core.imp;
import org.python.util.Generic;

public class cPickle implements ClassDictInit {
   public static String __doc__ = "Java implementation and optimization of the Python pickle module\n";
   public static String __version__ = "1.30";
   public static final String format_version = "2.0";
   public static final String[] compatible_formats = new String[]{"1.0", "1.1", "1.2", "1.3", "2.0"};
   public static final int HIGHEST_PROTOCOL = 2;
   public static String[] __depends__ = new String[]{"copy_reg"};
   public static PyObject PickleError;
   public static PyObject PicklingError;
   public static PyObject UnpickleableError;
   public static PyObject UnpicklingError;
   public static PyObject BadPickleGet;
   static final char MARK = '(';
   static final char STOP = '.';
   static final char POP = '0';
   static final char POP_MARK = '1';
   static final char DUP = '2';
   static final char FLOAT = 'F';
   static final char INT = 'I';
   static final char BININT = 'J';
   static final char BININT1 = 'K';
   static final char LONG = 'L';
   static final char BININT2 = 'M';
   static final char NONE = 'N';
   static final char PERSID = 'P';
   static final char BINPERSID = 'Q';
   static final char REDUCE = 'R';
   static final char STRING = 'S';
   static final char BINSTRING = 'T';
   static final char SHORT_BINSTRING = 'U';
   static final char UNICODE = 'V';
   static final char BINUNICODE = 'X';
   static final char APPEND = 'a';
   static final char BUILD = 'b';
   static final char GLOBAL = 'c';
   static final char DICT = 'd';
   static final char EMPTY_DICT = '}';
   static final char APPENDS = 'e';
   static final char GET = 'g';
   static final char BINGET = 'h';
   static final char INST = 'i';
   static final char LONG_BINGET = 'j';
   static final char LIST = 'l';
   static final char EMPTY_LIST = ']';
   static final char OBJ = 'o';
   static final char PUT = 'p';
   static final char BINPUT = 'q';
   static final char LONG_BINPUT = 'r';
   static final char SETITEM = 's';
   static final char TUPLE = 't';
   static final char EMPTY_TUPLE = ')';
   static final char SETITEMS = 'u';
   static final char BINFLOAT = 'G';
   static final char PROTO = '\u0080';
   static final char NEWOBJ = '\u0081';
   static final char EXT1 = '\u0082';
   static final char EXT2 = '\u0083';
   static final char EXT4 = '\u0084';
   static final char TUPLE1 = '\u0085';
   static final char TUPLE2 = '\u0086';
   static final char TUPLE3 = '\u0087';
   static final char NEWTRUE = '\u0088';
   static final char NEWFALSE = '\u0089';
   static final char LONG1 = '\u008a';
   static final char LONG4 = '\u008b';
   private static PyDictionary dispatch_table;
   private static PyDictionary extension_registry;
   private static PyDictionary inverted_registry;
   private static PyType BuiltinCallableType = PyType.fromClass(PyBuiltinCallable.class);
   private static PyType ReflectedFunctionType = PyType.fromClass(PyReflectedFunction.class);
   private static PyType ClassType = PyType.fromClass(PyClass.class);
   private static PyType TypeType = PyType.fromClass(PyType.class);
   private static PyType DictionaryType = PyType.fromClass(PyDictionary.class);
   private static PyType StringMapType = PyType.fromClass(PyStringMap.class);
   private static PyType FloatType = PyType.fromClass(PyFloat.class);
   private static PyType FunctionType = PyType.fromClass(PyFunction.class);
   private static PyType InstanceType = PyType.fromClass(PyInstance.class);
   private static PyType IntType = PyType.fromClass(PyInteger.class);
   private static PyType ListType = PyType.fromClass(PyList.class);
   private static PyType LongType = PyType.fromClass(PyLong.class);
   private static PyType NoneType = PyType.fromClass(PyNone.class);
   private static PyType StringType = PyType.fromClass(PyString.class);
   private static PyType UnicodeType = PyType.fromClass(PyUnicode.class);
   private static PyType TupleType = PyType.fromClass(PyTuple.class);
   private static PyType FileType = PyType.fromClass(PyFile.class);
   private static PyType BoolType = PyType.fromClass(PyBoolean.class);
   private static PyObject dict;
   private static final int BATCHSIZE = 1024;
   private static Map classmap = Generic.map();

   public static void classDictInit(PyObject dict) {
      cPickle.dict = dict;
      imp.importName("__builtin__", true);
      PyModule copyreg = (PyModule)importModule("copy_reg");
      dispatch_table = (PyDictionary)copyreg.__getattr__("dispatch_table");
      extension_registry = (PyDictionary)copyreg.__getattr__("_extension_registry");
      inverted_registry = (PyDictionary)copyreg.__getattr__("_inverted_registry");
      PickleError = Py.makeClass("PickleError", Py.Exception, _PickleError());
      PicklingError = Py.makeClass("PicklingError", PickleError, exceptionNamespace());
      UnpickleableError = Py.makeClass("UnpickleableError", PicklingError, _UnpickleableError());
      UnpicklingError = Py.makeClass("UnpicklingError", PickleError, exceptionNamespace());
      BadPickleGet = Py.makeClass("BadPickleGet", UnpicklingError, exceptionNamespace());
   }

   public static PyObject exceptionNamespace() {
      PyObject dict = new PyStringMap();
      dict.__setitem__((String)"__module__", new PyString("cPickle"));
      return dict;
   }

   public static PyObject _PickleError() {
      dict = exceptionNamespace();
      dict.__setitem__("__str__", getJavaFunc("__str__", "_PickleError__str__"));
      return dict;
   }

   public static PyString _PickleError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      PyObject selfArgs = self.__getattr__("args");
      return selfArgs.__len__() > 0 && selfArgs.__getitem__(0).__len__() > 0 ? selfArgs.__getitem__(0).__str__() : new PyString("(what)");
   }

   public static PyObject _UnpickleableError() {
      dict = exceptionNamespace();
      dict.__setitem__("__str__", getJavaFunc("__str__", "_UnpickleableError__str__"));
      return dict;
   }

   public static PyString _UnpickleableError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      PyObject selfArgs = self.__getattr__("args");
      PyObject a = selfArgs.__len__() > 0 ? selfArgs.__getitem__(0) : new PyString("(what)");
      return (new PyString("Cannot pickle %s objects")).__mod__((PyObject)a).__str__();
   }

   public static Pickler Pickler(PyObject file) {
      return new Pickler(file, 0);
   }

   public static Pickler Pickler(PyObject file, int protocol) {
      return new Pickler(file, protocol);
   }

   public static Unpickler Unpickler(PyObject file) {
      return new Unpickler(file);
   }

   public static void dump(PyObject object, PyObject file) {
      dump(object, file, 0);
   }

   public static void dump(PyObject object, PyObject file, int protocol) {
      (new Pickler(file, protocol)).dump(object);
   }

   public static PyString dumps(PyObject object) {
      return dumps(object, 0);
   }

   public static PyString dumps(PyObject object, int protocol) {
      cStringIO.StringIO file = cStringIO.StringIO();
      dump(object, file, protocol);
      return file.getvalue();
   }

   public static Object load(PyObject file) {
      try {
         return (new Unpickler(file)).load();
      } catch (ArrayIndexOutOfBoundsException var2) {
         throw Py.IndexError(var2.getMessage());
      } catch (StringIndexOutOfBoundsException var3) {
         throw Py.EOFError(var3.getMessage());
      }
   }

   public static Object loads(PyObject str) {
      cStringIO.StringIO file = cStringIO.StringIO((CharSequence)str.toString());
      return load(file);
   }

   private static final PyObject whichmodule(PyObject cls, PyObject clsname) {
      PyObject name = (PyObject)classmap.get(cls);
      if (name != null) {
         return name;
      } else {
         PyObject name = new PyString("__main__");
         PyObject sys = imp.importName("sys", true);
         PyObject modules = sys.__findattr__("modules");
         PyObject keylist = modules.invoke("keys");
         int len = keylist.__len__();

         for(int i = 0; i < len; ++i) {
            PyObject key = keylist.__finditem__(i);
            PyObject value = modules.__finditem__(key);
            if (!key.equals("__main__") && value.__findattr__(clsname.toString().intern()) == cls) {
               name = key;
               break;
            }
         }

         classmap.put(cls, name);
         return (PyObject)name;
      }
   }

   private static PyObject importModule(String name) {
      PyObject fromlist = new PyTuple(new PyObject[]{Py.newString("__doc__")});
      return __builtin__.__import__(name, Py.None, Py.None, fromlist);
   }

   private static PyObject getJavaFunc(String name, String methodName) {
      return exceptions.bindStaticJavaMethod(name, cPickle.class, methodName);
   }

   public static class Unpickler {
      private PyIOFile file;
      public Map memo = Generic.map();
      public PyObject persistent_load = null;
      public PyObject find_global = null;
      private PyObject mark = new PyString("spam");
      private int stackTop;
      private PyObject[] stack;

      Unpickler(PyObject file) {
         this.file = PyIOFileFactory.createIOFile(file);
      }

      public PyObject load() {
         this.stackTop = 0;
         this.stack = new PyObject[10];

         while(true) {
            String s = this.file.read(1);
            if (s.length() < 1) {
               this.load_eof();
            }

            char key = s.charAt(0);
            switch (key) {
               case '(':
                  this.load_mark();
                  break;
               case ')':
                  this.load_empty_tuple();
                  break;
               case '*':
               case '+':
               case ',':
               case '-':
               case '/':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '@':
               case 'A':
               case 'B':
               case 'C':
               case 'D':
               case 'E':
               case 'H':
               case 'O':
               case 'W':
               case 'Y':
               case 'Z':
               case '[':
               case '\\':
               case '^':
               case '_':
               case '`':
               case 'f':
               case 'k':
               case 'm':
               case 'n':
               case 'v':
               case 'w':
               case 'x':
               case 'y':
               case 'z':
               case '{':
               case '|':
               case '~':
               case '\u007f':
               default:
                  throw new PyException(cPickle.UnpicklingError, String.format("invalid load key, '%s'.", key));
               case '.':
                  return this.load_stop();
               case '0':
                  this.load_pop();
                  break;
               case '1':
                  this.load_pop_mark();
                  break;
               case '2':
                  this.load_dup();
                  break;
               case 'F':
                  this.load_float();
                  break;
               case 'G':
                  this.load_binfloat();
                  break;
               case 'I':
                  this.load_int();
                  break;
               case 'J':
                  this.load_binint();
                  break;
               case 'K':
                  this.load_binint1();
                  break;
               case 'L':
                  this.load_long();
                  break;
               case 'M':
                  this.load_binint2();
                  break;
               case 'N':
                  this.load_none();
                  break;
               case 'P':
                  this.load_persid();
                  break;
               case 'Q':
                  this.load_binpersid();
                  break;
               case 'R':
                  this.load_reduce();
                  break;
               case 'S':
                  this.load_string();
                  break;
               case 'T':
                  this.load_binstring();
                  break;
               case 'U':
                  this.load_short_binstring();
                  break;
               case 'V':
                  this.load_unicode();
                  break;
               case 'X':
                  this.load_binunicode();
                  break;
               case ']':
                  this.load_empty_list();
                  break;
               case 'a':
                  this.load_append();
                  break;
               case 'b':
                  this.load_build();
                  break;
               case 'c':
                  this.load_global();
                  break;
               case 'd':
                  this.load_dict();
                  break;
               case 'e':
                  this.load_appends();
                  break;
               case 'g':
                  this.load_get();
                  break;
               case 'h':
                  this.load_binget();
                  break;
               case 'i':
                  this.load_inst();
                  break;
               case 'j':
                  this.load_long_binget();
                  break;
               case 'l':
                  this.load_list();
                  break;
               case 'o':
                  this.load_obj();
                  break;
               case 'p':
                  this.load_put();
                  break;
               case 'q':
                  this.load_binput();
                  break;
               case 'r':
                  this.load_long_binput();
                  break;
               case 's':
                  this.load_setitem();
                  break;
               case 't':
                  this.load_tuple();
                  break;
               case 'u':
                  this.load_setitems();
                  break;
               case '}':
                  this.load_empty_dictionary();
                  break;
               case '\u0080':
                  this.load_proto();
                  break;
               case '\u0081':
                  this.load_newobj();
                  break;
               case '\u0082':
                  this.load_ext(1);
                  break;
               case '\u0083':
                  this.load_ext(2);
                  break;
               case '\u0084':
                  this.load_ext(4);
                  break;
               case '\u0085':
                  this.load_small_tuple(1);
                  break;
               case '\u0086':
                  this.load_small_tuple(2);
                  break;
               case '\u0087':
                  this.load_small_tuple(3);
                  break;
               case '\u0088':
                  this.load_boolean(true);
                  break;
               case '\u0089':
                  this.load_boolean(false);
                  break;
               case '\u008a':
                  this.load_bin_long(1);
                  break;
               case '\u008b':
                  this.load_bin_long(4);
            }
         }
      }

      private final int marker() {
         for(int k = this.stackTop - 1; k >= 0; --k) {
            if (this.stack[k] == this.mark) {
               return this.stackTop - k - 1;
            }
         }

         throw new PyException(cPickle.UnpicklingError, "Inputstream corrupt, marker not found");
      }

      private final void load_eof() {
         throw new PyException(Py.EOFError);
      }

      private void load_proto() {
         int proto = this.file.read(1).charAt(0);
         if (proto < 0 || proto > 2) {
            throw Py.ValueError("unsupported pickle protocol: " + proto);
         }
      }

      private final void load_persid() {
         this.load_persid(new PyString(this.file.readlineNoNl()));
      }

      private final void load_binpersid() {
         this.load_persid(this.pop());
      }

      private final void load_persid(PyObject pid) {
         if (this.persistent_load == null) {
            throw new PyException(cPickle.UnpicklingError, "A load persistent id instruction was encountered,\nbut no persistent_load function was specified.");
         } else {
            if (this.persistent_load instanceof PyList) {
               ((PyList)this.persistent_load).append(pid);
            } else {
               pid = this.persistent_load.__call__(pid);
            }

            this.push(pid);
         }
      }

      private final void load_none() {
         this.push(Py.None);
      }

      private final void load_int() {
         String line = this.file.readlineNoNl();
         Object value;
         if (line.equals("01")) {
            value = Py.True;
         } else if (line.equals("00")) {
            value = Py.False;
         } else {
            try {
               value = Py.newInteger(Integer.parseInt(line));
            } catch (NumberFormatException var6) {
               try {
                  value = Py.newLong(line);
               } catch (NumberFormatException var5) {
                  throw Py.ValueError("could not convert string to int");
               }
            }
         }

         this.push((PyObject)value);
      }

      private void load_boolean(boolean value) {
         this.push(value ? Py.True : Py.False);
      }

      private final void load_binint() {
         int x = this.read_binint();
         this.push(new PyInteger(x));
      }

      private int read_binint() {
         String s = this.file.read(4);
         return s.charAt(0) | s.charAt(1) << 8 | s.charAt(2) << 16 | s.charAt(3) << 24;
      }

      private final void load_binint1() {
         int val = this.file.read(1).charAt(0);
         this.push(new PyInteger(val));
      }

      private final void load_binint2() {
         int val = this.read_binint2();
         this.push(new PyInteger(val));
      }

      private int read_binint2() {
         String s = this.file.read(2);
         return s.charAt(1) << 8 | s.charAt(0);
      }

      private final void load_long() {
         String line = this.file.readlineNoNl();
         this.push(new PyLong(line.substring(0, line.length() - 1)));
      }

      private void load_bin_long(int length) {
         int longLength = this.read_binint(length);
         if (longLength == 0) {
            this.push(new PyLong(BigInteger.ZERO));
         } else {
            String s = this.file.read(longLength);
            byte[] bytes = new byte[s.length()];
            int n = s.length() - 1;

            for(int i = 0; i < s.length(); --n) {
               char c = s.charAt(i);
               if (c >= 128) {
                  bytes[n] = (byte)(c - 256);
               } else {
                  bytes[n] = (byte)c;
               }

               ++i;
            }

            BigInteger bigint = new BigInteger(bytes);
            this.push(new PyLong(bigint));
         }
      }

      private int read_binint(int length) {
         if (length == 1) {
            return this.file.read(1).charAt(0);
         } else {
            return length == 2 ? this.read_binint2() : this.read_binint();
         }
      }

      private final void load_float() {
         String line = this.file.readlineNoNl();
         this.push(new PyFloat(Double.valueOf(line)));
      }

      private final void load_binfloat() {
         String s = this.file.read(8);
         long bits = (long)s.charAt(7) | (long)s.charAt(6) << 8 | (long)s.charAt(5) << 16 | (long)s.charAt(4) << 24 | (long)s.charAt(3) << 32 | (long)s.charAt(2) << 40 | (long)s.charAt(1) << 48 | (long)s.charAt(0) << 56;
         this.push(new PyFloat(Double.longBitsToDouble(bits)));
      }

      private final void load_string() {
         String line = this.file.readlineNoNl();
         char quote = line.charAt(0);
         if (quote != '"' && quote != '\'') {
            throw Py.ValueError("insecure string pickle");
         } else {
            int nslash = 0;
            char ch = 0;
            int n = line.length();

            int i;
            for(i = 1; i < n; ++i) {
               ch = line.charAt(i);
               if (ch == quote && nslash % 2 == 0) {
                  break;
               }

               if (ch == '\\') {
                  ++nslash;
               } else {
                  nslash = 0;
               }
            }

            if (ch != quote) {
               throw Py.ValueError("insecure string pickle");
            } else {
               ++i;

               while(i < line.length()) {
                  if (line.charAt(i) > ' ') {
                     throw Py.ValueError("insecure string pickle " + i);
                  }

                  ++i;
               }

               String value = PyString.decode_UnicodeEscape(line, 1, n - 1, "strict", false);
               this.push(new PyString(value));
            }
         }
      }

      private final void load_binstring() {
         int len = this.read_binint();
         this.push(new PyString(this.file.read(len)));
      }

      private final void load_short_binstring() {
         int len = this.file.read(1).charAt(0);
         this.push(new PyString(this.file.read(len)));
      }

      private final void load_unicode() {
         String line = this.file.readlineNoNl();
         String value = codecs.PyUnicode_DecodeRawUnicodeEscape(line, "strict");
         this.push(new PyUnicode(value));
      }

      private final void load_binunicode() {
         int len = this.read_binint();
         String line = this.file.read(len);
         this.push(new PyUnicode(codecs.PyUnicode_DecodeUTF8(line, "strict")));
      }

      private final void load_tuple() {
         PyObject[] arr = new PyObject[this.marker()];
         this.pop(arr);
         this.pop();
         this.push(new PyTuple(arr));
      }

      private final void load_empty_tuple() {
         this.push(new PyTuple(Py.EmptyObjects));
      }

      private void load_small_tuple(int length) {
         PyObject[] data = new PyObject[length];

         for(int i = length - 1; i >= 0; --i) {
            data[i] = this.pop();
         }

         this.push(new PyTuple(data));
      }

      private final void load_empty_list() {
         this.push(new PyList(Py.EmptyObjects));
      }

      private final void load_empty_dictionary() {
         this.push(new PyDictionary());
      }

      private final void load_list() {
         PyObject[] arr = new PyObject[this.marker()];
         this.pop(arr);
         this.pop();
         this.push(new PyList(arr));
      }

      private final void load_dict() {
         int k = this.marker();
         PyDictionary d = new PyDictionary();

         for(int i = 0; i < k; i += 2) {
            PyObject value = this.pop();
            PyObject key = this.pop();
            d.__setitem__(key, value);
         }

         this.pop();
         this.push(d);
      }

      private final void load_inst() {
         PyObject[] args = new PyObject[this.marker()];
         this.pop(args);
         this.pop();
         String module = this.file.readlineNoNl();
         String name = this.file.readlineNoNl();
         PyObject klass = this.find_class(module, name);
         PyObject value = null;
         if (args.length == 0 && klass instanceof PyClass && klass.__findattr__("__getinitargs__") == null) {
            value = new PyInstance((PyClass)klass);
         } else {
            value = klass.__call__(args);
         }

         this.push((PyObject)value);
      }

      private final void load_obj() {
         PyObject[] args = new PyObject[this.marker() - 1];
         this.pop(args);
         PyObject klass = this.pop();
         this.pop();
         PyObject value = null;
         if (args.length == 0 && klass instanceof PyClass && klass.__findattr__("__getinitargs__") == null) {
            value = new PyInstance((PyClass)klass);
         } else {
            value = klass.__call__(args);
         }

         this.push((PyObject)value);
      }

      private final void load_global() {
         String module = this.file.readlineNoNl();
         String name = this.file.readlineNoNl();
         PyObject klass = this.find_class(module, name);
         this.push(klass);
      }

      private final PyObject find_class(String module, String name) {
         if (this.find_global != null) {
            if (this.find_global == Py.None) {
               throw new PyException(cPickle.UnpicklingError, "Global and instance pickles are not supported.");
            } else {
               return this.find_global.__call__((PyObject)(new PyString(module)), (PyObject)(new PyString(name)));
            }
         } else {
            PyObject modules = Py.getSystemState().modules;
            PyObject mod = modules.__finditem__(module.intern());
            if (mod == null) {
               mod = cPickle.importModule(module);
            }

            PyObject global = mod.__findattr__(name.intern());
            if (global == null) {
               throw new PyException(Py.SystemError, "Failed to import class " + name + " from module " + module);
            } else {
               return global;
            }
         }
      }

      private void load_ext(int length) {
         int code = this.read_binint(length);
         PyObject key = cPickle.inverted_registry.get((PyObject)Py.newInteger(code));
         if (key == null) {
            throw new PyException(Py.ValueError, "unregistered extension code " + code);
         } else {
            String module = key.__finditem__(0).toString();
            String name = key.__finditem__(1).toString();
            this.push(this.find_class(module, name));
         }
      }

      private final void load_reduce() {
         PyObject arg_tup = this.pop();
         PyObject callable = this.pop();
         PyObject value = null;
         if (arg_tup == Py.None) {
            value = callable.__findattr__("__basicnew__").__call__();
         } else {
            value = callable.__call__(this.make_array(arg_tup));
         }

         this.push(value);
      }

      private void load_newobj() {
         PyObject arg_tup = this.pop();
         PyObject cls = this.pop();
         PyObject[] args = new PyObject[arg_tup.__len__() + 1];
         args[0] = cls;

         for(int i = 1; i < args.length; ++i) {
            args[i] = arg_tup.__finditem__(i - 1);
         }

         this.push(cls.__getattr__("__new__").__call__(args));
      }

      private final PyObject[] make_array(PyObject seq) {
         int n = seq.__len__();
         PyObject[] objs = new PyObject[n];

         for(int i = 0; i < n; ++i) {
            objs[i] = seq.__finditem__(i);
         }

         return objs;
      }

      private final void load_pop() {
         this.pop();
      }

      private final void load_pop_mark() {
         this.pop(this.marker());
      }

      private final void load_dup() {
         this.push(this.peek());
      }

      private final void load_get() {
         String py_str = this.file.readlineNoNl();
         PyObject value = (PyObject)this.memo.get(py_str);
         if (value == null) {
            throw new PyException(cPickle.BadPickleGet, py_str);
         } else {
            this.push(value);
         }
      }

      private final void load_binget() {
         String py_key = String.valueOf(this.file.read(1).charAt(0));
         PyObject value = (PyObject)this.memo.get(py_key);
         if (value == null) {
            throw new PyException(cPickle.BadPickleGet, py_key);
         } else {
            this.push(value);
         }
      }

      private final void load_long_binget() {
         int i = this.read_binint();
         String py_key = String.valueOf(i);
         PyObject value = (PyObject)this.memo.get(py_key);
         if (value == null) {
            throw new PyException(cPickle.BadPickleGet, py_key);
         } else {
            this.push(value);
         }
      }

      private final void load_put() {
         this.memo.put(this.file.readlineNoNl(), this.peek());
      }

      private final void load_binput() {
         int i = this.file.read(1).charAt(0);
         this.memo.put(String.valueOf(i), this.peek());
      }

      private final void load_long_binput() {
         int i = this.read_binint();
         this.memo.put(String.valueOf(i), this.peek());
      }

      private final void load_append() {
         PyObject value = this.pop();
         PyObject obj = this.peek();
         if (obj instanceof PyList) {
            ((PyList)obj).append(value);
         } else {
            PyObject appender = obj.__getattr__("append");
            appender.__call__(value);
         }

      }

      private final void load_appends() {
         int mark = this.marker();
         PyObject obj = this.peek(mark + 1);
         if (obj instanceof PyList) {
            for(int i = mark - 1; i >= 0; --i) {
               ((PyList)obj).append(this.peek(i));
            }
         } else {
            PyObject appender = obj.__getattr__("append");

            for(int i = mark - 1; i >= 0; --i) {
               appender.__call__(this.peek(i));
            }
         }

         this.pop(mark + 1);
      }

      private final void load_setitem() {
         PyObject value = this.pop();
         PyObject key = this.pop();
         PyDictionary dict = (PyDictionary)this.peek();
         dict.__setitem__(key, value);
      }

      private final void load_setitems() {
         int mark = this.marker();
         PyDictionary dict = (PyDictionary)this.peek(mark + 1);

         for(int i = 0; i < mark; i += 2) {
            PyObject key = this.peek(i + 1);
            PyObject value = this.peek(i);
            dict.__setitem__(key, value);
         }

         this.pop(mark + 1);
      }

      private void load_build() {
         PyObject state = this.pop();
         PyObject inst = this.peek();
         PyObject setstate = inst.__findattr__("__setstate__");
         if (setstate != null) {
            setstate.__call__(state);
         } else {
            PyObject slotstate = null;
            PyObject dict;
            if (state instanceof PyTuple && state.__len__() == 2) {
               dict = state;
               state = state.__getitem__(0);
               slotstate = dict.__getitem__(1);
            }

            if (state != Py.None) {
               if (!(state instanceof PyDictionary)) {
                  throw new PyException(cPickle.UnpicklingError, "state is not a dictionary");
               }

               dict = inst.__getattr__("__dict__");
               Iterator var6 = ((PyDictionary)state).iteritems().asIterable().iterator();

               while(var6.hasNext()) {
                  PyObject item = (PyObject)var6.next();
                  dict.__setitem__(item.__getitem__(0), item.__getitem__(1));
               }
            }

            if (slotstate != null) {
               if (!(slotstate instanceof PyDictionary)) {
                  throw new PyException(cPickle.UnpicklingError, "slot state is not a dictionary");
               }

               Iterator var8 = ((PyDictionary)slotstate).iteritems().asIterable().iterator();

               while(var8.hasNext()) {
                  PyObject item = (PyObject)var8.next();
                  inst.__setattr__(PyObject.asName(item.__getitem__(0)), item.__getitem__(1));
               }
            }

         }
      }

      private final void load_mark() {
         this.push(this.mark);
      }

      private final PyObject load_stop() {
         return this.pop();
      }

      private final PyObject peek() {
         return this.stack[this.stackTop - 1];
      }

      private final PyObject peek(int count) {
         return this.stack[this.stackTop - count - 1];
      }

      private final PyObject pop() {
         PyObject val = this.stack[--this.stackTop];
         this.stack[this.stackTop] = null;
         return val;
      }

      private final void pop(int count) {
         for(int i = 0; i < count; ++i) {
            this.stack[--this.stackTop] = null;
         }

      }

      private final void pop(PyObject[] arr) {
         int len = arr.length;
         System.arraycopy(this.stack, this.stackTop - len, arr, 0, len);
         this.stackTop -= len;
      }

      private final void push(PyObject val) {
         if (this.stackTop >= this.stack.length) {
            PyObject[] newStack = new PyObject[(this.stackTop + 1) * 2];
            System.arraycopy(this.stack, 0, newStack, 0, this.stack.length);
            this.stack = newStack;
         }

         this.stack[this.stackTop++] = val;
      }
   }

   private static class PickleMemo {
      private final int[] primes;
      private transient int[] keys;
      private transient int[] position;
      private transient Object[] values;
      private int size;
      private transient int filled;
      private transient int prime;

      public PickleMemo(int capacity) {
         this.primes = new int[]{13, 61, 251, 1021, 4093, 5987, 9551, 15683, 19609, 31397, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301, 8388593, 16777213, 33554393, 67108859, 134217689, 268435399, 536870909, 1073741789};
         this.prime = 0;
         this.keys = null;
         this.values = null;
         this.resize(capacity);
      }

      public PickleMemo() {
         this(4);
      }

      public int size() {
         return this.size;
      }

      private int findIndex(int key, Object value) {
         int[] table = this.keys;
         int maxindex = table.length;
         int index = (key & Integer.MAX_VALUE) % maxindex;
         int stepsize = maxindex / 5;

         while(true) {
            int tkey = table[index];
            if (tkey == key && value == this.values[index]) {
               return index;
            }

            if (this.values[index] == null) {
               return -1;
            }

            index = (index + stepsize) % maxindex;
         }
      }

      public int findPosition(int key, Object value) {
         int idx = this.findIndex(key, value);
         return idx < 0 ? -1 : this.position[idx];
      }

      public Object findValue(int key, Object value) {
         int idx = this.findIndex(key, value);
         return idx < 0 ? null : this.values[idx];
      }

      private final void insertkey(int key, int pos, Object value) {
         int[] table = this.keys;
         int maxindex = table.length;
         int index = (key & Integer.MAX_VALUE) % maxindex;
         int stepsize = maxindex / 5;

         while(true) {
            int tkey = table[index];
            if (this.values[index] == null) {
               table[index] = key;
               this.position[index] = pos;
               this.values[index] = value;
               ++this.filled;
               ++this.size;
               break;
            }

            if (tkey == key && this.values[index] == value) {
               this.position[index] = pos;
               break;
            }

            index = (index + stepsize) % maxindex;
         }

      }

      private final void resize(int capacity) {
         int p;
         for(p = this.prime; p < this.primes.length && this.primes[p] < capacity; ++p) {
         }

         if (this.primes[p] < capacity) {
            throw Py.ValueError("can't make hashtable of size: " + capacity);
         } else {
            capacity = this.primes[p];
            this.prime = p;
            int[] oldKeys = this.keys;
            int[] oldPositions = this.position;
            Object[] oldValues = this.values;
            this.keys = new int[capacity];
            this.position = new int[capacity];
            this.values = new Object[capacity];
            this.size = 0;
            this.filled = 0;
            if (oldValues != null) {
               int n = oldValues.length;

               for(int i = 0; i < n; ++i) {
                  Object value = oldValues[i];
                  if (value != null) {
                     this.insertkey(oldKeys[i], oldPositions[i], value);
                  }
               }
            }

         }
      }

      public void put(int key, int pos, Object value) {
         if (2 * this.filled > this.keys.length) {
            this.resize(this.keys.length + 1);
         }

         this.insertkey(key, pos, value);
      }
   }

   public static class Pickler {
      private PyIOFile file;
      private int protocol;
      public boolean fast = false;
      private PickleMemo memo = new PickleMemo();
      public PyObject persistent_id = null;
      public PyObject inst_persistent_id = null;

      public Pickler(PyObject file, int protocol) {
         this.file = PyIOFileFactory.createIOFile(file);
         this.protocol = protocol;
      }

      public void dump(PyObject object) {
         if (this.protocol >= 2) {
            this.file.write('\u0080');
            this.file.write((char)this.protocol);
         }

         this.save(object);
         this.file.write('.');
         this.file.flush();
      }

      private static final int get_id(PyObject o) {
         return System.identityHashCode(o);
      }

      private void put(int i) {
         if (this.protocol > 0) {
            if (i < 256) {
               this.file.write('q');
               this.file.write((char)i);
            } else {
               this.file.write('r');
               this.file.write((char)(i & 255));
               this.file.write((char)(i >>> 8 & 255));
               this.file.write((char)(i >>> 16 & 255));
               this.file.write((char)(i >>> 24 & 255));
            }
         } else {
            this.file.write('p');
            this.file.write(String.valueOf(i));
            this.file.write("\n");
         }
      }

      private void get(int i) {
         if (this.protocol > 0) {
            if (i < 256) {
               this.file.write('h');
               this.file.write((char)i);
            } else {
               this.file.write('j');
               this.file.write((char)(i & 255));
               this.file.write((char)(i >>> 8 & 255));
               this.file.write((char)(i >>> 16 & 255));
               this.file.write((char)(i >>> 24 & 255));
            }
         } else {
            this.file.write('g');
            this.file.write(String.valueOf(i));
            this.file.write("\n");
         }
      }

      private void save(PyObject object) {
         this.save(object, false);
      }

      private void save(PyObject object, boolean pers_save) {
         if (pers_save || this.persistent_id == null || !this.save_pers(object, this.persistent_id)) {
            int d = get_id(object);
            PyType t = object.getType();
            if (t == cPickle.TupleType && object.__len__() == 0) {
               if (this.protocol > 0) {
                  this.save_empty_tuple(object);
               } else {
                  this.save_tuple(object);
               }

            } else {
               int m = this.getMemoPosition(d, object);
               if (m >= 0) {
                  this.get(m);
               } else if (!this.save_type(object, t)) {
                  if (pers_save || this.inst_persistent_id == null || !this.save_pers(object, this.inst_persistent_id)) {
                     if (Py.isSubClass(t, PyType.TYPE)) {
                        this.save_global(object);
                     } else {
                        PyObject tup = null;
                        PyObject reduce = cPickle.dispatch_table.__finditem__(t);
                        if (reduce == null) {
                           reduce = object.__findattr__("__reduce_ex__");
                           if (reduce != null) {
                              tup = reduce.__call__((PyObject)Py.newInteger(this.protocol));
                           } else {
                              reduce = object.__findattr__("__reduce__");
                              if (reduce == null) {
                                 throw new PyException(cPickle.UnpickleableError, object);
                              }

                              tup = reduce.__call__();
                           }
                        } else {
                           tup = reduce.__call__(object);
                        }

                        if (tup instanceof PyString) {
                           this.save_global(object, tup);
                        } else if (!(tup instanceof PyTuple)) {
                           throw new PyException(cPickle.PicklingError, "Value returned by " + reduce.__repr__() + " must be a tuple");
                        } else {
                           int l = tup.__len__();
                           if (l >= 2 && l <= 5) {
                              PyObject callable = tup.__finditem__(0);
                              PyObject arg_tup = tup.__finditem__(1);
                              PyObject state = l > 2 ? tup.__finditem__(2) : Py.None;
                              PyObject listitems = l > 3 ? tup.__finditem__(3) : Py.None;
                              PyObject dictitems = l > 4 ? tup.__finditem__(4) : Py.None;
                              if (!(arg_tup instanceof PyTuple) && arg_tup != Py.None) {
                                 throw new PyException(cPickle.PicklingError, "Second element of tupe returned by " + reduce.__repr__() + " must be a tuple");
                              } else {
                                 this.save_reduce(callable, arg_tup, state, listitems, dictitems, object);
                              }
                           } else {
                              throw new PyException(cPickle.PicklingError, "tuple returned by " + reduce.__repr__() + " must contain two to five elements");
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      private final boolean save_pers(PyObject object, PyObject pers_func) {
         PyObject pid = pers_func.__call__(object);
         if (pid == Py.None) {
            return false;
         } else {
            if (this.protocol == 0) {
               if (!Py.isInstance(pid, PyString.TYPE)) {
                  throw new PyException(cPickle.PicklingError, "persistent id must be string");
               }

               this.file.write('P');
               this.file.write(pid.toString());
               this.file.write("\n");
            } else {
               this.save(pid, true);
               this.file.write('Q');
            }

            return true;
         }
      }

      private final void save_reduce(PyObject callable, PyObject arg_tup, PyObject state, PyObject listitems, PyObject dictitems, PyObject object) {
         PyObject callableName = callable.__findattr__("__name__");
         if (this.protocol >= 2 && callableName != null && "__newobj__".equals(callableName.toString())) {
            PyObject cls = arg_tup.__finditem__(0);
            if (cls.__findattr__("__new__") == null) {
               throw new PyException(cPickle.PicklingError, "args[0] from __newobj__ args has no __new__");
            }

            this.save(cls);
            this.save(arg_tup.__getslice__(Py.One, Py.None));
            this.file.write('\u0081');
         } else {
            this.save(callable);
            this.save(arg_tup);
            this.file.write('R');
         }

         this.put(this.putMemo(get_id(object), object));
         if (listitems != Py.None) {
            this.batch_appends(listitems);
         }

         if (dictitems != Py.None) {
            this.batch_setitems(dictitems);
         }

         if (state != Py.None) {
            this.save(state);
            this.file.write('b');
         }

      }

      private final boolean save_type(PyObject object, PyType type) {
         if (type == cPickle.NoneType) {
            this.save_none(object);
         } else if (type == cPickle.StringType) {
            this.save_string(object);
         } else if (type == cPickle.UnicodeType) {
            this.save_unicode(object);
         } else if (type == cPickle.IntType) {
            this.save_int(object);
         } else if (type == cPickle.LongType) {
            this.save_long(object);
         } else if (type == cPickle.FloatType) {
            this.save_float(object);
         } else if (type == cPickle.TupleType) {
            this.save_tuple(object);
         } else if (type == cPickle.ListType) {
            this.save_list(object);
         } else if (type != cPickle.DictionaryType && type != cPickle.StringMapType) {
            if (type == cPickle.InstanceType) {
               this.save_inst((PyInstance)object);
            } else if (type == cPickle.ClassType) {
               this.save_global(object);
            } else if (type == cPickle.TypeType) {
               this.save_global(object);
            } else if (type == cPickle.FunctionType) {
               this.save_global(object);
            } else if (type == cPickle.BuiltinCallableType) {
               this.save_global(object);
            } else if (type == cPickle.ReflectedFunctionType) {
               this.save_global(object);
            } else {
               if (type != cPickle.BoolType) {
                  return false;
               }

               this.save_bool(object);
            }
         } else {
            this.save_dict(object);
         }

         return true;
      }

      private final void save_none(PyObject object) {
         this.file.write('N');
      }

      private final void save_int(PyObject object) {
         if (this.protocol > 0) {
            int l = ((PyInteger)object).getValue();
            char i1 = (char)(l & 255);
            char i2 = (char)(l >>> 8 & 255);
            char i3 = (char)(l >>> 16 & 255);
            char i4 = (char)(l >>> 24 & 255);
            if (i3 == 0 && i4 == 0) {
               if (i2 == 0) {
                  this.file.write('K');
                  this.file.write(i1);
                  return;
               }

               this.file.write('M');
               this.file.write(i1);
               this.file.write(i2);
               return;
            }

            this.file.write('J');
            this.file.write(i1);
            this.file.write(i2);
            this.file.write(i3);
            this.file.write(i4);
         } else {
            this.file.write('I');
            this.file.write(object.toString());
            this.file.write("\n");
         }

      }

      private void save_bool(PyObject object) {
         int value = ((PyBoolean)object).getValue();
         if (this.protocol >= 2) {
            this.file.write((char)(value != 0 ? '\u0088' : '\u0089'));
         } else {
            this.file.write('I');
            this.file.write(value != 0 ? "01" : "00");
            this.file.write("\n");
         }

      }

      private void save_long(PyObject object) {
         if (this.protocol >= 2) {
            BigInteger integer = ((PyLong)object).getValue();
            if (integer.compareTo(BigInteger.ZERO) == 0) {
               this.file.write('\u008a');
               this.file.write('\u0000');
               return;
            }

            byte[] bytes = integer.toByteArray();
            int l = bytes.length;
            if (l < 256) {
               this.file.write('\u008a');
               this.file.write((char)l);
            } else {
               this.file.write('\u008b');
               this.writeInt4(l);
            }

            for(int i = l - 1; i >= 0; --i) {
               int b = bytes[i] & 255;
               this.file.write((char)b);
            }
         } else {
            this.file.write('L');
            this.file.write(object.toString());
            this.file.write("\n");
         }

      }

      private void writeInt4(int l) {
         char i1 = (char)(l & 255);
         char i2 = (char)(l >>> 8 & 255);
         char i3 = (char)(l >>> 16 & 255);
         char i4 = (char)(l >>> 24 & 255);
         this.file.write(i1);
         this.file.write(i2);
         this.file.write(i3);
         this.file.write(i4);
      }

      private final void save_float(PyObject object) {
         if (this.protocol > 0) {
            this.file.write('G');
            double value = ((PyFloat)object).getValue();
            long bits = Double.doubleToLongBits(value);
            this.file.write((char)((int)(bits >>> 56 & 255L)));
            this.file.write((char)((int)(bits >>> 48 & 255L)));
            this.file.write((char)((int)(bits >>> 40 & 255L)));
            this.file.write((char)((int)(bits >>> 32 & 255L)));
            this.file.write((char)((int)(bits >>> 24 & 255L)));
            this.file.write((char)((int)(bits >>> 16 & 255L)));
            this.file.write((char)((int)(bits >>> 8 & 255L)));
            this.file.write((char)((int)(bits >>> 0 & 255L)));
         } else {
            this.file.write('F');
            this.file.write(object.toString());
            this.file.write("\n");
         }

      }

      private final void save_string(PyObject object) {
         String str = object.toString();
         if (this.protocol > 0) {
            int l = str.length();
            if (l < 256) {
               this.file.write('U');
               this.file.write((char)l);
            } else {
               this.file.write('T');
               this.file.write((char)(l & 255));
               this.file.write((char)(l >>> 8 & 255));
               this.file.write((char)(l >>> 16 & 255));
               this.file.write((char)(l >>> 24 & 255));
            }

            this.file.write(str);
         } else {
            this.file.write('S');
            this.file.write(object.__repr__().toString());
            this.file.write("\n");
         }

         this.put(this.putMemo(get_id(object), object));
      }

      private void save_unicode(PyObject object) {
         if (this.protocol > 0) {
            String str = codecs.PyUnicode_EncodeUTF8(object.toString(), "struct");
            this.file.write('X');
            this.writeInt4(str.length());
            this.file.write(str);
         } else {
            this.file.write('V');
            this.file.write(codecs.PyUnicode_EncodeRawUnicodeEscape(object.toString(), "strict", true));
            this.file.write("\n");
         }

         this.put(this.putMemo(get_id(object), object));
      }

      private void save_tuple(PyObject object) {
         int d = get_id(object);
         int len = object.__len__();
         int m;
         int i;
         if (len > 0 && len <= 3 && this.protocol >= 2) {
            for(m = 0; m < len; ++m) {
               this.save(object.__finditem__(m));
            }

            m = this.getMemoPosition(d, object);
            if (m >= 0) {
               for(i = 0; i < len; ++i) {
                  this.file.write('0');
               }

               this.get(m);
            } else {
               char opcode = (char)(133 + len - 1);
               this.file.write(opcode);
               this.put(this.putMemo(d, object));
            }

         } else {
            this.file.write('(');

            for(m = 0; m < len; ++m) {
               this.save(object.__finditem__(m));
            }

            if (len > 0) {
               m = this.getMemoPosition(d, object);
               if (m >= 0) {
                  if (this.protocol > 0) {
                     this.file.write('1');
                     this.get(m);
                     return;
                  }

                  for(i = 0; i < len + 1; ++i) {
                     this.file.write('0');
                  }

                  this.get(m);
                  return;
               }
            }

            this.file.write('t');
            this.put(this.putMemo(d, object));
         }
      }

      private final void save_empty_tuple(PyObject object) {
         this.file.write(')');
      }

      private void save_list(PyObject object) {
         if (this.protocol > 0) {
            this.file.write(']');
         } else {
            this.file.write('(');
            this.file.write('l');
         }

         this.put(this.putMemo(get_id(object), object));
         this.batch_appends(object);
      }

      private void batch_appends(PyObject object) {
         int countInBatch = 0;
         Iterator var3 = object.asIterable().iterator();

         while(var3.hasNext()) {
            PyObject nextObj = (PyObject)var3.next();
            if (this.protocol == 0) {
               this.save(nextObj);
               this.file.write('a');
            } else {
               if (countInBatch == 0) {
                  this.file.write('(');
               }

               ++countInBatch;
               this.save(nextObj);
               if (countInBatch == 1024) {
                  this.file.write('e');
                  countInBatch = 0;
               }
            }
         }

         if (countInBatch > 0) {
            this.file.write('e');
         }

      }

      private void save_dict(PyObject object) {
         if (this.protocol > 0) {
            this.file.write('}');
         } else {
            this.file.write('(');
            this.file.write('d');
         }

         this.put(this.putMemo(get_id(object), object));
         this.batch_setitems(object.invoke("iteritems"));
      }

      private void batch_setitems(PyObject object) {
         if (this.protocol == 0) {
            Iterator var6 = object.asIterable().iterator();

            while(var6.hasNext()) {
               PyObject p = (PyObject)var6.next();
               if (!(p instanceof PyTuple) || p.__len__() != 2) {
                  throw Py.TypeError("dict items iterator must return 2-tuples");
               }

               this.save(p.__getitem__(0));
               this.save(p.__getitem__(1));
               this.file.write('s');
            }
         } else {
            PyObject[] slice = new PyObject[1024];

            int n;
            do {
               PyObject obj;
               for(n = 0; n < 1024; ++n) {
                  obj = object.__iternext__();
                  if (obj == null) {
                     break;
                  }

                  slice[n] = obj;
               }

               if (n <= 1) {
                  if (n == 1) {
                     obj = slice[0];
                     this.save(obj.__getitem__(0));
                     this.save(obj.__getitem__(1));
                     this.file.write('s');
                  }
               } else {
                  this.file.write('(');

                  for(int i = 0; i < n; ++i) {
                     obj = slice[i];
                     this.save(obj.__getitem__(0));
                     this.save(obj.__getitem__(1));
                  }

                  this.file.write('u');
               }
            } while(n == 1024);
         }

      }

      private final void save_inst(PyInstance object) {
         PyClass cls = object.instclass;
         PySequence args = null;
         PyObject getinitargs = object.__findattr__("__getinitargs__");
         if (getinitargs != null) {
            args = (PySequence)getinitargs.__call__();
            this.keep_alive(args);
         }

         this.file.write('(');
         if (this.protocol > 0) {
            this.save(cls);
         }

         int mid;
         if (args != null) {
            mid = args.__len__();

            for(int i = 0; i < mid; ++i) {
               this.save(args.__finditem__(i));
            }
         }

         mid = this.putMemo(get_id(object), object);
         if (this.protocol > 0) {
            this.file.write('o');
            this.put(mid);
         } else {
            this.file.write('i');
            this.file.write(cls.__findattr__("__module__").toString());
            this.file.write("\n");
            this.file.write(cls.__name__);
            this.file.write("\n");
            this.put(mid);
         }

         PyObject stuff = null;
         PyObject getstate = object.__findattr__("__getstate__");
         if (getstate == null) {
            stuff = object.__dict__;
         } else {
            stuff = getstate.__call__();
            this.keep_alive(stuff);
         }

         this.save(stuff);
         this.file.write('b');
      }

      private final void save_global(PyObject object) {
         this.save_global(object, (PyObject)null);
      }

      private final void save_global(PyObject object, PyObject name) {
         if (name == null) {
            name = object.__findattr__("__name__");
         }

         PyObject module = object.__findattr__("__module__");
         if (module == null || module == Py.None) {
            module = cPickle.whichmodule(object, name);
         }

         if (this.protocol >= 2) {
            PyTuple extKey = new PyTuple(new PyObject[]{module, name});
            PyObject extCode = cPickle.extension_registry.get((PyObject)extKey);
            if (extCode != Py.None) {
               int code = ((PyInteger)extCode).getValue();
               if (code <= 255) {
                  this.file.write('\u0082');
                  this.file.write((char)code);
               } else if (code <= 65535) {
                  this.file.write('\u0083');
                  this.file.write((char)(code & 255));
                  this.file.write((char)(code >> 8));
               } else {
                  this.file.write('\u0084');
                  this.writeInt4(code);
               }

               return;
            }
         }

         this.file.write('c');
         this.file.write(module.toString());
         this.file.write("\n");
         this.file.write(name.toString());
         this.file.write("\n");
         this.put(this.putMemo(get_id(object), object));
      }

      private final int getMemoPosition(int id, Object o) {
         return this.memo.findPosition(id, o);
      }

      private final int putMemo(int id, PyObject object) {
         int memo_len = this.memo.size() + 1;
         this.memo.put(id, memo_len, object);
         return memo_len;
      }

      private final void keep_alive(PyObject obj) {
         int id = System.identityHashCode(this.memo);
         PyList list = (PyList)this.memo.findValue(id, this.memo);
         if (list == null) {
            list = new PyList();
            this.memo.put(id, -1, list);
         }

         list.append(obj);
      }
   }
}
