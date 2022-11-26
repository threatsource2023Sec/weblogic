package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAfterAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAfterReturningAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAroundAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.aspectj.DeclareParentsAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanReference;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanNameReference;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ParseState;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.xml.DomUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class ConfigBeanDefinitionParser implements BeanDefinitionParser {
   private static final String ASPECT = "aspect";
   private static final String EXPRESSION = "expression";
   private static final String ID = "id";
   private static final String POINTCUT = "pointcut";
   private static final String ADVICE_BEAN_NAME = "adviceBeanName";
   private static final String ADVISOR = "advisor";
   private static final String ADVICE_REF = "advice-ref";
   private static final String POINTCUT_REF = "pointcut-ref";
   private static final String REF = "ref";
   private static final String BEFORE = "before";
   private static final String DECLARE_PARENTS = "declare-parents";
   private static final String TYPE_PATTERN = "types-matching";
   private static final String DEFAULT_IMPL = "default-impl";
   private static final String DELEGATE_REF = "delegate-ref";
   private static final String IMPLEMENT_INTERFACE = "implement-interface";
   private static final String AFTER = "after";
   private static final String AFTER_RETURNING_ELEMENT = "after-returning";
   private static final String AFTER_THROWING_ELEMENT = "after-throwing";
   private static final String AROUND = "around";
   private static final String RETURNING = "returning";
   private static final String RETURNING_PROPERTY = "returningName";
   private static final String THROWING = "throwing";
   private static final String THROWING_PROPERTY = "throwingName";
   private static final String ARG_NAMES = "arg-names";
   private static final String ARG_NAMES_PROPERTY = "argumentNames";
   private static final String ASPECT_NAME_PROPERTY = "aspectName";
   private static final String DECLARATION_ORDER_PROPERTY = "declarationOrder";
   private static final String ORDER_PROPERTY = "order";
   private static final int METHOD_INDEX = 0;
   private static final int POINTCUT_INDEX = 1;
   private static final int ASPECT_INSTANCE_FACTORY_INDEX = 2;
   private ParseState parseState = new ParseState();

   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), parserContext.extractSource(element));
      parserContext.pushContainingComponent(compositeDef);
      this.configureAutoProxyCreator(parserContext, element);
      List childElts = DomUtils.getChildElements(element);
      Iterator var5 = childElts.iterator();

      while(var5.hasNext()) {
         Element elt = (Element)var5.next();
         String localName = parserContext.getDelegate().getLocalName(elt);
         if ("pointcut".equals(localName)) {
            this.parsePointcut(elt, parserContext);
         } else if ("advisor".equals(localName)) {
            this.parseAdvisor(elt, parserContext);
         } else if ("aspect".equals(localName)) {
            this.parseAspect(elt, parserContext);
         }
      }

      parserContext.popAndRegisterContainingComponent();
      return null;
   }

   private void configureAutoProxyCreator(ParserContext parserContext, Element element) {
      AopNamespaceUtils.registerAspectJAutoProxyCreatorIfNecessary(parserContext, element);
   }

   private void parseAdvisor(Element advisorElement, ParserContext parserContext) {
      AbstractBeanDefinition advisorDef = this.createAdvisorBeanDefinition(advisorElement, parserContext);
      String id = advisorElement.getAttribute("id");

      try {
         this.parseState.push(new AdvisorEntry(id));
         String advisorBeanName = id;
         if (StringUtils.hasText(id)) {
            parserContext.getRegistry().registerBeanDefinition(id, advisorDef);
         } else {
            advisorBeanName = parserContext.getReaderContext().registerWithGeneratedName(advisorDef);
         }

         Object pointcut = this.parsePointcutProperty(advisorElement, parserContext);
         if (pointcut instanceof BeanDefinition) {
            advisorDef.getPropertyValues().add("pointcut", pointcut);
            parserContext.registerComponent(new AdvisorComponentDefinition(advisorBeanName, advisorDef, (BeanDefinition)pointcut));
         } else if (pointcut instanceof String) {
            advisorDef.getPropertyValues().add("pointcut", new RuntimeBeanReference((String)pointcut));
            parserContext.registerComponent(new AdvisorComponentDefinition(advisorBeanName, advisorDef));
         }
      } finally {
         this.parseState.pop();
      }

   }

   private AbstractBeanDefinition createAdvisorBeanDefinition(Element advisorElement, ParserContext parserContext) {
      RootBeanDefinition advisorDefinition = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
      advisorDefinition.setSource(parserContext.extractSource(advisorElement));
      String adviceRef = advisorElement.getAttribute("advice-ref");
      if (!StringUtils.hasText(adviceRef)) {
         parserContext.getReaderContext().error("'advice-ref' attribute contains empty value.", advisorElement, this.parseState.snapshot());
      } else {
         advisorDefinition.getPropertyValues().add("adviceBeanName", new RuntimeBeanNameReference(adviceRef));
      }

      if (advisorElement.hasAttribute("order")) {
         advisorDefinition.getPropertyValues().add("order", advisorElement.getAttribute("order"));
      }

      return advisorDefinition;
   }

   private void parseAspect(Element aspectElement, ParserContext parserContext) {
      String aspectId = aspectElement.getAttribute("id");
      String aspectName = aspectElement.getAttribute("ref");

      try {
         this.parseState.push(new AspectEntry(aspectId, aspectName));
         List beanDefinitions = new ArrayList();
         List beanReferences = new ArrayList();
         List declareParents = DomUtils.getChildElementsByTagName(aspectElement, "declare-parents");

         for(int i = 0; i < declareParents.size(); ++i) {
            Element declareParentsElement = (Element)declareParents.get(i);
            beanDefinitions.add(this.parseDeclareParents(declareParentsElement, parserContext));
         }

         NodeList nodeList = aspectElement.getChildNodes();
         boolean adviceFoundAlready = false;

         for(int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            if (this.isAdviceNode(node, parserContext)) {
               if (!adviceFoundAlready) {
                  adviceFoundAlready = true;
                  if (!StringUtils.hasText(aspectName)) {
                     parserContext.getReaderContext().error("<aspect> tag needs aspect bean reference via 'ref' attribute when declaring advices.", aspectElement, this.parseState.snapshot());
                     return;
                  }

                  beanReferences.add(new RuntimeBeanReference(aspectName));
               }

               AbstractBeanDefinition advisorDefinition = this.parseAdvice(aspectName, i, aspectElement, (Element)node, parserContext, beanDefinitions, beanReferences);
               beanDefinitions.add(advisorDefinition);
            }
         }

         AspectComponentDefinition aspectComponentDefinition = this.createAspectComponentDefinition(aspectElement, aspectId, beanDefinitions, beanReferences, parserContext);
         parserContext.pushContainingComponent(aspectComponentDefinition);
         List pointcuts = DomUtils.getChildElementsByTagName(aspectElement, "pointcut");
         Iterator var21 = pointcuts.iterator();

         while(var21.hasNext()) {
            Element pointcutElement = (Element)var21.next();
            this.parsePointcut(pointcutElement, parserContext);
         }

         parserContext.popAndRegisterContainingComponent();
      } finally {
         this.parseState.pop();
      }
   }

   private AspectComponentDefinition createAspectComponentDefinition(Element aspectElement, String aspectId, List beanDefs, List beanRefs, ParserContext parserContext) {
      BeanDefinition[] beanDefArray = (BeanDefinition[])beanDefs.toArray(new BeanDefinition[0]);
      BeanReference[] beanRefArray = (BeanReference[])beanRefs.toArray(new BeanReference[0]);
      Object source = parserContext.extractSource(aspectElement);
      return new AspectComponentDefinition(aspectId, beanDefArray, beanRefArray, source);
   }

   private boolean isAdviceNode(Node aNode, ParserContext parserContext) {
      if (!(aNode instanceof Element)) {
         return false;
      } else {
         String name = parserContext.getDelegate().getLocalName(aNode);
         return "before".equals(name) || "after".equals(name) || "after-returning".equals(name) || "after-throwing".equals(name) || "around".equals(name);
      }
   }

   private AbstractBeanDefinition parseDeclareParents(Element declareParentsElement, ParserContext parserContext) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DeclareParentsAdvisor.class);
      builder.addConstructorArgValue(declareParentsElement.getAttribute("implement-interface"));
      builder.addConstructorArgValue(declareParentsElement.getAttribute("types-matching"));
      String defaultImpl = declareParentsElement.getAttribute("default-impl");
      String delegateRef = declareParentsElement.getAttribute("delegate-ref");
      if (StringUtils.hasText(defaultImpl) && !StringUtils.hasText(delegateRef)) {
         builder.addConstructorArgValue(defaultImpl);
      } else if (StringUtils.hasText(delegateRef) && !StringUtils.hasText(defaultImpl)) {
         builder.addConstructorArgReference(delegateRef);
      } else {
         parserContext.getReaderContext().error("Exactly one of the default-impl or delegate-ref attributes must be specified", declareParentsElement, this.parseState.snapshot());
      }

      AbstractBeanDefinition definition = builder.getBeanDefinition();
      definition.setSource(parserContext.extractSource(declareParentsElement));
      parserContext.getReaderContext().registerWithGeneratedName(definition);
      return definition;
   }

   private AbstractBeanDefinition parseAdvice(String aspectName, int order, Element aspectElement, Element adviceElement, ParserContext parserContext, List beanDefinitions, List beanReferences) {
      RootBeanDefinition var12;
      try {
         this.parseState.push(new AdviceEntry(parserContext.getDelegate().getLocalName(adviceElement)));
         RootBeanDefinition methodDefinition = new RootBeanDefinition(MethodLocatingFactoryBean.class);
         methodDefinition.getPropertyValues().add("targetBeanName", aspectName);
         methodDefinition.getPropertyValues().add("methodName", adviceElement.getAttribute("method"));
         methodDefinition.setSynthetic(true);
         RootBeanDefinition aspectFactoryDef = new RootBeanDefinition(SimpleBeanFactoryAwareAspectInstanceFactory.class);
         aspectFactoryDef.getPropertyValues().add("aspectBeanName", aspectName);
         aspectFactoryDef.setSynthetic(true);
         AbstractBeanDefinition adviceDef = this.createAdviceDefinition(adviceElement, parserContext, aspectName, order, methodDefinition, aspectFactoryDef, beanDefinitions, beanReferences);
         RootBeanDefinition advisorDefinition = new RootBeanDefinition(AspectJPointcutAdvisor.class);
         advisorDefinition.setSource(parserContext.extractSource(adviceElement));
         advisorDefinition.getConstructorArgumentValues().addGenericArgumentValue((Object)adviceDef);
         if (aspectElement.hasAttribute("order")) {
            advisorDefinition.getPropertyValues().add("order", aspectElement.getAttribute("order"));
         }

         parserContext.getReaderContext().registerWithGeneratedName(advisorDefinition);
         var12 = advisorDefinition;
      } finally {
         this.parseState.pop();
      }

      return var12;
   }

   private AbstractBeanDefinition createAdviceDefinition(Element adviceElement, ParserContext parserContext, String aspectName, int order, RootBeanDefinition methodDef, RootBeanDefinition aspectFactoryDef, List beanDefinitions, List beanReferences) {
      RootBeanDefinition adviceDefinition = new RootBeanDefinition(this.getAdviceClass(adviceElement, parserContext));
      adviceDefinition.setSource(parserContext.extractSource(adviceElement));
      adviceDefinition.getPropertyValues().add("aspectName", aspectName);
      adviceDefinition.getPropertyValues().add("declarationOrder", order);
      if (adviceElement.hasAttribute("returning")) {
         adviceDefinition.getPropertyValues().add("returningName", adviceElement.getAttribute("returning"));
      }

      if (adviceElement.hasAttribute("throwing")) {
         adviceDefinition.getPropertyValues().add("throwingName", adviceElement.getAttribute("throwing"));
      }

      if (adviceElement.hasAttribute("arg-names")) {
         adviceDefinition.getPropertyValues().add("argumentNames", adviceElement.getAttribute("arg-names"));
      }

      ConstructorArgumentValues cav = adviceDefinition.getConstructorArgumentValues();
      cav.addIndexedArgumentValue(0, (Object)methodDef);
      Object pointcut = this.parsePointcutProperty(adviceElement, parserContext);
      if (pointcut instanceof BeanDefinition) {
         cav.addIndexedArgumentValue(1, (Object)pointcut);
         beanDefinitions.add((BeanDefinition)pointcut);
      } else if (pointcut instanceof String) {
         RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String)pointcut);
         cav.addIndexedArgumentValue(1, (Object)pointcutRef);
         beanReferences.add(pointcutRef);
      }

      cav.addIndexedArgumentValue(2, (Object)aspectFactoryDef);
      return adviceDefinition;
   }

   private Class getAdviceClass(Element adviceElement, ParserContext parserContext) {
      String elementName = parserContext.getDelegate().getLocalName(adviceElement);
      if ("before".equals(elementName)) {
         return AspectJMethodBeforeAdvice.class;
      } else if ("after".equals(elementName)) {
         return AspectJAfterAdvice.class;
      } else if ("after-returning".equals(elementName)) {
         return AspectJAfterReturningAdvice.class;
      } else if ("after-throwing".equals(elementName)) {
         return AspectJAfterThrowingAdvice.class;
      } else if ("around".equals(elementName)) {
         return AspectJAroundAdvice.class;
      } else {
         throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
      }
   }

   private AbstractBeanDefinition parsePointcut(Element pointcutElement, ParserContext parserContext) {
      String id = pointcutElement.getAttribute("id");
      String expression = pointcutElement.getAttribute("expression");
      AbstractBeanDefinition pointcutDefinition = null;

      try {
         this.parseState.push(new PointcutEntry(id));
         pointcutDefinition = this.createPointcutDefinition(expression);
         pointcutDefinition.setSource(parserContext.extractSource(pointcutElement));
         String pointcutBeanName = id;
         if (StringUtils.hasText(id)) {
            parserContext.getRegistry().registerBeanDefinition(id, pointcutDefinition);
         } else {
            pointcutBeanName = parserContext.getReaderContext().registerWithGeneratedName(pointcutDefinition);
         }

         parserContext.registerComponent(new PointcutComponentDefinition(pointcutBeanName, pointcutDefinition, expression));
      } finally {
         this.parseState.pop();
      }

      return pointcutDefinition;
   }

   @Nullable
   private Object parsePointcutProperty(Element element, ParserContext parserContext) {
      if (element.hasAttribute("pointcut") && element.hasAttribute("pointcut-ref")) {
         parserContext.getReaderContext().error("Cannot define both 'pointcut' and 'pointcut-ref' on <advisor> tag.", element, this.parseState.snapshot());
         return null;
      } else {
         String pointcutRef;
         if (element.hasAttribute("pointcut")) {
            pointcutRef = element.getAttribute("pointcut");
            AbstractBeanDefinition pointcutDefinition = this.createPointcutDefinition(pointcutRef);
            pointcutDefinition.setSource(parserContext.extractSource(element));
            return pointcutDefinition;
         } else if (element.hasAttribute("pointcut-ref")) {
            pointcutRef = element.getAttribute("pointcut-ref");
            if (!StringUtils.hasText(pointcutRef)) {
               parserContext.getReaderContext().error("'pointcut-ref' attribute contains empty value.", element, this.parseState.snapshot());
               return null;
            } else {
               return pointcutRef;
            }
         } else {
            parserContext.getReaderContext().error("Must define one of 'pointcut' or 'pointcut-ref' on <advisor> tag.", element, this.parseState.snapshot());
            return null;
         }
      }
   }

   protected AbstractBeanDefinition createPointcutDefinition(String expression) {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(AspectJExpressionPointcut.class);
      beanDefinition.setScope("prototype");
      beanDefinition.setSynthetic(true);
      beanDefinition.getPropertyValues().add("expression", expression);
      return beanDefinition;
   }
}
