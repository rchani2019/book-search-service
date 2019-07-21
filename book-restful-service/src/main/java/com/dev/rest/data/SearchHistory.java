package com.dev.rest.data;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_SEARCH_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Column(columnDefinition = "DATETIME", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonIgnore
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "userNo")
	private User user;

	public SearchHistory(Integer historyNo, String keyword) {
		super();
		this.historyNo = historyNo;
		this.keyword = keyword;
	}
}
