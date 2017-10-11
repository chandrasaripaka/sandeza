package pour.streams.catalog;

import java.util.List;

public class CatalogStream {
  
  BoxPort inputBoxPort;
  
  List<BoxPort> outputBoxPorts;
  
  CatalogSchema schema;
  
  CatalogSubscription inputCatSubscription;
  
  List<CatalogSubscription> outCatSubscriptions;
  
  boolean isInputStream = false;
  
  boolean isInputOrOutputStream = false;
  
}
