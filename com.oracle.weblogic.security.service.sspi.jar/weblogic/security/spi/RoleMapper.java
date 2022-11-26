package weblogic.security.spi;

import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface RoleMapper {
   Map getRoles(Subject var1, Resource var2, ContextHandler var3);
}
