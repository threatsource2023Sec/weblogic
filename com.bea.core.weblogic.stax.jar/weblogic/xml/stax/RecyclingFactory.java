package weblogic.xml.stax;

import java.io.FileInputStream;
import java.io.Reader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;

public class RecyclingFactory extends XMLStreamInputFactory {
   private final Pool pool = new StackPool(32);

   public XMLStreamReader createXMLStreamReader(Reader in) throws XMLStreamException {
      XMLStreamReaderBase base = (XMLStreamReaderBase)this.pool.remove();
      if (base == null) {
         base = new XMLStreamReaderBase();
         base.setFactory(this);
         base.setConfigurationContext(this.config);
         base.setInput(in);
         return base;
      } else {
         base.setFactory(this);
         base.setInput(in);
         return base;
      }
   }

   public void add(XMLStreamReader r) throws XMLStreamException {
      this.pool.add(r);
   }

   public static void main(String[] args) throws Exception {
      RecyclingFactory factory = new RecyclingFactory();

      for(int i = 0; i < 50; ++i) {
         XMLStreamReader input = factory.createXMLStreamReader(new FileInputStream(args[0]));

         while(input.hasNext()) {
            System.out.println(input);
            input.next();
         }

         input.close();
      }

   }
}
