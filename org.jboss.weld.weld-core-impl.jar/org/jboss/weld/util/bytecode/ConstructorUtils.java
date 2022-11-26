package org.jboss.weld.util.bytecode;

import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.DuplicateMemberException;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class ConstructorUtils {
   private ConstructorUtils() {
   }

   public static void addDefaultConstructor(ClassFile file, List initialValueBytecode, boolean useUnsafeInstantiators) {
      addConstructor("V", new String[0], new String[0], file, initialValueBytecode, useUnsafeInstantiators);
   }

   public static void addConstructor(String returnType, String[] params, String[] exceptions, ClassFile file, List initialValueBytecode, boolean useUnsafeInstantiators) {
      try {
         String initMethodName = "<init>";
         ClassMethod ctor = file.addMethod(1, "<init>", returnType, params);
         ctor.addCheckedExceptions(exceptions);
         CodeAttribute b = ctor.getCodeAttribute();
         Iterator var9 = initialValueBytecode.iterator();

         while(var9.hasNext()) {
            DeferredBytecode iv = (DeferredBytecode)var9.next();
            iv.apply(b);
         }

         b.aload(0);
         b.loadMethodParameters();
         b.invokespecial(file.getSuperclass(), "<init>", DescriptorUtils.methodDescriptor(params, returnType));
         if (!useUnsafeInstantiators) {
            b.aload(0);
            b.iconst(1);
            b.putfield(file.getName(), "constructed", "Z");
         }

         b.returnInstruction();
      } catch (DuplicateMemberException var11) {
         throw new RuntimeException(var11);
      }
   }
}
