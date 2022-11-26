package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.AsyncUtil;

public class InvocationContext {
   private UriInfo uriInfo;
   private HttpServletRequest request;
   private boolean expandedValues;
   private boolean saveChanges;
   private JSONObject query;
   private Object bean;
   private boolean async;
   private int syncMaxWaitMilliSeconds;
   private int intervalToPollMilliSeconds;
   private PartitionUtils.CICs cics = new PartitionUtils.CICs();
   private String versionName;
   private int versionNumber;
   private boolean exposeInternals;

   private InvocationContext(InvocationContext clone, Object bean) throws Exception {
      this.uriInfo = clone.uriInfo();
      this.request = clone.request();
      this.expandedValues = clone.expandedValues();
      this.saveChanges = clone.saveChanges();
      this.async = clone.async();
      this.query = clone.query();
      this.syncMaxWaitMilliSeconds = clone.syncMaxWaitMilliSeconds();
      this.intervalToPollMilliSeconds = clone.intervalToPollMilliSeconds();
      this.versionName = clone.versionName();
      this.versionNumber = clone.versionNumber();
      this.exposeInternals = clone.exposeInternals();
      this.bean = bean;
   }

   public InvocationContext(UriInfo uriInfo, HttpServletRequest request, Object bean, JSONObject query) throws Exception {
      this.uriInfo = uriInfo;
      this.request = request;
      this.bean = bean;
      this.query = query;
      MultivaluedMap qps = this.uriInfo().getQueryParameters();
      this.expandedValues = Boolean.parseBoolean((String)qps.getFirst("expandedValues"));
      String saveChangesStr = (String)qps.getFirst("saveChanges");
      this.saveChanges = saveChangesStr != null ? Boolean.parseBoolean(saveChangesStr) : true;
      if (this.query == null) {
         this.query = QueryUtils.getIncludeExcludeQuery((String)qps.getFirst("fields"), (String)qps.getFirst("excludeFields"), (String)qps.getFirst("links"), (String)qps.getFirst("excludeLinks"));
      }

      this.async = AsyncUtil.isAsync(request, uriInfo);
      this.syncMaxWaitMilliSeconds = AsyncUtil.getSyncMaxWaitMilliSeconds(request, uriInfo);
      this.intervalToPollMilliSeconds = AsyncUtil.getIntervalToPollMilliSeconds(request, uriInfo);
      if (this.uriInfo().getPathSegments().size() >= 2) {
         this.versionName = ((PathSegment)this.uriInfo().getPathSegments().get(1)).getPath();
         this.versionNumber = SupportedVersions.getVersionNumber(this.versionName());
      } else {
         this.versionName = null;
         this.versionNumber = -1;
      }

      this.exposeInternals = Boolean.parseBoolean((String)qps.getFirst("internal"));
   }

   public InvocationContext clone(Object bean) throws Exception {
      InvocationContext clone = new InvocationContext(this, bean);
      clone.cics = this.cics;
      return clone;
   }

   public InvocationContext unfiltered() throws Exception {
      return this.clone(this.bean()).setQuery((JSONObject)null);
   }

   public InvocationContext setSync() {
      this.async = false;
      return this;
   }

   public InvocationContext setExpandedValues(boolean expandedValues) {
      this.expandedValues = expandedValues;
      return this;
   }

   public InvocationContext setQuery(JSONObject query) {
      this.query = query;
      return this;
   }

   public PartitionUtils.CICs getCICs() {
      return this.cics;
   }

   public UriInfo uriInfo() {
      return this.uriInfo;
   }

   public HttpServletRequest request() {
      return this.request;
   }

   public Object bean() {
      return this.bean;
   }

   public boolean expandedValues() {
      return this.expandedValues;
   }

   public boolean saveChanges() {
      return this.saveChanges;
   }

   public JSONObject query() {
      return this.query;
   }

   public boolean async() {
      return this.async;
   }

   public boolean sync() {
      return !this.async();
   }

   public int syncMaxWaitMilliSeconds() {
      return this.syncMaxWaitMilliSeconds;
   }

   public int intervalToPollMilliSeconds() {
      return this.intervalToPollMilliSeconds;
   }

   public String versionName() {
      return this.versionName;
   }

   public int versionNumber() {
      return this.versionNumber;
   }

   public boolean exposeInternals() {
      return this.exposeInternals;
   }
}
