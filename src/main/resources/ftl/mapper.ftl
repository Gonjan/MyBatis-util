<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${namespace}" >
  <resultMap id="BaseResultMap" type="${type}" >
  	<#if cols?exists>
		<#list cols as col>
		<#if col.ispk==true>
	<id column="${col.columnName}" property="${col.javaName}" jdbcType="${col.jdbcType}" />
		<#else>
	<result column="${col.columnName}" property="${col.javaName}" jdbcType="${col.jdbcType}" />
		</#if>
		</#list>
	</#if>
  </resultMap>
  <sql id="Base_Column_List" >
    ${basecolumn}
  </sql>
  <#if pkCol?exists>
  <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="${pkCol.parameterType}" >
    select 'true' as QUERYID,
    <include refid="Base_Column_List" />
    from ${tbname}
    where ${pkCol.columnName} = ${pkCol.javaAndJdbcType}
  </select>
  </#if>
  <select id="findByPage" resultMap="BaseResultMap" parameterType="page" >
    select
    <include refid="Base_Column_List" />
    from ${tbname} TB
    where 1=1 
    <#if cols?exists>
		<#list cols as col>
	  		<if test="params.${col.javaName} != null" >
		      and TB.${col.columnName} ${col.pageJavaAndJdbcType} 
		    </if>
		</#list>
		    <if test="params.zorderBy">
		         order by ${r"${params.zorderBy}"}
		    </if>
	 </#if>
  </select>
  <select id="findByParams" resultMap="BaseResultMap" parameterType="map" >
   select
    <include refid="Base_Column_List" />
    from ${tbname} TB
    where 1=1 
    <#if cols?exists>
    	<if test="sids != null" >
	      and TB.SID in
	      <foreach item="item" index="index" collection="sids" open="(" separator="," close=")">  
		  	${r"#{item}"}
		  </foreach>
	    </if>
		<#list cols as col>
	  		<if test="${col.javaName} != null" >
		      and TB.${col.columnName} ${col.javaParam} 
		    </if>
		</#list>
		    <if test="zorderBy">
		         order by ${r"${zorderBy}"}
		    </if>
	 </#if>
  </select>
  <#if pkCol?exists>
  <delete id="deleteByPrimaryKey" parameterType="${pkCol.parameterType}" >
    delete from ${tbname}
    where ${pkCol.columnName} = ${pkCol.javaAndJdbcType}
  </delete>
  </#if>
  <delete id="deleteByParams" parameterType="map" >
    delete from ${tbname}
    where 1=1
    <#if cols?exists>
		<#list cols as col>
	  		<if test="${col.javaName} != null" >
		      and ${col.columnName} ${col.javaParam} 
		    </if>
		</#list>		    
	 </#if>
  </delete>
  <insert id="insertSelective" parameterType="${type}" >
    insert into ${tbname}
    <trim prefix="(" suffix=")" suffixOverrides="," >
      <#if cols?exists>
		<#list cols as col>
	  <if test="${col.javaName} != null" >
        ${col.columnName},
      </if>
		</#list>
	 </#if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
     <#if cols?exists>
		<#list cols as col>
	  <if test="${col.javaName} != null" >
        ${col.javaAndJdbcType},
      </if>
		</#list>
	 </#if>
    </trim>
  </insert>
  <#if pkCol?exists>
  <update id="updateByPrimaryKeySelective" parameterType="${type}" >
    update ${tbname}
    <set >
      <#if cols?exists>
		<#list cols as col>
		<#if col.columnName != "SID">
	 		 <if test="${col.javaName} != null" >
		        ${col.columnName}=${col.javaAndJdbcType},
		      </if>
		</#if>
		</#list>
	 </#if>
    </set>
    where ${pkCol.columnName} = ${pkCol.javaAndJdbcType}
  </update>
  </#if>
</mapper>