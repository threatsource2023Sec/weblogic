package kodo.jdo.jdbc;

import java.io.StringReader;
import kodo.jdo.JDOEntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class JDOREntityResolver extends JDOEntityResolver {
   public static final String ID_SYSTEM_ORM = "file:/javax/jdo/orm.dtd";
   public static final String ID_PUBLIC_ORM = "-//Sun Microsystems, Inc.//DTD Java Data Objects Mapping Metadata 2.0//EN";
   private static String _ormDTD = readResource(JDOREntityResolver.class, "orm-dtd.rsrc", 6500);

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      InputSource in = super.resolveEntity(publicId, systemId);
      if (in == null && ("-//Sun Microsystems, Inc.//DTD Java Data Objects Mapping Metadata 2.0//EN".equals(publicId) || "file:/javax/jdo/orm.dtd".equals(systemId))) {
         in = new InputSource(new StringReader(_ormDTD));
      }

      return in;
   }
}
