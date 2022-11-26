package weblogic.xml.xmlnode;

import java.util.Map;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.Location;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLName;

public class StreamNode implements StartElement {
   private XMLNode node;

   public AttributeIterator getNamespaces() {
      throw new Error("NYI");
   }

   public XMLName getName() {
      throw new Error("NYI");
   }

   public Location getLocation() {
      throw new Error("NYI");
   }

   public AttributeIterator getAttributes() {
      throw new Error("NYI");
   }

   public AttributeIterator getAttributesAndNamespaces() {
      throw new Error("NYI");
   }

   public Attribute getAttributeByName(XMLName name) {
      throw new Error("NYI");
   }

   public XMLName getSchemaType() {
      throw new Error("NYI");
   }

   public boolean isStartElement() {
      return true;
   }

   public boolean isStartPrefixMapping() {
      return false;
   }

   public boolean isEntityReference() {
      return false;
   }

   public boolean isNull() {
      return false;
   }

   public String getTypeAsString() {
      throw new Error("NYI");
   }

   public int getType() {
      throw new Error("NYI");
   }

   public String getNamespaceUri(String prefix) {
      throw new Error("NYI");
   }

   public Map getNamespaceMap() {
      throw new Error("NYI");
   }

   public boolean hasName() {
      return true;
   }

   public boolean isChangePrefixMapping() {
      return false;
   }

   public boolean isProcessingInstruction() {
      return false;
   }

   public boolean isEndPrefixMapping() {
      return false;
   }

   public boolean isSpace() {
      return false;
   }

   public boolean isEndDocument() {
      return false;
   }

   public boolean isStartDocument() {
      return false;
   }

   public boolean isEndElement() {
      return true;
   }

   public boolean isCharacterData() {
      return false;
   }
}
