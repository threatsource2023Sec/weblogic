package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.persistence.PersistenceDocument;
import org.jcp.xmlns.xml.ns.persistence.PersistenceType;

public class PersistenceDocumentImpl extends XmlComplexContentImpl implements PersistenceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PERSISTENCE$0 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "persistence");

   public PersistenceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public PersistenceType getPersistence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceType target = null;
         target = (PersistenceType)this.get_store().find_element_user(PERSISTENCE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPersistence(PersistenceType persistence) {
      this.generatedSetterHelperImpl(persistence, PERSISTENCE$0, 0, (short)1);
   }

   public PersistenceType addNewPersistence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceType target = null;
         target = (PersistenceType)this.get_store().add_element_user(PERSISTENCE$0);
         return target;
      }
   }
}
