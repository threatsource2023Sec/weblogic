package weblogic.connector.configuration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.resource.spi.Activation;
import javax.resource.spi.AdministeredObject;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionDefinitions;
import javax.resource.spi.Connector;
import weblogic.application.AnnotationProcessingException;

public interface AdditionalAnnotatedClassesProvider {
   List ANNOTATIONS = Collections.unmodifiableList(new LinkedList() {
      private static final long serialVersionUID = 1L;

      {
         this.add(Connector.class);
         this.add(Activation.class);
         this.add(AdministeredObject.class);
         this.add(ConnectionDefinitions.class);
         this.add(ConnectionDefinition.class);
      }
   });

   Set getAnnotatedClasses() throws AnnotationProcessingException;
}
