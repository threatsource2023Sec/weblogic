package weblogic.servlet.jsp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import weblogic.jsp.compiler.IJavelin;
import weblogic.jsp.compiler.client.ClientUtils;
import weblogic.jsp.wlw.util.filesystem.FS;
import weblogic.jsp.wlw.util.filesystem.mds.MDSFileSystem;
import weblogic.servlet.internal.RequestCallback;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.StringUtils;

class ResourceProviderJavelinxJspStub extends JavelinxJSPStub {
   private static final String DELIMITER = "!";
   private String requestURI;

   ResourceProviderJavelinxJspStub(String name, String className, WebAppServletContext sci, JspConfig jsps, String requestURI) {
      super(name, className, sci, jsps);
      this.requestURI = requestURI;
   }

   protected URI getFileToCompileURI(String providerURI) throws Exception {
      URI fileToCompileURI = null;

      try {
         if (this.resourceProvider != null) {
            StringBuilder buf = new StringBuilder("mds");
            buf.append(":");
            buf.append(this.requestURI);
            buf.append("!");
            buf.append(providerURI);
            fileToCompileURI = new URI(buf.toString());
         }
      } catch (Exception var4) {
      }

      if (fileToCompileURI == null) {
         throw new ServletException("Can't get resource URI because: 1.) JspResourceProvider is not initialized properly or 2.) there's URI syntax error in providerURI. ReqeustURI: " + this.requestURI + "providerURI: " + providerURI + "JspResourceProvider: " + this.resourceProvider);
      } else {
         return fileToCompileURI;
      }
   }

   protected URI[] prepareDocRoots(String uri) throws IOException {
      List docRoots = new ArrayList();
      docRoots.add(MDSFileSystem.DEFAULT_ROOT_URI);
      docRoots.addAll(Arrays.asList(super.prepareDocRoots(uri)));
      return (URI[])docRoots.toArray(new URI[docRoots.size()]);
   }

   protected String[] getSourcePaths(String uri) {
      String sPaths = this.getContext().getResourceFinder("/").getClassPath();
      return StringUtils.splitCompletely(sPaths, File.pathSeparator);
   }

   protected void compilePage(RequestCallback rc) throws Exception {
      try {
         ((MDSFileSystem)FS.getFSFromScheme("mds")).storeJspResourceProvider(this.resourceProvider);
         super.compilePage(rc);
      } finally {
         ((MDSFileSystem)FS.getFSFromScheme("mds")).removeJspResourceProvider();
      }

   }

   protected IJavelin createJaveLin(boolean verboseload, boolean autoload) {
      return ClientUtils.createCommandLineJavelin(verboseload, false, true);
   }
}
