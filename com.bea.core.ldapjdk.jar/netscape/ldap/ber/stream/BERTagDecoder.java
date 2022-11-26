package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public abstract class BERTagDecoder {
   public abstract BERElement getElement(BERTagDecoder var1, int var2, InputStream var3, int[] var4, boolean[] var5) throws IOException;
}
