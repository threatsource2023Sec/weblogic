package weblogic.xml.stax;

import javax.xml.stream.XMLStreamException;

public class XMLEventPlayer extends XMLEventReaderBase {
   private XMLStreamPlayer player;

   public XMLEventPlayer(XMLStreamPlayer reader) throws XMLStreamException {
      super(reader);
      this.player = reader;
   }

   protected boolean parseSome() throws XMLStreamException {
      this.allocator.allocate(this.reader, this);
      if (this.reader.hasNext()) {
         this.reader.next();
      }

      if (this.isOpen() && this.reader.getEventType() == 8) {
         if (this.player.endDocumentIsPresent()) {
            this.allocator.allocate(this.reader, this);
         }

         this.close();
      }

      return !this.needsMore();
   }
}
