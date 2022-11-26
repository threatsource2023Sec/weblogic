package net.shibboleth.utilities.java.support.component;

public interface DestructableComponent extends Component {
   boolean isDestroyed();

   void destroy();
}
