package weblogic.ejb.embeddable;

import java.util.Map;
import javax.ejb.embeddable.EJBContainer;

public final class EJBContainerProvider implements javax.ejb.spi.EJBContainerProvider {
   public EJBContainer createEJBContainer(Map properties) {
      return properties != null && properties.get("javax.ejb.embeddable.provider") != null && !properties.get("javax.ejb.embeddable.provider").equals(this.getClass().getName()) ? null : new EJBContainerImpl(properties);
   }
}
