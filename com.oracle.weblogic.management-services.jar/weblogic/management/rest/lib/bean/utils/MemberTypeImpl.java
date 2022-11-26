package weblogic.management.rest.lib.bean.utils;

abstract class MemberTypeImpl implements MemberType {
   private BeanType beanType;
   private String name;
   private boolean visibleToPartitionsSet;
   private Boolean visibleToPartitions;
   private VersionVisibility versionVisibility;
   private boolean internal;

   protected MemberTypeImpl(BeanType beanType, VersionVisibility versionVisibility, String name, Boolean visibleToPartitions, boolean internal) throws Exception {
      this(beanType, name);
      this.setVisibleToPartitions(visibleToPartitions);
      this.versionVisibility = versionVisibility;
      this.internal = internal;
   }

   protected MemberTypeImpl(BeanType beanType, String name) throws Exception {
      this.visibleToPartitionsSet = false;
      this.internal = false;
      this.beanType = beanType;
      this.name = name;
   }

   protected boolean isVisibleToPartitionsSet() {
      return this.visibleToPartitionsSet;
   }

   protected void setVisibleToPartitions(Boolean visibleToPartitions) {
      if (this.isVisibleToPartitionsSet()) {
         throw new AssertionError("VisibleToPartitions already set.  Type: " + this.beanType.getName() + " name: " + this.getName());
      } else {
         this.visibleToPartitions = visibleToPartitions;
         this.visibleToPartitionsSet = true;
      }
   }

   public BeanType getBeanType() {
      return this.beanType;
   }

   public String getName() {
      return this.name;
   }

   public Boolean getVisibleToPartitions() {
      if (!this.isVisibleToPartitionsSet()) {
         throw new AssertionError("VisibleToPartitions not set.  Type: " + this.beanType.getName() + " name: " + this.getName());
      } else {
         return this.visibleToPartitions;
      }
   }

   public boolean isVisibleToRequest(InvocationContext ic) {
      return this.isInternal() ? ic.exposeInternals() : this.isVisibleToVersion(ic.versionNumber());
   }

   public boolean isVisibleToLatestVersion() {
      return this.internal ? false : this.isVisibleToVersion(SupportedVersions.LATEST_VERSION_NUMBER);
   }

   public boolean isVisibleToVersion(int versionNumber) {
      return this.versionVisibility != null ? this.versionVisibility.isVisible(versionNumber) : true;
   }

   public boolean isInternal() {
      return this.internal;
   }
}
