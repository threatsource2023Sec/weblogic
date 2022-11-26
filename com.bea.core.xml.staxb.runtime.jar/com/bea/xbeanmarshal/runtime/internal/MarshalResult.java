package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlTypeName;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

abstract class MarshalResult {
   private static final boolean VERBOSE = Verbose.isVerbose(MarshalResult.class);
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private final boolean walkTypes = true;
   private static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";

   MarshalResult(BindingLoader loader, RuntimeBindingTypeTable tbl) throws XmlException {
      this.bindingLoader = loader;
      this.typeTable = tbl;
   }

   final RuntimeBindingType determineRuntimeBindingType(RuntimeBindingType expected, Object instance) throws XmlException {
      if (expected instanceof XmlBeanAnyRuntimeBindingType) {
         return expected;
      } else if (instance != null && expected.canHaveSubtype()) {
         Class instance_class = instance.getClass();
         if (instance_class.equals(expected.getJavaType())) {
            return expected;
         } else {
            BindingTypeName type_name = expected.getBindingType().getName();
            if (!type_name.getJavaName().isNameForClass(instance_class)) {
               BindingType actual_type = MarshallerImpl.lookupBindingType(instance_class, type_name.getJavaName(), this.bindingLoader, true, (QName)null);
               if (actual_type != null) {
                  return this.typeTable.createRuntimeType(actual_type, this.bindingLoader);
               }
            }

            return expected;
         }
      } else {
         return expected;
      }
   }

   RuntimeBindingType createRuntimeBindingType(BindingTypeName type_name) throws XmlException {
      BindingType btype = this.bindingLoader.getBindingType(type_name);
      if (btype == null) {
         throw new XmlException("unable to load type " + type_name);
      } else {
         return this.typeTable.createRuntimeType(btype, this.bindingLoader);
      }
   }

   protected void serializeNullXmlObject(Document document, Node parent, Object argToSerialize, QName argToSerializeElementQName) throws XmlException {
      if (argToSerializeElementQName != null && !argToSerializeElementQName.equals(XmlTypeName.ANY_ELEMENT_WILDCARD_ELEMENT_NAME)) {
         if (VERBOSE) {
            Verbose.log(" serializeNullXmlObject:  marshal xsi:nil='true' for element '" + argToSerializeElementQName + "'");
         }

         String elementNamespaceURI = argToSerializeElementQName.getNamespaceURI();
         String elementLocalName = argToSerializeElementQName.getLocalPart();
         String elementPrefix = argToSerializeElementQName.getPrefix();
         String qualifiedName = elementLocalName;
         if (elementPrefix != null && elementPrefix.length() > 0) {
            qualifiedName = elementPrefix + ":" + elementLocalName;
         }

         Element nilElement = document.createElementNS(elementNamespaceURI, qualifiedName);
         nilElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:nil", "true");
         document.importNode(nilElement, true);
         parent.appendChild(nilElement);
         if (VERBOSE) {
            Verbose.log(" serializeNullXmlObject:  after serialization parent is \n" + XmlBeanUtil.toXMLString(parent) + "\n");
         }

      }
   }
}
