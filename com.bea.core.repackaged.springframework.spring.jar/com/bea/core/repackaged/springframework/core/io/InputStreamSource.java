package com.bea.core.repackaged.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {
   InputStream getInputStream() throws IOException;
}
