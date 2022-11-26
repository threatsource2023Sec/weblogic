package com.bea.xml.stream;

import java.io.Writer;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

public class AttributeBase implements Attribute, Location {
   private String value;
   private QName name;
   private QName attributeType;
   private String locationURI;
   private int eventType = -1;
   private int line = -1;
   private int column = -1;
   private int characterOffset = 0;

   public AttributeBase(String prefix, String namespaceURI, String localName, String value, String attributeType) {
      if (prefix == null) {
         prefix = "";
      }

      this.name = new QName(namespaceURI, localName, prefix);
      this.value = value;
      this.attributeType = new QName(attributeType);
   }

   public AttributeBase(String prefix, String localName, String value) {
      if (prefix == null) {
         prefix = "";
      }

      this.name = new QName("", localName, prefix);
      this.value = value;
   }

   public AttributeBase(QName name, String value) {
      this.name = name;
      this.value = value;
   }

   public String toString() {
      return this.name.getPrefix() != null && !this.name.getPrefix().equals("") ? "['" + this.name.getNamespaceURI() + "']:" + this.name.getPrefix() + ":" + this.name.getLocalPart() + "='" + this.value + "'" : this.name.getLocalPart() + "='" + this.value + "'";
   }

   public int getLineNumber() {
      return this.line;
   }

   public void setLineNumber(int line) {
      this.line = line;
   }

   public int getColumnNumber() {
      return this.column;
   }

   public void setColumnNumber(int col) {
      this.column = col;
   }

   public int getCharacterOffset() {
      return this.characterOffset;
   }

   public void setCharacterOffset(int c) {
      this.characterOffset = c;
   }

   public String getLocationURI() {
      return this.locationURI;
   }

   public void setLocationURI(String uri) {
      this.locationURI = uri;
   }

   public int getEventType() {
      return 10;
   }

   public boolean hasName() {
      return this.name != null;
   }

   public QName getName() {
      return this.name;
   }

   public boolean isNamespaceDeclaration() {
      return false;
   }

   public String getLocalName() {
      return this.name.getLocalPart();
   }

   public String getValue() {
      return this.value;
   }

   public String getDTDType() {
      return "CDATA";
   }

   public String getNamespaceURI() {
      return this.name.getNamespaceURI();
   }

   public void setNamespaceURI(String uri) {
      this.name = new QName(uri, this.name.getLocalPart());
   }

   public boolean isSpecified() {
      return false;
   }

   public boolean isStartElement() {
      return false;
   }

   public boolean isEndElement() {
      return false;
   }

   public boolean isEntityReference() {
      return false;
   }

   public boolean isProcessingInstruction() {
      return false;
   }

   public boolean isCharacters() {
      return false;
   }

   public boolean isAttribute() {
      return false;
   }

   public boolean isNamespace() {
      return false;
   }

   public boolean isStartDocument() {
      return false;
   }

   public boolean isEndDocument() {
      return false;
   }

   public boolean isEndEntity() {
      return false;
   }

   public boolean isStartEntity() {
      return false;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }

   public Location getLocation() {
      return this;
   }

   public StartElement asStartElement() {
      throw new ClassCastException("cannnot cast AttributeBase to StartElement");
   }

   public EndElement asEndElement() {
      throw new ClassCastException("cannnot cast AttributeBase to EndElement");
   }

   public Characters asCharacters() {
      throw new ClassCastException("cannnot cast AttributeBase to Characters");
   }

   public void recycle() {
   }

   public boolean isDefault() {
      return true;
   }

   public String getSourceName() {
      return null;
   }

   public QName getSchemaType() {
      return null;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
   }
}
