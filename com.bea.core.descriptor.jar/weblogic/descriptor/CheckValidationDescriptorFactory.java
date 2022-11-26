package weblogic.descriptor;

import com.bea.staxb.runtime.ObjectFactory;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamReader;

public interface CheckValidationDescriptorFactory extends DescriptorFactory {
   Object createDescriptor(InputStream var1, ObjectFactory var2, boolean var3) throws IOException;

   Object createDescriptor(XMLStreamReader var1, ObjectFactory var2, boolean var3) throws IOException;
}
