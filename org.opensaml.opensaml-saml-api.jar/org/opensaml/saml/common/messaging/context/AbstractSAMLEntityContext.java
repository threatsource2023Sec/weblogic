package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;

public abstract class AbstractSAMLEntityContext extends BaseContext {
   @Nullable
   @NotEmpty
   private String entityId;
   @Nullable
   private QName role;

   @Nullable
   @NotEmpty
   public String getEntityId() {
      return this.entityId;
   }

   public void setEntityId(@Nullable String id) {
      this.entityId = StringSupport.trimOrNull(id);
   }

   @Nullable
   public QName getRole() {
      return this.role;
   }

   public void setRole(@Nullable QName newRole) {
      this.role = newRole;
   }
}
