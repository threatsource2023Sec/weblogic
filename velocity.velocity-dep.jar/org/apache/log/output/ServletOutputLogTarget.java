package org.apache.log.output;

import javax.servlet.ServletContext;

public class ServletOutputLogTarget extends DefaultOutputLogTarget {
   private ServletContext m_context;

   public ServletOutputLogTarget(ServletContext context) {
      this.m_context = context;
      this.open();
   }

   protected void write(String message) {
      ServletContext context = this.m_context;
      if (null != context) {
         synchronized(context) {
            context.log(message);
         }
      }

   }

   public synchronized void close() {
      super.close();
      this.m_context = null;
   }
}
