package com.bea.staxb.runtime.internal;

class NamespaceTests {
   public static void main(String[] args) {
      ScopedNamespaceContext ctx = new ScopedNamespaceContext();

      assert 0 == ctx.getCurrentScopeNamespaceCount();

      ctx.openScope();

      assert 0 == ctx.getCurrentScopeNamespaceCount();

      ctx.bindNamespace("p1.1", "n1.1");

      assert 1 == ctx.getCurrentScopeNamespaceCount();

      ctx.bindNamespace("p1.2", "n1.2");

      assert 2 == ctx.getCurrentScopeNamespaceCount();

      ctx.openScope();
      ctx.bindNamespace("p2.1", "n2.1");

      assert 1 == ctx.getCurrentScopeNamespaceCount();

      ctx.bindNamespace("p2.2", "n2.2");

      assert 2 == ctx.getCurrentScopeNamespaceCount();

      assert "n1.1".equals(ctx.getNamespaceURI("p1.1"));

      assert "n2.1".equals(ctx.getNamespaceURI("p2.1"));

      ctx.closeScope();

      assert "n1.1".equals(ctx.getNamespaceURI("p1.1"));

      assert ctx.getNamespaceURI("p2.1") == null;

      String uri = "defaultNS";
      ctx.bindNamespace("", "defaultNS");
      ctx.bindNamespace("pfx2", "defaultNS");

      assert "".equals(ctx.getPrefix("defaultNS"));

   }
}
