package beans;

import beans.Enums.CommentMarkEnum;

public class Comment {
	
	private int id;
	private User buyerComment;
	private SportObject sportObjectComment;
	private String comment;
	private int commentMark;
	
	public Comment(int id, User buyerComment, SportObject sportObjectComment, String comment,
			int commentMark) {
		super();
		this.id = id;
		this.buyerComment = buyerComment;
		this.sportObjectComment = sportObjectComment;
		this.comment = comment;
		this.commentMark = commentMark;
	}

	public Comment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getBuyerComment() {
		return buyerComment;
	}

	public void setBuyerComment(User buyerComment) {
		this.buyerComment = buyerComment;
	}

	public SportObject getSportObjectComment() {
		return sportObjectComment;
	}

	public void setSportObjectComment(SportObject sportObjectComment) {
		this.sportObjectComment = sportObjectComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCommentMark() {
		return commentMark;
	}

	public void setCommentMark(int commentMark) {
		this.commentMark = commentMark;
	}

	public String fileLine() {
		return id + ";" + buyerComment + ";" + sportObjectComment + ";" + comment + ";" + commentMark;
	}
	
	
	
	
}
