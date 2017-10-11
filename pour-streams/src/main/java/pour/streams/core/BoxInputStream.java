package pour.streams.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pour.streams.catalog.BoxPort;
import pour.streams.catalog.CatalogSchema;
import pour.streams.catalog.CatalogSubscription;

public class BoxInputStream extends InputStream{
  
  BoxPort inputBoxPort;
  
  List<BoxPort> outputBoxPorts;
  
  CatalogSchema schema;
  
  CatalogSubscription inputCatSubscription;
  
  List<CatalogSubscription> outCatSubscriptions;
  
  boolean isInputStream = false;
  
  boolean isInputOrOutputStream = false;

  public BoxPort getInputBoxPort() {
    return inputBoxPort;
  }

  public List<BoxPort> getOutputBoxPorts() {
    return outputBoxPorts;
  }

  public CatalogSchema getSchema() {
    return schema;
  }

  public CatalogSubscription getInputCatSubscription() {
    return inputCatSubscription;
  }

  public List<CatalogSubscription> getOutCatSubscriptions() {
    return outCatSubscriptions;
  }

  public boolean isInputStream() {
    return isInputStream;
  }

  public boolean isInputOrOutputStream() {
    return isInputOrOutputStream;
  }

  public void setInputBoxPort(BoxPort inputBoxPort) {
    this.inputBoxPort = inputBoxPort;
  }

  public void setOutputBoxPorts(List<BoxPort> outputBoxPorts) {
    this.outputBoxPorts = outputBoxPorts;
  }

  public void setSchema(CatalogSchema schema) {
    this.schema = schema;
  }

  public void setInputCatSubscription(CatalogSubscription inputCatSubscription) {
    this.inputCatSubscription = inputCatSubscription;
  }

  public void setOutCatSubscriptions(List<CatalogSubscription> outCatSubscriptions) {
    this.outCatSubscriptions = outCatSubscriptions;
  }

  public void setInputStream(boolean isInputStream) {
    this.isInputStream = isInputStream;
  }

  public void setInputOrOutputStream(boolean isInputOrOutputStream) {
    this.isInputOrOutputStream = isInputOrOutputStream;
  }

  @Override
  public int read() throws IOException {
    // TODO Auto-generated method stub
    return 0;
  }
}
