package com.sun.faces.facelets.el;

import com.sun.faces.facelets.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.el.FunctionMapper;

public final class DefaultFunctionMapper extends FunctionMapper implements Externalizable {
   private static final long serialVersionUID = 1L;
   private Map functions = null;

   public Method resolveFunction(String prefix, String localName) {
      if (this.functions != null) {
         Function f = (Function)this.functions.get(prefix + ":" + localName);
         return f.getMethod();
      } else {
         return null;
      }
   }

   public void addFunction(String prefix, String localName, Method m) {
      if (this.functions == null) {
         this.functions = new HashMap();
      }

      Function f = new Function(prefix, localName, m);
      synchronized(this) {
         this.functions.put(prefix + ":" + localName, f);
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.functions);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.functions = (Map)in.readObject();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(128);
      sb.append("FunctionMapper[\n");
      Iterator itr = this.functions.values().iterator();

      while(itr.hasNext()) {
         sb.append(itr.next()).append('\n');
      }

      sb.append(']');
      return sb.toString();
   }

   private static class Function implements Externalizable {
      private static final long serialVersionUID = 1L;
      protected transient Method m;
      protected String owner;
      protected String name;
      protected String[] types;
      protected String prefix;
      protected String localName;

      public Function(String prefix, String localName, Method m) {
         if (localName == null) {
            throw new NullPointerException("LocalName cannot be null");
         } else if (m == null) {
            throw new NullPointerException("Method cannot be null");
         } else {
            this.prefix = prefix;
            this.localName = localName;
            this.m = m;
         }
      }

      public Function() {
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeUTF(this.prefix != null ? this.prefix : "");
         out.writeUTF(this.localName);
         out.writeUTF(this.m.getDeclaringClass().getName());
         out.writeUTF(this.m.getName());
         out.writeObject(ReflectionUtil.toTypeNameArray(this.m.getParameterTypes()));
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.prefix = in.readUTF();
         if ("".equals(this.prefix)) {
            this.prefix = null;
         }

         this.localName = in.readUTF();
         this.owner = in.readUTF();
         this.name = in.readUTF();
         this.types = (String[])((String[])in.readObject());
      }

      public Method getMethod() {
         if (this.m == null) {
            try {
               Class t = ReflectionUtil.forName(this.owner);
               Class[] p = ReflectionUtil.toTypeArray(this.types);
               this.m = t.getMethod(this.name, p);
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException var3) {
               var3.printStackTrace();
            }
         }

         return this.m;
      }

      public boolean matches(String prefix, String localName) {
         if (this.prefix != null) {
            if (prefix == null) {
               return false;
            }

            if (!this.prefix.equals(prefix)) {
               return false;
            }
         }

         return this.localName.equals(localName);
      }

      public boolean equals(Object obj) {
         if (obj instanceof Function) {
            return this.hashCode() == obj.hashCode();
         } else {
            return false;
         }
      }

      public int hashCode() {
         return (this.prefix + this.localName).hashCode();
      }

      public String toString() {
         StringBuffer sb = new StringBuffer(32);
         sb.append("Function[");
         if (this.prefix != null) {
            sb.append(this.prefix).append(':');
         }

         sb.append(this.name).append("] ");
         sb.append(this.m);
         return sb.toString();
      }
   }
}
