package pour.streams.schema;

public class SchemaField {
  
  private String name;
  
  private String type;
  
  private int size;
  
  private int offset;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public int getSize() {
    return size;
  }

  public int getOffset() {
    return offset;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

}
