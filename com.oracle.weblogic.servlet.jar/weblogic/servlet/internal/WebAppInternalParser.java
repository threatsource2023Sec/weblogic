package weblogic.servlet.internal;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.classloaders.Source;

public interface WebAppInternalParser extends WebAppParser {
   boolean hasWebDescriptorFile();

   DescriptorBean mergeLibaryDescriptors(Source[] var1, String var2) throws IOException, XMLStreamException;
}
