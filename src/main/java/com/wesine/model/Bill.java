package com.wesine.model;

public class Bill {

	private String id;//后台生成唯一uuid
	
	private String Start;
	
	private String End;
	
	private String VideoUrl;
	
	private String PictureUrl0;
	
	private String PictureUrl1;
	
	private String PictureUrl2;
	
	private String PictureUrl3;
	
	private String Sku;
	
	private String Text;
	
	private int CenterX;
	
	private int CenterY;
	
	private String Type;
	
	private boolean Hide;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart() {
		return Start;
	}

	public void setStart(String start) {
		Start = start;
	}

	public String getEnd() {
		return End;
	}

	public void setEnd(String end) {
		End = end;
	}

	public String getVideoUrl() {
		return VideoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		VideoUrl = videoUrl;
	}

	public String getPictureUrl0() {
		return PictureUrl0;
	}

	public void setPictureUrl0(String pictureUrl0) {
		PictureUrl0 = pictureUrl0;
	}

	public String getPictureUrl1() {
		return PictureUrl1;
	}

	public void setPictureUrl1(String pictureUrl1) {
		PictureUrl1 = pictureUrl1;
	}

	public String getPictureUrl2() {
		return PictureUrl2;
	}

	public void setPictureUrl2(String pictureUrl2) {
		PictureUrl2 = pictureUrl2;
	}

	public String getPictureUrl3() {
		return PictureUrl3;
	}

	public void setPictureUrl3(String pictureUrl3) {
		PictureUrl3 = pictureUrl3;
	}

	public String getSku() {
		return Sku;
	}

	public void setSku(String sku) {
		Sku = sku;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public int getCenterX() {
		return CenterX;
	}

	public void setCenterX(int centerX) {
		CenterX = centerX;
	}

	public int getCenterY() {
		return CenterY;
	}

	public void setCenterY(int centerY) {
		CenterY = centerY;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public boolean isHide() {
		return Hide;
	}

	public void setHide(boolean hide) {
		Hide = hide;
	}

	public Bill(String id, String start, String end, String videoUrl, String pictureUrl0, String pictureUrl1,
			String pictureUrl2, String pictureUrl3, String sku, String text, int centerX, int centerY, String type,
			boolean hide) {
		super();
		this.id = id;
		Start = start;
		End = end;
		VideoUrl = videoUrl;
		PictureUrl0 = pictureUrl0;
		PictureUrl1 = pictureUrl1;
		PictureUrl2 = pictureUrl2;
		PictureUrl3 = pictureUrl3;
		Sku = sku;
		Text = text;
		CenterX = centerX;
		CenterY = centerY;
		Type = type;
		Hide = hide;
	}

	public Bill(String start, String end, String videoUrl, String pictureUrl0, String pictureUrl1, String pictureUrl2,
			String pictureUrl3, String sku, String text, int centerX, int centerY, String type, boolean hide) {
		super();
		Start = start;
		End = end;
		VideoUrl = videoUrl;
		PictureUrl0 = pictureUrl0;
		PictureUrl1 = pictureUrl1;
		PictureUrl2 = pictureUrl2;
		PictureUrl3 = pictureUrl3;
		Sku = sku;
		Text = text;
		CenterX = centerX;
		CenterY = centerY;
		Type = type;
		Hide = hide;
	}
	
	
	
	
	
	
	
}
