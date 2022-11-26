package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.Message;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.model.Message.Severity;
import org.glassfish.admin.rest.utils.JsonFilter;
import org.glassfish.admin.rest.utils.PropertyException;
import org.glassfish.admin.rest.utils.PropertyExceptions;
import weblogic.descriptor.SettableBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.rest.lib.utils.SecurityUtils;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class BeanUtils {
   private static final String[] MBEAN_CLASS_NAME_SUFFIXES = new String[]{"MBean", "Bean"};
   private static final String KEY_REST_PARENT_ATTR_TYPE = "RestParentAttr";
   private static final String KEY_REST_IDENTITY = "RestIdentity";
   private static Set beanClasses = new HashSet();
   private static Set nonBeanClasses = new HashSet();
   private static Set vbeanClasses = new HashSet();
   private static Set nonVBeanClasses = new HashSet();

   public static void getBean(InvocationContext ic, RestJsonResponseBody rb) throws Exception {
      String tree = TreeUtils.getBeanTree(ic);
      boolean isEditableTree = TreeUtils.isEditableTree(ic);
      BeanType type = BeanType.getBeanType(ic.request(), ic.bean());
      boolean isValueObject = type.isValueObject();
      rb.setEntity(getBeanJson(ic, rb, type));
      List path = PathUtils.getBeanPath(ic);
      rb.addSelfResourceLinks(PathUtils.getTreeRelativeUri(ic, path));
      Iterator var7 = type.getContainedBeansTypes().iterator();

      ContainedBeanAttributeType attrType;
      MethodType creator;
      while(var7.hasNext()) {
         attrType = (ContainedBeanAttributeType)var7.next();
         if (attrType.isVisibleToRequest(ic) && isVisibleToTree(tree, attrType) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            addResourceLink(ic, rb, path, attrType.getName());
            if (isEditableTree) {
               creator = attrType.getCreator(ic.request());
               if (creator != null && PartitionUtils.isVisible((InvocationContext)ic, creator)) {
                  addResourceLink(ic, rb, path, attrType.getCreateFormResourceName());
               }
            }
         }
      }

      var7 = type.getContainedBeanTypes().iterator();

      while(var7.hasNext()) {
         attrType = (ContainedBeanAttributeType)var7.next();
         if (attrType.isVisibleToRequest(ic) && isVisibleToTree(tree, attrType) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            addResourceLink(ic, rb, path, attrType.getName());
            if (isEditableTree) {
               creator = attrType.getCreator(ic.request());
               if (creator != null && PartitionUtils.isVisible((InvocationContext)ic, creator)) {
                  addResourceLink(ic, rb, path, attrType.getCreateFormResourceName());
               }
            }
         }
      }

      var7 = type.getActionTypes().iterator();

      while(true) {
         ActionType actionType;
         do {
            do {
               do {
                  do {
                     if (!var7.hasNext()) {
                        var7 = type.getCustomResourceTypes().iterator();

                        while(var7.hasNext()) {
                           CustomResourceType resType = (CustomResourceType)var7.next();
                           if (resType.isVisibleToRequest(ic) && isVisibleToTree(tree, resType) && PartitionUtils.isVisible((InvocationContext)ic, resType)) {
                              addResourceLink(ic, rb, path, resType.getName());
                           }
                        }

                        return;
                     }

                     actionType = (ActionType)var7.next();
                  } while(!actionType.isVisibleToRequest(ic));
               } while(!isVisibleToTree(tree, actionType));
            } while(!PartitionUtils.isVisible((InvocationContext)ic, actionType));
         } while(TreeUtils.isEditTree(ic) && SecurityUtils.isSecurityMBean(ic.bean()));

         String action = actionType.getName();
         rb.addResourceLink("action", action, PathUtils.getTreeRelativeUri(ic, path, action));
      }
   }

   public static JSONObject getVBean(InvocationContext ic, BeanType type) throws Exception {
      JSONObject model = new JSONObject();
      InvocationContext vbIc = ic.clone(ic.bean()).setQuery((JSONObject)null).setExpandedValues(false);
      RestJsonResponseBody vbRb = new RestJsonResponseBody(vbIc.request(), (JsonFilter)null);
      Iterator var5 = type.getPropertyTypes().iterator();

      while(var5.hasNext()) {
         PropertyType attrType = (PropertyType)var5.next();
         Object propertyJsonVal = attrType.getMarshaller().marshal(vbIc, vbRb, attrType, false);
         model.put(attrType.getName(), propertyJsonVal);
      }

      return model;
   }

   private static boolean isVisibleToTree(String tree, ResourceType type) throws Exception {
      return type.getResourceDef(tree) != null;
   }

   public static boolean isVisible(Object bean) throws Exception {
      if (bean instanceof AbstractDescriptorBean && ((AbstractDescriptorBean)bean)._isTransient() && bean instanceof WebLogicMBean) {
         String name = ((WebLogicMBean)bean).getName();
         if (name != null && name.contains("$")) {
            return false;
         }
      }

      return true;
   }

   private static JSONObject getBeanJson(InvocationContext ic, ResponseBody rb, BeanType type) throws Exception {
      JSONObject model = new JSONObject();
      JsonFilter.Scope filter = QueryUtils.getPropertiesFilter(ic.request(), ic.query()).newScope();
      if (filter.include("identity")) {
         model.put("identity", PathUtils.getJsonBeanPath(ic));
      }

      Iterator var5 = type.getPropertyTypes().iterator();

      while(var5.hasNext()) {
         PropertyType attrType = (PropertyType)var5.next();
         if (filter.include(attrType.getName()) && attrType.isVisibleToRequest(ic) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            try {
               model.put(attrType.getName(), propertyToJSON(ic, rb, attrType));
            } catch (Exception var10) {
               handleGetPropertyException(var10);
            }
         }
      }

      var5 = type.getReferencedBeanTypes().iterator();

      while(var5.hasNext()) {
         ReferencedBeanType attrType = (ReferencedBeanType)var5.next();
         if (filter.include(attrType.getName()) && attrType.isVisibleToRequest(ic) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            try {
               model.put(attrType.getName(), referenceOrReferencesToJSON(ic, rb, attrType, true, false));
            } catch (Exception var9) {
               handleGetPropertyException(var9);
            }
         }
      }

      var5 = type.getReferencedBeansTypes().iterator();

      while(var5.hasNext()) {
         ReferencedBeansType attrType = (ReferencedBeansType)var5.next();
         if (filter.include(attrType.getName()) && attrType.isVisibleToRequest(ic) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            try {
               model.put(attrType.getName(), referenceOrReferencesToJSON(ic, rb, attrType, true, false));
            } catch (Exception var8) {
               handleGetPropertyException(var8);
            }
         }
      }

      return model;
   }

   private static void handleGetPropertyException(Exception e) throws Exception {
      if (e instanceof WebApplicationException) {
         WebApplicationException we = (WebApplicationException)e;
         Response r = we.getResponse();
         if (r != null && r.getStatus() == Status.FORBIDDEN.getStatusCode()) {
            return;
         }
      }

      throw e;
   }

   private static Object propertyToJSON(InvocationContext ic, ResponseBody rb, PropertyType attrType) throws Exception {
      return propertyToJSON(ic, rb, attrType, true);
   }

   private static Object propertyToJSON(InvocationContext ic, ResponseBody rb, PropertyType attrType, boolean doAtz) throws Exception {
      return attrType.getMarshaller().marshal(ic, rb, attrType, doAtz);
   }

   private static Object referenceOrReferencesToJSON(InvocationContext ic, ResponseBody rb, BeanAttributeType attrType, boolean doAtz, boolean unwrapExpandedValues) throws Exception {
      PropertyMarshaller marshaller = new DelegatingPropertyMarshaller(DefaultMarshallers.instance().getMarshaller(ic.request(), attrType.getPropertyDescriptor().getPropertyType()));
      Object jsonValue = marshaller.marshal(ic, rb, attrType, doAtz);
      return unwrapExpandedValues && ic.expandedValues() ? ((JSONObject)jsonValue).get("value") : jsonValue;
   }

   private static void addResourceLink(InvocationContext ic, ResponseBody rb, List path, String rel) throws Exception {
      rb.addResourceLink(rel, PathUtils.getTreeRelativeUri(ic, path, rel));
   }

   public static Object getIdentity(HttpServletRequest request, JSONObject entity, BeanType type) throws Exception {
      return entity.opt(type.getIdentityPropertyType().getName());
   }

   public static Object getIdentity(InvocationContext ic) throws Exception {
      Object identity = getIdentity(ic.bean());
      if (identity == null) {
         identity = getIdentity(ic, BeanType.getBeanType(ic.request(), ic.bean()));
      }

      return identity;
   }

   public static Object getIdentity(InvocationContext ic, BeanType type) throws Exception {
      Object identity = getIdentity(ic.bean());
      if (identity == null) {
         identity = getBeanProperty(ic, type.getIdentityPropertyType(), false);
         cacheIdentity(ic.bean(), identity);
      }

      return identity;
   }

   public static Object getBeanProperty(InvocationContext ic, String attrName) throws Exception {
      return getBeanProperty(ic, (AttributeType)BeanType.getBeanType(ic.request(), ic.bean()).getPropertyType(attrName));
   }

   public static Object getBeanProperty(InvocationContext ic, AttributeType attrType) throws Exception {
      return getBeanProperty(ic, attrType, true);
   }

   public static Object getBeanProperty(InvocationContext ic, AttributeType attrType, boolean doAtz) throws Exception {
      if (doAtz) {
         AtzUtils.checkGetAccess(ic, attrType);
      }

      Object val = InvokeUtils.invoke(ic, attrType.getReader());
      if (attrType instanceof ContainedBeanType) {
         if (!isVisible(val)) {
            return null;
         }

         cacheParentAttributeType(val, (ContainedBeanAttributeType)attrType);
      } else if (attrType instanceof ContainedBeansType) {
         List beans = new ArrayList();
         if (val != null) {
            Object[] var5 = (Object[])((Object[])val);
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Object bean = var5[var7];
               if (isVisible(bean)) {
                  beans.add(bean);
                  cacheParentAttributeType(bean, (ContainedBeanAttributeType)attrType);
               }
            }

            return beans.toArray(new Object[0]);
         }
      }

      return val;
   }

   public static Object getCollectionChild(InvocationContext ic, String collectionName, String childName) throws Exception {
      JSONArray path = (JSONArray)PathUtils.getJsonBeanPath(ic);
      path.put(collectionName);
      path.put(childName);
      return PathUtils.findBean(ic, path);
   }

   public static boolean isBeanPropertySet(InvocationContext ic, AttributeType attrType) throws Exception {
      return isBeanPropertySet(ic, attrType, true);
   }

   public static boolean isBeanPropertySet(InvocationContext ic, AttributeType attrType, boolean doAtz) throws Exception {
      if (doAtz) {
         AtzUtils.checkIsSetAccess(ic, attrType);
      }

      final Object bean = ic.bean();
      if (!(bean instanceof SettableBean)) {
         return false;
      } else {
         String propertyName = attrType.getPropertyDescriptor().getName();
         if (attrType instanceof PropertyType) {
            PropertyType propertyType = (PropertyType)attrType;
            if (propertyType.isEncrypted()) {
               propertyName = propertyName + "Encrypted";
            }
         }

         final String propName = propertyName;
         Object[] args = args(attrType.getPropertyDescriptor().getName());
         MethodDescriptor md = findSetMethodDescriptor(attrType, "isSet");

         try {
            boolean rtn = (Boolean)PartitionUtils.runAs(ic, new Callable() {
               public Boolean call() throws Exception {
                  return ((SettableBean)bean).isSet(propName);
               }
            });
            ConfigAuditUtils.auditInvoke(ic, (MethodDescriptor)md, args, (Exception)null);
            return rtn;
         } catch (Exception var9) {
            ConfigAuditUtils.auditInvoke(ic, md, args, var9);
            throw var9;
         }
      }
   }

   public static void setVBeanProperties(InvocationContext ic, BeanType type, JSONObject values) throws Exception {
      PropertyExceptions problems = new PropertyExceptions();
      Iterator var4 = type.getPropertyTypes().iterator();

      while(var4.hasNext()) {
         PropertyType attrType = (PropertyType)var4.next();

         try {
            if (!values.has(attrType.getName())) {
               throw new Exception(MessageUtils.beanFormatter(ic.request()).msgMissingVBeanProperty(attrType.getName()));
            }

            Object newJsonValue = values.get(attrType.getName());
            Object newJavaValue = attrType.getUnmarshaller().unmarshal(ic, attrType, newJsonValue);
            InvokeUtils.invoke(ic, attrType.getWriter(), newJavaValue);
         } catch (Throwable var8) {
            addProblem(attrType, problems, var8);
         }
      }

      if (problems.getPropertyExceptions().size() > 0) {
         throw problems;
      }
   }

   public static void setBeanProperties(InvocationContext ic, JSONObject values) throws Exception {
      PropertyExceptions problems = new PropertyExceptions();
      BeanType type = BeanType.getBeanType(ic.request(), ic.bean());
      Iterator var4 = type.getPropertyTypes().iterator();

      while(var4.hasNext()) {
         PropertyType attrType = (PropertyType)var4.next();
         setProperty(ic, values, attrType, problems);
      }

      var4 = type.getReferencedBeansTypes().iterator();

      while(var4.hasNext()) {
         ReferencedBeansType attrType = (ReferencedBeansType)var4.next();
         setBeanReferencesAttribute(ic, values, attrType, problems);
      }

      var4 = type.getReferencedBeanTypes().iterator();

      while(var4.hasNext()) {
         ReferencedBeanType attrType = (ReferencedBeanType)var4.next();
         setBeanReferenceAttribute(ic, values, attrType, problems);
      }

      if (problems.getPropertyExceptions().size() > 0) {
         throw problems;
      }
   }

   private static void setProperty(InvocationContext ic, JSONObject values, PropertyType attrType, PropertyExceptions problems) throws Exception {
      try {
         if (attrType.isWritable() && values.has(attrType.getName()) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            Object newJsonValue = values.get(attrType.getName());
            PropertyUnmarshaller unmarshaller = attrType.getUnmarshaller();
            PropertyUnmarshaller.Action action = unmarshaller.action(ic, attrType, newJsonValue);
            if (action == PropertyUnmarshaller.Action.UNSET) {
               unsetBeanProperty(ic, attrType);
            } else if (action == PropertyUnmarshaller.Action.SET) {
               Object newJavaValue = unmarshaller.unmarshal(ic, attrType, newJsonValue);
               ResponseBody rb = new ResponseBody(ic.request());
               Object oldJsonValue = propertyToJSON(ic, rb, attrType);
               setBeanProperty(ic, attrType, newJsonValue, newJavaValue, oldJsonValue);
            }
         }
      } catch (Throwable var10) {
         addProblem(attrType, problems, var10);
      }

   }

   private static void setBeanReferenceAttribute(InvocationContext ic, JSONObject values, ReferencedBeanType attrType, PropertyExceptions problems) throws Exception {
      try {
         if (attrType.isWritable() && values.has(attrType.getName()) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            Object newJsonValue = values.get(attrType.getName());
            PropertyUnmarshaller unmarshaller = new DelegatingPropertyUnmarshaller(DefaultMarshallers.instance().getUnmarshaller(ic.request(), attrType.getPropertyDescriptor().getPropertyType()));
            PropertyUnmarshaller.Action action = unmarshaller.action(ic, attrType, newJsonValue);
            if (action == PropertyUnmarshaller.Action.UNSET) {
               unsetBeanProperty(ic, attrType);
            } else if (action == PropertyUnmarshaller.Action.SET) {
               Object newJavaValue = unmarshaller.unmarshal(ic, attrType, newJsonValue);
               ResponseBody rb = new ResponseBody(ic.request());
               Object oldJsonValue = referenceOrReferencesToJSON(ic, rb, attrType, false, true);
               setBeanProperty(ic, attrType, newJsonValue, newJavaValue, oldJsonValue);
            }
         }
      } catch (Throwable var10) {
         addProblem(attrType, problems, var10);
      }

   }

   private static void setBeanReferencesAttribute(InvocationContext ic, JSONObject values, ReferencedBeansType attrType, PropertyExceptions problems) throws Exception {
      try {
         if (attrType.isWritable() && values.has(attrType.getName()) && PartitionUtils.isVisible((InvocationContext)ic, attrType)) {
            Object newJsonValue = values.get(attrType.getName());
            PropertyUnmarshaller unmarshaller = new DelegatingPropertyUnmarshaller(DefaultMarshallers.instance().getUnmarshaller(ic.request(), attrType.getPropertyDescriptor().getPropertyType()));
            PropertyUnmarshaller.Action action = unmarshaller.action(ic, attrType, newJsonValue);
            if (action == PropertyUnmarshaller.Action.UNSET) {
               unsetBeanProperty(ic, attrType);
            } else if (action == PropertyUnmarshaller.Action.SET) {
               Object newJavaValue = unmarshaller.unmarshal(ic, attrType, newJsonValue);
               ResponseBody rb = new ResponseBody(ic.request());
               JSONArray oldJsonValue = (JSONArray)referenceOrReferencesToJSON(ic, rb, attrType, false, true);
               if (oldJsonValue != null) {
                  for(int i = 0; i < oldJsonValue.length(); ++i) {
                     JSONObject ref = oldJsonValue.getJSONObject(i);
                     ref.remove("links");
                  }
               }

               setBeanProperty(ic, attrType, newJsonValue, newJavaValue, oldJsonValue);
            }
         }
      } catch (Throwable var12) {
         addProblem(attrType, problems, var12);
      }

   }

   private static void setBeanProperty(InvocationContext ic, AttributeType attrType, Object newJsonValue, Object newJavaValue, Object oldJsonValue) throws Exception {
      boolean doSet = false;
      if (ic.expandedValues()) {
         doSet = true;
      } else {
         doSet = !jsonEquals(oldJsonValue, newJsonValue);
      }

      if (doSet) {
         setBeanProperty(ic, attrType, newJavaValue);
      }

   }

   private static void unsetBeanProperty(final InvocationContext ic, final AttributeType attrType) throws Exception {
      AtzUtils.checkUnSetAccess(ic, attrType);
      Object[] args = args(attrType.getPropertyDescriptor().getName());
      MethodDescriptor md = findSetMethodDescriptor(attrType, "unSet");

      try {
         PartitionUtils.runAs(ic, new Callable() {
            public Void call() throws Exception {
               ((SettableBean)((SettableBean)ic.bean())).unSet(attrType.getPropertyDescriptor().getName());
               return null;
            }
         });
         ConfigAuditUtils.auditInvoke(ic, (MethodDescriptor)md, args, (Exception)null);
      } catch (Exception var5) {
         ConfigAuditUtils.auditInvoke(ic, md, args, var5);
         throw var5;
      }
   }

   private static Object[] args(Object... args) {
      return args;
   }

   private static MethodDescriptor findSetMethodDescriptor(AttributeType attrType, String method) throws Exception {
      MethodDescriptor[] mds = attrType.getBeanType().getBeanInfo().getMethodDescriptors();

      for(int i = 0; mds != null && i < mds.length; ++i) {
         MethodDescriptor md = mds[i];
         if (method.equals(md.getMethod().getName())) {
            return md;
         }
      }

      throw new AssertionError("Cannot find method " + method + " for BeanInfo " + attrType.getBeanType().getName());
   }

   private static void setBeanProperty(InvocationContext ic, AttributeType attrType, Object newJavaValue) throws Exception {
      AtzUtils.checkSetAccess(ic, attrType, newJavaValue);
      Object oldJavaValue = getBeanProperty(ic, attrType, false);

      try {
         InvokeUtils.invoke(ic, attrType.getWriter(), newJavaValue);
         ConfigAuditUtils.auditModify(ic, attrType, oldJavaValue, newJavaValue, (Exception)null);
      } catch (Exception var5) {
         ConfigAuditUtils.auditModify(ic, attrType, oldJavaValue, newJavaValue, var5);
         throw var5;
      }
   }

   private static void addProblem(AttributeType attrType, PropertyExceptions problems, Throwable problem) {
      String field = attrType.getName();
      if (problem instanceof WebApplicationException) {
         Response resp = ((WebApplicationException)problem).getResponse();
         Object entity = resp.getEntity();
         Iterator message;
         if (entity instanceof ResponseBody) {
            message = ((ResponseBody)entity).getMessages().iterator();

            while(message.hasNext()) {
               Message message = (Message)message.next();
               if (Severity.FAILURE.equals(message.getSeverity())) {
                  problems.add(new PropertyException(field, message.getMessage(), problem));
               }
            }
         } else {
            message = null;
            String message;
            if (entity != null) {
               message = entity.toString();
            } else {
               message = Status.fromStatusCode(resp.getStatus()).toString();
            }

            problems.add(new PropertyException(field, message, problem));
         }
      } else {
         problems.add(new PropertyException(field, problem));
      }

   }

   private static boolean jsonEquals(Object v1, Object v2) throws Exception {
      if (v1 instanceof JSONObject) {
         if (!(v2 instanceof JSONObject)) {
            return false;
         } else {
            JSONObject o1 = (JSONObject)v1;
            JSONObject o2 = (JSONObject)v2;
            if (o1.length() != o2.length()) {
               return false;
            } else {
               Iterator i = o1.keys();

               String key;
               do {
                  if (!i.hasNext()) {
                     return true;
                  }

                  key = (String)i.next();
                  if (!o2.has(key)) {
                     return false;
                  }
               } while(jsonEquals(o1.get(key), o2.get(key)));

               return false;
            }
         }
      } else if (v1 instanceof JSONArray) {
         if (!(v2 instanceof JSONArray)) {
            return false;
         } else {
            JSONArray a1 = (JSONArray)v1;
            JSONArray a2 = (JSONArray)v2;
            if (a1.length() != a2.length()) {
               return false;
            } else {
               for(int i = 0; i < a1.length(); ++i) {
                  if (!jsonEquals(a1.get(i), a2.get(i))) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else if (v1 != null && v2 != null) {
         return v1.toString().equals(v2.toString());
      } else {
         return v1 == null && v2 == null;
      }
   }

   public static void deleteCollectionChildBean(InvocationContext ic, Object parentBean, ContainedBeansType type) throws Exception {
      InvocationContext parentIc = ic.clone(parentBean);
      AtzUtils.checkDeleteAccess(parentIc, type, ic.bean());
      removeBeanReferences(ic);

      try {
         InvokeUtils.invoke(parentIc, type.getDestroyer(ic.request()).getMethod(), ic.bean());
         ConfigAuditUtils.auditRemove(ic, (Exception)null);
      } catch (Exception var5) {
         ConfigAuditUtils.auditRemove(ic, var5);
         throw var5;
      }
   }

   public static void deleteSingletonChildBean(InvocationContext ic, Object parentBean, ContainedBeanType type) throws Exception {
      InvocationContext parentIc = ic.clone(parentBean);
      AtzUtils.checkDeleteAccess(parentIc, type, ic.bean());
      removeBeanReferences(ic);

      try {
         if (type.noArgDestroyer()) {
            InvokeUtils.invoke(parentIc, type.getDestroyer(ic.request()).getMethod());
         } else {
            InvokeUtils.invoke(parentIc, type.getDestroyer(ic.request()).getMethod(), ic.bean());
         }

         ConfigAuditUtils.auditRemove(ic, (Exception)null);
      } catch (Exception var5) {
         ConfigAuditUtils.auditRemove(ic, var5);
         throw var5;
      }
   }

   private static void removeBeanReferences(InvocationContext ic) throws Exception {
      if (ic.bean() instanceof AbstractDescriptorBean) {
         (new BeanTransactionHelper(ic)).getConfigurationManager().removeReferencesToBean((AbstractDescriptorBean)ic.bean());
      }

   }

   public static boolean isBeanClass(Class clazz) throws Exception {
      if (beanClasses.contains(clazz)) {
         return true;
      } else if (nonBeanClasses.contains(clazz)) {
         return false;
      } else {
         boolean isBeanClass = clazz == AbstractDescriptorBean.class;
         if (!isBeanClass && DescriptorUtils.isBeanType(clazz.getName()) && !DescriptorUtils.isValueObject(DescriptorUtils.getBeanTypeInfo(clazz.getName()))) {
            isBeanClass = true;
         }

         if (!isBeanClass) {
            Class[] var2 = clazz.getInterfaces();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Class intf = var2[var4];
               if (isBeanClass(intf)) {
                  isBeanClass = true;
                  break;
               }
            }
         }

         if (isBeanClass) {
            beanClasses.add(clazz);
         } else {
            nonBeanClasses.add(clazz);
         }

         return isBeanClass;
      }
   }

   public static boolean isVBeanClass(Class clazz) throws Exception {
      if (vbeanClasses.contains(clazz)) {
         return true;
      } else if (nonVBeanClasses.contains(clazz)) {
         return false;
      } else {
         boolean isVBeanClass = false;
         if (DescriptorUtils.isBeanType(clazz.getName()) && DescriptorUtils.isValueObject(DescriptorUtils.getBeanTypeInfo(clazz.getName()))) {
            isVBeanClass = true;
         }

         if (!isVBeanClass) {
            Class[] var2 = clazz.getInterfaces();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Class intf = var2[var4];
               if (isVBeanClass(intf)) {
                  isVBeanClass = true;
                  break;
               }
            }
         }

         if (isVBeanClass) {
            vbeanClasses.add(clazz);
         } else {
            nonVBeanClasses.add(clazz);
         }

         return isVBeanClass;
      }
   }

   public static JSONObject getCreateForm(InvocationContext ic, ContainedBeanAttributeType attrType) throws Exception {
      MethodType creator = attrType.getCreator(ic.request());
      return creator == null ? null : getCreateForm(ic, attrType.getType(ic.request()), creator);
   }

   private static JSONObject getCreateForm(InvocationContext ic, BeanType type, MethodType creator) throws Exception {
      RestJsonResponseBody rb = new RestJsonResponseBody(ic.request(), QueryUtils.getLinksFilter(ic.request(), ic.query()));
      JsonFilter.Scope filter = QueryUtils.getPropertiesFilter(ic.request(), ic.query()).newScope();
      JSONObject item = getDefaultValues(ic, rb, filter, type);
      Class[] pts = creator.getMethodDescriptor().getMethod().getParameterTypes();
      ParameterDescriptor[] pds = creator.getMethodDescriptor().getParameterDescriptors();
      if (pds != null) {
         for(int i = 0; i < pds.length; ++i) {
            String name = pds[i].getName();
            if (filter.include(name)) {
               Class clazz = pts[i];
               Marshaller marshaller = DefaultMarshallers.instance().getMarshaller(ic.request(), clazz);
               Object javaVal = marshaller.getDefaultValue();
               Object jsonVal = marshaller.marshal(ic, rb, name, javaVal);
               item.put(name, jsonVal);
            }
         }
      }

      rb.setEntity(item);
      return rb.toJson();
   }

   private static JSONObject getDefaultValues(InvocationContext ic, ResponseBody rb, JsonFilter.Scope filter, BeanType type) throws Exception {
      DomainMBean domain = TreeUtils.getEditBean(ic.request());
      boolean prodMode = domain.isProductionModeEnabled();
      boolean secureMode = domain.getSecurityConfiguration().getSecureMode().isSecureModeEnabled();
      JSONObject j = new JSONObject();
      Iterator var8 = type.getPropertyTypes().iterator();

      while(true) {
         PropertyType t;
         do {
            do {
               do {
                  do {
                     if (!var8.hasNext()) {
                        var8 = type.getReferencedBeanTypes().iterator();

                        while(var8.hasNext()) {
                           ReferencedBeanType t = (ReferencedBeanType)var8.next();
                           if (t.isWritable() && filter.include(t.getName()) && PartitionUtils.isVisible((InvocationContext)ic, t)) {
                              j.put(t.getName(), JSONObject.NULL);
                           }
                        }

                        var8 = type.getReferencedBeansTypes().iterator();

                        while(var8.hasNext()) {
                           ReferencedBeansType t = (ReferencedBeansType)var8.next();
                           if (t.isWritable() && filter.include(t.getName()) && PartitionUtils.isVisible((InvocationContext)ic, t)) {
                              j.put(t.getName(), new JSONArray());
                           }
                        }

                        return j;
                     }

                     t = (PropertyType)var8.next();
                  } while(!t.isWritable());
               } while(!filter.include(t.getName()));
            } while(!PartitionUtils.isVisible((InvocationContext)ic, t));
         } while(t.isDerivedDefault());

         Marshaller marshaller = t.getMarshaller().getValueMarshaller();
         Object javaVal = null;
         if (secureMode && t.hasSecureValue() && !t.isSecureValueDocOnly()) {
            javaVal = t.getSecureValue();
         } else if (prodMode && t.hasProductionModeDefaultValue()) {
            javaVal = t.getProductionModeDefaultValue();
         } else if (t.hasDefaultValue()) {
            javaVal = t.getDefaultValue();
         } else {
            javaVal = marshaller.getDefaultValue();
         }

         Object jsonVal = marshaller.marshal(ic, rb, t.getName(), javaVal);
         j.put(t.getName(), jsonVal);
      }
   }

   public static ContainedBeanAttributeType cacheParentAttributeType(Object bean, ContainedBeanAttributeType val) throws Exception {
      return (ContainedBeanAttributeType)cacheMetaData(bean, "RestParentAttr", val);
   }

   public static String getWebLogicMBeanType(String mbeanClassName) throws Exception {
      int lastDot = mbeanClassName.lastIndexOf(".");
      String mbeanLeafClassName = lastDot != -1 ? mbeanClassName.substring(lastDot + 1, mbeanClassName.length()) : mbeanClassName;
      String[] var3 = MBEAN_CLASS_NAME_SUFFIXES;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String suffix = var3[var5];
         if (mbeanLeafClassName.endsWith(suffix)) {
            return mbeanLeafClassName.substring(0, mbeanLeafClassName.length() - suffix.length());
         }
      }

      return mbeanLeafClassName;
   }

   public static ContainedBeanAttributeType getParentAttributeType(Object bean) throws Exception {
      return (ContainedBeanAttributeType)getMetaData(bean, "RestParentAttr");
   }

   private static Object cacheIdentity(Object bean, Object val) throws Exception {
      return cacheMetaData(bean, "RestIdentity", val);
   }

   private static Object getIdentity(Object bean) throws Exception {
      return getMetaData(bean, "RestIdentity");
   }

   private static Object getMetaData(Object bean, String key) throws Exception {
      if (bean == null) {
         return null;
      } else if (bean instanceof AbstractDescriptorBean) {
         return ((AbstractDescriptorBean)bean).getMetaData(key);
      } else if (bean instanceof RuntimeMBeanDelegate) {
         return ((RuntimeMBeanDelegate)bean).getMetaData(key);
      } else {
         invalidBeanType(bean);
         return null;
      }
   }

   private static Object cacheMetaData(Object bean, String key, Object val) throws Exception {
      if (bean == null) {
         return null;
      } else {
         Object cachedVal = getMetaData(bean, key);
         if (cachedVal != null) {
            return cachedVal;
         } else {
            if (bean instanceof AbstractDescriptorBean) {
               ((AbstractDescriptorBean)bean).setMetaData(key, val);
            } else if (bean instanceof RuntimeMBeanDelegate) {
               ((RuntimeMBeanDelegate)bean).setMetaData(key, val);
            } else {
               invalidBeanType(bean);
            }

            return getMetaData(bean, key);
         }
      }
   }

   public static void invalidBeanType(Object bean) throws Exception {
      throw new AssertionError("Invalid bean type bean=" + bean + " class=" + bean.getClass());
   }
}
