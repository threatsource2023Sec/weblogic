package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.Type;

public class CPInterfaceMethodref extends CPMemberType {
   public CPInterfaceMethodref() {
      this.setTag(11);
   }

   public Type getType() {
      String descriptor = this.getDescriptor();
      int i = descriptor.indexOf(")") + 1;
      return Type.getType(descriptor.substring(i));
   }
}
