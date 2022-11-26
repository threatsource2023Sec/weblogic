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
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class Canonicalizer20010315Excl extends CanonicalizerBase {
   private SortedSet inclusiveNSSet;
   private boolean propagateDefaultNamespace = false;

   public Canonicalizer20010315Excl(boolean includeComments) {
      super(includeComments);
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode) throws CanonicalizationException {
      return this.engineCanonicalizeSubTree(rootNode, "", (Node)null);
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces) throws CanonicalizationException {
      return this.engineCanonicalizeSubTree(rootNode, inclusiveNamespaces, (Node)null);
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces, boolean propagateDefaultNamespace) throws CanonicalizationException {
      this.propagateDefaultNamespace = propagateDefaultNamespace;
      return this.engineCanonicalizeSubTree(rootNode, inclusiveNamespaces, (Node)null);
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces, Node excl) throws CanonicalizationException {
      this.inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
      return super.engineCanonicalizeSubTree(rootNode, excl);
   }

   public byte[] engineCanonicalize(XMLSignatureInput rootNode, String inclusiveNamespaces) throws CanonicalizationException {
      this.inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
      return super.engineCanonicalize(rootNode);
   }

   public byte[] engineCanonicalizeXPathNodeSet(Set xpathNodeSet, String inclusiveNamespaces) throws CanonicalizationException {
      this.inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
      return super.engineCanonicalizeXPathNodeSet(xpathNodeSet);
   }

   protected void outputAttributesSubtree(Element element, NameSpaceSymbTable ns, Map cache) throws CanonicalizationException, DOMException, IOException {
      SortedSet result = new TreeSet(COMPARE);
      SortedSet visiblyUtilized = new TreeSet();
      if (this.inclusiveNSSet != null && !this.inclusiveNSSet.isEmpty()) {
         visiblyUtilized.addAll(this.inclusiveNSSet);
      }

      NamedNodeMap attrs;
      Attr attr;
      if (element.hasAttributes()) {
         attrs = element.getAttributes();
         int attrsLength = attrs.getLength();

         for(int i = 0; i < attrsLength; ++i) {
            attr = (Attr)attrs.item(i);
            String NName = attr.getLocalName();
            String NNodeValue = attr.getNodeValue();
            if (!"http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI())) {
               String prefix = attr.getPrefix();
               if (prefix != null && !prefix.equals("xml") && !prefix.equals("xmlns")) {
                  visiblyUtilized.add(prefix);
               }

               result.add(attr);
            } else if ((!"xml".equals(NName) || !"http://www.w3.org/XML/1998/namespace".equals(NNodeValue)) && ns.addMapping(NName, NNodeValue, attr) && C14nHelper.namespaceIsRelative(NNodeValue)) {
               Object[] exArgs = new Object[]{element.getTagName(), NName, attr.getNodeValue()};
               throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
            }
         }
      }

      if (this.propagateDefaultNamespace && ns.getLevel() == 1 && this.inclusiveNSSet.contains("xmlns") && ns.getMappingWithoutRendered("xmlns") == null) {
         ns.removeMapping("xmlns");
         ns.addMapping("xmlns", "", this.getNullNode(element.getOwnerDocument()));
      }

      attrs = null;
      String prefix;
      if (element.getNamespaceURI() != null && element.getPrefix() != null && element.getPrefix().length() != 0) {
         prefix = element.getPrefix();
      } else {
         prefix = "xmlns";
      }

      visiblyUtilized.add(prefix);
      Iterator var14 = visiblyUtilized.iterator();

      while(var14.hasNext()) {
         String s = (String)var14.next();
         attr = ns.getMapping(s);
         if (attr != null) {
            result.add(attr);
         }
      }

      OutputStream writer = this.getWriter();
      Iterator var17 = result.iterator();

      while(var17.hasNext()) {
         attr = (Attr)var17.next();
         outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer, cache);
      }

   }

   protected void outputAttributes(Element element, NameSpaceSymbTable ns, Map cache) throws CanonicalizationException, DOMException, IOException {
      SortedSet result = new TreeSet(COMPARE);
      Set visiblyUtilized = null;
      boolean isOutputElement = this.isVisibleDO(element, ns.getLevel()) == 1;
      if (isOutputElement) {
         visiblyUtilized = new TreeSet();
         if (this.inclusiveNSSet != null && !this.inclusiveNSSet.isEmpty()) {
            visiblyUtilized.addAll(this.inclusiveNSSet);
         }
      }

      if (element.hasAttributes()) {
         NamedNodeMap attrs = element.getAttributes();
         int attrsLength = attrs.getLength();

         for(int i = 0; i < attrsLength; ++i) {
            Attr attribute = (Attr)attrs.item(i);
            String NName = attribute.getLocalName();
            String NNodeValue = attribute.getNodeValue();
            if (!"http://www.w3.org/2000/xmlns/".equals(attribute.getNamespaceURI())) {
               if (this.isVisible(attribute) && isOutputElement) {
                  String prefix = attribute.getPrefix();
                  if (prefix != null && !prefix.equals("xml") && !prefix.equals("xmlns")) {
                     visiblyUtilized.add(prefix);
                  }

                  result.add(attribute);
               }
            } else if (isOutputElement && !this.isVisible(attribute) && !"xmlns".equals(NName)) {
               ns.removeMappingIfNotRender(NName);
            } else {
               if (!isOutputElement && this.isVisible(attribute) && this.inclusiveNSSet.contains(NName) && !ns.removeMappingIfRender(NName)) {
                  Node n = ns.addMappingAndRender(NName, NNodeValue, attribute);
                  if (n != null) {
                     result.add((Attr)n);
                     if (C14nHelper.namespaceIsRelative(attribute)) {
                        Object[] exArgs = new Object[]{element.getTagName(), NName, attribute.getNodeValue()};
                        throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
                     }
                  }
               }

               if (ns.addMapping(NName, NNodeValue, attribute) && C14nHelper.namespaceIsRelative(NNodeValue)) {
                  Object[] exArgs = new Object[]{element.getTagName(), NName, attribute.getNodeValue()};
                  throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
               }
            }
         }
      }

      if (isOutputElement) {
         Attr xmlns = element.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
         if (xmlns != null && !this.isVisible(xmlns)) {
            ns.addMapping("xmlns", "", this.getNullNode(xmlns.getOwnerDocument()));
         }

         String prefix = null;
         if (element.getNamespaceURI() != null && element.getPrefix() != null && element.getPrefix().length() != 0) {
            prefix = element.getPrefix();
         } else {
            prefix = "xmlns";
         }

         visiblyUtilized.add(prefix);
         Iterator var19 = visiblyUtilized.iterator();

         while(var19.hasNext()) {
            String s = (String)var19.next();
            Attr key = ns.getMapping(s);
            if (key != null) {
               result.add(key);
            }
         }
      }

      OutputStream writer = this.getWriter();
      Iterator var18 = result.iterator();

      while(var18.hasNext()) {
         Attr attr = (Attr)var18.next();
         outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer, cache);
      }

   }

   protected void circumventBugIfNeeded(XMLSignatureInput input) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
      if (input.isNeedsToBeExpanded() && !this.inclusiveNSSet.isEmpty()) {
         Document doc = null;
         if (input.getSubNode() != null) {
            doc = XMLUtils.getOwnerDocument(input.getSubNode());
         } else {
            doc = XMLUtils.getOwnerDocument(input.getNodeSet());
         }

         XMLUtils.circumventBug2650(doc);
      }
   }
}
