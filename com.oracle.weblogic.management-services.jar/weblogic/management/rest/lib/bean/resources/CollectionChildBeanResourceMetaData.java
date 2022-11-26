package weblogic.management.rest.lib.bean.resources;

import java.util.Set;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.BeanType;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;

public class CollectionChildBeanResourceMetaData extends BeanResourceMetaData {
   private ContainedBeansType type;

   public void init(ResourceMetaData parent, ContainedBeansType type) throws Exception {
      this.type = type;
      super.init(parent, BeanType.getBeanType(this.request(), this.type().getTypeName()));
   }

   protected ContainedBeansType type() throws Exception {
      return this.type;
   }

   public String entityClassName() throws Exception {
      return this.type.getTypeName();
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

   public String path() throws Exception {
      return "{" + this.pathParamName() + "}";
   }

   protected Set POSTRolesAllowed() throws Exception {
      return MetaDataUtils.getRolesAllowed(this.type().getType(this.request()));
   }

   protected Set DELETERolesAllowed() throws Exception {
      return MetaDataUtils.getRolesAllowed(this.type().getDestroyer(this.request()));
   }

   protected String pathParamName() throws Exception {
      return BeanType.getBeanType(this.request(), this.entityClassName()).getIdentityPropertyType().getName();
   }
}
