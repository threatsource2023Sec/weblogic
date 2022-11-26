package weblogic.management.rest.lib.bean.utils;

import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.model.ApiInfo;
import org.glassfish.admin.rest.model.ArrayTypeInfo;
import org.glassfish.admin.rest.model.BeanReferenceTypeInfo;
import org.glassfish.admin.rest.model.BeanReferencesTypeInfo;
import org.glassfish.admin.rest.model.ConstraintInfo;
import org.glassfish.admin.rest.model.ConstraintType;
import org.glassfish.admin.rest.model.EntityInfo;
import org.glassfish.admin.rest.model.EntityRefTypeInfo;
import org.glassfish.admin.rest.model.ObjectTypeInfo;
import org.glassfish.admin.rest.model.PrimitiveTypeInfo;
import org.glassfish.admin.rest.model.PropertyInfo;
import org.glassfish.admin.rest.model.ResourceInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.model.VoidTypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.resources.BeanCollectionResourceMetaData;
import weblogic.management.rest.lib.bean.resources.CustomResourceMetaData;
import weblogic.management.rest.lib.bean.resources.ResourceMetaData;
import weblogic.management.rest.lib.bean.resources.SingletonChildBeanResourceMetaData;
import weblogic.management.runtime.TaskRuntimeMBean;

public class MetaDataUtils {
   private static final String[] PROGRESS_LEGAL_VALUES = new String[]{"succeeded", "processing", "failed"};
   private static final String ROLE_ADMIN = "admin";
   private static final String ROLE_DEPLOYER = "Deployer";
   private static final String ROLE_MONITOR = "Monitor";
   private static final String ROLE_OPERATOR = "Operator";
   public static final Set ALL_ROLES_ALLOWED = unmodifiableSet("admin", "Deployer", "Monitor", "Operator");
   public static final Set DEPLOYER_ROLES_ALLOWED = unmodifiableSet("admin", "Deployer");
   public static final Set ADMIN_ROLE_ALLOWED = unmodifiableSet("admin");
   private static boolean debug;
   private static boolean verbose;
   private static final String RESOURCES_PREFIX = "resources";
   private static final String TREE_PREFIX = "/management/weblogic/{version}/";

   public static boolean isDebugEnabled() {
      return debug;
   }

   public static void setDebugEnabled(boolean val) {
      debug = val;
   }

   public static boolean isVerboseEnabled() {
      return verbose;
   }

   public static void setVerboseEnabled(boolean val) {
      verbose = val;
   }

   public static boolean isRecursive(ResourceMetaData rmd, CustomResourceType type) throws Exception {
      return isRecursive(rmd, type, true);
   }

   public static boolean isRecursive(ResourceMetaData rmd, CustomResourceType type, boolean startAtParent) throws Exception {
      for(rmd = startAtParent ? rmd.parent() : rmd; rmd != null; rmd = rmd.parent()) {
         if (rmd instanceof CustomResourceMetaData && type != null && ((CustomResourceMetaData)rmd).type() == type) {
            return true;
         }
      }

      return false;
   }

   public static boolean isRecursive(ResourceMetaData rmd, ContainedBeanType type) throws Exception {
      return isRecursive(rmd, type, true);
   }

   public static boolean isRecursive(ResourceMetaData rmd, ContainedBeanType type, boolean startAtParent) throws Exception {
      for(rmd = startAtParent ? rmd.parent() : rmd; rmd != null; rmd = rmd.parent()) {
         if (rmd instanceof SingletonChildBeanResourceMetaData && ((SingletonChildBeanResourceMetaData)rmd).type() == type) {
            return true;
         }
      }

      return false;
   }

   public static boolean isRecursive(ResourceMetaData rmd, ContainedBeansType type) throws Exception {
      return isRecursive(rmd, type, true);
   }

   public static boolean isRecursive(ResourceMetaData rmd, ContainedBeansType type, boolean startAtParent) throws Exception {
      for(rmd = startAtParent ? rmd.parent() : rmd; rmd != null; rmd = rmd.parent()) {
         if (rmd instanceof BeanCollectionResourceMetaData && ((BeanCollectionResourceMetaData)rmd).type() == type) {
            return true;
         }
      }

      return false;
   }

   public static EntityInfo supportsAsync(HttpServletRequest request, EntityInfo e) throws Exception {
      addBooleanProperty(request, e, "completed", "asyncJobCompletedDesc");
      addProperty(request, e, "startTime", Date.class, "asyncJobStartTimeDesc");
      addProperty(request, e, "endTime", Date.class, "asyncJobEndTimeDesc");
      addStringProperty(request, e, "progress", "asyncJobProgressDesc").addConstraint(new ConstraintInfo(ConstraintType.LEGAL_VALUES, PROGRESS_LEGAL_VALUES));
      return e;
   }

   public static boolean isEntityReadOnly(HttpServletRequest request, ResourceMetaData rmd, String type) throws Exception {
      BeanType bt = BeanType.getBeanType(request, type);
      if (bt.getBeanInfo() == null) {
         return true;
      } else {
         Iterator var4 = bt.getPropertyTypes().iterator();

         PropertyType t;
         do {
            if (!var4.hasNext()) {
               var4 = bt.getReferencedBeansTypes().iterator();

               ReferencedBeansType t;
               do {
                  if (!var4.hasNext()) {
                     var4 = bt.getReferencedBeanTypes().iterator();

                     ReferencedBeanType t;
                     do {
                        if (!var4.hasNext()) {
                           return true;
                        }

                        t = (ReferencedBeanType)var4.next();
                     } while(!isVisible(rmd, t) || !t.isWritable());

                     return false;
                  }

                  t = (ReferencedBeansType)var4.next();
               } while(!isVisible(rmd, t) || !t.isWritable());

               return false;
            }

            t = (PropertyType)var4.next();
         } while(!isVisible(rmd, t) || !t.isWritable());

         return false;
      }
   }

   public static String entityDisplayName(String cn) throws Exception {
      return StringUtil.camelCaseToLowerCaseWords(BeanUtils.getWebLogicMBeanType(cn));
   }

   public static void createEntities(ResourceMetaData rmd) throws Exception {
      String type = rmd.entityClassName();
      Class beanClass = BeanType.getBeanType(rmd.request(), type).getBeanClass();
      createEntities(rmd.request(), rmd.api(), rmd, type, TaskRuntimeMBean.class.isAssignableFrom(beanClass));
   }

   public static void createEntities(HttpServletRequest request, ApiInfo api, String type, boolean async) throws Exception {
      createEntities(request, api, (ResourceMetaData)null, type, async);
   }

   private static void createEntities(HttpServletRequest request, ApiInfo api, ResourceMetaData rmd, String type, boolean async) throws Exception {
      BeanType bt = BeanType.getBeanType(request, type);
      if (bt.getBeanInfo() != null) {
         String entityClassName = bt.getName();
         boolean createdEntity = false;
         EntityInfo e = api.getEntity(entityClassName);
         if (e == null) {
            e = createEntity(request, api, entityClassName, bt.getDescription());
            createdEntity = true;
         }

         addProperties(request, bt, rmd, e, async, createdEntity);
      }
   }

   private static void addProperties(HttpServletRequest request, BeanType bt, ResourceMetaData rmd, EntityInfo e, boolean async, boolean createdEntity) throws Exception {
      Iterator var6 = bt.getPropertyTypes().iterator();

      while(true) {
         PropertyType t;
         String prop;
         boolean visible;
         do {
            if (!var6.hasNext()) {
               var6 = bt.getReferencedBeansTypes().iterator();

               while(var6.hasNext()) {
                  ReferencedBeansType t = (ReferencedBeansType)var6.next();
                  processEntityMemberType(request, e, t, isVisible(rmd, t), createdEntity);
               }

               var6 = bt.getReferencedBeanTypes().iterator();

               while(var6.hasNext()) {
                  ReferencedBeanType t = (ReferencedBeanType)var6.next();
                  processEntityMemberType(request, e, t, isVisible(rmd, t), createdEntity);
               }

               if (createdEntity && async) {
                  supportsAsync(request, e);
               }

               return;
            }

            t = (PropertyType)var6.next();
            prop = t.getName();
            visible = isVisible(rmd, t);
         } while(visible && async && prop.equals("endTimeAsLong"));

         processEntityMemberType(request, e, t, visible, createdEntity);
      }
   }

   public static boolean isVisible(ResourceMetaData rmd, MemberType t) throws Exception {
      if (!t.isVisibleToLatestVersion()) {
         return false;
      } else {
         return rmd != null ? PartitionUtils.isVisible(rmd, t) : true;
      }
   }

   private static void processEntityMemberType(HttpServletRequest request, EntityInfo e, MemberType mt, boolean visible, boolean createdEntity) throws Exception {
      String prop = mt.getName();
      boolean existed = createdEntity ? false : e.propertyExists(prop);
      if ((!visible || !existed) && (visible || existed)) {
         if (visible) {
            processEntityMemberType(request, e, mt);
         }

         if (!createdEntity && (visible && !existed || !visible && existed)) {
            setNotVisibleForDomainScopedMBeans(e, prop);
         }

      }
   }

   private static void setNotVisibleForDomainScopedMBeans(EntityInfo e, String propertyName) throws Exception {
      PropertyInfo p = e.getProperty(propertyName);
      Iterator var3 = p.getConstraints().iterator();

      ConstraintInfo c;
      do {
         if (!var3.hasNext()) {
            p.addConstraint(new ConstraintInfo(ConstraintType.NOT_VISIBLE_FOR_DOMAIN_SCOPED_MBEANS));
            return;
         }

         c = (ConstraintInfo)var3.next();
      } while(c.getType() != ConstraintType.NOT_VISIBLE_FOR_DOMAIN_SCOPED_MBEANS);

   }

   private static void processEntityMemberType(HttpServletRequest request, EntityInfo e, MemberType mt) throws Exception {
      if (mt instanceof PropertyType) {
         processEntityPropertyType(request, e, (PropertyType)mt);
      } else if (mt instanceof ReferencedBeansType) {
         processEntityReferencedBeansType(request, e, (ReferencedBeansType)mt);
      } else {
         if (!(mt instanceof ReferencedBeanType)) {
            throw new AssertionError("Unexpected member type : " + mt);
         }

         processEntityReferencedBeanType(request, e, (ReferencedBeanType)mt);
      }

   }

   private static void processEntityPropertyType(HttpServletRequest request, EntityInfo e, PropertyType pt) throws Exception {
      BaseMarshaller m = getBaseMarshaller(pt);
      PropertyInfo p = createProperty(request, e, pt, m.getDocType(request, e.getApi()));
      if (pt.hasSecureValue()) {
         ConstraintType type = pt.isSecureValueDocOnly() ? ConstraintType.DOC_ONLY_SECURE_VALUE : ConstraintType.SECURE_VALUE;
         p.addConstraint(new ConstraintInfo(type, pt.getSecureValue()));
      }

      if (pt.hasDefaultValue()) {
         p.addConstraint(new ConstraintInfo(ConstraintType.DEFAULT_VALUE, pt.getDefaultValue()));
      }

      if (pt.hasProductionModeDefaultValue()) {
         p.addConstraint(new ConstraintInfo(ConstraintType.PRODUCTION_MODE_DEFAULT_VALUE, pt.getProductionModeDefaultValue()));
      }

      if (pt.getMin() != null) {
         p.addConstraint(new ConstraintInfo(ConstraintType.MIN_VALUE, pt.getMin()));
      }

      if (pt.getMax() != null) {
         p.addConstraint(new ConstraintInfo(ConstraintType.MAX_VALUE, pt.getMax()));
      }

      if (pt.getLegalValues() != null) {
         p.addConstraint(new ConstraintInfo(ConstraintType.LEGAL_VALUES, pt.getLegalValues()));
      }

      if (pt.isNullable()) {
         p.addConstraint(new ConstraintInfo(ConstraintType.LEGAL_NULL));
      }

      if (pt.isUnharvestable()) {
         p.addConstraint(new ConstraintInfo(ConstraintType.UNHARVESTABLE));
      }

   }

   private static BaseMarshaller getBaseMarshaller(PropertyType pt) throws Exception {
      if (pt.getMarshaller() != null && pt.getMarshaller().getValueMarshaller() != null) {
         return pt.getMarshaller().getValueMarshaller();
      } else {
         throw new AssertionError("No base marshaller for " + pt.getName());
      }
   }

   private static void processEntityReferencedBeansType(HttpServletRequest request, EntityInfo e, ReferencedBeansType at) throws Exception {
      createProperty(request, e, at, new BeanReferencesTypeInfo(entityDisplayName(at.getTypeName()), at.getTypeName()));
   }

   private static void processEntityReferencedBeanType(HttpServletRequest request, EntityInfo e, ReferencedBeanType at) throws Exception {
      createProperty(request, e, at, new BeanReferenceTypeInfo(entityDisplayName(at.getTypeName()), at.getTypeName()));
   }

   private static PropertyInfo createProperty(HttpServletRequest request, EntityInfo e, AttributeType at, TypeInfo type) throws Exception {
      String propName = at.getName();
      String propDesc = DescriptionUtils.description(request, "/" + propName, at.getDescription(), true, entityKeys(e), e.getDisplayName(), propName);
      PropertyInfo p = addProperty((ObjectTypeInfo)e, (String)propName, (TypeInfo)type, (String)propDesc);
      if (!at.isWritable()) {
         p.addConstraint(new ConstraintInfo(ConstraintType.READ_ONLY));
      }

      if (at.isRestartRequired()) {
         p.addConstraint(new ConstraintInfo(ConstraintType.RESTART_REQUIRED));
      }

      return p;
   }

   public static String[] entityKeys(EntityInfo entity) throws Exception {
      return entityKeys(entity.getClassName());
   }

   public static String[] entityKeys(String entityClassName) throws Exception {
      return DescriptionUtils.keys("entities/" + entityClassName);
   }

   public static EntityInfo ensureEntityCreated(HttpServletRequest request, ApiInfo api, String entityClassName) throws Exception {
      return api.getEntity(entityClassName) != null ? null : createEntity(request, api, entityClassName);
   }

   public static EntityInfo createEntity(HttpServletRequest request, ApiInfo api, String entityClassName) throws Exception {
      return createEntity(request, api, entityClassName, (String)null);
   }

   public static EntityInfo createEntity(HttpServletRequest request, ApiInfo api, String entityClassName, String defaultDesc) throws Exception {
      String entityDisplayName = entityDisplayName(entityClassName);
      String entityDesc = DescriptionUtils.description(request, (String)null, (String)defaultDesc, entityKeys(entityClassName), entityDisplayName);
      return api.createEntity(entityClassName, entityDisplayName, entityDesc);
   }

   public static PropertyInfo addStringProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addStringProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addStringProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, String.class, defaultDesc);
   }

   public static PropertyInfo addBinaryProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addBinaryProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addBinaryProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, (TypeInfo)PrimitiveTypeInfo.BYTES, defaultDesc);
   }

   public static PropertyInfo addBooleanProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addBooleanProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addBooleanProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, Boolean.TYPE, defaultDesc);
   }

   public static PropertyInfo addLongProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addLongProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addLongProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, Long.TYPE, defaultDesc);
   }

   public static PropertyInfo addIntegerProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addIntegerProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addIntegerProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, Integer.TYPE, defaultDesc);
   }

   public static PropertyInfo addDoubleProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addDoubleProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addDoubleProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, Double.TYPE, defaultDesc);
   }

   public static PropertyInfo addFloatProperty(HttpServletRequest request, EntityInfo entity, String name) throws Exception {
      return addFloatProperty(request, entity, name, (String)null);
   }

   public static PropertyInfo addFloatProperty(HttpServletRequest request, EntityInfo entity, String name, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, Float.TYPE, defaultDesc);
   }

   public static PropertyInfo addArrayProperty(HttpServletRequest request, EntityInfo entity, String name, Class componentJavaType) throws Exception {
      return addArrayProperty(request, entity, name, (Class)componentJavaType, (String)null);
   }

   public static PropertyInfo addArrayProperty(HttpServletRequest request, EntityInfo entity, String name, Class componentJavaType, String defaultDesc) throws Exception {
      return addArrayProperty(request, entity, name, lookupTypeInfo(request, entity.getApi(), componentJavaType), defaultDesc);
   }

   public static PropertyInfo addArrayProperty(HttpServletRequest request, EntityInfo entity, String name, TypeInfo componentType) throws Exception {
      return addArrayProperty(request, entity, name, (TypeInfo)componentType, (String)null);
   }

   public static PropertyInfo addArrayProperty(HttpServletRequest request, EntityInfo entity, String name, TypeInfo componentType, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, (TypeInfo)(new ArrayTypeInfo(componentType)), defaultDesc);
   }

   public static PropertyInfo addBeanReferenceProperty(HttpServletRequest request, EntityInfo entity, String name, String beanType) throws Exception {
      return addBeanReferenceProperty(request, entity, name, beanType, (String)null);
   }

   public static PropertyInfo addBeanReferenceProperty(HttpServletRequest request, EntityInfo entity, String name, String beanType, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, (TypeInfo)(new BeanReferenceTypeInfo(entityDisplayName(beanType), beanType)), defaultDesc);
   }

   public static PropertyInfo addBeanReferencesProperty(HttpServletRequest request, EntityInfo entity, String name, String beanType) throws Exception {
      return addBeanReferencesProperty(request, entity, name, beanType, (String)null);
   }

   public static PropertyInfo addBeanReferencesProperty(HttpServletRequest request, EntityInfo entity, String name, String beanType, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, (TypeInfo)(new BeanReferencesTypeInfo(entityDisplayName(beanType), beanType)), defaultDesc);
   }

   public static PropertyInfo addProperty(HttpServletRequest request, EntityInfo entity, String name, Class javaType) throws Exception {
      return addProperty(request, entity, name, (Class)javaType, (String)null);
   }

   public static PropertyInfo addProperty(HttpServletRequest request, EntityInfo entity, String name, Class javaType, String defaultDesc) throws Exception {
      return addProperty(request, entity, name, lookupTypeInfo(request, entity.getApi(), javaType), defaultDesc);
   }

   public static PropertyInfo addProperty(HttpServletRequest request, EntityInfo entity, String name, TypeInfo type) throws Exception {
      return addProperty(request, entity, name, (TypeInfo)type, (String)null);
   }

   public static PropertyInfo addProperty(HttpServletRequest request, EntityInfo entity, String name, TypeInfo type, String defaultDesc) throws Exception {
      return addProperty((ObjectTypeInfo)entity, (String)name, (TypeInfo)type, (String)DescriptionUtils.description(request, "/" + name, defaultDesc, entityKeys(entity.getClassName()), entity.getDisplayName(), name));
   }

   public static PropertyInfo addProperty(ObjectTypeInfo objectType, String name, TypeInfo type, String description) throws Exception {
      return objectType.createProperty(name, type, description);
   }

   private static TypeInfo lookupTypeInfo(HttpServletRequest request, ApiInfo api, Class javaType) throws Exception {
      return DefaultMarshallers.instance().getMarshaller(request, javaType).getDocType(request, api);
   }

   public static Set getRolesAllowed(BeanType beanType) throws Exception {
      Set roles = new HashSet();
      Iterator var2 = beanType.getPropertyTypes().iterator();

      AttributeType type;
      while(var2.hasNext()) {
         type = (AttributeType)var2.next();
         collectRolesAllowed(roles, type);
      }

      var2 = beanType.getReferencedBeanTypes().iterator();

      while(var2.hasNext()) {
         type = (AttributeType)var2.next();
         collectRolesAllowed(roles, type);
      }

      var2 = beanType.getReferencedBeansTypes().iterator();

      while(var2.hasNext()) {
         type = (AttributeType)var2.next();
         collectRolesAllowed(roles, type);
      }

      return roles;
   }

   private static void collectRolesAllowed(Set roles, AttributeType type) throws Exception {
      if (type.isVisibleToLatestVersion()) {
         if (type.isWritable()) {
            Set r = getRolesAllowed(type);
            if (r != null) {
               roles.addAll(r);
            }

         }
      }
   }

   public static Set getRolesAllowed(AttributeType type) throws Exception {
      return getRolesAllowed(type, type.getPropertyDescriptor());
   }

   public static Set getRolesAllowed(MethodType type) throws Exception {
      return type == null ? null : getRolesAllowed(type, type.getMethodDescriptor());
   }

   private static Set getRolesAllowed(MemberType type, FeatureDescriptor fd) throws Exception {
      String[] r = DescriptorUtils.getRolesAllowed(fd);
      if (r == null) {
         r = DescriptorUtils.getRolesAllowed(type.getBeanType().getBeanInfo().getBeanDescriptor());
      }

      Set rtn = new HashSet();
      rtn.add("admin");
      if (r != null) {
         String[] var4 = r;
         int var5 = r.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String role = var4[var6];
            rtn.add(role);
         }
      }

      return rtn;
   }

   public static String[] resourceKeys(ResourceInfo resource) throws Exception {
      return resourceKeys(resource.getUri(), resource.getClassName());
   }

   public static String[] resourceKeys(String uri, String className) throws Exception {
      String classNameKey = "resources/" + className;
      if (uri.startsWith("/management/weblogic/{version}/")) {
         int idx = uri.indexOf("/", "/management/weblogic/{version}/".length());
         String relativeUri = "";
         if (idx != -1) {
            relativeUri = uri.substring(idx, uri.length());
         }

         return DescriptionUtils.keys("resources" + uriToKey(uri), "resources" + uriToKey("/management/weblogic/{version}/{tree}" + relativeUri), classNameKey);
      } else {
         return DescriptionUtils.keys("resources" + uriToKey(uri), classNameKey);
      }
   }

   public static String uriToKey(String uri) throws Exception {
      return uri;
   }

   private static Set unmodifiableSet(String... vals) {
      Set set = new HashSet();
      String[] var2 = vals;
      int var3 = vals.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String val = var2[var4];
         set.add(val);
      }

      return Collections.unmodifiableSet(set);
   }

   public static TypeInfo actionRespType(HttpServletRequest request, ResourceInfo resource, String scope, TypeInfo returnType, Object... args) throws Exception {
      if (returnType == VoidTypeInfo.instance()) {
         return returnType;
      } else {
         ObjectTypeInfo ot = new ObjectTypeInfo(DescriptionUtils.description(request, "actionReturnTitle"));
         addProperty(ot, "return", returnType, DescriptionUtils.description(request, scope + "/resp" + "/return", "actionReturnDesc", false, resourceKeys(resource), args));
         return ot;
      }
   }

   public static TypeInfo creatorEntityType(ResourceMetaData rmd, ContainedBeanAttributeType attrType) throws Exception {
      String entityClassName = rmd.entityClassName();
      createEntities(rmd);
      EntityInfo e = rmd.api().getEntity(entityClassName);
      MethodType mt = attrType.getCreator(rmd.request());
      MethodDescriptor md = mt.getMethodDescriptor();
      ParameterDescriptor[] parameterDescriptors = md.getParameterDescriptors();
      Class[] parameterTypes = md.getMethod().getParameterTypes();
      ObjectTypeInfo ot = null;

      for(int i = 0; parameterDescriptors != null && i < parameterDescriptors.length; ++i) {
         ot = addParamIfNotOnEntity(rmd, e, ot, parameterDescriptors[i], parameterTypes[i]);
      }

      if (ot != null) {
         copyEntityPropertiesToObjectType(e, ot);
         return ot;
      } else {
         return new EntityRefTypeInfo(entityDisplayName(entityClassName), entityClassName);
      }
   }

   private static ObjectTypeInfo addParamIfNotOnEntity(ResourceMetaData rmd, EntityInfo e, ObjectTypeInfo ot, ParameterDescriptor pd, Class pt) throws Exception {
      String paramName = pd.getName();
      TypeInfo paramType = DefaultMarshallers.instance().getMarshaller(rmd.request(), pt).getDocType(rmd.request(), rmd.api());
      PropertyInfo p = e.getProperty(paramName);
      if (p != null) {
         return ot;
      } else {
         if (ot == null) {
            ot = new ObjectTypeInfo(e.getDisplayName());
         }

         addNonEntityCreatorParam(rmd, e, ot, paramName, paramType, pd);
         return ot;
      }
   }

   private static void addNonEntityCreatorParam(ResourceMetaData rmd, EntityInfo e, ObjectTypeInfo ot, String paramName, TypeInfo paramType, ParameterDescriptor pd) throws Exception {
      String desc = DescriptionUtils.description(rmd.request(), "/" + paramName, pd.getShortDescription(), true, entityKeys(e), e.getDisplayName(), paramName);
      ot.createProperty(paramName, paramType, desc);
   }

   private static void copyEntityPropertiesToObjectType(EntityInfo e, ObjectTypeInfo ot) throws Exception {
      Iterator var2 = e.getProperties().iterator();

      while(var2.hasNext()) {
         PropertyInfo p = (PropertyInfo)var2.next();
         ot.createProperty(p.getName(), p.getType(), p.getDescription());
      }

   }
}
