package org.apache.openjpa.lib.meta;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLVersionParser extends XMLMetaDataParser {
   public static final String VERSION_1_0 = "1.0";
   public static final String VERSION_2_0 = "2.0";
   private static final String VERSION_ATTR = "version";
   private static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
   private static final String SCHEMA_LOCATION = "schemaLocation";
   private String _rootElement;
   private String _version;
   private String _schemaLocation;

   public XMLVersionParser(String rootElement) {
      this._rootElement = rootElement;
      this.setCaching(false);
      this.setValidating(false);
      this.setParseText(false);
   }

   protected void endElement(String name) throws SAXException {
   }

   protected boolean startElement(String name, Attributes attrs) throws SAXException {
      if (name.equals(this._rootElement)) {
         this._version = attrs.getValue("", "version");
         this._schemaLocation = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation");
         this.ignoreContent(true);
      }

      return false;
   }

   public String getVersion() {
      return this._version;
   }

   public String getSchemaLocation() {
      return this._schemaLocation;
   }
}
