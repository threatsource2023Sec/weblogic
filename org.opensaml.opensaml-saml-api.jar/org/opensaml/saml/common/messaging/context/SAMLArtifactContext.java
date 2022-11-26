package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;

public class SAMLArtifactContext extends BaseContext {
   @Nullable
   private byte[] artifactType;
   @Nullable
   @NotEmpty
   private String sourceEntityId;
   @Nullable
   @NotEmpty
   private String sourceArtifactResolutionServiceEndpointUrl;
   @Nullable
   private Integer sourceArtifactResolutionServiceEndpointIndex;

   @Nullable
   public byte[] getArtifactType() {
      return this.artifactType;
   }

   public void setArtifactType(@Nullable byte[] type) {
      this.artifactType = type;
   }

   @Nullable
   @NotEmpty
   public String getSourceEntityId() {
      if (this.sourceEntityId == null && this.getParent() != null) {
         SAMLSelfEntityContext self = (SAMLSelfEntityContext)this.getParent().getSubcontext(SAMLSelfEntityContext.class);
         if (self != null) {
            this.sourceEntityId = self.getEntityId();
         }
      }

      return this.sourceEntityId;
   }

   public void setSourceEntityId(@Nullable String entityId) {
      this.sourceEntityId = StringSupport.trimOrNull(entityId);
   }

   @Nullable
   @NotEmpty
   public String getSourceArtifactResolutionServiceEndpointURL() {
      return this.sourceArtifactResolutionServiceEndpointUrl;
   }

   public void setSourceArtifactResolutionServiceEndpointURL(@Nullable String url) {
      this.sourceArtifactResolutionServiceEndpointUrl = StringSupport.trimOrNull(url);
   }

   @Nullable
   public Integer getSourceArtifactResolutionServiceEndpointIndex() {
      return this.sourceArtifactResolutionServiceEndpointIndex;
   }

   public void setSourceArtifactResolutionServiceEndpointIndex(@Nullable Integer index) {
      this.sourceArtifactResolutionServiceEndpointIndex = index;
   }
}
