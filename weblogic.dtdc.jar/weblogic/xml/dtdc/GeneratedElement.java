package weblogic.xml.dtdc;

import java.io.IOException;
import java.io.PrintWriter;
import org.xml.sax.AttributeList;
import org.xml.sax.SAXException;

public interface GeneratedElement {
   void initialize(String var1, AttributeList var2) throws SAXException;

   void toXML(PrintWriter var1) throws IOException;

   void toXML(PrintWriter var1, int var2) throws IOException;

   String getElementName();
}
