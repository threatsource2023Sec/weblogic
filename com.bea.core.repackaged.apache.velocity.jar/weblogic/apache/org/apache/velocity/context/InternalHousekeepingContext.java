package weblogic.apache.org.apache.velocity.context;

import weblogic.apache.org.apache.velocity.runtime.resource.Resource;
import weblogic.apache.org.apache.velocity.util.introspection.IntrospectionCacheData;

interface InternalHousekeepingContext {
   void pushCurrentTemplateName(String var1);

   void popCurrentTemplateName();

   String getCurrentTemplateName();

   Object[] getTemplateNameStack();

   IntrospectionCacheData icacheGet(Object var1);

   void icachePut(Object var1, IntrospectionCacheData var2);

   Resource getCurrentResource();

   void setCurrentResource(Resource var1);
}
