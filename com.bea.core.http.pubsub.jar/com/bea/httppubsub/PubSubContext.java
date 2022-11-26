package com.bea.httppubsub;

import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.ChannelPersistenceManagerBuilder;
import javax.servlet.ServletContext;

public class PubSubContext {
   private final PubSubServer server;
   private final WeblogicPubsubBean config;
   private ServletContext context;
   private String contextPath;
   private ChannelPersistenceManagerBuilder cpmBuilder;

   public PubSubContext(PubSubServer server, WeblogicPubsubBean bean) {
      this.server = server;
      this.config = bean;
      ChannelBean[] chBeans = null;
      if (bean != null) {
         chBeans = bean.getChannels();
      } else {
         chBeans = new ChannelBean[0];
      }

      this.cpmBuilder = new ChannelPersistenceManagerBuilder(chBeans);
   }

   public PubSubServer getServer() {
      return this.server;
   }

   public WeblogicPubsubBean getConfig() {
      return this.config;
   }

   public ServletContext getServletContext() {
      return this.context;
   }

   public ChannelPersistenceManagerBuilder getChannelPersistManagerBuilder() {
      return this.cpmBuilder;
   }

   public void setServletContext(ServletContext context) {
      this.context = context;
   }

   public String getServletContextPath() {
      return this.contextPath;
   }

   public void setServletContextPath(String contextPath) {
      this.contextPath = contextPath;
   }
}
