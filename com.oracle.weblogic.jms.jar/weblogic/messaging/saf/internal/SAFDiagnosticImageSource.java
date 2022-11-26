package weblogic.messaging.saf.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.health.HealthState;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public final class SAFDiagnosticImageSource extends MessagingKernelDiagnosticImageSource {
   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")), 2);
         xsw.writeStartDocument();

         try {
            ((SAFManagerImpl)SAFManagerImpl.getManager()).dump(this, xsw);
         } catch (DiagnosticImageTimeoutException var4) {
            this.dumpTimeoutComment(xsw);
            return;
         }

         xsw.writeEndDocument();
         xsw.flush();
      } catch (XMLStreamException var5) {
         throw new ImageSourceCreationException("SAF image creation failed.", var5);
      } catch (IOException var6) {
         throw new ImageSourceCreationException("SAF image creation failed.", var6);
      }
   }

   public static void dumpHealthStateElement(XMLStreamWriter xsw, HealthState state) throws XMLStreamException {
      xsw.writeStartElement("Health");
      xsw.writeAttribute("state", HealthState.mapToString(state.getState()));
      String[] reasons = state.getReasonCode();
      if (reasons != null && reasons.length > 0) {
         for(int i = 0; i < reasons.length; ++i) {
            xsw.writeStartElement("Reason");
            xsw.writeCharacters(reasons[i]);
            xsw.writeEndElement();
         }
      }

      xsw.writeEndElement();
   }
}
