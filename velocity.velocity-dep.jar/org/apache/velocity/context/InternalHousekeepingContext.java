package org.apache.velocity.context;

import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.util.introspection.IntrospectionCacheData;

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
