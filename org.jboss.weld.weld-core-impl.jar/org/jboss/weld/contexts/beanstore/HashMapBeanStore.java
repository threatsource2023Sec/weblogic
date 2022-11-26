package org.jboss.weld.contexts.beanstore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public class HashMapBeanStore extends AbstractMapBackedBeanStore implements Serializable {
   private static final long serialVersionUID = 4770689245633688471L;
   protected Map delegate = new HashMap();

   public Map delegate() {
      return this.delegate;
   }

   public LockedBean lock(BeanIdentifier id) {
      return null;
   }
}
