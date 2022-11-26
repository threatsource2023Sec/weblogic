package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public class MethodNode extends MethodVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public List exceptions;
   public List parameters;
   public List visibleAnnotations;
   public List invisibleAnnotations;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   public List attrs;
   public Object annotationDefault;
   public int visibleAnnotableParameterCount;
   public List[] visibleParameterAnnotations;
   public int invisibleAnnotableParameterCount;
   public List[] invisibleParameterAnnotations;
   public InsnList instructions;
   public List tryCatchBlocks;
   public int maxStack;
   public int maxLocals;
   public List localVariables;
   public List visibleLocalVariableAnnotations;
   public List invisibleLocalVariableAnnotations;
   private boolean visited;

   public MethodNode() {
      this(458752);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int api) {
      super(api);
      this.instructions = new InsnList();
   }

   public MethodNode(int access, String name, String descriptor, String signature, String[] exceptions) {
      this(458752, access, name, descriptor, signature, exceptions);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int api, int access, String name, String descriptor, String signature, String[] exceptions) {
      super(api);
      this.access = access;
      this.name = name;
      this.desc = descriptor;
      this.signature = signature;
      this.exceptions = Util.asArrayList((Object[])exceptions);
      if ((access & 1024) == 0) {
         this.localVariables = new ArrayList(5);
      }

      this.tryCatchBlocks = new ArrayList();
      this.instructions = new InsnList();
   }

   public void visitParameter(String name, int access) {
      if (this.parameters == null) {
         this.parameters = new ArrayList(5);
      }

      this.parameters.add(new ParameterNode(name, access));
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return new AnnotationNode(new ArrayList(0) {
         public boolean add(Object o) {
            MethodNode.this.annotationDefault = o;
            return super.add(o);
         }
      });
   }

   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      if (visible) {
         if (this.visibleAnnotations == null) {
            this.visibleAnnotations = new ArrayList(1);
         }

         this.visibleAnnotations.add(annotation);
      } else {
         if (this.invisibleAnnotations == null) {
            this.invisibleAnnotations = new ArrayList(1);
         }

         this.invisibleAnnotations.add(annotation);
      }

      return annotation;
   }

   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         if (this.visibleTypeAnnotations == null) {
            this.visibleTypeAnnotations = new ArrayList(1);
         }

         this.visibleTypeAnnotations.add(typeAnnotation);
      } else {
         if (this.invisibleTypeAnnotations == null) {
            this.invisibleTypeAnnotations = new ArrayList(1);
         }

         this.invisibleTypeAnnotations.add(typeAnnotation);
      }

      return typeAnnotation;
   }

   public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
      if (visible) {
         this.visibleAnnotableParameterCount = parameterCount;
      } else {
         this.invisibleAnnotableParameterCount = parameterCount;
      }

   }

   public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      int params;
      if (visible) {
         if (this.visibleParameterAnnotations == null) {
            params = Type.getArgumentTypes(this.desc).length;
            this.visibleParameterAnnotations = (List[])(new List[params]);
         }

         if (this.visibleParameterAnnotations[parameter] == null) {
            this.visibleParameterAnnotations[parameter] = new ArrayList(1);
         }

         this.visibleParameterAnnotations[parameter].add(annotation);
      } else {
         if (this.invisibleParameterAnnotations == null) {
            params = Type.getArgumentTypes(this.desc).length;
            this.invisibleParameterAnnotations = (List[])(new List[params]);
         }

         if (this.invisibleParameterAnnotations[parameter] == null) {
            this.invisibleParameterAnnotations[parameter] = new ArrayList(1);
         }

         this.invisibleParameterAnnotations[parameter].add(annotation);
      }

      return annotation;
   }

   public void visitAttribute(Attribute attribute) {
      if (this.attrs == null) {
         this.attrs = new ArrayList(1);
      }

      this.attrs.add(attribute);
   }

   public void visitCode() {
   }

   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      this.instructions.add((AbstractInsnNode)(new FrameNode(type, numLocal, local == null ? null : this.getLabelNodes(local), numStack, stack == null ? null : this.getLabelNodes(stack))));
   }

   public void visitInsn(int opcode) {
      this.instructions.add((AbstractInsnNode)(new InsnNode(opcode)));
   }

   public void visitIntInsn(int opcode, int operand) {
      this.instructions.add((AbstractInsnNode)(new IntInsnNode(opcode, operand)));
   }

   public void visitVarInsn(int opcode, int var) {
      this.instructions.add((AbstractInsnNode)(new VarInsnNode(opcode, var)));
   }

   public void visitTypeInsn(int opcode, String type) {
      this.instructions.add((AbstractInsnNode)(new TypeInsnNode(opcode, type)));
   }

   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      this.instructions.add((AbstractInsnNode)(new FieldInsnNode(opcode, owner, name, descriptor)));
   }

   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         int opcode = opcodeAndSource & -257;
         this.instructions.add((AbstractInsnNode)(new MethodInsnNode(opcode, owner, name, descriptor, isInterface)));
      }
   }

   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      this.instructions.add((AbstractInsnNode)(new InvokeDynamicInsnNode(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments)));
   }

   public void visitJumpInsn(int opcode, Label label) {
      this.instructions.add((AbstractInsnNode)(new JumpInsnNode(opcode, this.getLabelNode(label))));
   }

   public void visitLabel(Label label) {
      this.instructions.add((AbstractInsnNode)this.getLabelNode(label));
   }

   public void visitLdcInsn(Object value) {
      this.instructions.add((AbstractInsnNode)(new LdcInsnNode(value)));
   }

   public void visitIincInsn(int var, int increment) {
      this.instructions.add((AbstractInsnNode)(new IincInsnNode(var, increment)));
   }

   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      this.instructions.add((AbstractInsnNode)(new TableSwitchInsnNode(min, max, this.getLabelNode(dflt), this.getLabelNodes(labels))));
   }

   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      this.instructions.add((AbstractInsnNode)(new LookupSwitchInsnNode(this.getLabelNode(dflt), keys, this.getLabelNodes(labels))));
   }

   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      this.instructions.add((AbstractInsnNode)(new MultiANewArrayInsnNode(descriptor, numDimensions)));
   }

   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      AbstractInsnNode currentInsn;
      for(currentInsn = this.instructions.getLast(); currentInsn.getOpcode() == -1; currentInsn = currentInsn.getPrevious()) {
      }

      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         if (currentInsn.visibleTypeAnnotations == null) {
            currentInsn.visibleTypeAnnotations = new ArrayList(1);
         }

         currentInsn.visibleTypeAnnotations.add(typeAnnotation);
      } else {
         if (currentInsn.invisibleTypeAnnotations == null) {
            currentInsn.invisibleTypeAnnotations = new ArrayList(1);
         }

         currentInsn.invisibleTypeAnnotations.add(typeAnnotation);
      }

      return typeAnnotation;
   }

   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      if (this.tryCatchBlocks == null) {
         this.tryCatchBlocks = new ArrayList(1);
      }

      this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(start), this.getLabelNode(end), this.getLabelNode(handler), type));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TryCatchBlockNode tryCatchBlock = (TryCatchBlockNode)this.tryCatchBlocks.get((typeRef & 16776960) >> 8);
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         if (tryCatchBlock.visibleTypeAnnotations == null) {
            tryCatchBlock.visibleTypeAnnotations = new ArrayList(1);
         }

         tryCatchBlock.visibleTypeAnnotations.add(typeAnnotation);
      } else {
         if (tryCatchBlock.invisibleTypeAnnotations == null) {
            tryCatchBlock.invisibleTypeAnnotations = new ArrayList(1);
         }

         tryCatchBlock.invisibleTypeAnnotations.add(typeAnnotation);
      }

      return typeAnnotation;
   }

   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      if (this.localVariables == null) {
         this.localVariables = new ArrayList(1);
      }

      this.localVariables.add(new LocalVariableNode(name, descriptor, signature, this.getLabelNode(start), this.getLabelNode(end), index));
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
      LocalVariableAnnotationNode localVariableAnnotation = new LocalVariableAnnotationNode(typeRef, typePath, this.getLabelNodes(start), this.getLabelNodes(end), index, descriptor);
      if (visible) {
         if (this.visibleLocalVariableAnnotations == null) {
            this.visibleLocalVariableAnnotations = new ArrayList(1);
         }

         this.visibleLocalVariableAnnotations.add(localVariableAnnotation);
      } else {
         if (this.invisibleLocalVariableAnnotations == null) {
            this.invisibleLocalVariableAnnotations = new ArrayList(1);
         }

         this.invisibleLocalVariableAnnotations.add(localVariableAnnotation);
      }

      return localVariableAnnotation;
   }

   public void visitLineNumber(int line, Label start) {
      this.instructions.add((AbstractInsnNode)(new LineNumberNode(line, this.getLabelNode(start))));
   }

   public void visitMaxs(int maxStack, int maxLocals) {
      this.maxStack = maxStack;
      this.maxLocals = maxLocals;
   }

   public void visitEnd() {
   }

   protected LabelNode getLabelNode(Label label) {
      if (!(label.info instanceof LabelNode)) {
         label.info = new LabelNode();
      }

      return (LabelNode)label.info;
   }

   private LabelNode[] getLabelNodes(Label[] labels) {
      LabelNode[] labelNodes = new LabelNode[labels.length];
      int i = 0;

      for(int n = labels.length; i < n; ++i) {
         labelNodes[i] = this.getLabelNode(labels[i]);
      }

      return labelNodes;
   }

   private Object[] getLabelNodes(Object[] objects) {
      Object[] labelNodes = new Object[objects.length];
      int i = 0;

      for(int n = objects.length; i < n; ++i) {
         Object o = objects[i];
         if (o instanceof Label) {
            o = this.getLabelNode((Label)o);
         }

         labelNodes[i] = o;
      }

      return labelNodes;
   }

   public void check(int api) {
      int i;
      AbstractInsnNode insn;
      Object value;
      if (api == 262144) {
         if (this.parameters != null && !this.parameters.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.tryCatchBlocks != null) {
            for(i = this.tryCatchBlocks.size() - 1; i >= 0; --i) {
               TryCatchBlockNode tryCatchBlock = (TryCatchBlockNode)this.tryCatchBlocks.get(i);
               if (tryCatchBlock.visibleTypeAnnotations != null && !tryCatchBlock.visibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }

               if (tryCatchBlock.invisibleTypeAnnotations != null && !tryCatchBlock.invisibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }

         i = this.instructions.size() - 1;

         while(true) {
            if (i < 0) {
               if (this.visibleLocalVariableAnnotations != null && !this.visibleLocalVariableAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }

               if (this.invisibleLocalVariableAnnotations != null && !this.invisibleLocalVariableAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }
               break;
            }

            insn = this.instructions.get(i);
            if (insn.visibleTypeAnnotations != null && !insn.visibleTypeAnnotations.isEmpty()) {
               throw new UnsupportedClassVersionException();
            }

            if (insn.invisibleTypeAnnotations != null && !insn.invisibleTypeAnnotations.isEmpty()) {
               throw new UnsupportedClassVersionException();
            }

            if (insn instanceof MethodInsnNode) {
               boolean isInterface = ((MethodInsnNode)insn).itf;
               if (isInterface != (insn.opcode == 185)) {
                  throw new UnsupportedClassVersionException();
               }
            } else if (insn instanceof LdcInsnNode) {
               value = ((LdcInsnNode)insn).cst;
               if (value instanceof Handle || value instanceof Type && ((Type)value).getSort() == 11) {
                  throw new UnsupportedClassVersionException();
               }
            }

            --i;
         }
      }

      if (api != 458752) {
         for(i = this.instructions.size() - 1; i >= 0; --i) {
            insn = this.instructions.get(i);
            if (insn instanceof LdcInsnNode) {
               value = ((LdcInsnNode)insn).cst;
               if (value instanceof ConstantDynamic) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }
      }

   }

   public void accept(ClassVisitor classVisitor) {
      String[] exceptionsArray = this.exceptions == null ? null : (String[])this.exceptions.toArray(new String[0]);
      MethodVisitor methodVisitor = classVisitor.visitMethod(this.access, this.name, this.desc, this.signature, exceptionsArray);
      if (methodVisitor != null) {
         this.accept(methodVisitor);
      }

   }

   public void accept(MethodVisitor methodVisitor) {
      int i;
      int n;
      if (this.parameters != null) {
         i = 0;

         for(n = this.parameters.size(); i < n; ++i) {
            ((ParameterNode)this.parameters.get(i)).accept(methodVisitor);
         }
      }

      if (this.annotationDefault != null) {
         AnnotationVisitor annotationVisitor = methodVisitor.visitAnnotationDefault();
         AnnotationNode.accept(annotationVisitor, (String)null, this.annotationDefault);
         if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
         }
      }

      AnnotationNode annotation;
      if (this.visibleAnnotations != null) {
         i = 0;

         for(n = this.visibleAnnotations.size(); i < n; ++i) {
            annotation = (AnnotationNode)this.visibleAnnotations.get(i);
            annotation.accept(methodVisitor.visitAnnotation(annotation.desc, true));
         }
      }

      if (this.invisibleAnnotations != null) {
         i = 0;

         for(n = this.invisibleAnnotations.size(); i < n; ++i) {
            annotation = (AnnotationNode)this.invisibleAnnotations.get(i);
            annotation.accept(methodVisitor.visitAnnotation(annotation.desc, false));
         }
      }

      TypeAnnotationNode typeAnnotation;
      if (this.visibleTypeAnnotations != null) {
         i = 0;

         for(n = this.visibleTypeAnnotations.size(); i < n; ++i) {
            typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         i = 0;

         for(n = this.invisibleTypeAnnotations.size(); i < n; ++i) {
            typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
         }
      }

      if (this.visibleAnnotableParameterCount > 0) {
         methodVisitor.visitAnnotableParameterCount(this.visibleAnnotableParameterCount, true);
      }

      int j;
      int m;
      AnnotationNode annotation;
      List parameterAnnotations;
      if (this.visibleParameterAnnotations != null) {
         i = 0;

         for(n = this.visibleParameterAnnotations.length; i < n; ++i) {
            parameterAnnotations = this.visibleParameterAnnotations[i];
            if (parameterAnnotations != null) {
               j = 0;

               for(m = parameterAnnotations.size(); j < m; ++j) {
                  annotation = (AnnotationNode)parameterAnnotations.get(j);
                  annotation.accept(methodVisitor.visitParameterAnnotation(i, annotation.desc, true));
               }
            }
         }
      }

      if (this.invisibleAnnotableParameterCount > 0) {
         methodVisitor.visitAnnotableParameterCount(this.invisibleAnnotableParameterCount, false);
      }

      if (this.invisibleParameterAnnotations != null) {
         i = 0;

         for(n = this.invisibleParameterAnnotations.length; i < n; ++i) {
            parameterAnnotations = this.invisibleParameterAnnotations[i];
            if (parameterAnnotations != null) {
               j = 0;

               for(m = parameterAnnotations.size(); j < m; ++j) {
                  annotation = (AnnotationNode)parameterAnnotations.get(j);
                  annotation.accept(methodVisitor.visitParameterAnnotation(i, annotation.desc, false));
               }
            }
         }
      }

      if (this.visited) {
         this.instructions.resetLabels();
      }

      if (this.attrs != null) {
         i = 0;

         for(n = this.attrs.size(); i < n; ++i) {
            methodVisitor.visitAttribute((Attribute)this.attrs.get(i));
         }
      }

      if (this.instructions.size() > 0) {
         methodVisitor.visitCode();
         if (this.tryCatchBlocks != null) {
            i = 0;

            for(n = this.tryCatchBlocks.size(); i < n; ++i) {
               ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).updateIndex(i);
               ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).accept(methodVisitor);
            }
         }

         this.instructions.accept(methodVisitor);
         if (this.localVariables != null) {
            i = 0;

            for(n = this.localVariables.size(); i < n; ++i) {
               ((LocalVariableNode)this.localVariables.get(i)).accept(methodVisitor);
            }
         }

         if (this.visibleLocalVariableAnnotations != null) {
            i = 0;

            for(n = this.visibleLocalVariableAnnotations.size(); i < n; ++i) {
               ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(i)).accept(methodVisitor, true);
            }
         }

         if (this.invisibleLocalVariableAnnotations != null) {
            i = 0;

            for(n = this.invisibleLocalVariableAnnotations.size(); i < n; ++i) {
               ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(i)).accept(methodVisitor, false);
            }
         }

         methodVisitor.visitMaxs(this.maxStack, this.maxLocals);
         this.visited = true;
      }

      methodVisitor.visitEnd();
   }
}
