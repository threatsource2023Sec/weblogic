package com.bea.util.annogen.generate.internal.joust;

import com.bea.util.jam.internal.JamLoggerImpl;
import com.bea.util.jam.provider.JamLogger;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.StringTokenizer;

public class SourceJavaOutputStream implements JavaOutputStream, ExpressionFactory {
   private static final String COMMENT_LINE_DELIMITERS = "\n\r\f";
   private static final String INDENT_STRING = "  ";
   private static final char[] hexLow = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final char[] hexHigh = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'};
   protected JamLogger mLogger = new JamLoggerImpl();
   private PrintWriter mOut = null;
   private int mIndentLevel = 0;
   private String mPackageName = null;
   private String mClassOrInterfaceName = null;
   private WriterFactory mWriterFactory;
   private StringWriter mCommentBuffer = null;
   private StringWriter mImportBuffer = null;
   private PrintWriter mCommentPrinter = null;
   private PrintWriter mImportPrinter = null;
   private boolean mVerbose = false;
   private static final Expression TRUE = newExp("true");
   private static final Expression FALSE = newExp("false");
   private static final Expression NULL = newExp("null");

   public SourceJavaOutputStream(WriterFactory factory) {
      this.setWriterFactory(factory);
   }

   protected SourceJavaOutputStream() {
   }

   protected void setWriterFactory(WriterFactory factory) {
      if (factory == null) {
         throw new IllegalArgumentException();
      } else {
         this.mWriterFactory = factory;
      }
   }

   public void setLogger(JamLogger bl) {
      if (bl == null) {
         throw new IllegalArgumentException("null logging");
      } else {
         this.mLogger = bl;
         this.mVerbose = bl.isVerbose((Object)this);
      }
   }

   public void startFile(String packageName, String classOrInterfaceName) throws IOException {
      if (packageName == null) {
         throw new IllegalArgumentException("null package");
      } else if (classOrInterfaceName == null) {
         throw new IllegalArgumentException("null classname");
      } else if (this.mOut != null) {
         throw new IllegalStateException("Start new file without calling endFile on existing file");
      } else if (this.mIndentLevel != 0) {
         throw new IllegalStateException();
      } else {
         this.mOut = new PrintWriter(this.mWriterFactory.createWriter(packageName, classOrInterfaceName));
         this.mPackageName = makeI18nSafe(packageName);
         this.mClassOrInterfaceName = makeI18nSafe(classOrInterfaceName);
      }
   }

   public void startStaticInitializer() throws IOException {
      this.checkStateForWrite();
      this.printIndents();
      this.mOut.println("static {");
      this.increaseIndent();
   }

   public void startClass(int modifiers, String extendsClassName, String[] interfaceNames) throws IOException {
      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      if (this.mVerbose) {
         this.mLogger.verbose("startClass " + this.mPackageName + "." + this.mClassOrInterfaceName);
      }

      extendsClassName = makeI18nSafe(extendsClassName);
      this.mOut.println("package " + this.mPackageName + ";");
      this.mOut.println();
      this.printImportsIfNeeded();
      this.mOut.print(Modifier.toString(modifiers));
      this.mOut.print(" class ");
      this.mOut.print(this.mClassOrInterfaceName);
      if (extendsClassName != null) {
         this.mOut.print(" extends ");
         this.mOut.print(extendsClassName);
      }

      if (interfaceNames != null && interfaceNames.length > 0) {
         this.mOut.print(" implements ");

         for(int i = 0; i < interfaceNames.length; ++i) {
            this.mOut.print(makeI18nSafe(interfaceNames[i]));
            if (i < interfaceNames.length - 1) {
               this.mOut.print(", ");
            }
         }
      }

      this.mOut.println(" {");
      this.mOut.println();
      this.increaseIndent();
   }

   public void startInterface(String[] extendsInterfaceNames) throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("startInterface " + this.mPackageName + "." + this.mClassOrInterfaceName);
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.mPackageName = makeI18nSafe(this.mPackageName);
      this.mOut.println("package " + this.mPackageName + ";");
      this.printImportsIfNeeded();
      this.mOut.print("public interface ");
      this.mOut.print(this.mClassOrInterfaceName);
      if (extendsInterfaceNames != null && extendsInterfaceNames.length > 0) {
         this.mOut.print(" extends ");

         for(int i = 0; i < extendsInterfaceNames.length; ++i) {
            this.mOut.print(makeI18nSafe(extendsInterfaceNames[i]));
            if (i < extendsInterfaceNames.length - 1) {
               this.mOut.print(", ");
            }
         }
      }

      this.mOut.println("{");
      this.mOut.println();
      this.increaseIndent();
   }

   public Variable writeField(int modifiers, String typeName, String fieldName, Expression defaultValue) throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("writeField " + typeName + " " + fieldName);
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.printIndents();
      typeName = makeI18nSafe(typeName);
      fieldName = makeI18nSafe(fieldName);
      this.mOut.print(Modifier.toString(modifiers));
      this.mOut.print(" ");
      this.mOut.print(typeName);
      this.mOut.print(" ");
      this.mOut.print(fieldName);
      if (defaultValue != null) {
         this.mOut.print(" = ");
         this.mOut.print((String)defaultValue.getMemento());
      }

      this.mOut.println(';');
      this.mOut.println();
      return newVar("this." + fieldName);
   }

   public Variable[] startConstructor(int modifiers, String[] paramTypeNames, String[] paramNames, String[] exceptionClassNames) throws IOException {
      return this.startMethod(modifiers, (String)null, this.mClassOrInterfaceName, paramTypeNames, paramNames, exceptionClassNames);
   }

   public Variable[] startMethod(int modifiers, String returnTypeName, String methodName, String[] paramTypeNames, String[] paramNames, String[] exceptionClassNames) throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("startMethod " + methodName);
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      methodName = makeI18nSafe(methodName);
      returnTypeName = makeI18nSafe(returnTypeName);
      this.printIndents();
      this.mOut.print(Modifier.toString(modifiers));
      this.mOut.print(" ");
      if (returnTypeName != null) {
         this.mOut.print(returnTypeName);
         this.mOut.print(" ");
      }

      this.mOut.print(methodName);
      Variable[] ret;
      int i;
      if (paramTypeNames != null && paramTypeNames.length != 0) {
         ret = new Variable[paramTypeNames.length];

         for(i = 0; i < ret.length; ++i) {
            this.mOut.print(i == 0 ? "(" : ", ");
            ret[i] = newVar(paramNames[i]);
            this.mOut.print(makeI18nSafe(paramTypeNames[i]));
            this.mOut.print(' ');
            this.mOut.print(makeI18nSafe(paramNames[i]));
         }

         this.mOut.print(")");
      } else {
         this.mOut.print("()");
         ret = new Variable[0];
      }

      if (exceptionClassNames != null && exceptionClassNames.length > 0) {
         for(i = 0; i < exceptionClassNames.length; ++i) {
            this.mOut.print(i == 0 ? " throws " : ", ");
            this.mOut.print(makeI18nSafe(exceptionClassNames[i]));
         }
      }

      this.mOut.println(" {");
      this.increaseIndent();
      return ret;
   }

   public void writeComment(String comment) throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("comment");
      }

      this.getCommentPrinter().println(comment);
   }

   public void writeImportStatement(String className) throws IOException {
      this.getImportPrinter().println("import " + makeI18nSafe(className) + ";");
   }

   public void writeEmptyLine() throws IOException {
      this.checkStateForWrite();
      this.mOut.println();
   }

   public void writeAnnotation(Annotation ann) throws IOException {
      PrintWriter out = this.getCommentPrinter();
      Iterator i = ((AnnotationImpl)ann).getPropertyNames();

      while(i.hasNext()) {
         String n = i.next().toString();
         out.print('@');
         out.print(((AnnotationImpl)ann).getType());
         out.print('.');
         out.print(n);
         out.print(" = ");
         out.print(((AnnotationImpl)ann).getValueDeclaration(n));
         out.println();
      }

   }

   public void writeStatement(String statement) throws IOException {
      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.printIndents();
      this.mOut.print(statement);
      this.mOut.println(";");
   }

   public void writeReturnStatement(Expression expression) throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("return");
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.printIndents();
      this.mOut.print("return ");
      this.mOut.print((String)expression.getMemento());
      this.mOut.println(";");
   }

   public void writeAssignmentStatement(Variable left, Expression right) throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("assignment");
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.printIndents();
      this.mOut.print((String)left.getMemento());
      this.mOut.print(" = ");
      this.mOut.print((String)right.getMemento());
      this.mOut.println(";");
   }

   public void endMethodOrConstructor() throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("endMethodOrConstructor");
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.reduceIndent();
      this.printIndents();
      this.mOut.println('}');
      this.mOut.println();
   }

   public void endClassOrInterface() throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("endClassOrInterface");
      }

      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      this.reduceIndent();
      this.printIndents();
      this.mOut.println('}');
   }

   public void endFile() throws IOException {
      this.checkStateForWrite();
      this.printCommentsIfNeeded();
      if (this.mVerbose) {
         this.mLogger.verbose("endFile");
      }

      this.closeOut();
   }

   public ExpressionFactory getExpressionFactory() {
      return this;
   }

   public Annotation createAnnotation(String type) {
      return new AnnotationImpl(type);
   }

   public void close() throws IOException {
      if (this.mVerbose) {
         this.mLogger.verbose("close");
      }

      this.closeOut();
   }

   public Expression createBoolean(boolean value) {
      return value ? TRUE : FALSE;
   }

   public Expression createString(String value) {
      return newExp("\"" + makeI18nSafe(value) + "\"");
   }

   public Expression createInt(int value) {
      return newExp(String.valueOf(value));
   }

   public Expression createNull() {
      return NULL;
   }

   public Expression createVerbatim(String value) {
      return newExp(makeI18nSafe(value));
   }

   private PrintWriter getCommentPrinter() {
      if (this.mCommentPrinter == null) {
         this.mCommentBuffer = new StringWriter();
         this.mCommentPrinter = new PrintWriter(this.mCommentBuffer);
      }

      return this.mCommentPrinter;
   }

   private void printCommentsIfNeeded() {
      if (this.mCommentBuffer != null) {
         this.checkStateForWrite();
         String comment = this.mCommentBuffer.toString();
         this.printIndents();
         this.mOut.println("/**");
         StringTokenizer st = new StringTokenizer(makeI18nSafe(comment), "\n\r\f");

         while(st.hasMoreTokens()) {
            this.printIndents();
            this.mOut.print(" * ");
            this.mOut.println(st.nextToken());
         }

         this.printIndents();
         this.mOut.println(" */");
         this.mCommentBuffer = null;
         this.mCommentPrinter = null;
      }
   }

   private PrintWriter getImportPrinter() {
      if (this.mImportPrinter == null) {
         this.mImportBuffer = new StringWriter();
         this.mImportPrinter = new PrintWriter(this.mImportBuffer);
      }

      return this.mImportPrinter;
   }

   private void printImportsIfNeeded() {
      if (this.mImportBuffer != null) {
         this.checkStateForWrite();
         String imports = this.mImportBuffer.toString();
         this.mOut.println(imports);
         this.mImportBuffer = null;
         this.mImportPrinter = null;
      }
   }

   private void checkStateForWrite() {
      if (this.mOut == null) {
         throw new IllegalStateException("Attempt to generate code when no file open.  This is indicates that there is some broken logic in the calling class");
      }
   }

   private void printIndents() {
      for(int i = 0; i < this.mIndentLevel; ++i) {
         this.mOut.print("  ");
      }

   }

   private void increaseIndent() {
      ++this.mIndentLevel;
   }

   private void reduceIndent() {
      --this.mIndentLevel;
      if (this.mIndentLevel < 0) {
         throw new IllegalStateException("Indent level reduced below zero. This is indicates that there is some broken logic in the calling class");
      }
   }

   private void closeOut() {
      if (this.mOut != null) {
         this.mOut.flush();
         this.mOut.close();
         this.mOut = null;
      }

   }

   private static Expression newExp(String s) {
      final String memento = makeI18nSafe(s);
      return new Expression() {
         public Object getMemento() {
            return memento;
         }
      };
   }

   private static Variable newVar(String s) {
      final String memento = makeI18nSafe(s);
      return new Variable() {
         public Object getMemento() {
            return memento;
         }
      };
   }

   private static String makeI18nSafe(String s) {
      if (s == null) {
         return null;
      } else {
         for(int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) > 127) {
               return buildI18nSafe(s);
            }
         }

         return s;
      }
   }

   private static String buildI18nSafe(String s) {
      StringBuffer mI18nSafeBuffer = new StringBuffer();
      int i = 0;
      int j = 0;

      while(true) {
         for(; i >= s.length() || s.charAt(i) > 127; j = i) {
            if (j < i) {
               mI18nSafeBuffer.append(s.substring(j, i));
            }

            while(i < s.length()) {
               int ch = s.charAt(i);
               if (ch <= 127) {
                  break;
               }

               int highByte = ch >>> 8;
               int lowByte = ch & 255;
               mI18nSafeBuffer.append("\\u");
               mI18nSafeBuffer.append(hexHigh[highByte]);
               mI18nSafeBuffer.append(hexLow[highByte]);
               mI18nSafeBuffer.append(hexHigh[lowByte]);
               mI18nSafeBuffer.append(hexLow[lowByte]);
               ++i;
            }
         }

         ++i;
      }
   }

   public static void main(String[] args) throws IOException {
      SourceJavaOutputStream sjos = new SourceJavaOutputStream(new WriterFactory() {
         private PrintWriter OUT;

         {
            this.OUT = new PrintWriter(System.out);
         }

         public Writer createWriter(String x, String y) {
            return this.OUT;
         }
      });
      JavaOutputStream joust = new ValidatingJavaOutputStream(sjos);
      ExpressionFactory exp = joust.getExpressionFactory();
      joust.startFile("foo.bar.baz", "MyClass");
      Annotation author = joust.createAnnotation("author");
      author.setValue("name", "Patrick Calahan");
      joust.writeComment("Test class");
      joust.writeAnnotation(author);
      joust.startClass(17, "MyBaseClass", (String[])null);
      String[] paramTypes = new String[]{"int", "List"};
      String[] paramNames = new String[]{"count", "fooList"};
      String[] exceptions = new String[]{"IOException"};
      Annotation deprecated = joust.createAnnotation("deprecated");
      deprecated.setValue("value", true);
      Variable counter = joust.writeField(2, "int", "counter", exp.createInt(99));
      joust.writeComment("This is the constructor comment");
      joust.writeComment("And here is another.\n\n  ok?");
      joust.writeAnnotation(deprecated);
      Variable[] params = joust.startConstructor(1, paramTypes, paramNames, exceptions);
      joust.writeAssignmentStatement(counter, params[0]);
      joust.endMethodOrConstructor();
      joust.endClassOrInterface();
      joust.endFile();
      joust.close();
   }
}
