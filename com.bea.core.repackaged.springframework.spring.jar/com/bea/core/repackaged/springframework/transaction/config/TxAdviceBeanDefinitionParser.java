package com.bea.core.repackaged.springframework.transaction.config;

import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedMap;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import com.bea.core.repackaged.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.RollbackRuleAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionInterceptor;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.xml.DomUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;

class TxAdviceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   private static final String METHOD_ELEMENT = "method";
   private static final String METHOD_NAME_ATTRIBUTE = "name";
   private static final String ATTRIBUTES_ELEMENT = "attributes";
   private static final String TIMEOUT_ATTRIBUTE = "timeout";
   private static final String READ_ONLY_ATTRIBUTE = "read-only";
   private static final String PROPAGATION_ATTRIBUTE = "propagation";
   private static final String ISOLATION_ATTRIBUTE = "isolation";
   private static final String ROLLBACK_FOR_ATTRIBUTE = "rollback-for";
   private static final String NO_ROLLBACK_FOR_ATTRIBUTE = "no-rollback-for";

   protected Class getBeanClass(Element element) {
      return TransactionInterceptor.class;
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      builder.addPropertyReference("transactionManager", TxNamespaceHandler.getTransactionManagerName(element));
      List txAttributes = DomUtils.getChildElementsByTagName(element, "attributes");
      if (txAttributes.size() > 1) {
         parserContext.getReaderContext().error("Element <attributes> is allowed at most once inside element <advice>", element);
      } else if (txAttributes.size() == 1) {
         Element attributeSourceElement = (Element)txAttributes.get(0);
         RootBeanDefinition attributeSourceDefinition = this.parseAttributeSource(attributeSourceElement, parserContext);
         builder.addPropertyValue("transactionAttributeSource", attributeSourceDefinition);
      } else {
         builder.addPropertyValue("transactionAttributeSource", new RootBeanDefinition("com.bea.core.repackaged.springframework.transaction.annotation.AnnotationTransactionAttributeSource"));
      }

   }

   private RootBeanDefinition parseAttributeSource(Element attrEle, ParserContext parserContext) {
      List methods = DomUtils.getChildElementsByTagName(attrEle, "method");
      ManagedMap transactionAttributeMap = new ManagedMap(methods.size());
      transactionAttributeMap.setSource(parserContext.extractSource(attrEle));
      Iterator var5 = methods.iterator();

      while(var5.hasNext()) {
         Element methodEle = (Element)var5.next();
         String name = methodEle.getAttribute("name");
         TypedStringValue nameHolder = new TypedStringValue(name);
         nameHolder.setSource(parserContext.extractSource(methodEle));
         RuleBasedTransactionAttribute attribute = new RuleBasedTransactionAttribute();
         String propagation = methodEle.getAttribute("propagation");
         String isolation = methodEle.getAttribute("isolation");
         String timeout = methodEle.getAttribute("timeout");
         String readOnly = methodEle.getAttribute("read-only");
         if (StringUtils.hasText(propagation)) {
            attribute.setPropagationBehaviorName("PROPAGATION_" + propagation);
         }

         if (StringUtils.hasText(isolation)) {
            attribute.setIsolationLevelName("ISOLATION_" + isolation);
         }

         if (StringUtils.hasText(timeout)) {
            try {
               attribute.setTimeout(Integer.parseInt(timeout));
            } catch (NumberFormatException var16) {
               parserContext.getReaderContext().error("Timeout must be an integer value: [" + timeout + "]", methodEle);
            }
         }

         if (StringUtils.hasText(readOnly)) {
            attribute.setReadOnly(Boolean.valueOf(methodEle.getAttribute("read-only")));
         }

         List rollbackRules = new LinkedList();
         String noRollbackForValue;
         if (methodEle.hasAttribute("rollback-for")) {
            noRollbackForValue = methodEle.getAttribute("rollback-for");
            this.addRollbackRuleAttributesTo(rollbackRules, noRollbackForValue);
         }

         if (methodEle.hasAttribute("no-rollback-for")) {
            noRollbackForValue = methodEle.getAttribute("no-rollback-for");
            this.addNoRollbackRuleAttributesTo(rollbackRules, noRollbackForValue);
         }

         attribute.setRollbackRules(rollbackRules);
         transactionAttributeMap.put(nameHolder, attribute);
      }

      RootBeanDefinition attributeSourceDefinition = new RootBeanDefinition(NameMatchTransactionAttributeSource.class);
      attributeSourceDefinition.setSource(parserContext.extractSource(attrEle));
      attributeSourceDefinition.getPropertyValues().add("nameMap", transactionAttributeMap);
      return attributeSourceDefinition;
   }

   private void addRollbackRuleAttributesTo(List rollbackRules, String rollbackForValue) {
      String[] exceptionTypeNames = StringUtils.commaDelimitedListToStringArray(rollbackForValue);
      String[] var4 = exceptionTypeNames;
      int var5 = exceptionTypeNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String typeName = var4[var6];
         rollbackRules.add(new RollbackRuleAttribute(StringUtils.trimWhitespace(typeName)));
      }

   }

   private void addNoRollbackRuleAttributesTo(List rollbackRules, String noRollbackForValue) {
      String[] exceptionTypeNames = StringUtils.commaDelimitedListToStringArray(noRollbackForValue);
      String[] var4 = exceptionTypeNames;
      int var5 = exceptionTypeNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String typeName = var4[var6];
         rollbackRules.add(new NoRollbackRuleAttribute(StringUtils.trimWhitespace(typeName)));
      }

   }
}
