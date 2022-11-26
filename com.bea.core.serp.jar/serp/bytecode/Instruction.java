package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;

public class Instruction extends CodeEntry implements BCEntity, VisitAcceptor {
   private Code _owner = null;
   private int _opcode = 0;

   Instruction(Code owner) {
      this._owner = owner;
   }

   Instruction(Code owner, int opcode) {
      this._owner = owner;
      this._opcode = opcode;
   }

   public Code getCode() {
      return this._owner;
   }

   public String getName() {
      return Constants.OPCODE_NAMES[this._opcode];
   }

   public int getOpcode() {
      return this._opcode;
   }

   Instruction setOpcode(int opcode) {
      this._opcode = opcode;
      return this;
   }

   public int getByteIndex() {
      return this._owner != null ? this._owner.getByteIndex(this) : 0;
   }

   void invalidateByteIndexes() {
      if (this._owner != null) {
         this._owner.invalidateByteIndexes();
      }

   }

   public LineNumber getLineNumber() {
      LineNumberTable table = this._owner.getLineNumberTable(false);
      return table == null ? null : table.getLineNumber(this);
   }

   int getLength() {
      return 1;
   }

   public int getLogicalStackChange() {
      return this.getStackChange();
   }

   public int getStackChange() {
      return 0;
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else {
         return other.getOpcode() == this.getOpcode();
      }
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
   }

   void invalidate() {
      this._owner = null;
   }

   void read(Instruction orig) {
   }

   void read(DataInput in) throws IOException {
   }

   void write(DataOutput out) throws IOException {
   }
}
