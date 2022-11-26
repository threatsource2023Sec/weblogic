package weblogic.ejb.spi;

import java.util.Set;

public interface EJBValidationInfo {
   boolean isClientDriven();

   boolean hasLocalClientView();

   String getLocalInterfaceName();

   String getLocalHomeInterfaceName();

   boolean hasRemoteClientView();

   String getHomeInterfaceName();

   boolean isSessionBean();

   boolean isEntityBean();

   String getRemoteInterfaceName();

   Set getBusinessLocals();

   Set getBusinessRemotes();
}
