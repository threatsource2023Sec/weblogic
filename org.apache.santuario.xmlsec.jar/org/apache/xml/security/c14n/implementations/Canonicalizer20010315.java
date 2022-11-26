package org.apache.xml.security.c14n.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.helper.C14nHelper;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class Canonicalizer20010315 extends CanonicalizerBase {
   private boolean firstCall;
   private final XmlAttrStack xmlattrStack;
   private final boolean c14n11;

   public Canonicalizer20010315(boolean includeComments) {
      this(includeComments, false);
   }

   public Canonicalizer20010315(boolean includeComments, boolean c14n11) {
      super(includeComments);
      this.firstCall = true;
      this.xmlattrStack = new XmlAttrStack(c14n11);
      this.c14n11 = c14n11;
   }

   public byte[] engineCanonicalizeXPathNodeSet(Set xpathNodeSet, String inclusiveNamespaces) throws CanonicalizationException {
      throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces) throws CanonicalizationException {
      throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces, boolean propagateDefaultNamespace) throws CanonicalizationException {
      throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
   }

   protected void outputAttributesSubtree(Element element, NameSpaceSymbTable ns, Map cache) throws CanonicalizationException, DOMException, IOException {
      if (element.hasAttributes() || this.firstCall) {
         SortedSet result = new TreeSet(COMPARE);
         if (element.hasAttributes()) {
            NamedNodeMap attrs = element.getAttributes();
            int attrsLength = attrs.getLength();

            for(int i = 0; i < attrsLength; ++i) {
               Attr attribute = (Attr)attrs.item(i);
               String NUri = attribute.getNamespaceURI();
               String NName = attribute.getLocalName();
               String NValue = attribute.getValue();
               if (!"http://www.w3.org/2000/xmlns/".equals(NUri)) {
                  result.add(attribute);
               } else if (!"xml".equals(NName) || !"http://www.w3.org/XML/1998/namespace".equals(NValue)) {
                  Node n = ns.addMappingAndRender(NName, NValue, attribute);
                  if (n != null) {
                     result.add((Attr)n);
                     if (C14nHelper.namespaceIsRelative(attribute)) {
                        Object[] exArgs = new Object[]{element.getTagName(), NName, attribute.getNodeValue()};
                        throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
                     }
                  }
               }
            }
         }

         if (this.firstCall) {
            ns.getUnrenderedNodes(result);
            this.xmlattrStack.getXmlnsAttr(result);
            this.firstCall = false;
         }

         OutputStream writer = this.getWriter();
         Iterator var15 = result.iterator();

         while(var15.hasNext()) {
            Attr attr = (Attr)var15.next();
            outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer, cache);
         }

      }
   }

   protected void outputAttributes(Element element, NameSpaceSymbTable ns, Map cache) throws CanonicalizationException, DOMException, IOException {
      this.xmlattrStack.push(ns.getLevel());
      boolean isRealVisible = this.isVisibleDO(element, ns.getLevel()) == 1;
      SortedSet result = new TreeSet(COMPARE);
      if (element.hasAttributes()) {
         NamedNodeMap attrs = element.getAttributes();
         int attrsLength = attrs.getLength();

         for(int i = 0; i < attrsLength; ++i) {
            Attr attribute = (Attr)attrs.item(i);
            String NUri = attribute.getNamespaceURI();
            String NName = attribute.getLocalName();
            String NValue = attribute.getValue();
            if (!"http://www.w3.org/2000/xmlns/".equals(NUri)) {
               if ("http://www.w3.org/XML/1998/namespace".equals(NUri)) {
                  if (this.c14n11 && "id".equals(NName)) {
                     if (isRealVisible) {
                        result.add(attribute);
                     }
                  } else {
                     this.xmlattrStack.addXmlnsAttr(attribute);
                  }
               } else if (isRealVisible) {
                  result.add(attribute);
               }
            } else if (!"xml".equals(NName) || !"http://www.w3.org/XML/1998/namespace".equals(NValue)) {
               if (this.isVisible(attribute)) {
                  if (isRealVisible || !ns.removeMappingIfRender(NName)) {
                     Node n = ns.addMappingAndRender(NName, NValue, attribute);
                     if (n != null) {
                        result.add((Attr)n);
                        if (C14nHelper.namespaceIsRelative(attribute)) {
                           Object[] exArgs = new Object[]{element.getTagName(), NName, attribute.getNodeValue()};
                           throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
                        }
                     }
                  }
               } else if (isRealVisible && !"xmlns".equals(NName)) {
                  ns.removeMapping(NName);
               } else {
                  ns.addMapping(NName, NValue, attribute);
               }
            }
         }
      }

      if (isRealVisible) {
         Attr xmlns = element.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
         Node n = null;
         if (xmlns == null) {
            n = ns.getMapping("xmlns");
         } else if (!this.isVisible(xmlns)) {
            n = ns.addMappingAndRender("xmlns", "", this.getNullNode(xmlns.getOwnerDocument()));
         }

         if (n != null) {
            result.add((Attr)n);
         }

         this.xmlattrStack.getXmlnsAttr(result);
         ns.getUnrenderedNodes(result);
      }

      OutputStream writer = this.getWriter();
      Iterator var18 = result.iterator();

      while(var18.hasNext()) {
         Attr attr = (Attr)var18.next();
         outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer, cache);
      }

   }

   protected void circumventBugIfNeeded(XMLSignatureInput input) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
      if (input.isNeedsToBeExpanded()) {
         Document doc = null;
         if (input.getSubNode() != null) {
            doc = XMLUtils.getOwnerDocument(input.getSubNode());
         } else {
            doc = XMLUtils.getOwnerDocument(input.getNodeSet());
         }

         XMLUtils.circumventBug2650(doc);
      }
   }

   protected void handleParent(Element e, NameSpaceSymbTable ns) {
      if (e.hasAttributes() || e.getNamespaceURI() != null) {
         this.xmlattrStack.push(-1);
         NamedNodeMap attrs = e.getAttributes();
         int attrsLength = attrs.getLength();

         String Name;
         for(int i = 0; i < attrsLength; ++i) {
            Attr attribute = (Attr)attrs.item(i);
            Name = attribute.getLocalName();
            String NValue = attribute.getNodeValue();
            if ("http://www.w3.org/2000/xmlns/".equals(attribute.getNamespaceURI())) {
               if (!"xml".equals(Name) || !"http://www.w3.org/XML/1998/namespace".equals(NValue)) {
                  ns.addMapping(Name, NValue, attribute);
               }
            } else if ("http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI()) && (!this.c14n11 || !"id".equals(Name))) {
               this.xmlattrStack.addXmlnsAttr(attribute);
            }
         }

         if (e.getNamespaceURI() != null) {
            String NName = e.getPrefix();
            String NValue = e.getNamespaceURI();
            if (NName != null && !NName.equals("")) {
               Name = "xmlns:" + NName;
            } else {
               NName = "xmlns";
               Name = "xmlns";
            }

            Attr n = e.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", Name);
            n.setValue(NValue);
            ns.addMapping(NName, NValue, n);
         }

      }
   }
}
