package weblogic.management.rest.lib.bean.resources;

import java.util.Iterator;
import org.glassfish.admin.rest.model.MethodInfo;
import weblogic.management.rest.lib.bean.utils.ActionType;
import weblogic.management.rest.lib.bean.utils.BeanType;
import weblogic.management.rest.lib.bean.utils.ContainedBeanAttributeType;
import weblogic.management.rest.lib.bean.utils.ContainedBeanType;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.CustomResourceType;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;
import weblogic.management.rest.lib.bean.utils.ReferencedBeanType;
import weblogic.management.rest.lib.bean.utils.ReferencedBeansType;

public abstract class BeanResourceMetaData extends ItemResourceMetaData {
   private BeanType beanType;

   public void init(ResourceMetaData parent, BeanType beanType) throws Exception {
      this.beanType = beanType;
      super.init(parent);
   }

   public String entityClassName() throws Exception {
      return this.beanType().getName();
   }

   public BeanType beanType() throws Exception {
      return this.beanType;
   }

   protected boolean supportsPOST() throws Exception {
      return this.isEditableTree() && !this.isEntityReadOnly();
   }

   protected boolean isEntityReadOnly() throws Exception {
      return MetaDataUtils.isEntityReadOnly(this.request(), this, this.entityClassName());
   }

   protected void createEntities() throws Exception {
      MetaDataUtils.createEntities(this);
   }

   protected void addGETLinks(MethodInfo m) throws Exception {
      Iterator var2 = this.beanType().getCustomResourceTypes().iterator();

      while(var2.hasNext()) {
         CustomResourceType t = (CustomResourceType)var2.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addCustomResourceTypeLinks(m, t);
         }
      }

      var2 = this.beanType().getContainedBeansTypes().iterator();

      while(var2.hasNext()) {
         ContainedBeansType t = (ContainedBeansType)var2.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addContainedBeansTypeLinks(m, t);
         }
      }

      var2 = this.beanType().getContainedBeanTypes().iterator();

      while(var2.hasNext()) {
         ContainedBeanType t = (ContainedBeanType)var2.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addContainedBeanTypeLinks(m, t);
         }
      }

      var2 = this.beanType().getReferencedBeansTypes().iterator();

      while(var2.hasNext()) {
         ReferencedBeansType t = (ReferencedBeansType)var2.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addReferencedBeansTypeLinks(m, t);
         }
      }

      var2 = this.beanType().getReferencedBeanTypes().iterator();

      while(var2.hasNext()) {
         ReferencedBeanType t = (ReferencedBeanType)var2.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addReferencedBeanTypeLinks(m, t);
         }
      }

      var2 = this.beanType().getActionTypes().iterator();

      while(var2.hasNext()) {
         ActionType t = (ActionType)var2.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addActionTypeLinks(m, t);
         }
      }

   }

   protected void createChildren() throws Exception {
      Iterator var1 = this.beanType().getCustomResourceTypes().iterator();

      while(var1.hasNext()) {
         CustomResourceType t = (CustomResourceType)var1.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addCustomResourceTypeChildren(t);
         }
      }

      var1 = this.beanType().getContainedBeansTypes().iterator();

      while(var1.hasNext()) {
         ContainedBeansType t = (ContainedBeansType)var1.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addContainedBeansTypeChildren(t);
         }
      }

      var1 = this.beanType().getContainedBeanTypes().iterator();

      while(var1.hasNext()) {
         ContainedBeanType t = (ContainedBeanType)var1.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addContainedBeanTypeChildren(t);
         }
      }

      var1 = this.beanType().getActionTypes().iterator();

      while(var1.hasNext()) {
         ActionType t = (ActionType)var1.next();
         if (MetaDataUtils.isVisible(this, t)) {
            this.addActionTypeChildren(t);
         }
      }

   }

   protected void addCustomResourceTypeLinks(MethodInfo m, CustomResourceType t) throws Exception {
      if (this.haveResourceMetaData(t.getResourceDef(this.beanTree()))) {
         this.addChildLink(m, t.getName(), "parentResCustomResLinkDesc");
      }

   }

   protected void addContainedBeansTypeLinks(MethodInfo m, ContainedBeansType t) throws Exception {
      if (this.haveResourceMetaData(t.getResourceDef(this.beanTree()))) {
         this.addChildLink(m, t.getName(), "parentResCollectionLinkDesc");
      }

      if (this.supportsCreate(t)) {
         String createForm = t.getCreateFormResourceName();
         this.addLink(m, createForm, (String)null, this.subUri(createForm), "parentResCollectionCreateFormLinkDesc", this.appendArgs(this.defaultDescriptionArgs(), new Object[]{createForm, t.getName()}));
      }

   }

   protected void addContainedBeanTypeLinks(MethodInfo m, ContainedBeanType t) throws Exception {
      if (this.haveResourceMetaData(t.getResourceDef(this.beanTree()))) {
         this.addChildLink(m, t.getName(), "parentResItemLinkDesc");
      }

      if (this.supportsCreate(t)) {
         String createForm = t.getCreateFormResourceName();
         this.addLink(m, createForm, (String)null, this.subUri(createForm), "parentResItemCreateFormLinkDesc", this.appendArgs(this.defaultDescriptionArgs(), new Object[]{createForm, t.getName()}));
      }

   }

   protected void addReferencedBeansTypeLinks(MethodInfo m, ReferencedBeansType t) throws Exception {
   }

   protected void addReferencedBeanTypeLinks(MethodInfo m, ReferencedBeanType t) throws Exception {
      String name = t.getName();
      String path = null;
      this.addLink(m, name, (String)null, (String)path, "referenceLinkDesc", this.appendArgs(this.defaultDescriptionArgs(), new Object[]{name, MetaDataUtils.entityDisplayName(t.getTypeName())}));
   }

   protected void addActionTypeLinks(MethodInfo m, ActionType t) throws Exception {
      if (this.haveResourceMetaData(t.getResourceDef(this.beanTree()))) {
         String name = t.getName();
         this.addChildLink(m, "action", name, name, "actionLinkDesc");
      }

   }

   protected void addCustomResourceTypeChildren(CustomResourceType t) throws Exception {
      CustomResourceMetaData child = (CustomResourceMetaData)this.createResourceMetaData(t.getResourceDef(this.beanTree()));
      if (child != null) {
         child.init(this, t);
         this.addChild(child);
      }

   }

   protected void addContainedBeansTypeChildren(ContainedBeansType t) throws Exception {
      BeanCollectionResourceMetaData child = (BeanCollectionResourceMetaData)this.createResourceMetaData(t.getResourceDef(this.beanTree()));
      if (child != null) {
         child.init(this, t);
         this.addChild(child);
      }

      if (this.supportsCreate(t)) {
         CollectionChildBeanCreateFormResourceMetaData child = (CollectionChildBeanCreateFormResourceMetaData)this.createResourceMetaData(t.getCreateFormResourceDef(this.beanTree()));
         if (child != null) {
            child.init(this, t);
            this.addChild(child);
         }
      }

   }

   protected void addContainedBeanTypeChildren(ContainedBeanType t) throws Exception {
      SingletonChildBeanResourceMetaData child = (SingletonChildBeanResourceMetaData)this.createResourceMetaData(t.getResourceDef(this.beanTree()));
      if (child != null) {
         child.init(this, t);
         this.addChild(child);
      }

      if (this.supportsCreate(t)) {
         SingletonChildBeanCreateFormResourceMetaData child = (SingletonChildBeanCreateFormResourceMetaData)this.createResourceMetaData(t.getCreateFormResourceDef(this.beanTree()));
         if (child != null) {
            child.init(this, t);
            this.addChild(child);
         }
      }

   }

   protected void addActionTypeChildren(ActionType t) throws Exception {
      BeanActionResourceMetaData child = (BeanActionResourceMetaData)this.createResourceMetaData(t.getResourceDef(this.beanTree()));
      if (child != null) {
         child.init(this, t);
         this.addChild(child);
      }

   }

   protected boolean isPartitionRoot() throws Exception {
      return PartitionUtils.isRootPartitionBean(this.entityClassName());
   }

   private boolean supportsCreate(ContainedBeanAttributeType type) throws Exception {
      if (this.isEditableTree()) {
         MethodType mt = type.getCreator(this.request());
         if (mt != null && PartitionUtils.isVisible((ResourceMetaData)this, mt)) {
            return true;
         }
      }

      return false;
   }
}
