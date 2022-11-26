package weblogic.xml.xmlnode;

import java.io.PrintWriter;
import weblogic.xml.stream.XMLName;

public class XMLEndNode extends XMLNode {
   public XMLEndNode(String n) {
      this.name = this.createXMLName(n, (String)null, (String)null);
   }

   public XMLEndNode(XMLName name) {
      super(name);
   }

   public boolean isEndNode() {
      return true;
   }

   public void write(PrintWriter out) {
      out.write("</");
      out.write(this.name.getQualifiedName());
      out.write(">");
   }
}
