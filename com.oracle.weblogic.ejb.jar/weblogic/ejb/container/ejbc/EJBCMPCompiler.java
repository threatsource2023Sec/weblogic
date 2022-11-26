package weblogic.ejb.container.ejbc;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import weblogic.ejb.container.interfaces.CMPCompiler;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.ICompilerFactory;

public final class EJBCMPCompiler implements CMPCompiler {
   private Getopt2 opts;
   private File outputDir;
   private CMPDeployer cmpDeployer = null;
   private CMPCodeGenerator cmpGenerator = null;
   private ICompilerFactory compilerFactory;

   EJBCMPCompiler(File outputDir, Getopt2 opts, ICompilerFactory compilerFactory) {
      this.outputDir = outputDir;
      this.opts = opts;
      this.compilerFactory = compilerFactory;
   }

   public List generatePersistenceSources(EntityBeanInfo ebi) throws Exception {
      assert ebi != null;

      PersistenceType persistenceType = ebi.getCMPInfo().getPersistenceType();
      this.cmpDeployer = ebi.getCMPInfo().getDeployer();

      assert this.cmpDeployer != null;

      persistenceType.setOptions(this.opts);
      this.cmpGenerator = persistenceType.getCodeGenerator();
      this.cmpDeployer.preCodeGeneration(this.cmpGenerator);
      List generatedOutputs = null;
      if (this.cmpGenerator != null) {
         this.cmpGenerator.setCompilerFactory(this.compilerFactory);
         this.cmpGenerator.setAssociatedType(persistenceType);
         this.cmpGenerator.setRootDirectoryName(this.outputDir.getAbsolutePath());
         this.cmpGenerator.setTargetDirectory(this.outputDir.getAbsolutePath());
         this.cmpGenerator.generate(ebi);
         generatedOutputs = this.cmpGenerator.getGeneratedOutputs();
      }

      if (generatedOutputs == null) {
         generatedOutputs = new LinkedList();
      }

      return (List)generatedOutputs;
   }

   public void postCompilation() throws Exception {
      this.cmpDeployer.postCodeGeneration(this.cmpGenerator);
   }
}
