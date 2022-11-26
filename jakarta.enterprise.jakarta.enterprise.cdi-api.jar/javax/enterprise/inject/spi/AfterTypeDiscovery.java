package javax.enterprise.inject.spi;

import java.util.List;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

public interface AfterTypeDiscovery {
   List getAlternatives();

   List getInterceptors();

   List getDecorators();

   void addAnnotatedType(AnnotatedType var1, String var2);

   AnnotatedTypeConfigurator addAnnotatedType(Class var1, String var2);
}
