package serp.bytecode.visitor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import serp.bytecode.Annotation;
import serp.bytecode.Attribute;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.ClassInstruction;
import serp.bytecode.Code;
import serp.bytecode.ConstantInstruction;
import serp.bytecode.ConstantValue;
import serp.bytecode.Constants;
import serp.bytecode.ExceptionHandler;
import serp.bytecode.Exceptions;
import serp.bytecode.GetFieldInstruction;
import serp.bytecode.IIncInstruction;
import serp.bytecode.IfInstruction;
import serp.bytecode.InnerClass;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LineNumber;
import serp.bytecode.LoadInstruction;
import serp.bytecode.LocalVariable;
import serp.bytecode.LocalVariableType;
import serp.bytecode.LookupSwitchInstruction;
import serp.bytecode.MethodInstruction;
import serp.bytecode.MultiANewArrayInstruction;
import serp.bytecode.NewArrayInstruction;
import serp.bytecode.Project;
import serp.bytecode.PutFieldInstruction;
import serp.bytecode.RetInstruction;
import serp.bytecode.SourceFile;
import serp.bytecode.StoreInstruction;
import serp.bytecode.TableSwitchInstruction;
import serp.bytecode.WideInstruction;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.DoubleEntry;
import serp.bytecode.lowlevel.Entry;
import serp.bytecode.lowlevel.FieldEntry;
import serp.bytecode.lowlevel.FloatEntry;
import serp.bytecode.lowlevel.IntEntry;
import serp.bytecode.lowlevel.InterfaceMethodEntry;
import serp.bytecode.lowlevel.LongEntry;
import serp.bytecode.lowlevel.MethodEntry;
import serp.bytecode.lowlevel.NameAndTypeEntry;
import serp.bytecode.lowlevel.StringEntry;
import serp.bytecode.lowlevel.UTF8Entry;

public class PrettyPrintVisitor extends BCVisitor {
   private PrintWriter _out = null;
   private String _prefix = "";

   public PrettyPrintVisitor() {
      this._out = new PrintWriter(System.out);
   }

   public PrettyPrintVisitor(PrintWriter out) {
      this._out = out;
   }

   public static void main(String[] args) throws ClassNotFoundException, IOException {
      if (args.length == 0) {
         System.err.println("Usage: java " + PrettyPrintVisitor.class.getName() + " <class name | .class file>+");
         System.exit(1);
      }

      PrettyPrintVisitor ppv = new PrettyPrintVisitor();
      Project project = new Project();

      for(int i = 0; i < args.length; ++i) {
         BCClass type;
         if (args[i].endsWith(".class")) {
            type = project.loadClass(new File(args[i]));
         } else {
            type = project.loadClass(Class.forName(args[i], false, PrettyPrintVisitor.class.getClassLoader()));
         }

         ppv.visit(type);
      }

   }

   public void visit(VisitAcceptor entity) {
      super.visit(entity);
      this._out.flush();
   }

   public void enterProject(Project obj) {
      this.openBlock("Project");
      this.println("name=" + obj.getName());
   }

   public void exitProject(Project obj) {
      this.closeBlock();
   }

   public void enterBCClass(BCClass obj) {
      this.openBlock("Class");
      this.println("magic=" + obj.getMagic());
      this.println("minor=" + obj.getMinorVersion());
      this.println("major=" + obj.getMajorVersion());
      this.println("access=" + obj.getAccessFlags());
      this.println("name=" + obj.getIndex() + " <" + obj.getName() + ">");
      this.println("super=" + obj.getSuperclassIndex() + " <" + obj.getSuperclassName() + ">");
      int[] indexes = obj.getDeclaredInterfaceIndexes();
      String[] names = obj.getDeclaredInterfaceNames();

      for(int i = 0; i < indexes.length; ++i) {
         this.println("interface=" + indexes[i] + " <" + names[i] + ">");
      }

   }

   public void exitBCClass(BCClass obj) {
      this.closeBlock();
   }

   public void enterBCField(BCField obj) {
      this.openBlock("Field");
      this.println("access=" + obj.getAccessFlags());
      this.println("name=" + obj.getNameIndex() + " <" + obj.getName() + ">");
      this.println("type=" + obj.getDescriptorIndex() + " <" + obj.getTypeName() + ">");
   }

   public void exitBCField(BCField obj) {
      this.closeBlock();
   }

   public void enterBCMethod(BCMethod obj) {
      this.openBlock("Method");
      this.println("access=" + obj.getAccessFlags());
      this.println("name=" + obj.getNameIndex() + " <" + obj.getName() + ">");
      this.println("descriptor=" + obj.getDescriptorIndex());
      this.println("return=" + obj.getReturnName());
      String[] params = obj.getParamNames();

      for(int i = 0; i < params.length; ++i) {
         this.println("param=" + params[i]);
      }

   }

   public void exitBCMethod(BCMethod obj) {
      this.closeBlock();
   }

   public void enterAttribute(Attribute obj) {
      this.openBlock(obj.getName());
   }

   public void exitAttribute(Attribute obj) {
      this.closeBlock();
   }

   public void enterConstantValue(ConstantValue obj) {
      this.println("value=" + obj.getValueIndex() + " <" + obj.getTypeName() + "=" + obj.getValue() + ">");
   }

   public void enterExceptions(Exceptions obj) {
      int[] indexes = obj.getExceptionIndexes();
      String[] names = obj.getExceptionNames();

      for(int i = 0; i < indexes.length; ++i) {
         this.println("exception=" + indexes[i] + " <" + names[i] + ">");
      }

   }

   public void enterSourceFile(SourceFile obj) {
      this.println("source=" + obj.getFileIndex() + " <" + obj.getFileName() + ">");
   }

   public void enterCode(Code obj) {
      this.println("maxStack=" + obj.getMaxStack());
      this.println("maxLocals=" + obj.getMaxLocals());
      this.println("");
   }

   public void enterExceptionHandler(ExceptionHandler obj) {
      this.openBlock("ExceptionHandler");
      this.println("startPc=" + obj.getTryStartPc());
      this.println("endPc=" + obj.getTryEndPc());
      this.println("handlerPc=" + obj.getHandlerStartPc());
      this.println("catch=" + obj.getCatchIndex() + " <" + obj.getCatchName() + ">");
   }

   public void exitExceptionHandler(ExceptionHandler obj) {
      this.closeBlock();
   }

   public void enterInnerClass(InnerClass obj) {
      this.openBlock("InnerClass");
      this.println("access=" + obj.getAccessFlags());
      this.println("name=" + obj.getNameIndex() + " <" + obj.getName() + ">");
      this.println("type=" + obj.getTypeIndex() + "<" + obj.getTypeName() + ">");
      this.println("declarer=" + obj.getDeclarerIndex() + "<" + obj.getDeclarerName() + ">");
   }

   public void exitInnerClass(InnerClass obj) {
      this.closeBlock();
   }

   public void enterLineNumber(LineNumber obj) {
      this.openBlock("LineNumber");
      this.println("startPc=" + obj.getStartPc());
      this.println("line=" + obj.getLine());
   }

   public void exitLineNumber(LineNumber obj) {
      this.closeBlock();
   }

   public void enterLocalVariable(LocalVariable obj) {
      this.openBlock("LocalVariable");
      this.println("startPc=" + obj.getStartPc());
      this.println("length=" + obj.getLength());
      this.println("local=" + obj.getLocal());
      this.println("name=" + obj.getNameIndex() + " <" + obj.getName() + ">");
      this.println("type=" + obj.getTypeIndex() + " <" + obj.getTypeName() + ">");
   }

   public void exitLocalVariable(LocalVariable obj) {
      this.closeBlock();
   }

   public void enterLocalVariableType(LocalVariableType obj) {
      this.openBlock("LocalVariableType");
      this.println("startPc=" + obj.getStartPc());
      this.println("length=" + obj.getLength());
      this.println("local=" + obj.getLocal());
      this.println("name=" + obj.getNameIndex() + " <" + obj.getName() + ">");
      this.println("signature=" + obj.getTypeIndex() + " <" + obj.getTypeName() + ">");
   }

   public void exitLocalVariableType(LocalVariableType obj) {
      this.closeBlock();
   }

   public void enterAnnotation(Annotation obj) {
      this.openBlock("Annotation");
      this.println("type=" + obj.getTypeIndex() + " <" + obj.getTypeName() + ">");
   }

   public void exitAnnotation(Annotation obj) {
      this.closeBlock();
   }

   public void enterAnnotationProperty(Annotation.Property obj) {
      this.openBlock("Property");
      this.println("name=" + obj.getNameIndex() + " <" + obj.getName() + ">");
      Object val = obj.getValue();
      if (val instanceof Object[]) {
         Object[] arr = (Object[])((Object[])val);

         for(int i = 0; i < arr.length; ++i) {
            this.printAnnotationPropertyValue(arr[i]);
         }
      } else {
         this.printAnnotationPropertyValue(val);
      }

   }

   private void printAnnotationPropertyValue(Object obj) {
      if (obj == null) {
         this.println("value=null");
      } else if (obj instanceof Annotation) {
         this._out.print(this._prefix);
         this._out.print("value=");
         ((Annotation)obj).acceptVisit(this);
      } else {
         this.println("value=(" + obj.getClass().getName() + ") " + obj);
      }

   }

   public void exitAnnotationProperty(Annotation.Property obj) {
      this.closeBlock();
   }

   public void enterInstruction(Instruction obj) {
      this._out.print(this._prefix + obj.getByteIndex() + " " + obj.getName() + " ");
   }

   public void exitInstruction(Instruction obj) {
      this._out.println();
   }

   public void enterClassInstruction(ClassInstruction obj) {
      this._out.print(obj.getTypeIndex() + " <" + obj.getTypeName() + ">");
   }

   public void enterConstantInstruction(ConstantInstruction obj) {
      this._out.print("<" + obj.getValue() + ">");
   }

   public void enterGetFieldInstruction(GetFieldInstruction obj) {
      this._out.print(obj.getFieldIndex() + " <" + obj.getFieldTypeName() + " " + obj.getFieldDeclarerName() + "." + obj.getFieldName() + ">");
   }

   public void enterIIncInstruction(IIncInstruction obj) {
      this._out.print(obj.getLocal() + " ");
      if (obj.getIncrement() < 0) {
         this._out.print("-");
      }

      this._out.print(obj.getIncrement());
   }

   public void enterJumpInstruction(JumpInstruction obj) {
      this._out.print(obj.getOffset());
   }

   public void enterIfInstruction(IfInstruction obj) {
      this._out.print(obj.getOffset());
   }

   public void enterLoadInstruction(LoadInstruction obj) {
      this._out.print("<" + obj.getLocal() + ">");
   }

   public void enterLookupSwitchInstruction(LookupSwitchInstruction obj) {
      this._out.println();
      this._prefix = this._prefix + "  ";
      int[] offsets = obj.getOffsets();
      int[] matches = obj.getMatches();

      for(int i = 0; i < offsets.length; ++i) {
         this.println("case " + matches[i] + "=" + offsets[i]);
      }

      this._out.print(this._prefix + "default=" + obj.getDefaultOffset());
      this._prefix = this._prefix.substring(2);
   }

   public void enterMethodInstruction(MethodInstruction obj) {
      this._out.print(obj.getMethodIndex() + " <" + obj.getMethodReturnName() + " " + obj.getMethodDeclarerName() + "." + obj.getMethodName() + "(");
      String[] params = obj.getMethodParamNames();

      for(int i = 0; i < params.length; ++i) {
         int dotIndex = params[i].lastIndexOf(46);
         if (dotIndex != -1) {
            params[i] = params[i].substring(dotIndex + 1);
         }

         this._out.print(params[i]);
         if (i != params.length - 1) {
            this._out.print(", ");
         }
      }

      this._out.print(")>");
   }

   public void enterMultiANewArrayInstruction(MultiANewArrayInstruction obj) {
      this._out.print(obj.getTypeIndex() + " " + obj.getDimensions() + " <" + obj.getTypeName());
      String post = "";

      for(int i = 0; i < obj.getDimensions(); ++i) {
         post = post + "[]";
      }

      this._out.print(post + ">");
   }

   public void enterNewArrayInstruction(NewArrayInstruction obj) {
      this._out.print(obj.getTypeCode() + " <" + obj.getTypeName() + "[]>");
   }

   public void enterPutFieldInstruction(PutFieldInstruction obj) {
      this._out.print(obj.getFieldIndex() + " <" + obj.getFieldTypeName() + " " + obj.getFieldDeclarerName() + "." + obj.getFieldName() + ">");
   }

   public void enterRetInstruction(RetInstruction obj) {
      this._out.print(obj.getLocal());
   }

   public void enterStoreInstruction(StoreInstruction obj) {
      this._out.print("<" + obj.getLocal() + ">");
   }

   public void enterTableSwitchInstruction(TableSwitchInstruction obj) {
      this._out.println();
      this._prefix = this._prefix + "  ";
      this.println("low=" + obj.getLow());
      this.println("high=" + obj.getHigh());
      int[] offsets = obj.getOffsets();

      for(int i = 0; i < offsets.length; ++i) {
         this.println("case=" + offsets[i]);
      }

      this._out.print(this._prefix + "default=" + obj.getDefaultOffset());
      this._prefix = this._prefix.substring(2);
   }

   public void enterWideInstruction(WideInstruction obj) {
      int ins = obj.getInstruction();
      this._out.print(ins + " <" + Constants.OPCODE_NAMES[ins] + ">");
   }

   public void enterConstantPool(ConstantPool obj) {
      this.openBlock("ConstantPool");
   }

   public void exitConstantPool(ConstantPool obj) {
      this.closeBlock();
   }

   public void enterEntry(Entry obj) {
      String name = obj.getClass().getName();
      this.openBlock(obj.getIndex() + ": " + name.substring(name.lastIndexOf(46) + 1));
   }

   public void exitEntry(Entry obj) {
      this.closeBlock();
   }

   public void enterClassEntry(ClassEntry obj) {
      this.println("name=" + obj.getNameIndex());
   }

   public void enterDoubleEntry(DoubleEntry obj) {
      this.println("value=" + obj.getValue());
   }

   public void enterFieldEntry(FieldEntry obj) {
      this.println("class=" + obj.getClassIndex());
      this.println("nameAndType=" + obj.getNameAndTypeIndex());
   }

   public void enterFloatEntry(FloatEntry obj) {
      this.println("value=" + obj.getValue());
   }

   public void enterIntEntry(IntEntry obj) {
      this.println("value=" + obj.getValue());
   }

   public void enterInterfaceMethodEntry(InterfaceMethodEntry obj) {
      this.println("class=" + obj.getClassIndex());
      this.println("nameAndType=" + obj.getNameAndTypeIndex());
   }

   public void enterLongEntry(LongEntry obj) {
      this.println("value=" + obj.getValue());
   }

   public void enterMethodEntry(MethodEntry obj) {
      this.println("class=" + obj.getClassIndex());
      this.println("nameAndType=" + obj.getNameAndTypeIndex());
   }

   public void enterNameAndTypeEntry(NameAndTypeEntry obj) {
      this.println("name=" + obj.getNameIndex());
      this.println("descriptor=" + obj.getDescriptorIndex());
   }

   public void enterStringEntry(StringEntry obj) {
      this.println("index=" + obj.getStringIndex());
   }

   public void enterUTF8Entry(UTF8Entry obj) {
      this.println("value=" + obj.getValue());
   }

   private void println(String ln) {
      this._out.print(this._prefix);
      this._out.println(ln);
   }

   private void openBlock(String name) {
      this.println(name + " {");
      this._prefix = this._prefix + "  ";
   }

   private void closeBlock() {
      this._prefix = this._prefix.substring(2);
      this.println("}");
   }
}
