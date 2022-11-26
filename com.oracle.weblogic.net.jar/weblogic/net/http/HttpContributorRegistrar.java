package weblogic.net.http;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class HttpContributorRegistrar {
   public static HttpContributorRegistrar getHttpContributorRegistrar() {
      return HttpContributorRegistrar.HttpContributorRegistrarImpl.getInstance();
   }

   public abstract void registerRequestHeaderContributor(HttpRequestHeaderContributor var1);

   abstract Collection getRequestHeaderContributors();

   private static class HttpContributorRegistrarImpl extends HttpContributorRegistrar {
      private AtomicBoolean contributorsChanged;
      private Collection snapShotContributors;
      private Collection contributors;

      static HttpContributorRegistrar getInstance() {
         return HttpContributorRegistrar.HttpContributorRegistrarImpl.SingletonMaker.theOne();
      }

      private HttpContributorRegistrarImpl() {
         this.contributors = new ArrayList();
         this.snapShotContributors = new ArrayList();
         this.contributorsChanged = new AtomicBoolean(false);
      }

      public void registerRequestHeaderContributor(HttpRequestHeaderContributor contributor) {
         synchronized(this.contributors) {
            this.contributorsChanged.set(true);
            this.contributors.add(contributor);
         }
      }

      Collection getRequestHeaderContributors() {
         if (this.contributorsChanged.get()) {
            synchronized(this.contributors) {
               this.snapShotContributors = (Collection)((ArrayList)this.contributors).clone();
               this.contributorsChanged.set(false);
            }
         }

         return this.snapShotContributors;
      }

      // $FF: synthetic method
      HttpContributorRegistrarImpl(Object x0) {
         this();
      }

      private static class SingletonMaker {
         private static final HttpContributorRegistrar singleton = new HttpContributorRegistrarImpl();

         public static HttpContributorRegistrar theOne() {
            return singleton;
         }
      }
   }
}
