package weblogic.servlet.internal;

import java.util.Iterator;
import java.util.Map;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import weblogic.j2ee.descriptor.MultipartConfigBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.wl.ServletDescriptorBean;
import weblogic.management.ManagementException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.JSPServlet;
import weblogic.servlet.jsp.JavelinxJSPStub;
import weblogic.servlet.jsp.JspConfig;
import weblogic.servlet.jsp.JspStub;
import weblogic.servlet.jsp.ResourceProviderJspStub;
import weblogic.utils.http.HttpParsing;

public class ServletStubFactory {
   static ServletStubImpl getInstance(WebAppServletContext context, String name, String className, Class servletClass, Servlet servlet, ServletDescriptorBean descriptorBean, ServletMappingBean[] mappings) {
      ServletStubImpl stub = null;
      if (servletClass != null) {
         stub = new ServletStubImpl(name, servletClass, context);
      } else if (servlet != null) {
         stub = new ServletStubImpl(name, servlet, context);
      } else {
         if (className == null) {
            throw new IllegalArgumentException("either className, or servletClass, servlet is non-null value");
         }

         stub = new ServletStubImpl(name, className, context);
      }

      configureStubWithDescriptor(stub, descriptorBean);
      registerToContext(context, name, stub);
      registerServletMapping(context, name, stub, mappings);
      return stub;
   }

   static ServletStubImpl getInstance(WebAppServletContext context, ServletBean servletBean, ServletDescriptorBean descriptorBean) {
      ServletStubImpl stub = null;
      String servletName = servletBean.getServletName();
      String servletClass = servletBean.getServletClass();
      String jspFile = servletBean.getJspFile();
      if (servletClass == null && jspFile != null) {
         jspFile = HttpParsing.ensureStartingSlash(jspFile);
         JSPManager jspManager = context.getJSPManager();
         String jspcPkgPrefix = jspManager.getJspcPkgPrefix();
         String jspClassName = JSPServlet.uri2classname(jspcPkgPrefix, jspFile);
         stub = new JavelinxJSPStub(servletName, jspClassName, context, jspManager.createJspConfig());
         ((JspStub)stub).setFilePath(jspFile);
         context.registerServletMap(servletName, jspFile, (ServletStubImpl)stub);
      } else {
         stub = new ServletStubImpl(servletName, servletClass, context);
      }

      configureStubWithDescriptor((ServletStubImpl)stub, (ServletBean)servletBean);
      configureStubWithDescriptor((ServletStubImpl)stub, (ServletDescriptorBean)descriptorBean);
      registerToContext(context, servletName, (ServletStubImpl)stub);
      return (ServletStubImpl)stub;
   }

   static ServletStubImpl getInstance(WebAppServletContext context, String servletName, String servletClass, Map initParams) {
      ServletStubImpl stub = new ServletStubImpl(servletName, servletClass, context);
      if (initParams != null) {
         Iterator var5 = initParams.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            stub.setInitParameter((String)entry.getKey(), (String)entry.getValue());
         }
      }

      registerToContext(context, servletName, stub);
      return stub;
   }

   public static ServletStubImpl getInstance(WebAppServletContext context, String uri, JspConfig jspConfig) {
      if (context.getJspResourceProvider() != null) {
         return new ResourceProviderJspStub(uri, context, jspConfig);
      } else {
         String className = JSPServlet.uri2classname(jspConfig.getPackagePrefix(), uri);
         return new JavelinxJSPStub(uri, className, context, jspConfig);
      }
   }

   static ServletStubImpl getInstance(WebAppServletContext context, String servletName, String jspFile) {
      jspFile = HttpParsing.ensureStartingSlash(jspFile);
      JSPManager jspManager = context.getJSPManager();
      String jspcPkgPrefix = jspManager.getJspcPkgPrefix();
      String jspClassName = JSPServlet.uri2classname(jspcPkgPrefix, jspFile);
      ServletStubImpl stub = new JavelinxJSPStub(servletName, jspClassName, context, jspManager.createJspConfig());
      ((JspStub)stub).setFilePath(jspFile);
      context.registerServletMap(servletName, jspFile, stub);
      registerToContext(context, servletName, stub);
      return stub;
   }

   private static void configureStubWithDescriptor(ServletStubImpl stub, ServletDescriptorBean bean) {
      if (bean != null) {
         if (bean.getDispatchPolicy() != null) {
            stub.setDispatchPolicy(bean.getDispatchPolicy());
         }

         StubSecurityHelper securityHelper = stub.getSecurityHelper();
         String initAsPrincipal = bean.getInitAsPrincipalName();
         String destroyAsPrincipal = bean.getDestroyAsPrincipalName();
         String runAsPrincipal = bean.getRunAsPrincipalName();
         securityHelper.setInitAsIdentity(initAsPrincipal);
         securityHelper.setRunAsIdentity(runAsPrincipal);
         securityHelper.setDestroyAsIdentity(destroyAsPrincipal);
      }
   }

   private static void configureStubWithDescriptor(ServletStubImpl stub, ServletBean bean) {
      if (bean != null) {
         ParamValueBean[] params = bean.getInitParams();
         if (params != null) {
            ParamValueBean[] var3 = params;
            int var4 = params.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ParamValueBean param = var3[var5];
               stub.setInitParameter(param.getParamName(), param.getParamValue());
            }
         }

         stub.setAsyncSupported(bean.isAsyncSupported());
         MultipartConfigBean multpartConfig = bean.getMultipartConfig();
         if (multpartConfig != null) {
            stub.setMultipartConfig(new MultipartConfigElement(multpartConfig.getLocation(), multpartConfig.getMaxFileSize(), multpartConfig.getMaxRequestSize(), multpartConfig.getFileSizeThreshold()));
         }

         if (bean.getRunAs() != null) {
            String runAsRoleName = bean.getRunAs().getRoleName();
            StubSecurityHelper securityHelper = stub.getSecurityHelper();
            securityHelper.setRunAsRoleName(runAsRoleName);
         }

      }
   }

   private static void registerToContext(WebAppServletContext context, String name, ServletStubImpl stub) {
      try {
         stub.initRuntime();
         context.registerServletStub(name, stub);
      } catch (ManagementException var4) {
         HTTPLogger.logErrorCreatingServletStub(context.getLogContext(), name, stub.getClassName(), stub.getInitParametersMap(), var4);
      }

   }

   private static void registerServletMapping(WebAppServletContext context, String name, ServletStubImpl stub, ServletMappingBean[] mappings) {
      if (mappings != null && mappings.length != 0) {
         ServletMappingBean[] var4 = mappings;
         int var5 = mappings.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServletMappingBean mapping = var4[var6];
            String[] patterns = mapping.getUrlPatterns();
            if (patterns != null && patterns.length != 0) {
               String[] var9 = patterns;
               int var10 = patterns.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String pattern = var9[var11];
                  context.registerServletMap(name, pattern, stub);
               }
            }
         }

      }
   }
}
