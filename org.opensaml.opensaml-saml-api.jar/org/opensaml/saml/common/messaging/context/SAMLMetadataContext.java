package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class SAMLMetadataContext extends BaseContext {
   @Nullable
   private EntityDescriptor entityDescriptor;
   @Nullable
   private transient RoleDescriptor roleDescriptor;

   @Nullable
   public EntityDescriptor getEntityDescriptor() {
      return this.entityDescriptor;
   }

   public void setEntityDescriptor(@Nullable EntityDescriptor descriptor) {
      this.entityDescriptor = descriptor;
   }

   @Nullable
   public RoleDescriptor getRoleDescriptor() {
      return this.roleDescriptor;
   }

   public void setRoleDescriptor(@Nullable RoleDescriptor descriptor) {
      this.roleDescriptor = descriptor;
   }
}
