package weblogic.iiop.spi;

import java.rmi.NoSuchObjectException;
import weblogic.rmi.internal.ServerReference;

public interface ServerReferenceDelegate {
   ServerReference getServerReference() throws NoSuchObjectException;
}
