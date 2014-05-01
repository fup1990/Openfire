/**
 * $RCSfile$
 * $Revision: 3144 $
 * $Date: 2005-12-01 14:20:11 -0300 (Thu, 01 Dec 2005) $
 *
 * Copyright (C) 2004-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.util.cache;


/**
 * This specialized wrapper is used for the Components cache, which
 * should not be purged.
 * 
 * See {@link http://issues.igniterealtime.org/browse/OF-114} for more info.
 *
 */
public class ComponentCacheWrapper<K, V> extends CacheWrapper<K, V> {

    public ComponentCacheWrapper(Cache<K, V> cache) {
        super(cache);
    }

    public void clear() {
        // no-op; we don't want to clear the components cache
    }
}