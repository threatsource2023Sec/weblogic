package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class MethodHandleEntry extends Entry {
   private int _reference_kind = 0;
   private int _reference_index = 0;

   public MethodHandleEntry() {
   }

   public MethodHandleEntry(int _reference_kind, int _reference_index) {
      this._reference_kind = _reference_kind;
      this._reference_index = _reference_index;
   }

   public void acceptVisit(BCVisitor visitor) {
      visitor.enterMethodHandleEntry(this);
      visitor.exitMethodHandleEntry(this);
   }

   public int getType() {
      return 15;
   }

   void readData(DataInput in) throws IOException {
      this._reference_kind = in.readUnsignedByte();
      this._reference_index = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeByte(this._reference_kind);
      out.writeShort(this._reference_index);
   }

   public int getReferenceKind() {
      return this._reference_kind;
   }

   public void setReferenceKind(int referenceKind) throws IllegalArgumentException {
      if (referenceKind >= 1 && referenceKind <= 9) {
         this._reference_kind = referenceKind;
      } else {
         throw new IllegalArgumentException("MethodHandle referencekind cannot accept a value of " + referenceKind);
      }
   }

   public Entry getReference() {
      return this.getPool().getEntry(this._reference_index);
   }

   public void setReference(int referenceIndex) {
      this._reference_index = referenceIndex;
   }
}
