package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import org.w3c.dom.Node;

final class LiteralUnmarshalResult extends UnmarshalResult {
   LiteralUnmarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable) {
      super(bindingLoader, typeTable);
   }

   void extractAndFillElementProp(RuntimeBindingProperty prop, Object inter, Node sourceNode, Class targetJavaClass) throws XmlException {
      try {
         RuntimeBindingType actual_rtt = prop.getRuntimeBindingType().determineActualRuntimeType(this);
         Object this_val = this.unmarshalElementProperty(prop, inter, actual_rtt, sourceNode, targetJavaClass);
         prop.fill(inter, this_val);
      } catch (InvalidLexicalValueException var7) {
      }

   }

   protected Object unmarshalElementProperty(RuntimeBindingProperty prop, Object inter, RuntimeBindingType actual_rtt, Node sourceNode, Class targetJavaClass) throws XmlException {
      Object this_val = null;
      if (this.hasXsiNil(sourceNode)) {
         this_val = NullUnmarshaller.getInstance().unmarshal(this, sourceNode, targetJavaClass);
      } else {
         this_val = actual_rtt.getUnmarshaller().unmarshal(this, sourceNode, targetJavaClass);
      }

      return this_val;
   }
}
