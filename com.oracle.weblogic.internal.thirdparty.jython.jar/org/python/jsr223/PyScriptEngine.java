package org.python.jsr223;

import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyModule;
import org.python.core.PyObject;
import org.python.core.PyTraceback;
import org.python.core.__builtin__;
import org.python.util.PythonInterpreter;

public class PyScriptEngine extends AbstractScriptEngine implements Compilable, Invocable, AutoCloseable {
   private final PythonInterpreter interp;
   private final ScriptEngineFactory factory;

   PyScriptEngine(ScriptEngineFactory factory) {
      this.factory = factory;
      this.interp = PythonInterpreter.threadLocalStateInterpreter(new PyScriptEngineScope(this, this.context));
   }

   public Object eval(String script, ScriptContext context) throws ScriptException {
      return this.eval(this.compileScript(script, context), context);
   }

   private Object eval(PyCode code, ScriptContext context) throws ScriptException {
      try {
         this.interp.setIn(context.getReader());
         this.interp.setOut(context.getWriter());
         this.interp.setErr(context.getErrorWriter());
         this.interp.setLocals(new PyScriptEngineScope(this, context));
         String filename = (String)context.getAttribute("javax.script.filename");
         String[] argv = (String[])((String[])context.getAttribute("javax.script.argv"));
         if (argv != null || filename != null) {
            PyList pyargv = new PyList();
            if (filename != null) {
               pyargv.append(Py.java2py(filename));
            }

            if (argv != null) {
               for(int i = 0; i < argv.length; ++i) {
                  pyargv.append(Py.java2py(argv[i]));
               }
            }

            this.interp.getSystemState().argv = pyargv;
         }

         return this.interp.eval((PyObject)code).__tojava__(Object.class);
      } catch (PyException var7) {
         throw scriptException(var7);
      }
   }

   public Object eval(Reader reader, ScriptContext context) throws ScriptException {
      return this.eval(this.compileScript(reader, context), context);
   }

   public Bindings createBindings() {
      return new SimpleBindings();
   }

   public ScriptEngineFactory getFactory() {
      return this.factory;
   }

   public CompiledScript compile(String script) throws ScriptException {
      return new PyCompiledScript(this.compileScript(script, this.context));
   }

   public CompiledScript compile(Reader reader) throws ScriptException {
      return new PyCompiledScript(this.compileScript(reader, this.context));
   }

   private PyCode compileScript(String script, ScriptContext context) throws ScriptException {
      try {
         String filename = (String)context.getAttribute("javax.script.filename");
         if (filename == null) {
            return this.interp.compile(script);
         } else {
            this.interp.getLocals().__setitem__((PyObject)Py.newString("__file__"), Py.newString(filename));
            return this.interp.compile(script, filename);
         }
      } catch (PyException var4) {
         throw scriptException(var4);
      }
   }

   private PyCode compileScript(Reader reader, ScriptContext context) throws ScriptException {
      try {
         String filename = (String)context.getAttribute("javax.script.filename");
         if (filename == null) {
            return this.interp.compile(reader);
         } else {
            this.interp.getLocals().__setitem__((PyObject)Py.newString("__file__"), Py.newString(filename));
            return this.interp.compile(reader, filename);
         }
      } catch (PyException var4) {
         throw scriptException(var4);
      }
   }

   public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
      try {
         this.interp.setLocals(new PyScriptEngineScope(this, this.context));
         if (!(thiz instanceof PyObject)) {
            thiz = Py.java2py(thiz);
         }

         PyObject method = ((PyObject)thiz).__findattr__(name);
         if (method == null) {
            throw new NoSuchMethodException(name);
         } else {
            PyObject result;
            if (args != null) {
               result = method.__call__(Py.javas2pys(args));
            } else {
               result = method.__call__();
            }

            return result.__tojava__(Object.class);
         }
      } catch (PyException var6) {
         throw scriptException(var6);
      }
   }

   public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
      try {
         this.interp.setLocals(new PyScriptEngineScope(this, this.context));
         PyObject function = this.interp.get(name);
         if (function == null) {
            throw new NoSuchMethodException(name);
         } else {
            PyObject result;
            if (args != null) {
               result = function.__call__(Py.javas2pys(args));
            } else {
               result = function.__call__();
            }

            return result.__tojava__(Object.class);
         }
      } catch (PyException var5) {
         throw scriptException(var5);
      }
   }

   public Object getInterface(Class clazz) {
      return this.getInterface(new PyModule("__jsr223__", this.interp.getLocals()), clazz);
   }

   public Object getInterface(Object obj, Class clazz) {
      if (obj == null) {
         throw new IllegalArgumentException("object expected");
      } else if (clazz != null && clazz.isInterface()) {
         this.interp.setLocals(new PyScriptEngineScope(this, this.context));
         final PyObject thiz = Py.java2py(obj);
         Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               try {
                  PyScriptEngine.this.interp.setLocals(new PyScriptEngineScope(PyScriptEngine.this, PyScriptEngine.this.context));
                  PyObject pyMethod = thiz.__findattr__(method.getName());
                  if (pyMethod == null) {
                     throw new NoSuchMethodException(method.getName());
                  } else {
                     PyObject result;
                     if (args != null) {
                        result = pyMethod.__call__(Py.javas2pys(args));
                     } else {
                        result = pyMethod.__call__();
                     }

                     return result.__tojava__(Object.class);
                  }
               } catch (PyException var6) {
                  throw PyScriptEngine.scriptException(var6);
               }
            }
         });
         return proxy;
      } else {
         throw new IllegalArgumentException("interface expected");
      }
   }

   private static ScriptException scriptException(PyException pye) {
      ScriptException se = null;

      try {
         pye.normalize();
         PyObject type = pye.type;
         PyObject value = pye.value;
         PyTraceback tb = pye.traceback;
         if (__builtin__.isinstance(value, Py.SyntaxError)) {
            PyObject filename = value.__findattr__("filename");
            PyObject lineno = value.__findattr__("lineno");
            PyObject offset = value.__findattr__("offset");
            value = value.__findattr__("msg");
            se = new ScriptException(Py.formatException(type, value), filename == null ? "<script>" : filename.toString(), lineno == null ? 0 : lineno.asInt(), offset == null ? 0 : offset.asInt());
         } else if (tb != null) {
            String filename;
            if (tb.tb_frame != null && tb.tb_frame.f_code != null) {
               filename = tb.tb_frame.f_code.co_filename;
            } else {
               filename = null;
            }

            se = new ScriptException(Py.formatException(type, value), filename, tb.tb_lineno);
         } else {
            se = new ScriptException(Py.formatException(type, value));
         }

         se.initCause(pye);
         return se;
      } catch (Exception var8) {
         se = new ScriptException(pye);
         return se;
      }
   }

   public void close() {
      this.interp.close();
   }

   private class PyCompiledScript extends CompiledScript {
      private PyCode code;

      PyCompiledScript(PyCode code) {
         this.code = code;
      }

      public ScriptEngine getEngine() {
         return PyScriptEngine.this;
      }

      public Object eval(ScriptContext ctx) throws ScriptException {
         return PyScriptEngine.this.eval(this.code, ctx);
      }
   }
}
