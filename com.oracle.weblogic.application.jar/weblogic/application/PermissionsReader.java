package weblogic.application;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;

public class PermissionsReader extends VersionMunger {
   public PermissionsReader(InputStream delegate, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(delegate, loader, "weblogic.j2ee.descriptor.PermissionsBeanImpl$SchemaHelper2", "http://xmlns.jcp.org/xml/ns/javaee");
   }

   protected VersionMunger.Continuation onStartElement(String localName) {
      if (!this.hasDTD() && "permissions".equals(localName)) {
         this.checkAndUpdateVersionAttribute();
      }

      return CONTINUE;
   }

   protected String getLatestSchemaVersion() {
      return "7";
   }

   protected boolean enableCallbacksOnSchema() {
      return true;
   }
}
