package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public interface EExpression {
   int DEPENDS_ON_USRGRP = 1;
   int DEPENDS_ON_ROL = 2;
   int DEPENDS_ON_PREDICATE = 4;

   boolean evaluate(ESubject var1, ResourceNode var2, ContextHandler var3, PredicateRegistry var4);

   String externalize();

   String serialize();

   int getDependsOn();
}
