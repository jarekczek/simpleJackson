import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MutableConfigOverride;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class Tests
{
  Logger logger;
  private File _file;

  public Tests()
  {
    logger = Logger.getLogger("bs");
    _file = new File("plik.json");
  }

  @Test
  public void jsonReadWrite() throws IOException {
    logger.info("This is just a simple test...");
    Book b = new Book();
    b.setTitle("W pustyni");
    ObjectMapper om = new ObjectMapper();
    om.writeValue(_file, b);

    Book b2 = om.readValue(_file, Book.class);
    System.out.println("title: " + b2.getTitle());
    Assert.assertEquals("W pustyni", b2.getTitle());

    Movie m = om.readValue(_file, Movie.class);
    Assert.assertEquals("W pustyni", m.getTitle());
  }

  @Test
  public void jsonReadMoreProps() throws IOException {
    ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Movie m = new Movie();
    m.setTitle("Psy");
    m.setDirector("Pasikowski");
    om.writeValue(_file, m);
    Book b = om.readValue(_file, Book.class);
    System.out.println("successfully read movie as book");
    Assert.assertEquals("Psy", b.getTitle());
  }

  @Test
  public void xmlSerialization() throws IOException
  {
    ObjectMapper om = new XmlMapper();
    MutableConfigOverride cfg = om.configOverride(Book.class);
    Book b = new Book();
    b.setTitle("Psy");
    om.writeValue(new File("plik.xml"), b);
  }

}