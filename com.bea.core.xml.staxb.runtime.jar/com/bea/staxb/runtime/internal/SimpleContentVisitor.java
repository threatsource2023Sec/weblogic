package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

abstract class SimpleContentVisitor extends NamedXmlTypeVisitor {
   private final CharSequence chars;
   private int state = 1;

   public SimpleContentVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result) throws XmlException {
      super(obj, property, result);
      if (obj == null) {
         this.chars = null;
      } else {
         this.chars = property.getLexical(obj, this.marshalResult);
      }

   }

   protected int getState() {
      return this.state;
   }

   protected int advance() throws XmlException {
      byte newstate;
      switch (this.state) {
         case 1:
            if (this.getParentObject() == null) {
               newstate = 4;
            } else {
               newstate = 3;
            }
            break;
         case 3:
            newstate = 4;
            break;
         default:
            throw new AssertionError("invalid state: " + this.state);
      }

      this.state = newstate;
      return newstate;
   }

   public XmlTypeVisitor getCurrentChild() throws XmlException {
      assert this.state == 3;

      return this;
   }

   protected CharSequence getCharData() {
      assert this.state == 3;

      assert this.getParentObject() != null;

      return this.chars;
   }
}
