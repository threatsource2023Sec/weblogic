package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.ServletBean;

public class InitParamMerger extends AbstractMerger {
   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      return "InitParams".equals(update.getPropertyName()) && (bean instanceof ServletBean || bean instanceof FilterBean);
   }

   protected void handleAddEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      ParamValueBean param = (ParamValueBean)update.getAddedObject();
      String paramName = param.getParamName();
      ParamValueBean[] paramsSource = getInitParams(sourceBean);
      if (!isParamExist(paramName, paramsSource) && isParamExist(paramName, getInitParams(targetBean)) && equal(param, getFirstParamByName(paramName, getInitParams(proposedBean)))) {
         throw new MergeException("Conflict found while merging web fragment, " + param);
      } else {
         String property = update.getPropertyName();
         property = singularizeProperty(property);
         addChildBean(targetBean, property, param);
      }
   }

   private static boolean isParamExist(String paramName, ParamValueBean[] params) {
      return getFirstParamByName(paramName, params) != null;
   }

   private static ParamValueBean getFirstParamByName(String paramName, ParamValueBean[] params) {
      ParamValueBean[] var2 = params;
      int var3 = params.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ParamValueBean param = var2[var4];
         if (paramName.equals(param.getParamName())) {
            return param;
         }
      }

      return null;
   }

   private static boolean equal(ParamValueBean param1, ParamValueBean param2) {
      if (param1 == null && param2 == null) {
         return true;
      } else if (param1 != null && param2 != null) {
         String name1 = param1.getParamName();
         String value1 = param1.getParamValue();
         String name2 = param2.getParamName();
         String value2 = param2.getParamValue();
         return name1.equals(name2) && (value1 == null && value2 == null || value1 != null && value1.equals(value2));
      } else {
         return false;
      }
   }

   private static ParamValueBean[] getInitParams(DescriptorBean bean) {
      if (bean instanceof ServletBean) {
         ServletBean sb = (ServletBean)bean;
         return sb.getInitParams();
      } else if (bean instanceof FilterBean) {
         FilterBean fb = (FilterBean)bean;
         return fb.getInitParams();
      } else {
         throw new AssertionError("must be a ServletBean or a FilterBean");
      }
   }
}
