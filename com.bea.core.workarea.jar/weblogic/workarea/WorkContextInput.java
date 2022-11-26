package weblogic.workarea;

import java.io.DataInput;
import java.io.IOException;

public interface WorkContextInput extends DataInput {
   String readASCII() throws IOException;

   WorkContext readContext() throws IOException, ClassNotFoundException;
}
