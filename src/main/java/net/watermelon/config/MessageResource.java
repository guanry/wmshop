package net.watermelon.config;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.watermelon.core.BaseDAO;
import net.watermelon.resource.dao.ResourceDao;
import net.watermelon.resource.vo.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;




public class MessageResource extends AbstractMessageSource implements
		ResourceLoaderAware  {
	
	 protected Log logger = LogFactory.getLog(getClass());  

	@Autowired
	private ResourceDao<Resource> resourceRepository ;
	
	 
	 @SuppressWarnings("unused")
	private ResourceLoader resourceLoader;
	/** * Map切分字符 */
	protected final String MAP_SPLIT_CODE = "|";
	private final Map<String, String> properties = new HashMap<String, String>();

	private boolean loaded = false;
	
	
	public MessageResource() {
		
	}

	public void reload() {
		properties.clear();
		properties.putAll(loadTexts());
	}

	private List<Resource> getResource() {
		List<Resource> resources = resourceRepository.findAll();
		return resources;
	}

	/** * * 描述：TODO * 加载数据 * @return */
	protected Map<String, String> loadTexts() {
		Map<String, String> mapResource = new HashMap<String, String>();
		List<Resource> resources = this.getResource();
		for (Resource item : resources) {
			String code = item.getName() + MAP_SPLIT_CODE + item.getLanguage();
			mapResource.put(code, item.getText());
		}
		return mapResource;
	}

	/** * * 描述：TODO * @param code * @param locale 本地化语言 * @return */
	private String getText(String code, Locale locale) {
		if(!loaded) {
			reload();
			loaded = true;
		}
		String localeCode = locale.getLanguage();
		String key = code + MAP_SPLIT_CODE + localeCode;
		String localeText = properties.get(key);
		String resourceText = code;
		if (localeText != null) {
			resourceText = localeText;
		} else {
			localeCode = Locale.ENGLISH.getLanguage();
			key = code + MAP_SPLIT_CODE + localeCode;
			localeText = properties.get(key);
			if (localeText != null) {
				resourceText = localeText;
			} else {
				try {
					if (getParentMessageSource() != null) {
						resourceText = getParentMessageSource().getMessage(
								code, null, locale);
					}
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return resourceText;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = (resourceLoader != null ? resourceLoader
				: new DefaultResourceLoader());
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String msg = getText(code, locale);
		MessageFormat result = createMessageFormat(msg, locale);
		return result;
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String result = getText(code, locale);
		return result;
	}
}