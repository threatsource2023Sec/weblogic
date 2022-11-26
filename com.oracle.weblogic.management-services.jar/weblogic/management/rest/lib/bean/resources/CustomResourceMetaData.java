package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.CustomResourceType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;

public abstract class CustomResourceMetaData extends ResourceMetaData {
   private CustomResourceType type;

   public void init(ResourceMetaData parent, CustomResourceType type) throws Exception {
      this.type = type;
      super.init(parent);
   }

   public CustomResourceType type() throws Exception {
      return this.type;
   }

   public String path() throws Exception {
      return this.type().getName();
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.path()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.path())});
   }

   public boolean isRecursive() throws Exception {
      return MetaDataUtils.isRecursive(this, (CustomResourceType)this.type()) ? true : super.isRecursive();
   }
}
