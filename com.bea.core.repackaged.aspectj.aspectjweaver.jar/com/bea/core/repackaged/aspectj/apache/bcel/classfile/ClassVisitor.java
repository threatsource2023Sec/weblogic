package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisTypeAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisTypeAnnos;

public interface ClassVisitor {
   void visitCode(Code var1);

   void visitCodeException(CodeException var1);

   void visitConstantClass(ConstantClass var1);

   void visitConstantDouble(ConstantDouble var1);

   void visitConstantFieldref(ConstantFieldref var1);

   void visitConstantFloat(ConstantFloat var1);

   void visitConstantInteger(ConstantInteger var1);

   void visitConstantInterfaceMethodref(ConstantInterfaceMethodref var1);

   void visitConstantLong(ConstantLong var1);

   void visitConstantMethodref(ConstantMethodref var1);

   void visitConstantMethodHandle(ConstantMethodHandle var1);

   void visitConstantNameAndType(ConstantNameAndType var1);

   void visitConstantMethodType(ConstantMethodType var1);

   void visitConstantInvokeDynamic(ConstantInvokeDynamic var1);

   void visitConstantPool(ConstantPool var1);

   void visitConstantString(ConstantString var1);

   void visitConstantUtf8(ConstantUtf8 var1);

   void visitConstantValue(ConstantValue var1);

   void visitDeprecated(Deprecated var1);

   void visitExceptionTable(ExceptionTable var1);

   void visitField(Field var1);

   void visitInnerClass(InnerClass var1);

   void visitInnerClasses(InnerClasses var1);

   void visitJavaClass(JavaClass var1);

   void visitLineNumber(LineNumber var1);

   void visitLineNumberTable(LineNumberTable var1);

   void visitLocalVariable(LocalVariable var1);

   void visitLocalVariableTable(LocalVariableTable var1);

   void visitMethod(Method var1);

   void visitSignature(Signature var1);

   void visitSourceFile(SourceFile var1);

   void visitSynthetic(Synthetic var1);

   void visitBootstrapMethods(BootstrapMethods var1);

   void visitUnknown(Unknown var1);

   void visitStackMap(StackMap var1);

   void visitStackMapEntry(StackMapEntry var1);

   void visitEnclosingMethod(EnclosingMethod var1);

   void visitRuntimeVisibleAnnotations(RuntimeVisAnnos var1);

   void visitRuntimeInvisibleAnnotations(RuntimeInvisAnnos var1);

   void visitRuntimeVisibleParameterAnnotations(RuntimeVisParamAnnos var1);

   void visitRuntimeInvisibleParameterAnnotations(RuntimeInvisParamAnnos var1);

   void visitRuntimeVisibleTypeAnnotations(RuntimeVisTypeAnnos var1);

   void visitRuntimeInvisibleTypeAnnotations(RuntimeInvisTypeAnnos var1);

   void visitAnnotationDefault(AnnotationDefault var1);

   void visitLocalVariableTypeTable(LocalVariableTypeTable var1);

   void visitMethodParameters(MethodParameters var1);
}
