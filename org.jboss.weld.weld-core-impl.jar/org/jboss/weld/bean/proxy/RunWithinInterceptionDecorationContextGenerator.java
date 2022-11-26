package org.jboss.weld.bean.proxy;

import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.code.BranchEnd;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.code.ExceptionHandler;
import org.jboss.classfilewriter.util.DescriptorUtils;

abstract class RunWithinInterceptionDecorationContextGenerator {
   static final String INTERCEPTION_DECORATION_CONTEXT_CLASS_NAME = InterceptionDecorationContext.class.getName();
   static final String START_INTERCEPTOR_CONTEXT_IF_NOT_EMPTY_METHOD_NAME = "startIfNotEmpty";
   static final String START_INTERCEPTOR_CONTEXT_IF_NOT_ON_TOP_METHOD_NAME = "startIfNotOnTop";
   static final String END_INTERCEPTOR_CONTEXT_METHOD_NAME = "end";
   private static final String STACK_DESCRIPTOR = DescriptorUtils.makeDescriptor(InterceptionDecorationContext.Stack.class);
   private static final String EMPTY_PARENTHESES = "()";
   private static final String RETURNS_STACK_DESCRIPTOR;
   static final String START_INTERCEPTOR_CONTEXT_IF_NOT_ON_TOP_METHOD_SIGNATURE;
   private final ClassMethod classMethod;
   private final CodeAttribute b;
   private final ProxyFactory factory;

   RunWithinInterceptionDecorationContextGenerator(ClassMethod classMethod, ProxyFactory factory) {
      this.classMethod = classMethod;
      this.b = classMethod.getCodeAttribute();
      this.factory = factory;
   }

   abstract void doWork(CodeAttribute var1, ClassMethod var2);

   abstract void doReturn(CodeAttribute var1, ClassMethod var2);

   void startIfNotEmpty(CodeAttribute b, ClassMethod method) {
      b.invokestatic(INTERCEPTION_DECORATION_CONTEXT_CLASS_NAME, "startIfNotEmpty", RETURNS_STACK_DESCRIPTOR);
      this.storeToLocalVariable(0);
   }

   void startIfNotOnTop(CodeAttribute b, ClassMethod method) {
      b.aload(0);
      this.factory.getMethodHandlerField(method.getClassFile(), b);
      b.dup();
      BranchEnd handlerNull = b.ifnull();
      b.invokestatic(INTERCEPTION_DECORATION_CONTEXT_CLASS_NAME, "startIfNotOnTop", START_INTERCEPTOR_CONTEXT_IF_NOT_ON_TOP_METHOD_SIGNATURE);
      BranchEnd endOfIfStatement = b.gotoInstruction();
      b.branchEnd(handlerNull);
      b.branchEnd(endOfIfStatement);
      this.storeToLocalVariable(0);
   }

   void withinCatchBlock(CodeAttribute b, ClassMethod method) {
      ExceptionHandler start = b.exceptionBlockStart(Throwable.class.getName());
      this.doWork(b, method);
      this.endIfStarted(b, method);
      BranchEnd gotoEnd = b.gotoInstruction();
      b.exceptionBlockEnd(start);
      b.exceptionHandlerStart(start);
      this.endIfStarted(b, method);
      b.athrow();
      b.branchEnd(gotoEnd);
      this.doReturn(b, method);
   }

   void endIfStarted(CodeAttribute b, ClassMethod method) {
      b.aload(this.getLocalVariableIndex(0));
      b.dup();
      BranchEnd ifnotnull = b.ifnull();
      b.checkcast(InterceptionDecorationContext.Stack.class);
      b.invokevirtual(InterceptionDecorationContext.Stack.class.getName(), "end", "()V");
      BranchEnd ifnull = b.gotoInstruction();
      b.branchEnd(ifnotnull);
      b.pop();
      b.branchEnd(ifnull);
   }

   void runStartIfNotEmpty() {
      this.startIfNotEmpty(this.b, this.classMethod);
      this.withinCatchBlock(this.b, this.classMethod);
   }

   void runStartIfNotOnTop() {
      this.startIfNotOnTop(this.b, this.classMethod);
      this.withinCatchBlock(this.b, this.classMethod);
   }

   void storeToLocalVariable(int i) {
      this.b.astore(this.getLocalVariableIndex(0));
   }

   private int getLocalVariableIndex(int i) {
      int index = this.classMethod.isStatic() ? 0 : 1;
      String[] var3 = this.classMethod.getParameters();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String type = var3[var5];
         if (!type.equals("D") && !type.equals("J")) {
            ++index;
         } else {
            index += 2;
         }
      }

      return index + i;
   }

   static {
      RETURNS_STACK_DESCRIPTOR = "()" + STACK_DESCRIPTOR;
      START_INTERCEPTOR_CONTEXT_IF_NOT_ON_TOP_METHOD_SIGNATURE = DescriptorUtils.methodDescriptor(new String[]{DescriptorUtils.makeDescriptor(CombinedInterceptorAndDecoratorStackMethodHandler.class)}, STACK_DESCRIPTOR);
   }
}
