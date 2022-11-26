package org.python.compiler;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import org.python.objectweb.asm.Label;
import org.python.util.Generic;

public class AdapterMaker extends ProxyMaker {
   public AdapterMaker(String adapterName, Class interfac) {
      super(adapterName, Object.class, interfac);
   }

   public void build() throws Exception {
      this.names = Generic.set();
      int access = 33;
      this.classfile = new ClassFile(this.myClass, "java/lang/Object", access);
      this.classfile.addInterface(mapClass(this.interfaces[0]));
      this.addMethods(this.interfaces[0], new HashSet());
      this.addConstructors(Object.class);
      this.doConstants();
   }

   public void doConstants() throws Exception {
      Iterator var1 = this.names.iterator();

      while(var1.hasNext()) {
         String name = (String)var1.next();
         this.classfile.addField(name, "Lorg/python/core/PyObject;", 1);
      }

   }

   public void addMethod(Method method, int access) throws Exception {
      Class[] parameters = method.getParameterTypes();
      Class ret = method.getReturnType();
      String name = method.getName();
      this.names.add(name);
      Code code = this.classfile.addMethod(name, makeSig(ret, parameters), 1);
      code.aload(0);
      code.getfield(this.classfile.name, name, "Lorg/python/core/PyObject;");
      code.dup();
      Label returnNull = new Label();
      code.ifnull(returnNull);
      this.callMethod(code, name, parameters, ret, method.getExceptionTypes());
      code.label(returnNull);
      doNullReturn(code, ret);
   }
}
