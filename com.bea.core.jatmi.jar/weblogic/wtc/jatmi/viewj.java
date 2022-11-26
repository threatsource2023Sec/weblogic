package weblogic.wtc.jatmi;

public final class viewj extends viewjCompiler {
   public static void main(String[] args) {
      viewj view = new viewj();
      view.compileView(args);
   }

   private viewj() {
      super(false);
   }
}
