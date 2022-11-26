package weblogic.servlet.spi;

import java.io.Serializable;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;

public interface SubjectHandle extends Serializable {
   boolean isAdmin();

   boolean isAnonymous();

   boolean isKernel();

   boolean isInAdminRoles(String[] var1);

   String getUsername();

   Principal getPrincipal();

   Object run(PrivilegedAction var1);

   Object run(PrivilegedExceptionAction var1) throws PrivilegedActionException;

   Map getAssociatedData();
}
