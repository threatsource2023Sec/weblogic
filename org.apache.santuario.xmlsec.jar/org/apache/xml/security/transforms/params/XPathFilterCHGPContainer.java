package org.apache.xml.security.transforms.params;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.TransformParam;
import org.apache.xml.security.utils.ElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class XPathFilterCHGPContainer extends ElementProxy implements TransformParam {
   public static final String TRANSFORM_XPATHFILTERCHGP = "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";
   private static final String _TAG_INCLUDE_BUT_SEARCH = "IncludeButSearch";
   private static final String _TAG_EXCLUDE_BUT_SEARCH = "ExcludeButSearch";
   private static final String _TAG_EXCLUDE = "Exclude";
   public static final String _TAG_XPATHCHGP = "XPathAlternative";
   public static final String _ATT_INCLUDESLASH = "IncludeSlashPolicy";
   public static final boolean IncludeSlash = true;
   public static final boolean ExcludeSlash = false;

   private XPathFilterCHGPContainer() {
   }

   private XPathFilterCHGPContainer(Document doc, boolean includeSlashPolicy, String includeButSearch, String excludeButSearch, String exclude) {
      super(doc);
      if (includeSlashPolicy) {
         this.setLocalAttribute("IncludeSlashPolicy", "true");
      } else {
         this.setLocalAttribute("IncludeSlashPolicy", "false");
      }

      Element excludeElem;
      if (includeButSearch != null && includeButSearch.trim().length() > 0) {
         excludeElem = ElementProxy.createElementForFamily(doc, this.getBaseNamespace(), "IncludeButSearch");
         excludeElem.appendChild(this.createText(indentXPathText(includeButSearch)));
         this.addReturnToSelf();
         this.appendSelf(excludeElem);
      }

      if (excludeButSearch != null && excludeButSearch.trim().length() > 0) {
         excludeElem = ElementProxy.createElementForFamily(doc, this.getBaseNamespace(), "ExcludeButSearch");
         excludeElem.appendChild(this.createText(indentXPathText(excludeButSearch)));
         this.addReturnToSelf();
         this.appendSelf(excludeElem);
      }

      if (exclude != null && exclude.trim().length() > 0) {
         excludeElem = ElementProxy.createElementForFamily(doc, this.getBaseNamespace(), "Exclude");
         excludeElem.appendChild(this.createText(indentXPathText(exclude)));
         this.addReturnToSelf();
         this.appendSelf(excludeElem);
      }

      this.addReturnToSelf();
   }

   static String indentXPathText(String xp) {
      return xp.length() > 2 && !Character.isWhitespace(xp.charAt(0)) ? "\n" + xp + "\n" : xp;
   }

   private XPathFilterCHGPContainer(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public static XPathFilterCHGPContainer getInstance(Document doc, boolean includeSlashPolicy, String includeButSearch, String excludeButSearch, String exclude) {
      return new XPathFilterCHGPContainer(doc, includeSlashPolicy, includeButSearch, excludeButSearch, exclude);
   }

   public static XPathFilterCHGPContainer getInstance(Element element, String baseURI) throws XMLSecurityException {
      return new XPathFilterCHGPContainer(element, baseURI);
   }

   private String getXStr(String type) {
      if (this.length(this.getBaseNamespace(), type) != 1) {
         return "";
      } else {
         Element xElem = XMLUtils.selectNode(this.getElement().getFirstChild(), this.getBaseNamespace(), type, 0);
         return XMLUtils.getFullTextChildrenFromNode(xElem);
      }
   }

   public String getIncludeButSearch() {
      return this.getXStr("IncludeButSearch");
   }

   public String getExcludeButSearch() {
      return this.getXStr("ExcludeButSearch");
   }

   public String getExclude() {
      return this.getXStr("Exclude");
   }

   public boolean getIncludeSlashPolicy() {
      return this.getLocalAttribute("IncludeSlashPolicy").equals("true");
   }

   private Node getHereContextNode(String type) {
      return this.length(this.getBaseNamespace(), type) != 1 ? null : selectNodeText(this.getFirstChild(), this.getBaseNamespace(), type, 0);
   }

   private static Text selectNodeText(Node sibling, String uri, String nodeName, int number) {
      Node n = XMLUtils.selectNode(sibling, uri, nodeName, number);
      if (n == null) {
         return null;
      } else {
         Node n;
         for(n = n.getFirstChild(); n != null && n.getNodeType() != 3; n = n.getNextSibling()) {
         }

         return (Text)n;
      }
   }

   public Node getHereContextNodeIncludeButSearch() {
      return this.getHereContextNode("IncludeButSearch");
   }

   public Node getHereContextNodeExcludeButSearch() {
      return this.getHereContextNode("ExcludeButSearch");
   }

   public Node getHereContextNodeExclude() {
      return this.getHereContextNode("Exclude");
   }

   public final String getBaseLocalName() {
      return "XPathAlternative";
   }

   public final String getBaseNamespace() {
      return "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";
   }
}
