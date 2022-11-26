package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.UserException;

public final class AppException extends UserException {
   public String name = null;

   public AppException() {
      super(AppExceptionHelper.id());
   }

   public AppException(String _name) {
      super(AppExceptionHelper.id());
      this.name = _name;
   }

   public AppException(String $reason, String _name) {
      super(AppExceptionHelper.id() + "  " + $reason);
      this.name = _name;
   }
}
