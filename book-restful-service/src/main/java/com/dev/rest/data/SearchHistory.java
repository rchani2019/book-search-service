package com.dev.rest.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_SEARCH_HISTORY")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchHistory extends BaseSerializable {

	private static final long serialVersionUID = 7628709142820941993L;

	/*
	 * 히스토리 번호
	 */
	@Id
	@GeneratedValue
	@Column(columnDefinition = "INT UNSIGNED")
	private Integer historyNo;

	/*
	 * 키워드
	 */
	@Column(length = 100, nullable = false)
	private String keyword;

	@JsonIgnore
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "userNo")
	private User user;

	public SearchHistory(String keyword, User user) {
		super();
		this.keyword = keyword;
		this.user = user;
	}
}
