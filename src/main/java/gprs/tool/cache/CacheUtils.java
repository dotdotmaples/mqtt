package gprs.tool.cache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 *<p>类  说  明: TODO EhCache 缓存工具
 *<p>创建时间: 2017年11月4日 下午3:15:45
 **/
public class CacheUtils {
	
//	private static Logger logger = Logger.getLogger(CacheUtils.class);

	private static CacheManager cacheManager = CacheManager.create();

	public static void put(String cacheName, String cacheKey,Object cacheValue) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(cacheKey, cacheValue);
		cache.put(element);
	}

	public static Object get(String cacheName, String cacheKey) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(cacheKey);
		return element == null ? null : element.getObjectValue();
	}

	public static void removeAll(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.removeAll();
//		logger.info("缓存清除所有:" + cacheName);
	}

	public static void remove(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		boolean b = cache.remove(key);
//		logger.info("缓存清除指定:" + cacheName + "|清除元素:" + key + "|结果:" + b);
	}
}
