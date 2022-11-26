package com.sun.faces.el;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.util.MessageUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.VariableResolver;

public class ELUtils {
   private static final String APPLICATION_SCOPE = "applicationScope";
   private static final String SESSION_SCOPE = "sessionScope";
   private static final String REQUEST_SCOPE = "requestScope";
   private static final String COOKIE_IMPLICIT_OBJ = "cookie";
   private static final String FACES_CONTEXT_IMPLICIT_OBJ = "facesContext";
   private static final String HEADER_IMPLICIT_OBJ = "header";
   private static final String HEADER_VALUES_IMPLICIT_OBJ = "headerValues";
   private static final String INIT_PARAM_IMPLICIT_OBJ = "initParam";
   private static final String PARAM_IMPLICIT_OBJ = "param";
   private static final String PARAM_VALUES_IMPLICIT_OBJ = "paramValues";
   private static final String VIEW_IMPLICIT_OBJ = "view";
   public static final ArrayELResolver ARRAY_RESOLVER = new ArrayELResolver();
   public static final BeanELResolver BEAN_RESOLVER = new BeanELResolver();
   public static final FacesResourceBundleELResolver FACES_BUNDLE_RESOLVER = new FacesResourceBundleELResolver();
   public static final ImplicitObjectELResolverForJsp IMPLICIT_JSP_RESOLVER = new ImplicitObjectELResolverForJsp();
   public static final ImplicitObjectELResolver IMPLICIT_RESOLVER = new ImplicitObjectELResolver();
   public static final ListELResolver LIST_RESOLVER = new ListELResolver();
   public static final ManagedBeanELResolver MANAGED_BEAN_RESOLVER = new ManagedBeanELResolver();
   public static final MapELResolver MAP_RESOLVER = new MapELResolver();
   public static final ResourceBundleELResolver BUNDLE_RESOLVER = new ResourceBundleELResolver();
   public static final ScopedAttributeELResolver SCOPED_RESOLVER = new ScopedAttributeELResolver();

   private ELUtils() {
      throw new IllegalStateException();
   }

   public static void buildFacesResolver(FacesCompositeELResolver composite, ApplicationAssociate associate) {
      String message;
      if (associate == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "associate");
         throw new NullPointerException(message);
      } else if (composite == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "composite");
         throw new NullPointerException(message);
      } else {
         composite.addRootELResolver(IMPLICIT_RESOLVER);
         addELResolvers(composite, associate.getELResolversFromFacesConfig());
         addVariableResolvers(composite, FacesCompositeELResolver.ELResolverChainType.Faces, associate);
         addPropertyResolvers(composite, associate);
         composite.add(associate.getApplicationELResolvers());
         composite.addRootELResolver(MANAGED_BEAN_RESOLVER);
         composite.addPropertyELResolver(BUNDLE_RESOLVER);
         composite.addRootELResolver(FACES_BUNDLE_RESOLVER);
         composite.addPropertyELResolver(MAP_RESOLVER);
         composite.addPropertyELResolver(LIST_RESOLVER);
         composite.addPropertyELResolver(ARRAY_RESOLVER);
         composite.addPropertyELResolver(BEAN_RESOLVER);
         composite.addRootELResolver(SCOPED_RESOLVER);
      }
   }

   public static void buildJSPResolver(FacesCompositeELResolver composite, ApplicationAssociate associate) {
      String message;
      if (associate == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "associate");
         throw new NullPointerException(message);
      } else if (composite == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "composite");
         throw new NullPointerException(message);
      } else {
         composite.addRootELResolver(IMPLICIT_JSP_RESOLVER);
         composite.addRootELResolver(MANAGED_BEAN_RESOLVER);
         composite.addRootELResolver(FACES_BUNDLE_RESOLVER);
         addELResolvers(composite, associate.getELResolversFromFacesConfig());
         addVariableResolvers(composite, FacesCompositeELResolver.ELResolverChainType.JSP, associate);
         addPropertyResolvers(composite, associate);
         composite.add(associate.getApplicationELResolvers());
      }
   }

   public static Object evaluateValueExpression(ValueExpression expression, ELContext elContext) {
      return expression.isLiteralText() ? expression.getExpressionString() : expression.getValue(elContext);
   }

   public static PropertyResolver getDelegatePR(ApplicationAssociate associate, boolean provideDefault) {
      PropertyResolver pr = associate.getLegacyPropertyResolver();
      if (pr == null) {
         pr = associate.getLegacyPRChainHead();
         if (pr == null && provideDefault) {
            pr = new DummyPropertyResolverImpl();
         }
      }

      return (PropertyResolver)pr;
   }

   public static VariableResolver getDelegateVR(ApplicationAssociate associate, boolean provideDefault) {
      VariableResolver vr = associate.getLegacyVariableResolver();
      if (vr == null) {
         vr = associate.getLegacyVRChainHead();
         if (vr == null && provideDefault) {
            vr = new ChainAwareVariableResolver();
         }
      }

      return (VariableResolver)vr;
   }

   public static List getExpressionsFromString(String expressionString) throws ReferenceSyntaxException {
      if (null == expressionString) {
         return Collections.emptyList();
      } else {
         List result = new ArrayList();
         int len = expressionString.length();
         int cur = 0;

         int i;
         while(cur < len && -1 != (i = expressionString.indexOf("#{", cur))) {
            int j;
            if (-1 == (j = expressionString.indexOf(125, i + 2))) {
               throw new ReferenceSyntaxException(MessageUtils.getExceptionMessageString("com.sun.faces.INVALID_EXPRESSION", expressionString));
            }

            cur = j + 1;
            result.add(expressionString.substring(i, cur));
         }

         return result;
      }
   }

   public static Scope getScope(String valueBinding, String[] outString) throws ReferenceSyntaxException {
      if (valueBinding != null && 0 != valueBinding.length()) {
         valueBinding = stripBracketsIfNecessary(valueBinding);
         int segmentIndex = getFirstSegmentIndex(valueBinding);
         String identifier = valueBinding;
         if (segmentIndex > 0) {
            identifier = valueBinding.substring(0, segmentIndex);
         }

         FacesContext context = FacesContext.getCurrentInstance();
         ExternalContext ec = context.getExternalContext();
         if (null != outString) {
            outString[0] = identifier;
         }

         if ("requestScope".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("sessionScope".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.SESSION;
         } else if ("applicationScope".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.APPLICATION;
         } else if ("initParam".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.APPLICATION;
         } else if ("cookie".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("facesContext".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("header".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("headerValues".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("param".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("paramValues".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else if ("view".equalsIgnoreCase(identifier)) {
            return ELUtils.Scope.REQUEST;
         } else {
            Map requestMap = ec.getRequestMap();
            if (requestMap != null && requestMap.containsKey(identifier)) {
               return ELUtils.Scope.REQUEST;
            } else {
               Map sessionMap = ec.getSessionMap();
               if (sessionMap != null && sessionMap.containsKey(identifier)) {
                  return ELUtils.Scope.SESSION;
               } else {
                  Map appMap = ec.getApplicationMap();
                  return appMap != null && appMap.containsKey(identifier) ? ELUtils.Scope.APPLICATION : null;
               }
            }
         }
      } else {
         return null;
      }
   }

   public static ValueExpression createValueExpression(String expression) {
      return createValueExpression(expression, Object.class);
   }

   public static ValueExpression createValueExpression(String expression, Class expectedType) {
      FacesContext context = FacesContext.getCurrentInstance();
      return context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), expression, expectedType);
   }

   public static Object coerce(Object value, Class toType) {
      FacesContext context = FacesContext.getCurrentInstance();
      return context.getApplication().getExpressionFactory().coerceToType(value, toType);
   }

   private static void addELResolvers(CompositeELResolver target, List resolvers) {
      if (resolvers != null && !resolvers.isEmpty()) {
         Iterator i$ = resolvers.iterator();

         while(i$.hasNext()) {
            ELResolver resolver = (ELResolver)i$.next();
            target.add(resolver);
         }
      }

   }

   private static void addPropertyResolvers(FacesCompositeELResolver target, ApplicationAssociate associate) {
      PropertyResolver pr = getDelegatePR(associate, false);
      if (pr != null) {
         target.addPropertyELResolver(new PropertyResolverChainWrapper(pr));
      }

   }

   private static void addVariableResolvers(FacesCompositeELResolver target, FacesCompositeELResolver.ELResolverChainType chainType, ApplicationAssociate associate) {
      VariableResolver vr = getDelegateVR(associate, true);
      if (vr != null) {
         VariableResolverChainWrapper vrChainWrapper = new VariableResolverChainWrapper(vr);
         target.addRootELResolver(vrChainWrapper);
         if (chainType == FacesCompositeELResolver.ELResolverChainType.JSP) {
            associate.setLegacyVRChainHeadWrapperForJsp(vrChainWrapper);
         } else {
            associate.setLegacyVRChainHeadWrapperForFaces(vrChainWrapper);
         }
      }

   }

   private static int getFirstSegmentIndex(String valueBinding) {
      int segmentIndex = valueBinding.indexOf(46);
      int bracketIndex = valueBinding.indexOf(91);
      if (segmentIndex < 0) {
         segmentIndex = bracketIndex;
      } else if (bracketIndex > 0 && segmentIndex > bracketIndex) {
         segmentIndex = bracketIndex;
      }

      return segmentIndex;
   }

   private static String stripBracketsIfNecessary(String expression) throws ReferenceSyntaxException {
      assert null != expression;

      if (expression.charAt(0) == '#') {
         if (expression.charAt(1) != '{') {
            throw new ReferenceSyntaxException(MessageUtils.getExceptionMessageString("com.sun.faces.INVALID_EXPRESSION", expression));
         }

         int len = expression.length();
         if (expression.charAt(len - 1) != '}') {
            throw new ReferenceSyntaxException(MessageUtils.getExceptionMessageString("com.sun.faces.INVALID_EXPRESSION", expression));
         }

         expression = expression.substring(2, len - 1);
      }

      return expression;
   }

   public static Scope getScopeForExpression(String expression) {
      return isMixedExpression(expression) ? getNarrowestScopeFromExpression(expression) : getScopeForSingleExpression(expression);
   }

   public static boolean hasValidLifespan(Scope expressionScope, Scope beanScope) throws EvaluationException, ReferenceSyntaxException {
      if (beanScope == ELUtils.Scope.NONE) {
         return expressionScope == ELUtils.Scope.NONE;
      } else if (beanScope == ELUtils.Scope.REQUEST) {
         return true;
      } else if (beanScope == ELUtils.Scope.SESSION) {
         return expressionScope != ELUtils.Scope.REQUEST;
      } else if (beanScope != ELUtils.Scope.APPLICATION) {
         assert false;

         return false;
      } else {
         return expressionScope != ELUtils.Scope.REQUEST && expressionScope != ELUtils.Scope.SESSION;
      }
   }

   public static Scope getScopeForSingleExpression(String value) throws ReferenceSyntaxException, EvaluationException {
      String[] firstSegment = new String[1];
      Scope valueScope = getScope(value, firstSegment);
      if (null == valueScope) {
         if (firstSegment[0] != null) {
            BeanManager manager = ApplicationAssociate.getCurrentInstance().getBeanManager();
            if (manager.isManaged(firstSegment[0])) {
               valueScope = manager.getBuilder(firstSegment[0]).getScope();
            }
         } else {
            valueScope = ELUtils.Scope.APPLICATION;
         }
      }

      return valueScope;
   }

   public static Scope getNarrowestScopeFromExpression(String expression) throws ReferenceSyntaxException {
      List expressions = getExpressionsFromString(expression);
      int shortestScope = ELUtils.Scope.NONE.ordinal();
      Scope result = ELUtils.Scope.NONE;
      Iterator i$ = expressions.iterator();

      while(i$.hasNext()) {
         String expr = (String)i$.next();
         Scope lScope = getScopeForSingleExpression(expr);
         if (null != lScope && lScope != ELUtils.Scope.NONE) {
            int currentScope = lScope.ordinal();
            if (ELUtils.Scope.NONE.ordinal() == shortestScope) {
               shortestScope = currentScope;
               result = lScope;
            } else if (currentScope < shortestScope) {
               shortestScope = currentScope;
               result = lScope;
            }
         }
      }

      return result;
   }

   public static boolean isMixedExpression(String expression) {
      if (null == expression) {
         return false;
      } else {
         return (!expression.startsWith("#{") || !expression.endsWith("}")) && isExpression(expression);
      }
   }

   public static boolean isExpression(String expression) {
      if (null == expression) {
         return false;
      } else {
         int start = expression.indexOf("#{");
         return expression.indexOf("#{") != -1 && start < expression.indexOf(125);
      }
   }

   public static enum Scope {
      NONE("none"),
      REQUEST("request"),
      SESSION("session"),
      APPLICATION("application");

      String scope;

      private Scope(String scope) {
         this.scope = scope;
      }

      public String toString() {
         return this.scope;
      }
   }
}
