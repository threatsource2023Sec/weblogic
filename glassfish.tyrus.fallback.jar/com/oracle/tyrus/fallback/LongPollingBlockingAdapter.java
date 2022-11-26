package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.LongPollingAdapter;
import com.oracle.tyrus.fallback.spi.ReadHandler;
import com.oracle.tyrus.fallback.spi.WriteFrame;
import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.util.Queue;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LongPollingBlockingAdapter implements LongPollingAdapter {
   public void read(HttpServletRequest req, ReadHandler rh, CompletionHandler ch) {
      try {
         ServletInputStream sis = req.getInputStream();

         while(true) {
            byte[] buf = new byte[4096];
            int len = sis.read(buf);
            if (len == -1) {
               break;
            }

            rh.handle(buf, 0, len);
         }
      } catch (IOException var7) {
         ch.failed(var7, (Object)null);
         return;
      }

      ch.completed((Object)null, (Object)null);
   }

   public void write(HttpServletResponse res, Queue frames, CompletionHandler ch) {
      try {
         ServletOutputStream sos = res.getOutputStream();

         while(!frames.isEmpty()) {
            WriteFrame wf = (WriteFrame)frames.remove();
            sos.write(wf.buf, wf.offset, wf.length);
         }
      } catch (IOException var6) {
         ch.failed(var6, (Object)null);
         return;
      }

      ch.completed((Object)null, (Object)null);
   }
}
