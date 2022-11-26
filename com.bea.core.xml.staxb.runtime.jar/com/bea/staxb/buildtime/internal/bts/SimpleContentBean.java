package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public class SimpleContentBean extends BindingType {
   private SimpleContentProperty simpleContentProperty;
   private GenericXmlProperty anyAttributeProperty;
   private Map attProps = new LinkedHashMap();
   private static final long serialVersionUID = 1L;

   public SimpleContentBean() {
   }

   public SimpleContentBean(BindingTypeName btName) {
      super(btName);
   }

   public QNameProperty getPropertyForAttribute(QName name) {
      return (QNameProperty)this.attProps.get(name);
   }

   public Collection getAttributeProperties() {
      return Collections.unmodifiableCollection(this.attProps.values());
   }

   public SimpleContentProperty getSimpleContentProperty() {
      return this.simpleContentProperty;
   }

   public void setSimpleContentProperty(SimpleContentProperty simpleContentProperty) {
      this.simpleContentProperty = simpleContentProperty;
   }

   public GenericXmlProperty getAnyAttributeProperty() {
      return this.anyAttributeProperty;
   }

   public void setAnyAttributeProperty(GenericXmlProperty prop) {
      this.anyAttributeProperty = prop;
   }

   public void addProperty(QNameProperty newProp) {
      boolean att = newProp.isAttribute();
      if (!att) {
         String msg = "property must be an attribute: " + newProp;
         throw new IllegalArgumentException(msg);
      } else if (this.attProps.containsKey(newProp.getQName())) {
         throw new IllegalArgumentException("duplicate property: " + newProp);
      } else {
         this.attProps.put(newProp.getQName(), newProp);
      }
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }
}
