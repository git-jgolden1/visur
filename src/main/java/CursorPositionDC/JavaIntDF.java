package CursorPositionDC;

import DataClass.DCHolder;
import DataClass.DataForm;
import DataClass.DataFormBrick;

import java.util.Optional;

public class JavaIntDF extends DataForm {
  public JavaIntDF(int numberOfConvertibleForms) {
    super(numberOfConvertibleForms);
  }

  @Override
  public Optional<DataFormBrick> makeBrick(Object val) {
    return Optional.empty();
  }

  @Override
  public Object getValAs(DCHolder dcHolder) {
    return null;
  }

  @Override
  public Object convertTo(DataForm toDF, Object fromDFVal, DCHolder dcHolder) {
    return null;
  }

  @Override
  public Object convertFrom(DataForm fromDF, Object toDFVal) {
    return null;
  }
}