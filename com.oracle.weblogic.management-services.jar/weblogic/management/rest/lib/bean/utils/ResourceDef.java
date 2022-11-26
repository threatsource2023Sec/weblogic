package weblogic.management.rest.lib.bean.utils;

public class ResourceDef {
   private Class metaDataClass;
   private Class resourceClass;

   public static Class getResourceClass(ResourceDef def) {
      return def != null ? def.getResourceClass() : null;
   }

   public static Class getMetaDataClass(ResourceDef def) {
      return def != null ? def.getMetaDataClass() : null;
   }

   public static ResourceDef resourceDef(Class metaDataClass, Class resourceClass) {
      return new ResourceDef(metaDataClass, resourceClass);
   }

   private ResourceDef(Class metaDataClass, Class resourceClass) {
      this.metaDataClass = metaDataClass;
      this.resourceClass = resourceClass;
   }

   public Class getResourceClass() {
      return this.resourceClass;
   }

   public Class getMetaDataClass() {
      return this.metaDataClass;
   }
}
