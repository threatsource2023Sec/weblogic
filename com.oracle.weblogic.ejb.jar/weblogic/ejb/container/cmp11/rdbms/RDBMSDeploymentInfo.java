package weblogic.ejb.container.cmp11.rdbms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.cmp11.rdbms.finders.Finder;
import weblogic.ejb.container.cmp11.rdbms.finders.InvalidFinderException;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.j2ee.descriptor.wl60.FieldMapBean;
import weblogic.j2ee.descriptor.wl60.FinderBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;

public final class RDBMSDeploymentInfo {
   protected EJBComplianceTextFormatter fmt;
   private WeblogicRdbmsJarBean wlJar;
   private Map rdbmsBeanMap = new HashMap();

   public RDBMSDeploymentInfo(WeblogicRdbmsJarBean wlJar, CMPDDParser.CompatibilitySettings compat, String fileName) throws RDBMSException, InvalidFinderException {
      this.wlJar = wlJar;
      this.fmt = new EJBComplianceTextFormatter();
      WeblogicRdbmsBeanBean[] beans = wlJar.getWeblogicRdbmsBeans();

      for(int i = 0; i < beans.length; ++i) {
         RDBMSBean bean = new RDBMSBean();
         bean.setEjbName(beans[i].getEjbName());
         bean.setFileName(fileName);
         bean.setPoolName(beans[i].getPoolName());
         bean.setDataSourceName(beans[i].getDataSourceJndiName());
         bean.setTableName(beans[i].getTableName());
         bean.setEnableTunedUpdates(beans[i].isEnableTunedUpdates());
         if (compat != null) {
            bean.setUseQuotedNames(compat.useQuotedNames);
            bean.setTransactionIsolation(compat.isolationLevel);
         }

         this.initFieldMaps(beans[i], bean);
         this.initFinders(beans[i], bean, compat);
         this.rdbmsBeanMap.put(bean.getEjbName(), bean);
      }

   }

   public RDBMSBean getRDBMSBean(String name) {
      return (RDBMSBean)this.rdbmsBeanMap.get(name);
   }

   public Map getRDBMSBeanMap() {
      return this.rdbmsBeanMap;
   }

   public WeblogicRdbmsJarBean getWeblogicRdbmsJarBean() {
      return this.wlJar;
   }

   private void initFieldMaps(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean) throws RDBMSException {
      Set fieldNames = new HashSet();
      Set columnNames = new HashSet();
      FieldMapBean[] fMaps = bean.getFieldMaps();

      for(int i = 0; i < fMaps.length; ++i) {
         if (!fieldNames.add(fMaps[i].getCmpField())) {
            throw new RDBMSException(this.fmt.DUPLICATE_MAPPING_FOR_CMP_FIELD(rdbmsBean.getFileName(), rdbmsBean.getEjbName(), fMaps[i].getCmpField()));
         }

         if (!columnNames.add(fMaps[i].getDbmsColumn())) {
            throw new RDBMSException(this.fmt.DUPLICATE_MAPPING_FOR_DBMS_COLUMN(rdbmsBean.getFileName(), rdbmsBean.getEjbName(), fMaps[i].getDbmsColumn()));
         }

         RDBMSBean.ObjectLink attribute = new RDBMSBean.ObjectLink(fMaps[i].getCmpField(), fMaps[i].getDbmsColumn());
         rdbmsBean.addObjectLink(attribute);
      }

   }

   private void initFinders(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean, CMPDDParser.CompatibilitySettings compat) throws InvalidFinderException {
      FinderBean[] finders = bean.getFinders();

      for(int i = 0; i < finders.length; ++i) {
         boolean isSql = true;
         String query = finders[i].getFinderSql();
         if (query == null) {
            query = finders[i].getFinderQuery();
            if ("empty-finder".equals(query)) {
               query = "";
            }

            isSql = false;
         }

         if (query != null) {
            Finder f = new Finder(finders[i].getFinderName(), query);
            f.setUsingSQL(isSql);
            String[] params = finders[i].getFinderParams();

            for(int j = 0; j < params.length; ++j) {
               f.addParameterType(params[j]);
            }

            List finderExpressions = null;
            if (compat != null) {
               String signature = DDUtils.getMethodSignature(finders[i].getFinderName(), finders[i].getFinderParams());
               finderExpressions = (List)compat.finderExpressionMap.get(signature);
            }

            if (finderExpressions != null) {
               Iterator it = finderExpressions.iterator();

               while(it.hasNext()) {
                  CMPDDParser.FinderExpression fex = (CMPDDParser.FinderExpression)it.next();
                  Finder.FinderExpression finderExpression = new Finder.FinderExpression(fex.expressionNumber, fex.expressionText, fex.expressionType);
                  f.addFinderExpression(finderExpression);
               }
            }

            if (finders[i].isFindForUpdate()) {
               Finder.FinderOptions fopt = new Finder.FinderOptions();
               fopt.setFindForUpdate(true);
               f.setFinderOptions(fopt);
            }

            rdbmsBean.addFinder(f);
         }
      }

   }
}
