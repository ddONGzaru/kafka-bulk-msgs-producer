package io.manasobi.domain;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point implements Serializable {

	private static final long serialVersionUID = -4158233963308977048L;

	private long    timestamp;

	private String  tagId;
	private String  tagName;
	private String  type;

	private String  value;

	private String  siteId;
	private String  opcId;
	private String  groupName;

	private int    quality;
	private int    errorCode;
	
	private Map<String,String> attribute = Maps.newHashMap();

}
