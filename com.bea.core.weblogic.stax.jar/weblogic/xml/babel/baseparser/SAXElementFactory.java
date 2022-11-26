package weblogic.xml.babel.baseparser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.LocatorImpl;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.babel.scanner.ScannerException;

public class SAXElementFactory {
   public static String nullToEmptyString(String s) {
      return s == null ? "" : s;
   }

   public static Attributes createAttributes(ArrayList babelAttributes) throws SAXException {
      AttributesImpl attributes = new AttributesImpl();
      attributes.clear();
      ListIterator i = babelAttributes.listIterator();
      Set names = new HashSet();

      while(i.hasNext()) {
         Attribute attribute = (Attribute)i.next();
         attributes.addAttribute(nullToEmptyString(attribute.uri), attribute.name, attribute.getRawName(), "CDATA", attribute.getValue());
         if (names.contains(attribute.getNSName())) {
            throw new SAXException("Attributes may not have the same name, more than one" + attribute.getName());
         }

         names.add(attribute.getNSName());
      }

      return attributes;
   }

   public static Locator createLocator(BaseParser parser) {
      LocatorImpl locator = new LocatorImpl();
      locator.setLineNumber(parser.getLine());
      locator.setColumnNumber(parser.getColumn());
      return locator;
   }

   public static Locator createLocator(Element element) {
      LocatorImpl locator = new LocatorImpl();
      locator.setLineNumber(element.getLine());
      locator.setColumnNumber(element.getColumn());
      return locator;
   }

   public static SAXParseException createSAXParseException(ScannerException scannerException, Locator locator) {
      return new SAXParseException(scannerException.getMessage(), locator, scannerException);
   }

   public static SAXParseException createSAXParseException(ParseException parseException, Locator locator) {
      return new SAXParseException(parseException.getMessage(), locator, parseException);
   }

   public static SAXParseException createSAXParseException(Exception exception, Locator locator) {
      return new SAXParseException(exception.getMessage(), locator, exception);
   }

   public static InputSource createInputSource(File file) throws IOException {
      InputSource retval = new InputSource(XmlReader.createReader(new BufferedInputStream(new FileInputStream(file))));
      String path = file.toURL().toString();
      retval.setSystemId("file:" + path);
      return retval;
   }

   public static InputSource createInputSource(String fileName) throws IOException {
      InputSource retval = new InputSource(XmlReader.createReader(new BufferedInputStream(new FileInputStream(fileName))));
      return retval;
   }
}
