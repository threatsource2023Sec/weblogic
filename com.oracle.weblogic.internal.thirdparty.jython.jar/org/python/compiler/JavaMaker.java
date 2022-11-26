package org.python.compiler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.python.core.PyObject;

public class JavaMaker extends ProxyMaker {
   public String pythonClass;
   public String pythonModule;
   PyObject methods;

   public JavaMaker(Class superclass, Class[] interfaces, String pythonClass, String pythonModule, String myClass, PyObject methods) {
      super(myClass, superclass, interfaces);
      this.pythonClass = pythonClass;
      this.pythonModule = pythonModule;
      this.methods = methods;
   }

   public void addConstructor(String name, Class[] parameters, Class ret, String sig, int access) throws Exception {
      Code code = this.classfile.addMethod("<init>", sig, access);
      this.callSuper(code, "<init>", name, parameters, Void.TYPE, false);
      this.callInitProxy(parameters, code);
   }

   public void addProxy() throws Exception {
      if (this.methods != null) {
         super.addProxy();
      }

      Code code = this.classfile.addMethod("__initProxy__", makeSig("V", new String[]{"[Ljava/lang/Object;"}), 1);
      code.visitVarInsn(25, 0);
      code.visitLdcInsn(this.pythonModule);
      code.visitLdcInsn(this.pythonClass);
      code.visitVarInsn(25, 1);
      code.visitMethodInsn(184, "org/python/core/Py", "initProxy", makeSig("V", new String[]{"Lorg/python/core/PyProxy;", "Ljava/lang/String;", "Ljava/lang/String;", "[Ljava/lang/Object;"}), false);
      code.visitInsn(177);
   }

   public void addMethod(Method method, int access) throws Exception {
      if (Modifier.isAbstract(access)) {
         super.addMethod(method, access);
      } else if (this.methods.__finditem__(method.getName().intern()) != null) {
         super.addMethod(method, access);
      } else if (Modifier.isProtected(method.getModifiers())) {
         this.addSuperMethod(method, access);
      }

   }
}
