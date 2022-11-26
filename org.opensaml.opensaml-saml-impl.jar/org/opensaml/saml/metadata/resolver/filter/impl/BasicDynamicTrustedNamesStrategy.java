package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Function;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class BasicDynamicTrustedNamesStrategy implements Function {
   @Nonnull
   @NonnullElements
   public Set apply(@Nullable XMLObject input) {
      if (input == null) {
         return Collections.emptySet();
      } else {
         Set rawResult = null;
         if (input instanceof EntityDescriptor) {
            rawResult = Collections.singleton(((EntityDescriptor)input).getEntityID());
         } else if (input instanceof EntitiesDescriptor) {
            rawResult = Collections.singleton(((EntitiesDescriptor)input).getName());
         } else {
            XMLObject parent;
            if (input instanceof RoleDescriptor) {
               parent = input.getParent();
               if (parent instanceof EntityDescriptor) {
                  rawResult = Collections.singleton(((EntityDescriptor)parent).getEntityID());
               }
            } else if (input instanceof AffiliationDescriptor) {
               rawResult = new HashSet();
               ((Set)rawResult).add(((AffiliationDescriptor)input).getOwnerID());
               parent = input.getParent();
               if (parent instanceof EntityDescriptor) {
                  ((Set)rawResult).add(((EntityDescriptor)parent).getEntityID());
               }
            }
         }

         return (Set)(rawResult != null ? new HashSet(StringSupport.normalizeStringCollection((Collection)rawResult)) : Collections.emptySet());
      }
   }
}
