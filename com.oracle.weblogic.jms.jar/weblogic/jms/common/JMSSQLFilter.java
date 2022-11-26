package weblogic.jms.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.messaging.common.CompiledSQLExpression;
import weblogic.messaging.common.SQLFilter;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Filter;
import weblogic.messaging.kernel.InvalidExpressionException;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Sink;
import weblogic.utils.expressions.ExpressionEvaluationException;
import weblogic.utils.expressions.ExpressionParser;
import weblogic.utils.expressions.ExpressionParserException;
import weblogic.utils.expressions.VariableBinder;

public class JMSSQLFilter extends SQLFilter {
   private static boolean enable1213NoLocalSemanticForDuraSubRestrictedClientID = Boolean.getBoolean("weblogic.jms.Enable1213NoLocalSemanticForDuraSubRestrictedClientID");
   private final Map indexedSubscribers = new HashMap();
   private final Map filteredForwardedIndexedSubs = new HashMap();
   private final Set filteredForwardedSubs = new HashSet();
   private final Map filteredForwardedAndMoreSubs = new HashMap();

   public JMSSQLFilter(Kernel kernel) {
      super(kernel, JMSVariableBinder.THE_ONE);
   }

   public Expression createExpression(Object exp) throws KernelException {
      try {
         JMSSQLExpression expression = (JMSSQLExpression)exp;
         return expression.isNull() ? null : new Exp(this.variableBinder, expression);
      } catch (ClassCastException var3) {
         throw new InvalidExpressionException("Wrong class: " + exp.getClass().getName());
      }
   }

   public void subscribe(Sink sink, Expression exp) {
      this.lock.lockWrite();

      try {
         if (exp == null) {
            this.subscribers.put(sink, (Object)null);
         } else {
            Exp jmsExp = (Exp)exp;
            String selectorId = jmsExp.getSelectorID();
            if (jmsExp.isJMSWLDDForwarded()) {
               this.filteredForwardedSubs.add(sink);
            } else if (jmsExp.isJMSWLDDForwardedAndMore()) {
               String ffSelectorId = jmsExp.getfilteredForwardedSelectorID();
               if (ffSelectorId != null) {
                  this.addIndexedSubscriber(sink, ffSelectorId, true);
               } else {
                  this.filteredForwardedAndMoreSubs.put(sink, exp);
               }
            } else if (selectorId != null) {
               this.addIndexedSubscriber(sink, selectorId, false);
            } else {
               this.subscribers.put(sink, jmsExp);
            }
         }
      } finally {
         this.lock.unlockWrite();
      }

   }

   private void addIndexedSubscriber(Sink sink, String selectorId, boolean isFilteredForwarded) {
      assert this.lock.isLockedForWrite();

      Map indexedSubs = isFilteredForwarded ? this.filteredForwardedIndexedSubs : this.indexedSubscribers;
      Object object = indexedSubs.get(selectorId);
      if (object == null) {
         indexedSubs.put(selectorId, sink);
      } else if (object instanceof Set) {
         ((Set)object).add(sink);
      } else {
         Set keySet = new HashSet();
         keySet.add(object);
         keySet.add(sink);
         indexedSubs.put(selectorId, keySet);
      }

   }

   public void unsubscribe(Sink sink) {
      if (sink != null) {
         this.lock.lockWrite();

         try {
            if (this.subscribers.remove(sink) == null && !this.filteredForwardedSubs.remove(sink) && this.filteredForwardedAndMoreSubs.remove(sink) == null) {
               this.removeIndexedSubscriber(sink);
            }
         } finally {
            this.lock.unlockWrite();
         }

      }
   }

   private void removeIndexedSubscriber(Sink sink) {
      assert this.lock.isLockedForWrite();

      Iterator sinks = this.indexedSubscribers.values().iterator();

      Object next;
      do {
         if (!sinks.hasNext()) {
            Iterator filteredSinks = this.filteredForwardedIndexedSubs.values().iterator();

            Object next;
            do {
               if (!filteredSinks.hasNext()) {
                  return;
               }

               next = filteredSinks.next();
               if (next instanceof Set && ((Set)next).contains(sink)) {
                  Set foundSet = (Set)next;
                  foundSet.remove(sink);
                  if (foundSet.isEmpty()) {
                     filteredSinks.remove();
                  }

                  return;
               }
            } while(!next.equals(sink));

            filteredSinks.remove();
            return;
         }

         next = sinks.next();
         if (next instanceof Set && ((Set)next).contains(sink)) {
            Set foundSet = (Set)next;
            foundSet.remove(sink);
            if (foundSet.isEmpty()) {
               sinks.remove();
            }

            return;
         }
      } while(!next.equals(sink));

      sinks.remove();
   }

   public Collection match(MessageElement message) {
      ArrayList ret = new ArrayList();

      MessageImpl msgImpl;
      try {
         msgImpl = (MessageImpl)message.getMessage();
      } catch (ClassCastException var12) {
         return null;
      }

      this.lock.lockRead();

      try {
         if (!this.filteredForwardedSubs.isEmpty() && !msgImpl.getDDForwarded()) {
            ret.addAll(this.filteredForwardedSubs);
         }

         Iterator props;
         Map.Entry entry;
         if (!this.filteredForwardedAndMoreSubs.isEmpty() && !msgImpl.getDDForwarded()) {
            props = this.filteredForwardedAndMoreSubs.entrySet().iterator();

            while(props.hasNext()) {
               entry = (Map.Entry)props.next();
               if (((CompiledSQLExpression)entry.getValue()).evaluate(message)) {
                  ret.add(entry.getKey());
               }
            }
         }

         Object subscribers;
         if (!this.filteredForwardedIndexedSubs.isEmpty() && !msgImpl.getDDForwarded()) {
            try {
               props = msgImpl.getPropertyNameCollection().iterator();

               while(props.hasNext()) {
                  subscribers = this.filteredForwardedIndexedSubs.get(props.next());
                  if (subscribers != null) {
                     if (subscribers instanceof Set) {
                        ret.addAll((Set)subscribers);
                     } else {
                        ret.add(subscribers);
                     }
                  }
               }
            } catch (javax.jms.JMSException var13) {
               entry = null;
               return entry;
            }
         }

         if (!this.indexedSubscribers.isEmpty()) {
            try {
               props = msgImpl.getPropertyNameCollection().iterator();

               while(props.hasNext()) {
                  subscribers = this.indexedSubscribers.get(props.next());
                  if (subscribers != null) {
                     if (subscribers instanceof Set) {
                        ret.addAll((Set)subscribers);
                     } else {
                        ret.add(subscribers);
                     }
                  }
               }
            } catch (javax.jms.JMSException var14) {
               entry = null;
               return entry;
            }
         }

         props = this.subscribers.entrySet().iterator();

         while(props.hasNext()) {
            entry = (Map.Entry)props.next();
            CompiledSQLExpression exp = (CompiledSQLExpression)entry.getValue();
            if (exp == null || exp.evaluate(message)) {
               ret.add(entry.getKey());
            }
         }

         ArrayList var17 = ret;
         return var17;
      } finally {
         this.lock.unlockRead();
      }
   }

   private final class Exp implements CompiledSQLExpression {
      private weblogic.utils.expressions.Expression wlExp;
      private boolean noLocal;
      private boolean notForwarded;
      private JMSID connectionId;
      private JMSID sessionId;
      private String clientId;
      private int clientIdPolicy = 0;

      Exp(VariableBinder binder, JMSSQLExpression exp) throws KernelException {
         this.noLocal = exp.isNoLocal();
         this.notForwarded = exp.isNotForwarded();
         this.connectionId = exp.getConnectionId();
         this.sessionId = exp.getSessionId();
         this.clientId = exp.getClientId();
         this.clientIdPolicy = exp.getClientIdPolicy();
         String selectorString = exp.getSelector();
         if (selectorString != null) {
            try {
               ExpressionParser parser = new ExpressionParser();
               this.wlExp = parser.parse(selectorString, binder);
            } catch (ExpressionParserException var6) {
               throw new InvalidExpressionException(selectorString, var6);
            }
         }

      }

      public Filter getFilter() {
         return JMSSQLFilter.this;
      }

      public boolean evaluate(MessageElement msg) {
         MessageImpl msgImpl = (MessageImpl)msg.getMessage();
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("JMSSQLFilter.evaluate(msg=" + msg + "), this.connectionId=" + this.connectionId + ", msg.connectionId=" + msgImpl.getConnectionId() + ", this.sessionId=" + this.sessionId + ", msg.sessionId=" + msgImpl.getSessionId());
         }

         if (this.noLocal) {
            boolean restrictedClientIdPolicyAndLocal;
            boolean unrestrictedClientIdPolicyAndLocal;
            if (!JMSSQLFilter.enable1213NoLocalSemanticForDuraSubRestrictedClientID) {
               restrictedClientIdPolicyAndLocal = msgImpl.getConnectionId() != null && this.connectionId != null && msgImpl.getConnectionId().equals(this.connectionId);
               if (restrictedClientIdPolicyAndLocal && msgImpl.getSessionId() != null && this.sessionId != null) {
                  restrictedClientIdPolicyAndLocal = msgImpl.getSessionId().equals(this.sessionId);
               }

               unrestrictedClientIdPolicyAndLocal = msgImpl.getClientId() != null && this.clientId != null && msgImpl.getClientId().equals(this.clientId);
               if (restrictedClientIdPolicyAndLocal || unrestrictedClientIdPolicyAndLocal) {
                  return false;
               }
            } else {
               restrictedClientIdPolicyAndLocal = msgImpl.getConnectionId() != null && this.connectionId != null && msgImpl.getConnectionId().equals(this.connectionId);
               if (restrictedClientIdPolicyAndLocal && msgImpl.getSessionId() != null && this.sessionId != null) {
                  restrictedClientIdPolicyAndLocal = msgImpl.getSessionId().equals(this.sessionId);
               }

               unrestrictedClientIdPolicyAndLocal = this.clientIdPolicy != 0 && msgImpl.getClientId() != null && this.clientId != null && msgImpl.getClientId().equals(this.clientId);
               if (restrictedClientIdPolicyAndLocal || unrestrictedClientIdPolicyAndLocal) {
                  return false;
               }
            }
         }

         if (this.notForwarded && msgImpl.getDDForwarded()) {
            return false;
         } else if (this.wlExp != null) {
            try {
               return this.wlExp.evaluate(msg);
            } catch (ExpressionEvaluationException var5) {
               return false;
            }
         } else {
            return true;
         }
      }

      String getSelectorID() {
         return this.wlExp == null ? null : this.wlExp.getSelectorID();
      }

      String getfilteredForwardedSelectorID() {
         return this.wlExp == null ? null : this.wlExp.getfilteredForwardedSelectorID();
      }

      boolean isJMSWLDDForwarded() {
         if (this.wlExp == null) {
            return false;
         } else {
            String forwardedPropName = this.wlExp.getStandardForwarderID();
            return forwardedPropName != null && forwardedPropName.equals("JMS_WL_DDForwarded");
         }
      }

      boolean isJMSWLDDForwardedAndMore() {
         if (this.wlExp == null) {
            return false;
         } else {
            String forwardedPropName = this.wlExp.getComplexForwarderID();
            return forwardedPropName != null && forwardedPropName.equals("JMS_WL_DDForwarded");
         }
      }
   }
}
