package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Auditable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Singleton;

@Singleton
public class AuditableCustomizer {
   public static final String CREATED_ON = "created-on";
   public static final String UPDATED_ON = "updated-on";
   public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";

   public Date getCreatedOnDate(Auditable auditable) {
      return date(auditable.getCreatedOn());
   }

   public Date getUpdatedOnDate(Auditable auditable) {
      return date(auditable.getUpdatedOn());
   }

   private static Date date(String dateString) {
      if (dateString != null) {
         try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            return dateFormat.parse(dateString);
         } catch (ParseException var2) {
            throw new RuntimeException(var2);
         }
      } else {
         return null;
      }
   }
}
