package weblogic.xml.babel.scanner;

import java.io.IOException;

public interface DTDProcessor {
   void read() throws IOException, ScannerException;

   void setExternalDTD(boolean var1);

   String getStringValue();
}
