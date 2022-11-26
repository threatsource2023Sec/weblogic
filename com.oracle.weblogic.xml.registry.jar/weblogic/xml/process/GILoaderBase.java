package weblogic.xml.process;

public class GILoaderBase {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   protected GeneratingInstructions instrux = new GeneratingInstructions();

   public GeneratingInstructions getGeneratingInstructions() {
      return this.instrux;
   }
}
