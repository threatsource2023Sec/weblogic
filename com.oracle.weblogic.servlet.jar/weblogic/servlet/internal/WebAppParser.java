package weblogic.servlet.internal;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;

public interface WebAppParser {
   WebAppBean getWebAppBean() throws IOException, XMLStreamException;

   WeblogicWebAppBean getWeblogicWebAppBean() throws IOException, XMLStreamException;
}
