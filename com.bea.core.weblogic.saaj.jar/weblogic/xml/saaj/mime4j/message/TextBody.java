package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.Reader;

public interface TextBody extends Body {
   Reader getReader() throws IOException;
}
