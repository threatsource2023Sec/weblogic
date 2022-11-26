package weblogic.messaging.path;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.messaging.runtime.DiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.PersistentStoreException;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public class PathServiceDiagnosticImageSource extends DiagnosticImageSource {
   private PathService pathService;

   public PathServiceDiagnosticImageSource(PathService pathService) {
      this.pathService = pathService;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      super.createDiagnosticImage(out);

      try {
         XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(out), 2);
         xsw.writeStartDocument();

         try {
            this.dump(xsw);
         } catch (DiagnosticImageTimeoutException var4) {
            this.dumpTimeoutComment(xsw);
            return;
         } catch (PersistentStoreException var5) {
            throw new ImageSourceCreationException("PathService image creation failed.", var5);
         }

         xsw.writeEndDocument();
         xsw.flush();
      } catch (XMLStreamException var6) {
         throw new ImageSourceCreationException("PathService image creation failed.", var6);
      }
   }

   public void dump(XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException, PersistentStoreException {
      this.checkTimeout();
      xsw.writeStartElement("PathService");
      Iterator psAdminIterator = this.pathService.getPathServiceAdminIterator();

      while(true) {
         PathServiceAdmin psa;
         do {
            if (!psAdminIterator.hasNext()) {
               xsw.writeEndElement();
               return;
            }

            psa = (PathServiceAdmin)psAdminIterator.next();
         } while(psa == null);

         xsw.writeAttribute("running", String.valueOf(psa.isRunning()));
         xsw.writeAttribute("registered", String.valueOf(this.pathService.isRegistered()));
         Iterator psMapIterator = psa.getPathServiceMapIterator();

         while(psMapIterator.hasNext()) {
            PathServiceMap pathServiceMap = (PathServiceMap)psMapIterator.next();
            if (pathServiceMap != null) {
               pathServiceMap.dump(this, xsw);
            }
         }
      }
   }
}
