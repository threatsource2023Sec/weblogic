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
import org.apache.xml.security.signature.XMLSignatureInput;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.SAXException;

public class CanonicalizerPhysical extends CanonicalizerBase {
   public CanonicalizerPhysical() {
      super(true);
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
      if (element.hasAttributes()) {
         SortedSet result = new TreeSet(COMPARE);
         NamedNodeMap attrs = element.getAttributes();
         int attrsLength = attrs.getLength();

         for(int i = 0; i < attrsLength; ++i) {
            Attr attribute = (Attr)attrs.item(i);
            result.add(attribute);
         }

         OutputStream writer = this.getWriter();
         Iterator var11 = result.iterator();

         while(var11.hasNext()) {
            Attr attr = (Attr)var11.next();
            outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer, cache);
         }
      }

   }

   protected void outputAttributes(Element element, NameSpaceSymbTable ns, Map cache) throws CanonicalizationException, DOMException, IOException {
      throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
   }

   protected void circumventBugIfNeeded(XMLSignatureInput input) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
   }

   protected void handleParent(Element e, NameSpaceSymbTable ns) {
   }

   public final String engineGetURI() {
      return "http://santuario.apache.org/c14n/physical";
   }

   public final boolean engineGetIncludeComments() {
      return true;
   }

   protected void outputPItoWriter(ProcessingInstruction currentPI, OutputStream writer, int position) throws IOException {
      super.outputPItoWriter(currentPI, writer, 0);
   }

   protected void outputCommentToWriter(Comment currentComment, OutputStream writer, int position) throws IOException {
      super.outputCommentToWriter(currentComment, writer, 0);
   }
}
