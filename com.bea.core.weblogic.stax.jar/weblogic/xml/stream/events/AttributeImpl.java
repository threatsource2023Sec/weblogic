package weblogic.xml.stream.events;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.XMLName;

/** @deprecated */
@Deprecated
public class AttributeImpl implements Attribute {
   protected XMLName name;
   protected String value;
   protected String type;
   protected XMLName schemaType;

   public AttributeImpl(String localName, String value) {
      this.name = new Name(localName);
      this.value = value;
      this.type = "CDATA";
   }

   public AttributeImpl(String namespaceUri, String localName, String value) {
      this.name = new Name(namespaceUri, localName);
      this.value = value;
      this.type = "CDATA";
   }

   public AttributeImpl(XMLName name, String value, String type) {
      this.name = name;
      this.value = value;
      this.type = type;
   }

   public AttributeImpl(XMLName name, String value, String type, XMLName schemaType) {
      this.name = name;
      this.value = value;
      this.type = type;
      this.schemaType = schemaType;
   }

   public XMLName getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String v) {
      this.value = v;
   }

   public String getType() {
      return this.type;
   }

   public XMLName getSchemaType() {
      return null;
   }

   public String toString() {
      return " " + this.name + "=\"" + this.value + "\"";
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof Attribute)) {
         return false;
      } else {
         Attribute attribute = (Attribute)obj;
         return this.name.equals(attribute.getName()) && this.value.equals(attribute.getValue());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
      return result;
   }
}
