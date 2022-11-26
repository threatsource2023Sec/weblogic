package weblogic.ejb.container.interfaces;

import java.util.Collection;

public interface ContainerTransaction {
   Collection getAllMethodDescriptors();

   String getTransactionAttribute();
}
