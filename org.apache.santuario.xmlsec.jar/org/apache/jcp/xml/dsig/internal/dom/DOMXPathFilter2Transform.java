package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
import javax.xml.crypto.dsig.spec.XPathType;
import javax.xml.crypto.dsig.spec.XPathType.Filter;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public final class DOMXPathFilter2Transform extends ApacheTransform {
   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
      if (params == null) {
         throw new InvalidAlgorithmParameterException("params are required");
      } else if (!(params instanceof XPathFilter2ParameterSpec)) {
         throw new InvalidAlgorithmParameterException("params must be of type XPathFilter2ParameterSpec");
      } else {
         this.params = params;
      }
   }

   public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {
      super.init(parent, context);

      try {
         this.unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
      } catch (MarshalException var4) {
         throw new InvalidAlgorithmParameterException(var4);
      }
   }

   private void unmarshalParams(Element curXPathElem) throws MarshalException {
      List list = new ArrayList();

      for(Element currentElement = curXPathElem; currentElement != null; currentElement = DOMUtils.getNextSiblingElement(currentElement)) {
         String xPath = currentElement.getFirstChild().getNodeValue();
         String filterVal = DOMUtils.getAttributeValue(currentElement, "Filter");
         if (filterVal == null) {
            throw new MarshalException("filter cannot be null");
         }

         XPathType.Filter filter = null;
         if ("intersect".equals(filterVal)) {
            filter = Filter.INTERSECT;
         } else if ("subtract".equals(filterVal)) {
            filter = Filter.SUBTRACT;
         } else {
            if (!"union".equals(filterVal)) {
               throw new MarshalException("Unknown XPathType filter type" + filterVal);
            }

            filter = Filter.UNION;
         }

         NamedNodeMap attributes = currentElement.getAttributes();
         if (attributes == null) {
            list.add(new XPathType(xPath, filter));
         } else {
            int length = attributes.getLength();
            Map namespaceMap = new HashMap(length);

            for(int i = 0; i < length; ++i) {
               Attr attr = (Attr)attributes.item(i);
               String prefix = attr.getPrefix();
               if (prefix != null && "xmlns".equals(prefix)) {
                  namespaceMap.put(attr.getLocalName(), attr.getValue());
               }
            }

            list.add(new XPathType(xPath, filter, namespaceMap));
         }
      }

      this.params = new XPathFilter2ParameterSpec(list);
   }

   public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
      super.marshalParams(parent, context);
      XPathFilter2ParameterSpec xp = (XPathFilter2ParameterSpec)this.getParameterSpec();
      String prefix = DOMUtils.getNSPrefix(context, "http://www.w3.org/2002/06/xmldsig-filter2");
      String qname = prefix != null && prefix.length() != 0 ? "xmlns:" + prefix : "xmlns";
      List xpathList = xp.getXPathList();
      Iterator var7 = xpathList.iterator();

      while(var7.hasNext()) {
         XPathType xpathType = (XPathType)var7.next();
         Element elem = DOMUtils.createElement(this.ownerDoc, "XPath", "http://www.w3.org/2002/06/xmldsig-filter2", prefix);
         elem.appendChild(this.ownerDoc.createTextNode(xpathType.getExpression()));
         DOMUtils.setAttribute(elem, "Filter", xpathType.getFilter().toString());
         elem.setAttributeNS("http://www.w3.org/2000/xmlns/", qname, "http://www.w3.org/2002/06/xmldsig-filter2");
         Set entries = xpathType.getNamespaceMap().entrySet();
         Iterator var11 = entries.iterator();

         while(var11.hasNext()) {
            Map.Entry entry = (Map.Entry)var11.next();
            elem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + (String)entry.getKey(), (String)entry.getValue());
         }

         this.transformElem.appendChild(elem);
      }

   }
}
