package com.sun.faces.facelets;

import java.io.IOException;
import java.net.URL;

public class PrivateApiFaceletCacheAdapter extends javax.faces.view.facelets.FaceletCache {
   private FaceletCache privateApi;
   private javax.faces.view.facelets.FaceletCache.MemberFactory memberFactory;
   private javax.faces.view.facelets.FaceletCache.MemberFactory metadataMemberFactory;

   public PrivateApiFaceletCacheAdapter(FaceletCache privateApi) {
      this.privateApi = privateApi;
   }

   public Object getFacelet(URL url) throws IOException {
      return this.privateApi.getFacelet(url);
   }

   public Object getViewMetadataFacelet(URL url) throws IOException {
      return this.privateApi.getMetadataFacelet(url);
   }

   public boolean isFaceletCached(URL url) {
      return this.privateApi.isFaceletCached(url);
   }

   public boolean isViewMetadataFaceletCached(URL url) {
      return this.privateApi.isMetadataFaceletCached(url);
   }

   public void setMemberFactories(final javax.faces.view.facelets.FaceletCache.MemberFactory faceletFactory, final javax.faces.view.facelets.FaceletCache.MemberFactory viewMetadataFaceletFactory) {
      FaceletCache.InstanceFactory instanceFactory = new FaceletCache.InstanceFactory() {
         public Object newInstance(URL key) throws IOException {
            return faceletFactory.newInstance(key);
         }
      };
      FaceletCache.InstanceFactory metadataInstanceFactory = new FaceletCache.InstanceFactory() {
         public Object newInstance(URL key) throws IOException {
            return viewMetadataFaceletFactory.newInstance(key);
         }
      };
      this.privateApi.init(instanceFactory, metadataInstanceFactory);
   }

   public javax.faces.view.facelets.FaceletCache.MemberFactory getMemberFactory() {
      return this.memberFactory;
   }

   public javax.faces.view.facelets.FaceletCache.MemberFactory getMetadataMemberFactory() {
      return this.metadataMemberFactory;
   }
}
