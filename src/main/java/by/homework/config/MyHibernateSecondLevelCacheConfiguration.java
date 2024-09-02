// package by.homework.config;

// import org.hibernate.cache.jcache.ConfigSettings;
// import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.cache.jcache.JCacheCacheManager;
// @Configuration(proxyBeanMethods = false)
// public class MyHibernateSecondLevelCacheConfiguration {

// 	@Bean
// 	public HibernatePropertiesCustomizer hibernateSecondLevelCacheCustomizer(JCacheCacheManager cacheManager) {
// 		return (properties) -> properties.put(ConfigSettings.CACHE_MANAGER, cacheManager.getCacheManager());
// 	}

// }