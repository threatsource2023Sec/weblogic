package weblogic.ejb.container.deployer;

import weblogic.ejb.container.interfaces.EntityBeanQuery;
import weblogic.j2ee.descriptor.QueryBean;

public final class EntityBeanQueryImpl implements EntityBeanQuery {
   private final QueryBean qmb;

   public EntityBeanQueryImpl(QueryBean qmb) {
      this.qmb = qmb;
   }

   public String getDescription() {
      return this.qmb.getDescription();
   }

   public String getMethodSignature() {
      StringBuilder result = (new StringBuilder(this.getMethodName())).append("(");
      String[] params = this.getMethodParams();

      for(int i = 0; i < params.length; ++i) {
         if (i > 0) {
            result.append(",");
         }

         result.append(params[i]);
      }

      result.append(")");
      return result.toString();
   }

   public String getMethodName() {
      return this.qmb.getQueryMethod().getMethodName();
   }

   public String[] getMethodParams() {
      return this.qmb.getQueryMethod().getMethodParams().getMethodParams();
   }

   public String getQueryText() {
      return this.qmb.getEjbQl();
   }

   public String getResultTypeMapping() {
      return this.qmb.getResultTypeMapping();
   }
}
