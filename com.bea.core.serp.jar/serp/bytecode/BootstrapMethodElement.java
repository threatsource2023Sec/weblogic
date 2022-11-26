package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import serp.bytecode.lowlevel.Entry;
import serp.bytecode.lowlevel.MethodHandleEntry;

public class BootstrapMethodElement {
   private BootstrapMethods _bootstrapMethodAttribute;
   private int _bootstrap_method_ref = 0;
   private int[] _bootstrap_arguments = new int[0];

   public BootstrapMethodElement() {
   }

   public BootstrapMethodElement(BootstrapMethods bootstrapmethodAttr, int bootstrap_method_ref, int num_bootstrap_arguments, int[] bootstrap_arguments) {
      this._bootstrapMethodAttribute = bootstrapmethodAttr;
      this._bootstrap_method_ref = bootstrap_method_ref;
      this._bootstrap_arguments = new int[num_bootstrap_arguments];

      for(int i = 0; i < num_bootstrap_arguments; ++i) {
         this._bootstrap_arguments[i] = bootstrap_arguments[i];
      }

   }

   public BootstrapMethodElement(BootstrapMethods bootstrapmethodAttr, DataInput in) throws IOException {
      this._bootstrapMethodAttribute = bootstrapmethodAttr;
      this._bootstrap_method_ref = in.readShort();
      int num_bootstrap_arguments = in.readShort();
      this._bootstrap_arguments = new int[num_bootstrap_arguments];

      for(int i = 0; i < num_bootstrap_arguments; ++i) {
         this._bootstrap_arguments[i] = in.readShort();
      }

   }

   public BootstrapMethods getBootstrapMethodAttribute() {
      return this._bootstrapMethodAttribute;
   }

   public void setBootstrapMethodAttribute(BootstrapMethods bootstrapMethodAttribute) {
      this._bootstrapMethodAttribute = bootstrapMethodAttribute;
   }

   public int getBootstrapMethodRef() {
      return this._bootstrap_method_ref;
   }

   public MethodHandleEntry getBootstrapMethod() {
      if (this._bootstrap_method_ref == 0) {
         return null;
      } else {
         Entry e = this._bootstrapMethodAttribute.getPool().getEntry(this._bootstrap_method_ref);
         return (MethodHandleEntry)e;
      }
   }

   public void setBootstrapMethodRef(int bootstrap_method_ref) {
      this._bootstrap_method_ref = bootstrap_method_ref;
   }

   public void setBootstrapMethod(MethodHandleEntry mhe) {
      if (mhe == null) {
         this._bootstrap_method_ref = 0;
      } else {
         this._bootstrap_method_ref = mhe.getIndex();
      }
   }

   public int getNumBootstrapArguments() {
      return this._bootstrap_arguments.length;
   }

   public int[] getBootstrapArgumentIndices() {
      return Arrays.copyOf(this._bootstrap_arguments, this._bootstrap_arguments.length);
   }

   public Entry[] getBootstrapArguments() {
      Entry[] ceArr = new Entry[this.getNumBootstrapArguments()];

      for(int i = 0; i < ceArr.length; ++i) {
         ceArr[i] = this._bootstrapMethodAttribute.getPool().getEntry(this._bootstrap_arguments[i]);
      }

      return ceArr;
   }

   public void setBootstrapArgumentIndices(int[] bootstrap_arguments) {
      if (bootstrap_arguments != null && bootstrap_arguments.length != 0) {
         this._bootstrap_arguments = Arrays.copyOf(bootstrap_arguments, bootstrap_arguments.length);
      } else {
         this._bootstrap_arguments = new int[0];
      }
   }

   public void setBootstrapArguments(Entry[] bsArgs) {
      if (bsArgs != null && bsArgs.length != 0) {
         this._bootstrap_arguments = new int[bsArgs.length];

         for(int i = 0; i < bsArgs.length; ++i) {
            this._bootstrap_arguments[i] = bsArgs[i].getIndex();
         }

      } else {
         this._bootstrap_arguments = new int[0];
      }
   }

   public int getLength() {
      return 4 + 2 * this._bootstrap_arguments.length;
   }

   public void write(DataOutput out) throws IOException {
      out.writeShort(this._bootstrap_method_ref);
      out.writeShort(this._bootstrap_arguments.length);

      for(int i = 0; i < this._bootstrap_arguments.length; ++i) {
         out.writeShort(this._bootstrap_arguments[i]);
      }

   }
}
