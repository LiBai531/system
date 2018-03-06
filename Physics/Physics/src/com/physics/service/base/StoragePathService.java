package com.physics.service.base;

import com.physics.entity.Storage;
import com.physics.entity.enumeration.PhysicsEnum;;

public interface StoragePathService {
	/**
	 * 获取数据库中配置的存储路径
	 * @param storage
	 * @return	数据库字段，代表存储路径(应该以"/"结尾)
	 */
	public abstract String getRootPath(Storage storage);
	/**
	 * 获得当前活动的根路径的绝对路径
	 * @return	返回绝对路径，以"/"结尾
	 */
	public abstract String getActiveRootPath();
	/**
	 * 获得当前活动的domain绝对路径
	 * @return	返回绝对路径，以"/"结尾
	 */
	public abstract String getActiveDomainPath();
	/**
	 * 
	 * @param subPath	Occasion后根据自定义规则设置的子路径(格式: (folder{/folder}/?)? )
	 * @return 返回对应的绝对路径(以"/"结尾)
	 */
	public abstract String getOccasionPath(PhysicsEnum occasion, String subPath);
	/**
	 * 获得某一具体存储路径的绝对路径
	 * @param parent	上级目录，可以是存储根目录路径也可以是domain路径，
	 * 					或者其他方式获得的路径(以"/"结尾)
	 * @param subPath	子路径(格式：{folder/})
	 * @param flag		路径如果不存在，true则建立，否则返回null
	 * @return	返回绝对路径(以"/"结尾，取决于subPath)或者null
	 */
	public abstract String getPath(String parent, String subPath, boolean flag);
	/**
	 * 根据ID获得路径的绝对路径
	 * @param id		设备号Id
	 * @param subPath 为相对于Id设备号所存储的根路径下的子路径(格式：{folder/})
	 * @return	返回绝对路径(以"/"结尾)或者null(如果路径不存在)
	 */
	public abstract String getStoragePathById(String id, String subPath);
	/**
	 * 获得以Domain开头的相对存储设备的相对路径
	 * @param occasion	枚举类型，代表存储分类
	 * @param subPath	可选(格式：(folder{folder/}/?)? )
	 * @return 返回相对路径,以"/"结尾(DomainID/occasion/subPath)
	 */
	public abstract String getOccasionRelativePath(PhysicsEnum occasion, String subPath);
	/**
	 * 获取freemarker模板根目录
	 * @param	模板目录下的子路径(格式：{folder/})
	 * @return 	freemarker模板根目录的绝对路径
	 */
	public abstract String getTemplateRootPath(String subPath);
}
