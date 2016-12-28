package ${package};

<#if imports?exists>
		<#list imports as col>
import ${col};
		</#list>
</#if>

public interface ${class}Dao extends BaseDao<${class}>{

}