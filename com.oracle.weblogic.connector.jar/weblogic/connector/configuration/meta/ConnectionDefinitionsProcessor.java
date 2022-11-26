package weblogic.connector.configuration.meta;

import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionDefinitions;
import weblogic.connector.exception.RAException;

@AnnotationProcessorDescription(
   targetAnnotation = ConnectionDefinitions.class
)
class ConnectionDefinitionsProcessor implements TypeAnnotationProcessor {
   private final ConnectionDefinitionProcessor definitionProcessor;

   ConnectionDefinitionsProcessor(ConnectionDefinitionProcessor nested) {
      this.definitionProcessor = nested;
   }

   public void processClass(Class clz, ConnectionDefinitions annotation) throws RAException {
      ConnectionDefinition[] value = annotation.value();
      if (value != null && value.length > 0) {
         ConnectionDefinition[] var4 = value;
         int var5 = value.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ConnectionDefinition connectionDefinition = var4[var6];
            this.definitionProcessor.processClass(clz, connectionDefinition);
         }
      }

   }
}
