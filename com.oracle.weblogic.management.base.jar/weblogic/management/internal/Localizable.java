package weblogic.management.internal;

import java.rmi.RemoteException;
import weblogic.management.configuration.ConfigurationException;

public interface Localizable {
   void localize() throws RemoteException, ConfigurationException;
}
