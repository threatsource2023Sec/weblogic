package weblogic.rmi.utils.io;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.utils.io.Replacer;

public interface RemoteReplacer extends Replacer {
   Object resolveStub(StubReference var1) throws RemoteException;
}
