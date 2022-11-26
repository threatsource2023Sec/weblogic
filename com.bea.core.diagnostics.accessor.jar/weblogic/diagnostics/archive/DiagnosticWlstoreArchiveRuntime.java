package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.runtime.WlstoreArchiveRuntimeMBean;
import weblogic.diagnostics.archive.wlstore.PersistentStoreDataArchive;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public class DiagnosticWlstoreArchiveRuntime extends DiagnosticEditableArchiveRuntime implements WlstoreArchiveRuntimeMBean {
   public DiagnosticWlstoreArchiveRuntime(PersistentStoreDataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }

   public int getIndexPageCount() {
      return ((PersistentStoreDataArchive)this.archive).getIndexPageCount();
   }

   public long getInsertionCount() {
      return ((PersistentStoreDataArchive)this.archive).getInsertionCount();
   }

   public long getInsertionTime() {
      return ((PersistentStoreDataArchive)this.archive).getInsertionTime() / 1000000L;
   }

   public long getDeletionCount() {
      return ((PersistentStoreDataArchive)this.archive).getDeletionCount();
   }

   public long getDeletionTime() {
      return ((PersistentStoreDataArchive)this.archive).getDeletionTime() / 1000000L;
   }

   public long getRecordCount() {
      return ((PersistentStoreDataArchive)this.archive).getRecordCount();
   }
}
