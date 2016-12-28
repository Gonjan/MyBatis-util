package ${package};

<#if imports?exists>
		<#list imports as col>
import ${col};
		</#list>
</#if>

public class ${class}ServiceImpl extends BaseServiceImpl<${class}, ${class}Dao> implements ${class}Service{

}