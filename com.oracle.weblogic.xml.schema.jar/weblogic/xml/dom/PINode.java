package weblogic.xml.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.ProcessingInstruction;

public class PINode extends ChildNode implements ProcessingInstruction {
   public PINode() {
      this.setNodeType((short)7);
   }

   public PINode(String target) {
      this();
      this.setNodeName(target);
   }

   public PINode(String target, String data) {
      this(target);
      this.setNodeValue(data);
   }

   public String getTarget() {
      return this.getNodeName();
   }

   public String getData() {
      return this.getNodeValue();
   }

   public void setData(String data) throws DOMException {
      this.setNodeValue(data);
   }

   public void print(StringBuffer b, int tab) {
      b.append(this.toString());
   }

   public String toString() {
      String target = this.getTarget();
      String data = this.getData();
      if (target == null) {
         target = "";
      }

      if (data == null) {
         data = "";
      } else {
         data = " " + data;
      }

      return "<?" + target + data + "?>";
   }
}
