package weblogic.apache.org.apache.velocity.anakia;

import java.io.IOException;
import java.io.StringWriter;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class OutputWrapper extends XMLOutputter {
   public String outputString(Element element, boolean strip) {
      StringWriter buff = new StringWriter();
      String name = element.getName();

      try {
         this.outputElementContent(element, buff);
      } catch (IOException var6) {
      }

      return buff.toString();
   }
}
