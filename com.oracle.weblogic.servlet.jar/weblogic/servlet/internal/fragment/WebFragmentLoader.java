package weblogic.servlet.internal.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.WebFragmentBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class WebFragmentLoader extends AbstractDescriptorLoader2 {
   private final URL resourceUrl;
   private final URI resourceUri;
   private String rootUrl;
   private WebFragmentBean rootBean;
   private boolean hasDD;

   public WebFragmentLoader(URL resourceUrl, String moduleName, String qualifiedURI, boolean hasDD) {
      super((File)null, (File)null, (DeploymentPlanBean)null, moduleName, qualifiedURI);
      this.resourceUrl = resourceUrl;

      try {
         this.resourceUri = resourceUrl.toURI();
      } catch (URISyntaxException var7) {
         throw new RuntimeException(var7);
      }

      String urlPath = resourceUrl.getPath();
      if (hasDD) {
         int endIndex = urlPath.length() - "META-INF/web-fragment.xml".length() - 2;
         this.rootUrl = urlPath.substring(0, endIndex);
      } else {
         this.rootUrl = urlPath;
      }

      this.hasDD = hasDD;
   }

   public InputStream getInputStream() throws IOException {
      return this.hasDD ? this.resourceUrl.openStream() : null;
   }

   public WebFragmentBean getWebFragmentBean() throws IOException, XMLStreamException {
      if (this.rootBean == null && this.hasDD) {
         this.rootBean = (WebFragmentBean)this.loadDescriptorBean();
      }

      if (this.rootBean == null) {
         this.rootBean = (WebFragmentBean)(new DescriptorManager()).createDescriptorRoot(WebFragmentBean.class).getRootBean();
      }

      return this.rootBean;
   }

   public URL getResourceUrl() {
      return this.resourceUrl;
   }

   public String getRootUrl() {
      return this.rootUrl;
   }

   public String getJarFileName() {
      int index = this.rootUrl.lastIndexOf(47);
      return this.rootUrl.substring(index + 1);
   }

   public boolean isMetadataComplete() {
      return this.rootBean.isMetadataComplete();
   }

   public boolean shouldProcessAnnotation(URL classSourceUrl) {
      if (classSourceUrl == null) {
         return false;
      } else {
         return this.isFrom(classSourceUrl) && !this.isMetadataComplete();
      }
   }

   public boolean isFrom(URL classSourceUrl) {
      if (classSourceUrl != null && classSourceUrl.getPath() != null) {
         String webInfoLibJar = this.getRootUrl();
         String classSourcePath = classSourceUrl.getPath();
         return classSourcePath.startsWith(webInfoLibJar);
      } else {
         return false;
      }
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof WebFragmentLoader)) {
         return false;
      } else {
         WebFragmentLoader other = (WebFragmentLoader)o;
         return this.resourceUri.equals(other.resourceUri);
      }
   }

   public int hashCode() {
      return this.resourceUri.hashCode();
   }
}
