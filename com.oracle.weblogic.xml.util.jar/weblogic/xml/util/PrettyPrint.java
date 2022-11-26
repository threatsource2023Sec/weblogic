package weblogic.xml.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import weblogic.utils.XXEUtils;
import weblogic.xml.stax.ReaderToWriter;

public class PrettyPrint {
   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.out.println("Usage: java weblogic.xml.util.PrettyPrint [file]");
      } else {
         XMLStreamWriter plain_writer = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
         int indentSize = true;
         XMLStreamWriter writer = new PrettyXMLStreamWriter(plain_writer, 2);
         File input_file = new File(args[0]);
         InputStream is = new FileInputStream(input_file);
         javax.xml.stream.XMLStreamReader reader = XXEUtils.createXMLInputFactoryInstance().createXMLStreamReader(is);
         ReaderToWriter readerToWriter = new ReaderToWriter(writer);
         readerToWriter.writeAll(reader);
         writer.flush();
         is.close();
      }
   }
}
