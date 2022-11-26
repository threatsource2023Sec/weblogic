package antlr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public interface JavaCodeGeneratorPrintWriterManager {
   PrintWriter setupOutput(Tool var1, Grammar var2) throws IOException;

   PrintWriter setupOutput(Tool var1, String var2) throws IOException;

   void startMapping(int var1);

   void startSingleSourceLineMapping(int var1);

   void endMapping();

   void finishOutput() throws IOException;

   Map getSourceMaps();
}
