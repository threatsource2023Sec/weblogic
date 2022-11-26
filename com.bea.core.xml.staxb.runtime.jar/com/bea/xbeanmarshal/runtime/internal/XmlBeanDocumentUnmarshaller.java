package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import org.w3c.dom.Node;

public class XmlBeanDocumentUnmarshaller extends XmlBeanBaseUnmarshaller implements TypeUnmarshaller {
   private final XmlBeanDocumentRuntimeBindingType type;

   public XmlBeanDocumentUnmarshaller(XmlBeanDocumentRuntimeBindingType rtt) {
      this.type = rtt;
   }

   public Object unmarshal(UnmarshalResult result, Node node, Class javaClass) throws XmlException {
      return this.commonXmlBeanUnmarshal(node, javaClass);
   }

   public Object unmarshalAttribute(UnmarshalResult result) throws XmlException {
      throw new AssertionError("not used");
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      throw new AssertionError("not used");
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new AssertionError("not used");
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result, Node node, Class javaClass) throws XmlException {
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
   }
}
