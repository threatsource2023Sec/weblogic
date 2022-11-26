package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SimpleConstructorNamespaceHandler implements NamespaceHandler {
   private static final String REF_SUFFIX = "-ref";
   private static final String DELIMITER_PREFIX = "_";

   public void init() {
   }

   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      parserContext.getReaderContext().error("Class [" + this.getClass().getName() + "] does not support custom elements.", element);
      return null;
   }

   public BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext) {
      if (node instanceof Attr) {
         Attr attr = (Attr)node;
         String argName = StringUtils.trimWhitespace(parserContext.getDelegate().getLocalName(attr));
         String argValue = StringUtils.trimWhitespace(attr.getValue());
         ConstructorArgumentValues cvs = definition.getBeanDefinition().getConstructorArgumentValues();
         boolean ref = false;
         if (argName.endsWith("-ref")) {
            ref = true;
            argName = argName.substring(0, argName.length() - "-ref".length());
         }

         ConstructorArgumentValues.ValueHolder valueHolder = new ConstructorArgumentValues.ValueHolder(ref ? new RuntimeBeanReference(argValue) : argValue);
         valueHolder.setSource(parserContext.getReaderContext().extractSource(attr));
         String arg;
         if (argName.startsWith("_")) {
            arg = argName.substring(1).trim();
            if (!StringUtils.hasText(arg)) {
               cvs.addGenericArgumentValue(valueHolder);
            } else {
               int index = -1;

               try {
                  index = Integer.parseInt(arg);
               } catch (NumberFormatException var13) {
                  parserContext.getReaderContext().error("Constructor argument '" + argName + "' specifies an invalid integer", attr);
               }

               if (index < 0) {
                  parserContext.getReaderContext().error("Constructor argument '" + argName + "' specifies a negative index", attr);
               }

               if (cvs.hasIndexedArgumentValue(index)) {
                  parserContext.getReaderContext().error("Constructor argument '" + argName + "' with index " + index + " already defined using <constructor-arg>. Only one approach may be used per argument.", attr);
               }

               cvs.addIndexedArgumentValue(index, valueHolder);
            }
         } else {
            arg = Conventions.attributeNameToPropertyName(argName);
            if (this.containsArgWithName(arg, cvs)) {
               parserContext.getReaderContext().error("Constructor argument '" + argName + "' already defined using <constructor-arg>. Only one approach may be used per argument.", attr);
            }

            valueHolder.setName(Conventions.attributeNameToPropertyName(argName));
            cvs.addGenericArgumentValue(valueHolder);
         }
      }

      return definition;
   }

   private boolean containsArgWithName(String name, ConstructorArgumentValues cvs) {
      return this.checkName(name, cvs.getGenericArgumentValues()) || this.checkName(name, cvs.getIndexedArgumentValues().values());
   }

   private boolean checkName(String name, Collection values) {
      Iterator var3 = values.iterator();

      ConstructorArgumentValues.ValueHolder holder;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         holder = (ConstructorArgumentValues.ValueHolder)var3.next();
      } while(!name.equals(holder.getName()));

      return true;
   }
}
