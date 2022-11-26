package weblogic.ejb.container.cmp.rdbms.finders;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.Log;
import weblogic.ejb.container.internal.QueryCachingHandler;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb20.cmp.rdbms.finders.InvalidFinderException;
import weblogic.utils.AssertionError;

public abstract class Finder {
   protected static final DebugLogger debugLogger;
   public static final int IS_FINDER_LOCAL_BEAN = 0;
   public static final int IS_SELECT_THIS_BEAN = 2;
   public static final int IS_SELECT_THIS_BEAN_FIELD = 3;
   public static final int IS_SELECT_LOCAL_BEAN = 4;
   public static final int IS_SELECT_LOCAL_BEAN_FIELD = 5;
   public static final int IS_SELECT_RESULT_SET = 6;
   private boolean isSqlFinder = true;
   protected RDBMSBean rdbmsBean = null;
   protected String methodName = null;
   private boolean isFinder = false;
   protected boolean isSelect = false;
   protected boolean isSelectInEntity = false;
   private boolean keyFinder = false;
   private String resultTypeMapping = null;
   protected int queryType = 0;
   protected int maxElements = 0;
   private RDBMSBean selectBeanTarget = null;
   private String selectFieldColumn = null;
   private Class selectFieldClass = null;
   private boolean includeUpdates = true;
   protected Class returnClassType = null;
   protected Class[] parameterClassTypes = null;
   private Class[] exceptionClassTypes = null;
   private String modifierString = null;
   private String[] parameterClassNames = null;
   protected boolean finderLoadsBean = false;
   protected List externalMethodParmList = new ArrayList();
   private List internalQueryParmList = new ArrayList();
   protected List internalInEntityParmList = new ArrayList();
   private boolean isSelectDistinct = false;
   protected String sqlQuery = null;
   protected String sqlQueryForUpdate = null;
   protected String sqlQueryForUpdateNoWait = null;
   protected boolean queryCachingEnabled = false;
   protected boolean eagerRefreshEnabled = false;
   protected Integer finderIndex = null;
   protected TTLManager ownerManager = null;
   protected Object[] arguments = null;
   protected QueryCacheKey queryCacheKey = null;
   protected EJBComplianceTextFormatter fmt;

   public Finder(String name, boolean isSqlFinder) throws InvalidFinderException {
      if (!name.startsWith("ejbSelect") && !name.equals("execute")) {
         this.isFinder = true;
      } else if (name.endsWith("InEntity")) {
         this.isSelectInEntity = true;
      } else {
         this.isSelect = true;
      }

      this.setName(name);
      this.isSqlFinder = isSqlFinder;
      this.fmt = new EJBComplianceTextFormatter();
   }

   public String getSQLQuery() {
      throw new AssertionError("Should never be called");
   }

   public String getSQLQueryForUpdate() {
      throw new AssertionError("Should never be called");
   }

   public String getSQLQueryForUpdateNoWait() {
      throw new AssertionError("Should never be called");
   }

   public String getSQLQueryForUpdateSelective() {
      throw new AssertionError("Should never be called");
   }

   public int getQueryType() {
      return this.queryType;
   }

   public void setQueryType(int s) {
      this.queryType = s;
   }

   public boolean isSelectInEntity() {
      return this.isSelectInEntity;
   }

   public void setIsSelectInEntity(boolean b) {
      this.isSelectInEntity = b;
   }

   public boolean isSelectDistinct() {
      return this.isSelectDistinct;
   }

   public void setSelectDistinct(boolean b) {
      this.isSelectDistinct = b;
   }

   public void setSelectBeanTarget(RDBMSBean b) {
      this.selectBeanTarget = b;
   }

   public RDBMSBean getSelectBeanTarget() {
      return this.selectBeanTarget;
   }

   public void setSelectFieldColumn(String s) {
      this.selectFieldColumn = s;
   }

   public String getSelectFieldColumn() {
      return this.selectFieldColumn;
   }

   public void setSelectFieldClass(Class c) {
      this.selectFieldClass = c;
   }

   public Class getSelectFieldClass() {
      return this.selectFieldClass;
   }

   public void setFinderLoadsBean(boolean val) {
      this.finderLoadsBean = val;
   }

   public boolean finderLoadsBean() {
      return this.finderLoadsBean;
   }

   public boolean isFinder() {
      return this.isFinder;
   }

   public void setIsSelect(boolean b) {
      this.isSelect = b;
   }

   public boolean isSelect() {
      return this.isSelect;
   }

   public boolean isAggregateQuery() {
      return false;
   }

   protected void setName(String finderName) throws InvalidFinderException {
      if (finderName == null) {
         throw new InvalidFinderException(1, finderName);
      } else if (finderName.equals("")) {
         throw new InvalidFinderException(2, finderName);
      } else if (!finderName.startsWith("find") && !finderName.startsWith("ejbSelect") && !finderName.equals("execute")) {
         throw new InvalidFinderException(3, finderName);
      } else {
         this.methodName = finderName;
      }
   }

   public String getName() {
      return this.methodName;
   }

   public void setReturnClassType(Class c) {
      this.returnClassType = c;
   }

   public Class getReturnClassType() {
      return this.returnClassType;
   }

   public void setParameterClassTypes(Class[] c) {
      this.parameterClassTypes = c;
      this.parameterClassNames = new String[this.parameterClassTypes.length];

      for(int i = 0; i < this.parameterClassTypes.length; ++i) {
         this.parameterClassNames[i] = this.parameterClassTypes[i].getName();
      }

   }

   public Class[] getParameterClassTypes() {
      return this.parameterClassTypes;
   }

   public Class getParameterTypeAt(int i) {
      return i < this.parameterClassTypes.length ? this.parameterClassTypes[i] : null;
   }

   public void setParameterClassNames(String[] paramNames) {
      this.parameterClassNames = paramNames;
   }

   public String[] getParameterClassNames() {
      return this.parameterClassNames;
   }

   public void setExceptionClassTypes(Class[] c) {
      this.exceptionClassTypes = c;
   }

   public Class[] getExceptionClassTypes() {
      return this.exceptionClassTypes;
   }

   public void setModifierString(String s) {
      this.modifierString = s;
   }

   public String getModifierString() {
      return this.modifierString;
   }

   public void setRDBMSBean(RDBMSBean b) {
      this.rdbmsBean = b;
   }

   public RDBMSBean getRDBMSBean() {
      return this.rdbmsBean;
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

   public String getResultTypeMapping() {
      return this.resultTypeMapping;
   }

   public void setResultTypeMapping(String val) {
      this.resultTypeMapping = val;
   }

   public boolean hasLocalResultType() {
      return "Local".equals(this.resultTypeMapping);
   }

   public boolean hasRemoteResultType() {
      return "Remote".equals(this.resultTypeMapping);
   }

   public boolean isMultiFinder() {
      return this.getReturnClassType() == null ? false : Collection.class.isAssignableFrom(this.getReturnClassType());
   }

   public boolean isScalarFinder() {
      return !this.isMultiFinder();
   }

   public boolean isSetFinder() {
      return this.getReturnClassType() == null ? false : this.getReturnClassType().getName().equals("java.util.Set");
   }

   public boolean isResultSetFinder() {
      return this.getReturnClassType() == null ? false : ResultSet.class.isAssignableFrom(this.getReturnClassType());
   }

   public boolean isFindByPrimaryKey() {
      return this.getName().equals("findByPrimaryKey");
   }

   public boolean isKeyFinder() {
      return this.keyFinder;
   }

   public void setKeyFinder(boolean keyFinder) {
      this.keyFinder = keyFinder;
   }

   protected void setMethod(Method method) throws Exception {
      this.setReturnClassType(method.getReturnType());
      this.setExceptionClassTypes(method.getExceptionTypes());
      StringBuffer sb = new StringBuffer();
      int methodModifiers = method.getModifiers();
      if (Modifier.isAbstract(methodModifiers)) {
         methodModifiers ^= 1024;
      }

      sb.append(Modifier.toString(methodModifiers)).append(" ");
      String s = sb.toString();
      int i = s.indexOf("strict ");
      if (i != -1) {
         sb.insert(i + "strict".length(), "fp");
      }

      this.setModifierString(sb.toString());
   }

   public abstract void setMethods(Method[] var1) throws Exception;

   public List getExternalMethodParmList() {
      return this.externalMethodParmList;
   }

   public List getExternalMethodAndInEntityParmList() {
      List l = new ArrayList(this.externalMethodParmList);
      return l;
   }

   public List getInternalQueryAndInEntityParmList() {
      List l = new ArrayList(this.internalQueryParmList);
      Iterator it = this.internalInEntityParmList.iterator();

      while(it.hasNext()) {
         ParamNode pNode = (ParamNode)it.next();
         l.add(pNode);
      }

      return l;
   }

   public void addInternalQueryParmList(ParamNode n) {
      this.internalQueryParmList.add(n);
   }

   protected ParamNode getInternalQueryParmNode(int position) {
      return this.internalQueryParmList != null && !this.internalQueryParmList.isEmpty() && position < this.internalQueryParmList.size() ? (ParamNode)this.internalQueryParmList.get(position) : null;
   }

   public void addInternalInEntityParmList(ParamNode n) {
      this.internalInEntityParmList.add(n);
   }

   public List getInternalInEntityParmList() {
      return this.internalInEntityParmList;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[Finder ");
      buf.append("methodName = " + this.methodName + "; ");
      buf.append("Parameter types = " + Arrays.toString(this.parameterClassTypes) + ";");
      buf.append("finderLoadsBean = " + Boolean.toString(this.finderLoadsBean) + ";");
      return buf.toString();
   }

   public boolean isSqlFinder() {
      return this.isSqlFinder;
   }

   public boolean isEjbqlFinder() {
      return !this.isSqlFinder;
   }

   public void setQueryCachingEnabled(RDBMSFinder rfinder, RDBMSBean rbean) {
      if (!rfinder.isQueryCachingEnabled()) {
         this.queryCachingEnabled = false;
      } else if (!this.checkIfQueryCachingLegal(rbean)) {
         this.queryCachingEnabled = false;
      } else {
         this.queryCachingEnabled = true;
         if (rfinder.isEnableEagerRefresh()) {
            this.eagerRefreshEnabled = true;
         }

      }
   }

   public void setQueryCachingEnabled(boolean on) {
      if (!on) {
         this.queryCachingEnabled = false;
      } else if (!this.checkIfQueryCachingLegal(this.rdbmsBean)) {
         this.queryCachingEnabled = false;
      } else {
         this.queryCachingEnabled = true;
      }
   }

   public boolean isQueryCachingEnabled() {
      return this.queryCachingEnabled;
   }

   public abstract QueryCachingHandler getQueryCachingHandler(Object[] var1, TTLManager var2);

   public String getFinderIndex() {
      return !this.isQueryCachingEnabled() ? null : this.generateFinderIndex();
   }

   protected String generateFinderIndex() {
      if (this.parameterClassNames == null) {
         throw new AssertionError("parameter class names is null; cannot generate finder index for query caching");
      } else {
         int index = this.methodName.hashCode();
         String[] var2 = this.parameterClassNames;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String parameterClassName = var2[var4];
            index ^= parameterClassName.hashCode();
         }

         return String.valueOf(index);
      }
   }

   protected boolean checkIfQueryCachingLegal(RDBMSBean rbean) {
      if (!rbean.isReadOnly()) {
         Log.getInstance().logWarning(this.fmt.QUERY_CACHING_SUPPORTED_FOR_RO_BEANS_ONLY(rbean.getEjbName(), this.getName()));
         return false;
      } else if (this.isFindByPrimaryKey()) {
         Log.getInstance().logWarning(this.fmt.QUERY_CACHING_UNNECESSARY_FOR_FIND_BY_PK(rbean.getEjbName()));
         return false;
      } else if (this.isSelect()) {
         Log.getInstance().logWarning(this.fmt.QUERY_CACHING_NOT_SUPPORTED_FOR_EJBSELECTS(rbean.getEjbName(), this.getName()));
         return false;
      } else if (this.getReturnClassType().equals(Enumeration.class)) {
         Log.getInstance().logWarning(this.fmt.QUERY_CACHING_NOT_SUPPORTED_FOR_ENUMFINDERS(rbean.getEjbName(), this.getName()));
         return false;
      } else {
         return true;
      }
   }

   public boolean isEagerRefreshEnabled() {
      return this.eagerRefreshEnabled;
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
