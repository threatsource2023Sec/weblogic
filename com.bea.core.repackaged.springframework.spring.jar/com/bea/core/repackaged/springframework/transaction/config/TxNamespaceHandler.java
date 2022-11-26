package com.bea.core.repackaged.springframework.transaction.config;

import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.w3c.dom.Element;

public class TxNamespaceHandler extends NamespaceHandlerSupport {
   static final String TRANSACTION_MANAGER_ATTRIBUTE = "transaction-manager";
   static final String DEFAULT_TRANSACTION_MANAGER_BEAN_NAME = "transactionManager";

   static String getTransactionManagerName(Element element) {
      return element.hasAttribute("transaction-manager") ? element.getAttribute("transaction-manager") : "transactionManager";
   }

   public void init() {
      this.registerBeanDefinitionParser("advice", new TxAdviceBeanDefinitionParser());
      this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
      this.registerBeanDefinitionParser("jta-transaction-manager", new JtaTransactionManagerBeanDefinitionParser());
   }
}
