package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.w3c.dom.Element;

public final class DOMExcC14NMethod extends ApacheCanonicalizer {
   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
      if (params != null) {
         if (!(params instanceof ExcC14NParameterSpec)) {
            throw new InvalidAlgorithmParameterException("params must be of type ExcC14NParameterSpec");
         }

         this.params = (C14NMethodParameterSpec)params;
      }

   }

   public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {
      super.init(parent, context);
      Element paramsElem = DOMUtils.getFirstChildElement(this.transformElem);
      if (paramsElem == null) {
         this.params = null;
         this.inclusiveNamespaces = null;
      } else {
         this.unmarshalParams(paramsElem);
      }
   }

   private void unmarshalParams(Element paramsElem) {
      String prefixListAttr = paramsElem.getAttributeNS((String)null, "PrefixList");
      this.inclusiveNamespaces = prefixListAttr;
      int begin = 0;
      int end = prefixListAttr.indexOf(32);

      ArrayList prefixList;
      for(prefixList = new ArrayList(); end != -1; end = prefixListAttr.indexOf(32, begin)) {
         prefixList.add(prefixListAttr.substring(begin, end));
         begin = end + 1;
      }

      if (begin <= prefixListAttr.length()) {
         prefixList.add(prefixListAttr.substring(begin));
      }

      this.params = new ExcC14NParameterSpec(prefixList);
   }

   public List getParameterSpecPrefixList(ExcC14NParameterSpec paramSpec) {
      return paramSpec.getPrefixList();
   }

   public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
      super.marshalParams(parent, context);
      AlgorithmParameterSpec spec = this.getParameterSpec();
      if (spec != null) {
         String prefix = DOMUtils.getNSPrefix(context, "http://www.w3.org/2001/10/xml-exc-c14n#");
         Element eElem = DOMUtils.createElement(this.ownerDoc, "InclusiveNamespaces", "http://www.w3.org/2001/10/xml-exc-c14n#", prefix);
         if (prefix != null && prefix.length() != 0) {
            eElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, "http://www.w3.org/2001/10/xml-exc-c14n#");
         } else {
            eElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2001/10/xml-exc-c14n#");
         }

         ExcC14NParameterSpec params = (ExcC14NParameterSpec)spec;
         StringBuilder prefixListAttr = new StringBuilder("");
         List prefixList = this.getParameterSpecPrefixList(params);
         int i = 0;

         for(int size = prefixList.size(); i < size; ++i) {
            prefixListAttr.append((String)prefixList.get(i));
            if (i < size - 1) {
               prefixListAttr.append(" ");
            }
         }

         DOMUtils.setAttribute(eElem, "PrefixList", prefixListAttr.toString());
         this.inclusiveNamespaces = prefixListAttr.toString();
         this.transformElem.appendChild(eElem);
      }
   }

   public String getParamsNSURI() {
      return "http://www.w3.org/2001/10/xml-exc-c14n#";
   }

   public Data transform(Data data, XMLCryptoContext xc) throws TransformException {
      if (data instanceof DOMSubTreeData) {
         DOMSubTreeData subTree = (DOMSubTreeData)data;
         if (subTree.excludeComments()) {
            try {
               this.apacheCanonicalizer = Canonicalizer.getInstance("http://www.w3.org/2001/10/xml-exc-c14n#");
               boolean secVal = Utils.secureValidation(xc);
               this.apacheCanonicalizer.setSecureValidation(secVal);
            } catch (InvalidCanonicalizerException var5) {
               throw new TransformException("Couldn't find Canonicalizer for: http://www.w3.org/2001/10/xml-exc-c14n#: " + var5.getMessage(), var5);
            }
         }
      }

      return this.canonicalize(data, xc);
   }
}
