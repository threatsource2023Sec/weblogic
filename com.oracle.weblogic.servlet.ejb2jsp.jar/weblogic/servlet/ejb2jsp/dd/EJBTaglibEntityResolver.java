package weblogic.servlet.ejb2jsp.dd;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.dom.ResourceEntityResolver;

public final class EJBTaglibEntityResolver extends ResourceEntityResolver {
   private static final boolean debug = true;
   private static final String EJB2JSP_PUBLIC_ID = "-//BEA Systems, Inc.//DTD EJB2JSP Taglib 1.0//EN";
   private static final String EJB2JSP_LOCAL_DTD_NAME = "weblogic-ejb2jsp.dtd";

   static void p(String s) {
      System.err.println("[ejbtagEntityResolver]: " + s);
   }

   public EJBTaglibEntityResolver() {
      this.addEntityResource("-//BEA Systems, Inc.//DTD EJB2JSP Taglib 1.0//EN", "weblogic-ejb2jsp.dtd", this.getClass());
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      return super.resolveEntity(publicId, systemId);
   }
}
