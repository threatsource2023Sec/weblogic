package org.glassfish.hk2.xml.jaxb.internal;

import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.xml.internal.Utilities;

public class XmlElementImpl extends AnnotationLiteral implements XmlElement {
   private static final long serialVersionUID = -5015658933035011114L;
   private final String name;
   private final boolean nillable;
   private final boolean required;
   private final String namespace;
   private final String defaultValue;
   private final String typeByName;

   public XmlElementImpl(String name, boolean nillable, boolean required, String namespace, String defaultValue, String typeByName) {
      this.name = name;
      this.nillable = nillable;
      this.required = required;
      this.namespace = namespace;
      this.defaultValue = defaultValue;
      this.typeByName = typeByName;
   }

   public String name() {
      return this.name;
   }

   public boolean nillable() {
      return this.nillable;
   }

   public boolean required() {
      return this.required;
   }

   public String namespace() {
      return this.namespace;
   }

   public String defaultValue() {
      return this.defaultValue;
   }

   public Class type() {
      return XmlElement.DEFAULT.class;
   }

   public String getTypeByName() {
      return this.typeByName;
   }

   public String toString() {
      return "@XmlElementImpl(name=" + this.name + ",nillable=" + this.nillable + ",required=" + this.required + ",namespace=" + this.namespace + ",defaultValue=" + Utilities.safeString(this.defaultValue) + ",typeByName=" + this.typeByName + ",sid=" + System.identityHashCode(this) + ")";
   }
}
