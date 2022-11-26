package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import weblogic.security.SecurityLogger;
import weblogic.security.spi.ServletAuthenticationFilter;
import weblogic.utils.enumerations.EmptyEnumerator;

public class ServletAuthenticationFilterServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private ServletAuthenticationFilter[] providers;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityAtn");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityAtn"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         boolean debug = this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".init";
         if (debug) {
            this.logger.debug(method);
         }

         ServletAuthenticationFilterServiceConfig myconfig = (ServletAuthenticationFilterServiceConfig)config;
         this.auditService = (AuditService)dependentServices.getService(myconfig.getAuditServiceName());
         String[] names = myconfig.getServletAuthenticationFilterProviderNames();
         this.providers = new ServletAuthenticationFilter[names != null ? names.length : 0];

         for(int i = 0; i < this.providers.length; ++i) {
            this.providers[i] = (ServletAuthenticationFilter)dependentServices.getService(names[i]);
         }

         return new ServiceImpl();
      }
   }

   public void shutdown() {
   }

   private final class FilterConfigImpl implements FilterConfig {
      private final ServletContext ctx;

      public FilterConfigImpl(ServletContext ctx) {
         this.ctx = ctx;
      }

      public String getFilterName() {
         return null;
      }

      public ServletContext getServletContext() {
         return this.ctx;
      }

      public String getInitParameter(String key) {
         return null;
      }

      public Enumeration getInitParameterNames() {
         return new EmptyEnumerator();
      }
   }

   private final class ServiceImpl implements ServletAuthenticationFilterService {
      private ServiceImpl() {
      }

      public Filter[] getServletAuthenticationFilters(ServletContext ctx) throws ServletException {
         boolean debug = ServletAuthenticationFilterServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getServletAuthenticationFilters" : null;
         if (debug) {
            ServletAuthenticationFilterServiceImpl.this.logger.debug(method);
         }

         int numProviders = ServletAuthenticationFilterServiceImpl.this.providers.length;
         Filter[][] filtersByProvider = new Filter[numProviders][];
         int total = 0;

         for(int i = 0; i < numProviders; ++i) {
            filtersByProvider[i] = ServletAuthenticationFilterServiceImpl.this.providers[i].getServletAuthenticationFilters();
            if (filtersByProvider[i] != null) {
               total += filtersByProvider[i].length;
               if (debug) {
                  ServletAuthenticationFilterServiceImpl.this.logger.debug(method + "Provider[" + i + "] added " + filtersByProvider[i].length + " filters.");
               }
            } else if (debug) {
               ServletAuthenticationFilterServiceImpl.this.logger.debug(method + "Provider[" + i + "] added no filters.");
            }
         }

         if (total == 0) {
            if (debug) {
               ServletAuthenticationFilterServiceImpl.this.logger.debug(method + " No Servlet Authentication Filters were found");
            }

            return null;
         } else {
            Filter[] filters = new Filter[total];
            int cur = 0;

            try {
               for(int j = 0; j < filtersByProvider.length; ++j) {
                  if (filtersByProvider[j] != null) {
                     for(int k = 0; k < filtersByProvider[j].length; ++k) {
                        filters[cur] = filtersByProvider[j][k];
                        filters[cur++].init(ServletAuthenticationFilterServiceImpl.this.new FilterConfigImpl(ctx));
                     }
                  }
               }
            } catch (ServletException var11) {
               if (debug) {
                  ServletAuthenticationFilterServiceImpl.this.logger.debug(method + " Filter init() failed", var11);
               }

               throw var11;
            }

            if (debug) {
               ServletAuthenticationFilterServiceImpl.this.logger.debug(method + " Returning " + filters.length + " filters.");
            }

            return filters;
         }
      }

      public void destroyServletAuthenticationFilters(Filter[] filters) {
         if (filters != null && filters.length != 0) {
            for(int i = 0; i < filters.length; ++i) {
               filters[i].destroy();
            }

         }
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
