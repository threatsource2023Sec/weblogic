package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AddressingType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.PortComponentRefType;
import org.jcp.xmlns.xml.ns.javaee.RespectBindingType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;
import org.jcp.xmlns.xml.ns.javaee.XsdNonNegativeIntegerType;

public class PortComponentRefTypeImpl extends XmlComplexContentImpl implements PortComponentRefType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICEENDPOINTINTERFACE$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-endpoint-interface");
   private static final QName ENABLEMTOM$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "enable-mtom");
   private static final QName MTOMTHRESHOLD$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mtom-threshold");
   private static final QName ADDRESSING$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "addressing");
   private static final QName RESPECTBINDING$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "respect-binding");
   private static final QName PORTCOMPONENTLINK$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "port-component-link");
   private static final QName ID$12 = new QName("", "id");

   public PortComponentRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEENDPOINTINTERFACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceEndpointInterface(FullyQualifiedClassType serviceEndpointInterface) {
      this.generatedSetterHelperImpl(serviceEndpointInterface, SERVICEENDPOINTINTERFACE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEENDPOINTINTERFACE$0);
         return target;
      }
   }

   public TrueFalseType getEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEMTOM$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEMTOM$2) != 0;
      }
   }

   public void setEnableMtom(TrueFalseType enableMtom) {
      this.generatedSetterHelperImpl(enableMtom, ENABLEMTOM$2, 0, (short)1);
   }

   public TrueFalseType addNewEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEMTOM$2);
         return target;
      }
   }

   public void unsetEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEMTOM$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MTOMTHRESHOLD$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MTOMTHRESHOLD$4) != 0;
      }
   }

   public void setMtomThreshold(XsdNonNegativeIntegerType mtomThreshold) {
      this.generatedSetterHelperImpl(mtomThreshold, MTOMTHRESHOLD$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MTOMTHRESHOLD$4);
         return target;
      }
   }

   public void unsetMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MTOMTHRESHOLD$4, 0);
      }
   }

   public AddressingType getAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AddressingType target = null;
         target = (AddressingType)this.get_store().find_element_user(ADDRESSING$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADDRESSING$6) != 0;
      }
   }

   public void setAddressing(AddressingType addressing) {
      this.generatedSetterHelperImpl(addressing, ADDRESSING$6, 0, (short)1);
   }

   public AddressingType addNewAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AddressingType target = null;
         target = (AddressingType)this.get_store().add_element_user(ADDRESSING$6);
         return target;
      }
   }

   public void unsetAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADDRESSING$6, 0);
      }
   }

   public RespectBindingType getRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RespectBindingType target = null;
         target = (RespectBindingType)this.get_store().find_element_user(RESPECTBINDING$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPECTBINDING$8) != 0;
      }
   }

   public void setRespectBinding(RespectBindingType respectBinding) {
      this.generatedSetterHelperImpl(respectBinding, RESPECTBINDING$8, 0, (short)1);
   }

   public RespectBindingType addNewRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RespectBindingType target = null;
         target = (RespectBindingType)this.get_store().add_element_user(RESPECTBINDING$8);
         return target;
      }
   }

   public void unsetRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPECTBINDING$8, 0);
      }
   }

   public String getPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTCOMPONENTLINK$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTCOMPONENTLINK$10) != 0;
      }
   }

   public void setPortComponentLink(String portComponentLink) {
      this.generatedSetterHelperImpl(portComponentLink, PORTCOMPONENTLINK$10, 0, (short)1);
   }

   public String addNewPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTCOMPONENTLINK$10);
         return target;
      }
   }

   public void unsetPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTCOMPONENTLINK$10, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
