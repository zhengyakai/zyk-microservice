package cn.zhengyk.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j
public class IpUtil {

    private IpUtil(){};

    public static String getHostIp() {

        String sIP = "";
        InetAddress ip = null;
        try {
            boolean bFindIP = false;
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                if (bFindIP)
                    break;
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                        bFindIP = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != ip) {
            sIP = ip.getHostAddress();
        }
        return sIP;
    }









    /**
     * 根据 ip 获得国家、省、市信息(离线计算，命中率99%)
     **/
    public static String getRegion(String ip){
        if (!Util.isIpAddress(ip)) {
            log.error("Error: Invalid ip address");
            return null;
        }

        String dbPath = IpUtil.class.getResource("/ip2region.db").getPath();
        File file = new File(dbPath);
        if (!file.exists()) {
            log.error("Error: Invalid ip2region.db file");
            return null;
        }
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);
            /*
             * memory算法：整个数据库全部载入内存，单次查询都在0.1x毫秒内。
             * binary算法：基于二分查找，基于ip2region.db文件，不需要载入内存，单次查询在0.x毫秒级别。
             * b-tree算法：基于btree算法，基于ip2region.db文件，不需要载入内存，单词查询在0.x毫秒级别，比binary算法更快。
             **/
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            return ((DataBlock) method.invoke(searcher, ip)).getRegion();
        } catch (Exception e) {
            log.error("search region by ip exception:", e);
        }
        return null;
    }

}
