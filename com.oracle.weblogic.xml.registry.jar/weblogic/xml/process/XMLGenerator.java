package weblogic.xml.process;

import java.io.IOException;
import java.io.Writer;

public interface XMLGenerator {
   void generate(String var1, Object[] var2) throws IOException, XMLProcessingException;

   void setWriter(Writer var1);

   Writer getWriter();
}
