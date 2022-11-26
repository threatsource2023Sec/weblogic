package weblogic.cluster;

import java.io.Serializable;
import weblogic.rmi.spi.HostID;

public interface GroupMessage extends Serializable {
   void execute(HostID var1);
}
