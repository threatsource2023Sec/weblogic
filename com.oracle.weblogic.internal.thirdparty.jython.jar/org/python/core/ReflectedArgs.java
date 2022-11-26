package org.python.core;

public class ReflectedArgs {
   public Class[] args;
   public Object data;
   public Class declaringClass;
   public boolean isStatic;
   public boolean isVarArgs;
   public int flags;
   public static final int StandardCall = 0;
   public static final int PyArgsCall = 1;
   public static final int PyArgsKeywordsCall = 2;
   public static final int REPLACE = 1998;

   public ReflectedArgs(Object data, Class[] args, Class declaringClass, boolean isStatic) {
      this(data, args, declaringClass, isStatic, false);
   }

   public ReflectedArgs(Object data, Class[] args, Class declaringClass, boolean isStatic, boolean isVarArgs) {
      this.data = data;
      this.args = args;
      this.declaringClass = declaringClass;
      this.isStatic = isStatic;
      this.isVarArgs = isVarArgs;
      if (args.length == 1 && args[0] == PyObject[].class) {
         this.flags = 1;
      } else if (args.length == 2 && args[0] == PyObject[].class && args[1] == String[].class) {
         this.flags = 2;
      } else {
         this.flags = 0;
      }

   }

   public boolean matches(PyObject self, PyObject[] pyArgs, String[] keywords, ReflectedCallData callData) {
      if (this.flags != 2 && keywords != null && keywords.length != 0) {
         return false;
      } else {
         if (this.isStatic) {
            if (self != null) {
               self = null;
            }
         } else if (self == null) {
            if (pyArgs.length == 0) {
               return false;
            }

            self = pyArgs[0];
            PyObject[] newArgs = new PyObject[pyArgs.length - 1];
            System.arraycopy(pyArgs, 1, newArgs, 0, newArgs.length);
            pyArgs = newArgs;
         }

         Object tmp;
         if (this.flags == 2) {
            callData.setLength(2);
            callData.args[0] = pyArgs;
            callData.args[1] = keywords;
            callData.self = self;
            if (self != null) {
               tmp = self.__tojava__(this.declaringClass);
               if (tmp != Py.NoConversion) {
                  callData.self = tmp;
               }
            }

            return true;
         } else if (this.flags == 1) {
            callData.setLength(1);
            callData.args[0] = pyArgs;
            callData.self = self;
            if (self != null) {
               tmp = self.__tojava__(this.declaringClass);
               if (tmp != Py.NoConversion) {
                  callData.self = tmp;
               }
            }

            return true;
         } else {
            int n = this.args.length;
            if (this.isVarArgs) {
               pyArgs = this.ensureBoxedVarargs(pyArgs, n);
            }

            if (pyArgs.length != n) {
               return false;
            } else {
               callData.errArg = -1;
               if (self != null) {
                  Object tmp = self.__tojava__(this.declaringClass);
                  if (tmp == Py.NoConversion) {
                     return false;
                  }

                  callData.self = tmp;
               } else {
                  callData.self = null;
               }

               callData.setLength(n);
               Object[] javaArgs = callData.args;

               for(int i = 0; i < n; ++i) {
                  PyObject pyArg = pyArgs[i];
                  Class targetClass = this.args[i];
                  Object javaArg = pyArg.__tojava__(targetClass);
                  javaArgs[i] = javaArg;
                  if (javaArg == Py.NoConversion) {
                     if (i > callData.errArg) {
                        callData.errArg = i;
                     }

                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private PyObject[] ensureBoxedVarargs(PyObject[] pyArgs, int n) {
      if (pyArgs.length == 0) {
         return new PyObject[]{new PyList()};
      } else {
         PyObject lastArg = pyArgs[pyArgs.length - 1];
         if (!(lastArg instanceof PySequenceList) && !(lastArg instanceof PyArray) && !(lastArg instanceof PyXRange) && !(lastArg instanceof PyIterator)) {
            int non_varargs_len = n - 1;
            if (pyArgs.length < non_varargs_len) {
               return pyArgs;
            } else {
               PyObject[] boxedPyArgs = new PyObject[n];

               int varargs_len;
               for(varargs_len = 0; varargs_len < non_varargs_len; ++varargs_len) {
                  boxedPyArgs[varargs_len] = pyArgs[varargs_len];
               }

               varargs_len = pyArgs.length - non_varargs_len;
               PyObject[] varargs = new PyObject[varargs_len];

               for(int i = 0; i < varargs_len; ++i) {
                  varargs[i] = pyArgs[non_varargs_len + i];
               }

               boxedPyArgs[non_varargs_len] = new PyList(varargs);
               return boxedPyArgs;
            }
         } else {
            return pyArgs;
         }
      }
   }

   public static int precedence(Class arg) {
      if (arg == Object.class) {
         return 3000;
      } else {
         if (arg.isPrimitive()) {
            if (arg == Long.TYPE) {
               return 10;
            }

            if (arg == Integer.TYPE) {
               return 11;
            }

            if (arg == Short.TYPE) {
               return 12;
            }

            if (arg == Character.TYPE) {
               return 13;
            }

            if (arg == Byte.TYPE) {
               return 14;
            }

            if (arg == Double.TYPE) {
               return 20;
            }

            if (arg == Float.TYPE) {
               return 21;
            }

            if (arg == Boolean.TYPE) {
               return 30;
            }
         }

         if (arg == String.class) {
            return 40;
         } else if (arg.isArray()) {
            Class componentType = arg.getComponentType();
            return componentType == Object.class ? 2500 : 100 + precedence(componentType);
         } else {
            return 2000;
         }
      }
   }

   public static int compare(Class arg1, Class arg2) {
      int p1 = precedence(arg1);
      int p2 = precedence(arg2);
      if (p1 >= 2000 && p2 >= 2000) {
         if (arg1.isAssignableFrom(arg2)) {
            return arg2.isAssignableFrom(arg1) ? 0 : 2;
         } else if (arg2.isAssignableFrom(arg1)) {
            return -2;
         } else {
            int cmp = arg1.getName().compareTo(arg2.getName());
            return cmp > 0 ? 1 : -1;
         }
      } else {
         return p1 > p2 ? 2 : (p1 == p2 ? 0 : -2);
      }
   }

   public int compareTo(ReflectedArgs other) {
      Class[] oargs = other.args;
      if (other.flags != this.flags) {
         return other.flags < this.flags ? -1 : 1;
      } else {
         int n = this.args.length;
         if (n < oargs.length) {
            return -1;
         } else if (n > oargs.length) {
            return 1;
         } else if (this.isStatic && !other.isStatic) {
            return 1;
         } else if (!this.isStatic && other.isStatic) {
            return -1;
         } else {
            int cmp = 0;

            for(int i = 0; i < n; ++i) {
               int tmp = compare(this.args[i], oargs[i]);
               if (tmp == 2 || tmp == -2) {
                  cmp = tmp;
               }

               if (cmp == 0) {
                  cmp = tmp;
               }
            }

            if (cmp != 0) {
               return cmp > 0 ? 1 : -1;
            } else {
               boolean replace = other.declaringClass.isAssignableFrom(this.declaringClass);
               if (!this.isStatic) {
                  replace = !replace;
               }

               return replace ? 1998 : 0;
            }
         }
      }
   }

   public String toString() {
      String s = this.declaringClass + ", static=" + this.isStatic + ", varargs=" + this.isVarArgs + ",flags=" + this.flags + ", " + this.data + "\n";
      s = s + "\t(";
      Class[] var2 = this.args;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class arg = var2[var4];
         s = s + arg.getName() + ", ";
      }

      s = s + ")";
      return s;
   }
}
