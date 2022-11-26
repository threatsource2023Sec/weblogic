package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.UserException;

public final class TypeNotSupported extends UserException {
   public TypeNotSupported() {
      super(TypeNotSupportedHelper.id());
   }

   public TypeNotSupported(String $reason) {
      super(TypeNotSupportedHelper.id() + "  " + $reason);
   }
}
