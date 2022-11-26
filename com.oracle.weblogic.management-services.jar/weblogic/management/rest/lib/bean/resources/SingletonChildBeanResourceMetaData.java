package weblogic.management.rest.lib.bean.resources;

import java.util.HashSet;
import java.util.Set;
import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.BeanType;
import weblogic.management.rest.lib.bean.utils.ContainedBeanType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;

public class SingletonChildBeanResourceMetaData extends BeanResourceMetaData {
   private ContainedBeanType type;

   public void init(ResourceMetaData parent, ContainedBeanType type) throws Exception {
      this.type = type;
      super.init(parent, BeanType.getBeanType(this.request(), type.getTypeName()));
   }

   public ContainedBeanType type() throws Exception {
      return this.type;
   }

   public String path() throws Exception {
      return this.type().getName();
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.type.getName()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.type.getName())});
   }

   protected boolean supportsDELETE() throws Exception {
      if (this.isEditableTree()) {
         MethodType mt = this.type().getDestroyer(this.request());
         if (mt != null && PartitionUtils.isVisible((ResourceMetaData)this, mt)) {
            return true;
         }
      }

      return false;
   }

   protected String defaultPOSTSummary() throws Exception {
      return this.supportsCreate() ? "itemPOSTCreateOrUpdateSummary" : super.defaultPOSTSummary();
   }

   protected String defaultPOSTDesc() throws Exception {
      return this.supportsCreate() ? "itemPOSTCreateOrUpdateDesc" : super.defaultPOSTDesc();
   }

   protected void addPOSTMethodExamples(MethodInfo m) throws Exception {
      if (this.supportsCreate()) {
         this.addMethodExamples(m, "-create", "itemExamplePOST");
         this.addMethodExamples(m, "-update", "itemExamplePOST");
      } else {
         super.addPOSTMethodExamples(m);
      }

   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      super.addGETLinks(m);
      if (this.supportsCreate()) {
         this.addLink(m, "create-form", this.parent().subUri(this.type().getCreateFormResourceName()), "itemResCreateFormLinkDesc");
      }

   }

   private boolean supportsCreate() throws Exception {
      if (this.isEditableTree()) {
         MethodType mt = this.type().getCreator(this.request());
         if (mt != null && PartitionUtils.isVisible((ResourceMetaData)this, mt)) {
            return true;
         }
      }

      return false;
   }

   public boolean isRecursive() throws Exception {
      return MetaDataUtils.isRecursive(this, (ContainedBeanType)this.type()) ? true : super.isRecursive();
   }

   public TypeInfo POSTReqType() throws Exception {
      return this.supportsCreate() ? MetaDataUtils.creatorEntityType(this, this.type()) : super.POSTReqType();
   }

   protected Set POSTRolesAllowed() throws Exception {
      Set setRoles = MetaDataUtils.getRolesAllowed(this.type().getType(this.request()));
      if (this.type().getCreator(this.request()) == null) {
         return setRoles;
      } else {
         Set roles = new HashSet();
         if (setRoles != null) {
            roles.addAll(setRoles);
         }

         Set createRoles = MetaDataUtils.getRolesAllowed(this.type().getCreator(this.request()));
         if (createRoles != null) {
            roles.addAll(createRoles);
         }

         return roles.size() > 0 ? roles : null;
      }
   }

   protected Set DELETERolesAllowed() throws Exception {
      return MetaDataUtils.getRolesAllowed(this.type().getDestroyer(this.request()));
   }

   protected String category() throws Exception {
      return this.path();
   }
}
