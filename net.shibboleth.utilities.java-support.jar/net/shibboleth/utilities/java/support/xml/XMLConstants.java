package net.shibboleth.utilities.java.support.xml;

import javax.xml.namespace.QName;

public final class XMLConstants {
   public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
   public static final String XML_PREFIX = "xml";
   public static final QName XML_BASE_ATTRIB_NAME = new QName("http://www.w3.org/XML/1998/namespace", "base", "xml");
   public static final QName XML_ID_ATTRIB_NAME = new QName("http://www.w3.org/XML/1998/namespace", "id", "xml");
   public static final QName XML_LANG_ATTRIB_NAME = new QName("http://www.w3.org/XML/1998/namespace", "lang", "xml");
   public static final QName XML_SPACE_ATTRIB_NAME = new QName("http://www.w3.org/XML/1998/namespace", "space", "xml");
   public static final String LIST_DELIMITERS = " \n\r\t";
   public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
   public static final String XMLNS_PREFIX = "xmlns";
   public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
   public static final String XSD_PREFIX = "xsd";
   public static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
   public static final String XSI_PREFIX = "xsi";
   public static final QName XSI_TYPE_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");
   public static final QName XSI_SCHEMA_LOCATION_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "xsi");
   public static final QName XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", "xsi");
   public static final QName XSI_NIL_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi");

   private XMLConstants() {
   }
}
