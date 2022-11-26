package weblogic.management.rest.lib.bean.utils;

import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import weblogic.management.provider.ManagementService;

public class BeanType {
   private static Map types = new HashMap();
   private String name;
   private BeanInfo beanInfo;
   private VersionVisibility versionVisibility;
   private Set subTypes;
   private PropertyType identityPropertyType;
   private Boolean visibleToPartitions;
   private boolean hasVisibleToPartitionsAlwaysMember;
   private Class beanClass;
   private Map propertyTypes = new HashMap();
   private Map containedBeanTypes = new HashMap();
   private Map containedBeanCreateFormTypes = new HashMap();
   private Map referencedBeanTypes = new HashMap();
   private Map containedBeansTypes = new HashMap();
   private Map containedBeansCreateFormTypes = new HashMap();
   private Map referencedBeansTypes = new HashMap();
   private Map actionTypes = new HashMap();
   private Map customResourceTypes = new HashMap();
   private Map propertyProblems = null;
   private Map methodProblems = null;

   public static BeanType getBeanType(HttpServletRequest request, String name) throws Exception {
      BeanType type = (BeanType)types.get(name);
      if (type == null) {
         type = new BeanType(request, name);
         types.put(name, type);
      }

      return type;
   }

   public static BeanType getBeanType(HttpServletRequest request, Object bean) throws Exception {
      return getBeanType(request, DescriptorUtils.getInterfaceClassName(DescriptorUtils.getBeanInfo(bean).getBeanDescriptor()));
   }

   public static Collection getBeanTypes() {
      return types.values();
   }

   public BeanType(HttpServletRequest request, String name) throws Exception {
      this.name = name;
      this.beanInfo = DescriptorUtils.getBeanTypeInfo(name);
      if (this.beanInfo != null) {
         this.findSubTypes();
         this.versionVisibility = VersionVisibility.getVersionVisibility(this.getBeanInfo().getBeanDescriptor());
         this.processProperties(request);
         this.processMethods(request);
         this.initVisibleToPartitions();
      }

      try {
         this.beanClass = Class.forName(this.getName());
      } catch (ClassNotFoundException var4) {
         this.beanClass = null;
      }

   }

   public boolean containsSubType(String name) {
      return this.subTypes == null ? false : this.subTypes.contains(name);
   }

   public String getName() {
      return this.name;
   }

   public BeanInfo getBeanInfo() {
      return this.beanInfo;
   }

   public VersionVisibility getVersionVisibility() {
      return this.versionVisibility;
   }

   public String getDescription() {
      return DescriptorUtils.getDescription(this.getBeanInfo().getBeanDescriptor());
   }

   public boolean isValueObject() {
      return DescriptorUtils.isValueObject(this.getBeanInfo());
   }

   public Boolean getVisibleToPartitions() {
      return this.visibleToPartitions;
   }

   public PropertyType getIdentityPropertyType() {
      return this.identityPropertyType;
   }

   public Collection getPropertyTypes() {
      return this.propertyTypes.values();
   }

   public Collection getContainedBeanTypes() {
      return this.containedBeanTypes.values();
   }

   public Collection getReferencedBeanTypes() {
      return this.referencedBeanTypes.values();
   }

   public Collection getContainedBeansTypes() {
      return this.containedBeansTypes.values();
   }

   public Collection getReferencedBeansTypes() {
      return this.referencedBeansTypes.values();
   }

   public Collection getActionTypes() {
      return this.actionTypes.values();
   }

   public PropertyType getPropertyType(String name) {
      return (PropertyType)this.propertyTypes.get(name);
   }

   public ContainedBeanType getContainedBeanType(String name) {
      return (ContainedBeanType)this.containedBeanTypes.get(name);
   }

   public ContainedBeanType getContainedBeanCreateFormType(String name) {
      return (ContainedBeanType)this.containedBeanCreateFormTypes.get(name);
   }

   public ReferencedBeanType getReferencedBeanType(String name) {
      return (ReferencedBeanType)this.referencedBeanTypes.get(name);
   }

   public ContainedBeansType getContainedBeansType(String name) {
      return (ContainedBeansType)this.containedBeansTypes.get(name);
   }

   public ContainedBeansType getContainedBeansCreateFormType(String name) {
      return (ContainedBeansType)this.containedBeansCreateFormTypes.get(name);
   }

   public ReferencedBeansType getReferencedBeansType(String name) {
      return (ReferencedBeansType)this.referencedBeansTypes.get(name);
   }

   public ActionType getActionType(String name) {
      return (ActionType)this.actionTypes.get(name);
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public boolean isCustomSecurityProvider() {
      return this.getBeanClass() == null;
   }

   public Collection getCustomResourceTypes() throws Exception {
      return this.getCustomResourceTypesMap().values();
   }

   public CustomResourceType getCustomResourceType(String name) throws Exception {
      return (CustomResourceType)this.getCustomResourceTypesMap().get(name);
   }

   public AttributeType getAttributeType(String name) {
      AttributeType at = this.getPropertyType(name);
      if (at == null) {
         at = this.getContainedBeansType(name);
      }

      if (at == null) {
         at = this.getContainedBeanType(name);
      }

      if (at == null) {
         at = this.getReferencedBeanType(name);
      }

      if (at == null) {
         at = this.getReferencedBeansType(name);
      }

      return (AttributeType)at;
   }

   private void findSubTypes() throws Exception {
      String[] sts = ManagementService.getBeanInfoAccess().getSubtypes(this.getName());
      if (sts != null && sts.length > 1) {
         this.subTypes = new HashSet();
         String[] var2 = sts;
         int var3 = sts.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String subType = var2[var4];
            if (!subType.equals(this.getName())) {
               this.subTypes.add(subType);
            }
         }
      }

   }

   private void processProperties(HttpServletRequest request) throws Exception {
      PropertyDescriptor[] var2 = this.getBeanInfo().getPropertyDescriptors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyDescriptor pd = var2[var4];
         if (DescriptorUtils.isPublicAttribute(pd) && pd.getValue("excludeFromRest") == null) {
            if (DescriptorUtils.isNonPublicBeanTypeInCurrentVersion(this, pd)) {
               this.addPropertyProblem(pd, "uses non-public (excluded, deprecated or obsolete) bean type: " + pd.getPropertyType().getName());
            } else {
               String rel;
               if (DescriptorUtils.isSingletonBean(pd)) {
                  rel = DescriptorUtils.getRelationship(this, pd);
                  if ("containment".equals(rel)) {
                     ContainedBeanTypeImpl type = new ContainedBeanTypeImpl(request, this, pd);
                     this.containedBeanTypes.put(type.getName(), type);
                     if (type.getCreator(request) != null) {
                        this.containedBeanCreateFormTypes.put(type.getCreateFormResourceName(), type);
                     }
                  } else if ("reference".equals(rel)) {
                     ReferencedBeanType type = new ReferencedBeanTypeImpl(this, pd);
                     this.referencedBeanTypes.put(type.getName(), type);
                  }
               } else if (DescriptorUtils.isBeanCollection(pd)) {
                  rel = DescriptorUtils.getRelationship(this, pd);
                  if ("containment".equals(rel)) {
                     ContainedBeansTypeImpl type = new ContainedBeansTypeImpl(this, pd);
                     this.containedBeansTypes.put(type.getName(), type);
                     if (type.getCreator(request) != null) {
                        this.containedBeansCreateFormTypes.put(type.getCreateFormResourceName(), type);
                     }
                  } else if ("reference".equals(rel)) {
                     ReferencedBeansType type = new ReferencedBeansTypeImpl(this, pd);
                     this.referencedBeansTypes.put(type.getName(), type);
                  }
               } else {
                  PropertyTypeImpl type = new PropertyTypeImpl(request, this, pd);
                  if (!type.ignore()) {
                     this.propertyTypes.put(type.getName(), type);
                     if (type.isIdentity()) {
                        this.identityPropertyType = type;
                     }
                  }
               }
            }
         }
      }

      if (this.getIdentityPropertyType() == null) {
         PropertyTypeImpl nameProperty = (PropertyTypeImpl)this.getPropertyType("name");
         if (nameProperty != null) {
            nameProperty.setIdentity(true);
            this.identityPropertyType = this.getPropertyType("name");
         }
      }

   }

   private void processMethods(HttpServletRequest request) throws Exception {
      MethodDescriptor[] var2 = this.getBeanInfo().getMethodDescriptors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodDescriptor md = var2[var4];
         if (DescriptorUtils.isPublicMethod(md)) {
            if (!InvokeUtils.invokable(request, md)) {
               this.addMethodProblem(md, InvokeUtils.getNotInvokableReason(request, md));
            } else {
               String property = DescriptorUtils.getStringField(md, "property");
               String role = DescriptorUtils.getStringField(md, "role");
               if (property != null) {
                  if (!"collection".equals(role) && !"factory".equals(role) && !"finder".equals(role)) {
                     this.addMethodProblem(md, "property specified, but missing or non-standard role: property=" + property + ", role=" + role);
                  }
               } else if ("operation".equals(role)) {
                  String restName = DescriptorUtils.getRestName((FeatureDescriptor)md);
                  ActionTypeImpl type = (ActionTypeImpl)this.getActionType(restName);
                  if (type == null) {
                     type = new ActionTypeImpl(request, this, md, restName);
                  } else {
                     type.addMethodDescriptor(request, md);
                  }

                  if (type.ignore()) {
                     this.actionTypes.remove(type.getName());
                  } else {
                     this.actionTypes.put(type.getName(), type);
                  }
               } else {
                  this.addMethodProblem(md, "neither property or role specified");
               }
            }
         }
      }

   }

   private Map getCustomResourceTypesMap() throws Exception {
      BeanResourceRegistry registry = BeanResourceRegistry.instance();
      Map customResourceTypes = registry.getCustomResourceTypes(this.getName());
      if (customResourceTypes == null) {
         Map resources = registry.customResources().get(this.getName());
         if (resources == null) {
            return new HashMap();
         }

         customResourceTypes = new HashMap();
         Iterator var4 = resources.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry e = (Map.Entry)var4.next();
            Boolean alwaysVisibleToPartitions = registry.customResources().isAlwaysVisibleToPartitions(this.getName(), (String)e.getKey());
            Boolean internal = registry.customResources().isInternal(this.getName(), (String)e.getKey());
            CustomResourceTypeImpl type = new CustomResourceTypeImpl(this, (String)e.getKey(), alwaysVisibleToPartitions, internal, (ResourceDefs)e.getValue());
            ((Map)customResourceTypes).put(type.getName(), type);
         }

         registry.registerCustomResourceTypes(this.getName(), (Map)customResourceTypes);
      }

      return (Map)customResourceTypes;
   }

   public boolean hasVisibleToPartitionsAlwaysMember() throws Exception {
      if (this.hasVisibleToPartitionsAlwaysMember) {
         return true;
      } else {
         Iterator var1 = this.getCustomResourceTypes().iterator();

         Boolean resVisible;
         do {
            if (!var1.hasNext()) {
               return false;
            }

            CustomResourceType resType = (CustomResourceType)var1.next();
            resVisible = resType.getVisibleToPartitions();
         } while(resVisible == null || !resVisible);

         return true;
      }
   }

   private void initVisibleToPartitions() throws Exception {
      this.visibleToPartitions = DescriptorUtils.getVisibleToPartitions(this.getBeanInfo().getBeanDescriptor());
      Iterator var1 = this.getPropertyTypes().iterator();

      AttributeType attrType;
      while(var1.hasNext()) {
         attrType = (AttributeType)var1.next();
         this.computeHasVisibleToPartitionsAlwaysMember(attrType);
      }

      var1 = this.getContainedBeanTypes().iterator();

      while(var1.hasNext()) {
         attrType = (AttributeType)var1.next();
         this.computeHasVisibleToPartitionsAlwaysMember(attrType);
      }

      var1 = this.getReferencedBeanTypes().iterator();

      while(var1.hasNext()) {
         attrType = (AttributeType)var1.next();
         this.computeHasVisibleToPartitionsAlwaysMember(attrType);
      }

      var1 = this.getContainedBeansTypes().iterator();

      while(var1.hasNext()) {
         attrType = (AttributeType)var1.next();
         this.computeHasVisibleToPartitionsAlwaysMember(attrType);
      }

      var1 = this.getReferencedBeansTypes().iterator();

      while(var1.hasNext()) {
         attrType = (AttributeType)var1.next();
         this.computeHasVisibleToPartitionsAlwaysMember(attrType);
      }

      var1 = this.getActionTypes().iterator();

      while(var1.hasNext()) {
         ActionType actionType = (ActionType)var1.next();
         Iterator var3 = actionType.getMethodTypes().iterator();

         while(var3.hasNext()) {
            MethodType methodType = (MethodType)var3.next();
            this.computeHasVisibleToPartitionsAlwaysMember(methodType.getVisibleToPartitions());
         }
      }

   }

   private void computeHasVisibleToPartitionsAlwaysMember(AttributeType type) throws Exception {
      this.computeHasVisibleToPartitionsAlwaysMember(type.getVisibleToPartitions());
   }

   private void computeHasVisibleToPartitionsAlwaysMember(Boolean memberVisibleToPartitions) throws Exception {
      if (memberVisibleToPartitions != null && memberVisibleToPartitions) {
         this.hasVisibleToPartitionsAlwaysMember = true;
      }

   }

   void addPropertyProblem(PropertyDescriptor pd, String problem) throws Exception {
      if (this.propertyProblems == null) {
         this.propertyProblems = new HashMap();
      }

      this.propertyProblems.put(pd, problem);
   }

   void addMethodProblem(MethodDescriptor md, String problem) throws Exception {
      if (this.methodProblems == null) {
         this.methodProblems = new HashMap();
      }

      this.methodProblems.put(md, problem);
   }

   public Map getPropertyProblems() throws Exception {
      return this.propertyProblems;
   }

   public Map getMethodProblems() throws Exception {
      return this.methodProblems;
   }
}
