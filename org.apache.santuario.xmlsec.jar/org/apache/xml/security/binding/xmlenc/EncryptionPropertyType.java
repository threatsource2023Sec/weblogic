package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EncryptionPropertyType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"content"}
)
public class EncryptionPropertyType {
   @XmlMixed
   @XmlAnyElement(
      lax = true
   )
   protected List content;
   @XmlAttribute(
      name = "Target"
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String target;
   @XmlAttribute(
      name = "Id"
   )
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlID
   @XmlSchemaType(
      name = "ID"
   )
   protected String id;
   @XmlAnyAttribute
   private Map otherAttributes = new HashMap();

   public List getContent() {
      if (this.content == null) {
         this.content = new ArrayList();
      }

      return this.content;
   }

   public String getTarget() {
      return this.target;
   }

   public void setTarget(String value) {
      this.target = value;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String value) {
      this.id = value;
   }

   public Map getOtherAttributes() {
      return this.otherAttributes;
   }
}
