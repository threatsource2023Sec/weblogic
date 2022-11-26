package weblogic.management.rest.lib.bean.utils;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.utils.MessageUtil;

public class MessageUtils extends MessageUtil {
   public static BeanTextTextFormatter beanFormatter(HttpServletRequest request) {
      return beanFormatter(getLocale(request));
   }

   public static BeanTextTextFormatter beanFormatter(Locale locale) {
      return locale != null ? BeanTextTextFormatter.getInstance(locale) : BeanTextTextFormatter.getInstance();
   }
}
