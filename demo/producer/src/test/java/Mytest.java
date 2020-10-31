import cn.zhengyk.core.utils.IpUtil;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/16 17:10
 */
public class Mytest {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>(10);
        list.add("1");
        list.add("2");
        list.add("3");
        List<String> collect = list.stream().map(s -> s = "0" + s).collect(Collectors.toList());
        System.out.println(collect);


    }

    @Test
    public void test2() throws URISyntaxException {
        URI uri = new URI("localhost:8080");
        System.out.println(uri.toString());

        System.out.println(IpUtil.getHostIp());

    }
}
