package weblogic.xml.stax;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class ChildReaderToWriter extends ReaderToWriter {
   public ChildReaderToWriter() {
   }

   public ChildReaderToWriter(XMLStreamWriter xmlw) {
      super(xmlw);
   }

   public void writeChildren(XMLStreamReader xmlr) throws XMLStreamException {
      if (!xmlr.isStartElement()) {
         throw new XMLStreamException("Child must be on a start element!");
      } else {
         xmlr.next();

         for(int depth = 1; depth > 0 && xmlr.hasNext(); xmlr.next()) {
            if (xmlr.getEventType() == 1) {
               ++depth;
            } else if (xmlr.getEventType() == 2) {
               --depth;
            }

            if (depth > 0) {
               super.write(xmlr);
            }
         }

      }
   }
}
