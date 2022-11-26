package kodo.profile;

import com.solarmetric.profile.ExecutionContextNameProviderImpl;
import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.ProfilingExportException;
import com.solarmetric.profile.ProfilingIO;
import java.io.File;
import kodo.kernel.KodoStoreContext;
import org.apache.openjpa.conf.OpenJPAVersion;

public class KodoExecutionContextNameProviderImpl extends ExecutionContextNameProviderImpl implements KodoExecutionContextNameProvider {
   public KodoExecutionContextNameProviderImpl() {
      this.addRemovalCandidate("kodo.");
      this.addRemovalCandidate("org.apache.openjpa.");
   }

   public void setRemovalCandidates(String removalCandidates) {
      String[] removalCandidateArray = removalCandidates.split(";");

      for(int i = 0; i < removalCandidateArray.length; ++i) {
         this.addRemovalCandidate(removalCandidateArray[i]);
      }

   }

   public static void exportAgent(KodoStoreContext ctx, String filename) throws ProfilingExportException {
      File file = new File(filename);
      exportAgent(ctx, file);
   }

   public static void exportAgent(KodoStoreContext ctx, File file) throws ProfilingExportException {
      ProfilingAgentImpl agent = (ProfilingAgentImpl)ctx.getProfilingAgent();
      ProfilingIO.exportAgent((new OpenJPAVersion()).toString(), ctx.getConfiguration(), agent, (File)file);
   }
}
