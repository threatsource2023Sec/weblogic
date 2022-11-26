package weblogic.management.rest.lib.bean.utils;

class CustomResourceTypeImpl extends MemberTypeImpl implements CustomResourceType {
   private ResourceDefs defs;

   CustomResourceTypeImpl(BeanType beanType, String name, Boolean visibleToPartitions, Boolean internal, ResourceDefs defs) throws Exception {
      super(beanType, (VersionVisibility)null, name, visibleToPartitions, internal);
      this.defs = defs;
   }

   public ResourceDef getResourceDef(String beanTree) throws Exception {
      return this.defs.get(beanTree);
   }
}
