package weblogic.j2ee.dd.xml;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSConnectionFactoryDefinitions;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.mail.MailSessionDefinition;
import javax.mail.MailSessionDefinitions;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnits;
import javax.resource.AdministeredObjectDefinition;
import javax.resource.AdministeredObjectDefinitions;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.ConnectionFactoryDefinitions;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.PojoAnnotationProcessor;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.utils.ErrorCollectionException;

@Service
@PerLookup
public class PojoAnnotationProcessorImpl extends J2eeAnnotationProcessor implements PojoAnnotationProcessor {
   private static final Class[] supportedAnnotations = new Class[]{Resource.class, Resources.class, EJB.class, EJBs.class, PersistenceContext.class, PersistenceContexts.class, PersistenceUnit.class, PersistenceUnits.class, WebServiceRef.class, WebServiceRefs.class, JMSConnectionFactoryDefinition.class, JMSConnectionFactoryDefinitions.class, JMSDestinationDefinitions.class, JMSDestinationDefinition.class, MailSessionDefinition.class, MailSessionDefinitions.class, AdministeredObjectDefinition.class, AdministeredObjectDefinitions.class, ConnectionFactoryDefinition.class, ConnectionFactoryDefinitions.class, DataSourceDefinition.class, DataSourceDefinitions.class};
   private static String[] supportedAnnotationNames;
   private static Set supportedAnnotationsSet;

   public PojoAnnotationProcessorImpl() {
      super(supportedAnnotationsSet);
   }

   public String[] getSupportedAnnotationNames() {
      return supportedAnnotationNames;
   }

   public void processJ2eeAnnotations(Class beanClass, J2eeEnvironmentBean eg, boolean throwErrors) throws ErrorCollectionException {
      super.processJ2eeAnnotations(beanClass, eg, throwErrors);
   }

   protected Map getClassResources(Class bean) {
      return Collections.emptyMap();
   }

   protected Map getClassEJBRefs(Class bean) {
      return Collections.emptyMap();
   }

   static {
      supportedAnnotationNames = new String[supportedAnnotations.length];
      supportedAnnotationsSet = new HashSet(supportedAnnotations.length);

      for(int i = 0; i < supportedAnnotations.length; ++i) {
         supportedAnnotationNames[i] = supportedAnnotations[i].getName();
         supportedAnnotationsSet.add(supportedAnnotations[i]);
      }

   }
}
