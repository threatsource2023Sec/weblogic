package weblogic.apache.org.apache.velocity.util.introspection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import weblogic.apache.org.apache.velocity.runtime.RuntimeLogger;
import weblogic.apache.org.apache.velocity.runtime.parser.node.AbstractExecutor;
import weblogic.apache.org.apache.velocity.runtime.parser.node.BooleanPropertyExecutor;
import weblogic.apache.org.apache.velocity.runtime.parser.node.GetExecutor;
import weblogic.apache.org.apache.velocity.runtime.parser.node.PropertyExecutor;
import weblogic.apache.org.apache.velocity.util.ArrayIterator;
import weblogic.apache.org.apache.velocity.util.EnumerationIterator;

public class UberspectImpl implements Uberspect, UberspectLoggable {
   private RuntimeLogger rlog;
   private static Introspector introspector;
   // $FF: synthetic field
   static Class class$java$util$Map;

   public void init() throws Exception {
   }

   public void setRuntimeLogger(RuntimeLogger runtimeLogger) {
      this.rlog = runtimeLogger;
      introspector = new Introspector(this.rlog);
   }

   public Iterator getIterator(Object obj, Info i) throws Exception {
      if (obj.getClass().isArray()) {
         return new ArrayIterator(obj);
      } else if (obj instanceof Collection) {
         return ((Collection)obj).iterator();
      } else if (obj instanceof Map) {
         return ((Map)obj).values().iterator();
      } else if (obj instanceof Iterator) {
         this.rlog.warn("Warning! The iterative  is an Iterator in the #foreach() loop at [" + i.getLine() + "," + i.getColumn() + "]" + " in template " + i.getTemplateName() + ". Because it's not resetable," + " if used in more than once, this may lead to" + " unexpected results.");
         return (Iterator)obj;
      } else if (obj instanceof Enumeration) {
         this.rlog.warn("Warning! The iterative  is an Enumeration in the #foreach() loop at [" + i.getLine() + "," + i.getColumn() + "]" + " in template " + i.getTemplateName() + ". Because it's not resetable," + " if used in more than once, this may lead to" + " unexpected results.");
         return new EnumerationIterator((Enumeration)obj);
      } else {
         this.rlog.warn("Could not determine type of iterator in #foreach loop  at [" + i.getLine() + "," + i.getColumn() + "]" + " in template " + i.getTemplateName());
         return null;
      }
   }

   public VelMethod getMethod(Object obj, String methodName, Object[] args, Info i) throws Exception {
      if (obj == null) {
         return null;
      } else {
         Method m = introspector.getMethod(obj.getClass(), methodName, args);
         return m != null ? new VelMethodImpl(m) : null;
      }
   }

   public VelPropertyGet getPropertyGet(Object obj, String identifier, Info i) throws Exception {
      Class claz = obj.getClass();
      AbstractExecutor executor = new PropertyExecutor(this.rlog, introspector, claz, identifier);
      if (!((AbstractExecutor)executor).isAlive()) {
         executor = new GetExecutor(this.rlog, introspector, claz, identifier);
      }

      if (!((AbstractExecutor)executor).isAlive()) {
         executor = new BooleanPropertyExecutor(this.rlog, introspector, claz, identifier);
      }

      return executor != null ? new VelGetterImpl((AbstractExecutor)executor) : null;
   }

   public VelPropertySet getPropertySet(Object obj, String identifier, Object arg, Info i) throws Exception {
      Class claz = obj.getClass();
      VelPropertySet vs = null;
      VelMethod vm = null;

      try {
         Object[] params = new Object[]{arg};

         try {
            vm = this.getMethod(obj, "set" + identifier, params, i);
            if (vm == null) {
               throw new NoSuchMethodException();
            }
         } catch (NoSuchMethodException var11) {
            StringBuffer sb = new StringBuffer("set");
            sb.append(identifier);
            if (Character.isLowerCase(sb.charAt(3))) {
               sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
            } else {
               sb.setCharAt(3, Character.toLowerCase(sb.charAt(3)));
            }

            vm = this.getMethod(obj, sb.toString(), params, i);
            if (vm == null) {
               throw new NoSuchMethodException();
            }
         }
      } catch (NoSuchMethodException var12) {
         if ((class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map).isAssignableFrom(claz)) {
            Object[] params = new Object[]{new Object(), new Object()};
            vm = this.getMethod(obj, "put", params, i);
            if (vm != null) {
               return new VelSetterImpl(vm, identifier);
            }
         }
      }

      return vm != null ? new VelSetterImpl(vm) : null;
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public class VelSetterImpl implements VelPropertySet {
      VelMethod vm = null;
      String putKey = null;

      public VelSetterImpl(VelMethod velmethod) {
         this.vm = velmethod;
      }

      public VelSetterImpl(VelMethod velmethod, String key) {
         this.vm = velmethod;
         this.putKey = key;
      }

      private VelSetterImpl() {
      }

      public Object invoke(Object o, Object value) throws Exception {
         ArrayList al = new ArrayList();
         if (this.putKey != null) {
            al.add(this.putKey);
            al.add(value);
         } else {
            al.add(value);
         }

         return this.vm.invoke(o, al.toArray());
      }

      public boolean isCacheable() {
         return true;
      }

      public String getMethodName() {
         return this.vm.getMethodName();
      }
   }

   public class VelGetterImpl implements VelPropertyGet {
      AbstractExecutor ae = null;

      public VelGetterImpl(AbstractExecutor exec) {
         this.ae = exec;
      }

      private VelGetterImpl() {
      }

      public Object invoke(Object o) throws Exception {
         return this.ae.execute(o);
      }

      public boolean isCacheable() {
         return true;
      }

      public String getMethodName() {
         return this.ae.getMethod().getName();
      }
   }

   public class VelMethodImpl implements VelMethod {
      Method method = null;

      public VelMethodImpl(Method m) {
         this.method = m;
      }

      private VelMethodImpl() {
      }

      public Object invoke(Object o, Object[] params) throws Exception {
         return this.method.invoke(o, params);
      }

      public boolean isCacheable() {
         return true;
      }

      public String getMethodName() {
         return this.method.getName();
      }

      public Class getReturnType() {
         return this.method.getReturnType();
      }
   }
}
