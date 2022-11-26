package weblogic.application.internal;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;

public class WlsExtensionReader extends VersionMunger {
   public WlsExtensionReader(InputStream delegate, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(delegate, loader, "weblogic.j2ee.descriptor.wl.WeblogicExtensionBeanImpl$SchemaHelper2", "http://xmlns.oracle.com/weblogic/weblogic-extension");
   }
}
