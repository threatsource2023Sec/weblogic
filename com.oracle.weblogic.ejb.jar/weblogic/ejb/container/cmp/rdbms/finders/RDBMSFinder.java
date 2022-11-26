package weblogic.ejb.container.cmp.rdbms.finders;

import java.lang.reflect.Method;
import java.util.Map;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.j2ee.descriptor.wl.MethodParamsBean;
import weblogic.j2ee.descriptor.wl.QueryMethodBean;
import weblogic.j2ee.descriptor.wl.WeblogicQueryBean;

public final class RDBMSFinder {
   private String finderName;
   private String[] finderParams;
   private String ejbQlQuery;
   private String groupName;
   private String cachingName;
   private String sqlShapeName;
   private Map sqlQueries;
   private int maxElements = 0;
   private boolean includeUpdates = true;
   private boolean sqlSelectDistinct = false;
   private boolean queryCachingEnabled = false;
   private boolean enableEagerRefresh = false;
   private boolean includeResultCacheHint = false;

   public void setFinderName(String finderName) {
      this.finderName = finderName;
   }

   public String getFinderName() {
      return this.finderName;
   }

   public void setFinderParams(String[] finderParams) {
      this.finderParams = finderParams;
   }

   public String[] getFinderParams() {
      return this.finderParams;
   }

   public void setEjbQlQuery(String finderQuery) {
      this.ejbQlQuery = finderQuery;
   }

   public String getEjbQlQuery() {
      return this.ejbQlQuery;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public String getGroupName() {
      return this.groupName;
   }

   public void setCachingName(String cachingName) {
      this.cachingName = cachingName;
   }

   public String getCachingName() {
      return this.cachingName;
   }

   public int getMaxElements() {
      return this.maxElements;
   }

   public void setMaxElements(int maxElements) {
      this.maxElements = maxElements;
   }

   public void setIncludeUpdates(boolean includeUpdates) {
      this.includeUpdates = includeUpdates;
   }

   public boolean getIncludeUpdates() {
      return this.includeUpdates;
   }

   public void setSqlSelectDistinct(boolean sqlSelectDistinct) {
      this.sqlSelectDistinct = sqlSelectDistinct;
   }

   public boolean getSqlSelectDistinct() {
      return this.sqlSelectDistinct;
   }

   public void setQueryCachingEnabled(boolean queryCachingEnabled) {
      this.queryCachingEnabled = queryCachingEnabled;
   }

   public boolean isQueryCachingEnabled() {
      return this.queryCachingEnabled;
   }

   public void setEnableEagerRefresh(boolean enableEagerRefresh) {
      this.enableEagerRefresh = enableEagerRefresh;
   }

   public boolean isEnableEagerRefresh() {
      return this.enableEagerRefresh;
   }

   public void setIncludeResultCacheHint(boolean includeResultCacheHint) {
      this.includeResultCacheHint = includeResultCacheHint;
   }

   public boolean isIncludeResultCacheHint() {
      return this.includeResultCacheHint;
   }

   public String toString() {
      return DDUtils.getMethodSignature(this.finderName, this.finderParams);
   }

   public String getSqlShapeName() {
      return this.sqlShapeName;
   }

   public void setSqlShapeName(String string) {
      this.sqlShapeName = string;
   }

   public Map getSqlQueries() {
      return this.sqlQueries;
   }

   public void setSqlQueries(Map sqlQueries) {
      this.sqlQueries = sqlQueries;
   }

   public boolean usesSql() {
      return this.sqlQueries != null;
   }

   public static class FinderKey {
      private String finderName;
      private String[] finderParams;

      public FinderKey(String finderName, String[] finderParams) {
         this.finderName = finderName;
         this.finderParams = finderParams;
         if (finderName == null) {
            this.finderName = "";
         }

         if (finderParams == null) {
            this.finderParams = new String[0];
         }

      }

      public FinderKey(RDBMSFinder finder) {
         this.finderName = finder.getFinderName();
         String[] finderParamsTemp = finder.getFinderParams();
         if (finderParamsTemp == null) {
            finderParamsTemp = new String[0];
         }

         this.finderParams = new String[finderParamsTemp.length];

         for(int i = 0; i < finderParamsTemp.length; ++i) {
            this.finderParams[i] = MethodUtils.decodePrimitiveTypeArrayMaybe(finderParamsTemp[i]);
         }

      }

      public FinderKey(Finder finder) {
         this.finderName = finder.getName();
         String[] parameterClassNames = finder.getParameterClassNames();
         if (parameterClassNames == null) {
            parameterClassNames = new String[0];
         }

         this.finderParams = new String[parameterClassNames.length];

         for(int i = 0; i < parameterClassNames.length; ++i) {
            this.finderParams[i] = MethodUtils.decodePrimitiveTypeArrayMaybe(parameterClassNames[i]);
         }

      }

      public FinderKey(Method method) {
         String methodName = method.getName();
         if (methodName.startsWith("ejbFind")) {
            this.finderName = MethodUtils.convertToDDFinderName(methodName);
         } else {
            this.finderName = methodName;
         }

         Class[] paramTypes = method.getParameterTypes();
         this.finderParams = MethodUtils.classesToJavaSourceTypes(paramTypes);
      }

      public FinderKey(WeblogicQueryBean mbean) {
         QueryMethodBean queryMbean = mbean.getQueryMethod();
         if (queryMbean == null) {
            this.finderName = "";
            this.finderParams = new String[0];
         } else {
            this.finderName = queryMbean.getMethodName();
            if (this.finderName == null) {
               this.finderName = "";
            }

            MethodParamsBean paramMbean = queryMbean.getMethodParams();
            if (paramMbean == null) {
               this.finderParams = new String[0];
            } else {
               this.finderParams = paramMbean.getMethodParams();
               if (this.finderParams == null) {
                  this.finderParams = new String[0];
               }
            }
         }

      }

      public void setFinderName(String finderName) {
         this.finderName = finderName;
      }

      public String getFinderName() {
         return this.finderName;
      }

      public void setFinderParams(String[] finderParams) {
         this.finderParams = finderParams;
      }

      public String[] getFinderParams() {
         return this.finderParams;
      }

      public boolean equals(Object other) {
         if (!(other instanceof FinderKey)) {
            return false;
         } else {
            FinderKey otherKey = (FinderKey)other;
            if (!this.finderName.equals(otherKey.getFinderName())) {
               return false;
            } else {
               String[] otherParams = otherKey.getFinderParams();
               if (this.finderParams.length != otherParams.length) {
                  return false;
               } else {
                  for(int i = 0; i < otherParams.length; ++i) {
                     if (!this.finderParams[i].equals(otherParams[i])) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      }

      public int hashCode() {
         return this.finderName.hashCode();
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("[FinderKey: " + this.getFinderName() + "(");
         if (this.finderParams != null) {
            for(int i = 0; i < this.finderParams.length; ++i) {
               sb.append(this.finderParams[i]);
               if (i < this.finderParams.length - 1) {
                  sb.append(", ");
               }
            }
         }

         sb.append(")]");
         return sb.toString();
      }
   }
}
