package org.jboss.weld.servlet.spi.helpers;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;

public class RegexHttpContextActivationFilter implements HttpContextActivationFilter {
   private final Pattern pattern;

   public RegexHttpContextActivationFilter(Pattern pattern) {
      this.pattern = pattern;
   }

   public RegexHttpContextActivationFilter(String regex) {
      this.pattern = Pattern.compile(regex);
   }

   public boolean accepts(HttpServletRequest request) {
      String path = request.getRequestURI().substring(request.getContextPath().length());
      return this.pattern.matcher(path).matches();
   }

   public void cleanup() {
   }
}
