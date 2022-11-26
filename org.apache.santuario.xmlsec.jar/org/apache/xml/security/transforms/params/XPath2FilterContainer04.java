package org.apache.xml.security.transforms.params;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.TransformParam;
import org.apache.xml.security.utils.ElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XPath2FilterContainer04 extends ElementProxy implements TransformParam {
   private static final String _ATT_FILTER = "Filter";
   private static final String _ATT_FILTER_VALUE_INTERSECT = "intersect";
   private static final String _ATT_FILTER_VALUE_SUBTRACT = "subtract";
   private static final String _ATT_FILTER_VALUE_UNION = "union";
   public static final String _TAG_XPATH2 = "XPath";
   public static final String XPathFilter2NS = "http://www.w3.org/2002/04/xmldsig-filter2";

   private XPath2FilterContainer04() {
   }

   private XPath2FilterContainer04(Document doc, String xpath2filter, String filterType) {
      super(doc);
      this.setLocalAttribute("Filter", filterType);
      if (xpath2filter.length() > 2 && !Character.isWhitespace(xpath2filter.charAt(0))) {
         this.addReturnToSelf();
         this.appendSelf(this.createText(xpath2filter));
         this.addReturnToSelf();
      } else {
         this.appendSelf(this.createText(xpath2filter));
      }

   }

   private XPath2FilterContainer04(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
      String filterStr = this.getLocalAttribute("Filter");
      if (!filterStr.equals("intersect") && !filterStr.equals("subtract") && !filterStr.equals("union")) {
         Object[] exArgs = new Object[]{"Filter", filterStr, "intersect, subtract or union"};
         throw new XMLSecurityException("attributeValueIllegal", exArgs);
      }
   }

   public static XPath2FilterContainer04 newInstanceIntersect(Document doc, String xpath2filter) {
      return new XPath2FilterContainer04(doc, xpath2filter, "intersect");
   }

   public static XPath2FilterContainer04 newInstanceSubtract(Document doc, String xpath2filter) {
      return new XPath2FilterContainer04(doc, xpath2filter, "subtract");
   }

   public static XPath2FilterContainer04 newInstanceUnion(Document doc, String xpath2filter) {
      return new XPath2FilterContainer04(doc, xpath2filter, "union");
   }

   public static XPath2FilterContainer04 newInstance(Element element, String baseURI) throws XMLSecurityException {
      return new XPath2FilterContainer04(element, baseURI);
   }

   public boolean isIntersect() {
      return this.getLocalAttribute("Filter").equals("intersect");
   }

   public boolean isSubtract() {
      return this.getLocalAttribute("Filter").equals("subtract");
   }

   public boolean isUnion() {
      return this.getLocalAttribute("Filter").equals("union");
   }

   public String getXPathFilterStr() {
      return this.getTextFromTextChild();
   }

   public Node getXPathFilterTextNode() {
      for(Node childNode = this.getElement().getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
         if (childNode.getNodeType() == 3) {
            return childNode;
         }
      }

      return null;
   }

   public final String getBaseLocalName() {
      return "XPath";
   }

   public final String getBaseNamespace() {
      return "http://www.w3.org/2002/04/xmldsig-filter2";
   }
}
