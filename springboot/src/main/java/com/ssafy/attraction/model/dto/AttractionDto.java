package com.ssafy.attraction.model.dto;

public class AttractionDto {
	// info
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String firstImage;
	private String firstImage2;
	private int readcount;
	private int sidoCode;
	private int gugunCode;
	private double latitude;
	private double longitude;
	private String mlevel;
	// description
	private String hompage;
	private String overview;
	private String telname;
	// detail
	private String cat1;
	private String cat2;
	private String cat3;
	private String createdTime;
	private String modifiedTime;
	private String booktour;
	private int scrap_cnt;
	
	public AttractionDto() {
		// TODO Auto-generated constructor stub
	}
	
	public AttractionDto(int contentId, int contentTypeId, String title, String addr1, String addr2, String zipcode,
			String tel, String firstImage, String firstImage2, int readcount, int sidoCode, int gugunCode,
			double latitude, double longitude, String mlevel, String hompage, String overview, String telname,
			String cat1, String cat2, String cat3, String createdTime, String modifiedTime, String booktour,
			int scrap_cnt) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zipcode = zipcode;
		this.tel = tel;
		this.firstImage = firstImage;
		this.firstImage2 = firstImage2;
		this.readcount = readcount;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mlevel = mlevel;
		this.hompage = hompage;
		this.overview = overview;
		this.telname = telname;
		this.cat1 = cat1;
		this.cat2 = cat2;
		this.cat3 = cat3;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
		this.booktour = booktour;
		this.scrap_cnt = scrap_cnt;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public String getFirstImage2() {
		return firstImage2;
	}

	public void setFirstImage2(String firstImage2) {
		this.firstImage2 = firstImage2;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getMlevel() {
		return mlevel;
	}

	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}

	public String getHompage() {
		return hompage;
	}

	public void setHompage(String hompage) {
		this.hompage = hompage;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getTelname() {
		return telname;
	}

	public void setTelname(String telname) {
		this.telname = telname;
	}

	public String getCat1() {
		return cat1;
	}

	public void setCat1(String cat1) {
		this.cat1 = cat1;
	}

	public String getCat2() {
		return cat2;
	}

	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}

	public String getCat3() {
		return cat3;
	}

	public void setCat3(String cat3) {
		this.cat3 = cat3;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getBooktour() {
		return booktour;
	}

	public void setBooktour(String booktour) {
		this.booktour = booktour;
	}

	public int getScrap_cnt() {
		return scrap_cnt;
	}

	public void setScrap_cnt(int scrap_cnt) {
		this.scrap_cnt = scrap_cnt;
	}

	@Override
	public String toString() {
		return "AttractionDto [contentId=" + contentId + ", contentTypeId=" + contentTypeId + ", title=" + title
				+ ", addr1=" + addr1 + ", addr2=" + addr2 + ", zipcode=" + zipcode + ", tel=" + tel + ", firstImage="
				+ firstImage + ", firstImage2=" + firstImage2 + ", readcount=" + readcount + ", sidoCode=" + sidoCode
				+ ", gugunCode=" + gugunCode + ", latitude=" + latitude + ", longitude=" + longitude + ", mlevel="
				+ mlevel + ", hompage=" + hompage + ", overview=" + overview + ", telname=" + telname + ", cat1=" + cat1
				+ ", cat2=" + cat2 + ", cat3=" + cat3 + ", createdTime=" + createdTime + ", modifiedTime="
				+ modifiedTime + ", booktour=" + booktour + ", scrap_cnt=" + scrap_cnt + "]";
	}
}
