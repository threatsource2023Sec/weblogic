package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.ContainedBeanType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;

public class SingletonChildBeanCreateFormResourceMetaData extends ResourceMetaData {
   private ContainedBeanType type;
   private TypeInfo entityType;

   public void init(ResourceMetaData parent, ContainedBeanType type) throws Exception {
      this.type = type;
      super.init(parent);
   }

   public ContainedBeanType type() throws Exception {
      return this.type;
   }

   public String entityClassName() throws Exception {
      return this.type.getTypeName();
   }

   public TypeInfo entityType() throws Exception {
      return this.entityRefType(this.entityClassName());
   }

   public String className() throws Exception {
      return this.entityClassName() + ".createForm";
   }

   public String path() throws Exception {
      return this.type().getCreateFormResourceName();
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.type.getName()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.type.getName())});
   }

   protected String defaultGETSummary() throws Exception {
      return "itemCreateFormGETSummary";
   }

   protected String defaultGETDesc() throws Exception {
      return "itemCreateFormGETDesc";
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return "itemExampleCreateFormGET";
   }

   protected String defaultGETRespDesc() throws Exception {
      return "itemRespDesc";
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      this.addLink(m, "create", this.parent().subUri(this.type().getCreateFormResourceName()), "itemCreateFormResItemLinkDesc");
   }

   public boolean isRecursive() throws Exception {
      return MetaDataUtils.isRecursive(this, (ContainedBeanType)this.type()) ? true : super.isRecursive();
   }

   public TypeInfo GETRespType() throws Exception {
      return MetaDataUtils.creatorEntityType(this, this.type());
   }

   protected String category() throws Exception {
      return this.type.getName();
   }
}
