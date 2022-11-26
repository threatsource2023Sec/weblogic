package com.bea.xml.stream.events;

import javax.xml.stream.events.Characters;

public class CharactersEvent extends BaseEvent implements Characters {
   private String data;
   private boolean isCData = false;
   private boolean isSpace = false;
   private boolean isIgnorable = false;

   public CharactersEvent() {
      this.init();
   }

   public CharactersEvent(String data) {
      this.init();
      this.setData(data);
   }

   public CharactersEvent(String data, boolean isCData) {
      this.init();
      this.setData(data);
      this.isCData = isCData;
   }

   public void setSpace(boolean space) {
      this.isSpace = space;
   }

   public boolean isWhiteSpace() {
      return this.isSpace;
   }

   public boolean isIgnorableWhiteSpace() {
      return this.isIgnorable;
   }

   public void setIgnorable(boolean ignorable) {
      this.isIgnorable = ignorable;
   }

   protected void init() {
      this.setEventType(4);
   }

   public String getData() {
      return this.data;
   }

   public void setData(String data) {
      this.data = data;
   }

   public boolean hasData() {
      return this.data != null;
   }

   public boolean isCData() {
      return this.isCData;
   }

   public char[] getDataAsArray() {
      return this.data.toCharArray();
   }

   public String toString() {
      return this.isCData ? "<![CDATA[" + this.getData() + "]]>" : this.data;
   }
}
