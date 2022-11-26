package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.DeployAsAWholeDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class DeployAsAWholeDocumentImpl extends XmlComplexContentImpl implements DeployAsAWholeDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DEPLOYASAWHOLE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "deploy-as-a-whole");

   public DeployAsAWholeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getDeployAsAWhole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEPLOYASAWHOLE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDeployAsAWhole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEPLOYASAWHOLE$0, 0);
         return target;
      }
   }

   public void setDeployAsAWhole(boolean deployAsAWhole) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEPLOYASAWHOLE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEPLOYASAWHOLE$0);
         }

         target.setBooleanValue(deployAsAWhole);
      }
   }

   public void xsetDeployAsAWhole(XmlBoolean deployAsAWhole) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEPLOYASAWHOLE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DEPLOYASAWHOLE$0);
         }

         target.set(deployAsAWhole);
      }
   }
}
