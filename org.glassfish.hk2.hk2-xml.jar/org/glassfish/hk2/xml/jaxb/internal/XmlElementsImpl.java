package org.glassfish.hk2.xml.jaxb.internal;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import org.glassfish.hk2.api.AnnotationLiteral;

public class XmlElementsImpl extends AnnotationLiteral implements XmlElements {
   private static final long serialVersionUID = -4972076348183489648L;
   private final XmlElement[] value;

   public XmlElementsImpl(XmlElement[] value) {
      this.value = value;
   }

   public XmlElement[] value() {
      return this.value;
   }

   public String toString() {
      return "@XmlElementsImpl(" + Arrays.toString(this.value) + "," + System.identityHashCode(this) + ")";
   }
}
