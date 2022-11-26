package weblogic.xml.schema.types;

import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

public final class XSDQName implements XSDBuiltinType {
   private final QName javaValue;
   private final String prefix;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "QName");
   private static final char NAMESPACE_SEP = ':';
   private static final String EMPTY_PREFIX = "";

   public static XSDQName createFromXml(String xml, String namespaceUri) {
      return new XSDQName(xml, namespaceUri);
   }

   public static XSDQName createFromXml(String xml, NamespaceContext namespaceContext) {
      return new XSDQName(xml, namespaceContext);
   }

   public static XSDQName createFromXml(String xml, Map prefixToNamespaceMap) {
      return new XSDQName(xml, prefixToNamespaceMap);
   }

   public static XSDQName createFromQName(QName qname, String prefix) {
      return new XSDQName(qname, prefix);
   }

   private XSDQName(String xml, NamespaceContext nscontext) {
      this.xmlValue = xml;
      int idx = indexOfNamespaceSeperator(xml);
      String localpart;
      if (idx < 0) {
         this.prefix = "";
         localpart = xml;
      } else {
         this.prefix = xml.substring(0, idx);
         localpart = xml.substring(1 + idx);
      }

      String namespaceUri = getUriFromContext(this.prefix, xml, nscontext, false);
      this.javaValue = new QName(namespaceUri, localpart);
   }

   private XSDQName(String xml, Map prefixToNamespaceMap) {
      this.xmlValue = xml;
      int idx = indexOfNamespaceSeperator(xml);
      String localpart;
      if (idx < 0) {
         this.prefix = "";
         localpart = xml;
      } else {
         this.prefix = xml.substring(0, idx);
         localpart = xml.substring(1 + idx);
      }

      String namespaceUri = getUriFromMap(this.prefix, xml, prefixToNamespaceMap, false);
      this.javaValue = new QName(namespaceUri, localpart);
   }

   private static String getUriFromContext(String prefix, String original_xml, NamespaceContext nscontext, boolean lax) {
      String uri = nscontext.getNamespaceURI(prefix);
      if (uri == null) {
         if (lax) {
            return null;
         } else {
            String msg = createBadPrefixMsg(prefix);
            throw new IllegalLexicalValueException(msg, original_xml, XML_TYPE_NAME);
         }
      } else {
         return uri;
      }
   }

   private static String createBadPrefixMsg(String prefix) {
      String msg = "unable to locate namespace URI for prefix \"" + prefix + "\"";
      return msg;
   }

   private static String getUriFromMap(String prefix, String original_xml, Map prefixToNamespaceMap, boolean lax) {
      Object uri = prefixToNamespaceMap.get(prefix);
      String msg;
      if (uri == null) {
         if (lax) {
            return null;
         } else {
            msg = createBadPrefixMsg(prefix);
            throw new IllegalLexicalValueException(msg, original_xml, XML_TYPE_NAME);
         }
      } else if (!(uri instanceof String)) {
         msg = "supplied prefixToNamespaceMap found  non string object for prefix \"" + prefix + "\": " + uri;
         throw new IllegalLexicalValueException(msg, original_xml, XML_TYPE_NAME);
      } else {
         return (String)uri;
      }
   }

   private XSDQName(String xml, String namespaceUri) {
      this.xmlValue = xml;
      int idx = indexOfNamespaceSeperator(xml);
      if (idx < 0) {
         this.prefix = "";
         this.javaValue = new QName(xml);
      } else {
         this.prefix = xml.substring(0, idx);
         String localpart = xml.substring(1 + idx);
         this.javaValue = new QName(namespaceUri, localpart);
      }

   }

   private static int indexOfNamespaceSeperator(String xml) {
      int idx = xml.indexOf(58);
      if (idx == 0) {
         String msg = TypeUtils.createInvalidArgMsg(xml, XML_TYPE_NAME) + " - an empty prefix is not allowed in xml namespaces";
         throw new IllegalLexicalValueException(msg, xml, XML_TYPE_NAME);
      } else {
         int second_sep = xml.indexOf(58, idx + 1);
         if (second_sep >= 0) {
            String msg = TypeUtils.createInvalidArgMsg(xml, XML_TYPE_NAME) + " - extra namespace separator found at character " + second_sep;
            throw new IllegalLexicalValueException(msg, xml, XML_TYPE_NAME);
         } else {
            return idx;
         }
      }
   }

   private XSDQName(QName qname, String prefix) {
      this.javaValue = qname;
      this.prefix = prefix;
      this.xmlValue = prefix + ':' + qname.getLocalPart();
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return this.xmlValue;
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public Object getJavaObject() {
      return this.javaValue;
   }

   public QName getQName() {
      return this.javaValue;
   }

   public static QName convertXml(String xsd_qname, String namespaceUri) {
      return convertXml(xsd_qname, namespaceUri, false);
   }

   public static QName convertXml(String xsd_qname, String namespaceUri, boolean lax) {
      if (xsd_qname == null) {
         throw new NullPointerException("qname cannot be null");
      } else if (namespaceUri == null && !lax) {
         throw new IllegalArgumentException("namespaceUri cannot be null");
      } else {
         return new QName(namespaceUri, getLocalPartFromQName(xsd_qname));
      }
   }

   public static QName convertXml(String xsd_qname, Map prefixToNamespaceMap) {
      return convertXml(xsd_qname, prefixToNamespaceMap, false);
   }

   public static QName convertXml(String xsd_qname, Map prefixToNamespaceMap, boolean lax) {
      if (xsd_qname == null) {
         throw new NullPointerException("qname cannot be null");
      } else if (prefixToNamespaceMap == null) {
         throw new NullPointerException("prefixToNamespaceMap cannot be null");
      } else {
         int idx = indexOfNamespaceSeperator(xsd_qname);
         String prefix;
         String localpart;
         if (idx < 0) {
            prefix = "";
            localpart = xsd_qname;
         } else {
            prefix = xsd_qname.substring(0, idx);
            localpart = xsd_qname.substring(1 + idx);
         }

         String namespaceUri = getUriFromMap(prefix, xsd_qname, prefixToNamespaceMap, lax);
         return new QName(namespaceUri, localpart, prefix);
      }
   }

   public static QName convertXml(String xsd_qname, NamespaceContext nscontext) {
      return convertXml(xsd_qname, nscontext, false);
   }

   public static QName convertXml(String xsd_qname, NamespaceContext nscontext, boolean lax) {
      if (xsd_qname == null) {
         throw new NullPointerException("qname cannot be null");
      } else if (nscontext == null) {
         throw new NullPointerException("nscontext cannot be null");
      } else {
         int idx = indexOfNamespaceSeperator(xsd_qname);
         String prefix;
         String localpart;
         if (idx < 0) {
            prefix = "";
            localpart = xsd_qname;
         } else {
            prefix = xsd_qname.substring(0, idx);
            localpart = xsd_qname.substring(1 + idx);
         }

         String namespaceUri = getUriFromContext(prefix, xsd_qname, nscontext, lax);
         return new QName(namespaceUri, localpart, prefix);
      }
   }

   public static String getPrefixFromQName(String qname) {
      int idx = indexOfNamespaceSeperator(qname);
      return idx < 0 ? "" : qname.substring(0, idx);
   }

   public static String getLocalPartFromQName(String qname) {
      int idx = indexOfNamespaceSeperator(qname);
      if (idx < 0) {
         return qname;
      } else {
         ++idx;
         return qname.substring(idx);
      }
   }

   public static void validateXml(String xsd_qname, boolean check_xml_char) {
      indexOfNamespaceSeperator(xsd_qname);
      if (check_xml_char) {
         TypeUtils.validateXml(xsd_qname, XML_TYPE_NAME, check_xml_char);
      }

   }

   public static String getXml(QName qname, String prefix) {
      return getXml(qname.getNamespaceURI(), qname.getLocalPart(), prefix);
   }

   public static String getXml(QName qname, NamespaceContext nsContext) {
      String uri = qname.getNamespaceURI();
      String prefix = nsContext.getPrefix(uri);
      if (prefix == null) {
         throw new IllegalArgumentException("NamespaceContext does not provide prefix for namespaceURI " + uri);
      } else {
         return getXml(uri, qname.getLocalPart(), prefix);
      }
   }

   public static String getXml(QName qname) {
      return getXml(qname.getNamespaceURI(), qname.getLocalPart(), qname.getPrefix());
   }

   public static String getCanonicalXml(QName qname, String prefix) {
      return getXml(qname, prefix);
   }

   public static String getXml(String uri, String localpart, String prefix) {
      return uri != null && prefix != null && prefix.length() > 0 ? prefix + ':' + localpart : localpart;
   }

   public String toString() {
      return this.javaValue.toString();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof XSDQName) {
         XSDQName other = (XSDQName)obj;
         return this.javaValue.equals(other.javaValue);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}
