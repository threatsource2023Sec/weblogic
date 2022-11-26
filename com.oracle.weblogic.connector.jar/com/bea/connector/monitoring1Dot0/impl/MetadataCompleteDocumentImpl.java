package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.MetadataCompleteDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class MetadataCompleteDocumentImpl extends XmlComplexContentImpl implements MetadataCompleteDocument {
   private static final long serialVersionUID = 1L;
   private static final QName METADATACOMPLETE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "metadata-complete");

   public MetadataCompleteDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METADATACOMPLETE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(METADATACOMPLETE$0, 0);
         return target;
      }
   }

   public void setMetadataComplete(boolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METADATACOMPLETE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(METADATACOMPLETE$0);
         }

         target.setBooleanValue(metadataComplete);
      }
   }

   public void xsetMetadataComplete(XmlBoolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(METADATACOMPLETE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(METADATACOMPLETE$0);
         }

         target.set(metadataComplete);
      }
   }
}
