package com.bea.xml.stream.events;

import com.bea.xml.stream.util.ElementTypeNames;
import java.io.Writer;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class BaseEvent implements XMLEvent, Location {
   private int eventType = -1;
   private int line = -1;
   private int column = -1;
   private int characterOffset = 0;
   private String locationURI;

   public BaseEvent() {
   }

   public BaseEvent(int type) {
      this.eventType = type;
   }

   public int getEventType() {
      return this.eventType;
   }

   protected void setEventType(int type) {
      this.eventType = type;
   }

   public String getTypeAsString() {
      return ElementTypeNames.getEventTypeString(this.eventType);
   }

   public boolean isStartElement() {
      return this.eventType == 1;
   }

   public boolean isEndElement() {
      return this.eventType == 2;
   }

   public boolean isEntityReference() {
      return this.eventType == 9;
   }

   public boolean isProcessingInstruction() {
      return this.eventType == 3;
   }

   public boolean isCharacters() {
      return this.eventType == 4;
   }

   public boolean isStartDocument() {
      return this.eventType == 7;
   }

   public boolean isEndDocument() {
      return this.eventType == 8;
   }

   public boolean isAttribute() {
      return this.eventType == 10;
   }

   public boolean isNamespace() {
      return this.eventType == 13;
   }

   public Location getLocation() {
      return this;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }

   public String getSourceName() {
      return null;
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

   public StartElement asStartElement() {
      return (StartElement)this;
   }

   public EndElement asEndElement() {
      return (EndElement)this;
   }

   public Characters asCharacters() {
      return (Characters)this;
   }

   public void recycle() {
   }

   public QName getSchemaType() {
      return null;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
   }
}
