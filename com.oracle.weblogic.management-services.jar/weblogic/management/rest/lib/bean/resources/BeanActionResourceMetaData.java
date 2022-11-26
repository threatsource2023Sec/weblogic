package weblogic.management.rest.lib.bean.resources;

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.glassfish.admin.rest.model.MethodInfo;
import org.glassfish.admin.rest.model.ObjectTypeInfo;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.model.VoidTypeInfo;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.ActionType;
import weblogic.management.rest.lib.bean.utils.BeanType;
import weblogic.management.rest.lib.bean.utils.DefaultMarshallers;
import weblogic.management.rest.lib.bean.utils.DescriptionUtils;
import weblogic.management.rest.lib.bean.utils.InvokeUtils;
import weblogic.management.rest.lib.bean.utils.MetaDataUtils;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.runtime.TaskRuntimeMBean;

public class BeanActionResourceMetaData extends ActionResourceMetaData {
   private ActionType type;

   public void init(ResourceMetaData parent, ActionType type) throws Exception {
      this.type = type;
      this.init(parent);
   }

   public BeanType beanType() throws Exception {
      return this.type().getBeanType();
   }

   public ActionType type() throws Exception {
      return this.type;
   }

   public String actionName() throws Exception {
      return this.type().getName();
   }

   public String entityClassName() throws Exception {
      return this.beanType().getName();
   }

   public String className() throws Exception {
      return this.entityClassName() + "." + this.actionName();
   }

   protected void addMethods() throws Exception {
      Iterator var1 = this.type().getMethodTypes().iterator();

      while(var1.hasNext()) {
         MethodType mt = (MethodType)var1.next();
         this.addMethod(mt);
      }

   }

   protected MethodInfo addMethod(MethodType mt) throws Exception {
      return this.addMethod(mt, TaskRuntimeMBean.class.isAssignableFrom(mt.getMethodDescriptor().getMethod().getReturnType()));
   }

   protected MethodInfo addMethod(MethodType mt, boolean async) throws Exception {
      this.createMethodEntities(mt);
      String descArgs = this.type().getMethodTypes().size() > 1 ? this.argList(mt, false) : "";
      String titleArgs = this.type().getMethodTypes().size() > 1 ? this.argList(mt, true) : "";
      MethodInfo m = this.POSTMethodInfo(DescriptionUtils.description(this.request(), this.actionScope(mt) + "/summary", this.defaultPOSTSummary(), false, MetaDataUtils.resourceKeys(this.resource()), StringUtil.lowerCaseWordsToUpperCaseWords(MetaDataUtils.entityDisplayName(mt.getBeanType().getName())), this.formatCamelCase(mt.getName(), true), titleArgs), DescriptionUtils.description(this.request(), this.actionScope(mt), mt.getDescription(), true, MetaDataUtils.resourceKeys(this.resource()), MetaDataUtils.entityDisplayName(mt.getBeanType().getName()), mt.getName(), descArgs), this.POSTReqType(mt), this.POSTRespType(mt), this.POSTReqDesc(mt), this.POSTRespDesc(mt), this.actionScope(mt), async);
      m.setRoles(MetaDataUtils.getRolesAllowed(mt));
      return m;
   }

   protected String defaultExamplePOSTBaseKey() throws Exception {
      return "actionExamplePOST";
   }

   private TypeInfo POSTReqType(MethodType mt) throws Exception {
      MethodDescriptor md = mt.getMethodDescriptor();
      ParameterDescriptor[] parameterDescriptors = md.getParameterDescriptors();
      Class[] parameterTypes = md.getMethod().getParameterTypes();
      if (parameterDescriptors == null) {
         return VoidTypeInfo.instance();
      } else {
         ObjectTypeInfo t = new ObjectTypeInfo(DescriptionUtils.description(this.request(), "actionArgumentsTitle"));

         for(int i = 0; i < parameterDescriptors.length; ++i) {
            ParameterDescriptor pd = parameterDescriptors[i];
            String propName = pd.getName();
            TypeInfo propType = DefaultMarshallers.instance().getMarshaller(this.request(), parameterTypes[i]).getDocType(this.request(), this.api());
            String propDesc = DescriptionUtils.description(this.request(), this.actionScope(mt) + "/req" + "/" + propName, pd.getShortDescription(), true, MetaDataUtils.resourceKeys(this.resource()), mt.getBeanType().getName(), mt.getName(), propName);
            t.createProperty(propName, propType, propDesc);
         }

         return t;
      }
   }

   private TypeInfo POSTRespType(MethodType mt) throws Exception {
      Class rt = mt.getMethodDescriptor().getMethod().getReturnType();
      TypeInfo ot = InvokeUtils.isVoid(rt) ? VoidTypeInfo.instance() : DefaultMarshallers.instance().getMarshaller(this.request(), rt).getDocType(this.request(), this.api());
      return MetaDataUtils.actionRespType(this.request(), this.resource(), this.actionScope(mt), (TypeInfo)ot, this.entityClassName(), this.actionName());
   }

   private String POSTReqDesc(MethodType mt) throws Exception {
      return this.description(this.actionScope(mt) + "/req", this.defaultPOSTReqDesc(), new Object[]{mt.getBeanType().getName(), mt.getName()});
   }

   private String POSTRespDesc(MethodType mt) throws Exception {
      return this.description(this.actionScope(mt) + "/resp", this.defaultPOSTRespDesc(), new Object[]{mt.getBeanType().getName(), mt.getName()});
   }

   private void createMethodEntities(MethodType mt) throws Exception {
      Method m = mt.getMethodDescriptor().getMethod();
      Class rt = m.getReturnType();
      if (!InvokeUtils.isVoid(rt)) {
         this.createMethodEntities(rt);
      }

      Class[] parameterTypes = m.getParameterTypes();
      if (parameterTypes != null) {
         Class[] var5 = parameterTypes;
         int var6 = parameterTypes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class pt = var5[var7];
            this.createMethodEntities(pt);
         }
      }

   }

   private void createMethodEntities(Class type) throws Exception {
      DefaultMarshallers.instance().getMarshaller(this.request(), type).getDocType(this.request(), this.api());
   }

   private String actionScope(MethodType mt) throws Exception {
      StringBuilder sb = new StringBuilder();
      sb.append("/POST");
      ParameterDescriptor[] pds = mt.getMethodDescriptor().getParameterDescriptors();

      for(int i = 0; pds != null && i < pds.length; ++i) {
         sb.append("-" + pds[i].getName());
      }

      return sb.toString();
   }

   private String argList(MethodType mt, boolean asTitle) throws Exception {
      StringBuilder sb = new StringBuilder();
      if (asTitle) {
         sb.append(" ");
      }

      sb.append("(");
      ParameterDescriptor[] pds = mt.getMethodDescriptor().getParameterDescriptors();
      boolean first = true;
      long argCount = pds != null ? (long)pds.length : 0L;

      for(int i = 0; (long)i < argCount; ++i) {
         if (first) {
            if (asTitle) {
               sb.append(" ");
            }

            first = false;
         } else {
            sb.append(", ");
         }

         sb.append(this.formatCamelCase(pds[i].getName(), asTitle));
      }

      if (asTitle && argCount > 0L) {
         sb.append(" ");
      }

      sb.append(")");
      return sb.toString();
   }

   private String formatCamelCase(String camelCase, boolean asTitle) throws Exception {
      return asTitle ? StringUtil.camelCaseToUpperCaseWords(camelCase) : camelCase;
   }
}
