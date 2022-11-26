package weblogic.ejb.container.ejbc.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import weblogic.utils.PlatformConstants;
import weblogic.utils.collections.AggregateKey;

public final class MethodSignature implements PlatformConstants {
   private static final int MAX_LINE = 80;
   private boolean printThrowsClause = true;
   private GenericMethodSignature genericMethodSig;
   private String name;
   private Method method;
   private Class returnType;
   private Class[] parameterTypes;
   private Type[] typeParameters;
   private String[] parameterNames;
   private Class[] exceptionTypes;
   private int modifiers;

   MethodSignature() {
      this.parameterTypes = new Class[0];
      this.exceptionTypes = new Class[0];
   }

   public MethodSignature(Method m) {
      this.setMethod(m);
      this.setName(m.getName());
      this.setReturnType(m.getReturnType());
      this.setParameterTypes(m.getParameterTypes());
      this.parameterNames = new String[this.parameterTypes.length];

      for(int i = 0; i < this.parameterNames.length; ++i) {
         this.parameterNames[i] = "arg" + i;
      }

      this.setExceptionTypes(m.getExceptionTypes());
      this.setModifiers(m.getModifiers());
      GenericMethodSignature gms = new GenericMethodSignature(m);
      this.setGenericMethodSignature(gms);
   }

   public MethodSignature(Method m, Class pClass) {
      this.setMethod(m);
      this.setName(m.getName());
      this.setReturnType(m.getReturnType());
      this.setTypeParameters(m.getTypeParameters());
      this.setParameterTypes(m.getParameterTypes());
      this.parameterNames = new String[this.parameterTypes.length];

      for(int i = 0; i < this.parameterNames.length; ++i) {
         this.parameterNames[i] = "arg" + i;
      }

      this.setExceptionTypes(m.getExceptionTypes());
      this.setModifiers(m.getModifiers());
      GenericMethodSignature gms = new GenericMethodSignature(m, pClass);
      this.setGenericMethodSignature(gms);
   }

   public MethodSignature(String s) throws MalformedMethodSignatureException {
      this.exceptionTypes = new Class[0];
      MethodSignatureParser parser = new MethodSignatureParser(s);
      if (!parser.matchSignature(this)) {
         throw new MalformedMethodSignatureException(s);
      }
   }

   public void setPrintThrowsClause(boolean b) {
      this.printThrowsClause = b;
   }

   public Object asNameAndParamTypesKey() {
      return new AggregateKey(this.name, new AggregateKey(this.parameterTypes));
   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean indentify) {
      StringBuffer sb = new StringBuffer();
      sb.append(Modifier.toString(this.modifiers & -129 & -65)).append(" ");
      String s = sb.toString();
      int i = s.indexOf("strict ");
      if (i != -1) {
         sb.insert(i + "strict".length(), "fp");
      }

      if (this.typeParameters != null && this.typeParameters.length > 0) {
         sb.append(" <");
         sb.append(this.getTypeParametersName());
         sb.append("> ");
      }

      if (this.returnType != null) {
         sb.append(this.genericMethodSig.getParameterizedName(this.getGenericReturnType())).append(" ");
      }

      sb.append(this.name).append("(");

      int idx;
      for(idx = 0; idx < this.parameterTypes.length; ++idx) {
         sb.append(this.genericMethodSig.getParameterizedName(this.getGenericParameterTypes()[idx])).append(" ").append(this.parameterNames[idx]);
         if (idx < this.parameterTypes.length - 1) {
            sb.append(", ");
         }
      }

      sb.append(")");
      if (this.printThrowsClause && this.exceptionTypes.length > 0) {
         idx = sb.length();
         sb.append(" throws ");
         Type[] genericExceptions = this.getGenericExceptionTypes();

         for(int i = 0; i < this.exceptionTypes.length; ++i) {
            if (genericExceptions.length != this.exceptionTypes.length) {
               sb.append(this.toJavaCode(this.exceptionTypes[i]));
            } else {
               sb.append(this.genericMethodSig.getParameterizedName(genericExceptions[i]));
            }

            if (i < this.exceptionTypes.length - 1) {
               sb.append(", ");
            }
         }

         if (indentify && sb.length() > 80) {
            sb.insert(idx, EOL + "    ");
         }
      }

      return sb.toString();
   }

   public void setParameterNames(int idx, String name) {
      this.parameterNames[idx] = name;
   }

   public int getParameterIndex(String name) throws Exception {
      for(int i = 0; i < this.parameterNames.length; ++i) {
         if (this.parameterNames[i].equals(name)) {
            return i;
         }
      }

      throw new Exception("No param named " + name);
   }

   public Class getParameterType(String name) throws Exception {
      int idx = this.getParameterIndex(name);
      return this.parameterTypes[idx];
   }

   public String getParameterNames(int idx) {
      return this.parameterNames[idx];
   }

   public String getParameterTypesAsArgs() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.parameterTypes.length; ++i) {
         if (i > 0) {
            sb.append(",");
         }

         sb.append(this.genericMethodSig.getParameterizedName(this.getGenericParameterTypes()[i]));
      }

      return sb.toString();
   }

   public String getParametersAsArgs() {
      StringBuffer sb = new StringBuffer(4 * this.parameterNames.length);

      for(int i = 0; i < this.parameterNames.length; ++i) {
         sb.append(this.parameterNames[i]);
         if (i < this.parameterNames.length - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public GenericMethodSignature getGenericMethodSignature() {
      return this.genericMethodSig;
   }

   public void setGenericMethodSignature(GenericMethodSignature gms) {
      this.genericMethodSig = gms;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      this.name = s;
   }

   public Method getMethod() {
      return this.method;
   }

   public void setMethod(Method m) {
      this.method = m;
   }

   public Class getReturnType() {
      return this.returnType;
   }

   public void setReturnType(Class c) {
      this.returnType = c;
   }

   public Type getGenericReturnType() {
      return this.method.getGenericReturnType();
   }

   public String getReturnTypeName() {
      return this.genericMethodSig.getParameterizedName(this.getGenericReturnType());
   }

   public Class[] getParameterTypes() {
      return this.parameterTypes;
   }

   public void setParameterTypes(Class[] c) {
      this.parameterTypes = c;
   }

   public Type[] getGenericParameterTypes() {
      return this.method.getGenericParameterTypes();
   }

   public Type[] getTypeParameters() {
      return this.typeParameters;
   }

   public void setTypeParameters(Type[] c) {
      this.typeParameters = c;
   }

   public String getTypeParametersName() {
      if (this.typeParameters != null && this.typeParameters.length != 0) {
         StringBuffer name = new StringBuffer();

         for(int j = 0; j < this.typeParameters.length; ++j) {
            Type t = this.typeParameters[j];
            if (t instanceof TypeVariable) {
               name.append(this.genericMethodSig.getRealNameForTypeVariable(t, true));
            }

            if (j < this.typeParameters.length - 1) {
               name.append(", ");
            }
         }

         return name.toString();
      } else {
         return "";
      }
   }

   public String[] getParameterNames() {
      return this.parameterNames;
   }

   public void setParameterNames(String[] s) {
      this.parameterNames = s;
   }

   public int getNumberOfParameters() {
      return this.parameterTypes.length;
   }

   public Class[] getExceptionTypes() {
      return this.exceptionTypes;
   }

   public void setExceptionTypes(Class[] c) {
      this.exceptionTypes = c;
   }

   public Type[] getGenericExceptionTypes() {
      return this.method.getGenericExceptionTypes();
   }

   public int getModifiers() {
      return this.modifiers;
   }

   public void setModifiers(int i) {
      this.modifiers = i;
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.modifiers);
   }

   public void setPublic(boolean b) {
      if (b) {
         this.modifiers |= 1;
         this.setPrivate(false);
         this.setProtected(false);
      } else {
         this.modifiers &= -2;
      }

   }

   public boolean isPrivate() {
      return Modifier.isPrivate(this.modifiers);
   }

   public void setPrivate(boolean b) {
      if (b) {
         this.modifiers |= 2;
         this.setPublic(false);
         this.setProtected(false);
      } else {
         this.modifiers &= -3;
      }

   }

   public boolean isProtected() {
      return Modifier.isProtected(this.modifiers);
   }

   public void setProtected(boolean b) {
      if (b) {
         this.modifiers |= 4;
         this.setPublic(false);
         this.setPrivate(false);
      } else {
         this.modifiers &= -5;
      }

   }

   public boolean isStatic() {
      return Modifier.isStatic(this.modifiers);
   }

   public void setStatic(boolean b) {
      if (b) {
         this.modifiers |= 8;
      } else {
         this.modifiers &= -9;
      }

   }

   public boolean isFinal() {
      return Modifier.isFinal(this.modifiers);
   }

   public void setFinal(boolean b) {
      if (b) {
         this.modifiers |= 16;
         this.setAbstract(false);
      } else {
         this.modifiers &= -17;
      }

   }

   public boolean isSynchronized() {
      return Modifier.isSynchronized(this.modifiers);
   }

   public void setSynchronized(boolean b) {
      if (b) {
         this.modifiers |= 32;
      } else {
         this.modifiers &= -33;
      }

   }

   public boolean isVolatile() {
      return Modifier.isVolatile(this.modifiers);
   }

   public void setVolatile(boolean b) {
      if (b) {
         this.modifiers |= 64;
      } else {
         this.modifiers &= -65;
      }

   }

   public boolean isTransient() {
      return Modifier.isTransient(this.modifiers);
   }

   public void setTransient(boolean b) {
      if (b) {
         this.modifiers |= 128;
      } else {
         this.modifiers &= -129;
      }

   }

   public boolean isNative() {
      return Modifier.isNative(this.modifiers);
   }

   public void setNative(boolean b) {
      if (b) {
         this.modifiers |= 256;
         this.setAbstract(false);
      } else {
         this.modifiers &= -257;
      }

   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.modifiers);
   }

   public void setAbstract(boolean b) {
      if (b) {
         this.modifiers |= 1024;
         this.setFinal(false);
         this.setNative(false);
      } else {
         this.modifiers &= -1025;
      }

   }

   public static boolean equalsMethodsBySig(MethodSignature sig1, MethodSignature sig2) {
      if (sig1 != null && sig2 != null) {
         if (sig1.getName().equals(sig2.getName())) {
            if (sig1.getNumberOfParameters() != sig2.getNumberOfParameters()) {
               return false;
            } else if (sig1.getParameterTypesAsArgs().equals(sig2.getParameterTypesAsArgs())) {
               return true;
            } else {
               for(int i = 0; i < sig1.getNumberOfParameters(); ++i) {
                  String name1 = sig1.getGenericMethodSignature().getParameterizedName(sig1.getGenericParameterTypes()[i]);
                  String name2 = sig2.getGenericMethodSignature().getParameterizedName(sig2.getGenericParameterTypes()[i]);
                  if (!name1.equals(name2) && !sig1.equalsParameterTypeWithClass(sig1.getGenericParameterTypes()[i], sig2.getGenericParameterTypes()[i]) && !sig2.equalsParameterTypeWithClass(sig2.getGenericParameterTypes()[i], sig1.getGenericParameterTypes()[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean equalsParameterTypeWithClass(Type parameter, Type classType) {
      if (classType instanceof Class) {
         String name = this.genericMethodSig.getParameterizedName(parameter);
         int idx = name.indexOf("<");
         if (idx > 0 && idx < name.length() - 1) {
            String pre = name.substring(0, idx);
            if (pre.equals(((Class)classType).getName())) {
               return true;
            }
         }
      }

      return false;
   }

   public static void compare(MethodSignature sig1, MethodSignature sig2) {
      if (sig1.getName().equals(sig2.getName())) {
         System.out.println("name ok.");
      } else {
         System.out.println(sig1.getName() + " != " + sig2.getName());
      }

      if (sig1.getReturnType().equals(sig2.getReturnType())) {
         System.out.println("return type ok.");
      } else {
         System.out.println(sig1.getReturnType() + " != " + sig2.getReturnType());
      }

      if (sig1.getModifiers() == sig2.getModifiers()) {
         System.out.println("mods ok.");
      } else {
         System.out.println(sig1.getModifiers() + " != " + sig2.getModifiers());
      }

   }

   private String toJavaCode(Class type) {
      int arrayDim;
      for(arrayDim = 0; type.isArray(); type = type.getComponentType()) {
         ++arrayDim;
      }

      StringBuffer sb = new StringBuffer(type.getName());

      for(Class dc = type.getDeclaringClass(); dc != null; dc = dc.getDeclaringClass()) {
         sb.setCharAt(dc.getName().length(), '.');
      }

      for(int i = 0; i < arrayDim; ++i) {
         sb.append("[]");
      }

      return sb.toString();
   }
}
