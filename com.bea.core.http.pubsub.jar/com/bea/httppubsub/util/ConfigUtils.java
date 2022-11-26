package com.bea.httppubsub.util;

import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.ChannelConstraintBean;
import com.bea.httppubsub.descriptor.JmsHandlerBean;
import com.bea.httppubsub.descriptor.JmsHandlerMappingBean;
import com.bea.httppubsub.descriptor.ServerConfigBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletContext;

public final class ConfigUtils {
   private ConfigUtils() {
   }

   public static boolean isJmsHandlerEnable(WeblogicPubsubBean configuration) {
      boolean result = false;
      ChannelBean[] channelBeans = configuration.getChannels();
      if (channelBeans != null && channelBeans.length > 0) {
         ChannelBean[] var3 = channelBeans;
         int var4 = channelBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ChannelBean channelBean = var3[var5];
            if (StringUtils.isNotEmpty(channelBean.getJmsHandlerName())) {
               result = true;
               break;
            }
         }
      }

      return result;
   }

   public static boolean isPublishWithoutConnectAllowed(WeblogicPubsubBean configuration) {
      boolean result = false;
      ServerConfigBean serverConfigBean = configuration.getServerConfig();
      if (serverConfigBean != null) {
         result = serverConfigBean.isPublishWithoutConnectAllowed();
      }

      return result;
   }

   public static boolean isChannelConstraintEnable(WeblogicPubsubBean configuration) {
      ChannelConstraintBean[] ccbs = configuration.getChannelConstraints();
      return ccbs != null && ccbs.length > 0;
   }

   public static String getPubSubServerName(WeblogicPubsubBean configuration, ServletContext servletContext) {
      String name = null;
      if (configuration.getServerConfig() != null) {
         name = configuration.getServerConfig().getName();
      }

      if (name == null) {
         name = servletContext.getContextPath();
      }

      return name;
   }

   public static Map getJmsHandlerMappings(WeblogicPubsubBean configuration) {
      Map result = new LinkedHashMap();
      JmsHandlerMappingBean[] mappingBeans = configuration.getJmsHandlerMappings();
      if (mappingBeans != null && mappingBeans.length != 0) {
         JmsHandlerMappingBean[] var3 = mappingBeans;
         int var4 = mappingBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            JmsHandlerMappingBean mappingBean = var3[var5];
            String jmsHandlerName = mappingBean.getJmsHandlerName();
            JmsHandlerBean jmsHandlerBean = mappingBean.getJmsHandler();
            result.put(jmsHandlerName, jmsHandlerBean);
         }

         return result;
      } else {
         return result;
      }
   }
}
