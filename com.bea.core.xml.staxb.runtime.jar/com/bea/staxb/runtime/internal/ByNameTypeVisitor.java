package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.ArrayUtils;
import com.bea.xml.XmlException;
import java.util.Iterator;

final class ByNameTypeVisitor extends NamedXmlTypeVisitor {
   private final ByNameRuntimeBindingType type = (ByNameRuntimeBindingType)this.getActualRuntimeBindingType();
   private final int maxElementPropCount;
   private final int maxAttributePropCount;
   private int elemPropIdx = -1;
   private Iterator currMultipleIterator;
   private Object currMultipleItem;
   private boolean haveMultipleItem;

   ByNameTypeVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result) throws XmlException {
      super(obj, property, result);
      this.maxElementPropCount = obj == null ? 0 : this.type.getElementPropertyCount();
      this.maxAttributePropCount = obj == null ? 0 : this.type.getAttributePropertyCount();
   }

   protected int getState() {
      assert this.elemPropIdx <= this.maxElementPropCount;

      if (this.elemPropIdx < 0) {
         return 1;
      } else {
         return this.elemPropIdx >= this.maxElementPropCount ? 4 : 2;
      }
   }

   protected int advance() throws XmlException {
      assert this.elemPropIdx < this.maxElementPropCount;

      do {
         boolean hit_end = this.advanceToNextItem();
         if (hit_end) {
            return 4;
         }
      } while(!this.currentPropHasMore());

      assert this.elemPropIdx >= 0;

      return this.getState();
   }

   private boolean advanceToNextItem() throws XmlException {
      if (this.haveMultipleItem && this.currMultipleIterator.hasNext()) {
         this.currMultipleItem = this.currMultipleIterator.next();
         this.haveMultipleItem = true;
         return false;
      } else {
         return this.advanceToNextProperty();
      }
   }

   private boolean advanceToNextProperty() throws XmlException {
      ++this.elemPropIdx;
      this.currMultipleIterator = null;
      this.haveMultipleItem = false;
      if (this.elemPropIdx >= this.maxElementPropCount) {
         return true;
      } else {
         this.updateCurrIterator();
         return false;
      }
   }

   private void updateCurrIterator() throws XmlException {
      RuntimeBindingProperty property = this.getCurrentElementProperty();
      if (property.isMultiple()) {
         Object parent = this.getParentObject();
         Object prop_obj = property.isSet(parent) ? property.getValue(parent) : null;
         Iterator itr = ArrayUtils.getCollectionIterator(prop_obj);
         this.currMultipleIterator = itr;
         if (itr.hasNext()) {
            this.currMultipleItem = itr.next();
            this.haveMultipleItem = true;
         } else {
            this.haveMultipleItem = false;
         }
      }

   }

   private boolean currentPropHasMore() throws XmlException {
      if (this.elemPropIdx < 0) {
         return false;
      } else if (this.haveMultipleItem) {
         return this.currMultipleItem != null ? true : this.getCurrentElementProperty().isNillable();
      } else if (this.currMultipleIterator != null) {
         return false;
      } else {
         RuntimeBindingProperty property = this.getCurrentElementProperty();
         return property.isSet(this.getParentObject());
      }
   }

   public XmlTypeVisitor getCurrentChild() throws XmlException {
      RuntimeBindingProperty property = this.getCurrentElementProperty();
      if (this.haveMultipleItem) {
         return this.marshalResult.createVisitor(property, this.currMultipleItem);
      } else {
         Object prop_obj = property.getValue(this.getParentObject());
         return this.marshalResult.createVisitor(property, prop_obj);
      }
   }

   private RuntimeBindingProperty getCurrentElementProperty() {
      RuntimeBindingProperty property = this.type.getElementProperty(this.elemPropIdx);

      assert property != null;

      return property;
   }

   protected void initAttributes() throws XmlException {
      initAttributesInternal(this, this.type, this.maxAttributePropCount, this.marshalResult);
   }

   static void initAttributesInternal(NamedXmlTypeVisitor typeVisitor, AttributeRuntimeBindingType rtt, int maxAttributePropCount, PullMarshalResult marshalResult) throws XmlException {
      Object parent = typeVisitor.getParentObject();
      if (parent == null) {
         marshalResult.addXsiNilAttribute();
         if (typeVisitor.needsXsiType()) {
            marshalResult.addXsiTypeAttribute(rtt);
         }
      } else {
         if (typeVisitor.needsXsiType()) {
            marshalResult.addXsiTypeAttribute(rtt);
         }

         int i = 0;

         for(int len = maxAttributePropCount; i < len; ++i) {
            RuntimeBindingProperty prop = rtt.getAttributeProperty(i);
            if (prop.isSet(parent)) {
               Object value = prop.getValue(parent);
               CharSequence val = prop.getLexical(value, marshalResult);
               if (val != null) {
                  marshalResult.fillAndAddAttribute(prop.getName(), val.toString());
               }
            }
         }
      }

   }

   protected CharSequence getCharData() {
      throw new IllegalStateException("not text: " + this);
   }
}
