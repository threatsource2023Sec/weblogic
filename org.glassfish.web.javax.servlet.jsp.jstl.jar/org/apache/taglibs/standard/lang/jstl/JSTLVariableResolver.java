package org.apache.taglibs.standard.lang.jstl;

import javax.servlet.jsp.PageContext;

public class JSTLVariableResolver implements VariableResolver {
   public Object resolveVariable(String pName, Object pContext) throws ELException {
      PageContext ctx = (PageContext)pContext;
      if ("pageContext".equals(pName)) {
         return ctx;
      } else if ("pageScope".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getPageScopeMap();
      } else if ("requestScope".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getRequestScopeMap();
      } else if ("sessionScope".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getSessionScopeMap();
      } else if ("applicationScope".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getApplicationScopeMap();
      } else if ("param".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getParamMap();
      } else if ("paramValues".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getParamsMap();
      } else if ("header".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getHeaderMap();
      } else if ("headerValues".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getHeadersMap();
      } else if ("initParam".equals(pName)) {
         return ImplicitObjects.getImplicitObjects(ctx).getInitParamMap();
      } else {
         return "cookie".equals(pName) ? ImplicitObjects.getImplicitObjects(ctx).getCookieMap() : ctx.findAttribute(pName);
      }
   }
}
