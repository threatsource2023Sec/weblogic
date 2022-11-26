package weblogic.utils.classfile;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import weblogic.utils.classfile.cp.CPClass;

public class MethodInfo extends ClassMember {
   private Scope scope;
   private final List exceptions = new LinkedList();
   private Exceptions_attribute ea;

   MethodInfo(ClassFile classFile) {
      super(classFile);
   }

   public MethodInfo(ClassFile classFile, Method m, int accessFlags) {
      super(classFile, m.getName(), CodeGenHelper.getMethodDescriptor(m), accessFlags);
      this.scope = new Scope(m, accessFlags);
   }

   public MethodInfo(ClassFile classFile, String methodName, String methodDescriptor, int accessFlags) {
      super(classFile, methodName, methodDescriptor, accessFlags);
      this.scope = new Scope(methodDescriptor, accessFlags);
   }

   public String getType() {
      return "method";
   }

   public Scope getScope() {
      return this.scope;
   }

   public CodeAttribute getCodeAttribute() {
      CodeAttribute ca = (CodeAttribute)this.getAttributes().getAttribute("Code");
      if (ca == null) {
         ca = new CodeAttribute(this);
         this.getAttributes().addAttribute(ca);
      }

      return ca;
   }

   public CPClass[] getExceptions() {
      attribute_info attrs = this.attributes.getAttribute("Exceptions");
      return attrs instanceof Exceptions_attribute ? ((Exceptions_attribute)attrs).getExceptionTable() : new CPClass[0];
   }

   public int getLineNumber(Op op) {
      attribute_info attrs = this.attributes.getAttribute("Code");
      CodeAttribute codeAttrs = (CodeAttribute)attrs;
      LineNumberTable_attribute lines = (LineNumberTable_attribute)codeAttrs.getAttributes().getAttribute("LineNumberTable");
      Bytecodes code = ((CodeAttribute)attrs).code;
      int pc = code.pcForOp(op);
      return lines.getLineNumber(pc);
   }

   public void addException(CPClass exceptionClass) {
      this.ea = new Exceptions_attribute(this.cp);
      this.exceptions.add(exceptionClass);
   }

   private void addExceptionsAttribute() {
      if (this.ea != null) {
         this.ea.exception_table = new CPClass[this.exceptions.size()];

         for(int i = 0; i < this.exceptions.size(); ++i) {
            this.ea.exception_table[i] = (CPClass)this.exceptions.get(i);
         }

         this.ea.number_of_exceptions = this.exceptions.size();
         this.getAttributes().addAttribute(this.ea);
      }
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      this.addExceptionsAttribute();
      super.write(out);
   }

   public void setSynthetic() {
      if (this.getAttributes().getAttribute("Synthetic") == null) {
         this.getAttributes().addAttribute("Synthetic", new byte[0]);
      }

   }
}
