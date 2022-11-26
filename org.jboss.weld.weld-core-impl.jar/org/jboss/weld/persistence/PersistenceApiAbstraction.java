package org.jboss.weld.persistence;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ApiAbstraction;

public class PersistenceApiAbstraction extends ApiAbstraction implements Service {
   public final Class PERSISTENCE_CONTEXT_ANNOTATION_CLASS = this.annotationTypeForName("javax.persistence.PersistenceContext");
   public final Class PERSISTENCE_UNIT_ANNOTATION_CLASS = this.annotationTypeForName("javax.persistence.PersistenceUnit");
   public final Object EXTENDED_PERSISTENCE_CONTEXT_ENUM_VALUE;
   public final Class PERSISTENCE_CONTEXT_TYPE_CLASS = this.classForName("javax.persistence.PersistenceContextType");
   public final Class ENTITY_CLASS;
   public final Class MAPPED_SUPERCLASS_CLASS;
   public final Class EMBEDDABLE_CLASS;
   public final Class ENTITY_MANAGER_CLASS;
   public final Class ENTITY_MANAGER_FACTORY_CLASS;
   public final String SESSION_NAME = "org.hibernate.Session";
   public final String SESSION_FACTORY_NAME = "org.hibernate.SessionFactory";

   public PersistenceApiAbstraction(ResourceLoader resourceLoader) {
      super(resourceLoader);
      if (this.PERSISTENCE_CONTEXT_TYPE_CLASS.getClass().equals(ApiAbstraction.Dummy.class)) {
         this.EXTENDED_PERSISTENCE_CONTEXT_ENUM_VALUE = this.enumValue(this.PERSISTENCE_CONTEXT_TYPE_CLASS, "EXTENDED");
      } else {
         this.EXTENDED_PERSISTENCE_CONTEXT_ENUM_VALUE = ApiAbstraction.DummyEnum.DUMMY_VALUE;
      }

      this.ENTITY_CLASS = this.annotationTypeForName("javax.persistence.Entity");
      this.MAPPED_SUPERCLASS_CLASS = this.annotationTypeForName("javax.persistence.MappedSuperclass");
      this.EMBEDDABLE_CLASS = this.annotationTypeForName("javax.persistence.Embeddable");
      this.ENTITY_MANAGER_CLASS = this.classForName("javax.persistence.EntityManager");
      this.ENTITY_MANAGER_FACTORY_CLASS = this.classForName("javax.persistence.EntityManagerFactory");
   }

   public void cleanup() {
   }
}
