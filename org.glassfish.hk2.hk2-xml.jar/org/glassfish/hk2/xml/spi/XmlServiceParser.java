package org.glassfish.hk2.xml.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface XmlServiceParser {
   String DEFAULT_PARSING_SERVICE = "JAXBXmlParsingService";
   String STREAM_PARSING_SERVICE = "StreamXmlParsingService";

   Object parseRoot(Model var1, URI var2, Unmarshaller.Listener var3, Map var4) throws Exception;

   Object parseRoot(Model var1, InputStream var2, Unmarshaller.Listener var3, Map var4) throws Exception;

   PreGenerationRequirement getPreGenerationRequirement();

   void marshal(OutputStream var1, XmlRootHandle var2, Map var3) throws IOException;
}
