package weblogic.management.rest.lib.bean.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.model.ApiInfo;
import org.glassfish.admin.rest.model.ArrayTypeInfo;
import org.glassfish.admin.rest.model.BeanReferenceTypeInfo;
import org.glassfish.admin.rest.model.BeanReferencesTypeInfo;
import org.glassfish.admin.rest.model.EntityRefTypeInfo;
import org.glassfish.admin.rest.model.ExampleInfo;
import org.glassfish.admin.rest.model.LinkInfo;
import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.ObjectTypeInfo;
import org.glassfish.admin.rest.model.PrimitiveTypeInfo;
import org.glassfish.admin.rest.model.ResourceInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.model.VoidTypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.DescriptionUtils;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;
import weblogic.management.rest.lib.bean.utils.ResourceDef;

public abstract class ResourceMetaData {
   private ResourceMetaData parent;
   private HttpServletRequest request;
   private ApiInfo api;
   private ResourceInfo resource;
   private List children = null;
   public static final String SCOPE_GET = "/GET";
   public static final String SCOPE_POST = "/POST";
   public static final String SCOPE_DELETE = "/DELETE";
   public static final String SCOPE_SUMMARY = "/summary";
   public static final String SCOPE_REQ = "/req";
   public static final String SCOPE_RESP = "/resp";
   public static final String SCOPE_LINKS = "/links";
   public static final String SCOPE_QUERY = "/query";
   private static final String[] FILTER_QUERY_PARAM_NAMES = new String[]{"fields", "excludeFields", "links", "excludeLinks"};

   protected String linkScope(String rel) {
      return this.linkScope(rel, (String)null);
   }

   protected String linkScope(String rel, String title) {
      String scope = "/links/" + rel;
      if (title != null) {
         scope = scope + "-" + title;
      }

      return scope;
   }

   protected String methodScope(MethodInfo m) {
      return m.getScope();
   }

   public void init(ResourceMetaData parent) throws Exception {
      this.parent = parent;
      this.request = parent.request();
      this.api = parent.api();
   }

   public void init(HttpServletRequest request, ApiInfo api) throws Exception {
      this.parent = null;
      this.request = request;
      this.api = api;
   }

   protected String subUri(String path) throws Exception {
      return this.resource().getUri() + "/" + path;
   }

   protected boolean supportsGET() throws Exception {
      return true;
   }

   protected boolean supportsPOST() throws Exception {
      return false;
   }

   protected boolean supportsDELETE() throws Exception {
      return false;
   }

   public String beanTree() throws Exception {
      ResourceMetaData parent = this.parent();
      return parent != null ? parent.beanTree() : null;
   }

   protected boolean isEditTree() throws Exception {
      return "edit".equals(this.beanTree());
   }

   protected boolean isEditableTree() throws Exception {
      String tree = this.beanTree();
      return "edit".equals(tree) || "serverRuntime".equals(tree) || "domainRuntime".equals(tree);
   }

   protected String defaultGETSummary() throws Exception {
      return null;
   }

   protected String defaultGETDesc() throws Exception {
      return null;
   }

   protected String defaultGETReqDesc() throws Exception {
      return null;
   }

   protected String defaultGETRespDesc() throws Exception {
      return null;
   }

   protected String defaultPOSTSummary() throws Exception {
      return null;
   }

   protected String defaultPOSTDesc() throws Exception {
      return null;
   }

   protected String defaultPOSTReqDesc() throws Exception {
      return null;
   }

   protected String defaultPOSTRespDesc() throws Exception {
      return null;
   }

   protected String defaultDELETESummary() throws Exception {
      return null;
   }

   protected String defaultDELETEDesc() throws Exception {
      return null;
   }

   protected String defaultDELETEReqDesc() throws Exception {
      return null;
   }

   protected String defaultDELETERespDesc() throws Exception {
      return null;
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return null;
   }

   protected String defaultExamplePOSTBaseKey() throws Exception {
      return null;
   }

   protected String defaultExampleDELETEBaseKey() throws Exception {
      return null;
   }

   protected String GETSummary() throws Exception {
      return this.summary("/GET/summary", this.defaultGETSummary());
   }

   protected String GETDesc() throws Exception {
      return this.description("/GET", this.defaultGETDesc());
   }

   protected String GETReqDesc() throws Exception {
      return this.description("/GET/req", this.defaultGETReqDesc());
   }

   protected String GETRespDesc() throws Exception {
      return this.description("/GET/resp", this.defaultGETRespDesc());
   }

   protected String POSTSummary() throws Exception {
      return this.summary("/POST/summary", this.defaultPOSTSummary());
   }

   protected String POSTDesc() throws Exception {
      return this.description("/POST", this.defaultPOSTDesc());
   }

   protected String POSTReqDesc() throws Exception {
      return this.description("/POST/req", this.defaultPOSTReqDesc());
   }

   protected String POSTRespDesc() throws Exception {
      return this.description("/POST/resp", this.defaultPOSTRespDesc());
   }

   protected String DELETESummary() throws Exception {
      return this.summary("/DELETE/summary", this.defaultDELETESummary());
   }

   protected String DELETEDesc() throws Exception {
      return this.description("/DELETE", this.defaultDELETEDesc());
   }

   protected String DELETEReqDesc() throws Exception {
      return this.description("/DELETE/req", this.defaultDELETEReqDesc());
   }

   protected String DELETERespDesc() throws Exception {
      return this.description("/DELETE/resp", this.defaultDELETERespDesc());
   }

   protected boolean supportsGETFilteringQueryParams() throws Exception {
      return true;
   }

   protected boolean POSTCreates() throws Exception {
      return false;
   }

   protected TypeInfo GETReqType() throws Exception {
      return this.voidType();
   }

   protected TypeInfo GETRespType() throws Exception {
      return null;
   }

   protected TypeInfo POSTReqType() throws Exception {
      return null;
   }

   protected TypeInfo POSTRespType() throws Exception {
      return null;
   }

   protected TypeInfo DELETEReqType() throws Exception {
      return this.voidType();
   }

   protected TypeInfo DELETERespType() throws Exception {
      return this.voidType();
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return null;
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.defaultDescriptionArgs();
   }

   protected Object[] defaultLinkArgs(String rel) throws Exception {
      return this.defaultLinkArgs(rel, (String)null);
   }

   protected Object[] defaultLinkArgs(String rel, String title) throws Exception {
      Object[] baseArgs = this.defaultDescriptionArgs();
      return title != null ? this.appendArgs(baseArgs, rel, title) : this.appendArgs(baseArgs, rel);
   }

   protected Object[] appendArgs(Object[] baseArgs, Object... args) throws Exception {
      int l1 = baseArgs != null ? baseArgs.length : 0;
      int l2 = args != null ? args.length : 0;
      if (l1 == 0) {
         return args;
      } else if (l2 == 0) {
         return baseArgs;
      } else {
         Object[] rtn = new Object[l1 + l2];

         int i;
         for(i = 0; i < l1; ++i) {
            rtn[i] = baseArgs[i];
         }

         for(i = 0; i < l2; ++i) {
            rtn[l1 + i] = args[i];
         }

         return rtn;
      }
   }

   protected Object[] args(Object... args) throws Exception {
      return DescriptionUtils.args(args);
   }

   protected String[] keys(String... keys) throws Exception {
      return DescriptionUtils.keys(keys);
   }

   protected String description(String scope) throws Exception {
      return this.description(scope, (String)null);
   }

   protected String description(String scope, String defaultPattern) throws Exception {
      return this.description(scope, defaultPattern, this.defaultDescriptionArgs());
   }

   protected String summary(String scope) throws Exception {
      return this.summary(scope, (String)null);
   }

   protected String summary(String scope, String defaultPattern) throws Exception {
      return this.description(scope, defaultPattern, this.defaultSummaryArgs());
   }

   protected String description(String scope, String defaultPattern, Object... args) throws Exception {
      return DescriptionUtils.description(this.request(), scope, defaultPattern, MetaDataUtils.resourceKeys(this.resource()), args);
   }

   public abstract String className() throws Exception;

   public abstract String entityClassName() throws Exception;

   public abstract String path() throws Exception;

   public String entityDisplayName() throws Exception {
      return MetaDataUtils.entityDisplayName(this.entityClassName());
   }

   public String categoryDisplayName() throws Exception {
      return StringUtil.camelCaseToLowerCaseWords(this.category());
   }

   protected void createResource() throws Exception {
      String path = this.path();
      String uri = this.parent() != null ? this.parent().subUri(path) : path;
      this.resource = this.api().createResource(this.className(), this.entityClassName(), this.entityDisplayName(), uri, this.pathParamNames());
      this.addMethods();
   }

   protected void addMethods() throws Exception {
      if (this.supportsGET()) {
         MethodInfo m = this.GETMethodInfo(this.GETSummary(), this.GETDesc(), this.GETRespType(), this.GETRespDesc());
         this.addGETLinks(m);
      }

      if (this.supportsPOST()) {
         this.POSTMethodInfo(this.POSTSummary(), this.POSTDesc(), this.POSTReqType(), this.POSTRespType(), this.POSTReqDesc(), this.POSTRespDesc());
      }

      if (this.supportsDELETE()) {
         this.DELETEMethodInfo(this.DELETESummary(), this.DELETEDesc());
      }

   }

   protected void addGETLinks(MethodInfo m) throws Exception {
   }

   public void addToApi() throws Exception {
      this.createEntities();
      this.createResource();
   }

   protected void createEntities() throws Exception {
   }

   protected void createChildren() throws Exception {
   }

   public HttpServletRequest request() {
      return this.request;
   }

   public ApiInfo api() {
      return this.api;
   }

   public ResourceMetaData parent() {
      return this.parent;
   }

   public ResourceInfo resource() throws Exception {
      return this.resource;
   }

   public List children() throws Exception {
      if (this.children == null) {
         this.children = new ArrayList();
         this.createChildren();
      }

      return this.children;
   }

   protected void addChild(ResourceMetaData child) throws Exception {
      this.children().add(child);
   }

   protected void initAndAddChild(ResourceMetaData child) throws Exception {
      child.init(this);
      this.addChild(child);
   }

   protected LinkInfo addChildLink(MethodInfo m, String rel, String defaultDesc) throws Exception {
      return this.addChildLink(m, rel, rel, defaultDesc);
   }

   protected LinkInfo addChildLink(MethodInfo m, String rel, String path, String defaultDesc) throws Exception {
      return this.addChildLink(m, rel, (String)null, path, defaultDesc);
   }

   protected LinkInfo addChildLink(MethodInfo m, String rel, String title, String path, String defaultDesc) throws Exception {
      return this.addLink(m, rel, title, this.subUri(path), defaultDesc);
   }

   protected LinkInfo addLink(MethodInfo m, String rel, String uri, String defaultDesc) throws Exception {
      return this.addLink(m, rel, (String)null, uri, defaultDesc);
   }

   protected LinkInfo addLink(MethodInfo m, String rel, String title, String uri, String defaultDesc) throws Exception {
      return this.addLink(m, rel, title, uri, defaultDesc, this.defaultLinkArgs(rel, title));
   }

   protected LinkInfo addLink(MethodInfo m, String rel, String title, String uri, String defaultDesc, Object... args) throws Exception {
      return m.createLink(rel, uri, title, this.description(this.methodScope(m) + this.linkScope(rel, title), defaultDesc, args));
   }

   protected MethodInfo GETMethodInfo(String methodSummary, String methodDesc, TypeInfo responseType, String responseDesc) throws Exception {
      return this.GETMethodInfo(methodSummary, methodDesc, responseType, responseDesc, false);
   }

   protected MethodInfo GETMethodInfo(String methodSummary, String methodDesc, TypeInfo responseType, String responseDesc, boolean async) throws Exception {
      MethodInfo m = this.GETMethodInfo(methodSummary, methodDesc, responseType, async);
      m.setResponseBodyDesc(responseDesc);
      return m;
   }

   protected MethodInfo GETMethodInfo(String methodSummary, String methodDesc, TypeInfo responseType) throws Exception {
      return this.GETMethodInfo(methodSummary, methodDesc, responseType, false);
   }

   protected MethodInfo GETMethodInfo(String methodSummary, String methodDesc, TypeInfo responseType, boolean async) throws Exception {
      MethodInfo m = this.methodInfo("GET", methodSummary, methodDesc, (String)null, async, this.GETRolesAllowed());
      m.setRequestBodyType(this.voidType());
      m.setResponseBodyType(responseType);
      this.addLink(m, "self", this.resource().getUri(), "selfLinkDesc");
      this.addLink(m, "canonical", this.resource().getUri(), "canonicalLinkDesc");
      if (this.parent() != null) {
         this.addLink(m, "parent", this.parent().resource().getUri(), "parentLinkDesc");
      }

      this.addGETMethodExamples(m);
      this.addGETQueryParams(m);
      return m;
   }

   protected Set GETRolesAllowed() throws Exception {
      return MetaDataUtils.ALL_ROLES_ALLOWED;
   }

   protected void addGETQueryParams(MethodInfo m) throws Exception {
      if (this.supportsGETFilteringQueryParams()) {
         String[] var2 = FILTER_QUERY_PARAM_NAMES;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String qp = var2[var4];
            m.addQueryParamName(qp);
            this.ensureQueryParamExists(qp, this.stringType(), false);
         }
      }

   }

   protected MethodInfo POSTMethodInfo(String methodSummary, String methodDesc, TypeInfo requestType, TypeInfo responseType, String requestDesc, String responseDesc) throws Exception {
      return this.POSTMethodInfo(methodSummary, methodDesc, requestType, responseType, requestDesc, responseDesc, (String)null, false);
   }

   protected MethodInfo POSTMethodInfo(String methodSummary, String methodDesc, TypeInfo requestType, TypeInfo responseType, String requestDesc, String responseDesc, String scope, boolean async) throws Exception {
      MethodInfo m = this.POSTMethodInfo(methodSummary, methodDesc, scope, async);
      m.setRequestBodyType(requestType);
      m.setResponseBodyType(responseType);
      m.setRequestBodyDesc(requestDesc);
      m.setResponseBodyDesc(responseDesc);
      if (this.POSTCreates()) {
         m.setStatusCode(MethodInfo.CREATED);
      }

      return m;
   }

   protected MethodInfo POSTMethodInfo(String methodSummary, String methodDesc) throws Exception {
      return this.POSTMethodInfo(methodSummary, methodDesc, (String)null, false);
   }

   protected MethodInfo POSTMethodInfo(String methodSummary, String methodDesc, String scope, boolean async) throws Exception {
      MethodInfo m = this.methodInfo("POST", methodSummary, methodDesc, scope, async, this.POSTRolesAllowed());
      this.addPOSTMethodExamples(m);
      return m;
   }

   protected Set POSTRolesAllowed() throws Exception {
      return null;
   }

   protected MethodInfo DELETEMethodInfo(String methodSummary, String methodDesc) throws Exception {
      return this.DELETEMethodInfo(methodSummary, methodDesc, false);
   }

   protected MethodInfo DELETEMethodInfo(String methodSummary, String methodDesc, boolean async) throws Exception {
      MethodInfo m = this.methodInfo("DELETE", methodSummary, methodDesc, (String)null, async, this.DELETERolesAllowed());
      m.setRequestBodyType(this.voidType());
      m.setResponseBodyType(this.voidType());
      this.addDELETEMethodExamples(m);
      return m;
   }

   protected Set DELETERolesAllowed() throws Exception {
      return null;
   }

   protected MethodInfo methodInfo(String verb, String methodSummary, String methodDesc, Set roles) throws Exception {
      return this.methodInfo(verb, methodSummary, methodDesc, (String)null, false, roles);
   }

   protected MethodInfo methodInfo(String verb, String methodSummary, String methodDesc, String scope, boolean async, Set roles) throws Exception {
      if (async) {
         methodDesc = methodDesc + DescriptionUtils.description(this.request(), "syncAsyncDesc");
      }

      MethodInfo m = this.resource().createMethod(verb.toLowerCase(), verb, methodSummary, methodDesc);
      if (scope != null) {
         m.setScope(scope);
      }

      if (async) {
         m.setSupportsAsync(true);
         m.addRequestHeaderName("Prefer");
         this.ensureRequestHeaderExists("Prefer", this.stringType(), false);
      }

      if ("DELETE".equals(verb) || "POST".equals(verb)) {
         m.addRequestHeaderName("X-Requested-By");
         this.ensureRequestHeaderExists("X-Requested-By", this.stringType(), true);
      }

      m.setRoles(roles);
      return m;
   }

   protected void addGETMethodExamples(MethodInfo m) throws Exception {
      this.addMethodExamples(m, "", this.defaultExampleGETBaseKey());
   }

   protected void addPOSTMethodExamples(MethodInfo m) throws Exception {
      this.addMethodExamples(m, "", this.defaultExamplePOSTBaseKey());
   }

   protected void addDELETEMethodExamples(MethodInfo m) throws Exception {
      this.addMethodExamples(m, "", this.defaultExampleDELETEBaseKey());
   }

   protected void addMethodExamples(MethodInfo m, String prefix, String defaultBaseKey) throws Exception {
      if (m.supportsAsync()) {
         String syncPrefix = prefix + "-sync";
         this._addMethodExamples(m, syncPrefix, this.prefixedKey(defaultBaseKey, syncPrefix));
         String asyncPrefix = prefix + "-async";
         this._addMethodExamples(m, asyncPrefix, this.prefixedKey(defaultBaseKey, asyncPrefix));
      } else {
         this._addMethodExamples(m, prefix, this.prefixedKey(defaultBaseKey, prefix));
      }

   }

   private void _addMethodExamples(MethodInfo m, String prefix, String defaultBaseKey) throws Exception {
      this.__addMethodExamples(m, prefix, defaultBaseKey, true);
      if (this.isPartitioned()) {
         this.__addMethodExamples(m, prefix + "-mt", this.prefixedKey(defaultBaseKey, prefix), false);
      } else {
         this.__addMethodExamples(m, prefix + "-non-mt", this.prefixedKey(defaultBaseKey, prefix), false);
      }

   }

   private void __addMethodExamples(MethodInfo m, String prefix, String defaultBaseKey, boolean debugByDefault) throws Exception {
      if (!this.addMethodExample(m, prefix, defaultBaseKey, debugByDefault)) {
         boolean found = true;

         for(int i = 0; found; ++i) {
            found = this.addMethodExample(m, prefix + "-" + i, defaultBaseKey, false);
         }
      }

   }

   private String prefixedKey(String defaultBaseKey, String prefix) {
      return defaultBaseKey != null ? defaultBaseKey + prefix : null;
   }

   private boolean addMethodExample(MethodInfo m, String suffix, String defaultBaseKey, boolean debugByDefault) throws Exception {
      String title = this.getExampleText(m, suffix, defaultBaseKey, "/title");
      String desc = this.getExampleText(m, suffix, defaultBaseKey, "/desc");
      String reqKey = this.getExampleKey(m, suffix, "/req");
      String respKey = this.getExampleKey(m, suffix, "/resp");
      String req = DescriptionUtils.exactVal(this.request, reqKey);
      String resp = DescriptionUtils.exactVal(this.request, respKey);
      boolean haveExample = req != null;
      ExampleInfo e;
      if (MetaDataUtils.isDebugEnabled()) {
         if (debugByDefault || MetaDataUtils.isVerboseEnabled()) {
            e = m.createExample("Example Info");
            e.addText(title);
            e.addText(desc);
            e.addText(this.found(reqKey, req));
            e.addText(this.found(respKey, resp));
         }
      } else if (req != null) {
         e = m.createExample(title);
         if (desc != null) {
            e.addText(desc);
         }

         e.addHeader(DescriptionUtils.description(this.request(), "exampleRequestHeader"));
         e.addTextContent(req);
         if (resp != null) {
            e.addHeader(DescriptionUtils.description(this.request(), "exampleResponseHeader"));
            e.addTextContent(resp);
         }
      }

      return haveExample;
   }

   private String found(String key, String val) throws Exception {
      StringBuilder sb = new StringBuilder();
      sb.append(key).append(" - ");
      if (val != null) {
         sb.append(" found");
      } else {
         sb.append(" not found");
      }

      return sb.toString();
   }

   private String getExampleText(MethodInfo m, String suffix, String defaultBaseKey, String keySuffix) throws Exception {
      String scope = this.methodScope(m) + suffix + keySuffix;
      String defaultDescription = defaultBaseKey + keySuffix;
      String[] customKeys = new String[]{"examples" + MetaDataUtils.uriToKey(m.getResource().getUri())};
      Object[] args = this.defaultDescriptionArgs();
      return DescriptionUtils.description(this.request(), scope, defaultDescription, customKeys, args);
   }

   private String getExampleKey(MethodInfo m, String suffix, String keySuffix) throws Exception {
      return "examples" + MetaDataUtils.uriToKey(m.getResource().getUri()) + this.methodScope(m) + suffix + keySuffix;
   }

   protected boolean haveResourceMetaData(ResourceDef rd) throws Exception {
      return ResourceDef.getMetaDataClass(rd) != null;
   }

   protected ResourceMetaData createResourceMetaData(ResourceDef rd) throws Exception {
      Class clazz = ResourceDef.getMetaDataClass(rd);
      return clazz != null ? (ResourceMetaData)clazz.newInstance() : null;
   }

   public boolean isRecursive() throws Exception {
      ResourceMetaData parent = this.parent();
      return parent != null ? parent.isRecursive() : false;
   }

   public boolean isPartitioned() throws Exception {
      ResourceMetaData parent = this.parent();
      return parent != null ? parent.isPartitioned() : false;
   }

   protected boolean isPartitionRoot() throws Exception {
      return false;
   }

   public boolean isParentedByPartition() throws Exception {
      ResourceMetaData parent = this.parent();
      if (parent == null) {
         return false;
      } else {
         return parent.isPartitionRoot() ? true : parent.isParentedByPartition();
      }
   }

   protected VoidTypeInfo voidType() throws Exception {
      return VoidTypeInfo.instance();
   }

   protected ObjectTypeInfo anyObjectType(String title) throws Exception {
      return this.objectType(title);
   }

   protected ObjectTypeInfo objectType(String title) throws Exception {
      return new ObjectTypeInfo(title);
   }

   protected ObjectTypeInfo multiPartFormObjectType() throws Exception {
      return this.objectType((String)null);
   }

   protected EntityRefTypeInfo entityRefType(String entityClassName) throws Exception {
      return new EntityRefTypeInfo(MetaDataUtils.entityDisplayName(entityClassName), entityClassName);
   }

   protected ArrayTypeInfo arrayType(TypeInfo componentType) throws Exception {
      return new ArrayTypeInfo(componentType);
   }

   protected PrimitiveTypeInfo binaryType() throws Exception {
      return PrimitiveTypeInfo.BYTES;
   }

   protected PrimitiveTypeInfo stringType() throws Exception {
      return PrimitiveTypeInfo.STRING;
   }

   protected PrimitiveTypeInfo longType() throws Exception {
      return PrimitiveTypeInfo.LONG;
   }

   protected PrimitiveTypeInfo fileType() throws Exception {
      return PrimitiveTypeInfo.FILE;
   }

   protected BeanReferenceTypeInfo beanReferenceType(String beanType) throws Exception {
      return new BeanReferenceTypeInfo(MetaDataUtils.entityDisplayName(beanType), beanType);
   }

   protected BeanReferencesTypeInfo beanReferencesType(String beanType) throws Exception {
      return new BeanReferencesTypeInfo(MetaDataUtils.entityDisplayName(beanType), beanType);
   }

   protected String category() throws Exception {
      return null;
   }

   public boolean isActionsCategory() throws Exception {
      return false;
   }

   public Set pathParamNames() throws Exception {
      Set names = new TreeSet();

      for(ResourceMetaData r = this; r != null; r = r.parent()) {
         String name = r.pathParamName();
         if (name != null) {
            this.ensurePathParamExists(name, this.stringType());
            if (!names.contains(name)) {
               names.add(name);
            }
         }
      }

      return names;
   }

   protected String pathParamName() throws Exception {
      return null;
   }

   private void ensurePathParamExists(String pathParamName, TypeInfo type) throws Exception {
      if (!this.api().pathParamExists(pathParamName)) {
         this.api().createPathParam(pathParamName, DescriptionUtils.description(this.request(), (String)null, (String)"defaultPathParamDesc", this.pathParamKeys(pathParamName), pathParamName), type);
      }

   }

   private void ensureQueryParamExists(String queryParamName, TypeInfo type, boolean isRequired) throws Exception {
      if (!this.api().queryParamExists(queryParamName)) {
         this.api().createQueryParam(queryParamName, DescriptionUtils.description(this.request(), "queryParams/" + queryParamName), type, isRequired);
      }

   }

   private void ensureRequestHeaderExists(String requestHeaderName, TypeInfo type, boolean isRequired) throws Exception {
      if (!this.api().requestHeaderExists(requestHeaderName)) {
         this.api().createRequestHeader(requestHeaderName, DescriptionUtils.description(this.request(), "requestHeaders/" + requestHeaderName), type, isRequired);
      }

   }

   private void ensureResponseHeaderExists(String responseHeaderName, TypeInfo type) throws Exception {
      if (!this.api().responseHeaderExists(responseHeaderName)) {
         this.api().createResponseHeader(responseHeaderName, DescriptionUtils.description(this.request(), "responseHeaders/" + responseHeaderName), type);
      }

   }

   private String[] pathParamKeys(String pathParamName) throws Exception {
      return this.keys("pathParams/" + pathParamName);
   }
}
