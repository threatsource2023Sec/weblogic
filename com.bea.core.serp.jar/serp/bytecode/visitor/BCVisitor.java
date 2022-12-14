package serp.bytecode.visitor;

import serp.bytecode.Annotation;
import serp.bytecode.Annotations;
import serp.bytecode.ArrayLoadInstruction;
import serp.bytecode.ArrayStoreInstruction;
import serp.bytecode.Attribute;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMember;
import serp.bytecode.BCMethod;
import serp.bytecode.BootstrapMethods;
import serp.bytecode.ClassInstruction;
import serp.bytecode.CmpInstruction;
import serp.bytecode.Code;
import serp.bytecode.ConstantInstruction;
import serp.bytecode.ConstantValue;
import serp.bytecode.ConvertInstruction;
import serp.bytecode.Deprecated;
import serp.bytecode.ExceptionHandler;
import serp.bytecode.Exceptions;
import serp.bytecode.GetFieldInstruction;
import serp.bytecode.IIncInstruction;
import serp.bytecode.IfInstruction;
import serp.bytecode.InnerClass;
import serp.bytecode.InnerClasses;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LineNumber;
import serp.bytecode.LineNumberTable;
import serp.bytecode.LoadInstruction;
import serp.bytecode.LocalVariable;
import serp.bytecode.LocalVariableTable;
import serp.bytecode.LocalVariableType;
import serp.bytecode.LocalVariableTypeTable;
import serp.bytecode.LookupSwitchInstruction;
import serp.bytecode.MathInstruction;
import serp.bytecode.MethodInstruction;
import serp.bytecode.MonitorEnterInstruction;
import serp.bytecode.MonitorExitInstruction;
import serp.bytecode.MultiANewArrayInstruction;
import serp.bytecode.NewArrayInstruction;
import serp.bytecode.Project;
import serp.bytecode.PutFieldInstruction;
import serp.bytecode.RetInstruction;
import serp.bytecode.ReturnInstruction;
import serp.bytecode.SourceFile;
import serp.bytecode.StackInstruction;
import serp.bytecode.StoreInstruction;
import serp.bytecode.Synthetic;
import serp.bytecode.TableSwitchInstruction;
import serp.bytecode.UnknownAttribute;
import serp.bytecode.WideInstruction;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.DoubleEntry;
import serp.bytecode.lowlevel.Entry;
import serp.bytecode.lowlevel.FieldEntry;
import serp.bytecode.lowlevel.FloatEntry;
import serp.bytecode.lowlevel.IntEntry;
import serp.bytecode.lowlevel.InterfaceMethodEntry;
import serp.bytecode.lowlevel.InvokeDynamicEntry;
import serp.bytecode.lowlevel.LongEntry;
import serp.bytecode.lowlevel.MethodEntry;
import serp.bytecode.lowlevel.MethodHandleEntry;
import serp.bytecode.lowlevel.MethodTypeEntry;
import serp.bytecode.lowlevel.NameAndTypeEntry;
import serp.bytecode.lowlevel.StringEntry;
import serp.bytecode.lowlevel.UTF8Entry;

public class BCVisitor {
   public void visit(VisitAcceptor obj) {
      if (obj != null) {
         obj.acceptVisit(this);
      }
   }

   public void enterProject(Project obj) {
   }

   public void exitProject(Project obj) {
   }

   public void enterBCClass(BCClass obj) {
   }

   public void exitBCClass(BCClass obj) {
   }

   public void enterBCMember(BCMember obj) {
   }

   public void exitBCMember(BCMember obj) {
   }

   public void enterBCField(BCField obj) {
   }

   public void exitBCField(BCField obj) {
   }

   public void enterBCMethod(BCMethod obj) {
   }

   public void exitBCMethod(BCMethod obj) {
   }

   public void enterAttribute(Attribute obj) {
   }

   public void exitAttribute(Attribute obj) {
   }

   public void enterConstantValue(ConstantValue obj) {
   }

   public void exitConstantValue(ConstantValue obj) {
   }

   public void enterDeprecated(Deprecated obj) {
   }

   public void exitDeprecated(Deprecated obj) {
   }

   public void enterExceptions(Exceptions obj) {
   }

   public void exitExceptions(Exceptions obj) {
   }

   public void enterInnerClasses(InnerClasses obj) {
   }

   public void exitInnerClasses(InnerClasses obj) {
   }

   public void enterLineNumberTable(LineNumberTable obj) {
   }

   public void exitLineNumberTable(LineNumberTable obj) {
   }

   public void enterLocalVariableTable(LocalVariableTable obj) {
   }

   public void exitLocalVariableTable(LocalVariableTable obj) {
   }

   public void enterLocalVariableTypeTable(LocalVariableTypeTable obj) {
   }

   public void exitLocalVariableTypeTable(LocalVariableTypeTable obj) {
   }

   public void enterAnnotations(Annotations obj) {
   }

   public void exitAnnotations(Annotations obj) {
   }

   public void enterAnnotation(Annotation obj) {
   }

   public void exitAnnotation(Annotation obj) {
   }

   public void enterAnnotationProperty(Annotation.Property obj) {
   }

   public void exitAnnotationProperty(Annotation.Property obj) {
   }

   public void enterSourceFile(SourceFile obj) {
   }

   public void exitSourceFile(SourceFile obj) {
   }

   public void enterSynthetic(Synthetic obj) {
   }

   public void exitSynthetic(Synthetic obj) {
   }

   public void enterUnknownAttribute(UnknownAttribute obj) {
   }

   public void exitUnknownAttribute(UnknownAttribute obj) {
   }

   public void enterCode(Code obj) {
   }

   public void exitCode(Code obj) {
   }

   public void enterExceptionHandler(ExceptionHandler obj) {
   }

   public void exitExceptionHandler(ExceptionHandler obj) {
   }

   public void enterInnerClass(InnerClass obj) {
   }

   public void exitInnerClass(InnerClass obj) {
   }

   public void enterLineNumber(LineNumber obj) {
   }

   public void exitLineNumber(LineNumber obj) {
   }

   public void enterLocalVariable(LocalVariable obj) {
   }

   public void exitLocalVariable(LocalVariable obj) {
   }

   public void enterLocalVariableType(LocalVariableType obj) {
   }

   public void exitLocalVariableType(LocalVariableType obj) {
   }

   public void enterInstruction(Instruction obj) {
   }

   public void exitInstruction(Instruction obj) {
   }

   public void enterArrayLoadInstruction(ArrayLoadInstruction obj) {
   }

   public void exitArrayLoadInstruction(ArrayLoadInstruction obj) {
   }

   public void enterArrayStoreInstruction(ArrayStoreInstruction obj) {
   }

   public void exitArrayStoreInstruction(ArrayStoreInstruction obj) {
   }

   public void enterClassInstruction(ClassInstruction obj) {
   }

   public void exitClassInstruction(ClassInstruction obj) {
   }

   public void enterConstantInstruction(ConstantInstruction obj) {
   }

   public void exitConstantInstruction(ConstantInstruction obj) {
   }

   public void enterConvertInstruction(ConvertInstruction obj) {
   }

   public void exitConvertInstruction(ConvertInstruction obj) {
   }

   public void enterGetFieldInstruction(GetFieldInstruction obj) {
   }

   public void exitGetFieldInstruction(GetFieldInstruction obj) {
   }

   public void enterIIncInstruction(IIncInstruction obj) {
   }

   public void exitIIncInstruction(IIncInstruction obj) {
   }

   public void enterJumpInstruction(JumpInstruction obj) {
   }

   public void exitJumpInstruction(JumpInstruction obj) {
   }

   public void enterIfInstruction(IfInstruction obj) {
   }

   public void exitIfInstruction(IfInstruction obj) {
   }

   public void enterLoadInstruction(LoadInstruction obj) {
   }

   public void exitLoadInstruction(LoadInstruction obj) {
   }

   public void enterLookupSwitchInstruction(LookupSwitchInstruction obj) {
   }

   public void exitLookupSwitchInstruction(LookupSwitchInstruction obj) {
   }

   public void enterMathInstruction(MathInstruction obj) {
   }

   public void exitMathInstruction(MathInstruction obj) {
   }

   public void enterMethodInstruction(MethodInstruction obj) {
   }

   public void exitMethodInstruction(MethodInstruction obj) {
   }

   public void enterMultiANewArrayInstruction(MultiANewArrayInstruction obj) {
   }

   public void exitMultiANewArrayInstruction(MultiANewArrayInstruction obj) {
   }

   public void enterNewArrayInstruction(NewArrayInstruction obj) {
   }

   public void exitNewArrayInstruction(NewArrayInstruction obj) {
   }

   public void enterPutFieldInstruction(PutFieldInstruction obj) {
   }

   public void exitPutFieldInstruction(PutFieldInstruction obj) {
   }

   public void enterRetInstruction(RetInstruction obj) {
   }

   public void exitRetInstruction(RetInstruction obj) {
   }

   public void enterReturnInstruction(ReturnInstruction obj) {
   }

   public void exitReturnInstruction(ReturnInstruction obj) {
   }

   public void enterStackInstruction(StackInstruction obj) {
   }

   public void exitStackInstruction(StackInstruction obj) {
   }

   public void enterStoreInstruction(StoreInstruction obj) {
   }

   public void exitStoreInstruction(StoreInstruction obj) {
   }

   public void enterTableSwitchInstruction(TableSwitchInstruction obj) {
   }

   public void exitTableSwitchInstruction(TableSwitchInstruction obj) {
   }

   public void enterWideInstruction(WideInstruction obj) {
   }

   public void exitWideInstruction(WideInstruction obj) {
   }

   public void enterMonitorEnterInstruction(MonitorEnterInstruction obj) {
   }

   public void exitMonitorEnterInstruction(MonitorEnterInstruction obj) {
   }

   public void enterMonitorExitInstruction(MonitorExitInstruction obj) {
   }

   public void exitMonitorExitInstruction(MonitorExitInstruction obj) {
   }

   public void enterCmpInstruction(CmpInstruction obj) {
   }

   public void exitCmpInstruction(CmpInstruction obj) {
   }

   public void enterConstantPool(ConstantPool obj) {
   }

   public void exitConstantPool(ConstantPool obj) {
   }

   public void enterEntry(Entry obj) {
   }

   public void exitEntry(Entry obj) {
   }

   public void enterClassEntry(ClassEntry obj) {
   }

   public void exitClassEntry(ClassEntry obj) {
   }

   public void enterDoubleEntry(DoubleEntry obj) {
   }

   public void exitDoubleEntry(DoubleEntry obj) {
   }

   public void enterFieldEntry(FieldEntry obj) {
   }

   public void exitFieldEntry(FieldEntry obj) {
   }

   public void enterFloatEntry(FloatEntry obj) {
   }

   public void exitFloatEntry(FloatEntry obj) {
   }

   public void enterIntEntry(IntEntry obj) {
   }

   public void exitIntEntry(IntEntry obj) {
   }

   public void enterInterfaceMethodEntry(InterfaceMethodEntry obj) {
   }

   public void exitInterfaceMethodEntry(InterfaceMethodEntry obj) {
   }

   public void enterLongEntry(LongEntry obj) {
   }

   public void exitLongEntry(LongEntry obj) {
   }

   public void enterMethodEntry(MethodEntry obj) {
   }

   public void exitMethodEntry(MethodEntry obj) {
   }

   public void enterNameAndTypeEntry(NameAndTypeEntry obj) {
   }

   public void exitNameAndTypeEntry(NameAndTypeEntry obj) {
   }

   public void enterStringEntry(StringEntry obj) {
   }

   public void exitStringEntry(StringEntry obj) {
   }

   public void enterUTF8Entry(UTF8Entry obj) {
   }

   public void exitUTF8Entry(UTF8Entry obj) {
   }

   public void enterInvokeDynamicEntry(InvokeDynamicEntry obj) {
   }

   public void exitInvokeDynamicEntry(InvokeDynamicEntry obj) {
   }

   public void enterMethodHandleEntry(MethodHandleEntry obj) {
   }

   public void exitMethodHandleEntry(MethodHandleEntry obj) {
   }

   public void enterMethodTypeEntry(MethodTypeEntry obj) {
   }

   public void exitMethodTypeEntry(MethodTypeEntry obj) {
   }

   public void enterBootstrapMethod(BootstrapMethods obj) {
   }

   public void exitBootstrapMethod(BootstrapMethods obj) {
   }
}
