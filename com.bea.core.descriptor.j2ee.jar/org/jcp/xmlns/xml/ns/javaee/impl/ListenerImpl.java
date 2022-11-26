package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ArtifactRef;
import org.jcp.xmlns.xml.ns.javaee.Listener;
import org.jcp.xmlns.xml.ns.javaee.Properties;

public class ListenerImpl extends XmlComplexContentImpl implements Listener {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTIES$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "properties");
   private static final QName REF$2 = new QName("", "ref");

   public ListenerImpl(SchemaType sType) {
      super(sType);
   }

   public Properties getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().find_element_user(PROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$0) != 0;
      }
   }

   public void setProperties(Properties properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$0, 0, (short)1);
   }

   public Properties addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().add_element_user(PROPERTIES$0);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$0, 0);
      }
   }

   public String getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public ArtifactRef xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArtifactRef target = null;
         target = (ArtifactRef)this.get_store().find_attribute_user(REF$2);
         return target;
      }
   }

   public void setRef(String ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$2);
         }

         target.setStringValue(ref);
      }
   }

   public void xsetRef(ArtifactRef ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArtifactRef target = null;
         target = (ArtifactRef)this.get_store().find_attribute_user(REF$2);
         if (target == null) {
            target = (ArtifactRef)this.get_store().add_attribute_user(REF$2);
         }

         target.set(ref);
      }
   }
}
