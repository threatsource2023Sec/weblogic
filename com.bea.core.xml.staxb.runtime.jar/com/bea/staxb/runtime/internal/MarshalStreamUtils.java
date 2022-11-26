package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.richParser.XMLStreamReaderExt;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlError;
import com.bea.xml.XmlException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

final class MarshalStreamUtils {
   static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
   static final String XSI_TYPE_ATTR = "type";
   static final String XSI_NIL_ATTR = "nil";
   static final String XSI_SCHEMA_LOCATION_ATTR = "schemaLocation";
   static final String XSI_NO_NS_SCHEMA_LOCATION_ATTR = "noNamespaceSchemaLocation";
   static final QName XSI_NIL_QNAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "nil");
   static final QName XSI_TYPE_QNAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "type");

   static void getXsiAttributes(XsiAttributeHolder holder, XMLStreamReaderExt reader, Collection errors) throws XMLStreamException {
      assert reader.isStartElement();

      holder.reset();
      int att_cnt = reader.getAttributeCount();

      for(int att_idx = 0; att_idx < att_cnt; ++att_idx) {
         if ("http://www.w3.org/2001/XMLSchema-instance".equals(reader.getAttributeNamespace(att_idx))) {
            try {
               String lname = reader.getAttributeLocalName(att_idx);
               if ("type".equals(lname)) {
                  holder.xsiType = reader.getAttributeQNameValue(att_idx);
               } else if ("nil".equals(lname)) {
                  holder.hasXsiNil = reader.getAttributeBooleanValue(att_idx);
               } else if ("schemaLocation".equals(lname)) {
                  holder.schemaLocation = reader.getAttributeStringValue(att_idx, 3);
               } else if ("noNamespaceSchemaLocation".equals(lname)) {
                  holder.noNamespaceSchemaLocation = reader.getAttributeStringValue(att_idx, 3);
               }
            } catch (InvalidLexicalValueException var6) {
               addError(errors, var6.getMessage(), var6.getLocation());
            }
         }
      }

   }

   static QName getXsiType(XMLStreamReader reader, Collection errors) {
      assert reader.isStartElement();

      int att_cnt = reader.getAttributeCount();

      for(int att_idx = 0; att_idx < att_cnt; ++att_idx) {
         if ("http://www.w3.org/2001/XMLSchema-instance".equals(reader.getAttributeNamespace(att_idx))) {
            String lname = reader.getAttributeLocalName(att_idx);
            if ("type".equals(lname)) {
               String type_str = reader.getAttributeValue(att_idx);
               return XsTypeConverter.lexQName(type_str, errors, reader.getNamespaceContext());
            }
         }
      }

      return null;
   }

   static boolean advanceToNextStartElement(XMLStreamReader reader) throws XmlException {
      try {
         int state = reader.getEventType();

         while(reader.hasNext()) {
            switch (state) {
               case 1:
                  return true;
               case 2:
                  return false;
               case 8:
                  throw new XmlException("unexpected end of XML");
               default:
                  state = reader.next();
            }
         }

         return false;
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   static void skipElement(XMLStreamReader reader) throws XmlException {
      assert reader.isStartElement();

      int cnt = -1;

      try {
         int event = reader.getEventType();

         while(true) {
            switch (event) {
               case 1:
                  ++cnt;
                  break;
               case 2:
                  if (cnt == 0) {
                     return;
                  }

                  --cnt;
                  break;
               case 8:
                  throw new XmlException("unexpected end of xml document");
            }

            if (!reader.hasNext()) {
               throw new XmlException("unexpected end of xml stream");
            }

            event = reader.next();
         }
      } catch (XMLStreamException var3) {
         throw new XmlException(var3);
      }
   }

   static void advanceToFirstItemOfInterest(XMLStreamReader rdr) throws XmlException {
      try {
         int state = rdr.getEventType();

         while(rdr.hasNext()) {
            switch (state) {
               case 1:
                  return;
               case 2:
                  throw new XmlException("unexpected end of XML");
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 9:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
                  state = rdr.next();
                  break;
               case 8:
                  throw new XmlException("unexpected end of XML");
               case 10:
                  throw new AssertionError("NAKED ATTRIBUTE UNIMPLEMENTED");
               default:
                  throw new XmlException("unexpected xml state:" + state + "in" + rdr);
            }
         }
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }

      throw new XmlException("unexpected end of xml stream");
   }

   static void addError(Collection errors, String msg, Location location) {
      addError(errors, msg, 0, location);
   }

   static void addError(Collection errors, String msg, int severity, Location location) {
      XmlError err;
      if (location == null) {
         err = XmlError.forMessage(msg, severity);
      } else {
         String systemId = location.getSystemId();
         if (systemId == null && location.getLineNumber() != -1) {
            systemId = "<unknown>";
         }

         err = XmlError.forLocation(msg, severity, systemId, location.getLineNumber(), location.getColumnNumber(), location.getCharacterOffset());
      }

      errors.add(err);
   }

   static Object inputStreamToBytes(InputStream val) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      int b;
      while((b = val.read()) != -1) {
         baos.write(b);
      }

      return baos.toByteArray();
   }
}
