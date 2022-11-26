package weblogic.xml.saaj;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.Text;
import org.w3c.dom.Node;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.NodeImpl;

class TextImpl extends weblogic.xml.domimpl.TextImpl implements Text, SaajNode {
   private static final long serialVersionUID = 5304036018172221961L;

   TextImpl(DocumentImpl ownerDocument, String data) {
      super(ownerDocument, data);
   }

   public boolean isComment() {
      String txt = this.getNodeValue();
      return txt.startsWith("<!--") && txt.endsWith("-->");
   }

   public void detachNode() {
      Node parentNode = this.getParentNode();
      if (parentNode != null) {
         parentNode.removeChild(this);
      }

   }

   public void recycleNode() {
      this.detachNode();
   }

   public String getValue() {
      String nodeValue = this.getNodeValue();
      return nodeValue.equals("") ? null : nodeValue;
   }

   public void setValue(String value) {
      this.setNodeValue(value);
   }

   public SOAPElement getParentElement() {
      return (SOAPElement)this.getParentNode();
   }

   public void setParentElement(SOAPElement parent) throws SOAPException {
      if (parent == null) {
         throw new SOAPException("Cannot pass NULL to setParentElement");
      } else {
         ((ElementBase)parent).appendChild(this);
      }
   }

   public SaajNode createAndAppendSaajElement(NodeImpl parent, String namespaceURI, String localName, String prefix, int numAttrs) {
      throw new AssertionError("not used");
   }

   public ChildNode fixChildSaajType(ChildNode child) {
      throw new AssertionError("not used");
   }
}
