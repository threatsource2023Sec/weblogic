package weblogic.messaging.saf.internal;

import java.util.HashMap;
import javax.jms.JMSException;
import weblogic.jms.extensions.WLMessage;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.saf.SAFRequest;
import weblogic.utils.expressions.Expression;
import weblogic.utils.expressions.ExpressionEvaluationException;
import weblogic.utils.expressions.Variable;
import weblogic.utils.expressions.VariableBinder;
import weblogic.utils.expressions.Expression.Type;

final class SAFVariableBinder implements VariableBinder {
   public static final SAFVariableBinder THE_ONE = new SAFVariableBinder();
   private static final HashMap VARIABLES = new HashMap();

   public Variable getVariable(String name) {
      Variable ret = (Variable)VARIABLES.get(name);
      return (Variable)(ret != null ? ret : new JMSPropertiesVariable(name));
   }

   private static SAFRequest saf(Object context) {
      if (context instanceof SAFRequest) {
         return (SAFRequest)context;
      } else {
         return context instanceof MessageElement ? (SAFRequest)((MessageElement)context).getMessage() : null;
      }
   }

   private static WLMessage msg(Object context) {
      if (context instanceof SAFRequest) {
         Object o = ((SAFRequest)context).getPayload();
         if (o instanceof WLMessage) {
            return (WLMessage)o;
         }
      }

      return context instanceof MessageElement ? (WLMessage)((MessageElement)context).getMessage() : null;
   }

   private static Message kmsg(Object context) {
      if (context instanceof SAFRequest) {
         Object o = ((SAFRequest)context).getPayload();
         if (o instanceof Message) {
            return (Message)o;
         }
      }

      return context instanceof MessageElement ? ((MessageElement)context).getMessage() : null;
   }

   private static ExpressionEvaluationException wrapException(JMSException jmse) throws ExpressionEvaluationException {
      ExpressionEvaluationException eee = new ExpressionEvaluationException("Failed to bind variable");
      eee.initCause(jmse);
      throw eee;
   }

   static {
      VARIABLES.put("JMSDeliveryMode", new JMSDeliveryModeVariable());
      VARIABLES.put("JMSMessageID", new JMSMessageIDVariable());
      VARIABLES.put("JMSTimestamp", new JMSTimestampVariable());
      VARIABLES.put("JMSCorrelationID", new JMSCorrelationIDVariable());
      VARIABLES.put("JMSType", new JMSTypeVariable());
      VARIABLES.put("JMSPriority", new JMSPriorityVariable());
      VARIABLES.put("JMSExpiration", new JMSExpirationVariable());
      VARIABLES.put("JMSRedelivered", new JMSRedeliveredVariable());
      VARIABLES.put("JMSDeliveryTime", new JMSDeliveryTimeVariable());
      VARIABLES.put("JMSRedeliveryLimit", new JMSRedeliveryLimitVariable());
      VARIABLES.put("JMS_BEA_Size", new JMS_BEA_SizeVariable());
      VARIABLES.put("JMS_BEA_UnitOfOrder", new JMS_BEA_UnitOfOrderVariable());
      VARIABLES.put("SAFConversationName", new SAFConversationNameVariable());
      VARIABLES.put("SAFSequenceNumber", new SAFSequenceNumberVariable());
      VARIABLES.put("SAFExpiration", new SAFExpirationVariable());
      VARIABLES.put("SAFDeliveryMode", new SAFDeliveryModeVariable());
      VARIABLES.put("SAFTimestamp", new SAFTimestampVariable());
      VARIABLES.put("SAFMessageID", new SAFMessageIDVariable());
   }

   private static class SAFMessageIDVariable implements Variable {
      private SAFMessageIDVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return SAFVariableBinder.saf(context).getMessageId();
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      SAFMessageIDVariable(Object x0) {
         this();
      }
   }

   private static class SAFTimestampVariable implements Variable {
      private SAFTimestampVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return new Long(SAFVariableBinder.saf(context).getTimestamp());
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      SAFTimestampVariable(Object x0) {
         this();
      }
   }

   private static class SAFDeliveryModeVariable implements Variable {
      private SAFDeliveryModeVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return SAFVariableBinder.saf(context).getDeliveryMode() == 2 ? "PERSISTENT" : "NON_PERSISTENT";
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      SAFDeliveryModeVariable(Object x0) {
         this();
      }
   }

   private static class SAFExpirationVariable implements Variable {
      private SAFExpirationVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return SAFVariableBinder.saf(context).getTimeToLive() == -1L ? new Long(-1L) : new Long(SAFVariableBinder.saf(context).getTimestamp() + SAFVariableBinder.saf(context).getTimeToLive());
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      SAFExpirationVariable(Object x0) {
         this();
      }
   }

   private static class SAFSequenceNumberVariable implements Variable {
      private SAFSequenceNumberVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return new Long(SAFVariableBinder.saf(context).getSequenceNumber());
      }

      public Expression.Type getType() {
         return Type.NUMERIC;
      }

      // $FF: synthetic method
      SAFSequenceNumberVariable(Object x0) {
         this();
      }
   }

   private static class SAFConversationNameVariable implements Variable {
      private SAFConversationNameVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         return SAFVariableBinder.saf(context).getConversationName();
      }

      public Expression.Type getType() {
         return Type.STRING;
      }

      // $FF: synthetic method
      SAFConversationNameVariable(Object x0) {
         this();
      }
   }

   private static class JMS_BEA_UnitOfOrderVariable implements Variable {
      private JMS_BEA_UnitOfOrderVariable() {
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return SAFVariableBinder.msg(context).getStringProperty("JMS_BEA_UnitOfOrder");
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
         return new Long(SAFVariableBinder.kmsg(context).size());
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
            return new Integer(SAFVariableBinder.msg(context).getJMSRedeliveryLimit());
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return new Long(SAFVariableBinder.msg(context).getJMSDeliveryTime());
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return SAFVariableBinder.msg(context).getJMSRedelivered();
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
         }
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
            return new Long(SAFVariableBinder.msg(context).getJMSExpiration());
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return new Integer(SAFVariableBinder.msg(context).getJMSPriority());
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return SAFVariableBinder.msg(context).getJMSType();
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return SAFVariableBinder.msg(context).getJMSCorrelationID();
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return new Long(SAFVariableBinder.msg(context).getJMSTimestamp());
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return SAFVariableBinder.msg(context).getJMSMessageID();
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return SAFVariableBinder.msg(context).getJMSDeliveryMode() == 2 ? "PERSISTENT" : "NON_PERSISTENT";
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
            return SAFVariableBinder.msg(context).getObjectProperty(this.key);
         } catch (JMSException var3) {
            throw SAFVariableBinder.wrapException(var3);
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
