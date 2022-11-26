package weblogic.rmi.extensions.activation;

import java.rmi.NoSuchObjectException;

public interface Activator {
   Activatable activate(Object var1) throws NoSuchObjectException;

   void deactivate(Activatable var1);
}
