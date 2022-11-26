package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.DoubleKey;
import weblogic.utils.classfile.Type;

public abstract class CPMemberType extends CPInfo {
   public CPClass clazz;
   public CPNameAndType name_and_type;

   public void init(Object o) {
      DoubleKey key = (DoubleKey)o;
      this.clazz = (CPClass)key.one;
      this.name_and_type = (CPNameAndType)key.two;
   }

   public String className() {
      return this.clazz.name.getValue();
   }

   public CPClass getContainingClass() {
      return this.clazz;
   }

   public String getName() {
      return this.name_and_type.name.getValue();
   }

   public String getDescriptor() {
      return this.name_and_type.descriptor.getValue();
   }

   public CPNameAndType getNameAndType() {
      return this.name_and_type;
   }

   public abstract Type getType();

   /** @deprecated */
   @Deprecated
   public String name() {
      return this.getName();
   }

   /** @deprecated */
   @Deprecated
   public String descriptorAsString() {
      return this.getDescriptor();
   }

   public String toString() {
      return this.className() + "." + this.name() + "(" + this.descriptorAsString() + ")";
   }
}
