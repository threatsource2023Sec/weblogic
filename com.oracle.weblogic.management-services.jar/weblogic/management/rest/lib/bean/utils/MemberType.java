package weblogic.management.rest.lib.bean.utils;

public interface MemberType {
   BeanType getBeanType();

   String getName();

   Boolean getVisibleToPartitions();

   boolean isVisibleToRequest(InvocationContext var1);

   boolean isVisibleToLatestVersion();

   boolean isVisibleToVersion(int var1);

   boolean isInternal();
}
