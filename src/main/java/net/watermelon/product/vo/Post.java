package net.watermelon.product.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "wp_posts")
public class Post implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID") //自动编号
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "post_author") //作者编号
	Integer author;

	@Column(name = "post_date") //创建日期
	Date postDate;

	@Column(name = "post_content") //内容
	String content;
	
	@Column(name = "product_content_addition") //内容
	String contentAddition;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getContentFiltered() {
		return contentFiltered;
	}

	public void setContentFiltered(String contentFiltered) {
		this.contentFiltered = contentFiltered;
	}

	@Column(name = "post_title") //标题
	private String title;

	@Column(name = "post_excerpt") //描述
	private String excerpt;

	@Column(name = "post_status") //状态
	private String status;

	@Column(name = "comment_status") //是否可以评价
	private String commentStatus;

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	@Column(name = "post_password") //浏览密码
	private String password;

	@Column(name = "post_name") //原始名字，可以是上传附件的名字
	private String name;

	@Column(name = "post_modified") //修改日期
	private Date modified;

	public String getMimeType() {
		return mimeType;
	}

	public boolean getIsImg() {
		boolean op = mimeType.startsWith("image");
		return op;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Column(name = "post_content_filtered") //内容过滤标准，可以加入过滤词或者过滤标准
	private String contentFiltered;

	@Column(name = "post_parent")
	private Integer parent;

	private String guid;   //生成的静态文件路径

	@Column(name = "menu_order") //排列次序
	private Integer menuOrder;

	@Column(name = "post_type") //类型，可以是 post ,attchment,或者其他(自己定义)
	private String type;

	@Column(name = "post_mime_type") //类型， 类似 jpg,等等，就是上传附件的头
	private String mimeType;

	@Column(name = "comment_count") //评价数量
	private Integer commentCount;







	
	
	// 设定延迟加载
		@OneToMany(fetch = FetchType.LAZY)
		@Cascade(org.hibernate.annotations.CascadeType.DELETE)
		@JoinColumn(name = "post_parent", updatable = false, insertable = false)
		private List<Post> postSons; //文档的其他属性
		public List<Post> getPostSons() {
			return postSons;
		}
		public void setPostSons(List<Post> postSons) {
			this.postSons = postSons;
		}
 /** 后面加上商城管理的内容
  * 
  * 
  */
		
		@Column(name = "post_title_img") //标题图片，或者是产品的缩略图
		private String titleImg;
		
		@Column(name = "product_price") //此类商品的价格或者价格范围。
		private String productPrice;
		
		@Column(name = "product_rate_it") //推荐评价 （几颗星）
		private String productRateIt;
		
		@Column(name = "product_brand") 
		private String productBrand;

		public String getTitleImg() {
			return titleImg;
		}

		public void setTitleImg(String titleImg) {
			this.titleImg = titleImg;
		}

		public String getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(String productPrice) {
			this.productPrice = productPrice;
		}

		public String getProductRateIt() {
			return productRateIt;
		}

		public void setProductRateIt(String productRateIt) {
			this.productRateIt = productRateIt;
		}

		public String getProductBrand() {
			return productBrand;
		}

		public void setProductBrand(String productBrand) {
			this.productBrand = productBrand;
		}

		public String getContentAddition() {
			return contentAddition;
		}

		public void setContentAddition(String contentAddition) {
			this.contentAddition = contentAddition;
		}
		
		
}
