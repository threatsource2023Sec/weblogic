package org.apache.xml.security.transforms.params;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.TransformParam;
import org.apache.xml.security.utils.ElementProxy;
import org.apache.xml.security.utils.HelperNodeList;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPath2FilterContainer extends ElementProxy implements TransformParam {
   private static final String _ATT_FILTER = "Filter";
   private static final String _ATT_FILTER_VALUE_INTERSECT = "intersect";
   private static final String _ATT_FILTER_VALUE_SUBTRACT = "subtract";
   private static final String _ATT_FILTER_VALUE_UNION = "union";
   public static final String INTERSECT = "intersect";
   public static final String SUBTRACT = "subtract";
   public static final String UNION = "union";
   public static final String _TAG_XPATH2 = "XPath";
   public static final String XPathFilter2NS = "http://www.w3.org/2002/06/xmldsig-filter2";

   private XPath2FilterContainer() {
   }

   private XPath2FilterContainer(Document doc, String xpath2filter, String filterType) {
      super(doc);
      this.setLocalAttribute("Filter", filterType);
      this.appendSelf(this.createText(xpath2filter));
   }

   private XPath2FilterContainer(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
      String filterStr = this.getLocalAttribute("Filter");
      if (!filterStr.equals("intersect") && !filterStr.equals("subtract") && !filterStr.equals("union")) {
         Object[] exArgs = new Object[]{"Filter", filterStr, "intersect, subtract or union"};
         throw new XMLSecurityException("attributeValueIllegal", exArgs);
      }
   }

   public static XPath2FilterContainer newInstanceIntersect(Document doc, String xpath2filter) {
      return new XPath2FilterContainer(doc, xpath2filter, "intersect");
   }

   public static XPath2FilterContainer newInstanceSubtract(Document doc, String xpath2filter) {
      return new XPath2FilterContainer(doc, xpath2filter, "subtract");
   }

   public static XPath2FilterContainer newInstanceUnion(Document doc, String xpath2filter) {
      return new XPath2FilterContainer(doc, xpath2filter, "union");
   }

   public static NodeList newInstances(Document doc, String[][] params) {
      HelperNodeList nl = new HelperNodeList();
      XMLUtils.addReturnToElement(doc, nl);

      for(int i = 0; i < params.length; ++i) {
         String type = params[i][0];
         String xpath = params[i][1];
         if (!type.equals("intersect") && !type.equals("subtract") && !type.equals("union")) {
            throw new IllegalArgumentException("The type(" + i + ")=\"" + type + "\" is illegal");
         }

         XPath2FilterContainer c = new XPath2FilterContainer(doc, xpath, type);
         nl.appendChild(c.getElement());
         XMLUtils.addReturnToElement(doc, nl);
      }

      return nl;
   }

   public static XPath2FilterContainer newInstance(Element element, String baseURI) throws XMLSecurityException {
      return new XPath2FilterContainer(element, baseURI);
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
      return "http://www.w3.org/2002/06/xmldsig-filter2";
   }
}
