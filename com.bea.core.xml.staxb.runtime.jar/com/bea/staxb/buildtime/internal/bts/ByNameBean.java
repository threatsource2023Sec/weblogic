package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public class ByNameBean extends BindingType {
   private static final long serialVersionUID = 1L;
   private List props = new ArrayList();
   private transient Map eltProps = new HashMap();
   private transient Map attProps = new HashMap();
   private GenericXmlProperty anyElement;
   private GenericXmlProperty anyAttribute;

   public ByNameBean() {
   }

   public ByNameBean(BindingTypeName btName) {
      super(btName);
   }

   public GenericXmlProperty getAnyElementProperty() {
      return this.anyElement;
   }

   public void setAnyElementProperty(GenericXmlProperty prop) {
      this.anyElement = prop;
   }

   public GenericXmlProperty getAnyAttributeProperty() {
      return this.anyAttribute;
   }

   public void setAnyAttributeProperty(GenericXmlProperty prop) {
      this.anyAttribute = prop;
   }

   public Collection getProperties() {
      return Collections.unmodifiableCollection(this.props);
   }

   public QNameProperty getPropertyForAttribute(QName name) {
      this.initializePropertyMappings();
      return (QNameProperty)this.attProps.get(name);
   }

   public QNameProperty getPropertyForElement(QName name) {
      this.initializePropertyMappings();
      return (QNameProperty)this.eltProps.get(name);
   }

   public void addProperty(QNameProperty newProp) {
      this.initializePropertyMappings();
      if (newProp.getQName() == null) {
         throw new IllegalArgumentException(newProp + " has null qname");
      } else if (this.hasProperty(newProp)) {
         throw new IllegalArgumentException("duplicate property: " + newProp.getQName() + " in type " + this);
      } else {
         this.props.add(newProp);
         if (newProp.isAttribute()) {
            this.attProps.put(newProp.getQName(), newProp);
         } else {
            this.eltProps.put(newProp.getQName(), newProp);
         }

      }
   }

   public boolean hasProperty(QNameProperty newProp) {
      QName prop_qname = newProp.getQName();
      this.initializePropertyMappings();
      return newProp.isAttribute() ? this.attProps.containsKey(prop_qname) : this.eltProps.containsKey(prop_qname);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public String toString() {
      return "com.bea.staxb.buildtime.internal.bts.ByNameBean{" + super.toString() + "-props=" + (this.props == null ? null : "size:" + this.props.size() + this.props) + "}";
   }

   private synchronized void initializePropertyMappings() {
      if (this.attProps == null && this.eltProps == null) {
         this.attProps = new HashMap();
         this.eltProps = new HashMap();
         if (this.props != null) {
            Iterator i = this.props.iterator();

            while(i.hasNext()) {
               QNameProperty prop = (QNameProperty)i.next();
               if (prop.isAttribute()) {
                  this.attProps.put(prop.getQName(), prop);
               } else {
                  this.eltProps.put(prop.getQName(), prop);
               }
            }

         }
      }
   }
}
