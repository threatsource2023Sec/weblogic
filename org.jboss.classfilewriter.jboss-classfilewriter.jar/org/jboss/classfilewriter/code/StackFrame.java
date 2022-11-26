package org.jboss.classfilewriter.code;

import org.jboss.classfilewriter.ClassMethod;

public class StackFrame {
   private final StackState stackState;
   private final LocalVariableState localVariableState;
   private final StackFrameType type;

   public StackFrame(ClassMethod method) {
      this.stackState = new StackState(method.getClassFile().getConstPool());
      this.localVariableState = new LocalVariableState(method);
      this.type = StackFrameType.FULL_FRAME;
   }

   public StackFrame(StackState stackState, LocalVariableState localVariableState, StackFrameType type) {
      this.stackState = stackState;
      this.localVariableState = localVariableState;
      this.type = type;
   }

   public StackState getStackState() {
      return this.stackState;
   }

   public LocalVariableState getLocalVariableState() {
      return this.localVariableState;
   }

   public StackFrame push(String type) {
      StackState ns = this.stackState.push(type);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame push(StackEntry entry) {
      StackState ns = this.stackState.push(entry);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame aconstNull() {
      StackState ns = this.stackState.aconstNull();
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame pop(int no) {
      StackState ns = this.stackState.pop(no);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame pop() {
      StackState ns = this.stackState.pop(1);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame pop2() {
      StackState ns = this.stackState.pop(2);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame pop3() {
      StackState ns = this.stackState.pop(3);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame pop4() {
      StackState ns = this.stackState.pop(4);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame replace(String type) {
      StackState ns = this.stackState.pop(1).push(type);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame dup() {
      StackState ns = this.stackState.dup();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame dupX1() {
      StackState ns = this.stackState.dupX1();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame dupX2() {
      StackState ns = this.stackState.dupX2();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame dup2() {
      StackState ns = this.stackState.dup2();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame dup2X1() {
      StackState ns = this.stackState.dup2X1();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame dup2X2() {
      StackState ns = this.stackState.dup2X2();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame store(int no) {
      StackEntry top = this.stackState.top();
      StackState ns;
      LocalVariableState ls;
      StackEntry type;
      if (top.getType() == StackEntryType.TOP) {
         type = this.stackState.top_1();
         ns = this.stackState.pop(2);
         ls = this.localVariableState.storeWide(no, type);
      } else {
         type = this.stackState.top();
         ns = this.stackState.pop(1);
         ls = this.localVariableState.store(no, type);
      }

      return new StackFrame(ns, ls, StackFrameType.FULL_FRAME);
   }

   public StackFrame pop2push1(String type) {
      StackState ns = this.stackState.pop(2).push(type);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public StackFrame pop4push1(String type) {
      StackState ns = this.stackState.pop(4).push(type);
      return new StackFrame(ns, this.localVariableState, this.typeNoLocalChange(ns));
   }

   public String toString() {
      return "StackFrame [localVariableState=" + this.localVariableState + ", stackState=" + this.stackState + "]";
   }

   public StackFrame constructorCall(int initializedValueStackPosition) {
      StackEntry entry = (StackEntry)this.stackState.getContents().get(this.stackState.getContents().size() - 1 - initializedValueStackPosition);
      StackState ns = this.stackState.constructorCall(initializedValueStackPosition, entry);
      LocalVariableState locals = this.localVariableState.constructorCall(entry);
      return new StackFrame(ns, locals, StackFrameType.FULL_FRAME);
   }

   public StackFrame swap() {
      StackState ns = this.stackState.swap();
      return new StackFrame(ns, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   private StackFrameType typeNoLocalChange(StackState ns) {
      int size = ns.getContents().size();
      if (size == 0) {
         return StackFrameType.SAME_FRAME;
      } else {
         return size == 1 ? StackFrameType.SAME_LOCALS_1_STACK : StackFrameType.FULL_FRAME;
      }
   }

   public StackFrame mergeStack(int pos, StackEntry frame) {
      StackState stack = this.stackState.updateMerged(pos, frame);
      return new StackFrame(stack, this.localVariableState, StackFrameType.FULL_FRAME);
   }

   public StackFrame mergeLocals(int pos, StackEntry frame) {
      LocalVariableState locals = this.localVariableState.updateMerged(pos, frame);
      return new StackFrame(this.stackState, locals, StackFrameType.FULL_FRAME);
   }

   public StackFrameType getType() {
      return this.type;
   }

   public StackFrame createFull() {
      return new StackFrame(this.stackState, this.localVariableState, StackFrameType.FULL_FRAME);
   }
}
