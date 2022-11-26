package org.python.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

@Untraversable
public class PyReflectedConstructor extends PyReflectedFunction {
   public PyReflectedConstructor(String name) {
      super(name);
   }

   public PyReflectedConstructor(Constructor c) {
      this(c.getDeclaringClass().getName());
      this.addConstructor(c);
   }

   private ReflectedArgs makeArgs(Constructor m) {
      return new ReflectedArgs(m, m.getParameterTypes(), m.getDeclaringClass(), true);
   }

   public void addConstructor(Constructor m) {
      int mods = m.getModifiers();
      if (Modifier.isPublic(mods) || !Options.respectJavaAccessibility) {
         this.addArgs(this.makeArgs(m));
      }
   }

   PyObject make(PyObject[] args, String[] keywords) {
      ReflectedCallData callData = new ReflectedCallData();
      Object method = null;
      boolean consumes_keywords = false;
      PyObject[] allArgs = null;
      if (this.nargs > 0) {
         if (this.argslist[0].matches((PyObject)null, args, keywords, callData)) {
            method = this.argslist[0].data;
            consumes_keywords = this.argslist[0].flags == 2;
         } else {
            allArgs = args;
            int i = 1;
            if (keywords.length > 0) {
               args = new PyObject[args.length - keywords.length];
               System.arraycopy(allArgs, 0, args, 0, args.length);
               i = 0;
            }

            while(i < this.nargs) {
               if (this.argslist[i].matches((PyObject)null, args, Py.NoKeywords, callData)) {
                  method = this.argslist[i].data;
                  break;
               }

               ++i;
            }
         }
      }

      if (method == null) {
         this.throwError(callData.errArg, args.length, true, false);
      }

      PyObject obj;
      try {
         obj = (PyObject)((Constructor)method).newInstance(callData.getArgsArray());
      } catch (Throwable var10) {
         throw Py.JavaError(var10);
      }

      if (!consumes_keywords) {
         int offset = args.length;

         for(int i = 0; i < keywords.length; ++i) {
            obj.__setattr__(keywords[i], allArgs[i + offset]);
         }
      }

      return obj;
   }

   public PyObject __call__(PyObject self, PyObject[] args, String[] keywords) {
      if (self == null) {
         throw Py.TypeError("invalid self argument to constructor");
      } else {
         Class javaClass = self.getType().getProxyType();
         if (javaClass == null) {
            throw Py.TypeError("self invalid - must be a Java subclass [self=" + self + "]");
         } else {
            Class declaringClass = this.argslist[0] == null ? null : this.argslist[0].declaringClass;
            if ((declaringClass == null || !PyProxy.class.isAssignableFrom(declaringClass)) && !(self.getType() instanceof PyJavaType)) {
               return PyType.fromClass(javaClass).lookup("__init__").__call__(self, args, keywords);
            } else if (this.nargs == 0) {
               throw Py.TypeError("No visible constructors for class (" + javaClass.getName() + ")");
            } else if (!declaringClass.isAssignableFrom(javaClass)) {
               throw Py.TypeError("self invalid - must implement: " + declaringClass.getName());
            } else {
               int mods = declaringClass.getModifiers();
               if (Modifier.isInterface(mods)) {
                  throw Py.TypeError("can't instantiate interface (" + declaringClass.getName() + ")");
               } else if (Modifier.isAbstract(mods)) {
                  throw Py.TypeError("can't instantiate abstract class (" + declaringClass.getName() + ")");
               } else if (JyAttribute.hasAttr(self, (byte)-128)) {
                  Class sup = javaClass;
                  if (PyProxy.class.isAssignableFrom(javaClass)) {
                     sup = javaClass.getSuperclass();
                  }

                  throw Py.TypeError("instance already instantiated for " + sup.getName());
               } else {
                  ReflectedCallData callData = new ReflectedCallData();
                  Object method = null;
                  int nkeywords = keywords.length;
                  ReflectedArgs rargs = null;
                  PyObject[] allArgs = args;
                  boolean usingKeywordArgsCtor = false;
                  int offset;
                  int i;
                  if (nkeywords > 0) {
                     offset = this.nargs;

                     for(i = 0; i < offset; ++i) {
                        rargs = this.argslist[i];
                        if (rargs.matches((PyObject)null, args, keywords, callData)) {
                           method = rargs.data;
                           break;
                        }
                     }

                     if (method != null) {
                        usingKeywordArgsCtor = true;
                     } else {
                        args = new PyObject[args.length - nkeywords];
                        System.arraycopy(allArgs, 0, args, 0, args.length);

                        for(i = 0; i < offset; ++i) {
                           rargs = this.argslist[i];
                           if (rargs.matches((PyObject)null, args, Py.NoKeywords, callData)) {
                              method = rargs.data;
                              break;
                           }
                        }
                     }
                  } else {
                     offset = this.nargs;

                     for(i = 0; i < offset; ++i) {
                        rargs = this.argslist[i];
                        if (rargs.matches((PyObject)null, args, Py.NoKeywords, callData)) {
                           method = rargs.data;
                           break;
                        }
                     }
                  }

                  if (method == null) {
                     this.throwError(callData.errArg, args.length, false, false);
                  }

                  this.constructProxy(self, (Constructor)method, callData.getArgsArray(), javaClass);
                  if (!usingKeywordArgsCtor) {
                     offset = args.length;

                     for(i = 0; i < nkeywords; ++i) {
                        self.__setattr__(keywords[i], allArgs[i + offset]);
                     }
                  }

                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      if (args.length < 1) {
         throw Py.TypeError("constructor requires self argument");
      } else {
         PyObject[] newArgs = new PyObject[args.length - 1];
         System.arraycopy(args, 1, newArgs, 0, newArgs.length);
         return this.__call__(args[0], newArgs, keywords);
      }
   }

   protected void constructProxy(PyObject obj, Constructor ctor, Object[] args, Class proxy) {
      Object jself = null;
      Object[] previous = (Object[])ThreadContext.initializingProxy.get();
      ThreadContext.initializingProxy.set(new Object[]{obj});

      try {
         jself = ctor.newInstance(args);
      } catch (InvocationTargetException var14) {
         if (var14.getTargetException() instanceof InstantiationException) {
            Class sup = proxy.getSuperclass();
            String msg = "Constructor failed for Java superclass";
            if (sup != null) {
               msg = msg + " " + sup.getName();
            }

            throw Py.TypeError(msg);
         }

         throw Py.JavaError(var14);
      } catch (Throwable var15) {
         throw Py.JavaError(var15);
      } finally {
         ThreadContext.initializingProxy.set(previous);
      }

      JyAttribute.setAttr(obj, (byte)-128, jself);
   }

   public PyObject _doget(PyObject container, PyObject wherefound) {
      return (PyObject)(container == null ? this : new PyMethod(this, container, wherefound));
   }

   public String toString() {
      return "<java constructor " + this.__name__ + " " + Py.idstr(this) + ">";
   }
}
