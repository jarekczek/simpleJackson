import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.avro.Schema;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AvroGenerTests
{
  abstract class IgnoreSchemaProperty
  {
    @JsonIgnore abstract void getSchema();
  }

  @Test
  public void writeJson() throws IOException {
    BookAvro b = BookAvro.newBuilder()
      .setTitle("Wilk stepowy")
      .setAuthor("Herman Hesse")
      .build();
    ObjectMapper om = new ObjectMapper();
    om.enable(SerializationFeature.INDENT_OUTPUT);
    om.addMixIn(BookAvro.class, IgnoreSchemaProperty.class);
    om.writeValue(new File("plik_z_gen.json"), b);
  }
}
