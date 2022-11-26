package org.python.core;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import org.python.util.Generic;

@Untraversable
public class PyBeanEventProperty extends PyObject {
   private static Map adapterClasses = Generic.map();
   private static Map adapters = new WeakHashMap();
   public Method addMethod;
   public String eventName;
   public Class eventClass;
   public String __name__;
   private Field adapterField;
   private Class adapterClass;

   public PyBeanEventProperty(String eventName, Class eventClass, Method addMethod, Method eventMethod) {
      this.__name__ = eventMethod.getName().intern();
      this.addMethod = addMethod;
      this.eventName = eventName;
      this.eventClass = eventClass;
   }

   public PyObject _doget(PyObject self) {
      if (self == null) {
         return this;
      } else {
         this.initAdapter();
         Object jself = Py.tojava(self, this.addMethod.getDeclaringClass());

         Object field;
         try {
            field = this.adapterField.get(this.getAdapter(jself));
         } catch (Exception var5) {
            throw Py.JavaError(var5);
         }

         PyCompoundCallable func;
         if (field == null) {
            func = new PyCompoundCallable();
            this.setFunction(jself, func);
            return func;
         } else if (field instanceof PyCompoundCallable) {
            return (PyCompoundCallable)field;
         } else {
            func = new PyCompoundCallable();
            this.setFunction(jself, func);
            func.append((PyObject)field);
            return func;
         }
      }
   }

   public boolean _doset(PyObject self, PyObject value) {
      Object jself = Py.tojava(self, this.addMethod.getDeclaringClass());
      if (!(value instanceof PyCompoundCallable)) {
         PyCompoundCallable func = new PyCompoundCallable();
         this.setFunction(jself, func);
         func.append(value);
      } else {
         this.setFunction(jself, value);
      }

      return true;
   }

   public String toString() {
      return "<beanEventProperty " + this.__name__ + " for event " + this.eventClass.toString() + " " + Py.idstr(this) + ">";
   }

   private Object getAdapter(Object o, String evc) {
      Map ads = (Map)adapters.get(o);
      if (ads == null) {
         return null;
      } else {
         WeakReference adw = (WeakReference)ads.get(evc);
         return adw == null ? null : adw.get();
      }
   }

   private void putAdapter(Object o, String evc, Object ad) {
      Map ads = (Map)adapters.get(o);
      if (ads == null) {
         ads = Generic.map();
         adapters.put(o, ads);
      }

      ads.put(evc, new WeakReference(ad));
   }

   private synchronized Object getAdapter(Object self) {
      String eventClassName = this.eventClass.getName();
      Object adapter = this.getAdapter(self, eventClassName);
      if (adapter != null) {
         return adapter;
      } else {
         try {
            adapter = this.adapterClass.newInstance();
            this.addMethod.invoke(self, adapter);
         } catch (Exception var5) {
            throw Py.JavaError(var5);
         }

         this.putAdapter(self, eventClassName, adapter);
         return adapter;
      }
   }

   private void initAdapter() {
      if (this.adapterClass == null) {
         this.adapterClass = getAdapterClass(this.eventClass);

         try {
            this.adapterField = this.adapterClass.getField(this.__name__);
         } catch (NoSuchFieldException var2) {
            throw Py.AttributeError("Internal bean event error: " + this.__name__);
         }
      }

   }

   private void setFunction(Object self, PyObject callable) {
      this.initAdapter();

      try {
         this.adapterField.set(this.getAdapter(self), callable);
      } catch (Exception var4) {
         throw Py.JavaError(var4);
      }
   }

   private static synchronized Class getAdapterClass(Class c) {
      String name = "org.python.proxies." + c.getName() + "$Adapter";
      Class pc = Py.findClass(name);
      if (pc == null) {
         pc = (Class)adapterClasses.get(name);
         if (pc == null) {
            pc = MakeProxies.makeAdapter(c);
            adapterClasses.put(name, pc);
         }
      }

      return pc;
   }
}
