package weblogic.application;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;

public interface DescriptorUpdater {
   void setApplicationDescriptor(ApplicationDescriptor var1) throws IOException, XMLStreamException;
}
