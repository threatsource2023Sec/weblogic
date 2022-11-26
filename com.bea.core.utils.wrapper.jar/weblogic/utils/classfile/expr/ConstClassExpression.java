package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.FieldInfo;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPFieldref;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.CPString;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.BranchOp;
import weblogic.utils.classfile.ops.ConstPoolOp;

class ConstClassExpression implements Expression {
   private final String className;
   private final boolean primitive;

   public ConstClassExpression(Class c) {
      this.className = c.getName();
      this.primitive = c.isPrimitive();
   }

   public ConstClassExpression(String cname) {
      this.className = cname;
      this.primitive = this.isPrimitive(cname);
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      if (this.primitive) {
         this.codePrimitive(ca, code);
      } else {
         this.codeNonPrimitive(ca, code);
      }

   }

   private void codePrimitive(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      String primitiveClassName = this.getPrimitiveClassName(this.className);
      CPFieldref synfield = cp.getFieldref(primitiveClassName, "TYPE", "Ljava/lang/Class;");
      code.add(new ConstPoolOp(178, cp, synfield));
   }

   private void codeNonPrimitive(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      ClassFile cf = ca.getMethodInfo().getClassFile();
      cf.addClassForNameMethod();
      ca.getMethodInfo().getClassFile().addClassForNameMethod();
      String synFieldName = this.getSyntheticFieldName(this.className);
      FieldInfo fi = ca.getMethodInfo().getClassFile().addField(synFieldName, "Ljava/lang/Class;", 10);
      fi.getAttributes().addAttribute("Synthetic", new byte[0]);
      String implClassName = cf.getClassName();
      CPFieldref synfield = cp.getFieldref(implClassName, synFieldName, "Ljava/lang/Class;");
      CPString str = cp.getString(this.className);
      CPMethodref mgetClass = cp.getMethodref(implClassName, "class$", "(Ljava/lang/String;)Ljava/lang/Class;");
      code.add(new ConstPoolOp(178, cp, synfield));
      Label branchTarget = new Label();
      Label exit = new Label();
      BranchOp bo = new BranchOp(199);
      bo.target = branchTarget;
      code.add(bo);
      BranchOp opGoto = new BranchOp(200);
      opGoto.target = exit;
      code.add(new ConstPoolOp(19, cp, str));
      code.add(new ConstPoolOp(184, cp, mgetClass));
      code.add(new Op(89));
      code.add(new ConstPoolOp(179, cp, synfield));
      code.add(opGoto);
      code.add(branchTarget);
      code.add(new ConstPoolOp(178, cp, synfield));
      code.add(exit);
   }

   private String getPrimitiveClassName(String className) {
      if (className.equals(Boolean.TYPE.getName())) {
         return "java/lang/Boolean";
      } else if (className.equals(Integer.TYPE.getName())) {
         return "java/lang/Integer";
      } else if (className.equals(Short.TYPE.getName())) {
         return "java/lang/Short";
      } else if (className.equals(Long.TYPE.getName())) {
         return "java/lang/Long";
      } else if (className.equals(Double.TYPE.getName())) {
         return "java/lang/Double";
      } else if (className.equals(Float.TYPE.getName())) {
         return "java/lang/Float";
      } else if (className.equals(Byte.TYPE.getName())) {
         return "java/lang/Byte";
      } else if (className.equals(Character.TYPE.getName())) {
         return "java/lang/Character";
      } else {
         throw new AssertionError("Not primitive: " + className);
      }
   }

   private String getSyntheticFieldName(String className) {
      StringBuffer sb = new StringBuffer();
      if (className.charAt(0) == '[') {
         sb.append("array");
      } else {
         sb.append("class$");
      }

      sb.append(className);

      for(int i = 0; i < sb.length(); ++i) {
         char c = sb.charAt(i);
         switch (c) {
            case '.':
            case '[':
               sb.setCharAt(i, '$');
               break;
            case ';':
               sb.deleteCharAt(i);
         }
      }

      return sb.toString();
   }

   private boolean isPrimitive(String cname) {
      return cname.equals(Boolean.TYPE.getName()) || cname.equals(Integer.TYPE.getName()) || cname.equals(Byte.TYPE.getName()) || cname.equals(Long.TYPE.getName()) || cname.equals(Short.TYPE.getName()) || cname.equals(Character.TYPE.getName()) || cname.equals(Double.TYPE.getName()) || cname.equals(Float.TYPE.getName());
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      return 2;
   }
}
