package org.python.util;

import org.python.core.Py;
import org.python.core.PyBuiltinFunctionSet;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.__builtin__;

public class InteractiveConsole extends InteractiveInterpreter {
   public static String CONSOLE_FILENAME = "<stdin>";
   public String filename;

   public InteractiveConsole() {
      this((PyObject)null, CONSOLE_FILENAME);
   }

   public InteractiveConsole(PyObject locals) {
      this(locals, CONSOLE_FILENAME);
   }

   public InteractiveConsole(PyObject locals, String filename) {
      this(locals, filename, false);
   }

   public InteractiveConsole(PyObject locals, String filename, boolean replaceRawInput) {
      super(locals);
      this.filename = filename;
      if (replaceRawInput) {
         PyObject newRawInput = new PyBuiltinFunctionSet("raw_input", 0, 0, 1) {
            public PyObject __call__() {
               return this.__call__(Py.EmptyString);
            }

            public PyObject __call__(PyObject prompt) {
               return Py.newString(InteractiveConsole.this.raw_input(prompt));
            }
         };
         Py.getSystemState().getBuiltins().__setitem__((String)"raw_input", newRawInput);
      }

   }

   public void interact() {
      this.interact(getDefaultBanner(), (PyObject)null);
   }

   public static String getDefaultBanner() {
      return String.format("Jython %s on %s", PySystemState.version, Py.getSystemState().platform);
   }

   public void interact(String banner, PyObject file) {
      PyObject old_ps1 = this.systemState.ps1;
      PyObject old_ps2 = this.systemState.ps2;
      this.systemState.ps1 = new PyString(">>> ");
      this.systemState.ps2 = new PyString("... ");

      try {
         this._interact(banner, file);
      } finally {
         this.systemState.ps1 = old_ps1;
         this.systemState.ps2 = old_ps2;
      }

   }

   public void _interact(String banner, PyObject file) {
      if (banner != null) {
         this.write(banner);
         this.write("\n");
      }

      this.exec("2");
      boolean more = false;

      while(true) {
         PyObject prompt = more ? this.systemState.ps2 : this.systemState.ps1;

         String line;
         try {
            if (file == null) {
               line = this.raw_input(prompt);
            } else {
               line = this.raw_input(prompt, file);
            }
         } catch (PyException var7) {
            if (!var7.match(Py.EOFError)) {
               throw var7;
            }

            if (banner != null) {
               this.write("\n");
            }

            return;
         } catch (Throwable var8) {
            throw Py.JavaError(var8);
         }

         more = this.push(line);
      }
   }

   public boolean push(String line) {
      if (this.buffer.length() > 0) {
         this.buffer.append("\n");
      }

      this.buffer.append(line);
      boolean more = this.runsource(this.buffer.toString(), this.filename);
      if (!more) {
         this.resetbuffer();
      }

      return more;
   }

   public String raw_input(PyObject prompt) {
      return __builtin__.raw_input(prompt);
   }

   public String raw_input(PyObject prompt, PyObject file) {
      return __builtin__.raw_input(prompt, file);
   }
}
