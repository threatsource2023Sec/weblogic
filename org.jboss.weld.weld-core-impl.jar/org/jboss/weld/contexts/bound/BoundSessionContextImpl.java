package org.jboss.weld.contexts.bound;

import java.util.Map;
import javax.enterprise.context.SessionScoped;
import org.jboss.weld.context.bound.BoundSessionContext;
import org.jboss.weld.contexts.AbstractBoundContext;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SessionMapBeanStore;
import org.jboss.weld.contexts.beanstore.SimpleBeanIdentifierIndexNamingScheme;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.serialization.BeanIdentifierIndex;

public class BoundSessionContextImpl extends AbstractBoundContext implements BoundSessionContext {
   static final String NAMING_SCHEME_PREFIX = "WELD_BS";
   static final String KEY_BEAN_ID_INDEX_HASH = "WELD_BS_HASH";
   private final NamingScheme namingScheme;

   public BoundSessionContextImpl(String contextId, BeanIdentifierIndex index) {
      super(contextId, true);
      this.namingScheme = new SimpleBeanIdentifierIndexNamingScheme("WELD_BS", index);
   }

   public Class getScope() {
      return SessionScoped.class;
   }

   public boolean associate(Map storage) {
      if (this.getBeanStore() == null) {
         this.setBeanStore(new SessionMapBeanStore(this.namingScheme, storage));
         this.checkBeanIdentifierIndexConsistency(storage);
         return true;
      } else {
         return false;
      }
   }

   private void checkBeanIdentifierIndexConsistency(Map storage) {
      BeanIdentifierIndex index = (BeanIdentifierIndex)this.getServiceRegistry().get(BeanIdentifierIndex.class);
      if (index != null && index.isBuilt()) {
         Object hash = storage.get("WELD_BS_HASH");
         if (hash != null) {
            if (!index.getIndexHash().equals(hash)) {
               throw ContextLogger.LOG.beanIdentifierIndexInconsistencyDetected(hash.toString(), index.getDebugInfo());
            }
         } else {
            storage.put("WELD_BS_HASH", index.getIndexHash());
         }
      }

   }
}
