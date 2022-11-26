package org.jboss.weld.module;

import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.manager.BeanManagerImpl;

public interface ExpressionLanguageSupport extends Service {
   ExpressionFactory wrapExpressionFactory(ExpressionFactory var1);

   ELResolver createElResolver(BeanManagerImpl var1);
}
