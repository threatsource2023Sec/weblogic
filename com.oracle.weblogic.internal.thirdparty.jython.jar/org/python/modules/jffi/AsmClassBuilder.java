package org.python.modules.jffi;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicLong;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;

final class AsmClassBuilder {
   public static final boolean DEBUG = Boolean.getBoolean("jython.ctypes.compile.dump");
   private static final AtomicLong nextClassID = new AtomicLong(0L);
   private final JITSignature signature;
   private final ClassWriter classWriter;
   private final ClassVisitor classVisitor;
   private final String className;
   private final Class parentClass;
   private final JITMethodGenerator generator;

   AsmClassBuilder(JITMethodGenerator generator, JITSignature signature) {
      this.generator = generator;
      this.signature = signature;
      switch (signature.getParameterCount()) {
         case 0:
            this.parentClass = JITInvoker0.class;
            break;
         case 1:
            this.parentClass = JITInvoker1.class;
            break;
         case 2:
            this.parentClass = JITInvoker2.class;
            break;
         case 3:
            this.parentClass = JITInvoker3.class;
            break;
         case 4:
            this.parentClass = JITInvoker4.class;
            break;
         case 5:
            this.parentClass = JITInvoker5.class;
            break;
         case 6:
            this.parentClass = JITInvoker6.class;
            break;
         default:
            throw new UnsupportedOperationException("arity " + signature.getParameterCount() + " not supported");
      }

      this.className = CodegenUtils.p(Invoker.class) + "$ffi$" + nextClassID.getAndIncrement();
      this.classWriter = new ClassWriter(2);
      this.classVisitor = (ClassVisitor)(DEBUG ? newCheckClassAdapter(this.classWriter) : this.classWriter);
      this.classVisitor.visit(49, 17, this.className, (String)null, CodegenUtils.p(this.parentClass), new String[0]);
   }

   Class build() {
      SkinnyMethodAdapter init = new SkinnyMethodAdapter(this.classVisitor, 1, "<init>", CodegenUtils.sig(Void.TYPE, com.kenai.jffi.Function.class, NativeDataConverter.class, NativeDataConverter[].class, Invoker.class), (String)null, (String[])null);
      init.start();
      init.aload(0);
      init.aload(1);
      init.aload(4);
      init.invokespecial(CodegenUtils.p(this.parentClass), "<init>", CodegenUtils.sig(Void.TYPE, com.kenai.jffi.Function.class, Invoker.class));
      if (this.signature.hasResultConverter()) {
         this.classVisitor.visitField(18, this.getResultConverterFieldName(), CodegenUtils.ci(NativeDataConverter.class), (String)null, (Object)null);
         init.aload(0);
         init.aload(2);
         init.putfield(this.className, this.getResultConverterFieldName(), CodegenUtils.ci(NativeDataConverter.class));
      }

      for(int i = 0; i < this.signature.getParameterCount(); ++i) {
         if (this.signature.hasParameterConverter(i)) {
            this.classVisitor.visitField(18, this.getParameterConverterFieldName(i), CodegenUtils.ci(NativeDataConverter.class), (String)null, (Object)null);
            init.aload(0);
            init.aload(3);
            init.pushInt(i);
            init.aaload();
            init.putfield(this.className, this.getParameterConverterFieldName(i), CodegenUtils.ci(NativeDataConverter.class));
         }
      }

      init.voidreturn();
      init.visitMaxs(10, 10);
      init.visitEnd();
      this.generator.generate(this, "invoke", this.signature);
      this.classVisitor.visitEnd();

      try {
         byte[] bytes = this.classWriter.toByteArray();
         if (DEBUG) {
            ClassVisitor trace = newTraceClassVisitor(new PrintWriter(System.err));
            (new ClassReader(bytes)).accept(trace, 0);
         }

         JITClassLoader loader = new JITClassLoader(this.getClass().getClassLoader());
         return loader.defineClass(CodegenUtils.c(this.className), bytes);
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public static ClassVisitor newCheckClassAdapter(ClassVisitor cv) {
      try {
         Class tmvClass = Class.forName("org.python.objectweb.asm.util.CheckClassAdapter").asSubclass(ClassVisitor.class);
         Constructor c = tmvClass.getDeclaredConstructor(ClassVisitor.class);
         return (ClassVisitor)c.newInstance(cv);
      } catch (Throwable var3) {
         return cv;
      }
   }

   public static final ClassVisitor newTraceClassVisitor(PrintWriter out) {
      try {
         Class tmvClass = Class.forName("org.python.objectweb.asm.util.TraceClassVisitor").asSubclass(ClassVisitor.class);
         Constructor c = tmvClass.getDeclaredConstructor(PrintWriter.class);
         return (ClassVisitor)c.newInstance(out);
      } catch (Throwable var3) {
         return null;
      }
   }

   final String getFunctionFieldName() {
      return "jffiFunction";
   }

   final String getResultConverterFieldName() {
      return "resultConverter";
   }

   final String getParameterConverterFieldName(int i) {
      return "parameterConverter" + i;
   }

   final String getFallbackInvokerFieldName() {
      return "fallbackInvoker";
   }

   final ClassVisitor getClassVisitor() {
      return this.classVisitor;
   }

   final String getClassName() {
      return this.className;
   }

   static final class JITClassLoader extends ClassLoader {
      public JITClassLoader() {
      }

      public JITClassLoader(ClassLoader parent) {
         super(parent);
      }

      public Class defineClass(String name, byte[] b) {
         Class klass = this.defineClass(name, b, 0, b.length);
         this.resolveClass(klass);
         return klass;
      }
   }
}
