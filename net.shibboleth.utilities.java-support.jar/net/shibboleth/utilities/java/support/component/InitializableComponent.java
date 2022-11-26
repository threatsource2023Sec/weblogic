package net.shibboleth.utilities.java.support.component;

public interface InitializableComponent extends Component {
   boolean isInitialized();

   void initialize() throws ComponentInitializationException;
}
