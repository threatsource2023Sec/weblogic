package org.python.core;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import jnr.posix.util.Platform;
import org.python.antlr.base.mod;
import org.python.core.adapter.ClassicPyObjectAdapter;
import org.python.core.adapter.ExtensiblePyObjectAdapter;
import org.python.google.common.base.CharMatcher;
import org.python.jline.console.UserInterruptException;
import org.python.modules.posix.PosixModule;

public final class Py {
   public static final PyObject None = new PyNone();
   public static final PyObject Ellipsis = new PyEllipsis();
   public static final PyObject NotImplemented = new PyNotImplemented();
   public static final String[] NoKeywords = new String[0];
   public static final PyObject[] EmptyObjects = new PyObject[0];
   public static final PyFrozenSet EmptyFrozenSet = new PyFrozenSet();
   public static final PyTuple EmptyTuple;
   public static final PyInteger Zero;
   public static final PyInteger One;
   public static final PyBoolean False;
   public static final PyBoolean True;
   public static final PyString EmptyString;
   public static final PyUnicode EmptyUnicode;
   public static final PyString Newline;
   public static final PyUnicode UnicodeNewline;
   public static final PyString Space;
   public static final PyUnicode UnicodeSpace;
   public static final long TPFLAGS_HEAPTYPE = 512L;
   public static final long TPFLAGS_BASETYPE = 1024L;
   public static final long TPFLAGS_IS_ABSTRACT = 1048576L;
   public static final Object NoConversion;
   public static PyObject OSError;
   public static PyObject NotImplementedError;
   public static PyObject EnvironmentError;
   public static PyObject OverflowError;
   public static PyObject RuntimeError;
   public static PyObject KeyboardInterrupt;
   public static PyObject FloatingPointError;
   public static PyObject SyntaxError;
   public static PyObject IndentationError;
   public static PyObject TabError;
   public static PyObject AttributeError;
   public static PyObject IOError;
   public static PyObject KeyError;
   public static PyObject AssertionError;
   public static PyObject TypeError;
   public static PyObject ReferenceError;
   public static PyObject SystemError;
   public static PyObject IndexError;
   public static PyObject ZeroDivisionError;
   public static PyObject NameError;
   public static PyObject UnboundLocalError;
   public static PyObject SystemExit;
   public static PyObject StopIteration;
   public static PyObject GeneratorExit;
   public static PyObject ImportError;
   public static PyObject ValueError;
   public static PyObject UnicodeError;
   public static PyObject UnicodeTranslateError;
   public static PyObject UnicodeDecodeError;
   public static PyObject UnicodeEncodeError;
   public static PyObject EOFError;
   public static PyObject MemoryError;
   public static PyObject BufferError;
   public static PyObject ArithmeticError;
   public static PyObject LookupError;
   public static PyObject StandardError;
   public static PyObject Exception;
   public static PyObject BaseException;
   public static PyObject Warning;
   public static PyObject UserWarning;
   public static PyObject DeprecationWarning;
   public static PyObject PendingDeprecationWarning;
   public static PyObject SyntaxWarning;
   public static PyObject RuntimeWarning;
   public static PyObject FutureWarning;
   public static PyObject ImportWarning;
   public static PyObject UnicodeWarning;
   public static PyObject BytesWarning;
   private static PyObject warnings_mod;
   public static PyObject JavaError;
   private static final PyInteger[] integerCache;
   public static volatile PySystemState defaultSystemState;
   private static boolean syspathJavaLoaderRestricted;
   private static final ThreadStateMapping threadStateMapping;
   private static Console console;
   private static final String IMPORT_SITE_ERROR = "Cannot import site module and its dependencies: %s\nDetermine if the following attributes are correct:\n  * sys.path: %s\n    This attribute might be including the wrong directories, such as from CPython\n  * sys.prefix: %s\n    This attribute is set by the system property python.home, although it can\n    be often automatically determined by the location of the Jython jar file\n\nYou can use the -S option or python.import.site=false to not import the site module";
   public static StdoutWrapper stderr;
   static StdoutWrapper stdout;
   private static final PyString[] letters;
   private static ExtensiblePyObjectAdapter adapter;
   private static int nameindex;
   private static IdImpl idimpl;
   public static final int ERROR = -1;
   public static final int WARNING = 0;
   public static final int MESSAGE = 1;
   public static final int COMMENT = 2;
   public static final int DEBUG = 3;
   private static final String JAR_URL_PREFIX = "jar:file:";
   private static final String JAR_SEPARATOR = "!";
   private static final String VFSZIP_PREFIX = "vfszip:";
   private static final String VFS_PREFIX = "vfs:";

   public static PyException OSError(String message) {
      return new PyException(OSError, message);
   }

   public static PyException OSError(IOException ioe) {
      return fromIOException(ioe, OSError);
   }

   public static PyException OSError(Constant errno) {
      int value = errno.intValue();
      PyObject args = new PyTuple(new PyObject[]{newInteger(value), PosixModule.strerror(value)});
      return new PyException(OSError, args);
   }

   public static PyException OSError(Constant errno, PyObject filename) {
      int value = errno.intValue();
      if (Platform.IS_WINDOWS && (value == 20047 || value == Errno.ESRCH.intValue())) {
         value = Errno.EEXIST.intValue();
      }

      PyObject args = new PyTuple(new PyObject[]{newInteger(value), PosixModule.strerror(value), filename});
      return new PyException(OSError, args);
   }

   public static PyException NotImplementedError(String message) {
      return new PyException(NotImplementedError, message);
   }

   public static PyException EnvironmentError(String message) {
      return new PyException(EnvironmentError, message);
   }

   public static PyException OverflowError(String message) {
      return new PyException(OverflowError, message);
   }

   public static PyException RuntimeError(String message) {
      return new PyException(RuntimeError, message);
   }

   public static PyException KeyboardInterrupt(String message) {
      return new PyException(KeyboardInterrupt, message);
   }

   public static PyException FloatingPointError(String message) {
      return new PyException(FloatingPointError, message);
   }

   public static PyException SyntaxError(String message) {
      return new PyException(SyntaxError, message);
   }

   public static PyException AttributeError(String message) {
      return new PyException(AttributeError, message);
   }

   public static PyException IOError(IOException ioe) {
      return fromIOException(ioe, IOError);
   }

   public static PyException IOError(String message) {
      return new PyException(IOError, message);
   }

   public static PyException IOError(Constant errno) {
      int value = errno.intValue();
      PyObject args = new PyTuple(new PyObject[]{newInteger(value), PosixModule.strerror(value)});
      return new PyException(IOError, args);
   }

   public static PyException IOError(Constant errno, String filename) {
      return IOError(errno, (PyObject)fileSystemEncode(filename));
   }

   public static PyException IOError(Constant errno, PyObject filename) {
      int value = errno.intValue();
      PyObject args = new PyTuple(new PyObject[]{newInteger(value), PosixModule.strerror(value), filename});
      return new PyException(IOError, args);
   }

   private static PyException fromIOException(IOException ioe, PyObject err) {
      String message = ioe.getMessage();
      if (message == null) {
         message = ioe.getClass().getName();
      }

      if (ioe instanceof FileNotFoundException) {
         PyTuple args = new PyTuple(new PyObject[]{newInteger(Errno.ENOENT.intValue()), newStringOrUnicode("File not found - " + message)});
         return new PyException(err, args);
      } else {
         return new PyException(err, message);
      }
   }

   public static PyException KeyError(String message) {
      return new PyException(KeyError, message);
   }

   public static PyException KeyError(PyObject key) {
      return new PyException(KeyError, key);
   }

   public static PyException AssertionError(String message) {
      return new PyException(AssertionError, message);
   }

   public static PyException TypeError(String message) {
      return new PyException(TypeError, message);
   }

   public static PyException ReferenceError(String message) {
      return new PyException(ReferenceError, message);
   }

   public static PyException SystemError(String message) {
      return new PyException(SystemError, message);
   }

   public static PyException IndexError(String message) {
      return new PyException(IndexError, message);
   }

   public static PyException ZeroDivisionError(String message) {
      return new PyException(ZeroDivisionError, message);
   }

   public static PyException NameError(String message) {
      return new PyException(NameError, message);
   }

   public static PyException UnboundLocalError(String message) {
      return new PyException(UnboundLocalError, message);
   }

   static void maybeSystemExit(PyException exc) {
      if (exc.match(SystemExit)) {
         PyObject value = exc.value;
         if (PyException.isExceptionInstance(exc.value)) {
            value = value.__findattr__("code");
         }

         getSystemState().callExitFunc();
         if (value instanceof PyInteger) {
            System.exit(((PyInteger)value).getValue());
         } else {
            if (value != None) {
               try {
                  println(value);
                  System.exit(1);
               } catch (Throwable var3) {
               }
            }

            System.exit(0);
         }
      }

   }

   public static PyException StopIteration(String message) {
      return new PyException(StopIteration, message);
   }

   public static PyException GeneratorExit(String message) {
      return new PyException(GeneratorExit, message);
   }

   public static PyException ImportError(String message) {
      return new PyException(ImportError, message);
   }

   public static PyException ValueError(String message) {
      return new PyException(ValueError, message);
   }

   public static PyException UnicodeError(String message) {
      return new PyException(UnicodeError, message);
   }

   public static PyException UnicodeTranslateError(String object, int start, int end, String reason) {
      return new PyException(UnicodeTranslateError, new PyTuple(new PyObject[]{new PyString(object), new PyInteger(start), new PyInteger(end), new PyString(reason)}));
   }

   public static PyException UnicodeDecodeError(String encoding, String object, int start, int end, String reason) {
      return new PyException(UnicodeDecodeError, new PyTuple(new PyObject[]{new PyString(encoding), new PyString(object), new PyInteger(start), new PyInteger(end), new PyString(reason)}));
   }

   public static PyException UnicodeEncodeError(String encoding, String object, int start, int end, String reason) {
      return new PyException(UnicodeEncodeError, new PyTuple(new PyObject[]{new PyString(encoding), new PyUnicode(object), new PyInteger(start), new PyInteger(end), new PyString(reason)}));
   }

   public static PyException EOFError(String message) {
      return new PyException(EOFError, message);
   }

   public static void memory_error(OutOfMemoryError t) {
      if (Options.showJavaExceptions) {
         t.printStackTrace();
      }

   }

   public static PyException MemoryError(String message) {
      return new PyException(MemoryError, message);
   }

   public static PyException BufferError(String message) {
      return new PyException(BufferError, message);
   }

   public static void Warning(String message) {
      warning(Warning, message);
   }

   public static void UserWarning(String message) {
      warning(UserWarning, message);
   }

   public static void DeprecationWarning(String message) {
      warning(DeprecationWarning, message);
   }

   public static void PendingDeprecationWarning(String message) {
      warning(PendingDeprecationWarning, message);
   }

   public static void SyntaxWarning(String message) {
      warning(SyntaxWarning, message);
   }

   public static void RuntimeWarning(String message) {
      warning(RuntimeWarning, message);
   }

   public static void FutureWarning(String message) {
      warning(FutureWarning, message);
   }

   public static void ImportWarning(String message) {
      warning(ImportWarning, message);
   }

   public static void UnicodeWarning(String message) {
      warning(UnicodeWarning, message);
   }

   public static void BytesWarning(String message) {
      warning(BytesWarning, message);
   }

   public static void warnPy3k(String message) {
      warnPy3k(message, 1);
   }

   public static void warnPy3k(String message, int stacklevel) {
      if (Options.py3k_warning) {
         warning(DeprecationWarning, message, stacklevel);
      }

   }

   private static PyObject importWarnings() {
      if (warnings_mod != null) {
         return warnings_mod;
      } else {
         PyObject mod;
         try {
            mod = __builtin__.__import__("warnings");
         } catch (PyException var2) {
            if (var2.match(ImportError)) {
               return null;
            }

            throw var2;
         }

         warnings_mod = mod;
         return mod;
      }
   }

   private static String warn_hcategory(PyObject category) {
      PyObject name = category.__findattr__("__name__");
      return name != null ? "[" + name + "]" : "[warning]";
   }

   public static void warning(PyObject category, String message) {
      warning(category, message, 1);
   }

   public static void warning(PyObject category, String message, int stacklevel) {
      PyObject func = null;
      PyObject mod = importWarnings();
      if (mod != null) {
         func = mod.__getattr__("warn");
      }

      if (func == null) {
         System.err.println(warn_hcategory(category) + ": " + message);
      } else {
         func.__call__((PyObject)newString(message), (PyObject)category, (PyObject)newInteger(stacklevel));
      }
   }

   public static void warning(PyObject category, String message, String filename, int lineno, String module, PyObject registry) {
      PyObject func = null;
      PyObject mod = importWarnings();
      if (mod != null) {
         func = mod.__getattr__("warn_explicit");
      }

      if (func == null) {
         System.err.println(filename + ":" + lineno + ":" + warn_hcategory(category) + ": " + message);
      } else {
         func.__call__(new PyObject[]{newString(message), category, newString(filename), newInteger(lineno), (PyObject)(module == null ? None : newString(module)), registry}, NoKeywords);
      }
   }

   public static PyException JavaError(Throwable t) {
      if (t instanceof PyException) {
         return (PyException)t;
      } else if (t instanceof InvocationTargetException) {
         return JavaError(((InvocationTargetException)t).getTargetException());
      } else if (t instanceof StackOverflowError) {
         return RuntimeError("maximum recursion depth exceeded (Java StackOverflowError)");
      } else {
         if (t instanceof OutOfMemoryError) {
            memory_error((OutOfMemoryError)t);
         } else if (t instanceof UserInterruptException) {
            return KeyboardInterrupt("");
         }

         PyObject exc = PyJavaType.wrapJavaObject(t);
         PyException pyex = new PyException(exc.getType(), exc);
         pyex.initCause(t);
         return pyex;
      }
   }

   private Py() {
   }

   public static Object tojava(PyObject o, Class c) {
      Object obj = o.__tojava__(c);
      if (obj == NoConversion) {
         throw TypeError("can't convert " + o.__repr__() + " to " + c.getName());
      } else {
         return obj;
      }
   }

   public static Object tojava(PyObject o, String s) {
      Class c = findClass(s);
      if (c == null) {
         throw TypeError("can't convert to: " + s);
      } else {
         return tojava(o, c);
      }
   }

   public static final PyInteger newInteger(int i) {
      return i >= -100 && i < 900 ? integerCache[i + 100] : new PyInteger(i);
   }

   public static PyObject newInteger(long i) {
      return (PyObject)(i >= -2147483648L && i <= 2147483647L ? newInteger((int)i) : new PyLong(i));
   }

   public static PyLong newLong(String s) {
      return new PyLong(s);
   }

   public static PyLong newLong(BigInteger i) {
      return new PyLong(i);
   }

   public static PyLong newLong(int i) {
      return new PyLong((long)i);
   }

   public static PyLong newLong(long l) {
      return new PyLong(l);
   }

   public static PyComplex newImaginary(double v) {
      return new PyComplex(0.0, v);
   }

   public static PyFloat newFloat(float v) {
      return new PyFloat((double)v);
   }

   public static PyFloat newFloat(double v) {
      return new PyFloat(v);
   }

   public static PyString newString(char c) {
      return makeCharacter(c);
   }

   public static PyString newString(String s) {
      return new PyString(s);
   }

   public static PyString newStringOrUnicode(String s) {
      return newStringOrUnicode(EmptyString, s);
   }

   public static PyString newStringOrUnicode(PyObject precedent, String s) {
      return (PyString)(!(precedent instanceof PyUnicode) && CharMatcher.ascii().matchesAllOf(s) ? newString(s) : newUnicode(s));
   }

   public static PyString newStringUTF8(String s) {
      return CharMatcher.ascii().matchesAllOf(s) ? newString(s) : newString(codecs.PyUnicode_EncodeUTF8(s, (String)null));
   }

   public static String fileSystemDecode(PyString filename) {
      String s = filename.getString();
      if (!(filename instanceof PyUnicode) && !CharMatcher.ascii().matchesAllOf(s)) {
         assert "utf-8".equals(PySystemState.FILE_SYSTEM_ENCODING.toString());

         return codecs.PyUnicode_DecodeUTF8(s, (String)null);
      } else {
         return s;
      }
   }

   public static String fileSystemDecode(PyObject filename) {
      if (filename instanceof PyString) {
         return fileSystemDecode((PyString)filename);
      } else {
         throw TypeError(String.format("coercing to Unicode: need string, %s type found", filename.getType().fastGetName()));
      }
   }

   public static PyString fileSystemEncode(String filename) {
      if (CharMatcher.ascii().matchesAllOf(filename)) {
         return newString(filename);
      } else {
         assert "utf-8".equals(PySystemState.FILE_SYSTEM_ENCODING.toString());

         return newString(codecs.PyUnicode_EncodeUTF8(filename, (String)null));
      }
   }

   public static PyString fileSystemEncode(PyString filename) {
      return filename instanceof PyUnicode ? fileSystemEncode(filename.getString()) : filename;
   }

   private static List fileSystemDecode(PyList path) {
      List list = new ArrayList(path.__len__());
      Iterator var2 = path.getList().iterator();

      while(var2.hasNext()) {
         PyObject filename = (PyObject)var2.next();
         list.add(fileSystemDecode(filename));
      }

      return list;
   }

   public static PyStringMap newStringMap() {
      if (!PyType.hasBuilder(PyStringMap.class)) {
         BootstrapTypesSingleton.getInstance().add(PyStringMap.class);
      }

      return new PyStringMap();
   }

   public static PyUnicode newUnicode(char c) {
      return (PyUnicode)makeCharacter(c, true);
   }

   static PyObject newUnicode(int codepoint) {
      return makeCharacter(codepoint, true);
   }

   public static PyUnicode newUnicode(String s) {
      return new PyUnicode(s);
   }

   public static PyUnicode newUnicode(String s, boolean isBasic) {
      return new PyUnicode(s, isBasic);
   }

   public static PyBoolean newBoolean(boolean t) {
      return t ? True : False;
   }

   public static PyObject newDate(Date date) {
      if (date == null) {
         return None;
      } else {
         PyObject datetimeModule = __builtin__.__import__("datetime");
         PyObject dateClass = datetimeModule.__getattr__("date");
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         return dateClass.__call__((PyObject)newInteger(cal.get(1)), (PyObject)newInteger(cal.get(2) + 1), (PyObject)newInteger(cal.get(5)));
      }
   }

   public static PyObject newTime(Time time) {
      if (time == null) {
         return None;
      } else {
         PyObject datetimeModule = __builtin__.__import__("datetime");
         PyObject timeClass = datetimeModule.__getattr__("time");
         Calendar cal = Calendar.getInstance();
         cal.setTime(time);
         return timeClass.__call__((PyObject)newInteger(cal.get(11)), newInteger(cal.get(12)), (PyObject)newInteger(cal.get(13)), (PyObject)newInteger(cal.get(14) * 1000));
      }
   }

   public static PyObject newDatetime(Timestamp timestamp) {
      if (timestamp == null) {
         return None;
      } else {
         PyObject datetimeModule = __builtin__.__import__("datetime");
         PyObject datetimeClass = datetimeModule.__getattr__("datetime");
         Calendar cal = Calendar.getInstance();
         cal.setTime(timestamp);
         return datetimeClass.__call__(new PyObject[]{newInteger(cal.get(1)), newInteger(cal.get(2) + 1), newInteger(cal.get(5)), newInteger(cal.get(11)), newInteger(cal.get(12)), newInteger(cal.get(13)), newInteger(timestamp.getNanos() / 1000)});
      }
   }

   public static PyObject newDecimal(String decimal) {
      if (decimal == null) {
         return None;
      } else {
         PyObject decimalModule = __builtin__.__import__("decimal");
         PyObject decimalClass = decimalModule.__getattr__("Decimal");
         return decimalClass.__call__((PyObject)newString(decimal));
      }
   }

   public static PyCode newCode(int argcount, String[] varnames, String filename, String name, boolean args, boolean keywords, PyFunctionTable funcs, int func_id, String[] cellvars, String[] freevars, int npurecell, int moreflags) {
      return new PyTableCode(argcount, varnames, filename, name, 0, args, keywords, funcs, func_id, cellvars, freevars, npurecell, moreflags);
   }

   public static PyCode newCode(int argcount, String[] varnames, String filename, String name, int firstlineno, boolean args, boolean keywords, PyFunctionTable funcs, int func_id, String[] cellvars, String[] freevars, int npurecell, int moreflags) {
      return new PyTableCode(argcount, varnames, filename, name, firstlineno, args, keywords, funcs, func_id, cellvars, freevars, npurecell, moreflags);
   }

   public static PyCode newCode(int argcount, String[] varnames, String filename, String name, boolean args, boolean keywords, PyFunctionTable funcs, int func_id) {
      return new PyTableCode(argcount, varnames, filename, name, 0, args, keywords, funcs, func_id);
   }

   public static PyCode newCode(int argcount, String[] varnames, String filename, String name, int firstlineno, boolean args, boolean keywords, PyFunctionTable funcs, int func_id) {
      return new PyTableCode(argcount, varnames, filename, name, firstlineno, args, keywords, funcs, func_id);
   }

   public static PyCode newJavaCode(Class cls, String name) {
      return new JavaCode(newJavaFunc(cls, name));
   }

   public static PyObject newJavaFunc(Class cls, String name) {
      try {
         Method m = cls.getMethod(name, PyObject[].class, String[].class);
         return new JavaFunc(m);
      } catch (NoSuchMethodException var3) {
         throw JavaError(var3);
      }
   }

   private static PyObject initExc(String name, PyObject exceptions, PyObject dict) {
      PyObject tmp = exceptions.__getattr__(name);
      dict.__setitem__(name, tmp);
      return tmp;
   }

   static void initClassExceptions(PyObject dict) {
      PyObject exc = imp.load("exceptions");
      BaseException = initExc("BaseException", exc, dict);
      Exception = initExc("Exception", exc, dict);
      SystemExit = initExc("SystemExit", exc, dict);
      StopIteration = initExc("StopIteration", exc, dict);
      GeneratorExit = initExc("GeneratorExit", exc, dict);
      StandardError = initExc("StandardError", exc, dict);
      KeyboardInterrupt = initExc("KeyboardInterrupt", exc, dict);
      ImportError = initExc("ImportError", exc, dict);
      EnvironmentError = initExc("EnvironmentError", exc, dict);
      IOError = initExc("IOError", exc, dict);
      OSError = initExc("OSError", exc, dict);
      EOFError = initExc("EOFError", exc, dict);
      RuntimeError = initExc("RuntimeError", exc, dict);
      NotImplementedError = initExc("NotImplementedError", exc, dict);
      NameError = initExc("NameError", exc, dict);
      UnboundLocalError = initExc("UnboundLocalError", exc, dict);
      AttributeError = initExc("AttributeError", exc, dict);
      SyntaxError = initExc("SyntaxError", exc, dict);
      IndentationError = initExc("IndentationError", exc, dict);
      TabError = initExc("TabError", exc, dict);
      TypeError = initExc("TypeError", exc, dict);
      AssertionError = initExc("AssertionError", exc, dict);
      LookupError = initExc("LookupError", exc, dict);
      IndexError = initExc("IndexError", exc, dict);
      KeyError = initExc("KeyError", exc, dict);
      ArithmeticError = initExc("ArithmeticError", exc, dict);
      OverflowError = initExc("OverflowError", exc, dict);
      ZeroDivisionError = initExc("ZeroDivisionError", exc, dict);
      FloatingPointError = initExc("FloatingPointError", exc, dict);
      ValueError = initExc("ValueError", exc, dict);
      UnicodeError = initExc("UnicodeError", exc, dict);
      UnicodeEncodeError = initExc("UnicodeEncodeError", exc, dict);
      UnicodeDecodeError = initExc("UnicodeDecodeError", exc, dict);
      UnicodeTranslateError = initExc("UnicodeTranslateError", exc, dict);
      ReferenceError = initExc("ReferenceError", exc, dict);
      SystemError = initExc("SystemError", exc, dict);
      MemoryError = initExc("MemoryError", exc, dict);
      BufferError = initExc("BufferError", exc, dict);
      Warning = initExc("Warning", exc, dict);
      UserWarning = initExc("UserWarning", exc, dict);
      DeprecationWarning = initExc("DeprecationWarning", exc, dict);
      PendingDeprecationWarning = initExc("PendingDeprecationWarning", exc, dict);
      SyntaxWarning = initExc("SyntaxWarning", exc, dict);
      RuntimeWarning = initExc("RuntimeWarning", exc, dict);
      FutureWarning = initExc("FutureWarning", exc, dict);
      ImportWarning = initExc("ImportWarning", exc, dict);
      UnicodeWarning = initExc("UnicodeWarning", exc, dict);
      BytesWarning = initExc("BytesWarning", exc, dict);
      PyType.fromClass(OutOfMemoryError.class);
   }

   public static synchronized boolean initPython() {
      PySystemState.initialize();
      return true;
   }

   private static Class findClassInternal(String name, String reason) throws ClassNotFoundException {
      ClassLoader classLoader = getSystemState().getClassLoader();
      if (classLoader != null) {
         if (reason != null) {
            writeDebug("import", "trying " + name + " as " + reason + " in sys.classLoader");
         }

         return loadAndInitClass(name, classLoader);
      } else {
         if (!syspathJavaLoaderRestricted) {
            try {
               classLoader = imp.getSyspathJavaLoader();
               if (classLoader != null && reason != null) {
                  writeDebug("import", "trying " + name + " as " + reason + " in SysPathJavaLoader");
               }
            } catch (SecurityException var4) {
               syspathJavaLoaderRestricted = true;
            }
         }

         if (syspathJavaLoaderRestricted) {
            classLoader = imp.getParentClassLoader();
            if (classLoader != null && reason != null) {
               writeDebug("import", "trying " + name + " as " + reason + " in Jython's parent class loader");
            }
         }

         if (classLoader != null) {
            try {
               return loadAndInitClass(name, classLoader);
            } catch (ClassNotFoundException var5) {
            }
         }

         if (reason != null) {
            writeDebug("import", "trying " + name + " as " + reason + " in context class loader, for backwards compatibility");
         }

         return loadAndInitClass(name, Thread.currentThread().getContextClassLoader());
      }
   }

   public static Class findClass(String name) {
      try {
         return findClassInternal(name, (String)null);
      } catch (ClassNotFoundException var2) {
         return null;
      } catch (IllegalArgumentException var3) {
         return null;
      } catch (NoClassDefFoundError var4) {
         return null;
      }
   }

   public static Class findClassEx(String name, String reason) {
      try {
         return findClassInternal(name, reason);
      } catch (ClassNotFoundException var3) {
         return null;
      } catch (IllegalArgumentException var4) {
         throw JavaError(var4);
      } catch (LinkageError var5) {
         throw JavaError(var5);
      }
   }

   private static Class loadAndInitClass(String name, ClassLoader loader) throws ClassNotFoundException {
      return Class.forName(name, true, loader);
   }

   public static void initProxy(PyProxy proxy, String module, String pyclass, Object[] args) {
      if (proxy._getPyInstance() == null) {
         PyObject instance = (PyObject)((PyObject)((Object[])ThreadContext.initializingProxy.get())[0]);
         ThreadState ts = getThreadState();
         if (instance != null) {
            if (JyAttribute.hasAttr(instance, (byte)-128)) {
               throw TypeError("Proxy instance reused");
            } else {
               JyAttribute.setAttr(instance, (byte)-128, proxy);
               proxy._setPyInstance(instance);
               proxy._setPySystemState(ts.getSystemState());
            }
         } else {
            importSiteIfSelected();
            PyObject mod = imp.importName(module.intern(), false);
            PyType pyc = (PyType)mod.__getattr__(pyclass.intern());
            PyObject[] pargs;
            if (args != null && args.length != 0) {
               pargs = javas2pys(args);
            } else {
               pargs = EmptyObjects;
            }

            instance = pyc.__call__(pargs);
            JyAttribute.setAttr(instance, (byte)-128, proxy);
            proxy._setPyInstance(instance);
            proxy._setPySystemState(ts.getSystemState());
         }
      }
   }

   public static void runMain(PyRunnable main, String[] args) throws Exception {
      runMain((CodeBootstrap)(new PyRunnableBootstrap(main)), args);
   }

   public static void runMain(CodeBootstrap main, String[] args) throws Exception {
      PySystemState.initialize((Properties)null, (Properties)null, args, main.getClass().getClassLoader());

      try {
         imp.createFromCode("__main__", CodeLoader.loadCode(main));
      } catch (PyException var3) {
         getSystemState().callExitFunc();
         if (var3.match(SystemExit)) {
            return;
         }

         throw var3;
      }

      getSystemState().callExitFunc();
   }

   private static String getStackTrace(Throwable javaError) {
      CharArrayWriter buf = new CharArrayWriter();
      javaError.printStackTrace(new PrintWriter(buf));
      String str = buf.toString();
      int index = -1;
      if (index == -1) {
         index = str.indexOf("at org.python.core.PyReflectedConstructor.__call__");
      }

      if (index == -1) {
         index = str.indexOf("at org.python.core.PyReflectedFunction.__call__");
      }

      if (index == -1) {
         index = str.indexOf("at org/python/core/PyReflectedConstructor.__call__");
      }

      if (index == -1) {
         index = str.indexOf("at org/python/core/PyReflectedFunction.__call__");
      }

      if (index != -1) {
         index = str.lastIndexOf("\n", index);
      }

      int index0 = str.indexOf("\n");
      if (index >= index0) {
         str = str.substring(index0 + 1, index + 1);
      }

      return str;
   }

   public static void printException(Throwable t) {
      printException(t, (PyFrame)null, (PyObject)null);
   }

   public static void printException(Throwable t, PyFrame f) {
      printException(t, f, (PyObject)null);
   }

   public static synchronized void printException(Throwable t, PyFrame f, PyObject file) {
      StdoutWrapper stderr = Py.stderr;
      if (file != null) {
         stderr = new FixedFileWrapper(file);
      }

      if (Options.showJavaExceptions) {
         ((StdoutWrapper)stderr).println("Java Traceback:");
         CharArrayWriter buf = new CharArrayWriter();
         if (t instanceof PyException) {
            ((PyException)t).super__printStackTrace(new PrintWriter(buf));
         } else {
            t.printStackTrace(new PrintWriter(buf));
         }

         ((StdoutWrapper)stderr).print(buf.toString());
      }

      PyException exc = JavaError(t);
      maybeSystemExit(exc);
      setException(exc, f);
      ThreadState ts = getThreadState();
      PySystemState sys = ts.getSystemState();
      sys.last_value = exc.value;
      sys.last_type = exc.type;
      sys.last_traceback = exc.traceback;
      PyObject exceptHook = sys.__findattr__("excepthook");
      if (exceptHook != null) {
         try {
            exceptHook.__call__((PyObject)exc.type, (PyObject)exc.value, (PyObject)exc.traceback);
         } catch (PyException var9) {
            var9.normalize();
            flushLine();
            ((StdoutWrapper)stderr).println("Error in sys.excepthook:");
            displayException(var9.type, var9.value, var9.traceback, file);
            ((StdoutWrapper)stderr).println();
            ((StdoutWrapper)stderr).println("Original exception was:");
            displayException(exc.type, exc.value, exc.traceback, file);
         }
      } else {
         ((StdoutWrapper)stderr).println("sys.excepthook is missing");
         displayException(exc.type, exc.value, exc.traceback, file);
      }

      ts.exception = null;
   }

   public static void displayException(PyObject type, PyObject value, PyObject tb, PyObject file) {
      StdoutWrapper stderr = Py.stderr;
      String errors = "replace";
      String encoding;
      if (file != null) {
         stderr = new FixedFileWrapper(file);
         encoding = codecs.getDefaultEncoding();
      } else {
         encoding = getAttr(getSystemState().__stderr__, "encoding", (String)null);
      }

      encoding = getAttr(((StdoutWrapper)stderr).myFile(), "encoding", encoding);
      errors = getAttr(((StdoutWrapper)stderr).myFile(), "errors", errors);
      flushLine();

      try {
         PyString bytes = exceptionToBytes(type, value, tb, encoding, errors);
         ((StdoutWrapper)stderr).print((PyObject)bytes);
      } catch (Exception var9) {
         PyObject value = newString("<exception str() failed>");
         PyString bytes = exceptionToBytes(type, value, tb, encoding, errors);
         ((StdoutWrapper)stderr).print((PyObject)bytes);
      }

   }

   private static String getAttr(PyObject target, String internedName, String def) {
      PyObject attr = target.__findattr__(internedName);
      if (attr == null) {
         return def;
      } else {
         return attr instanceof PyUnicode ? ((PyUnicode)attr).getString() : attr.__str__().getString();
      }
   }

   private static PyString exceptionToBytes(PyObject type, PyObject value, PyObject tb, String encoding, String errors) {
      String string = exceptionToString(type, value, tb);

      String bytes;
      try {
         bytes = codecs.encode(newUnicode(string), encoding, errors);
      } catch (Exception var8) {
         bytes = codecs.PyUnicode_EncodeASCII(string, string.length(), "replace");
      }

      return newString(bytes);
   }

   static String exceptionToString(PyObject type, PyObject value, PyObject tb) {
      StringBuilder buf;
      if (tb instanceof PyTraceback) {
         buf = new StringBuilder(((PyTraceback)tb).dumpStack());
      } else {
         buf = new StringBuilder();
      }

      if (__builtin__.isinstance(value, SyntaxError)) {
         appendSyntaxError(buf, value);
         value = value.__findattr__("msg");
         if (value == null) {
            value = None;
         }
      }

      if (value.getJavaProxy() != null) {
         Object javaError = value.__tojava__(Throwable.class);
         if (javaError != null && javaError != NoConversion) {
            buf.append(getStackTrace((Throwable)javaError));
         }
      }

      buf.append(formatException(type, value)).append('\n');
      return buf.toString();
   }

   private static void appendSyntaxError(StringBuilder buf, PyObject value) {
      PyObject filename = value.__findattr__("filename");
      PyObject text = value.__findattr__("text");
      PyObject lineno = value.__findattr__("lineno");
      buf.append("  File \"");
      buf.append(filename != None && filename != null ? filename.toString() : "<string>");
      buf.append("\", line ");
      buf.append(lineno == null ? newString('0') : lineno);
      buf.append('\n');
      if (text != None && text != null && text.__len__() != 0) {
         appendSyntaxErrorText(buf, value.__findattr__("offset").asInt(), text.toString());
      }

   }

   private static void appendSyntaxErrorText(StringBuilder buf, int offset, String text) {
      if (offset >= 0) {
         if (offset > 0 && offset == text.length()) {
            --offset;
         }

         while(true) {
            int i = text.indexOf("\n");
            if (i == -1 || i >= offset) {
               for(i = 0; i < text.length(); ++i) {
                  char c = text.charAt(i);
                  if (c != ' ' && c != '\t') {
                     break;
                  }

                  --offset;
               }

               text = text.substring(i, text.length());
               break;
            }

            offset -= i + 1;
            text = text.substring(i + 1, text.length());
         }
      }

      buf.append("    ");
      buf.append(text);
      if (text.length() == 0 || !text.endsWith("\n")) {
         buf.append('\n');
      }

      if (offset != -1) {
         buf.append("    ");
         --offset;

         while(offset > 0) {
            buf.append(' ');
            --offset;
         }

         buf.append("^\n");
      }
   }

   public static String formatException(PyObject type, PyObject value) {
      return formatException(type, value, false);
   }

   public static String formatException(PyObject type, PyObject value, boolean useRepr) {
      StringBuilder buf = new StringBuilder();
      String className;
      if (PyException.isExceptionClass(type)) {
         className = PyException.exceptionClassName(type);
         int lastDot = className.lastIndexOf(46);
         if (lastDot != -1) {
            className = className.substring(lastDot + 1);
         }

         PyObject moduleName = type.__findattr__("__module__");
         if (moduleName == null) {
            buf.append("<unknown>");
         } else {
            String moduleStr = moduleName.toString();
            if (!moduleStr.equals("exceptions")) {
               buf.append(moduleStr);
               buf.append(".");
            }
         }

         buf.append(className);
      } else {
         buf.append(asMessageString(type, useRepr));
      }

      if (value != null && value != None) {
         className = asMessageString(value, useRepr);
         if (className.length() > 0) {
            buf.append(": ").append(className);
         }
      }

      return buf.toString();
   }

   private static String asMessageString(PyObject value, boolean useRepr) {
      if (useRepr) {
         value = ((PyObject)value).__repr__();
      }

      return value instanceof PyUnicode ? ((PyObject)value).asString() : ((PyObject)value).__str__().getString();
   }

   public static void writeUnraisable(Throwable unraisable, PyObject obj) {
      PyException pye = JavaError(unraisable);
      stderr.println(String.format("Exception %s in %s ignored", formatException(pye.type, pye.value, true), obj));
   }

   public static void assert_(PyObject test, PyObject message) {
      if (!test.__nonzero__()) {
         throw new PyException(AssertionError, message);
      }
   }

   public static void assert_(PyObject test) {
      assert_(test, None);
   }

   public static void addTraceback(Throwable t, PyFrame frame) {
      JavaError(t).tracebackHere(frame, true);
   }

   public static PyException setException(Throwable t, PyFrame frame) {
      PyException pye = JavaError(t);
      pye.normalize();
      pye.tracebackHere(frame);
      getThreadState().exception = pye;
      return pye;
   }

   /** @deprecated */
   @Deprecated
   public static boolean matchException(PyException pye, PyObject exc) {
      return pye.match(exc);
   }

   public static PyException makeException(PyObject type, PyObject value, PyObject traceback) {
      return PyException.doRaise(type, value, traceback);
   }

   public static PyException makeException(PyObject type, PyObject value) {
      return makeException(type, value, (PyObject)null);
   }

   public static PyException makeException(PyObject type) {
      return makeException(type, (PyObject)null);
   }

   public static PyException makeException() {
      return makeException((PyObject)null);
   }

   public static PyObject runCode(PyCode code, PyObject locals, PyObject globals) {
      ThreadState ts = getThreadState();
      if (locals == null || locals == None) {
         if (globals != null && globals != None) {
            locals = globals;
         } else {
            locals = ts.frame.getLocals();
         }
      }

      if (globals != null && globals != None) {
         if (globals.__finditem__("__builtins__") == null) {
            try {
               globals.__setitem__("__builtins__", getSystemState().modules.__finditem__("__builtin__").__getattr__("__dict__"));
            } catch (PyException var6) {
               if (!var6.match(AttributeError)) {
                  throw var6;
               }
            }
         }
      } else {
         globals = ts.frame.f_globals;
      }

      PyBaseCode baseCode = null;
      if (code instanceof PyBaseCode) {
         baseCode = (PyBaseCode)code;
      }

      PyFrame f = new PyFrame(baseCode, locals, globals, getSystemState().getBuiltins());
      return code.call(ts, f);
   }

   public static void exec(PyObject o, PyObject globals, PyObject locals) {
      int flags = 0;
      PyTuple tuple;
      if (o instanceof PyTuple) {
         tuple = (PyTuple)o;
         int len = tuple.__len__();
         if ((globals == null || globals.equals(None)) && (locals == null || locals.equals(None)) && len >= 2 && len <= 3) {
            o = tuple.__getitem__(0);
            globals = tuple.__getitem__(1);
            if (len == 3) {
               locals = tuple.__getitem__(2);
            }
         }
      }

      PyCode code;
      if (o instanceof PyCode) {
         code = (PyCode)o;
         if (locals == null && o instanceof PyBaseCode && ((PyBaseCode)o).hasFreevars()) {
            throw TypeError("code object passed to exec may not contain free variables");
         }
      } else {
         tuple = null;
         String contents;
         if (o instanceof PyString) {
            if (o instanceof PyUnicode) {
               flags |= 256;
            }

            contents = o.toString();
         } else {
            if (!(o instanceof PyFile)) {
               throw TypeError("exec: argument 1 must be string, code or file object");
            }

            PyFile fp = (PyFile)o;
            if (fp.getClosed()) {
               return;
            }

            contents = fp.read().toString();
         }

         code = compile_flags(contents, "<string>", CompileMode.exec, getCompilerFlags(flags, false));
      }

      runCode(code, locals, globals);
   }

   public static final ThreadState getThreadState() {
      return getThreadState((PySystemState)null);
   }

   public static final ThreadState getThreadState(PySystemState newSystemState) {
      return threadStateMapping.getThreadState(newSystemState);
   }

   public static final PySystemState setSystemState(PySystemState newSystemState) {
      ThreadState ts = getThreadState(newSystemState);
      PySystemState oldSystemState = ts.getSystemState();
      if (oldSystemState != newSystemState) {
         ts.setSystemState(newSystemState);
      }

      return oldSystemState;
   }

   public static final PySystemState getSystemState() {
      return getThreadState().getSystemState();
   }

   public static PyFrame getFrame() {
      ThreadState ts = getThreadState();
      return ts == null ? null : ts.frame;
   }

   public static void setFrame(PyFrame f) {
      getThreadState().frame = f;
   }

   public static Console getConsole() {
      if (console == null) {
         try {
            installConsole(new PlainConsole("ascii"));
         } catch (Exception var1) {
            throw RuntimeError("Could not create fall-back PlainConsole: " + var1);
         }
      }

      return console;
   }

   public static void installConsole(Console console) throws UnsupportedOperationException, IOException {
      if (Py.console != null) {
         Py.console.uninstall();
         Py.console = null;
      }

      console.install();
      Py.console = console;
      if (defaultSystemState != null) {
         defaultSystemState.__setattr__("_jy_console", java2py(console));
      }

   }

   public static boolean isInteractive() {
      String isTTY = System.getProperty("python.launcher.tty");
      if (isTTY != null && isTTY.equals("true")) {
         return true;
      } else if (isTTY != null && isTTY.equals("false")) {
         return false;
      } else {
         try {
            POSIX posix = POSIXFactory.getPOSIX();
            FileDescriptor in = FileDescriptor.in;
            return posix.isatty(in);
         } catch (SecurityException var3) {
            return false;
         }
      }
   }

   public static boolean importSiteIfSelected() {
      if (Options.importSite) {
         try {
            imp.load("site");
            return true;
         } catch (PyException var5) {
            if (var5.match(ImportError)) {
               PySystemState sys = getSystemState();
               String value = var5.value.__getattr__("args").__getitem__(0).toString();
               List path = fileSystemDecode(sys.path);
               String prefix = fileSystemDecode(PySystemState.prefix);
               throw ImportError(String.format("Cannot import site module and its dependencies: %s\nDetermine if the following attributes are correct:\n  * sys.path: %s\n    This attribute might be including the wrong directories, such as from CPython\n  * sys.prefix: %s\n    This attribute is set by the system property python.home, although it can\n    be often automatically determined by the location of the Jython jar file\n\nYou can use the -S option or python.import.site=false to not import the site module", value, path, prefix));
            } else {
               throw var5;
            }
         }
      } else {
         return false;
      }
   }

   public static void print(PyObject file, PyObject o) {
      if (file == None) {
         print(o);
      } else {
         (new FixedFileWrapper(file)).print(o);
      }

   }

   public static void printComma(PyObject file, PyObject o) {
      if (file == None) {
         printComma(o);
      } else {
         (new FixedFileWrapper(file)).printComma(o);
      }

   }

   public static void println(PyObject file, PyObject o) {
      if (file == None) {
         println(o);
      } else {
         (new FixedFileWrapper(file)).println(o);
      }

   }

   public static void printlnv(PyObject file) {
      if (file == None) {
         println();
      } else {
         (new FixedFileWrapper(file)).println();
      }

   }

   public static void print(PyObject o) {
      stdout.print(o);
   }

   public static void printComma(PyObject o) {
      stdout.printComma(o);
   }

   public static void println(PyObject o) {
      stdout.println(o);
   }

   public static void println() {
      stdout.println();
   }

   public static void flushLine() {
      stdout.flushLine();
   }

   public static boolean py2boolean(PyObject o) {
      return o.__nonzero__();
   }

   public static byte py2byte(PyObject o) {
      if (o instanceof PyInteger) {
         return (byte)((PyInteger)o).getValue();
      } else {
         Object i = o.__tojava__(Byte.TYPE);
         if (i != null && i != NoConversion) {
            return (Byte)i;
         } else {
            throw TypeError("integer required");
         }
      }
   }

   public static short py2short(PyObject o) {
      if (o instanceof PyInteger) {
         return (short)((PyInteger)o).getValue();
      } else {
         Object i = o.__tojava__(Short.TYPE);
         if (i != null && i != NoConversion) {
            return (Short)i;
         } else {
            throw TypeError("integer required");
         }
      }
   }

   public static int py2int(PyObject o) {
      return py2int(o, "integer required");
   }

   public static int py2int(PyObject o, String msg) {
      if (o instanceof PyInteger) {
         return ((PyInteger)o).getValue();
      } else {
         Object obj = o.__tojava__(Integer.TYPE);
         if (obj == NoConversion) {
            throw TypeError(msg);
         } else {
            return (Integer)obj;
         }
      }
   }

   public static long py2long(PyObject o) {
      if (o instanceof PyInteger) {
         return (long)((PyInteger)o).getValue();
      } else {
         Object i = o.__tojava__(Long.TYPE);
         if (i != null && i != NoConversion) {
            return (Long)i;
         } else {
            throw TypeError("integer required");
         }
      }
   }

   public static float py2float(PyObject o) {
      if (o instanceof PyFloat) {
         return (float)((PyFloat)o).getValue();
      } else if (o instanceof PyInteger) {
         return (float)((PyInteger)o).getValue();
      } else {
         Object i = o.__tojava__(Float.TYPE);
         if (i != null && i != NoConversion) {
            return (Float)i;
         } else {
            throw TypeError("float required");
         }
      }
   }

   public static double py2double(PyObject o) {
      if (o instanceof PyFloat) {
         return ((PyFloat)o).getValue();
      } else if (o instanceof PyInteger) {
         return (double)((PyInteger)o).getValue();
      } else {
         Object i = o.__tojava__(Double.TYPE);
         if (i != null && i != NoConversion) {
            return (Double)i;
         } else {
            throw TypeError("float required");
         }
      }
   }

   public static char py2char(PyObject o) {
      return py2char(o, "char required");
   }

   public static char py2char(PyObject o, String msg) {
      if (o instanceof PyString) {
         PyString s = (PyString)o;
         if (s.__len__() != 1) {
            throw TypeError(msg);
         } else {
            return s.toString().charAt(0);
         }
      } else if (o instanceof PyInteger) {
         return (char)((PyInteger)o).getValue();
      } else {
         Object i = o.__tojava__(Character.TYPE);
         if (i != null && i != NoConversion) {
            return (Character)i;
         } else {
            throw TypeError(msg);
         }
      }
   }

   public static void py2void(PyObject o) {
      if (o != None) {
         throw TypeError("None required for void return");
      }
   }

   public static final PyString makeCharacter(Character o) {
      return makeCharacter(o);
   }

   public static final PyString makeCharacter(char c) {
      return c <= 255 ? letters[c] : new PyString(c);
   }

   static final PyString makeCharacter(int codepoint, boolean toUnicode) {
      if (toUnicode) {
         return new PyUnicode(codepoint);
      } else {
         return codepoint >= 0 && codepoint <= 255 ? letters[codepoint] : new PyString('\uffff');
      }
   }

   public static PyObject java2py(Object o) {
      return getAdapter().adapt(o);
   }

   public static PyObject[] javas2pys(Object... objects) {
      PyObject[] objs = new PyObject[objects.length];

      for(int i = 0; i < objs.length; ++i) {
         objs[i] = java2py(objects[i]);
      }

      return objs;
   }

   public static ExtensiblePyObjectAdapter getAdapter() {
      if (adapter == null) {
         adapter = new ClassicPyObjectAdapter();
      }

      return adapter;
   }

   protected static void setAdapter(ExtensiblePyObjectAdapter adapter) {
      Py.adapter = adapter;
   }

   public static PyObject makeClass(String name, PyObject[] bases, PyCode code) {
      return makeClass(name, bases, code, (PyObject[])null);
   }

   public static PyObject makeClass(String name, PyObject[] bases, PyCode code, PyObject[] closure_cells) {
      ThreadState state = getThreadState();
      PyObject dict = code.call(state, (PyObject[])EmptyObjects, (String[])NoKeywords, state.frame.f_globals, EmptyObjects, new PyTuple(closure_cells));
      return makeClass(name, bases, dict);
   }

   public static PyObject makeClass(String name, PyObject base, PyObject dict) {
      PyObject[] bases = base == null ? EmptyObjects : new PyObject[]{base};
      return makeClass(name, bases, dict);
   }

   public static PyObject makeClass(String name, PyObject[] bases, PyObject dict) {
      PyObject metaclass = dict.__finditem__("__metaclass__");
      if (metaclass == null) {
         PyObject base;
         if (bases.length != 0) {
            base = bases[0];
            metaclass = base.__findattr__("__class__");
            if (metaclass == null) {
               metaclass = base.getType();
            }
         } else {
            base = getFrame().f_globals;
            if (base != null) {
               metaclass = base.__finditem__("__metaclass__");
            }

            if (metaclass == null) {
               metaclass = PyClass.TYPE;
            }
         }
      }

      try {
         return ((PyObject)metaclass).__call__((PyObject)(new PyString(name)), (PyObject)(new PyTuple(bases)), (PyObject)dict);
      } catch (PyException var5) {
         if (!var5.match(TypeError)) {
            throw var5;
         } else {
            var5.value = newString(String.format("Error when calling the metaclass bases\n    %s", var5.value.__str__().toString()));
            throw var5;
         }
      }
   }

   public static synchronized String getName() {
      String name = "org.python.pycode._pyx" + nameindex;
      ++nameindex;
      return name;
   }

   public static CompilerFlags getCompilerFlags() {
      return CompilerFlags.getCompilerFlags();
   }

   public static CompilerFlags getCompilerFlags(int flags, boolean dont_inherit) {
      PyFrame frame;
      if (dont_inherit) {
         frame = null;
      } else {
         frame = getFrame();
      }

      return CompilerFlags.getCompilerFlags(flags, frame);
   }

   public static CompilerFlags getCompilerFlags(CompilerFlags flags, boolean dont_inherit) {
      PyFrame frame;
      if (dont_inherit) {
         frame = null;
      } else {
         frame = getFrame();
      }

      return CompilerFlags.getCompilerFlags(flags, frame);
   }

   public static PyCode compile(InputStream istream, String filename, CompileMode kind) {
      return compile_flags(istream, filename, kind, new CompilerFlags());
   }

   public static PyCode compile_flags(mod node, String name, String filename, boolean linenumbers, boolean printResults, CompilerFlags cflags) {
      return CompilerFacade.compile(node, name, filename, linenumbers, printResults, cflags);
   }

   public static PyCode compile_flags(mod node, String filename, CompileMode kind, CompilerFlags cflags) {
      return compile_flags(node, getName(), filename, true, kind == CompileMode.single, cflags);
   }

   public static PyCode compile_flags(InputStream istream, String filename, CompileMode kind, CompilerFlags cflags) {
      mod node = ParserFacade.parse(istream, kind, filename, cflags);
      return compile_flags(node, filename, kind, cflags);
   }

   public static PyCode compile_flags(String data, String filename, CompileMode kind, CompilerFlags cflags) {
      if (data.contains("\u0000")) {
         throw TypeError("compile() expected string without null bytes");
      } else {
         if (cflags != null && cflags.dont_imply_dedent) {
            data = data + "\n";
         } else {
            data = data + "\n\n";
         }

         mod node = ParserFacade.parse(data, kind, filename, cflags);
         return compile_flags(node, filename, kind, cflags);
      }
   }

   public static PyObject compile_command_flags(String string, String filename, CompileMode kind, CompilerFlags cflags, boolean stdprompt) {
      mod node = ParserFacade.partialParse(string + "\n", kind, filename, cflags, stdprompt);
      return (PyObject)(node == null ? None : compile_flags(node, getName(), filename, true, true, cflags));
   }

   public static PyObject[] unpackSequence(PyObject obj, int length) {
      if (obj instanceof PyTuple && obj.__len__() == length) {
         return ((PyTuple)obj).getArray();
      } else {
         PyObject[] ret = new PyObject[length];
         PyObject iter = obj.__iter__();

         for(int i = 0; i < length; ++i) {
            PyObject tmp = iter.__iternext__();
            if (tmp == null) {
               throw ValueError(String.format("need more than %d value%s to unpack", i, i == 1 ? "" : "s"));
            }

            ret[i] = tmp;
         }

         if (iter.__iternext__() != null) {
            throw ValueError("too many values to unpack");
         } else {
            return ret;
         }
      }
   }

   public static PyObject iter(PyObject seq, String message) {
      try {
         return seq.__iter__();
      } catch (PyException var3) {
         if (var3.match(TypeError)) {
            throw TypeError(message);
         } else {
            throw var3;
         }
      }
   }

   public static long id(PyObject o) {
      return idimpl.id(o);
   }

   public static String idstr(PyObject o) {
      return idimpl.idstr(o);
   }

   public static long java_obj_id(Object o) {
      return idimpl.java_obj_id(o);
   }

   public static void printResult(PyObject ret) {
      getThreadState().getSystemState().invoke("displayhook", ret);
   }

   public static void maybeWrite(String type, String msg, int level) {
      if (level <= Options.verbose) {
         System.err.println(type + ": " + msg);
      }

   }

   public static void writeError(String type, String msg) {
      maybeWrite(type, msg, -1);
   }

   public static void writeWarning(String type, String msg) {
      maybeWrite(type, msg, 0);
   }

   public static void writeMessage(String type, String msg) {
      maybeWrite(type, msg, 1);
   }

   public static void writeComment(String type, String msg) {
      maybeWrite(type, msg, 2);
   }

   public static void writeDebug(String type, String msg) {
      maybeWrite(type, msg, 3);
   }

   public static void saveClassFile(String name, ByteArrayOutputStream bytestream) {
      String dirname = Options.proxyDebugDirectory;
      if (dirname != null) {
         byte[] bytes = bytestream.toByteArray();
         File dir = new File(dirname);
         File file = makeFilename(name, dir);
         (new File(file.getParent())).mkdirs();

         try {
            FileOutputStream o = new FileOutputStream(file);
            o.write(bytes);
            o.close();
         } catch (Throwable var7) {
            var7.printStackTrace();
         }

      }
   }

   private static File makeFilename(String name, File dir) {
      int index = name.indexOf(".");
      return index == -1 ? new File(dir, name + ".class") : makeFilename(name.substring(index + 1, name.length()), new File(dir, name.substring(0, index)));
   }

   public static boolean isInstance(PyObject inst, PyObject cls) {
      if (inst.getType() == cls) {
         return true;
      } else if (cls instanceof PyTuple) {
         Iterator var4 = cls.asIterable().iterator();

         PyObject item;
         do {
            if (!var4.hasNext()) {
               return false;
            }

            item = (PyObject)var4.next();
         } while(!isInstance(inst, item));

         return true;
      } else {
         PyObject checkerResult;
         return (checkerResult = dispatchToChecker(inst, cls, "__instancecheck__")) != null ? checkerResult.__nonzero__() : recursiveIsInstance(inst, cls);
      }
   }

   static boolean recursiveIsInstance(PyObject inst, PyObject cls) {
      if (cls instanceof PyClass && inst instanceof PyInstance) {
         PyClass inClass = ((PyInstance)inst).fastGetClass();
         return inClass.isSubClass((PyClass)cls);
      } else if (cls instanceof PyType) {
         PyType type = (PyType)cls;
         if (inst instanceof PyStringMap && type.equals(PyDictionary.TYPE)) {
            return true;
         } else {
            PyType instType = inst.getType();
            if (instType != type && !instType.isSubType(type)) {
               PyObject instCls = inst.__findattr__("__class__");
               return instCls != null && instCls != instType && instCls instanceof PyType ? ((PyType)instCls).isSubType(type) : false;
            } else {
               return true;
            }
         }
      } else {
         checkClass(cls, "isinstance() arg 2 must be a class, type, or tuple of classes and types");
         PyObject instCls = inst.__findattr__("__class__");
         return instCls == null ? false : abstractIsSubClass(instCls, cls);
      }
   }

   public static boolean isSubClass(PyObject derived, PyObject cls) {
      if (cls instanceof PyTuple) {
         Iterator var4 = cls.asIterable().iterator();

         PyObject item;
         do {
            if (!var4.hasNext()) {
               return false;
            }

            item = (PyObject)var4.next();
         } while(!isSubClass(derived, item));

         return true;
      } else {
         PyObject checkerResult;
         return (checkerResult = dispatchToChecker(derived, cls, "__subclasscheck__")) != null ? checkerResult.__nonzero__() : recursiveIsSubClass(derived, cls);
      }
   }

   static boolean recursiveIsSubClass(PyObject derived, PyObject cls) {
      if (derived instanceof PyType && cls instanceof PyType) {
         if (derived == cls) {
            return true;
         } else {
            PyType type = (PyType)cls;
            PyType subtype = (PyType)derived;
            return type == PyDictionary.TYPE && subtype == PyType.fromClass(PyStringMap.class) ? true : subtype.isSubType(type);
         }
      } else if (derived instanceof PyClass && cls instanceof PyClass) {
         return ((PyClass)derived).isSubClass((PyClass)cls);
      } else {
         checkClass(derived, "issubclass() arg 1 must be a class");
         checkClass(cls, "issubclass() arg 2 must be a class or tuple of classes");
         return abstractIsSubClass(derived, cls);
      }
   }

   private static boolean abstractIsSubClass(PyObject derived, PyObject cls) {
      while(derived != cls) {
         PyTuple bases = abstractGetBases(derived);
         if (bases == null) {
            return false;
         }

         int basesSize = bases.size();
         if (basesSize == 0) {
            return false;
         }

         if (basesSize != 1) {
            Iterator var4 = bases.asIterable().iterator();

            PyObject base;
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               base = (PyObject)var4.next();
            } while(!abstractIsSubClass(base, cls));

            return true;
         }

         derived = bases.pyget(0);
      }

      return true;
   }

   private static PyObject dispatchToChecker(PyObject checkerArg, PyObject cls, String checkerName) {
      if (cls instanceof PyClass) {
         return null;
      } else {
         PyObject checker = cls.getType().__findattr__(checkerName);
         return checker != null && !(checker instanceof PyMethodDescr) && !(checker instanceof PyBuiltinMethodNarrow) ? checker.__call__(cls, checkerArg) : null;
      }
   }

   private static PyTuple abstractGetBases(PyObject cls) {
      PyObject bases = cls.__findattr__("__bases__");
      return bases instanceof PyTuple ? (PyTuple)bases : null;
   }

   private static void checkClass(PyObject cls, String message) {
      if (abstractGetBases(cls) == null) {
         throw TypeError(message);
      }
   }

   static PyObject[] make_array(PyObject iterable) {
      if (iterable instanceof PySequenceList) {
         return ((PySequenceList)iterable).getArray();
      } else {
         int n = 10;
         if (!(iterable instanceof PyGenerator)) {
            try {
               n = iterable.__len__();
            } catch (PyException var5) {
            }
         }

         List objs = new ArrayList(n);
         Iterator var3 = iterable.asIterable().iterator();

         while(var3.hasNext()) {
            PyObject item = (PyObject)var3.next();
            objs.add(item);
         }

         return (PyObject[])objs.toArray(EmptyObjects);
      }
   }

   public static String getDefaultExecutableName() {
      return getDefaultBinDir() + File.separator + (Platform.IS_WINDOWS ? "jython.exe" : "jython");
   }

   public static String getDefaultBinDir() {
      String jar = _getJarFileName();
      if (File.separatorChar != '/') {
         jar = jar.replace('/', File.separatorChar);
      }

      return jar.substring(0, jar.lastIndexOf(File.separatorChar) + 1) + "bin";
   }

   public static String getJarFileName() {
      String jar = _getJarFileName();
      if (File.separatorChar != '/') {
         jar = jar.replace('/', File.separatorChar);
      }

      return jar;
   }

   public static String _getJarFileName() {
      Class thisClass = Py.class;
      String fullClassName = thisClass.getName();
      String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
      URL url = thisClass.getResource(className + ".class");
      return getJarFileNameFromURL(url);
   }

   public static String getJarFileNameFromURL(URL url) {
      String jarFileName = null;
      if (url != null) {
         try {
            String plus = "\\+";
            String escapedPlus = "__ppluss__";
            String rawUrl = url.toString();
            rawUrl = rawUrl.replaceAll("\\+", "__ppluss__");
            String urlString = URLDecoder.decode(rawUrl, "UTF-8");
            urlString = urlString.replaceAll("__ppluss__", "\\+");
            int jarSeparatorIndex = urlString.lastIndexOf("!");
            if (urlString.startsWith("jar:file:") && jarSeparatorIndex > 0) {
               int start = "jar:file:".length();
               if (Platform.IS_WINDOWS) {
                  ++start;
               }

               jarFileName = urlString.substring(start, jarSeparatorIndex);
            } else {
               String path;
               int jarIndex;
               int start;
               if (urlString.startsWith("vfszip:")) {
                  path = Py.class.getName().replace('.', '/');
                  jarIndex = urlString.indexOf(".jar/".concat(path));
                  if (jarIndex > 0) {
                     jarIndex += 4;
                     start = "vfszip:".length();
                     if (Platform.IS_WINDOWS) {
                        ++start;
                     }

                     jarFileName = urlString.substring(start, jarIndex);
                  }
               } else if (urlString.startsWith("vfs:")) {
                  path = Py.class.getName().replace('.', '/');
                  jarIndex = urlString.indexOf(".jar/".concat(path));
                  if (jarIndex > 0) {
                     jarIndex += 4;
                     start = "vfs:".length();
                     if (Platform.IS_WINDOWS) {
                        ++start;
                     }

                     jarFileName = urlString.substring(start, jarIndex);
                  }
               }
            }
         } catch (Exception var10) {
         }
      }

      return jarFileName;
   }

   protected static PyObject ensureInterface(PyObject cls, Class interfce) {
      PyObject pjc = PyType.fromClass(interfce);
      if (isSubClass(cls, pjc)) {
         return cls;
      } else {
         PyObject[] bases = new PyObject[]{cls, pjc};
         return makeClass(interfce.getName(), (PyObject[])bases, (PyObject)(new PyStringMap()));
      }
   }

   public static synchronized PyObject javaPyClass(PyObject cls, Class interfce) {
      py2JyClassCacheItem cacheItem = (py2JyClassCacheItem)JyAttribute.getAttr(cls, (byte)4);
      PyObject result;
      if (cacheItem == null) {
         result = ensureInterface(cls, interfce);
         cacheItem = new py2JyClassCacheItem(interfce, result);
         JyAttribute.setAttr(cls, (byte)4, cacheItem);
      } else {
         result = cacheItem.get(interfce);
         if (result == null) {
            result = ensureInterface(cls, interfce);
            cacheItem.add(interfce, result);
         }
      }

      return result;
   }

   public static Object newJ(PyObject cls, Class jcls, Object... args) {
      PyObject cls2 = javaPyClass(cls, jcls);
      PyObject resultPy = cls2.__call__(javas2pys(args));
      return resultPy.__tojava__(jcls);
   }

   public static Object newJ(PyObject cls, Class jcls, PyObject[] args, String[] keywords) {
      PyObject cls2 = javaPyClass(cls, jcls);
      PyObject resultPy = cls2.__call__(args, keywords);
      return resultPy.__tojava__(jcls);
   }

   public static Object newJ(PyObject cls, Class jcls, String[] keywords, Object... args) {
      PyObject cls2 = javaPyClass(cls, jcls);
      PyObject resultPy = cls2.__call__(javas2pys(args), keywords);
      return resultPy.__tojava__(jcls);
   }

   public static Object newJ(PyModule module, Class jcls, Object... args) {
      PyObject cls = module.__getattr__(jcls.getSimpleName().intern());
      return newJ(cls, jcls, args);
   }

   public static Object newJ(PyModule module, Class jcls, String[] keywords, Object... args) {
      PyObject cls = module.__getattr__(jcls.getSimpleName().intern());
      return newJ(cls, jcls, keywords, args);
   }

   static {
      EmptyTuple = new PyTuple(EmptyObjects);
      Zero = new PyInteger(0);
      One = new PyInteger(1);
      False = new PyBoolean(false);
      True = new PyBoolean(true);
      EmptyString = new PyString("");
      EmptyUnicode = new PyUnicode("");
      Newline = new PyString("\n");
      UnicodeNewline = new PyUnicode("\n");
      Space = new PyString(" ");
      UnicodeSpace = new PyUnicode(" ");
      NoConversion = new PySingleton("Error");
      integerCache = new PyInteger[1000];

      for(int j = -100; j < 900; ++j) {
         integerCache[j + 100] = new PyInteger(j);
      }

      syspathJavaLoaderRestricted = false;
      threadStateMapping = new ThreadStateMapping();
      stderr = new StderrWrapper();
      stdout = new StdoutWrapper();
      letters = new PyString[256];

      for(char j = 0; j < 256; ++j) {
         letters[j] = new PyString(j);
      }

      nameindex = 0;
      idimpl = new IdImpl();
   }

   static class py2JyClassCacheItem {
      List interfaces;
      List pyClasses;

      public py2JyClassCacheItem(Class initClass, PyObject initPyClass) {
         if (!initClass.isInterface()) {
            throw new IllegalArgumentException("cls must be an interface.");
         } else {
            this.interfaces = new ArrayList(1);
            this.pyClasses = new ArrayList(1);
            this.interfaces.add(initClass);
            this.pyClasses.add(initPyClass);
         }
      }

      public PyObject get(Class cls) {
         for(int i = 0; i < this.interfaces.size(); ++i) {
            if (cls.isAssignableFrom((Class)this.interfaces.get(i))) {
               return (PyObject)this.pyClasses.get(i);
            }
         }

         return null;
      }

      public void add(Class cls, PyObject pyCls) {
         if (!cls.isInterface()) {
            throw new IllegalArgumentException("cls must be an interface.");
         } else {
            this.interfaces.add(0, cls);
            this.pyClasses.add(0, pyCls);

            for(int i = this.interfaces.size() - 1; i > 0; --i) {
               if (((Class)this.interfaces.get(i)).isAssignableFrom(cls)) {
                  this.interfaces.remove(i);
                  this.pyClasses.remove(i);
               }
            }

         }
      }
   }

   static class SingletonResolver implements Serializable {
      private String which;

      SingletonResolver(String which) {
         this.which = which;
      }

      private Object readResolve() throws ObjectStreamException {
         if (this.which.equals("None")) {
            return Py.None;
         } else if (this.which.equals("Ellipsis")) {
            return Py.Ellipsis;
         } else if (this.which.equals("NotImplemented")) {
            return Py.NotImplemented;
         } else {
            throw new StreamCorruptedException("unknown singleton: " + this.which);
         }
      }
   }
}
