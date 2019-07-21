package com.dev.rest.message;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApiResponse {
	List<KakaoApiBookInfo> documents = new ArrayList<>();
	KakaoApiMetaInfo meta = new KakaoApiMetaInfo();
}
