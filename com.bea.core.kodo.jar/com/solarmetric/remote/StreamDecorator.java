package com.solarmetric.remote;

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamDecorator {
   InputStream decorate(InputStream var1) throws Exception;

   OutputStream decorate(OutputStream var1) throws Exception;
}
