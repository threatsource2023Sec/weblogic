package weblogic.management.runtime;

import java.io.Serializable;

public class WebsocketErrorCount implements Serializable {
   private static final long serialVersionUID = 4017576727584978455L;
   private final String throwableClassName;
   private final Long count;

   public WebsocketErrorCount(String throwableClassName, Long count) {
      this.throwableClassName = throwableClassName;
      this.count = count;
   }

   public String getThrowableClassName() {
      return this.throwableClassName;
   }

   public Long getCount() {
      return this.count;
   }
}
