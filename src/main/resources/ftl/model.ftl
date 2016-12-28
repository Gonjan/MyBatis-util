package ${package};

<#if imports?exists>
		<#list imports as col>
import ${col};
		</#list>
</#if>

public class ${class} extends BaseEntity{
<#if cols?exists>
		<#list cols as col>
	private ${col.javaType} ${col.javaName};
		</#list>
</#if>

<#if cols?exists>
		<#list cols as col>
	public ${col.javaType} ${col.getter}() {
		return ${col.javaName};
	}
	public void ${col.setter}(${col.javaType} ${col.javaName}) {
		this.${col.javaName}=${col.javaName};
	}
		</#list>
</#if>
}