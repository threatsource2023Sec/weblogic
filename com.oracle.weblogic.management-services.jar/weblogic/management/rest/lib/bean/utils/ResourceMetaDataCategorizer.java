package weblogic.management.rest.lib.bean.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.model.ApiInfo;
import org.glassfish.admin.rest.model.CategoryInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.resources.ResourceMetaData;

public class ResourceMetaDataCategorizer {
   private HttpServletRequest request;

   public static void categorizeResources(HttpServletRequest request, ApiInfo api, List rmds) throws Exception {
      (new ResourceMetaDataCategorizer(request)).categorizeResources(api, rmds);
   }

   private ResourceMetaDataCategorizer(HttpServletRequest request) {
      this.request = request;
   }

   private HttpServletRequest request() {
      return this.request;
   }

   private void categorizeResources(ApiInfo api, List rmds) throws Exception {
      Map entity2Rmds = this.getEntityToResourcesMap(rmds);
      Iterator var4 = entity2Rmds.keySet().iterator();

      while(var4.hasNext()) {
         String entity = (String)var4.next();
         this.categorizeEntityResources(api, entity, (List)entity2Rmds.get(entity));
      }

   }

   private Map getEntityToResourcesMap(List rmds) throws Exception {
      Map entity2Rmds = new HashMap();

      ResourceMetaData rmd;
      String entity;
      for(Iterator var3 = rmds.iterator(); var3.hasNext(); ((List)entity2Rmds.get(entity)).add(rmd)) {
         rmd = (ResourceMetaData)var3.next();
         entity = rmd.entityDisplayName();
         if (!entity2Rmds.containsKey(entity)) {
            entity2Rmds.put(entity, new ArrayList());
         }
      }

      return entity2Rmds;
   }

   private void categorizeEntityResources(ApiInfo api, String entity, List rmds) throws Exception {
      ResourceCategory category = new ResourceCategory(entity);
      Iterator var5 = rmds.iterator();

      while(var5.hasNext()) {
         ResourceMetaData rmd = (ResourceMetaData)var5.next();
         this.categorizeResourceByPath(category, rmd);
      }

      this.condenseCategory(category);
      this.addCategoryToModels(api, category);
   }

   private void categorizeResourceByPath(ResourceCategory category, ResourceMetaData rmd) throws Exception {
      ResourceMetaData parentCategoryRmd;
      for(Iterator var3 = this.getParentCategoryResources(rmd).iterator(); var3.hasNext(); category = category.ensureSubCategoryExists(parentCategoryRmd.categoryDisplayName())) {
         parentCategoryRmd = (ResourceMetaData)var3.next();
      }

      if (rmd.isActionsCategory()) {
         category = category.ensureActionsSubCategoryExists();
      }

      category.addResource(rmd);
   }

   private List getParentCategoryResources(ResourceMetaData rmd) throws Exception {
      List resources = new ArrayList();

      for(ResourceMetaData r = rmd; r != null; r = r.parent()) {
         if (r.categoryDisplayName() != null) {
            resources.add(0, r);
         }
      }

      return resources;
   }

   private void condenseCategory(ResourceCategory category) throws Exception {
      ResourceCategory leaf = this.getSingleLeafCategory(category);
      if (leaf != null) {
         if (leaf != category) {
            category.resources().addAll(leaf.resources());
            category.subCategories().clear();
            category.subCategories.putAll(leaf.subCategories());
            Iterator var3 = leaf.subCategories().values().iterator();

            while(var3.hasNext()) {
               ResourceCategory subCategory = (ResourceCategory)var3.next();
               subCategory.setParent(category);
            }

         }
      }
   }

   private ResourceCategory getSingleLeafCategory(ResourceCategory category) throws Exception {
      int numSubCategories = category.subCategories().size();
      if (numSubCategories > 1) {
         return null;
      } else if (numSubCategories == 0) {
         return category;
      } else if (((ResourceCategory)category.subCategories().values().iterator().next()).isActions()) {
         return category;
      } else {
         return category.resources().size() > 0 ? null : this.getSingleLeafCategory((ResourceCategory)category.subCategories().values().iterator().next());
      }
   }

   private void addCategoryToModels(ApiInfo api, ResourceCategory category) throws Exception {
      this._addCategoryToModels(api.createCategory(this.getCategoryTitle(category), this.getCategoryDescription(category)), category);
   }

   private void addSubCategoryToModels(CategoryInfo parentCI, ResourceCategory subCategory) throws Exception {
      this._addCategoryToModels(parentCI.createSubCategory(this.getSubCategoryTitle(subCategory), this.getSubCategoryDescription(subCategory)), subCategory);
   }

   private void _addCategoryToModels(CategoryInfo ci, ResourceCategory category) throws Exception {
      Iterator var3 = category.resources().iterator();

      while(var3.hasNext()) {
         ResourceMetaData rmd = (ResourceMetaData)var3.next();
         rmd.resource().setCategory(ci);
      }

      var3 = category.subCategories().values().iterator();

      while(var3.hasNext()) {
         ResourceCategory subCategory = (ResourceCategory)var3.next();
         this.addSubCategoryToModels(ci, subCategory);
      }

   }

   private String getCategoryTitle(ResourceCategory category) throws Exception {
      return StringUtil.lowerCaseWordsToUpperCaseWords(category.name());
   }

   private String getSubCategoryTitle(ResourceCategory category) throws Exception {
      return category.isActions() ? DescriptionUtils.description(this.request(), "actionsSubCategoryTitle") : StringUtil.lowerCaseWordsToUpperCaseWords(category.name());
   }

   private String getCategoryDescription(ResourceCategory category) throws Exception {
      return DescriptionUtils.description(this.request(), (String)null, (String)"defaultCategoryDesc", categoryKeys(category), this.getCategoryTitle(category));
   }

   private String getSubCategoryDescription(ResourceCategory subCategory) throws Exception {
      return DescriptionUtils.description(this.request(), (String)null, (String)this.subCategoryDefaultDescKey(subCategory), subCategoryKeys(subCategory), this.getCategoryTitle(this.getRootCategory(subCategory)), this.getSubCategoryScope(subCategory));
   }

   private ResourceCategory getRootCategory(ResourceCategory category) throws Exception {
      while(category.parent() != null) {
         category = category.parent();
      }

      return category;
   }

   private String subCategoryDefaultDescKey(ResourceCategory subCategory) throws Exception {
      if (subCategory.isActions()) {
         return subCategory.parent().isSubCategory() ? "defaultSubCategoryActionsDesc" : "defaultCategoryActionsDesc";
      } else {
         return "defaultSubCategoryDesc";
      }
   }

   private String getSubCategoryScope(ResourceCategory subCategory) throws Exception {
      if (subCategory.isActions()) {
         subCategory = subCategory.parent();
      }

      boolean first = true;
      boolean multipleScopes = false;

      String scope;
      for(scope = ""; subCategory.parent() != null; subCategory = subCategory.parent()) {
         if (!first) {
            multipleScopes = true;
            scope = " / " + scope;
         }

         first = false;
         scope = this.getCategoryTitle(subCategory) + scope;
      }

      if (multipleScopes) {
         scope = "[ " + scope + " ]";
      }

      return scope;
   }

   private static String[] categoryKeys(ResourceCategory category) throws Exception {
      return DescriptionUtils.keys("categories/" + category.name());
   }

   private static String[] subCategoryKeys(ResourceCategory subCategory) throws Exception {
      boolean first = true;

      String key;
      for(key = ""; subCategory != null; subCategory = subCategory.parent()) {
         if (!first) {
            key = "." + key;
         }

         first = false;
         key = subCategory.name() + key;
      }

      return DescriptionUtils.keys("categories/" + key);
   }

   private class ResourceCategory {
      private static final String ACTIONS = "actions";
      private ResourceCategory parent;
      private String name;
      private boolean isActions;
      private List resources;
      private Map subCategories;

      private ResourceCategory(String name) throws Exception {
         this((ResourceCategory)null, name, false);
      }

      private ResourceCategory(ResourceCategory parent, String name, boolean isActions) throws Exception {
         this.resources = new ArrayList();
         this.subCategories = new HashMap();
         this.parent = parent;
         this.name = name;
         this.isActions = isActions;
      }

      private void addResource(ResourceMetaData rmd) {
         this.resources().add(rmd);
      }

      private ResourceCategory ensureSubCategoryExists(String name) throws Exception {
         return this.ensureSubCategoryExists(name, false);
      }

      private ResourceCategory ensureActionsSubCategoryExists() throws Exception {
         return this.ensureSubCategoryExists("actions", true);
      }

      private ResourceCategory ensureSubCategoryExists(String name, boolean isActions) throws Exception {
         if (this.isActions()) {
            throw new AssertionError("Action categories must not parent other categories: " + this.parent.name() + " " + name);
         } else {
            ResourceCategory sub = (ResourceCategory)this.subCategories().get(name);
            if (sub != null && sub.isActions() != isActions) {
               throw new AssertionError("Existing category does not match isActions : " + this.name() + " " + name + " " + isActions + " " + sub.isActions());
            } else {
               if (sub == null) {
                  this.subCategories().put(name, ResourceMetaDataCategorizer.this.new ResourceCategory(this, name, isActions));
               }

               return (ResourceCategory)this.subCategories().get(name);
            }
         }
      }

      private boolean isSubCategory() {
         return this.parent() != null;
      }

      private boolean isActions() {
         return this.isActions;
      }

      private void setParent(ResourceCategory parent) {
         this.parent = parent;
      }

      private ResourceCategory parent() {
         return this.parent;
      }

      private String name() {
         return this.name;
      }

      private Map subCategories() {
         return this.subCategories;
      }

      private List resources() {
         return this.resources;
      }

      // $FF: synthetic method
      ResourceCategory(String x1, Object x2) throws Exception {
         this(x1);
      }
   }
}
