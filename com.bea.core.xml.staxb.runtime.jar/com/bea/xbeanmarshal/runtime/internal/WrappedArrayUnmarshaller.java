package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.XmlException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WrappedArrayUnmarshaller implements TypeUnmarshaller {
   private static final boolean VERBOSE = Verbose.isVerbose(WrappedArrayUnmarshaller.class);
   private final WrappedArrayRuntimeBindingType type;

   public WrappedArrayUnmarshaller(WrappedArrayRuntimeBindingType rtt) {
      this.type = rtt;
   }

   public Object unmarshal(UnmarshalResult result, Node node, Class javaClass) throws XmlException {
      if (XmlBeanUtil.hasXsiNil(node)) {
         if (VERBOSE) {
            Verbose.log(" array element has xsi:nil set.  Returning NULL result for java array.");
         }

         return null;
      } else {
         Object inter = this.type.createIntermediary(result);
         this.unmarshalIntoIntermediary(inter, result, node, javaClass);
         return this.type.getFinalObjectFromIntermediary(inter, result);
      }
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result, Node node, Class javaClass) throws XmlException {
      this.deserializeContents(intermediary, result, node, javaClass);
   }

   private void deserializeContents(Object inter, UnmarshalResult context, Node node, Class javaClass) throws XmlException {
      if (VERBOSE) {
         Verbose.log(" deserializeContents for element: " + XmlBeanUtil.toXMLString(node) + "\n    javaClass: '" + javaClass.getName() + "\n");
      }

      NodeList nl = node.getChildNodes();
      int nlLength = nl.getLength();
      if (nlLength <= 0) {
         throw new XmlException(" Unexpected error while deserializing XmlBean array. The array element \n" + XmlBeanUtil.toXMLString(node) + "\n is not null, but has no array elements. ");
      } else {
         String elementNamespaceURI = node.getNamespaceURI();
         String elementLocalName = node.getLocalName();
         WrappedArrayRuntimeBindingType.ItemProperty elem_prop = this.type.getElementProperty();

         for(int i = 0; i < nlLength; ++i) {
            Node arrayNode = nl.item(i);
            String arrayElementNamespaceURI = arrayNode.getNamespaceURI();
            String arrayElementLocalName = arrayNode.getLocalName();
            if (arrayElementLocalName != null) {
               if (VERBOSE) {
                  Verbose.log(" ------   unmarshal array item element " + i + ":\n" + XmlBeanUtil.toXMLString(arrayNode) + "\n");
               }

               context.extractAndFillElementProp(elem_prop, inter, arrayNode, elem_prop.getElementClass());
            }
         }

         if (VERBOSE) {
            Verbose.log("\n  WRAPPED Array Unmarshal DONE. \n");
         }

      }
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

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
   }
}
