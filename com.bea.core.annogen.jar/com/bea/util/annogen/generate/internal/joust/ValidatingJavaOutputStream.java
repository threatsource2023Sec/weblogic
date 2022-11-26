package com.bea.util.annogen.generate.internal.joust;

import java.io.IOException;

public class ValidatingJavaOutputStream implements JavaOutputStream {
   private JavaOutputStream mDest;

   public ValidatingJavaOutputStream(JavaOutputStream destination) {
      if (destination == null) {
         throw new IllegalArgumentException();
      } else {
         this.mDest = destination;
      }
   }

   public void startFile(String packageName, String interfaceOrClassName) throws IOException {
      this.mDest.startFile(packageName, interfaceOrClassName);
   }

   public void startClass(int modifiers, String extendsClassName, String[] implementsInterfaceNames) throws IOException {
      this.mDest.startClass(modifiers, extendsClassName, implementsInterfaceNames);
   }

   public void startInterface(String[] extendsInterfaceNames) throws IOException {
      this.mDest.startInterface(extendsInterfaceNames);
   }

   public Variable writeField(int modifiers, String typeName, String fieldName, Expression defaultValue) throws IOException {
      return this.mDest.writeField(modifiers, typeName, fieldName, defaultValue);
   }

   public void startStaticInitializer() throws IOException {
      this.mDest.startStaticInitializer();
   }

   public Variable[] startConstructor(int modifiers, String[] paramTypeNames, String[] paramNames, String[] exceptionClassNames) throws IOException {
      return this.mDest.startConstructor(modifiers, paramTypeNames, paramNames, exceptionClassNames);
   }

   public Variable[] startMethod(int modifiers, String methodName, String returnTypeName, String[] paramTypeNames, String[] paramNames, String[] exceptionClassNames) throws IOException {
      return this.mDest.startMethod(modifiers, methodName, returnTypeName, paramTypeNames, paramNames, exceptionClassNames);
   }

   public void writeEmptyLine() throws IOException {
      this.mDest.writeEmptyLine();
   }

   public void writeComment(String comment) throws IOException {
      this.mDest.writeComment(comment);
   }

   public void writeAnnotation(Annotation ann) throws IOException {
      this.mDest.writeAnnotation(ann);
   }

   public Annotation createAnnotation(String type) {
      return this.mDest.createAnnotation(type);
   }

   public void writeImportStatement(String className) throws IOException {
      this.mDest.writeImportStatement(className);
   }

   public void writeStatement(String statement) throws IOException {
      this.mDest.writeStatement(statement);
   }

   public void writeReturnStatement(Expression expression) throws IOException {
      this.mDest.writeReturnStatement(expression);
   }

   public void writeAssignmentStatement(Variable left, Expression right) throws IOException {
      this.mDest.writeAssignmentStatement(left, right);
   }

   public void endMethodOrConstructor() throws IOException {
      this.mDest.endMethodOrConstructor();
   }

   public void endClassOrInterface() throws IOException {
      this.mDest.endClassOrInterface();
   }

   public void endFile() throws IOException {
      this.mDest.endFile();
   }

   public ExpressionFactory getExpressionFactory() {
      return this.mDest.getExpressionFactory();
   }

   public void close() throws IOException {
      this.mDest.close();
   }
}
