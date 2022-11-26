package weblogic.xml.babel.examples.simple;

import org.xml.sax.InputSource;
import weblogic.xml.babel.parsers.BabelXMLEventStream;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.TextEvent;
import weblogicx.xml.stream.XMLEvent;

public class SimpleParser {
   public static void main(String[] args) throws Exception {
      BabelXMLEventStream xes = new BabelXMLEventStream();
      xes.startDocument(new InputSource(args[0]));

      while(xes.hasNext()) {
         StartElementEvent startElementEvent = xes.startElement();
         System.out.println(startElementEvent);
         XMLEvent e = xes.peek();
         if (e instanceof TextEvent) {
            TextEvent te = (TextEvent)e;
            System.out.println(te.getText());
         }
      }

   }
}
