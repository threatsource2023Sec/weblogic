package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponent;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponentFactory;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractArchiveBasedProvisioningComponentFactory implements ProvisioningComponentFactory {
   protected AbstractArchiveBasedProvisioningComponentFactory() {
   }

   protected abstract Set getRoots() throws ProvisioningException;

   protected abstract ProvisioningComponentDescriptor getProvisioningComponentDescriptor(URI var1) throws ProvisioningException;

   protected abstract Set getProvisioningResources(ProvisioningComponentDescriptor var1) throws ProvisioningException;

   protected abstract ProvisioningComponent createProvisioningComponent(ProvisioningComponentDescriptor var1, Set var2) throws ProvisioningException;

   public Set getProvisioningComponents() throws ProvisioningException {
      Set returnValue = new HashSet();
      Set roots = this.getRoots();
      if (roots != null && !roots.isEmpty()) {
         Iterator var3 = roots.iterator();

         while(var3.hasNext()) {
            URI root = (URI)var3.next();
            if (root != null) {
               ProvisioningComponentDescriptor descriptor = this.getProvisioningComponentDescriptor(root);
               if (descriptor != null) {
                  Set provisioningResources = this.getProvisioningResources(descriptor);
                  ProvisioningComponent provisioningComponent = this.createProvisioningComponent(descriptor, provisioningResources);
                  if (provisioningComponent != null) {
                     returnValue.add(provisioningComponent);
                  }
               }
            }
         }
      }

      return Collections.unmodifiableSet(returnValue);
   }

   public static final class ProvisioningComponentDescriptor {
      private final ProvisioningComponentIdentifier id;
      private final URI root;
      private final String description;
      private final boolean selectable;

      public ProvisioningComponentDescriptor(ProvisioningComponentIdentifier id, URI root, String description, boolean selectable) {
         Objects.requireNonNull(id);
         Objects.requireNonNull(root);
         if (id.getName() == null) {
            throw new IllegalArgumentException("id", new IllegalStateException("id.getName() == null"));
         } else {
            this.id = id;
            this.root = root;
            this.description = description;
            this.selectable = selectable;
         }
      }

      public final ProvisioningComponentIdentifier getId() {
         return this.id;
      }

      public final URI getRoot() {
         return this.root;
      }

      public final String getDescription() {
         return this.description;
      }

      public final boolean isSelectable() {
         return this.selectable;
      }
   }
}
