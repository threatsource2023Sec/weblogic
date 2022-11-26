package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;

interface CMSSecureReadable {
   InputStream getInputStream() throws IOException, CMSException;
}
