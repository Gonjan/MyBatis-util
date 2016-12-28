package cn.com.zwan;

import cn.com.zwan.mybatis.CodeGeneralUtil;

public class MybatisGeneralCodeUtil {
	public static void main(String[] args) {
		String configxml="G:/PetLoveWorkSpace/crm-parent/mybatis-util/src/main/resources/MyBatisGeneral2.xml";
		try {
			CodeGeneralUtil.generalCode(configxml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
