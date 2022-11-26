package com.bea.core.repackaged.aspectj.weaver.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public interface PointcutExpression {
   void setMatchingContext(MatchingContext var1);

   boolean couldMatchJoinPointsInType(Class var1);

   boolean mayNeedDynamicTest();

   ShadowMatch matchesMethodExecution(Method var1);

   ShadowMatch matchesConstructorExecution(Constructor var1);

   ShadowMatch matchesStaticInitialization(Class var1);

   ShadowMatch matchesAdviceExecution(Method var1);

   ShadowMatch matchesInitialization(Constructor var1);

   ShadowMatch matchesPreInitialization(Constructor var1);

   ShadowMatch matchesMethodCall(Method var1, Member var2);

   ShadowMatch matchesMethodCall(Method var1, Class var2);

   ShadowMatch matchesConstructorCall(Constructor var1, Member var2);

   ShadowMatch matchesConstructorCall(Constructor var1, Class var2);

   ShadowMatch matchesHandler(Class var1, Member var2);

   ShadowMatch matchesHandler(Class var1, Class var2);

   ShadowMatch matchesFieldSet(Field var1, Member var2);

   ShadowMatch matchesFieldSet(Field var1, Class var2);

   ShadowMatch matchesFieldGet(Field var1, Member var2);

   ShadowMatch matchesFieldGet(Field var1, Class var2);

   String getPointcutExpression();
}
