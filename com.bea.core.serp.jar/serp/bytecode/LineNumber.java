package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;

public class LineNumber implements Comparable, InstructionPtr, BCEntity, VisitAcceptor {
   private int _line;
   private LineNumberTable _owner;
   InstructionPtrStrategy _target;

   LineNumber(LineNumberTable owner) {
      this._line = 0;
      this._owner = null;
      this._target = new InstructionPtrStrategy(this);
      this._owner = owner;
   }

   LineNumber(LineNumberTable owner, int startPc) {
      this(owner);
      this.setStartPc(startPc);
   }

   public LineNumberTable getTable() {
      return this._owner;
   }

   void invalidate() {
      this._owner = null;
   }

   public int getLine() {
      return this._line;
   }

   public void setLine(int lineNumber) {
      this._line = lineNumber;
   }

   public Instruction getStart() {
      return this._target.getTargetInstruction();
   }

   public int getStartPc() {
      return this._target.getByteIndex();
   }

   public void setStartPc(int startPc) {
      this._target.setByteIndex(startPc);
   }

   public void setStart(Instruction instruction) {
      this._target.setTargetInstruction(instruction);
   }

   public void updateTargets() {
      this._target.updateTargets();
   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      this._target.replaceTarget(oldTarget, newTarget);
   }

   public Project getProject() {
      return this._owner.getProject();
   }

   public ConstantPool getPool() {
      return this._owner.getPool();
   }

   public ClassLoader getClassLoader() {
      return this._owner.getClassLoader();
   }

   public boolean isValid() {
      return this._owner != null;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLineNumber(this);
      visit.exitLineNumber(this);
   }

   public int compareTo(Object other) {
      if (!(other instanceof LineNumber)) {
         return -1;
      } else {
         LineNumber ln = (LineNumber)other;
         if (this.getStartPc() == ln.getStartPc()) {
            return 0;
         } else {
            return this.getStartPc() < ln.getStartPc() ? -1 : 1;
         }
      }
   }

   void read(DataInput in) throws IOException {
      this.setStartPc(in.readUnsignedShort());
      this.setLine(in.readUnsignedShort());
   }

   void write(DataOutput out) throws IOException {
      out.writeShort(this.getStartPc());
      out.writeShort(this.getLine());
   }

   public Code getCode() {
      return this._owner.getCode();
   }
}
