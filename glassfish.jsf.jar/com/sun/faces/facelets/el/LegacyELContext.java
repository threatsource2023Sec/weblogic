package com.sun.faces.facelets.el;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.PropertyNotWritableException;
import javax.el.VariableMapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;

/** @deprecated */
public final class LegacyELContext extends ELContext {
   private static final String[] IMPLICIT_OBJECTS = new String[]{"application", "applicationScope", "cookie", "facesContext", "header", "headerValues", "initParam", "param", "paramValues", "request", "requestScope", "session", "sessionScope", "view"};
   private static final FunctionMapper FUNCTIONS = new EmptyFunctionMapper();
   private final FacesContext faces;
   private final ELResolver resolver;
   private final VariableMapper variables;

   public LegacyELContext(FacesContext faces) {
      this.faces = faces;
      this.resolver = new LegacyELResolver();
      this.variables = new DefaultVariableMapper();
   }

   public ELResolver getELResolver() {
      return this.resolver;
   }

   public FunctionMapper getFunctionMapper() {
      return FUNCTIONS;
   }

   public VariableMapper getVariableMapper() {
      return this.variables;
   }

   public FacesContext getFacesContext() {
      return this.faces;
   }

   private static final class EmptyFunctionMapper extends FunctionMapper {
      private EmptyFunctionMapper() {
      }

      public Method resolveFunction(String prefix, String localName) {
         return null;
      }

      // $FF: synthetic method
      EmptyFunctionMapper(Object x0) {
         this();
      }
   }

   private final class LegacyELResolver extends ELResolver {
      private LegacyELResolver() {
      }

      public Class getCommonPropertyType(ELContext context, Object base) {
         return Object.class;
      }

      public Iterator getFeatureDescriptors(ELContext context, Object base) {
         return Collections.EMPTY_LIST.iterator();
      }

      private VariableResolver getVariableResolver() {
         return LegacyELContext.this.faces.getApplication().getVariableResolver();
      }

      private PropertyResolver getPropertyResolver() {
         return LegacyELContext.this.faces.getApplication().getPropertyResolver();
      }

      public Class getType(ELContext context, Object base, Object property) {
         if (property == null) {
            return null;
         } else {
            try {
               context.setPropertyResolved(true);
               if (base == null) {
                  Object obj = this.getVariableResolver().resolveVariable(LegacyELContext.this.faces, property.toString());
                  return obj != null ? obj.getClass() : null;
               } else {
                  return !(base instanceof List) && !base.getClass().isArray() ? this.getPropertyResolver().getType(base, property) : this.getPropertyResolver().getType(base, Integer.parseInt(property.toString()));
               }
            } catch (PropertyNotFoundException var5) {
               throw new javax.el.PropertyNotFoundException(var5.getMessage(), var5.getCause());
            } catch (EvaluationException var6) {
               throw new ELException(var6.getMessage(), var6.getCause());
            }
         }
      }

      public Object getValue(ELContext context, Object base, Object property) {
         if (property == null) {
            return null;
         } else {
            try {
               context.setPropertyResolved(true);
               if (base == null) {
                  return this.getVariableResolver().resolveVariable(LegacyELContext.this.faces, property.toString());
               } else {
                  return !(base instanceof List) && !base.getClass().isArray() ? this.getPropertyResolver().getValue(base, property) : this.getPropertyResolver().getValue(base, Integer.parseInt(property.toString()));
               }
            } catch (PropertyNotFoundException var5) {
               throw new javax.el.PropertyNotFoundException(var5.getMessage(), var5.getCause());
            } catch (EvaluationException var6) {
               throw new ELException(var6.getMessage(), var6.getCause());
            }
         }
      }

      public boolean isReadOnly(ELContext context, Object base, Object property) {
         if (property == null) {
            return true;
         } else {
            try {
               context.setPropertyResolved(true);
               if (base == null) {
                  return false;
               } else {
                  return !(base instanceof List) && !base.getClass().isArray() ? this.getPropertyResolver().isReadOnly(base, property) : this.getPropertyResolver().isReadOnly(base, Integer.parseInt(property.toString()));
               }
            } catch (PropertyNotFoundException var5) {
               throw new javax.el.PropertyNotFoundException(var5.getMessage(), var5.getCause());
            } catch (EvaluationException var6) {
               throw new ELException(var6.getMessage(), var6.getCause());
            }
         }
      }

      public void setValue(ELContext context, Object base, Object property, Object value) {
         if (property == null) {
            throw new PropertyNotWritableException("Null Property");
         } else {
            try {
               context.setPropertyResolved(true);
               if (base == null) {
                  if (Arrays.binarySearch(LegacyELContext.IMPLICIT_OBJECTS, property.toString()) >= 0) {
                     throw new PropertyNotWritableException("Implicit Variable Not Setable: " + property);
                  }

                  Map scope = this.resolveScope(property.toString());
                  this.getPropertyResolver().setValue(scope, property, value);
               } else if (!(base instanceof List) && !base.getClass().isArray()) {
                  this.getPropertyResolver().setValue(base, property, value);
               } else {
                  this.getPropertyResolver().setValue(base, Integer.parseInt(property.toString()), value);
               }

            } catch (PropertyNotFoundException var6) {
               throw new javax.el.PropertyNotFoundException(var6.getMessage(), var6.getCause());
            } catch (EvaluationException var7) {
               throw new ELException(var7.getMessage(), var7.getCause());
            }
         }
      }

      private Map resolveScope(String var) {
         ExternalContext ext = LegacyELContext.this.faces.getExternalContext();
         Map map = ext.getRequestMap();
         if (!map.containsKey(var)) {
            map = ext.getSessionMap();
            if (!map.containsKey(var)) {
               map = ext.getApplicationMap();
               if (!map.containsKey(var)) {
                  map = ext.getRequestMap();
               }
            }
         }

         return map;
      }

      // $FF: synthetic method
      LegacyELResolver(Object x1) {
         this();
      }
   }
}
