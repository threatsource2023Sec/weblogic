package org.apache.jcp.xml.dsig.internal.dom;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.crypto.NodeSetData;
import org.apache.xml.security.signature.NodeFilter;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Node;

public class ApacheNodeSetData implements ApacheData, NodeSetData {
   private XMLSignatureInput xi;

   public ApacheNodeSetData(XMLSignatureInput xi) {
      this.xi = xi;
   }

   public Iterator iterator() {
      if (this.xi.getNodeFilters() != null && !this.xi.getNodeFilters().isEmpty()) {
         return Collections.unmodifiableSet(this.getNodeSet(this.xi.getNodeFilters())).iterator();
      } else {
         try {
            return Collections.unmodifiableSet(this.xi.getNodeSet()).iterator();
         } catch (Exception var2) {
            throw new RuntimeException("unrecoverable error retrieving nodeset", var2);
         }
      }
   }

   public XMLSignatureInput getXMLSignatureInput() {
      return this.xi;
   }

   private Set getNodeSet(List nodeFilters) {
      if (this.xi.isNeedsToBeExpanded()) {
         XMLUtils.circumventBug2650(XMLUtils.getOwnerDocument(this.xi.getSubNode()));
      }

      Set inputSet = new LinkedHashSet();
      XMLUtils.getSet(this.xi.getSubNode(), inputSet, (Node)null, !this.xi.isExcludeComments());
      Set nodeSet = new LinkedHashSet();
      Iterator var4 = inputSet.iterator();

      while(var4.hasNext()) {
         Node currentNode = (Node)var4.next();
         Iterator it = nodeFilters.iterator();
         boolean skipNode = false;

         while(it.hasNext() && !skipNode) {
            NodeFilter nf = (NodeFilter)it.next();
            if (nf.isNodeInclude(currentNode) != 1) {
               skipNode = true;
            }
         }

         if (!skipNode) {
            nodeSet.add(currentNode);
         }
      }

      return nodeSet;
   }
}
