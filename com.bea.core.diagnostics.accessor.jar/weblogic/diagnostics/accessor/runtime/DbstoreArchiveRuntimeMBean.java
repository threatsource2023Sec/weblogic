package weblogic.diagnostics.accessor.runtime;

public interface DbstoreArchiveRuntimeMBean extends EditableArchiveRuntimeMBean {
   long getInsertionCount();

   long getInsertionTime();

   long getDeletionCount();

   long getDeletionTime();
}
