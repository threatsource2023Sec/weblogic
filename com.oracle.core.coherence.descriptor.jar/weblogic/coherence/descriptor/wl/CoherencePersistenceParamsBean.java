package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherencePersistenceParamsBean extends SettableBean {
   String ON_DEMAND = "on-demand";
   String ACTIVE = "active";

   String getDefaultPersistenceMode();

   void setDefaultPersistenceMode(String var1);

   String getActiveDirectory();

   void setActiveDirectory(String var1);

   String getSnapshotDirectory();

   void setSnapshotDirectory(String var1);

   String getTrashDirectory();

   void setTrashDirectory(String var1);
}
