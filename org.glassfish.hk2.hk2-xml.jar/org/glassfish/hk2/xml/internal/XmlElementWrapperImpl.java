package org.glassfish.hk2.xml.internal;

import javax.xml.bind.annotation.XmlElementWrapper;
import org.glassfish.hk2.api.AnnotationLiteral;

public class XmlElementWrapperImpl extends AnnotationLiteral implements XmlElementWrapper {
   private static final long serialVersionUID = 3661729772479049681L;
   private final String name;
   private final String namespace;
   private final boolean nillable;
   private final boolean required;

   public XmlElementWrapperImpl(String name, String namespace, boolean nillable, boolean required) {
      this.name = name;
      this.namespace = namespace;
      this.nillable = nillable;
      this.required = required;
   }

   public String name() {
      return this.name;
   }

   public String namespace() {
      return this.namespace;
   }

   public boolean nillable() {
      return this.nillable;
   }

   public boolean required() {
      return this.required;
   }

   public String toString() {
      return "@XmlElemntWrapperImpl(" + this.name + "," + this.namespace + "," + this.nillable + "," + this.required + "," + System.identityHashCode(this) + ")";
   }
}
