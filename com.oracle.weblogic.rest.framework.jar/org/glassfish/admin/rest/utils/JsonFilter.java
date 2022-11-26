package org.glassfish.admin.rest.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JsonFilter {
   private boolean defaultInclude;
   private List filters;

   public JsonFilter() {
      this.filters = new ArrayList();
      this.defaultInclude = true;
   }

   public JsonFilter(Locale locale, String include, String exclude, String includeFieldsName, String excludeFieldsName) throws Exception {
      this(locale, include, exclude, "name", includeFieldsName, excludeFieldsName);
   }

   public JsonFilter(Locale locale, String include, String exclude, String identityAttr, String includeFieldsName, String excludeFieldsName) throws Exception {
      this.filters = new ArrayList();
      if (include == null && exclude == null) {
         this.defaultInclude = true;
      } else if (include != null && exclude != null) {
         String msg = MessageUtil.formatter(locale).msgIncludeAndExcludeFieldsSpecified(includeFieldsName, excludeFieldsName);
         throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
      } else {
         if (identityAttr != null) {
            this.addFilter(new IdentityFilter(identityAttr));
         }

         if (include != null) {
            this.addFilter(new IncludeFilter(locale, include));
            this.defaultInclude = false;
         } else if (exclude != null) {
            this.addFilter(new ExcludeFilter(locale, exclude));
            this.defaultInclude = true;
         }
      }
   }

   public JsonFilter addFilter(Filter filter, int position) {
      this.filters.add(position, filter);
      return this;
   }

   public JsonFilter addFilter(Filter filter) {
      this.filters.add(filter);
      return this;
   }

   public JSONObject trim(JSONObject j) {
      this.newScope().trimJsonObject(j);
      return j;
   }

   public Scope newScope() {
      return new Scope();
   }

   public class Scope {
      private Stack scopeStack;

      private Scope() {
         this.scopeStack = null;
         if (JsonFilter.this.filters.size() > 0) {
            this.scopeStack = new Stack();
         }

      }

      public JSONObject trim(JSONObject j) {
         (JsonFilter.this.new Scope()).trimJsonObject(j);
         return j;
      }

      public void trimJsonObject(JSONObject j) {
         Iterator var2 = this.getPropertyNames(j).iterator();

         while(var2.hasNext()) {
            String property = (String)var2.next();
            if (!this.include(property)) {
               j.remove(property);
            } else {
               try {
                  Object o = j.get(property);
                  if (o instanceof JSONObject) {
                     JSONObject next = (JSONObject)o;
                     this.beginObjectAttr(property);

                     try {
                        this.trimJsonObject(next);
                     } finally {
                        this.endObjectAttr();
                     }
                  } else if (o instanceof JSONArray) {
                     JSONArray ar = (JSONArray)o;
                     this.beginArrayAttr(property);

                     try {
                        this.trimJsonArray(ar);
                     } finally {
                        this.endArrayAttr();
                     }
                  }
               } catch (JSONException var16) {
               }
            }
         }

      }

      private List getPropertyNames(JSONObject j) {
         List rtn = new ArrayList();
         Iterator it = j.keys();

         while(it.hasNext()) {
            String property = (String)((String)it.next());
            rtn.add(property);
         }

         return rtn;
      }

      private void trimJsonArray(JSONArray ar) {
         for(int i = 0; i < ar.length(); ++i) {
            try {
               Object o = ar.get(i);
               if (o instanceof JSONObject) {
                  this.trimJsonObject((JSONObject)o);
               } else if (o instanceof JSONArray) {
                  this.trimJsonArray((JSONArray)o);
               }
            } catch (JSONException var4) {
            }
         }

      }

      public boolean includeAny(String[] properties) {
         String[] var2 = properties;
         int var3 = properties.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String property = var2[var4];
            if (this.include(property)) {
               return true;
            }
         }

         return false;
      }

      public boolean include(String property) {
         if (this.scopeStack != null) {
            String attr = this.scopeStack.isEmpty() ? property : (String)this.scopeStack.peek() + "." + property;
            Iterator var3 = JsonFilter.this.filters.iterator();

            while(var3.hasNext()) {
               Filter filter = (Filter)var3.next();
               Result r = filter.filter(attr);
               if (r == JsonFilter.Result.Include) {
                  return true;
               }

               if (r == JsonFilter.Result.Exclude) {
                  return false;
               }
            }
         }

         return JsonFilter.this.defaultInclude;
      }

      public void beginObjectAttr(String name) {
         this.beginAttr(name);
      }

      public void endObjectAttr() {
         this.endAttr();
      }

      public void beginArrayAttr(String name) {
         this.beginAttr(name);
      }

      public void endArrayAttr() {
         this.endAttr();
      }

      private void beginAttr(String name) {
         if (this.scopeStack != null) {
            String scope = this.scopeStack.isEmpty() ? name : (String)this.scopeStack.peek() + "." + name;
            this.scopeStack.push(scope);
         }

      }

      private void endAttr() {
         if (this.scopeStack != null) {
            this.scopeStack.pop();
         }

      }

      // $FF: synthetic method
      Scope(Object x1) {
         this();
      }
   }

   public static class ExcludeExceptFilter extends AttrsFilter {
      public ExcludeExceptFilter(Locale locale, String attrsString) throws Exception {
         super(locale, attrsString, true);
      }

      protected Result foundResult() {
         return JsonFilter.Result.Deferr;
      }

      protected Result notFoundResult() {
         return JsonFilter.Result.Exclude;
      }
   }

   public static class IncludeExceptFilter extends AttrsFilter {
      public IncludeExceptFilter(Locale locale, String attrsString) throws Exception {
         super(locale, attrsString, false);
      }

      protected Result foundResult() {
         return JsonFilter.Result.Deferr;
      }

      protected Result notFoundResult() {
         return JsonFilter.Result.Include;
      }
   }

   public static class ExcludeFilter extends AttrsFilter {
      public ExcludeFilter(Locale locale, String attrsString) throws Exception {
         super(locale, attrsString, false);
      }

      protected Result foundResult() {
         return JsonFilter.Result.Exclude;
      }

      protected Result notFoundResult() {
         return JsonFilter.Result.Deferr;
      }
   }

   public static class IncludeFilter extends AttrsFilter {
      public IncludeFilter(Locale locale, String attrsString) throws Exception {
         super(locale, attrsString, true);
      }

      protected Result foundResult() {
         return JsonFilter.Result.Include;
      }

      protected Result notFoundResult() {
         return JsonFilter.Result.Deferr;
      }
   }

   public abstract static class AttrsFilter implements Filter {
      private Set attrs = new HashSet();
      private Set parentAttrs = new HashSet();

      protected AttrsFilter(Locale locale, String attrsString, boolean includeParents) throws Exception {
         String[] var4 = attrsString.split(",");
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String attrString = var4[var6];
            attrString = attrString.trim();
            if (this.attrs.contains(attrString) || this.parentAttrs.contains(attrString)) {
               this.throwOverlappingFieldsException(locale, attrsString);
            }

            this.attrs.add(attrString);
            String parent = "";
            boolean first = true;
            String[] var10 = attrString.split("\\.");
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String comp = var10[var12];
               parent = this.processParentComponent(locale, attrsString, attrString, parent, comp.trim(), first);
               first = false;
            }
         }

         if (!includeParents) {
            this.parentAttrs.clear();
         }

      }

      private String processParentComponent(Locale locale, String attrsString, String attrString, String parent, String comp, boolean first) throws Exception {
         StringBuilder sb = new StringBuilder();
         sb.append(parent);
         if (!first) {
            sb.append(".");
         }

         sb.append(comp);
         parent = sb.toString();
         if (!parent.equals(attrString)) {
            if (this.attrs.contains(parent)) {
               this.throwOverlappingFieldsException(locale, attrsString);
            }

            this.parentAttrs.add(parent);
         }

         return parent;
      }

      private void throwOverlappingFieldsException(Locale locale, String attrs) throws Exception {
         String msg = MessageUtil.formatter(locale).msgOverLappingFieldsSpecified(attrs);
         throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
      }

      public Result filter(String attr) {
         if (!this.attrs.contains(attr) && !this.parentAttrs.contains(attr)) {
            Iterator var2 = this.attrs.iterator();

            String a;
            do {
               if (!var2.hasNext()) {
                  return this.notFoundResult();
               }

               a = (String)var2.next();
            } while(!attr.startsWith(a + "."));

            return this.foundResult();
         } else {
            return this.foundResult();
         }
      }

      protected abstract Result foundResult();

      protected abstract Result notFoundResult();
   }

   public static class IdentityFilter implements Filter {
      private String identityAttr;

      public IdentityFilter(String identityAttr) {
         this.identityAttr = identityAttr;
      }

      public Result filter(String attr) {
         return this.identityAttr != null && this.identityAttr.equals(attr) ? JsonFilter.Result.Include : JsonFilter.Result.Deferr;
      }
   }

   public interface Filter {
      Result filter(String var1);
   }

   protected static enum Result {
      Exclude,
      Include,
      Deferr;
   }
}
