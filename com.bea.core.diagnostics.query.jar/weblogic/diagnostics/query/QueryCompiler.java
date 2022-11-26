package weblogic.diagnostics.query;

import antlr.RecognitionException;
import antlr.collections.AST;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import weblogic.diagnostics.debug.DebugLogger;

final class QueryCompiler extends ClassLoader implements Opcodes {
   private static final String JAVA_NAME = "weblogic.diagnostics.query.CompiledQuery";
   private static final String CLASS_NAME = "weblogic/diagnostics/query/CompiledQuery";
   private static DebugLogger queryDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticQuery");
   private static final int ONE = 1;
   private static final int TWO = 2;
   private static final int DEFAULT_CLASS_VERSION = 48;
   private ClassWriter classWriter;
   private MethodVisitor consWriter;
   private MethodVisitor methodWriter;
   private int consStack;
   private int consLocals;
   private int maxStack;
   private int maxLocals;
   private int patternCount;
   private int inSetCount;
   private VariableDeclarator varDeclarator;
   private VariableIndexResolver varIndexResolver;
   private Class generatedClass;

   public QueryCompiler(VariableDeclarator varDecl, VariableIndexResolver varIdxResolver) {
      this(ClassLoader.getSystemClassLoader(), varDecl, varIdxResolver);
   }

   public QueryCompiler(ClassLoader parent, VariableDeclarator varDecl, VariableIndexResolver varIdxResolver) {
      super(parent);
      this.classWriter = new ClassWriter(1);
      this.consStack = 1;
      this.consLocals = 1;
      this.maxStack = 0;
      this.maxLocals = 2;
      this.patternCount = 0;
      this.inSetCount = 0;
      this.generatedClass = null;
      this.varDeclarator = varDecl;
      this.varIndexResolver = varIdxResolver;
      this.classWriter.visit(48, 1, "weblogic/diagnostics/query/CompiledQuery", (String)null, "java/lang/Object", new String[]{"weblogic/diagnostics/query/Query"});
      this.consWriter = this.classWriter.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      this.consWriter.visitVarInsn(25, 0);
      this.consWriter.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
      MethodVisitor traceMethod = this.classWriter.visitMethod(1, "getLastExecutionTrace", "()Lweblogic/diagnostics/query/QueryExecutionTrace;", (String)null, (String[])null);
      traceMethod.visitInsn(1);
      traceMethod.visitInsn(176);
      traceMethod.visitMaxs(1, 1);
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Loading class " + name);
      }

      if (!name.equals("weblogic.diagnostics.query.CompiledQuery")) {
         return super.findClass(name);
      } else {
         if (this.generatedClass == null) {
            byte[] classBytes = this.classWriter.toByteArray();
            if (queryDebugLogger.isDebugEnabled()) {
               FileOutputStream fos = null;

               try {
                  queryDebugLogger.debug("Writing class file ./CompiledQuery.class");
                  fos = new FileOutputStream("CompiledQuery.class");
                  fos.write(classBytes);
               } catch (IOException var13) {
                  System.err.println("Error writing class file " + var13);
               } finally {
                  if (fos != null) {
                     try {
                        fos.close();
                     } catch (IOException var12) {
                        queryDebugLogger.debug("Error closing stream.", var12);
                     }
                  }

               }
            }

            this.generatedClass = this.defineClass("weblogic.diagnostics.query.CompiledQuery", classBytes, 0, classBytes.length);
         }

         return this.generatedClass;
      }
   }

   public void beginExecuteQueryMethodCompilation() {
      this.methodWriter = this.classWriter.visitMethod(1, "executeQuery", "(Lweblogic/diagnostics/query/VariableResolver;)Z", (String)null, new String[]{"weblogic/diagnostics/query/QueryExecutionException"});
   }

   public void visitLessThan(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 1) {
         this.visitIntRelationOperator(161, left, right);
      } else if (compareType == 2) {
         this.visitNumberRelationOperator(compareType, 148, 155, left, right, true);
      } else if (compareType == 3) {
         this.visitNumberRelationOperator(compareType, 150, 155, left, right, true);
      } else if (compareType == 4) {
         this.visitNumberRelationOperator(compareType, 152, 155, left, right, true);
      } else if (compareType == 0) {
         this.visitStringRelationOperator(155, left, right, true);
      }

   }

   public void visitGreaterThan(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 1) {
         this.visitIntRelationOperator(163, left, right);
      } else if (compareType == 2) {
         this.visitNumberRelationOperator(compareType, 148, 157, left, right, true);
      } else if (compareType == 3) {
         this.visitNumberRelationOperator(compareType, 150, 157, left, right, true);
      } else if (compareType == 4) {
         this.visitNumberRelationOperator(4, 152, 157, left, right, true);
      } else if (compareType == 0) {
         this.visitStringRelationOperator(157, left, right, true);
      }

   }

   public void visitLessThanEquals(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 1) {
         this.visitIntRelationOperator(164, left, right);
      } else if (compareType == 2) {
         this.visitNumberRelationOperator(compareType, 148, 157, left, right, false);
      } else if (compareType == 3) {
         this.visitNumberRelationOperator(compareType, 150, 157, left, right, false);
      } else if (compareType == 4) {
         this.visitNumberRelationOperator(compareType, 152, 157, left, right, false);
      } else if (compareType == 0) {
         this.visitStringRelationOperator(157, left, right, false);
      }

   }

   public void visitGreaterThanEquals(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 1) {
         this.visitIntRelationOperator(162, left, right);
      } else if (compareType == 2) {
         this.visitNumberRelationOperator(compareType, 148, 155, left, right, false);
      } else if (compareType == 3) {
         this.visitNumberRelationOperator(compareType, 150, 155, left, right, false);
      } else if (compareType == 4) {
         this.visitNumberRelationOperator(4, 152, 155, left, right, false);
      } else if (compareType == 0) {
         this.visitStringRelationOperator(155, left, right, false);
      }

   }

   public void visitAnd(QueryExpressionCompiler qc, AST leftNode, AST rightNode) throws RecognitionException, QueryExecutionException {
      qc.compileQuery(leftNode);
      Label falseLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitJumpInsn(153, falseLabel);
      qc.compileQuery(rightNode);
      this.methodWriter.visitJumpInsn(153, falseLabel);
      this.methodWriter.visitInsn(4);
      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(falseLabel);
      this.methodWriter.visitInsn(3);
      this.methodWriter.visitLabel(endLabel);
      ++this.maxStack;
   }

   public void visitOr(QueryExpressionCompiler qc, AST leftNode, AST rightNode) throws RecognitionException, QueryExecutionException {
      qc.compileQuery(leftNode);
      Label trueLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitJumpInsn(154, trueLabel);
      qc.compileQuery(rightNode);
      this.methodWriter.visitJumpInsn(154, trueLabel);
      this.methodWriter.visitInsn(3);
      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(trueLabel);
      this.methodWriter.visitInsn(4);
      this.methodWriter.visitLabel(endLabel);
      ++this.maxStack;
   }

   public void visitNot(QueryExpressionCompiler qc, AST t) throws RecognitionException, QueryExecutionException {
      qc.compileQuery(t);
      Label trueLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitJumpInsn(153, trueLabel);
      this.methodWriter.visitInsn(3);
      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(trueLabel);
      this.methodWriter.visitInsn(4);
      this.methodWriter.visitLabel(endLabel);
   }

   public void visitEquals(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 1) {
         this.visitIntRelationOperator(159, left, right);
      } else if (compareType == 2) {
         this.visitNumberRelationOperator(compareType, 148, 153, left, right, true);
      } else if (compareType == 3) {
         this.visitNumberRelationOperator(compareType, 150, 153, left, right, true);
      } else if (compareType == 4) {
         this.visitNumberRelationOperator(compareType, 152, 153, left, right, true);
      } else if (compareType == 0) {
         this.visitStringRelationOperator(153, left, right, true);
      } else if (compareType == 5) {
         this.visitBooleanEqualityOperator(159, left, right);
      }

   }

   public void visitNotEquals(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 1) {
         this.visitIntRelationOperator(160, left, right);
      } else if (compareType == 2) {
         this.visitNumberRelationOperator(compareType, 148, 154, left, right, true);
      } else if (compareType == 3) {
         this.visitNumberRelationOperator(compareType, 150, 154, left, right, true);
      } else if (compareType == 4) {
         this.visitNumberRelationOperator(compareType, 152, 154, left, right, true);
      } else if (compareType == 0) {
         this.visitStringRelationOperator(154, left, right, true);
      } else if (compareType == 5) {
         this.visitBooleanEqualityOperator(160, left, right);
      }

   }

   public void visitLike(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      this.visitStringComparator(left, right, true);
   }

   public void visitMatches(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      this.visitStringComparator(left, right, false);
   }

   public void visitIn(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      String fieldName = "inSet" + this.inSetCount;
      this.classWriter.visitField(2, fieldName, "Ljava/util/HashSet;", (String)null, (Object)null);
      ++this.inSetCount;
      Set values = (Set)right.getValue();
      Iterator iter = values.iterator();
      this.consWriter.visitVarInsn(25, 0);
      ++this.consStack;
      this.consWriter.visitTypeInsn(187, "java/util/HashSet");
      ++this.consStack;
      this.consWriter.visitInsn(89);
      ++this.consStack;
      this.consWriter.visitMethodInsn(183, "java/util/HashSet", "<init>", "()V");
      this.consWriter.visitFieldInsn(181, "weblogic/diagnostics/query/CompiledQuery", fieldName, "Ljava/util/HashSet;");
      int itemStackSize = 1;

      while(iter.hasNext()) {
         Object item = iter.next();
         this.consWriter.visitVarInsn(25, 0);
         this.consWriter.visitFieldInsn(180, "weblogic/diagnostics/query/CompiledQuery", fieldName, "Ljava/util/HashSet;");
         pushConstantObject(item, this.consWriter);
         itemStackSize = Math.max(itemStackSize, getItemStackSize(item));
         this.consWriter.visitMethodInsn(182, "java/util/HashSet", "add", "(Ljava/lang/Object;)Z");
         this.consWriter.visitInsn(87);
      }

      if (itemStackSize == 2) {
         ++this.consStack;
      }

      this.methodWriter.visitVarInsn(25, 0);
      ++this.maxStack;
      this.methodWriter.visitFieldInsn(180, "weblogic/diagnostics/query/CompiledQuery", fieldName, "Ljava/util/HashSet;");
      int javaType = this.getJavaType(left);
      switch (javaType) {
         case 1:
            this.pushIntOperand(left);
            pushIntObject(this.methodWriter);
            break;
         case 2:
            this.pushLongOperand(left);
            pushLongObject(this.methodWriter);
            break;
         case 3:
            this.pushFloatOperand(left);
            pushFloatObject(this.methodWriter);
            break;
         case 4:
            this.pushDoubleOperand(left);
            pushDoubleObject(this.methodWriter);
            break;
         default:
            this.pushStringOperand(left);
      }

      this.methodWriter.visitMethodInsn(182, "java/util/HashSet", "contains", "(Ljava/lang/Object;)Z");
   }

   private static void pushConstantObject(Object obj, MethodVisitor cv) {
      cv.visitLdcInsn(obj);
      Class clz = obj.getClass();
      if (clz == Integer.class) {
         pushIntObject(cv);
      } else if (clz == Float.class) {
         pushFloatObject(cv);
      } else if (clz == Long.class) {
         pushLongObject(cv);
      } else if (clz == Double.class) {
         pushDoubleObject(cv);
      }

   }

   private static void pushIntObject(MethodVisitor cv) {
      cv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
   }

   private static void pushFloatObject(MethodVisitor cv) {
      cv.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
   }

   private static void pushLongObject(MethodVisitor cv) {
      cv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
   }

   private static void pushDoubleObject(MethodVisitor cv) {
      cv.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
   }

   private static int getItemStackSize(Object item) {
      Class clz = item.getClass();
      return clz != Long.class && clz != Double.class ? 1 : 2;
   }

   private void visitStringComparator(AtomNode left, AtomNode right, boolean isLike) throws TypeMismatchException, UnknownVariableException {
      int compareType = this.getCompareType(left, right);
      if (compareType == 0) {
         String fieldName = "compiledPattern" + this.patternCount;
         this.classWriter.visitField(2, fieldName, "Ljava/util/regex/Pattern;", (String)null, (Object)null);
         ++this.patternCount;
         this.consWriter.visitVarInsn(25, 0);
         ++this.consStack;
         if (isLike) {
            this.consWriter.visitLdcInsn(right.getLikePatternString());
         } else {
            this.consWriter.visitLdcInsn(right.getText());
         }

         ++this.consStack;
         this.consWriter.visitLdcInsn(32);
         ++this.consStack;
         this.consWriter.visitMethodInsn(184, "java/util/regex/Pattern", "compile", "(Ljava/lang/String;I)Ljava/util/regex/Pattern;");
         this.consWriter.visitFieldInsn(181, "weblogic/diagnostics/query/CompiledQuery", fieldName, "Ljava/util/regex/Pattern;");
         this.methodWriter.visitVarInsn(25, 0);
         ++this.maxStack;
         this.methodWriter.visitFieldInsn(180, "weblogic/diagnostics/query/CompiledQuery", fieldName, "Ljava/util/regex/Pattern;");
         this.pushStringOperand(left);
         this.methodWriter.visitMethodInsn(182, "java/util/regex/Pattern", "matcher", "(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;");
         this.methodWriter.visitMethodInsn(182, "java/util/regex/Matcher", "matches", "()Z");
      }

   }

   public void endExecuteQueryMethodCompilation() {
      this.consWriter.visitInsn(177);
      this.consWriter.visitMaxs(this.consStack, this.consLocals);
      this.methodWriter.visitInsn(172);
      this.methodWriter.visitMaxs(this.maxStack, this.maxLocals);
   }

   public Query getCompiledQuery() throws QueryParsingException {
      try {
         Class clz = this.loadClass("weblogic.diagnostics.query.CompiledQuery", true);
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Compiled " + clz);
         }

         return (Query)clz.newInstance();
      } catch (Throwable var2) {
         throw new QueryParsingException("Compilation failed", var2);
      }
   }

   private void visitBooleanEqualityOperator(int opcode, AtomNode left, AtomNode right) throws UnknownVariableException {
      this.pushBooleanOperand(left);
      this.pushBooleanOperand(right);
      Label trueLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitJumpInsn(opcode, trueLabel);
      this.methodWriter.visitInsn(3);
      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(trueLabel);
      this.methodWriter.visitInsn(4);
      this.methodWriter.visitLabel(endLabel);
      this.maxStack += 2;
   }

   private void visitNumberRelationOperator(int compareType, int visitInsn, int opcode, AtomNode left, AtomNode right, boolean jumpOnTrue) throws UnknownVariableException {
      switch (compareType) {
         case 2:
            this.pushLongOperand(left);
            this.pushLongOperand(right);
            break;
         case 3:
            this.pushFloatOperand(left);
            this.pushFloatOperand(right);
            break;
         case 4:
            this.pushDoubleOperand(left);
            this.pushDoubleOperand(right);
            break;
         default:
            throw new IllegalArgumentException();
      }

      Label jumpLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitInsn(visitInsn);
      this.methodWriter.visitJumpInsn(opcode, jumpLabel);
      if (jumpOnTrue) {
         this.methodWriter.visitInsn(3);
      } else {
         this.methodWriter.visitInsn(4);
      }

      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(jumpLabel);
      if (jumpOnTrue) {
         this.methodWriter.visitInsn(4);
      } else {
         this.methodWriter.visitInsn(3);
      }

      this.methodWriter.visitLabel(endLabel);
   }

   private void visitStringRelationOperator(int opcode, AtomNode left, AtomNode right, boolean jumpOnTrue) throws UnknownVariableException {
      this.pushStringOperand(left);
      this.pushStringOperand(right);
      Label jumpLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitMethodInsn(182, "java/lang/String", "compareTo", "(Ljava/lang/String;)I");
      this.methodWriter.visitJumpInsn(opcode, jumpLabel);
      if (jumpOnTrue) {
         this.methodWriter.visitInsn(3);
      } else {
         this.methodWriter.visitInsn(4);
      }

      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(jumpLabel);
      if (jumpOnTrue) {
         this.methodWriter.visitInsn(4);
      } else {
         this.methodWriter.visitInsn(3);
      }

      this.methodWriter.visitLabel(endLabel);
   }

   private void visitIntRelationOperator(int opcode, AtomNode left, AtomNode right) throws UnknownVariableException {
      this.pushIntOperand(left);
      this.pushIntOperand(right);
      Label trueLabel = new Label();
      Label endLabel = new Label();
      this.methodWriter.visitJumpInsn(opcode, trueLabel);
      this.methodWriter.visitInsn(3);
      this.methodWriter.visitJumpInsn(167, endLabel);
      this.methodWriter.visitLabel(trueLabel);
      this.methodWriter.visitInsn(4);
      this.methodWriter.visitLabel(endLabel);
      this.maxStack += 2;
   }

   private void pushBooleanOperand(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 4) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing boolean constant on the stack " + atom.getValue());
         }

         Boolean n = (Boolean)atom.getValue();
         this.methodWriter.visitLdcInsn(n);
      } else {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing boolean variable on the stack " + atom.getText());
         }

         this.methodWriter.visitVarInsn(25, 1);
         if (this.varIndexResolver != null) {
            int index = this.varIndexResolver.getVariableIndex(atom.getText());
            this.methodWriter.visitLdcInsn(index);
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveVariable", "(I)Ljava/lang/Object;");
         } else {
            this.methodWriter.visitLdcInsn(atom.getText());
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveVariable", "(Ljava/lang/String;)Ljava/lang/Object;");
         }

         this.methodWriter.visitTypeInsn(192, "java/lang/Boolean");
         this.methodWriter.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z");
         ++this.maxStack;
      }

      ++this.maxStack;
      ++this.maxStack;
   }

   private void pushDoubleOperand(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 5) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing double constant on the stack " + atom.getValue());
         }

         Number n = (Number)atom.getValue();
         this.methodWriter.visitLdcInsn(n.doubleValue());
      } else {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing double variable on the stack " + atom.getText());
         }

         this.methodWriter.visitVarInsn(25, 1);
         if (this.varIndexResolver != null) {
            int index = this.varIndexResolver.getVariableIndex(atom.getText());
            this.methodWriter.visitLdcInsn(index);
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveDouble", "(I)D");
         } else {
            this.methodWriter.visitLdcInsn(atom.getText());
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveDouble", "(Ljava/lang/String;)D");
         }
      }

      ++this.maxStack;
      ++this.maxStack;
   }

   private void pushFloatOperand(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 5) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing double constant on the stack " + atom.getValue());
         }

         Number n = (Number)atom.getValue();
         this.methodWriter.visitLdcInsn(n.floatValue());
         ++this.maxStack;
      } else {
         this.methodWriter.visitVarInsn(25, 1);
         if (this.varIndexResolver != null) {
            int index = this.varIndexResolver.getVariableIndex(atom.getText());
            this.methodWriter.visitLdcInsn(index);
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveFloat", "(I)F");
         } else {
            this.methodWriter.visitLdcInsn(atom.getText());
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveFloat", "(Ljava/lang/String;)F");
         }

         ++this.maxStack;
         ++this.maxStack;
      }

   }

   private void pushLongOperand(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 5) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing long constant on the stack " + atom.getValue());
         }

         Number n = (Number)atom.getValue();
         this.methodWriter.visitLdcInsn(n.longValue());
      } else {
         this.methodWriter.visitVarInsn(25, 1);
         if (this.varIndexResolver != null) {
            int index = this.varIndexResolver.getVariableIndex(atom.getText());
            this.methodWriter.visitLdcInsn(index);
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveLong", "(I)J");
         } else {
            this.methodWriter.visitLdcInsn(atom.getText());
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveLong", "(Ljava/lang/String;)J");
         }
      }

      ++this.maxStack;
      ++this.maxStack;
   }

   private void pushIntOperand(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 5) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing integer constant on the stack " + atom.getValue());
         }

         Number n = (Number)atom.getValue();
         this.methodWriter.visitLdcInsn(n.intValue());
         ++this.maxStack;
      } else {
         this.methodWriter.visitVarInsn(25, 1);
         if (this.varIndexResolver != null) {
            int index = this.varIndexResolver.getVariableIndex(atom.getText());
            this.methodWriter.visitLdcInsn(index);
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveInteger", "(I)I");
         } else {
            this.methodWriter.visitLdcInsn(atom.getText());
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveInteger", "(Ljava/lang/String;)I");
         }

         ++this.maxStack;
         ++this.maxStack;
      }

   }

   private void pushStringOperand(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 6) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Pushing String constant on the stack " + atom.getValue());
         }

         this.methodWriter.visitLdcInsn(atom.getValue());
         ++this.maxStack;
      } else {
         this.methodWriter.visitVarInsn(25, 1);
         if (this.varIndexResolver != null) {
            int index = this.varIndexResolver.getVariableIndex(atom.getText());
            this.methodWriter.visitLdcInsn(index);
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveString", "(I)Ljava/lang/String;");
         } else {
            this.methodWriter.visitLdcInsn(atom.getText());
            this.methodWriter.visitMethodInsn(185, "weblogic/diagnostics/query/VariableResolver", "resolveString", "(Ljava/lang/String;)Ljava/lang/String;");
         }

         ++this.maxStack;
         ++this.maxStack;
      }

   }

   private int getCompareType(AtomNode left, AtomNode right) throws TypeMismatchException, UnknownVariableException {
      int leftJavaType = this.getJavaType(left);
      int rightJavaType = this.getJavaType(right);
      if (leftJavaType == 0 && rightJavaType == 0) {
         return 0;
      } else if (leftJavaType == 5 && rightJavaType == 5) {
         return 5;
      } else if (leftJavaType >= 1 && rightJavaType <= 4) {
         return Math.max(leftJavaType, rightJavaType);
      } else {
         throw new TypeMismatchException("Left and Right hand types mismatch in query expression");
      }
   }

   private int getJavaType(AtomNode atom) throws UnknownVariableException {
      if (atom.getType() == 5) {
         return getConstantNumberType(atom);
      } else if (atom.getType() == 6) {
         return 0;
      } else if (atom.getType() == 7) {
         return this.varDeclarator.getVariableType(atom.getText());
      } else if (atom.getType() == 4) {
         return 5;
      } else {
         throw new UnknownVariableException("Variable name is unknown");
      }
   }

   private static int getConstantNumberType(AtomNode atom) throws UnknownVariableException {
      Number num = (Number)atom.getValue();
      if (num instanceof Double) {
         return 4;
      } else if (num instanceof Long) {
         return 2;
      } else if (num instanceof Float) {
         return 3;
      } else if (num instanceof Integer) {
         return 1;
      } else {
         throw new UnknownVariableException("Variable name is unknown");
      }
   }

   public static void main(String[] args) throws Exception {
      FileOutputStream fos = new FileOutputStream(args[0]);

      try {
         QueryCompiler qc = new QueryCompiler((VariableDeclarator)null, (VariableIndexResolver)null);
         qc.beginExecuteQueryMethodCompilation();
         qc.endExecuteQueryMethodCompilation();
         byte[] classBytes = qc.classWriter.toByteArray();
         fos.write(classBytes);
         qc.defineClass("weblogic.diagnostics.query.CompiledQuery", classBytes, 0, classBytes.length);
      } finally {
         fos.close();
      }

   }
}
