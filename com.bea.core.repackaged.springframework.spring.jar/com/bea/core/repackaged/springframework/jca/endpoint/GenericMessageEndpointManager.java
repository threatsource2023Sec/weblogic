package com.bea.core.repackaged.springframework.jca.endpoint;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.context.SmartLifecycle;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.endpoint.MessageEndpointFactory;

public class GenericMessageEndpointManager implements SmartLifecycle, InitializingBean, DisposableBean {
   @Nullable
   private ResourceAdapter resourceAdapter;
   @Nullable
   private MessageEndpointFactory messageEndpointFactory;
   @Nullable
   private ActivationSpec activationSpec;
   private boolean autoStartup = true;
   private int phase = Integer.MAX_VALUE;
   private volatile boolean running = false;
   private final Object lifecycleMonitor = new Object();

   public void setResourceAdapter(@Nullable ResourceAdapter resourceAdapter) {
      this.resourceAdapter = resourceAdapter;
   }

   @Nullable
   public ResourceAdapter getResourceAdapter() {
      return this.resourceAdapter;
   }

   public void setMessageEndpointFactory(@Nullable MessageEndpointFactory messageEndpointFactory) {
      this.messageEndpointFactory = messageEndpointFactory;
   }

   @Nullable
   public MessageEndpointFactory getMessageEndpointFactory() {
      return this.messageEndpointFactory;
   }

   public void setActivationSpec(@Nullable ActivationSpec activationSpec) {
      this.activationSpec = activationSpec;
   }

   @Nullable
   public ActivationSpec getActivationSpec() {
      return this.activationSpec;
   }

   public void setAutoStartup(boolean autoStartup) {
      this.autoStartup = autoStartup;
   }

   public boolean isAutoStartup() {
      return this.autoStartup;
   }

   public void setPhase(int phase) {
      this.phase = phase;
   }

   public int getPhase() {
      return this.phase;
   }

   public void afterPropertiesSet() throws ResourceException {
      if (this.getResourceAdapter() == null) {
         throw new IllegalArgumentException("Property 'resourceAdapter' is required");
      } else if (this.getMessageEndpointFactory() == null) {
         throw new IllegalArgumentException("Property 'messageEndpointFactory' is required");
      } else {
         ActivationSpec activationSpec = this.getActivationSpec();
         if (activationSpec == null) {
            throw new IllegalArgumentException("Property 'activationSpec' is required");
         } else {
            if (activationSpec.getResourceAdapter() == null) {
               activationSpec.setResourceAdapter(this.getResourceAdapter());
            } else if (activationSpec.getResourceAdapter() != this.getResourceAdapter()) {
               throw new IllegalArgumentException("ActivationSpec [" + activationSpec + "] is associated with a different ResourceAdapter: " + activationSpec.getResourceAdapter());
            }

         }
      }
   }

   public void start() {
      synchronized(this.lifecycleMonitor) {
         if (!this.running) {
            ResourceAdapter resourceAdapter = this.getResourceAdapter();
            Assert.state(resourceAdapter != null, "No ResourceAdapter set");

            try {
               resourceAdapter.endpointActivation(this.getMessageEndpointFactory(), this.getActivationSpec());
            } catch (ResourceException var5) {
               throw new IllegalStateException("Could not activate message endpoint", var5);
            }

            this.running = true;
         }

      }
   }

   public void stop() {
      synchronized(this.lifecycleMonitor) {
         if (this.running) {
            ResourceAdapter resourceAdapter = this.getResourceAdapter();
            Assert.state(resourceAdapter != null, "No ResourceAdapter set");
            resourceAdapter.endpointDeactivation(this.getMessageEndpointFactory(), this.getActivationSpec());
            this.running = false;
         }

      }
   }

   public void stop(Runnable callback) {
      synchronized(this.lifecycleMonitor) {
         this.stop();
         callback.run();
      }
   }

   public boolean isRunning() {
      return this.running;
   }

   public void destroy() {
      this.stop();
   }
}
