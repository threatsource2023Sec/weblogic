package org.opensaml.saml.common.messaging.context.navigate;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.AbstractAuthenticatableSAMLEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;

public class SAMLMessageContextAuthenticationFunction implements Function {
   @Nonnull
   private Class entityContextClass = SAMLPeerEntityContext.class;

   public void setEntityContextClass(@Nonnull Class clazz) {
      this.entityContextClass = (Class)Constraint.isNotNull(clazz, "The SAML entity context class may not be null");
   }

   @Nullable
   public Boolean apply(@Nullable MessageContext input) {
      if (input != null) {
         AbstractAuthenticatableSAMLEntityContext entityCtx = (AbstractAuthenticatableSAMLEntityContext)input.getSubcontext(this.entityContextClass);
         if (entityCtx != null) {
            return entityCtx.isAuthenticated();
         }
      }

      return null;
   }
}
