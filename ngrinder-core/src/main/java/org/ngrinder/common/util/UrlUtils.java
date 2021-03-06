/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ngrinder.common.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * Utility for Url manipulation.
 * 
 * @author JunHo Yoon
 * @since 3.2
 */
public abstract class UrlUtils {
	/**
	 * Get host part of the given url.
	 * 
	 * @param url
	 *            url
	 * @return host name
	 */
	public static String getHost(String url) {
		try {
			if (!url.startsWith("http")) {
				url = "http://" + url;
			}
			return StringUtils.trim(new URL(url).getHost());
		} catch (MalformedURLException e) {
			return "";
		}
	}
}
