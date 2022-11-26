package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xml.XmlException;
import org.w3c.dom.Node;

public class XmlBeanTypeUnmarshaller extends XmlBeanBaseUnmarshaller implements TypeUnmarshaller {
   private final XmlBeanTypeRuntimeBindingType type;

   public XmlBeanTypeUnmarshaller(XmlBeanTypeRuntimeBindingType rtt) {
      this.type = rtt;
   }

   public Object unmarshal(UnmarshalResult result, Node node, Class javaClass) throws XmlException {
      Node xmlFragment = XmlBeanUtil.createXMLFragmentFromElement(node);
      return this.commonXmlBeanUnmarshal(xmlFragment, javaClass);
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
