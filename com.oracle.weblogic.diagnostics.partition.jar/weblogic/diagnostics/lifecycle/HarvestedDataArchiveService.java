package weblogic.diagnostics.lifecycle;

import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.accessor.DiagnosticAccessRuntime;
import weblogic.diagnostics.accessor.DiagnosticDataAccessRuntime;
import weblogic.diagnostics.archive.ArchiveException;
import weblogic.diagnostics.archive.DataWriter;
import weblogic.diagnostics.archive.HarvestedDataArchive;

@Singleton
@Service
public class HarvestedDataArchiveService implements HarvestedDataArchive {
   public DataWriter getDataWriter() throws ArchiveException {
      try {
         DiagnosticAccessRuntime accessor = DiagnosticAccessRuntime.getInstance();
         DiagnosticDataAccessRuntime runtime = (DiagnosticDataAccessRuntime)accessor.lookupWLDFDataAccessRuntime("HarvestedDataArchive");
         return (DataWriter)runtime.getDiagnosticDataAccessService();
      } catch (Exception var3) {
         throw new ArchiveException(var3);
      }
   }
}
