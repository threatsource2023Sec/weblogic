package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.common.PrefixResolver;
import com.bea.xml.XmlException;
import com.bea.xml.soap.SOAPArrayType;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

final class Soap11UnmarshalResult extends SoapUnmarshalResult {
   Soap11UnmarshalResult(BindingLoader loader, RuntimeBindingTypeTable typeTable, RefObjectTable refObjectTable, StreamRefNavigator refNavigator, UnmarshalOptions opts) {
      super(loader, typeTable, refObjectTable, refNavigator, opts);
   }

   protected String getReferencedIdFromAttributeValue(String attval) {
      String idstr = Soap11Constants.extractIdFromRef(attval);
      if (idstr == null) {
         throw new InvalidLexicalValueException("invalid reference", this.getLocation());
      } else {
         return idstr;
      }
   }

   protected String getIdFromAttributeValue(String attval) {
      return attval;
   }

   protected QName getIdAttributeName() {
      return Soap11Constants.ID_NAME;
   }

   protected QName getRefAttributeName() {
      return Soap11Constants.REF_NAME;
   }

   public SOAPArrayType extractSoapArrayType() {
      SOAPArrayType soapArrayType = null;

      try {
         soapArrayType = this.generateSoapArrayType();
      } catch (XmlException var3) {
      }

      if (soapArrayType == null) {
         String soap_array_str = this.baseReader.getAttributeValue(Soap11Constants.ARRAY_TYPE_NAME.getNamespaceURI(), Soap11Constants.ARRAY_TYPE_NAME.getLocalPart());
         if (soap_array_str == null) {
            soap_array_str = this.baseReader.getAttributeValue(Soap11Constants.SOAP12_ARRAY_TYPE_NAME.getNamespaceURI(), Soap11Constants.SOAP12_ARRAY_TYPE_NAME.getLocalPart());
            if (soap_array_str != null && this.baseReader.getAttributeValue(Soap11Constants.SOAP12_ARRAYSIZE.getNamespaceURI(), Soap11Constants.SOAP12_ARRAYSIZE.getLocalPart()) != null) {
               soap_array_str = soap_array_str + "[" + this.baseReader.getAttributeValue(Soap11Constants.SOAP12_ARRAYSIZE.getNamespaceURI(), Soap11Constants.SOAP12_ARRAYSIZE.getLocalPart()) + "]";
            } else {
               soap_array_str = null;
            }
         }

         if (soap_array_str == null) {
            return null;
         }

         soapArrayType = new SOAPArrayType(soap_array_str, this);
      }

      return soapArrayType;
   }

   private SOAPArrayType generateSoapArrayType() throws XmlException {
      Stack dimsStack = new Stack();
      SOAPArrayType arrayType = null;
      final SOAPElement current = this.getCurrentSOAPElementNode();

      while(current != null) {
         String arrayTypeStr = current.getAttributeValue(Soap11Constants.ARRAY_TYPE_NAME);
         if (arrayTypeStr == null || "".equals(arrayTypeStr)) {
            break;
         }

         arrayType = new SOAPArrayType(arrayTypeStr, new PrefixResolver() {
            public String getNamespaceForPrefix(String prefix) {
               return current.getNamespaceURI(prefix);
            }
         });
         dimsStack.push(arrayType.getDimensions());
         if ("http://www.w3.org/2001/XMLSchema".equals(arrayType.getQName().getNamespaceURI())) {
            break;
         }

         boolean hasChild = false;
         Iterator iterator = current.getChildElements();

         while(iterator.hasNext()) {
            Object node = iterator.next();
            if (node instanceof SOAPElement) {
               current = (SOAPElement)node;
               hasChild = true;
               break;
            }
         }

         if (!hasChild) {
            break;
         }

         QName xsiType = this.getXsiType(current);
         if (xsiType == null || !xsiType.equals(arrayType.getQName())) {
            break;
         }
      }

      if (!dimsStack.isEmpty()) {
         dimsStack.pop();
      }

      while(!dimsStack.isEmpty()) {
         int[] dims = (int[])dimsStack.pop();
         arrayType = new SOAPArrayType(arrayType, dims);
      }

      return arrayType;
   }

   private QName getXsiType(SOAPElement element) {
      String xsiType = element.getAttributeValue(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
      if (xsiType != null && !xsiType.equals("")) {
         int i = xsiType.indexOf(58);
         return i < 0 ? new QName(element.getNamespaceURI(""), xsiType) : new QName(element.getNamespaceURI(xsiType.substring(0, i)), xsiType.substring(i + 1));
      } else {
         return null;
      }
   }

   boolean doesByNameElementMatch(QName expected_name, String localname, String uri, Map namespaceMapping) {
      if (!expected_name.getLocalPart().equals(localname)) {
         return false;
      } else if (uri != null && uri.length() != 0) {
         boolean matched = expected_name.getNamespaceURI().equals(uri);
         if (matched) {
            return true;
         } else {
            if (namespaceMapping != null) {
               String mappedName = (String)namespaceMapping.get(expected_name.getNamespaceURI());
               if (mappedName != null) {
                  return mappedName.equals(uri);
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }
}
