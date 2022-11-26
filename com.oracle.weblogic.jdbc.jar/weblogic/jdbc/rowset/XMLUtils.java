package weblogic.jdbc.rowset;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.Location;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLName;

final class XMLUtils implements XMLSchemaConstants {
   private static String loc(StartElement se) {
      Location loc = se.getLocation();
      return "line: " + loc.getLineNumber() + " column: " + loc.getColumnNumber();
   }

   public static boolean getOptionalBooleanAttribute(StartElement se, XMLName n) throws IOException {
      Attribute attr = se.getAttributeByName(n);
      if (attr == null) {
         return false;
      } else {
         try {
            return new Boolean(attr.getValue());
         } catch (NumberFormatException var4) {
            throw new IOException("Expected true or false, but found " + attr.getValue() + " for attribute: " + n + " on element: " + se + " at " + loc(se));
         }
      }
   }

   public static String getOptionalStringAttribute(StartElement se, XMLName n) throws IOException {
      Attribute attr = se.getAttributeByName(n);
      return attr == null ? null : attr.getValue();
   }

   public static Attribute getRequiredAttribute(StartElement se, String localName) throws IOException {
      return getRequiredAttribute(se, ElementFactory.createXMLName(localName));
   }

   public static Attribute getRequiredAttribute(StartElement se, XMLName n) throws IOException {
      Attribute a = se.getAttributeByName(n);
      if (a == null) {
         throw new IOException("Element " + se.getName() + " at " + loc(se) + " does not contain required attribute: " + n.getLocalName());
      } else {
         return a;
      }
   }

   public static void addAttributesFromPropertyMap(List attributes, Map m) {
      Iterator it = m.keySet().iterator();

      while(it.hasNext()) {
         XMLName namespace = (XMLName)it.next();
         String uri = namespace.getNamespaceUri();
         String prefix = namespace.getPrefix();
         if (uri != null && prefix != null) {
            attributes.add(ElementFactory.createNamespaceAttribute(prefix, uri));
         }

         Properties props = (Properties)m.get(namespace);
         Enumeration en = props.propertyNames();

         while(en.hasMoreElements()) {
            String propName = (String)en.nextElement();
            String value = props.getProperty(propName);
            XMLName attName = ElementFactory.createXMLName(uri, propName, prefix);
            attributes.add(ElementFactory.createAttribute(attName, value));
         }
      }

   }

   public static Map readPropertyMapFromAttributes(StartElement se) {
      Map m = new HashMap();
      AttributeIterator it = se.getAttributes();

      while(it.hasNext()) {
         Attribute a = it.next();
         XMLName name = a.getName();
         String namespace = name.getNamespaceUri();
         if (!"http://www.w3.org/2001/XMLSchema".equals(namespace) && !"http://www.bea.com/2002/10/weblogicdata".equals(namespace) && namespace != null) {
            XMLName canonName = ElementFactory.createXMLName(name.getNamespaceUri(), "", name.getPrefix());
            Properties p = (Properties)m.get(canonName);
            if (p == null) {
               p = new Properties();
               m.put(canonName, p);
            }

            p.put(name.getLocalName(), a.getValue());
         }
      }

      return m;
   }
}
