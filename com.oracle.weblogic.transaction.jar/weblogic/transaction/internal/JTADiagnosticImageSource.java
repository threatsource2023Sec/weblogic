package weblogic.transaction.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.health.HealthState;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public class JTADiagnosticImageSource implements JTAImageSource {
   private ServerTransactionManagerImpl tm;
   private boolean timedOut;

   JTADiagnosticImageSource(ServerTransactionManagerImpl tm) {
      this.tm = tm;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImage((String)null, out);
   }

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      try {
         XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")), 2);
         xsw.writeStartDocument();

         try {
            if (partitionName == null) {
               this.tm.dump(this, xsw);
            } else {
               this.tm.dumpPartition(this, xsw, partitionName);
            }
         } catch (DiagnosticImageTimeoutException var5) {
            this.dumpTimeoutComment(xsw);
            return;
         }

         xsw.writeEndDocument();
         xsw.flush();
      } catch (XMLStreamException var6) {
         throw new ImageSourceCreationException("JTA image creation failed.", var6);
      } catch (IOException var7) {
         throw new ImageSourceCreationException("JTA image creation failed.", var7);
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
