package weblogic.management.commo;

import javax.management.InvalidAttributeValueException;
import javax.management.ObjectName;

public interface StandardInterface {
   String wls_getInterfaceClassName();

   /** @deprecated */
   @Deprecated
   String wls_getDisplayName();

   ObjectName wls_getObjectName();

   String getName();

   void setName(String var1) throws InvalidAttributeValueException;
}
