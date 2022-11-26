package weblogic.ejb.spi;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.DescriptorBean;

public interface RDBMSDescriptor {
   DescriptorBean getDescriptorBean() throws IOException, XMLStreamException;
}
