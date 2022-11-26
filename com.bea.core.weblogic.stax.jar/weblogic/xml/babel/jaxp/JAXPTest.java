package weblogic.xml.babel.jaxp;

import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.xml.babel.helpers.SAXDefaultHandlerImpl;

public class JAXPTest extends SAXParserImpl {
   public static void main(String[] args) {
      try {
         DefaultHandler defaultHandler = new SAXDefaultHandlerImpl();
         ((SAXDefaultHandlerImpl)defaultHandler).setVerbose();
         System.setProperty("javax.xml.parsers.SAXParserFactory", "weblogic.xml.babel.jaxp.SAXParserFactoryImpl");
         SAXParserFactory factory = SAXParserFactory.newInstance();
         System.out.println("FACTORY VALIDATING:" + factory.isValidating());
         System.out.println("FACTORY NAMESPACE :" + factory.isNamespaceAware());
         SAXParser parser = factory.newSAXParser();
         parser.parse(args[0], defaultHandler);
      } catch (SAXException var4) {
         System.out.println("---------SAXException-------");
         System.out.println(var4);
      } catch (IOException var5) {
         System.out.println("---------IOException-------");
         System.out.println(var5);
      } catch (Exception var6) {
         System.out.println("---------Exception-------");
         System.out.println(var6);
      }

   }
}
