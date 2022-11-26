package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public interface StandardPointcutExpression {
   void setMatchingContext(MatchingContext var1);

   boolean couldMatchJoinPointsInType(Class var1);

   boolean mayNeedDynamicTest();

   ShadowMatch matchesMethodExecution(ResolvedMember var1);

   ShadowMatch matchesStaticInitialization(ResolvedType var1);

   ShadowMatch matchesMethodCall(ResolvedMember var1, ResolvedMember var2);

   String getPointcutExpression();
}
