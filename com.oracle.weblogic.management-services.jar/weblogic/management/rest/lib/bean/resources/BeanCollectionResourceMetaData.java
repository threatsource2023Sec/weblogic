package weblogic.management.rest.lib.bean.resources;

import java.util.Set;
import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;

public class BeanCollectionResourceMetaData extends CollectionResourceMetaData {
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

   public String path() throws Exception {
      return this.type().getName();
   }

   protected boolean supportsPOST() throws Exception {
      if (this.isEditableTree()) {
         MethodType mt = this.type().getCreator(this.request());
         if (mt != null && PartitionUtils.isVisible((ResourceMetaData)this, mt)) {
            return true;
         }
      }

      return false;
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      if (this.supportsPOST() && this.haveResourceMetaData(this.type().getChildResourceDef(this.beanTree()))) {
         this.addLink(m, "create-form", this.parent().subUri(this.type().getCreateFormResourceName()), "collectionResCreateFormLinkDesc");
      }

   }

   protected void createChildren() throws Exception {
      ContainedBeansType t = this.type();
      CollectionChildBeanResourceMetaData child = (CollectionChildBeanResourceMetaData)this.createResourceMetaData(t.getChildResourceDef(this.beanTree()));
      if (child != null) {
         child.init(this, t);
         this.addChild(child);
      }

   }

   public boolean isRecursive() throws Exception {
      return MetaDataUtils.isRecursive(this, (ContainedBeansType)this.type()) ? true : super.isRecursive();
   }

   public TypeInfo POSTReqType() throws Exception {
      return MetaDataUtils.creatorEntityType(this, this.type());
   }

   protected Set POSTRolesAllowed() throws Exception {
      return MetaDataUtils.getRolesAllowed(this.type().getCreator(this.request()));
   }
}
