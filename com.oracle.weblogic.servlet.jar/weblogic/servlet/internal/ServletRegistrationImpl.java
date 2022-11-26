package weblogic.servlet.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebResourceCollectionBean;
import weblogic.j2ee.descriptor.wl.ServletDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.DeploymentException;

public class ServletRegistrationImpl implements ServletRegistration.Dynamic {
   protected WebAppServletContext context;
   protected ServletStubImpl stub;
   private ServletSecurityElement servletSecurityElement = null;
   private Set urlMappings = new HashSet();

   protected void setServletSecurityElement(ServletSecurityElement servletSecurityElement) {
      this.servletSecurityElement = servletSecurityElement;
   }

   ServletRegistrationImpl(WebAppServletContext context, ServletStubImpl stub) {
      this.context = context;
      this.stub = stub;
   }

   public Set addMapping(String... urlPatterns) {
      this.checkContextStatus();
      if (urlPatterns != null && urlPatterns.length != 0) {
         Set conflicts = new HashSet();

         int i;
         String urlPattern;
         for(i = 0; i < urlPatterns.length; ++i) {
            urlPattern = urlPatterns[i];
            if (this.context.isServletRegistered(urlPattern)) {
               conflicts.add(urlPattern);
            }
         }

         if (conflicts.isEmpty()) {
            for(i = 0; i < urlPatterns.length; ++i) {
               urlPattern = urlPatterns[i];
               this.context.registerServletMap(this.stub.getServletName(), urlPattern);
               this.urlMappings.add(urlPattern);
            }
         }

         return conflicts;
      } else {
         throw new IllegalArgumentException("url pattern parameters can't be null");
      }
   }

   public Collection getMappings() {
      Object[] values = this.context.getServletMapping().values();
      if (values != null && values.length >= 1) {
         Collection ret = new HashSet();

         for(int i = 0; i < values.length; ++i) {
            URLMatchHelper umh = (URLMatchHelper)values[i];
            if (umh.getServletStub() == this.stub) {
               ret.add(umh.getPattern());
            }
         }

         return ret;
      } else {
         return Collections.emptySet();
      }
   }

   public String getClassName() {
      return this.stub.getClassName();
   }

   public String getInitParameter(String name) {
      return this.stub.getInitParameter(name);
   }

   public Map getInitParameters() {
      return new HashMap(this.stub.getInitParametersMap());
   }

   public String getName() {
      return this.stub.getServletName();
   }

   public boolean setInitParameter(String name, String value) {
      this.checkContextStatus();
      if (name != null && value != null) {
         return this.disallowConfigure() ? true : this.stub.setInitParameter(name, value);
      } else {
         throw new IllegalArgumentException("Init parameters can't be null");
      }
   }

   public Set setInitParameters(Map initParameters) {
      this.checkContextStatus();
      Iterator keys = initParameters.keySet().iterator();

      String key;
      do {
         if (!keys.hasNext()) {
            Set ret = new HashSet();
            if (this.disallowConfigure()) {
               return ret;
            }

            keys = initParameters.keySet().iterator();

            String value;
            do {
               if (!keys.hasNext()) {
                  return ret;
               }

               key = (String)keys.next();
               if (this.stub.getInitParameter(key) != null) {
                  ret.add(key);
               }

               value = (String)initParameters.get(key);
            } while(this.setInitParameter(key, value));

            return null;
         }

         String key = (String)keys.next();
         if (key == null) {
            throw new IllegalArgumentException("Init parameters can't be null");
         }

         key = (String)initParameters.get(key);
      } while(key != null);

      throw new IllegalArgumentException("Init parameters can't be null");
   }

   public void setLoadOnStartup(int loadOnStartup) {
      this.checkContextStatus();
      if (!this.disallowConfigure()) {
         try {
            this.context.registerServletLoadSequence(this.stub.getServletName(), loadOnStartup);
         } catch (DeploymentException var3) {
         }

      }
   }

   public void setMultipartConfig(MultipartConfigElement multipartConfig) {
      this.checkContextStatus();
      if (multipartConfig == null) {
         throw new IllegalArgumentException("MultipartConfig can't be null");
      } else if (!this.disallowConfigure()) {
         this.stub.setMultipartConfig(multipartConfig);
      }
   }

   public String getRunAsRole() {
      return this.stub.getSecurityHelper().getRunAsRoleName();
   }

   public void setRunAsRole(String roleName) {
      this.checkContextStatus();
      if (roleName == null) {
         throw new IllegalArgumentException("role name can't be null");
      } else if (!this.disallowConfigure()) {
         StubSecurityHelper securityHelper = this.stub.getSecurityHelper();
         securityHelper.setRunAsRoleName(roleName);
         WeblogicWebAppBean wlWebAppBean = this.context.getWebAppModule().getWlWebAppBean();
         if (wlWebAppBean != null) {
            ServletDescriptorBean descriptor = wlWebAppBean.lookupServletDescriptor(this.stub.getServletName());
            if (descriptor != null) {
               securityHelper.setRunAsIdentity(descriptor.getRunAsPrincipalName());
            }
         }

      }
   }

   private Collection findSecurityConstraintWebResources(WebAppBean webAppBean) {
      ArrayList urlPatternSet = null;
      SecurityConstraintBean[] scbs = webAppBean.getSecurityConstraints();
      if (scbs != null) {
         SecurityConstraintBean[] var4 = scbs;
         int var5 = scbs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            SecurityConstraintBean scb = var4[var6];
            String source = (String)((AbstractDescriptorBean)scb).getMetaData("source");
            if (source == null || !source.equals("annotation") && !source.equals("dynamic")) {
               WebResourceCollectionBean[] wrcbs = scb.getWebResourceCollections();
               if (wrcbs != null) {
                  WebResourceCollectionBean[] var10 = wrcbs;
                  int var11 = wrcbs.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     WebResourceCollectionBean wrcb = var10[var12];
                     String[] urlPatterns = wrcb.getUrlPatterns();
                     if (urlPatterns != null && urlPatterns.length > 0) {
                        if (urlPatternSet == null) {
                           urlPatternSet = new ArrayList();
                        }

                        urlPatternSet.addAll(Arrays.asList(urlPatterns));
                     }
                  }
               }
            }
         }
      }

      Object retVal;
      if (urlPatternSet == null) {
         retVal = Collections.emptySet();
      } else {
         retVal = urlPatternSet;
      }

      return (Collection)retVal;
   }

   public Set setServletSecurity(ServletSecurityElement constraint) {
      this.checkContextStatus();
      SecurityConstraintHelper.checkServletSecurityElement(constraint);
      WebAppBean webBean = this.context.getWebAppModule().getWebAppBean();
      Collection webResourcesToDefine = this.getMappings();
      Set ret = new HashSet(webResourcesToDefine);
      Collection urlConstrainted = this.findSecurityConstraintWebResources(webBean);
      webResourcesToDefine.removeAll(urlConstrainted);
      this.setServletSecurityElement(constraint);
      ret.retainAll(urlConstrainted);
      return ret;
   }

   public void setAsyncSupported(boolean isAsyncSupported) {
      this.checkContextStatus();
      if (!this.disallowConfigure()) {
         this.stub.setAsyncSupported(isAsyncSupported);
      }
   }

   protected void checkContextStatus() {
      if (this.context.isStarted()) {
         throw new IllegalStateException("ServletContext has already been initialized.");
      }
   }

   protected void deployServletSecurity() {
      if (this.urlMappings != null && this.servletSecurityElement != null) {
         WebAppBean webBean = this.context.getWebAppModule().getWebAppBean();
         Iterator itor = this.urlMappings.iterator();

         while(itor.hasNext()) {
            String urlMapping = (String)itor.next();
            SecurityConstraintHelper.processServletSecurityElement(webBean, this.urlMappings, this.servletSecurityElement, false);
         }

      }
   }

   private boolean disallowConfigure() {
      return this.stub.isContextClassLoaderChanged();
   }
}
