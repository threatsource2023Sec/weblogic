package weblogic.apache.org.apache.velocity.anakia;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.DefaultJDOMFactory;

public class AnakiaJDOMFactory extends DefaultJDOMFactory {
   public Element element(String name, Namespace namespace) {
      return new AnakiaElement(name, namespace);
   }

   public Element element(String name) {
      return new AnakiaElement(name);
   }

   public Element element(String name, String uri) {
      return new AnakiaElement(name, uri);
   }

   public Element element(String name, String prefix, String uri) {
      return new AnakiaElement(name, prefix, uri);
   }
}
