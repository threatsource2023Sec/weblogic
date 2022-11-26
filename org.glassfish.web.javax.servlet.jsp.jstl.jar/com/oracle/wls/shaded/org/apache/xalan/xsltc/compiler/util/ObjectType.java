package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;

public final class ObjectType extends Type {
   private String _javaClassName = "java.lang.Object";
   private Class _clazz;
   // $FF: synthetic field
   static Class class$java$lang$Object;

   protected ObjectType(String javaClassName) {
      this._clazz = class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object;
      this._javaClassName = javaClassName;

      try {
         this._clazz = ObjectFactory.findProviderClass(javaClassName, ObjectFactory.findClassLoader(), true);
      } catch (ClassNotFoundException var3) {
         this._clazz = null;
      }

   }

   protected ObjectType(Class clazz) {
      this._clazz = class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object;
      this._clazz = clazz;
      this._javaClassName = clazz.getName();
   }

   public int hashCode() {
      return (class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof ObjectType;
   }

   public String getJavaClassName() {
      return this._javaClassName;
   }

   public Class getJavaClass() {
      return this._clazz;
   }

   public String toString() {
      return this._javaClassName;
   }

   public boolean identicalTo(Type other) {
      return this == other;
   }

   public String toSignature() {
      StringBuffer result = new StringBuffer("L");
      result.append(this._javaClassName.replace('.', '/')).append(';');
      return result.toString();
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Type toJCType() {
      return Util.getJCRefType(this.toSignature());
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type) {
      if (type == Type.String) {
         this.translateTo(classGen, methodGen, (StringType)type);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), type.toString());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)DUP);
      BranchHandle ifNull = il.append((BranchInstruction)(new IFNULL((InstructionHandle)null)));
      il.append((Instruction)(new INVOKEVIRTUAL(cpg.addMethodref(this._javaClassName, "toString", "()Ljava/lang/String;"))));
      BranchHandle gotobh = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      ifNull.setTarget(il.append((Instruction)POP));
      il.append((CompoundInstruction)(new PUSH(cpg, "")));
      gotobh.setTarget(il.append(NOP));
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      if (clazz.isAssignableFrom(this._clazz)) {
         methodGen.getInstructionList().append(NOP);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), clazz.getClass().toString());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      methodGen.getInstructionList().append(NOP);
   }

   public Instruction LOAD(int slot) {
      return new ALOAD(slot);
   }

   public Instruction STORE(int slot) {
      return new ASTORE(slot);
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
