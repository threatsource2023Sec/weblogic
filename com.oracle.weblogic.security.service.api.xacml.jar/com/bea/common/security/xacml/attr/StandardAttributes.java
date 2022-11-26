package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Node;

public class StandardAttributes implements AttributeFactory {
   private Map registry = new HashMap();

   private void register(URI identifier, SimpleAttributeFactory saf) {
      this.registry.put(identifier, saf);
   }

   public StandardAttributes() throws URISyntaxException {
      this.register(Type.ANY_URI.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            try {
               return new AnyURIAttribute(new URI(attribute));
            } catch (java.net.URISyntaxException var3) {
               throw new URISyntaxException(var3);
            }
         }
      });
      this.register(Type.BASE64_BINARY.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new Base64BinaryAttribute(attribute);
         }
      });
      this.register(Type.BOOLEAN.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new BooleanAttribute(attribute);
         }
      });
      this.register(Type.DATE.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new DateAttribute(attribute);
         }
      });
      this.register(Type.DATE_TIME.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new DateTimeAttribute(attribute);
         }
      });
      this.register(Type.DAY_TIME_DURATION.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new DayTimeDurationAttribute(attribute);
         }
      });
      this.register(Type.DNS_ADDRESS.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new DNSAddressAttribute(attribute);
         }
      });
      this.register(Type.DOUBLE.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new DoubleAttribute(attribute);
         }
      });
      this.register(Type.HEX_BINARY.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new HexBinaryAttribute(attribute);
         }
      });
      this.register(Type.INTEGER.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new IntegerAttribute(attribute);
         }
      });
      this.register(Type.IP_ADDRESS.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new IPAddressAttribute(attribute);
         }
      });
      this.register(Type.RFC822_NAME.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new RFC822NameAttribute(attribute);
         }
      });
      this.register(Type.STRING.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new StringAttribute(attribute);
         }
      });
      this.register(Type.TIME.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new TimeAttribute(attribute);
         }
      });
      this.register(Type.X500_NAME.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new X500NameAttribute(attribute);
         }
      });
      this.register(Type.YEAR_MONTH_DURATION.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new YearMonthDurationAttribute(attribute);
         }
      });
      this.register(Type.CHARACTER.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new CharacterAttribute(attribute);
         }
      });
      this.register(Type.LONG.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new LongAttribute(attribute);
         }
      });
      this.register(Type.FLOAT.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new FloatAttribute(attribute);
         }
      });
      this.register(Type.DECIMAL.getDataType(), new SimpleAttributeFactory() {
         public AttributeValue createAttribute(String attribute) throws InvalidAttributeException, URISyntaxException {
            return new DecimalAttribute(attribute);
         }
      });
   }

   public AttributeValue createAttribute(Node attribute, Iterator otherFactories) throws URISyntaxException, InvalidAttributeException {
      try {
         return this.createAttribute(new URI(attribute.getAttributes().getNamedItem("DataType").getNodeValue()), attribute, otherFactories);
      } catch (java.net.URISyntaxException var4) {
         throw new URISyntaxException(var4);
      }
   }

   public AttributeValue createAttribute(URI dataType, Node attribute, Iterator otherFactories) throws URISyntaxException, InvalidAttributeException {
      SimpleAttributeFactory saf = (SimpleAttributeFactory)this.registry.get(dataType);
      if (saf != null) {
         Node node = attribute.getFirstChild();
         String value = null;
         if (node == null) {
            value = "";
         } else {
            switch (node.getNodeType()) {
               case 3:
               case 4:
               case 8:
                  value = node.getNodeValue();
                  break;
               default:
                  throw new InvalidAttributeException("AttributeValue did not contain appropriate child element");
            }
         }

         return saf.createAttribute(value);
      } else {
         return null;
      }
   }

   public AttributeValue createAttribute(URI dataType, String attributeValue) throws InvalidAttributeException, URISyntaxException {
      SimpleAttributeFactory saf = (SimpleAttributeFactory)this.registry.get(dataType);
      return saf != null ? saf.createAttribute(attributeValue) : null;
   }
}
