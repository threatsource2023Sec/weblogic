package weblogic.xml.util.xed;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.events.AttributeImpl;

public class AttributeVariable extends Variable {
   Attribute attribute;

   public AttributeVariable(String name, String value) {
      super(name);
      this.attribute = new AttributeImpl(name, value);
   }

   public Object evaluate(Context context) throws StreamEditorException {
      return this.attribute;
   }

   public String toString() {
      return this.attribute.toString();
   }
}
