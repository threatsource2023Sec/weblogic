package org.jboss.weld.contexts.beanstore;

import java.util.Collection;
import java.util.Iterator;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public interface NamingScheme {
   boolean accept(String var1);

   BeanIdentifier deprefix(String var1);

   String prefix(BeanIdentifier var1);

   Collection filterIds(Iterator var1);

   Collection deprefix(Collection var1);

   Collection prefix(Collection var1);
}
