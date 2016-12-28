package ${package};

<#if imports?exists>
		<#list imports as col>
import ${col};
		</#list>
</#if>

public interface ${class}Service extends BaseService<${class}, ${class}Dao>{

}