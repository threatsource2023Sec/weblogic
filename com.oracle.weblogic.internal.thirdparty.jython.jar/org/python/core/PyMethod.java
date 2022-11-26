package org.python.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "instancemethod",
   isBaseType = false,
   doc = "instancemethod(function, instance, class)\n\nCreate an instance method object."
)
public class PyMethod extends PyObject implements InvocationHandler, Traverseproc {
   public static final PyType TYPE;
   public PyObject im_class;
   public PyObject __func__;
   public PyObject __self__;

   /** @deprecated */
   @Deprecated
   public PyObject getFunc() {
      return this.__func__;
   }

   /** @deprecated */
   @Deprecated
   public PyObject getSelf() {
      return this.__self__;
   }

   public PyMethod(PyObject function, PyObject self, PyObject type) {
      super(TYPE);
      if (self == Py.None) {
         self = null;
      }

      this.__func__ = function;
      this.__self__ = self;
      this.im_class = type;
   }

   @ExposedNew
   static final PyObject instancemethod___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("instancemethod", args, keywords, "func");
      ap.noKeywords();
      PyObject func = ap.getPyObject(0);
      PyObject self = ap.getPyObject(1);
      PyObject classObj = ap.getPyObject(2, (PyObject)null);
      if (!func.isCallable()) {
         throw Py.TypeError("first argument must be callable");
      } else if (self == Py.None && classObj == null) {
         throw Py.TypeError("unbound methods must have non-NULL im_class");
      } else {
         return new PyMethod(func, self, classObj);
      }
   }

   public PyObject __findattr_ex__(String name) {
      return this.instancemethod___findattr_ex__(name);
   }

   final PyObject instancemethod___findattr_ex__(String name) {
      PyObject ret = super.__findattr_ex__(name);
      return ret != null ? ret : this.__func__.__findattr_ex__(name);
   }

   final PyObject instancemethod___getattribute__(PyObject arg0) {
      String name = asName(arg0);
      PyObject ret = this.instancemethod___findattr_ex__(name);
      if (ret == null) {
         this.noAttributeError(name);
      }

      return ret;
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.instancemethod___get__(obj, type);
   }

   final PyObject instancemethod___get__(PyObject obj, PyObject type) {
      if (obj != null && this.__self__ == null) {
         return Py.isSubClass(obj.fastGetClass(), this.im_class) ? new PyMethod(this.__func__, obj, this.im_class) : this;
      } else {
         return this;
      }
   }

   public PyObject __call__() {
      return this.__call__(Py.getThreadState());
   }

   public PyObject __call__(ThreadState state) {
      PyObject self = this.checkSelf((PyObject)null, (PyObject[])null);
      return self == null ? this.__func__.__call__(state) : this.__func__.__call__(state, self);
   }

   public PyObject __call__(PyObject arg0) {
      return this.__call__(Py.getThreadState(), arg0);
   }

   public PyObject __call__(ThreadState state, PyObject arg0) {
      PyObject self = this.checkSelf(arg0, (PyObject[])null);
      return self == null ? this.__func__.__call__(state, arg0) : this.__func__.__call__(state, self, arg0);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1) {
      return this.__call__(Py.getThreadState(), arg0, arg1);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1) {
      PyObject self = this.checkSelf(arg0, (PyObject[])null);
      return self == null ? this.__func__.__call__(state, arg0, arg1) : this.__func__.__call__(state, self, arg0, arg1);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2) {
      return this.__call__(Py.getThreadState(), arg0, arg1, arg2);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1, PyObject arg2) {
      PyObject self = this.checkSelf(arg0, (PyObject[])null);
      return self == null ? this.__func__.__call__(state, arg0, arg1, arg2) : this.__func__.__call__(state, self, arg0, arg1, arg2);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.__call__(Py.getThreadState(), arg0, arg1, arg2, arg3);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      PyObject self = this.checkSelf(arg0, (PyObject[])null);
      return self == null ? this.__func__.__call__(state, arg0, arg1, arg2, arg3) : this.__func__.__call__(state, self, new PyObject[]{arg0, arg1, arg2, arg3}, Py.NoKeywords);
   }

   public PyObject __call__(PyObject arg1, PyObject[] args, String[] keywords) {
      return this.__call__(Py.getThreadState(), arg1, args, keywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg1, PyObject[] args, String[] keywords) {
      PyObject self = this.checkSelf(arg1, args);
      if (self == null) {
         return this.__func__.__call__(state, arg1, args, keywords);
      } else {
         PyObject[] newArgs = new PyObject[args.length + 1];
         System.arraycopy(args, 0, newArgs, 1, args.length);
         newArgs[0] = arg1;
         return this.__func__.__call__(state, self, newArgs, keywords);
      }
   }

   public PyObject __call__(PyObject[] args) {
      return this.__call__(Py.getThreadState(), args);
   }

   public PyObject __call__(ThreadState state, PyObject[] args) {
      return this.__call__(state, args, Py.NoKeywords);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.__call__(Py.getThreadState(), args, keywords);
   }

   public PyObject __call__(ThreadState state, PyObject[] args, String[] keywords) {
      return this.instancemethod___call__(state, args, keywords);
   }

   final PyObject instancemethod___call__(ThreadState state, PyObject[] args, String[] keywords) {
      PyObject self = this.checkSelf((PyObject)null, args);
      return self == null ? this.__func__.__call__(state, args, keywords) : this.__func__.__call__(state, self, args, keywords);
   }

   private PyObject checkSelf(PyObject arg, PyObject[] args) {
      PyObject self = this.__self__;
      if (self == null) {
         if (arg != null) {
            self = arg;
         } else if (args != null && args.length >= 1) {
            self = args[0];
         }

         boolean ok;
         if (self == null) {
            ok = false;
         } else {
            ok = Py.isInstance(self, this.im_class);
         }

         if (!ok) {
            String msg = String.format("unbound method %s%s must be called with %s instance as first argument (got %s%s instead)", this.getFuncName(), "()", this.getClassName(this.im_class), this.getInstClassName(self), self == null ? "" : " instance");
            throw Py.TypeError(msg);
         } else {
            return null;
         }
      } else {
         return self;
      }
   }

   public int __cmp__(PyObject other) {
      return this.instancemethod___cmp__(other);
   }

   final int instancemethod___cmp__(PyObject other) {
      if (!(other instanceof PyMethod)) {
         return -2;
      } else {
         PyMethod otherMethod = (PyMethod)other;
         int cmp = this.__func__._cmp(otherMethod.__func__);
         if (cmp != 0) {
            return cmp;
         } else if (this.__self__ == otherMethod.__self__) {
            return 0;
         } else if (this.__self__ != null && otherMethod.__self__ != null) {
            return this.__self__._cmp(otherMethod.__self__);
         } else {
            return System.identityHashCode(this.__self__) < System.identityHashCode(otherMethod.__self__) ? -1 : 1;
         }
      }
   }

   public int hashCode() {
      int hashCode = this.__self__ == null ? Py.None.hashCode() : this.__self__.hashCode();
      return hashCode ^ this.__func__.hashCode();
   }

   public PyObject getDoc() {
      return this.__func__.__getattr__("__doc__");
   }

   public String toString() {
      String className = "?";
      if (this.im_class != null) {
         className = this.getClassName(this.im_class);
      }

      return this.__self__ == null ? String.format("<unbound method %s.%s>", className, this.getFuncName()) : String.format("<bound method %s.%s of %s>", className, this.getFuncName(), this.__self__.__str__());
   }

   private String getClassName(PyObject cls) {
      if (cls instanceof PyClass) {
         return ((PyClass)cls).__name__;
      } else {
         return cls instanceof PyType ? ((PyType)cls).fastGetName() : "?";
      }
   }

   private String getInstClassName(PyObject inst) {
      if (inst == null) {
         return "nothing";
      } else {
         PyObject classObj = inst.__findattr__("__class__");
         if (classObj == null) {
            classObj = inst.getType();
         }

         return this.getClassName((PyObject)classObj);
      }
   }

   private String getFuncName() {
      PyObject funcName = null;

      try {
         funcName = this.__func__.__findattr__("__name__");
      } catch (PyException var3) {
      }

      return funcName == null ? "?" : funcName.toString();
   }

   public Object __tojava__(Class c) {
      if (this.__self__ == null) {
         return super.__tojava__(c);
      } else if (c.isInstance(this) && c != InvocationHandler.class) {
         return c.cast(this);
      } else {
         if (c.isInterface()) {
            if (c.getDeclaredMethods().length == 1 && c.getInterfaces().length == 0) {
               return this.proxy(c);
            }

            String name = null;
            Method[] var3 = c.getMethods();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Method method = var3[var5];
               if (method.getDeclaringClass() != Object.class) {
                  if (name != null && !name.equals(method.getName())) {
                     name = null;
                     break;
                  }

                  name = method.getName();
               }
            }

            if (name != null) {
               return this.proxy(c);
            }
         }

         return super.__tojava__(c);
      }
   }

   private Object proxy(Class c) {
      return Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c}, this);
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (method.getDeclaringClass() == Object.class) {
         return method.invoke(this, args);
      } else {
         return args != null && args.length != 0 ? this.__call__(Py.javas2pys(args)).__tojava__(method.getReturnType()) : this.__call__().__tojava__(method.getReturnType());
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.im_class != null) {
         retVal = visit.visit(this.im_class, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__func__ != null) {
         retVal = visit.visit(this.__func__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.__self__ == null ? 0 : visit.visit(this.__self__, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.im_class || ob == this.__func__ || ob == this.__self__);
   }

   static {
      PyType.addBuilder(PyMethod.class, new PyExposer());
      TYPE = PyType.fromClass(PyMethod.class);
   }

   private static class instancemethod___getattribute___exposer extends PyBuiltinMethodNarrow {
      public instancemethod___getattribute___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public instancemethod___getattribute___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instancemethod___getattribute___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyMethod)this.self).instancemethod___getattribute__(var1);
      }
   }

   private static class instancemethod___get___exposer extends PyBuiltinMethodNarrow {
      public instancemethod___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public instancemethod___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instancemethod___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyMethod)this.self).instancemethod___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyMethod)this.self).instancemethod___get__(var1, (PyObject)null);
      }
   }

   private static class instancemethod___call___exposer extends PyBuiltinMethod {
      public instancemethod___call___exposer(String var1) {
         super(var1);
         super.doc = "x.__call__(...) <==> x(...)";
      }

      public instancemethod___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__call__(...) <==> x(...)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instancemethod___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(ThreadState var1, PyObject[] var2, String[] var3) {
         return ((PyMethod)this.self).instancemethod___call__(var1, var2, var3);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return this.__call__(Py.getThreadState(), var1, var2);
      }
   }

   private static class instancemethod___cmp___exposer extends PyBuiltinMethodNarrow {
      public instancemethod___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public instancemethod___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instancemethod___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyMethod)this.self).instancemethod___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("instancemethod.__cmp__(x,y) requires y to be 'instancemethod', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class __self___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __self___descriptor() {
         super("__self__", PyObject.class, "the instance to which a method is bound; None for unbound methods");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethod)var1).__self__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __func___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __func___descriptor() {
         super("__func__", PyObject.class, "the function (or other callable) implementing a method");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethod)var1).__func__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class im_func_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public im_func_descriptor() {
         super("im_func", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethod)var1).getFunc();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class im_self_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public im_self_descriptor() {
         super("im_self", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethod)var1).getSelf();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class im_class_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public im_class_descriptor() {
         super("im_class", PyObject.class, "the class associated with a method");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethod)var1).im_class;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethod)var1).getDoc();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyMethod.instancemethod___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new instancemethod___getattribute___exposer("__getattribute__"), new instancemethod___get___exposer("__get__"), new instancemethod___call___exposer("__call__"), new instancemethod___cmp___exposer("__cmp__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __self___descriptor(), new __func___descriptor(), new im_func_descriptor(), new im_self_descriptor(), new im_class_descriptor(), new __doc___descriptor()};
         super("instancemethod", PyMethod.class, Object.class, (boolean)0, "instancemethod(function, instance, class)\n\nCreate an instance method object.", var1, var2, new exposed___new__());
      }
   }
}
