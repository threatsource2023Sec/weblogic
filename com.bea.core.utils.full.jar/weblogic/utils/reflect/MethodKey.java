package weblogic.utils.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodKey {
   Method m;

   public MethodKey(Method m) {
      this.m = m;
   }

   public boolean equals(Object o) {
      try {
         Method m2 = ((MethodKey)o).m;
         if (!this.m.getName().equals(m2.getName())) {
            return false;
         } else if (this.m.getReturnType() != m2.getReturnType()) {
            return false;
         } else {
            Class[] params = this.m.getParameterTypes();
            Class[] params2 = m2.getParameterTypes();
            if (params.length != params2.length) {
               return false;
            } else {
               for(int i = 0; i < params.length; ++i) {
                  if (params[i] != params2[i]) {
                     return false;
                  }
               }

               Class[] exceps = this.m.getExceptionTypes();
               Class[] exceps2 = m2.getExceptionTypes();
               if (exceps.length != exceps2.length) {
                  return false;
               } else {
                  for(int i = 0; i < exceps.length; ++i) {
                     if (exceps[i] != exceps2[i]) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      } catch (ClassCastException var8) {
         return false;
      }
   }

   public int hashCode() {
      int h = this.m.getName().hashCode();
      h ^= this.m.getReturnType().hashCode();
      Class[] params = this.m.getParameterTypes();

      for(int i = 0; i < params.length; ++i) {
         h ^= params[i].hashCode();
      }

      Class[] exceps = this.m.getExceptionTypes();

      for(int i = 0; i < exceps.length; ++i) {
         h ^= exceps[i].hashCode();
      }

      return h;
   }

   private String asString(Class[] c) {
      if (c.length == 0) {
         return "";
      } else {
         StringBuffer o = new StringBuffer(" ");

         for(int i = 0; i < c.length; ++i) {
            o = o.append(c[i].getName());
            if (i < c.length - 1) {
               o = o.append(", ");
            }
         }

         return o.toString();
      }
   }

   public String toString() {
      Class[] exceptions = this.m.getExceptionTypes();
      Class[] parameters = this.m.getParameterTypes();
      if (exceptions.length == 0 && parameters.length == 0) {
         return Modifier.toString(this.m.getModifiers()) + " " + this.m.getReturnType() + " " + this.m.getName() + "()";
      } else if (exceptions.length != 0 && parameters.length == 0) {
         return Modifier.toString(this.m.getModifiers()) + " " + this.m.getReturnType() + " " + this.m.getName() + "() " + this.asString(this.m.getExceptionTypes());
      } else {
         return exceptions.length == 0 && parameters.length != 0 ? Modifier.toString(this.m.getModifiers()) + " " + this.m.getReturnType() + " " + this.m.getName() + "(" + this.asString(this.m.getParameterTypes()) + ")" : Modifier.toString(this.m.getModifiers()) + " " + this.m.getReturnType() + " " + this.m.getName() + "(" + this.asString(this.m.getParameterTypes()) + ") " + this.asString(this.m.getExceptionTypes());
      }
   }
}
