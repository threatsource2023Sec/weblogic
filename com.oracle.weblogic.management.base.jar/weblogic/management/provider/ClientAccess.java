package weblogic.management.provider;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.management.configuration.DomainMBean;

public interface ClientAccess {
   DomainMBean getDomain(boolean var1) throws IOException, XMLStreamException;

   DomainMBean getDomain(String var1, boolean var2) throws IOException, XMLStreamException;

   void saveDomain(DomainMBean var1, OutputStream var2) throws IOException;

   void saveDomainDirectory(DomainMBean var1, String var2) throws IOException;
}
