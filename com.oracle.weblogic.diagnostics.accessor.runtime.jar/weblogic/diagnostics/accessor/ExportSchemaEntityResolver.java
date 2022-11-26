package weblogic.diagnostics.accessor;

import java.io.IOException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.diagnostics.debug.DebugLogger;

public class ExportSchemaEntityResolver implements EntityResolver {
   private static final String EXPORT_SCHEMA = "export.xsd";
   private static final DebugLogger accessorDebug = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      if (accessorDebug.isDebugEnabled()) {
         accessorDebug.debug("Resolving public Id " + publicId);
         accessorDebug.debug("Resolving system Id " + systemId);
      }

      return publicId != null && !systemId.equals("http://www.bea.com/ns/weblogic/90/diagnostics/accessor/export.xsd export.xsd") ? null : new InputSource(this.getClass().getResourceAsStream("export.xsd"));
   }
}
