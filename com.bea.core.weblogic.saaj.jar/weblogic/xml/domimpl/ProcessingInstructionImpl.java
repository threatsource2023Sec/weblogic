package weblogic.xml.domimpl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import weblogic.xml.domimpl.util.EmptyNodeList;

class ProcessingInstructionImpl extends ChildNode implements ProcessingInstruction {
   protected String target;

   public ProcessingInstructionImpl(DocumentImpl ownerDoc, String target, String data) {
      super(ownerDoc);
      this.target = target;
   }

   public short getNodeType() {
      return 7;
   }

   public NodeList getChildNodes() {
      return EmptyNodeList.getInstance();
   }

   public String getNodeName() {
      return this.target;
   }

   public String getTarget() {
      return this.target;
   }

   public String getData() {
      throw new AssertionError("UNIMP");
   }

   public void setData(String data) {
      throw new AssertionError("UNIMP");
   }

   public String getBaseURI() {
      return this.ownerNode.getBaseURI();
   }

   public final Node getFirstChild() {
      return null;
   }

   public final Node getLastChild() {
      return null;
   }

   public final Node getPreviousSibling() {
      return null;
   }

   public final Node getNextSibling() {
      return null;
   }

   public final boolean hasChildNodes() {
      return false;
   }
}
