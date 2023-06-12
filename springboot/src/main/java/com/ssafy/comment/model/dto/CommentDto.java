package com.ssafy.comment.model.dto;

public class CommentDto {
	private int commentId; // 댓글 id
	private String content; // 댓글 내용
	private String userId; // 댓글 작성자
	private String registerTime; // 댓글 작성된 시간
	private int articleId; // 댓글이 속한 게시글
	private int parent; // 댓글의 부모
	private int depth; // 댓글의 깊이
	private int groupNo; // 댓글이 어떤 댓글에 속하는지
	private int category; // 1 : 핫플, 2 : 공지사항, 3 : 친구모집
	
	/*     댓글id 속한글 내용       작성자            작성날짜          업데이트날짜   삭제여부   속한 댓글   부모 댓글   깊이
	    WITH RECURSIVE content_hierarchy AS (
		  SELECT comment_id, article_id, content, user_id, register_time, updatedate, hide, group_no, parent, depth, 1 AS level
		  FROM content
		  WHERE parent = 0 AND article_id = #{article_id}
		  UNION ALL
		  SELECT r.comment_id, r.article_id, r.content, r.user_id, r.register_time, r.updatedate, r.hide, r.group_no, r.parent, r.depth, rh.level + 1
		  FROM content r
		  INNER JOIN content_hierarchy rh ON r.parent = rh.comment_id
		)
		SELECT comment_id, article_id, content, user_id, register_time, updatedate, hide, group_no, parent, depth
		FROM content_hierarchy
		ORDER BY level ASC, comment_id ASC, group_no ASC
		LIMIT #{cri.pageNum} * #{cri.amount}
		OFFSET (#{cri.pageNum} - 1) * #{cri.amount};
		
		--------------------------------------------------------------------------
		
		 WITH RECURSIVE comments_hierarchy AS (
		    SELECT comment_id, article_id, content, user_id, register_time, updatedate, hide, group_no, parent, depth, 1 AS level
		    FROM comments
		    WHERE parent = 0 AND article_id = #{article_id}
		    UNION ALL
		    SELECT r.comment_id, r.article_id, r.content, r.user_id, r.register_time, r.updatedate, r.hide, r.group_no, r.parent, r.depth, rh.level + 1
		    FROM comments r
		    INNER JOIN comments_hierarchy rh ON r.parent = rh.comment_id
		  )
		  SELECT comment_id, article_id, content, user_id, register_time, updatedate, hide, group_no, parent, depth
		  FROM comments_hierarchy
		  ORDER BY level ASC, comment_id ASC, group_no ASC
		  
		  LIMIT #{pageNum} * #{amount}
		  OFFSET (#{pageNum} - 1) * #{amount}
		  
		  -----------------------------------------------------------------------------------------------------
		  
		  select comment_id, content, user_id, register_time, article_id, parent, depth, group_no, category
		from comments
		where article_id = #{articleId} and category = #{category}

 
	 */
	
	public CommentDto() {
		// TODO Auto-generated constructor stub
	}

	public CommentDto(int commentId, String content, String userId, String registerTime, int articleId, int parent,
			int depth, int groupNo, int category) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.userId = userId;
		this.registerTime = registerTime;
		this.articleId = articleId;
		this.parent = parent;
		this.depth = depth;
		this.groupNo = groupNo;
		this.category = category;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "commentDto [commentId=" + commentId + ", content=" + content + ", userId=" + userId + ", registerTime="
				+ registerTime + ", articleId=" + articleId + ", parent=" + parent + ", depth=" + depth + ", groupNo="
				+ groupNo + ", category=" + category + "]";
	}
	
	
}
