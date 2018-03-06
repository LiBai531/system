package com.physics.common.exception;

public class BusinessException2 extends BusinessException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 信息类型,text为普通文本；json为json类型
	 */
	private String messageType;
	public BusinessException2() {
		// TODO Auto-generated constructor stub
		super();
	}
	public BusinessException2(String message)
	{
		super(message);
	}
	public BusinessException2(String message,String type)
	{
		super(message);
		setMessageType(type);
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}
