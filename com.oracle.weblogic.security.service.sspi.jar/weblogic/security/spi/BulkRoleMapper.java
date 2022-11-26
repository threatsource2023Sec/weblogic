package weblogic.security.spi;

import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface BulkRoleMapper {
   Map getRoles(Subject var1, List var2, ContextHandler var3);
}
