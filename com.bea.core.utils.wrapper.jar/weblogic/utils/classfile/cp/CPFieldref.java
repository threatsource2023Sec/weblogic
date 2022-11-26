package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.Type;

public class CPFieldref extends CPMemberType {
   public CPFieldref() {
      this.setTag(9);
   }

   public Type getType() {
      return Type.getType(this.getDescriptor());
   }
}
