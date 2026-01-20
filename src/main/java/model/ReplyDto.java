package model;

public class ReplyDto {

	private int bno;
	private int blog_bno;
	private String userid;
	private String replytext;
	private String regdate;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getBlog_bno() {
		return blog_bno;
	}
	public void setBlog_bno(int blog_bno) {
		this.blog_bno = blog_bno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getReplytext() {
		return replytext;
	}
	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
    
}
