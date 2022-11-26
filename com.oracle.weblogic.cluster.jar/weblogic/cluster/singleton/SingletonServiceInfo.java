package weblogic.cluster.singleton;

import weblogic.work.StackableThreadContext;

public class SingletonServiceInfo {
   private final String name;
   private final SingletonService service;
   private final StackableThreadContext context;
   private final boolean internalService;
   private volatile boolean activated = false;
   private volatile boolean removed = false;

   public boolean isRemoved() {
      return this.removed;
   }

   public void setRemoved() {
      this.removed = true;
   }

   public SingletonServiceInfo(String name, SingletonService service, StackableThreadContext context, boolean internalService) {
      this.name = name;
      this.service = service;
      this.context = context;
      this.internalService = internalService;
   }

   public SingletonService getService() {
      return this.service;
   }

   public StackableThreadContext getContext() {
      return this.context;
   }

   public String getName() {
      return this.name;
   }

   public boolean isActivated() {
      return this.activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public boolean isInternalService() {
      return this.internalService;
   }
}
