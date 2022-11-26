package org.glassfish.hk2.runlevel;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.runlevel.internal.AsyncRunLevelContext;
import org.jvnet.hk2.annotations.Service;

@Service
@Named("DefaultRunLevelContext")
@Visibility(DescriptorVisibility.LOCAL)
public class RunLevelContext implements Context {
   public static final String CONTEXT_NAME = "DefaultRunLevelContext";
   private final AsyncRunLevelContext asyncRunLevelContext;

   @Inject
   private RunLevelContext(AsyncRunLevelContext asyncRunLevelContext) {
      this.asyncRunLevelContext = asyncRunLevelContext;
   }

   public Class getScope() {
      return RunLevel.class;
   }

   public Object findOrCreate(ActiveDescriptor activeDescriptor, ServiceHandle root) {
      return this.asyncRunLevelContext.findOrCreate(activeDescriptor, root);
   }

   public boolean containsKey(ActiveDescriptor descriptor) {
      return this.asyncRunLevelContext.containsKey(descriptor);
   }

   public void destroyOne(ActiveDescriptor descriptor) {
      this.asyncRunLevelContext.destroyOne(descriptor);
   }

   public boolean supportsNullCreation() {
      return false;
   }

   public boolean isActive() {
      return true;
   }

   public void shutdown() {
   }
}
