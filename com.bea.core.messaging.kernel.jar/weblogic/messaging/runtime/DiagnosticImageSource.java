package weblogic.messaging.runtime;

import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;

public abstract class DiagnosticImageSource implements ImageSource {
   protected boolean timedOut;

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.timedOut = false;
   }

   public void timeoutImageCreation() {
      this.timedOut = true;
   }

   public void checkTimeout() throws DiagnosticImageTimeoutException {
      if (this.timedOut) {
         throw new DiagnosticImageTimeoutException();
      }
   }

   public void dumpTimeoutComment(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeComment("Diagnostic image creation timed out, aborting image dump");
   }
}
