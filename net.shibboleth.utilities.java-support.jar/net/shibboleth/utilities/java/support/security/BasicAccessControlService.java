package net.shibboleth.utilities.java.support.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.ServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicAccessControlService extends AbstractIdentifiableInitializableComponent implements AccessControlService {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BasicAccessControlService.class);
   @Nonnull
   @NonnullElements
   private Map policyMap = Collections.emptyMap();

   public void setPolicyMap(@Nonnull @NonnullElements Map map) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      Constraint.isNotNull(map, "Policy map cannot be null");
      this.policyMap = new HashMap(map.size());
      Iterator i$ = map.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         String trimmed = StringSupport.trimOrNull((String)entry.getKey());
         if (trimmed != null && entry.getValue() != null) {
            this.policyMap.put(trimmed, entry.getValue());
         }
      }

   }

   @Nonnull
   public AccessControl getInstance(@Nonnull String name) {
      AccessControl ac = (AccessControl)this.policyMap.get(name);
      if (ac != null) {
         return ac;
      } else {
         this.log.warn("Access Control Service {}: No policy named '{}' found, returning default denial policy", this.getId(), name);
         return new AccessControl() {
            public boolean checkAccess(ServletRequest request, String operation, String resource) {
               return false;
            }
         };
      }
   }
}
