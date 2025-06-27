# LinkUtils getDomain Fix

## Problem
The `LinkUtils.getDomain()` method was always returning "publish" as the default domain, causing images to not display correctly on author environments because they were using publish host URLs instead of author URLs.

## Solution
Implemented dynamic environment detection using AEM's Sling run modes to determine whether the current environment is "author" or "publish", and return the appropriate domain accordingly.

## Key Components

### 1. SisalConfigService
- **Interface**: `com.sisal.foundations.core.services.SisalConfigService`
- **Implementation**: `com.sisal.foundations.core.services.impl.SisalConfigServiceImpl`
- Uses `SlingSettingsService` to detect run modes and determine environment type

### 2. LinkUtils
- **Location**: `com.sisal.foundations.core.utils.LinkUtils`
- **Fixed Method**: `getDomain(Map<String, String> externalizerDomains, String originalPath)`
- Now uses environment detection instead of hardcoded "publish" default

### 3. OSGi Integration
- **Service Activator**: `com.sisal.foundations.core.utils.LinkUtilsServiceActivator`
- Ensures proper dependency injection for static utility methods

## Behavior

| Environment | Run Mode Contains | getDomain() Returns |
|-------------|-------------------|-------------------|
| Author      | No "publish"      | "author"          |
| Publish     | "publish"         | "publish"         |
| Error/Unknown | Any             | "publish" (fallback) |

## Testing
Comprehensive unit tests demonstrate:
- Correct domain detection for both environments
- Proper error handling
- Removal of hardcoded localhost dependencies

## Usage
```java
Map<String, String> domains = new HashMap<>();
String domain = LinkUtils.getDomain(domains, originalPath);
// Returns "author" on author environment, "publish" on publish environment
```

The fix ensures images display correctly on both author and publish environments by using the appropriate domain for each environment type.