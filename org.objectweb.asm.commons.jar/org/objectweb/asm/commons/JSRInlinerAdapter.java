package org.objectweb.asm.commons;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class JSRInlinerAdapter extends MethodNode implements Opcodes {
   private final BitSet mainSubroutineInsns;
   private final Map subroutinesInsns;
   final BitSet sharedSubroutineInsns;

   public JSRInlinerAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
      this(458752, methodVisitor, access, name, descriptor, signature, exceptions);
      if (this.getClass() != JSRInlinerAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected JSRInlinerAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
      super(api, access, name, descriptor, signature, exceptions);
      this.mainSubroutineInsns = new BitSet();
      this.subroutinesInsns = new HashMap();
      this.sharedSubroutineInsns = new BitSet();
      this.mv = methodVisitor;
   }

   public void visitJumpInsn(int opcode, Label label) {
      super.visitJumpInsn(opcode, label);
      LabelNode labelNode = ((JumpInsnNode)this.instructions.getLast()).label;
      if (opcode == 168 && !this.subroutinesInsns.containsKey(labelNode)) {
         this.subroutinesInsns.put(labelNode, new BitSet());
      }

   }

   public void visitEnd() {
      if (!this.subroutinesInsns.isEmpty()) {
         this.findSubroutinesInsns();
         this.emitCode();
      }

      if (this.mv != null) {
         this.accept(this.mv);
      }

   }

   private void findSubroutinesInsns() {
      BitSet visitedInsns = new BitSet();
      this.findSubroutineInsns(0, this.mainSubroutineInsns, visitedInsns);
      Iterator var2 = this.subroutinesInsns.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         LabelNode jsrLabelNode = (LabelNode)entry.getKey();
         BitSet subroutineInsns = (BitSet)entry.getValue();
         this.findSubroutineInsns(this.instructions.indexOf(jsrLabelNode), subroutineInsns, visitedInsns);
      }

   }

   private void findSubroutineInsns(int startInsnIndex, BitSet subroutineInsns, BitSet visitedInsns) {
      this.findReachableInsns(startInsnIndex, subroutineInsns, visitedInsns);

      boolean applicableHandlerFound;
      do {
         applicableHandlerFound = false;
         Iterator var5 = this.tryCatchBlocks.iterator();

         while(var5.hasNext()) {
            TryCatchBlockNode tryCatchBlockNode = (TryCatchBlockNode)var5.next();
            int handlerIndex = this.instructions.indexOf(tryCatchBlockNode.handler);
            if (!subroutineInsns.get(handlerIndex)) {
               int startIndex = this.instructions.indexOf(tryCatchBlockNode.start);
               int endIndex = this.instructions.indexOf(tryCatchBlockNode.end);
               int firstSubroutineInsnAfterTryCatchStart = subroutineInsns.nextSetBit(startIndex);
               if (firstSubroutineInsnAfterTryCatchStart >= startIndex && firstSubroutineInsnAfterTryCatchStart < endIndex) {
                  this.findReachableInsns(handlerIndex, subroutineInsns, visitedInsns);
                  applicableHandlerFound = true;
               }
            }
         }
      } while(applicableHandlerFound);

   }

   private void findReachableInsns(int insnIndex, BitSet subroutineInsns, BitSet visitedInsns) {
      int currentInsnIndex = insnIndex;

      while(currentInsnIndex < this.instructions.size()) {
         if (subroutineInsns.get(currentInsnIndex)) {
            return;
         }

         subroutineInsns.set(currentInsnIndex);
         if (visitedInsns.get(currentInsnIndex)) {
            this.sharedSubroutineInsns.set(currentInsnIndex);
         }

         visitedInsns.set(currentInsnIndex);
         AbstractInsnNode currentInsnNode = this.instructions.get(currentInsnIndex);
         if (currentInsnNode.getType() == 7 && currentInsnNode.getOpcode() != 168) {
            JumpInsnNode jumpInsnNode = (JumpInsnNode)currentInsnNode;
            this.findReachableInsns(this.instructions.indexOf(jumpInsnNode.label), subroutineInsns, visitedInsns);
         } else {
            Iterator var7;
            LabelNode labelNode;
            if (currentInsnNode.getType() == 11) {
               TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)currentInsnNode;
               this.findReachableInsns(this.instructions.indexOf(tableSwitchInsnNode.dflt), subroutineInsns, visitedInsns);
               var7 = tableSwitchInsnNode.labels.iterator();

               while(var7.hasNext()) {
                  labelNode = (LabelNode)var7.next();
                  this.findReachableInsns(this.instructions.indexOf(labelNode), subroutineInsns, visitedInsns);
               }
            } else if (currentInsnNode.getType() == 12) {
               LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)currentInsnNode;
               this.findReachableInsns(this.instructions.indexOf(lookupSwitchInsnNode.dflt), subroutineInsns, visitedInsns);
               var7 = lookupSwitchInsnNode.labels.iterator();

               while(var7.hasNext()) {
                  labelNode = (LabelNode)var7.next();
                  this.findReachableInsns(this.instructions.indexOf(labelNode), subroutineInsns, visitedInsns);
               }
            }
         }

         switch (this.instructions.get(currentInsnIndex).getOpcode()) {
            case 167:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 191:
               return;
            case 168:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            default:
               ++currentInsnIndex;
         }
      }

   }

   private void emitCode() {
      LinkedList worklist = new LinkedList();
      worklist.add(new Instantiation((Instantiation)null, this.mainSubroutineInsns));
      InsnList newInstructions = new InsnList();
      List newTryCatchBlocks = new ArrayList();
      List newLocalVariables = new ArrayList();

      while(!worklist.isEmpty()) {
         Instantiation instantiation = (Instantiation)worklist.removeFirst();
         this.emitInstantiation(instantiation, worklist, newInstructions, newTryCatchBlocks, newLocalVariables);
      }

      this.instructions = newInstructions;
      this.tryCatchBlocks = newTryCatchBlocks;
      this.localVariables = newLocalVariables;
   }

   private void emitInstantiation(Instantiation instantiation, List worklist, InsnList newInstructions, List newTryCatchBlocks, List newLocalVariables) {
      LabelNode previousLabelNode = null;

      LabelNode retLabel;
      LabelNode end;
      for(int i = 0; i < this.instructions.size(); ++i) {
         AbstractInsnNode insnNode = this.instructions.get(i);
         if (insnNode.getType() == 8) {
            retLabel = (LabelNode)insnNode;
            end = instantiation.getClonedLabel(retLabel);
            if (end != previousLabelNode) {
               newInstructions.add(end);
               previousLabelNode = end;
            }
         } else if (instantiation.findOwner(i) == instantiation) {
            if (insnNode.getOpcode() != 169) {
               if (insnNode.getOpcode() == 168) {
                  retLabel = ((JumpInsnNode)insnNode).label;
                  BitSet subroutineInsns = (BitSet)this.subroutinesInsns.get(retLabel);
                  Instantiation newInstantiation = new Instantiation(instantiation, subroutineInsns);
                  LabelNode clonedJsrLabelNode = newInstantiation.getClonedLabelForJumpInsn(retLabel);
                  newInstructions.add(new InsnNode(1));
                  newInstructions.add(new JumpInsnNode(167, clonedJsrLabelNode));
                  newInstructions.add(newInstantiation.returnLabel);
                  worklist.add(newInstantiation);
               } else {
                  newInstructions.add(insnNode.clone(instantiation));
               }
            } else {
               retLabel = null;

               for(Instantiation retLabelOwner = instantiation; retLabelOwner != null; retLabelOwner = retLabelOwner.parent) {
                  if (retLabelOwner.subroutineInsns.get(i)) {
                     retLabel = retLabelOwner.returnLabel;
                  }
               }

               if (retLabel == null) {
                  throw new IllegalArgumentException("Instruction #" + i + " is a RET not owned by any subroutine");
               }

               newInstructions.add(new JumpInsnNode(167, retLabel));
            }
         }
      }

      Iterator var13 = this.tryCatchBlocks.iterator();

      while(true) {
         TryCatchBlockNode tryCatchBlockNode;
         do {
            if (!var13.hasNext()) {
               var13 = this.localVariables.iterator();

               while(var13.hasNext()) {
                  LocalVariableNode localVariableNode = (LocalVariableNode)var13.next();
                  retLabel = instantiation.getClonedLabel(localVariableNode.start);
                  end = instantiation.getClonedLabel(localVariableNode.end);
                  if (retLabel != end) {
                     newLocalVariables.add(new LocalVariableNode(localVariableNode.name, localVariableNode.desc, localVariableNode.signature, retLabel, end, localVariableNode.index));
                  }
               }

               return;
            }

            tryCatchBlockNode = (TryCatchBlockNode)var13.next();
            retLabel = instantiation.getClonedLabel(tryCatchBlockNode.start);
            end = instantiation.getClonedLabel(tryCatchBlockNode.end);
         } while(retLabel == end);

         LabelNode handler = instantiation.getClonedLabelForJumpInsn(tryCatchBlockNode.handler);
         if (retLabel == null || end == null || handler == null) {
            throw new AssertionError("Internal error!");
         }

         newTryCatchBlocks.add(new TryCatchBlockNode(retLabel, end, handler, tryCatchBlockNode.type));
      }
   }

   private class Instantiation extends AbstractMap {
      final Instantiation parent;
      final BitSet subroutineInsns;
      final Map clonedLabels;
      final LabelNode returnLabel;

      Instantiation(Instantiation parent, BitSet subroutineInsns) {
         for(Instantiation instantiation = parent; instantiation != null; instantiation = instantiation.parent) {
            if (instantiation.subroutineInsns == subroutineInsns) {
               throw new IllegalArgumentException("Recursive invocation of " + subroutineInsns);
            }
         }

         this.parent = parent;
         this.subroutineInsns = subroutineInsns;
         this.returnLabel = parent == null ? null : new LabelNode();
         this.clonedLabels = new HashMap();
         LabelNode clonedLabelNode = null;

         for(int insnIndex = 0; insnIndex < JSRInlinerAdapter.this.instructions.size(); ++insnIndex) {
            AbstractInsnNode insnNode = JSRInlinerAdapter.this.instructions.get(insnIndex);
            if (insnNode.getType() == 8) {
               LabelNode labelNode = (LabelNode)insnNode;
               if (clonedLabelNode == null) {
                  clonedLabelNode = new LabelNode();
               }

               this.clonedLabels.put(labelNode, clonedLabelNode);
            } else if (this.findOwner(insnIndex) == this) {
               clonedLabelNode = null;
            }
         }

      }

      Instantiation findOwner(int insnIndex) {
         if (!this.subroutineInsns.get(insnIndex)) {
            return null;
         } else if (!JSRInlinerAdapter.this.sharedSubroutineInsns.get(insnIndex)) {
            return this;
         } else {
            Instantiation owner = this;

            for(Instantiation instantiation = this.parent; instantiation != null; instantiation = instantiation.parent) {
               if (instantiation.subroutineInsns.get(insnIndex)) {
                  owner = instantiation;
               }
            }

            return owner;
         }
      }

      LabelNode getClonedLabelForJumpInsn(LabelNode labelNode) {
         return (LabelNode)this.findOwner(JSRInlinerAdapter.this.instructions.indexOf(labelNode)).clonedLabels.get(labelNode);
      }

      LabelNode getClonedLabel(LabelNode labelNode) {
         return (LabelNode)this.clonedLabels.get(labelNode);
      }

      public Set entrySet() {
         throw new UnsupportedOperationException();
      }

      public LabelNode get(Object key) {
         return this.getClonedLabelForJumpInsn((LabelNode)key);
      }

      public boolean equals(Object other) {
         throw new UnsupportedOperationException();
      }

      public int hashCode() {
         throw new UnsupportedOperationException();
      }
   }
}
