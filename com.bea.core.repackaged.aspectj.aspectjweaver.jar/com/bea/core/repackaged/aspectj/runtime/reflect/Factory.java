package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.JoinPoint;
import com.bea.core.repackaged.aspectj.lang.Signature;
import com.bea.core.repackaged.aspectj.lang.reflect.AdviceSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.CatchClauseSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.ConstructorSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.FieldSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.InitializerSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.LockSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.MethodSignature;
import com.bea.core.repackaged.aspectj.lang.reflect.SourceLocation;
import com.bea.core.repackaged.aspectj.lang.reflect.UnlockSignature;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.StringTokenizer;

public final class Factory {
   Class lexicalClass;
   ClassLoader lookupClassLoader;
   String filename;
   int count;
   static Hashtable prims = new Hashtable();
   private static Object[] NO_ARGS;
   // $FF: synthetic field
   static Class class$java$lang$ClassNotFoundException;

   static Class makeClass(String s, ClassLoader loader) {
      if (s.equals("*")) {
         return null;
      } else {
         Class ret = (Class)prims.get(s);
         if (ret != null) {
            return ret;
         } else {
            try {
               return loader == null ? Class.forName(s) : Class.forName(s, false, loader);
            } catch (ClassNotFoundException var4) {
               return class$java$lang$ClassNotFoundException == null ? (class$java$lang$ClassNotFoundException = class$("java.lang.ClassNotFoundException")) : class$java$lang$ClassNotFoundException;
            }
         }
      }
   }

   public Factory(String filename, Class lexicalClass) {
      this.filename = filename;
      this.lexicalClass = lexicalClass;
      this.count = 0;
      this.lookupClassLoader = lexicalClass.getClassLoader();
   }

   public JoinPoint.StaticPart makeSJP(String kind, String modifiers, String methodName, String declaringType, String paramTypes, String paramNames, String exceptionTypes, String returnType, int l) {
      Signature sig = this.makeMethodSig(modifiers, methodName, declaringType, paramTypes, paramNames, exceptionTypes, returnType);
      return new JoinPointImpl.StaticPartImpl(this.count++, kind, sig, this.makeSourceLoc(l, -1));
   }

   public JoinPoint.StaticPart makeSJP(String kind, String modifiers, String methodName, String declaringType, String paramTypes, String paramNames, String returnType, int l) {
      Signature sig = this.makeMethodSig(modifiers, methodName, declaringType, paramTypes, paramNames, "", returnType);
      return new JoinPointImpl.StaticPartImpl(this.count++, kind, sig, this.makeSourceLoc(l, -1));
   }

   public JoinPoint.StaticPart makeSJP(String kind, Signature sig, SourceLocation loc) {
      return new JoinPointImpl.StaticPartImpl(this.count++, kind, sig, loc);
   }

   public JoinPoint.StaticPart makeSJP(String kind, Signature sig, int l, int c) {
      return new JoinPointImpl.StaticPartImpl(this.count++, kind, sig, this.makeSourceLoc(l, c));
   }

   public JoinPoint.StaticPart makeSJP(String kind, Signature sig, int l) {
      return new JoinPointImpl.StaticPartImpl(this.count++, kind, sig, this.makeSourceLoc(l, -1));
   }

   public JoinPoint.EnclosingStaticPart makeESJP(String kind, Signature sig, SourceLocation loc) {
      return new JoinPointImpl.EnclosingStaticPartImpl(this.count++, kind, sig, loc);
   }

   public JoinPoint.EnclosingStaticPart makeESJP(String kind, Signature sig, int l, int c) {
      return new JoinPointImpl.EnclosingStaticPartImpl(this.count++, kind, sig, this.makeSourceLoc(l, c));
   }

   public JoinPoint.EnclosingStaticPart makeESJP(String kind, Signature sig, int l) {
      return new JoinPointImpl.EnclosingStaticPartImpl(this.count++, kind, sig, this.makeSourceLoc(l, -1));
   }

   public static JoinPoint.StaticPart makeEncSJP(Member member) {
      Signature sig = null;
      String kind = null;
      if (member instanceof Method) {
         Method method = (Method)member;
         sig = new MethodSignatureImpl(method.getModifiers(), method.getName(), method.getDeclaringClass(), method.getParameterTypes(), new String[method.getParameterTypes().length], method.getExceptionTypes(), method.getReturnType());
         kind = "method-execution";
      } else {
         if (!(member instanceof Constructor)) {
            throw new IllegalArgumentException("member must be either a method or constructor");
         }

         Constructor cons = (Constructor)member;
         sig = new ConstructorSignatureImpl(cons.getModifiers(), cons.getDeclaringClass(), cons.getParameterTypes(), new String[cons.getParameterTypes().length], cons.getExceptionTypes());
         kind = "constructor-execution";
      }

      return new JoinPointImpl.EnclosingStaticPartImpl(-1, kind, (Signature)sig, (SourceLocation)null);
   }

   public static JoinPoint makeJP(JoinPoint.StaticPart staticPart, Object _this, Object target) {
      return new JoinPointImpl(staticPart, _this, target, NO_ARGS);
   }

   public static JoinPoint makeJP(JoinPoint.StaticPart staticPart, Object _this, Object target, Object arg0) {
      return new JoinPointImpl(staticPart, _this, target, new Object[]{arg0});
   }

   public static JoinPoint makeJP(JoinPoint.StaticPart staticPart, Object _this, Object target, Object arg0, Object arg1) {
      return new JoinPointImpl(staticPart, _this, target, new Object[]{arg0, arg1});
   }

   public static JoinPoint makeJP(JoinPoint.StaticPart staticPart, Object _this, Object target, Object[] args) {
      return new JoinPointImpl(staticPart, _this, target, args);
   }

   public MethodSignature makeMethodSig(String stringRep) {
      MethodSignatureImpl ret = new MethodSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public MethodSignature makeMethodSig(String modifiers, String methodName, String declaringType, String paramTypes, String paramNames, String exceptionTypes, String returnType) {
      int modifiersAsInt = Integer.parseInt(modifiers, 16);
      Class declaringTypeClass = makeClass(declaringType, this.lookupClassLoader);
      StringTokenizer st = new StringTokenizer(paramTypes, ":");
      int numParams = st.countTokens();
      Class[] paramTypeClasses = new Class[numParams];

      for(int i = 0; i < numParams; ++i) {
         paramTypeClasses[i] = makeClass(st.nextToken(), this.lookupClassLoader);
      }

      st = new StringTokenizer(paramNames, ":");
      numParams = st.countTokens();
      String[] paramNamesArray = new String[numParams];

      for(int i = 0; i < numParams; ++i) {
         paramNamesArray[i] = st.nextToken();
      }

      st = new StringTokenizer(exceptionTypes, ":");
      numParams = st.countTokens();
      Class[] exceptionTypeClasses = new Class[numParams];

      for(int i = 0; i < numParams; ++i) {
         exceptionTypeClasses[i] = makeClass(st.nextToken(), this.lookupClassLoader);
      }

      Class returnTypeClass = makeClass(returnType, this.lookupClassLoader);
      MethodSignatureImpl ret = new MethodSignatureImpl(modifiersAsInt, methodName, declaringTypeClass, paramTypeClasses, paramNamesArray, exceptionTypeClasses, returnTypeClass);
      return ret;
   }

   public MethodSignature makeMethodSig(int modifiers, String name, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes, Class returnType) {
      MethodSignatureImpl ret = new MethodSignatureImpl(modifiers, name, declaringType, parameterTypes, parameterNames, exceptionTypes, returnType);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public ConstructorSignature makeConstructorSig(String stringRep) {
      ConstructorSignatureImpl ret = new ConstructorSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public ConstructorSignature makeConstructorSig(String modifiers, String declaringType, String paramTypes, String paramNames, String exceptionTypes) {
      int modifiersAsInt = Integer.parseInt(modifiers, 16);
      Class declaringTypeClass = makeClass(declaringType, this.lookupClassLoader);
      StringTokenizer st = new StringTokenizer(paramTypes, ":");
      int numParams = st.countTokens();
      Class[] paramTypeClasses = new Class[numParams];

      for(int i = 0; i < numParams; ++i) {
         paramTypeClasses[i] = makeClass(st.nextToken(), this.lookupClassLoader);
      }

      st = new StringTokenizer(paramNames, ":");
      numParams = st.countTokens();
      String[] paramNamesArray = new String[numParams];

      for(int i = 0; i < numParams; ++i) {
         paramNamesArray[i] = st.nextToken();
      }

      st = new StringTokenizer(exceptionTypes, ":");
      numParams = st.countTokens();
      Class[] exceptionTypeClasses = new Class[numParams];

      for(int i = 0; i < numParams; ++i) {
         exceptionTypeClasses[i] = makeClass(st.nextToken(), this.lookupClassLoader);
      }

      ConstructorSignatureImpl ret = new ConstructorSignatureImpl(modifiersAsInt, declaringTypeClass, paramTypeClasses, paramNamesArray, exceptionTypeClasses);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public ConstructorSignature makeConstructorSig(int modifiers, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes) {
      ConstructorSignatureImpl ret = new ConstructorSignatureImpl(modifiers, declaringType, parameterTypes, parameterNames, exceptionTypes);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public FieldSignature makeFieldSig(String stringRep) {
      FieldSignatureImpl ret = new FieldSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public FieldSignature makeFieldSig(String modifiers, String name, String declaringType, String fieldType) {
      int modifiersAsInt = Integer.parseInt(modifiers, 16);
      Class declaringTypeClass = makeClass(declaringType, this.lookupClassLoader);
      Class fieldTypeClass = makeClass(fieldType, this.lookupClassLoader);
      FieldSignatureImpl ret = new FieldSignatureImpl(modifiersAsInt, name, declaringTypeClass, fieldTypeClass);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public FieldSignature makeFieldSig(int modifiers, String name, Class declaringType, Class fieldType) {
      FieldSignatureImpl ret = new FieldSignatureImpl(modifiers, name, declaringType, fieldType);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public AdviceSignature makeAdviceSig(String stringRep) {
      AdviceSignatureImpl ret = new AdviceSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public AdviceSignature makeAdviceSig(String modifiers, String name, String declaringType, String paramTypes, String paramNames, String exceptionTypes, String returnType) {
      int modifiersAsInt = Integer.parseInt(modifiers, 16);
      Class declaringTypeClass = makeClass(declaringType, this.lookupClassLoader);
      StringTokenizer st = new StringTokenizer(paramTypes, ":");
      int numParams = st.countTokens();
      Class[] paramTypeClasses = new Class[numParams];

      for(int i = 0; i < numParams; ++i) {
         paramTypeClasses[i] = makeClass(st.nextToken(), this.lookupClassLoader);
      }

      st = new StringTokenizer(paramNames, ":");
      numParams = st.countTokens();
      String[] paramNamesArray = new String[numParams];

      for(int i = 0; i < numParams; ++i) {
         paramNamesArray[i] = st.nextToken();
      }

      st = new StringTokenizer(exceptionTypes, ":");
      numParams = st.countTokens();
      Class[] exceptionTypeClasses = new Class[numParams];

      for(int i = 0; i < numParams; ++i) {
         exceptionTypeClasses[i] = makeClass(st.nextToken(), this.lookupClassLoader);
      }

      Class returnTypeClass = makeClass(returnType, this.lookupClassLoader);
      AdviceSignatureImpl ret = new AdviceSignatureImpl(modifiersAsInt, name, declaringTypeClass, paramTypeClasses, paramNamesArray, exceptionTypeClasses, returnTypeClass);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public AdviceSignature makeAdviceSig(int modifiers, String name, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes, Class returnType) {
      AdviceSignatureImpl ret = new AdviceSignatureImpl(modifiers, name, declaringType, parameterTypes, parameterNames, exceptionTypes, returnType);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public InitializerSignature makeInitializerSig(String stringRep) {
      InitializerSignatureImpl ret = new InitializerSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public InitializerSignature makeInitializerSig(String modifiers, String declaringType) {
      int modifiersAsInt = Integer.parseInt(modifiers, 16);
      Class declaringTypeClass = makeClass(declaringType, this.lookupClassLoader);
      InitializerSignatureImpl ret = new InitializerSignatureImpl(modifiersAsInt, declaringTypeClass);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public InitializerSignature makeInitializerSig(int modifiers, Class declaringType) {
      InitializerSignatureImpl ret = new InitializerSignatureImpl(modifiers, declaringType);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public CatchClauseSignature makeCatchClauseSig(String stringRep) {
      CatchClauseSignatureImpl ret = new CatchClauseSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public CatchClauseSignature makeCatchClauseSig(String declaringType, String parameterType, String parameterName) {
      Class declaringTypeClass = makeClass(declaringType, this.lookupClassLoader);
      StringTokenizer st = new StringTokenizer(parameterType, ":");
      Class parameterTypeClass = makeClass(st.nextToken(), this.lookupClassLoader);
      st = new StringTokenizer(parameterName, ":");
      String parameterNameForReturn = st.nextToken();
      CatchClauseSignatureImpl ret = new CatchClauseSignatureImpl(declaringTypeClass, parameterTypeClass, parameterNameForReturn);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public CatchClauseSignature makeCatchClauseSig(Class declaringType, Class parameterType, String parameterName) {
      CatchClauseSignatureImpl ret = new CatchClauseSignatureImpl(declaringType, parameterType, parameterName);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public LockSignature makeLockSig(String stringRep) {
      LockSignatureImpl ret = new LockSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public LockSignature makeLockSig() {
      Class declaringTypeClass = makeClass("Ljava/lang/Object;", this.lookupClassLoader);
      LockSignatureImpl ret = new LockSignatureImpl(declaringTypeClass);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public LockSignature makeLockSig(Class declaringType) {
      LockSignatureImpl ret = new LockSignatureImpl(declaringType);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public UnlockSignature makeUnlockSig(String stringRep) {
      UnlockSignatureImpl ret = new UnlockSignatureImpl(stringRep);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public UnlockSignature makeUnlockSig() {
      Class declaringTypeClass = makeClass("Ljava/lang/Object;", this.lookupClassLoader);
      UnlockSignatureImpl ret = new UnlockSignatureImpl(declaringTypeClass);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public UnlockSignature makeUnlockSig(Class declaringType) {
      UnlockSignatureImpl ret = new UnlockSignatureImpl(declaringType);
      ret.setLookupClassLoader(this.lookupClassLoader);
      return ret;
   }

   public SourceLocation makeSourceLoc(int line, int col) {
      return new SourceLocationImpl(this.lexicalClass, this.filename, line);
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      prims.put("void", Void.TYPE);
      prims.put("boolean", Boolean.TYPE);
      prims.put("byte", Byte.TYPE);
      prims.put("char", Character.TYPE);
      prims.put("short", Short.TYPE);
      prims.put("int", Integer.TYPE);
      prims.put("long", Long.TYPE);
      prims.put("float", Float.TYPE);
      prims.put("double", Double.TYPE);
      NO_ARGS = new Object[0];
   }
}
