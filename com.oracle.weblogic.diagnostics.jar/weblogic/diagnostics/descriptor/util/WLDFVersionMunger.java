package weblogic.diagnostics.descriptor.util;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.VersionMunger;

public class WLDFVersionMunger extends VersionMunger {
   private static final String WLDF_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   private static final String BEA_WLDF_NAMESPACE_URI = "http://www.bea.com/ns/weblogic/weblogic-diagnostics";
   private static final String WLDF_OLD_NAMESPACE_URI = "http://www.bea.com/ns/weblogic/90/diagnostics";

   public WLDFVersionMunger(InputStream in, WLDFDescriptorLoader loader) throws XMLStreamException {
      super(in, loader, "weblogic.diagnostics.descriptor.WLDFResourceBeanImpl$SchemaHelper2", "http://xmlns.oracle.com/weblogic/weblogic-diagnostics");
   }

   protected boolean isOldNamespaceURI(String namespaceURI) {
      return namespaceURI != null && (namespaceURI.equals("http://www.bea.com/ns/weblogic/90/diagnostics") || namespaceURI.equals("http://www.bea.com/ns/weblogic/weblogic-diagnostics"));
   }
}
