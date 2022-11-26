package weblogic.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.utils.PersistenceUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

class PersistenceToolsModule implements ToolsExtension {
   private GenericClassLoader gcl;
   private ToolsContext ctx;

   public void init(ToolsContext ctx, GenericClassLoader gcl) throws ToolFailureException {
      this.gcl = gcl;
      this.ctx = ctx;

      try {
         PersistenceUtils.addRootPersistenceJars(gcl, ctx.getApplicationId(), ctx.getApplicationDD());
      } catch (IOException var4) {
         throw new ToolFailureException("Unable to process persistence descriptors", var4);
      }
   }

   public Map merge() throws ToolFailureException {
      PersistenceUnitViewer perViewer = new PersistenceUnitViewer.ResourceViewer(this.gcl, this.ctx.getApplicationId(), this.ctx.getConfigDir(), this.ctx.getPlanBean());
      perViewer.loadDescriptors();
      Map descriptors = new HashMap();
      Iterator uris = perViewer.getDescriptorURIs();

      while(uris.hasNext()) {
         String uri = (String)uris.next();
         descriptors.put(uri, perViewer.getDescriptor(uri).getRootBean());
      }

      return descriptors;
   }

   public void cleanup() {
   }

   public void compile() throws ToolFailureException {
   }
}
