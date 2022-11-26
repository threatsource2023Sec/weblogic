package weblogic.corba.cos.naming;

import org.omg.CORBA.Any;
import org.omg.CosNaming.NamingContextExtOperations;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import weblogic.corba.cos.naming.NamingContextAnyPackage.AppException;
import weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed;
import weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound;
import weblogic.corba.cos.naming.NamingContextAnyPackage.TypeNotSupported;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;

public interface NamingContextAnyOperations extends NamingContextExtOperations {
   void bind_any(WNameComponent[] var1, Any var2) throws NotFound, CannotProceed, AppException, InvalidName, AlreadyBound, TypeNotSupported;

   void rebind_any(WNameComponent[] var1, Any var2) throws NotFound, CannotProceed, AppException, InvalidName, TypeNotSupported;

   Any resolve_any(WNameComponent[] var1) throws NotFound, CannotProceed, AppException, InvalidName;

   Any resolve_str_any(String var1) throws NotFound, CannotProceed, AppException, InvalidName;
}
