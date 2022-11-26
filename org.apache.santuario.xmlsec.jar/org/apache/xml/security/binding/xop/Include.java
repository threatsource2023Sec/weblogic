package org.apache.xml.security.binding.xop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Include",
   namespace = "http://www.w3.org/2004/08/xop/include",
   propOrder = {"any"}
)
public class Include {
   @XmlAnyElement(
      lax = true
   )
   protected List any;
   @XmlAttribute(
      name = "href",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String href;
   @XmlAnyAttribute
   private Map otherAttributes = new HashMap();

   public List getAny() {
      if (this.any == null) {
         this.any = new ArrayList();
      }

      return this.any;
   }

   public String getHref() {
      return this.href;
   }

   public void setHref(String value) {
      this.href = value;
   }

   public Map getOtherAttributes() {
      return this.otherAttributes;
   }
}
