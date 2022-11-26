package weblogic.management.rest.lib.bean.utils;

import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

class ActionTypeImpl extends MemberTypeImpl implements ActionType {
   private boolean isTaskRuntimeMBean;
   private List mts = new ArrayList();
   private boolean ignore = false;

   ActionTypeImpl(HttpServletRequest request, BeanType beanType, MethodDescriptor md, String restName) throws Exception {
      super(beanType, restName);
      this.isTaskRuntimeMBean = TaskRuntimeMBeanUtils.isTaskRuntimeMBean(md.getMethod().getReturnType());
      this.addMethodDescriptor(request, md);
   }

   void addMethodDescriptor(HttpServletRequest request, MethodDescriptor md) throws Exception {
      if (this.hasProblem(request, md)) {
         this.ignore = true;
      }

      this.mts.add(new MethodTypeImpl(this.getBeanType(), md));
   }

   private boolean hasProblem(HttpServletRequest request, MethodDescriptor md) throws Exception {
      boolean p1 = this.ambiguous(request, md);
      boolean p2 = this.overloadedWithDifferentReturnType(request, md);
      boolean p3 = this.usesNonPublicBeanType(md);
      return p1 | p2 | p3;
   }

   private boolean ambiguous(HttpServletRequest request, MethodDescriptor md) throws Exception {
      if (this.mts.size() > 0) {
         String signature = InvokeUtils.formatParams(request, md);
         Iterator var4 = this.mts.iterator();

         while(var4.hasNext()) {
            MethodType mt = (MethodType)var4.next();
            if (signature.equals(InvokeUtils.formatParams(request, mt.getMethodDescriptor()))) {
               this.getBeanType().addMethodProblem(md, "ambiguous signature");
               return true;
            }
         }
      }

      return false;
   }

   private boolean overloadedWithDifferentReturnType(HttpServletRequest request, MethodDescriptor md) throws Exception {
      if (this.mts.size() > 0) {
         String name = DescriptorUtils.getRestName((FeatureDescriptor)md);
         String rtn = md.getMethod().getReturnType().toString();
         Iterator var5 = this.mts.iterator();

         while(var5.hasNext()) {
            MethodType mt = (MethodType)var5.next();
            MethodDescriptor md2 = mt.getMethodDescriptor();
            if (DescriptorUtils.getRestName((FeatureDescriptor)md2).equals(name) && !md2.getMethod().getReturnType().toString().equals(rtn)) {
               this.getBeanType().addMethodProblem(md, "overloaded method with different return type");
               return true;
            }
         }
      }

      return false;
   }

   private boolean usesNonPublicBeanType(MethodDescriptor md) throws Exception {
      Method m = md.getMethod();
      boolean invalid = false;
      if (this.usesNonPublicBeanType(md, m.getReturnType())) {
         invalid = true;
      }

      Class[] pts = m.getParameterTypes();

      for(int i = 0; pts != null && i < pts.length; ++i) {
         if (this.usesNonPublicBeanType(md, pts[i])) {
            invalid = true;
         }
      }

      return invalid;
   }

   private boolean usesNonPublicBeanType(MethodDescriptor md, Class type) throws Exception {
      if (!this.isVisibleToLatestVersion()) {
         return false;
      } else if (DescriptorUtils.isNonPublicBeanTypeInCurrentVersion(type)) {
         this.getBeanType().addMethodProblem(md, "uses non-public (excluded, deprecated or obsolete) bean type: " + type.getName());
         return true;
      } else {
         return false;
      }
   }

   public List getMethodTypes() {
      return this.mts;
   }

   public ResourceDef getResourceDef(String beanTree) throws Exception {
      ResourceDefs defs = this.isTaskRuntimeMBean ? BeanResourceRegistry.instance().taskRuntimeActionResources().get(this.getBeanType().getName(), this.getName()) : BeanResourceRegistry.instance().actionResources().get(this.getBeanType().getName(), this.getName());
      return defs.get(beanTree);
   }

   public Boolean getVisibleToPartitions() {
      if (!this.isVisibleToPartitionsSet()) {
         this.setVisibleToPartitions(this.computeVisibleToPartitions());
      }

      return super.getVisibleToPartitions();
   }

   public boolean isVisibleToVersion(int versionNumber) {
      Iterator var2 = this.getMethodTypes().iterator();

      MethodType mt;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         mt = (MethodType)var2.next();
      } while(!mt.isVisibleToVersion(versionNumber));

      return true;
   }

   public boolean isInternal() {
      Iterator var1 = this.getMethodTypes().iterator();

      MethodType mt;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         mt = (MethodType)var1.next();
      } while(mt.isInternal());

      return false;
   }

   private Boolean computeVisibleToPartitions() {
      boolean haveDefaultVisibilityMethod = false;
      boolean haveExplicitlyNotVisibleMethod = false;
      Iterator var3 = this.getMethodTypes().iterator();

      while(var3.hasNext()) {
         MethodType mt = (MethodType)var3.next();
         Boolean v2p = mt.getVisibleToPartitions();
         if (v2p != null) {
            if (v2p) {
               return v2p;
            }

            haveExplicitlyNotVisibleMethod = true;
         } else {
            haveDefaultVisibilityMethod = true;
         }
      }

      if (haveDefaultVisibilityMethod) {
         return null;
      } else if (haveExplicitlyNotVisibleMethod) {
         return Boolean.FALSE;
      } else {
         throw new AssertionError("Action has no methods : type=" + this.getBeanType().getName() + " action=" + this.getName());
      }
   }

   boolean ignore() {
      return this.ignore;
   }
}
