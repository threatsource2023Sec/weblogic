package org.glassfish.hk2.xml.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import javax.xml.stream.XMLStreamReader;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface XmlService {
   String DEFAULT_NAMESPACE = "##default";

   XmlRootHandle unmarshal(URI var1, Class var2);

   XmlRootHandle unmarshal(URI var1, Class var2, boolean var3, boolean var4);

   XmlRootHandle unmarshal(URI var1, Class var2, boolean var3, boolean var4, Map var5);

   XmlRootHandle unmarshal(XMLStreamReader var1, Class var2, boolean var3, boolean var4);

   XmlRootHandle unmarshal(XMLStreamReader var1, Class var2, boolean var3, boolean var4, Map var5);

   XmlRootHandle unmarshal(InputStream var1, Class var2);

   XmlRootHandle unmarshal(InputStream var1, Class var2, boolean var3, boolean var4);

   XmlRootHandle unmarshal(InputStream var1, Class var2, boolean var3, boolean var4, Map var5);

   XmlRootHandle createEmptyHandle(Class var1, boolean var2, boolean var3);

   XmlRootHandle createEmptyHandle(Class var1);

   Object createBean(Class var1);

   void marshal(OutputStream var1, XmlRootHandle var2) throws IOException;

   void marshal(OutputStream var1, XmlRootHandle var2, Map var3) throws IOException;
}
