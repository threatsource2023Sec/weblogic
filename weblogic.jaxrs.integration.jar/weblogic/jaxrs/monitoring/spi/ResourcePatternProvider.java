package weblogic.jaxrs.monitoring.spi;

import javax.servlet.ServletContext;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ResourcePatternProvider {
   String getResourcePattern(ServletContext var1);
}
