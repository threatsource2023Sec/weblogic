package weblogic.apache.org.apache.velocity.anakia;

import java.util.List;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;

public class AnakiaElement extends Element {
   private static final XMLOutputter DEFAULT_OUTPUTTER = new XMLOutputter();

   public AnakiaElement(String name, Namespace namespace) {
      super(name, namespace);
   }

   public AnakiaElement(String name) {
      super(name);
   }

   public AnakiaElement(String name, String uri) {
      super(name, uri);
   }

   public AnakiaElement(String name, String prefix, String uri) {
      super(name, prefix, uri);
   }

   public NodeList selectNodes(String xpathExpression) {
      return new NodeList(XPathCache.getXPath(xpathExpression).applyTo(this), false);
   }

   public String toString() {
      return DEFAULT_OUTPUTTER.outputString(this);
   }

   public List getContent() {
      return new NodeList(super.getContent(), false);
   }

   public List getChildren() {
      return new NodeList(super.getChildren(), false);
   }

   public List getChildren(String name) {
      return new NodeList(super.getChildren(name));
   }

   public List getChildren(String name, Namespace ns) {
      return new NodeList(super.getChildren(name, ns));
   }

   public List getAttributes() {
      return new NodeList(super.getAttributes());
   }
}
