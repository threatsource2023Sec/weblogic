package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;

public class CollectionChildBeanCreateFormResourceMetaData extends ResourceMetaData {
   private ContainedBeansType type;

   public void init(ResourceMetaData parent, ContainedBeansType type) throws Exception {
      this.type = type;
      super.init(parent);
   }

   public ContainedBeansType type() throws Exception {
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
      return "collectionCreateFormGETSummary";
   }

   protected String defaultGETDesc() throws Exception {
      return "collectionCreateFormGETDesc";
   }

   protected String defaultExampleGETBaseKey() throws Exception {
      return "collectionExampleCreateFormGET";
   }

   protected String defaultGETRespDesc() throws Exception {
      return "itemRespDesc";
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      this.addLink(m, "create", this.parent().subUri(this.type().getName()), "collectionCreateFormResCollectionLinkDesc");
   }

   public boolean isRecursive() throws Exception {
      return MetaDataUtils.isRecursive(this, (ContainedBeansType)this.type()) ? true : super.isRecursive();
   }

   public TypeInfo GETRespType() throws Exception {
      return MetaDataUtils.creatorEntityType(this, this.type());
   }

   protected String category() throws Exception {
      return this.type.getName();
   }
}
