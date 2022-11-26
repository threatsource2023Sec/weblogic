package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NamingContext;

public final class CannotProceed extends UserException {
   public NamingContext cxt = null;
   public WNameComponent[] rest_of_name = null;

   public CannotProceed() {
      super(CannotProceedHelper.id());
   }

   public CannotProceed(NamingContext _cxt, WNameComponent[] _rest_of_name) {
      super(CannotProceedHelper.id());
      this.cxt = _cxt;
      this.rest_of_name = _rest_of_name;
   }

   public CannotProceed(String $reason, NamingContext _cxt, WNameComponent[] _rest_of_name) {
      super(CannotProceedHelper.id() + "  " + $reason);
      this.cxt = _cxt;
      this.rest_of_name = _rest_of_name;
   }
}
