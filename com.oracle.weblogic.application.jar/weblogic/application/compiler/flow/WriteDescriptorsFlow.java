package weblogic.application.compiler.flow;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.compiler.CompilerCtx;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.utils.compiler.ToolFailureException;

public final class WriteDescriptorsFlow extends WriteInferredDescriptorFlow {
   public WriteDescriptorsFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.removeLibRefs();
      this.writeDescriptors();
      super.compile();
   }

   public void cleanup() {
   }

   private void removeLibRefs() throws ToolFailureException {
      if (this.ctx.getWLApplicationDD() != null && this.ctx.getWLApplicationDD().getLibraryRefs() != null) {
         WeblogicApplicationBean wlDD = this.ctx.getWLApplicationDD();
         LibraryRefBean[] libs = this.ctx.getWLApplicationDD().getLibraryRefs();

         for(int i = 0; i < libs.length; ++i) {
            wlDD.destroyLibraryRef(libs[i]);
         }

      }
   }

   private void writeDescriptors() throws ToolFailureException {
      try {
         ApplicationDescriptor appDes = this.ctx.getApplicationDescriptor();
         if (appDes != null) {
            appDes.writeDescriptors(this.ctx.getOutputDir());
         }

      } catch (IOException var2) {
         throw new RuntimeException(var2);
      } catch (XMLStreamException var3) {
         throw new RuntimeException(var3);
      }
   }
}
