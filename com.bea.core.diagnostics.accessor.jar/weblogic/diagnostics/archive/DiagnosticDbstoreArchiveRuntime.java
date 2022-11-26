package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.runtime.DbstoreArchiveRuntimeMBean;
import weblogic.diagnostics.archive.dbstore.JdbcDataArchive;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public class DiagnosticDbstoreArchiveRuntime extends DiagnosticEditableArchiveRuntime implements DbstoreArchiveRuntimeMBean {
   public DiagnosticDbstoreArchiveRuntime(JdbcDataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }

   public long getInsertionCount() {
      return ((JdbcDataArchive)this.archive).getInsertionCount();
   }

   public long getInsertionTime() {
      return ((JdbcDataArchive)this.archive).getInsertionTime() / 1000000L;
   }

   public long getDeletionCount() {
      return ((JdbcDataArchive)this.archive).getDeletionCount();
   }

   public long getDeletionTime() {
      return ((JdbcDataArchive)this.archive).getDeletionTime() / 1000000L;
   }
}
