package com.sun.faces.flow;

import com.sun.faces.facelets.util.ReflectionUtil;
import com.sun.faces.util.FacesLogger;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.MethodCallNode;
import javax.faces.flow.Parameter;

public class MethodCallNodeImpl extends MethodCallNode implements Serializable {
   private static final long serialVersionUID = -5400138716176841428L;
   private final String id;
   private static final Logger LOGGER;
   private MethodExpression methodExpression;
   private ValueExpression outcome;
   private List _parameters;
   private List parameters;

   public MethodCallNodeImpl(String id) {
      this.id = id;
      this._parameters = new CopyOnWriteArrayList();
   }

   public MethodCallNodeImpl(FacesContext context, String id, String methodExpressionString, String defaultOutcomeString, List parametersFromConfig) {
      this(id);
      if (null != parametersFromConfig) {
         this._parameters.addAll(parametersFromConfig);
      }

      this.parameters = Collections.unmodifiableList(this._parameters);
      ExpressionFactory ef = context.getApplication().getExpressionFactory();
      Class[] paramTypes = new Class[0];
      if (0 < this.parameters.size()) {
         paramTypes = new Class[this.parameters.size()];
         int i = 0;

         for(Iterator var9 = this.parameters.iterator(); var9.hasNext(); ++i) {
            Parameter cur = (Parameter)var9.next();
            if (null != cur.getName()) {
               try {
                  paramTypes[i] = ReflectionUtil.forName(cur.getName());
               } catch (ClassNotFoundException var12) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, "parameter " + cur.getName() + "incorrect type", var12);
                  }

                  paramTypes[i] = null;
               }
            } else {
               paramTypes[i] = String.class;
            }
         }
      }

      ELContext elContext = context.getELContext();
      this.methodExpression = ef.createMethodExpression(elContext, methodExpressionString, (Class)null, paramTypes);
      if (null != defaultOutcomeString) {
         this.outcome = ef.createValueExpression(elContext, defaultOutcomeString, Object.class);
      }

   }

   public String getId() {
      return this.id;
   }

   public List getParameters() {
      return this.parameters;
   }

   public List _getParameters() {
      if (null == this.parameters) {
         this.parameters = Collections.unmodifiableList(this._parameters);
      }

      return this._parameters;
   }

   public MethodExpression getMethodExpression() {
      return this.methodExpression;
   }

   public void setMethodExpression(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public ValueExpression getOutcome() {
      return this.outcome;
   }

   public void setOutcome(ValueExpression outcome) {
      this.outcome = outcome;
   }

   static {
      LOGGER = FacesLogger.FLOW.getLogger();
   }
}
