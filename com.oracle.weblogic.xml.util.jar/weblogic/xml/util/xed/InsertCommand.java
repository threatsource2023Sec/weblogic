package weblogic.xml.util.xed;

import java.io.StringReader;
import weblogic.xml.stream.BufferedXMLInputStream;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.util.TypeFilter;

public class InsertCommand extends Command {
   private String xml;
   private BufferedXMLInputStream xis;
   private boolean hit = false;

   public String getName() {
      return "insert";
   }

   public void setXML(String xml) throws StreamEditorException {
      try {
         this.xml = xml;
         XMLInputStreamFactory f = XMLInputStreamFactory.newInstance();
         f.setFilter(new TypeFilter(86));
         this.xis = f.newBufferedInputStream(f.newInputStream(new StringReader(xml)));
         this.xis.mark();

         while(this.xis.hasNext()) {
            this.xis.next();
         }

      } catch (XMLStreamException var3) {
         throw new StreamEditorException(var3.toString());
      }
   }

   public Object evaluate(Context context) throws StreamEditorException {
      this.hit = true;
      return null;
   }

   public String getXML() {
      return this.xml;
   }

   public String toString() {
      return super.toString() + " [" + this.getXML() + "]";
   }

   public XMLInputStream getResult() throws XMLStreamException {
      if (this.hit) {
         this.hit = false;
         this.xis.reset();
         this.xis.mark();
         return this.xis;
      } else {
         return null;
      }
   }
}
