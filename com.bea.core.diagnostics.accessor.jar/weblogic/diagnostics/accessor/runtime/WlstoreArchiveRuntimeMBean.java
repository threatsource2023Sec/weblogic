package weblogic.diagnostics.accessor.runtime;

public interface WlstoreArchiveRuntimeMBean extends EditableArchiveRuntimeMBean {
   int getIndexPageCount();

   long getInsertionCount();

   long getInsertionTime();

   long getDeletionCount();

   long getDeletionTime();

   long getRecordCount();
}
