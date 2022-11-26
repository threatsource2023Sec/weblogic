package weblogic.management.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class MBeanReflector {
   private final Set attributes = new TreeSet();
   private final Set operations = new TreeSet();
   private final Class subject;

   public MBeanReflector(Class subjectClass) {
      this.subject = subjectClass;
      this.initialize(this.subject);
   }

   public MBeanReflector(String subjectClassName) {
      try {
         this.subject = AttributeInfo.Helper.findClass(subjectClassName);
      } catch (ClassNotFoundException var3) {
         throw new NoClassDefFoundError(var3.getMessage());
      }

      this.initialize(this.subject);
   }

   public Class getSubject() {
      return this.subject;
   }

   public Operation[] getOperations() {
      Operation[] result = new Operation[this.operations.size()];
      return (Operation[])((Operation[])this.operations.toArray(result));
   }

   public Attribute[] getAttributes() {
      Attribute[] result = new Attribute[this.attributes.size()];
      return (Attribute[])((Attribute[])this.attributes.toArray(result));
   }

   public Method getAttributeGetMethod(Attribute attribute) {
      Class[] paramTypes = new Class[0];
      Method m = this.getMethod("get" + attribute.getName(), paramTypes);
      return m != null ? m : this.getMethod("is" + attribute.getName(), paramTypes);
   }

   public Method getAttributeSetMethod(Attribute attribute) {
      Class[] paramTypes = new Class[]{attribute.getType()};
      return this.getMethod("set" + attribute.getName(), paramTypes);
   }

   public Method getAttributeAddMethod(Attribute attribute) {
      Class[] paramTypes = new Class[]{attribute.getType().getComponentType()};
      return this.getMethod("add" + attribute.getSingularName(), paramTypes);
   }

   public Method getAttributeRemoveMethod(Attribute attribute) {
      Class[] paramTypes = new Class[]{attribute.getType().getComponentType()};
      return this.getMethod("remove" + attribute.getSingularName(), paramTypes);
   }

   private Method getMethod(String s, Class[] paramTypes) {
      try {
         return this.subject.getMethod(s, paramTypes);
      } catch (NoSuchMethodException var4) {
         return null;
      }
   }

   private void initialize(Class subject) {
      Method[] methods = subject.getMethods();
      List setters = new ArrayList();

      Method method;
      Attribute attribute;
      for(int i = 0; i < methods.length; ++i) {
         method = methods[i];
         if (method.getName().startsWith("set")) {
            setters.add(method);
         } else {
            attribute = MBeanReflector.Attribute.attributeFromMethod(method);
            if (attribute != null) {
               this.attributes.add(attribute);
            } else {
               this.operations.add(new Operation(method));
            }
         }
      }

      Iterator i = setters.iterator();

      while(true) {
         do {
            if (!i.hasNext()) {
               return;
            }

            method = (Method)i.next();
            attribute = MBeanReflector.Attribute.attributeFromMethod(method);
         } while(attribute != null && this.attributes.contains(attribute));

         Operation operation = new Operation(method);
         this.operations.add(operation);
      }
   }

   public static final class Operation implements Comparable {
      private final Method method;

      Operation(Method method) {
         this.method = method;
      }

      public Method getMethod() {
         return this.method;
      }

      public int hashCode() {
         return this.method.getName().hashCode();
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Operation)) {
            return false;
         } else {
            Operation other = (Operation)o;
            if (other.method == this.method) {
               return true;
            } else if (!other.method.getName().equals(this.method.getName())) {
               return false;
            } else {
               Class[] params1 = this.method.getParameterTypes();
               Class[] params2 = other.method.getParameterTypes();
               if (params1.length != params2.length) {
                  return false;
               } else {
                  for(int i = 0; i < params1.length; ++i) {
                     if (params1[i] != params2[i]) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      }

      public int compareTo(Object o) {
         Operation other = (Operation)o;
         if (this.equals(other)) {
            return 0;
         } else {
            int result = other.method.getName().compareTo(this.method.getName());
            return result != 0 ? result : System.identityHashCode(other) - System.identityHashCode(this);
         }
      }
   }

   public static final class Attribute implements Comparable {
      private final String name;
      private final Class type;

      Attribute(String name, Class type) {
         this.name = name;
         this.type = type;
      }

      private static Attribute attributeFromMethod(Method method) {
         String methodName = method.getName();
         if (methodName.startsWith("get") && method.getReturnType() != Void.TYPE && method.getParameterTypes().length == 0) {
            return new Attribute(methodName.substring(3), method.getReturnType());
         } else if (methodName.startsWith("is") && method.getReturnType() == Boolean.TYPE && method.getParameterTypes().length == 0) {
            return new Attribute(methodName.substring(2), Boolean.TYPE);
         } else {
            return methodName.startsWith("set") && method.getReturnType() == Void.TYPE && method.getParameterTypes().length == 1 ? new Attribute(methodName.substring(3), method.getParameterTypes()[0]) : null;
         }
      }

      public String getName() {
         return this.name;
      }

      public Class getType() {
         return this.type;
      }

      public String getSingularName() {
         if (this.name.endsWith("ies")) {
            return this.name.substring(0, this.name.length() - 3) + 'y';
         } else {
            return this.name.endsWith("s") ? this.name.substring(0, this.name.length() - 1) : this.name;
         }
      }

      public String getFieldName() {
         StringBuffer buf = new StringBuffer(this.name);
         buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));

         for(int i = 1; i < buf.length() && !Character.isLowerCase(buf.charAt(i)) && (i + 1 >= buf.length() || !Character.isLowerCase(buf.charAt(i + 1))); ++i) {
            buf.setCharAt(i, Character.toLowerCase(buf.charAt(i)));
         }

         return buf.toString();
      }

      public int hashCode() {
         return this.name.hashCode();
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Attribute)) {
            return false;
         } else {
            Attribute other = (Attribute)o;
            return other.type == this.type && other.name.equals(this.name);
         }
      }

      public int compareTo(Object o) {
         Attribute other = (Attribute)o;
         if (this.equals(other)) {
            return 0;
         } else {
            int result = this.name.compareTo(other.name);
            return result != 0 ? result : System.identityHashCode(other) - System.identityHashCode(this);
         }
      }
   }
}
