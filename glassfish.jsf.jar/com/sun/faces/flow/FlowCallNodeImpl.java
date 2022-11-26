package com.sun.faces.flow;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.Parameter;

public class FlowCallNodeImpl extends FlowCallNode implements Serializable {
   private static final long serialVersionUID = 543332738561754405L;
   private final String id;
   private final ValueExpression calledFlowIdVE;
   private final ValueExpression calledFlowDocumentIdVE;
   private Map _outboundParameters;
   private Map outboundParameters;

   public FlowCallNodeImpl(String id, String calledFlowDocumentId, String calledFlowId, List outboundParametersFromConfig) {
      FacesContext context = FacesContext.getCurrentInstance();
      this.id = id;
      if (null != calledFlowDocumentId) {
         this.calledFlowDocumentIdVE = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), calledFlowDocumentId, String.class);
      } else {
         this.calledFlowDocumentIdVE = null;
      }

      if (null != calledFlowId) {
         this.calledFlowIdVE = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), calledFlowId, String.class);
      } else {
         this.calledFlowIdVE = null;
      }

      this._outboundParameters = new ConcurrentHashMap();
      if (null != outboundParametersFromConfig) {
         Iterator var6 = outboundParametersFromConfig.iterator();

         while(var6.hasNext()) {
            Parameter cur = (Parameter)var6.next();
            this._outboundParameters.put(cur.getName(), cur);
         }
      }

      this.outboundParameters = Collections.unmodifiableMap(this._outboundParameters);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         FlowCallNodeImpl other;
         label56: {
            other = (FlowCallNodeImpl)obj;
            if (this.id == null) {
               if (other.id == null) {
                  break label56;
               }
            } else if (this.id.equals(other.id)) {
               break label56;
            }

            return false;
         }

         if (this.calledFlowIdVE != other.calledFlowIdVE && (this.calledFlowIdVE == null || !this.calledFlowIdVE.equals(other.calledFlowIdVE))) {
            return false;
         } else if (this.calledFlowDocumentIdVE == other.calledFlowDocumentIdVE || this.calledFlowDocumentIdVE != null && this.calledFlowDocumentIdVE.equals(other.calledFlowDocumentIdVE)) {
            return this._outboundParameters == other._outboundParameters || this._outboundParameters != null && this._outboundParameters.equals(other._outboundParameters);
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int hash = 3;
      hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
      hash = 59 * hash + (this.calledFlowIdVE != null ? this.calledFlowIdVE.hashCode() : 0);
      hash = 59 * hash + (this.calledFlowDocumentIdVE != null ? this.calledFlowDocumentIdVE.hashCode() : 0);
      hash = 59 * hash + (this._outboundParameters != null ? this._outboundParameters.hashCode() : 0);
      return hash;
   }

   public String getId() {
      return this.id;
   }

   public String getCalledFlowDocumentId(FacesContext context) {
      String result = null;
      if (null != this.calledFlowDocumentIdVE) {
         result = (String)this.calledFlowDocumentIdVE.getValue(context.getELContext());
      }

      return result;
   }

   public String getCalledFlowId(FacesContext context) {
      String result = null;
      if (null != this.calledFlowIdVE) {
         result = (String)this.calledFlowIdVE.getValue(context.getELContext());
      }

      return result;
   }

   public Map _getOutboundParameters() {
      return this._outboundParameters;
   }

   public Map getOutboundParameters() {
      return this._outboundParameters;
   }
}
