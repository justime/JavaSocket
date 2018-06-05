package org.vean.common;

public enum CharsetEnum {
	UTF8("utf-8", (byte) 1), // 默认编码方式
	GBK("gbk", (byte) 2), UTF16("utf-16", (byte) 3);

	private String charsetName;
	private byte charsetByte;

	private CharsetEnum(String charsetName, byte charsetByte) {
		this.charsetName = charsetName;
		this.charsetByte = charsetByte;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public byte getCharsetByte() {
		return charsetByte;
	}

	public void setCharsetByte(byte charsetByte) {
		this.charsetByte = charsetByte;
	}

	/**
	 * 判断当前是否为指定的字符集
	 * 
	 * @param charsetName
	 * @return
	 */
	public boolean isCharsetName(String charsetName) {
		if (charsetName == null)
			return false;
		charsetName = charsetName.toLowerCase().trim();
		return charsetName.equals(this.charsetName);
	}

	/**
	 * 判断当前是否为指定的字符集
	 * 
	 * @param charsetName
	 * @return
	 */
	public boolean isCharsetByte(byte charsetByte) {
		return this.charsetByte == charsetByte;
	}

	/**
	 * 根据字符集编号返回字符集名称
	 * 
	 * @param charsetByte
	 * @return
	 */
	public static String getCharsetNameByByte(byte charsetByte) {
		for (CharsetEnum charsetEnum : CharsetEnum.values()) {
			if (charsetEnum.isCharsetByte(charsetByte))
				return charsetEnum.getCharsetName();
		}
		throw new RuntimeException("不支持编号为" + charsetByte + "的字符集！");
	}

	/**
	 * 根据字符集名称返回字符集编号
	 * 
	 * @param charsetByte
	 * @return
	 */
	public static byte getCharsetByteByName(String charsetName) {
		for (CharsetEnum charsetEnum : CharsetEnum.values()) {
			if (charsetEnum.isCharsetName(charsetName))
				return charsetEnum.getCharsetByte();
		}
		throw new RuntimeException("不支持字符集" + charsetName + "!");
	}

	/**
	 * 根据字符集名称返回字符集，默认为utf8
	 * 
	 * @param charsetName
	 * @return
	 */
	public static CharsetEnum getCharsetByName(String charsetName) {
		for (CharsetEnum charsetEnum : CharsetEnum.values()) {
			if (charsetEnum.isCharsetName(charsetName))
				return charsetEnum;
		}
		return CharsetEnum.UTF8;
	}

	/**
	 * 根据字符集编号返回字符集，默认为utf8
	 * 
	 * @param charsetName
	 * @return
	 */
	public static CharsetEnum getCharsetByByte(byte charsetByte) {
		for (CharsetEnum charsetEnum : CharsetEnum.values()) {
			if (charsetEnum.isCharsetByte(charsetByte))
				return charsetEnum;
		}
		return CharsetEnum.UTF8;
	}

}
