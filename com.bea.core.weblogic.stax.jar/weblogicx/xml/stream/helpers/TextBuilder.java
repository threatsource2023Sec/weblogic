package weblogicx.xml.stream.helpers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import weblogicx.xml.stream.ElementEvent;
import weblogicx.xml.stream.EndElementEvent;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.TextEvent;
import weblogicx.xml.stream.XMLEvent;
import weblogicx.xml.stream.XMLEventStream;

public class TextBuilder {
   public static String process(XMLEventStream xes) throws SAXException {
      StringBuffer sb = new StringBuffer();
      int startTags = 0;
      int endTags = 0;

      while(xes.hasNext()) {
         XMLEvent e = xes.next();
         if (!(e instanceof ElementEvent)) {
            if (e instanceof TextEvent) {
               TextEvent te = (TextEvent)e;
               sb.append(te.getText());
            }
         } else if (!(e instanceof StartElementEvent)) {
            if (e instanceof EndElementEvent) {
               ++endTags;
               if (endTags > startTags) {
                  break;
               }

               EndElementEvent eee = (EndElementEvent)e;
               sb.append("</");
               sb.append(eee.getQualifiedName());
               sb.append(">");
            }
         } else {
            ++startTags;
            StartElementEvent see = (StartElementEvent)e;
            sb.append("<");
            sb.append(see.getQualifiedName());
            Attributes attrs = see.getAttributes();

            for(int i = 0; i < attrs.getLength(); ++i) {
               sb.append(" ");
               sb.append(attrs.getQName(i));
               sb.append("='");
               sb.append(attrs.getValue(i));
               sb.append("'");
            }

            sb.append(">");
         }
      }

      return sb.toString();
   }
}
