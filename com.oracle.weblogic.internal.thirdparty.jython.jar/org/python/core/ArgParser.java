package org.python.core;

import java.util.HashSet;
import java.util.Set;
import org.python.antlr.AST;

public class ArgParser {
   private String funcname;
   private PyObject[] args;
   private String[] kws;
   private String[] params;
   private static Object required = new Object();
   private static String[] emptyKws = new String[0];

   private ArgParser(String funcname, PyObject[] args, String[] kws) {
      this.params = null;
      this.funcname = funcname;
      this.args = args;
      if (kws == null) {
         kws = emptyKws;
      }

      this.kws = kws;
   }

   public ArgParser(String funcname, PyObject[] args, String[] kws, String p0) {
      this(funcname, args, kws);
      this.params = new String[]{p0};
      this.check();
   }

   public ArgParser(String funcname, PyObject[] args, String[] kws, String p0, String p1) {
      this(funcname, args, kws);
      this.params = new String[]{p0, p1};
      this.check();
   }

   public ArgParser(String funcname, PyObject[] args, String[] kws, String p0, String p1, String p2) {
      this(funcname, args, kws);
      this.params = new String[]{p0, p1, p2};
      this.check();
   }

   public ArgParser(String funcname, PyObject[] args, String[] kws, String[] paramnames) {
      this(funcname, args, kws);
      this.params = paramnames;
      this.check();
   }

   public ArgParser(String funcname, PyObject[] args, String[] kws, String[] paramnames, int minargs) {
      this(funcname, args, kws);
      this.params = paramnames;
      this.check();
      if (!PyBuiltinCallable.DefaultInfo.check(args.length, minargs, this.params.length)) {
         throw PyBuiltinCallable.DefaultInfo.unexpectedCall(args.length, false, funcname, minargs, this.params.length);
      }
   }

   public ArgParser(String funcname, PyObject[] args, String[] kws, String[] paramnames, int minargs, boolean takesZeroArgs) {
      this(funcname, args, kws);
      this.params = paramnames;
      this.check();
      if (!AST.check(args.length - kws.length, minargs, takesZeroArgs)) {
         throw AST.unexpectedCall(minargs, funcname);
      }
   }

   public String getString(int pos) {
      return (String)this.getArg(pos, String.class, "string");
   }

   public String getString(int pos, String def) {
      return (String)this.getArg(pos, String.class, "string", def);
   }

   public int getInt(int pos) {
      return this.asInt(this.getRequiredArg(pos));
   }

   public int getInt(int pos, int def) {
      PyObject value = this.getOptionalArg(pos);
      return value == null ? def : this.asInt(value);
   }

   private int asInt(PyObject value) {
      if (value instanceof PyFloat) {
         Py.warning(Py.DeprecationWarning, "integer argument expected, got float");
         value = value.__int__();
      }

      return value.asInt();
   }

   public int getIndex(int pos) {
      PyObject value = this.getRequiredArg(pos);
      return value.asIndex();
   }

   public int getIndex(int pos, int def) {
      PyObject value = this.getOptionalArg(pos);
      return value == null ? def : value.asIndex();
   }

   public PyObject getPyObject(int pos) {
      return this.getRequiredArg(pos);
   }

   public PyObject getPyObject(int pos, PyObject def) {
      PyObject value = this.getOptionalArg(pos);
      if (value == null) {
         value = def;
      }

      return value;
   }

   public PyObject getPyObjectByType(int pos, PyType type) {
      PyObject arg = this.getRequiredArg(pos);
      return checkedForType(arg, pos, type);
   }

   public PyObject getPyObjectByType(int pos, PyType type, PyObject def) {
      PyObject arg = this.getOptionalArg(pos);
      return checkedForType(arg != null ? arg : def, pos, type);
   }

   private static PyObject checkedForType(PyObject arg, int pos, PyType type) {
      if (arg != null && !Py.isInstance(arg, type)) {
         throw Py.TypeError(String.format("argument %d must be %s, not %s", pos + 1, type.fastGetName(), arg.getType().fastGetName()));
      } else {
         return arg;
      }
   }

   public PyObject getList(int pos) {
      int kws_start = this.args.length - this.kws.length;
      if (pos < kws_start) {
         PyObject[] ret = new PyObject[kws_start - pos];
         System.arraycopy(this.args, pos, ret, 0, kws_start - pos);
         return new PyTuple(ret);
      } else {
         return Py.EmptyTuple;
      }
   }

   public void noKeywords() {
      if (this.kws.length > 0) {
         throw Py.TypeError(String.format("%s does not take keyword arguments", this.funcname));
      }
   }

   private void check() {
      Set usedKws = new HashSet();
      int nargs = this.args.length - this.kws.length;

      label32:
      for(int i = 0; i < this.kws.length; ++i) {
         for(int j = 0; j < this.params.length; ++j) {
            if (this.kws[i].equals(this.params[j])) {
               if (j < nargs) {
                  throw Py.TypeError("keyword parameter '" + this.params[j] + "' was given by position and by name");
               }

               if (usedKws.contains(j)) {
                  throw Py.TypeError(String.format("%s got multiple values for keyword argument '%s'", this.funcname, this.params[j]));
               }

               usedKws.add(j);
               continue label32;
            }
         }

         throw Py.TypeError("'" + this.kws[i] + "' is an invalid keyword " + "argument for this function");
      }

   }

   private PyObject getRequiredArg(int pos) {
      PyObject ret = this.getOptionalArg(pos);
      if (ret == null) {
         throw Py.TypeError(this.funcname + ": The " + ordinal(pos) + " argument is required");
      } else {
         return ret;
      }
   }

   private PyObject getOptionalArg(int pos) {
      int kws_start = this.args.length - this.kws.length;
      if (pos < kws_start) {
         return this.args[pos];
      } else {
         for(int i = 0; i < this.kws.length; ++i) {
            if (this.kws[i].equals(this.params[pos])) {
               return this.args[kws_start + i];
            }
         }

         return null;
      }
   }

   private Object getArg(int pos, Class clss, String classname) {
      return this.getArg(pos, clss, classname, required);
   }

   private Object getArg(int pos, Class clss, String classname, Object def) {
      PyObject value = null;
      if (def == required) {
         value = this.getRequiredArg(pos);
      } else {
         value = this.getOptionalArg(pos);
         if (value == null) {
            return def;
         }
      }

      Object ret = value.__tojava__(clss);
      if (ret == Py.NoConversion) {
         throw Py.TypeError("argument " + (pos + 1) + ": expected " + classname + ", " + value.getType().fastGetName() + " found");
      } else {
         return ret;
      }
   }

   private static String ordinal(int n) {
      switch (n + 1) {
         case 1:
            return "1st";
         case 2:
            return "2nd";
         case 3:
            return "3rd";
         default:
            return Integer.toString(n + 1) + "th";
      }
   }
}
