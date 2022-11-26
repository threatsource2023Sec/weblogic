package org.glassfish.hk2.xml.jaxb.internal;

import javax.xml.bind.annotation.XmlRootElement;
import org.glassfish.hk2.api.AnnotationLiteral;

public class XmlRootElementImpl extends AnnotationLiteral implements XmlRootElement {
   private static final long serialVersionUID = -4244154751522096417L;
   private final String namespace;
   private final String name;

   public XmlRootElementImpl(String namespace, String name) {
      this.namespace = namespace;
      this.name = name;
   }

   public String namespace() {
      return this.namespace;
   }

   public String name() {
      return this.name;
   }
}
