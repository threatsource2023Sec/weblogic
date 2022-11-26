package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Code;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.CodeException;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ExceptionTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LineNumber;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LineNumberTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariableTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeParamAnnos;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class MethodGen extends FieldGenOrMethodGen {
   private String classname;
   private Type[] parameterTypes;
   private String[] parameterNames;
   private int maxLocals;
   private int maxStack;
   private InstructionList il;
   private boolean stripAttributes;
   private int highestLineNumber;
   private ArrayList localVariablesList;
   private ArrayList lineNumbersList;
   private ArrayList exceptionsList;
   private ArrayList exceptionsThrown;
   private ArrayList codeAttributesList;
   private List[] param_annotations;
   private boolean hasParameterAnnotations;
   private boolean haveUnpackedParameterAnnotations;

   public MethodGen(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPool cp) {
      this.highestLineNumber = 0;
      this.localVariablesList = new ArrayList();
      this.lineNumbersList = new ArrayList();
      this.exceptionsList = new ArrayList();
      this.exceptionsThrown = new ArrayList();
      this.codeAttributesList = new ArrayList();
      this.hasParameterAnnotations = false;
      this.haveUnpackedParameterAnnotations = false;
      this.modifiers = access_flags;
      this.type = return_type;
      this.parameterTypes = arg_types;
      this.parameterNames = arg_names;
      this.name = method_name;
      this.classname = class_name;
      this.il = il;
      this.cp = cp;
   }

   public int getHighestlinenumber() {
      return this.highestLineNumber;
   }

   public MethodGen(Method m, String class_name, ConstantPool cp) {
      this(m, class_name, cp, false);
   }

   public MethodGen(Method m, String class_name, ConstantPool cp, boolean useTags) {
      this(m.getModifiers(), m.getReturnType(), m.getArgumentTypes(), (String[])null, m.getName(), class_name, (m.getModifiers() & 1280) == 0 ? new InstructionList(m.getCode().getCode()) : null, cp);
      Attribute[] attributes = m.getAttributes();

      for(int i = 0; i < attributes.length; ++i) {
         Attribute a = attributes[i];
         if (!(a instanceof Code)) {
            if (a instanceof ExceptionTable) {
               String[] names = ((ExceptionTable)a).getExceptionNames();

               for(int j = 0; j < names.length; ++j) {
                  this.addException(names[j]);
               }
            } else if (a instanceof RuntimeAnnos) {
               RuntimeAnnos runtimeAnnotations = (RuntimeAnnos)a;
               List l = runtimeAnnotations.getAnnotations();
               this.annotationList.addAll(l);
            } else {
               this.addAttribute(a);
            }
         } else {
            Code code = (Code)a;
            this.setMaxStack(code.getMaxStack());
            this.setMaxLocals(code.getMaxLocals());
            CodeException[] ces = code.getExceptionTable();
            InstructionHandle[] arrayOfInstructions = this.il.getInstructionsAsArray();
            int j;
            if (ces != null) {
               CodeException[] var11 = ces;
               int var12 = ces.length;

               for(j = 0; j < var12; ++j) {
                  CodeException ce = var11[j];
                  int type = ce.getCatchType();
                  ObjectType catchType = null;
                  if (type > 0) {
                     String cen = m.getConstantPool().getConstantString_CONSTANTClass(type);
                     catchType = new ObjectType(cen);
                  }

                  int end_pc = ce.getEndPC();
                  int length = m.getCode().getCode().length;
                  InstructionHandle end;
                  if (length == end_pc) {
                     end = this.il.getEnd();
                  } else {
                     end = this.il.findHandle(end_pc, arrayOfInstructions);
                     end = end.getPrev();
                  }

                  this.addExceptionHandler(this.il.findHandle(ce.getStartPC(), arrayOfInstructions), end, this.il.findHandle(ce.getHandlerPC(), arrayOfInstructions), catchType);
               }
            }

            Attribute[] codeAttrs = code.getAttributes();

            for(j = 0; j < codeAttrs.length; ++j) {
               a = codeAttrs[j];
               int k;
               if (a instanceof LineNumberTable) {
                  LineNumber[] ln = ((LineNumberTable)a).getLineNumberTable();
                  LineNumber l;
                  if (useTags) {
                     for(k = 0; k < ln.length; ++k) {
                        l = ln[k];
                        int lnum = l.getLineNumber();
                        if (lnum > this.highestLineNumber) {
                           this.highestLineNumber = lnum;
                        }

                        LineNumberTag lt = new LineNumberTag(lnum);
                        this.il.findHandle(l.getStartPC(), arrayOfInstructions, true).addTargeter(lt);
                     }
                  } else {
                     for(k = 0; k < ln.length; ++k) {
                        l = ln[k];
                        this.addLineNumber(this.il.findHandle(l.getStartPC(), arrayOfInstructions, true), l.getLineNumber());
                     }
                  }
               } else if (!(a instanceof LocalVariableTable)) {
                  this.addCodeAttribute(a);
               } else {
                  LocalVariable[] lv;
                  LocalVariable l;
                  if (!useTags) {
                     lv = ((LocalVariableTable)a).getLocalVariableTable();
                     this.removeLocalVariables();

                     for(k = 0; k < lv.length; ++k) {
                        l = lv[k];
                        InstructionHandle start = this.il.findHandle(l.getStartPC(), arrayOfInstructions);
                        InstructionHandle end = this.il.findHandle(l.getStartPC() + l.getLength(), arrayOfInstructions);
                        if (end != null) {
                           end = end.getPrev();
                        }

                        if (start == null) {
                           start = this.il.getStart();
                        }

                        if (end == null) {
                           end = this.il.getEnd();
                        }

                        this.addLocalVariable(l.getName(), Type.getType(l.getSignature()), l.getIndex(), start, end);
                     }
                  } else {
                     lv = ((LocalVariableTable)a).getLocalVariableTable();

                     for(k = 0; k < lv.length; ++k) {
                        l = lv[k];
                        Type t = Type.getType(l.getSignature());
                        LocalVariableTag lvt = new LocalVariableTag(t, l.getSignature(), l.getName(), l.getIndex(), l.getStartPC());
                        InstructionHandle start = this.il.findHandle(l.getStartPC(), arrayOfInstructions, true);
                        byte b = t.getType();
                        int end;
                        if (b != 16) {
                           end = t.getSize();
                           if (l.getIndex() + end > this.maxLocals) {
                              this.maxLocals = l.getIndex() + end;
                           }
                        }

                        end = l.getStartPC() + l.getLength();

                        do {
                           start.addTargeter(lvt);
                           start = start.getNext();
                        } while(start != null && start.getPosition() < end);
                     }
                  }
               }
            }
         }
      }

   }

   public LocalVariableGen addLocalVariable(String name, Type type, int slot, InstructionHandle start, InstructionHandle end) {
      int size = type.getSize();
      if (slot + size > this.maxLocals) {
         this.maxLocals = slot + size;
      }

      LocalVariableGen l = new LocalVariableGen(slot, name, type, start, end);
      int i = this.localVariablesList.indexOf(l);
      if (i >= 0) {
         this.localVariablesList.set(i, l);
      } else {
         this.localVariablesList.add(l);
      }

      return l;
   }

   public LocalVariableGen addLocalVariable(String name, Type type, InstructionHandle start, InstructionHandle end) {
      return this.addLocalVariable(name, type, this.maxLocals, start, end);
   }

   public void removeLocalVariable(LocalVariableGen l) {
      this.localVariablesList.remove(l);
   }

   public void removeLocalVariables() {
      this.localVariablesList.clear();
   }

   private static final void sort(LocalVariableGen[] vars, int l, int r) {
      int i = l;
      int j = r;
      int m = vars[(l + r) / 2].getIndex();

      do {
         while(vars[i].getIndex() < m) {
            ++i;
         }

         while(m < vars[j].getIndex()) {
            --j;
         }

         if (i <= j) {
            LocalVariableGen h = vars[i];
            vars[i] = vars[j];
            vars[j] = h;
            ++i;
            --j;
         }
      } while(i <= j);

      if (l < j) {
         sort(vars, l, j);
      }

      if (i < r) {
         sort(vars, i, r);
      }

   }

   public LocalVariableGen[] getLocalVariables() {
      int size = this.localVariablesList.size();
      LocalVariableGen[] lg = new LocalVariableGen[size];
      this.localVariablesList.toArray(lg);

      for(int i = 0; i < size; ++i) {
         if (lg[i].getStart() == null) {
            lg[i].setStart(this.il.getStart());
         }

         if (lg[i].getEnd() == null) {
            lg[i].setEnd(this.il.getEnd());
         }
      }

      if (size > 1) {
         sort(lg, 0, size - 1);
      }

      return lg;
   }

   public LocalVariableTable getLocalVariableTable(ConstantPool cp) {
      LocalVariableGen[] lg = this.getLocalVariables();
      int size = lg.length;
      LocalVariable[] lv = new LocalVariable[size];

      for(int i = 0; i < size; ++i) {
         lv[i] = lg[i].getLocalVariable(cp);
      }

      return new LocalVariableTable(cp.addUtf8("LocalVariableTable"), 2 + lv.length * 10, lv, cp);
   }

   public LineNumberGen addLineNumber(InstructionHandle ih, int src_line) {
      LineNumberGen l = new LineNumberGen(ih, src_line);
      this.lineNumbersList.add(l);
      return l;
   }

   public void removeLineNumber(LineNumberGen l) {
      this.lineNumbersList.remove(l);
   }

   public void removeLineNumbers() {
      this.lineNumbersList.clear();
   }

   public LineNumberGen[] getLineNumbers() {
      LineNumberGen[] lg = new LineNumberGen[this.lineNumbersList.size()];
      this.lineNumbersList.toArray(lg);
      return lg;
   }

   public LineNumberTable getLineNumberTable(ConstantPool cp) {
      int size = this.lineNumbersList.size();
      LineNumber[] ln = new LineNumber[size];

      for(int i = 0; i < size; ++i) {
         ln[i] = ((LineNumberGen)this.lineNumbersList.get(i)).getLineNumber();
      }

      return new LineNumberTable(cp.addUtf8("LineNumberTable"), 2 + ln.length * 4, ln, cp);
   }

   public CodeExceptionGen addExceptionHandler(InstructionHandle start_pc, InstructionHandle end_pc, InstructionHandle handler_pc, ObjectType catch_type) {
      if (start_pc != null && end_pc != null && handler_pc != null) {
         CodeExceptionGen c = new CodeExceptionGen(start_pc, end_pc, handler_pc, catch_type);
         this.exceptionsList.add(c);
         return c;
      } else {
         throw new ClassGenException("Exception handler target is null instruction");
      }
   }

   public void removeExceptionHandler(CodeExceptionGen c) {
      this.exceptionsList.remove(c);
   }

   public void removeExceptionHandlers() {
      this.exceptionsList.clear();
   }

   public CodeExceptionGen[] getExceptionHandlers() {
      CodeExceptionGen[] cg = new CodeExceptionGen[this.exceptionsList.size()];
      this.exceptionsList.toArray(cg);
      return cg;
   }

   private CodeException[] getCodeExceptions() {
      int size = this.exceptionsList.size();
      CodeException[] c_exc = new CodeException[size];

      try {
         for(int i = 0; i < size; ++i) {
            CodeExceptionGen c = (CodeExceptionGen)this.exceptionsList.get(i);
            c_exc[i] = c.getCodeException(this.cp);
         }
      } catch (ArrayIndexOutOfBoundsException var5) {
      }

      return c_exc;
   }

   public void addException(String class_name) {
      this.exceptionsThrown.add(class_name);
   }

   public void removeException(String c) {
      this.exceptionsThrown.remove(c);
   }

   public void removeExceptions() {
      this.exceptionsThrown.clear();
   }

   public String[] getExceptions() {
      String[] e = new String[this.exceptionsThrown.size()];
      this.exceptionsThrown.toArray(e);
      return e;
   }

   private ExceptionTable getExceptionTable(ConstantPool cp) {
      int size = this.exceptionsThrown.size();
      int[] ex = new int[size];

      try {
         for(int i = 0; i < size; ++i) {
            ex[i] = cp.addClass((String)this.exceptionsThrown.get(i));
         }
      } catch (ArrayIndexOutOfBoundsException var5) {
      }

      return new ExceptionTable(cp.addUtf8("Exceptions"), 2 + 2 * size, ex, cp);
   }

   public void addCodeAttribute(Attribute a) {
      this.codeAttributesList.add(a);
   }

   public void addParameterAnnotationsAsAttribute(ConstantPool cp) {
      if (this.hasParameterAnnotations) {
         Attribute[] attrs = Utility.getParameterAnnotationAttributes(cp, this.param_annotations);
         if (attrs != null) {
            for(int i = 0; i < attrs.length; ++i) {
               this.addAttribute(attrs[i]);
            }
         }

      }
   }

   public void removeCodeAttribute(Attribute a) {
      this.codeAttributesList.remove(a);
   }

   public void removeCodeAttributes() {
      this.codeAttributesList.clear();
   }

   public Attribute[] getCodeAttributes() {
      Attribute[] attributes = new Attribute[this.codeAttributesList.size()];
      this.codeAttributesList.toArray(attributes);
      return attributes;
   }

   public Method getMethod() {
      String signature = this.getSignature();
      int name_index = this.cp.addUtf8(this.name);
      int signature_index = this.cp.addUtf8(signature);
      byte[] byte_code = null;
      if (this.il != null) {
         try {
            byte_code = this.il.getByteCode();
         } catch (Exception var15) {
            throw new IllegalStateException("Unexpected problem whilst preparing bytecode for " + this.getClassName() + "." + this.getName() + this.getSignature(), var15);
         }
      }

      LineNumberTable lnt = null;
      LocalVariableTable lvt = null;
      if (this.localVariablesList.size() > 0 && !this.stripAttributes) {
         this.addCodeAttribute(lvt = this.getLocalVariableTable(this.cp));
      }

      if (this.lineNumbersList.size() > 0 && !this.stripAttributes) {
         this.addCodeAttribute(lnt = this.getLineNumberTable(this.cp));
      }

      Attribute[] code_attrs = this.getCodeAttributes();
      int attrs_len = 0;

      for(int i = 0; i < code_attrs.length; ++i) {
         attrs_len += code_attrs[i].getLength() + 6;
      }

      CodeException[] c_exc = this.getCodeExceptions();
      int exc_len = c_exc.length * 8;
      Code code = null;
      if (this.il != null && !this.isAbstract()) {
         List attributes = this.getAttributes();

         for(int i = 0; i < attributes.size(); ++i) {
            Attribute a = (Attribute)attributes.get(i);
            if (a instanceof Code) {
               this.removeAttribute(a);
            }
         }

         code = new Code(this.cp.addUtf8("Code"), 8 + byte_code.length + 2 + exc_len + 2 + attrs_len, this.maxStack, this.maxLocals, byte_code, c_exc, code_attrs, this.cp);
         this.addAttribute(code);
      }

      this.addAnnotationsAsAttribute(this.cp);
      this.addParameterAnnotationsAsAttribute(this.cp);
      ExceptionTable et = null;
      if (this.exceptionsThrown.size() > 0) {
         this.addAttribute(et = this.getExceptionTable(this.cp));
      }

      Method m = new Method(this.modifiers, name_index, signature_index, this.getAttributesImmutable(), this.cp);
      if (lvt != null) {
         this.removeCodeAttribute(lvt);
      }

      if (lnt != null) {
         this.removeCodeAttribute(lnt);
      }

      if (code != null) {
         this.removeAttribute(code);
      }

      if (et != null) {
         this.removeAttribute(et);
      }

      return m;
   }

   public void setMaxLocals(int m) {
      this.maxLocals = m;
   }

   public int getMaxLocals() {
      return this.maxLocals;
   }

   public void setMaxStack(int m) {
      this.maxStack = m;
   }

   public int getMaxStack() {
      return this.maxStack;
   }

   public String getClassName() {
      return this.classname;
   }

   public void setClassName(String class_name) {
      this.classname = class_name;
   }

   public void setReturnType(Type return_type) {
      this.setType(return_type);
   }

   public Type getReturnType() {
      return this.getType();
   }

   public void setArgumentTypes(Type[] arg_types) {
      this.parameterTypes = arg_types;
   }

   public Type[] getArgumentTypes() {
      return this.parameterTypes;
   }

   public void setArgumentType(int i, Type type) {
      this.parameterTypes[i] = type;
   }

   public Type getArgumentType(int i) {
      return this.parameterTypes[i];
   }

   public void setArgumentNames(String[] arg_names) {
      this.parameterNames = arg_names;
   }

   public String[] getArgumentNames() {
      return this.parameterNames != null ? (String[])this.parameterNames.clone() : new String[0];
   }

   public void setArgumentName(int i, String name) {
      this.parameterNames[i] = name;
   }

   public String getArgumentName(int i) {
      return this.parameterNames[i];
   }

   public InstructionList getInstructionList() {
      return this.il;
   }

   public void setInstructionList(InstructionList il) {
      this.il = il;
   }

   public String getSignature() {
      return Utility.toMethodSignature(this.type, this.parameterTypes);
   }

   public void setMaxStack() {
      if (this.il != null) {
         this.maxStack = getMaxStack(this.cp, this.il, this.getExceptionHandlers());
      } else {
         this.maxStack = 0;
      }

   }

   public void setMaxLocals() {
      if (this.il != null) {
         int max = this.isStatic() ? 0 : 1;
         if (this.parameterTypes != null) {
            for(int i = 0; i < this.parameterTypes.length; ++i) {
               max += this.parameterTypes[i].getSize();
            }
         }

         for(InstructionHandle ih = this.il.getStart(); ih != null; ih = ih.getNext()) {
            Instruction ins = ih.getInstruction();
            if (ins instanceof InstructionLV || ins instanceof RET) {
               int index = ins.getIndex() + ins.getType(this.cp).getSize();
               if (index > max) {
                  max = index;
               }
            }
         }

         this.maxLocals = max;
      } else {
         this.maxLocals = 0;
      }

   }

   public void stripAttributes(boolean flag) {
      this.stripAttributes = flag;
   }

   public static int getMaxStack(ConstantPool cp, InstructionList il, CodeExceptionGen[] et) {
      BranchStack branchTargets = new BranchStack();
      int stackDepth = 0;
      int maxStackDepth = 0;
      int i = 0;

      for(int max = et.length; i < max; ++i) {
         InstructionHandle handlerPos = et[i].getHandlerPC();
         if (handlerPos != null) {
            maxStackDepth = 1;
            branchTargets.push(handlerPos, 1);
         }
      }

      InstructionHandle ih = il.getStart();

      while(ih != null) {
         Instruction instruction = ih.getInstruction();
         short opcode = instruction.opcode;
         int prod = instruction.produceStack(cp);
         int con = instruction.consumeStack(cp);
         int delta = prod - con;
         stackDepth += delta;
         if (stackDepth > maxStackDepth) {
            maxStackDepth = stackDepth;
         }

         if (!(instruction instanceof InstructionBranch)) {
            if (opcode == 191 || opcode == 169 || opcode >= 172 && opcode <= 177) {
               ih = null;
            }
         } else {
            InstructionBranch branch = (InstructionBranch)instruction;
            if (instruction instanceof InstructionSelect) {
               InstructionSelect select = (InstructionSelect)branch;
               InstructionHandle[] targets = select.getTargets();

               for(int i = 0; i < targets.length; ++i) {
                  branchTargets.push(targets[i], stackDepth);
               }

               ih = null;
            } else if (!branch.isIfInstruction()) {
               if (opcode == 168 || opcode == 201) {
                  branchTargets.push(ih.getNext(), stackDepth - 1);
               }

               ih = null;
            }

            branchTargets.push(branch.getTarget(), stackDepth);
         }

         if (ih != null) {
            ih = ih.getNext();
         }

         if (ih == null) {
            BranchTarget bt = branchTargets.pop();
            if (bt != null) {
               ih = bt.target;
               stackDepth = bt.stackDepth;
            }
         }
      }

      return maxStackDepth;
   }

   public final String toString() {
      String access = Utility.accessToString(this.modifiers);
      String signature = Utility.toMethodSignature(this.type, this.parameterTypes);
      signature = Utility.methodSignatureToString(signature, this.name, access, true, this.getLocalVariableTable(this.cp));
      StringBuffer buf = new StringBuffer(signature);
      if (this.exceptionsThrown.size() > 0) {
         Iterator e = this.exceptionsThrown.iterator();

         while(e.hasNext()) {
            buf.append("\n\t\tthrows " + (String)e.next());
         }
      }

      return buf.toString();
   }

   public List getAnnotationsOnParameter(int i) {
      this.ensureExistingParameterAnnotationsUnpacked();
      return this.hasParameterAnnotations && i <= this.parameterTypes.length ? this.param_annotations[i] : null;
   }

   private void ensureExistingParameterAnnotationsUnpacked() {
      if (!this.haveUnpackedParameterAnnotations) {
         List attrs = this.getAttributes();
         RuntimeParamAnnos paramAnnVisAttr = null;
         RuntimeParamAnnos paramAnnInvisAttr = null;
         Iterator var4 = attrs.iterator();

         while(true) {
            Attribute attribute;
            do {
               if (!var4.hasNext()) {
                  if (paramAnnVisAttr != null) {
                     this.removeAttribute(paramAnnVisAttr);
                  }

                  if (paramAnnInvisAttr != null) {
                     this.removeAttribute(paramAnnInvisAttr);
                  }

                  this.haveUnpackedParameterAnnotations = true;
                  return;
               }

               attribute = (Attribute)var4.next();
            } while(!(attribute instanceof RuntimeParamAnnos));

            if (!this.hasParameterAnnotations) {
               this.param_annotations = new List[this.parameterTypes.length];

               for(int j = 0; j < this.parameterTypes.length; ++j) {
                  this.param_annotations[j] = new ArrayList();
               }
            }

            this.hasParameterAnnotations = true;
            RuntimeParamAnnos rpa = (RuntimeParamAnnos)attribute;
            if (rpa.areVisible()) {
               paramAnnVisAttr = rpa;
            } else {
               paramAnnInvisAttr = rpa;
            }

            for(int j = 0; j < this.parameterTypes.length; ++j) {
               AnnotationGen[] annos = rpa.getAnnotationsOnParameter(j);
               AnnotationGen[] var9 = annos;
               int var10 = annos.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  AnnotationGen anAnnotation = var9[var11];
                  this.param_annotations[j].add(anAnnotation);
               }
            }
         }
      }
   }

   private List makeMutableVersion(AnnotationGen[] mutableArray) {
      List result = new ArrayList();

      for(int i = 0; i < mutableArray.length; ++i) {
         result.add(new AnnotationGen(mutableArray[i], this.getConstantPool(), false));
      }

      return result;
   }

   public void addParameterAnnotation(int parameterIndex, AnnotationGen annotation) {
      this.ensureExistingParameterAnnotationsUnpacked();
      if (!this.hasParameterAnnotations) {
         this.param_annotations = new List[this.parameterTypes.length];
         this.hasParameterAnnotations = true;
      }

      List existingAnnotations = this.param_annotations[parameterIndex];
      if (existingAnnotations != null) {
         existingAnnotations.add(annotation);
      } else {
         List l = new ArrayList();
         l.add(annotation);
         this.param_annotations[parameterIndex] = l;
      }

   }

   static final class BranchStack {
      Stack branchTargets = new Stack();
      Hashtable visitedTargets = new Hashtable();

      public void push(InstructionHandle target, int stackDepth) {
         if (!this.visited(target)) {
            this.branchTargets.push(this.visit(target, stackDepth));
         }
      }

      public BranchTarget pop() {
         if (!this.branchTargets.empty()) {
            BranchTarget bt = (BranchTarget)this.branchTargets.pop();
            return bt;
         } else {
            return null;
         }
      }

      private final BranchTarget visit(InstructionHandle target, int stackDepth) {
         BranchTarget bt = new BranchTarget(target, stackDepth);
         this.visitedTargets.put(target, bt);
         return bt;
      }

      private final boolean visited(InstructionHandle target) {
         return this.visitedTargets.get(target) != null;
      }
   }

   static final class BranchTarget {
      InstructionHandle target;
      int stackDepth;

      BranchTarget(InstructionHandle target, int stackDepth) {
         this.target = target;
         this.stackDepth = stackDepth;
      }
   }
}
