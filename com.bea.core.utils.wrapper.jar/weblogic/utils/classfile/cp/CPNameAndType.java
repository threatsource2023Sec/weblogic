package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.Descriptor;
import weblogic.utils.classfile.DoubleKey;

public class CPNameAndType extends CPInfo {
   public CPUtf8 name;
   public CPUtf8 descriptor;

   public CPNameAndType() {
      this.setTag(12);
   }

   public void init(Object o) {
      DoubleKey key = (DoubleKey)o;
      this.name = (CPUtf8)key.one;
      this.descriptor = (CPUtf8)key.two;
   }

   public String getName() {
      return this.name.toString();
   }

   public String getRawDescriptor() {
      return this.descriptor.toString();
   }

   public Descriptor getDescriptor() {
      return new Descriptor(this.descriptor.toString());
   }

   public String toString() {
      return "name: " + this.name.getValue() + "desc: " + this.descriptor.getValue();
   }
}
