package weblogic.xml.stream;

import java.io.File;
import java.io.IOException;

/** @deprecated */
@Deprecated
public class XMLStream {
   public static void usage() {
      System.out.println("XMLStream usage:");
      System.out.println("   weblogic.xml.stream.XMLStream [filename]");
      System.exit(1);
   }

   public static void main(String[] args) throws XMLStreamException, IOException {
      if (args.length != 1) {
         usage();
      }

      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      XMLInputStream stream = factory.newInputStream(new File(args[0]));

      while(stream.hasNext()) {
         XMLEvent event = stream.next();
         System.out.print("EVENT:[" + event.getLocation().getLineNumber() + "][" + event.getLocation().getColumnNumber() + "] " + event.getTypeAsString() + " [");
         System.out.print(event);
         System.out.println("]");
      }

   }
}
