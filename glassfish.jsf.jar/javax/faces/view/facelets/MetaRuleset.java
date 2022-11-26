package javax.faces.view.facelets;

public abstract class MetaRuleset {
   public abstract MetaRuleset ignore(String var1);

   public abstract MetaRuleset ignoreAll();

   public abstract MetaRuleset alias(String var1, String var2);

   public abstract MetaRuleset add(Metadata var1);

   public abstract MetaRuleset addRule(MetaRule var1);

   public abstract Metadata finish();
}
