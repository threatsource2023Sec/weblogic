package weblogic.xml.stream.util;

import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

/** @deprecated */
@Deprecated
public class XMLSubStreamBase extends XMLInputStreamFilterBase {
   protected int startTags;
   protected int endTags;

   protected XMLSubStreamBase() {
   }

   public XMLSubStreamBase(XMLInputStream parent) throws XMLStreamException {
      super(parent, new AcceptingFilter());
   }

   public XMLSubStreamBase(XMLInputStream parent, ElementFilter filter) throws XMLStreamException {
      super(parent, filter);
   }

   public XMLEvent next() throws XMLStreamException {
      if (this.hasNext()) {
         XMLEvent e = this.parent.next();
         if (e != null) {
            switch (e.getType()) {
               case 2:
                  ++this.startTags;
                  break;
               case 4:
                  ++this.endTags;
                  if (this.startTags <= this.endTags) {
                     this.open = false;
                  }
                  break;
               case 128:
               case 512:
                  this.open = false;
            }
         }

         return e;
      } else {
         return null;
      }
   }
}
