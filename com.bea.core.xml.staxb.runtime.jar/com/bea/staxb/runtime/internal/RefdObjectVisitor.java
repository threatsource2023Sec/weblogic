package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

abstract class RefdObjectVisitor extends NamedXmlTypeVisitor {
   protected final int id;
   private int state = 1;

   public RefdObjectVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result, int id) throws XmlException {
      super(obj, property, result);

      assert obj != null;

      assert id >= 0;

      this.id = id;
   }

   protected int getState() {
      return this.state;
   }

   protected int advance() throws XmlException {
      if (this.state == 1) {
         return this.state = 4;
      } else {
         throw new AssertionError("invalid state: " + this.state);
      }
   }

   protected void initAttributes() throws XmlException {
      this.marshalResult.fillAndAddAttribute(this.getRefQName(), this.getRefValue());
   }

   protected abstract QName getRefQName();

   protected abstract String getRefValue();

   public XmlTypeVisitor getCurrentChild() throws XmlException {
      throw new AssertionError("no children");
   }

   protected CharSequence getCharData() {
      throw new AssertionError("no char data");
   }
}
