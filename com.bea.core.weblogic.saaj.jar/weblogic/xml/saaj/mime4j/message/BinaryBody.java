package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.InputStream;

public interface BinaryBody extends Body {
   InputStream getInputStream() throws IOException;
}
