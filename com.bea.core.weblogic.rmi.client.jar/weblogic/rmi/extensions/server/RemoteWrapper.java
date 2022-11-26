package weblogic.rmi.extensions.server;

import java.io.Serializable;
import java.rmi.Remote;

public interface RemoteWrapper extends Serializable {
   Remote getRemoteDelegate();
}
