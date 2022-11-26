package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.concurrent.atomic.AtomicLong;
import org.jboss.classfilewriter.AccessFlag;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.weld.security.GetDeclaredMethodAction;
import org.jboss.weld.util.bytecode.BytecodeUtils;

public class DefaultBytecodeMethodResolver implements BytecodeMethodResolver {
   private static final AtomicLong METHOD_COUNT = new AtomicLong();
   private static final String WELD_MEMBER_PREFIX = "weld$$$";
   private static final String LJAVA_LANG_REFLECT_METHOD = "Ljava/lang/reflect/Method;";

   public void getDeclaredMethod(ClassMethod classMethod, String declaringClass, String methodName, String[] parameterTypes, ClassMethod staticConstructor) {
      String weldMemberName = "weld$$$" + METHOD_COUNT.incrementAndGet();
      staticConstructor.getClassFile().addField(10, weldMemberName, "Ljava/lang/reflect/Method;");
      CodeAttribute code = staticConstructor.getCodeAttribute();
      this.addInitMethod(declaringClass, methodName, parameterTypes, weldMemberName, staticConstructor.getClassFile());
      code.invokestatic(staticConstructor.getClassFile().getName(), weldMemberName, "()Ljava/lang/reflect/Method;");
      code.putstatic(classMethod.getClassFile().getName(), weldMemberName, "Ljava/lang/reflect/Method;");
      CodeAttribute methodCode = classMethod.getCodeAttribute();
      methodCode.getstatic(classMethod.getClassFile().getName(), weldMemberName, "Ljava/lang/reflect/Method;");
   }

   private void addInitMethod(String declaringClass, String methodName, String[] parameterTypes, String weldMethodName, ClassFile classFile) {
      ClassMethod initMethod = classFile.addMethod(AccessFlag.of(new int[]{2, 8}), weldMethodName, "Ljava/lang/reflect/Method;", new String[0]);
      CodeAttribute code = initMethod.getCodeAttribute();
      BytecodeUtils.pushClassType(code, declaringClass);
      code.ldc(methodName);
      code.iconst(parameterTypes.length);
      code.anewarray(Class.class.getName());

      for(int i = 0; i < parameterTypes.length; ++i) {
         code.dup();
         code.iconst(i);
         String type = parameterTypes[i];
         BytecodeUtils.pushClassType(code, type);
         code.aastore();
      }

      code.invokestatic(DefaultBytecodeMethodResolver.class.getName(), "getMethod", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;");
      code.returnInstruction();
   }

   public static Method getMethod(Class javaClass, String methodName, Class... parameterTypes) {
      return (Method)AccessController.doPrivileged(GetDeclaredMethodAction.wrapException(javaClass, methodName, parameterTypes));
   }
}
