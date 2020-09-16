package web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.SessionScope;

import web.entity.*;

@Transactional
@Controller
@RequestMapping("/user")
public class ClientController{

	USERS user = new USERS();
	PRODUCTS product = new PRODUCTS();
	ORDERS order = new ORDERS();
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	@Autowired
	JavaMailSender mailer;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@RequestMapping("/product")
	public String index(ModelMap model, HttpServletRequest  request){
		request.setAttribute("count", countProd(username));
		return "client/product";
	}
	
	@RequestMapping(value = "/forget-pass", method = RequestMethod.GET)
	public String forget() {
		return "client/success";
	}
	
	@RequestMapping(value = "/forget-pass", method = RequestMethod.POST)
	public String forget(ModelMap model, @ModelAttribute("u") USERS u) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "FROM USERS";
		Query query = session.createQuery(hql);
		List<USERS> listAcc = query.list();
		for(USERS user: listAcc) {
			if(user.getUsername().equals(u.getUsername())) {
				int code = (int) Math.floor(((Math.random() * 8999999) + 1000000));
				String kq=String.valueOf(code);
				try {
					user.setPassword(kq);
					session.update(user);
					t.commit();
					sendMail(user.getEmail(), user.getPassword());
					model.addAttribute("message", true);
				}
				catch(Exception e) {
					t.rollback();
					model.addAttribute("message", false);
				}
				finally {
					session.close();
				}
			}
		}
		return "client/success";
	}
	@RequestMapping("/{type}")
	public String code(ModelMap model, HttpServletRequest  request, @PathVariable("type") String type){
		Session session = factory.getCurrentSession();
		String hql1 = "FROM PRODUCTS p WHERE p.type LIKE :type";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("type", type);
		List<PRODUCTS> list1 = query1.list();
		model.addAttribute("sort", list1);

		request.setAttribute("count", countProd(username));
		return "client/sort";
	}
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model) throws ServletException, IOException {
		String se = request.getParameter("se").toString();
		if(se.equals("false")) {
			request.getSession().removeAttribute("username");
			request.getSession().removeAttribute("count");
		}
		return "client/login";
	}


	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@ModelAttribute("users") USERS user, HttpServletRequest request, ModelMap model, BindingResult errors)
			throws ServletException, IOException {
		Session session = factory.getCurrentSession();
		String hql = "FROM USERS";
		Query query = session.createQuery(hql);
		List<USERS> listAcc = query.list();
		if(user.getUsername().trim().length() == 0) {
			errors.rejectValue("username", "users", "Vui lòng nhập tên người dùng!");
		}
		if(user.getPassword().trim().length() == 0) {
			errors.rejectValue("password", "users", "Vui lòng nhập mật khẩu!");
		}
		if(errors.hasErrors()) {
			model.addAttribute("message1", "Vui lòng điền thông tin đầy đủ!");
		}
		else {
			boolean check = true;
			for (USERS ktra : listAcc) {
				if (user.getUsername().equals(ktra.getUsername()) && user.getPassword().equals(ktra.getPassword()) && ktra.getDisable().trim().equals("n")){
					
					check = false;
					username = ktra.getUsername();
					HttpSession s = request.getSession();
			        s.setAttribute("username", ktra.getUsername());
			        s.setAttribute("count", countProd(ktra.getUsername()));
					if(ktra.getStatus().equals("A")) {
						return "redirect:/ad/home.htm";
					}
				}
			}
			if(check == false) {
				return "redirect:/user/home.htm";
			}
			else {
				model.addAttribute("message", "Tên tài khoản hoặc mật khẩu không chính xác.");
			}
		}

		return "client/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(ModelMap model) throws ServletException, IOException {
		model.addAttribute("users", new USERS());
		return "client/register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(@ModelAttribute("users") USERS user, BindingResult errors, HttpServletRequest request, ModelMap model)
			throws ServletException, IOException {
		String pass_conf= request.getParameter("pass_conf");
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		boolean check = true;
		String hql = "FROM USERS";
		Query query = s.createQuery(hql);
		List<USERS> listAcc = query.list();


		if(user.getUsername().trim().length() == 0) {
			errors.rejectValue("username", "users", "Vui lòng nhập tên người dùng!");
		}		
		if(user.getAddress().trim().length() == 0) {
			errors.rejectValue("address", "users", "Vui lòng nhập địa chỉ!");
		}
		
		if(user.getPhone().trim().length() == 0) {
			errors.rejectValue("phone", "users", "Vui lòng nhập số điện thoại!");
		}
		if(user.getPassword().trim().length() == 0) {
			errors.rejectValue("password", "users", "Vui lòng nhập mật khẩu!");
		}
		if(user.getPassword().equals(pass_conf)) {
			
		}
		else {
			errors.rejectValue("password", "users", "Mật khẩu không giống nhau!");
		}
		if(errors.hasErrors()) {
			model.addAttribute("message1", "Vui lòng điền thông tin đầy đủ!");
		}
		else {
			for (USERS ktra : listAcc) {
				if (user.getUsername().equals(ktra.getUsername())) {
					check = false;
				}
			}
			if(check == true) {
				user.setStatus("C");
				user.setDisable("n");
				s.save(user);
				t.commit();
				model.addAttribute("message", true);
			}
			else {
				model.addAttribute("message", false);
			}
		}

		return "client/register";
	}
	
	
	@RequestMapping(value = "/infor", method = RequestMethod.GET)
	public String infor(ModelMap model, HttpServletRequest request) throws ServletException, IOException {
		
		Session session = factory.getCurrentSession();
		String hql = "FROM USERS";
		Query query = session.createQuery(hql);
		List<USERS> list = query.list();
		for(USERS ktra:list) {
			if(username.equals(ktra.getUsername())) {
				model.addAttribute("inf", ktra);
			}
		}
		request.setAttribute("count", countProd(username));

		return "client/infor";
	}

	@RequestMapping(value = "/infor", method = RequestMethod.POST)
	public String infor(@ModelAttribute("inf") USERS in, ModelMap model, HttpServletRequest request) {

		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM USERS";
		Query query = s.createQuery(hql);
		List<USERS> list = query.list();
		int id = Integer.parseInt(request.getParameter("id"));
		for(USERS u : list) {
			if(u.getId() == id) {
				try {
					u.setAddress(in.getAddress());
					u.setPassword(in.getPassword());
					u.setPhone(in.getPhone());
					u.setUsername(in.getUsername());
					u.setFullname(in.getFullname());
					u.setEmail(in.getEmail());
					s.update(u);
					t.commit();
					model.addAttribute("message1", "Lưu thành công!!");
					return "redirect:/user/infor.htm";
				}
				catch(Exception e) {
					t.rollback();
					model.addAttribute("message1", "Lưu không thành công!!");
					return "redirect:/user/infor.htm";
				}
				finally {
					s.close();
				}
			}
		}
		return "redirect:/user/infor.htm";
	}
	@RequestMapping("/home")
	public String home(HttpServletRequest request) {
		request.setAttribute("count", countProd(username));
		return "client/home";
	}
	@RequestMapping("/about")
	public String about(HttpServletRequest request) {
		request.setAttribute("count", countProd(username));
		return "client/about";
	}
	
	@RequestMapping(value="/show_prod", method=RequestMethod.GET)
	public String showProd(ModelMap model, HttpServletRequest request) {
		model.addAttribute("order", new ORDERS());
		int code = Integer.parseInt(request.getParameter("code"));
		request.setAttribute("prod", selectProd(code));
		request.setAttribute("count", countProd(username));
		return "client/show_prod";
		
	}
	@RequestMapping(value="/show_prod", method=RequestMethod.POST)
	public String showProd(ORDERS orders, ModelMap model, HttpServletRequest request, BindingResult errors)throws ServletException, Exception {
		if(username == null) {
			return "redirect:/user/login.htm";
		}
		else {
			Session s1 = factory.openSession();
			Transaction t = s1.beginTransaction();
			int code = Integer.parseInt(request.getParameter("code"));
			request.setAttribute("prod", selectProd(code));
			try {
				orders.setDateCreate(new Date());
				orders.setUsers_orders(selectUser(username));
				orders.setProducts_orders(selectProd(code));;
				orders.setStatus("CHƯA XÁC NHẬN");
				orders.setDetail("N");
				s1.save(orders);
				t.commit();
				model.addAttribute("message", "Thêm vào giỏ hàng thành công!");
				request.setAttribute("count", countProd(username));
				return "client/show_prod";
			}
			catch(Exception e) {
				t.rollback();
				model.addAttribute("message", "k thanh cong");
				return "client/show_prod";
			}
		}
	}
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	public String cart(ModelMap model, HttpServletRequest request) {
		
		if(username == null) {
			return "redirect:/user/login.htm";
		}
		else {

			if(countProd(username) == 0) {
				model.addAttribute("mess", "Không có sản phẩm nào!");
			}

			request.setAttribute("count", countProd(username));
			return "client/cart";
		}
	}
	@RequestMapping(value="/cart", method = RequestMethod.POST)
	public String cart(PRODUCTS prod, ModelMap model, HttpServletRequest request) {
		return "client/cart";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, ModelMap model, HttpServletResponse rsp) {
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		int x = Integer.parseInt(request.getParameter("id"));
		
		String hql1 = "FROM ORDERS";
		Query query1 = s.createQuery(hql1);
		List<ORDERS> list1 = query1.list();
		for(ORDERS p: list1) {
			if(p.getProducts_orders().getCode() == x && p.getStatus().equals("CHƯA XÁC NHẬN")) {
				s.delete(p);
				t.commit();
				s.close();
				model.addAttribute("message1", true);
				rsp.addIntHeader("Refresh", 0); 
			}
			else {
				model.addAttribute("message1", false);
			}
		}
		request.setAttribute("count", countProd(username));
		return "client/cart";
	}
	
	@RequestMapping("change")
	public String change(HttpServletRequest request, ModelMap model) {
		Session s = factory.getCurrentSession();
		int x = Integer.parseInt(request.getParameter("id"));
		
		String hql1 = "FROM ORDERS";
		Query query1 = s.createQuery(hql1);
		List<ORDERS> list1 = query1.list();
		for(ORDERS p: list1) {
			if(p.getId() == x) {
				model.addAttribute("prod", selectProd(p.getProducts_orders().getCode()));
				
			}
		}
		request.setAttribute("count", countProd(username));
		return "client/show_prod";
	}
	
	@RequestMapping("buy")
	public String buy(HttpServletRequest request, ModelMap model) {
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		int x = Integer.parseInt(request.getParameter("id"));
		
		String hql1 = "FROM ORDERS";
		Query query1 = s.createQuery(hql1);
		List<ORDERS> list1 = query1.list();
		for(ORDERS p: list1) {
			if(p.getId() == x) {
				p.setDetail("Y");
				try{
					s.update(p);
					t.commit();
					return "redirect:/user/cart.htm";
				}
				catch(Exception e) {
					t.rollback();
					model.addAttribute("message", "Xóa không thành công!!");
				}
				finally {
					s.close();
				}
			}
		}
		request.setAttribute("count", countProd(username));
		return "client/cart";
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
	

	@RequestMapping("deleteAccount")
	public String deleteAcc(ModelMap model, HttpServletRequest request) {
		Session s = factory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM USERS";
		Query query = s.createQuery(hql);
		List<USERS> list = query.list();
		int id = Integer.parseInt(request.getParameter("id"));
		for(USERS u : list) {
			if(u.getId() == id) {
				try {
					u.setDisable("y");
					s.update(u);
					t.commit();
					model.addAttribute("message1", "Lưu thành công!!");
					return "redirect:/user/login.htm";
				}
				catch(Exception e) {
					t.rollback();
					model.addAttribute("message1", "Lưu không thành công!!");
					return "redirect:/user/infor.htm";
				}
				finally {
					s.close();
				}
			}
		}
		
		return "client/infor";
	}

	@ModelAttribute("user")
	public List<USERS> selectUser() {
		
		Session session = factory.getCurrentSession();
		String hql1 = "FROM USERS";
		Query query1 = session.createQuery(hql1);
		List<USERS> list1 = query1.list();
		return list1;
	}
	

	public USERS selectUser(String uname) {
		USERS user =null;
		Session session = factory.getCurrentSession();
		String hql = "from USERS";
		Query query = session.createQuery(hql);
		List<USERS> list = query.list();
		for(USERS u: list) {
			if(u.getUsername().equals(uname)) {
				user = u;
			}
		}
		return user;

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
	public OD_DETAIL selectOd_detail(int code) {
		OD_DETAIL od =null;
		Session session = factory.getCurrentSession();
		String hql = "from OD_DETAIL";
		Query query = session.createQuery(hql);
		List<OD_DETAIL> list = query.list();
		for(OD_DETAIL u: list) {
			if(u.getId() == code) {
				od = u;
			}
		}
		return od;

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
	
	public int countProd(String username) {
		int count = 0;
		Session session = factory.getCurrentSession();
		String hql1 = "FROM ORDERS";
		Query query1 = session.createQuery(hql1);
		List<ORDERS> list1 = query1.list();
		for(ORDERS o: list1) {
			if(o.getUsers_orders().getUsername().equals(username) && o.getStatus().equals("CHƯA XÁC NHẬN")) {
				count++;
			}
		}
		
		return count;
	}
	
	public boolean checkProd() {
		boolean check = false;
		Session session = factory.getCurrentSession();
		String hql1 = "FROM PRODUCTS";
		Query query1 = session.createQuery(hql1);
		List<PRODUCTS> list1 = query1.list();
		for(PRODUCTS p : list1) {
			if(p.getTotalNum() > 0) {
				check = true;
			}
		}
		return check;
	}
	
	@ModelAttribute("od_hide")
	public List<ORDERS> hideProd() {
		ModelMap model = null;
		
		Session session = factory.getCurrentSession();
		String hql = "from ORDERS o where o.status LIKE :status and o.users_orders.username LIKE :user";
		Query query = session.createQuery(hql);
		query.setParameter("status", "CHƯA XÁC NHẬN");
		query.setParameter("user", username);
		List<ORDERS> list = query.list();
		return list;
	}

//	public boolean validatePhone(String hex) {
//		Pattern pattern = Pattern.compile(phone);
//		Matcher matcher = pattern.matcher(hex); 
//		return matcher.matches();
//	}
//	public boolean validateUser(String hex) {
//		Pattern pattern = Pattern.compile(hex);
//		Matcher matcher = pattern.matcher(usernameRegex);
//		return matcher.matches();
//
//	}
	public boolean sendMail(String mailClient, String pass) {
		boolean check = true;
		String body = "Bạn đã ấn quên mật khẩu, đây là mật khẩu tạm thời của bạn:" + pass + ". Bạn hãy dùng mật khẩu này để tiến hành đăng nhập, sau đó vào thay đổi thành mật khẩu khác nếu không muốn bị hack tài khoản. Xin cảm ơn!";
				
		String from = "thuxyz7777@gmail.com";
		try{
			MimeMessage mail = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setFrom(from, from);
			helper.setTo(mailClient);
			helper.setReplyTo(from, from);
			helper.setSubject("Thông báo xác nhận đổi mật khẩu!");
			helper.setText(body, true);

			mailer.send(mail);
		}
		catch(Exception e){
			check = false;
		}
		return check;
	}
}

