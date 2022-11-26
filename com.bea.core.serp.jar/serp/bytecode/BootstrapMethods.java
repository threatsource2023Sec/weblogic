package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class BootstrapMethods extends Attribute {
   private BootstrapMethodElement[] _bootstrapMethods = new BootstrapMethodElement[0];

   BootstrapMethods(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public void acceptVisit(BCVisitor visitor) {
      visitor.enterBootstrapMethod(this);
      visitor.exitBootstrapMethod(this);
   }

   public int getNumberBootstrapMethods() {
      return this._bootstrapMethods.length;
   }

   public BootstrapMethodElement[] getBootstrapMethods() {
      BootstrapMethodElement[] retval = new BootstrapMethodElement[this._bootstrapMethods.length];

      for(int i = 0; i < this._bootstrapMethods.length; ++i) {
         retval[i] = this._bootstrapMethods[i];
      }

      return retval;
   }

   public void setBootstrapMethods(BootstrapMethodElement[] methods) {
      if (methods != null && methods.length != 0) {
         this._bootstrapMethods = new BootstrapMethodElement[methods.length];

         for(int i = 0; i < methods.length; ++i) {
            this._bootstrapMethods[i] = methods[i];
         }

      } else {
         this._bootstrapMethods = new BootstrapMethodElement[0];
      }
   }

   int getLength() {
      int length = 2;

      for(int i = 0; i < this._bootstrapMethods.length; ++i) {
         length += this._bootstrapMethods[i].getLength();
      }

      return length;
   }

   void read(DataInput in, int length) throws IOException {
      int num_bootstrap_methods = in.readShort();
      this._bootstrapMethods = new BootstrapMethodElement[num_bootstrap_methods];

      for(int i = 0; i < num_bootstrap_methods; ++i) {
         this._bootstrapMethods[i] = new BootstrapMethodElement(this, in);
      }

   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this._bootstrapMethods.length);

      for(int i = 0; i < this._bootstrapMethods.length; ++i) {
         this._bootstrapMethods[i].write(out);
      }

   }
}
