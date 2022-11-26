package org.python.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Properties;
import org.python.antlr.base.mod;
import org.python.core.CompileMode;
import org.python.core.CompilerFlags;
import org.python.core.ParserFacade;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyFileReader;
import org.python.core.PyFileWriter;
import org.python.core.PyModule;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.__builtin__;

public class PythonInterpreter implements AutoCloseable, Closeable {
   protected PySystemState systemState;
   PyObject globals;
   protected final boolean useThreadLocalState;
   protected static ThreadLocal threadLocals = new ThreadLocal() {
      protected Object[] initialValue() {
         return new Object[1];
      }
   };
   protected CompilerFlags cflags;
   private volatile boolean closed;

   public static void initialize(Properties preProperties, Properties postProperties, String[] argv) {
      PySystemState.initialize(preProperties, postProperties, argv);
   }

   public PythonInterpreter() {
      this((PyObject)null, (PySystemState)null);
   }

   public static PythonInterpreter threadLocalStateInterpreter(PyObject dict) {
      return new PythonInterpreter(dict, new PySystemState(), true);
   }

   public PythonInterpreter(PyObject dict) {
      this(dict, (PySystemState)null);
   }

   public PythonInterpreter(PyObject dict, PySystemState systemState) {
      this(dict, systemState, false);
   }

   protected PythonInterpreter(PyObject dict, PySystemState systemState, boolean useThreadLocalState) {
      this.cflags = new CompilerFlags();
      this.closed = false;
      if (dict == null) {
         dict = Py.newStringMap();
      }

      this.globals = (PyObject)dict;
      if (systemState == null) {
         systemState = Py.getSystemState();
      }

      this.systemState = systemState;
      this.setSystemState();
      this.useThreadLocalState = useThreadLocalState;
      if (!useThreadLocalState) {
         PyModule module = new PyModule("__main__", (PyObject)dict);
         systemState.modules.__setitem__((String)"__main__", module);
      }

      Py.importSiteIfSelected();
   }

   public PySystemState getSystemState() {
      return this.systemState;
   }

   protected void setSystemState() {
      Py.setSystemState(this.getSystemState());
   }

   public void setIn(PyObject inStream) {
      this.getSystemState().stdin = inStream;
   }

   public void setIn(Reader inStream) {
      this.setIn((PyObject)(new PyFileReader(inStream)));
   }

   public void setIn(InputStream inStream) {
      this.setIn((PyObject)(new PyFile(inStream)));
   }

   public void setOut(PyObject outStream) {
      this.getSystemState().stdout = outStream;
   }

   public void setOut(Writer outStream) {
      this.setOut((PyObject)(new PyFileWriter(outStream)));
   }

   public void setOut(OutputStream outStream) {
      this.setOut((PyObject)(new PyFile(outStream)));
   }

   public void setErr(PyObject outStream) {
      this.getSystemState().stderr = outStream;
   }

   public void setErr(Writer outStream) {
      this.setErr((PyObject)(new PyFileWriter(outStream)));
   }

   public void setErr(OutputStream outStream) {
      this.setErr((PyObject)(new PyFile(outStream)));
   }

   public PyObject eval(String s) {
      this.setSystemState();
      return __builtin__.eval(new PyString(s), this.getLocals());
   }

   public PyObject eval(PyObject code) {
      this.setSystemState();
      return __builtin__.eval(code, this.getLocals());
   }

   public void exec(String s) {
      this.setSystemState();
      Py.exec(Py.compile_flags(s, "<string>", CompileMode.exec, this.cflags), this.getLocals(), (PyObject)null);
      Py.flushLine();
   }

   public void exec(PyObject code) {
      this.setSystemState();
      Py.exec(code, this.getLocals(), (PyObject)null);
      Py.flushLine();
   }

   public void execfile(String filename) {
      PyObject locals = this.getLocals();
      this.setSystemState();
      __builtin__.execfile_flags(filename, locals, locals, this.cflags);
      Py.flushLine();
   }

   public void execfile(InputStream s) {
      this.execfile(s, "<iostream>");
   }

   public void execfile(InputStream s, String name) {
      this.setSystemState();
      Py.runCode(Py.compile_flags(s, name, CompileMode.exec, this.cflags), (PyObject)null, this.getLocals());
      Py.flushLine();
   }

   public PyCode compile(String script) {
      return this.compile(script, "<script>");
   }

   public PyCode compile(Reader reader) {
      return this.compile(reader, "<script>");
   }

   public PyCode compile(String script, String filename) {
      return this.compile((Reader)(new StringReader(script)), filename);
   }

   public PyCode compile(Reader reader, String filename) {
      mod node = ParserFacade.parseExpressionOrModule(reader, filename, this.cflags);
      this.setSystemState();
      return Py.compile_flags(node, filename, CompileMode.eval, this.cflags);
   }

   public PyObject getLocals() {
      if (!this.useThreadLocalState) {
         return this.globals;
      } else {
         PyObject locals = (PyObject)((Object[])threadLocals.get())[0];
         return locals != null ? locals : this.globals;
      }
   }

   public void setLocals(PyObject d) {
      if (!this.useThreadLocalState) {
         this.globals = d;
      } else {
         ((Object[])threadLocals.get())[0] = d;
      }

   }

   public void set(String name, Object value) {
      this.getLocals().__setitem__(name.intern(), Py.java2py(value));
   }

   public void set(String name, PyObject value) {
      this.getLocals().__setitem__(name.intern(), value);
   }

   public PyObject get(String name) {
      return this.getLocals().__finditem__(name.intern());
   }

   public Object get(String name, Class javaclass) {
      PyObject val = this.getLocals().__finditem__(name.intern());
      return val == null ? null : Py.tojava(val, javaclass);
   }

   public void cleanup() {
      this.setSystemState();
      PySystemState sys = Py.getSystemState();
      sys.callExitFunc();

      try {
         sys.stdout.invoke("flush");
      } catch (PyException var4) {
      }

      try {
         sys.stderr.invoke("flush");
      } catch (PyException var3) {
      }

      threadLocals.remove();
      sys.cleanup();
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         this.cleanup();
      }

   }
}
