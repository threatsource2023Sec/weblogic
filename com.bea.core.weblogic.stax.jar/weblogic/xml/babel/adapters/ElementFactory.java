package weblogic.xml.babel.adapters;

import java.io.IOException;
import org.xml.sax.SAXException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.CharDataElement;
import weblogic.xml.babel.baseparser.CommentElement;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.EndElement;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.babel.scanner.ScannerException;

public interface ElementFactory {
   Object create(CharDataElement var1);

   Object create(StartElement var1);

   Object create(Space var1);

   Object create(CommentElement var1);

   Object create(EndElement var1);

   Object create(String var1, String var2);

   Object create(String var1);

   Object create(PrefixMapping var1);

   Object dispatch(Element var1);

   void setBaseParser(BaseParser var1);

   Throwable create(ParseException var1);

   Throwable create(ScannerException var1);

   Throwable create(SAXException var1);

   Throwable create(IOException var1);
}
