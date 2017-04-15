/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.manasobi.utils;

import java.util.Random;

/**
 * Created by tw.jang on 16. 2. 1.
 */
public final class RandomUtils {
	
	private RandomUtils() { }
	
	private static final char[] ALPHAS = new char[] {'A', 'B', 'C', 'D', 'E',
		'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
		'S', 'T', 'U', 'X', 'Y', 'V', 'W', 'Z', 'a', 'b', 'c', 'd', 'e',
		'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
		's', 't', 'u', 'x', 'y', 'v', 'w', 'z' };

	private static final Random GENERATOR = new Random(System.currentTimeMillis());
	
	
	/** 랜덤한 문자열 생성에 사용 INT */
	private static final int RANDOM_ALPHABETIC_INT = 52;
	
	/** 랜덤한 한글문자열 생성에 사용 INT */
	private static final int RANDOM_STR_KR_INT = 11172;
	
	/** 랜덤한 한글문자열 생성에 사용 HEX */
	private static final int RANDOM_STR_KR_HEX = 0xAC00;
	
	/**
	 * 최소, 최대 자리수 사이의 랜덤한 문자열을 반환한다.<br><br>
	 *
	 * RandomUtils.getString(10, 15) = "jRTwRnLzSsOWC"
	 *
	 * @param minSize 최소 자리수
	 * @param maxSize 최대 자리수
	 * @return 최소, 최대 자리수 사이의 랜덤한 문자열
	 */
	public static String getString(int minSize, int maxSize) {
		Random generator = new Random(System.currentTimeMillis());
		int randomLength = generator.nextInt(maxSize - minSize) + minSize;

		return randomAlphabetic(randomLength);
	}

	/**
	 * 특정한 길이 만큼의 랜덤한 문자열을 반환한다.<br><br>
	 *
	 * RandomUtils.getString(8) = "ikwblpTL"
	 *
	 * @param count 원하는 문자열 길이
	 * @return 특정 길이 만큼의 랜덤한 문자열
	 */
	public static String getString(int count) {
		return randomAlphabetic(count);
	}
	
	private static String randomAlphabetic(int randomLength) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < randomLength; i++) {
			buf.append(ALPHAS[GENERATOR.nextInt(RANDOM_ALPHABETIC_INT)]);
		}
		return buf.toString();
	}

}
