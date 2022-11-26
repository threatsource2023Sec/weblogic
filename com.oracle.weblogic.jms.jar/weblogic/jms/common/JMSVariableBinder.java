package weblogic.jms.common;

import java.util.HashMap;
import weblogic.jms.extensions.WLMessage;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.runtime.MessageInfo;
import weblogic.utils.expressions.Expression;
import weblogic.utils.expressions.ExpressionEvaluationException;
import weblogic.utils.expressions.Variable;
import weblogic.utils.expressions.VariableBinder;
import weblogic.utils.expressions.Expression.Type;

public final class JMSVariableBinder implements VariableBinder {
   public static final JMSVariableBinder THE_ONE = new JMSVariableBinder();
   private static final HashMap VARIABLES = new HashMap();

   public Variable getVariable(String name) {
      Variable ret = (Variable)VARIABLES.get(name);
      return (Variable)(ret != null ? ret : new JMSPropertiesVariable(name));
   }

   protected static WLMessage msg(Object context) {
      return (WLMessage)element(context).getMessage();
   }

   protected static Message kmsg(Object context) {
      return element(context).getMessage();
   }

   protected static MessageElement element(Object context) {
      return (MessageElement)context;
   }

   protected static ExpressionEvaluationException wrapException(javax.jms.JMSException jmse) throws ExpressionEvaluationException {
      ExpressionEvaluationException eee = new ExpressionEvaluationException("Failed to bind variable");
      eee.initCause(jmse);
      throw eee;
   }

   static {
      VARIABLES.put("JMSDeliveryMode", new JMSDeliveryModeVariable());
      VARIABLES.put("JMSMessageID", new JMSMessageIDVariable());
      VARIABLES.put("JMSMessageId", new JMSMessageIDVariable());
      VARIABLES.put("JMSTimestamp", new JMSTimestampVariable());
      VARIABLES.put("JMSCorrelationID", new JMSCorrelationIDVariable());
      VARIABLES.put("JMSType", new JMSTypeVariable());
      VARIABLES.put("JMSPriority", new JMSPriorityVariable());
      VARIABLES.put("JMSExpiration", new JMSExpirationVariable());
      VARIABLES.put("JMSRedelivered", new JMSRedeliveredVariable());
      VARIABLES.put("JMSDeliveryTime", new JMSDeliveryTimeVariable());
      VARIABLES.put("JMS_BEA_DeliveryTime", new JMSDeliveryTimeVariable());
      VARIABLES.put("JMSRedeliveryLimit", new JMSRedeliveryLimitVariable());
      VARIABLES.put("JMS_BEA_RedeliveryLimit", new JMSRedeliveryLimitVariable());
      VARIABLES.put("JMS_BEA_Size", new JMS_BEA_SizeVariable());
      VARIABLES.put("JMS_BEA_UnitOfOrder", new JMS_BEA_UnitOfOrderVariable());
      VARIABLES.put("JMSXUserID", new JMSXUserIDVariable());
      VARIABLES.put("JMSXDeliveryCount", new JMSXDeliveryCountVariable());
      VARIABLES.put("JMS_BEA_State", new JMSBEAStateVariable());
      VARIABLES.put("JMS_WL_DDForwarded", new JMS_WL_DDForwardedVariable());
   }

   private static class JMS_WL_DDForwardedVariable implements Variable {
      private JMS_WL_DDForwardedVariable() {
      }

      public Object get(Object context) {
         return new Boolean(((WLMessage)JMSVariableBinder.element(context).getMessage()).getDDForwarded());
      }

      public Expression.Type getType() {
         return Type.BOOLEAN;
      }

      // $FF: synthetic method
      JMS_WL_DDForwardedVariable(Object x0) {
         this();
      }
   }

   private static class JMSBEAStateVariable implements Variable {
      private JMSBEAStateVariable() {
      }

      public Object get(Object context) {
         return MessageInfo.getStateString(JMSVariableBinder.element(context).getState());
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMSBEAStateVariable(Object x0) {
         this();
      }
   }

   private static class JMSXDeliveryCountVariable implements Variable {
      private JMSXDeliveryCountVariable() {
      }

      public Object get(Object context) {
         return new Integer(JMSVariableBinder.element(context).getDeliveryCount());
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMSXDeliveryCountVariable(Object x0) {
         this();
      }
   }

   private static class JMSXUserIDVariable implements Variable {
      private JMSXUserIDVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getStringProperty("JMSXUserID");
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMSXUserIDVariable(Object x0) {
         this();
      }
   }

   private static class JMS_BEA_UnitOfOrderVariable implements Variable {
      private JMS_BEA_UnitOfOrderVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getStringProperty("JMS_BEA_UnitOfOrder");
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMS_BEA_UnitOfOrderVariable(Object x0) {
         this();
      }
   }

   private static class JMS_BEA_SizeVariable implements Variable {
      private JMS_BEA_SizeVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return new Long(JMSVariableBinder.kmsg(context).size());
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMS_BEA_SizeVariable(Object x0) {
         this();
      }
   }

   private static class JMSRedeliveryLimitVariable implements Variable {
      private JMSRedeliveryLimitVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return new Integer(JMSVariableBinder.msg(context).getJMSRedeliveryLimit());
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMSRedeliveryLimitVariable(Object x0) {
         this();
      }
   }

   private static class JMSDeliveryTimeVariable implements Variable {
      private JMSDeliveryTimeVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return new Long(JMSVariableBinder.msg(context).getJMSDeliveryTime());
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMSDeliveryTimeVariable(Object x0) {
         this();
      }
   }

   private static class JMSRedeliveredVariable implements Variable {
      private JMSRedeliveredVariable() {
      }

      public Object get(Object context) {
         return JMSVariableBinder.element(context).getDeliveryCount() > 1;
      }

      public Expression.Type getType() {
         return Type.BOOLEAN;
      }

      // $FF: synthetic method
      JMSRedeliveredVariable(Object x0) {
         this();
      }
   }

   private static class JMSExpirationVariable implements Variable {
      private JMSExpirationVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return new Long(JMSVariableBinder.msg(context).getJMSExpiration());
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMSExpirationVariable(Object x0) {
         this();
      }
   }

   private static class JMSPriorityVariable implements Variable {
      private JMSPriorityVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return new Integer(JMSVariableBinder.msg(context).getJMSPriority());
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMSPriorityVariable(Object x0) {
         this();
      }
   }

   private static class JMSTypeVariable implements Variable {
      private JMSTypeVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getJMSType();
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMSTypeVariable(Object x0) {
         this();
      }
   }

   private static class JMSCorrelationIDVariable implements Variable {
      private JMSCorrelationIDVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getJMSCorrelationID();
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMSCorrelationIDVariable(Object x0) {
         this();
      }
   }

   private static class JMSTimestampVariable implements Variable {
      private JMSTimestampVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return new Long(JMSVariableBinder.msg(context).getJMSTimestamp());
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      JMSTimestampVariable(Object x0) {
         this();
      }
   }

   private static class JMSMessageIDVariable implements Variable {
      private JMSMessageIDVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getJMSMessageID();
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMSMessageIDVariable(Object x0) {
         this();
      }
   }

   private static class JMSDeliveryModeVariable implements Variable {
      private JMSDeliveryModeVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getJMSDeliveryMode() == 2 ? "PERSISTENT" : "NON_PERSISTENT";
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      JMSDeliveryModeVariable(Object x0) {
         this();
      }
   }

   private static class JMSPropertiesVariable implements Variable {
      private final String key;

      private JMSPropertiesVariable(String key) {
         this.key = key;
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return JMSVariableBinder.msg(context).getObjectProperty(this.key);
         } catch (javax.jms.JMSException var3) {
            throw JMSVariableBinder.wrapException(var3);
         }
      }

      public Expression.Type getType() {
         return Type.ANY;
      }

      // $FF: synthetic method
      JMSPropertiesVariable(String x0, Object x1) {
         this(x0);
      }
   }
}
