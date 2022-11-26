package weblogic.utils.classfile;

import weblogic.utils.classfile.cp.CPNameAndType;

public class WrongDescriptorTypeException extends RuntimeException {
   private static final long serialVersionUID = 3783074990777510816L;
   private CPNameAndType nameAndType;

   public WrongDescriptorTypeException() {
   }

   public WrongDescriptorTypeException(String msg) {
      super(msg);
   }

   public WrongDescriptorTypeException(String msg, CPNameAndType nt) {
      super(msg);
      this.nameAndType = nt;
   }

   public CPNameAndType getNameAndType() {
      return this.nameAndType;
   }
}
