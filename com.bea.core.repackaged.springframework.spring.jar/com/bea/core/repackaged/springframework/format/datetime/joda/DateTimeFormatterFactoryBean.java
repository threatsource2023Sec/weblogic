package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeFormatterFactoryBean extends DateTimeFormatterFactory implements FactoryBean, InitializingBean {
   @Nullable
   private DateTimeFormatter dateTimeFormatter;

   public void afterPropertiesSet() {
      this.dateTimeFormatter = this.createDateTimeFormatter();
   }

   @Nullable
   public DateTimeFormatter getObject() {
      return this.dateTimeFormatter;
   }

   public Class getObjectType() {
      return DateTimeFormatter.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
