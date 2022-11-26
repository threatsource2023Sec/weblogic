package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;

public final class NotFound extends UserException {
   public NotFoundReason why = null;
   public WNameComponent[] rest_of_name = null;

   public NotFound() {
      super(NotFoundHelper.id());
   }

   public NotFound(NotFoundReason _why, WNameComponent[] _rest_of_name) {
      super(NotFoundHelper.id());
      this.why = _why;
      this.rest_of_name = _rest_of_name;
   }

   public NotFound(String $reason, NotFoundReason _why, WNameComponent[] _rest_of_name) {
      super(NotFoundHelper.id() + "  " + $reason);
      this.why = _why;
      this.rest_of_name = _rest_of_name;
   }
}
