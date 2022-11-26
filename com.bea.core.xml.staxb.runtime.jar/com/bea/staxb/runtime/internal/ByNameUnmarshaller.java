package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.StaxDeSerializer;
import com.bea.xml.XmlException;
import javax.xml.stream.XMLStreamException;

final class ByNameUnmarshaller extends AttributeUnmarshaller {
   private final ByNameRuntimeBindingType byNameType;

   public ByNameUnmarshaller(ByNameRuntimeBindingType type) {
      super(type);
      this.byNameType = type;
   }

   protected void deserializeContents(Object inter, UnmarshalResult context) throws XmlException {
      assert context.isStartElement();

      if (this.byNameType.getElementPropertyCount() == 0 && inter instanceof StaxDeSerializer) {
         try {
            ((StaxDeSerializer)inter).readFrom(context.baseReader);
         } catch (XMLStreamException var8) {
            throw new XmlException(var8);
         }
      } else {
         String ourStartUri = context.getNamespaceURI();
         String ourStartLocalName = context.getLocalName();
         context.next();
         boolean inWildMode = false;

         while(context.advanceToNextStartElement()) {
            assert context.isStartElement();

            ByNameRuntimeBindingType.ElementQNameProperty prop = this.findMatchingElementProperty(context, inWildMode);
            if (prop == null) {
               context.skipElement();
            } else {
               if (prop.isWildUnbounded()) {
                  inWildMode = true;
               }

               context.extractAndFillElementProp(prop, inter);
            }

            assert context.isEndElement();

            if (context.hasNext()) {
               context.next();
            }
         }

         assert context.isEndElement();

         String ourEndUri = context.getNamespaceURI();
         String ourEndLocalName = context.getLocalName();

         assert ourStartUri.equals(ourEndUri) : "expected=" + ourStartUri + " got=" + ourEndUri;

         assert ourStartLocalName.equals(ourEndLocalName) : "expected=" + ourStartLocalName + " got=" + ourEndLocalName;

      }
   }

   private ByNameRuntimeBindingType.ElementQNameProperty findMatchingElementProperty(UnmarshalResult context, boolean wild) {
      String uri = context.getNamespaceURI();
      String lname = context.getLocalName();
      return this.byNameType.getMatchingElementProperty(uri, lname, context, wild);
   }
}
