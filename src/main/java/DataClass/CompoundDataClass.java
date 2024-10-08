package DataClass;

import java.util.HashMap;

public abstract class CompoundDataClass extends OuterDataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  protected int requiredSetValues;
  public CompoundDataClass(int requiredSetValues) {
    this.requiredSetValues = requiredSetValues;
  }

  public DataClass getInner(String innerName) {
    return inners.get(innerName);
  }
  public void putInner(String innerName, DataClass innerVal) {
    inners.put(innerName, innerVal);
  }

}
