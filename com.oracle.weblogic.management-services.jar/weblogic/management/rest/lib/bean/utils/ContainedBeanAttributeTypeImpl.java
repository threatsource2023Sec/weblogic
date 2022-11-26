package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.utils.ExceptionUtil;

abstract class ContainedBeanAttributeTypeImpl extends BeanAttributeTypeImpl implements ContainedBeanAttributeType {
   private static final String BASE_CREATOR = "creator";
   private static final String DERIVED_CREATOR = "creator.";
   private Map creators = new HashMap();
   private MethodReference destroyer;

   protected ContainedBeanAttributeTypeImpl(BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
      this.initCreator();
   }

   private void initCreator() throws Exception {
      this.creators.put(this.getBaseWebLogicMBeanType(), this.newCreatorMethodReference("creator"));
      this.initDerivedCreators();
   }

   private void initDerivedCreators() throws Exception {
      Enumeration e = this.getPropertyDescriptor().attributeNames();

      while(e.hasMoreElements()) {
         String attr = (String)e.nextElement();
         if (attr.startsWith("creator.")) {
            String derivedLeafMbeanClassName = attr.substring("creator.".length(), attr.length());
            String derivedType = BeanUtils.getWebLogicMBeanType(derivedLeafMbeanClassName);
            MethodReference derivedCreator = this.newCreatorMethodReference(attr);
            this.creators.put(derivedType, derivedCreator);
         }
      }

   }

   private MethodReference newCreatorMethodReference(String creatorAttribute) throws Exception {
      return new MethodReference(this.getBeanType(), DescriptorUtils.getStringField(this.getPropertyDescriptor(), creatorAttribute), (Class[])null);
   }

   protected void initDestroyer(Class... signature) throws Exception {
      this.destroyer = new MethodReference(this.getBeanType(), DescriptorUtils.getStringField(this.getPropertyDescriptor(), "destroyer"), signature);
   }

   public MethodType getCreator(HttpServletRequest request) throws Exception {
      return this.getCreator(request, (String)null);
   }

   public MethodType getCreator(HttpServletRequest request, String type) throws Exception {
      if (type == null || this.creators.size() == 1) {
         type = this.getBaseWebLogicMBeanType();
      }

      MethodReference creator = (MethodReference)this.creators.get(type);
      if (creator == null) {
         String supportedTypes = this.creators.keySet().toString();
         ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgCreateNotSupportedForType(type, supportedTypes));
      }

      return creator.getMethodType(request);
   }

   public MethodType getDestroyer(HttpServletRequest request) throws Exception {
      return this.destroyer.getMethodType(request);
   }

   private String getBaseWebLogicMBeanType() throws Exception {
      return BeanUtils.getWebLogicMBeanType(this.getTypeName());
   }
}
