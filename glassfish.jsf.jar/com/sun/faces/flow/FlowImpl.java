package com.sun.faces.flow;

import com.sun.faces.util.Util;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.FlowNode;
import javax.faces.flow.MethodCallNode;
import javax.faces.flow.ViewNode;
import javax.faces.lifecycle.ClientWindow;

public class FlowImpl extends Flow implements Serializable {
   private static final long serialVersionUID = 5287030395068302998L;
   public static final Flow SYNTHESIZED_RETURN_CASE_FLOW = new FlowImpl("javax.faces.flow.NullFlow");
   public static final Flow ABANDONED_FLOW = new FlowImpl("javax.faces.flow.AbandonedFlow");
   private String id;
   private String definingDocumentId;
   private String startNodeId;
   private final ConcurrentHashMap _navigationCases;
   private final Map navigationCases;
   private final CopyOnWriteArrayList _views;
   private final List views;
   private final CopyOnWriteArrayList _methodCalls;
   private final List methodCalls;
   private final ConcurrentHashMap _inboundParameters;
   private final Map inboundParameters;
   private final ConcurrentHashMap _returns;
   private final Map returns;
   private final ConcurrentHashMap _switches;
   private final Map switches;
   private final ConcurrentHashMap _facesFlowCalls;
   private final Map facesFlowCalls;
   private final ConcurrentHashMap _facesFlowCallsByTargetFlowId;
   private MethodExpression initializer;
   private MethodExpression finalizer;
   private boolean hasBeenInitialized = false;

   public FlowImpl() {
      this._inboundParameters = new ConcurrentHashMap();
      this.inboundParameters = Collections.unmodifiableMap(this._inboundParameters);
      this._returns = new ConcurrentHashMap();
      this.returns = Collections.unmodifiableMap(this._returns);
      this._switches = new ConcurrentHashMap();
      this.switches = Collections.unmodifiableMap(this._switches);
      this._facesFlowCalls = new ConcurrentHashMap();
      this.facesFlowCalls = Collections.unmodifiableMap(this._facesFlowCalls);
      this._facesFlowCallsByTargetFlowId = new ConcurrentHashMap();
      this._views = new CopyOnWriteArrayList();
      this.views = Collections.unmodifiableList(this._views);
      this._navigationCases = new ConcurrentHashMap();
      this.navigationCases = Collections.unmodifiableMap(this._navigationCases);
      this._methodCalls = new CopyOnWriteArrayList();
      this.methodCalls = Collections.unmodifiableList(this._methodCalls);
   }

   private FlowImpl(String id) {
      this.id = id;
      this.definingDocumentId = null;
      this.startNodeId = null;
      this._navigationCases = null;
      this.navigationCases = null;
      this._views = null;
      this.views = null;
      this._methodCalls = null;
      this.methodCalls = null;
      this._inboundParameters = null;
      this.inboundParameters = null;
      this._returns = null;
      this.returns = null;
      this._switches = null;
      this.switches = null;
      this._facesFlowCalls = null;
      this.facesFlowCalls = null;
      this._facesFlowCallsByTargetFlowId = null;
      this.initializer = null;
      this.finalizer = null;
      this.hasBeenInitialized = true;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         Flow other = (Flow)obj;
         if (this.id == null) {
            if (other.getId() != null) {
               return false;
            }
         } else if (!this.id.equals(other.getId())) {
            return false;
         }

         label76: {
            if (this.startNodeId == null) {
               if (other.getStartNodeId() == null) {
                  break label76;
               }
            } else if (this.startNodeId.equals(other.getStartNodeId())) {
               break label76;
            }

            return false;
         }

         if (this._views == other.getViews() || this._views != null && this._views.equals(other.getViews())) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (null != context) {
               if (this._returns != other.getReturns() && (this._returns == null || !this._returns.equals(other.getReturns()))) {
                  return false;
               } else if (this.initializer == other.getInitializer() || this.initializer != null && this.initializer.equals(other.getInitializer())) {
                  if (this.finalizer == other.getFinalizer() || this.finalizer != null && this.finalizer.equals(other.getFinalizer())) {
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int hash = 3;
      hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
      hash = 59 * hash + (this.startNodeId != null ? this.startNodeId.hashCode() : 0);
      hash = 59 * hash + (this._views != null ? this._views.hashCode() : 0);
      hash = 59 * hash + (this._returns != null ? this._returns.hashCode() : 0);
      hash = 59 * hash + (this.initializer != null ? this.initializer.hashCode() : 0);
      hash = 59 * hash + (this.finalizer != null ? this.finalizer.hashCode() : 0);
      return hash;
   }

   public String getId() {
      return this.id;
   }

   public String getDefiningDocumentId() {
      return this.definingDocumentId;
   }

   public void setId(String definingDocumentId, String id) {
      Util.notNull("definingDocumentId", definingDocumentId);
      Util.notNull("flowId", id);
      this.id = id;
      this.definingDocumentId = definingDocumentId;
   }

   public String getStartNodeId() {
      return this.startNodeId;
   }

   public void setStartNodeId(String defaultNodeId) {
      this.startNodeId = defaultNodeId;
   }

   public MethodExpression getFinalizer() {
      return this.finalizer;
   }

   public void setFinalizer(MethodExpression finalizer) {
      this.finalizer = finalizer;
   }

   public MethodExpression getInitializer() {
      return this.initializer;
   }

   public void setInitializer(MethodExpression initializer) {
      this.initializer = initializer;
   }

   public Map getInboundParameters() {
      return this.inboundParameters;
   }

   public Map _getInboundParameters() {
      return this._inboundParameters;
   }

   public List getViews() {
      return this.views;
   }

   public List _getViews() {
      return this._views;
   }

   public Map getReturns() {
      return this.returns;
   }

   public Map _getReturns() {
      return this._returns;
   }

   public Map getSwitches() {
      return this.switches;
   }

   public Map _getSwitches() {
      return this._switches;
   }

   public Map getFlowCalls() {
      return this.facesFlowCalls;
   }

   public Map _getFlowCalls() {
      return this._facesFlowCalls;
   }

   public Map getNavigationCases() {
      return this.navigationCases;
   }

   public Map _getNavigationCases() {
      return this._navigationCases;
   }

   public FlowCallNode getFlowCall(Flow targetFlow) {
      String targetFlowId = targetFlow.getId();
      if (!this.hasBeenInitialized) {
         FacesContext context = FacesContext.getCurrentInstance();
         this.init(context);
      }

      FlowCallNode result = (FlowCallNode)this._facesFlowCallsByTargetFlowId.get(targetFlowId);
      return result;
   }

   public List getMethodCalls() {
      return this.methodCalls;
   }

   public List _getMethodCalls() {
      return this._methodCalls;
   }

   public FlowNode getNode(String nodeId) {
      List myViews = this.getViews();
      FlowNode result = null;
      if (null != myViews) {
         Iterator var4 = myViews.iterator();

         while(var4.hasNext()) {
            ViewNode cur = (ViewNode)var4.next();
            if (nodeId.equals(cur.getId())) {
               result = cur;
               break;
            }
         }
      }

      Map myReturns;
      if (null == result) {
         myReturns = this.getSwitches();
         result = (FlowNode)myReturns.get(nodeId);
      }

      if (null == result) {
         List myMethods = this.getMethodCalls();
         Iterator var9 = myMethods.iterator();

         while(var9.hasNext()) {
            MethodCallNode cur = (MethodCallNode)var9.next();
            if (nodeId.equals(cur.getId())) {
               result = cur;
               break;
            }
         }
      }

      if (null == result) {
         myReturns = this.getFlowCalls();
         result = (FlowNode)myReturns.get(nodeId);
      }

      if (null == result) {
         myReturns = this.getReturns();
         result = (FlowNode)myReturns.get(nodeId);
      }

      return (FlowNode)result;
   }

   public String getClientWindowFlowId(ClientWindow curWindow) {
      String result = null;
      result = curWindow.getId() + "_" + this.getId();
      return result;
   }

   public void init(FacesContext context) {
      if (!this.hasBeenInitialized) {
         this.hasBeenInitialized = true;
         FlowCallNode curNode = null;
         String curTargetFlowId = null;
         Iterator var4 = this._facesFlowCalls.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry cur = (Map.Entry)var4.next();
            curNode = (FlowCallNode)cur.getValue();
            curTargetFlowId = curNode.getCalledFlowId(context);
            this._facesFlowCallsByTargetFlowId.put(curTargetFlowId, curNode);
         }

      }
   }
}
