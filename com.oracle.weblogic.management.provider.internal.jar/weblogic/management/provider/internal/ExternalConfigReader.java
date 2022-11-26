package weblogic.management.provider.internal;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;

public class ExternalConfigReader extends ConfigReader {
   private boolean rootElementStarted = false;

   public ExternalConfigReader(InputStream _in) throws XMLStreamException {
      super(_in);
   }

   public int next() throws XMLStreamException {
      int next = super.next();
      if (!this.rootElementStarted && next == 1) {
         this.rootElementStarted = true;
         if ("weblogic-jms".equals(this.getLocalName())) {
            this.addNamespaceMapping("http://www.bea.com/ns/weblogic/90", "http://xmlns.oracle.com/weblogic/weblogic-jms");
            this.addNamespaceMapping("http://www.bea.com/ns/weblogic/weblogic-jms", "http://xmlns.oracle.com/weblogic/weblogic-jms");
         } else if ("jdbc-data-source".equals(this.getLocalName())) {
            this.addNamespaceMapping("http://www.bea.com/ns/weblogic/90", "http://xmlns.oracle.com/weblogic/jdbc-data-source");
            this.addNamespaceMapping("http://www.bea.com/ns/weblogic/jdbc-data-source", "http://xmlns.oracle.com/weblogic/jdbc-data-source");
         } else if ("wldf-resource".equals(this.getLocalName())) {
            this.addNamespaceMapping("http://www.bea.com/ns/weblogic/90/diagnostics", "http://xmlns.oracle.com/weblogic/weblogic-diagnostics");
            this.addNamespaceMapping("http://www.bea.com/ns/weblogic/weblogic-diagnostics", "http://xmlns.oracle.com/weblogic/weblogic-diagnostics");
         }
      }

      return next;
   }
}
