package weblogic.application.compiler.flow;

import java.io.File;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public final class DescriptorParsingFlow extends CompilerFlow {
   public DescriptorParsingFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      Getopt2 opts = this.ctx.getOpts();
      VirtualJarFile vSource = this.ctx.getVSource();
      File altAppDD = null;
      String option = opts.hasOption("altappdd") ? "altappdd" : (opts.hasOption("altdd") ? "altdd" : null);
      if (option != null) {
         altAppDD = new File(opts.getOption(option));
         if (!altAppDD.exists()) {
            throw new ToolFailureException(J2EELogger.logAppcMissingApplicationAltDDFileLoggable(altAppDD.getPath()).getMessage());
         }
      }

      File altAppWLDD = null;
      if (opts.hasOption("altwlsappdd")) {
         altAppWLDD = new File(opts.getOption("altwlsappdd"));
         if (!altAppWLDD.exists()) {
            throw new ToolFailureException(J2EELogger.logAppcMissingApplicationAltDDFileLoggable(altAppWLDD.getPath()).getMessage());
         }
      }

      ApplicationDescriptor appParser = new ApplicationDescriptor(altAppDD, altAppWLDD, vSource, this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.ctx.getSourceName());
      AppcUtils.setDDs(appParser, this.ctx);
      if (this.ctx.getApplicationDD() == null) {
         throw new ToolFailureException(J2EELogger.logAppcNoApplicationDDFoundLoggable(vSource.getName()).getMessage());
      }
   }

   public void cleanup() {
   }
}
