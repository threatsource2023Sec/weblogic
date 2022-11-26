package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.OutputStream;

public interface Body {
   Entity getParent();

   void setParent(Entity var1);

   void writeTo(OutputStream var1) throws IOException;
}
