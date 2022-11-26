package weblogic.j2ee.dd.xml;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;

@Service
@PerLookup
public class JMSDestinationDefinitionProcessor extends AnnotationProcessorService {
   static final String[] attributeNames = new String[]{"AnnotationType", "ClassName", "Description", "Name", "InterfaceName", "ResourceAdapter", "DestinationName"};
   static final Set ACCEPTED_UD_QUEUE_PROPERTIES = new HashSet(Arrays.asList("attach-sender", "bytes-high", "bytes-low", "consumption-paused-at-startup", "default-unit-of-order", "delivery-mode", "expiration-logging-policy", "expiration-policy", "forward-delay", "incomplete-work-expiration-time", "insertion-paused-at-startup", "jms-create-destination-identifier", "load-balancing-policy", "maximum-message-size", "message-logging-enabled", "message-logging-format", "messages-high", "messages-low", "messaging-performance-preference", "priority", "production-paused-at-startup", "redelivery-delay", "redelivery-limit", "reset-delivery-count-on-forward", "saf-export-policy", "time-to-deliver", "time-to-live", "unit-of-order-routing", "unit-of-work-handling-policy"));
   static final Set ACCEPTED_UD_TOPIC_PROPERTIES = new HashSet(Arrays.asList("attach-sender", "bytes-high", "bytes-low", "consumption-paused-at-startup", "default-unit-of-order", "delivery-mode", "expiration-logging-policy", "expiration-policy", "forwarding-policy", "incomplete-work-expiration-time", "insertion-paused-at-startup", "jms-create-destination-identifier", "load-balancing-policy", "maximum-message-size", "message-logging-enabled", "message-logging-format", "messages-high", "messages-low", "messaging-performance-preference", "multicast-address", "multicast-port", "multicast-time-to-live", "priority", "production-paused-at-startup", "redelivery-delay", "redelivery-limit, saf-export-policy", "time-to-deliver", "time-to-live", "unit-of-order-routing", "unit-of-work-handling-policy"));

   private Object[] getAnnotationAttributes(JMSDestinationDefinition anno) {
      return new Object[]{anno.annotationType(), anno.className(), anno.description(), anno.name(), anno.interfaceName(), anno.resourceAdapter(), anno.destinationName()};
   }

   protected JMSDestinationDefinition processAnnotation(JmsDestinationBean bean, JMSDestinationDefinition anno, JMSDestinationDefinition mergedAnnotation, boolean isNotInDescriptor) throws DuplicateAnnotationException {
      if (mergedAnnotation == null) {
         Object[] beanValues = new Object[]{null, bean.getClassName(), bean.getDescription(), bean.getName(), bean.getInterfaceName(), bean.getResourceAdapter(), bean.getDestinationName()};
         AnnotationProxy proxy = new AnnotationProxy(isNotInDescriptor, bean.getProperties(), attributeNames, beanValues, anno.properties(), this.getAnnotationAttributes(anno));
         mergedAnnotation = (JMSDestinationDefinition)proxy.newInstance(JMSDestinationDefinition.class);
      } else {
         AnnotationProxy proxy = (AnnotationProxy)Proxy.getInvocationHandler(mergedAnnotation);
         boolean isTopic = anno.interfaceName().equals("javax.jms.Topic");
         Set knownPropertyNames = isTopic ? ACCEPTED_UD_TOPIC_PROPERTIES : ACCEPTED_UD_QUEUE_PROPERTIES;
         proxy.merge(knownPropertyNames, anno, anno.properties(), this.getAnnotationAttributes(anno));
      }

      this.setUnsetAttribute(bean, isNotInDescriptor, "ClassName", anno.className());
      this.setUnsetAttribute(bean, isNotInDescriptor, "Description", anno.description());
      this.setUnsetAttribute(bean, isNotInDescriptor, "InterfaceName", anno.interfaceName());
      this.setUnsetAttribute(bean, isNotInDescriptor, "ResourceAdapter", anno.resourceAdapter());
      this.setUnsetAttribute(bean, isNotInDescriptor, "DestinationName", anno.destinationName());
      this.setUnsetProperties(bean, isNotInDescriptor, anno.properties());
      return mergedAnnotation;
   }

   protected JmsDestinationBean createBeanNamed(String name, J2eeClientEnvironmentBean eg) {
      JmsDestinationBean bean = eg.createJmsDestination();
      bean.setName(name);
      return bean;
   }

   protected JmsDestinationBean[] getBeans(J2eeClientEnvironmentBean eg) {
      return eg.getJmsDestinations();
   }

   protected void validateAnnotation(JMSDestinationDefinition def, Class beanClass) {
      if (def.name().length() == 0) {
         this.addProcessingError("A JMSDestinationDefinition on class, " + beanClass.getName() + ", does not have the name attribute set.");
      }

   }

   protected Class getAnnotationsClass() {
      return JMSDestinationDefinitions.class;
   }

   protected Class getAnnotationClass() {
      return JMSDestinationDefinition.class;
   }
}
