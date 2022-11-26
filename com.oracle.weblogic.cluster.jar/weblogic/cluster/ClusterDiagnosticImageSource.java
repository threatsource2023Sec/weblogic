package weblogic.cluster;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public class ClusterDiagnosticImageSource implements ImageSource {
   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")), 2);
         xsw.writeStartDocument();
         xsw.writeStartElement("Cluster");
         ClusterMessagesManager multicastManager = ClusterMessagesManager.theOne();
         if (multicastManager != null) {
            multicastManager.dumpDiagnosticImageData(xsw);
         }

         xsw.writeEndElement();
         xsw.writeEndDocument();
         xsw.flush();
      } catch (XMLStreamException var4) {
         throw new ImageSourceCreationException("Cluster image creation failed.", var4);
      } catch (IOException var5) {
         throw new ImageSourceCreationException("Cluster image creation failed.", var5);
      }
   }

   public void timeoutImageCreation() {
   }
}
