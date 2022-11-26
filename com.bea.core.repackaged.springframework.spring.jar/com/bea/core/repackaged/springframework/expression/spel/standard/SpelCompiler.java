package com.bea.core.repackaged.springframework.expression.spel.standard;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.asm.ClassWriter;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Opcodes;
import com.bea.core.repackaged.springframework.expression.Expression;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.CompiledExpression;
import com.bea.core.repackaged.springframework.expression.spel.ast.SpelNodeImpl;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class SpelCompiler implements Opcodes {
   private static final Log logger = LogFactory.getLog(SpelCompiler.class);
   private static final int CLASSES_DEFINED_LIMIT = 100;
   private static final Map compilers = new ConcurrentReferenceHashMap();
   private ChildClassLoader ccl;
   private final AtomicInteger suffixId = new AtomicInteger(1);

   private SpelCompiler(@Nullable ClassLoader classloader) {
      this.ccl = new ChildClassLoader(classloader);
   }

   @Nullable
   public CompiledExpression compile(SpelNodeImpl expression) {
      if (expression.isCompilable()) {
         if (logger.isDebugEnabled()) {
            logger.debug("SpEL: compiling " + expression.toStringAST());
         }

         Class clazz = this.createExpressionClass(expression);
         if (clazz != null) {
            try {
               return (CompiledExpression)ReflectionUtils.accessibleConstructor(clazz).newInstance();
            } catch (Throwable var4) {
               throw new IllegalStateException("Failed to instantiate CompiledExpression", var4);
            }
         }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("SpEL: unable to compile " + expression.toStringAST());
      }

      return null;
   }

   private int getNextSuffix() {
      return this.suffixId.incrementAndGet();
   }

   @Nullable
   private Class createExpressionClass(SpelNodeImpl expressionToCompile) {
      String className = "spel/Ex" + this.getNextSuffix();
      ClassWriter cw = new ExpressionClassWriter();
      cw.visit(49, 1, className, (String)null, "com/bea/core/repackaged/springframework/expression/spel/CompiledExpression", (String[])null);
      MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, "com/bea/core/repackaged/springframework/expression/spel/CompiledExpression", "<init>", "()V", false);
      mv.visitInsn(177);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
      mv = cw.visitMethod(1, "getValue", "(Ljava/lang/Object;Lorg/springframework/expression/EvaluationContext;)Ljava/lang/Object;", (String)null, new String[]{"com/bea/core/repackaged/springframework/expression/EvaluationException"});
      mv.visitCode();
      CodeFlow cf = new CodeFlow(className, cw);

      try {
         expressionToCompile.generateCode(mv, cf);
      } catch (IllegalStateException var7) {
         if (logger.isDebugEnabled()) {
            logger.debug(expressionToCompile.getClass().getSimpleName() + ".generateCode opted out of compilation: " + var7.getMessage());
         }

         return null;
      }

      CodeFlow.insertBoxIfNecessary(mv, cf.lastDescriptor());
      if ("V".equals(cf.lastDescriptor())) {
         mv.visitInsn(1);
      }

      mv.visitInsn(176);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
      cw.visitEnd();
      cf.finish();
      byte[] data = cw.toByteArray();
      return this.loadClass(StringUtils.replace(className, "/", "."), data);
   }

   private Class loadClass(String name, byte[] bytes) {
      if (this.ccl.getClassesDefinedCount() > 100) {
         this.ccl = new ChildClassLoader(this.ccl.getParent());
      }

      return this.ccl.defineClass(name, bytes);
   }

   public static SpelCompiler getCompiler(@Nullable ClassLoader classLoader) {
      ClassLoader clToUse = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
      synchronized(compilers) {
         SpelCompiler compiler = (SpelCompiler)compilers.get(clToUse);
         if (compiler == null) {
            compiler = new SpelCompiler(clToUse);
            compilers.put(clToUse, compiler);
         }

         return compiler;
      }
   }

   public static boolean compile(Expression expression) {
      return expression instanceof SpelExpression && ((SpelExpression)expression).compileExpression();
   }

   public static void revertToInterpreted(Expression expression) {
      if (expression instanceof SpelExpression) {
         ((SpelExpression)expression).revertToInterpreted();
      }

   }

   private class ExpressionClassWriter extends ClassWriter {
      public ExpressionClassWriter() {
         super(3);
      }

      protected ClassLoader getClassLoader() {
         return SpelCompiler.this.ccl;
      }
   }

   private static class ChildClassLoader extends URLClassLoader {
      private static final URL[] NO_URLS = new URL[0];
      private int classesDefinedCount = 0;

      public ChildClassLoader(@Nullable ClassLoader classLoader) {
         super(NO_URLS, classLoader);
      }

      int getClassesDefinedCount() {
         return this.classesDefinedCount;
      }

      public Class defineClass(String name, byte[] bytes) {
         Class clazz = super.defineClass(name, bytes, 0, bytes.length);
         ++this.classesDefinedCount;
         return clazz;
      }
   }
}
