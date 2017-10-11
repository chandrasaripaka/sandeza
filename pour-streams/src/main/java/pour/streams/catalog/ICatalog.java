package pour.streams.catalog;

public interface ICatalog {
  
  public CatalogSchema getSchema(String schemaName);
  
  public CatalogStream getStream(String streamName);
  
  public CatalogBox getBox(String boxName);
  
  public CatalogTable getTable(String tableName);

}
