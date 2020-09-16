package web.controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sun.xml.internal.bind.v2.runtime.Location;

import web.entity.*;

@Transactional
@Controller
@RequestMapping("/ad")
public class AdminController {

	USERS user = new USERS();
	PRODUCTS product = new PRODUCTS();
	@Autowired
	ClientController cc;
	
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	@Autowired
	JavaMailSender mailer;
	
	@RequestMapping(value="/home")
	public String home(ModelMap model, HttpServletRequest request) {
		if(checkName(cc.getUsername()).equals("A")) {
			return "admin/home";
		}
		else {

			return "admin/error";
		}
	}
	
	@RequestMapping(value="add_prods", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request) {
		if(checkName(cc.getUsername()).equals("A")) {
			model.addAttribute("p", new PRODUCTS());
			return "admin/add_prods"; 
		}
		else {
			return "admin/error";
		}

	}
	@RequestMapping(value="add_prods", method = RequestMethod.POST)
	public String add(HttpServletResponse rsp, @ModelAttribute("p") PRODUCTS prod, @RequestParam(value="image1") MultipartFile img1, @RequestParam(value="image2") MultipartFile img2, @RequestParam(value="image3") MultipartFile img3, ModelMap model){
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		try {
			saveImage(img1);
			saveImage(img2);
			saveImage(img3);
			prod.setDateCreate(new Date());
			prod.setImg1(img1.getOriginalFilename());
			prod.setImg2(img2.getOriginalFilename());
			prod.setImg3(img3.getOriginalFilename());
			prod.setDisable("n");
			s.save(prod);
			t.commit();
			model.addAttribute("message", true);

			rsp.addIntHeader("Refresh", 0); 
		}
		catch(Exception e) {
			t.rollback();
			model.addAttribute("message", false);
		}
		finally {
			s.close();
		}
		return "admin/add_prods";
	}
	
	@RequestMapping(value="del_prods", method = RequestMethod.GET)
	public String del(ModelMap model) {
		if(checkName(cc.getUsername()).equals("A")) {
			return "admin/del_prods";
		}
		else {
			return "admin/error";
		}
	}
	@RequestMapping(value="del_prods", method = RequestMethod.POST)
	public String del(ModelMap model, HttpServletRequest request, HttpServletResponse rsp) throws ServletException, IOException{

		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		int code = Integer.parseInt(request.getParameter("codedel"));
		System.out.print(code);
		try {
			String hql = "from PRODUCTS where code LIKE :code";
			Query query = s.createQuery(hql);
			query.setParameter("code", code);
			List<PRODUCTS> list = query.list();
			for(PRODUCTS prod: list) {
				s.delete(prod);
				t.commit();
				model.addAttribute("message1", true);
				rsp.addIntHeader("Refresh", 0); 
			}
//			return "redirect:/ad/del_prods.htm";
		} catch (Exception e) {
			
			model.addAttribute("message1", false);
		} finally {
			s.close();
		}
		return "admin/del_prods";
	}

	int code = 0;
	@RequestMapping(value="update_prods", method = RequestMethod.GET)
	public String update(ModelMap model, HttpServletRequest request) {

		if("A".equals(checkName(cc.getUsername()))) {
			code = Integer.parseInt(request.getParameter("codeUpdate"));
			model.addAttribute("prods", selectProd(code));
			return "admin/update_prods";
		}
		else {
			return "admin/error";
		} 
	}
	@RequestMapping(value="update_prods", method = RequestMethod.POST)
	public String update(@ModelAttribute("prods") PRODUCTS prod, @RequestParam(value="image1") MultipartFile img1, @RequestParam(value="image2") MultipartFile img2, @RequestParam(value="image3") MultipartFile img3, ModelMap model, HttpServletRequest request) throws ServletException, IOException{
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "from PRODUCTS";
		Query query = s.createQuery(hql);
		List<PRODUCTS> list = query.list();
		for(PRODUCTS p : list) {
			if(p.getCode() == code) {
				try {
					p.setDes(prod.getDes());
					p.setName(prod.getName());
					p.setPrice(prod.getPrice());
					p.setType(prod.getType());
					p.setTotalNum(prod.getTotalNum());
					p.setDateCreate(new Date());
//					if(img1 != null) {
//						saveImage(img1);
//						p.setImg1(img1.getOriginalFilename());
//					}
//					if(img2 != null) {
//						saveImage(img2);
//						p.setImg2(img2.getOriginalFilename());
//					}
//					if(img3 != null) {
//						saveImage(img3);
//						p.setImg3(img3.getOriginalFilename());
//					}
					s.update(p);
					t.commit();
					model.addAttribute("message1", "Cập nhật thành công!!");
					return "redirect:/ad/del_prods.htm";
				} catch (Exception e) {
					
					model.addAttribute("message1", "Cập nhật không thành công!!");
					return "redirect:/ad/del_prods.htm";
				} finally {
					s.close();
				}
			}
		}
		return "admin/update_prods";
	}
	
	@RequestMapping(value="manage", method = RequestMethod.GET)
	public String manage(ModelMap model, HttpServletRequest request) {
		if("A".equals(checkName(cc.getUsername()))) {
			return "admin/manage_order";
		}
		else {
			return "admin/error";
		}
		
	}
	
	@RequestMapping(value = "manage", method = RequestMethod.POST)
	public String manage(OD_DETAIL od, ModelMap model, HttpServletRequest request) {
		
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		
		int id = Integer.parseInt(request.getParameter("id"));

		boolean check = false;
		
		try {
			od.setOrders_od_detail(selectOrders(id));
			String hql1 = "select u.email, p.name, p.price ,o.amount_o from USERS u, PRODUCTS p, ORDERS o where o.id like :id and p.code like o.products_orders.code and o.users_orders.id = u.id ";
			Query query1 = s.createQuery(hql1);
			query1.setParameter("id", id);
			List<Object[]> list1 = query1.list();
			String email = "";
			String name = "";
			float total1 = 1, price = 1;
			int amount = 1;
			for(Object[] ktra : list1) {
				email = ktra[0].toString();
				name = ktra[1].toString();
				amount = Integer.parseInt(ktra[2].toString());
				price = Float.parseFloat(ktra[3].toString());
				total1 = amount * price;
			}

			String hql = "from ORDERS where id LIKE :id";
			Query query = s.createQuery(hql);
			query.setParameter("id", id);
			List<ORDERS> list = query.list();
			for(ORDERS ktra:list) {
				if(ktra.getStatus().equals("CHƯA XÁC NHẬN") && ktra.getDetail().equals("Y")) {
					if(sendMail(email, ktra.getId(), name, ktra.getAmount_o(), total1) == true) {
						update(ktra.getProducts_orders().getCode(), ktra.getAmount_o());
						od.setAmount(amount);
						od.setPrice(price);
						od.setTotal(total1);
						s.save(od);
						ktra.setStatus("ĐÃ XÁC NHẬN");;
						s.update(ktra);
						t.commit();
					}
					else {
						model.addAttribute("message1", "Xác nhận hong thành công vì gửi mail chưa được!!");
					}
				}
				else {
					model.addAttribute("message1", "Đơn hàng đã được xác nhận rồi!!");
				}
			}
			return "redirect:/ad/manage.htm";

		} catch (Exception e) {
	
		} finally {
			s.close();
		}

		
		return "admin/manage_order";
	}
	
	
	@RequestMapping("/tk")
	public String tk(ModelMap model) {
		
		Session s = factory.getCurrentSession();
		String hql = "select o.dateCreate, COUNT(od.id), SUM(od.total) from OD_DETAIL od, ORDERS o where o.id LIKE od.orders_od_detail.id group by o.dateCreate";
		Query query = s.createQuery(hql);
		List<Object[]> list = query.list();
		model.addAttribute("list", list);
		return "admin/tk";
	}
	
	public ORDERS selectOrders(int code) {
		ORDERS o =null;
		Session session = factory.getCurrentSession();
		String hql = "from ORDERS";
		Query query = session.createQuery(hql);
		List<ORDERS> list = query.list();
		for(ORDERS u: list) {
			if(u.getId() == code) {
				o = u;
			}
		}
		return o;

	}
	
	public PRODUCTS selectProd(int code) {
		PRODUCTS prod =null;
		Session session = factory.getCurrentSession();
		String hql = "from PRODUCTS";
		Query query = session.createQuery(hql);
		List<PRODUCTS> list = query.list();
		for(PRODUCTS u: list) {
			if(u.getCode() == code) {
				prod = u;
			}
		}
		return prod;

	}
	
	
	@ModelAttribute("product")
	public List<PRODUCTS> selectProd() {
		
		Session session = factory.getCurrentSession();
		String hql1 = "FROM PRODUCTS";
		Query query1 = session.createQuery(hql1);
		List<PRODUCTS> list1 = query1.list();
		return list1;
	}

	@ModelAttribute("product_type")
	public List<Object[]> selectProdType() {
		
		Session session = factory.getCurrentSession();
		String hql1 = "SELECT DISTINCT p.type FROM PRODUCTS p";
		Query query1 = session.createQuery(hql1);
		List<Object[]> list1 = query1.list();
		return list1;
	}
	
	@ModelAttribute("test")
	public List<Object[]> test() {
		
		Session session = factory.getCurrentSession();
		String hql1 = "select o.dateCreate, o.id, p.name, o.amount_o, u.username " + 
				"from PRODUCTS p, USERS u, ORDERS o " + 
				"where p.code = o.products_orders.code and u.id = o.users_orders.id";
		Query query1 = session.createQuery(hql1);
		List<Object[]> list1 = query1.list();
		return list1;
	}
	
	@ModelAttribute("orders")
	public List<ORDERS> selectOrders() {
		
		Session session = factory.getCurrentSession();
		String hql1 = "FROM ORDERS";
		Query query1 = session.createQuery(hql1);
		List<ORDERS> list1 = query1.list();
		return list1;
	}
	
	@ModelAttribute("od")
	public List<OD_DETAIL> selectOrderDetail() {
		
		Session session = factory.getCurrentSession();
		String hql1 = "FROM OD_DETAIL";
		Query query1 = session.createQuery(hql1);
		List<OD_DETAIL> list1 = query1.list();
		return list1;
	}
	
	
	@ModelAttribute("od_hide")
	public List<ORDERS> hideProd() {
		
		Session session = factory.getCurrentSession();
		String hql = "from ORDERS where status LIKE :status and detail LIKE :detail";
		Query query = session.createQuery(hql);
		query.setParameter("status", "CHƯA XÁC NHẬN");
		query.setParameter("detail", "Y");
		List<ORDERS> list = query.list();
	
		return list;
	}
	
	public void update(int code, int quantity) {
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql1 = "from PRODUCTS";
		Query query1 = session.createQuery(hql1);
		List<PRODUCTS> list1 = query1.list();
		
		for(PRODUCTS p:list1) {
			if(p.getCode() == code) {
				int x = p.getTotalNum() - quantity;
				p.setTotalNum(x);
				session.update(p);
				t.commit();
				session.close();
			}
		}

	}
	
	public boolean sendMail(String mailClient, int id, String name, int quantity, float total) {
		boolean check = true;
		String body = "Đơn hàng "+ id + " đã được xác nhận, vui lòng kiểm tra thông tin đơn hàng!" + 
						"Tên sản phẩm: " + name +
						"Số lượng: " + quantity +
						"Tổng thanh toán: " + total +
						"Đơn hàng sẽ được giao 3-5 ngày kể từ ngày nhận mail, xin cảm ơn và không cần phản hồi lại mail này!";
				
		String from = "thuxyz7777@gmail.com";
		try{
			MimeMessage mail = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setFrom(from, from);
			helper.setTo(mailClient);
			helper.setReplyTo(from, from);
			helper.setSubject("Thông báo xác nhận đơn hàng!");
			helper.setText(body, true);

			mailer.send(mail);
		}
		catch(Exception e){
			check = false;
		}
		return check;
	}

	
	public String checkName(String username) {
		String status = "";
		Session session = factory.getCurrentSession();
		String hql1 = "from USERS";
		Query query1 = session.createQuery(hql1);
		List<USERS> list1 = query1.list();
		for(USERS a: list1) {
			if(a.getUsername().equals(username)) {
				status = a.getStatus();
				break;
			}
		}
		return status;
		
	}
	public void saveImage(MultipartFile image) {
		try {
			image.transferTo(new File(
					"D:\\eclipse-2020-03\\TuiXach\\WebContent\\img\\"
							+ image.getOriginalFilename()));
		} catch (Exception e) {
			System.out.println("Lỗi Lưu Ảnh");
			e.printStackTrace();
		}
	}
}
