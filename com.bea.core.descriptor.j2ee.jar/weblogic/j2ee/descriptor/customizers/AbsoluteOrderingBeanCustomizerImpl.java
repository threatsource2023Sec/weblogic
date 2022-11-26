package weblogic.j2ee.descriptor.customizers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import weblogic.j2ee.descriptor.AbsoluteOrderingBean;
import weblogic.j2ee.descriptor.NameOrOrderingOthersBean;
import weblogic.j2ee.descriptor.OrderingOthersBean;

public class AbsoluteOrderingBeanCustomizerImpl implements AbsoluteOrderingBeanCustomizer {
   private static final String TARGET_NS = "http://java.sun.com/xml/ns/javaee";
   private final String NAME = "name";
   private final String OTHERS = "others";
   private final String ID = "id";
   AbsoluteOrderingBean bean;

   public AbsoluteOrderingBeanCustomizerImpl(AbsoluteOrderingBean bean) {
      this.bean = bean;
   }

   public void readFrom(XMLStreamReader reader) throws XMLStreamException {
      reader.next();

      while(advanceToNextStartElement(reader)) {
         NameOrOrderingOthersBean namedBean;
         if ("name".equals(reader.getLocalName())) {
            namedBean = this.bean.createNameOrOrderingOther();
            namedBean.setName(reader.getElementText());
         } else {
            if (!"others".equals(reader.getLocalName())) {
               throw new XMLStreamException("get unexpected child element:\t" + reader.getName() + " while unmarshal AbsoluteOrderingBean");
            }

            namedBean = this.bean.createNameOrOrderingOther();
            OrderingOthersBean other = namedBean.createOther();
            String id = reader.getAttributeValue((String)null, "id");
            if (id != null) {
               other.setId(id);
            }
         }

         advanceToNextEndElement(reader);
         reader.next();
      }

   }

   static boolean advanceToNextEndElement(XMLStreamReader reader) throws XMLStreamException {
      int state = reader.getEventType();

      while(reader.hasNext()) {
         switch (state) {
            case 2:
               return true;
            case 8:
               throw new XMLStreamException("unexpected end of XML");
            default:
               state = reader.next();
         }
      }

      return false;
   }

   static boolean advanceToNextStartElement(XMLStreamReader reader) throws XMLStreamException {
      int state = reader.getEventType();

      while(reader.hasNext()) {
         switch (state) {
            case 1:
               return true;
            case 2:
               return false;
            case 8:
               throw new XMLStreamException("unexpected end of XML");
            default:
               state = reader.next();
         }
      }

      return false;
   }

   public void writeTo(XMLStreamWriter writer) throws XMLStreamException {
      NameOrOrderingOthersBean[] nooos = this.bean.getNameOrOrderingOther();
      if (nooos != null) {
         for(int i = 0; i < nooos.length; ++i) {
            NameOrOrderingOthersBean nooo = nooos[i];
            String prefix = writer.getNamespaceContext().getPrefix("http://java.sun.com/xml/ns/javaee");
            if (prefix == null) {
               prefix = "";
            }

            if (nooo.getName() != null) {
               writer.writeStartElement(prefix, "name", "http://java.sun.com/xml/ns/javaee");
               writer.writeCharacters(nooo.getName());
               writer.writeEndElement();
            } else if (nooo.getOther() != null) {
               writer.writeStartElement(prefix, "others", "http://java.sun.com/xml/ns/javaee");
               if (nooo.getOther().getId() != null) {
                  writer.writeAttribute("id", nooo.getOther().getId());
               }

               writer.writeEndElement();
            }
         }
      }

   }
}
