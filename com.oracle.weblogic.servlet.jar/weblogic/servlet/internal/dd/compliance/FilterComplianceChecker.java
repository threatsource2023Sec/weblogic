package weblogic.servlet.internal.dd.compliance;

import java.util.HashSet;
import java.util.Set;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.utils.ErrorCollectionException;

public class FilterComplianceChecker extends BaseComplianceChecker {
   private static final String SUPER_CLASS = "javax.servlet.Filter";
   private static final boolean debug = false;

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      FilterBean[] filters = info.getWebAppBean().getFilters();
      FilterMappingBean[] filterMappings = info.getWebAppBean().getFilterMappings();
      if (filters != null || filterMappings != null) {
         if (filters != null) {
            Set filterNames = filters.length > 1 ? new HashSet() : null;

            for(int i = 0; i < filters.length; ++i) {
               this.checkFilter(filters[i], info.getClassLoader());
               if (filterNames != null && !filterNames.add(filters[i].getFilterName())) {
                  this.update(this.fmt.warning() + this.fmt.DUPLICATE_FILTER_DEF(filters[i].getFilterName()));
               }
            }
         }

         this.checkForExceptions();
         int i;
         if (filterMappings == null && filters != null) {
            for(i = 0; i < filters.length; ++i) {
               this.update(this.fmt.warning() + this.fmt.NO_MAPPING_FOR_FILTER(filters[i].getFilterName()));
            }
         }

         if (filterMappings != null) {
            for(i = 0; i < filterMappings.length; ++i) {
               this.checkFilterMapping(filters, filterMappings[i], info);
            }
         }

      }
   }

   private void checkFilter(FilterBean filter, ClassLoader cl) throws ErrorCollectionException {
      String filterName = filter.getFilterName();
      if (filterName == null || filterName.length() == 0) {
         this.addDescriptorError(this.fmt.NO_FILTER_NAME());
      }

      this.checkForExceptions();
      String filterClass = filter.getFilterClass();
      if (filterClass == null || filterClass.length() == 0) {
         this.addDescriptorError(this.fmt.NO_FILTER_CLASS(filterName));
      }

      this.checkForExceptions();
      if (filterClass != null && cl != null && !this.isClassAssignable(cl, "filter-class", filterClass, "javax.servlet.Filter")) {
         this.checkForExceptions();
      }

   }

   private void checkFilterMapping(FilterBean[] filters, FilterMappingBean mapping, DeploymentInfo info) throws ErrorCollectionException {
      String filterName = mapping.getFilterName();
      if (filterName != null) {
         this.update(this.fmt.CHECKING_FILTER_MAPPING(filterName));
      }

      boolean found = false;

      for(int i = 0; i < filters.length; ++i) {
         if (filterName.equals(filters[i].getFilterName())) {
            found = true;
            break;
         }
      }

      String[] urlPatternArr = mapping.getUrlPatterns();
      if (!found) {
         this.addDescriptorError(this.fmt.NO_FILTER_DEF_FOR_MAPPING(filterName), new DescriptorErrorInfo(new String[]{"<filter-mapping>", "<filter-name>"}, filterName, new Object[]{"<filter-name>"}));
         this.checkForExceptions();
      }

      String[] servletNames = mapping.getServletNames();
      int i;
      if (servletNames != null && servletNames.length > 0) {
         for(i = 0; i < servletNames.length; ++i) {
            if (!this.validateServletName(servletNames[i], info)) {
               this.addDescriptorError(this.fmt.NO_SERVLET_DEF_FOR_FILTER(servletNames[i], filterName), new DescriptorErrorInfo(new String[]{"<filter-mapping>", "<filter-name>"}, filterName, new Object[]{"<servlet-name>"}));
            }
         }
      } else if (urlPatternArr != null && urlPatternArr.length > 0) {
         for(i = 0; i < urlPatternArr.length; ++i) {
            this.validateURLPattern(filterName, urlPatternArr[i]);
         }
      }

      this.checkForExceptions();
   }

   private void validateURLPattern(String filterName, String pattern) {
      if (pattern == null || pattern.length() == 0) {
         this.addDescriptorError(this.fmt.NO_URL_PATTERN_FOR_FILTER(filterName), new DescriptorErrorInfo("<filter-name>", filterName, "<url-pattern>"));
      }

   }

   public boolean validateServletName(String servletName, DeploymentInfo info) {
      ServletBean[] servletBeans = info.getWebAppBean().getServlets();
      if (servletBeans != null && servletName != null) {
         for(int i = 0; i < servletBeans.length; ++i) {
            if (servletName.equals(servletBeans[i].getServletName())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
