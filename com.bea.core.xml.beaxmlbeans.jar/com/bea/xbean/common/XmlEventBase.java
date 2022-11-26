package com.bea.xbean.common;

import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.events.ElementTypeNames;

public abstract class XmlEventBase implements XMLEvent {
   private int _type;

   public XmlEventBase() {
   }

   public XmlEventBase(int type) {
      this._type = type;
   }

   public void setType(int type) {
      this._type = type;
   }

   public int getType() {
      return this._type;
   }

   public String getTypeAsString() {
      return ElementTypeNames.getName(this._type);
   }

   public boolean isStartElement() {
      return this._type == 2;
   }

   public boolean isEndElement() {
      return this._type == 4;
   }

   public boolean isEntityReference() {
      return this._type == 8192;
   }

   public boolean isStartPrefixMapping() {
      return this._type == 1024;
   }

   public boolean isEndPrefixMapping() {
      return this._type == 2048;
   }

   public boolean isChangePrefixMapping() {
      return this._type == 4096;
   }

   public boolean isProcessingInstruction() {
      return this._type == 8;
   }

   public boolean isCharacterData() {
      return this._type == 16;
   }

   public boolean isSpace() {
      return this._type == 64;
   }

   public boolean isNull() {
      return this._type == 128;
   }

   public boolean isStartDocument() {
      return this._type == 256;
   }

   public boolean isEndDocument() {
      return this._type == 512;
   }
}
