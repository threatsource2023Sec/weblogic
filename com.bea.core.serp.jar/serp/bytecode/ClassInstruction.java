package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.visitor.BCVisitor;

public class ClassInstruction extends TypedInstruction {
   private int _index = 0;

   ClassInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getStackChange() {
      return this.getOpcode() == 187 ? 1 : 0;
   }

   int getLength() {
      return super.getLength() + 2;
   }

   public int getTypeIndex() {
      return this._index;
   }

   public ClassInstruction setTypeIndex(int index) {
      this._index = index;
      return this;
   }

   public String getTypeName() {
      if (this._index == 0) {
         return null;
      } else {
         ClassEntry entry = (ClassEntry)this.getPool().getEntry(this._index);
         return this.getProject().getNameCache().getExternalForm(entry.getNameEntry().getValue(), false);
      }
   }

   public TypedInstruction setType(String type) {
      if (type == null) {
         this._index = 0;
      } else {
         type = this.getProject().getNameCache().getInternalForm(type, false);
         this._index = this.getPool().findClassEntry(type, true);
      }

      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         String type = this.getTypeName();
         String otherType = ((ClassInstruction)other).getTypeName();
         return type == null || otherType == null || type.equals(otherType);
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterClassInstruction(this);
      visit.exitClassInstruction(this);
   }

   void read(Instruction other) {
      super.read(other);
      this.setType(((ClassInstruction)other).getTypeName());
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this._index = in.readUnsignedShort();
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeShort(this._index);
   }
}
