package org.apache.xmlbeans.xml.stream;

public interface XMLEvent {
   int XML_EVENT = 1;
   int START_ELEMENT = 2;
   int END_ELEMENT = 4;
   int PROCESSING_INSTRUCTION = 8;
   int CHARACTER_DATA = 16;
   int COMMENT = 32;
   int SPACE = 64;
   int NULL_ELEMENT = 128;
   int START_DOCUMENT = 256;
   int END_DOCUMENT = 512;
   int START_PREFIX_MAPPING = 1024;
   int END_PREFIX_MAPPING = 2048;
   int CHANGE_PREFIX_MAPPING = 4096;
   int ENTITY_REFERENCE = 8192;

   int getType();

   XMLName getSchemaType();

   String getTypeAsString();

   XMLName getName();

   boolean hasName();

   Location getLocation();

   boolean isStartElement();

   boolean isEndElement();

   boolean isEntityReference();

   boolean isStartPrefixMapping();

   boolean isEndPrefixMapping();

   boolean isChangePrefixMapping();

   boolean isProcessingInstruction();

   boolean isCharacterData();

   boolean isSpace();

   boolean isNull();

   boolean isStartDocument();

   boolean isEndDocument();
}
