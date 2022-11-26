package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;
import java.lang.reflect.Array;

class SoapArrayTypeVisitor extends NamedXmlTypeVisitor {
   private final SoapArrayRuntimeBindingType type = (SoapArrayRuntimeBindingType)this.getActualRuntimeBindingType();
   private final int arrayLength;
   private int currIndex = -1;

   SoapArrayTypeVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result) throws XmlException {
      super(obj, property, result);
      this.arrayLength = getArrayLength(obj);
   }

   private static int getArrayLength(Object obj) {
      return obj == null ? 0 : Array.getLength(obj);
   }

   protected int getState() {
      assert this.currIndex <= this.arrayLength;

      if (this.currIndex < 0) {
         return 1;
      } else {
         return this.currIndex >= this.arrayLength ? 4 : 2;
      }
   }

   protected int advance() throws XmlException {
      assert this.currIndex < this.arrayLength;

      do {
         ++this.currIndex;
         if (this.currIndex == this.arrayLength) {
            return 4;
         }
      } while(!this.currentItemHasValue());

      assert this.currIndex >= 0;

      assert this.getState() == 2;

      return 2;
   }

   private boolean currentItemHasValue() throws XmlException {
      throw new AssertionError("soap arrays in pull mode unimplemented");
   }

   private Object getCurrentValue() throws XmlException {
      throw new AssertionError("soap arrays in pull mode unimplemented");
   }

   public XmlTypeVisitor getCurrentChild() throws XmlException {
      Object value = this.getCurrentValue();
      return this.marshalResult.createVisitor(this.type.getElementProperty(), value);
   }

   protected CharSequence getCharData() {
      throw new IllegalStateException("not text: " + this);
   }

   protected void initAttributes() throws XmlException {
      if (this.getParentObject() == null) {
         this.marshalResult.addXsiNilAttribute();
         if (this.needsXsiType()) {
            this.marshalResult.addXsiTypeAttribute(this.getActualRuntimeBindingType());
         }
      } else if (this.needsXsiType()) {
         this.marshalResult.addXsiTypeAttribute(this.getActualRuntimeBindingType());
      }

   }
}
