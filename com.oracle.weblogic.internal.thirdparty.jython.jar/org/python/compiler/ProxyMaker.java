package org.python.compiler;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;
import org.python.util.Generic;

public class ProxyMaker extends ProxyCodeHelpers implements ClassConstants, Opcodes {
   protected final Class superclass;
   protected final Class[] interfaces;
   Set names;
   Set supernames;
   Set namesAndSigs;
   public ClassFile classfile;
   public String myClass;

   /** @deprecated */
   @Deprecated
   public ProxyMaker(String superclassName, Class superclass) {
      this("org.python.proxies." + superclassName, superclass.isInterface() ? Object.class : superclass, superclass.isInterface() ? new Class[]{superclass} : new Class[0]);
   }

   public ProxyMaker(String proxyClassName, Class superclass, Class... interfaces) {
      this.supernames = Generic.set();
      this.myClass = proxyClassName;
      if (superclass == null) {
         superclass = Object.class;
      }

      if (superclass.isInterface()) {
         throw new IllegalArgumentException("Given an interface,  " + superclass.getName() + ", for a proxy superclass");
      } else {
         this.superclass = superclass;
         if (interfaces == null) {
            interfaces = new Class[0];
         }

         Class[] var4 = interfaces;
         int var5 = interfaces.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class interfac = var4[var6];
            if (!interfac.isInterface()) {
               throw new IllegalArgumentException("All classes in the interfaces array must be interfaces, unlike " + interfac.getName());
            }
         }

         this.interfaces = interfaces;
      }
   }

   public void doConstants() throws Exception {
      Code code = this.classfile.addMethod("<clinit>", makeSig("V", new String[0]), 8);
      code.return_();
   }

   public void callSuper(Code var1, String var2, String var3, Class[] var4, Class var5, boolean var6) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void doJavaCall(Code code, String name, String type, String jcallName) throws Exception {
      code.invokevirtual("org/python/core/PyObject", jcallName, makeSig("Lorg/python/core/PyObject;", new String[]{"[Ljava/lang/Object;"}));
      code.invokestatic("org/python/core/Py", "py2" + name, makeSig(type, new String[]{"Lorg/python/core/PyObject;"}));
   }

   public void getArgs(Code var1, Class[] var2) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void callMethod(Code var1, String var2, Class[] var3, Class var4, Class[] var5) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void addMethod(Method method, int access) throws Exception {
      this.addMethod(method.getName(), method.getReturnType(), method.getParameterTypes(), method.getExceptionTypes(), access, method.getDeclaringClass());
   }

   public void addMethod(String name, Class ret, Class[] parameters, Class[] exceptions, int access, Class declaringClass) throws Exception {
      this.addMethod(name, name, ret, parameters, exceptions, access, declaringClass, (ProxyCodeHelpers.AnnotationDescr[])null, (ProxyCodeHelpers.AnnotationDescr[][])null);
   }

   public void addMethod(String name, String pyName, Class ret, Class[] parameters, Class[] exceptions, int access, Class declaringClass, ProxyCodeHelpers.AnnotationDescr[] methodAnnotations, ProxyCodeHelpers.AnnotationDescr[][] parameterAnnotations) throws Exception {
      boolean isAbstract = false;
      if (Modifier.isAbstract(access)) {
         access &= -1025;
         isAbstract = true;
      }

      String sig = makeSig(ret, parameters);
      String[] exceptionTypes = mapExceptions(exceptions);
      this.names.add(name);
      Code code = null;
      if (methodAnnotations != null && parameterAnnotations != null) {
         code = this.classfile.addMethod(name, sig, access, exceptionTypes, methodAnnotations, parameterAnnotations);
      } else {
         code = this.classfile.addMethod(name, sig, access, exceptionTypes);
      }

      code.aload(0);
      code.ldc(pyName);
      if (!isAbstract) {
         int tmp = code.getLocal("org/python/core/PyObject");
         code.invokestatic("org/python/compiler/ProxyMaker", "findPython", makeSig("Lorg/python/core/PyObject;", new String[]{"Lorg/python/core/PyProxy;", "Ljava/lang/String;"}));
         code.astore(tmp);
         code.aload(tmp);
         Label callPython = new Label();
         code.ifnonnull(callPython);
         String superClass = mapClass(declaringClass);
         this.callSuper(code, name, superClass, parameters, ret, true);
         code.label(callPython);
         code.aload(tmp);
         this.callMethod(code, name, parameters, ret, exceptions);
         this.addSuperMethod("super__" + name, name, superClass, parameters, ret, sig, access);
      } else {
         code.invokestatic("org/python/compiler/ProxyMaker", "findPython", makeSig("Lorg/python/core/PyObject;", new String[]{"Lorg/python/core/PyProxy;", "Ljava/lang/String;"}));
         code.dup();
         Label returnNull = new Label();
         code.ifnull(returnNull);
         this.callMethod(code, name, parameters, ret, exceptions);
         code.label(returnNull);
         code.pop();
         code.aload(0);
         code.ldc(pyName);
         code.ldc(declaringClass.getName());
         code.invokestatic("org/python/compiler/ProxyCodeHelpers", "notImplementedAbstractMethod", makeSig("Lorg/python/core/PyException;", new String[]{"Lorg/python/core/PyProxy;", "Ljava/lang/String;", "Ljava/lang/String;"}));
         code.checkcast(CodegenUtils.p(Throwable.class));
         code.athrow();
         doNullReturn(code, ret);
      }

   }

   public void addConstructorMethodCode(String pyName, Class[] parameters, Class[] exceptions, int access, Class declaringClass, Code code) throws Exception {
      code.aload(0);
      code.ldc(pyName);
      int tmp = code.getLocal("org/python/core/PyObject");
      code.invokestatic("org/python/compiler/ProxyMaker", "findPython", makeSig("Lorg/python/core/PyObject;", new String[]{"Lorg/python/core/PyProxy;", "Ljava/lang/String;"}));
      code.astore(tmp);
      code.aload(tmp);
      this.callMethod(code, "<init>", parameters, Void.TYPE, exceptions);
   }

   private String methodString(Method m) {
      StringBuffer buf = new StringBuffer(m.getName());
      buf.append(":");
      Class[] params = m.getParameterTypes();
      Class[] var4 = params;
      int var5 = params.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class param = var4[var6];
         buf.append(param.getName());
         buf.append(",");
      }

      return buf.toString();
   }

   protected void addMethods(Class c, Set t) throws Exception {
      Method[] methods = c.getDeclaredMethods();
      Method[] var4 = methods;
      int var5 = methods.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         if (t.add(this.methodString(method))) {
            int access = method.getModifiers();
            if (!Modifier.isStatic(access) && !Modifier.isPrivate(access)) {
               if (Modifier.isNative(access)) {
                  access &= -257;
               }

               if (Modifier.isProtected(access)) {
                  access = access & -5 | 1;
                  if (Modifier.isFinal(access)) {
                     this.addSuperMethod(method, access);
                     continue;
                  }
               } else if (Modifier.isFinal(access) || !Modifier.isPublic(access)) {
                  continue;
               }

               this.addMethod(method, access);
            }
         }
      }

      Class sc = c.getSuperclass();
      if (sc != null) {
         this.addMethods(sc, t);
      }

      Class[] var10 = c.getInterfaces();
      var6 = var10.length;

      for(int var11 = 0; var11 < var6; ++var11) {
         Class iface = var10[var11];
         this.addMethods(iface, t);
      }

   }

   public void addConstructor(String name, Class[] parameters, Class ret, String sig, int access) throws Exception {
      Code code = this.classfile.addMethod("<init>", sig, access);
      this.callSuper(code, "<init>", name, parameters, Void.TYPE, true);
   }

   public void addConstructors(Class c) throws Exception {
      Constructor[] constructors = c.getDeclaredConstructors();
      String name = mapClass(c);
      Constructor[] var4 = constructors;
      int var5 = constructors.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Constructor constructor = var4[var6];
         int access = constructor.getModifiers();
         if (!Modifier.isPrivate(access)) {
            if (Modifier.isNative(access)) {
               access &= -257;
            }

            if (Modifier.isProtected(access)) {
               access = access & -5 | 1;
            }

            Class[] parameters = constructor.getParameterTypes();
            this.addConstructor(name, parameters, Void.TYPE, makeSig(Void.TYPE, parameters), access);
         }
      }

   }

   protected void addClassAnnotation(ProxyCodeHelpers.AnnotationDescr annotation) {
      this.classfile.addClassAnnotation(annotation);
   }

   public void addSuperMethod(Method method, int access) throws Exception {
      Class[] parameters = method.getParameterTypes();
      Class ret = method.getReturnType();
      String superClass = mapClass(method.getDeclaringClass());
      String superName = method.getName();
      String methodName = superName;
      if (Modifier.isFinal(access)) {
         methodName = "super__" + superName;
         access &= -17;
      }

      this.addSuperMethod(methodName, superName, superClass, parameters, ret, makeSig(ret, parameters), access);
   }

   public void addSuperMethod(String methodName, String superName, String declClass, Class[] parameters, Class ret, String sig, int access) throws Exception {
      if (methodName.startsWith("super__")) {
         try {
            this.superclass.getMethod(methodName, parameters);
            return;
         } catch (NoSuchMethodException var9) {
         } catch (SecurityException var10) {
            return;
         }
      }

      this.supernames.add(methodName);
      Code code = this.classfile.addMethod(methodName, sig, access);
      this.callSuper(code, superName, declClass, parameters, ret, true);
   }

   public void addProxy() throws Exception {
      this.classfile.addField("__proxy", "Lorg/python/core/PyObject;", 4);
      Code code = this.classfile.addMethod("_setPyInstance", makeSig("V", new String[]{"Lorg/python/core/PyObject;"}), 1);
      code.aload(0);
      code.aload(1);
      code.putfield(this.classfile.name, "__proxy", "Lorg/python/core/PyObject;");
      code.return_();
      code = this.classfile.addMethod("_getPyInstance", makeSig("Lorg/python/core/PyObject;", new String[0]), 1);
      code.aload(0);
      code.getfield(this.classfile.name, "__proxy", "Lorg/python/core/PyObject;");
      code.areturn();
      String pySys = "Lorg/python/core/PySystemState;";
      this.classfile.addField("__systemState", pySys, 132);
      code = this.classfile.addMethod("_setPySystemState", makeSig("V", new String[]{pySys}), 1);
      code.aload(0);
      code.aload(1);
      code.putfield(this.classfile.name, "__systemState", pySys);
      code.return_();
      code = this.classfile.addMethod("_getPySystemState", makeSig(pySys, new String[0]), 1);
      code.aload(0);
      code.getfield(this.classfile.name, "__systemState", pySys);
      code.areturn();
   }

   public void addClassDictInit() throws Exception {
      this.classfile.addInterface(mapClass(ClassDictInit.class));
      Code code = this.classfile.addMethod("classDictInit", makeSig("V", new String[]{"Lorg/python/core/PyObject;"}), 9);
      code.aload(0);
      code.ldc("__supernames__");
      int strArray = CodeCompiler.makeStrings(code, this.supernames);
      code.aload(strArray);
      code.freeLocal(strArray);
      code.invokestatic("org/python/core/Py", "java2py", makeSig("Lorg/python/core/PyObject;", new String[]{"Ljava/lang/Object;"}));
      code.invokevirtual("org/python/core/PyObject", "__setitem__", makeSig("V", new String[]{"Ljava/lang/String;", "Lorg/python/core/PyObject;"}));
      code.return_();
   }

   public void build(OutputStream out) throws Exception {
      this.build();
      this.classfile.write(out);
   }

   public void build() throws Exception {
      this.names = Generic.set();
      this.namesAndSigs = Generic.set();
      int access = this.superclass.getModifiers();
      if ((access & 16) != 0) {
         throw new InstantiationException("can't subclass final class");
      } else {
         int access = 33;
         this.classfile = new ClassFile(this.myClass, mapClass(this.superclass), access);
         this.addProxy();
         this.visitConstructors();
         this.classfile.addInterface("org/python/core/PyProxy");
         this.visitClassAnnotations();
         this.visitMethods();
         this.doConstants();
         this.addClassDictInit();
      }
   }

   protected void visitMethods(Class klass) throws Exception {
      Method[] var2 = klass.getDeclaredMethods();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         if (this.namesAndSigs.add(this.methodString(method))) {
            int access = method.getModifiers();
            if (!Modifier.isStatic(access) && !Modifier.isPrivate(access)) {
               if (Modifier.isNative(access)) {
                  access &= -257;
               }

               if (Modifier.isProtected(access)) {
                  access = access & -5 | 1;
                  if (Modifier.isFinal(access)) {
                     this.addSuperMethod(method, access);
                     continue;
                  }
               } else if (Modifier.isFinal(access) || !Modifier.isPublic(access)) {
                  continue;
               }

               this.addMethod(method, access);
            }
         }
      }

      Class superClass = klass.getSuperclass();
      if (superClass != null) {
         this.visitMethods(superClass);
      }

      Class[] var8 = klass.getInterfaces();
      var4 = var8.length;

      for(int var9 = 0; var9 < var4; ++var9) {
         Class iface = var8[var9];
         this.visitMethods(iface);
      }

   }

   protected void visitMethod(Method method) throws Exception {
      this.addMethod(method, method.getModifiers());
   }

   protected void visitMethods() throws Exception {
      this.visitMethods(this.superclass);
      Class[] var1 = this.interfaces;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class iface = var1[var3];
         if (iface.isAssignableFrom(this.superclass)) {
            Py.writeWarning("compiler", "discarding redundant interface: " + iface.getName());
         } else {
            this.classfile.addInterface(mapClass(iface));
            this.visitMethods(iface);
         }
      }

   }

   protected void addConstructor(Class[] parameters, int access) throws Exception {
      String sig = makeSig(Void.TYPE, parameters);
      Code code = this.classfile.addMethod("<init>", sig, access);
      this.callSuper(code, "<init>", mapClass(this.superclass), parameters, Void.TYPE, true);
   }

   protected void visitConstructor(Constructor constructor) throws Exception {
      this.callInitProxy(constructor.getParameterTypes(), this.addOpenConstructor(constructor));
   }

   protected Code addOpenConstructor(Constructor constructor) throws Exception {
      String sig = makeSig(Void.TYPE, constructor.getParameterTypes());
      Code code = this.classfile.addMethod("<init>", sig, constructor.getModifiers());
      this.callSuper(code, "<init>", mapClass(this.superclass), constructor.getParameterTypes(), Void.TYPE, true);
      return code;
   }

   protected void callInitProxy(Class[] parameters, Code code) throws Exception {
      code.visitVarInsn(25, 0);
      this.getArgs(code, parameters);
      code.visitMethodInsn(182, this.classfile.name, "__initProxy__", makeSig("V", new String[]{"[Ljava/lang/Object;"}), false);
      code.visitInsn(177);
   }

   protected void visitConstructors() throws Exception {
      this.addConstructors(this.superclass);
   }

   protected void visitClassAnnotations() throws Exception {
   }
}
