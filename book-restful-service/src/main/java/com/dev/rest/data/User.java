package com.dev.rest.data;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User extends BaseSerializable {
	
	private static final long serialVersionUID = 8581048116579437700L;
	
    /*
     * 사용자 번호
     */
    @Id
    @GeneratedValue
    @Column(columnDefinition = "INT UNSIGNED")	
	private Integer userNo;
    
    /*
     * 사용자 아이디 
     */
    @Column(length = 20, nullable = false)
	private String userId;
	
    /*
     * 비밀번호 
     */
    @Column(length = 100, nullable = false)
	private String password;
	
    /*
     * 이름 
     */
    @Column(length = 10, nullable = false)
	private String name;
    
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<SearchHistory> searchHistorys = new ArrayList<>();

	public User(String userId, String password, String name) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
	}
}
