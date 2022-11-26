package org.opensaml.saml.security.impl;

import java.util.List;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.credential.CredentialContext;

public class SAMLMDCredentialContext implements CredentialContext {
   private KeyDescriptor keyDescriptor;
   private RoleDescriptor role;
   private List encMethods;

   public SAMLMDCredentialContext(KeyDescriptor descriptor) {
      this.keyDescriptor = descriptor;
      if (descriptor != null) {
         this.encMethods = descriptor.getEncryptionMethods();
         this.role = (RoleDescriptor)descriptor.getParent();
      }

   }

   public KeyDescriptor getKeyDescriptor() {
      return this.keyDescriptor;
   }

   public List getEncryptionMethods() {
      return this.encMethods;
   }

   public RoleDescriptor getRoleDescriptor() {
      return this.role;
   }
}
