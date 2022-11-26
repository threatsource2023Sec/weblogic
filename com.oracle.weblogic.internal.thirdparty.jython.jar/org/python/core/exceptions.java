package org.python.core;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.python.modules.zipimport.zipimport;

@Untraversable
public class exceptions extends PyObject implements ClassDictInit {
   public static String __doc__ = "Python's standard exception class hierarchy.\n\nExceptions found here are defined both in the exceptions module and the\nbuilt-in namespace.  It is recommended that user-defined exceptions\ninherit from Exception.  See the documentation for the exception\ninheritance hierarchy.\n";

   public static void classDictInit(PyObject dict) {
      dict.invoke("clear");
      dict.__setitem__((String)"__name__", new PyString("exceptions"));
      dict.__setitem__((String)"__doc__", new PyString(__doc__));
      ThreadState ts = Py.getThreadState();
      if (ts.getSystemState() == null) {
         ts.setSystemState(Py.defaultSystemState);
      }

      PyFrame frame = new PyFrame((PyBaseCode)null, new PyStringMap());
      frame.f_back = ts.frame;
      if (frame.f_builtins == null) {
         if (frame.f_back != null) {
            frame.f_builtins = frame.f_back.f_builtins;
         } else {
            frame.f_builtins = PySystemState.getDefaultBuiltins();
         }
      }

      ts.frame = frame;
      dict.__setitem__((String)"BaseException", PyBaseException.TYPE);
      buildClass(dict, "KeyboardInterrupt", "BaseException", "Program interrupted by user.");
      buildClass(dict, "SystemExit", "BaseException", SystemExit(), "Request to exit from the interpreter.");
      buildClass(dict, "Exception", "BaseException", "Common base class for all non-exit exceptions.");
      buildClass(dict, "StandardError", "Exception", "Base class for all standard Python exceptions that do not represent\ninterpreter exiting.");
      buildClass(dict, "SyntaxError", "StandardError", SyntaxError(), "Invalid syntax.");
      buildClass(dict, "IndentationError", "SyntaxError", "Improper indentation.");
      buildClass(dict, "TabError", "IndentationError", "Improper mixture of spaces and tabs.");
      buildClass(dict, "EnvironmentError", "StandardError", EnvironmentError(), "Base class for I/O related errors.");
      buildClass(dict, "IOError", "EnvironmentError", "I/O operation failed.");
      buildClass(dict, "OSError", "EnvironmentError", "OS system call failed.");
      buildClass(dict, "RuntimeError", "StandardError", "Unspecified run-time error.");
      buildClass(dict, "NotImplementedError", "RuntimeError", "Method or function hasn't been implemented yet.");
      buildClass(dict, "SystemError", "StandardError", "Internal error in the Python interpreter.\n\nPlease report this to the Python maintainer, along with the traceback,\nthe Python version, and the hardware/OS platform and version.");
      buildClass(dict, "ReferenceError", "StandardError", "Weak ref proxy used after referent went away.");
      buildClass(dict, "EOFError", "StandardError", "Read beyond end of file.");
      buildClass(dict, "ImportError", "StandardError", "Import can't find module, or can't find name in module.");
      buildClass(dict, "TypeError", "StandardError", "Inappropriate argument type.");
      buildClass(dict, "ValueError", "StandardError", "Inappropriate argument value (of correct type).");
      buildClass(dict, "UnicodeError", "ValueError", "Unicode related error.");
      buildClass(dict, "UnicodeEncodeError", "UnicodeError", UnicodeEncodeError(), "Unicode encoding error.");
      buildClass(dict, "UnicodeDecodeError", "UnicodeError", UnicodeDecodeError(), "Unicode decoding error.");
      buildClass(dict, "UnicodeTranslateError", "UnicodeError", UnicodeTranslateError(), "Unicode translation error.");
      buildClass(dict, "AssertionError", "StandardError", "Assertion failed.");
      buildClass(dict, "ArithmeticError", "StandardError", "Base class for arithmetic errors.");
      buildClass(dict, "OverflowError", "ArithmeticError", "Result too large to be represented.");
      buildClass(dict, "FloatingPointError", "ArithmeticError", "Floating point operation failed.");
      buildClass(dict, "ZeroDivisionError", "ArithmeticError", "Second argument to a division or modulo operation was zero.");
      buildClass(dict, "LookupError", "StandardError", "Base class for lookup errors.");
      buildClass(dict, "IndexError", "LookupError", "Sequence index out of range.");
      buildClass(dict, "KeyError", "LookupError", KeyError(), "Mapping key not found.");
      buildClass(dict, "AttributeError", "StandardError", "Attribute not found.");
      buildClass(dict, "NameError", "StandardError", "Name not found globally.");
      buildClass(dict, "UnboundLocalError", "NameError", "Local name referenced but not bound to a value.");
      buildClass(dict, "MemoryError", "StandardError", "Out of memory.");
      buildClass(dict, "BufferError", "StandardError", "Buffer error.");
      buildClass(dict, "StopIteration", "Exception", "Signal the end from iterator.next().");
      buildClass(dict, "GeneratorExit", "BaseException", "Request that a generator exit.");
      buildClass(dict, "Warning", "Exception", "Base class for warning categories.");
      buildClass(dict, "UserWarning", "Warning", "Base class for warnings generated by user code.");
      buildClass(dict, "DeprecationWarning", "Warning", "Base class for warnings about deprecated features.");
      buildClass(dict, "PendingDeprecationWarning", "Warning", "Base class for warnings about features which will be deprecated\nin the future.");
      buildClass(dict, "SyntaxWarning", "Warning", "Base class for warnings about dubious syntax.");
      buildClass(dict, "RuntimeWarning", "Warning", "Base class for warnings about dubious runtime behavior.");
      buildClass(dict, "FutureWarning", "Warning", "Base class for warnings about constructs that will change semantically\nin the future.");
      buildClass(dict, "ImportWarning", "Warning", "Base class for warnings about probable mistakes in module imports");
      buildClass(dict, "UnicodeWarning", "Warning", "Base class for warnings about Unicode related problems, mostly\nrelated to conversion problems.");
      buildClass(dict, "BytesWarning", "Warning", "Base class for warnings about bytes and buffer related problems, mostly\nrelated to conversion from str or comparing to str.");
      zipimport.initClassExceptions(dict);
      ts.frame = ts.frame.f_back;
   }

   public static PyObject SyntaxError() {
      PyObject __dict__ = new PyStringMap();
      defineSlots(__dict__, "msg", "filename", "lineno", "offset", "text", "print_file_and_line");
      __dict__.__setitem__("__init__", bindStaticJavaMethod("__init__", "SyntaxError__init__"));
      __dict__.__setitem__("__str__", bindStaticJavaMethod("__str__", "SyntaxError__str__"));
      return __dict__;
   }

   public static void SyntaxError__init__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException.TYPE.invoke("__init__", self, args, kwargs);
      initSlots(self);
      if (args.length >= 1) {
         self.__setattr__("msg", args[0]);
      }

      if (args.length == 2) {
         PyObject[] info = Py.make_array(args[1]);
         if (info.length != 4) {
            throw Py.IndexError("tuple index out of range");
         }

         self.__setattr__("filename", info[0]);
         self.__setattr__("lineno", info[1]);
         self.__setattr__("offset", info[2]);
         self.__setattr__("text", info[3]);
      }

   }

   public static PyString SyntaxError__str__(PyObject self, PyObject[] arg, String[] kwargs) {
      PyObject msg = self.__getattr__("msg");
      PyObject str = msg.__str__();
      if (!(msg instanceof PyString)) {
         return Py.newString(str.toString());
      } else {
         PyObject filename = self.__findattr__("filename");
         PyObject lineno = self.__findattr__("lineno");
         boolean haveFilename = filename instanceof PyString;
         boolean haveLieno = lineno instanceof PyInteger;
         if (!haveFilename && !haveLieno) {
            return (PyString)str;
         } else {
            String result;
            if (haveFilename && haveLieno) {
               result = String.format("%s (%s, line %d)", str, basename(filename.toString()), lineno.asInt());
            } else if (haveFilename) {
               result = String.format("%s (%s)", str, basename(filename.toString()));
            } else {
               result = String.format("%s (line %d)", str, lineno.asInt());
            }

            return Py.newString(result);
         }
      }
   }

   public static PyObject EnvironmentError() {
      PyObject dict = new PyStringMap();
      defineSlots(dict, "errno", "strerror", "filename");
      dict.__setitem__("__init__", bindStaticJavaMethod("__init__", "EnvironmentError__init__"));
      dict.__setitem__("__str__", bindStaticJavaMethod("__str__", "EnvironmentError__str__"));
      dict.__setitem__("__reduce__", bindStaticJavaMethod("__reduce__", "EnvironmentError__reduce__"));
      return dict;
   }

   public static void EnvironmentError__init__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException.TYPE.invoke("__init__", self, args, kwargs);
      initSlots(self);
      if (args.length > 1 && args.length <= 3) {
         PyObject errno = args[0];
         PyObject strerror = args[1];
         self.__setattr__("errno", errno);
         self.__setattr__("strerror", strerror);
         if (args.length == 3) {
            self.__setattr__("filename", args[2]);
            self.__setattr__((String)"args", new PyTuple(new PyObject[]{errno, strerror}));
         }

      }
   }

   public static PyObject EnvironmentError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      PyObject errno = self.__findattr__("errno");
      PyObject strerror = self.__findattr__("strerror");
      PyObject filename = self.__findattr__("filename");
      PyString result;
      if (filename.__nonzero__()) {
         result = Py.newString("[Errno %s] %s: %s");
         result = (PyString)result.__mod__(new PyTuple(new PyObject[]{errno, strerror, filename.__repr__()}));
      } else {
         if (!errno.__nonzero__() || !strerror.__nonzero__()) {
            return PyBaseException.TYPE.invoke("__str__", self, args, kwargs);
         }

         result = Py.newString("[Errno %s] %s");
         result = (PyString)result.__mod__(new PyTuple(new PyObject[]{errno, strerror}));
      }

      return result;
   }

   public static PyObject EnvironmentError__reduce__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException selfBase = (PyBaseException)self;
      PyObject reduceArgs = selfBase.args;
      PyObject filename = self.__findattr__("filename");
      if (selfBase.args.__len__() == 2 && filename != null) {
         reduceArgs = new PyTuple(new PyObject[]{selfBase.args.__finditem__(0), selfBase.args.__finditem__(1), filename});
      }

      return selfBase.__dict__ != null ? new PyTuple(new PyObject[]{selfBase.getType(), (PyObject)reduceArgs, selfBase.__dict__}) : new PyTuple(new PyObject[]{selfBase.getType(), (PyObject)reduceArgs});
   }

   public static PyObject SystemExit() {
      PyObject dict = new PyStringMap();
      defineSlots(dict, "code");
      dict.__setitem__("__init__", bindStaticJavaMethod("__init__", "SystemExit__init__"));
      return dict;
   }

   public static void SystemExit__init__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException.TYPE.invoke("__init__", self, args, kwargs);
      initSlots(self);
      if (args.length == 1) {
         self.__setattr__("code", args[0]);
      } else if (args.length > 1) {
         self.__setattr__((String)"code", new PyTuple(args));
      }

   }

   public static PyObject KeyError() {
      PyObject dict = new PyStringMap();
      dict.__setitem__("__str__", bindStaticJavaMethod("__str__", "KeyError__str__"));
      return dict;
   }

   public static PyObject KeyError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException selfBase = (PyBaseException)self;
      return (PyObject)(selfBase.args.__len__() == 1 ? selfBase.args.__getitem__(0).__repr__() : PyBaseException.TYPE.invoke("__str__", self, args, kwargs));
   }

   public static PyObject UnicodeError() {
      PyObject dict = new PyStringMap();
      defineSlots(dict, "encoding", "object", "start", "end", "reason");
      return dict;
   }

   public static void UnicodeError__init__(PyObject self, PyObject[] args, String[] kwargs, PyType objectType) {
      ArgParser ap = new ArgParser("__init__", args, kwargs, new String[]{"encoding", "object", "start", "end", "reason"}, 5);
      self.__setattr__("encoding", ap.getPyObjectByType(0, PyString.TYPE));
      self.__setattr__("object", ap.getPyObjectByType(1, objectType));
      self.__setattr__("start", ap.getPyObjectByType(2, PyInteger.TYPE));
      self.__setattr__("end", ap.getPyObjectByType(3, PyInteger.TYPE));
      self.__setattr__("reason", ap.getPyObjectByType(4, PyString.TYPE));
   }

   public static PyObject UnicodeDecodeError() {
      PyObject dict = new PyStringMap();
      dict.__setitem__("__init__", bindStaticJavaMethod("__init__", "UnicodeDecodeError__init__"));
      dict.__setitem__("__str__", bindStaticJavaMethod("__str__", "UnicodeDecodeError__str__"));
      return dict;
   }

   public static void UnicodeDecodeError__init__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException.TYPE.invoke("__init__", self, args, kwargs);
      UnicodeError__init__(self, args, kwargs, PyString.TYPE);
   }

   public static PyString UnicodeDecodeError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      int start = self.__getattr__("start").asInt();
      int end = self.__getattr__("end").asInt();
      PyObject reason = self.__getattr__("reason").__str__();
      PyObject encoding = self.__getattr__("encoding").__str__();
      PyObject object = getString(self.__getattr__("object"), "object");
      String result;
      if (start < object.__len__() && end == start + 1) {
         int badByte = object.toString().charAt(start) & 255;
         result = String.format("'%.400s' codec can't decode byte 0x%x in position %d: %.400s", encoding, badByte, start, reason);
      } else {
         result = String.format("'%.400s' codec can't decode bytes in position %d-%d: %.400s", encoding, start, end - 1, reason);
      }

      return Py.newString(result);
   }

   public static PyObject UnicodeEncodeError() {
      PyObject dict = new PyStringMap();
      dict.__setitem__("__init__", bindStaticJavaMethod("__init__", "UnicodeEncodeError__init__"));
      dict.__setitem__("__str__", bindStaticJavaMethod("__str__", "UnicodeEncodeError__str__"));
      return dict;
   }

   public static void UnicodeEncodeError__init__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException.TYPE.invoke("__init__", self, args, kwargs);
      UnicodeError__init__(self, args, kwargs, PyUnicode.TYPE);
   }

   public static PyString UnicodeEncodeError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      int start = self.__getattr__("start").asInt();
      int end = self.__getattr__("end").asInt();
      PyObject reason = self.__getattr__("reason").__str__();
      PyObject encoding = self.__getattr__("encoding").__str__();
      PyObject object = getUnicode(self.__getattr__("object"), "object");
      String result;
      if (start < object.__len__() && end == start + 1) {
         int badchar = object.toString().codePointAt(start);
         String badcharStr;
         if (badchar <= 255) {
            badcharStr = String.format("x%02x", badchar);
         } else if (badchar <= 65535) {
            badcharStr = String.format("u%04x", badchar);
         } else {
            badcharStr = String.format("U%08x", badchar);
         }

         result = String.format("'%.400s' codec can't encode character u'\\%s' in position %d: %.400s", encoding, badcharStr, start, reason);
      } else {
         result = String.format("'%.400s' codec can't encode characters in position %d-%d: %.400s", encoding, start, end - 1, reason);
      }

      return Py.newString(result);
   }

   public static PyObject UnicodeTranslateError() {
      PyObject dict = new PyStringMap();
      dict.__setitem__("__init__", bindStaticJavaMethod("__init__", "UnicodeTranslateError__init__"));
      dict.__setitem__("__str__", bindStaticJavaMethod("__str__", "UnicodeTranslateError__str__"));
      return dict;
   }

   public static void UnicodeTranslateError__init__(PyObject self, PyObject[] args, String[] kwargs) {
      PyBaseException.TYPE.invoke("__init__", self, args, kwargs);
      ArgParser ap = new ArgParser("__init__", args, kwargs, new String[]{"object", "start", "end", "reason"}, 4);
      self.__setattr__("object", ap.getPyObjectByType(0, PyUnicode.TYPE));
      self.__setattr__("start", ap.getPyObjectByType(1, PyInteger.TYPE));
      self.__setattr__("end", ap.getPyObjectByType(2, PyInteger.TYPE));
      self.__setattr__("reason", ap.getPyObjectByType(3, PyString.TYPE));
   }

   public static PyString UnicodeTranslateError__str__(PyObject self, PyObject[] args, String[] kwargs) {
      int start = self.__getattr__("start").asInt();
      int end = self.__getattr__("end").asInt();
      PyObject reason = self.__getattr__("reason").__str__();
      PyObject object = getUnicode(self.__getattr__("object"), "object");
      String result;
      if (start < object.__len__() && end == start + 1) {
         int badchar = object.toString().codePointAt(start);
         String badCharStr;
         if (badchar <= 255) {
            badCharStr = String.format("x%02x", badchar);
         } else if (badchar <= 65535) {
            badCharStr = String.format("u%04x", badchar);
         } else {
            badCharStr = String.format("U%08x", badchar);
         }

         result = String.format("can't translate character u'\\%s' in position %d: %.400s", badCharStr, start, reason);
      } else {
         result = String.format("can't translate characters in position %d-%d: %.400s", start, end - 1, reason);
      }

      return Py.newString(result);
   }

   public static int getStart(PyObject self, boolean unicode) {
      int start = self.__getattr__("start").asInt();
      Object object;
      if (unicode) {
         object = getUnicode(self.__getattr__("object"), "object");
      } else {
         object = getString(self.__getattr__("object"), "object");
      }

      if (start < 0) {
         start = 0;
      }

      if (start >= ((PyObject)object).__len__()) {
         start = ((PyObject)object).__len__() - 1;
      }

      return start;
   }

   public static int getEnd(PyObject self, boolean unicode) {
      int end = self.__getattr__("end").asInt();
      Object object;
      if (unicode) {
         object = getUnicode(self.__getattr__("object"), "object");
      } else {
         object = getString(self.__getattr__("object"), "object");
      }

      if (end < 1) {
         end = 1;
      }

      if (end > ((PyObject)object).__len__()) {
         end = ((PyObject)object).__len__();
      }

      return end;
   }

   public static PyString getString(PyObject attr, String name) {
      if (!Py.isInstance(attr, PyString.TYPE)) {
         throw Py.TypeError(String.format("%.200s attribute must be str", name));
      } else {
         return (PyString)attr;
      }
   }

   public static PyUnicode getUnicode(PyObject attr, String name) {
      if (!(attr instanceof PyUnicode)) {
         throw Py.TypeError(String.format("%.200s attribute must be unicode", name));
      } else {
         return (PyUnicode)attr;
      }
   }

   private static String basename(String name) {
      int lastSep = name.lastIndexOf(File.separatorChar);
      return lastSep > -1 ? name.substring(lastSep + 1, name.length()) : name;
   }

   private static void defineSlots(PyObject dict, String... slotNames) {
      PyObject[] slots = new PyObject[slotNames.length];

      for(int i = 0; i < slotNames.length; ++i) {
         slots[i] = Py.newString(slotNames[i]);
      }

      dict.__setitem__((String)"__slots__", new PyTuple(slots));
   }

   private static void initSlots(PyObject self) {
      Iterator var1 = self.__findattr__("__slots__").asIterable().iterator();

      while(var1.hasNext()) {
         PyObject name = (PyObject)var1.next();
         if (name instanceof PyString) {
            self.__setattr__((PyString)name, Py.None);
         }
      }

   }

   private static PyObject buildClass(PyObject dict, String classname, String superclass, String doc) {
      return buildClass(dict, classname, superclass, new PyStringMap(), doc);
   }

   private static PyObject buildClass(PyObject dict, String classname, String superclass, PyObject classDict, String doc) {
      classDict.__setitem__((String)"__doc__", Py.newString(doc));
      PyType type = (PyType)Py.makeClass("exceptions." + classname, dict.__finditem__(superclass), classDict);
      type.builtin = true;
      dict.__setitem__((String)classname, type);
      return type;
   }

   public static PyObject bindStaticJavaMethod(String name, String methodName) {
      return bindStaticJavaMethod(name, exceptions.class, methodName);
   }

   public static PyObject bindStaticJavaMethod(String name, Class cls, String methodName) {
      Method javaMethod;
      try {
         javaMethod = cls.getMethod(methodName, PyObject.class, PyObject[].class, String[].class);
      } catch (Exception var5) {
         throw Py.JavaError(var5);
      }

      return new BoundStaticJavaMethod(name, javaMethod);
   }

   @Untraversable
   static class BoundStaticJavaMethod extends PyBuiltinMethod {
      private Method javaMethod;

      public BoundStaticJavaMethod(String name, Method javaMethod) {
         super(name);
         this.javaMethod = javaMethod;
      }

      protected BoundStaticJavaMethod(PyType type, PyObject self, PyBuiltinCallable.Info info, Method javaMethod) {
         super(type, self, info);
         this.javaMethod = javaMethod;
      }

      public PyBuiltinCallable bind(PyObject self) {
         return new BoundStaticJavaMethod(this.getType(), self, this.info, this.javaMethod);
      }

      public PyObject __get__(PyObject obj, PyObject type) {
         return (PyObject)(obj != null ? this.bind(obj) : this.makeDescriptor((PyType)type));
      }

      public PyObject __call__(PyObject[] args, String[] kwargs) {
         try {
            return Py.java2py(this.javaMethod.invoke((Object)null, this.self, args, kwargs));
         } catch (Throwable var4) {
            throw Py.JavaError(var4);
         }
      }
   }
}
