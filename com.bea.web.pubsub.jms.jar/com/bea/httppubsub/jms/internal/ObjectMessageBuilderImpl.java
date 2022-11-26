package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.ObjectMessageBuilder;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.bayeux.messages.EventMessageImpl;
import com.bea.httppubsub.internal.ChannelId;
import com.bea.httppubsub.jms.utils.Encoder;
import com.bea.httppubsub.util.StringUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public class ObjectMessageBuilderImpl implements ObjectMessageBuilder {
   public ObjectMessage build(Session session, String channel, String data) throws JMSException {
      if (session == null) {
         throw new IllegalArgumentException("JMS Session cannot be null.");
      } else if (StringUtils.isEmpty(channel)) {
         throw new IllegalArgumentException("Target channel cannot be empty.");
      } else {
         EventMessageImpl em = new EventMessageImpl();
         em.setPayLoad(data);
         em.setChannel(channel);
         ObjectMessage message = session.createObjectMessage();
         message.setObject(new DeliverEventMessage(em));
         Set properties = getPossibleMessageProperties(channel);
         Iterator var7 = properties.iterator();

         while(var7.hasNext()) {
            String property = (String)var7.next();
            message.setBooleanProperty(Encoder.encode(property), true);
         }

         return message;
      }
   }

   static Set getPossibleMessageProperties(String channelPattern) {
      Set result = new HashSet();
      ChannelId cid = ChannelId.newInstance(channelPattern);
      if (!cid.isWild()) {
         result.add(channelPattern);
      }

      List segments = ChannelId.newInstance(channelPattern).getSegments();
      if (segments.size() > 0) {
         result.add("/**");
      }

      if (segments.size() == 1 && cid.isWild()) {
         result.add("/*");
      }

      for(int i = 0; i < segments.size(); ++i) {
         if (i < segments.size() - 2) {
            result.add(generateChannelName(segments, i) + "/**");
         } else if (i == segments.size() - 2) {
            result.add(generateChannelName(segments, i) + "/*");
            result.add(generateChannelName(segments, i) + "/**");
         }
      }

      return result;
   }

   private static String generateChannelName(List segments, int index) {
      StringBuilder sb = new StringBuilder("/");

      for(int i = 0; i <= index; ++i) {
         sb.append((String)segments.get(i));
         if (i != index) {
            sb.append("/");
         }
      }

      return sb.toString();
   }
}
