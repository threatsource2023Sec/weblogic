package weblogic.ejb.container.persistence;

import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.Relationships;

public final class RelationshipsImpl implements Relationships {
   protected String[] description;
   protected Map ejbEntityRefs = new HashMap();
   protected Map ejbRelations = new HashMap();

   public void setDescriptions(String[] d) {
      this.description = d;
   }

   public String[] getDescriptions() {
      return this.description;
   }

   public void addEjbEntityRef(EjbEntityRef ref) {
      this.ejbEntityRefs.put(ref.getRemoteEjbName(), ref);
   }

   public EjbEntityRef removeEjbEntityRef(String remoteName) {
      return (EjbEntityRef)this.ejbEntityRefs.remove(remoteName);
   }

   public EjbEntityRef getEjbEntityRef(String remoteName) {
      return (EjbEntityRef)this.ejbEntityRefs.get(remoteName);
   }

   public Map getAllEjbEntityRefs() {
      return this.ejbEntityRefs;
   }

   public void addEjbRelation(EjbRelation rel) {
      this.ejbRelations.put(rel.getEjbRelationName(), rel);
   }

   public EjbRelation removeEjbRelation(String relName) {
      return (EjbRelation)this.ejbRelations.remove(relName);
   }

   public EjbRelation getEjbRelation(String relName) {
      return (EjbRelation)this.ejbRelations.get(relName);
   }

   public Map getAllEjbRelations() {
      return this.ejbRelations;
   }
}
