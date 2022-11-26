package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Constant;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ByteSequence;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class InstructionList implements Serializable {
   private InstructionHandle start = null;
   private InstructionHandle end = null;
   private int length = 0;
   private int[] positions;

   public InstructionList() {
   }

   public InstructionList(Instruction i) {
      this.append(i);
   }

   public boolean isEmpty() {
      return this.start == null;
   }

   public static InstructionHandle findHandle(InstructionHandle[] ihs, int[] pos, int count, int target) {
      return findHandle(ihs, pos, count, target, false);
   }

   public static InstructionHandle findHandle(InstructionHandle[] ihs, int[] pos, int count, int target, boolean returnClosestIfNoExactMatch) {
      int l = 0;
      int r = count - 1;

      int i;
      do {
         i = (l + r) / 2;
         int j = pos[i];
         if (j == target) {
            return ihs[i];
         }

         if (target < j) {
            r = i - 1;
         } else {
            l = i + 1;
         }
      } while(l <= r);

      if (returnClosestIfNoExactMatch) {
         i = (l + r) / 2;
         if (i < 0) {
            i = 0;
         }

         return ihs[i];
      } else {
         return null;
      }
   }

   public InstructionHandle findHandle(int pos) {
      InstructionHandle[] ihs = this.getInstructionHandles();
      return findHandle(ihs, this.positions, this.length, pos);
   }

   public InstructionHandle[] getInstructionsAsArray() {
      return this.getInstructionHandles();
   }

   public InstructionHandle findHandle(int pos, InstructionHandle[] instructionArray) {
      return findHandle(instructionArray, this.positions, this.length, pos);
   }

   public InstructionHandle findHandle(int pos, InstructionHandle[] instructionArray, boolean useClosestApproximationIfNoExactFound) {
      return findHandle(instructionArray, this.positions, this.length, pos, useClosestApproximationIfNoExactFound);
   }

   public InstructionList(byte[] code) {
      ByteSequence bytes = new ByteSequence(code);
      InstructionHandle[] ihs = new InstructionHandle[code.length];
      int[] pos = new int[code.length];
      int count = 0;

      int i;
      try {
         while(bytes.available() > 0) {
            i = bytes.getIndex();
            pos[count] = i;
            Instruction i = Instruction.readInstruction(bytes);
            Object ih;
            if (i instanceof InstructionBranch) {
               ih = this.append((InstructionBranch)i);
            } else {
               ih = this.append(i);
            }

            ((InstructionHandle)ih).setPosition(i);
            ihs[count] = (InstructionHandle)ih;
            ++count;
         }
      } catch (IOException var13) {
         throw new ClassGenException(var13.toString());
      }

      this.positions = new int[count];
      System.arraycopy(pos, 0, this.positions, 0, count);

      for(i = 0; i < count; ++i) {
         if (ihs[i] instanceof BranchHandle) {
            InstructionBranch bi = (InstructionBranch)ihs[i].instruction;
            int target = bi.positionOfThisInstruction + bi.getIndex();
            InstructionHandle ih = findHandle(ihs, pos, count, target);
            if (ih == null) {
               throw new ClassGenException("Couldn't find target for branch: " + bi);
            }

            bi.setTarget(ih);
            if (bi instanceof InstructionSelect) {
               InstructionSelect s = (InstructionSelect)bi;
               int[] indices = s.getIndices();

               for(int j = 0; j < indices.length; ++j) {
                  target = bi.positionOfThisInstruction + indices[j];
                  ih = findHandle(ihs, pos, count, target);
                  if (ih == null) {
                     throw new ClassGenException("Couldn't find target for switch: " + bi);
                  }

                  s.setTarget(j, ih);
               }
            }
         }
      }

   }

   public InstructionHandle append(InstructionHandle appendTo, InstructionList appendee) {
      assert appendee != null;

      if (appendee.isEmpty()) {
         return appendTo;
      } else {
         InstructionHandle next = appendTo.next;
         InstructionHandle ret = appendee.start;
         appendTo.next = appendee.start;
         appendee.start.prev = appendTo;
         appendee.end.next = next;
         if (next != null) {
            next.prev = appendee.end;
         } else {
            this.end = appendee.end;
         }

         this.length += appendee.length;
         appendee.clear();
         return ret;
      }
   }

   public InstructionHandle append(Instruction i, InstructionList il) {
      InstructionHandle ih;
      if ((ih = this.findInstruction2(i)) == null) {
         throw new ClassGenException("Instruction " + i + " is not contained in this list.");
      } else {
         return this.append(ih, il);
      }
   }

   public InstructionHandle append(InstructionList il) {
      assert il != null;

      if (il.isEmpty()) {
         return null;
      } else if (this.isEmpty()) {
         this.start = il.start;
         this.end = il.end;
         this.length = il.length;
         il.clear();
         return this.start;
      } else {
         return this.append(this.end, il);
      }
   }

   private void append(InstructionHandle ih) {
      if (this.isEmpty()) {
         this.start = this.end = ih;
         ih.next = ih.prev = null;
      } else {
         this.end.next = ih;
         ih.prev = this.end;
         ih.next = null;
         this.end = ih;
      }

      ++this.length;
   }

   public InstructionHandle append(Instruction i) {
      InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
      this.append(ih);
      return ih;
   }

   public InstructionHandle appendDUP() {
      InstructionHandle ih = InstructionHandle.getInstructionHandle(InstructionConstants.DUP);
      this.append(ih);
      return ih;
   }

   public InstructionHandle appendNOP() {
      InstructionHandle ih = InstructionHandle.getInstructionHandle(InstructionConstants.NOP);
      this.append(ih);
      return ih;
   }

   public InstructionHandle appendPOP() {
      InstructionHandle ih = InstructionHandle.getInstructionHandle(InstructionConstants.POP);
      this.append(ih);
      return ih;
   }

   public BranchHandle append(InstructionBranch i) {
      BranchHandle ih = BranchHandle.getBranchHandle(i);
      this.append((InstructionHandle)ih);
      return ih;
   }

   public InstructionHandle append(Instruction i, Instruction j) {
      return this.append(i, new InstructionList(j));
   }

   public InstructionHandle append(InstructionHandle ih, Instruction i) {
      return this.append(ih, new InstructionList(i));
   }

   public BranchHandle append(InstructionHandle ih, InstructionBranch i) {
      BranchHandle bh = BranchHandle.getBranchHandle(i);
      InstructionList il = new InstructionList();
      il.append((InstructionHandle)bh);
      this.append(ih, il);
      return bh;
   }

   public InstructionHandle insert(InstructionHandle ih, InstructionList il) {
      if (il == null) {
         throw new ClassGenException("Inserting null InstructionList");
      } else if (il.isEmpty()) {
         return ih;
      } else {
         InstructionHandle prev = ih.prev;
         InstructionHandle ret = il.start;
         ih.prev = il.end;
         il.end.next = ih;
         il.start.prev = prev;
         if (prev != null) {
            prev.next = il.start;
         } else {
            this.start = il.start;
         }

         this.length += il.length;
         il.clear();
         return ret;
      }
   }

   public InstructionHandle insert(InstructionList il) {
      if (this.isEmpty()) {
         this.append(il);
         return this.start;
      } else {
         return this.insert(this.start, il);
      }
   }

   private void insert(InstructionHandle ih) {
      if (this.isEmpty()) {
         this.start = this.end = ih;
         ih.next = ih.prev = null;
      } else {
         this.start.prev = ih;
         ih.next = this.start;
         ih.prev = null;
         this.start = ih;
      }

      ++this.length;
   }

   public InstructionHandle insert(Instruction i, InstructionList il) {
      InstructionHandle ih;
      if ((ih = this.findInstruction1(i)) == null) {
         throw new ClassGenException("Instruction " + i + " is not contained in this list.");
      } else {
         return this.insert(ih, il);
      }
   }

   public InstructionHandle insert(Instruction i) {
      InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
      this.insert(ih);
      return ih;
   }

   public BranchHandle insert(InstructionBranch i) {
      BranchHandle ih = BranchHandle.getBranchHandle(i);
      this.insert((InstructionHandle)ih);
      return ih;
   }

   public InstructionHandle insert(Instruction i, Instruction j) {
      return this.insert(i, new InstructionList(j));
   }

   public InstructionHandle insert(InstructionHandle ih, Instruction i) {
      return this.insert(ih, new InstructionList(i));
   }

   public BranchHandle insert(InstructionHandle ih, InstructionBranch i) {
      BranchHandle bh = BranchHandle.getBranchHandle(i);
      InstructionList il = new InstructionList();
      il.append((InstructionHandle)bh);
      this.insert(ih, il);
      return bh;
   }

   public void move(InstructionHandle start, InstructionHandle end, InstructionHandle target) {
      if (start != null && end != null) {
         if (target != start && target != end) {
            InstructionHandle prev;
            for(prev = start; prev != end.next; prev = prev.next) {
               if (prev == null) {
                  throw new ClassGenException("Invalid range: From " + start + " to " + end);
               }

               if (prev == target) {
                  throw new ClassGenException("Invalid range: From " + start + " to " + end + " contains target " + target);
               }
            }

            prev = start.prev;
            InstructionHandle next = end.next;
            if (prev != null) {
               prev.next = next;
            } else {
               this.start = next;
            }

            if (next != null) {
               next.prev = prev;
            } else {
               this.end = prev;
            }

            start.prev = end.next = null;
            if (target == null) {
               end.next = this.start;
               this.start = start;
            } else {
               next = target.next;
               target.next = start;
               start.prev = target;
               end.next = next;
               if (next != null) {
                  next.prev = end;
               }
            }

         } else {
            throw new ClassGenException("Invalid range: From " + start + " to " + end + " contains target " + target);
         }
      } else {
         throw new ClassGenException("Invalid null handle: From " + start + " to " + end);
      }
   }

   public void move(InstructionHandle ih, InstructionHandle target) {
      this.move(ih, ih, target);
   }

   private void remove(InstructionHandle prev, InstructionHandle next, boolean careAboutLostTargeters) throws TargetLostException {
      InstructionHandle last;
      InstructionHandle first;
      if (prev == null && next == null) {
         first = last = this.start;
         this.start = this.end = null;
      } else {
         if (prev == null) {
            first = this.start;
            this.start = next;
         } else {
            first = prev.next;
            prev.next = next;
         }

         if (next == null) {
            last = this.end;
            this.end = prev;
         } else {
            last = next.prev;
            next.prev = prev;
         }
      }

      first.prev = null;
      last.next = null;
      if (careAboutLostTargeters) {
         ArrayList target_vec = new ArrayList();

         for(InstructionHandle ih = first; ih != null; ih = ih.next) {
            ih.getInstruction().dispose();
         }

         StringBuffer buf = new StringBuffer("{ ");

         for(InstructionHandle ih = first; ih != null; ih = next) {
            next = ih.next;
            --this.length;
            Set targeters = ih.getTargeters();
            boolean isOK = false;
            Iterator tIter = targeters.iterator();

            while(true) {
               while(tIter.hasNext()) {
                  InstructionTargeter instructionTargeter = (InstructionTargeter)tIter.next();
                  if (!instructionTargeter.getClass().getName().endsWith("ShadowRange") && !instructionTargeter.getClass().getName().endsWith("ExceptionRange") && !instructionTargeter.getClass().getName().endsWith("LineNumberTag")) {
                     System.out.println(instructionTargeter.getClass());
                  } else {
                     isOK = true;
                  }
               }

               if (!isOK) {
                  target_vec.add(ih);
                  buf.append(ih.toString(true) + " ");
                  ih.next = ih.prev = null;
               } else {
                  ih.dispose();
               }
               break;
            }
         }

         buf.append("}");
         if (!target_vec.isEmpty()) {
            InstructionHandle[] targeted = new InstructionHandle[target_vec.size()];
            target_vec.toArray(targeted);
            throw new TargetLostException(targeted, buf.toString());
         }
      }
   }

   public void delete(InstructionHandle ih) throws TargetLostException {
      this.remove(ih.prev, ih.next, false);
   }

   public void delete(InstructionHandle from, InstructionHandle to) throws TargetLostException {
      this.remove(from.prev, to.next, false);
   }

   public void delete(Instruction from, Instruction to) throws TargetLostException {
      InstructionHandle from_ih;
      if ((from_ih = this.findInstruction1(from)) == null) {
         throw new ClassGenException("Instruction " + from + " is not contained in this list.");
      } else {
         InstructionHandle to_ih;
         if ((to_ih = this.findInstruction2(to)) == null) {
            throw new ClassGenException("Instruction " + to + " is not contained in this list.");
         } else {
            this.delete(from_ih, to_ih);
         }
      }
   }

   private InstructionHandle findInstruction1(Instruction i) {
      for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
         if (ih.instruction == i) {
            return ih;
         }
      }

      return null;
   }

   private InstructionHandle findInstruction2(Instruction i) {
      for(InstructionHandle ih = this.end; ih != null; ih = ih.prev) {
         if (ih.instruction == i) {
            return ih;
         }
      }

      return null;
   }

   public boolean contains(InstructionHandle i) {
      if (i == null) {
         return false;
      } else {
         for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
            if (ih == i) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean contains(Instruction i) {
      return this.findInstruction1(i) != null;
   }

   public void setPositions() {
      this.setPositions(false);
   }

   public void setPositions(boolean check) {
      int maxAdditionalBytes = 0;
      int index = 0;
      int count = 0;
      int[] pos = new int[this.length];
      if (check) {
         this.checkInstructionList();
      }

      for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
         Instruction i = ih.instruction;
         ih.setPosition(index);
         pos[count++] = index;
         switch (i.opcode) {
            case 167:
            case 168:
               maxAdditionalBytes += 2;
            case 169:
            default:
               break;
            case 170:
            case 171:
               maxAdditionalBytes += 3;
         }

         index += i.getLength();
      }

      boolean nonZeroOffset = false;
      int offset = 0;

      InstructionHandle ih;
      for(ih = this.start; ih != null; ih = ih.next) {
         if (ih instanceof BranchHandle) {
            offset += ((BranchHandle)ih).updatePosition(offset, maxAdditionalBytes);
            if (offset != 0) {
               nonZeroOffset = true;
            }
         }
      }

      if (nonZeroOffset) {
         count = 0;
         index = 0;

         for(ih = this.start; ih != null; ih = ih.next) {
            Instruction i = ih.instruction;
            ih.setPosition(index);
            pos[count++] = index;
            index += i.getLength();
         }
      }

      this.positions = new int[count];
      System.arraycopy(pos, 0, this.positions, 0, count);
   }

   private void checkInstructionList() {
      for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
         Instruction i = ih.instruction;
         if (i instanceof InstructionBranch) {
            Instruction inst = ((InstructionBranch)i).getTarget().instruction;
            if (!this.contains(inst)) {
               throw new ClassGenException("Branch target of " + Constants.OPCODE_NAMES[i.opcode] + ":" + inst + " not in instruction list");
            }

            if (i instanceof InstructionSelect) {
               InstructionHandle[] targets = ((InstructionSelect)i).getTargets();

               for(int j = 0; j < targets.length; ++j) {
                  inst = targets[j].instruction;
                  if (!this.contains(inst)) {
                     throw new ClassGenException("Branch target of " + Constants.OPCODE_NAMES[i.opcode] + ":" + inst + " not in instruction list");
                  }
               }
            }

            if (!(ih instanceof BranchHandle)) {
               throw new ClassGenException("Branch instruction " + Constants.OPCODE_NAMES[i.opcode] + ":" + inst + " not contained in BranchHandle.");
            }
         }
      }

   }

   public byte[] getByteCode() {
      this.setPositions();
      ByteArrayOutputStream b = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(b);

      try {
         for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
            Instruction i = ih.instruction;
            i.dump(out);
         }
      } catch (IOException var5) {
         System.err.println(var5);
         return null;
      }

      byte[] byteCode = b.toByteArray();
      if (byteCode.length > 65536) {
         throw new ClassGenException("Code size too big: " + byteCode.length);
      } else {
         return byteCode;
      }
   }

   public Instruction[] getInstructions() {
      ByteSequence bytes = new ByteSequence(this.getByteCode());
      ArrayList instructions = new ArrayList();

      try {
         while(bytes.available() > 0) {
            instructions.add(Instruction.readInstruction(bytes));
         }
      } catch (IOException var4) {
         throw new ClassGenException(var4.toString());
      }

      Instruction[] result = new Instruction[instructions.size()];
      instructions.toArray(result);
      return result;
   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean verbose) {
      StringBuffer buf = new StringBuffer();

      for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
         buf.append(ih.toString(verbose) + "\n");
      }

      return buf.toString();
   }

   public Iterator iterator() {
      return new Iterator() {
         private InstructionHandle ih;

         {
            this.ih = InstructionList.this.start;
         }

         public Object next() {
            InstructionHandle i = this.ih;
            this.ih = this.ih.next;
            return i;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }

         public boolean hasNext() {
            return this.ih != null;
         }
      };
   }

   public InstructionHandle[] getInstructionHandles() {
      InstructionHandle[] ihs = new InstructionHandle[this.length];
      InstructionHandle ih = this.start;

      for(int i = 0; i < this.length; ++i) {
         ihs[i] = ih;
         ih = ih.next;
      }

      return ihs;
   }

   public int[] getInstructionPositions() {
      return this.positions;
   }

   public InstructionList copy() {
      HashMap map = new HashMap();
      InstructionList il = new InstructionList();

      InstructionHandle ih;
      Instruction i;
      for(ih = this.start; ih != null; ih = ih.next) {
         Instruction i = ih.instruction;
         i = i.copy();
         if (i instanceof InstructionBranch) {
            map.put(ih, il.append((InstructionBranch)i));
         } else {
            map.put(ih, il.append(i));
         }
      }

      ih = this.start;

      for(InstructionHandle ch = il.start; ih != null; ch = ch.next) {
         i = ih.instruction;
         Instruction c = ch.instruction;
         if (i instanceof InstructionBranch) {
            InstructionBranch bi = (InstructionBranch)i;
            InstructionBranch bc = (InstructionBranch)c;
            InstructionHandle itarget = bi.getTarget();
            bc.setTarget((InstructionHandle)map.get(itarget));
            if (bi instanceof InstructionSelect) {
               InstructionHandle[] itargets = ((InstructionSelect)bi).getTargets();
               InstructionHandle[] ctargets = ((InstructionSelect)bc).getTargets();

               for(int j = 0; j < itargets.length; ++j) {
                  ctargets[j] = (InstructionHandle)map.get(itargets[j]);
               }
            }
         }

         ih = ih.next;
      }

      return il;
   }

   public void replaceConstantPool(ConstantPool old_cp, ConstantPool new_cp) {
      for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
         Instruction i = ih.instruction;
         if (i.isConstantPoolInstruction()) {
            InstructionCP ci = (InstructionCP)i;
            Constant c = old_cp.getConstant(ci.getIndex());
            ci.setIndex(new_cp.addConstant(c, old_cp));
         }
      }

   }

   private void clear() {
      this.start = this.end = null;
      this.length = 0;
   }

   public void dispose() {
      for(InstructionHandle ih = this.end; ih != null; ih = ih.prev) {
         ih.dispose();
      }

      this.clear();
   }

   public InstructionHandle getStart() {
      return this.start;
   }

   public InstructionHandle getEnd() {
      return this.end;
   }

   public int getLength() {
      return this.length;
   }

   public int size() {
      return this.length;
   }

   public void redirectBranches(InstructionHandle old_target, InstructionHandle new_target) {
      for(InstructionHandle ih = this.start; ih != null; ih = ih.next) {
         Instruction i = ih.getInstruction();
         if (i instanceof InstructionBranch) {
            InstructionBranch b = (InstructionBranch)i;
            InstructionHandle target = b.getTarget();
            if (target == old_target) {
               b.setTarget(new_target);
            }

            if (b instanceof InstructionSelect) {
               InstructionHandle[] targets = ((InstructionSelect)b).getTargets();

               for(int j = 0; j < targets.length; ++j) {
                  if (targets[j] == old_target) {
                     ((InstructionSelect)b).setTarget(j, new_target);
                  }
               }
            }
         }
      }

   }

   public void redirectLocalVariables(LocalVariableGen[] lg, InstructionHandle old_target, InstructionHandle new_target) {
      for(int i = 0; i < lg.length; ++i) {
         InstructionHandle start = lg[i].getStart();
         InstructionHandle end = lg[i].getEnd();
         if (start == old_target) {
            lg[i].setStart(new_target);
         }

         if (end == old_target) {
            lg[i].setEnd(new_target);
         }
      }

   }

   public void redirectExceptionHandlers(CodeExceptionGen[] exceptions, InstructionHandle old_target, InstructionHandle new_target) {
      for(int i = 0; i < exceptions.length; ++i) {
         if (exceptions[i].getStartPC() == old_target) {
            exceptions[i].setStartPC(new_target);
         }

         if (exceptions[i].getEndPC() == old_target) {
            exceptions[i].setEndPC(new_target);
         }

         if (exceptions[i].getHandlerPC() == old_target) {
            exceptions[i].setHandlerPC(new_target);
         }
      }

   }
}
