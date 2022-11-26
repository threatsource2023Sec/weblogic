package weblogic.servlet.jsp.dd;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.ResourceEntityResolver;
import weblogic.xml.jaxp.WebLogicDocumentBuilderFactory;

public final class JSPEntityResolver extends ResourceEntityResolver implements ErrorHandler {
   public static final String TLD_PUBLIC_ID_11 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";
   public static final String TLD_PUBLIC_ID_12 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";
   public boolean is12;

   public JSPEntityResolver() {
      this.addEntityResource("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN", "taglib-1_2.dtd", this.getClass());
      this.addEntityResource("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN", "taglib.dtd", this.getClass());
   }

   public void warning(SAXParseException e) throws SAXParseException {
   }

   public void error(SAXParseException e) throws SAXParseException {
      HTTPLogger.logMalformedWebDescriptor(e.toString(), e);
   }

   public void fatalError(SAXParseException e) throws SAXParseException {
      HTTPLogger.logMalformedWebDescriptor(e.toString(), e);
      throw e;
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      if (publicId == null) {
         return null;
      } else {
         if (publicId.equals("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN")) {
            this.is12 = true;
         } else if (!publicId.equals("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN")) {
            throw new IOException("Invalid DTD for taglib: cannot resolve '" + publicId + "'");
         }

         return super.resolveEntity(publicId, systemId);
      }
   }

   public static TLDDescriptor load(Reader rdr, ErrorHandler eh) throws IOException, ParserConfigurationException, SAXException, DOMProcessingException {
      DocumentBuilderFactory fact = new WebLogicDocumentBuilderFactory();
      fact.setValidating(true);
      DocumentBuilder domParser = fact.newDocumentBuilder();
      JSPEntityResolver res = new JSPEntityResolver();
      domParser.setEntityResolver(res);
      if (eh == null) {
         eh = res;
      }

      domParser.setErrorHandler((ErrorHandler)eh);
      InputSource src = new InputSource(rdr);
      Document root = domParser.parse(src);
      TLDDescriptor ret = new TLDDescriptor(root);
      return ret;
   }

   public static TLDDescriptor load(Reader rdr) throws IOException, ParserConfigurationException, SAXException, DOMProcessingException {
      return load(rdr, (ErrorHandler)null);
   }

   public static TLDDescriptor load(Reader rdr, String ctx, String location) throws IOException, ParserConfigurationException, SAXException, DOMProcessingException {
      JspcLogErrHandler err = new JspcLogErrHandler(ctx, location);
      return load(rdr, err);
   }

   public static void main(String[] a) throws Exception {
      TLDDescriptor dd = load(new FileReader(a[0]), new StderrHandler());
      TagDescriptor[] tags = (TagDescriptor[])((TagDescriptor[])dd.getTags());
      System.err.println("contains " + tags.length + " tags:");

      for(int i = 0; i < tags.length; ++i) {
         System.err.println(" " + tags[i]);
      }

   }
}
