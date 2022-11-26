package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.xml.namespace.QName;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;

class ConstrainedMethodStaxBuilder extends AbstractConstrainedExecutableElementStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String METHOD_QNAME_LOCAL_PART = "method";
   private static final QName NAME_QNAME = new QName("name");

   ConstrainedMethodStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
      super(classLoadingHelper, constraintHelper, typeResolutionHelper, valueExtractorManager, defaultPackageStaxBuilder, annotationProcessingOptions);
   }

   Optional getMainAttributeValueQname() {
      return Optional.of(NAME_QNAME);
   }

   protected String getAcceptableQName() {
      return "method";
   }

   public String getMethodName() {
      return this.mainAttributeValue;
   }

   ConstrainedExecutable build(Class beanClass, List alreadyProcessedMethods) {
      Class[] parameterTypes = (Class[])this.constrainedParameterStaxBuilders.stream().map((builderx) -> {
         return builderx.getParameterType(beanClass);
      }).toArray((x$0) -> {
         return new Class[x$0];
      });
      String methodName = this.getMethodName();
      Method method = (Method)run(GetDeclaredMethod.action(beanClass, methodName, parameterTypes));
      if (method == null) {
         throw LOG.getBeanDoesNotContainMethodException(beanClass, methodName, parameterTypes);
      } else if (alreadyProcessedMethods.contains(method)) {
         throw LOG.getMethodIsDefinedTwiceInMappingXmlForBeanException(method, beanClass);
      } else {
         alreadyProcessedMethods.add(method);
         if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsOnMember(method, (Boolean)this.ignoreAnnotations.get());
         }

         List constrainedParameters = CollectionHelper.newArrayList(this.constrainedParameterStaxBuilders.size());

         for(int index = 0; index < this.constrainedParameterStaxBuilders.size(); ++index) {
            ConstrainedParameterStaxBuilder builder = (ConstrainedParameterStaxBuilder)this.constrainedParameterStaxBuilders.get(index);
            constrainedParameters.add(builder.build(method, index));
         }

         Set crossParameterConstraints = (Set)this.getCrossParameterStaxBuilder().map((builderx) -> {
            return builderx.build(method);
         }).orElse(Collections.emptySet());
         Set returnValueConstraints = new HashSet();
         Set returnValueTypeArgumentConstraints = new HashSet();
         CascadingMetaDataBuilder cascadingMetaDataBuilder = (CascadingMetaDataBuilder)this.getReturnValueStaxBuilder().map((builderx) -> {
            return builderx.build(method, returnValueConstraints, returnValueTypeArgumentConstraints);
         }).orElse(CascadingMetaDataBuilder.nonCascading());
         return new ConstrainedExecutable(ConfigurationSource.XML, method, constrainedParameters, crossParameterConstraints, returnValueConstraints, returnValueTypeArgumentConstraints, cascadingMetaDataBuilder);
      }
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
