package zhangyu.handle;
import java.util.Map;

public interface IHandler {
    Map<String, Object> convert(String resource);
}
