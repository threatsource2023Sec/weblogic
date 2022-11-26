package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InstructionHandle implements Serializable {
   InstructionHandle next;
   InstructionHandle prev;
   Instruction instruction;
   protected int pos = -1;
   private Set targeters = Collections.emptySet();

   protected InstructionHandle(Instruction i) {
      this.setInstruction(i);
   }

   static final InstructionHandle getInstructionHandle(Instruction i) {
      return new InstructionHandle(i);
   }

   public final InstructionHandle getNext() {
      return this.next;
   }

   public final InstructionHandle getPrev() {
      return this.prev;
   }

   public final Instruction getInstruction() {
      return this.instruction;
   }

   public void setInstruction(Instruction i) {
      if (this.instruction != null) {
         this.instruction.dispose();
      }

      this.instruction = i;
   }

   public int getPosition() {
      return this.pos;
   }

   void setPosition(int pos) {
      this.pos = pos;
   }

   void dispose() {
      this.next = this.prev = null;
      this.instruction.dispose();
      this.instruction = null;
      this.pos = -1;
      this.removeAllTargeters();
   }

   public void removeAllTargeters() {
      this.targeters.clear();
   }

   public void removeTargeter(InstructionTargeter t) {
      this.targeters.remove(t);
   }

   public void addTargeter(InstructionTargeter t) {
      if (this.targeters == Collections.EMPTY_SET) {
         this.targeters = new HashSet();
      }

      this.targeters.add(t);
   }

   public boolean hasTargeters() {
      return !this.targeters.isEmpty();
   }

   public Set getTargeters() {
      return this.targeters;
   }

   public Set getTargetersCopy() {
      Set copy = new HashSet();
      copy.addAll(this.targeters);
      return copy;
   }

   public String toString(boolean verbose) {
      return Utility.format(this.pos, 4, false, ' ') + ": " + this.instruction.toString(verbose);
   }

   public String toString() {
      return this.toString(true);
   }
}
