package com.bea.util.annogen.generate.internal.joust;

import java.io.IOException;

public interface JavaOutputStream {
   void startFile(String var1, String var2) throws IOException;

   void startClass(int var1, String var2, String[] var3) throws IOException;

   void startStaticInitializer() throws IOException;

   void startInterface(String[] var1) throws IOException;

   Variable writeField(int var1, String var2, String var3, Expression var4) throws IOException;

   Variable[] startConstructor(int var1, String[] var2, String[] var3, String[] var4) throws IOException;

   Variable[] startMethod(int var1, String var2, String var3, String[] var4, String[] var5, String[] var6) throws IOException;

   void writeComment(String var1) throws IOException;

   void writeAnnotation(Annotation var1) throws IOException;

   Annotation createAnnotation(String var1);

   void writeEmptyLine() throws IOException;

   void writeStatement(String var1) throws IOException;

   void writeImportStatement(String var1) throws IOException;

   void writeReturnStatement(Expression var1) throws IOException;

   void writeAssignmentStatement(Variable var1, Expression var2) throws IOException;

   void endMethodOrConstructor() throws IOException;

   void endClassOrInterface() throws IOException;

   void endFile() throws IOException;

   void close() throws IOException;

   ExpressionFactory getExpressionFactory();
}
