package org.python.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;
import org.python.util.Generic;

public class PyReflectedFunction extends PyObject implements Traverseproc {
   public String __name__;
   public PyObject __doc__;
   public PyObject __module__;
   public ReflectedArgs[] argslist;
   public int nargs;
   private boolean calledStatically;

   protected PyReflectedFunction(String name) {
      this.__doc__ = Py.None;
      this.__module__ = Py.None;
      this.argslist = new ReflectedArgs[1];
      this.__name__ = name;
   }

   public PyReflectedFunction(Method... methods) {
      this(methods[0].getName());
      Method[] var2 = methods;
      int var3 = methods.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method meth = var2[var4];
         this.addMethod(meth);
      }

      if (this.nargs == 0) {
         String msg = String.format("Attempted to make Java method visible, but it isn't callable[method=%s, class=%s]", methods[0].getName(), methods[0].getDeclaringClass());
         throw Py.SystemError(msg);
      }
   }

   public PyObject _doget(PyObject container) {
      return this._doget(container, (PyObject)null);
   }

   public PyObject _doget(PyObject container, PyObject wherefound) {
      if (container == null) {
         return this.calledStatically ? this : this.copyWithCalledStatically(true);
      } else {
         return new PyMethod(this.calledStatically ? this.copyWithCalledStatically(false) : this, container, wherefound);
      }
   }

   private ReflectedArgs makeArgs(Method m) {
      return new ReflectedArgs(m, m.getParameterTypes(), m.getDeclaringClass(), Modifier.isStatic(m.getModifiers()), m.isVarArgs());
   }

   public PyReflectedFunction copy() {
      PyReflectedFunction func = new PyReflectedFunction(this.__name__);
      func.__doc__ = this.__doc__;
      func.__module__ = this.__module__;
      func.nargs = this.nargs;
      func.argslist = new ReflectedArgs[this.nargs];
      System.arraycopy(this.argslist, 0, func.argslist, 0, this.nargs);
      return func;
   }

   private PyReflectedFunction copyWithCalledStatically(boolean calledStatically) {
      PyReflectedFunction copy = this.copy();
      copy.calledStatically = calledStatically;
      return copy;
   }

   public boolean handles(Method method) {
      return this.handles(this.makeArgs(method));
   }

   protected boolean handles(ReflectedArgs args) {
      for(int i = 0; i < this.nargs; ++i) {
         int cmp = args.compareTo(this.argslist[i]);
         if (cmp == 0) {
            return true;
         }

         if (cmp == 1) {
            return false;
         }
      }

      return false;
   }

   public void addMethod(Method m) {
      if (Modifier.isPublic(m.getModifiers()) || !Options.respectJavaAccessibility) {
         if (isPackagedProtected(m.getDeclaringClass())) {
            try {
               m.setAccessible(true);
            } catch (SecurityException var3) {
            }
         }

         this.addArgs(this.makeArgs(m));
      }
   }

   public static boolean isPackagedProtected(Class c) {
      int mods = c.getModifiers();
      return !Modifier.isPublic(mods) && !Modifier.isPrivate(mods) && !Modifier.isProtected(mods);
   }

   protected void addArgs(ReflectedArgs args) {
      int i = 0;

      while(true) {
         int cmp;
         if (i < this.nargs) {
            cmp = args.compareTo(this.argslist[i]);
            if (cmp == 0) {
               return;
            }

            if (cmp == 1998) {
               this.argslist[i] = args;
               return;
            }

            if (cmp != -1) {
               ++i;
               continue;
            }
         }

         cmp = this.nargs + 1;
         if (cmp > this.argslist.length) {
            ReflectedArgs[] newargslist = new ReflectedArgs[cmp + 2];
            System.arraycopy(this.argslist, 0, newargslist, 0, this.nargs);
            this.argslist = newargslist;
         }

         for(int j = this.nargs; j > i; --j) {
            this.argslist[j] = this.argslist[j - 1];
         }

         this.argslist[i] = args;
         this.nargs = cmp;
         return;
      }
   }

   public PyObject __call__(PyObject self, PyObject[] args, String[] keywords) {
      ReflectedCallData callData = new ReflectedCallData();
      ReflectedArgs match = null;

      for(int i = 0; i < this.nargs && match == null; ++i) {
         if (this.argslist[i].matches(self, args, keywords, callData)) {
            match = this.argslist[i];
         }
      }

      if (match == null) {
         this.throwError(callData.errArg, args.length, self != null, keywords.length != 0);
      }

      Object cself = callData.self;
      Method m = (Method)match.data;
      if (self == null && cself != null && cself instanceof PyProxy && !this.__name__.startsWith("super__") && match.declaringClass != cself.getClass()) {
         String mname = "super__" + this.__name__;

         try {
            m = cself.getClass().getMethod(mname, m.getParameterTypes());
         } catch (Exception var11) {
            throw Py.JavaError(var11);
         }
      }

      Object o;
      try {
         o = m.invoke(cself, callData.getArgsArray());
      } catch (Throwable var10) {
         throw Py.JavaError(var10);
      }

      return Py.java2py(o);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      PyObject self;
      if (this.calledStatically) {
         self = null;
      } else {
         PyObject[] unboundArgs = new PyObject[args.length - 1];
         System.arraycopy(args, 1, unboundArgs, 0, unboundArgs.length);
         self = args[0];
         args = unboundArgs;
      }

      return this.__call__(self, args, keywords);
   }

   protected void throwError(String message) {
      throw Py.TypeError(this.__name__ + "(): " + message);
   }

   private static void addRange(StringBuilder buf, int min, int max, String sep) {
      if (buf.length() > 0) {
         buf.append(sep);
      }

      if (min < max) {
         buf.append(Integer.toString(min)).append('-').append(max);
      } else {
         buf.append(min);
      }

   }

   protected void throwArgCountError(int nArgs, boolean self) {
      boolean[] legalArgs = new boolean[40];
      int maxArgs = -1;
      int minArgs = 40;

      int a;
      for(int i = 0; i < this.nargs; ++i) {
         ReflectedArgs rargs = this.argslist[i];
         a = rargs.args.length;
         if (!self && !rargs.isStatic) {
            ++a;
         }

         legalArgs[a] = true;
         if (a > maxArgs) {
            maxArgs = a;
         }

         if (a < minArgs) {
            minArgs = a;
         }
      }

      StringBuilder buf = new StringBuilder();
      int startRange = minArgs;
      a = minArgs + 1;

      while(true) {
         while(a < maxArgs) {
            if (legalArgs[a]) {
               ++a;
            } else {
               addRange(buf, startRange, a - 1, ", ");
               ++a;

               while(a <= maxArgs) {
                  if (legalArgs[a]) {
                     startRange = a;
                     break;
                  }

                  ++a;
               }
            }
         }

         addRange(buf, startRange, maxArgs, " or ");
         this.throwError("expected " + buf + " args; got " + nArgs);
         return;
      }
   }

   private static String ordinal(int n) {
      switch (n + 1) {
         case 0:
            return "self";
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

   private static String niceName(Class arg) {
      if (arg != String.class && arg != PyString.class) {
         return arg.isArray() ? niceName(arg.getComponentType()) + "[]" : arg.getName();
      } else {
         return "String";
      }
   }

   protected void throwBadArgError(int errArg, int nArgs, boolean self) {
      Set argTypes = Generic.set();

      for(int i = 0; i < this.nargs; ++i) {
         if (this.argslist[i].args.length == nArgs || !this.argslist[i].isStatic && !self && this.argslist[i].args.length == nArgs - 1) {
            if (errArg == -1) {
               argTypes.add(this.argslist[i].declaringClass);
            } else {
               argTypes.add(this.argslist[i].args[errArg]);
            }
         }
      }

      StringBuilder buf = new StringBuilder();
      Iterator var6 = argTypes.iterator();

      while(var6.hasNext()) {
         Class arg = (Class)var6.next();
         buf.append(niceName(arg));
         buf.append(", ");
      }

      if (buf.length() > 2) {
         buf.setLength(buf.length() - 2);
      }

      this.throwError(ordinal(errArg) + " arg can't be coerced to " + buf);
   }

   protected void throwError(int errArg, int nArgs, boolean self, boolean keywords) {
      if (keywords) {
         this.throwError("takes no keyword arguments");
      } else if (errArg == -2) {
         this.throwArgCountError(nArgs, self);
      } else {
         this.throwBadArgError(errArg, nArgs, self);
      }

   }

   public void printArgs() {
      System.err.println("nargs: " + this.nargs);

      for(int i = 0; i < this.nargs; ++i) {
         ReflectedArgs args = this.argslist[i];
         System.err.println(args.toString());
      }

   }

   public String toString() {
      return "<java function " + this.__name__ + " " + Py.idstr(this) + ">";
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.__module__ != null) {
         int res = visit.visit(this.__module__, arg);
         if (res != 0) {
            return res;
         }
      }

      return this.__doc__ != null ? visit.visit(this.__doc__, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.__doc__ || ob == this.__module__);
   }
}
