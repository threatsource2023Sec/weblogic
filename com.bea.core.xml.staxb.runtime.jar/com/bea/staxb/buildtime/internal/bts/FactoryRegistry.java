package com.bea.staxb.buildtime.internal.bts;

public final class FactoryRegistry {
   private static com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory writeJavaInstanceFactory(JavaInstanceFactory javaInstanceFactory, com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory node) {
      return node;
   }

   private static com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory writeParentInstanceFactory(ParentInstanceFactory parentInstanceFactory, com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory node) {
      node = (com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory)node.changeType(com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory.type);
      node = writeJavaInstanceFactory(parentInstanceFactory, node);
      com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory pifNode = (com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory)node;
      if (parentInstanceFactory.getCreateObjectMethod() != null) {
         TypeRegistry.writeMethodName(pifNode.addNewCreateObjectMethod(), parentInstanceFactory.getCreateObjectMethod());
      }

      return pifNode;
   }

   static void writeAJavaInstanceFactory(JavaInstanceFactory jif, com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory jif_node) {
      if (jif instanceof ParentInstanceFactory) {
         ParentInstanceFactory pif = (ParentInstanceFactory)jif;
         writeParentInstanceFactory(pif, jif_node);
      } else {
         throw new AssertionError("unknown type: " + jif.getClass());
      }
   }

   public static JavaInstanceFactory forNode(com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory node) {
      assert node != null;

      if (node instanceof com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory) {
         ParentInstanceFactory pif = new ParentInstanceFactory();
         fillFactoryFromNode(pif, node);
         return pif;
      } else {
         throw new AssertionError("unknown node type " + node.getClass());
      }
   }

   private static void fillFactoryFromNode(ParentInstanceFactory parentInstanceFactory, com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory node) {
      com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory pifNode = (com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory)node;
      parentInstanceFactory.setCreateObjectMethod(BindingFileUtils.create(pifNode.getCreateObjectMethod()));
   }
}
