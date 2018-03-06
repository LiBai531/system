/**
 * Project Name:DSEP
 * File Name:ExportService.java
 * Package Name:com.dsep.service.file
 * Date:2014年5月14日下午3:51:54
 *
 */

package com.physics.service.base.file;

import java.util.List;
import java.util.Map;

public interface ExportService {
	abstract public String exportUsersByType(String userType,String rootPath);
}

