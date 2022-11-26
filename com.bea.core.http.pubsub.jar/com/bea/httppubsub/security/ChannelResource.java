package com.bea.httppubsub.security;

import java.security.InvalidParameterException;
import weblogic.security.service.ApplicationResource;
import weblogic.security.service.ResourceBase;
import weblogic.security.spi.Resource;

public class ChannelResource extends ResourceBase {
   private static final long serialVersionUID = 7377863464378294890L;
   private static final String[] KEYS = new String[]{"application", "context", "operation", "channel"};
   private static final Resource TOP = new ChannelResource((String)null, (String)null, (String)null, (String)null, true, (String)null);
   private String application;
   private String context;
   private String operation;
   private String channel;
   private boolean lastPathSegment;
   private String realOperation;

   private ChannelResource(String application, String context, String operation, String channel, boolean lastPathSegment, String realOperation) {
      this.lastPathSegment = true;
      this.application = application;
      this.context = context;
      this.operation = operation;
      this.realOperation = realOperation;
      this.channel = channel;
      if (channel == null || channel.startsWith("/") && !channel.endsWith("/")) {
         this.lastPathSegment = lastPathSegment;
         this.init(new String[]{application, context, operation, channel}, 0L);
      } else {
         throw new InvalidParameterException("Invalid channel name");
      }
   }

   public ChannelResource(String application, String context, String operation, String channel) {
      this(application, context, operation, channel, true, operation);
      if (context == null && (operation != null || channel != null)) {
         throw new InvalidParameterException("One must always supply a context for a ChannelResource with a supplied channel");
      }
   }

   public ChannelResource(String context, String operation, String channel) {
      this((String)null, context, operation, channel, true, operation);
   }

   public Resource getParentResource() {
      if (this.operation == null && this.realOperation != null) {
         this.parent = this.makeParent();
      }

      return super.getParentResource();
   }

   protected Resource makeParent() {
      if (this.application == null && this.context == null && this.operation == null) {
         return NO_PARENT;
      } else if (this.channel == null) {
         if (this.operation != null) {
            return new ChannelResource(this.application, this.context, (String)null, this.channel, this.lastPathSegment, this.realOperation);
         } else if (this.context != null) {
            return (Resource)(this.application == null ? TOP : new ChannelResource(this.application, (String)null, this.operation, this.channel, this.lastPathSegment, this.realOperation));
         } else {
            return new ApplicationResource(this.application, TOP);
         }
      } else if (this.operation != null) {
         return new ChannelResource(this.application, this.context, (String)null, this.channel, this.lastPathSegment, this.realOperation);
      } else if (this.channel.equals("/**")) {
         return new ChannelResource(this.application, this.context, this.realOperation, (String)null, this.lastPathSegment, this.realOperation);
      } else {
         int slash;
         if (this.channel.endsWith("/**")) {
            slash = this.channel.lastIndexOf(47, this.channel.length() - 4);
            return new ChannelResource(this.application, this.context, this.realOperation, this.channel.substring(0, slash + 1) + "**", false, this.realOperation);
         } else if (this.channel.endsWith("/*")) {
            return new ChannelResource(this.application, this.context, this.realOperation, this.channel + "*", this.lastPathSegment, this.realOperation);
         } else {
            slash = this.channel.lastIndexOf(47);
            return new ChannelResource(this.application, this.context, this.realOperation, this.channel.substring(0, slash + 1) + "*", this.lastPathSegment, this.realOperation);
         }
      }
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("channel") ? 2 : 1;
   }

   public String[] getKeys() {
      return KEYS;
   }

   public String getType() {
      return "<channel>";
   }
}
