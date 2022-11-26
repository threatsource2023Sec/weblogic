package weblogic.application.compiler.flow;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.compiler.CompilerCtx;
import weblogic.utils.compiler.ToolFailureException;

public class WriteInferredDescriptorFlow extends CompilerFlow {
   public WriteInferredDescriptorFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void cleanup() throws ToolFailureException {
   }

   public void compile() throws ToolFailureException {
      if (this.ctx.isWriteInferredDescriptors()) {
         this.writeInferredApplicationDescriptor();
      }

   }

   private void writeInferredApplicationDescriptor() throws ToolFailureException {
      try {
         ApplicationDescriptor appDes = this.ctx.getApplicationDescriptor();
         if (appDes != null) {
            appDes.doWriteInferredApplicationDescriptor(this.ctx.getOutputDir());
         }

      } catch (IOException var2) {
         throw new RuntimeException(var2);
      } catch (XMLStreamException var3) {
         throw new RuntimeException(var3);
      }
   }
}
