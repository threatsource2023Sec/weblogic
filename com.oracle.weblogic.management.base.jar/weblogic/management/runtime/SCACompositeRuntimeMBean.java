package weblogic.management.runtime;

public interface SCACompositeRuntimeMBean extends RuntimeMBean {
   String getCompositeName();

   String getDescriptor();

   ComponentRuntimeMBean[] getComponentRuntimes();
}
