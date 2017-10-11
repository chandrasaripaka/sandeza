package pour.streams.operators;

public abstract class AbstractFactory extends Factory{
  
  abstract void create();
  
  abstract void remove();
  
  abstract void destroy();

}
