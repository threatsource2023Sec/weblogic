package net.shibboleth.utilities.java.support.net;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CookieManager extends AbstractInitializableComponent {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(CookieManager.class);
   @Nullable
   private String cookiePath;
   @Nullable
   private String cookieDomain;
   @NonnullAfterInit
   private HttpServletRequest httpRequest;
   @NonnullAfterInit
   private HttpServletResponse httpResponse;
   private boolean secure = true;
   private boolean httpOnly = true;
   private int maxAge = -1;

   public void setCookiePath(@Nullable String path) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.cookiePath = StringSupport.trimOrNull(path);
   }

   public void setCookieDomain(@Nullable String domain) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.cookieDomain = StringSupport.trimOrNull(domain);
   }

   public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpRequest = (HttpServletRequest)Constraint.isNotNull(request, "HttpServletRequest cannot be null");
   }

   public void setHttpServletResponse(@Nonnull HttpServletResponse response) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpResponse = (HttpServletResponse)Constraint.isNotNull(response, "HttpServletResponse cannot be null");
   }

   public void setSecure(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.secure = flag;
   }

   public void setHttpOnly(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpOnly = flag;
   }

   public void setMaxAge(int age) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.maxAge = age;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.httpRequest != null && this.httpResponse != null) {
         if (!this.secure) {
            this.log.warn("Use of secure property is strongly advised");
         }

      } else {
         throw new ComponentInitializationException("Servlet request and response must be set");
      }
   }

   @Nullable
   public void addCookie(@Nonnull @NotEmpty String name, @Nonnull @NotEmpty String value) {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      Cookie cookie = new Cookie(name, value);
      cookie.setPath(this.cookiePath != null ? this.cookiePath : this.contextPathToCookiePath());
      if (this.cookieDomain != null) {
         cookie.setDomain(this.cookieDomain);
      }

      cookie.setSecure(this.secure);
      cookie.setHttpOnly(this.httpOnly);
      cookie.setMaxAge(this.maxAge);
      this.httpResponse.addCookie(cookie);
   }

   @Nullable
   public void unsetCookie(@Nonnull @NotEmpty String name) {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      Cookie cookie = new Cookie(name, (String)null);
      cookie.setPath(this.cookiePath != null ? this.cookiePath : this.contextPathToCookiePath());
      if (this.cookieDomain != null) {
         cookie.setDomain(this.cookieDomain);
      }

      cookie.setSecure(this.secure);
      cookie.setHttpOnly(this.httpOnly);
      cookie.setMaxAge(0);
      this.httpResponse.addCookie(cookie);
   }

   public boolean cookieHasValue(@Nonnull @NotEmpty String name, @Nonnull @NotEmpty String expectedValue) {
      String realValue = this.getCookieValue(name, (String)null);
      return realValue == null ? false : realValue.equals(expectedValue);
   }

   @Nullable
   public String getCookieValue(@Nonnull @NotEmpty String name, @Nullable String defValue) {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      Cookie[] cookies = this.httpRequest.getCookies();
      if (cookies != null) {
         Cookie[] arr$ = cookies;
         int len$ = cookies.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Cookie cookie = arr$[i$];
            if (cookie.getName().equals(name)) {
               return cookie.getValue();
            }
         }
      }

      return defValue;
   }

   @Nonnull
   @NotEmpty
   private String contextPathToCookiePath() {
      return "".equals(this.httpRequest.getContextPath()) ? "/" : this.httpRequest.getContextPath();
   }
}
