package config.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.publiccms.common.handler.FullBeanNameGenerator;
import com.publiccms.common.interceptor.AdminContextInterceptor;
import com.publiccms.common.interceptor.CsrfInterceptor;
import com.publiccms.common.view.AdminFreeMarkerView;
import com.publiccms.logic.component.cache.CacheComponent;
import com.publiccms.logic.component.template.TemplateComponent;

/**
 * AdminServlet配置类
 * 
 * AdminConfig
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.publiccms.controller.admin", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(value = { Controller.class }) }, nameGenerator = FullBeanNameGenerator.class)
public class AdminConfig implements WebMvcConfigurer {
    /**
     * 管理后台上下文路径 Management Context Path
     */
    public static final String ADMIN_CONTEXT_PATH = "/admin";

    @Resource
    private CacheComponent cacheComponent;
    @Resource
    private AdminContextInterceptor adminInterceptor;

    @Bean
    public LocaleResolver localeResolver(Environment env) {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("cms.locale");
        localeResolver.setCookieMaxAge(30 * 24 * 3600);
        String defaultLocale = env.getProperty("cms.defaultLocale");
        if (!"auto".equalsIgnoreCase(defaultLocale)) {
            localeResolver.setDefaultLocale(Locale.forLanguageTag(defaultLocale));
        }
        return localeResolver;
    }

    /**
     * 视图层解析器
     * 
     * @return FreeMarker view resolver
     */
    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();
        bean.setOrder(1);
        bean.setViewClass(AdminFreeMarkerView.class);
        bean.setPrefix("/admin/");
        bean.setSuffix(".html");
        bean.setExposeSpringMacroHelpers(false);
        bean.setContentType("text/html;charset=UTF-8");
        cacheComponent.registerCachingViewResolverList(bean);
        return bean;
    }

    /**
     * 拦截器
     * 
     * @param templateComponent
     * 
     * @return admin servlet interceptor
     */
    @Bean
    public AdminContextInterceptor adminInterceptor(TemplateComponent templateComponent) {
        templateComponent.setAdminContextPath(ADMIN_CONTEXT_PATH);
        AdminContextInterceptor bean = new AdminContextInterceptor();
        bean.setAdminContextPath(ADMIN_CONTEXT_PATH);
        bean.setLoginUrl("/login.html");
        bean.setUnauthorizedUrl("/common/unauthorizedUrl.html");
        bean.setLoginJsonUrl("/common/ajaxTimeout.html");
        bean.setNeedNotLoginUrls(new String[] { "/changeLocale", "/login", "/cmsContent/manualSave" });
        bean.setNeedNotAuthorizedUrls(new String[] { "/index", "/main", "/logout", "/menus", "/common/", "/cmsContent/manualSave" });
        return bean;
    }

    @Bean
    public CsrfInterceptor csrfInterceptor() {
        return new CsrfInterceptor(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(csrfInterceptor());
        registry.addInterceptor(adminInterceptor);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                List<MediaType> list = new ArrayList<>();
                list.add(MediaType.TEXT_PLAIN);
                ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(list);
                SimpleModule module = new SimpleModule();
                module.addSerializer(Long.class, ToStringSerializer.instance);
                ((MappingJackson2HttpMessageConverter) converter).getObjectMapper().registerModule(module);
            }
        }
    }
}
