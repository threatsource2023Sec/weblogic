package weblogic.xml.stream.events;

import weblogic.xml.stream.Location;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLName;

/** @deprecated */
@Deprecated
public class ElementEvent implements XMLEvent, Cloneable {
   protected int type;
   protected XMLName name = null;
   protected Location location = null;
   protected String content = null;
   protected XMLName schemaType = null;

   public ElementEvent() {
      this.init();
   }

   public ElementEvent(int type, XMLName name) {
      this.init();
      this.type = type;
      this.name = name;
   }

   protected void init() {
      this.type = 1;
   }

   public int getType() {
      return this.type;
   }

   protected void setType(int type) {
      this.type = type;
   }

   public void setName(XMLName name) {
      this.name = name;
   }

   public XMLName getName() {
      return this.name;
   }

   public boolean hasName() {
      return this.name != null;
   }

   public Location getLocation() {
      return (Location)(this.location == null ? new LocationImpl(0, 0) : this.location);
   }

   public String getTypeAsString() {
      return ElementTypeNames.getName(this.type);
   }

   public boolean isStartElement() {
      return (this.type & 2) != 0;
   }

   public boolean isEndElement() {
      return (this.type & 4) != 0;
   }

   public boolean isEntityReference() {
      return (this.type & 8192) != 0;
   }

   public boolean isStartPrefixMapping() {
      return (this.type & 1024) != 0;
   }

   public boolean isEndPrefixMapping() {
      return (this.type & 2048) != 0;
   }

   public boolean isChangePrefixMapping() {
      return (this.type & 4096) != 0;
   }

   public boolean isProcessingInstruction() {
      return (this.type & 8) != 0;
   }

   public boolean isCharacterData() {
      return (this.type & 16) != 0;
   }

   public boolean isSpace() {
      return (this.type & 64) != 0;
   }

   public boolean isNull() {
      return (this.type & 128) != 0;
   }

   public boolean isStartDocument() {
      return (this.type & 256) != 0;
   }

   public boolean isEndDocument() {
      return (this.type & 512) != 0;
   }

   public XMLName getSchemaType() {
      return this.schemaType;
   }

   public void setSchemaType(XMLName type) {
      this.schemaType = type;
   }

   protected static boolean compare(String one, String two) {
      return one == null ? two == null : one.equals(two);
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public boolean equals(Object obj) {
      if (!this.hasName() && obj == null) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof XMLEvent)) {
         return false;
      } else {
         XMLEvent element = (XMLEvent)obj;
         return this.name.equals(element.getName());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }
}
