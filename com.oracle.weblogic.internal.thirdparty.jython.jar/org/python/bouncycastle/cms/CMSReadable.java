package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;

interface CMSReadable {
   InputStream getInputStream() throws IOException, CMSException;
}
