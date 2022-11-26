package weblogic.management.runtime;

import weblogic.management.configuration.ComponentMBean;

public interface LibraryRuntimeMBean extends RuntimeMBean {
   ComponentMBean[] getComponents();

   String getLibraryName();

   String getLibraryIdentifier();

   String getSpecificationVersion();

   String getImplementationVersion();

   String[] getReferencingNames();

   RuntimeMBean[] getReferencingRuntimes();

   boolean isReferenced();

   String getPartitionName();
}
