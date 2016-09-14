package net.watermelon.product.web;

import java.util.List;

import net.watermelon.admin.menu.vo.MenuMenu;


import net.watermelon.core.BaseDAO;
import net.watermelon.product.vo.ProductInformation;
import net.watermelon.product.vo.ProductSearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

	/**
	 * Prodcut Information 页面，显示 Product List
	 */

	@Autowired
	private BaseDAO baseDAO;

	@RequestMapping("/index")
	public String indexSearch(ProductSearchCriteria productSearchCriteria,
			Model model) {
		List<MenuMenu> list = baseDAO
				.getObjectLists(productSearchCriteria.getSQL());
		model.addAttribute("list", list);
		return "/product/index";
	}

	// 获得Product 编辑页面
	@RequestMapping("/edit")
	public String setupForm(Model model) {
		ProductInformation product = new ProductInformation();
		model.addAttribute("product", product);
		return "/product/edit";
	}

	// 获得Product Price编辑页面
	@RequestMapping("/price")
	public String productPrice(Model model) {
		ProductInformation product = new ProductInformation();
		model.addAttribute("product", product);
		return "/product/price";
	}

	// 获得 SEO 编辑页面
	@RequestMapping("/seo")
	public String productSeo(Model model) {
		ProductInformation product = new ProductInformation();
		model.addAttribute("product", product);
		return "/product/seo";
	}

	// 关联发布目录
	@RequestMapping("/associations")
	public String productAssociations(Model model) {
		ProductInformation product = new ProductInformation();
		model.addAttribute("product", product);
		return "/product/associations";
	}

	// 数量管理 仓库的数量管理，也可以管理打包产品的数量
	@RequestMapping("/quantitle")
	public String productQuatitles(Model model) {
		ProductInformation product = new ProductInformation();
		model.addAttribute("product", product);
		return "/product/quantitle";
	}

	// 商品图片管理
	@RequestMapping("/images")
	public String productImages(Model model) {
		ProductInformation product = new ProductInformation();
		model.addAttribute("product", product);
		return "product/images";
	}

	/**
	 * 管理商品的私有化属性
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/feature")
	public String productFeatures(Model model) {
		return "product/feature";

	}

	/**
	 * 设置自定义内容显示的数量
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/customization")
	public String Customization(Model model) {
		return "product/customization";

	}

	/**
	 * 添加和修改附件
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/attchment")
	public String Attchment(Model model) {
		return "product/attchment";

	}

	/**
	 * 供应商管理
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/suppliers")
	public String Suppliers(Model model) {
		return "product/suppliers";

	}
}
