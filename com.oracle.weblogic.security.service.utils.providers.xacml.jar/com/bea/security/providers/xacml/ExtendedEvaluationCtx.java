package com.bea.security.providers.xacml;

import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Direction;

public interface ExtendedEvaluationCtx extends BasicEvaluationCtx {
   Subject getSubject();

   List getResources();

   ContextHandler getContextHandler();

   Map getRoles();

   Direction getDirection();

   ResourceConverter getResourceConverter();

   SubjectConverter getSubjectConverter();

   SubjectConverter getSubjectConverter(Subject var1);

   RoleConverter getRoleConverter();

   ContextConverter getContextConverter();

   DirectionConverter getDirectionConverter();
}
