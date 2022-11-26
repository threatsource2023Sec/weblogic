package weblogic.nodemanager.adminserver;

import java.io.IOException;
import weblogic.management.configuration.MachineMBean;

public interface MachineChangeListProvider {
   void syncChangeList(MachineMBean var1) throws IOException;
}
