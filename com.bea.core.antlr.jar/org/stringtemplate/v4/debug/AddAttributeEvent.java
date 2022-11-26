package org.stringtemplate.v4.debug;

public class AddAttributeEvent extends ConstructionEvent {
   String name;
   Object value;

   public AddAttributeEvent(String name, Object value) {
      this.name = name;
      this.value = value;
   }

   public String toString() {
      return "addEvent{, name='" + this.name + '\'' + ", value=" + this.value + ", location=" + this.getFileName() + ":" + this.getLine() + '}';
   }
}
