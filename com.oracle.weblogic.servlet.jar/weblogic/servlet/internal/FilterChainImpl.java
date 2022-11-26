package weblogic.servlet.internal;

import java.io.IOException;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public final class FilterChainImpl implements FilterChain {
   private List filters = new LinkedList();
   private BitSet asyncSupportedBits = new BitSet();
   private int index = 0;
   private int headFilterInsertPos = 0;
   private ServletRequestImpl req;
   private ServletResponseImpl res;

   public void add(Filter f) {
      if (f != null) {
         this.filters.add(f);
      }

   }

   public void add(Filter f, boolean asyncSupported) {
      if (f != null) {
         this.filters.add(f);
         if (asyncSupported) {
            this.asyncSupportedBits.set(this.filters.size() - 1, true);
         }
      }

   }

   public void add(FilterWrapper fw) throws ServletException {
      if (fw != null) {
         Filter f = fw.getFilter(true);
         if (f != null) {
            if (fw.isHeadFilter()) {
               this.filters.add(this.headFilterInsertPos, f);
               ++this.headFilterInsertPos;
            } else {
               this.filters.add(f);
            }

            if (fw.getAsyncSupported()) {
               this.asyncSupportedBits.set(this.filters.size() - 1, true);
            }

         }
      }
   }

   public void doFilter(ServletRequest req, ServletResponse rsp) throws IOException, ServletException {
      ServletRequestImpl.getOriginalRequest(req).setAsyncSupported(this.asyncSupportedBits.get(this.index));
      Filter f = this.index < this.filters.size() - 1 ? (Filter)this.filters.get(this.index++) : (Filter)this.filters.get(this.index);
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Doing f.doFilter() where f =" + f);
      }

      f.doFilter(req, rsp, this);
   }

   public ServletRequestImpl getOrigRequest() {
      return this.req;
   }

   public void setOrigRequest(ServletRequestImpl r) {
      this.req = r;
   }

   public ServletResponseImpl getOrigResponse() {
      return this.res;
   }

   public void setOrigResponse(ServletResponseImpl r) {
      this.res = r;
   }
}
