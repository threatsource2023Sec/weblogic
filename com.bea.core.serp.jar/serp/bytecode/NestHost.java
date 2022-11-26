package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.visitor.BCVisitor;

public class NestHost extends Attribute {
   int _nestHostIndex = 0;

   NestHost(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   int getLength() {
      return 2;
   }

   public int getHostIndex() {
      return this._nestHostIndex;
   }

   public void setNestHostIndex(int nestHostIndex) {
      if (nestHostIndex < 0) {
         nestHostIndex = 0;
      }

      this._nestHostIndex = nestHostIndex;
   }

   public String getNestHostName() {
      return this._nestHostIndex == 0 ? null : ((ClassEntry)this.getPool().getEntry(this._nestHostIndex)).getNameEntry().getValue();
   }

   public void setNestHostName(String name) {
      if (name == null) {
         this.setNestHostIndex(0);
      } else {
         this.setNestHostIndex(this.getPool().findClassEntry(name, true));
      }

   }

   void read(Attribute other) {
      this.setNestHostName(((NestHost)other).getNestHostName());
   }

   void read(DataInput in, int length) throws IOException {
      this.setNestHostIndex(in.readUnsignedShort());
   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this.getHostIndex());
   }

   public void acceptVisit(BCVisitor visitor) {
   }
}
