package weblogic.j2ee.dd.xml;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSConnectionFactoryDefinitions;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;

@Service
@PerLookup
public class JMSConnectionFactoryProcessor extends AnnotationProcessorService {
   static final String[] attributeNames = new String[]{"AnnotationType", "ClassName", "ClientId", "Description", "Name", "InterfaceName", "MaxPoolSize", "MinPoolSize", "Password", "ResourceAdapter", "IsTransactional", "User"};
   static final Set knownPropertyNames = new HashSet(Arrays.asList("acknowledge-policy", "allow-close-in-onMessage", "attach-jmsx-user-id", "client-id-policy", "default-compression-threshold", "default-delivery-mode", "default-priority", "default-redelivery-delay", "default-time-to-deliver", "default-time-to-live", "default-unit-of-order", "flow-control-enabled", "flow-interval", "flow-maximum", "flow-minimum", "flow-steps", "load-balancing-enabled", "messages-maximum", "multicast-overrun-policy", "one-way-send-mode", "one-way-send-window-size", "reconnect-blocking-millis", "reconnect-policy", "send-timeout", "server-affinity-enabled", "subscription-sharing-policy", "synchronous-prefetch-mode", "total-reconnect-period-millis", "transaction-timeout"));

   private Object[] getAnnotationAttributes(JMSConnectionFactoryDefinition anno) {
      return new Object[]{anno.annotationType(), anno.className(), anno.clientId(), anno.description(), anno.name(), anno.interfaceName(), anno.maxPoolSize(), anno.minPoolSize(), anno.password(), anno.resourceAdapter(), anno.interfaceName(), anno.user()};
   }

   protected JMSConnectionFactoryDefinition processAnnotation(JmsConnectionFactoryBean bean, JMSConnectionFactoryDefinition anno, JMSConnectionFactoryDefinition mergedAnnotation, boolean isNotInDescriptor) throws DuplicateAnnotationException {
      if (mergedAnnotation == null) {
         Object[] beanValues = new Object[]{null, bean.getClassName(), bean.getClientId(), bean.getDescription(), bean.getName(), bean.getInterfaceName(), bean.getMaxPoolSize(), bean.getMinPoolSize(), bean.getPassword(), bean.getResourceAdapter(), bean.isTransactional(), bean.getUser()};
         AnnotationProxy proxy = new AnnotationProxy(isNotInDescriptor, bean.getProperties(), attributeNames, beanValues, anno.properties(), this.getAnnotationAttributes(anno));
         mergedAnnotation = (JMSConnectionFactoryDefinition)proxy.newInstance(JMSConnectionFactoryDefinition.class);
      } else {
         AnnotationProxy proxy = (AnnotationProxy)Proxy.getInvocationHandler(mergedAnnotation);
         proxy.merge(knownPropertyNames, anno, anno.properties(), this.getAnnotationAttributes(anno));
      }

      this.setUnsetAttribute(bean, isNotInDescriptor, "ClassName", anno.className());
      this.setUnsetAttribute(bean, isNotInDescriptor, "ClientId", anno.clientId());
      this.setUnsetAttribute(bean, isNotInDescriptor, "Description", anno.description());
      this.setUnsetAttribute(bean, isNotInDescriptor, "InterfaceName", anno.interfaceName());
      this.setUnsetAttribute(bean, isNotInDescriptor, "MaxPoolSize", anno.maxPoolSize());
      this.setUnsetAttribute(bean, isNotInDescriptor, "MinPoolSize", anno.minPoolSize());
      this.setUnsetAttribute(bean, isNotInDescriptor, "Password", anno.password());
      this.setUnsetAttribute(bean, isNotInDescriptor, "ResourceAdapter", anno.resourceAdapter());
      this.setUnsetAttribute(bean, isNotInDescriptor, "Transactional", anno.transactional());
      this.setUnsetAttribute(bean, isNotInDescriptor, "User", anno.user());
      this.setUnsetProperties(bean, isNotInDescriptor, anno.properties());
      return mergedAnnotation;
   }

   protected JmsConnectionFactoryBean createBeanNamed(String name, J2eeClientEnvironmentBean eg) {
      JmsConnectionFactoryBean bean = eg.createJmsConnectionFactory();
      bean.setName(name);
      return bean;
   }

   protected JmsConnectionFactoryBean[] getBeans(J2eeClientEnvironmentBean eg) {
      return eg.getJmsConnectionFactories();
   }

   protected void validateAnnotation(JMSConnectionFactoryDefinition def, Class beanClass) {
      if (def.name().length() == 0) {
         this.addProcessingError("A JMSConnectionFactoryDefinition on class, " + beanClass.getName() + ", does not have the name attribute set.");
      }

   }

   protected Class getAnnotationsClass() {
      return JMSConnectionFactoryDefinitions.class;
   }

   protected Class getAnnotationClass() {
      return JMSConnectionFactoryDefinition.class;
   }
}
