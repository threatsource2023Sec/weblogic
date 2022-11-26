package com.oracle.weblogic.lifecycle.config.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.hub.api.Hub;

public abstract class LifecycleConfigService {
   public static final String CREATED_ON = "createdOn";
   public static final String UPDATED_ON = "updatedOn";
   @Configured("$instance")
   private String instanceId;
   @Configured("$type")
   private String type;
   @Configured
   private String createdOn;
   @Configured
   private String updatedOn;
   @Inject
   Hub hub;
   @Inject
   private ServiceLocator serviceLocator;
   @Inject
   LifecycleConfigManager lcm;
   static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");

   ServiceLocator getServiceLocator() {
      return this.serviceLocator;
   }

   public String getInstanceId() {
      return this.instanceId;
   }

   public String getType() {
      return this.type;
   }

   public String getParentInstanceId() {
      int i = this.instanceId.lastIndexOf(ConfigUtil.getSeparatorChar());
      return i < 0 ? null : this.instanceId.substring(0, i);
   }

   public String getCreatedOn() {
      return this.createdOn;
   }

   public String getUpdatedOn() {
      return this.updatedOn;
   }

   public Date getCreatedOnDate() {
      return this.getDate(this.createdOn);
   }

   public Date getUpdatedOnDate() {
      return this.getDate(this.updatedOn);
   }

   private Date getDate(String date) {
      if (date != null) {
         try {
            return ISO_DATE_FORMAT.parse(date);
         } catch (ParseException var3) {
         }
      }

      return null;
   }

   void add(String type, String instanceId, Map map) {
      this.lcm.add(type, instanceId, map);
   }

   void delete(String type, String instanceId) {
      this.lcm.delete(type, instanceId);
   }
}
