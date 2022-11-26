package org.apache.taglibs.standard.tag.common.core;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.resources.Resources;

public class SetSupport extends BodyTagSupport {
   protected Object value;
   protected boolean valueSpecified;
   protected Object target;
   protected String property;
   private String var;
   private int scope;
   private boolean scopeSpecified;

   public SetSupport() {
      this.init();
   }

   private void init() {
      this.value = this.var = null;
      this.scopeSpecified = this.valueSpecified = false;
      this.scope = 1;
   }

   public void release() {
      super.release();
      this.init();
   }

   public int doEndTag() throws JspException {
      Object result;
      if (this.value != null) {
         result = this.value;
      } else if (this.valueSpecified) {
         result = null;
      } else if (this.bodyContent != null && this.bodyContent.getString() != null) {
         result = this.bodyContent.getString().trim();
      } else {
         result = "";
      }

      if (this.var != null) {
         VariableMapper vm;
         if (result != null) {
            if (result instanceof ValueExpression) {
               if (this.scope != 1) {
                  throw new JspException(Resources.getMessage("SET_BAD_SCOPE_DEFERRED"));
               }

               vm = this.pageContext.getELContext().getVariableMapper();
               if (vm != null) {
                  vm.setVariable(this.var, (ValueExpression)result);
               }
            } else {
               if (this.scope == 1) {
                  vm = this.pageContext.getELContext().getVariableMapper();
                  if (vm != null) {
                     vm.setVariable(this.var, (ValueExpression)null);
                  }
               }

               this.pageContext.setAttribute(this.var, result, this.scope);
            }
         } else {
            if (this.scopeSpecified) {
               this.pageContext.removeAttribute(this.var, this.scope);
            } else {
               this.pageContext.removeAttribute(this.var);
            }

            if (this.scope == 1) {
               vm = this.pageContext.getELContext().getVariableMapper();
               if (vm != null) {
                  vm.setVariable(this.var, (ValueExpression)null);
               }
            }
         }
      } else {
         if (this.target == null) {
            throw new JspTagException();
         }

         if (this.target instanceof Map) {
            if (result == null) {
               ((Map)this.target).remove(this.property);
            } else {
               ((Map)this.target).put(this.property, result);
            }
         } else {
            try {
               PropertyDescriptor[] pd = Introspector.getBeanInfo(this.target.getClass()).getPropertyDescriptors();
               boolean succeeded = false;

               for(int i = 0; i < pd.length; ++i) {
                  if (pd[i].getName().equals(this.property)) {
                     Method m = pd[i].getWriteMethod();
                     if (m == null) {
                        throw new JspException(Resources.getMessage("SET_NO_SETTER_METHOD", (Object)this.property));
                     }

                     if (result != null) {
                        try {
                           m.invoke(this.target, this.convertToExpectedType(result, m.getParameterTypes()[0]));
                        } catch (ELException var7) {
                           throw new JspTagException(var7);
                        }
                     } else {
                        m.invoke(this.target, null);
                     }

                     succeeded = true;
                  }
               }

               if (!succeeded) {
                  throw new JspTagException(Resources.getMessage("SET_INVALID_PROPERTY", (Object)this.property));
               }
            } catch (IllegalAccessException var8) {
               throw new JspException(var8);
            } catch (IntrospectionException var9) {
               throw new JspException(var9);
            } catch (InvocationTargetException var10) {
               throw new JspException(var10);
            }
         }
      }

      return 6;
   }

   private Object convertToExpectedType(Object value, Class expectedType) {
      JspFactory jspFactory = JspFactory.getDefaultFactory();
      JspApplicationContext jspAppContext = jspFactory.getJspApplicationContext(this.pageContext.getServletContext());
      ExpressionFactory exprFactory = jspAppContext.getExpressionFactory();
      return exprFactory.coerceToType(value, expectedType);
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
      this.scopeSpecified = true;
   }
}
