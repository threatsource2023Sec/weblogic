package org.apache.xml.security.transforms.params;

import org.apache.xml.security.transforms.TransformParam;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class XPathContainer extends SignatureElementProxy implements TransformParam {
   public XPathContainer(Document doc) {
      super(doc);
   }

   public void setXPath(String xpath) {
      Node childNode = this.getElement().getFirstChild();

      while(childNode != null) {
         Node nodeToBeRemoved = childNode;
         childNode = childNode.getNextSibling();
         this.getElement().removeChild(nodeToBeRemoved);
      }

      Text xpathText = this.createText(xpath);
      this.appendSelf(xpathText);
   }

   public String getXPath() {
      return this.getTextFromTextChild();
   }

   public String getBaseLocalName() {
      return "XPath";
   }
}
