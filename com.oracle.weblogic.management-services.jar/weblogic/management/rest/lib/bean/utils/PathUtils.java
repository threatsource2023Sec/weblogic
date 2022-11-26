package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PathUtils {
   private static Map alternateBeanTrees = new HashMap();

   public static Object findBean(InvocationContext ic, String beanTree, String... segments) throws Exception {
      return findBean(ic, beanTree, StringUtil.toJson(segments));
   }

   public static Object findBean(InvocationContext ic, String beanTree, JSONArray path) throws Exception {
      return findBean(ic.clone(TreeUtils.getRootBean(ic.request(), beanTree)), path);
   }

   public static Object findBean(InvocationContext ic, JSONArray path) throws Exception {
      Object bean = findBean(ic.clone(getRoot(ic)), path, 0, new ArrayList());
      if (bean == null) {
         String primaryTree = TreeUtils.getBeanTree(ic);
         String alternateTree = (String)alternateBeanTrees.get(primaryTree);
         if (alternateTree != null) {
            bean = findBean(ic.clone(TreeUtils.getRootBean(ic.request(), alternateTree)), path, 0, new ArrayList());
         }
      }

      return bean;
   }

   private static Object findBean(InvocationContext ic, JSONArray path, int index, List identity) throws Exception {
      if (index == path.length()) {
         return ic.bean();
      } else {
         String property = path.getString(index++);
         identity.add(property);
         BeanType type = BeanType.getBeanType(ic.request(), ic.bean());
         ContainedBeanType t = type.getContainedBeanType(property);
         if (t != null && t.isVisibleToRequest(ic)) {
            Object childBean = BeanUtils.getBeanProperty(ic, (AttributeType)t);
            return findBean(ic.clone(childBean), path, index, identity);
         } else {
            ContainedBeansType t = type.getContainedBeansType(property);
            if (t != null && t.isVisibleToRequest(ic)) {
               String childIdentity = path.getString(index++);
               identity.add(childIdentity);
               Object childBean = findChild(ic, childIdentity, t);
               return findBean(ic.clone(childBean), path, index, identity);
            } else {
               return null;
            }
         }
      }
   }

   public static Object getJsonBeanPath(InvocationContext ic) throws Exception {
      if (ic.bean() == null) {
         return JSONObject.NULL;
      } else {
         JSONArray val = new JSONArray();
         Iterator var2 = getBeanPath(ic).iterator();

         while(var2.hasNext()) {
            String segment = (String)var2.next();
            val.put(segment);
         }

         return val;
      }
   }

   public static List getBeanPath(InvocationContext ic) throws Exception {
      if (ic.bean() == null) {
         return null;
      } else {
         List path = new ArrayList();

         while(true) {
            Object child = ic.bean();
            Object parent = getParent(child);
            if (parent == null) {
               return path;
            }

            ContainedBeanAttributeType parentAttrType = getParentAttributeType(ic, parent);
            if (parentAttrType instanceof ContainedBeansType) {
               path.add(0, BeanUtils.getIdentity(ic).toString());
            }

            path.add(0, parentAttrType.getName());
            ic = ic.clone(parent);
         }
      }
   }

   private static ContainedBeanAttributeType getParentAttributeType(InvocationContext ic, Object parent) throws Exception {
      Object child = ic.bean();
      ContainedBeanAttributeType t = BeanUtils.getParentAttributeType(child);
      if (t != null) {
         return t;
      } else {
         BeanType childType = BeanType.getBeanType(ic.request(), child);
         BeanType parentType = BeanType.getBeanType(ic.request(), parent);
         if (child instanceof AbstractDescriptorBean) {
            t = computeADBParentAttributeType(ic, parentType, (AbstractDescriptorBean)parent, (AbstractDescriptorBean)child);
         } else if (child instanceof RuntimeMBeanDelegate) {
            t = computeRMDParentAttributeType(ic, parentType, (RuntimeMBeanDelegate)child);
         } else {
            BeanUtils.invalidBeanType(child);
         }

         if (t == null) {
            t = computeBeanTypeParentAttributeType(ic, parentType, parent, childType);
         }

         if (t == null) {
            throw new AssertionError("Couldn't find parent attr " + childType.getName() + " " + parentType.getName());
         } else {
            return BeanUtils.cacheParentAttributeType(child, t);
         }
      }
   }

   private static ContainedBeanAttributeType computeADBParentAttributeType(InvocationContext ic, BeanType parentType, AbstractDescriptorBean parent, AbstractDescriptorBean child) throws Exception {
      Iterator var4 = parentType.getContainedBeansTypes().iterator();

      String mbeanPropertyName;
      int propertyIndex;
      while(var4.hasNext()) {
         ContainedBeansType attrType = (ContainedBeansType)var4.next();
         if (attrType.isVisibleToRequest(ic)) {
            mbeanPropertyName = attrType.getPropertyDescriptor().getName();
            propertyIndex = parent._getPropertyIndex(mbeanPropertyName);
            if (child.isChildProperty(parent, propertyIndex)) {
               return attrType;
            }
         }
      }

      var4 = parentType.getContainedBeanTypes().iterator();

      while(var4.hasNext()) {
         ContainedBeanType attrType = (ContainedBeanType)var4.next();
         if (attrType.isVisibleToRequest(ic)) {
            mbeanPropertyName = attrType.getPropertyDescriptor().getName();
            propertyIndex = parent._getPropertyIndex(mbeanPropertyName);
            if (child.isChildProperty(parent, propertyIndex)) {
               return attrType;
            }
         }
      }

      return null;
   }

   private static ContainedBeanAttributeType computeRMDParentAttributeType(InvocationContext ic, BeanType parentType, RuntimeMBeanDelegate child) throws Exception {
      String parentAttr;
      try {
         parentAttr = child.getParentAttribute();
      } catch (Throwable var6) {
         return null;
      }

      Iterator var4 = parentType.getContainedBeansTypes().iterator();

      ContainedBeansType attrType;
      do {
         if (!var4.hasNext()) {
            var4 = parentType.getContainedBeanTypes().iterator();

            ContainedBeanType attrType;
            do {
               if (!var4.hasNext()) {
                  return null;
               }

               attrType = (ContainedBeanType)var4.next();
            } while(!attrType.isVisibleToRequest(ic) || !parentAttr.equals(attrType.getPropertyDescriptor().getName()));

            return attrType;
         }

         attrType = (ContainedBeansType)var4.next();
      } while(!attrType.isVisibleToRequest(ic) || !parentAttr.equals(attrType.getPropertyDescriptor().getName()));

      return attrType;
   }

   private static ContainedBeanAttributeType computeBeanTypeParentAttributeType(InvocationContext ic, BeanType parentType, Object parent, BeanType childType) throws Exception {
      String childTypeName = childType.getName();
      InvocationContext parentIc = ic.clone(parent);
      Iterator var6 = parentType.getContainedBeansTypes().iterator();

      ContainedBeansType attrType;
      do {
         do {
            do {
               if (!var6.hasNext()) {
                  var6 = parentType.getContainedBeanTypes().iterator();

                  ContainedBeanType attrType;
                  do {
                     do {
                        do {
                           if (!var6.hasNext()) {
                              return null;
                           }

                           attrType = (ContainedBeanType)var6.next();
                        } while(!attrType.isVisibleToRequest(ic));
                     } while(!childTypeName.equals(attrType.getType(ic.request()).getName()) && !attrType.getType(ic.request()).containsSubType(childTypeName));

                     BeanUtils.getBeanProperty(parentIc, (AttributeType)attrType);
                  } while(BeanUtils.getParentAttributeType(ic.bean()) == null);

                  return attrType;
               }

               attrType = (ContainedBeansType)var6.next();
            } while(!attrType.isVisibleToRequest(ic));
         } while(!childTypeName.equals(attrType.getType(ic.request()).getName()) && !attrType.getType(ic.request()).containsSubType(childTypeName));

         findChild(parentIc, BeanUtils.getIdentity(ic).toString(), attrType);
      } while(BeanUtils.getParentAttributeType(ic.bean()) == null);

      return attrType;
   }

   public static Object findChild(InvocationContext ic, String identity, ContainedBeansType attrType) throws Exception {
      MethodType finder = attrType.getFinder(ic.request());
      if (finder != null) {
         AtzUtils.checkFindAccess(ic, attrType, identity);
         Object child = InvokeUtils.invoke(ic, finder.getMethod(), identity);
         if (BeanUtils.isVisible(child)) {
            BeanUtils.cacheParentAttributeType(child, attrType);
            return child;
         } else {
            return null;
         }
      } else {
         Object[] var4 = (Object[])((Object[])BeanUtils.getBeanProperty(ic, (AttributeType)attrType));
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Object child = var4[var6];
            if (identity.equals(BeanUtils.getIdentity(ic.clone(child)).toString())) {
               return child;
            }
         }

         return null;
      }
   }

   public static Object getRoot(InvocationContext ic) throws Exception {
      Object bean = ic.bean();
      if (bean instanceof ConfigurationManagerMBean) {
         return TreeUtils.getEditBean(ic.request());
      } else {
         while(true) {
            Object parent = getParent(bean);
            if (parent == null) {
               return bean;
            }

            bean = parent;
         }
      }
   }

   public static Object getParent(InvocationContext ic) throws Exception {
      return getParent(ic.bean());
   }

   private static Object getParent(Object bean) throws Exception {
      if (bean instanceof AbstractDescriptorBean) {
         Object parent = ((AbstractDescriptorBean)((AbstractDescriptorBean)bean)).getParentBean();
         if (parent == null) {
            AbstractDescriptorBean adb = (AbstractDescriptorBean)bean;
            DescriptorImpl descriptor = (DescriptorImpl)adb.getDescriptor();
            return descriptor.getContext().get("DescriptorConfigExtension");
         } else {
            return parent;
         }
      } else if (bean instanceof RuntimeMBeanDelegate) {
         return ((RuntimeMBeanDelegate)bean).getRestParent();
      } else if (bean instanceof ConfigurationManagerMBean) {
         return null;
      } else if (bean instanceof ActivationTaskMBean) {
         return null;
      } else {
         BeanUtils.invalidBeanType(bean);
         return null;
      }
   }

   public static Object getReferencedBeanJson(InvocationContext ic) throws Exception {
      if (ic.bean() == null) {
         return JSONObject.NULL;
      } else {
         List path = getBeanPath(ic);
         JSONArray id = new JSONArray();
         Iterator var3 = path.iterator();

         while(var3.hasNext()) {
            String segment = (String)var3.next();
            id.put(segment);
         }

         return id;
      }
   }

   public static URI getParentUri(InvocationContext ic) throws Exception {
      List path = getBeanPath(ic);
      if (path == null) {
         return null;
      } else if (path.size() > 0) {
         List path = new ArrayList(path);
         path.remove(path.size() - 1);
         return getTreeRelativeUri(ic, path);
      } else {
         return getVersionRelativeBuilder(ic).build(new Object[0]);
      }
   }

   public static URI getUri(InvocationContext ic) throws Exception {
      return ic.bean() == null ? null : getTreeRelativeUri(ic, getBeanPath(ic));
   }

   public static URI getSubUri(InvocationContext ic, String... segs) throws Exception {
      return getTreeRelativeUri(ic, getBeanPath(ic), segs);
   }

   public static URI getTreeRelativeUri(InvocationContext ic, List segments, String... segs) throws Exception {
      return getTreeRelativeUri(ic, TreeUtils.getBeanTree(ic), segments, segs);
   }

   public static URI getTreeRelativeUri(InvocationContext ic, String tree, List segments, String... segs) throws Exception {
      if (segments == null && segs == null) {
         return null;
      } else {
         UriBuilder bldr = getVersionRelativeBuilder(ic).segment(new String[]{tree});
         if (segments != null) {
            Iterator var5 = segments.iterator();

            while(var5.hasNext()) {
               String segment = (String)var5.next();
               bldr.segment(new String[]{segment});
            }
         }

         if (segs != null) {
            String[] var9 = segs;
            int var10 = segs.length;

            for(int var7 = 0; var7 < var10; ++var7) {
               String seg = var9[var7];
               bldr.segment(new String[]{seg});
            }
         }

         return bldr.build(new Object[0]);
      }
   }

   public static URI getVersionRelativeUri(InvocationContext ic, String... segs) throws Exception {
      UriBuilder bldr = getVersionRelativeBuilder(ic);
      if (segs != null) {
         String[] var3 = segs;
         int var4 = segs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String seg = var3[var5];
            bldr.segment(new String[]{seg});
         }
      }

      return bldr.build(new Object[0]);
   }

   private static UriBuilder getVersionRelativeBuilder(InvocationContext ic) throws Exception {
      String weblogic = ((PathSegment)ic.uriInfo().getPathSegments().get(0)).getPath();
      String version = ((PathSegment)ic.uriInfo().getPathSegments().get(1)).getPath();
      return ic.uriInfo().getBaseUriBuilder().segment(new String[]{weblogic}).segment(new String[]{version});
   }

   static {
      alternateBeanTrees.put("domainRuntime", "domainConfig");
      alternateBeanTrees.put("domainConfig", "domainRuntime");
      alternateBeanTrees.put("serverRuntime", "serverConfig");
      alternateBeanTrees.put("serverConfig", "serverRuntime");
   }
}
