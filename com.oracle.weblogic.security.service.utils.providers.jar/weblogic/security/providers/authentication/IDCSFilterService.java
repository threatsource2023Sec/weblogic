package weblogic.security.providers.authentication;

import com.bea.common.logger.spi.LoggerSpi;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface IDCSFilterService {
   LoggerSpi getServiceLogger();

   boolean isSyncFilterEnabled();

   boolean getSyncFilterClientCertOnly();

   boolean getSyncFilterMatchCase();

   Map getRemoteUserTenant(HttpServletRequest var1) throws IllegalArgumentException;

   boolean isIDCSSession(HttpServletRequest var1);

   void refresh(IDCSConfiguration var1);
}
